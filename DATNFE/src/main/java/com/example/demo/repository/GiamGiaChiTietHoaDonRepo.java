package com.example.demo.repository;

import com.example.demo.entity.GiamGiaChiTietHoaDon;
import com.example.demo.entity.GiamGiaHoaDon;
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
public class GiamGiaChiTietHoaDonRepo {

    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/giamgiachitiethoadon";


    private String getUrldelete(UUID hdid, UUID gghdid) {
        return url + "/delete" + "/" + hdid + "/" + gghdid;
    }

    // url theo mã
    private String getUrl1(String ma) {
        return url + "/" + ma;
    }


    // get all giảm giá CT hóa đơn
    public List<GiamGiaChiTietHoaDon> getAllGGCTHD() {
        ResponseEntity<List<GiamGiaChiTietHoaDon>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<GiamGiaChiTietHoaDon>>() {
                });

        return response.getBody();
    }

    // tạo mới GGCTHD
    public String createGGCTHD2(GiamGiaChiTietHoaDon giamGiaChiTietHoaDon) {
        HttpEntity<GiamGiaChiTietHoaDon> entity = new HttpEntity<>(giamGiaChiTietHoaDon);
        JsonNode resp = restTemplate.postForObject(url + "/createGGCTHD", entity, JsonNode.class);
        return resp.get("trangthai").asText();
    }

    //xóa GGCTHD
    public void deleteGGCTHD(UUID hdid, UUID gghdid) {
        restTemplate.delete(getUrldelete(hdid, gghdid));
    }

    // find giảm giá CT hóa đơn theo id hóa đơn và id giảm giá hóa đơn
    public GiamGiaChiTietHoaDon getGiamGiaCTHoaDonByHDandGGHD(UUID hdid, UUID gghdid) {
        return restTemplate.getForObject(getUrl1("getGGCTHDbyHDandGGHD/" + hdid + "/" + gghdid), GiamGiaChiTietHoaDon.class);
    }

    // find giảm giá CT hóa đơn theo id hóa đơn
    public GiamGiaChiTietHoaDon getGiamGiaCTHoaDonByHD(UUID hdid) {
        return restTemplate.getForObject(getUrl1("getGGCTHDbyHD/" + hdid), GiamGiaChiTietHoaDon.class);
    }
}
