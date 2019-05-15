package com.codegym.employee.controller;

import com.codegym.employee.model.Department;
import com.codegym.employee.model.Employee;
import com.codegym.employee.service.DepartmentService;
import com.codegym.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;

    @ModelAttribute("departments")
    public List<Department> departments() {
        return departmentService.findAll();
    }

    @GetMapping("/")
    public String list(Model model, Optional<String> search, @PageableDefault(value = 5) Pageable pageable) {
        if (search.isPresent()) {
            Page<Employee> employees = employeeService.findAllByNameContaining(search.get(), pageable);
            model.addAttribute("employees", employees);
        } else {
            Page<Employee> employees = employeeService.findAll(pageable);
            model.addAttribute("employees", employees);
        }
        return "employee/list";
    }

    @GetMapping("/add")
    public String formAdd(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/add";
    }

    @PostMapping("/add")
    public String add(Model model, @ModelAttribute Employee employee) {
        employeeService.save(employee);
        model.addAttribute("employee", new Employee());
        model.addAttribute("message", "Thêm m?i thành công");
        return "employee/add";
    }

    @GetMapping("/edit/{id}")
    public String formEdit(@PathVariable long id, Model model) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            model.addAttribute("employee", employee);
            return "employee/edit";
        } else {
            return "404";
        }

    }

    @PostMapping("/edit")
    public String edit(Model model, @ModelAttribute Employee employee) {
        employeeService.save(employee);
        model.addAttribute("employee", employee);
        model.addAttribute("message", "S?a thành công");
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            employeeService.delete(id);
            return "redirect:/";
        } else {
            return "404";
        }
    }
}
