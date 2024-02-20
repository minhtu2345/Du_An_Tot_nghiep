package com.example.demo.schedule;

import com.example.demo.entity.ChuongTrinhGiamGiaSP;
import com.example.demo.entity.GiamGiaHoaDon;
import com.example.demo.repository.ChuongTrinhGiamGiaSPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GiamGiaSpSchedule {
    @Autowired
    ChuongTrinhGiamGiaSPRepository ggspRepo;

    @Scheduled(cron = "0 * * * * ?") // chạy mỗi phút
    public void updateExpiredGiamGiaHoaDon() {
        Date currentDate = new Date();
        List<ChuongTrinhGiamGiaSP> updateTT = ggspRepo
                .findAll()
                .stream()
                .filter(ggsp -> ggsp.getNgayKetThuc().before(currentDate))
                .collect(Collectors.toList());

        updateTT.forEach(ggsp -> {
            ggsp.setTrangThai(0); // Cập nhật trạng thái thành 0
        });

        ggspRepo.saveAll(updateTT);
    }
}
