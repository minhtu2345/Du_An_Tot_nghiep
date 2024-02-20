package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "lich_su_tich_diem")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LichSuTieuDiem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Integer so_diem_da_dung;
    private LocalDate ngay_su_dung;
    private Integer so_diem_cong;
    private Integer trangthai;
    @ManyToOne
    @JoinColumn(name = "id_vi_diem")
    private ViDiem vi_diem;
    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    private HoaDon hoa_don;
    @ManyToOne
    @JoinColumn(name = "id_quy_doi")
    private QuyDoiDiem quy_doi_diem;
}
