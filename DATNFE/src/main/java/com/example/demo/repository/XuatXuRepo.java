package com.example.demo.repository;

import com.example.demo.entity.XuatXu;
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
public class XuatXuRepo {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/xuatxu";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    public List<XuatXu> getListXuatXu() {
        ResponseEntity<List<XuatXu>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<XuatXu>>() {
                });

        return response.getBody();
    }
    public PageDTO<XuatXu> getPageXuatXu(Integer page) {
        ResponseEntity<PageDTO<XuatXu>> response = restTemplate.exchange(
                getUrl("phantrang?page="+page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<XuatXu>>() {}
        );
        return response.getBody();
    }

    public XuatXu getXuatXuByMa(String ma) {
        return restTemplate.getForObject(getUrl(ma), XuatXu.class);
    }

    public String createXuatXu(XuatXu XuatXu) {
        HttpEntity<XuatXu> entity = new HttpEntity<>(XuatXu);
        JsonNode resp = restTemplate.postForObject(url, entity, JsonNode.class);
        return resp.get("ten").asText();
    }

    public XuatXu updateXuatXu(String ma, XuatXu XuatXu) {
        HttpEntity<XuatXu> entity = new HttpEntity<>(XuatXu);
        restTemplate.put(getUrl(ma), XuatXu);
        return XuatXu;
    }

    public void delete(String ma) {
        restTemplate.delete(getUrl(ma));
    }
}
