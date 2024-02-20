package com.example.demo.repository;

import com.example.demo.entity.Giay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GiayDAO extends JpaRepository<Giay, UUID> {
    @Query("select p from Giay p where p.ma = ?1")
    Giay getGiayByMa(String ma);
}
