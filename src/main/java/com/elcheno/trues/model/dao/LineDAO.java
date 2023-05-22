package com.elcheno.trues.model.dao;

import com.elcheno.trues.model.connections.ConnectionMySQL;
import com.elcheno.trues.model.domain.Line;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineDAO implements iDAO<Line>{
    /**
     * LineDAO class
     * @author Elcheno
     */

    //DECLARATION OF QUERYS FOR THE DATABASE
    private final static String FINDALL = "SELECT id, _description FROM line";
    private final static String FINDBYID = "SELECT id, _description FROM line WHERE id = ?";
    private final static String INSERT = "INSERT INTO line (_description) VALUES (?)";
    private final static String UPDATE = "UPDATE line SET _description = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM line WHERE id = ?";


    private Connection conn; //CONNECTOR TO THE DATABASE

    //CONSTRUCT
    public LineDAO(Connection conn) {
        this.conn=conn;
    }
    public LineDAO() {
        this.conn= ConnectionMySQL.getConnect();
    }

    /**
     * Method to find all employees to the database
     * @return List of all employees
     * @throws SQLException
     */
    @Override
    public List<Line> findAll() throws SQLException {
        List<Line> result = new ArrayList<>();
        try(PreparedStatement pst = this.conn.prepareStatement(FINDALL)){
            try(ResultSet res = pst.executeQuery()){
                result = new ArrayList<>();
                while(res.next()) {
                    Line aux = new Line();
                    aux.setId(res.getInt("id"));
                    aux.setDescription(res.getString("_description"));
                    aux.setLineEmpsDTO(null);
                    aux.setProducts(null);
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
    public Line findById(int id) throws SQLException {
        Line result = null;
        try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)){
            pst.setInt(1, id);
            try(ResultSet res = pst.executeQuery()){
                if(res.next()){
                    result = new Line();
                    result.setId(res.getInt("id"));
                    result.setDescription(res.getString("_description"));
                    result.setLineEmpsDTO(null);
                    result.setProducts(null);
                }
            }
        }
        return result;
    }

    /**
     * Method to save or update an employee in the database
     * @param entity employee to save
     * @return true if the employee is saved or false if not
     * @throws SQLException
     */
    @Override
    public boolean save(Line entity) throws SQLException {
        boolean result = false;
        if(entity!=null){
            Line aux = findById(entity.getId());
            if(aux==null){
                //INSERT
                try(PreparedStatement pst = this.conn.prepareStatement(INSERT)){
                    pst.setString(1, entity.getDescription());
                    if(pst.executeUpdate()==1){
                        result = true;
                    }
                }
            }else{
                //UPDATE
                try(PreparedStatement pst = this.conn.prepareStatement(UPDATE)){
                    pst.setString(1, entity.getDescription());
                    pst.setInt(2, entity.getId());
                    if(pst.executeUpdate()==1){
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Method to delete an employee in the database
     * @param entity employee to delete
     * @return true if the employee is deleted or false if not
     * @throws SQLException
     */
    @Override
    public boolean delete(Line entity) throws SQLException {
        boolean result = false;
        if(entity!=null && entity.getId()>0){
            try(PreparedStatement pst = this.conn.prepareStatement(DELETE)){
                pst.setInt(1, entity.getId());
                if(pst.executeUpdate()==1){
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public void close() throws Exception {

    }
}
