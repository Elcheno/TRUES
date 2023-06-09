package com.elcheno.trues.model.service;

import com.elcheno.trues.model.dao.EmpLineDAO;
import com.elcheno.trues.model.dao.EmployeeDAO;
import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.dto.EmpLineDTO;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    /**
     * Service class for EmployeeDAO
     * @see EmployeeDAO
     * @autor Elcheno
     */
    private EmployeeDAO employeeDAO;

    public EmployeeService(){
        employeeDAO = new EmployeeDAO();
    }

    public List<Employee> getAll() throws SQLException {
        return employeeDAO.findAll();
    }

    public Employee getById(int id) throws SQLException {
        return employeeDAO.findById(id);
    }

    public List<EmpLineDTO> getAllEmpLinesDTO(Employee entity) {
        EmpLineDAO empLineDAO = new EmpLineDAO();
        return empLineDAO.findAllEmp(entity);
    }

    public Employee getByCod(int cod) throws SQLException {
        return employeeDAO.findByCod(cod);
    }

    public boolean save(Employee entity) throws SQLException {
        return employeeDAO.save(entity);
    }

    public boolean remove(Employee entity) throws SQLException {
        return employeeDAO.delete(entity);
    }

}
