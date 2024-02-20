package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
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
    private String madc;

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
    private Integer trangthai = 1;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;
}
