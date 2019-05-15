package com.codegym.employee.service;


import com.codegym.employee.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();
    Department findById(Long id);
    void save(Department department);
    void delete(Long id);
}
