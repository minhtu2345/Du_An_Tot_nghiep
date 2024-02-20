package com.example.demo.controller;

import com.example.demo.entity.DanhGia;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.DanhGiaDAO;
import com.example.demo.repository.NhanVienDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/danh-gia")
public class DanhGiaController {

    @Autowired
    private DanhGiaDAO danhGiaDAO;

    Integer numberCurrent=0;

    @GetMapping("")
    public String danhGia(Model model, @RequestParam(defaultValue = "0") String number){
        Pageable pageable= PageRequest.of(Integer.valueOf(number),10);
        numberCurrent=Integer.valueOf(number);
        model.addAttribute("page",new PageDTO<>(danhGiaDAO.findDanhGiasByTrangThai0(pageable)));
        model.addAttribute("number",Integer.valueOf(number));
        return "danh_gia/danh_gia";
    }

    @GetMapping("/duyet/{id}")
    public String duyet(@PathVariable String id){
        danhGiaDAO.duyetOne(UUID.fromString(id));
        return "redirect:/admin/danh-gia?number="+numberCurrent;
    }

    @GetMapping("/duyet-tat-ca")
    public String duyetAll(){
        danhGiaDAO.duyetAll();
        return "redirect:/admin/danh-gia";
    }

    @ResponseBody
    @PostMapping("/duyet-nhieu")
    public ResponseEntity<?> duyetNhieu(@RequestBody List<String> list){
        list.stream().forEach(item -> danhGiaDAO.duyetOne(UUID.fromString(item)));
        return ResponseEntity.ok(numberCurrent);
    }

    @ResponseBody
    @PostMapping("/xoa-nhieu")
    public ResponseEntity<?> xoaNhieu(@RequestBody List<String> list){
        list.stream().forEach(item -> danhGiaDAO.deleteById(UUID.fromString(item)));
        return ResponseEntity.ok(numberCurrent);
    }

    @GetMapping("/xoa/{id}")
    public String xoa1(@PathVariable String id){
        danhGiaDAO.deleteById(UUID.fromString(id));
        return "redirect:/admin/danh-gia?number="+numberCurrent;
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
