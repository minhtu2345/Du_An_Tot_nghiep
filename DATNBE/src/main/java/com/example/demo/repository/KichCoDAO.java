package com.example.demo.repository;

import com.example.demo.entity.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.UUID;

public interface KichCoDAO extends JpaRepository<KichCo, UUID> {
    @Query("select p from KichCo p where p.ma=?1")
    KichCo findKichCoByMa(String ma);

    @Modifying
    @Transactional
    @Query("delete from KichCo where ma=?1")
    void deleteKichCoByMa(String ma);
}
