package com.example.demo.service;

import com.example.demo.entity.NhanVien;
import com.example.demo.entity.PageDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NhanVienService {

    List<NhanVien> getList();

    PageDTO<NhanVien> getPage(Integer number);

    NhanVien getByMa(String ma);

    Boolean create(NhanVien nhanVien);

    Boolean update(NhanVien nhanVien);

    Boolean deleteByMa(String ma);
}
