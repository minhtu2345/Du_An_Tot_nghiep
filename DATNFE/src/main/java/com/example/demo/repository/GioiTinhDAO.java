package com.example.demo.repository;

import com.example.demo.entity.GioiTinh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GioiTinhDAO extends JpaRepository<GioiTinh,UUID> {
    GioiTinh findGioiTinhByMa(String ma);
}
