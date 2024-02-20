package com.example.demo.controller;

import com.example.demo.config.Config;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.CallAPIGHN;
import com.example.demo.service.UntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

@Controller
public class GioHangController {
    @Autowired
    GiayDAO giayDAO;
    @Autowired
    GiayChiTietDAO giayChiTietDAO;
    @Autowired
    GioHangDAO gioHangDAO;
    @Autowired
    GioHangChiTietDAO gioHangChiTietDAO;
    @Autowired
    KhachHangDao khachHangDao;
    @Autowired
    DiachiDao diachiDao;
    @Autowired
    GGHDDAO gghddao;
    @Autowired
    HoaDonDAO hoaDonDAO;
    @Autowired
    HoaDonChiTietDAO hoaDonChiTietDAO;
    @Autowired
    CallAPIGHN callAPIGHN;
    @Autowired
    UntityService untityService;
    @Autowired
    DanhGiaDAO danhGiaDAO;
    @Autowired
    GioHangYeuThichChiTietDao sanPhamYeuThichDAo;
    @Autowired
    AnhGiayDAO anhGiayDAO;
    @Autowired
    QuyDoiDiemDAO quyDoiDiemDAO;
    @Autowired
    ViDiemDAO viDiemDAO;
    @Autowired
    LichSuTieuDiemDAO lichSuTieuDiemDAO;
    @Autowired
    private GiamGiaHoaDonRepo giamGiaHoaDonRepo;

    @Autowired
    private GiamGiaChiTietHoaDonRepo giamGiaChiTietHoaDonRepo;


    private Authentication authentication;


    @RequestMapping("/ctsp/{x}-{giaythuonghieu}-{giaymausac}")
    public String ctsp(Model model, @PathVariable("x") String ma, @RequestParam(defaultValue = "0") String numberDg,
                       @PathVariable("giaythuonghieu") String thuonghieu,
                       @PathVariable("giaymausac") String mausac) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KhachHang khachHang = khachHangDao.getKhByEmail(authentication.getName());

        Pageable pageable1 = PageRequest.of(0, 3);

        Giay giay = giayDAO.getGiayByMa(ma);
        model.addAttribute("item", giay);
        model.addAttribute("Tongsothichsanpham", sanPhamYeuThichDAo.countYeuThichByGiayId(ma));

        if (khachHang != null) {
            model.addAttribute("taikhoan", khachHang.getId());
            model.addAttribute("khachHang", khachHang);
            model.addAttribute("yeuthich", sanPhamYeuThichDAo.getSan_Pham_Yeu_Thich_Chi_Tiet11Byma(ma));
        }

        model.addAttribute("ListGiayTheoThuongHieu", giayDAO.getAllGiayByThuonghieu(thuonghieu));
        model.addAttribute("ListGiayTheoMauSac", giayDAO.getAllGiayByMauSac(mausac,pageable1));
        model.addAttribute("listanhgiay",anhGiayDAO.getAnhByMaGiay(ma));

        Pageable pageable = PageRequest.of(Integer.valueOf(numberDg), 3);
        model.addAttribute("pageDg", new PageDTO<>(danhGiaDAO.findDanhGiasByMaSpAndTt(ma, pageable)));
        model.addAttribute("totalDg", danhGiaDAO.countGiayByMaGiayAndTt(ma));
        model.addAttribute("x", ma);
        model.addAttribute("giaythuonghieu", thuonghieu);
        model.addAttribute("giaymausac", mausac);
        model.addAttribute("dg", DanhGia.builder().giay(giayDAO.getGiayByMa(ma)).trangThai(0).build());

        return "home/chitietsanpham";
    }


    @ResponseBody
    @PostMapping("/danh-gia")
    public ResponseEntity<?> danhGiaPost(@RequestBody DanhGia danhGia) {
        danhGiaDAO.save(danhGia);
        return ResponseEntity.ok(true);
    }

    @RequestMapping("/createBill")
    public void createBill() {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDPageContentStream content = new PDPageContentStream(doc, page);
            content.setFont(PDType1Font.HELVETICA_BOLD, 12);
            content.beginText();
            content.newLineAtOffset(100, 700);
            content.showText("Hello, World!");
            content.endText();

            content.close();

            doc.save("output.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/cart/add")
    public String addcart(@RequestParam("ma_giay") String ma_giay, @RequestParam("size_giay") String size_giay, @RequestParam("so_luong") Integer so_luong) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        KhachHang khachHang = khachHangDao.getKhByEmail(username);
        GiayChiTiet giayChiTiet = giayChiTietDAO.getAllByMaGiayAndSize(ma_giay, size_giay);

        GioHang gioHang = khachHang.getGio_hang();
        GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
        boolean kq = true;
        for (GioHangChiTiet x : gioHang.getListGHCT(gioHang.getGioHangChiTiets())
        ) {
            if (x.getGiay_chi_tiet().getId().equals(giayChiTiet.getId())) {
                x.setSo_luong(x.getSo_luong() + so_luong);
                gioHangChiTietDAO.save(x);
                kq = false;
            }
        }
        if (kq == true) {
            gioHangChiTiet.setGio_hang(gioHang);
            gioHangChiTiet.setGiay_chi_tiet(giayChiTiet);
            gioHangChiTiet.setSo_luong(so_luong);
            gioHangChiTiet.setTrangthai(1);
            gioHangChiTietDAO.save(gioHangChiTiet);
        }
        return "redirect:/cart/view";
    }

    @PostMapping("/getlistGiay")
    public String getlistvalue(Model model, HttpServletRequest request, @RequestParam("maVC") String maVC) {


        String[] listvalue = request.getParameterValues("listGiay");
        List<UUID> listvalue1 = new ArrayList<>();
        List<GiayChiTiet> giayChiTietList = new ArrayList<>();
        if (listvalue != null) {
            for (String x : listvalue
            ) {
                listvalue1.add(UUID.fromString(x));
                GiayChiTiet giayChiTiet = giayChiTietDAO.findById(UUID.fromString(x)).get();
                System.out.println("gct" + giayChiTiet.getId());
                giayChiTietList.add(giayChiTiet);
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        KhachHang khachHang = khachHangDao.getKhByEmail(username);
        model.addAttribute("khachHang", khachHang);
        List<GioHangChiTiet> gioHangChiTietList = khachHang.getGio_hang().
                getListGHCT(khachHang.getGio_hang().getGioHangChiTiets());
        BigDecimal tongTien = BigDecimal.valueOf(0);
        for (GioHangChiTiet x : gioHangChiTietList
        ) {
            x.setSo_luong(Integer.parseInt(request.getParameter(x.getGiay_chi_tiet().getId() + "soluong")));
            gioHangChiTietDAO.save(x);
        }
        if (!giayChiTietList.isEmpty()) {
            for (GiayChiTiet x : giayChiTietList
            ) {
                tongTien = tongTien.add(x.getGiay().tinhTong(x.getGiay().getGia_sau_khuyen_mai(), Integer.parseInt(request.getParameter(x.getId() + "soluong"))));
            }
        }
        String maGGHD = "";
        Integer phan_tramGGHD = 0;
        BigDecimal so_tienGGHD = BigDecimal.valueOf(0);
        BigDecimal so_tienMinHD = BigDecimal.valueOf(0);
        Date currentDate1 = new Date();
        List<GiamGiaHoaDon> listGGHD = gghddao.getGiamGiaHoaDonByDk(currentDate1);
        for (GiamGiaHoaDon x : listGGHD
        ) {
            if (x.getMa().equals(maVC)) {
                so_tienMinHD = x.getDieu_kien();
                System.out.println("Min"+so_tienMinHD);
                System.out.println("Max"+tongTien);
                if (so_tienMinHD.compareTo(tongTien) < 0){
                    maGGHD = x.getMa();
                    phan_tramGGHD = x.getPhan_tram_giam();
                    so_tienGGHD = x.getSo_tien_giam_max();
                    model.addAttribute("maGGHD", x.getMa());
                    model.addAttribute("phan_tramGGHD", x.getPhan_tram_giam());
                }
            }
        }
        BigDecimal tienGGHD = tongTien.multiply(BigDecimal.valueOf(phan_tramGGHD)).divide(BigDecimal.valueOf(100));
        if (tienGGHD.compareTo(so_tienGGHD) > 0) {
            tienGGHD = so_tienGGHD;
        }
        model.addAttribute("tienGGHD", tienGGHD);
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("tienThanhToan", tongTien.subtract(tienGGHD));
        model.addAttribute("listGiay", listvalue1);
        model.addAttribute("maGGHD", maGGHD);
        model.addAttribute("maVC", maVC);
        model.addAttribute("listGHCT", gioHangChiTietList);
        return "home/viewcart";
    }

    @RequestMapping("/cart/view")
    public String viewcart(Model model, HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        KhachHang khachHang = khachHangDao.getKhByEmail(username);
        model.addAttribute("khachHang", khachHang);
        List<UUID> listUUIDGiay = new ArrayList<>();
        List<GioHangChiTiet> gioHangChiTietList = khachHang.getGio_hang().
                getListGHCT(khachHang.getGio_hang().getGioHangChiTiets());
        BigDecimal tongTien = BigDecimal.valueOf(0);
        for (GioHangChiTiet x : gioHangChiTietList
        ) {
            tongTien = tongTien.add(x.getGiay_chi_tiet().getGiay().tinhTong(x.getGiay_chi_tiet().getGiay().getGia_sau_khuyen_mai(), x.getSo_luong()));
            String a = String.valueOf(x.getGiay_chi_tiet().getId());
            System.out.println(a);
        }
        model.addAttribute("listGiay", listUUIDGiay);
        model.addAttribute("tienGGHD", 0);
        model.addAttribute("maGGHD", "");
        model.addAttribute("tienThanhToan", 0);
        model.addAttribute("tongTien", 0);
        model.addAttribute("listGHCT", gioHangChiTietList);
        return "home/viewcart";
    }

    @PostMapping("/cart/addvoucher")
    public String addVoucher(Model model, @RequestParam("maVC") String maVC) {
        String maGGHD = "";
        Integer phan_tramGGHD = 0;
        BigDecimal so_tienGGHD = BigDecimal.valueOf(0);
        List<GiamGiaHoaDon> listGGHD = gghddao.findAll();
        for (GiamGiaHoaDon x : listGGHD
        ) {
            if (x.getMa().equals(maVC)) {
                maGGHD = x.getMa();
                phan_tramGGHD = x.getPhan_tram_giam();
                so_tienGGHD = x.getSo_tien_giam_max();
                model.addAttribute("maGGHD", x.getMa());
                model.addAttribute("phan_tramGGHD", x.getPhan_tram_giam());
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        KhachHang khachHang = khachHangDao.getKhByEmail(username);
        List<GioHangChiTiet> gioHangChiTietList = khachHang.getGio_hang().
                getListGHCT(khachHang.getGio_hang().getGioHangChiTiets());
        BigDecimal tongTien = BigDecimal.valueOf(0);
        for (GioHangChiTiet x : gioHangChiTietList
        ) {
            tongTien = tongTien.add(x.getGiay_chi_tiet().getGiay().tinhTong(x.getGiay_chi_tiet().getGiay().getGia_sau_khuyen_mai(), x.getSo_luong()));
        }
        BigDecimal tienGGHD = tongTien.multiply(BigDecimal.valueOf(phan_tramGGHD)).divide(BigDecimal.valueOf(100));
        if (tienGGHD.compareTo(so_tienGGHD) > 0) {
            tienGGHD = so_tienGGHD;
        }
        model.addAttribute("tienGGHD", tienGGHD);
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("tienThanhToan", tongTien.subtract(tienGGHD));
        model.addAttribute("listGHCT", gioHangChiTietList);
        System.out.println("tienGGHD la" + tienGGHD);
        return "home/viewcart";
    }

    @RequestMapping("/checkout")
    public String checkout(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KhachHang khachHang = khachHangDao.getKhByEmail(authentication.getName());
        model.addAttribute("khachHang", khachHang);
        return "home/checkout";
    }

    @PostMapping("/checkout")
    public String checkout1(Model model, HttpServletRequest request, @RequestParam(value = "maVC", defaultValue = "") String maVC, @RequestParam(value = "ma_giay", defaultValue = "") String ma_giay, @RequestParam(value = "size_giay", defaultValue = "") String size_giay, @RequestParam(value = "so_luong", defaultValue = "") Integer so_luong) {

        String[] listvalue = null;
        listvalue = request.getParameterValues("listGiay");

        List<UUID> listvalue1 = new ArrayList<>();
        List<GiayChiTiet> giayChiTietList = new ArrayList<>();
        List<GioHangChiTiet> gioHangChiTietList1 = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        KhachHang khachHang = khachHangDao.getKhByEmail(username);
        model.addAttribute("khachHang", khachHang);
        BigDecimal tongTien = BigDecimal.valueOf(0);
        if (listvalue != null) {
            for (String x : listvalue
            ) {
                listvalue1.add(UUID.fromString(x));
                GiayChiTiet giayChiTiet = giayChiTietDAO.findById(UUID.fromString(x)).get();
                System.out.println("gct" + giayChiTiet.getId());
                giayChiTietList.add(giayChiTiet);
            }
            List<GioHangChiTiet> gioHangChiTietList = khachHang.getGio_hang().
                    getListGHCT(khachHang.getGio_hang().getGioHangChiTiets());
            for (String x : listvalue
            ) {
                for (GioHangChiTiet x1 : gioHangChiTietList
                ) {
                    if (x1.getGiay_chi_tiet().getId().equals(UUID.fromString(x))) {
                        gioHangChiTietList1.add(x1);
                    }
                }
            }
            for (GiayChiTiet x : giayChiTietList
            ) {
                tongTien = tongTien.add(x.getGiay().tinhTong(x.getGiay().getGia_sau_khuyen_mai(), Integer.parseInt(request.getParameter(x.getId() + "soluong"))));
            }
        } else {
            GiayChiTiet giayChiTietMN = giayChiTietDAO.getAllByMaGiayAndSize(ma_giay, size_giay);
            listvalue1.add(giayChiTietMN.getId());
            giayChiTietList.add(giayChiTietMN);
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setSo_luong(so_luong);
            gioHangChiTiet.setGiay_chi_tiet(giayChiTietMN);
            gioHangChiTietList1.add(gioHangChiTiet);
            for (GiayChiTiet x : giayChiTietList
            ) {
                tongTien = tongTien.add(x.getGiay().tinhTong(x.getGiay().getGia_sau_khuyen_mai(), so_luong));
            }
            System.out.println("Chạy vào đây");
        }
        String maGGHD = "";
        Integer phan_tramGGHD = 0;
        BigDecimal so_tienGGHD = BigDecimal.valueOf(0);
        BigDecimal so_tienMinHD = BigDecimal.valueOf(0);
        List<GiamGiaHoaDon> listGGHD = gghddao.findAll();
        for (GiamGiaHoaDon x : listGGHD
        ) {
            if (x.getMa().equals(maVC)) {
                so_tienMinHD = x.getDieu_kien();
                System.out.println("Min"+so_tienMinHD);
                System.out.println("Max"+tongTien);
                if (so_tienMinHD.compareTo(tongTien) < 0){
                    maGGHD = x.getMa();
                    phan_tramGGHD = x.getPhan_tram_giam();
                    so_tienGGHD = x.getSo_tien_giam_max();
                    model.addAttribute("maGGHD", x.getMa());
                    model.addAttribute("phan_tramGGHD", x.getPhan_tram_giam());
                }
            }
        }
        BigDecimal tienGGHD = tongTien.multiply(BigDecimal.valueOf(phan_tramGGHD)).divide(BigDecimal.valueOf(100));
        if (tienGGHD.compareTo(so_tienGGHD) > 0) {
            tienGGHD = so_tienGGHD;
        }
        GiaoHangNhanh giaoHangNhanh = new GiaoHangNhanh();
        List<DiaChi> diaChiList = diachiDao.getAllByMaDiaChiSortTT(khachHang.getMa());
        System.out.println("Size "+diaChiList.size());
        for (DiaChi x : diaChiList
        ) {
            if (x.getTrangthai() == 1) {
                giaoHangNhanh.setTo_district_name(x.getHuyen()); //huyện
                giaoHangNhanh.setTo_province_name(x.getThanhpho()); //thành phố
                giaoHangNhanh.setTo_ward_name(x.getXa()); //xã
//                giaoHangNhanh.setInsurance_value(tongTien.intValue());
            }
        }
        ViDiem viDiem = viDiemDAO.getViDiemByMaKH(khachHang.getMa());
        String phiShip = callAPIGHN.getAPIGHN(giaoHangNhanh);
        BigDecimal tienPhaiTT = tongTien.add(BigDecimal.valueOf(Double.valueOf(phiShip))).subtract(tienGGHD);
        QuyDoiDiem quyDoiDiem = quyDoiDiemDAO.getQuyDoiDiemByTT1();
        Integer diemTichLuy = tienPhaiTT.divide(quyDoiDiem.getSo_tien_tuong_ung()).multiply(new BigDecimal(quyDoiDiem.getSo_diem_tuong_ung())).intValue();
        model.addAttribute("tien_tuong_ung",quyDoiDiem.getSo_tien_tuong_ung());
        model.addAttribute("diem_tuong_ung",quyDoiDiem.getSo_diem_tuong_ung());
        model.addAttribute("tongDiemQuyDoiHienCo",viDiem.getTong_diem());
        model.addAttribute("phiShip", phiShip);
        model.addAttribute("tienGGHD", tienGGHD);
        model.addAttribute("tongTien", tongTien);
        model.addAttribute("tienThanhToan", tienPhaiTT);
        model.addAttribute("listGiay", listvalue1);
        model.addAttribute("maGGHD", maGGHD);
        model.addAttribute("diemTichLuy", diemTichLuy);
        model.addAttribute("maVC", maVC);
        model.addAttribute("listGHCT", gioHangChiTietList1);
        model.addAttribute("list_dia_chi", diaChiList);

        return "home/checkout";
    }

    @PostMapping("/pay")
    public String getPay(HttpServletRequest request, HttpServletResponse resp, @RequestParam("ghichu") String ghichu, @RequestParam(value = "soTienQuyDoi",defaultValue = "0.00") BigDecimal soTienQuyDoi, @RequestParam(value = "soDiemCong",defaultValue = "0") Integer soDiemCong, @RequestParam(value = "soDiemDaDung",defaultValue = "0") Integer soDiemDaDung, @RequestParam("maVC") String maVC, @RequestParam("payment_method") String pttt, @RequestParam("tienThanhToan") BigDecimal tienTT, @RequestParam("phiShip") BigDecimal phiShip, @RequestParam("tienGGHD") BigDecimal tienGGHD, @RequestParam("dc") String dc) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        KhachHang khachHang = khachHangDao.getKhByEmail(username);
        String dia_chi = "";
        String ten_nguoi_nhan = "";
        String sdt_nguoi_nhan = "";
        if (dc.equals("existing")) {
            DiaChi diaChi = diachiDao.getDiachiByma(request.getParameter("dia_chi"));
            dia_chi = diaChi.getTendiachi() + "-" + diaChi.getXa() + "-" + diaChi.getHuyen() + "-" + diaChi.getThanhpho();
            ten_nguoi_nhan = diaChi.getTen_nguoi_nhan();
            sdt_nguoi_nhan = diaChi.getSdt_nguoi_nhan();
        } else {
            dia_chi = request.getParameter("address_1") + "-" + request.getParameter("ward") + "-" + request.getParameter("district") + "-" + request.getParameter("province");
            ten_nguoi_nhan = request.getParameter("firstname");
            sdt_nguoi_nhan = request.getParameter("sdt");
        }
        if (pttt.equals("vnpay_payment")) {
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";
            Integer tienTH1 = tienTT.intValue();
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
            vnp_Params.put("vnp_ReturnUrl", "http://localhost:8080/trangchu");
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
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMa(untityService.genMaHoaDon());
            hoaDon.setKhachHang(khachHang);
            LocalDate currentDate = LocalDate.now();
            hoaDon.setNgay_tao(currentDate);
            hoaDon.setMo_ta(ghichu);
            hoaDon.setTong_tien(tienTT);
            hoaDon.setSdt_nguoi_nhan(sdt_nguoi_nhan);
            hoaDon.setTen_nguoi_nhan(ten_nguoi_nhan);
            hoaDon.setDia_chi(dia_chi);
            hoaDon.setNgay_thanh_toan(LocalDate.now());
            hoaDon.setPhi_ship(phiShip);
            hoaDon.setSo_tien_giam(tienGGHD);
            hoaDon.setHinh_thuc_mua(1); //online
            hoaDon.setHinh_thuc_thanh_toan(1); //vnpay
            hoaDon.setSo_diem_su_dung(soDiemDaDung);
            hoaDon.setSo_tien_quy_doi(soTienQuyDoi);
            hoaDon.setTrangthai(1); //chờ giao
            HoaDon hoaDon1 = hoaDonDAO.save(hoaDon);
            ViDiem viDiem = viDiemDAO.getViDiemByMaKH(khachHang.getMa());
            LichSuTieuDiem lichSuTieuDiem = new LichSuTieuDiem();
            lichSuTieuDiem.setTrangthai(1);
            lichSuTieuDiem.setVi_diem(viDiem);
            lichSuTieuDiem.setHoa_don(hoaDon1);
            lichSuTieuDiem.setNgay_su_dung(LocalDate.now());
            lichSuTieuDiem.setQuy_doi_diem(quyDoiDiemDAO.getQuyDoiDiemByTT1());
            lichSuTieuDiem.setSo_diem_da_dung(soDiemDaDung);
            lichSuTieuDiem.setSo_diem_cong(soDiemCong);
            lichSuTieuDiemDAO.save(lichSuTieuDiem);
//            viDiem.setSo_diem_da_cong(viDiem.getSo_diem_da_cong()+soDiemCong);
            viDiem.setSo_diem_da_dung(viDiem.getSo_diem_da_dung()+soDiemDaDung);
            viDiem.setTong_diem(viDiem.getSo_diem_da_cong()-viDiem.getSo_diem_da_dung());
            viDiemDAO.save(viDiem);
            if (!maVC.equals("")){
                GiamGiaHoaDon giamGiaHoaDon = giamGiaHoaDonRepo.getGiamGiaHoaDonByMa(maVC);
                GiamGiaChiTietHoaDon giamGiaChiTietHoaDon = new GiamGiaChiTietHoaDon();
                giamGiaChiTietHoaDon.setHd(hoaDon);
                giamGiaChiTietHoaDon.setGghd(giamGiaHoaDon);
                giamGiaChiTietHoaDon.setTrangthai(1);
                giamGiaChiTietHoaDonRepo.createGGCTHD2(giamGiaChiTietHoaDon);

                // Giảm số lượng voucher còn lại
                giamGiaHoaDon.setSo_luong(giamGiaHoaDon.getSo_luong() - 1);
                giamGiaHoaDonRepo.createGGHD(giamGiaHoaDon);
            }
            String[] listvalue = request.getParameterValues("listGiay");
            List<UUID> listvalue1 = new ArrayList<>();
            List<GiayChiTiet> giayChiTietList = new ArrayList<>();
            for (String x : listvalue
            ) {
                listvalue1.add(UUID.fromString(x));
                GiayChiTiet giayChiTiet = giayChiTietDAO.findById(UUID.fromString(x)).get();
                System.out.println("gct" + giayChiTiet.getId());
                giayChiTietList.add(giayChiTiet);
            }
            for (GiayChiTiet x : giayChiTietList
            ) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(hoaDon1);
                hoaDonChiTiet.setGiayChiTiet(x);
                hoaDonChiTiet.setSo_luong(Integer.parseInt(request.getParameter(x.getId() + "soluong")));
                hoaDonChiTiet.setGia_nhap(x.getGiay().getGianhap());
                hoaDonChiTiet.setDon_gia(x.getGiay().getGia_sau_khuyen_mai());
                hoaDonChiTiet.setTrangthai(1);
                hoaDonChiTietDAO.save(hoaDonChiTiet);
                x.setSo_luong_ton(x.getSo_luong_ton()-Integer.parseInt(request.getParameter(x.getId() + "soluong")));
                giayChiTietDAO.save(x);
                List<GioHangChiTiet> gioHangChiTietList = khachHang.getGio_hang().
                        getListGHCT(khachHang.getGio_hang().getGioHangChiTiets());
                for (GioHangChiTiet a:gioHangChiTietList){
                    if (a.getGiay_chi_tiet().getId().equals(x.getId())){
                        System.out.println("Ma xoa"+x.getId());
                        System.out.println("Ma xoa 2"+a.getGiay_chi_tiet().getId());
                        gioHangChiTietDAO.deleteSPInGHCT(a.getId());
                    }
                }
            }
        } else if (pttt.equals("cod")) {
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMa(untityService.genMaHoaDon());
            hoaDon.setKhachHang(khachHang);
            LocalDate currentDate = LocalDate.now();
            hoaDon.setNgay_tao(currentDate);
            hoaDon.setMo_ta(ghichu);
            hoaDon.setTong_tien(tienTT);
            hoaDon.setSdt_nguoi_nhan(sdt_nguoi_nhan);
            hoaDon.setTen_nguoi_nhan(ten_nguoi_nhan);
            hoaDon.setDia_chi(dia_chi);
            hoaDon.setPhi_ship(phiShip);
            hoaDon.setSo_tien_giam(tienGGHD);
            hoaDon.setHinh_thuc_mua(1); //online
            hoaDon.setHinh_thuc_thanh_toan(3); //khi nhan hang
            hoaDon.setSo_diem_su_dung(soDiemDaDung);
            hoaDon.setSo_tien_quy_doi(soTienQuyDoi);
            hoaDon.setTrangthai(0);
            HoaDon hoaDon1 = hoaDonDAO.save(hoaDon);
            ViDiem viDiem = viDiemDAO.getViDiemByMaKH(khachHang.getMa());
            LichSuTieuDiem lichSuTieuDiem = new LichSuTieuDiem();
            lichSuTieuDiem.setTrangthai(1);
            lichSuTieuDiem.setVi_diem(viDiem);
            lichSuTieuDiem.setHoa_don(hoaDon1);
            lichSuTieuDiem.setNgay_su_dung(LocalDate.now());
            lichSuTieuDiem.setQuy_doi_diem(quyDoiDiemDAO.getQuyDoiDiemByTT1());
            lichSuTieuDiem.setSo_diem_da_dung(soDiemDaDung);
            lichSuTieuDiem.setSo_diem_cong(soDiemCong);
            lichSuTieuDiemDAO.save(lichSuTieuDiem);
//            viDiem.setSo_diem_da_cong(viDiem.getSo_diem_da_cong()+soDiemCong);
            viDiem.setSo_diem_da_dung(viDiem.getSo_diem_da_dung()+soDiemDaDung);
            viDiem.setTong_diem(viDiem.getSo_diem_da_cong()-viDiem.getSo_diem_da_dung());
            viDiemDAO.save(viDiem);
            if (!maVC.equals("")){
                GiamGiaHoaDon giamGiaHoaDon = giamGiaHoaDonRepo.getGiamGiaHoaDonByMa(maVC);
                GiamGiaChiTietHoaDon giamGiaChiTietHoaDon = new GiamGiaChiTietHoaDon();
                giamGiaChiTietHoaDon.setHd(hoaDon);
                giamGiaChiTietHoaDon.setGghd(giamGiaHoaDon);
                giamGiaChiTietHoaDon.setTrangthai(1);
                giamGiaChiTietHoaDonRepo.createGGCTHD2(giamGiaChiTietHoaDon);

                // Giảm số lượng voucher còn lại
                giamGiaHoaDon.setSo_luong(giamGiaHoaDon.getSo_luong() - 1);
                giamGiaHoaDonRepo.createGGHD(giamGiaHoaDon);
            }
            String[] listvalue = request.getParameterValues("listGiay");
            List<UUID> listvalue1 = new ArrayList<>();
            List<GiayChiTiet> giayChiTietList = new ArrayList<>();
            for (String x : listvalue
            ) {
                listvalue1.add(UUID.fromString(x));
                GiayChiTiet giayChiTiet = giayChiTietDAO.findById(UUID.fromString(x)).get();
                System.out.println("gct" + giayChiTiet.getId());
                giayChiTietList.add(giayChiTiet);
            }
            for (GiayChiTiet x : giayChiTietList
            ) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setHoaDon(hoaDon1);
                hoaDonChiTiet.setGiayChiTiet(x);
                hoaDonChiTiet.setSo_luong(Integer.parseInt(request.getParameter(x.getId() + "soluong")));
                hoaDonChiTiet.setGia_nhap(x.getGiay().getGianhap());
                hoaDonChiTiet.setDon_gia(x.getGiay().getGia_sau_khuyen_mai());
                hoaDonChiTiet.setTrangthai(1);
                hoaDonChiTietDAO.save(hoaDonChiTiet);
                x.setSo_luong_ton(x.getSo_luong_ton()-Integer.parseInt(request.getParameter(x.getId() + "soluong")));
                giayChiTietDAO.save(x);
                List<GioHangChiTiet> gioHangChiTietList = khachHang.getGio_hang().
                        getListGHCT(khachHang.getGio_hang().getGioHangChiTiets());
                for (GioHangChiTiet a:gioHangChiTietList){
                    if (a.getGiay_chi_tiet().getId().equals(x.getId())){
                        gioHangChiTietDAO.deleteSPInGHCT(a.getId());
                    }
                }
            }
            return "redirect:/trangchu";
        }
        return "redirect:/trangchu";
    }

    @GetMapping("/hoadon/{x}")
    public String getHoaDon(@PathVariable("x") String chuoiIDGCT, @RequestParam("vnp_ResponseCode") Integer vnp_ResponseCode) {
        if (vnp_ResponseCode == 00) {
            String[] chuoiIDGCT1 = chuoiIDGCT.split("_");
            for (String part : chuoiIDGCT1) {
                System.out.println(part);
            }
        }
        return "home/index";
    }

    @GetMapping("/demogenmahd")
    public String getgenmahd() {
        String maHD = untityService.genMaHoaDon();
        System.out.println(maHD);
        return "home/index";
    }
}
