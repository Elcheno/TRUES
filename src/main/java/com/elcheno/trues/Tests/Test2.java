package com.elcheno.trues.Tests;

import com.elcheno.trues.model.dao.EmployeeDAO;
import com.elcheno.trues.model.domain.Employee;

import java.sql.SQLException;

public class Test2 {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee aux = new Employee();
        aux.setId(5);
        aux.setCod(6789);
        aux.setDni("12345678D");
        aux.setName("Empleado5");
        aux.setLastName("Apellido");
        try {
            System.out.println(employeeDAO.save(aux));
        } catch (SQLException e) {
            System.out.println("Error al guardar el empleado");
            System.err.println(e.getMessage());
        }
    }
}
