package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "chuong_trinh_giam_gia_chi_tiet_hoa_don")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GiamGiaChiTietHoaDon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_hoa_don")
    private HoaDon hd;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_chuong_trinh_giam_gia_hoa_don")
    private GiamGiaHoaDon gghd;

    private BigDecimal tong_tien;

    private BigDecimal so_tien_da_giam;

    private Integer trangthai;
}
