package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
public class SanPhamController {
    @Autowired
    AnhGiayDAO anhGiayDAO;
    @Autowired
    GiayDAO giayDAO;
    @Autowired
    GiayChiTietDAO giayChiTietDAO;
    @Autowired
    KichCoDAO kichCoDAO;

    @Data
    public static class SearchForm {
        String tensp = "";
        String thuong_hieu = "";
        String chat_lieu = "";
        String xuat_xu = "";
        String mau_sac = "";
        String gioi_tinh = "";
        String kieu_dang = "";
        String de_giay = "";
        Integer page = 0;
        BigDecimal tien_min;
        BigDecimal tien_max;
    }

    @RequestMapping("/admin/sanpham")
    public String product(Model model, @ModelAttribute("searchform") SearchForm searchForm) {
        Pageable pageable = PageRequest.of(searchForm.page, 20);
        if (searchForm.tien_min == null && searchForm.tien_max == null) {
            model.addAttribute("items", giayDAO.getSearchsanphamByTT("%" + searchForm.tensp + "%",
                    BigDecimal.ZERO, BigDecimal.valueOf(999999999),
                    "%" + searchForm.thuong_hieu + "%", "%" + searchForm.chat_lieu + "%", "%" + searchForm.xuat_xu + "%",
                    "%" + searchForm.mau_sac + "%", "%" + searchForm.gioi_tinh + "%", "%" + searchForm.kieu_dang + "%", "%" + searchForm.de_giay + "%", pageable));
        }
        if (searchForm.tien_min == null) {
            model.addAttribute("items", giayDAO.getSearchsanphamByTT("%" + searchForm.tensp + "%",
                    BigDecimal.ZERO, searchForm.tien_max,
                    "%" + searchForm.thuong_hieu + "%", "%" + searchForm.chat_lieu + "%", "%" + searchForm.xuat_xu + "%",
                    "%" + searchForm.mau_sac + "%", "%" + searchForm.gioi_tinh + "%", "%" + searchForm.kieu_dang + "%", "%" + searchForm.de_giay + "%", pageable));
        }
        if (searchForm.tien_max == null) {
            model.addAttribute("items", giayDAO.getSearchsanphamByTT("%" + searchForm.tensp + "%",
                    searchForm.tien_min, BigDecimal.valueOf(999999999),
                    "%" + searchForm.thuong_hieu + "%", "%" + searchForm.chat_lieu + "%", "%" + searchForm.xuat_xu + "%",
                    "%" + searchForm.mau_sac + "%", "%" + searchForm.gioi_tinh + "%", "%" + searchForm.kieu_dang + "%", "%" + searchForm.de_giay + "%", pageable));

        } else {
            model.addAttribute("items", giayDAO.getSearchsanphamByTT("%" + searchForm.tensp + "%",
                    searchForm.tien_min, searchForm.tien_max,
                    "%" + searchForm.thuong_hieu + "%", "%" + searchForm.chat_lieu + "%", "%" + searchForm.xuat_xu + "%",
                    "%" + searchForm.mau_sac + "%", "%" + searchForm.gioi_tinh + "%", "%" + searchForm.kieu_dang + "%", "%" + searchForm.de_giay + "%", pageable));

        }
        return "product/hien_thi_sp";
    }

    @RequestMapping("/admin/sanpham/{x}")
    public String productDetail(Model model, @PathVariable("x") String x) {
        model.addAttribute("items", giayDAO.findAll());
        return "product/index";
    }

    @GetMapping("/admin/sanpham/create")
    public String productCreate(Model model, @ModelAttribute("sanpham") Giay giay) {
        giay.setMa(giayDAO.generateNextMaGiay());
        model.addAttribute("items", giayDAO.findAll());
        return "product/create_sp";
    }

    @GetMapping("/admin/sanpham/update/{x}")
    public String productUpdate(Model model, @PathVariable("x") String x, @ModelAttribute("giaychitiet") GiayChiTiet giayChiTiet) {
        model.addAttribute("listgiaychitiet", giayChiTietDAO.getAllByMaGiay(x));
        model.addAttribute("anh", anhGiayDAO.getAnhByMaGiay(x));
        model.addAttribute("sanpham", giayDAO.getGiayByMa(x));
        return "product/update_sp";
    }

    @GetMapping(value = "/admin/sanpham/update/{x}", params = "spct")
    public String productUpdate1(Model model, @PathVariable("x") String x, @RequestParam("spct") String spct) {
        model.addAttribute("listgiaychitiet", giayChiTietDAO.getAllByMaGiay(x));
        model.addAttribute("anh", anhGiayDAO.getAnhByMaGiay(x));
        model.addAttribute("sanpham", giayDAO.getGiayByMa(x));
        model.addAttribute("giaychitiet", giayChiTietDAO.getAllByMaGiayChiTiet(x, spct));
        return "product/update_sp";
    }

    @PostMapping("/admin/sanpham/update/{x}")
    public String productUpdate2(Model model, @PathVariable("x") String x, @ModelAttribute("sanpham") Giay giay) {
        Giay giay1 = giayDAO.getGiayByMa(x);
        giay.setMa(x);
        giay.setGia_sau_khuyen_mai(giay.getGiaban());
        giay.setId(giay1.getId());
        giay.setNgay_nhap(giay1.getNgay_nhap());
        giayDAO.save(giay);
        return "redirect:/admin/sanpham/update/" + giay.getMa();
    }

    @PostMapping("/admin/sanpham/create")
    public String productCreate2(Model model, @ModelAttribute("sanpham") Giay giay) {
        giay.setMa(giayDAO.generateNextMaGiay());
        giay.setNgay_nhap(LocalDate.now());
        giay.setGia_sau_khuyen_mai(giay.getGiaban());
        String ma = giayDAO.save(giay).getMa();
        model.addAttribute("items", giayDAO.findAll());
        return "redirect:/admin/sanpham/update/" + ma;
    }

    @PostMapping("/admin/sanpham/createkc")
    public String productcreatekc(Model model, HttpServletRequest request, @RequestParam("idsp") UUID idsp) {
        String[] listvalue = request.getParameterValues("listKC");
        if (listvalue != null) {
            for (String x : listvalue
            ) {
                GiayChiTiet giayChiTiet = new GiayChiTiet();
                giayChiTiet.setKich_co(kichCoDAO.findById(UUID.fromString(x)).get());
                giayChiTiet.setSo_luong_ton(Integer.parseInt(request.getParameter(x + "_soluong")));
                giayChiTiet.setGiay(Giay.builder().id(idsp).build());
                giayChiTiet.setTrangthai(1);
                giayChiTietDAO.save(giayChiTiet);
            }
        }
        String ma = giayDAO.findById(idsp).get().getMa();
        return "redirect:/admin/sanpham/update/" + ma;
    }

    @PostMapping("/admin/sanpham/createact")
    public String productCreateAct(Model model, @ModelAttribute("giaychitiet") GiayChiTiet giayChiTiet, @RequestParam("idsp") UUID idsp) {
        giayChiTiet.setGiay(Giay.builder().id(idsp).build());
        giayChiTietDAO.save(giayChiTiet);
        String ma = giayDAO.findById(giayChiTiet.getGiay().getId()).get().getMa();
        return "redirect:/admin/sanpham/update/"+ma;
    }

    @PostMapping("/admin/sanpham/createanh")
    public String createanh(Model model, HttpServletRequest request, @RequestParam("idsp") UUID idsp, @RequestPart("ten_url1") List<MultipartFile> files) throws IOException, ServletException {
        for (MultipartFile file : files) {
            // Xử lý mỗi tệp tin (lưu, xử lý, vv.)
            Anh anh = new Anh();
            Path path = Paths.get("src/main/webapp/images/");
            try {
                InputStream inputStream = file.getInputStream();
                Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                e.printStackTrace();
            }
            anh.setGiay(Giay.builder().id(idsp).build());
            anh.setTen_url(file.getOriginalFilename());
            anhGiayDAO.save(anh);
        }
        String ma = giayDAO.findById(idsp).get().getMa();
        return "redirect:/admin/sanpham/update/" + ma;
    }

    @PostMapping("/admin/sanpham/deleteanh")
    public String deleteanh(Model model, HttpServletRequest request, @RequestParam("idAnhDel") UUID idAnhDel) {
        String ma = anhGiayDAO.findById(idAnhDel).get().getGiay().getMa();
        anhGiayDAO.deleteById(idAnhDel);
        return "redirect:/admin/sanpham/update/" + ma;
    }


    final String exportFilePath = "src/main/resources/excel/export.xlsx";
    @RequestMapping("/admin/sanpham/export")
    public void downFileExport(HttpServletResponse response) throws IOException {
        exportFileSp();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ListSpExport.xlsx");
        try (InputStream is = new FileInputStream(exportFilePath);
             OutputStream os = response.getOutputStream()) {
            org.apache.commons.io.IOUtils.copy(Objects.requireNonNull(is), os);
            os.flush(); // Đảm bảo dữ liệu được ghi vào response
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @PostMapping("/admin/sanpham/import")
    public Boolean importExcel(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            List<Giay> giays = ListSpFromFileSp(file);
            giays.stream().forEach(item -> {
                item.setMa(giayDAO.generateNextMaGiay());
                giayDAO.save(item);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    List<Giay> ListSpFromFileSp(MultipartFile file) throws IOException {
        List<Giay> giays = new ArrayList<>();
        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            // Lấy sheet đầu tiên (giả sử chỉ có một sheet)
            Sheet sheet = workbook.getSheetAt(0);

            // Bắt đầu từ dòng thứ 2 (index 1)
            Iterator<Row> rowIterator = sheet.iterator();
            // Bỏ qua dòng tiêu đề
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            // Lặp qua tất cả các dòng và thêm vào danh sách
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Giay giay = giayFromRow(row);
                if (giay != null) {
                    giays.add(giay);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return giays;
    }

    @Autowired
    ThuongHieuDAO thuongHieuDAO;
    @Autowired
    GioiTinhDAO gioiTinhDAO;
    @Autowired
    ChatLieuDAO chatLieuDAO;
    @Autowired
    DeGiayDAO deGiayDAO;
    @Autowired
    MauSacDAO mauSacDAO;
    @Autowired
    XuatXuDAO xuatXuDAO;
    @Autowired
    KieuDangDAO kieuDangDAO;


    Giay giayFromRow(Row row) {
        try {
            Giay giay = new Giay();

            String ten = row.getCell(0).toString();
            giay.setTen(ten);

            String maTh = row.getCell(1).toString();
            ThuongHieu thuongHieu = thuongHieuDAO.findThuongHieuByMa(maTh);
            giay.setThuong_hieu(thuongHieu);

            String maGt = row.getCell(2).toString();
            GioiTinh gioiTinh = gioiTinhDAO.findGioiTinhByMa(maGt);
            giay.setGioi_tinh(gioiTinh);

            String maCl = row.getCell(3).toString();
            ChatLieu chatLieu = chatLieuDAO.findChatLieuByMa(maCl);
            giay.setChat_lieu(chatLieu);

            String maDg = row.getCell(4).toString();
            DeGiay deGiay = deGiayDAO.findDeGiayByMa(maDg);
            giay.setDe_giay(deGiay);

            String maMs = row.getCell(5).toString();
            MauSac mauSac = mauSacDAO.findMauSacByMa(maMs);
            giay.setMau_sac(mauSac);

            String maXx = row.getCell(6).toString();
            XuatXu xuatXu = xuatXuDAO.findXuatXuByMa(maXx);
            giay.setXuat_xu(xuatXu);

            String maKd = row.getCell(7).toString();
            KieuDang kieuDang = kieuDangDAO.findKieuDangByMa(maKd);
            giay.setKieu_dang(kieuDang);

            String mota = row.getCell(8).toString();
            giay.setMota(mota);

            String giaNhapClone = row.getCell(9).getStringCellValue();
            giay.setGianhap(new BigDecimal(giaNhapClone));

            String giaBanClone = row.getCell(10).getStringCellValue();
            giay.setGiaban(new BigDecimal(giaBanClone));

//            double giaBanClone = row.getCell(10).getNumericCellValue();
//            giay.setGiaban(BigDecimal.valueOf(giaBanClone));

//            double tt = row.getCell(11).getNumericCellValue();
//            String formatTt = new DecimalFormat("#").format(tt);
            String tt = row.getCell(11).getStringCellValue();
            giay.setTrangthai(Integer.valueOf(tt));

            String giaSauKm = row.getCell(12).getStringCellValue();
            giay.setGia_sau_khuyen_mai(new BigDecimal(giaSauKm));

            String ngayNhapClone=row.getCell(13).getStringCellValue();
            LocalDate ngayNhap=LocalDate.parse(ngayNhapClone);
            giay.setNgay_nhap(ngayNhap);

            String doHot = row.getCell(14).getStringCellValue();
//            double doHot = row.getCell(14).getNumericCellValue();
//            String formatDh = new DecimalFormat("#").format(doHot);
            giay.setDo_hot(Integer.valueOf(doHot));

            return giay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String[] columns = {"Mã", "Tên", "Thương hiệu", "Giới tính", "Chất liệu", "Đế giày",
            "Màu sắc", "Xuất xứ", "Kiểu dáng", "Mô tả", "Giá nhập", "Giá bán",
            "Trạng thái", "Giá sau khuyến mãi", "Ngày nhập", "Độ hot"};

    void exportFileSp() {
        List<Giay> giays = giayDAO.allGiay();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Giay");

        // Tạo một Font cho việc định dạng các ô header
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Tạo một Row cho header
        Row headerRow = sheet.createRow(0);
        // Tạo các ô header
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
            sheet.autoSizeColumn(i);
        }

        int rowNum = 1;
        for (Giay giay : giays
        ) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(giay.getMa());
            row.createCell(1).setCellValue(giay.getTen());
            row.createCell(2).setCellValue(giay.getThuong_hieu().getTen());
            row.createCell(3).setCellValue(giay.getGioi_tinh().getTen());
            row.createCell(4).setCellValue(giay.getChat_lieu().getTen());
            row.createCell(5).setCellValue(giay.getDe_giay().getTen());
            row.createCell(6).setCellValue(giay.getMau_sac().getTen());
            row.createCell(7).setCellValue(giay.getXuat_xu().getTen());
            row.createCell(8).setCellValue(giay.getKieu_dang().getTen());
            row.createCell(9).setCellValue(giay.getMota());
            row.createCell(10).setCellValue(giay.getGianhap().toString());
            row.createCell(11).setCellValue(giay.getGiaban().toString());
            row.createCell(12).setCellValue(giay.getTrangthai().toString());
            row.createCell(13).setCellValue(giay.getGia_sau_khuyen_mai().toString());
            row.createCell(14).setCellValue(giay.getNgay_nhap().toString());
            row.createCell(15).setCellValue(giay.getDo_hot().toString());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi kết quả ra một tệp Excel
        try (FileOutputStream fileOut = new FileOutputStream("src/main/resources/excel/export.xlsx")) {
            workbook.write(fileOut);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Đóng workbook
    }

    @Autowired NhanVienDAO nhanVienDAO;
    private Authentication authentication;
    @ModelAttribute("nhanVienLogin")
    public NhanVien nhanVienLogin() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        NhanVien nv=nhanVienDAO.getNVByEmail(authentication.getName());
        return nv;
    }
}
