package com.example.demo.controller;

import com.example.demo.entity.DeGiay;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.XuatXu;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.DeGiayDAO;
import com.example.demo.repository.NhanVienDAO;
import com.example.demo.repository.XuatXuDAO;
import com.example.demo.repository.XuatXuRepo;
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
public class XuatXuController {
    @Autowired
    XuatXuRepo XuatXuRepo;
    @RequestMapping("/admin/xuatxu")
    public String XuatXu(@ModelAttribute("xuatxu") XuatXu XuatXu, @RequestParam("page") Optional<Integer> page,Model model) {
       PageDTO<XuatXu> page1 = XuatXuRepo.getPageXuatXu(page.orElse(0));
       model.addAttribute("i",0);
       model.addAttribute("listPXuatXu",page1);
        return "product/xuat_xu";
    }

    @PostMapping("/admin/xuatxu/create")
    public String createXuatXu(@ModelAttribute("xuatxu") XuatXu XuatXu) {
        XuatXuRepo.createXuatXu(XuatXu);
        return "redirect:/admin/xuatxu";
    }
    @PostMapping("/admin/XuatXu/update/{x}")
    public String updateXuatXu(@PathVariable("x") String ma,@ModelAttribute("xuatxu") XuatXu XuatXu) {
        XuatXuRepo.updateXuatXu(ma,XuatXu);
        return "redirect:/admin/xuatxu";
    }
    @RequestMapping ("/admin/xuatxu/delete/{x}")
    public String deleteXuatXu(@PathVariable("x") String ma) {
        XuatXuRepo.delete(ma);
        return "redirect:/admin/xuatxu";
    }

    @RequestMapping("/admin/xuatxu/detail/{ma}")
    public String createXuatXu(@PathVariable("ma") String ma,  @RequestParam("page") Optional<Integer> page,Model model) {
        PageDTO<XuatXu> page1 = XuatXuRepo.getPageXuatXu(page.orElse(0));
        model.addAttribute("listPXuatXu",page1);
        model.addAttribute("xuatxu", XuatXuRepo.getXuatXuByMa(ma));
        return "product/xuat_xu";
    }


    private static String[] columns = {"Mã", "Tên","Trạng thái"};
    @Autowired
    XuatXuDAO xuatXuDAO;
    final String exportFilePath = "src/main/resources/excel/exportXs.xlsx";

    @ResponseBody
    @PostMapping("/admin/xuatxu/import")
    public Boolean importExcel(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            List<XuatXu> xuatXus = ListXsFromFileXs(file);
            xuatXus.stream().forEach(item -> {
                item.setMa(xuatXuDAO.generateNextMaXs());
                xuatXuDAO.save(item);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("/admin/xuatxu/export")
    public void downFileExport(HttpServletResponse response) throws IOException {
        exportFileXs();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ListXsExport.xlsx");
        try (InputStream is = new FileInputStream(exportFilePath);
             OutputStream os = response.getOutputStream()) {
            org.apache.commons.io.IOUtils.copy(Objects.requireNonNull(is), os);
            os.flush(); // Đảm bảo dữ liệu được ghi vào response
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    XuatXu xuatXuFromRow(Row row) {
        XuatXu xuatXu = new XuatXu();
        try {
            String ten = row.getCell(0).toString();
            xuatXu.setTen(ten);
            double tt = row.getCell(1).getNumericCellValue();
            xuatXu.setTrangthai((int)tt);
            return xuatXu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    List<XuatXu> ListXsFromFileXs(MultipartFile file) throws IOException {
        List<XuatXu> xuatXus = new ArrayList<>();
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
                XuatXu xuatXu = xuatXuFromRow(row);
                if (xuatXu != null) {
                    xuatXus.add(xuatXu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xuatXus;
    }

    void exportFileXs() {
        List<XuatXu> xuatXus = xuatXuDAO.findAllByOrderByMaDesc();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("XuatSu");

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
        for (XuatXu xuatXu : xuatXus
        ) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(xuatXu.getMa());
            row.createCell(1).setCellValue(xuatXu.getTen());
            row.createCell(2).setCellValue(xuatXu.getTrangthai());
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
