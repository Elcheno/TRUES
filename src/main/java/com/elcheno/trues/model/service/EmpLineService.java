package com.elcheno.trues.model.service;

import com.elcheno.trues.model.dao.EmpLineDAO;
import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.dto.EmpLineDTO;

import java.sql.SQLException;
import java.util.List;

public class EmpLineService {
    /**
     * EmpLine Service Class to EmpLineDAO
     * @author Elcheno
     */

    private EmpLineDAO empLineDAO; //CONNECTION TO DAO
    //CONSTRUCT
    public EmpLineService(){
        empLineDAO = new EmpLineDAO();
    }

    public List<EmpLineDTO> getAll(Employee e) throws SQLException {
        return empLineDAO.findAll(e);
    }

    public EmpLineDTO getByPK(EmpLineDTO entityDTO, int id) throws SQLException {
        return empLineDAO.findByPK(entityDTO, id);
    }

    public List<Employee> getByLine(int id) throws SQLException {
        return empLineDAO.findEmpByLine(id);
    }

    public EmpLineDTO getByEmp(int idEmp, int idLine) throws SQLException {
        return empLineDAO.findByEmpLine(idEmp, idLine);
    }

    public boolean save(EmpLineDTO entityDTO, Employee entityEmp) throws SQLException {
        return empLineDAO.save(entityDTO, entityEmp);
    }
}
