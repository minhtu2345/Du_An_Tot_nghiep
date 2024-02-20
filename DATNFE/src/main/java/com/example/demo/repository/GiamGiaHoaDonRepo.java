package com.example.demo.repository;

import com.example.demo.entity.GiamGiaHoaDon;
import com.example.demo.entity.HoaDon;
import com.example.demo.entity.PageDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class GiamGiaHoaDonRepo {

    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/giamgiahoadon";

    //url theo id
    private String getUrl1(UUID id) {
        return url + "/" + id;
    }

    // url theo mã
    private String getUrl(String ma) {
        return url + "/" + ma;
    }

    // get all giảm giá hóa đơn
    public List<GiamGiaHoaDon> getAllGGHD() {
        ResponseEntity<List<GiamGiaHoaDon>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<GiamGiaHoaDon>>() {
                });

        return response.getBody();
    }

    // get all giảm giá hóa đơn với trạng thái =1
    public List<GiamGiaHoaDon> getAllGGHDtrangthai1() {
        ResponseEntity<List<GiamGiaHoaDon>> response =
                restTemplate.exchange(url + "/trang-thai-1", HttpMethod.GET, null, new ParameterizedTypeReference<List<GiamGiaHoaDon>>() {
                });

        return response.getBody();
    }

    // phân trang
    public PageDTO<GiamGiaHoaDon> getPageGGHD(Integer page) {
        ResponseEntity<PageDTO<GiamGiaHoaDon>> response = restTemplate.exchange(
                getUrl("phantrang?page=" + page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<GiamGiaHoaDon>>() {
                }
        );
        return response.getBody();
    }

    // findGGHD by mã
    public GiamGiaHoaDon getGiamGiaHoaDonByMa(String ma) {
        return restTemplate.getForObject(getUrl(ma), GiamGiaHoaDon.class);
    }

    // findGGHD by id
    public GiamGiaHoaDon getGiamGiaHoaDonById(UUID id) {
        return restTemplate.getForObject(getUrl( "getbyid/" + id), GiamGiaHoaDon.class);
    }

    // tạo mới GGHD
    public String createGGHD(GiamGiaHoaDon giamGiaHoaDon) {
        HttpEntity<GiamGiaHoaDon> entity = new HttpEntity<>(giamGiaHoaDon);
        JsonNode resp = restTemplate.postForObject(url, entity, JsonNode.class);
        return resp.get("ten").asText();
    }

    // xóa GGHD
    public void deleteGGHD(UUID id) {
        restTemplate.delete(getUrl1(id));
    }

    // Update GGHD
    public GiamGiaHoaDon updateGGHD(String ma, GiamGiaHoaDon giamGiaHoaDon) {
        HttpEntity<GiamGiaHoaDon> entity = new HttpEntity<>(giamGiaHoaDon);
        restTemplate.put(getUrl(ma), giamGiaHoaDon);
        return giamGiaHoaDon;
    }

    // Lọc GGHD theo ngày
    public PageDTO<GiamGiaHoaDon> getPageGGHDByDateRange(Date startDate, Date endDate, Integer page) {
        ResponseEntity<PageDTO<GiamGiaHoaDon>> response = restTemplate.exchange(
                getUrl("loc-theo-ngay?ngay_bat_dau=" + startDate + "&ngay_ket_thuc=" + endDate + "&page=" + page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<GiamGiaHoaDon>>() {
                }
        );
        return response.getBody();
    }

    // Lọc GGHD theo tên
    public PageDTO<GiamGiaHoaDon> getPageGGHDByTen(String ten, Integer page) {
        ResponseEntity<PageDTO<GiamGiaHoaDon>> response = restTemplate.exchange(
                getUrl("loc-theo-ten?ten=" + ten + "&page=" + page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<GiamGiaHoaDon>>() {
                }
        );
        return response.getBody();
    }

    // Lọc GGHD theo trạng thái
    public PageDTO<GiamGiaHoaDon> getPageGGHDByTrangThai(Integer trangthai, Integer page) {
        ResponseEntity<PageDTO<GiamGiaHoaDon>> response = restTemplate.exchange(
                getUrl("loc-theo-trangthai?trangthai=" + trangthai + "&page=" + page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<GiamGiaHoaDon>>() {
                }
        );
        return response.getBody();
    }

    // Tìm các hóa đơn đã được giảm giá
    public PageDTO<HoaDon> getHoaDonByChuongTrinhGiamGiaPage(String GiamGiaHoaDonMa, Integer page) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(GiamGiaHoaDonMa, headers);

        ResponseEntity<PageDTO<HoaDon>> response = restTemplate.exchange(
                url + "/phantrang/{GiamGiaHoaDonMa}?page=" + page,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageDTO<HoaDon>>() {
                },
                GiamGiaHoaDonMa);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            System.err.println("Lỗi khi lấy danh sách hóa đơn đã áp dụng giảm giá.");
            return (PageDTO<HoaDon>) Collections.emptyList();
        }
    }
}
