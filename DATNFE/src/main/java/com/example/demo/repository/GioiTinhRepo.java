package com.example.demo.repository;

import com.example.demo.entity.GioiTinh;
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
public class GioiTinhRepo {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/gioitinh";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    public List<GioiTinh> getListGioiTinh() {
        ResponseEntity<List<GioiTinh>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<GioiTinh>>() {
                });

        return response.getBody();
    }
    public PageDTO<GioiTinh> getPageGioiTinh(Integer page) {
        ResponseEntity<PageDTO<GioiTinh>> response = restTemplate.exchange(
                getUrl("phantrang?page="+page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<GioiTinh>>() {}
        );
        return response.getBody();
    }

    public GioiTinh getGioiTinhByMa(String ma) {
        return restTemplate.getForObject(getUrl(ma), GioiTinh.class);
    }

    public String createGioiTinh(GioiTinh GioiTinh) {
        HttpEntity<GioiTinh> entity = new HttpEntity<>(GioiTinh);
        JsonNode resp = restTemplate.postForObject(url, entity, JsonNode.class);
        return resp.get("ten").asText();
    }

    public GioiTinh updateGioiTinh(String ma, GioiTinh GioiTinh) {
        HttpEntity<GioiTinh> entity = new HttpEntity<>(GioiTinh);
        restTemplate.put(getUrl(ma), GioiTinh);
        return GioiTinh;
    }

    public void delete(String ma) {
        restTemplate.delete(getUrl(ma));
    }
}
