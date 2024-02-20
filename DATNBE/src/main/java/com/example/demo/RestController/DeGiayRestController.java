package com.example.demo.RestController;

import com.example.demo.entity.DeGiay;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.DeGiayDAO;
import com.example.demo.repository.DeGiayDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/degiay")
public class DeGiayRestController {
    @Autowired
    DeGiayDAO deGiayDAO;

    @GetMapping()
    public List<DeGiay> getListDeGiay() {
        return deGiayDAO.findAll();
    }
    @GetMapping("/phantrang")
    public PageDTO<DeGiay> getPageDeGiay(@RequestParam("page") Optional<Integer> page) {
        Pageable pageable= PageRequest.of(page.orElse(0),5);
        return new PageDTO<>(deGiayDAO.findAll(pageable));
    }
    @GetMapping("/{ma}")
    public DeGiay getDeGiayByMa(@PathVariable("ma") String ma) {
        return deGiayDAO.findDeGiayByMa(ma);
    }

    @PostMapping()
    public DeGiay createDeGiay(@RequestBody DeGiay DeGiay) {
        return deGiayDAO.save(DeGiay);
    }

    @PutMapping("/{ma}")
    public DeGiay updateDeGiay(@PathVariable("ma") String ma, @RequestBody DeGiay DeGiay) {
        return deGiayDAO.save(DeGiay);
    }

    @DeleteMapping("/{ma}")
    public void delete(@PathVariable("ma") String ma) {
        deGiayDAO.deleteDeGiayByMa(ma);
    }
}
