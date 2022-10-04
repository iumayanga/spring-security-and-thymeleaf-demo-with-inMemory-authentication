package com.example.thymeleafdemo.service;

import com.example.thymeleafdemo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    public List<Employee> findAll();

    public Employee findById(int id);

    public void save(Employee employee);

    public void deleteById(int id);

    public List<Employee> searchBy(String theName);

}
