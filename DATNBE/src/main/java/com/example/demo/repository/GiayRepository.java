package com.example.demo.repository;

import com.example.demo.entity.Giay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GiayRepository extends JpaRepository<Giay, UUID> {

    @Query(value = "delete from giay where id=?", nativeQuery = true)
    Giay deleteGiayById(UUID id);
}
