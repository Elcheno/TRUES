package com.elcheno.trues.model.domain;

import com.elcheno.trues.model.dto.EmpLineDTO;
import com.elcheno.trues.model.service.EmpLineService;
import com.elcheno.trues.model.service.EmployeeService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Employee extends Person{
    /**
     * Employee class
     * @autor Elcheno
     */

    private int cod;
    private List<EmpLineDTO> empLinesDTO;

    //CONSTRUCT
    public Employee(int cod, String dni, String name, String lastName){
        super(dni, name, lastName);
        this.cod = cod;
        this.empLinesDTO = null;
    }
    public Employee(){
        this(0, "", "", "");
    }

    //GETTER AND SETTER
    public int getCod() {
        return cod;
    }
    public void setCod(int cod) {
        this.cod = cod;
    }
    public List<EmpLineDTO> getEmpLinesDTO() {
        if(empLinesDTO == null){
            EmployeeService employeeService = new EmployeeService();
            employeeService.getAllEmpLinesDTO(this);
        }
        return empLinesDTO;
    }
    public void setEmpLinesDTO(List<EmpLineDTO> empLinesDTO) {
        this.empLinesDTO = empLinesDTO;
    }
    public void addEmpLinesDTO(EmpLineDTO empLineDTO){
        if(!empLinesDTO.contains(empLineDTO)){
            this.empLinesDTO.add(empLineDTO);
        }
    }

    //TOSTRING
    @Override
    public String toString() {
        return "Employee{" +
                "cod=" + cod +
                super.toString() +
                '}';
    }
}
