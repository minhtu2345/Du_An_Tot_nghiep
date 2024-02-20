package com.example.demo.repository;

import com.example.demo.entity.KichCo;
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
public class KichCoRepo {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/kichco";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    public List<KichCo> getListKichCo() {
        ResponseEntity<List<KichCo>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<KichCo>>() {
                });

        return response.getBody();
    }
    public PageDTO<KichCo> getPageKichCo(Integer page) {
        ResponseEntity<PageDTO<KichCo>> response = restTemplate.exchange(
                getUrl("phantrang?page="+page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<KichCo>>() {}
        );
        return response.getBody();
    }

    public KichCo getKichCoByMa(String ma) {
        return restTemplate.getForObject(getUrl(ma), KichCo.class);
    }

    public String createKichCo(KichCo KichCo) {
        HttpEntity<KichCo> entity = new HttpEntity<>(KichCo);
        JsonNode resp = restTemplate.postForObject(url, entity, JsonNode.class);
        return resp.get("ten").asText();
    }

    public KichCo updateKichCo(String ma, KichCo KichCo) {
        HttpEntity<KichCo> entity = new HttpEntity<>(KichCo);
        restTemplate.put(getUrl(ma), KichCo);
        return KichCo;
    }

    public void delete(String ma) {
        restTemplate.delete(getUrl(ma));
    }
}
