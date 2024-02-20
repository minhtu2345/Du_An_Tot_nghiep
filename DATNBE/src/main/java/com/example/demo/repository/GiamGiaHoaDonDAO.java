package com.example.demo.repository;

import com.example.demo.entity.GiamGiaHoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public interface GiamGiaHoaDonDAO extends JpaRepository<GiamGiaHoaDon, UUID> {

    // Query find Giảm Giá Hóa Đơn by Mã
    @Query("select gg from GiamGiaHoaDon gg where gg.ma=?1")
    GiamGiaHoaDon findGiamGiaHoaDonByMa(String ma);

    // Query find GGHD by ngày bắt đầu và ngày kết thúc
    @Query("SELECT gg FROM GiamGiaHoaDon gg " +
            "WHERE (:startDate IS NULL OR gg.ngay_bat_dau >= :startDate) " +
            "AND (:endDate IS NULL OR gg.ngay_ket_thuc <= :endDate)")
    Page<GiamGiaHoaDon> findGiamGiaHoaDonByNgayBatDauAndNgayKetThucBetween(
            @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

    // Query find GGHD by tên
    @Query("SELECT gg FROM GiamGiaHoaDon gg " +
            "WHERE lower(gg.ten) LIKE lower(concat('%', :ten, '%'))")
    Page<GiamGiaHoaDon> findGiamGiaHoaDonByTen(
            @Param("ten") String ten, Pageable pageable);

    // Query find GGHD by trạng thái
    @Query("SELECT gg FROM GiamGiaHoaDon gg WHERE (:trangthai IN (0, 1) AND gg.trangthai = :trangthai) OR (:trangthai = 2)")
    Page<GiamGiaHoaDon> findGiamGiaHoaDonByTrangthai(@Param("trangthai") Integer trangthai, Pageable pageable);

    // Query find GGHD với trạng thái = 1
    @Query("SELECT gg FROM GiamGiaHoaDon gg WHERE gg.trangthai = 1 and gg.so_luong > 0")
    List<GiamGiaHoaDon> getAllGiamGiaHoaDonHoatDong();
}


