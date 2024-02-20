package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Table(name = "gio_hang")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String ma;
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khach_hang;
    private LocalDate ngay_tao;
    private LocalDate ngay_cap_nhap;
    private String ghi_chu;
    private Integer trangthai;
    @OneToMany(mappedBy = "gio_hang",fetch = FetchType.EAGER)
    Set<GioHangChiTiet> gioHangChiTiets;

    public List<GioHangChiTiet> getListGHCT(Set<GioHangChiTiet> gioHangChiTiets){
        List<GioHangChiTiet> list = new ArrayList<GioHangChiTiet>(gioHangChiTiets);
        list.sort(Comparator.comparing(GioHangChiTiet::getId));
        return list;
    }
}
