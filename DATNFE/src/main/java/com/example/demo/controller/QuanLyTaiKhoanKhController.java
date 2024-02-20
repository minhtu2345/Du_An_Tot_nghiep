package com.example.demo.controller;

import com.example.demo.email.service.EmailService;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
public class QuanLyTaiKhoanKhController {

    @Autowired
    private KhachHangRepo khachHangRepo;

    @Autowired
    private DiaChiRepo diaChiRepo;

    @Autowired
    private HoaDonDAO hoaDonDAO;

    @Autowired
    private DiachiDao diachiDao;

    @Autowired
    private KhachHangDao khachHangDao;
    @Autowired
    private GioHangDAO gioHangDAO;
    @Autowired
    GioHangYeuThichDao gioHangYeuThichDAo;

    private Authentication authentication;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/qltk-kh")
    public String qltkKh() {
        return "qltk_kh/index";
    }

    @RequestMapping("/qltk-kh/thong-tin")
    public String thongTin(Model model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        KhachHang khachHang = khachHangDao.getKhByEmail(authentication.getName());
        model.addAttribute("khachHang", khachHang);
        return "qltk_kh/thong_tin";
    }

    @PostMapping("/qltk-kh/cap-nhat-thong-tin/{idKh}")
    public String capNhatThongTin(@PathVariable String idKh, @ModelAttribute KhachHang khachHang, @RequestParam("file") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String uploadDir = "src/main/webapp/images/"; // Đường dẫn thư mục lưu trữ ảnh
                Path path = Paths.get(uploadDir);

                // Copy file ảnh vào thư mục trên server
                Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

                // Cập nhật đường dẫn file vào đối tượng khách hàng
                khachHang.setAvatar( file.getOriginalFilename()); // Đường dẫn tới file ảnh sau khi lưu
            } else {
                // Nếu không có file mới được chọn, giữ nguyên đường dẫn ảnh hiện tại
                KhachHang existingKhachHang =  khachHangDao.findById(UUID.fromString(idKh)).get(); // Lấy thông tin khách hàng hiện tại từ database
                khachHang.setAvatar(existingKhachHang.getAvatar()); // Sử dụng đường dẫn ảnh hiện tại
            }

            KhachHang kh = khachHangDao.findById(UUID.fromString(idKh)).get();
            khachHang.setMatkhau(kh.getMatkhau());
            khachHang.setTrangthai(kh.getTrangthai());
            khachHang.setEmail(kh.getEmail());
            khachHang.setMa(kh.getMa());
            khachHangRepo.update(idKh, khachHang);
            return "redirect:/qltk-kh/thong-tin";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/qltk-kh/thong-tin";
        }

    }

    @RequestMapping("/qltk-kh/doi-mat-khau")
    public String doiMatKhau(Model model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        KhachHang khachHang = khachHangDao.getKhByEmail(authentication.getName());
        model.addAttribute("khachHang", khachHang);
        return "qltk_kh/doi_mat_khau";
    }

    @PostMapping("/qltk-kh/doi-mat-khau")
    public String doiMatKhauPost(@RequestParam String mkCu,
                                 @RequestParam String mkMoi,
                                 @RequestParam String mkMoi2
    ) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        KhachHang khachHang = khachHangDao.getKhByEmail(authentication.getName());
        if(passwordEncoder.matches(mkCu,khachHang.getMatkhau())){
            if (mkMoi.trim().equalsIgnoreCase(mkMoi2.trim())) {
                khachHang.setMatkhau(passwordEncoder.encode(mkMoi));
                khachHangRepo.update(String.valueOf(khachHang.getId()), khachHang);
            }
        }
        return "redirect:/qltk-kh/doi-mat-khau";
    }

    @RequestMapping("/qltk-kh/dia-chi")
    public String diaChiKh(Model model, @ModelAttribute DiaChi diaChi) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        KhachHang khachHang = khachHangDao.getKhByEmail(authentication.getName());
        diaChi.setKhachHang(khachHang);
        model.addAttribute("listdiachi", diachiDao.getAllByMaDiaChi(khachHang.getMa()));
        model.addAttribute("khachHang", khachHang);
        model.addAttribute("diaChiUd", new DiaChi());
        return "qltk_kh/dia_chi";
    }

    @RequestMapping("/qltk-kh/xoa-dia-chi/{ma}")
    public String xoaDiaChi(@PathVariable UUID ma) {
        diaChiRepo.delete(ma);
        return "redirect:/qltk-kh/dia-chi";
    }

    @PostMapping("/qltk-kh/them-dia-chi")
    public String themDiaChi(@ModelAttribute DiaChi diaChi) {
        diaChi.setMadc("DC"+String.valueOf(diachiDao.getMaMax()+1));
        if(diaChi.getTrangthai()==null || diaChi.getTrangthai()==0){
            diaChi.setTrangthai(0);
        }
        else{
            diachiDao.updateTtDiaChiByIdKh(0,diaChi.getKhachHang().getId());
            diachiDao.updateTtDiaChiByMaDc(1,diaChi.getMadc());
        }
        diachiDao.save(diaChi);
        return "redirect:/qltk-kh/dia-chi";
    }

    @PostMapping("/qltk-kh/cap-nhat-dia-chi")
    public String capNhatDiaChi(@ModelAttribute("diaChiUd") DiaChi diaChi) {
        DiaChi dc1 = diachiDao.getDiachiByma(diaChi.getMadc());
        dc1.setHuyen(diaChi.getHuyen());
        dc1.setXa(diaChi.getXa());
        dc1.setThanhpho(diaChi.getThanhpho());
        dc1.setTendiachi(diaChi.getTendiachi());
        dc1.setTrangthai(diaChi.getTrangthai());
        dc1.setTen_nguoi_nhan(diaChi.getTen_nguoi_nhan());
        dc1.setSdt_nguoi_nhan(diaChi.getSdt_nguoi_nhan());

        if(diaChi.getTrangthai()==null || diaChi.getTrangthai()==0){
            dc1.setTrangthai(0);
        }
        else{
            diachiDao.updateTtDiaChiByIdKh(0,dc1.getKhachHang().getId());
            diachiDao.updateTtDiaChiByMaDc(1,dc1.getMadc());
        }
        diachiDao.save(dc1);
        return "redirect:/qltk-kh/dia-chi";
    }

    @GetMapping("/qltk-kh/dat-dc-mac-dinh/{ma}")
    public String datDcMacDinh(@PathVariable("ma") String madc) {
        DiaChi dc=diachiDao.getDiachiByma(madc);
        diachiDao.updateTtDiaChiByIdKh(0,dc.getKhachHang().getId());
        diachiDao.updateTtDiaChiByMaDc(1,madc);
        return "redirect:/qltk-kh/dia-chi";
    }

    static Map<String, Integer> trangThaiDonHang;
    static {
        trangThaiDonHang = new HashMap<String, Integer>();
        trangThaiDonHang.put("tat-ca", null);
        trangThaiDonHang.put("cho-xac-nhan", 0);
        trangThaiDonHang.put("cho-giao", 1);
        trangThaiDonHang.put("van-chuyen", 2);
        trangThaiDonHang.put("hoan-thanh", 3);
        trangThaiDonHang.put("huy", 4);
    }

    @GetMapping("/qltk-kh/don-hang")
    public String donHang(Model model,
                          @RequestParam(defaultValue = "tat-ca") String trangThaiDon,
                          @RequestParam(defaultValue = "0") String number
                          ) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        KhachHang khachHang = khachHangDao.getKhByEmail(authentication.getName());
        Pageable pageable= PageRequest.of(Integer.valueOf(number),6);
        Page<HoaDon>page=hoaDonDAO.findHdByMaKhAndTt(khachHang.getMa(),trangThaiDonHang.get(trangThaiDon),pageable);
        PageDTO<HoaDon> pageDTO=new PageDTO<>(page);
        model.addAttribute("pageHd",pageDTO);
//        model.addAttribute("trangThaiDonHang",trangThaiDonHang);
        model.addAttribute("trangThaiDon",trangThaiDon);
        model.addAttribute("khachHang", khachHang);
        return "qltk_kh/don_hang";
    }

    @GetMapping("/dang-ky-khach-hang")
    public String dangKyKhachHang(Model model,@ModelAttribute KhachHang khachHang){
        return "qltk_kh/dang_ky";
    }

    @PostMapping("/dang-ky-khach-hang")
    public String dangKyKhachHangPost(Model model,
                                      @ModelAttribute KhachHang khachHang){
        model.addAttribute("khachHang",khachHang);
        KhachHang kh=khachHangDao.getKhByEmail(khachHang.getEmail().trim());
        if(kh==null){
            emailService.sendOtpDangKy(khachHang.getEmail());
            return "qltk_kh/otp";
        }
        else {
            return "qltk_kh/dang_ky";
        }
    }

    @PostMapping("/otp")
    public String otpPostDangKy(Model model,
                                      @ModelAttribute KhachHang khachHang,
                                      @RequestParam String OTP){
        if(emailService.isValidOtp(khachHang.getEmail(),OTP)){
            khachHang.setMa("KH"+String.valueOf(khachHangDao.getMaMax()+1));
            khachHang.setTrangthai(1);
            khachHang.setAvatar("avatar.jpg");
            khachHang.setMatkhau(passwordEncoder.encode(khachHang.getMatkhau()));
            KhachHang kh = khachHangDao.save(khachHang);
                GioHang gioHang1 = new GioHang();
                gioHang1.setKhach_hang(kh);
                gioHang1.setNgay_tao(LocalDate.now());
                gioHang1.setMa(gioHangDAO.generateNextMaGioHang());
                gioHangDAO.save(gioHang1);
        }
        else {
            model.addAttribute("khachHang",khachHang);
            model.addAttribute("otp","OTP không đúng hoặc hết hiệu lực");
            return "qltk_kh/otp";
        }
        return "redirect:/login";
    }

    @GetMapping("/quen-mk")
    public String quenMk(){
        return "qltk_kh/quen_mk";
    }

    @PostMapping("/quen-mk")
    public String quenMkPost(@RequestParam String email,Model model){
        if(khachHangDao.getKhByEmail(email)!=null){
            emailService.sendOtpQuenMk(email);
        }
        model.addAttribute("email",email);
        return "qltk_kh/otp_quenmk";
    }

    @PostMapping("/otp-quen-mk")
    public String otpPostQuenMk(@RequestParam String OTP,
                                @RequestParam String email,
                                Model model){
        if(emailService.isValidOtp(email,OTP)){
            emailService.sendPass(email);
        }
        else {
            model.addAttribute("email",email);
            model.addAttribute("otp","OTP không đúng hoặc hết hiệu lực");
            return "qltk_kh/otp_quenmk";
        }
        return "redirect:/login";
    }
    @Autowired
    private EmailService emailService;

    @ResponseBody
    @GetMapping("/gui-otp/{email}")
    public Boolean guiOtp(@PathVariable String email){
        emailService.sendOtpDangKy(email);
        return true;
    }

    @ResponseBody
    @GetMapping("/khIsExist/{email}")
    public Boolean isExist(@PathVariable String email) {
        KhachHang kh=khachHangDao.getKhByEmail(email.trim());
        if(kh!=null){
            return true;
        }else {
            return false;
        }
    }
}
