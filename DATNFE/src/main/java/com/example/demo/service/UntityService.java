package com.example.demo.service;

import com.example.demo.entity.HoaDon;
import com.example.demo.repository.HoaDonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class UntityService {
    @Autowired
    HoaDonDAO hoaDonDAO;
    public String genMaHoaDon(){
        String maHD="HD";
        List<HoaDon> list =  new ArrayList<>();
        list = hoaDonDAO.findAll();
        Collections.sort(list, new Comparator<HoaDon>() {
            @Override
            public int compare(HoaDon o1, HoaDon o2) {
                int num1 = Integer.parseInt(o1.getMa().substring(2)); // lấy phần số của chuỗi thứ nhất
                int num2 = Integer.parseInt(o2.getMa().substring(2)); // lấy phần số của chuỗi thứ hai

                return Integer.compare(num1, num2);
            }
        });
        if (list.isEmpty()){
            maHD = "HD1";
        }else {
            for (int i = 0; i < list.size(); i++) {
                if (i==list.size()-1){
                    int num1 = Integer.parseInt(list.get(i).getMa().substring(2)) +1;
                    maHD = maHD + String.valueOf(num1);
                }
            }
        }
        return maHD;
    }
}
