package com.example.demo.repository;

import com.example.demo.entity.ChuongTrinhGiamGiaSP;
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
public class ChuongTrinhGiamGiaSPRepo {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/chuong-trinh-giam-gia-sp";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    private String getUrlId(UUID id) {
        return url + "/detail/" + id;
    }

    private String getUrlUpdate(UUID id) {
        return url + "/update" + id;
    }

    public List<ChuongTrinhGiamGiaSP> getListVoucher() {
        ResponseEntity<List<ChuongTrinhGiamGiaSP>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ChuongTrinhGiamGiaSP>>() {
                });

        return response.getBody();
    }


    public ChuongTrinhGiamGiaSP getOneByMa(String maKM) {
        return restTemplate.getForObject(getUrl(maKM), ChuongTrinhGiamGiaSP.class);
    }

    public ChuongTrinhGiamGiaSP getOneByMaKM(String maKM) {
        return restTemplate.getForObject(getUrl("findbyma/" + maKM), ChuongTrinhGiamGiaSP.class);
    }

    public ChuongTrinhGiamGiaSP getOneById(UUID idKM) {
        return restTemplate.getForObject(getUrlId(idKM), ChuongTrinhGiamGiaSP.class);
    }

    public String saveVoucher(ChuongTrinhGiamGiaSP voucherForm) {
        HttpEntity<ChuongTrinhGiamGiaSP> entity = new HttpEntity<>(voucherForm);
        JsonNode resp = restTemplate.postForObject(url, entity, JsonNode.class);
        return resp.get("tenKhuyenMai").asText();
    }

    public ChuongTrinhGiamGiaSP update(String ma, ChuongTrinhGiamGiaSP ctggsp) {
        HttpEntity<ChuongTrinhGiamGiaSP> entity = new HttpEntity<>(ctggsp);
        restTemplate.put(getUrl(ma), ctggsp);
        return ctggsp;
    }

//    public ChuongTrinhGiamGiaSP updateVoucher(String ma, ChuongTrinhGiamGiaSP chuongTrinhGiamGiaSP) {
//        HttpEntity<ChuongTrinhGiamGiaSP> entity = new HttpEntity<>(chuongTrinhGiamGiaSP);
//        restTemplate.put(getUrl(ma), chuongTrinhGiamGiaSP);
//        return chuongTrinhGiamGiaSP;
//    }

    public PageDTO<ChuongTrinhGiamGiaSP> phanTrang(Integer page) {
        ResponseEntity<PageDTO<ChuongTrinhGiamGiaSP>> responseEntity =
                restTemplate.exchange(getUrl("phantrang?page=" + page), HttpMethod.GET, null,
                        new ParameterizedTypeReference<PageDTO<ChuongTrinhGiamGiaSP>>(){}
                );
        return responseEntity.getBody();
    }

    public PageDTO<ChuongTrinhGiamGiaSP> searchTrangThaiAndKeyword(Integer page, String keyword) {
        ResponseEntity<PageDTO<ChuongTrinhGiamGiaSP>> responseEntity = restTemplate.exchange(
                getUrl("phantrang?page=" + page + "&keyword=" + keyword),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<ChuongTrinhGiamGiaSP>>() {}
        );

        return responseEntity.getBody();
    }


}
