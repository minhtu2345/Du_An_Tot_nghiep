package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Table(name = "giay_chi_tiet")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GiayChiTiet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER) // lấy thông tin của kích cỡ
    @JoinColumn(name = "id_kich_co")
    private KichCo kich_co;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_giay")
    private Giay giay;

    private Integer so_luong_ton;

    private Integer trangthai;
}
