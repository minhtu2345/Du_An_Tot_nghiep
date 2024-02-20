package com.example.demo.RestController;

import com.example.demo.entity.KichCo;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.KichCoDAO;
import com.example.demo.repository.KichCoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/kichco")
public class KichCoRestController {
    @Autowired
    KichCoDAO kichCoDAO;

    @GetMapping()
    public List<KichCo> getListKichCo() {
        return kichCoDAO.findAll();
    }
    @GetMapping("/phantrang")
    public PageDTO<KichCo> getPageKichCo(@RequestParam("page") Optional<Integer> page) {
        Pageable pageable= PageRequest.of(page.orElse(0),5);
        return new PageDTO<>(kichCoDAO.findAll(pageable));
    }
    @GetMapping("/{ma}")
    public KichCo getKichCoByMa(@PathVariable("ma") String ma) {
        return kichCoDAO.findKichCoByMa(ma);
    }

    @PostMapping()
    public KichCo createKichCo(@RequestBody KichCo KichCo) {
        return kichCoDAO.save(KichCo);
    }

    @PutMapping("/{ma}")
    public KichCo updateKichCo(@PathVariable("ma") String ma, @RequestBody KichCo KichCo) {
        return kichCoDAO.save(KichCo);
    }

    @DeleteMapping("/{ma}")
    public void delete(@PathVariable("ma") String ma) {
        kichCoDAO.deleteKichCoByMa(ma);
    }
}
