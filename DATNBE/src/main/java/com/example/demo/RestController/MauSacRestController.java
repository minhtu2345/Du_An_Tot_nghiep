package com.example.demo.RestController;

import com.example.demo.entity.MauSac;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.MauSacDAO;
import com.example.demo.repository.MauSacDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/mausac")
public class MauSacRestController {
    @Autowired
    MauSacDAO mauSacDAO;

    @GetMapping()
    public List<MauSac> getListMauSac() {
        return mauSacDAO.findAll();
    }
    @GetMapping("/phantrang")
    public PageDTO<MauSac> getPageMauSac(@RequestParam("page") Optional<Integer> page) {
        Pageable pageable= PageRequest.of(page.orElse(0),5);
        return new PageDTO<>(mauSacDAO.findAll(pageable));
    }
    @GetMapping("/{ma}")
    public MauSac getMauSacByMa(@PathVariable("ma") String ma) {
        return mauSacDAO.findMauSacByMa(ma);
    }

    @PostMapping()
    public MauSac createMauSac(@RequestBody MauSac MauSac) {
        return mauSacDAO.save(MauSac);
    }

    @PutMapping("/{ma}")
    public MauSac updateMauSac(@PathVariable("ma") String ma, @RequestBody MauSac MauSac) {
        return mauSacDAO.save(MauSac);
    }

    @DeleteMapping("/{ma}")
    public void delete(@PathVariable("ma") String ma) {
        mauSacDAO.deleteMauSacByMa(ma);
    }
}
