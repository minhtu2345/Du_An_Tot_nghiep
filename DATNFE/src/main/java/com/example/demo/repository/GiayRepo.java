package com.example.demo.repository;

import com.example.demo.entity.Giay;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class GiayRepo {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/giay/hien-thi";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    public List<Giay> getListGiay() {
        ResponseEntity<List<Giay>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Giay>>() {
                });

        return response.getBody();
    }
}
