package com.example.demo.controller;

import com.example.demo.entity.DeGiay;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.DeGiayDAO;
import com.example.demo.repository.MauSacDAO;
import com.example.demo.repository.MauSacRepo;
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
public class MauSacController {
    @Autowired
    MauSacRepo mauSacRepo;
    @RequestMapping("/admin/mausac")
    public String MauSac(@ModelAttribute("mausac") MauSac MauSac, @RequestParam("page") Optional<Integer> page,Model model) {
       PageDTO<MauSac> page1 = mauSacRepo.getPageMauSac(page.orElse(0));
       model.addAttribute("i",0);
       model.addAttribute("listPMauSac",page1);
        return "product/mau_sac";
    }

    @PostMapping("/admin/mausac/create")
    public String createMauSac(@ModelAttribute("mausac") MauSac MauSac) {
        mauSacRepo.createMauSac(MauSac);
        return "redirect:/admin/mausac";
    }
    @PostMapping("/admin/mausac/update/{x}")
    public String updateMauSac(@PathVariable("x") String ma,@ModelAttribute("mausac") MauSac MauSac) {
        mauSacRepo.updateMauSac(ma,MauSac);
        return "redirect:/admin/mausac";
    }
    @RequestMapping ("/admin/mausac/delete/{x}")
    public String deleteMauSac(@PathVariable("x") String ma) {
        mauSacRepo.delete(ma);
        return "redirect:/admin/mausac";
    }

    @RequestMapping("/admin/mausac/detail/{ma}")
    public String createMauSac(@PathVariable("ma") String ma,  @RequestParam("page") Optional<Integer> page,Model model) {
        PageDTO<MauSac> page1 = mauSacRepo.getPageMauSac(page.orElse(0));
        model.addAttribute("listPMauSac",page1);
        model.addAttribute("mausac", mauSacRepo.getMauSacByMa(ma));
        return "product/mau_sac";
    }

    private static String[] columns = {"Mã", "Tên","Trạng thái"};
    @Autowired
    MauSacDAO mauSacDAO;
    final String exportFilePath = "src/main/resources/excel/exportMs.xlsx";

    @ResponseBody
    @PostMapping("/admin/mausac/import")
    public Boolean importExcel(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            List<MauSac> mauSacs = ListMsFromFileMs(file);
            mauSacs.stream().forEach(item -> {
                item.setMa(mauSacDAO.generateNextMaMs());
                mauSacDAO.save(item);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("/admin/mausac/export")
    public void downFileExport(HttpServletResponse response) throws IOException {
        exportFileMs();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ListMsExport.xlsx");
        try (InputStream is = new FileInputStream(exportFilePath);
             OutputStream os = response.getOutputStream()) {
            org.apache.commons.io.IOUtils.copy(Objects.requireNonNull(is), os);
            os.flush(); // Đảm bảo dữ liệu được ghi vào response
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MauSac mauSacFromRow(Row row) {
        MauSac mauSac = new MauSac();
        try {
            String ten = row.getCell(0).toString();
            mauSac.setTen(ten);
            double tt = row.getCell(1).getNumericCellValue();
            mauSac.setTrangthai((int)tt);
            return mauSac;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    List<MauSac> ListMsFromFileMs(MultipartFile file) throws IOException {
        List<MauSac> mauSacs = new ArrayList<>();
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
                MauSac mauSac = mauSacFromRow(row);
                if (mauSac != null) {
                    mauSacs.add(mauSac);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mauSacs;
    }

    void exportFileMs() {
        List<MauSac> mauSacs = mauSacDAO.findAllByOrderByMaDesc();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("MauSac");

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
        for (MauSac mauSac : mauSacs
        ) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(mauSac.getMa());
            row.createCell(1).setCellValue(mauSac.getTen());
            row.createCell(2).setCellValue(mauSac.getTrangthai());
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

    @ModelAttribute("listMauSac")
    public List<MauSac> getListMauSac() {
        return mauSacRepo.getListMauSac();
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
