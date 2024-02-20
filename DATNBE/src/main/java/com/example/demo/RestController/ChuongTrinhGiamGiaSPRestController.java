package com.example.demo.RestController;

import com.example.demo.entity.ChuongTrinhGiamGiaSP;
import com.example.demo.entity.GiamGiaHoaDon;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.ChuongTrinhGiamGiaSPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/chuong-trinh-giam-gia-sp")
public class ChuongTrinhGiamGiaSPRestController {

    @Autowired
    private ChuongTrinhGiamGiaSPRepository chuongTrinhGiamGiaSPRepository;

    @GetMapping()        // http://localhost:2020/rest/chuong-trinh-giam-gia-sp
    public ResponseEntity<?> getAllVoucher() {
        return ResponseEntity.ok(chuongTrinhGiamGiaSPRepository.findAll());
    }


//    /SOURCE CODE ĐÚNG
//    @GetMapping("/phantrang")
//    public PageDTO<ChuongTrinhGiamGiaSP> getPageGiamGiaSP(@RequestParam("page") Optional<Integer> page,
//                                                         @RequestParam("keyword") Optional<String> keyword) {
//        Pageable pageable = PageRequest.of(page.orElse(0), 5);
//        Page<ChuongTrinhGiamGiaSP> giamGiaSPPage;
//        if (keyword != null) {
//            giamGiaSPPage = (Page<ChuongTrinhGiamGiaSP>) chuongTrinhGiamGiaSPRepository.timKiemMaHoacTen("%" +keyword.get() + "%", pageable);
//        } else {
//            giamGiaSPPage = chuongTrinhGiamGiaSPRepository.findAll(pageable);
//        }
//
//        return new PageDTO<>(giamGiaSPPage);
//
//    }

    @GetMapping("/phantrang")
    public PageDTO<ChuongTrinhGiamGiaSP> getPageGiamGiaSP(@RequestParam("page") Optional<Integer> page,
                                                          @RequestParam("keyword") Optional<String> keyword) {
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<ChuongTrinhGiamGiaSP> giamGiaSPPage;
        if (keyword != null) {
            giamGiaSPPage = (Page<ChuongTrinhGiamGiaSP>) chuongTrinhGiamGiaSPRepository.searchMaOrTenOrTrangThai("%" + keyword.get() + "%", pageable);
        } else {
            giamGiaSPPage = chuongTrinhGiamGiaSPRepository.findAll(pageable);
        }

        return new PageDTO<>(giamGiaSPPage);

    }


    @PostMapping()        // http://localhost:2020/rest/chuong-trinh-giam-gia-sp
    public ResponseEntity<?> createVoucher(@RequestBody ChuongTrinhGiamGiaSP chuongTrinhGiamGiaSP) {
        return ResponseEntity.ok(chuongTrinhGiamGiaSPRepository.save(chuongTrinhGiamGiaSP));
    }

    @PutMapping("/update/{id}")        // http://localhost:2020/rest/chuong-trinh-giam-gia-sp/update
    public ResponseEntity<?> updateVoucher(@RequestBody ChuongTrinhGiamGiaSP km, @PathVariable UUID id) {
        Optional<ChuongTrinhGiamGiaSP> op = chuongTrinhGiamGiaSPRepository.findById(id);
        op.map(o -> {
            o.setNgayBatDau(km.getNgayBatDau());
            o.setNgayKetThuc(km.getNgayKetThuc());
            o.setTrangThai(km.getTrangThai());
            o.setPhanTramGiam(km.getPhanTramGiam());
            o.setMaKhuyenMai(km.getMaKhuyenMai());
            o.setTenKhuyenMai(km.getTenKhuyenMai());
            return chuongTrinhGiamGiaSPRepository.save(o);
        }).orElse(null);
        return ResponseEntity.ok(op);
    }

    @GetMapping("/detail/{id}")        // http://localhost:2020/rest/chuong-trinh-giam-gia-sp/detail
    public ResponseEntity<?> detail(@PathVariable UUID id) {
        return ResponseEntity.ok(chuongTrinhGiamGiaSPRepository.findById(id));
    }

    //    //Tìm kiếm theo mã:
    @GetMapping("/findbyma/{ma}")        // http://localhost:2020/rest/chuong-trinh-giam-gia-sp/GGSP01
    public ChuongTrinhGiamGiaSP getCTGGSPByMa(@PathVariable("ma") String ma) {
        return chuongTrinhGiamGiaSPRepository.findByMa(ma);
    }

    //update giảm giá SP theo mã
    @PostMapping("/{ma}")
    public ChuongTrinhGiamGiaSP updateVoucher(@PathVariable("ma") String ma, @RequestBody ChuongTrinhGiamGiaSP updatedGGSP) {
        // Truy vấn đối tượng GiamGiaSP từ cơ sở dữ liệu dựa trên ma
        ChuongTrinhGiamGiaSP ggspUpdate = chuongTrinhGiamGiaSPRepository.findByMa(ma);

        if (ggspUpdate != null) {
            // Cập nhật các thuộc tính của đối tượng đã có từ GGSP
            ggspUpdate.setTenKhuyenMai(updatedGGSP.getTenKhuyenMai());
            ggspUpdate.setPhanTramGiam(updatedGGSP.getPhanTramGiam());
            ggspUpdate.setNgayBatDau(updatedGGSP.getNgayBatDau());
            ggspUpdate.setNgayKetThuc(updatedGGSP.getNgayKetThuc());
            ggspUpdate.setTrangThai(updatedGGSP.getTrangThai());
        }
        return chuongTrinhGiamGiaSPRepository.save(ggspUpdate);
    }

    //Tìm kiếm bằng id
    @GetMapping("/{idKM}")        // http://localhost:2020/rest/chuong-trinh-giam-gia-sp/detail
    public ResponseEntity<?> getOneById(@PathVariable UUID idKM) {
        return ResponseEntity.ok(chuongTrinhGiamGiaSPRepository.findChuongTrinhGiamGiaSPByIdKhuyenMai(idKM));
    }

//    //Update theo mã (method Put)
//    @PutMapping("/{ma}")
//    public ResponseEntity<?> update(@RequestBody ChuongTrinhGiamGiaSP ctggsp, @PathVariable("ma") String ma) {
//        ChuongTrinhGiamGiaSP getOneCTGG = chuongTrinhGiamGiaSPRepository.findByMa(ma);
//
//        getOneCTGG.setTenKhuyenMai(ctggsp.getTenKhuyenMai());
//        getOneCTGG.setNgayBatDau(ctggsp.getNgayBatDau());
//        getOneCTGG.setNgayKetThuc(ctggsp.getNgayKetThuc());
//        getOneCTGG.setPhanTramGiam(ctggsp.getPhanTramGiam());
//        getOneCTGG.setTrangThai(ctggsp.getTrangThai());
//
//        return ResponseEntity.ok(chuongTrinhGiamGiaSPRepository.save(getOneCTGG));
//    }

}
