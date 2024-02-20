package com.example.demo.repository;

import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonChiTietDAO extends JpaRepository<HoaDonChiTiet, UUID> {

    @Query("SELECT h FROM HoaDonChiTiet h WHERE h.hoaDon.ma = :hoaDonMa")
    Page<HoaDonChiTiet> findByHoaDonMaPage(@Param("hoaDonMa") String hoaDonMa, Pageable pageable);

    @Query("SELECT h FROM HoaDonChiTiet h WHERE h.hoaDon.ma = :hoaDonMa")
    List<HoaDonChiTiet> findHoaDonChiTietByMaHD(@Param("hoaDonMa") String hoaDonMa);
}
