package com.elcheno.trues.Tests;

import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.service.EmployeeService;

import java.sql.SQLException;

public class Test4 {
    public final static EmployeeService employeeService = new EmployeeService();

    public static void main(String[] args) {
        Employee aux = new Employee();
        try{
            aux = employeeService.getById(1);
            try {
                System.out.println(employeeService.remove(aux));
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
