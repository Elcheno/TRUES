package com.elcheno.trues.model.dto;

import com.elcheno.trues.model.domain.Employee;
public class EmployeeInfoDTO {
    /**
     * This class is a DTO(Singleton) for the Employee class.
     * @see Employee
     * @author Elcheno
     */

    private static EmployeeInfoDTO _newInstance;
    private static Employee _employee;

    private EmployeeInfoDTO(Employee employee) {
        _employee = employee;
    }

    public static EmployeeInfoDTO getInstance(){
        if(_newInstance == null){
            _newInstance = new EmployeeInfoDTO(null);
        }
        return _newInstance;
    }

    public static void setEmployee(Employee employee){
        _employee = employee;
    }

    public static Employee getEmployee(){
        return _employee;
    }
}
