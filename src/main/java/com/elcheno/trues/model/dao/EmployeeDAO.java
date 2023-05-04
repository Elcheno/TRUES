package com.elcheno.trues.model.dao;

import com.elcheno.trues.model.connections.ConnectionMySQL;
import com.elcheno.trues.model.domain.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements iDAO{

    private final static String FINDALL = "SELECT * FROM employee";

    private Connection conn;
    public EmployeeDAO(Connection conn) {
        this.conn=conn;
    }
    public EmployeeDAO() {
        this.conn=ConnectionMySQL.getConnect();
    }

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
                    aux.setName(res.getString("name"));
                    aux.setLastName(res.getString("surname"));
                    result.add(aux);
                }
            }
        }
        return null;
    }

    @Override
    public Object findById(int id) {
        return null;
    }

    @Override
    public void save(Object entity) {

    }

    @Override
    public void delete(Object entity) {

    }

    @Override
    public void close() throws Exception {

    }
}
