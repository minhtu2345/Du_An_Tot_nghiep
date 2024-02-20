package com.example.demo.controller;

import com.example.demo.entity.DeGiay;
import com.example.demo.entity.KieuDang;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.KieuDangDAO;
import com.example.demo.repository.KieuDangRepo;
import com.example.demo.repository.NhanVienDAO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class KieuDangController {
    @Autowired
    KieuDangRepo KieuDangRepo;
    @RequestMapping("/admin/kieudang")
    public String KieuDang(@ModelAttribute("kieudang") KieuDang KieuDang, @RequestParam("page") Optional<Integer> page,Model model) {
       PageDTO<KieuDang> page1 = KieuDangRepo.getPageKieuDang(page.orElse(0));
       model.addAttribute("i",0);
       model.addAttribute("listPKieuDang",page1);
        return "product/kieu_dang";
    }

    @PostMapping("/admin/kieudang/create")
    public String createKieuDang(@ModelAttribute("kieudang") KieuDang KieuDang) {
        KieuDangRepo.createKieuDang(KieuDang);
        return "redirect:/admin/kieudang";
    }
    @PostMapping("/admin/kieudang/update/{x}")
    public String updateKieuDang(@PathVariable("x") String ma,@ModelAttribute("kieudang") KieuDang KieuDang) {
        KieuDangRepo.updateKieuDang(ma,KieuDang);
        return "redirect:/admin/kieudang";
    }
    @RequestMapping ("/admin/kieudang/delete/{x}")
    public String deleteKieuDang(@PathVariable("x") String ma) {
        KieuDangRepo.delete(ma);
        return "redirect:/admin/kieudang";
    }

    @RequestMapping("/admin/kieudang/detail/{ma}")
    public String createKieuDang(@PathVariable("ma") String ma,  @RequestParam("page") Optional<Integer> page,Model model) {
        PageDTO<KieuDang> page1 = KieuDangRepo.getPageKieuDang(page.orElse(0));
        model.addAttribute("listPKieuDang",page1);
        model.addAttribute("kieudang", KieuDangRepo.getKieuDangByMa(ma));
        return "product/kieu_dang";
    }

    private static String[] columns = {"Mã", "Tên","Trạng thái"};
    @Autowired
    KieuDangDAO kieuDangDAO;
    final String exportFilePath = "src/main/resources/excel/exportKd.xlsx";

    @ResponseBody
    @PostMapping("/admin/kieudang/import")
    public Boolean importExcel(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            List<KieuDang> kieuDangs = ListKdFromFileKd(file);
            kieuDangs.stream().forEach(item -> {
                item.setMa(kieuDangDAO.generateNextMaKd());
                kieuDangDAO.save(item);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("/admin/kieudang/export")
    public void downFileExport(HttpServletResponse response) throws IOException {
        exportFileCl();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ListKdExport.xlsx");
        try (InputStream is = new FileInputStream(exportFilePath);
             OutputStream os = response.getOutputStream()) {
            org.apache.commons.io.IOUtils.copy(Objects.requireNonNull(is), os);
            os.flush(); // Đảm bảo dữ liệu được ghi vào response
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    KieuDang kieuDangFromRow(Row row) {
        KieuDang kieuDang = new KieuDang();
        try {
            String ten = row.getCell(0).toString();
            kieuDang.setTen(ten);
            double tt = row.getCell(1).getNumericCellValue();
            kieuDang.setTrangthai((int)tt);
            return kieuDang;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    List<KieuDang> ListKdFromFileKd(MultipartFile file) throws IOException {
        List<KieuDang> kieuDangs = new ArrayList<>();
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
                KieuDang kieuDang = kieuDangFromRow(row);
                if (kieuDang != null) {
                    kieuDangs.add(kieuDang);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kieuDangs;
    }

    void exportFileCl() {
        List<KieuDang> kieuDangs = kieuDangDAO.findAllByOrderByMaDesc();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("KieuDang");

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
        for (KieuDang kieuDang : kieuDangs
        ) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(kieuDang.getMa());
            row.createCell(1).setCellValue(kieuDang.getTen());
            row.createCell(2).setCellValue(kieuDang.getTrangthai());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi kết quả ra một tệp Excel
        try (FileOutputStream fileOut = new FileOutputStream(exportFilePath)) {
            workbook.write(fileOut);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Đóng workbook
    }

    @Autowired
    NhanVienDAO nhanVienDAO;
    private Authentication authentication;
    @ModelAttribute("nhanVienLogin")
    public NhanVien nhanVienLogin() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        NhanVien nv=nhanVienDAO.getNVByEmail(authentication.getName());
        return nv;
    }
 }
