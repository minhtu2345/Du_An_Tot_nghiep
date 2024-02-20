package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.GiamGiaChiTietHoaDonRepo;
import com.example.demo.repository.GiamGiaHoaDonRepo;
import com.example.demo.repository.HoaDonRepo;
import com.example.demo.repository.NhanVienDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class GiamGiaHoaDonController {

    @Autowired
    GiamGiaHoaDonRepo giamGiaHoaDonRepo;

    @Autowired
    GiamGiaChiTietHoaDonRepo giamGiaChiTietHoaDonRepo;

    @Autowired
    HoaDonRepo hoaDonRepo;

    //index giảm giá hóa đơn : table page
    @RequestMapping("/admin/giamgiahoadon")
    public String giamGiaHoaDon(@ModelAttribute("giamgiahoadon") GiamGiaHoaDon giamGiaHoaDon,
                                @RequestParam("page") Optional<Integer> page,
                                Model model) {
        PageDTO<GiamGiaHoaDon> pageGGHD = giamGiaHoaDonRepo.getPageGGHD(page.orElse(0));
        model.addAttribute("listPGiamGiaHoaDon", pageGGHD);
        return "giamgiahoadon/giam_gia_hoa_don";
    }

    // create Giảm Giá Hóa Đơn
    @PostMapping("/admin/giamgiahoadon/create")
    public String createGiamGiaHoaDon(@Valid @ModelAttribute("giamgiahoadon") GiamGiaHoaDon giamGiaHoaDon,
                                      BindingResult result,
                                      @RequestParam("page") Optional<Integer> page,
                                      Model model) {

        GiamGiaHoaDon checkma = giamGiaHoaDonRepo.getGiamGiaHoaDonByMa(giamGiaHoaDon.getMa());
        if (result.hasErrors()) {
            model.addAttribute("message", "Không được để trống thông tin");
            return "redirect:/admin/giamgiahoadon";
        }
        else if (checkma != null) {
            result.rejectValue("ma", "error.giamGiaHoaDon", "Mã đã tồn tại");
            return "redirect:/admin/giamgiahoadon";
        }
        giamGiaHoaDonRepo.createGGHD(giamGiaHoaDon);
        return "redirect:/admin/giamgiahoadon";
    }

    @GetMapping("/admin/giamgiahoadon/checkMaTrung")
    @ResponseBody
    public boolean checkMaTrung(@RequestParam String ma) {
        GiamGiaHoaDon existingGiamGia = giamGiaHoaDonRepo.getGiamGiaHoaDonByMa(ma);
        return existingGiamGia != null;
    }

    // view update Giảm Giá Hóa Đơn
    @RequestMapping("/admin/giamgiahoadon/view-update/{ma}")
    public String viewupdateGiamGiaHoaDon(@PathVariable("ma") String ma, @ModelAttribute("giamgiahoadon") GiamGiaHoaDon giamGiaHoaDon, Model model) {
        model.addAttribute("giamgiahoadon", giamGiaHoaDonRepo.getGiamGiaHoaDonByMa(ma));
        return "giamgiahoadon/update_giam_gia_hoa_don";
    }

    // update Giảm Giá Hóa Đơn
    @PostMapping("/admin/giamgiahoadon/update")
    public String updateGiamGiaHoaDon(@Valid @ModelAttribute("giamgiahoadon") GiamGiaHoaDon giamGiaHoaDon,
                                      BindingResult result,
                                      Model model) {
        if(result.hasErrors())
        {
            model.addAttribute("message", "Không được để trống thông tin");
            return "redirect:/admin/giamgiahoadon";
        }
        giamGiaHoaDonRepo.createGGHD(giamGiaHoaDon);
        return "redirect:/admin/giamgiahoadon";
    }

    @PostMapping("/admin/giamgiahoadon/set-ngung-hoat-dong")
    public String ngungHoatDongGiamGiaHoaDon(@RequestParam("idGGHD") UUID idGGHD,
            Model model) {
        GiamGiaHoaDon giamGiaHoaDon = giamGiaHoaDonRepo.getGiamGiaHoaDonById(idGGHD);
        giamGiaHoaDon.setTrangthai(0);
        giamGiaHoaDonRepo.createGGHD(giamGiaHoaDon);
        return "redirect:/admin/giamgiahoadon";
    }

    // view detail Giảm Giá Hóa Đơn
    @RequestMapping("/admin/giamgiahoadon/detail/{ma}")
    public String detailGGHD(@PathVariable("ma") String ma,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam("page") Optional<Integer> page,
                             Model model) {
        // tìm giảm giá hóa đơn tương ứng theo mã
        model.addAttribute("giamgiahoadon", giamGiaHoaDonRepo.getGiamGiaHoaDonByMa(ma));

        // Danh sách hóa đơn chưa áp mã
        PageDTO<HoaDon> hoaDonPageDTOchuaGG = hoaDonRepo.getAllHDchuaGGPage(page.orElse(0));
        model.addAttribute("listHoaDon", hoaDonPageDTOchuaGG);
        // Tìm danh sách hóa đơn đã được áp mã
        PageDTO<HoaDon> hoaDonPageDTOdaGG = giamGiaHoaDonRepo.getHoaDonByChuongTrinhGiamGiaPage(ma, page.orElse(0));
        model.addAttribute("hoaDonListdaGG", hoaDonPageDTOdaGG); // Page hóa đơn đã giảm giá


        return "giamgiahoadon/detail_giam_gia_hoa_don";
    }

    // Xóa Giảm Giá Hóa Đơn
    @RequestMapping("/admin/giamgiahoadon/delete/{id}")
    public String deleteGiamGiaHoaDon(@PathVariable("id") UUID id) {
        giamGiaHoaDonRepo.deleteGGHD(id);
        return "redirect:/admin/giamgiahoadon";
    }

    // Lọc Giảm Giá Hóa Đơn theo ngày
    @RequestMapping("/admin/giamgiahoadon/loc-theo-ngay")
    public String locGiamGiaHoaDonTheoNgay(@RequestParam(value = "ngay_bat_dau", required = false) Date startDate,
                                           @RequestParam(value = "ngay_ket_thuc", required = false) Date endDate,
                                           @RequestParam("page") Optional<Integer> page,
                                           @ModelAttribute("giamgiahoadon") GiamGiaHoaDon giamGiaHoaDon,
                                           Model model) {

        PageDTO<GiamGiaHoaDon> pageResult = giamGiaHoaDonRepo.getPageGGHDByDateRange(startDate, endDate, page.orElse(0));
        model.addAttribute("listPGiamGiaHoaDon", pageResult);
        model.addAttribute("ngayBatDauDetail", startDate);
        model.addAttribute("ngayKetThucDetail", endDate);
        return "giamgiahoadon/giam_gia_hoa_don";
    }

//    @RequestMapping("/admin/giam-gia-hoa-don/tim-kiem-hoa-don-chua-ap-ma")
//    public String timHoaDonChuaApMa(@RequestParam("keyword") String keyword,
//                                    @RequestParam("timTheo") String timTheo,
//                                    @RequestParam("page") Optional<Integer> page,
//                                    @RequestParam("maGGHD") String maGGHD,
//                                    Model model) {
//
//        PageDTO<HoaDon> hoaDonPageDTOchuaGGFind = hoaDonRepo.getPageHDByTrangThai1chuaApMa(keyword,timTheo,page.orElse(0));
//        model.addAttribute("listHoaDon", hoaDonPageDTOchuaGGFind);
//        return "redirect:/admin/giamgiahoadon/detail/" + maGGHD;
//    }

    @RequestMapping("/admin/giamgiahoadon/loc-theo-ten")
    public String locGiamGiaHoaDonTheoTen(@RequestParam(value = "ten", required = false) String ten,
                                          @RequestParam("page") Optional<Integer> page,
                                          @ModelAttribute("giamgiahoadon") GiamGiaHoaDon giamGiaHoaDon,
                                          Model model) {

        PageDTO<GiamGiaHoaDon> pageResult = giamGiaHoaDonRepo.getPageGGHDByTen(ten, page.orElse(0));
        model.addAttribute("listPGiamGiaHoaDon", pageResult);
        model.addAttribute("loctheotenDetail",ten);
        return "giamgiahoadon/giam_gia_hoa_don";
    }

    // Lọc GGHD theo trạng thái
    @RequestMapping("/admin/giamgiahoadon/loc-theo-trangthai")
    public String locGiamGiaHoaDonTheoTrangThai(@RequestParam(value = "trangthai", required = false) Integer trangthai,
                                                @RequestParam("page") Optional<Integer> page,
                                                @ModelAttribute("giamgiahoadon") GiamGiaHoaDon giamGiaHoaDon,
                                                Model model) {
        PageDTO<GiamGiaHoaDon> pageResult = giamGiaHoaDonRepo.getPageGGHDByTrangThai(trangthai, page.orElse(0));
        model.addAttribute("listPGiamGiaHoaDon", pageResult);
        model.addAttribute("trangthaidetail", trangthai);
        return "giamgiahoadon/giam_gia_hoa_don";
    }

    // Áp mã chương trình giảm giá cho Hóa đơn
    @PostMapping("/admin/giamgiachitiethoadon/createGGCTHD")
    public String createGiamGiaChiTietHoaDon(@RequestParam("HoaDonMa") List<String> HDmas,
                                             @RequestParam("GiamGiaHoaDonMa") String GGHDma) {
        for (String hdma : HDmas) {
            HoaDon hoaDon = hoaDonRepo.getHoaDonByMa(hdma); // tìm hóa đơn theo mã
            GiamGiaHoaDon giamGiaHoaDon = giamGiaHoaDonRepo.getGiamGiaHoaDonByMa(GGHDma); // tìm giảm giá hóa đơn theo mã
            if (giamGiaHoaDon != null && hoaDon != null) {
                GiamGiaChiTietHoaDon giamGiaChiTietHoaDon = new GiamGiaChiTietHoaDon(); // tạo mới giảm giá chi tiết hóa đơn
                giamGiaChiTietHoaDon.setHd(hoaDon); // set hóa đơn vào giảm giá chi tiết hóa đơn
                giamGiaChiTietHoaDon.setGghd(giamGiaHoaDon); // set giảm giá hóa đơn vào giảm giá chi tiết hóa đơn
                BigDecimal tongTienHoaDon = hoaDon.getTong_tien();  // Lấy tổng tiền của hóa đơn
                BigDecimal phiShip = hoaDon.getPhi_ship(); // lấy phí ship
                BigDecimal tongTienHoaDonChuaPhiShip = tongTienHoaDon.subtract(phiShip); // lấy tổng tiền chưa có phí ship cộng vào

                int phanTramGiam = giamGiaHoaDon.getPhan_tram_giam();// Lấy phần trăm giảm
                BigDecimal soTienGiamMax = giamGiaHoaDon.getSo_tien_giam_max(); // Lấy số tiền giảm tối đa
                BigDecimal soTienGiam = tongTienHoaDonChuaPhiShip.multiply(new BigDecimal(phanTramGiam)).divide(new BigDecimal(100)); // Tính số tiền giảm dựa trên phần trăm

                // Nếu số tiền giảm vượt quá số tiền giảm tối đa, sử dụng số tiền giảm tối đa
                if (soTienGiam.compareTo(soTienGiamMax) > 0) {
                    soTienGiam = soTienGiamMax;
                }

                tongTienHoaDonChuaPhiShip = tongTienHoaDonChuaPhiShip.subtract(soTienGiam); // Trừ số tiền giảm khỏi tổng tiền hóa đơn
                BigDecimal tongTienSauCung = tongTienHoaDonChuaPhiShip.add(phiShip); // cộng lại phí ship
                hoaDon.setTong_tien(tongTienSauCung); // Cập nhật tổng tiền của hóa đơn
                hoaDon.setSo_tien_giam(soTienGiam); // cập nhật số tiền giảm của hóa đơn
                giamGiaHoaDon.setSo_luong(giamGiaHoaDon.getSo_luong() - 1); // Giảm số lượng giảm giá hóa đơn đi 1
                giamGiaChiTietHoaDon.setTrangthai(1);// Set trạng thái của chi tiết giảm giá trên hóa đơn
                giamGiaChiTietHoaDon.setTong_tien(tongTienSauCung); // set tổng tiền ban đầu vào giảm giá chi tiết hóa đơn
                giamGiaChiTietHoaDon.setSo_tien_da_giam(soTienGiam); // set số tiền giảm vào giảm giá chi tiết hóa đơn

                // Lưu cập nhật vào cơ sở dữ liệu
                giamGiaChiTietHoaDonRepo.createGGCTHD2(giamGiaChiTietHoaDon);
                giamGiaHoaDonRepo.createGGHD(giamGiaHoaDon);
                hoaDonRepo.createHoaDon(hoaDon);
            }
        }
        return "redirect:/admin/giamgiahoadon/detail/" + GGHDma;
    }


    // Hủy áp mã chương trình giảm giá cho hóa đơn
    @PostMapping("/admin/giamgiachitiethoadon/huy-ap-ma")
    public String deleteGGCTHD(@RequestParam("HoaDonID") List<UUID> idHoaDon,
                               @RequestParam("GiamGiaHoaDonId") UUID gghdid,
                               @RequestParam("GiamGiaHoaDonMa") String gghdma) {

        if (idHoaDon != null) {
            for (UUID hdid : idHoaDon) {
                GiamGiaChiTietHoaDon giamGiaChiTietHoaDon = giamGiaChiTietHoaDonRepo.getGiamGiaCTHoaDonByHDandGGHD(hdid, gghdid); // tìm giảm giá chi tiết hóa đơn theo 2 id phụ
                HoaDon hoaDon = hoaDonRepo.getHoaDonByID(hdid); // tìm hóa đơn theo id
                GiamGiaHoaDon giamGiaHoaDon = giamGiaHoaDonRepo.getGiamGiaHoaDonById(gghdid); // tìm giảm giá hóa đơn theo id
                BigDecimal soTienGiam = hoaDon.getSo_tien_giam();
                BigDecimal phiShip = hoaDon.getPhi_ship();
                BigDecimal tongTienDaGiamGiaVaPhiShip = giamGiaChiTietHoaDon.getTong_tien();
                BigDecimal tongTienCoPhiShip = tongTienDaGiamGiaVaPhiShip.add(soTienGiam);

                hoaDon.setTong_tien(tongTienCoPhiShip); // tính lại tổng tiền hóa đơn về ban đầu
                hoaDon.setSo_tien_giam(BigDecimal.ZERO); // set số tiền giảm về 0
                giamGiaHoaDon.setSo_luong(giamGiaHoaDon.getSo_luong() + 1); // tăng lại số lượng lên 1 đơn vị

                hoaDonRepo.createHoaDon(hoaDon); // cập nhật hóa đơn
                giamGiaHoaDonRepo.createGGHD(giamGiaHoaDon); // cập nhật giảm giá hóa đơn

                giamGiaChiTietHoaDonRepo.deleteGGCTHD(hdid, gghdid); // xóa giảm giá chi tiết hóa đơn
            }
        }
        return "redirect:/admin/giamgiahoadon/detail/" + gghdma;
    }

    @Autowired
    NhanVienDAO nhanVienDAO;
    private Authentication authentication;
    @ModelAttribute("nhanVienLogin")
    public NhanVien nhanVienLogin() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        NhanVien nv=nhanVienDAO.getNVByEmail(authentication.getName());
        return nv;
    }
}
