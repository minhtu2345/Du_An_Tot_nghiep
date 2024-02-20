package com.example.demo.repository;

import com.example.demo.entity.DeGiay;
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
public class DeGiayRepo {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/degiay";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    public List<DeGiay> getListDeGiay() {
        ResponseEntity<List<DeGiay>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<DeGiay>>() {
                });

        return response.getBody();
    }
    public PageDTO<DeGiay> getPageDeGiay(Integer page) {
        ResponseEntity<PageDTO<DeGiay>> response = restTemplate.exchange(
                getUrl("phantrang?page="+page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<DeGiay>>() {}
        );
        return response.getBody();
    }

    public DeGiay getDeGiayByMa(String ma) {
        return restTemplate.getForObject(getUrl(ma), DeGiay.class);
    }

    public String createDeGiay(DeGiay DeGiay) {
        HttpEntity<DeGiay> entity = new HttpEntity<>(DeGiay);
        JsonNode resp = restTemplate.postForObject(url, entity, JsonNode.class);
        return resp.get("ten").asText();
    }

    public DeGiay updateDeGiay(String ma, DeGiay DeGiay) {
        HttpEntity<DeGiay> entity = new HttpEntity<>(DeGiay);
        restTemplate.put(getUrl(ma), DeGiay);
        return DeGiay;
    }

    public void delete(String ma) {
        restTemplate.delete(getUrl(ma));
    }
}
