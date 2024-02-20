package com.example.demo.repository;

import com.example.demo.entity.DiaChi;
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
public class DiaChiRepo {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/diachi";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    private String getUrl1(UUID ma) {
        return url + "/" + ma;
    }

    public List<DiaChi> getall() {
        ResponseEntity<List<DiaChi>> entity = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<DiaChi>>() {
                });
        return entity.getBody();
    }


    public String save(DiaChi diaChi) {
        HttpEntity<DiaChi> entity = new HttpEntity<>(diaChi);
        JsonNode jsonNode = restTemplate.postForObject(url, entity, JsonNode.class);
        return jsonNode.get("tendiachi").asText();
    }

    public DiaChi getbyma1(UUID ma) {
        return restTemplate.getForObject(getUrl1(ma), DiaChi.class);
    }
    public DiaChi getDCbyma(String ma) {
        return restTemplate.getForObject(getUrl("getDiaChiByMa/" + ma), DiaChi.class);
    }

    public void delete(UUID ma) {
        restTemplate.delete(getUrl1(ma));
    }

    public DiaChi update(String id, DiaChi dc) {
        HttpEntity<DiaChi> entity = new HttpEntity<>(dc);
        restTemplate.put(getUrl(id), entity);
        return dc;
    }

    public List<DiaChi> getListDCbyKH(String ma) {
        ResponseEntity<List<DiaChi>> entity = restTemplate.exchange(url + "/getDiaChiByKH/" + ma, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<DiaChi>>() {
                });
        return entity.getBody();
    }

}
