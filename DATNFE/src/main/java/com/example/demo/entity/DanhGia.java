package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Table(name = "danh_gia")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DanhGia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_giay", referencedColumnName = "id")
    private Giay giay;

    @Column(name = "ten_nguoi_danh_gia")
    private String tenNguoiDanhGia;

    @Column(name = "sao")
    private Integer sao;

    @Column(name = "noi_dung")
    private String noiDung;

    @Column(name = "trang_thai")
    private Integer trangThai;
}
