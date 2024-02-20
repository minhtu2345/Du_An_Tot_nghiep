package com.example.demo.controller;

import com.example.demo.config.Config;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.UserService;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Controller
public class TrangChuController {
    @Autowired
    GiayDAO giayDAO;
    @Autowired
    GiayChiTietDAO giayChiTietDAO;
    @Autowired
    GioHangDAO gioHangDAO;
    @Autowired
    GioHangChiTietDAO gioHangChiTietDAO;
    @Autowired
    KhachHangDao khachHangDao;
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "layout/login";
    }

//    @RequestMapping("/admin/login")
//    public String loginadmin() {
//        return "layout/loginadmin";
//    }
//    @RequestMapping(value = "/admin/login", params = "error")
//    public String loginfailadmin() {
//        return "layout/login";
//    }
//    @RequestMapping(value = "/admin/login", params = "logout")
//    public String logoutadmin() {
//        return "layout/logout";
//    }
    @RequestMapping(value = "/login", params = "error")
    public String loginfail() {
        return "layout/login";
    }

    @RequestMapping(value = "/login", params = "logout")
    public String logout() {
        return "layout/logout";
    }

    @RequestMapping("/oauth2/login/success")
    public String oauth2(OAuth2AuthenticationToken oauth2) {
        userService.loginFromOAuth2(oauth2);
        return "home/index";
    }

    @RequestMapping("/trangchu")
    public String trangchu(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KhachHang khachHang = khachHangDao.getKhByEmail(authentication.getName());

        model.addAttribute("itemsNew", giayDAO.top20SpMoiNhat());
        model.addAttribute("itemsHot", giayDAO.top20SpHotNhat());
        model.addAttribute("active", "trangchu");

        if (khachHang != null) {
            model.addAttribute("khachHang", khachHang);
        }
        return "home/index";
    }

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
        String kich_co = "";
        Integer page = 0;
        BigDecimal tien_min;
        BigDecimal tien_max;
    }

    @RequestMapping("/sanpham")
    public String sanpham(Model model, @ModelAttribute("searchform") SearchForm searchForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KhachHang khachHang = khachHangDao.getKhByEmail(authentication.getName());
        model.addAttribute("khachHang", khachHang);
        System.out.println("Thuong hieu la" + searchForm.thuong_hieu);
        List<GiayChiTiet> giayChiTietList;
        if (searchForm.tien_min == null && searchForm.tien_max == null) {
            giayChiTietList = giayChiTietDAO.getSearchsanphamByTT("%" + searchForm.tensp + "%",
                    BigDecimal.ZERO, BigDecimal.valueOf(999999999),
                    "%" + searchForm.thuong_hieu + "%", "%" + searchForm.kich_co + "%", "%" + searchForm.chat_lieu + "%", "%" + searchForm.xuat_xu + "%",
                    "%" + searchForm.mau_sac + "%", "%" + searchForm.gioi_tinh + "%", "%" + searchForm.kieu_dang + "%", "%" + searchForm.de_giay + "%");
        }
        if (searchForm.tien_min == null) {
            giayChiTietList = giayChiTietDAO.getSearchsanphamByTT("%" + searchForm.tensp + "%",
                    BigDecimal.ZERO, searchForm.tien_max,
                    "%" + searchForm.thuong_hieu + "%", "%" + searchForm.kich_co + "%", "%" + searchForm.chat_lieu + "%", "%" + searchForm.xuat_xu + "%",
                    "%" + searchForm.mau_sac + "%", "%" + searchForm.gioi_tinh + "%", "%" + searchForm.kieu_dang + "%", "%" + searchForm.de_giay + "%");
        }
        if (searchForm.tien_max == null) {
            giayChiTietList = giayChiTietDAO.getSearchsanphamByTT("%" + searchForm.tensp + "%",
                    searchForm.tien_min, BigDecimal.valueOf(999999999),
                    "%" + searchForm.thuong_hieu + "%", "%" + searchForm.kich_co + "%", "%" + searchForm.chat_lieu + "%", "%" + searchForm.xuat_xu + "%",
                    "%" + searchForm.mau_sac + "%", "%" + searchForm.gioi_tinh + "%", "%" + searchForm.kieu_dang + "%", "%" + searchForm.de_giay + "%");

        } else {
            giayChiTietList = giayChiTietDAO.getSearchsanphamByTT("%" + searchForm.tensp + "%",
                    searchForm.tien_min, searchForm.tien_max,
                    "%" + searchForm.thuong_hieu + "%", "%" + searchForm.kich_co + "%", "%" + searchForm.chat_lieu + "%", "%" + searchForm.xuat_xu + "%",
                    "%" + searchForm.mau_sac + "%", "%" + searchForm.gioi_tinh + "%", "%" + searchForm.kieu_dang + "%", "%" + searchForm.de_giay + "%");
        }
        Pageable pageable = PageRequest.of(searchForm.page, 20);
        List<Giay> giayList = new ArrayList<>();
        System.out.println("Mang là" + giayChiTietList.size());

        for (GiayChiTiet x : giayChiTietList
        ) {
            if (giayList.isEmpty()) {
                giayList.add(x.getGiay());
            } else {
                boolean kq = true;
                for (Giay a : giayList
                ) {
                    if (x.getGiay().getMa().equals(a.getMa())) {
                        kq = false;
                    }
                }
                if (kq == true) {
                    giayList.add(x.getGiay());
                }
            }
        }
        model.addAttribute("items", giayList);
        model.addAttribute("active", "sanpham");
        return "home/sanpham";
    }

    @RequestMapping("/contact")
    public String contact(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KhachHang khachHang = khachHangDao.getKhByEmail(authentication.getName());
        model.addAttribute("khachHang", khachHang);
        model.addAttribute("active", "lienhe");
        return "home/contact";
    }

    @RequestMapping("/pdf")
    public String generatePdf(Model model) {
        try {
            generateInvoicePdf();
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý lỗi nếu cần thiết
        }
        return "redirect:/trangchu"; // Hoặc forward:/trangchu tùy thuộc vào yêu cầu của bạn
    }

    public static void generateInvoicePdf() throws IOException {
        try (PdfWriter writer = new PdfWriter("invoice.pdf");
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Sử dụng font không chứa dấu

            // Logo và tên thương hiệu
//            Image logo = new Image(ImageDataFactory.create("E:\\Spring_Boot_Java5\\DATNFE\\src\\main\\webapp\\images\\logodarlin.png")).scaleToFit(200f, 100f);;
            Image logo = new Image(ImageDataFactory.create("C:\\Users\\duc27\\OneDrive\\Máy tính\\DATNHT\\DATNFE\\src\\main\\webapp\\images\\logodarlin.png")).scaleToFit(200f, 100f);
            ;
            float[] columnWidths1 = {150f, 200f, 400f};
            Table headerTable = new Table(columnWidths1);

            Paragraph header = new Paragraph().add(logo);
            header.setPaddingTop(0).setMarginTop(0);
            Cell logoCell = new Cell().add(header);
            logoCell.setBorder(Border.NO_BORDER);
            logoCell.setTextAlignment(TextAlignment.LEFT);
            logoCell.setHorizontalAlignment(HorizontalAlignment.LEFT);
            logoCell.setVerticalAlignment(VerticalAlignment.TOP);
            logoCell.setPaddingTop(0).setMarginTop(0);
            headerTable.addCell(logoCell);

            Paragraph infoWeb = new Paragraph();
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
            Text t = new Text("Darling Shop").setBold().setFontSize(17).setFont(font);
            infoWeb.add(t);
            infoWeb.add("\nlocalhost:8080");
            headerTable.addCell(new Cell().add(infoWeb).setBorder(Border.NO_BORDER));

            Paragraph infoParagraph = new Paragraph();
            infoParagraph.setPaddingTop(0).setMarginTop(0);
            infoParagraph.add(new Text("HOA DON HD03").setBold().setFontSize(23)).add("\n");
            infoParagraph.add(new SimpleDateFormat("HH:mm 'Ngay' dd 'thang' MM 'nam' yyyy").format(new Date()));
            Cell infoCell = new Cell().add(infoParagraph).setTextAlignment(TextAlignment.RIGHT);
            infoCell.setBorder(Border.NO_BORDER);
            infoCell.setTextAlignment(TextAlignment.RIGHT);
            infoCell.setHorizontalAlignment(HorizontalAlignment.RIGHT);
            infoCell.setVerticalAlignment(VerticalAlignment.TOP);
            infoCell.setPaddingTop(0).setMarginTop(0);
            headerTable.addCell(infoCell);
            headerTable.setBorder(Border.NO_BORDER);
            document.add(headerTable);

            // Tiêu đề HÓA ĐƠN
            document.add(new Paragraph("HOA DON \n").setFontSize(32).setBold());
            document.add(new Paragraph("Nguyen Thanh Danh \n").setFontSize(20).setBold());

            // Thông tin khách hàng
            float[] columnWidths2 = {200f, 50f, 50f, 50f};
            Table table2 = new Table(columnWidths2)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER)
                    .setMarginTop(2);
            table2.addCell(
                    new Cell().setBorder(Border.NO_BORDER).add(
                            new Paragraph().add("Tên khách hàng \n So dien thoai khach hang \n Dia chi khach hang")));
            table2.addCell(
                    new Cell().setBorder(Border.NO_BORDER).add(
                            new Paragraph().add("Nguyen Thanh Danh \n 0385090080 \n Ha Nam, Ha Noi")));
            document.add(table2);
            document.add(new Paragraph("\n\n"));
            // Bảng chi tiết sản phẩm
            float[] columnWidths = {200f, 200f, 200f, 200f};
            Table table = new Table(columnWidths)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER);
            table.addHeaderCell(new Cell().add(new Paragraph("Ten SP").setBold().setFontSize(12)).setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("So luong").setBold().setFontSize(12)).setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("Don Gia").setBold().setFontSize(12)).setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("Thanh Tien").setBold().setFontSize(12)).setBorder(Border.NO_BORDER));

            // Thêm sản phẩm (đây chỉ là ví dụ, bạn cần thay thế bằng dữ liệu thực tế)
            int index = 5;
            for (int i = 0; i < index; i++) {
                table.addCell(new Cell().add(new Paragraph("\nSan pham 1\n"))
                        .setBorderLeft(Border.NO_BORDER)
                        .setBorderRight(Border.NO_BORDER)
                        .setBorderBottom(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph("\n2\n"))
                        .setBorderLeft(Border.NO_BORDER)
                        .setBorderRight(Border.NO_BORDER)
                        .setBorderBottom(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph("\n$50\n"))
                        .setBorderLeft(Border.NO_BORDER)
                        .setBorderRight(Border.NO_BORDER)
                        .setBorderBottom(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph("\n$100\n"))
                        .setBorderLeft(Border.NO_BORDER)
                        .setBorderRight(Border.NO_BORDER)
                        .setBorderBottom(Border.NO_BORDER));
            }
            table.setBorder(Border.NO_BORDER);
            document.add(table);

            // Thông tin thanh toán

            float[] columnWidths3 = {200f, 200f, 200f, 200f};
            Table table3 = new Table(columnWidths3)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setBorder(Border.NO_BORDER);
            //dong1
            table3.addCell(new Cell().setBorder(Border.NO_BORDER));
            table3.addCell(new Cell().setBorder(Border.NO_BORDER));
            table3.addCell(
                    new Cell()
                            .setTextAlignment(TextAlignment.RIGHT)
                            .setBorder(Border.NO_BORDER)
                            .add(new Paragraph("Tong tien: ")));
            table3.addCell(
                    new Cell()
                            .setTextAlignment(TextAlignment.LEFT)
                            .setBorder(Border.NO_BORDER)
                            .add(new Paragraph("$130")));

            //dong2
            table3.addCell(new Cell().setBorder(Border.NO_BORDER));
            table3.addCell(new Cell().setBorder(Border.NO_BORDER));
            table3.addCell(
                    new Cell()
                            .setTextAlignment(TextAlignment.RIGHT)
                            .setBorder(Border.NO_BORDER)
                            .add(new Paragraph("Giam gia: ")));
            table3.addCell(
                    new Cell()
                            .setTextAlignment(TextAlignment.LEFT)
                            .setBorder(Border.NO_BORDER)
                            .add(new Paragraph("$10")));

            //dong3
            table3.addCell(new Cell().setBorder(Border.NO_BORDER));
            table3.addCell(new Cell().setBorder(Border.NO_BORDER));
            table3.addCell(
                    new Cell()
                            .setTextAlignment(TextAlignment.RIGHT)
                            .setBorder(Border.NO_BORDER)
                            .add(new Paragraph("Phi Ship: ")));
            table3.addCell(
                    new Cell()
                            .setTextAlignment(TextAlignment.LEFT)
                            .setBorder(Border.NO_BORDER)
                            .add(new Paragraph("$5")));

            //dong4
//            com.itextpdf.kernel.color.Color myColor = new DeviceRgb(255, 100, 20);
            Color color= new DeviceRgb(0,0,128);
            table3.addCell(new Cell().setBorder(Border.NO_BORDER));
            table3.addCell(new Cell().setBorder(Border.NO_BORDER));
            table3.addCell(
                    new Cell()
                            .setTextAlignment(TextAlignment.RIGHT)
                            .setBorder(Border.NO_BORDER)
                            .setFontColor(color)
                            .add(new Paragraph("Tong thanh toan: ").setFontSize(17)));
            table3.addCell(
                    new Cell()
                            .setTextAlignment(TextAlignment.LEFT)
                            .setBorder(Border.NO_BORDER)
                            .setFontColor(color)
                            .add(new Paragraph("$125").setFontSize(17)));
            document.add(new Paragraph("\n\n"));
            document.add(table3);

        }
    }
}
