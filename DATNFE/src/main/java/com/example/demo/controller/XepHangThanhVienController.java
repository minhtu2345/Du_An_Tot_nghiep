package com.example.demo.controller;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.KhachHang;
import com.example.demo.repository.HangKhachHangDAO;
import com.example.demo.repository.HoaDonDAO;
import com.example.demo.repository.KhachHangDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class XepHangThanhVienController {

    @Autowired
    KhachHangDao khachHangDao;

    @Autowired
    HoaDonDAO hoaDonDAO;

    @Autowired
    HangKhachHangDAO hangKhachHangDAO;

    private Authentication authentication;

    @GetMapping("/qltk-kh/hang-thanh-vien")
    public String index(Model model){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        KhachHang khachHang=  khachHangDao.getKhByEmail(authentication.getName());
      List<HoaDon> hoaDons = hoaDonDAO.getHoaDonByMaKh(khachHang.getMa());
        BigDecimal tongTienChiTieu = BigDecimal.ZERO;
        if (!hoaDons.isEmpty()){
            for (HoaDon x:hoaDons
            ) {
                if (x.getTrangthai()==3){
                    tongTienChiTieu=tongTienChiTieu.add(x.getTong_tien());
                }
            }
        }
       String mahkh = khachHang.getHang_khach_hang().getMa();

        Integer hht = Integer.parseInt(mahkh.substring(3))+1;
        String hkhtren = "HKH"+hht;
        BigDecimal tienchenhlech = hangKhachHangDAO.getHangKhachHangByMa(hkhtren).getDieu_kien().subtract(tongTienChiTieu);
        model.addAttribute("tongTienChiTieu", tongTienChiTieu);
        model.addAttribute("khachHang", khachHang);
        model.addAttribute("mahkh", mahkh);
        model.addAttribute("tienchenhlech", tienchenhlech);
        return "qltk_kh/xephangthanhvien";
    }
}
