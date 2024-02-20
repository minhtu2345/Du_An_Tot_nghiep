package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Table(name = "nhan_vien")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "ma",unique = true)
    private String ma;

    @Column(name = "anh")
    private String anh;

    @NotBlank
    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "ngay_sinh")
    private String ngaySinh;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "xa")
    private String xa;

    @Column(name = "huyen")
    private String huyen;

    @Column(name = "thanh_pho")
    private String thanhPho;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "ngay_vao_lam")
    private String ngayVaoLam;

    @Column(name = "ngay_nghi_viec")
    private String ngayNghiViec;

    @Column(name = "trangthai")
    private Integer trangThai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_chuc_vu")
    private ChucVu chucVu;

    public NhanVien convertEmptyToNull() {
        return NhanVien.builder()
                .ma(this.getMa().trim().isEmpty()?null:this.getMa().trim())
                .hoTen(this.getHoTen().trim().isEmpty()?"":this.getHoTen().trim())
                .ngaySinh(this.getNgaySinh().trim().isEmpty()?null:this.getNgaySinh().trim())
                .diaChi(this.getDiaChi().trim().isEmpty()?"":this.getDiaChi().trim())
                .thanhPho(this.getThanhPho().trim().isEmpty()?"":this.getThanhPho().trim())
                .sdt(this.getSdt().trim().isEmpty()?"":this.getSdt().trim())
                .email(this.getEmail().trim().isEmpty()?"":this.getEmail().trim())
                .ngayVaoLam(this.getNgayVaoLam().trim().isEmpty()?null:this.getNgayVaoLam().trim())
                .ngayNghiViec(this.getNgayNghiViec().trim().isEmpty()?null:this.getNgayNghiViec().trim())
                .trangThai(this.getTrangThai()==null?null:this.getTrangThai())
                .chucVu(this.getChucVu().getId()==null?null:ChucVu.builder().id(this.getChucVu().getId()).build())
                .build();
    }

}
