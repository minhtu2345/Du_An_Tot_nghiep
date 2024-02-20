package com.example.demo.service.impl;

import com.example.demo.entity.NhanVien;
import com.example.demo.entity.PageDTO;
import com.example.demo.repository.NhanVienDAO;
import com.example.demo.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienDAO nhanVienDAO;

    @Override
    public List<NhanVien> getList() {
        return nhanVienDAO.findAll();
    }

    @Override
    public PageDTO<NhanVien> getPage(Integer number) {
        return new PageDTO<NhanVien>(nhanVienDAO.findAll(PageRequest.of(number, 5)));
    }

    @Override
    public NhanVien getByMa(String ma) {
        return nhanVienDAO.findByMa(ma);
    }

    @Override
    public Boolean create(NhanVien nhanVien) {
        try {
            nhanVien.setId(null);
            nhanVienDAO.save(nhanVien);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public Boolean update(NhanVien nhanVien) {
        try {
            NhanVien nv = nhanVienDAO.findByMa(nhanVien.getMa());
//        nv= NhanVien.builder()
//                .hoTen(nhanVien.getHoTen())
//                .ngaySinh(nhanVien.getNgaySinh())
//                .diaChi(nhanVien.getDiaChi())
//                .thanhPho(nhanVien.getThanhPho())
//                .sdt(nhanVien.getSdt())
//                .email(nhanVien.getEmail())
//                .matKhau(nhanVien.getMatKhau())
//                .trangThai(nhanVien.getTrangThai())
//                .chucVu(nhanVien.getChucVu())
//                .build();
            nv.setHoTen(nhanVien.getHoTen());
            nv.setNgaySinh(nhanVien.getNgaySinh());
            nv.setDiaChi(nhanVien.getDiaChi());
            nv.setXa(nhanVien.getXa());
            nv.setHuyen(nhanVien.getHuyen());
            nv.setThanhPho(nhanVien.getThanhPho());
            nv.setSdt(nhanVien.getSdt());
            nv.setEmail(nhanVien.getEmail());
            nv.setMatKhau(nhanVien.getMatKhau());
            nv.setTrangThai(nhanVien.getTrangThai());
            nv.setNgayVaoLam(nhanVien.getNgayVaoLam());
            nv.setNgayNghiViec(nhanVien.getNgayNghiViec());
            nv.setChucVu(nhanVien.getChucVu());
            nv.setAnh(nhanVien.getAnh());
            nhanVienDAO.save(nv);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean deleteByMa(String ma) {
        try {
            nhanVienDAO.deleteByMa(ma);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
