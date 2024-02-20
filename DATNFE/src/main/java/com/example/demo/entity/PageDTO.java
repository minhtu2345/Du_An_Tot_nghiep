package com.example.demo.entity;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageDTO<T> {

    private List<T> content;
    private long totalElements;
    private long number;
    private long totalPages;
    private long size;
    private long numberOfElements;
    // ... other pagination fields ...

    public PageDTO(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.number = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.size = page.getSize();
        this.numberOfElements = page.getNumberOfElements();
        // ... set other fields ...
    }

    // ... getters and setters ...
}
