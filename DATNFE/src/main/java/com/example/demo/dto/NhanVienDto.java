package com.example.demo.dto;

import lombok.*;


public interface NhanVienDto {

    //số lượng nhân viên hiện tại
    Integer getNvHienTai();

    //số lượng nhân viên mới trong tháng
    Integer getNvMoiTrongThang();

    //số lượng nhân viên nghỉ trong tháng
    Integer getNvNghiTrongThang();
}
