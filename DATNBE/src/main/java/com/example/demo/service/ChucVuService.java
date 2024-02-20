package com.example.demo.service;

import com.example.demo.entity.ChucVu;
import com.example.demo.entity.PageDTO;

import java.util.List;

public interface ChucVuService {

    List<ChucVu> getList();

    PageDTO<ChucVu> getPage(Integer number);

    ChucVu getByMa(String ma);

    Boolean create(ChucVu chucVu);

    Boolean update(ChucVu chucVu);

    Boolean deleteByMa(String ma);
}
