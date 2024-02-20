package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Table(name = "chat_lieu")
@Entity
@Getter
@Setter
//@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatLieu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String ma;
    private String ten;
    private Integer trangthai = 1;
}
