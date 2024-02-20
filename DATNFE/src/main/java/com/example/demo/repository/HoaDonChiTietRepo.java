package com.example.demo.repository;

import com.example.demo.entity.GiamGiaHoaDon;
import com.example.demo.entity.HoaDonChiTiet;
import com.example.demo.entity.PageDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public class HoaDonChiTietRepo {

    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:2020/rest/hoadonchitiet";

    //url theo id
    private String getUrl(UUID id) {
        return url + "/" + id;
    }
    //url theo mã
    private String getUrl1(String ma) {
        return url + "/" + ma;
    }
    private String getUrldelete(UUID hdid, UUID gghdid) {
        return url + "/delete" + "/" + hdid + "/" + gghdid;
    }

    // tạo mới HDCT
    public String createHDCT(HoaDonChiTiet hoaDonChiTiet) {
        HttpEntity<HoaDonChiTiet> entity = new HttpEntity<>(hoaDonChiTiet);
        JsonNode resp = restTemplate.postForObject(url +"/create", entity, JsonNode.class);
        return resp.get("trangthai").asText();
    }

    // Tạo mới danh sách hóa đơn chi tiết
    public List<HoaDonChiTiet> createHoaDonChiTietList(List<HoaDonChiTiet> hoaDonChiTietList) {
        HttpEntity<List<HoaDonChiTiet>> entity = new HttpEntity<>(hoaDonChiTietList);
        ResponseEntity<List<HoaDonChiTiet>> response = restTemplate.exchange(
                url + "/createList",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<HoaDonChiTiet>>() {}
        );
        return response.getBody();
    }


    // findHDCT by mã HD Page
    public PageDTO<HoaDonChiTiet> getPageHDCTbyMaHD(String ma, Integer page) {
        ResponseEntity<PageDTO<HoaDonChiTiet>> response = restTemplate.exchange(
                getUrl1(ma+ "?page=" +page),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDTO<HoaDonChiTiet>>() {
                }
        );
        return response.getBody();
    }

    // findHDCT by mã HD List
    public List<HoaDonChiTiet> getListHDCTbyMaHD(String ma) {
        ResponseEntity<List<HoaDonChiTiet>> response = restTemplate.exchange(
                getUrl1("list/" +ma),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<HoaDonChiTiet>>() {
                }
        );
        return response.getBody();
    }

    // xóa hóa đơn chi tiết theo giày chi tiết id
    public void deleteHDCT(UUID hdid, UUID ctspid) {
        restTemplate.delete(getUrldelete(hdid, ctspid));
    }
}
