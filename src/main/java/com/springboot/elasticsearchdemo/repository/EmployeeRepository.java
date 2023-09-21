package com.springboot.elasticsearchdemo.repository;

import com.springboot.elasticsearchdemo.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.annotation.Nonnull;
import java.util.List;

public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {
    List<Employee> findByName(String name);

    @Nonnull
    Page<Employee> findAll(@Nonnull Pageable pageable);

    List<Employee> findBySalaryBetween(double minSalary, double maxSalary);

    @Query("{ \"bool\": { \"must\": [ { \"match\": { \"name\": \"?0\" } } ] } }")
    List<Employee> findByNameMatch(String name);

    @Query("{ \"bool\": { \"must\": [ { \"match\": { \"name\": \"?0\" } }, { \"range\": { \"salary\": { \"gte\": ?1, \"lte\": ?2 } } } ] } }")
    List<Employee> findByNameMatchAndSalaryRange(String name, double min, double max);
}
