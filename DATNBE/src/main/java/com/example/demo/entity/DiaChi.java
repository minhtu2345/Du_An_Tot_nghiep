package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dia_chi")
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten_dia_chi")
    private String tendiachi;

    @Column(name = "xa")
    private String xa;

    @Column(name = "huyen")
    private String huyen;

    @Column(name = "thanh_pho")
    private String thanhpho;

    @Column(name = "ten_nguoi_nhan")
    private String ten_nguoi_nhan;

    @Column(name = "sdt_nguoi_nhan")
    private String sdt_nguoi_nhan;

    @Column(name = "trangthai")
    private Integer trangthai;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;
}
