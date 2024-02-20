package com.example.demo.RestController;

import com.example.demo.entity.Giay;
import com.example.demo.repository.ChuongTrinhGiamGiaChiTietSanPhamRepo;
import com.example.demo.repository.ChuongTrinhGiamGiaSPRepository;
import com.example.demo.repository.GiayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rest/giay")
public class GiayRestController {

    @Autowired
    private GiayRepository repository;

    @GetMapping("/hien-thi")        // http://localhost:2020/rest/giay/hien-thi
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/add")        // http://localhost:2020/rest/giay/add
    public ResponseEntity<?> addGiay(@RequestBody Giay giay){
        return ResponseEntity.ok(repository.save(giay));
    }

    @PutMapping("/update/{id}")        // http://localhost:2020/rest/giay/update
    public ResponseEntity<?> updateGiay(@RequestBody Giay giay, @PathVariable UUID id){
        Optional<Giay> op = repository.findById(id);
        op.map(o-> {
            o.setMa(giay.getMa());
            o.setGiaban(giay.getGiaban());
            o.setTen(giay.getTen());
            o.setTrangthai(giay.getTrangthai());
            return repository.save(o);
        }).orElse(null);
        return ResponseEntity.ok(op);
    }

//    @DeleteMapping("/delete/{id}")     // http://localhost:2020/rest/giay/delete
//    public ResponseEntity<?> deleteGiay(@RequestBody Giay giay, @PathVariable UUID id) {
//        Optional<Giay> op = repository.findById(id);
//        if (op.isPresent()) {
//            Giay g = op.get();
//            repository.delete(g);
//            return ResponseEntity.ok("Delete Successfully!");
//        } else {
//            return ResponseEntity.ok("Failed!");
//        }
//    }

//    @DeleteMapping("/delete/{id}")     // http://localhost:2020/giay-restcontroller/delete
//    public ResponseEntity<?> deleteGiay(@RequestBody Giay giay, @PathVariable UUID id) {
//        return ResponseEntity.ok(repository.deleteGiayById(id));
//    }
    //Không được xóa vì liên quan khóa phụ


}
