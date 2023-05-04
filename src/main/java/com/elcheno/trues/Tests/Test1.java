package com.elcheno.trues.Tests;

import com.elcheno.trues.model.dao.EmployeeDAO;
import com.elcheno.trues.model.domain.Employee;

import java.sql.SQLException;
import java.util.List;

public class Test1 {
    public static void main(String[] args) throws SQLException {
        EmployeeDAO employeeDAO = new EmployeeDAO();

        List<Employee> employees = employeeDAO.findAll();
        for(Employee e : employees) {
            System.out.println(e.toString());
        }


        System.out.println("=====================================");
        Employee employee1 = (Employee) employeeDAO.findById(1);
        System.out.println(employee1.toString());
    }
}
