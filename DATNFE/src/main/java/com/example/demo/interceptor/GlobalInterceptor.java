package com.example.demo.interceptor;

import com.example.demo.entity.KhachHang;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GlobalInterceptor
        implements HandlerInterceptor {

    @Autowired
    XuatXuRepo xuatXuRepo;
    @Autowired
    ChatLieuRepo chatLieuRepo;
    @Autowired
    KieuDangRepo kieuDangRepo;
    @Autowired
    DeGiayRepo deGiayRepo;
    @Autowired
    GioiTinhRepo gioiTinhRepo;
    @Autowired
    KichCoRepo kichCoRepo;
    @Autowired
    MauSacRepo mauSacRepo;
    @Autowired
    ThuongHieuRepo thuongHieuRepo;
    @Autowired
    ThuongHieuDAO thuongHieuDAO;
    @Autowired
    GioHangChiTietDAO gioHangChiTietDAO;
    @Autowired
    KhachHangDao khachHangDao;
    @Autowired
    GioHangYeuThichChiTietDao gioHangYeuThichChiTietDao;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("listXuatXu", xuatXuRepo.getListXuatXu());
        request.setAttribute("listChatLieu", chatLieuRepo.getListChatLieu());
        request.setAttribute("listKieuDang", kieuDangRepo.getListKieuDang());
        request.setAttribute("listDeGiay", deGiayRepo.getListDeGiay());
        request.setAttribute("listGioiTinh", gioiTinhRepo.getListGioiTinh());
        request.setAttribute("listKichCo", kichCoRepo.getListKichCo());
        request.setAttribute("listMauSac", mauSacRepo.getListMauSac());
        request.setAttribute("listThuongHieu", thuongHieuDAO.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("Name là"+username);
        if (!username.equals("anonymousUser")){
            KhachHang khachHang = khachHangDao.getKhByEmail(username);
            if (khachHang!=null){
                request.setAttribute("countGHCTByKH", gioHangChiTietDAO.countGH(khachHang.getMa()));
                request.setAttribute("countGHSPYTByKH", gioHangYeuThichChiTietDao.countGHSPYEByMaKH(khachHang.getMa()));
            }
        }else {
            request.setAttribute("countGHCTByKH", 0);
            request.setAttribute("countGHSPYTByKH", 0);
        }
    }
}
