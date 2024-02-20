package com.example.demo.repository;

import com.example.demo.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonDAO extends JpaRepository<HoaDon, UUID> {

    @Query(value = "select hd from HoaDon hd where hd.nhanVien.ma=?1 and hd.trangthai=1")
    Page<HoaDon> findHdByMaNv(String maNv, Pageable pageable);

    @Query(value = "select hd from HoaDon hd where hd.khachHang.ma=?1 and (?2 IS NULL OR hd.trangthai=?2)")
    Page<HoaDon> findHdByMaKhAndTt(String maKh, Integer tt, Pageable pageable);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.trangthai = 0 " +
            "AND (hd.ma LIKE %:keyword% OR hd.khachHang.hoten LIKE %:keyword%)" +
            "and hd.trangthai = 0")
    Page<HoaDon> searchHoaDonByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("select p from HoaDon p where p.khachHang.ma=?1")
    List<HoaDon> getHoaDonByMaKh(String makh);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.ma LIKE %:keyword% AND hd.trangthai = :trangThai")
    Page<HoaDon> searchHoaDon(@Param("keyword") String keyword, @Param("trangThai") Integer trangThai, Pageable pageable);

    @Query("SELECT MAX(hd.ma) FROM HoaDon hd")
    String findHighestMaHoaDon();

    void deleteByMa(String ma);

    @Query("SELECT MAX(CAST(SUBSTRING(hd.ma, 3, LENGTH(hd.ma) - 2) AS int)) FROM HoaDon hd")
    Integer findMaxMaHoaDonNumber();

    default String generateNextMaHoaDon() {
        Integer maxMaNumber = findMaxMaHoaDonNumber();
        int nextNumber;

        if (maxMaNumber != null) {
            nextNumber = maxMaNumber + 1;
        } else {
            nextNumber = 1;
        }

        return "HD" + nextNumber;
    }

    // tính tổng số tiền hôm nay của hóa đơn
    @Query("SELECT SUM(tong_tien) " +
            "FROM HoaDon " +
            "WHERE trangthai = 3 AND ngay_thanh_toan = :ngayHienTai")
    BigDecimal tongSoTienHomNay(@Param("ngayHienTai") LocalDate ngayHienTai);

    // tổng số hóa đơn đang chờ xác nhận
    @Query("SELECT COUNT(*) AS TongSoLuongTrangThai1\n" +
            "FROM HoaDon\n" +
            "WHERE trangthai = 0\n")
    Integer tongsohoadondangchoxanhan();

    // tính tổng tiền theo tháng trong năm
    @Query("SELECT MONTH(ngay_thanh_toan) AS thang, SUM(tong_tien) AS tong_tien_thang\n" +
            "FROM HoaDon \n" +
            "WHERE YEAR(ngay_thanh_toan) = YEAR(CURRENT_DATE()) and trangthai =3\n" +
            "GROUP BY MONTH(ngay_thanh_toan)\n" +
            "ORDER BY thang")
    List<Object[]> getHoaDonByTongTienTheoThangTrongNam();

    // tính tổng tiền theo tháng trong năm
    @Query("SELECT MONTH(ngay_thanh_toan) AS thang, SUM(tong_tien) AS tong_tien_thang\n" +
            "FROM HoaDon \n" +
            "WHERE YEAR(ngay_thanh_toan) = ?1 and trangthai =3\n" +
            "GROUP BY MONTH(ngay_thanh_toan)\n" +
            "ORDER BY thang")
    List<Object[]> getHoaDonByTongTienTheoThangTrongNam(Integer nam);

    // Tìm tất cả hóa đơn theo trạng thái
    Integer countByTrangthai(int trangthai);

    //Tính tổng tiền doanh thu bán hàng theo ngày
    @Query("SELECT SUM(tong_tien) FROM HoaDon WHERE trangthai = 3 AND ngay_thanh_toan = :ngay")
    BigDecimal tongTienTheoNgay(@Param("ngay") LocalDate ngay);

    //Tính tổng tiền doanh thu bán hàng Trong ngày
    @Query("SELECT SUM(tong_tien) FROM HoaDon WHERE trangthai = 3 AND ngay_thanh_toan = :ngay")
    BigDecimal tongTienTrongNgay(LocalDate ngay);

    //Tính tổng tiền doanh thu bán hàng theo tuần
    @Query("SELECT SUM(tong_tien) FROM HoaDon WHERE trangthai = 3 AND EXTRACT(WEEK FROM ngay_thanh_toan) = EXTRACT(WEEK FROM :ngay)")
    BigDecimal tongTienTheoTuan(@Param("ngay") LocalDate ngay);

    //Tính tổng tiền doanh thu bán hàng theo tháng
    @Query("SELECT SUM(tong_tien) FROM HoaDon WHERE trangthai = 3 AND YEAR(ngay_thanh_toan) = YEAR(:ngay) AND MONTH(ngay_thanh_toan) = MONTH(:ngay)")
    BigDecimal tongTienTheoThang(@Param("ngay") LocalDate ngay);

    //Tính tổng tiền doanh thu bán hàng theo năm
    @Query("SELECT SUM(tong_tien) FROM HoaDon WHERE trangthai = 3 AND YEAR(ngay_thanh_toan) = YEAR(:ngay)")
    BigDecimal tongTienTheoNam(@Param("ngay") LocalDate ngay);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.ngay_thanh_toan = :ngayTT")
    List<HoaDon> findHoaDonByNgay_thanh_toan(@Param("ngayTT") LocalDate ngayTT);

    @Query("SELECT h FROM HoaDon h WHERE h.ngay_thanh_toan BETWEEN :ngayBatDau AND :ngayKetThuc")
    List<HoaDon> findHoaDonByNgay_thanh_toanBetween(@Param("ngayBatDau") LocalDate ngayBatDau, @Param("ngayKetThuc") LocalDate ngayKetThuc);

    @Query("SELECT h FROM HoaDon h WHERE YEAR(h.ngay_thanh_toan) = :nam")
    List<HoaDon> findByNgayThanhToanYear(@Param("nam") int nam);


//    Tổng Số Người mua kiều online
    @Query("SELECT COUNT(*) AS TongSoHinhThucMuaOn\n" +
            "FROM HoaDon\n" +
            "WHERE hinh_thuc_mua = 1\n")
    Integer tongsomuaonline();

    //    Tổng Số Người mua kiều Trức tiếp
    @Query("SELECT COUNT(*) AS TongSoHinhThucMuaTrucTiep\n" +
            "FROM HoaDon\n" +
            "WHERE hinh_thuc_mua = 0\n")
    Integer tongsomuatructiep();


//    Top 10 Sản Phẩm Bán Chạy Nhất Trong Năm
    @Query(value = "SELECT TOP 10\n" +
            "    giay.id AS id_giay,\n" +
            "    giay.ten AS ten_giay,\n" +
            "    SUM(hoa_don_chi_tiet.so_luong) AS so_luong_ban\n" +
            "FROM hoa_don_chi_tiet\n" +
            "JOIN giay_chi_tiet ON hoa_don_chi_tiet.id_giay_chi_tiet = giay_chi_tiet.id\n" +
            "JOIN giay ON giay_chi_tiet.id_giay = giay.id\n" +
            "JOIN hoa_don ON hoa_don_chi_tiet.id_hoa_don = hoa_don.id\n" +
            "WHERE hoa_don.trangthai = 3\n" +
            "    AND hoa_don.ngay_tao >= DATEADD(YEAR, DATEDIFF(YEAR, 0, GETDATE()), 0) \n" +
            "    AND hoa_don.ngay_tao < DATEADD(YEAR, DATEDIFF(YEAR, 0, GETDATE()) + 1, 0) \n" +
            "GROUP BY giay.id, giay.ten\n" +
            "ORDER BY so_luong_ban DESC",nativeQuery = true)
    List<Object[]> top10SanPhamBanChaytrongnam();

    //    Top 10 Sản Phẩm Bán Chạy Nhất Tháng Trước
    @Query(value = "SELECT TOP 10\n" +
            "    giay.id AS id_giay,\n" +
            "    giay.ten AS ten_giay,\n" +
            "    SUM(hoa_don_chi_tiet.so_luong) AS so_luong_ban\n" +
            "FROM hoa_don_chi_tiet\n" +
            "JOIN giay_chi_tiet ON hoa_don_chi_tiet.id_giay_chi_tiet = giay_chi_tiet.id\n" +
            "JOIN giay ON giay_chi_tiet.id_giay = giay.id\n" +
            "JOIN hoa_don ON hoa_don_chi_tiet.id_hoa_don = hoa_don.id\n" +
            "WHERE hoa_don.trangthai = 3\n" +
            "    AND hoa_don.ngay_tao >= DATEADD(MONTH, -1, GETDATE()) \n" +
            "    AND hoa_don.ngay_tao < GETDATE()\n" +
            "GROUP BY giay.id, giay.ten\n" +
            "ORDER BY so_luong_ban DESC",nativeQuery = true)
    List<Object[]> top10SanPhamBanChaythangtrc();

    //    Top 10 Sản Phẩm Bán Chạy Nhất 7 Ngày qua
    @Query(value = "SELECT TOP 10\n" +
            "    giay.id AS id_giay,\n" +
            "    giay.ten AS ten_giay,\n" +
            "    SUM(hoa_don_chi_tiet.so_luong) AS so_luong_ban\n" +
            "FROM hoa_don_chi_tiet\n" +
            "JOIN giay_chi_tiet ON hoa_don_chi_tiet.id_giay_chi_tiet = giay_chi_tiet.id\n" +
            "JOIN giay ON giay_chi_tiet.id_giay = giay.id\n" +
            "JOIN hoa_don ON hoa_don_chi_tiet.id_hoa_don = hoa_don.id\n" +
            "WHERE hoa_don.trangthai = 3\n" +
            "    AND hoa_don.ngay_tao >= DATEADD(DAY, -7, GETDATE())\n" +
            "    AND hoa_don.ngay_tao < GETDATE()\n" +
            "GROUP BY giay.id, giay.ten\n" +
            "ORDER BY so_luong_ban DESC",nativeQuery = true)
    List<Object[]> top10SanPhamBanChaytrong7ngayqua();


    //    Top 10 Sản Phẩm Bán Chạy Nhất Hôm Qua
    @Query(value = "SELECT TOP 10\n" +
            "    giay.id AS id_giay,\n" +
            "    giay.ten AS ten_giay,\n" +
            "    SUM(hoa_don_chi_tiet.so_luong) AS so_luong_ban\n" +
            "FROM hoa_don_chi_tiet\n" +
            "JOIN giay_chi_tiet ON hoa_don_chi_tiet.id_giay_chi_tiet = giay_chi_tiet.id\n" +
            "JOIN giay ON giay_chi_tiet.id_giay = giay.id\n" +
            "JOIN hoa_don ON hoa_don_chi_tiet.id_hoa_don = hoa_don.id\n" +
            "WHERE hoa_don.trangthai = 3\n" +
            "    AND hoa_don.ngay_tao >= DATEADD(DAY, -1, GETDATE())\n" +
            "    AND hoa_don.ngay_tao < GETDATE() \n" +
            "GROUP BY giay.id, giay.ten\n" +
            "ORDER BY so_luong_ban DESC", nativeQuery = true)
    List<Object[]> top10SanPhamBanChaytronghomqua();

    //    Top 10 Sản Phẩm Bán Chạy Nhất Tháng Này
    @Query(value = "SELECT TOP 10\n" +
            "    giay.id AS id_giay,\n" +
            "    giay.ten AS ten_giay,\n" +
            "    SUM(hoa_don_chi_tiet.so_luong) AS so_luong_ban\n" +
            "FROM hoa_don_chi_tiet\n" +
            "JOIN giay_chi_tiet ON hoa_don_chi_tiet.id_giay_chi_tiet = giay_chi_tiet.id\n" +
            "JOIN giay ON giay_chi_tiet.id_giay = giay.id\n" +
            "JOIN hoa_don ON hoa_don_chi_tiet.id_hoa_don = hoa_don.id\n" +
            "WHERE hoa_don.trangthai = 3\n" +
            "    AND hoa_don.ngay_tao >= DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0) \n" +
            "    AND hoa_don.ngay_tao < DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()) + 1, 0) \n" +
            "GROUP BY giay.id, giay.ten\n" +
            "ORDER BY so_luong_ban DESC", nativeQuery = true)
    List<Object[]> top10SanPhamBanChaytrongthangnay();

//    Top 10 khách hàng mua nhiều nhất trong năm
    @Query(value = "SELECT TOP 10\n" +
            "    kh.id AS khach_hang_id,\n" +
            "    kh.ho_ten AS ten_khach_hang,\n" +
            "    SUM(hdct.don_gia) AS tong_tien_mua\n" +
            "FROM\n" +
            "    khach_hang kh\n" +
            "JOIN\n" +
            "    hoa_don hd ON kh.id = hd.id_khach_hang\n" +
            "JOIN\n" +
            "    hoa_don_chi_tiet hdct ON hd.id = hdct.id_hoa_don\n" +
            "WHERE\n" +
            "    YEAR(hd.ngay_tao) = YEAR(GETDATE()) \n" +
            "    AND hd.trangthai = 3\n" +
            "GROUP BY\n" +
            "    kh.id, kh.ho_ten\n" +
            "ORDER BY\n" +
            "    tong_tien_mua DESC", nativeQuery = true)
    List<Object[]> top10khachhangmuanhieunhattrongnam();

    //    Top 10 khách hàng mua nhiều nhất 7 ngày qua
    @Query(value = "\tSELECT TOP 10\n" +
            "    kh.id AS khach_hang_id,\n" +
            "    kh.ho_ten AS ten_khach_hang,\n" +
            "    SUM(hdct.don_gia) AS tong_tien_mua\n" +
            "FROM\n" +
            "    khach_hang kh\n" +
            "JOIN\n" +
            "    hoa_don hd ON kh.id = hd.id_khach_hang\n" +
            "JOIN\n" +
            "    hoa_don_chi_tiet hdct ON hd.id = hdct.id_hoa_don\n" +
            "WHERE\n" +
            "    hd.ngay_tao >= DATEADD(DAY, -6, GETDATE())\n" +
            "    AND hd.trangthai = 3\n" +
            "GROUP BY\n" +
            "    kh.id, kh.ho_ten\n" +
            "ORDER BY\n" +
            "    tong_tien_mua DESC", nativeQuery = true)
    List<Object[]> top10khachhangmuanhieunhat7ngayqua();

    //    Top 10 khách hàng mua nhiều nhất trong Hôm qua
    @Query(value = "\n" +
            "\tSELECT TOP 10\n" +
            "    kh.id AS khach_hang_id,\n" +
            "    kh.ho_ten AS ten_khach_hang,\n" +
            "    SUM(hdct.don_gia) AS tong_tien_mua\n" +
            "FROM\n" +
            "    khach_hang kh\n" +
            "JOIN\n" +
            "    hoa_don hd ON kh.id = hd.id_khach_hang\n" +
            "JOIN\n" +
            "    hoa_don_chi_tiet hdct ON hd.id = hdct.id_hoa_don\n" +
            "WHERE\n" +
            "    CAST(hd.ngay_tao AS DATE) = CAST(DATEADD(DAY, -1, GETDATE()) AS DATE) \n" +
            "    AND hd.trangthai = 3\n" +
            "GROUP BY\n" +
            "    kh.id, kh.ho_ten\n" +
            "ORDER BY\n" +
            "    tong_tien_mua DESC", nativeQuery = true)
    List<Object[]> top10khachhangmuanhieunhathomqua();

    //    Top 10 khách hàng mua nhiều nhất trong Tháng trc
    @Query(value = "SELECT TOP 10\n" +
            "    kh.id AS khach_hang_id,\n" +
            "    kh.ho_ten AS ten_khach_hang,\n" +
            "    SUM(hdct.don_gia) AS tong_tien_mua\n" +
            "FROM\n" +
            "    khach_hang kh\n" +
            "JOIN\n" +
            "    hoa_don hd ON kh.id = hd.id_khach_hang\n" +
            "JOIN\n" +
            "    hoa_don_chi_tiet hdct ON hd.id = hdct.id_hoa_don\n" +
            "WHERE\n" +
            "    MONTH(hd.ngay_tao) = MONTH(DATEADD(MONTH, -1, GETDATE()))  -- Lấy hóa đơn trong tháng trước\n" +
            "    AND YEAR(hd.ngay_tao) = YEAR(GETDATE())  -- Lấy hóa đơn trong năm hiện tại\n" +
            "    AND hd.trangthai = 3\n" +
            "GROUP BY\n" +
            "    kh.id, kh.ho_ten\n" +
            "ORDER BY\n" +
            "    tong_tien_mua DESC", nativeQuery = true)
    List<Object[]> top10khachhangmuanhieunhatthangtruoc();

    //    Top 10 khách hàng mua nhiều nhất trong tháng này
    @Query(value = "SELECT TOP 10\n" +
            "    kh.id AS khach_hang_id,\n" +
            "    kh.ho_ten AS ten_khach_hang,\n" +
            "    SUM(hdct.don_gia) AS tong_tien_mua\n" +
            "FROM\n" +
            "    khach_hang kh\n" +
            "JOIN\n" +
            "    hoa_don hd ON kh.id = hd.id_khach_hang\n" +
            "JOIN\n" +
            "    hoa_don_chi_tiet hdct ON hd.id = hdct.id_hoa_don\n" +
            "WHERE\n" +
            "    MONTH(hd.ngay_tao) = MONTH(GETDATE())  -- Lấy hóa đơn trong tháng hiện tại\n" +
            "    AND YEAR(hd.ngay_tao) = YEAR(GETDATE())  -- Lấy hóa đơn trong năm hiện tại\n" +
            "    AND hd.trangthai = 3\n" +
            "GROUP BY\n" +
            "    kh.id, kh.ho_ten\n" +
            "ORDER BY\n" +
            "    tong_tien_mua DESC", nativeQuery = true)
    List<Object[]> top10khachhangmuanhieunhatthangnay();

    //    Top 10 Nhân viên bán được nhiều nhất trong năm
    @Query(value = "SELECT TOP 10\n" +
            "    NV.id AS NhanVienID,\n" +
            "    NV.ho_ten AS HoTen,\n" +
            "    COUNT(HD.id) AS SoHoaDonBanDuoc\n" +
            "FROM\n" +
            "    nhan_vien NV\n" +
            "INNER JOIN\n" +
            "    hoa_don HD ON NV.id = HD.id_nhan_vien\n" +
            "WHERE\n" +
            "    HD.trangthai = 3 AND HD.hinh_thuc_mua = 0\n" +
            "    AND YEAR(HD.ngay_tao) = YEAR(GETDATE()) -- Filter for the current year\n" +
            "GROUP BY\n" +
            "    NV.id, NV.ho_ten\n" +
            "ORDER BY\n" +
            "    SoHoaDonBanDuoc DESC\n", nativeQuery = true)
    List<Object[]> top10nhanvienbannhieunhattrongnam();

    //    Top 10 Nhân viên bán được nhiều nhất trong tháng này
    @Query(value = "SELECT TOP 10\n" +
            "    NV.id AS NhanVienID,\n" +
            "    NV.ho_ten AS HoTen,\n" +
            "    COUNT(HD.id) AS SoHoaDonBanDuoc\n" +
            "FROM\n" +
            "    nhan_vien NV\n" +
            "INNER JOIN\n" +
            "    hoa_don HD ON NV.id = HD.id_nhan_vien\n" +
            "WHERE\n" +
            "    HD.trangthai = 3 AND HD.hinh_thuc_mua = 0 \n" +
            "    AND YEAR(HD.ngay_tao) = YEAR(GETDATE()) -- Filter for the current year\n" +
            "    AND MONTH(HD.ngay_tao) = MONTH(GETDATE()) -- Filter for the current month\n" +
            "GROUP BY\n" +
            "    NV.id, NV.ho_ten\n" +
            "ORDER BY\n" +
            "    SoHoaDonBanDuoc DESC\n", nativeQuery = true)
    List<Object[]> top10nhanvienbannhieunhatthangnay();

    //    Top 10 Nhân viên bán được nhiều nhất trong tháng trc
    @Query(value = "SELECT TOP 10\n" +
            "    NV.id AS NhanVienID,\n" +
            "    NV.ho_ten AS HoTen,\n" +
            "    COUNT(HD.id) AS SoHoaDonBanDuoc\n" +
            "FROM\n" +
            "    nhan_vien NV\n" +
            "INNER JOIN\n" +
            "    hoa_don HD ON NV.id = HD.id_nhan_vien\n" +
            "WHERE\n" +
            "    HD.trangthai = 3 AND HD.hinh_thuc_mua = 0\n" +
            "    AND YEAR(HD.ngay_tao) = YEAR(DATEADD(MONTH, -1, GETDATE())) -- Filter for the previous year\n" +
            "    AND MONTH(HD.ngay_tao) = MONTH(DATEADD(MONTH, -1, GETDATE())) -- Filter for the previous month\n" +
            "GROUP BY\n" +
            "    NV.id, NV.ho_ten\n" +
            "ORDER BY\n" +
            "    SoHoaDonBanDuoc DESC\n", nativeQuery = true)
    List<Object[]> top10nhanvienbannhieunhatthangtruoc();

    //    Top 10 Nhân viên bán được nhiều nhất trong hôm qua
    @Query(value = "SELECT TOP 10\n" +
            "    NV.id AS NhanVienID,\n" +
            "    NV.ho_ten AS HoTen,\n" +
            "    COUNT(HD.id) AS SoHoaDonBanDuoc\n" +
            "FROM\n" +
            "    nhan_vien NV\n" +
            "INNER JOIN\n" +
            "    hoa_don HD ON NV.id = HD.id_nhan_vien\n" +
            "WHERE\n" +
            "    HD.trangthai = 3 AND HD.hinh_thuc_mua = 0\n" +
            "    AND CAST(HD.ngay_tao AS DATE) = CAST(GETDATE() - 1 AS DATE) -- Filter for yesterday\n" +
            "GROUP BY\n" +
            "    NV.id, NV.ho_ten\n" +
            "ORDER BY\n" +
            "    SoHoaDonBanDuoc DESC\n", nativeQuery = true)
    List<Object[]> top10nhanvienbannhieunhathomqua();

    //    Top 10 Nhân viên bán được nhiều nhất trong 7 ngày qua
    @Query(value = "SELECT TOP 10\n" +
            "    NV.id AS NhanVienID,\n" +
            "    NV.ho_ten AS HoTen,\n" +
            "    COUNT(HD.id) AS SoHoaDonBanDuoc\n" +
            "FROM\n" +
            "    nhan_vien NV\n" +
            "INNER JOIN\n" +
            "    hoa_don HD ON NV.id = HD.id_nhan_vien\n" +
            "WHERE\n" +
            "    HD.trangthai = 3 AND HD.hinh_thuc_mua = 0\n" +
            "    AND HD.ngay_tao >= DATEADD(DAY, -6, GETDATE()) -- Filter for the last 7 days\n" +
            "GROUP BY\n" +
            "    NV.id, NV.ho_ten\n" +
            "ORDER BY\n" +
            "    SoHoaDonBanDuoc DESC\n", nativeQuery = true)
    List<Object[]> top10nhanvienbannhieunhat7ngayqua();

    // Tổng tiền lợi nhuận hàng tháng trong năm
    @Query(value = "SELECT MONTH(ngay_thanh_toan) AS Thang,\n" +
            "       YEAR(ngay_thanh_toan) AS Nam,\n" +
            "       SUM((hdc.don_gia - hdc.gia_nhap) * hdc.so_luong) AS LoiNhuanThang\n" +
            "FROM hoa_don hd\n" +
            "INNER JOIN hoa_don_chi_tiet hdc ON hd.id = hdc.id_hoa_don\n" +
            "WHERE YEAR(ngay_thanh_toan) = YEAR(GETDATE()) AND hd.trangthai = 3\n" +
            "GROUP BY YEAR(ngay_thanh_toan), MONTH(ngay_thanh_toan)",nativeQuery = true)
    List<Object[]> getHoaDonByTongTienloinhuanTheoThangTrongNam();

    // Tổng tiền lợi nhuận hàng tháng trong năm
    @Query(value = "SELECT MONTH(ngay_thanh_toan) AS Thang,\n" +
            "       YEAR(ngay_thanh_toan) AS Nam,\n" +
            "       SUM((hdc.don_gia - hdc.gia_nhap) * hdc.so_luong) AS LoiNhuanThang\n" +
            "FROM hoa_don hd\n" +
            "INNER JOIN hoa_don_chi_tiet hdc ON hd.id = hdc.id_hoa_don\n" +
            "WHERE YEAR(ngay_thanh_toan) = ?1 AND hd.trangthai = 3\n" +
            "GROUP BY YEAR(ngay_thanh_toan), MONTH(ngay_thanh_toan)",nativeQuery = true)
    List<Object[]> getHoaDonByTongTienloinhuanTheoThangTrongNam(Integer nam);


}

