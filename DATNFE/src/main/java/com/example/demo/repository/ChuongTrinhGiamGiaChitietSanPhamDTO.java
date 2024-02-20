package com.example.demo.repository;

import com.example.demo.entity.ChuongTrinhGiamGiaChiTietSP;
import com.example.demo.entity.ChuongTrinhGiamGiaSP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChuongTrinhGiamGiaChitietSanPhamDTO extends JpaRepository<ChuongTrinhGiamGiaChiTietSP, UUID> {

//    @Query("select ctggctsp from ChuongTrinhGiamGiaChiTietSP  ctggctsp where ctggctsp.chuongTrinhGiamGiaSP.idKhuyenMai like ?1")
//    List<ChuongTrinhGiamGiaChiTietSP> getListSPApDungByIdKhuyenMai(UUID idKhuyenMai);

    @Query(value = "select * from chuong_trinh_giam_gia_chi_tiet_san_pham   where id_chuong_trinh_giam_gia = ?", nativeQuery = true)
    List<ChuongTrinhGiamGiaChiTietSP> getListSPApDungByIdKhuyenMai(UUID id);

    @Query(value = "select*from chuong_trinh_giam_gia_chi_tiet_san_pham ctkm\n" +
            "join chuong_tring_giam_gia_san_pham km on ctkm.id_chuong_trinh_giam_gia = km.id\n" +
            "where km.id=?", nativeQuery = true)
    List<ChuongTrinhGiamGiaChiTietSP> listGiayByIdKM(UUID idKhuyenMai);


    @Query(value = "delete from chuong_trinh_giam_gia_chi_tiet_san_pham " +
            "where id_chuong_trinh_giam_gia=?1 and id_giay=?2", nativeQuery = true)
    ChuongTrinhGiamGiaChiTietSP deleteByTwoId(UUID idKm, UUID idGiay);

    @Query(value = "insert into chuong_trinh_giam_gia_chi_tiet_san_pham (id_giay, id_chuong_trinh_giam_gia, so_tien_da_giam, trangthai)" +
            "values(?1,?2,?3,1)", nativeQuery = true)
    void createSPApdung(String idGiay, String idKm, BigDecimal soTienGiam);

    @Query(value = "select * from chuong_trinh_giam_gia_chi_tiet_san_pham " +
            "where id_chuong_trinh_giam_gia=?1 and id_giay=?2", nativeQuery = true)
    ChuongTrinhGiamGiaChiTietSP selectByTwoId(UUID idKm, UUID idGiay);

    @Query(value = "select * from chuong_trinh_giam_gia_chi_tiet_san_pham " +
            "where id_chuong_trinh_giam_gia=?1 and id_giay=?2", nativeQuery = true)
    ChuongTrinhGiamGiaChiTietSP updateByTwoId(UUID idKm, UUID idGiay);

}
