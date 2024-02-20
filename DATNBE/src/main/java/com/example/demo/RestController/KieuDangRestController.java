package com.example.demo.RestController;

import com.example.demo.entity.PageDTO;
import com.example.demo.entity.KieuDang;
import com.example.demo.repository.KieuDangDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/kieudang")
public class KieuDangRestController {
    @Autowired
    KieuDangDAO KieuDangDAO;

    @GetMapping()
    public List<KieuDang> getListKieuDang() {
        return KieuDangDAO.findAll();
    }
    @GetMapping("/phantrang")
    public PageDTO<KieuDang> getPageKieuDang(@RequestParam("page") Optional<Integer> page) {
        Pageable pageable= PageRequest.of(page.orElse(0),5);
        return new PageDTO<>(KieuDangDAO.findAll(pageable));
    }
    @GetMapping("/{ma}")
    public KieuDang getKieuDangByMa(@PathVariable("ma") String ma) {
        return KieuDangDAO.findKieuDangByMa(ma);
    }

    @PostMapping()
    public KieuDang createKieuDang(@RequestBody KieuDang KieuDang) {
        return KieuDangDAO.save(KieuDang);
    }

    @PutMapping("/{ma}")
    public KieuDang updateKieuDang(@PathVariable("ma") String ma, @RequestBody KieuDang KieuDang) {
        return KieuDangDAO.save(KieuDang);
    }

    @DeleteMapping("/{ma}")
    public void delete(@PathVariable("ma") String ma) {
        KieuDangDAO.deleteKieuDangByMa(ma);
    }
}
