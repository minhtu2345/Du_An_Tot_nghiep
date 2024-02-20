package com.example.demo.repository;

import com.example.demo.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface KhachHangRepo extends JpaRepository<KhachHang, UUID> {

    @Query(value = "select kh from KhachHang kh where kh.ma = ?1")
    KhachHang getkhachhanByma(String ma);

    @Query("select kh from KhachHang  kh where kh.hoten like ?1 or kh.sdt like ?1 or  kh.ma like ?1")
    Page<KhachHang> search( String keyword, Pageable pageable);
}
