package com.example.demo.repository;

import com.example.demo.entity.ChatLieu;
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
public class ChatLieuRepo {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/chatlieu";

    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    public List<ChatLieu> getListChatLieu() {
        ResponseEntity<List<ChatLieu>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ChatLieu>>() {
                });

        return response.getBody();
    }
    public PageDTO<ChatLieu> getPageChatLieu(Integer page) {
        ResponseEntity<PageDTO<ChatLieu>> response = restTemplate.exchange(
                getUrl("phantrang?page="+page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<ChatLieu>>() {}
        );
        return response.getBody();
    }

    public ChatLieu getChatLieuByMa(String ma) {
        return restTemplate.getForObject(getUrl(ma), ChatLieu.class);
    }

    public String createChatLieu(ChatLieu ChatLieu) {
        HttpEntity<ChatLieu> entity = new HttpEntity<>(ChatLieu);
        JsonNode resp = restTemplate.postForObject(url, entity, JsonNode.class);
        return resp.get("ten").asText();
    }

    public ChatLieu updateChatLieu(String ma, ChatLieu ChatLieu) {
        HttpEntity<ChatLieu> entity = new HttpEntity<>(ChatLieu);
        restTemplate.put(getUrl(ma), ChatLieu);
        return ChatLieu;
    }

    public void delete(String ma) {
        restTemplate.delete(getUrl(ma));
    }
}
