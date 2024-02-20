package com.example.demo.repository;

import com.example.demo.entity.GiayChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface GiayChiTietDAO extends JpaRepository<GiayChiTiet, UUID> {
    @Query("select p from GiayChiTiet p where p.giay.ma=?1")
    List<GiayChiTiet> getAllByMaGiay(String ma);
    @Query("select p from GiayChiTiet p where p.giay.ma=?1 and p.kich_co.ten=?2")
    GiayChiTiet getAllByMaGiayChiTiet(String ma,String size);
    @Query("select p from GiayChiTiet p where p.giay.ma=?1 and p.kich_co.ma=?2")
    GiayChiTiet getAllByMaGiayAndSize(String ma,String size);

    @Query("select p from GiayChiTiet p where p.giay.ten like ?1 and p.kich_co.ten like ?2 and p.so_luong_ton > 0")
    Page<GiayChiTiet> getSearchsanpham(String keyword, String sỉze, Pageable pageable);

    @Query("SELECT h FROM GiayChiTiet h")
    Page<GiayChiTiet> findAllWithPagination(Pageable pageable);

    @Query("select p from GiayChiTiet p where p.giay.ten like ?1 and (?2 is null or p.giay.gia_sau_khuyen_mai > ?2) and (?3 is null or p.giay.gia_sau_khuyen_mai < ?3) " +
            "and p.giay.thuong_hieu.ten like ?4 and p.kich_co.ten like ?5 and p.giay.chat_lieu.ten like ?6 and p.giay.xuat_xu.ten like ?7 " +
            "and p.giay.mau_sac.ten like ?8 and p.giay.gioi_tinh.ten like ?9 and p.giay.kieu_dang.ten like ?10 and p.giay.de_giay.ten like ?11 ")
    List<GiayChiTiet> getSearchsanphamByTT(String tensp, BigDecimal giabnmin, BigDecimal giabnmax, String thuong_hieu,
                                           String kich_co,String chat_lieu,String xuat_xu,String mau_sac,
                                           String gioi_tinh,String kieu_dang,String de_giay);
}
