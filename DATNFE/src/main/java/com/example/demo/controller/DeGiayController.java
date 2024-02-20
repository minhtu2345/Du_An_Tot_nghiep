package com.example.demo.controller;

import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.DeGiay;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.*;
import com.example.demo.repository.DeGiayRepo;
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
public class DeGiayController {
    @Autowired
    DeGiayRepo deGiayRepo;
    @RequestMapping("/admin/degiay")
    public String DeGiay(@ModelAttribute("degiay") DeGiay DeGiay, @RequestParam("page") Optional<Integer> page,Model model) {
       PageDTO<DeGiay> page1 = deGiayRepo.getPageDeGiay(page.orElse(0));
       model.addAttribute("i",0);
       model.addAttribute("listPDeGiay",page1);
        return "product/de_giay";
    }

    @PostMapping("/admin/degiay/create")
    public String createDeGiay(@ModelAttribute("degiay") DeGiay DeGiay) {
        deGiayRepo.createDeGiay(DeGiay);
        return "redirect:/admin/degiay";
    }
    @PostMapping("/admin/degiay/update/{x}")
    public String updateDeGiay(@PathVariable("x") String ma,@ModelAttribute("degiay") DeGiay DeGiay) {
        deGiayRepo.updateDeGiay(ma,DeGiay);
        return "redirect:/admin/degiay";
    }
    @RequestMapping ("/admin/degiay/delete/{x}")
    public String deleteDeGiay(@PathVariable("x") String ma) {
        deGiayRepo.delete(ma);
        return "redirect:/admin/degiay";
    }

    @RequestMapping("/admin/degiay/detail/{ma}")
    public String createDeGiay(@PathVariable("ma") String ma,  @RequestParam("page") Optional<Integer> page,Model model) {
        PageDTO<DeGiay> page1 = deGiayRepo.getPageDeGiay(page.orElse(0));
        model.addAttribute("listPDeGiay",page1);
        model.addAttribute("degiay", deGiayRepo.getDeGiayByMa(ma));
        return "product/de_giay";
    }

    private static String[] columns = {"Mã", "Tên","Trạng thái"};
    @Autowired
    DeGiayDAO deGiayDAO;
    final String exportFilePath = "src/main/resources/excel/exportDg.xlsx";

    @ResponseBody
    @PostMapping("/admin/degiay/import")
    public Boolean importExcel(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            List<DeGiay> deGiays = ListDgFromFileDg(file);
            deGiays.stream().forEach(item -> {
                item.setMa(deGiayDAO.generateNextMaDg());
                deGiayDAO.save(item);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("/admin/degiay/export")
    public void downFileExport(HttpServletResponse response) throws IOException {
        exportFileCl();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ListDgExport.xlsx");
        try (InputStream is = new FileInputStream(exportFilePath);
             OutputStream os = response.getOutputStream()) {
            org.apache.commons.io.IOUtils.copy(Objects.requireNonNull(is), os);
            os.flush(); // Đảm bảo dữ liệu được ghi vào response
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    DeGiay deGiayFromRow(Row row) {
        DeGiay deGiay = new DeGiay();
        try {
            String ten = row.getCell(0).toString();
            deGiay.setTen(ten);
            double tt = row.getCell(1).getNumericCellValue();
            deGiay.setTrangthai((int)tt);
            return deGiay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    List<DeGiay> ListDgFromFileDg(MultipartFile file) throws IOException {
        List<DeGiay> deGiays = new ArrayList<>();
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
                DeGiay deGiay = deGiayFromRow(row);
                if (deGiay != null) {
                    deGiays.add(deGiay);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deGiays;
    }

    void exportFileCl() {
        List<DeGiay> deGiays = deGiayDAO.findAllByOrderByMaDesc();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DeGiay");

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
        for (DeGiay deGiay : deGiays
        ) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(deGiay.getMa());
            row.createCell(1).setCellValue(deGiay.getTen());
            row.createCell(2).setCellValue(deGiay.getTrangthai());
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

    @ModelAttribute("listDeGiay")
    public List<DeGiay> getListDeGiay() {
        return deGiayRepo.getListDeGiay();
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
