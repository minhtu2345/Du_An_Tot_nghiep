package com.example.demo.RestController;

import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.HoaDonChiTietDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/hoadonchitiet")
public class HoaDonChiTietRestController {

    @Autowired
    private HoaDonChiTietDAO hoaDonChiTietDAO;

    // Get list hóa đơn
    @GetMapping()
    public List<HoaDonChiTiet> getListHoaDonChiTiet() {
        return hoaDonChiTietDAO.findAll();
    }

    // phân trang hóa đơn chi tiết
    @GetMapping("/phantrang")
    public PageDTO<HoaDonChiTiet> getPageHD(@RequestParam("page1") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        return new PageDTO<>(hoaDonChiTietDAO.findAll(pageable));
    }

    //tạo mới hóa đơn chi tiết
    @PostMapping("/create")
    public HoaDonChiTiet createHoaDon(@RequestBody HoaDonChiTiet hoaDonChiTiet) {
        return hoaDonChiTietDAO.save(hoaDonChiTiet);
    }

    //lưu danh sách hóa đơn chi tiết
    @PostMapping("/createList")
    public List<HoaDonChiTiet> createHoaDonChiTietList(@RequestBody List<HoaDonChiTiet> hoaDonChiTietList) {
        return hoaDonChiTietDAO.saveAll(hoaDonChiTietList);
    }

    //update hóa đơn chi tiết theo id
    @PostMapping("/{id}")
    public HoaDonChiTiet updateHoaDon(@PathVariable("id") UUID id, @RequestBody HoaDonChiTiet hoaDonChiTiet) {
        return hoaDonChiTietDAO.save(hoaDonChiTiet);
    }

    // xóa hóa đơn chi tiết theo id
    @RequestMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        hoaDonChiTietDAO.deleteById(id);
    }

    // Tìm hóa đơn chi tiết theo mã hóa đơn page
    @GetMapping("/{maHD}")
    public PageDTO<HoaDonChiTiet> findHDCTByMaHD(
            @PathVariable("maHD") String maHD,
            @RequestParam("page") Optional<Integer> page) {

        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        return new PageDTO<>(hoaDonChiTietDAO.findByHoaDonMaPage(maHD, pageable));
    }

    // Tìm hóa đơn chi tiết theo mã hóa đơn list
    @GetMapping("/list/{maHD}")
    public List<HoaDonChiTiet> findHDCTByMaHD(@PathVariable("maHD") String maHD) {
        return hoaDonChiTietDAO.findByHoaDonMaList(maHD);
    }

    // Xóa hóa đơn chi tiết theo id của giày chi tiết
    @Transactional
    @RequestMapping("delete/{idhd}/{idctsp}")
    public void deleteHDCTByGCT(@PathVariable("idhd") UUID idhd,@PathVariable("idctsp") UUID idctsp) {
        hoaDonChiTietDAO.deleteByHoaDonIdAndChiTietId(idhd,idctsp);
    }
}
