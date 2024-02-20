package com.example.demo.repository;

import com.example.demo.entity.LichSuTieuDiem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LichSuTieuDiemDAO extends JpaRepository<LichSuTieuDiem, UUID> {
}
