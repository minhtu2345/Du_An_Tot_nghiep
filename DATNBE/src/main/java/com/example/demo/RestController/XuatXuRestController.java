package com.example.demo.RestController;

import com.example.demo.entity.PageDTO;
import com.example.demo.entity.XuatXu;
import com.example.demo.repository.XuatXuDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/xuatxu")
public class XuatXuRestController {
    @Autowired
    XuatXuDAO xuatXuDAO;

    @GetMapping()
    public List<XuatXu> getListXuatXu() {
        return xuatXuDAO.findAll();
    }
    @GetMapping("/phantrang")
    public PageDTO<XuatXu> getPageXuatXu(@RequestParam("page") Optional<Integer> page) {
        Pageable pageable= PageRequest.of(page.orElse(0),5);
        return new PageDTO<>(xuatXuDAO.findAll(pageable));
    }
    @GetMapping("/{ma}")
    public XuatXu getXuatXuByMa(@PathVariable("ma") String ma) {
        return xuatXuDAO.findXuatXuByMa(ma);
    }

    @PostMapping()
    public XuatXu createXuatXu(@RequestBody XuatXu XuatXu) {
        return xuatXuDAO.save(XuatXu);
    }

    @PutMapping("/{ma}")
    public XuatXu updateXuatXu(@PathVariable("ma") String ma, @RequestBody XuatXu XuatXu) {
        return xuatXuDAO.save(XuatXu);
    }

    @DeleteMapping("/{ma}")
    public void delete(@PathVariable("ma") String ma) {
        xuatXuDAO.deleteXuatXuByMa(ma);
    }
}
