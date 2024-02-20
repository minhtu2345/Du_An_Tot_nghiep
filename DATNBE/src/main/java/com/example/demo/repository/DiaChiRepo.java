package com.example.demo.repository;

import com.example.demo.entity.DiaChi;
import com.example.demo.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DiaChiRepo extends JpaRepository<DiaChi, UUID> {

    @Query("select p from DiaChi p where p.id = ?1")
    DiaChi getDiaChiByid(UUID id);

    @Query("select d from DiaChi d where d.khachHang.ma = ?1")
    List<DiaChi> findDiaChiByMaKhachHang(String maKhachHang);

    @Query("select d from DiaChi d where d.ma = ?1")
    DiaChi findDiaChiByByMa(String ma);
}
