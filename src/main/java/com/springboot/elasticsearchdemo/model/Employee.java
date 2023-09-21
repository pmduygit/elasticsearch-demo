package com.springboot.elasticsearchdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "employee")
public class Employee {
    @Id
    private String id;
    private String name;
    private String department;
    private double salary;
}
