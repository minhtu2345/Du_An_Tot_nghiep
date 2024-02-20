package com.example.demo.repository;

import com.example.demo.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.UUID;

public interface MauSacDAO extends JpaRepository<MauSac, UUID> {
    @Query("select p from MauSac p where p.ma=?1")
    MauSac findMauSacByMa(String ma);

    @Modifying
    @Transactional
    @Query("delete from MauSac where ma=?1")
    void deleteMauSacByMa(String ma);
}
