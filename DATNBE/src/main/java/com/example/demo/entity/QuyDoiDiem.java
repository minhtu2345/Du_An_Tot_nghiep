package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "quy_doi_diem")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuyDoiDiem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private BigDecimal so_tien_tuong_ung;
    private Integer so_diem_tuong_ung;
    private Integer trangthai;
}
