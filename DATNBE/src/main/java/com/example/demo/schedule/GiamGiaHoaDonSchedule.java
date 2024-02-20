package com.example.demo.schedule;

import com.example.demo.entity.GiamGiaHoaDon;
import com.example.demo.repository.GiamGiaHoaDonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
class GiamGiaHoaDonScheDule {

    @Autowired
    GiamGiaHoaDonDAO giamGiaHoaDonDAO;

    @Scheduled(cron = "0 * * * * ?") // chạy mỗi phút
    public void updateExpiredGiamGiaHoaDon() {
        Date currentDate = new Date();

        List<GiamGiaHoaDon> expiredGiamGiaHoaDon = giamGiaHoaDonDAO
                .findAll()
                .stream()
                .filter(gghd -> gghd.getNgay_ket_thuc().before(currentDate))
                .collect(Collectors.toList());

        expiredGiamGiaHoaDon.forEach(gghd -> {
            gghd.setTrangthai(0); // Cập nhật trạng thái thành 0
        });

        giamGiaHoaDonDAO.saveAll(expiredGiamGiaHoaDon);
    }
}
