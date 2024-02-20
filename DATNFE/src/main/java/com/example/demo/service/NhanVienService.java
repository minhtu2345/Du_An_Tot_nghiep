package com.example.demo.service;

import com.example.demo.dto.NhanVienDto;
import com.example.demo.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NhanVienService {

    Boolean deleteByMa(String ma);

    Boolean create(NhanVien nhanVien);

    Boolean update(NhanVien nhanVien);

    NhanVien findNvByMaNv(String ma);

    List<NhanVien> getAll();

    //tim theo ma hoac (ten hoac sdt hoac email) va chuc vu
    Page<NhanVien> findNhanVien(Optional<String> ma, Optional<String> data, Optional<String> idCv, Integer number);

    Page<NhanVien> getPageByTrangThai(Integer tt, Integer number);

    //tra ve nhanviendto co field so luong nhan vien hien tai, so luong nhan vien moi,nghi trong thang
    NhanVienDto getNhanVienView();

    String genMaNvMoi();
}
