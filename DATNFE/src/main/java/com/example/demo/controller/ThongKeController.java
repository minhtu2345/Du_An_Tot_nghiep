package com.example.demo.controller;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.NhanVien;
import com.example.demo.repository.GiayDAO;
import com.example.demo.repository.HoaDonChiTietDAO;
import com.example.demo.repository.HoaDonDAO;
import com.example.demo.repository.NhanVienDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
public class ThongKeController {

    @Autowired
    HoaDonDAO hoaDonDAO;

    @Autowired
    HoaDonChiTietDAO hoaDonChiTietDAO;


    private void prepareModelData(Model model) {
        LocalDate ngayHienTai = LocalDate.now();
        BigDecimal tongTienTheoNgay = hoaDonDAO.tongTienTheoNgay(ngayHienTai);

        BigDecimal tongTien = (tongTienTheoNgay != null) ? tongTienTheoNgay : BigDecimal.ZERO;
        model.addAttribute("tongTien",tongTien);
        model.addAttribute("tongTienTrongNgay",hoaDonDAO.tongTienTrongNgay(ngayHienTai));
        model.addAttribute("tongsohoadondangchoxanhan", hoaDonDAO.tongsohoadondangchoxanhan());
        model.addAttribute("tongSoHoaDonDaHuy", hoaDonDAO.countByTrangthai(4));
        model.addAttribute("tongSoHoaDonDaHoanThanh", hoaDonDAO.countByTrangthai(5));
        model.addAttribute("tongLoiNhuan", tinhLoiNhuanTheoNgay(ngayHienTai));

        model.addAttribute("TongOn", hoaDonDAO.tongsomuaonline());
        model.addAttribute("Tongtructiep", hoaDonDAO.tongsomuatructiep());
        List<Object[]> tongtienthangtrongnam = hoaDonDAO.getHoaDonByTongTienTheoThangTrongNam();
        List<Object[]> tongtienloinhuanthangtrongnam = hoaDonDAO.getHoaDonByTongTienloinhuanTheoThangTrongNam();
        model.addAttribute("jsonData", tongtienthangtrongnam);
        model.addAttribute("jsonData1", tongtienloinhuanthangtrongnam);
    }

    @RequestMapping("/admin/thong-ke")
    public String index(Model model) {
        prepareModelData(model);
        return "thongke/thongkedoanhthu";
    }

    @RequestMapping("/admin/thong-ke/bieu-do-doanh-thu-hang-trong-nam")
    public String doanhthuhangthangtrongnam(@RequestParam("chon-nam") Integer thoiGian, Model model){
        prepareModelData(model);
        model.addAttribute("nam",thoiGian);
        List<Object[]> tongtienthangtrongnam = hoaDonDAO.getHoaDonByTongTienTheoThangTrongNam(thoiGian);
        List<Object[]> tongtienloinhuanthangtrongnam = hoaDonDAO.getHoaDonByTongTienloinhuanTheoThangTrongNam(thoiGian);
        model.addAttribute("jsonData", tongtienthangtrongnam);
        model.addAttribute("jsonData1", tongtienloinhuanthangtrongnam);
        return "thongke/thongkedoanhthu";
    }

    @RequestMapping("/admin/thong-ke/doanh-thu-ban-hang")
    public String thongKeDoanhThuBanHang(@RequestParam("thoiGian") String thoiGian, Model model) {
        prepareModelData(model);

        LocalDate ngayHienTai = LocalDate.now();
        BigDecimal tongTien = BigDecimal.ZERO;

        switch (thoiGian) {
            case "ngay":
                BigDecimal TongTienTheoNgay = hoaDonDAO.tongTienTheoNgay(ngayHienTai);
                tongTien = ( TongTienTheoNgay!= null) ? TongTienTheoNgay : BigDecimal.ZERO;
                model.addAttribute("ngayDaChonDoanhThuDetail", thoiGian);
                break;
            case "tuan":
                BigDecimal TongTienTheoTuaan = hoaDonDAO.tongTienTheoTuan(ngayHienTai);
                tongTien = (TongTienTheoTuaan != null) ? TongTienTheoTuaan : BigDecimal.ZERO;
                model.addAttribute("ngayDaChonDoanhThuDetail", thoiGian);
                break;
            case "thang":
                BigDecimal TongTienTheothang= hoaDonDAO.tongTienTheoThang(ngayHienTai);
                tongTien = TongTienTheothang != null ? TongTienTheothang : BigDecimal.ZERO;
                model.addAttribute("ngayDaChonDoanhThuDetail", thoiGian);
                break;
            case "nam":
                BigDecimal TongTienTheonam = hoaDonDAO.tongTienTheoNam(ngayHienTai);
                tongTien = TongTienTheonam != null ? TongTienTheonam : BigDecimal.ZERO;
                model.addAttribute("ngayDaChonDoanhThuDetail", thoiGian);
                break;
            default:
                break;
        }
        model.addAttribute("tongTien", tongTien);

        return "thongke/thongkedoanhthu";
    }


    @RequestMapping("/admin/thong-ke/loi-nhuan-ban-hang")
    public String thongKeLoiNhuanBanHang(@RequestParam("thoiGian") String thoiGian, Model model) {
        BigDecimal tongLoiNhuan = BigDecimal.ZERO;
        LocalDate ngayHienTai = LocalDate.now();

        switch (thoiGian) {
            case "ngay":
                tongLoiNhuan = tinhLoiNhuanTheoNgay(ngayHienTai);
                model.addAttribute("ngayDaChonLoiNhuanDetail", thoiGian);
                break;
            case "tuan":
                tongLoiNhuan = tinhLoiNhuanTheoTuan(ngayHienTai);
                model.addAttribute("ngayDaChonLoiNhuanDetail", thoiGian);
                break;
            case "thang":
                tongLoiNhuan = tinhLoiNhuanTheoThang(ngayHienTai);
                model.addAttribute("ngayDaChonLoiNhuanDetail", thoiGian);
                break;
            case "nam":
                tongLoiNhuan = tinhLoiNhuanTheoNam(ngayHienTai);
                model.addAttribute("ngayDaChonLoiNhuanDetail", thoiGian);
                break;
            default:
                break;
        }

        prepareModelData(model);
        model.addAttribute("tongLoiNhuan", tongLoiNhuan);
        return "thongke/thongkedoanhthu";
    }

    private BigDecimal tinhLoiNhuanTheoNgay(LocalDate ngay) {
        BigDecimal tongLoiNhuan = BigDecimal.ZERO;

        // Lấy danh sách hóa đơn cho ngày cụ thể từ cơ sở dữ liệu
        List<HoaDon> hoaDonList = hoaDonDAO.findHoaDonByNgay_thanh_toan(ngay);

        for (HoaDon hoaDon : hoaDonList) {
            Set<HoaDonChiTiet> chiTietList = hoaDon.getListHdct();

            for (HoaDonChiTiet chiTiet : chiTietList) {
                BigDecimal giaNhap = chiTiet.getGia_nhap();
                BigDecimal giaBan = chiTiet.getDon_gia();
                Integer soluong = chiTiet.getSo_luong();
                BigDecimal soLuong = BigDecimal.valueOf(soluong);

                // Tính lợi nhuận cho mỗi mục chi tiết hóa đơn và cộng vào tổng lợi nhuận
                BigDecimal loiNhuan = giaBan.subtract(giaNhap).multiply(soLuong);
                tongLoiNhuan = tongLoiNhuan.add(loiNhuan);
            }
        }

        return tongLoiNhuan;
    }

    private BigDecimal tinhLoiNhuanTheoTuan(LocalDate ngay) {
        BigDecimal tongLoiNhuan = BigDecimal.ZERO;
        LocalDate ngayBatDauTuan = ngay.with(DayOfWeek.MONDAY); // Lấy ngày đầu tuần
        LocalDate ngayKetThucTuan = ngayBatDauTuan.plusDays(6); // Lấy ngày cuối tuần

        // Lấy danh sách hóa đơn trong khoảng thời gian cụ thể từ cơ sở dữ liệu
        List<HoaDon> hoaDonList = hoaDonDAO.findHoaDonByNgay_thanh_toanBetween(ngayBatDauTuan, ngayKetThucTuan);

        // Tính lợi nhuận từ danh sách hóa đơn và chi tiết hóa đơn tương ứng
        for (HoaDon hoaDon : hoaDonList) {
            Set<HoaDonChiTiet> chiTietList = hoaDon.getListHdct();
            for (HoaDonChiTiet chiTiet : chiTietList) {
                BigDecimal giaNhap = chiTiet.getGia_nhap();
                BigDecimal giaBan = chiTiet.getDon_gia();
                Integer soluong = chiTiet.getSo_luong();
                BigDecimal soLuong = BigDecimal.valueOf(soluong);

                // Tính lợi nhuận cho mỗi mục chi tiết hóa đơn và cộng vào tổng lợi nhuận
                BigDecimal loiNhuan = giaBan.subtract(giaNhap).multiply(soLuong);
                tongLoiNhuan = tongLoiNhuan.add(loiNhuan);
            }
        }

        return tongLoiNhuan;
    }

    private BigDecimal tinhLoiNhuanTheoThang(LocalDate ngay) {
        BigDecimal tongLoiNhuan = BigDecimal.ZERO;
        LocalDate ngayBatDauThang = ngay.withDayOfMonth(1); // Lấy ngày đầu tháng
        LocalDate ngayKetThucThang = ngay.withDayOfMonth(ngay.lengthOfMonth()); // Lấy ngày cuối tháng

        // Lấy danh sách hóa đơn trong khoảng thời gian cụ thể từ cơ sở dữ liệu
        List<HoaDon> hoaDonList = hoaDonDAO.findHoaDonByNgay_thanh_toanBetween(ngayBatDauThang, ngayKetThucThang);

        // Tính lợi nhuận từ danh sách hóa đơn và chi tiết hóa đơn tương ứng
        for (HoaDon hoaDon : hoaDonList) {
            Set<HoaDonChiTiet> chiTietList = hoaDon.getListHdct();
            for (HoaDonChiTiet chiTiet : chiTietList) {
                BigDecimal giaNhap = chiTiet.getGia_nhap();
                BigDecimal giaBan = chiTiet.getDon_gia();
                Integer soluong = chiTiet.getSo_luong();
                BigDecimal soLuong = BigDecimal.valueOf(soluong);

                // Tính lợi nhuận cho mỗi mục chi tiết hóa đơn và cộng vào tổng lợi nhuận
                BigDecimal loiNhuan = giaBan.subtract(giaNhap).multiply(soLuong);
                tongLoiNhuan = tongLoiNhuan.add(loiNhuan);
            }
        }

        return tongLoiNhuan;
    }

    private BigDecimal tinhLoiNhuanTheoNam(LocalDate ngay) {
        BigDecimal tongLoiNhuan = BigDecimal.ZERO;
        int nam = ngay.getYear();

        // Lấy danh sách hóa đơn trong khoảng thời gian cụ thể từ cơ sở dữ liệu
        List<HoaDon> hoaDonList = hoaDonDAO.findByNgayThanhToanYear(nam);

        // Tính lợi nhuận từ danh sách hóa đơn và chi tiết hóa đơn tương ứng
        for (HoaDon hoaDon : hoaDonList) {
            Set<HoaDonChiTiet> chiTietList = hoaDon.getListHdct();
            for (HoaDonChiTiet chiTiet : chiTietList) {
                BigDecimal giaNhap = chiTiet.getGia_nhap();
                BigDecimal giaBan = chiTiet.getDon_gia();
                BigDecimal loiNhuan = giaBan.subtract(giaNhap);
                tongLoiNhuan = tongLoiNhuan.add(loiNhuan);
            }
        }

        return tongLoiNhuan;
    }


    public void index1(Model model) {
        List<Object[]> top10sanphambanchay = hoaDonDAO.top10SanPhamBanChaytrongnam();
        model.addAttribute("top10sanphambanchay",top10sanphambanchay);
        String thoiGian = "Năm";
        model.addAttribute("hienthithoigian", thoiGian);

        List<Object[]> top10khachhangmuanhieunhat = hoaDonDAO.top10khachhangmuanhieunhattrongnam();
        model.addAttribute("top10khachhangmuanhieunhat",top10khachhangmuanhieunhat);
        String thoiGian1 = "Năm";
        model.addAttribute("hienthithoigian1", thoiGian1);

        List<Object[]> top10nhanvienbannhieunhat = hoaDonDAO.top10nhanvienbannhieunhattrongnam();
        model.addAttribute("top10nhanvienbannhieunhat",top10nhanvienbannhieunhat);
        String thoiGian2 = "Năm";
        model.addAttribute("hienthithoigian2", thoiGian2);
    }

    @GetMapping("/admin/thong-ke-top-ban-chay")
    public String hienthi(Model model) {
        index1(model);
        return "thongke/thongketopbanchay";
    }

    @RequestMapping("/admin/chon-thoi-gian-thong-ke-top-ban-chay-san-pham")
    public String hienthibieudochonthoigiantop10sanpham(@RequestParam("thoiGian") String thoiGian, Model model){
        index1(model);
        String thoiGian1 = "Năm";
        model.addAttribute("hienthithoigian", thoiGian1);
        List<Object[]> bieudosanphambanchay = null;
        switch (thoiGian) {
            case "Năm":
                bieudosanphambanchay = hoaDonDAO.top10SanPhamBanChaytrongnam();
                model.addAttribute("ngayDaChonDetail", thoiGian);
                model.addAttribute("hienthithoigian", thoiGian);
                break;
            case "Tháng Trước":
                bieudosanphambanchay = hoaDonDAO.top10SanPhamBanChaythangtrc();
                model.addAttribute("ngayDaChonDetail", thoiGian);
                model.addAttribute("hienthithoigian", thoiGian);
                break;
            case "Tháng này":
                bieudosanphambanchay = hoaDonDAO.top10SanPhamBanChaytrongthangnay();
                model.addAttribute("ngayDaChonDetail", thoiGian);
                model.addAttribute("hienthithoigian", thoiGian);
                break;
            case "7 Ngày qua":
                bieudosanphambanchay = hoaDonDAO.top10SanPhamBanChaytrong7ngayqua();
                model.addAttribute("ngayDaChonDetail", thoiGian);
                model.addAttribute("hienthithoigian", thoiGian);
                break;
            case "Hôm Qua":
                bieudosanphambanchay = hoaDonDAO.top10SanPhamBanChaytronghomqua();
                model.addAttribute("ngayDaChonDetail", thoiGian);
                model.addAttribute("hienthithoigian", thoiGian);
                break;
            default:
                break;
        }

        model.addAttribute("top10sanphambanchay", bieudosanphambanchay);
        return "thongke/thongketopbanchay";

    }

    @RequestMapping("/admin/chon-thoi-gian-thong-ke-top-ban-chay-khach-hang")
    public String hienthibieudochonthoigiantop10khachhang(@RequestParam("thoiGian") String thoiGian, Model model){
        index1(model);
        String thoiGian1 = "Năm";
        model.addAttribute("hienthithoigian1", thoiGian1);
        List<Object[]> bieudosanphambanchay = null;
        switch (thoiGian) {
            case "Năm":
                bieudosanphambanchay = hoaDonDAO.top10khachhangmuanhieunhattrongnam();
                model.addAttribute("ngayDaChonDetailkhachhang", thoiGian);
                model.addAttribute("hienthithoigian1", thoiGian);
                break;
            case "Tháng Trước":
                bieudosanphambanchay = hoaDonDAO.top10khachhangmuanhieunhatthangtruoc();
                model.addAttribute("ngayDaChonDetailkhachhang", thoiGian);
                model.addAttribute("hienthithoigian1", thoiGian);
                break;
            case "Tháng này":
                bieudosanphambanchay = hoaDonDAO.top10khachhangmuanhieunhatthangnay();
                model.addAttribute("ngayDaChonDetailkhachhang", thoiGian);
                model.addAttribute("hienthithoigian1", thoiGian);
                break;
            case "7 Ngày qua":
                bieudosanphambanchay = hoaDonDAO.top10khachhangmuanhieunhat7ngayqua();
                model.addAttribute("ngayDaChonDetailkhachhang", thoiGian);
                model.addAttribute("hienthithoigian1", thoiGian);
                break;
            case "Hôm Qua":
                bieudosanphambanchay = hoaDonDAO.top10khachhangmuanhieunhathomqua();
                model.addAttribute("ngayDaChonDetailkhachhang", thoiGian);
                model.addAttribute("hienthithoigian1", thoiGian);
                break;
            default:
                break;
        }
        model.addAttribute("top10khachhangmuanhieunhat", bieudosanphambanchay);
        return "thongke/thongketopbanchay";

    }


    @RequestMapping("/admin/chon-thoi-gian-thong-ke-top-ban-chay-nhan-vien")
    public String hienthibieudochonthoigiantop10nhanvien(@RequestParam("thoiGian") String thoiGian, Model model){
        index1(model);
        String thoiGian2 = "Năm";
        model.addAttribute("hienthithoigian2", thoiGian2);
        List<Object[]> bieudosanphambanchay = null;
        switch (thoiGian) {
            case "Năm":
                bieudosanphambanchay = hoaDonDAO.top10nhanvienbannhieunhattrongnam();
                model.addAttribute("ngayDaChonDetailnhanvien", thoiGian);
                model.addAttribute("hienthithoigian2", thoiGian);
                break;
            case "Tháng Trước":
                bieudosanphambanchay = hoaDonDAO.top10nhanvienbannhieunhatthangtruoc();
                model.addAttribute("ngayDaChonDetailnhanvien", thoiGian);
                model.addAttribute("hienthithoigian2", thoiGian);
                break;
            case "Tháng này":
                bieudosanphambanchay = hoaDonDAO.top10nhanvienbannhieunhatthangnay();
                model.addAttribute("ngayDaChonDetailnhanvien", thoiGian);
                model.addAttribute("hienthithoigian2", thoiGian);
                break;
            case "7 Ngày qua":
                bieudosanphambanchay = hoaDonDAO.top10nhanvienbannhieunhat7ngayqua();
                model.addAttribute("ngayDaChonDetailnhanvien", thoiGian);
                model.addAttribute("hienthithoigian2", thoiGian);
                break;
            case "Hôm Qua":
                bieudosanphambanchay = hoaDonDAO.top10nhanvienbannhieunhathomqua();
                model.addAttribute("ngayDaChonDetailnhanvien", thoiGian);
                model.addAttribute("hienthithoigian2", thoiGian);
                break;
            default:
                break;
        }
        model.addAttribute("top10nhanvienbannhieunhat", bieudosanphambanchay);
        return "thongke/thongketopbanchay";

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
