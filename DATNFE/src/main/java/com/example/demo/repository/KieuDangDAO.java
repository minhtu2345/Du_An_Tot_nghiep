package com.example.demo.repository;

import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.DeGiay;
import com.example.demo.entity.KieuDang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface KieuDangDAO extends JpaRepository<KieuDang,UUID> {
    KieuDang findKieuDangByMa(String ma);

    @Query(value = "SELECT cl" +
            " FROM KieuDang cl " +
            " WHERE cl.ma LIKE 'KD%' ORDER BY CAST(SUBSTRING(cl.ma, 3, LEN(cl.ma) - 2) AS int) DESC")
    List<KieuDang> findAllByOrderByMaDesc();

    @Query("SELECT MAX(CAST(SUBSTRING(hd.ma, 3, LENGTH(hd.ma) - 2) AS int)) FROM KieuDang hd")
    Integer findMaxMaKdNumber();

    default String generateNextMaKd() {
        Integer maxMaNumber = findMaxMaKdNumber();
        int nextNumber;

        if (maxMaNumber != null) {
            nextNumber = maxMaNumber + 1;
        } else {
            nextNumber = 1;
        }

        return "KD" + nextNumber;
    }
}
