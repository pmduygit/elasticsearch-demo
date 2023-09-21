package com.springboot.elasticsearchdemo.controller;

import com.springboot.elasticsearchdemo.model.Employee;
import com.springboot.elasticsearchdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> findEmployeeByName(@RequestParam String name) {
        return ResponseEntity.ok(employeeService.findEmployeeByName(name));
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    @PostMapping("/add-all")
    public ResponseEntity<List<Employee>> addAllEmployee(@RequestBody List<Employee> employees) {
        return new ResponseEntity<>(employeeService.insertManyEmployee(employees), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.updateEmployee(id, employee), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Delete successfully");
    }

    @GetMapping("/paging")
    public ResponseEntity<Page<Employee>> paginateEmployees(@RequestParam(required = false, defaultValue = "1") int page,
                                                            @RequestParam(required = false, defaultValue = "4") int size) {
        return new ResponseEntity<>(employeeService.paginateEmployees(page, size), HttpStatus.OK);
    }

    @GetMapping("/filter-salary-range")
    public ResponseEntity<List<Employee>> filterEmployeesBySalaryRange(@RequestParam double min,@RequestParam double max) {
        return new ResponseEntity<>(employeeService.filterEmployeesBySalaryRange(min, max), HttpStatus.OK);
    }

    @GetMapping("/search-match")
    public ResponseEntity<List<Employee>> findEmployeesByNameMatch(@RequestParam String name) {
        return new ResponseEntity<>(employeeService.findByNameMatch(name), HttpStatus.OK);
    }

    @GetMapping("/filter-many")
    public ResponseEntity<List<Employee>> findEmployeesByNameMatchAndSalaryRange(@RequestParam String name,
                                                                                 @RequestParam double minSalary,
                                                                                 @RequestParam double maxSalary) {
        return new ResponseEntity<>(employeeService.findByNameMatchAndSalaryRange(name, minSalary, maxSalary), HttpStatus.OK);
    }
}
