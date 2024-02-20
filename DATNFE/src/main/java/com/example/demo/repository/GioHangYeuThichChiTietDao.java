package com.example.demo.repository;

import com.example.demo.entity.Giay;
import com.example.demo.entity.Gio_Hang_Yeu_Thich_Chi_Tiet;
import com.example.demo.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GioHangYeuThichChiTietDao extends JpaRepository<Gio_Hang_Yeu_Thich_Chi_Tiet, UUID> {
    @Query("select spyt from Gio_Hang_Yeu_Thich_Chi_Tiet spyt where spyt.sanPhamYeuThich.khachHang.ma = ?1")
    List<Gio_Hang_Yeu_Thich_Chi_Tiet> getSan_Pham_Yeu_ThichByMa(String ma);

    @Query("SELECT COUNT(ss) FROM Gio_Hang_Yeu_Thich_Chi_Tiet ss WHERE ss.giay.ma = :giayMa")
    Integer countYeuThichByGiayId(String giayMa);

    @Query("SELECT s FROM Gio_Hang_Yeu_Thich_Chi_Tiet s WHERE s.sanPhamYeuThich.khachHang = :khachHang AND s.giay = :giay")
    Gio_Hang_Yeu_Thich_Chi_Tiet findByKhachHangAndGiay(@Param("khachHang") KhachHang khachHang, @Param("giay") Giay giay);

    @Query("select spytct from Gio_Hang_Yeu_Thich_Chi_Tiet spytct where spytct.giay.ma = :giayMa")
    Gio_Hang_Yeu_Thich_Chi_Tiet getSan_Pham_Yeu_Thich_Chi_Tiet11Byma(String giayMa);

    @Query("select count(p.id) from Gio_Hang_Yeu_Thich_Chi_Tiet p where p.sanPhamYeuThich.khachHang.ma = ?1")
    Integer countGHSPYEByMaKH(String giayMa);
}
