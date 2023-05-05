package com.elcheno.trues.model.dao;

import com.elcheno.trues.model.connections.ConnectionMySQL;
import com.elcheno.trues.model.domain.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements iDAO<Employee>{
    /**
     * EmployeeDAO class
     * @author Elcheno
     */

    //DECLARATION OF QUERYS FOR THE DATABASE
    private final static String FINDALL = "SELECT * FROM employee";
    private final static String FINDBYID = "SELECT * FROM employee WHERE id = ?";
    private final static String INSERT = "INSERT INTO employee (cod, dni, _name, lastname) VALUES (?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE employee SET cod = ?, dni = ?, _name = ?, lastname = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM employee WHERE id = ?";

    private Connection conn; //CONNECTION TO THE DATABASE
    //CONSTRUCT
    public EmployeeDAO(Connection conn) {
        this.conn=conn;
    }
    public EmployeeDAO() {
        this.conn=ConnectionMySQL.getConnect();
    }

    /**
     * Method to find all employees to the database
     * @return List of all employees
     * @throws SQLException
     */
    @Override
    public List findAll() throws SQLException {
        List<Employee> result = new ArrayList<>();
        try(PreparedStatement pst = this.conn.prepareStatement(FINDALL)){
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
                    Employee aux = new Employee();
                    aux.setId(res.getInt("id"));
                    aux.setCod(res.getInt("cod"));
                    aux.setDni(res.getString("dni"));
                    aux.setName(res.getString("_name"));
                    aux.setLastName(res.getString("lastname"));
                    result.add(aux);
                }
            }
        }
        return result;
    }

    /**
     * Method to find an employee by id
     * @param id id of the employee
     * @return Employee with the id passed by parameter
     * @throws SQLException
     */
    @Override
    public Employee findById(int id) throws SQLException {
        Employee result = null;
        try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)){
            pst.setInt(1, id);
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
                    result = new Employee();
                    result.setId(res.getInt("id"));
                    result.setCod(res.getInt("cod"));
                    result.setDni(res.getString("dni"));
                    result.setName(res.getString("_name"));
                    result.setLastName(res.getString("lastname"));
                }
            }
        }
        return result;
    }

    /**
     * Method to save or update an employee
     * @param entity employee to save or update
     * @return true if the employee was saved or updated, false if not
     * @throws SQLException
     */
    @Override
    public boolean save(Employee entity) throws SQLException {
        boolean result = false;
        if(entity!=null){
            Employee aux = findById(entity.getId());
            if(aux==null){
                //INSERT
                try(PreparedStatement pst = this.conn.prepareStatement(INSERT)){
                    pst.setInt(1, entity.getCod());
                    pst.setString(2, entity.getDni());
                    pst.setString(3, entity.getName());
                    pst.setString(4, entity.getLastName());
                    if(pst.executeUpdate()==1){
                        result= true;
                    }
                }
            }else{
                //UPDATE
                try(PreparedStatement pst = this.conn.prepareStatement(UPDATE)){
                    pst.setInt(1, entity.getCod());
                    pst.setString(2, entity.getDni());
                    pst.setString(3, entity.getName());
                    pst.setString(4, entity.getLastName());
                    pst.setInt(5, aux.getId());
                    if(pst.executeUpdate()==1){
                        result= true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Method to delete an employee
     * @param entity employee to delete
     * @return true if the employee was deleted, false if not
     * @throws SQLException
     */
    @Override
    public boolean delete(Employee entity) throws SQLException {
        boolean result = false;
        if(entity!=null && entity.getId()!=0){
            try(PreparedStatement pst = this.conn.prepareStatement(DELETE)){
                pst.setInt(1, entity.getId());
                if(pst.executeUpdate()==1){
                    result= true;
                }
            }
        }
        return result;
    }

    @Override
    public void close() throws Exception {

    }
}
