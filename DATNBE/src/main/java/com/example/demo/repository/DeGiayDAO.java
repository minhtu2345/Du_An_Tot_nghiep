package com.example.demo.repository;

import com.example.demo.entity.DeGiay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.UUID;

public interface DeGiayDAO extends JpaRepository<DeGiay, UUID> {
    @Query("select p from DeGiay p where p.ma=?1")
    DeGiay findDeGiayByMa(String ma);

    @Modifying
    @Transactional
    @Query("delete from DeGiay where ma=?1")
    void deleteDeGiayByMa(String ma);
}
