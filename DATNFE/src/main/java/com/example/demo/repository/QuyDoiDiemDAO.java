package com.example.demo.repository;

import com.example.demo.entity.QuyDoiDiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface QuyDoiDiemDAO extends JpaRepository<QuyDoiDiem, UUID> {
    @Query("select p from QuyDoiDiem p where p.trangthai = 1")
    QuyDoiDiem getQuyDoiDiemByTT1();
}
