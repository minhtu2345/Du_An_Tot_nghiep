package com.example.demo.repository;

import com.example.demo.entity.ChuongTrinhGiamGiaSP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChuongTrinhGiamGiaSPRepository extends JpaRepository<ChuongTrinhGiamGiaSP, UUID> {

    ChuongTrinhGiamGiaSP findChuongTrinhGiamGiaSPByMaKhuyenMai(String maKhuyenMai);

    ChuongTrinhGiamGiaSP findChuongTrinhGiamGiaSPByIdKhuyenMai(UUID idKhuyenMai);

    @Query("select ctggsp from ChuongTrinhGiamGiaSP  ctggsp where ctggsp.maKhuyenMai like ?1 or ctggsp.tenKhuyenMai like ?1")
    Page<ChuongTrinhGiamGiaSP> timKiemMaHoacTen(String keyword, Pageable pageable);

    @Query("select ctggsp from ChuongTrinhGiamGiaSP  ctggsp where ctggsp.maKhuyenMai = ?1 ")
    ChuongTrinhGiamGiaSP findByMa(String maKM);

    @Query("select ctggsp from ChuongTrinhGiamGiaSP  ctggsp where ctggsp.trangThai = ?1")
    Page<ChuongTrinhGiamGiaSP> searchByTrangThai(String trangthai, Pageable pageable);

    @Query("select ctggsp from ChuongTrinhGiamGiaSP  ctggsp where ctggsp.maKhuyenMai like ?1 or ctggsp.tenKhuyenMai like ?1 or ctggsp.trangThai like ?1")
    Page<ChuongTrinhGiamGiaSP> searchMaOrTenOrTrangThai(String keyword, Pageable pageable);


}
