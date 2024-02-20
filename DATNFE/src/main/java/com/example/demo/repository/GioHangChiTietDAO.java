package com.example.demo.repository;

import com.example.demo.entity.GiayChiTiet;
import com.example.demo.entity.GioHangChiTiet;
import com.example.demo.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.UUID;

public interface GioHangChiTietDAO extends JpaRepository<GioHangChiTiet, UUID> {
    @Modifying
    @Transactional
    @Query("update GioHangChiTiet set so_luong=?1 where id=?2 ")
    Boolean updateGHCT(Integer solyong,UUID uuid);
    @Query("SELECT COUNT(p.id) FROM GioHangChiTiet p where p.gio_hang.khach_hang.ma=?1")
    Integer countGH(String maKH);
    @Modifying
    @Transactional
    @Query("delete from GioHangChiTiet p where p.id=?1")
    void deleteSPInGHCT(UUID idgh);
}
