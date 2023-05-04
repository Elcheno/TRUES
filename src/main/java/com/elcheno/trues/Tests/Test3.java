package com.elcheno.trues.Tests;

import com.elcheno.trues.model.dao.EmployeeDAO;
import com.elcheno.trues.model.domain.Employee;

import java.sql.SQLException;

public class Test3 {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee aux = new Employee();
        try {
            aux = employeeDAO.findById(5);
            try {
                System.out.println(employeeDAO.delete(aux));
            } catch (SQLException e) {
                System.out.println("Error al eliminar el empleado");
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el empleado");
            System.err.println(e.getMessage());
        }

    }
}
