package com.example.demo.repository;

import com.example.demo.entity.GiamGiaHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface GGHDDAO extends JpaRepository<GiamGiaHoaDon, UUID> {
    @Query("select p from GiamGiaHoaDon p where p.so_luong>0 and p.trangthai =1 and p.ngay_ket_thuc>?1")
    List<GiamGiaHoaDon> getGiamGiaHoaDonByDk(Date date);
}
