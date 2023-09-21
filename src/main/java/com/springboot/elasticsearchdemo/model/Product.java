package com.springboot.elasticsearchdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products")
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    private String category;
}
