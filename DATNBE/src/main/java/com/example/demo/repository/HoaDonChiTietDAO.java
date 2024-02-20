package com.example.demo.repository;

import com.example.demo.entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface HoaDonChiTietDAO extends JpaRepository<HoaDonChiTiet, UUID> {

    @Query("SELECT h FROM HoaDonChiTiet h WHERE h.hoaDon.ma = :hoaDonMa")
    Page<HoaDonChiTiet> findByHoaDonMaPage(@Param("hoaDonMa") String hoaDonMa, Pageable pageable);

    @Query("SELECT h FROM HoaDonChiTiet h WHERE h.hoaDon.ma = :hoaDonMa")
    List<HoaDonChiTiet> findByHoaDonMaList(@Param("hoaDonMa") String hoaDonMa);

    @Modifying
    @Query("DELETE FROM HoaDonChiTiet hdct WHERE hdct.hoaDon.id = :idHoaDon AND hdct.giayChiTiet.id = :idctsp")
    void deleteByHoaDonIdAndChiTietId(@Param("idHoaDon") UUID idHoaDon, @Param("idctsp") UUID idctsp);
}
