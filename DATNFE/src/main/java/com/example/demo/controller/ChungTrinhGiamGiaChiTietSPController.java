package com.example.demo.controller;

import com.example.demo.entity.ChuongTrinhGiamGiaChiTietSP;
import com.example.demo.entity.ChuongTrinhGiamGiaSP;
import com.example.demo.entity.Giay;
import com.example.demo.entity.NhanVien;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Controller
public class ChungTrinhGiamGiaChiTietSPController {

    @Autowired
    ChuongTrinhGiamGiaChiTietSPRepo ctkmRepo;

    @Autowired
    ChuongTrinhGiamGiaChitietSanPhamDTO ctkmDTO;

    @Autowired
    GiayDTO giayDTO;

    @Autowired
    ChuongTrinhGiamGiaSPRepo ctggRepo;

    @ModelAttribute("ctkmTable")
    public List<ChuongTrinhGiamGiaChiTietSP> getListCtkm() {
        return ctkmRepo.getListCtkm();
    }


    @PostMapping("/admin/create-ctkm")
    public String addListSP(HttpServletRequest request, Model model,
                            @RequestParam(value = "idKM") UUID idKM,
                            @RequestParam(value = "idGiay") UUID idGiay
    ) {
        String[] listGiay = request.getParameterValues("listIdGiay");
        for (String x : listGiay) {
            Giay giay= giayDTO.getOneById(UUID.fromString(x));

            String giaBan = request.getParameter(x + "giaBan");
            String phanTramGiam = request.getParameter(x + "phanTramGiam");
            Integer giaBanInt = Integer.parseInt(giaBan);
            Integer ptGiamInt = Integer.parseInt(phanTramGiam);
            BigDecimal soTienDaGiam = BigDecimal.valueOf(giaBanInt * ptGiamInt / 100);
            BigDecimal gia_sau_khuyen_mai =  BigDecimal.valueOf(giaBanInt - giaBanInt * ptGiamInt / 100);

            ChuongTrinhGiamGiaChiTietSP ctkm = new ChuongTrinhGiamGiaChiTietSP();
            ctkm.setGiay(Giay.builder().id(UUID.fromString(x)).build());        //Sá»­a láº¡i trc khi commit
            ctkm.setChuongTrinhGiamGiaSP(ChuongTrinhGiamGiaSP.builder().idKhuyenMai(idKM).build());
            ctkm.setTrangThai(1);
            ctkm.setSoTienDaGiam(soTienDaGiam);
            ctkmDTO.save(ctkm);
            giay.setGia_sau_khuyen_mai(gia_sau_khuyen_mai);
            giayDTO.save(giay);
        }
        String maKM  = ctggRepo.getOneById(idKM).getMaKhuyenMai();
        return "redirect:/admin/chuong-trinh-giam-gia-sp/detail/" + maKM;
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
