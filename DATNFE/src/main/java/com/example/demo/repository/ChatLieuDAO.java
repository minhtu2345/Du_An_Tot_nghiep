package com.example.demo.repository;

import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.Giay;
import com.example.demo.entity.GioiTinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ChatLieuDAO extends JpaRepository<ChatLieu,UUID> {
    ChatLieu findChatLieuByMa(String ma);
    @Query(value = "SELECT cl" +
            " FROM ChatLieu cl " +
            " WHERE cl.ma LIKE 'CL%' ORDER BY CAST(SUBSTRING(cl.ma, 3, LEN(cl.ma) - 2) AS int) DESC")
    List<ChatLieu> findAllByOrderByMaDesc();

    @Query("SELECT MAX(CAST(SUBSTRING(hd.ma, 3, LENGTH(hd.ma) - 2) AS int)) FROM ChatLieu hd")
    Integer findMaxMaClNumber();

    default String generateNextMaCl() {
        Integer maxMaNumber = findMaxMaClNumber();
        int nextNumber;

        if (maxMaNumber != null) {
            nextNumber = maxMaNumber + 1;
        } else {
            nextNumber = 1;
        }

        return "CL" + nextNumber;
    }
}
