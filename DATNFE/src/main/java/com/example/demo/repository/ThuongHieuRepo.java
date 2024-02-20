package com.example.demo.repository;

import com.example.demo.entity.ThuongHieu;
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
public class ThuongHieuRepo {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/thuonghieu";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    public List<ThuongHieu> getListThuongHieu() {
        ResponseEntity<List<ThuongHieu>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ThuongHieu>>() {
                });

        return response.getBody();
    }
    public PageDTO<ThuongHieu> getPageThuongHieu(Integer page) {
        ResponseEntity<PageDTO<ThuongHieu>> response = restTemplate.exchange(
                getUrl("phantrang?page="+page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<ThuongHieu>>() {}
        );
        return response.getBody();
    }

    public ThuongHieu getThuongHieuByMa(String ma) {
        return restTemplate.getForObject(getUrl(ma), ThuongHieu.class);
    }

    public String createThuongHieu(ThuongHieu ThuongHieu) {
        HttpEntity<ThuongHieu> entity = new HttpEntity<>(ThuongHieu);
        JsonNode resp = restTemplate.postForObject(url, entity, JsonNode.class);
        return resp.get("ten").asText();
    }

    public ThuongHieu updateThuongHieu(String ma, ThuongHieu ThuongHieu) {
        HttpEntity<ThuongHieu> entity = new HttpEntity<>(ThuongHieu);
        restTemplate.put(getUrl(ma), ThuongHieu);
        return ThuongHieu;
    }

    public void delete(String ma) {
        restTemplate.delete(getUrl(ma));
    }
}
