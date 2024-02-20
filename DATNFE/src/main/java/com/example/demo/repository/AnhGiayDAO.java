package com.example.demo.repository;

import com.example.demo.entity.Anh;
import com.example.demo.entity.Giay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AnhGiayDAO extends JpaRepository<Anh, UUID> {
    @Query("select p from Anh p where p.giay.ma=?1")
    List<Anh> getAnhByMaGiay(String ma);
}
