package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "vi_diem")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ViDiem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @OneToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khach_hang;
    private Integer tong_diem;
    private Integer so_diem_da_dung;
    private Integer so_diem_da_cong;
    private Integer trangthai;
}
