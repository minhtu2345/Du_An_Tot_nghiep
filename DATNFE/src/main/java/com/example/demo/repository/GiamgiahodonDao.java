package com.example.demo.repository;

import com.example.demo.entity.GiamGiaHoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GiamgiahodonDao extends JpaRepository<GiamGiaHoaDon, UUID> {

    @Query("select p from GiamGiaHoaDon p where p.ten like ?1")
    Page<GiamGiaHoaDon> getSearchGiamgiahodonDao(String keyword, Pageable pageable);
}
