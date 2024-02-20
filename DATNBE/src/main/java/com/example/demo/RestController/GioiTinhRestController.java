package com.example.demo.RestController;

import com.example.demo.entity.GioiTinh;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.GioiTinhDAO;
import com.example.demo.repository.GioiTinhDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/gioitinh")
public class GioiTinhRestController {
    @Autowired
    GioiTinhDAO gioiTinhDAO;

    @GetMapping()
    public List<GioiTinh> getListGioiTinh() {
        return gioiTinhDAO.findAll();
    }
    @GetMapping("/phantrang")
    public PageDTO<GioiTinh> getPageGioiTinh(@RequestParam("page") Optional<Integer> page) {
        Pageable pageable= PageRequest.of(page.orElse(0),5);
        return new PageDTO<>(gioiTinhDAO.findAll(pageable));
    }
    @GetMapping("/{ma}")
    public GioiTinh getGioiTinhByMa(@PathVariable("ma") String ma) {
        return gioiTinhDAO.findGioiTinhByMa(ma);
    }

    @PostMapping()
    public GioiTinh createGioiTinh(@RequestBody GioiTinh GioiTinh) {
        return gioiTinhDAO.save(GioiTinh);
    }

    @PutMapping("/{ma}")
    public GioiTinh updateGioiTinh(@PathVariable("ma") String ma, @RequestBody GioiTinh GioiTinh) {
        return gioiTinhDAO.save(GioiTinh);
    }

    @DeleteMapping("/{ma}")
    public void delete(@PathVariable("ma") String ma) {
        gioiTinhDAO.deleteGioiTinhByMa(ma);
    }
}
