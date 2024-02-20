package com.example.demo.controller;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.YKienDongGop;
import com.example.demo.repository.KhachHangDao;
import com.example.demo.repository.YKienDongGopDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class YKienDongGopController {

    @Autowired
    YKienDongGopDao yKienDongGopDao;

    @Autowired
    KhachHangDao khachHangDao;

    Integer numberCurrent=0;

    @GetMapping("/admin/y-kien-dong-gop")
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page){
        Pageable pageable = PageRequest.of( page.orElse(0),5);
        model.addAttribute("dsykien",  yKienDongGopDao.phantrang(pageable));
        return "ykiendonggop/y_kien_dong_gop";
    }

    @PostMapping("/save-y-kien")
    public String save(@ModelAttribute("yKienDongGop") YKienDongGop yKienDongGop,
                       @RequestParam("ten") String ten,
                       @RequestParam("sdt") String sdt,
                       @RequestParam("email")
                           @Email(message = "Địa chỉ Email không hợp lệ") String email,
                       @RequestParam("ykienphanhoi") String ykienphanhoi
                      ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KhachHang khachHang = khachHangDao.getKhByEmail(authentication.getName());

        yKienDongGop.setKhachHang(khachHang);
        yKienDongGop.setMa(yKienDongGopDao.generateNextMaKhachHang());
        yKienDongGop.setTrangthai(1);
        yKienDongGop.setTen(ten);
        yKienDongGop.setEmail(email);
        yKienDongGop.setSdt(sdt);
        yKienDongGop.setNoidung(ykienphanhoi);
        yKienDongGopDao.save(yKienDongGop);
        return "redirect:/contact";
    }


    @ResponseBody
    @PostMapping("/admin/deletes")
    public ResponseEntity<?> xoaNhieu(@RequestBody List<String> list){
        list.stream().forEach(item -> yKienDongGopDao.deleteById(UUID.fromString(item)));
        return ResponseEntity.ok(numberCurrent);
    }

    @GetMapping("/admin/delete/{id}")
    public String xoa1(@PathVariable String id){
        yKienDongGopDao.deleteById(UUID.fromString(id));
        return "redirect:/admin/y-kien-dong-gop?number="+numberCurrent;
    }
}
