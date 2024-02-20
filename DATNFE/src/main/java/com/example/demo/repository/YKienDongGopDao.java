package com.example.demo.repository;

import com.example.demo.entity.YKienDongGop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface YKienDongGopDao extends JpaRepository<YKienDongGop, UUID> {

    @Query("select ykien from YKienDongGop ykien")
    Page<YKienDongGop> phantrang(Pageable pageable);

    @Query("SELECT MAX(CAST(SUBSTRING(ykien.ma, 3, LENGTH(ykien.ma) - 2) AS int)) FROM  YKienDongGop ykien")
    Integer findMaxMaHoaDonNumber();

    default String generateNextMaKhachHang() {
        Integer maxMaNumber = findMaxMaHoaDonNumber();
        int nextNumber;

        if (maxMaNumber != null) {
            nextNumber = maxMaNumber + 1;
        } else {
            nextNumber = 1;
        }

        return "YK" + nextNumber;
    }
}
