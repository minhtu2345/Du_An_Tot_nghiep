package com.example.demo.repository;

import com.example.demo.entity.Anh;
import com.example.demo.entity.DeGiay;
import com.example.demo.entity.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ThuongHieuDAO extends JpaRepository<ThuongHieu, UUID> {
    ThuongHieu findThuongHieuByMa(String ma);

    @Query(value = "SELECT cl" +
            " FROM ThuongHieu cl " +
            " WHERE cl.ma LIKE 'TH%' ORDER BY CAST(SUBSTRING(cl.ma, 3, LEN(cl.ma) - 2) AS int) DESC")
    List<ThuongHieu> findAllByOrderByMaDesc();

    @Query("SELECT MAX(CAST(SUBSTRING(hd.ma, 3, LENGTH(hd.ma) - 2) AS int)) FROM ThuongHieu hd")
    Integer findMaxMaThNumber();

    default String generateNextMaTh() {
        Integer maxMaNumber = findMaxMaThNumber();
        int nextNumber;

        if (maxMaNumber != null) {
            nextNumber = maxMaNumber + 1;
        } else {
            nextNumber = 1;
        }

        return "TH" + nextNumber;
    }
}
