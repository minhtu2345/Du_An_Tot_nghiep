package com.example.demo.controller;

import com.example.demo.entity.ChucVu;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.ChucVuRepository;
import com.example.demo.repository.HoaDonDAO;
import com.example.demo.repository.NhanVienDAO;
import com.example.demo.repository.NhanVienRepository;
import com.example.demo.dto.NhanVienDto;
import com.example.demo.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/admin/nhan-vien")
@Controller
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private HoaDonDAO hoaDonDAO;

    @Autowired
    private ChucVuRepository chucVuRepository;

    @Autowired
    private NhanVienDAO nhanVienDAO;


    private int numberCurrent=0;

    PageDTO<NhanVien> page;

    @GetMapping("")
    public String page(
            @ModelAttribute NhanVien nhanVien,
                       @RequestParam(defaultValue = "0") String number,
                       Model model) {
        nhanVien.setTrangThai(1);
        nhanVien.setNgayNghiViec(String.valueOf(LocalDate.now()));
        numberCurrent=Integer.valueOf(number);
        page=new PageDTO<>(nhanVienService.getPageByTrangThai(1,Integer.valueOf(number)));
        model.addAttribute("page", page);
        return "nhan_vien/nhan_vien/nhan_vien";
    }

    @GetMapping("/find")
    public String find(@RequestParam Optional<String> ma,
                       @RequestParam Optional<String> data,
                       @RequestParam Optional<String> maCv,
                       @ModelAttribute NhanVien nhanVien,
                       Model model,
                       Optional<String> number) {
        nhanVien.setTrangThai(1);
        nhanVien.setNgayNghiViec(String.valueOf(LocalDate.now()));
        page = new PageDTO<>(nhanVienService.findNhanVien(ma,data,maCv, Integer.valueOf(number.orElse("0"))));
        model.addAttribute("page", page);
        model.addAttribute("data", data.orElse(null));
        model.addAttribute("maCv", maCv.orElse(null));
        model.addAttribute("ma", ma.orElse(null));
        return "nhan_vien/nhan_vien/nhan_vien_tim_kiem";
    }

    @GetMapping("/findHoaDon/{maNv}")
    public String findHoaDon(@PathVariable String maNv, @RequestParam(defaultValue = "0") String number, Model model) {
        Pageable pageable = PageRequest.of(Integer.valueOf(number), 5);
        PageDTO<HoaDon> pageDTO = new PageDTO<>(hoaDonDAO.findHdByMaNv(maNv, pageable));
        model.addAttribute("pageHdNv", pageDTO);
        model.addAttribute("nv", nhanVienService.findNvByMaNv(maNv));
        return "nhan_vien/nhan_vien/nhan_vien_hoa_don";
    }

//    @GetMapping("/view-create")
//    public String viewCreate(@ModelAttribute NhanVien nhanVien) {
//        return "nhan_vien/nhan_vien/nhan_vien_view_create";
//    }

    @PostMapping("/create")
    public String create(@ModelAttribute NhanVien nhanVien,Model model,@RequestParam("file") MultipartFile file) {
        Path path = Paths.get("src/main/webapp/images/");
        try {
            if(!file.isEmpty()){
                InputStream inputStream = file.getInputStream();
                String[] duoi=file.getOriginalFilename().split("\\.");
                String nameavt="avatar_nv"+String.valueOf(nhanVienDAO.getMaMax()+1)+"."+(duoi[duoi.length - 1]);
                Files.copy(inputStream,path.resolve(nameavt), StandardCopyOption.REPLACE_EXISTING);
                inputStream.close();
                nhanVien.setAnh(nameavt);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("successMessage", "Thêm mới thành công!");
        nhanVienService.create(nhanVien);
        return "redirect:/admin/nhan-vien";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute NhanVien nhanVien,@RequestParam("file") MultipartFile file) {
        Path path = Paths.get("src/main/webapp/images/");
        try {
            if(!file.isEmpty()){
                InputStream inputStream = file.getInputStream();
                String[] duoi=file.getOriginalFilename().split("\\.");
                String nameavt="avatar_nv"+String.valueOf(nhanVien.getMa().split("NV")[1])+"."+(duoi[duoi.length - 1]);
                Files.copy(inputStream,path.resolve(nameavt), StandardCopyOption.REPLACE_EXISTING);
                inputStream.close();
                nhanVien.setAnh(nameavt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        nhanVienService.update(nhanVien);
        return "redirect:/admin/nhan-vien?number="+numberCurrent;
    }

    @GetMapping("/delete/{ma}")
    public String delete(@PathVariable String ma) {
        nhanVienService.deleteByMa(ma);
        return "redirect:/admin/nhan-vien?number="+numberCurrent;
    }

    @ResponseBody
    @GetMapping("/isExist/{email}")
    public Boolean isExist(@PathVariable String email) {
        NhanVien nv=nhanVienDAO.getNVByEmail(email.trim());
        if(nv!=null){
            return true;
        }else {
            return false;
        }
    }

    @ModelAttribute("listNhanVien")
    public List<NhanVien> getListNhanVien() {
        return nhanVienService.getAll();
    }

    private Authentication authentication;
    @ModelAttribute("nhanVienLogin")
    public NhanVien nhanVienLogin() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        NhanVien nv=nhanVienDAO.getNVByEmail(authentication.getName());
        return nv;
    }

    @ModelAttribute("nhanVienDto")
    public NhanVienDto getNhanVienView() {
        return nhanVienService.getNhanVienView();
    }

    @ModelAttribute("listChucVu")
    public List<ChucVu> getListChucVu() {
        return chucVuRepository.getList();
    }
}
