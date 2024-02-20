package com.example.demo.repository;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.NhanVien;
import com.example.demo.dto.NhanVienDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NhanVienDAO extends JpaRepository<NhanVien, UUID> {

    @Query(value = "select nv from NhanVien nv where nv.ma=?1")
    NhanVien findNvByMaNv(String maNv);

    @Query(value = "SELECT \n" +
            "            (SELECT COUNT(*) FROM nhan_vien WHERE trangthai=1) AS nvHienTai,\n" +
            "                (SELECT COUNT(*) FROM nhan_vien WHERE MONTH(ngay_vao_lam) = MONTH(GETDATE()) AND YEAR(ngay_vao_lam) = YEAR(GETDATE())) AS nvMoiTrongThang,\n" +
            "                (SELECT COUNT(*) FROM nhan_vien WHERE MONTH(ngay_nghi_viec) = MONTH(GETDATE()) AND YEAR(ngay_nghi_viec) = YEAR(GETDATE())) AS nvNghiTrongThang",
            nativeQuery = true)
    NhanVienDto getNhanVienDto();

    @Query(value = "SELECT nv FROM NhanVien nv WHERE " +
            "(:ma IS NULL OR nv.ma = :ma) AND " +
            "((:data IS NULL OR nv.sdt LIKE %:data%) OR " +
            "(:data IS NULL OR nv.email LIKE %:data%) OR " +
            "(:data IS NULL OR nv.hoTen LIKE %:data%)) AND " +
            "(:maCv IS NULL OR nv.chucVu.ma = :maCv)")
    Page<NhanVien> findNhanVien(String ma, String data, String maCv,
                                Pageable pageable);


    Page<NhanVien> getAllByTrangThai(Integer tt,
                                     Pageable pageable);

    @Query("select p from NhanVien p where p.email=?1")
    NhanVien getNVByEmail(String email);

    //lay ra so cua manv co so lon nhat
    @Query(value = "SELECT TOP 1 SUBSTRING(ma, 3, LEN(ma) - 2) " +
            "FROM nhan_vien " +
            "WHERE ma LIKE 'NV%' ORDER BY CAST(SUBSTRING(ma, 3, LEN(ma) - 2) AS INT) DESC",nativeQuery = true)
    Integer getMaMax();
}
