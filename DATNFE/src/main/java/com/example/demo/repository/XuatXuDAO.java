package com.example.demo.repository;

import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.DeGiay;
import com.example.demo.entity.XuatXu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface XuatXuDAO extends JpaRepository<XuatXu,UUID> {
    XuatXu findXuatXuByMa(String ma);

    @Query(value = "SELECT cl" +
            " FROM XuatXu cl " +
            " WHERE cl.ma LIKE 'XS%' ORDER BY CAST(SUBSTRING(cl.ma, 3, LEN(cl.ma) - 2) AS int) DESC")
    List<XuatXu> findAllByOrderByMaDesc();

    @Query("SELECT MAX(CAST(SUBSTRING(hd.ma, 3, LENGTH(hd.ma) - 2) AS int)) FROM XuatXu hd")
    Integer findMaxMaXsNumber();

    default String generateNextMaXs() {
        Integer maxMaNumber = findMaxMaXsNumber();
        int nextNumber;

        if (maxMaNumber != null) {
            nextNumber = maxMaNumber + 1;
        } else {
            nextNumber = 1;
        }

        return "XS" + nextNumber;
    }
}
