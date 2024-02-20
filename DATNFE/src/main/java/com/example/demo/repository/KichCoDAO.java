package com.example.demo.repository;

import com.example.demo.entity.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KichCoDAO extends JpaRepository<KichCo, UUID> {
}
