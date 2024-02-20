package com.example.demo.RestController;

import com.example.demo.entity.GiayChiTiet;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.GiayChiTietDAO;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/giaychitiet")
public class GiayChiTietRestController {

    @Autowired
    private GiayChiTietDAO giayChiTietDAO;

    // Get list Giày chi tiết
    @GetMapping()
    public List<GiayChiTiet> getListGiayChiTiet() {
        return giayChiTietDAO.findAll();
    }

    // phân trang giày chi tiết
    @GetMapping("/phantrang")
    public PageDTO<GiayChiTiet> getPageHD(@RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        return new PageDTO<>(giayChiTietDAO.findAll(pageable));
    }

    //tạo mới giày chi tiết
    @PostMapping()
    public GiayChiTiet createGiayChiTiet(@RequestBody GiayChiTiet giayChiTiet) {
        return giayChiTietDAO.save(giayChiTiet);
    }

    // xóa giày chi tiết theo id
    @RequestMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        giayChiTietDAO.deleteById(id);
    }

    // Tìm giày chi tiết theo ID
    @GetMapping("/{id}")
    public Optional<GiayChiTiet> getGiayChiTietById(@PathVariable("id") UUID id) {
        return giayChiTietDAO.findById(id);
    }
}
