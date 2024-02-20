package com.example.demo.entity;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Table(name = "anh_giay")
@Entity
@Data
public class Anh implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String ten_url;
    @ManyToOne
    @JoinColumn(name = "id_giay")
    private Giay giay;
    private Integer trangthai;
}
