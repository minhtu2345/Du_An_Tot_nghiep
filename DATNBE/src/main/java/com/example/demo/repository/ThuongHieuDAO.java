package com.example.demo.repository;

import com.example.demo.entity.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.UUID;

public interface ThuongHieuDAO extends JpaRepository<ThuongHieu, UUID> {
    @Query("select p from ThuongHieu p where p.ma=?1")
    ThuongHieu findThuongHieuByMa(String ma);

    @Modifying
    @Transactional
    @Query("delete from ThuongHieu where ma=?1")
    void deleteThuongHieuByMa(String ma);
}
