package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.repository.ThuongHieuRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Controller
public class ThuongHieuController {
    @Autowired
    ThuongHieuRepo thuongHieuRepo;
    @Autowired
    ThuongHieuDAO thuongHieuDAO;
    @RequestMapping("/admin/thuonghieu")
    public String ThuongHieu(@ModelAttribute("thuonghieu") ThuongHieu ThuongHieu, @RequestParam("page") Optional<Integer> page,Model model) {
       PageDTO<ThuongHieu> page1 = thuongHieuRepo.getPageThuongHieu(page.orElse(0));
       model.addAttribute("i",0);
       model.addAttribute("listPThuongHieu",page1);
        return "product/thuong_hieu";
    }

    @PostMapping("/admin/thuonghieu/create")
    public String createThuongHieu(@ModelAttribute("thuonghieu") ThuongHieu thuongHieu,HttpServletRequest request,@RequestPart("file") MultipartFile file) {
        Path path = Paths.get("src/main/webapp/images/");
        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream,path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
        thuongHieu.setTen_url(file.getOriginalFilename());
        thuongHieuRepo.createThuongHieu(thuongHieu);
        return "redirect:/admin/thuonghieu";
    }
    @PostMapping("/admin/thuonghieu/update/{x}")
    public String updateThuongHieu(@PathVariable("x") String ma,@ModelAttribute("thuonghieu") ThuongHieu ThuongHieu) {
        thuongHieuRepo.updateThuongHieu(ma,ThuongHieu);
        return "redirect:/admin/thuonghieu";
    }
    @RequestMapping ("/admin/thuonghieu/delete/{x}")
    public String deleteThuongHieu(@PathVariable("x") String ma) {
        thuongHieuRepo.delete(ma);
        return "redirect:/admin/thuonghieu";
    }

    @RequestMapping("/admin/thuonghieu/detail/{ma}")
    public String createThuongHieu(@PathVariable("ma") String ma,  @RequestParam("page") Optional<Integer> page,Model model) {
        PageDTO<ThuongHieu> page1 = thuongHieuRepo.getPageThuongHieu(page.orElse(0));
        model.addAttribute("listPThuongHieu",page1);
        model.addAttribute("thuonghieu", thuongHieuRepo.getThuongHieuByMa(ma));
        return "product/thuong_hieu";
    }

    @ModelAttribute("listThuongHieu")
    public List<ThuongHieu> getListThuongHieu() {
        return thuongHieuRepo.getListThuongHieu();
    }
    @PostMapping("/admin/thuonghieu/createanh")
    public String createanhth(Model model, HttpServletRequest request, @RequestParam("idth") UUID idth, @RequestPart("ten_url1") MultipartFile file) throws IOException, ServletException {
        Path path = Paths.get("src/main/webapp/images/");
        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream,path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
       ThuongHieu thuongHieu = thuongHieuDAO.findById(idth).get();
        thuongHieu.setTen_url(file.getOriginalFilename());
        return "product/thuong_hieu";
    }


    private static String[] columns = {"Mã", "Tên","Trạng thái"};
    final String exportFilePath = "src/main/resources/excel/exportTh.xlsx";

    @ResponseBody
    @PostMapping("/admin/thuonghieu/import")
    public Boolean importExcel(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            List<ThuongHieu> thuongHieus = ListThFromFileTh(file);
            thuongHieus.stream().forEach(item -> {
                item.setMa(thuongHieuDAO.generateNextMaTh());
                thuongHieuDAO.save(item);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("/admin/thuonghieu/export")
    public void downFileExport(HttpServletResponse response) throws IOException {
        exportFileTh();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ListThExport.xlsx");
        try (InputStream is = new FileInputStream(exportFilePath);
             OutputStream os = response.getOutputStream()) {
            org.apache.commons.io.IOUtils.copy(Objects.requireNonNull(is), os);
            os.flush(); // Đảm bảo dữ liệu được ghi vào response
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ThuongHieu thuongHieuFromRow(Row row) {
        ThuongHieu thuongHieu = new ThuongHieu();
        try {
            String ten = row.getCell(0).toString();
            thuongHieu.setTen(ten);
            double tt = row.getCell(1).getNumericCellValue();
            thuongHieu.setTrangthai((int)tt);
            return thuongHieu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    List<ThuongHieu> ListThFromFileTh(MultipartFile file) throws IOException {
        List<ThuongHieu> thuongHieus = new ArrayList<>();
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
                ThuongHieu thuongHieu = thuongHieuFromRow(row);
                if (thuongHieu != null) {
                    thuongHieus.add(thuongHieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thuongHieus;
    }

    void exportFileTh() {
        List<ThuongHieu> thuongHieus = thuongHieuDAO.findAllByOrderByMaDesc();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ThuongHieu");

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
        for (ThuongHieu thuongHieu : thuongHieus
        ) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(thuongHieu.getMa());
            row.createCell(1).setCellValue(thuongHieu.getTen());
            row.createCell(2).setCellValue(thuongHieu.getTrangthai());
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
