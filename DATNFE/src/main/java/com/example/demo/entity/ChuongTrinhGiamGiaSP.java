package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chuong_tring_giam_gia_san_pham")
public class ChuongTrinhGiamGiaSP implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID idKhuyenMai;

    @Column(name = "ma")
    private String maKhuyenMai;

    @Column(name = "ten")
    private String tenKhuyenMai;

    @Column(name = "phan_tram_giam")
    private Integer phanTramGiam;

    @Column(name = "ngay_bat_dau")
    private Date ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private Date ngayKetThuc;

    @Column(name = "trangthai")
    private Integer trangThai = 1;


//    @OneToMany(mappedBy = "cuaHang", fetch = FetchType.EAGER)
//    private List<NhanVien> listNV;

}
