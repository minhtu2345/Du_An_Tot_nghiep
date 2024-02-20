package com.example.demo.RestController;

import com.example.demo.entity.ChucVu;
import com.example.demo.service.ChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/chucvu")
public class ChucVuRestController {

    @Autowired
    private ChucVuService chucVuService;

    @GetMapping()
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(chucVuService.getList());
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0") Integer number) {
        return ResponseEntity.ok(chucVuService.getPage(number));
    }

    @GetMapping("/{ma}")
    public ResponseEntity<?> getByMa(@PathVariable("ma") String ma) {
        return ResponseEntity.ok(chucVuService.getByMa(ma));
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ChucVu chucVu) {
        return ResponseEntity.ok(chucVuService.create(chucVu));
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody ChucVu chucVu) {
        return ResponseEntity.ok(chucVuService.update(chucVu));
    }

    @DeleteMapping("/{ma}")
    public ResponseEntity<?> deleteByMa(@PathVariable("ma") String ma) {
        return ResponseEntity.ok(chucVuService.deleteByMa(ma));
    }
}
