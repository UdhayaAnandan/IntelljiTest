package com.springbootDB.cruddemo.dao;

import com.springbootDB.cruddemo.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Component
public class EmployeeDAOHibernateImpl implements EmployeeDAO {
    //Define field for entityManager

    private EntityManager entityManager;

    // Setup constructor injeciton

    @Autowired
    public EmployeeDAOHibernateImpl(EntityManager theEntityManager)//Constructor injection automatically created by Spring boot
    {
        entityManager = theEntityManager;
    }

    @Override
    @Transactional //Taken care the transcation management
    public List<Employee> findAll() {

        //Get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        //Create a query
        Query<Employee> theQuery = currentSession.createQuery("from Employee", Employee.class);

        //execute query and get result list

        List<Employee> employees = theQuery.getResultList();

        //return the results
        return employees;
    }

    @Override
    public Employee findById(int theId) {
        // Get the Current Hibernate Session
        Session currentSession = entityManager.unwrap(Session.class);
        //Get the Employee
        Employee employee = currentSession.get(Employee.class, theId);
        return employee;
    }

    @Override
    @Transactional
    public void save(Employee employee) {
        //Get the Current Session
        Session currentSession = entityManager.unwrap(Session.class);

        //Save the Employee details
        currentSession.saveOrUpdate(employee); //If the primaryKey 0 then Save / insert  else update
    }

    @Override
    @Transactional
    public void deleteByID(int theId) {
        //Get the Current Session
        Session currentSession = entityManager.unwrap(Session.class);
        //Delete Employee by ID
        Query query = currentSession.createQuery("Delete from Employee where id = :id");
        query.setParameter("id", theId);
        query.executeUpdate();

    }
}
