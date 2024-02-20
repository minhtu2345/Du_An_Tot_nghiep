package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.ChatLieuDAO;
import com.example.demo.repository.ChatLieuRepo;
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
public class ChatLieuController {
    @Autowired
    ChatLieuRepo chatLieuRepo;
    @RequestMapping("/admin/chatlieu")
    public String ChatLieu(@ModelAttribute("chatlieu") ChatLieu ChatLieu, @RequestParam("page") Optional<Integer> page,Model model) {
       PageDTO<ChatLieu> page1 = chatLieuRepo.getPageChatLieu(page.orElse(0));
       model.addAttribute("i",0);
       model.addAttribute("listPChatLieu",page1);
        return "product/chat_lieu";
    }

    @PostMapping("/admin/chatlieu/create")
    public String createChatLieu(@ModelAttribute("chatlieu") ChatLieu ChatLieu) {
        chatLieuRepo.createChatLieu(ChatLieu);
        return "redirect:/admin/chatlieu";
    }
    @PostMapping("/admin/chatlieu/update/{x}")
    public String updateChatLieu(@PathVariable("x") String ma,@ModelAttribute("chatlieu") ChatLieu ChatLieu) {
        chatLieuRepo.updateChatLieu(ma,ChatLieu);
        return "redirect:/admin/chatlieu";
    }
    @RequestMapping ("/admin/chatlieu/delete/{x}")
    public String deleteChatLieu(@PathVariable("x") String ma) {
        chatLieuRepo.delete(ma);
        return "redirect:/admin/chatlieu";
    }

    @RequestMapping("/admin/chatlieu/detail/{ma}")
    public String createChatLieu(@PathVariable("ma") String ma,  @RequestParam("page") Optional<Integer> page,Model model) {
        PageDTO<ChatLieu> page1 = chatLieuRepo.getPageChatLieu(page.orElse(0));
        model.addAttribute("listPChatLieu",page1);
        model.addAttribute("chatlieu", chatLieuRepo.getChatLieuByMa(ma));
        return "product/chat_lieu";
    }

    private static String[] columns = {"Mã", "Tên","Trạng thái"};
    @Autowired
    ChatLieuDAO chatLieuDAO;
    final String exportFilePath = "src/main/resources/excel/exportCl.xlsx";

    @RequestMapping("/admin/chatlieu/export")
    public void downFileExport(HttpServletResponse response) throws IOException {
        exportFileCl();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=ListClExport.xlsx");
        try (InputStream is = new FileInputStream(exportFilePath);
             OutputStream os = response.getOutputStream()) {
            org.apache.commons.io.IOUtils.copy(Objects.requireNonNull(is), os);
            os.flush(); // Đảm bảo dữ liệu được ghi vào response
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @PostMapping("/admin/chatlieu/import")
    public Boolean importExcel(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            List<ChatLieu> chatLieus = ListClFromFileCl(file);
            chatLieus.stream().forEach(item -> {
                item.setMa(chatLieuDAO.generateNextMaCl());
                chatLieuDAO.save(item);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    List<ChatLieu> ListClFromFileCl(MultipartFile file) throws IOException {
        List<ChatLieu> chatLieus = new ArrayList<>();
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
                ChatLieu chatLieu = chatLieuFromRow(row);
                if (chatLieu != null) {
                    chatLieus.add(chatLieu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chatLieus;
    }

    ChatLieu chatLieuFromRow(Row row) {
        try {
            ChatLieu chatLieu = new ChatLieu();

            String ten = row.getCell(0).toString();
            chatLieu.setTen(ten);
            double tt = row.getCell(1).getNumericCellValue();
            chatLieu.setTrangthai((int)tt);

            return chatLieu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    void exportFileCl() {
        List<ChatLieu> chatLieus = chatLieuDAO.findAllByOrderByMaDesc();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ChatLieu");

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
        for (ChatLieu chatLieu : chatLieus
        ) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(chatLieu.getMa());
            row.createCell(1).setCellValue(chatLieu.getTen());
            row.createCell(2).setCellValue(chatLieu.getTrangthai());
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi kết quả ra một tệp Excel
        try (FileOutputStream fileOut = new FileOutputStream("src/main/resources/excel/exportCl.xlsx")) {
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
