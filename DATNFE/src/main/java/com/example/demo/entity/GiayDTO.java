package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GiayDTO {
    private String ten;
    private String kichCo;
    private int soLuong;
    private BigDecimal donGia;
    private String anhDau;
}
