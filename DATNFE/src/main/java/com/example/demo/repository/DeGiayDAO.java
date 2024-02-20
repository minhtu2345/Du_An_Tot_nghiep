package com.example.demo.repository;

import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.DeGiay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DeGiayDAO extends JpaRepository<DeGiay,UUID> {
    DeGiay findDeGiayByMa(String ma);

    @Query(value = "SELECT cl" +
            " FROM DeGiay cl " +
            " WHERE cl.ma LIKE 'DG%' ORDER BY CAST(SUBSTRING(cl.ma, 3, LEN(cl.ma) - 2) AS int) DESC")
    List<DeGiay> findAllByOrderByMaDesc();

    @Query("SELECT MAX(CAST(SUBSTRING(hd.ma, 3, LENGTH(hd.ma) - 2) AS int)) FROM DeGiay hd")
    Integer findMaxMaDgNumber();

    default String generateNextMaDg() {
        Integer maxMaNumber = findMaxMaDgNumber();
        int nextNumber;

        if (maxMaNumber != null) {
            nextNumber = maxMaNumber + 1;
        } else {
            nextNumber = 1;
        }

        return "DG" + nextNumber;
    }
}
