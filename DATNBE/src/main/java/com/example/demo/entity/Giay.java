package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Table(name = "giay")
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Giay implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String ma;

    private String ten;

    @ManyToOne
    @JoinColumn(name = "id_chat_lieu")
    private ChatLieu chat_lieu;

    @ManyToOne
    @JoinColumn(name = "id_de_giay")
    private DeGiay de_giay;

    @ManyToOne
    @JoinColumn(name = "id_gioi_tinh")
    private GioiTinh gioi_tinh;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
    private MauSac mau_sac;
    @ManyToOne
    @JoinColumn(name = "id_xuat_xu")
    private XuatXu xuat_xu;
    @ManyToOne
    @JoinColumn(name = "id_kieu_dang")
    private KieuDang kieu_dang;

    @ManyToOne
    @JoinColumn(name = "id_thuong_hieu")
    private ThuongHieu thuong_hieu;

    @JsonIgnore
    @OneToMany(mappedBy = "giay", fetch = FetchType.EAGER) // lấy hết các thông tin
    Set<Anh> anhs;

    @JsonIgnore
    @OneToMany(mappedBy = "giay",fetch = FetchType.EAGER)
    Set<GiayChiTiet> giayChiTiets;

    private String mota;

    private BigDecimal gianhap; 

    private BigDecimal giaban;

    @Column(name = "gia_sau_khuyen_mai")
    private BigDecimal gia_sau_khuyen_mai;
    private Integer do_hot;
    private LocalDate ngay_nhap;
    private Integer trangthai;
//    @OneToMany(mappedBy = "giay",fetch = FetchType.EAGER)
//    Set<ChuongTrinhGiamGiaChiTietSP> chuongTrinhGiamGiaChiTietSP;
    public String getAnhDau(Set<Anh> anhs1){
        List<Anh> list = new ArrayList<Anh>(anhs1);
        return list.get(0).getTen_url();
    }
}
