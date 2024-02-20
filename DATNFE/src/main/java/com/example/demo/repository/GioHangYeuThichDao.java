package com.example.demo.repository;

import com.example.demo.entity.Gio_Hang_Yeu_Thich;
import com.example.demo.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GioHangYeuThichDao extends JpaRepository<Gio_Hang_Yeu_Thich, UUID> {
    Gio_Hang_Yeu_Thich findByKhachHang(KhachHang khachHang);

}
