package com.elcheno.trues.Tests;

import com.elcheno.trues.model.dao.EmpLineDAO;
import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.dto.EmpLineDTO;
import com.elcheno.trues.model.service.EmpLineService;
import com.elcheno.trues.model.service.EmployeeService;
import com.elcheno.trues.model.service.LineService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Test9 {

    public static void main(String[] args) {
        EmpLineService empLineService = new EmpLineService();
        EmployeeService employeeService = new EmployeeService();
        try {
            Employee e = employeeService.getById(1);
            List<EmpLineDTO> aux = empLineService.getAll(e);
            for (EmpLineDTO emp : aux) {
                System.out.print(emp.getLine() + "    ");
                System.out.print(emp.getDate() + "    ");
                System.out.print(emp.getDateIn() + "    ");
                System.out.println(emp.getDateOut());
            }
        } catch (SQLException ex) {
            System.out.println("Error al cargar getEmpLinesDTO");
            System.err.println(ex.getMessage());
        }

//        System.out.println("--------------------------------------------------");
//
//
//        try {
//            EmpLineDAO empLineDAO = new EmpLineDAO();
//            LineService lineService = new LineService();
//
//            Employee e = employeeService.getById(1);
//            EmpLineDTO empLineDTO = new EmpLineDTO();
//            empLineDTO.setLine(lineService.getById(1));
//            empLineDTO.setDate(LocalDate.of(2023, 5,5));
//            empLineDTO.setDateIn(LocalTime.of(8, 30, 0));
//            System.out.println(empLineDAO.findTimeOut(empLineDTO, e.getId()));
//        } catch (SQLException ex) {
//            System.out.println("Error al cargar getEmpLinesDTO");
//            System.out.println(ex.getMessage());
//        }

        System.out.println("--------------------------------------------------");

        try {
            LineService lineService = new LineService();
            EmpLineDAO empLineDAO = new EmpLineDAO();

            Employee e = employeeService.getById(1);
            EmpLineDTO empLineDTO = new EmpLineDTO();
            empLineDTO.setLine(lineService.getById(1));
            empLineDTO.setDate(LocalDate.of(2023, 5,6));
            empLineDTO.setDateIn(LocalTime.of(9, 52, 29));
            empLineDTO.setDateOut(LocalTime.now());
            System.out.println(empLineDAO.save(empLineDTO, e));

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


//        try {
//            Employee e = employeeService.getById(1);
//            List<EmpLineDTO> aux = e.getEmpLinesDTO();
//        } catch (SQLException ex) {
//            System.out.println("Error al cargar getEmpLinesDTO");
//            System.err.println(ex.getMessage());
//        }


    }
}
