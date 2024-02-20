package com.example.demo.repository;

import com.example.demo.entity.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface ChucVuDAO extends JpaRepository<ChucVu, UUID> {

    //    @Query("select cv from ChucVu cv where cv.ma=?1")
    ChucVu findByMa(String ma);

    @Transactional
    @Modifying
    @Query("delete from ChucVu cv where cv.ma=?1")
    void deleteByMa(String ma);
}
