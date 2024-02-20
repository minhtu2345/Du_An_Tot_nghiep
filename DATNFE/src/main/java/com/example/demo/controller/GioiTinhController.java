package com.example.demo.controller;

import com.example.demo.entity.GioiTinh;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.GioiTinhRepo;
import com.example.demo.repository.GioiTinhRepo;
import com.example.demo.repository.NhanVienDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class GioiTinhController {
    @Autowired
    GioiTinhRepo gioiTinhRepo;
    @RequestMapping("/admin/gioitinh")
    public String GioiTinh(@ModelAttribute("gioitinh") GioiTinh GioiTinh, @RequestParam("page") Optional<Integer> page,Model model) {
       PageDTO<GioiTinh> page1 = gioiTinhRepo.getPageGioiTinh(page.orElse(0));
       model.addAttribute("i",0);
       model.addAttribute("listPGioiTinh",page1);
        return "product/gioi_tinh";
    }

    @PostMapping("/admin/gioitinh/create")
    public String createGioiTinh(@ModelAttribute("gioitinh") GioiTinh GioiTinh) {
        gioiTinhRepo.createGioiTinh(GioiTinh);
        return "redirect:/admin/gioitinh";
    }
    @PostMapping("/admin/gioitinh/update/{x}")
    public String updateGioiTinh(@PathVariable("x") String ma,@ModelAttribute("gioitinh") GioiTinh GioiTinh) {
        gioiTinhRepo.updateGioiTinh(ma,GioiTinh);
        return "redirect:/admin/gioitinh";
    }
    @RequestMapping ("/admin/gioitinh/delete/{x}")
    public String deleteGioiTinh(@PathVariable("x") String ma) {
        gioiTinhRepo.delete(ma);
        return "redirect:/admin/gioitinh";
    }

    @RequestMapping("/admin/gioitinh/detail/{ma}")
    public String createGioiTinh(@PathVariable("ma") String ma,  @RequestParam("page") Optional<Integer> page,Model model) {
        PageDTO<GioiTinh> page1 = gioiTinhRepo.getPageGioiTinh(page.orElse(0));
        model.addAttribute("listPGioiTinh",page1);
        model.addAttribute("gioitinh", gioiTinhRepo.getGioiTinhByMa(ma));
        return "product/gioi_tinh";
    }

    @ModelAttribute("listGioiTinh")
    public List<GioiTinh> getListGioiTinh() {
        return gioiTinhRepo.getListGioiTinh();
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
