package com.example.demo.RestController;

import com.example.demo.entity.DiaChi;
import com.example.demo.entity.GiamGiaHoaDon;
import com.example.demo.repository.DiaChiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/diachi")
public class DiaChiController {

    @Autowired
    DiaChiRepo diaChiRepo;


    @GetMapping()
    public List<DiaChi> getall() {
        return diaChiRepo.findAll();
    }

    @PostMapping()
    public DiaChi save(@RequestBody DiaChi diaChi) {
        return diaChiRepo.save(diaChi);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getdiachiByma(@PathVariable("id") UUID ma) {
        return ResponseEntity.ok(diaChiRepo.getDiaChiByid(ma));
    }

    @DeleteMapping("/{ma}")
    public void delete(@PathVariable("ma") UUID ma) {
        diaChiRepo.deleteById(ma);
    }


    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody DiaChi diacchi, @PathVariable("id")  String id) {
        return ResponseEntity.ok(diaChiRepo.save(diacchi));
    }

    @GetMapping("/getDiaChiByKH/{ma}")
    public List<DiaChi> getDCbyKH(@PathVariable("ma") String ma) {
        return diaChiRepo.findDiaChiByMaKhachHang(ma);
    }
    @GetMapping("/getDiaChiByMa/{maDC}")
    public DiaChi getDCbyMa(@PathVariable("maDC") String ma) {
        return diaChiRepo.findDiaChiByByMa(ma);
    }
}
