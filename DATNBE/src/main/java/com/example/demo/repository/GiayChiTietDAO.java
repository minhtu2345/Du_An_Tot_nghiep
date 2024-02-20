package com.example.demo.repository;

import com.example.demo.entity.GiayChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GiayChiTietDAO extends JpaRepository<GiayChiTiet, UUID> {
    @Query("select p from GiayChiTiet p where p.giay.ma=?1")
    List<GiayChiTiet> getAllByMaGiay(String ma);
}
