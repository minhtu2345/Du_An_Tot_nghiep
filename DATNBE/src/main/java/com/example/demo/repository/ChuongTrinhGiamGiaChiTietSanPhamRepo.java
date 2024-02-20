package com.example.demo.repository;

import com.example.demo.entity.ChuongTrinhGiamGiaChiTietSP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChuongTrinhGiamGiaChiTietSanPhamRepo extends JpaRepository<ChuongTrinhGiamGiaChiTietSP, UUID> {

//    @Query(value = "select km.ma as 'Mã KM', km.ten,km.ngay_bat_dau, km.ngay_ket_thuc, g.ma, g.ten, km.trangthai\n" +
//            "from chuong_tring_giam_gia_san_pham km join chuong_trinh_giam_gia_chi_tiet_san_pham ctkm \n" +
//            "on km.id = ctkm.id_chuong_trinh_giam_gia join giay g on g.id = ctkm.id_giay WHERE ctkm.id_chuong_trinh_giam_gia=?",  nativeQuery = true)
//    List<ChuongTrinhGiamGiaChiTietSP> getDetailVoucher(UUID );

    @Query(value = "select * from chuong_trinh_giam_gia_chi_tiet_san_pham ctkm join giay g on ctkm.id_giay = g.id \n" +
            "where id_chuong_trinh_giam_gia =?", nativeQuery = true)
    List<ChuongTrinhGiamGiaChiTietSP> findAllSPApdung(UUID id_chuong_trinh_giam_gia);
}
