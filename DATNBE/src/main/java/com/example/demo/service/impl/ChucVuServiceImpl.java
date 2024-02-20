package com.example.demo.service.impl;

import com.example.demo.entity.ChucVu;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.ChucVuDAO;
import com.example.demo.service.ChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChucVuServiceImpl implements ChucVuService {

    @Autowired
    private ChucVuDAO chucVuDAO;

    @Override
    public List<ChucVu> getList() {
        return chucVuDAO.findAll();
    }

    @Override
    public PageDTO<ChucVu> getPage(Integer number) {
        return new PageDTO<ChucVu>(chucVuDAO.findAll(PageRequest.of(number,5)));
    }

    @Override
    public ChucVu getByMa(String ma) {
        return chucVuDAO.findByMa(ma);
    }

    @Override
    public Boolean create(ChucVu chucVu) {
        try {
            chucVu.setId(null);
            chucVuDAO.save(chucVu);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(ChucVu chucVu) {
        try {
            ChucVu cv = chucVuDAO.findByMa(chucVu.getMa());
            cv.setTen(chucVu.getTen());
            cv.setTrangThai(chucVu.getTrangThai());
            chucVuDAO.save(cv);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean deleteByMa(String ma) {
        try {
            chucVuDAO.deleteByMa(ma);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
