package com.example.demo.repository;

import com.example.demo.entity.KieuDang;
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
public class KieuDangRepo {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/kieudang";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    public List<KieuDang> getListKieuDang() {
        ResponseEntity<List<KieuDang>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<KieuDang>>() {
                });

        return response.getBody();
    }
    public PageDTO<KieuDang> getPageKieuDang(Integer page) {
        ResponseEntity<PageDTO<KieuDang>> response = restTemplate.exchange(
                getUrl("phantrang?page="+page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<KieuDang>>() {}
        );
        return response.getBody();
    }

    public KieuDang getKieuDangByMa(String ma) {
        return restTemplate.getForObject(getUrl(ma), KieuDang.class);
    }

    public String createKieuDang(KieuDang KieuDang) {
        HttpEntity<KieuDang> entity = new HttpEntity<>(KieuDang);
        JsonNode resp = restTemplate.postForObject(url, entity, JsonNode.class);
        return resp.get("ten").asText();
    }

    public KieuDang updateKieuDang(String ma, KieuDang KieuDang) {
        HttpEntity<KieuDang> entity = new HttpEntity<>(KieuDang);
        restTemplate.put(getUrl(ma), KieuDang);
        return KieuDang;
    }

    public void delete(String ma) {
        restTemplate.delete(getUrl(ma));
    }
}
