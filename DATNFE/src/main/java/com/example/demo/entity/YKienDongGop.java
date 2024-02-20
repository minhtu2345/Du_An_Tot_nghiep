package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "y_kien_dong_gop")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class YKienDongGop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  UUID id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten_nguoi_dg")
    private String ten;

    @Column(name = "so_dien_thoai")
    private String sdt;

    @Column(name = "email")
    private String email;

    @Column(name = "noi_dung")
    private String noidung;

    @Column(name = "trangthai")
    private Integer trangthai;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;
}
