package com.example.demo.repository;

import com.example.demo.entity.KieuDang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.UUID;

public interface KieuDangDAO extends JpaRepository<KieuDang, UUID> {
    @Query("select p from KieuDang p where p.ma=?1")
    KieuDang findKieuDangByMa(String ma);

    @Modifying
    @Transactional
    @Query("delete from KieuDang where ma=?1")
    void deleteKieuDangByMa(String ma);
}
