package com.example.demo.RestController;

import com.example.demo.entity.ChuongTrinhGiamGiaChiTietSP;
import com.example.demo.entity.ChuongTrinhGiamGiaSP;
import com.example.demo.repository.ChuongTrinhGiamGiaChiTietSanPhamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/rest/chuong-trinh-giam-gia-chi-tiet-sp")
public class ChuongTrinhGiamGiaChiTietSPRestController {

    @Autowired
    private ChuongTrinhGiamGiaChiTietSanPhamRepo chuongTrinhGiamGiaChiTietSanPhamRepo;

    @GetMapping("/hien-thi")        // http://localhost:2020/rest/chuong-trinh-giam-gia-chi-tiet-sp/hien-thi
    public ResponseEntity<?> getAllDetailVoucher(){
        return ResponseEntity.ok(chuongTrinhGiamGiaChiTietSanPhamRepo.findAll());
    }

//    @GetMapping("/detail")        // http://localhost:2020/rest/chuong-trinh-giam-gia-chi-tiet-sp/hien-thi
//    public ResponseEntity<?> getOneDetailVoucher(){
//        return ResponseEntity.ok(chuongTrinhGiamGiaChiTietSanPhamRepo.getDetailVoucher());
//    }

    @GetMapping("/detail-sp-by/{idVoucher}")
    public ResponseEntity<?> findByIdVoucher(@PathVariable UUID idVoucher){
        return ResponseEntity.ok(chuongTrinhGiamGiaChiTietSanPhamRepo.findAllSPApdung(idVoucher));
        //List
    }

    @PostMapping()        // http://localhost:2020/rest/chuong-trinh-giam-gia-sp/hien-thi
    public ResponseEntity<?> createCtkm(@RequestBody ChuongTrinhGiamGiaChiTietSP ctkm){
        return ResponseEntity.ok(chuongTrinhGiamGiaChiTietSanPhamRepo.save(ctkm));
    }

}
