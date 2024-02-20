package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chuong_trinh_giam_gia_chi_tiet_san_pham")
public class ChuongTrinhGiamGiaChiTietSP implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_giay", referencedColumnName = "id")
    private Giay giay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_chuong_trinh_giam_gia", referencedColumnName = "id")
    private ChuongTrinhGiamGiaSP chuongTrinhGiamGiaSP;

    @Column(name = "so_tien_da_giam")
    private BigDecimal soTienDaGiam;

    @Column(name = "trangthai")
    private Integer trangThai;


}
