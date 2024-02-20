package com.example.demo.repository;

import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.DeGiay;
import com.example.demo.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MauSacDAO extends JpaRepository<MauSac,UUID> {
    MauSac findMauSacByMa(String ma);

    @Query(value = "SELECT cl" +
            " FROM MauSac cl " +
            " WHERE cl.ma LIKE 'MS%' ORDER BY CAST(SUBSTRING(cl.ma, 3, LEN(cl.ma) - 2) AS int) DESC")
    List<MauSac> findAllByOrderByMaDesc();

    @Query("SELECT MAX(CAST(SUBSTRING(hd.ma, 3, LENGTH(hd.ma) - 2) AS int)) FROM MauSac hd")
    Integer findMaxMaMsNumber();

    default String generateNextMaMs() {
        Integer maxMaNumber = findMaxMaMsNumber();
        int nextNumber;

        if (maxMaNumber != null) {
            nextNumber = maxMaNumber + 1;
        } else {
            nextNumber = 1;
        }
        return "MS" + nextNumber;
    }
}
