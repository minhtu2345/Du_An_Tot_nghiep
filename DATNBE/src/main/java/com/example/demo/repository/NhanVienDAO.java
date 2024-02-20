package com.example.demo.repository;

import com.example.demo.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface NhanVienDAO extends JpaRepository<NhanVien, UUID> {

    NhanVien findByMa(String ma);

    @Transactional
    @Modifying
    @Query("delete from NhanVien nv where nv.ma=?1")
    void deleteByMa(String ma);
}