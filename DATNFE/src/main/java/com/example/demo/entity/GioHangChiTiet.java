package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "gio_hang_chi_tiet")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "id_gio_hang")
    private GioHang gio_hang;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_giay_chi_tiet")
    private GiayChiTiet giay_chi_tiet;
    private Integer so_luong;
    private String ghi_chu;
    private Integer trangthai;
}
