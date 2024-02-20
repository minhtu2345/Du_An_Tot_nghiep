package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Table(name = "thuong_hieu")
@Entity
@Getter
@Setter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ThuongHieu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String ma;
    private String ten_url;
    private String ten;
    private Integer trangthai = 1;

    @JsonIgnore
    @OneToMany(mappedBy = "thuong_hieu", fetch = FetchType.EAGER)
    List<Giay> giayList;
    public Integer getSumSP(){
        if (giayList==null){
            return 0;
        }else {
            return giayList.size();
        }
    }
}
