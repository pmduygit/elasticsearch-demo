package com.springboot.elasticsearchdemo.service;

import com.springboot.elasticsearchdemo.model.Employee;
import com.springboot.elasticsearchdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeRepository.findAll().forEach(employeeList::add);
        return employeeList;
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> findEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> insertManyEmployee(List<Employee> employeeList) {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.saveAll(employeeList).forEach(employees::add);
        return employees;
    }

    public Employee updateEmployee(String id, Employee employee) {
        if (employeeRepository.findById(id).isPresent()) {
            employee.setId(id);
            employeeRepository.save(employee);
            return  employee;
        }
        return null;
    }

    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }

    public Page<Employee> paginateEmployees(int page, int size) {
        page = page - 1;
        if (page < 0 || size < 1) return  null;
        PageRequest pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }

    public List<Employee> filterEmployeesBySalaryRange(double min, double max) {
        return employeeRepository.findBySalaryBetween(min, max);
    }

    public List<Employee> findByNameMatch(String name) {
        return employeeRepository.findByNameMatch(name);
    }

    public List<Employee> findByNameMatchAndSalaryRange(String name, double min, double max) {
        return employeeRepository.findByNameMatchAndSalaryRange(name, min, max);
    }
}
