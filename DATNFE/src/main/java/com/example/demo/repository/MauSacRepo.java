package com.example.demo.repository;

import com.example.demo.entity.MauSac;
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
public class MauSacRepo {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/mausac";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    public List<MauSac> getListMauSac() {
        ResponseEntity<List<MauSac>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<MauSac>>() {
                });

        return response.getBody();
    }
    public PageDTO<MauSac> getPageMauSac(Integer page) {
        ResponseEntity<PageDTO<MauSac>> response = restTemplate.exchange(
                getUrl("phantrang?page="+page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<MauSac>>() {}
        );
        return response.getBody();
    }

    public MauSac getMauSacByMa(String ma) {
        return restTemplate.getForObject(getUrl(ma), MauSac.class);
    }

    public String createMauSac(MauSac MauSac) {
        HttpEntity<MauSac> entity = new HttpEntity<>(MauSac);
        JsonNode resp = restTemplate.postForObject(url, entity, JsonNode.class);
        return resp.get("ten").asText();
    }

    public MauSac updateMauSac(String ma, MauSac MauSac) {
        HttpEntity<MauSac> entity = new HttpEntity<>(MauSac);
        restTemplate.put(getUrl(ma), MauSac);
        return MauSac;
    }

    public void delete(String ma) {
        restTemplate.delete(getUrl(ma));
    }
}
