package com.example.demo.controller;

import com.example.demo.entity.KichCo;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.KichCoRepo;
import com.example.demo.repository.KichCoRepo;
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
public class KichCoController {
    @Autowired
    KichCoRepo kichCoRepo;
    @RequestMapping("/admin/kichco")
    public String KichCo(@ModelAttribute("kichco") KichCo KichCo, @RequestParam("page") Optional<Integer> page,Model model) {
       PageDTO<KichCo> page1 = kichCoRepo.getPageKichCo(page.orElse(0));
       model.addAttribute("i",0);
       model.addAttribute("listPKichCo",page1);
        return "product/kich_co";
    }

    @PostMapping("/admin/kichco/create")
    public String createKichCo(@ModelAttribute("kichco") KichCo KichCo) {
        kichCoRepo.createKichCo(KichCo);
        return "redirect:/admin/kichco";
    }
    @PostMapping("/admin/kichco/update/{x}")
    public String updateKichCo(@PathVariable("x") String ma,@ModelAttribute("kichco") KichCo KichCo) {
        kichCoRepo.updateKichCo(ma,KichCo);
        return "redirect:/admin/KichCo";
    }
    @RequestMapping ("/admin/kichco/delete/{x}")
    public String deleteKichCo(@PathVariable("x") String ma) {
        kichCoRepo.delete(ma);
        return "redirect:/admin/kichco";
    }

    @RequestMapping("/admin/kichco/detail/{ma}")
    public String createKichCo(@PathVariable("ma") String ma,  @RequestParam("page") Optional<Integer> page,Model model) {
        PageDTO<KichCo> page1 = kichCoRepo.getPageKichCo(page.orElse(0));
        model.addAttribute("listPKichCo",page1);
        model.addAttribute("kichco", kichCoRepo.getKichCoByMa(ma));
        return "product/kich_co";
    }

    @ModelAttribute("listKichCo")
    public List<KichCo> getListKichCo() {
        return kichCoRepo.getListKichCo();
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
