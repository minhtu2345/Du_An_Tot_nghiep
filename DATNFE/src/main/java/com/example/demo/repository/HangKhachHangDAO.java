package com.example.demo.repository;

import com.example.demo.entity.HangKhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface HangKhachHangDAO extends JpaRepository<HangKhachHang, UUID> {
    @Query("select p from HangKhachHang p where p.ma=?1")
    HangKhachHang getHangKhachHangByMa(String ma);
}
