package com.example.demo.controller;

import com.example.demo.entity.DiaChi;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Controller
public class KhachHangController {
    @Autowired
    KhachHangRepo khachHangRepo;

    @Autowired
    DiachiDao diachiDao;

    @Autowired
    DiaChiRepo diaChiRepo;

    @Autowired
    KhachHangDao khachHangDao;

// Phần khách hàng


//    List khách hàng và phân trang và tìm kiếm
    @RequestMapping("/admin/khachhang")
    public String index(@ModelAttribute("khachhang") KhachHang khachHang
                        ,@RequestParam("page") Optional<Integer> pageNumber , Model model
                        ,@RequestParam("keyword") Optional<String> keyword) {
        Pageable pageable = PageRequest.of(pageNumber.orElse(0), 5);
        Page<KhachHang> page1 = khachHangDao.search("%" +keyword.orElse("") + "%",pageable);
        model.addAttribute("i", 0);
        model.addAttribute("listPKhachhang", page1);
        model.addAttribute("keyword", keyword.orElse(""));
        model.addAttribute("emaikkhachhang",khachHangDao.findEmailByMa());
        return "khachhang/Khach_hang";
    }

//    fill listdanhsach khách hàng lên table
    @ModelAttribute("listdanhsach")
    public List<KhachHang> getall() {
        return khachHangRepo.getAll();
    }


    @RequestMapping("/admin/khachhang/detail")
    public String detail(@ModelAttribute("khachhang") KhachHang khachHang) {
        return "khachhang/detail";
    }

//    detail khách hàng theo mã
    @GetMapping("/admin/khachhang/detail/{ma}")
    public String getBykhachhangma(@PathVariable("ma") String ma,
                                   Model model,
                                   @ModelAttribute("diachi") DiaChi diaChi) {
            model.addAttribute("listdiachi", diachiDao.getAllByMaDiaChi(ma));
        model.addAttribute("khachhang", khachHangDao.GetKhachhangByma(ma));

        return "khachhang/detail";
    }


    //    Thêm mới  khách hàng
    @PostMapping("admin/khachhang/save")
    public String save(@ModelAttribute("khachhang") KhachHang kh,
                       Model model,
                       @RequestPart("ten_url1") MultipartFile file) {
        Path path = Paths.get("src/main/webapp/images/");
        try {
            if (file == null || file.isEmpty()) {
                // Nếu không có ảnh được chọn, gán đường dẫn của ảnh mặc định vào đối tượng khách hàng
                kh.setAvatar("avatar.jpg"); // Thay đổi đường dẫn của ảnh mặc định theo đường dẫn thực tế của bạn
            } else {
                InputStream inputStream = file.getInputStream();
                Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                kh.setAvatar(file.getOriginalFilename());
            }

            String maKH = khachHangDao.generateNextMaKhachHang();
            kh.setMa(maKH);
            String ma = khachHangDao.save(kh).getMa();
            return "redirect:/admin/khachhang/detail/" + ma;
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin/khachhang";
        }
    }


    //    Xóa khách hàng
    @RequestMapping("/admin/khachhang/delete/{ma}")
    public String delete(@PathVariable("ma") UUID ma) {
        khachHangRepo.delete(ma);
        return "redirect:/admin/khachhang";
    }

    @PostMapping("/admin/khachhang/update/{id}")
    public String update(@PathVariable("id") String id,
                         @ModelAttribute("khachhang") KhachHang kh,
                         @RequestParam("file") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String uploadDir = "src/main/webapp/images/"; // Đường dẫn thư mục lưu trữ ảnh
                Path path = Paths.get(uploadDir);
                // Copy file ảnh vào thư mục trên server
                Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                // Cập nhật đường dẫn file vào đối tượng khách hàng
                kh.setAvatar( file.getOriginalFilename()); // Đường dẫn tới file ảnh sau khi lưu
            } else {
                // Nếu không có file mới được chọn, giữ nguyên đường dẫn ảnh hiện tại
                KhachHang existingKhachHang = khachHangDao.GetKhachhangByma(id); // Lấy thông tin khách hàng hiện tại từ database
                kh.setAvatar(existingKhachHang.getAvatar()); // Sử dụng đường dẫn ảnh hiện tại
            }
            khachHangRepo.update(id, kh); // Cập nhật thông tin của khách hàng
            return "redirect:/admin/khachhang/detail/" + id;
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/admin/khachhang";
        }
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
