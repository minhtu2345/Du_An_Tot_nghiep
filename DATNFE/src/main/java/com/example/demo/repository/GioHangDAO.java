package com.example.demo.repository;

import com.example.demo.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GioHangDAO extends JpaRepository<GioHang, UUID> {
    @Query("SELECT MAX(CAST(SUBSTRING(hd.ma, 3, LENGTH(hd.ma) - 2) AS int)) FROM GioHang hd")
    Integer findMaxMaGioHangNumber();

    default String generateNextMaGioHang() {
        Integer maxMaNumber = findMaxMaGioHangNumber();
        int nextNumber;

        if (maxMaNumber != null) {
            nextNumber = maxMaNumber + 1;
        } else {
            nextNumber = 1;
        }

        return "GH" + nextNumber;
    }
    @Query("select p from GioHang p where p.khach_hang.ma=?1")
    GioHang getGioHangByMaKH(String makh);
}
