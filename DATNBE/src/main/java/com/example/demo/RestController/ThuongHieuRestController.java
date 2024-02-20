package com.example.demo.RestController;

import com.example.demo.entity.ThuongHieu;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.ThuongHieuDAO;
import com.example.demo.repository.ThuongHieuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/thuonghieu")
public class ThuongHieuRestController {
    @Autowired
    ThuongHieuDAO thuongHieuDAO;

    @GetMapping()
    public List<ThuongHieu> getListThuongHieu() {
        return thuongHieuDAO.findAll();
    }
    @GetMapping("/phantrang")
    public PageDTO<ThuongHieu> getPageThuongHieu(@RequestParam("page") Optional<Integer> page) {
        Pageable pageable= PageRequest.of(page.orElse(0),5);
        return new PageDTO<>(thuongHieuDAO.findAll(pageable));
    }
    @GetMapping("/{ma}")
    public ThuongHieu getThuongHieuByMa(@PathVariable("ma") String ma) {
        return thuongHieuDAO.findThuongHieuByMa(ma);
    }

    @PostMapping()
    public ThuongHieu createThuongHieu(@RequestBody ThuongHieu ThuongHieu) {
        return thuongHieuDAO.save(ThuongHieu);
    }

    @PutMapping("/{ma}")
    public ThuongHieu updateThuongHieu(@PathVariable("ma") String ma, @RequestBody ThuongHieu ThuongHieu) {
        return thuongHieuDAO.save(ThuongHieu);
    }

    @DeleteMapping("/{ma}")
    public void delete(@PathVariable("ma") String ma) {
        thuongHieuDAO.deleteThuongHieuByMa(ma);
    }
}
