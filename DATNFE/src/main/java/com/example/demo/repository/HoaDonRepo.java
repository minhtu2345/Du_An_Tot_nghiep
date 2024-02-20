package com.example.demo.repository;

import com.example.demo.entity.GiamGiaHoaDon;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.PageDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Repository
public class HoaDonRepo {

    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/hoadon";

    //url theo id
    private String getUrl1(UUID id) {
        return url + "/" + id;
    }

    // url theo mã
    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    // get all hóa đơn chưa giảm giá theo page
    public PageDTO<HoaDon> getAllHDchuaGGPage(Integer page) {
        ResponseEntity<PageDTO<HoaDon>> response =
                restTemplate.exchange(url + "/phantrang?page1=" + page, HttpMethod.GET, null, new ParameterizedTypeReference<PageDTO<HoaDon>>() {
                });
        return response.getBody();
    }

    // get all hóa đơn chưa thanh toán
    public PageDTO<HoaDon> getAllHDchuaTTPage(Integer page) {
        ResponseEntity<PageDTO<HoaDon>> response =
                restTemplate.exchange(url + "/pagehdctt?page=" + page, HttpMethod.GET, null, new ParameterizedTypeReference<PageDTO<HoaDon>>() {
                });
        return response.getBody();
    }
    // find HD by id
    public HoaDon getHoaDonByID(UUID id) {
        return restTemplate.getForObject(getUrl("getbyid/" + id), HoaDon.class);
    }

    // find HD by mã
    public HoaDon getHoaDonByMa(String ma) {
        return restTemplate.getForObject(getUrl(ma), HoaDon.class);
    }

    // Xóa Hóa Đơn
    public void deleteHD(UUID id) {
        restTemplate.delete(getUrl1(id));
    }

    // Tìm hóa đơn theo mã hoặc tên khách hàng
    public PageDTO<HoaDon> timkiemHoaDon(String keyword, Integer page) {
        ResponseEntity<PageDTO<HoaDon>> response = restTemplate.exchange(
                getUrl("tim-kiem-hoa-don?keyword=" + keyword + "&page=" + page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<HoaDon>>() {
                }
        );
        return response.getBody();
    }

    public String createHoaDon(HoaDon hoaDon) {
        HttpEntity<HoaDon> entity = new HttpEntity<>(hoaDon);
        JsonNode resp = restTemplate.postForObject(url, entity, JsonNode.class);
        return resp.get("ma").asText();
    }
    //Tìm hóa đơn theo trạng thái
    public PageDTO<HoaDon> getPageHDByTrangThai(Integer trangthai, Integer page) {
        ResponseEntity<PageDTO<HoaDon>> response = restTemplate.exchange(
                getUrl("phan-trang?trangthai=" + trangthai + "&page=" + page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<HoaDon>>() {
                }
        );
        return response.getBody();
    }

    public PageDTO<HoaDon> getPageHDByTrangThai1(Integer trangthai, String keyword, String timtheo, Integer page) {
        ResponseEntity<PageDTO<HoaDon>> response = restTemplate.exchange(
                getUrl("tim-hd-theo-trang-thai?trangthai=" + trangthai + "&keyword=" + keyword  + "&timTheo=" + timtheo + "&page=" + page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<HoaDon>>() {
                }
        );
        return response.getBody();
    }
    public PageDTO<HoaDon> getPageHDByTrangThai1chuaApMa(String keyword, String timtheo, Integer page) {
        ResponseEntity<PageDTO<HoaDon>> response = restTemplate.exchange(
                getUrl("tim-kiem-hoa-don-chua-ap-ma?keyword=" + keyword  + "&timTheo=" + timtheo + "&page=" + page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<HoaDon>>() {
                }
        );
        return response.getBody();
    }
}
