package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Table(name = "chuc_vu")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChucVu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "ma")
    @NotBlank(message = "Mã không được để trống")
    private String ma;

    @Column(name = "ten")
    @NotBlank(message = "Tên không được để trống")
    private String ten;

    @Column(name = "trangthai")
    @NotNull(message = "Chưa chọn trạng thái")
    private Integer trangThai;
}
