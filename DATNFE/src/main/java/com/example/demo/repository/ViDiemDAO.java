package com.example.demo.repository;

import com.example.demo.entity.ViDiem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ViDiemDAO extends JpaRepository<ViDiem, UUID> {
    @Query("select p from ViDiem p where p.khach_hang.ma=?1")
    ViDiem getViDiemByMaKH(String maKH);
}
