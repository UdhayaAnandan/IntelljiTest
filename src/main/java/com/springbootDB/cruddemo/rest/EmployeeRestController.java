package com.springbootDB.cruddemo.rest;

import com.springbootDB.cruddemo.dao.EmployeeDAO;
import com.springbootDB.cruddemo.entity.Employee;
import com.springbootDB.cruddemo.entity.EmployeeFailureResponse;
import com.springbootDB.cruddemo.exception.EmployeeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")


public class EmployeeRestController {
    static Logger log = Logger.getLogger(EmployeeRestController.class.getName());
    @Autowired
    @Qualifier("employeeDAOHibernateImpl")
    private EmployeeDAO employeeDAO;

    @Value("${status.success}")
    public String propValue;
  /*  public EmployeeRestController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }*/

    //Expose "/employees " and return list of employees
    @GetMapping("/employees")
    public List<Employee> getEmpDetail() {
        log.info("Going to create HelloWord Obj");
        System.out.println(propValue);
        return employeeDAO.findAll();
    }

    @GetMapping("/employees/{empId}")
    public Employee getEmployeeById(@PathVariable int empId) {
        Employee employee = employeeDAO.findById(empId);
        if (employee == null) {
            System.out.println("**TEST********");
            throw new EmployeeException("Employee not found --" + empId);
        } else return employee;
    }

    @PostMapping("/AddEmployee")
    public List<Employee> addEmployee(@RequestBody List<Employee> emp) {
        for (Employee employee : emp) {
            employee.setId(0); //Always Add new employee
            employeeDAO.save(employee);
        }
        return emp;

    }

    @PutMapping("/UpdateEmployee")
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeDAO.save(employee);
        return employee;
    }

    @DeleteMapping("/DeleteEmployee/{empId}")
    public String deleteEmployee(@PathVariable int empId) {
        employeeDAO.deleteByID(empId);
        return empId + " Employee  Deleted";
    }


    //Add an exception Handler using

    @ExceptionHandler(value = EmployeeException.class)
    public ResponseEntity<EmployeeFailureResponse> handleException(EmployeeException employeeException) {
        //Create Employee error response
        log.info(" Entering into the ExceptionHandle");
        System.out.println(" Entering into the ExceptionHandle");
        EmployeeFailureResponse empFailure = new EmployeeFailureResponse();
        empFailure.setStatus(HttpStatus.NOT_FOUND.value());
        empFailure.setMessage(employeeException.getMessage());
        empFailure.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<EmployeeFailureResponse>(empFailure, HttpStatus.NOT_FOUND);
    }

}
