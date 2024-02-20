package com.example.demo.repository;

import com.example.demo.entity.ChucVu;
import com.example.demo.entity.NhanVien;
import com.example.demo.entity.PageDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class NhanVienRepository {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/nhanvien";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    public List<NhanVien> getList() {
        ResponseEntity<List<NhanVien>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<NhanVien>>() {
                });
        return response.getBody();
    }

    public PageDTO<NhanVien> getPage(String number) {
        ResponseEntity<PageDTO<NhanVien>> response =
                restTemplate.exchange(getUrl("page?number="+number), HttpMethod.GET, null, new ParameterizedTypeReference<PageDTO<NhanVien>>() {
                });
        return response.getBody();
    }

    public NhanVien getByMa(String ma) {
        return restTemplate.getForObject(getUrl(ma), NhanVien.class);
    }

    public Boolean create(NhanVien nhanVien) {
        try {
            HttpEntity<NhanVien> entity = new HttpEntity<>(nhanVien);
            return restTemplate.postForObject(url, entity, Boolean.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Boolean update(NhanVien nhanVien) {
        try {
            HttpEntity<NhanVien> entity = new HttpEntity<>(nhanVien);
            restTemplate.put(url, nhanVien);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Boolean delete(String ma) {
        try {
            restTemplate.delete(getUrl(ma));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
