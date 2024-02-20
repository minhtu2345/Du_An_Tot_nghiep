package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Table(name = "hoa_don")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoaDon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String ma;

    private LocalDate ngay_tao;

    private LocalDate ngay_thanh_toan;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    private String mo_ta;

    private String ten_nguoi_nhan;

    private String sdt_nguoi_nhan;

    private BigDecimal tong_tien;

    private Integer trangthai;

    private String dia_chi;

    private Integer hinh_thuc_mua;

    private Integer hinh_thuc_thanh_toan;

    private BigDecimal so_tien_giam;

    private BigDecimal phi_ship;

    private Integer so_diem_su_dung;

    private BigDecimal so_tien_quy_doi;

    @OneToMany(mappedBy = "hd", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<GiamGiaChiTietHoaDon> list1;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<HoaDonChiTiet> listHdct;

    public String convertTrangThai() {
        switch (this.trangthai) {
            case 1:
                return "Chờ xác nhận";
            case 2:
                return "Đang chuẩn bị";
            case 3:
                return "Đang giao hàng";
            case 4:
                return "Hoàn thành";
            case 5:
                return "Đã hủy";
            case 6:
                return "Trả hàng/Hoàn tiền";
            default:
                return null;
        }
    }
}
