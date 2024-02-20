package com.example.demo.repository;

import com.example.demo.entity.XuatXu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.UUID;

public interface XuatXuDAO extends JpaRepository<XuatXu, UUID> {
    @Query("select p from XuatXu p where p.ma=?1")
    XuatXu findXuatXuByMa(String ma);

    @Modifying
    @Transactional
    @Query("delete from XuatXu where ma=?1")
    void deleteXuatXuByMa(String ma);
}
