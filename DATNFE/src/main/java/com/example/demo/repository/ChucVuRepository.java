package com.example.demo.repository;

import com.example.demo.entity.ChucVu;
import com.example.demo.entity.PageDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class ChucVuRepository {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/chucvu";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    public List<ChucVu> getList() {
        ResponseEntity<List<ChucVu>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ChucVu>>() {
                });
        return response.getBody();
    }

    public PageDTO<ChucVu> getPage(String number) {
        ResponseEntity<PageDTO<ChucVu>> response =
                restTemplate.exchange(getUrl("page?number=" + number), HttpMethod.GET, null, new ParameterizedTypeReference<PageDTO<ChucVu>>() {
                });
        return response.getBody();
    }

    public ChucVu getByMa(String ma) {
        return restTemplate.getForObject(getUrl(ma), ChucVu.class);
    }

    public Boolean create(ChucVu chucVu) {
        try {
            HttpEntity<ChucVu> entity = new HttpEntity<>(chucVu);
            return restTemplate.postForObject(url, entity, Boolean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean update(ChucVu chucVu) {
        try {
            HttpEntity<ChucVu> entity = new HttpEntity<>(chucVu);
            restTemplate.put(url, chucVu);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean delete(String ma) {
        try {
            restTemplate.delete(getUrl(ma));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
