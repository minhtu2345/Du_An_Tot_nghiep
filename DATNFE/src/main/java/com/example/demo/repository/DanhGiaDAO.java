package com.example.demo.repository;

import com.example.demo.entity.DanhGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Repository
public interface DanhGiaDAO extends JpaRepository<DanhGia, UUID> {

    @Query("select dg from DanhGia dg where dg.trangThai=0")
    Page<DanhGia> findDanhGiasByTrangThai0(Pageable pageable);

    @Query("select dg from DanhGia dg where dg.giay.ma=?1 and dg.trangThai=1")
    Page<DanhGia> findDanhGiasByMaSpAndTt(String masp,Pageable pageable);

    @Query("update DanhGia set trangThai=1")
    @Modifying
    @Transactional
    void duyetAll();

    @Query("update DanhGia set trangThai=1 where id=?1")
    @Modifying
    @Transactional
    void duyetOne(UUID id);

    @Query("select count(dg) from DanhGia dg where dg.giay.ma=?1 and dg.trangThai=1")
    Integer countGiayByMaGiayAndTt(String ma);
}
