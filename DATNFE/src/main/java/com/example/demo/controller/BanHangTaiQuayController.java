package com.example.demo.controller;

import com.example.demo.config.Config;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.GiamGiaChiTietHoaDon;
import com.example.demo.entity.GiamGiaHoaDon;
import com.example.demo.entity.GiaoHangNhanh;
import com.example.demo.entity.GiayChiTiet;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.PageDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.CallAPIGHN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

@Controller
public class BanHangTaiQuayController {

    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Autowired
    private HoaDonDAO hoaDonDAO;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private GiayChiTietRepo giayChiTietRepo;

    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;

    @Autowired
    private KhachHangRepo khachHangRepo;

    @Autowired
    private NhanVienDAO nhanVienDAO;

    @Autowired
    private DiaChiRepo diaChiRepo;

    @Autowired
    private DiachiDao diachiDao;

    @Autowired
    private GiamGiaHoaDonRepo giamGiaHoaDonRepo;

    @Autowired
    private GiamGiaChiTietHoaDonRepo giamGiaChiTietHoaDonRepo;

    @Autowired
    private GiayChiTietDAO giayChiTietDAO;

    @Autowired
    private HoaDonChiTietDAO hoaDonChiTietDAO;

    @Autowired
    private GiayDAO giayDAO;

    @Autowired
    private KichCoRepo kichCoRepo;

    @Autowired
    CallAPIGHN callAPIGHN;

    @Autowired
    QuyDoiDiemDAO quyDoiDiemDAO;

    @Autowired
    ViDiemDAO viDiemDAO;

    @Autowired
    LichSuTieuDiemDAO lichSuTieuDiemDAO;

    @Autowired
    KhachHangDao khachHangDao;

    @Autowired
    GiamgiahodonDao giamgiahodonDao;


    // Hiển thị danh sách hóa đơn đang chờ
    @RequestMapping("/admin/ban-hang-tai-quay")
    public String giamGiaHoaDon(@RequestParam("page") Optional<Integer> page,
                                Model model) {
        PageDTO<HoaDon> hoaDonPageDTO = hoaDonRepo.getAllHDchuaTTPage(page.orElse(0));
        model.addAttribute("hoaDonListchuaTT", hoaDonPageDTO);
        model.addAttribute("i", 0);
        return "banhangtaiquay/ban_hang_tai_quay";
    }

    // Xóa Hóa Đơn
    @RequestMapping("/admin/ban-hang-tai-quay/delete/{id}")
    public String deleteHoaDon(@PathVariable("id") UUID id) {
        hoaDonRepo.deleteHD(id);
        return "redirect:/admin/ban-hang";
    }

    @RequestMapping("/admin/ban-hang-tai-quay/huy-hoa-don/{maHD}")
    @Transactional
    public String huyHoaDon(@PathVariable("maHD") String maHD) {
        HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(maHD);
        hoaDon.setTrangthai(4);
        hoaDonRepo.createHoaDon(hoaDon);
        return "redirect:/admin/ban-hang";
    }

    // tìm kiếm hóa đơn theo mã hoặc theo tên khách hàng
    @RequestMapping("/admin/ban-hang-tai-quay/tim-kiem-hoa-don")
    public String locGiamGiaHoaDonTheoTen(@RequestParam(value = "keyword", required = false) String keyword,
                                          @RequestParam("page") Optional<Integer> page,
                                          Model model) {
        PageDTO<HoaDon> pageResult = hoaDonRepo.timkiemHoaDon(keyword, page.orElse(0));
        model.addAttribute("i", 0);
        model.addAttribute("hoaDonListchuaTT", pageResult);
        return "banhangtaiquay/ban_hang_tai_quay";
    }

    @PostMapping("/admin/ban-hang-tai-quay/tao-don-hang")
    public String taoHoaDonTaiQuay(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        NhanVien nv = nhanVienDAO.getNVByEmail(authentication.getName());
        HoaDon hoaDon = new HoaDon();
        if(nv == null)
        {
            return "redirect:/admin/ban-hang";
        }

        String maHoaDonMoi = hoaDonDAO.generateNextMaHoaDon();
        hoaDon.setMa(maHoaDonMoi);
        hoaDon.setNhanVien(nv);
        LocalDate currentDate = LocalDate.now();
        hoaDon.setNgay_tao(currentDate);
        hoaDon.setNgay_tao(currentDate);
        hoaDon.setHinh_thuc_mua(0);
        hoaDon.setTong_tien(BigDecimal.ZERO);
        hoaDon.setHinh_thuc_thanh_toan(0);
        hoaDon.setSo_tien_giam(BigDecimal.ZERO);
        hoaDon.setPhi_ship(BigDecimal.ZERO);
        hoaDon.setSo_tien_quy_doi(BigDecimal.ZERO);
        hoaDon.setSo_diem_su_dung(0);
        hoaDon.setTrangthai(0);
        hoaDonRepo.createHoaDon(hoaDon);
        redirectAttributes.addAttribute("maHD", maHoaDonMoi);
        return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
    }


    //View tạo đơn hàng
    @RequestMapping("/admin/ban-hang-tai-quay/view-cart/{maHD}")
    public String showsanpham(@PathVariable("maHD") String ma,
                              @RequestParam("keyword") Optional<String> keyword,
                              @RequestParam("size") Optional<String> size,
                              @RequestParam("page1") Optional<Integer> page1,
                              @RequestParam("page2") Optional<Integer> page2,
                              @RequestParam("keywordkh") Optional<String> keywordkh,
                              @RequestParam("searchhd") Optional<String> searchhd,
                              Model model) {

        Pageable pageable1 = PageRequest.of(page2.orElse(0), 12);
        PageDTO<GiayChiTiet> pageDTO1 = new PageDTO<>(giayChiTietDAO.getSearchsanpham("%" + keyword.orElse("") + "%", "%" + size.orElse("") + "%", pageable1));
        model.addAttribute("ListGiayChiTiet", pageDTO1); // Danh sách giày chi tiết theo Page

        Pageable pageable = PageRequest.of(page1.orElse(0), 5);
        PageDTO<HoaDonChiTiet> pageDTO = new PageDTO<>(hoaDonChiTietDAO.findByHoaDonMaPage(ma, pageable));
        model.addAttribute("hoaDonChiTietPage", pageDTO); // danh sách hóa đơn chi tiết theo page

        PageDTO<KhachHang> pageDTO2 = new PageDTO<>(khachHangDao.getSearchkhachhang("%" + keywordkh.orElse("") + "%", pageable));
        model.addAttribute("ListKhachHang", pageDTO2); //ListKhachHang

        PageDTO<GiamGiaHoaDon> pageDTO3 = new PageDTO<>(giamgiahodonDao.getSearchGiamgiahodonDao("%" + searchhd.orElse("") + "%", pageable));
        model.addAttribute("ListGiamGiaHoaDon", pageDTO3); //List Giảm giá hóa đơn

        model.addAttribute("maHD", ma); // Mã hóa đơn
        model.addAttribute("HoaDonDetail", hoaDonRepo.getHoaDonByMa(ma));
        model.addAttribute("dskichco", kichCoRepo.getListKichCo());

        HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(ma);
        model.addAttribute("trangthaicheck", hoaDon.getTrangthai());
        model.addAttribute("KhachHangcheck", hoaDon.getKhachHang());
        model.addAttribute("htm", hoaDon.getHinh_thuc_mua());
        model.addAttribute("httt", hoaDon.getHinh_thuc_thanh_toan());
        if (hoaDon.getKhachHang() != null) {
            model.addAttribute("KhachHangDetail", hoaDon.getKhachHang());
            List<DiaChi> diaChiList = diachiDao.getdiachibyma(hoaDon.getKhachHang().getMa());
            model.addAttribute("ListDiaChicuaKH", diaChiList);
            ViDiem viDiem = viDiemDAO.getViDiemByMaKH(hoaDon.getKhachHang().getMa());
            model.addAttribute("tongDiemQuyDoiHienCo",viDiem.getTong_diem());
            QuyDoiDiem quyDoiDiem = quyDoiDiemDAO.getQuyDoiDiemByTT1();
            Integer diemTichLuy = hoaDon.getTong_tien().divide(quyDoiDiem.getSo_tien_tuong_ung()).multiply(new BigDecimal(quyDoiDiem.getSo_diem_tuong_ung())).intValue();
            model.addAttribute("diemTichLuy", diemTichLuy);
        }

        String diaChiGop = hoaDon.getDia_chi();
        String xa = "";
        String huyen = "";
        String thanhPho = "";
        if (diaChiGop != null) {
            String[] diaChiDetails = diaChiGop.split(",");
            if (diaChiDetails.length >= 4) {
                String tenDiaChi = diaChiDetails[0].trim();
                xa = diaChiDetails[1].trim();
                huyen = diaChiDetails[2].trim();
                thanhPho = diaChiDetails[3].trim();

                model.addAttribute("tenNNDetail", hoaDon.getTen_nguoi_nhan());
                model.addAttribute("sdtDetail", hoaDon.getSdt_nguoi_nhan());
                model.addAttribute("tenDcDetail", tenDiaChi);
                model.addAttribute("xaDetail", xa);
                model.addAttribute("huyenDetail", huyen);
                model.addAttribute("thanhPhoDetail", thanhPho);
                model.addAttribute("moTaDetail", hoaDon.getMo_ta());
            }
        }

        BigDecimal tongTienTruocGiam = calculateTotal(hoaDon);
        model.addAttribute("phiShip", hoaDon.getPhi_ship());
        model.addAttribute("sodiemsudung", hoaDon.getSo_diem_su_dung());
        model.addAttribute("sotienquydoi", hoaDon.getSo_tien_quy_doi());

        tongTienTruocGiam = tongTienTruocGiam;
        model.addAttribute("TongTienTruocGiam", tongTienTruocGiam);

        model.addAttribute("soTienGiam", hoaDon.getSo_tien_giam());
        model.addAttribute("TongTienSauGiam", hoaDon.getTong_tien());

        GiamGiaChiTietHoaDon giamGiaChiTietHoaDon = giamGiaChiTietHoaDonRepo.getGiamGiaCTHoaDonByHD(hoaDon.getId());
        if (giamGiaChiTietHoaDon != null) {
            model.addAttribute("detailMaGGHD", giamGiaChiTietHoaDon.getGghd().getMa());

        } else {
            model.addAttribute("checkGGHD", 1);
        }

        return "banhangtaiquay/tao_don_hang";
    }

    @PostMapping("/admin/ban-hang-tai-quay/tao-don-hang/add-to-cart")
    public String themGiayVaoHoaDonChiTiet(@RequestParam("maHD") String maHD,
                                           @RequestParam("selectedIds") List<UUID> selectedIds,
                                           @RequestParam Map<String, String> quantities,
                                           RedirectAttributes redirectAttributes) {

        for (UUID idctsp : selectedIds) {
            HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(maHD);
            List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepo.getListHDCTbyMaHD(hoaDon.getMa());
            GiayChiTiet giayChiTiet = giayChiTietRepo.getGiayChiTietById(idctsp);
            boolean foundExistingChiTiet = false;
            int soLuong = Integer.parseInt(quantities.get("quantity[" + idctsp + "]"));

            // Truy xuất số lượng tồn từ GiayChiTiet
            Integer soLuongTon = giayChiTiet.getSo_luong_ton();

            if (soLuong <= soLuongTon) {
                // Số lượng tồn đủ, trừ đi số lượng tồn
                soLuongTon -= soLuong;

                // Cập nhật số lượng tồn trong giayChiTiet
                giayChiTiet.setSo_luong_ton(soLuongTon);
                giayChiTietDAO.save(giayChiTiet);

                for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                    if (hdct.getGiayChiTiet() == null) {
                        hdct.setHoaDon(hoaDon);
                        hdct.setGiayChiTiet(giayChiTiet);
                        hdct.setSo_luong(soLuong);
                        hdct.setGia_nhap(giayChiTiet.getGiay().getGianhap());
                        hdct.setDon_gia(giayChiTiet.getGiay().getGia_sau_khuyen_mai());
                        hdct.setTrangthai(1);
                        foundExistingChiTiet = true;
                        break;
                    } else if (hdct.getGiayChiTiet().getId().equals(idctsp)) {
                        hdct.setSo_luong(hdct.getSo_luong() + soLuong);
                        hdct.setDon_gia(giayChiTiet.getGiay().getGia_sau_khuyen_mai());
                        hdct.setGia_nhap(giayChiTiet.getGiay().getGianhap());
                        foundExistingChiTiet = true;
                        break;
                    }
                }

                if (!foundExistingChiTiet) {
                    HoaDonChiTiet newHoaDonChiTiet = new HoaDonChiTiet();
                    newHoaDonChiTiet.setGiayChiTiet(giayChiTiet);
                    newHoaDonChiTiet.setHoaDon(hoaDon);
                    newHoaDonChiTiet.setSo_luong(soLuong);
                    newHoaDonChiTiet.setGia_nhap(giayChiTiet.getGiay().getGianhap());
                    newHoaDonChiTiet.setDon_gia(giayChiTiet.getGiay().getGia_sau_khuyen_mai());
                    newHoaDonChiTiet.setTrangthai(1);
                    hoaDonChiTietRepo.createHDCT(newHoaDonChiTiet);
                }
                hoaDonChiTietRepo.createHoaDonChiTietList(hoaDonChiTietList);
                List<HoaDonChiTiet> hoaDonChiTietList2 = hoaDonChiTietRepo.getListHDCTbyMaHD(hoaDon.getMa());
                BigDecimal tongTien = tinhTongTienHoaDon(hoaDonChiTietList2);
                hoaDon.setTong_tien(tongTien);
                hoaDonRepo.createHoaDon(hoaDon);
            } else {
                redirectAttributes.addAttribute("maHD", maHD);
                return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
            }
        }

        redirectAttributes.addAttribute("maHD", maHD);
        return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
    }


    @PostMapping("/add/so-luong-hoa-don")
    public String addsoluonghd(@RequestParam("maHD") String maHD,
                               @RequestParam("idctsp") UUID idctsp,
                               RedirectAttributes redirectAttributes) {
        HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(maHD);
        GiayChiTiet giayChiTiet = giayChiTietRepo.getGiayChiTietById(idctsp);
        Integer soLuongTon = giayChiTiet.getSo_luong_ton(); // Số lượng tồn của sản phẩm
        if (giayChiTiet.getSo_luong_ton() <= soLuongTon) {
            List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepo.getListHDCTbyMaHD(hoaDon.getMa());
            for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                if (hdct.getGiayChiTiet().getId().equals(idctsp)) {
                    hdct.setSo_luong(hdct.getSo_luong() + 1);
                    hdct.setDon_gia(giayChiTiet.getGiay().getGia_sau_khuyen_mai());
                    giayChiTiet.setSo_luong_ton(soLuongTon - 1);
                    giayChiTietDAO.save(giayChiTiet);
                    break;
                }
            }

            hoaDonChiTietRepo.createHoaDonChiTietList(hoaDonChiTietList);
            List<HoaDonChiTiet> hoaDonChiTietList2 = hoaDonChiTietRepo.getListHDCTbyMaHD(hoaDon.getMa());
            BigDecimal tongTien = tinhTongTienHoaDon(hoaDonChiTietList2);
            hoaDon.setTong_tien(tongTien);
            hoaDonRepo.createHoaDon(hoaDon);
        } else {
            redirectAttributes.addAttribute("maHD", maHD);
            return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
            // Số lượng nhập vào lớn hơn số lượng tồn, xử lý tùy ý, ví dụ, thông báo lỗi hoặc chuyển hướng đến trang khác.
        }
        redirectAttributes.addAttribute("maHD", maHD);
        return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
    }


    @PostMapping("/giam/so-luong-hoa-don")
    public String giamsoluong(@RequestParam("maHD") String maHD,
                              @RequestParam("idctsp") UUID idctsp,
                              RedirectAttributes redirectAttributes) {
        HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(maHD);
        GiayChiTiet giayChiTiet = giayChiTietRepo.getGiayChiTietById(idctsp);
        Integer soLuongTon = giayChiTiet.getSo_luong_ton(); // Số lượng tồn của sản phẩm

        if (giayChiTiet.getSo_luong_ton() <= soLuongTon) {
            List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepo.getListHDCTbyMaHD(hoaDon.getMa());
            for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                if (hdct.getGiayChiTiet().getId().equals(idctsp)) {
                    hdct.setSo_luong(hdct.getSo_luong() - 1);
                    hdct.setDon_gia(giayChiTiet.getGiay().getGia_sau_khuyen_mai());

                    // Cập nhật số lượng tồn của sản phẩm
                    giayChiTiet.setSo_luong_ton(soLuongTon + 1);
                    giayChiTietDAO.save(giayChiTiet);
                    break;
                }
            }

            hoaDonChiTietRepo.createHoaDonChiTietList(hoaDonChiTietList);
            List<HoaDonChiTiet> hoaDonChiTietList2 = hoaDonChiTietRepo.getListHDCTbyMaHD(hoaDon.getMa());
            BigDecimal tongTien = tinhTongTienHoaDon(hoaDonChiTietList2);
            hoaDon.setTong_tien(tongTien);
            hoaDonRepo.createHoaDon(hoaDon);
        } else {
            redirectAttributes.addAttribute("maHD", maHD);
            return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
            // Số lượng nhập vào lớn hơn số lượng tồn, xử lý tùy ý, ví dụ, thông báo lỗi hoặc chuyển hướng đến trang khác.
        }
        redirectAttributes.addAttribute("maHD", maHD);
        return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
    }


    private BigDecimal tinhTongTienHoaDon(List<HoaDonChiTiet> hoaDonChiTietList) {
        BigDecimal tongTien = BigDecimal.ZERO; // Khởi tạo tổng tiền ban đầu là 0
        for (HoaDonChiTiet hdct : hoaDonChiTietList) {
            if (hdct.getTrangthai() == 1) { // Chỉ tính tổng tiền cho các sản phẩm có trạng thái là 1 (đã được chọn)
                BigDecimal donGia = hdct.getDon_gia();
                int soLuong = hdct.getSo_luong();
                BigDecimal giaTienSanPham = donGia.multiply(BigDecimal.valueOf(soLuong));
                tongTien = tongTien.add(giaTienSanPham);
            }
        }
        System.out.println("Tong tien" + tongTien);
        if (hoaDonChiTietList != null) {
            tongTien = tongTien.subtract(hoaDonChiTietList.get(0).getHoaDon().getSo_tien_giam().add(hoaDonChiTietList.get(0).getHoaDon().getSo_tien_quy_doi())).add(hoaDonChiTietList.get(0).getHoaDon().getPhi_ship());
        }
        return tongTien;
    }

    @RequestMapping("/admin/ban-hang-tai-quay/tao-don-hang/delete/{mahd}/{idctsp}/{soLuong}")
    public String deleteHoaDonChiTietByGCT(@PathVariable("mahd") String mahd,
                                           @PathVariable("idctsp") UUID idctsp,
                                           @PathVariable("soLuong") int soLuong,
                                           RedirectAttributes redirectAttributes) {

        HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(mahd);

        // Lấy tổng tiền hiện tại của hóa đơn
        BigDecimal tongTienHienTai = hoaDon.getTong_tien();

        // Lấy giá trị của sản phẩm đã bị xóa
        GiayChiTiet giayChiTiet = giayChiTietRepo.getGiayChiTietById(idctsp);
        BigDecimal giaTienSanPhamDaXoa = giayChiTiet.getGiay().getGia_sau_khuyen_mai();

        // Trả lại số lượng và giá trị sản phẩm đã xóa
        giayChiTiet.setSo_luong_ton(giayChiTiet.getSo_luong_ton() + soLuong);

        // Trừ giá trị sản phẩm đã bị xóa (nhân với số lượng đã xóa)
        BigDecimal giaTienDaXoa = giaTienSanPhamDaXoa.multiply(BigDecimal.valueOf(soLuong));
        tongTienHienTai = tongTienHienTai.subtract(giaTienDaXoa);

        // Cập nhật giayChiTiet trong kho lưu trữ sau khi đã thay đổi số lượng tồn
        giayChiTietDAO.save(giayChiTiet);

        // Xóa chi tiết hóa đơn
        hoaDonChiTietRepo.deleteHDCT(hoaDon.getId(), idctsp);

        // Cập nhật tổng tiền cho hóa đơn
        hoaDon.setTong_tien(tongTienHienTai);
        hoaDonRepo.createHoaDon(hoaDon);

        redirectAttributes.addAttribute("maHD", hoaDon.getMa());
        return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
    }


    @PostMapping("/admin/ban-hang-tai-quay/tao-don-hang/add-khach-hang")
    public String themKhachHangVaoHoaDon(@RequestParam("maHD") String maHD,
                                         @RequestParam("maKH") String maKH,
                                         RedirectAttributes redirectAttributes,
                                         Model model) {
        HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(maHD);
        KhachHang khachHang = khachHangRepo.getBykhachhangma(maKH);
        hoaDon.setKhachHang(khachHang);
        DiaChi diaChi = diachiDao.getDiaChiByKhachHangMaAndTrangthai(maKH);
        if(diaChi != null)
        {
            String diaChiGop = diaChi.getTendiachi() + ", " + diaChi.getXa() + ", " + diaChi.getHuyen() + ", " + diaChi.getThanhpho();
            hoaDon.setDia_chi(diaChiGop);
            hoaDon.setTen_nguoi_nhan(diaChi.getTen_nguoi_nhan());
            hoaDon.setSdt_nguoi_nhan(diaChi.getSdt_nguoi_nhan());
            GiaoHangNhanh giaoHangNhanh = new GiaoHangNhanh();
            giaoHangNhanh.setTo_district_name(diaChi.getHuyen()); //huyện
            giaoHangNhanh.setTo_province_name(diaChi.getThanhpho()); //thành phố
            giaoHangNhanh.setTo_ward_name(diaChi.getXa()); //xã
            String phiShip = callAPIGHN.getAPIGHN(giaoHangNhanh);
            hoaDon.setPhi_ship(new BigDecimal(phiShip));
            hoaDonRepo.createHoaDon(hoaDon);
        }
        else
        {
            String diaChiNull = "";
            hoaDon.setDia_chi(diaChiNull);
        }
        hoaDonRepo.createHoaDon(hoaDon);
        redirectAttributes.addAttribute("maHD", maHD);
        return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
    }

    @RequestMapping("/admin/ban-hang-tai-quay/xoa-khach-hang/{maHD}")
    public String deleteKhachHang(@PathVariable("maHD") String maHD,
                                  RedirectAttributes redirectAttributes) {

        HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(maHD);
        hoaDon.setKhachHang(null);
        hoaDon.setTen_nguoi_nhan(null);
        hoaDon.setSdt_nguoi_nhan(null);
        hoaDon.setDia_chi(null);
        hoaDonRepo.createHoaDon(hoaDon);

        redirectAttributes.addAttribute("maHD", maHD);
        return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
    }

    @PostMapping("/admin/ban-hang-tai-quay/tao-don-hang/add-dia-chi")
    public String themDiaChiKhachHangVaoHoaDon(@RequestParam("maHD") String maHD,
                                               @RequestParam("maDC") String maDC,
                                               RedirectAttributes redirectAttributes) {

        if (maDC != null || maHD != null) {
            HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(maHD);
            DiaChi diaChi = diachiDao.getDiachiByma(maDC);
            GiaoHangNhanh giaoHangNhanh = new GiaoHangNhanh();
            giaoHangNhanh.setTo_district_name(diaChi.getHuyen()); //huyện
            giaoHangNhanh.setTo_province_name(diaChi.getThanhpho()); //thành phố
            giaoHangNhanh.setTo_ward_name(diaChi.getXa()); //xã
            String phiShip = callAPIGHN.getAPIGHN(giaoHangNhanh);
            String diaChiGop = diaChi.getTendiachi() + ", " + diaChi.getXa() + ", " + diaChi.getHuyen() + ", " + diaChi.getThanhpho();
            BigDecimal phiShipBig = BigDecimal.valueOf(Double.parseDouble(phiShip));
            if (phiShipBig.compareTo(hoaDon.getPhi_ship()) < 0) {
                hoaDon.setTong_tien(hoaDon.getTong_tien().subtract(hoaDon.getPhi_ship().subtract(phiShipBig)));
            } else {
                hoaDon.setTong_tien(hoaDon.getTong_tien().add(phiShipBig.subtract(hoaDon.getPhi_ship())));
            }
            hoaDon.setPhi_ship(phiShipBig);
            hoaDon.setDia_chi(diaChiGop);
            hoaDon.setTen_nguoi_nhan(diaChi.getTen_nguoi_nhan());
            hoaDon.setSdt_nguoi_nhan(diaChi.getSdt_nguoi_nhan());
            hoaDonRepo.createHoaDon(hoaDon);
        }

        redirectAttributes.addAttribute("maHD", maHD);
        return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
    }


    @PostMapping("/admin/ban-hang-tai-quay/tao-don-hang/add-dia-chi1")
    public String themDiaChiKhachHangVaoHoaDon1(@RequestParam("maHD") String maHD,
                                                @RequestParam("phuongThucMuaHang") Integer phuongThucMuaHang,
                                                @RequestParam("phuongThucThanhToan") Integer phuongThucThanhToan,
                                                @RequestParam("ten_nguoi_nhan") String tennguoinhan,
                                                @RequestParam("sdt_nguoi_nhan") String sdtnguoinhan,
                                                @RequestParam("dia_chi") String diaChi1,
                                                @RequestParam("ward") String xa1,
                                                @RequestParam("district") String huyen1,
                                                @RequestParam("province") String thanhPho1,
                                                @RequestParam("mota") String moTa,
                                                @RequestParam("tongTienSauGiam") String tongTienSauGiam,
                                                @RequestParam("phiShip") String phiShip,
                                                @RequestParam("soTienGiam") String soTienGiam,
                                                @RequestParam("sodiemsudung") Integer sodiemsudung,
                                                @RequestParam("diemHienco") Integer diemHienCo,
                                                @RequestParam("sotienquydoi") BigDecimal sotienquydoi,
                                                HttpServletRequest request,
                                                RedirectAttributes redirectAttributes) {
        if(sodiemsudung<5000 || sodiemsudung>diemHienCo){
            sodiemsudung=0;
        }
        System.out.println("in tien ra" + tongTienSauGiam + phiShip + soTienGiam);
        HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(maHD);
        String diaChiGop = diaChi1 + ", " + xa1 + ", " + huyen1 + ", " + thanhPho1;
        hoaDon.setDia_chi(diaChiGop);
        hoaDon.setHinh_thuc_thanh_toan(phuongThucThanhToan);
        hoaDon.setHinh_thuc_mua(phuongThucMuaHang);
        hoaDon.setTen_nguoi_nhan(tennguoinhan);
        hoaDon.setSdt_nguoi_nhan(sdtnguoinhan);
        hoaDon.setSo_tien_giam(BigDecimal.valueOf(Double.parseDouble(soTienGiam)));
        hoaDon.setTong_tien(BigDecimal.valueOf(Double.parseDouble(tongTienSauGiam)));
        hoaDon.setPhi_ship(BigDecimal.valueOf(Double.parseDouble(phiShip)));
        hoaDon.setSo_tien_quy_doi(sotienquydoi);
        hoaDon.setSo_diem_su_dung(sodiemsudung);
        hoaDon.setMo_ta(moTa);
        hoaDonRepo.createHoaDon(hoaDon);
        redirectAttributes.addAttribute("maHD", maHD);
        return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
    }


    @PostMapping("/admin/ban-hang-tai-quay/tao-don-hang/add-voucher")
    public String themVoucherVaoHoaDon(@RequestParam("maHD") String maHD,
                                       @RequestParam("maGGHD") String maGGHD,
                                       RedirectAttributes redirectAttributes) {
        HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(maHD);
        GiamGiaHoaDon giamGiaHoaDon = giamGiaHoaDonRepo.getGiamGiaHoaDonByMa(maGGHD);

        // Kiểm tra hoaDon và giamGiaHoaDon không phải là null và còn số lượng voucher
        if (hoaDon != null && giamGiaHoaDon != null && giamGiaHoaDon.getSo_luong() > 0) {
            // Lấy số tiền giảm tối đa từ voucher
            BigDecimal soTienGiamToiDa = giamGiaHoaDon.getSo_tien_giam_max();

            // Lấy phần trăm giảm từ voucher
            Integer phanTramGiam = giamGiaHoaDon.getPhan_tram_giam();

            // Tính giảm giá dựa trên phần trăm
            BigDecimal giamGiaPhanTram = hoaDon.getTong_tien().multiply(new BigDecimal(phanTramGiam)).divide(new BigDecimal(100));

            // So sánh giảm giá dựa trên phần trăm với số tiền giảm tối đa
            BigDecimal soTienGiam = giamGiaPhanTram.min(soTienGiamToiDa);

            // Áp dụng giảm giá cho tổng tiền của hóa đơn
            BigDecimal tongTienHoaDon = hoaDon.getTong_tien();
            BigDecimal tongTienSauGiam = tongTienHoaDon.subtract(soTienGiam);

            // Cập nhật tổng tiền của hóa đơn
            hoaDon.setSo_tien_giam(soTienGiam);
            hoaDon.setTong_tien(tongTienSauGiam);
            hoaDonRepo.createHoaDon(hoaDon);

            // Tạo chi tiết giảm giá trong hóa đơn
            GiamGiaChiTietHoaDon giamGiaChiTietHoaDon = new GiamGiaChiTietHoaDon();
            giamGiaChiTietHoaDon.setHd(hoaDon);
            giamGiaChiTietHoaDon.setGghd(giamGiaHoaDon);
            giamGiaChiTietHoaDon.setTrangthai(1);
            giamGiaChiTietHoaDonRepo.createGGCTHD2(giamGiaChiTietHoaDon);

            // Giảm số lượng voucher còn lại
            giamGiaHoaDon.setSo_luong(giamGiaHoaDon.getSo_luong() - 1);
            giamGiaHoaDonRepo.createGGHD(giamGiaHoaDon);

            redirectAttributes.addAttribute("maHD", maHD);
            return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
        }

        redirectAttributes.addAttribute("maHD", maHD);
        return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
    }


    @RequestMapping("/admin/ban-hang-tai-quay/tao-don-hang/delete")
    public String deleteGGCTHD(@RequestParam("maHD") String maHD,
                               @RequestParam("maGGHD") String maGGHD,
                               RedirectAttributes redirectAttributes) {
        HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(maHD);
        GiamGiaHoaDon giamGiaHoaDon = giamGiaHoaDonRepo.getGiamGiaHoaDonByMa(maGGHD);

        // Xóa chi tiết giảm giá
        giamGiaChiTietHoaDonRepo.deleteGGCTHD(hoaDon.getId(), giamGiaHoaDon.getId());

        // Tính lại tổng tiền của hóa đơn từ đầu
        BigDecimal newTotal = calculateTotal(hoaDon);

        hoaDon.setTong_tien(newTotal);
        hoaDon.setSo_tien_giam(BigDecimal.ZERO);
        // Tăng số lượng voucher còn lại
        giamGiaHoaDon.setSo_luong(giamGiaHoaDon.getSo_luong() + 1);
        giamGiaHoaDonRepo.createGGHD(giamGiaHoaDon);

        // Cập nhật hóa đơn trong repository
        hoaDonRepo.createHoaDon(hoaDon);
        redirectAttributes.addAttribute("maHD", maHD);
        return "redirect:/admin/ban-hang-tai-quay/view-cart/{maHD}";
    }

    private BigDecimal calculateTotal(HoaDon hoaDon) {
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepo.getListHDCTbyMaHD(hoaDon.getMa());
        BigDecimal total = BigDecimal.ZERO;

        if (hoaDonChiTietList != null) {
            for (HoaDonChiTiet chiTiet : hoaDonChiTietList) {
                Integer soLuong = chiTiet.getSo_luong();
                BigDecimal gia = chiTiet.getDon_gia();

                if (soLuong != null) {
                    BigDecimal subtotal = BigDecimal.valueOf(soLuong).multiply(gia);
                    total = total.add(subtotal);
                }
            }
        }

        return total;
    }

    @PostMapping("/admin/ban-hang-tai-quay/xac-nhan-don-hang")
    public String xacNhan(HttpServletRequest request,
                          HttpServletResponse resp,
                          @RequestParam("maHD") String maHD,
                          RedirectAttributes redirectAttributes) throws IOException {
        HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(maHD);
        if (hoaDon.getHinh_thuc_thanh_toan() == 0 && hoaDon.getHinh_thuc_mua() == 0) {
            LocalDate currentDate = LocalDate.now();
            hoaDon.setNgay_thanh_toan(currentDate);
            hoaDon.setTrangthai(3);
            hoaDonRepo.createHoaDon(hoaDon);
            QuyDoiDiem quyDoiDiem = quyDoiDiemDAO.getQuyDoiDiemByTT1();
            if (hoaDon.getKhachHang()!=null){
                ViDiem viDiem = viDiemDAO.getViDiemByMaKH(hoaDon.getKhachHang().getMa());
                LichSuTieuDiem lichSuTieuDiem = new LichSuTieuDiem();
                lichSuTieuDiem.setTrangthai(1);
                lichSuTieuDiem.setVi_diem(viDiem);
                lichSuTieuDiem.setHoa_don(hoaDon);
                lichSuTieuDiem.setNgay_su_dung(LocalDate.now());
                lichSuTieuDiem.setQuy_doi_diem(quyDoiDiemDAO.getQuyDoiDiemByTT1());
                lichSuTieuDiem.setSo_diem_da_dung(hoaDon.getSo_diem_su_dung());
                Integer soDiemCong = hoaDon.getTong_tien().divide(quyDoiDiem.getSo_tien_tuong_ung()).multiply(new BigDecimal(quyDoiDiem.getSo_diem_tuong_ung())).intValue();
                lichSuTieuDiem.setSo_diem_cong(soDiemCong);
                lichSuTieuDiemDAO.save(lichSuTieuDiem);
                viDiem.setSo_diem_da_cong(viDiem.getSo_diem_da_cong()+soDiemCong);
                viDiem.setSo_diem_da_dung(viDiem.getSo_diem_da_dung()+hoaDon.getSo_diem_su_dung());
                viDiem.setTong_diem(viDiem.getSo_diem_da_cong()-viDiem.getSo_diem_da_dung());
                viDiemDAO.save(viDiem);
                return "redirect:/admin/ban-hang";

            }
        } else if (hoaDon.getHinh_thuc_thanh_toan() == 1 && hoaDon.getHinh_thuc_mua() == 0){
            BigDecimal newTotal = calculateTotal(hoaDon);
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";
            Integer tienTH1 = newTotal.intValue();
            long amount = tienTH1 * 100;
            String bankCode = "NCB";

            String vnp_TxnRef = Config.getRandomNumber(8);
            String vnp_IpAddr = "127.0.0.1";

            String vnp_TmnCode = Config.vnp_TmnCode;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");

            vnp_Params.put("vnp_BankCode", bankCode);
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
            vnp_Params.put("vnp_OrderType", orderType);

//
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", "http://localhost:8080/admin/ban-hang");
//            vnp_Params.put("vnp_ReturnUrl", "http://localhost:8080/hoadon/"+StringListGCT);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
            resp.sendRedirect(paymentUrl);
            LocalDate currentDate = LocalDate.now();
            hoaDon.setNgay_thanh_toan(currentDate);
            hoaDon.setTrangthai(3);
            hoaDonRepo.createHoaDon(hoaDon);
            QuyDoiDiem quyDoiDiem = quyDoiDiemDAO.getQuyDoiDiemByTT1();
            if (hoaDon.getKhachHang()!=null){
                ViDiem viDiem = viDiemDAO.getViDiemByMaKH(hoaDon.getKhachHang().getMa());
                LichSuTieuDiem lichSuTieuDiem = new LichSuTieuDiem();
                lichSuTieuDiem.setTrangthai(1);
                lichSuTieuDiem.setVi_diem(viDiem);
                lichSuTieuDiem.setHoa_don(hoaDon);
                lichSuTieuDiem.setNgay_su_dung(LocalDate.now());
                lichSuTieuDiem.setQuy_doi_diem(quyDoiDiemDAO.getQuyDoiDiemByTT1());
                lichSuTieuDiem.setSo_diem_da_dung(hoaDon.getSo_diem_su_dung());
                Integer soDiemCong = hoaDon.getTong_tien().divide(quyDoiDiem.getSo_tien_tuong_ung()).multiply(new BigDecimal(quyDoiDiem.getSo_diem_tuong_ung())).intValue();
                lichSuTieuDiem.setSo_diem_cong(soDiemCong);
                lichSuTieuDiemDAO.save(lichSuTieuDiem);
                viDiem.setSo_diem_da_cong(viDiem.getSo_diem_da_cong()+soDiemCong);
                viDiem.setSo_diem_da_dung(viDiem.getSo_diem_da_dung()+hoaDon.getSo_diem_su_dung());
                viDiem.setTong_diem(viDiem.getSo_diem_da_cong()-viDiem.getSo_diem_da_dung());
                viDiemDAO.save(viDiem);
            }

        } else if(hoaDon.getHinh_thuc_thanh_toan() == 0 && hoaDon.getHinh_thuc_mua() == 1){
            LocalDate currentDate = LocalDate.now();
            hoaDon.setNgay_thanh_toan(currentDate);
            hoaDon.setTrangthai(5);
            hoaDonRepo.createHoaDon(hoaDon);
            QuyDoiDiem quyDoiDiem = quyDoiDiemDAO.getQuyDoiDiemByTT1();
            if (hoaDon.getKhachHang()!=null){
                ViDiem viDiem = viDiemDAO.getViDiemByMaKH(hoaDon.getKhachHang().getMa());
                LichSuTieuDiem lichSuTieuDiem = new LichSuTieuDiem();
                lichSuTieuDiem.setTrangthai(1);
                lichSuTieuDiem.setVi_diem(viDiem);
                lichSuTieuDiem.setHoa_don(hoaDon);
                lichSuTieuDiem.setNgay_su_dung(LocalDate.now());
                lichSuTieuDiem.setQuy_doi_diem(quyDoiDiemDAO.getQuyDoiDiemByTT1());
                lichSuTieuDiem.setSo_diem_da_dung(hoaDon.getSo_diem_su_dung());
                Integer soDiemCong = hoaDon.getTong_tien().divide(quyDoiDiem.getSo_tien_tuong_ung()).multiply(new BigDecimal(quyDoiDiem.getSo_diem_tuong_ung())).intValue();
                lichSuTieuDiem.setSo_diem_cong(soDiemCong);
                lichSuTieuDiemDAO.save(lichSuTieuDiem);
//                viDiem.setSo_diem_da_cong(viDiem.getSo_diem_da_cong()+soDiemCong);
                viDiem.setSo_diem_da_dung(viDiem.getSo_diem_da_dung()+hoaDon.getSo_diem_su_dung());
                viDiem.setTong_diem(viDiem.getSo_diem_da_cong()-viDiem.getSo_diem_da_dung());
                viDiemDAO.save(viDiem);
            }
            return "redirect:/admin/ban-hang";

        }else if(hoaDon.getHinh_thuc_thanh_toan() == 1 && hoaDon.getHinh_thuc_mua() == 1){
            LocalDate currentDate = LocalDate.now();
            hoaDon.setNgay_thanh_toan(currentDate);
            hoaDon.setTrangthai(5);
            hoaDonRepo.createHoaDon(hoaDon);
            QuyDoiDiem quyDoiDiem = quyDoiDiemDAO.getQuyDoiDiemByTT1();
            if (hoaDon.getKhachHang()!=null){
                ViDiem viDiem = viDiemDAO.getViDiemByMaKH(hoaDon.getKhachHang().getMa());
                LichSuTieuDiem lichSuTieuDiem = new LichSuTieuDiem();
                lichSuTieuDiem.setTrangthai(1);
                lichSuTieuDiem.setVi_diem(viDiem);
                lichSuTieuDiem.setHoa_don(hoaDon);
                lichSuTieuDiem.setNgay_su_dung(LocalDate.now());
                lichSuTieuDiem.setQuy_doi_diem(quyDoiDiemDAO.getQuyDoiDiemByTT1());
                lichSuTieuDiem.setSo_diem_da_dung(hoaDon.getSo_diem_su_dung());
                Integer soDiemCong = hoaDon.getTong_tien().divide(quyDoiDiem.getSo_tien_tuong_ung()).multiply(new BigDecimal(quyDoiDiem.getSo_diem_tuong_ung())).intValue();
                lichSuTieuDiem.setSo_diem_cong(soDiemCong);
                lichSuTieuDiemDAO.save(lichSuTieuDiem);
//                viDiem.setSo_diem_da_cong(viDiem.getSo_diem_da_cong()+soDiemCong);
                viDiem.setSo_diem_da_dung(viDiem.getSo_diem_da_dung()+hoaDon.getSo_diem_su_dung());
                viDiem.setTong_diem(viDiem.getSo_diem_da_cong()-viDiem.getSo_diem_da_dung());
                viDiemDAO.save(viDiem);
            }
            return "redirect:/admin/ban-hang";

        }
        return "redirect:/admin/ban-hang";
    }

    private Authentication authentication;
    @ModelAttribute("nhanVienLogin")
    public NhanVien nhanVienLogin() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        NhanVien nv=nhanVienDAO.getNVByEmail(authentication.getName());
        return nv;
    }
}
