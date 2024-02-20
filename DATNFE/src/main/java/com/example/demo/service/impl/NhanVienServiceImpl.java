package com.example.demo.service.impl;

import com.example.demo.dto.NhanVienDto;
import com.example.demo.entity.NhanVien;
import com.example.demo.repository.NhanVienDAO;
import com.example.demo.repository.NhanVienRepository;
import com.example.demo.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienDAO nhanVienDAO;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<NhanVien> getAll() {
        return nhanVienRepository.getList();
    }

    @Override
    public Boolean create(NhanVien nhanVien) {
        nhanVien.setMa(genMaNvMoi());
        nhanVien.setMatKhau(passwordEncoder.encode(nhanVien.getMatKhau()));
        return nhanVienRepository.create(nhanVien);
    }

    @Override
    public Boolean update(NhanVien nhanVien) {
        if(nhanVien.getNgayNghiViec()!=null && !nhanVien.getNgayNghiViec().trim().isEmpty()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ngayNghiViecDate = LocalDate.parse(nhanVien.getNgayNghiViec(), formatter);
            LocalDate currentDate = LocalDate.now();
            if(ngayNghiViecDate.isBefore(currentDate)){
                nhanVien.setTrangThai(0);
            }
        }
        return nhanVienRepository.update(nhanVien);
    }

    @Override
    public Boolean deleteByMa(String ma) {
        return nhanVienRepository.delete(ma);
    }

    @Override
    public NhanVien findNvByMaNv(String ma) {
        return nhanVienDAO.findNvByMaNv(ma);
    }



    @Override
    public Page<NhanVien> findNhanVien(Optional<String> ma, Optional<String> data, Optional<String> maCv, Integer number) {
        Pageable pageable = PageRequest.of(number, 5);
        String maF= ma.get().trim().isEmpty()?null:ma.get().trim();
        String dataF= data.get().trim().isEmpty()?null:data.get().trim();
        String maCvF= maCv.get().trim().isEmpty()?null:maCv.get();
        return nhanVienDAO.findNhanVien(
                maF,
                dataF,
                maCvF,
                pageable
        );
    }

    @Override
    public Page<NhanVien> getPageByTrangThai(Integer tt, Integer number) {
        Pageable pageable = PageRequest.of(number, 5);
        return nhanVienDAO.getAllByTrangThai(tt,pageable);
    }

    @Override
    public NhanVienDto getNhanVienView() {
        return nhanVienDAO.getNhanVienDto();
    }

    @Override
    public String genMaNvMoi() {
        Integer maxMaNumber=nhanVienDAO.getMaMax();
        Integer nextNumber;
        if(maxMaNumber==null){
            nextNumber=1;
        }else {
            nextNumber=maxMaNumber+1;
        }
        return "NV"+String.valueOf(nextNumber);
    }


}
