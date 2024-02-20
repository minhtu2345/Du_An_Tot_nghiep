package com.example.demo.RestController;

import com.example.demo.entity.NhanVien;
import com.example.demo.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/nhanvien")
public class NhanVienRestController {

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping()
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(nhanVienService.getList());
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0") Integer number) {
        return ResponseEntity.ok(nhanVienService.getPage(number));
    }

    @GetMapping("/{ma}")
    public ResponseEntity<?> getByMa(@PathVariable("ma") String ma) {
        return ResponseEntity.ok(nhanVienService.getByMa(ma));
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody NhanVien nhanVien) {
        return ResponseEntity.ok(nhanVienService.create(nhanVien));
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody NhanVien nhanVien) {
        return ResponseEntity.ok(nhanVienService.update(nhanVien));
    }

    @DeleteMapping("/{ma}")
    public ResponseEntity<?> deleteByMa(@PathVariable("ma") String ma) {
        return ResponseEntity.ok(nhanVienService.deleteByMa(ma));
    }
}
