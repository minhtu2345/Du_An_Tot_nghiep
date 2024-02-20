package com.example.demo.repository;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.PageDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Repository
public class KhachHangRepo {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/khachhang";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    private String getUrl1(UUID id) {
        return url + "/" + id;
    }

    public List<KhachHang> getAll() {
        ResponseEntity<List<KhachHang>> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<KhachHang>>() {
                });
        return responseEntity.getBody();
    }

    public KhachHang getBykhachhangma(String ma) {
        return restTemplate.getForObject(getUrl(ma), KhachHang.class);
    }

    public String save(KhachHang kh) {
        HttpEntity<KhachHang> entity = new HttpEntity<>(kh);
        JsonNode jsonNode = restTemplate.postForObject(url,entity, JsonNode.class);
        return jsonNode.get("hoten").asText();
    }



    public KhachHang update(String ma, KhachHang kh) {
        HttpEntity<KhachHang> entity = new HttpEntity<>(kh);
        restTemplate.put(getUrl(ma), entity);
        return kh;
    }

    public void delete(UUID ma) {
        restTemplate.delete(getUrl1(ma));
    }

    public PageDTO<KhachHang> phantrang(Integer page) {
        ResponseEntity<PageDTO<KhachHang>> responseEntity =
                restTemplate.exchange(getUrl("phantrang?page=" + page), HttpMethod.GET, null,
                        new ParameterizedTypeReference<PageDTO<KhachHang>>(){}
                );
        return responseEntity.getBody();
    }

    public PageDTO<KhachHang> searchAndPaginate(Integer page, String keyword) {
        ResponseEntity<PageDTO<KhachHang>> responseEntity = restTemplate.exchange(
                getUrl("phantrang?page=" + page + "&keyword=" + keyword),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<KhachHang>>() {}
        );

        return responseEntity.getBody();
    }
}
