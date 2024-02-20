package com.example.demo.RestController;

import com.example.demo.entity.DiaChi;
import com.example.demo.entity.KhachHang;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.DiaChiRepo;
import com.example.demo.repository.KhachHangRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/khachhang")
public class KhachHangConntroller {

    @Autowired
    KhachHangRepo khachHangRepo;

    @Autowired
    DiaChiRepo diaChiRepo;

    @GetMapping()
    public ResponseEntity<?> getAll()  {
        return ResponseEntity.ok(khachHangRepo.findAll());
    }

    @GetMapping("/phantrang")
    public PageDTO<KhachHang> getPagekhachhang(@RequestParam("page") Optional<Integer> page,
                                                @RequestParam("keyword") Optional<String> keyword) {
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<KhachHang> khachHangPage;
        if (keyword != null) {
            khachHangPage = khachHangRepo.search("%" +keyword.get() + "%", pageable);
        } else {
            khachHangPage = khachHangRepo.findAll(pageable);
        }

        return new PageDTO<>(khachHangPage);

    }


    @GetMapping("/{ma}")
    public ResponseEntity<?> getkhachhangByma(@PathVariable("ma") String ma) {
        return ResponseEntity.ok(khachHangRepo.getkhachhanByma(ma));
    }


    @PostMapping()
    public ResponseEntity<?> save(@RequestBody  KhachHang kh) {
        return ResponseEntity.ok(khachHangRepo.save(kh));
    }

    @DeleteMapping("/{ma}")
    public void delete(@PathVariable("ma") UUID ma) {
         khachHangRepo.deleteById(ma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody KhachHang khachHang,@PathVariable("id")  String id) {
        return ResponseEntity.ok(khachHangRepo.save(khachHang));
    }

//    @PutMapping("/{ma}")
//    public ResponseEntity<?> update(@RequestBody DiaChi diacchi, @PathVariable("ma")  String ma) {
//        return ResponseEntity.ok(diaChiRepo.save(diacchi));
//    }
}
