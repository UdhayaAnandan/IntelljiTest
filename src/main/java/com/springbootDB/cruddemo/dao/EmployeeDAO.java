package com.springbootDB.cruddemo.dao;

import com.springbootDB.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

     List<Employee> findAll();
     Employee findById(int theId);
     void save (Employee employee);
     void deleteByID(int theId);

}
