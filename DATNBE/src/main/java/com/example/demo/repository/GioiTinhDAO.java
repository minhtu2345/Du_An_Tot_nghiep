package com.example.demo.repository;

import com.example.demo.entity.GioiTinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.UUID;

public interface GioiTinhDAO extends JpaRepository<GioiTinh, UUID> {
    @Query("select p from GioiTinh p where p.ma=?1")
    GioiTinh findGioiTinhByMa(String ma);

    @Modifying
    @Transactional
    @Query("delete from GioiTinh where ma=?1")
    void deleteGioiTinhByMa(String ma);
}
