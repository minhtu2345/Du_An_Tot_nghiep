package com.example.demo.repository;

import com.example.demo.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface DiachiDao extends JpaRepository<DiaChi, UUID> {
    @Query("select p from DiaChi p where p.khachHang.ma = ?1")
    List<DiaChi> getAllByMaDiaChi(String ma);
    @Query("select p from DiaChi p where p.khachHang.ma = ?1 order by p.trangthai desc ")
    List<DiaChi> getAllByMaDiaChiSortTT(String ma);
    @Query("select p from DiaChi p where p.khachHang.id = ?1")
    DiaChi GetKhachhangByid(UUID id);
    
    @Query("select p from DiaChi p where p.khachHang.ma = ?1")
    List<DiaChi> getdiachibyma(String ma);

    @Query("select p from DiaChi p where p.madc = ?1")
    DiaChi getDiachiByma(String ma);

    @Query("select p from DiaChi p where p.tendiachi = ?1 and p.khachHang.id = ?2")
    DiaChi getDiachiBytendiachi(String tendiachi,UUID idKhachHang);

    @Query("select p from DiaChi p where p.khachHang.ma=?1 and p.trangthai = 0")
    DiaChi getDiachiByTrangThai0(String ma);
    @Modifying
    @Transactional
    @Query("update DiaChi set trangthai=?1 where khachHang.id=?2")
    void updateTtDiaChiByIdKh(Integer tt,UUID idKh);

    @Modifying
    @Transactional
    @Query("update DiaChi set trangthai=?1 where madc=?2")
    void updateTtDiaChiByMaDc(Integer tt,String madc);

    @Query("select p from DiaChi p where p.khachHang.ma = ?1 and p.trangthai = 1")
    DiaChi getDiaChiByKhachHangMaAndTrangthai(String maKH);

    //lay ra so cua madc co so lon nhat
    @Query(value = "SELECT TOP 1 SUBSTRING(ma, 3, LEN(ma) - 2) " +
            "FROM dia_chi " +
            "WHERE ma LIKE 'DC%' ORDER BY CAST(SUBSTRING(ma, 3, LEN(ma) - 2) AS INT) DESC",nativeQuery = true)
    Integer getMaMax();


    @Query("SELECT MAX(CAST(SUBSTRING(dc.madc, 3, LENGTH(dc.madc) - 2) AS int)) FROM DiaChi dc")
    Integer findMaxMaHoaDonNumber();

    default String generateNextDiaChi() {
        Integer maxMaNumber = findMaxMaHoaDonNumber();
        int nextNumber;

        if (maxMaNumber != null) {
            nextNumber = maxMaNumber + 1;
        } else {
            nextNumber = 1;
        }

        return "DC" + nextNumber;
    }

}
