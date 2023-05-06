package com.elcheno.trues.model.dao;

import com.elcheno.trues.model.connections.ConnectionMySQL;
import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.dto.EmpLineDTO;
import com.elcheno.trues.model.service.LineService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Time;

public class EmpLineDAO implements iDAOdto<EmpLineDTO, Employee>{
    /**
     * EmpLineDAO class
     * @author Elcheno
     */

    private final static String FINDBYID = "SELECT id_line, _date, date_in, date_out FROM emp_line WHERE id_emp = ?";
    private final static String FINDBYPK = "SELECT id_line, _date, date_in, date_out FROM emp_line WHERE id_emp = ? AND id_line = ? AND _date = ? AND date_in = ?";
    private final static String INSERT = "INSERT INTO emp_line (id_emp, id_line, _date, date_in, date_out) VALUES (?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE emp_line SET date_out = ? WHERE id_emp = ? AND id_line = ? AND _date = ? AND date_in = ?";

    private Connection conn; //CONNECTOR TO THE DATABASE
    //CONSTRUCT
    public EmpLineDAO(Connection conn){
        this.conn=conn;
    }
    public EmpLineDAO(){
        this.conn= ConnectionMySQL.getConnect();
    }

    /**
     * Method to find all EmpLineDTO(relation to Line) to the database by employee
     * @param entity Employee
     * @return List of all EmpLineDTO
     * @throws SQLException
     */
    @Override
    public List<EmpLineDTO> findAll(Employee entity) throws SQLException {
        List<EmpLineDTO> result = null;
        if(entity != null && entity.getId() > 0){
            LineService lineService = new LineService();
            try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)){
                pst.setInt(1, entity.getId());
                try(ResultSet res = pst.executeQuery()){
                    result = new ArrayList<>();
                    while(res.next()){
                        EmpLineDTO aux = new EmpLineDTO();
                        aux.setLine(lineService.getById(res.getInt("id_line")));
                        aux.setDate(res.getDate("_date").toLocalDate());
                        aux.setDateIn(res.getTime("date_in").toLocalTime());
                        if(res.getTime("date_out") != null){
                            aux.setDateOut(res.getTime("date_out").toLocalTime());
                        }
                        result.add(aux);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Method to find one EmpLineDTO(relation to Line) to the database by employee
     * @param entity EmpLineDTO
     * @param id int id of Employee
     * @return EmpLineDTO if exist
     * @throws SQLException
     */
    public EmpLineDTO findByPK(EmpLineDTO entity, int id) throws SQLException {
        EmpLineDTO result = null;
        if(entity!=null){
            try(PreparedStatement pst = this.conn.prepareStatement(FINDBYPK)){
                pst.setInt(1, id);
                pst.setInt(2, entity.getLine().getId());
                pst.setDate(3, Date.valueOf(entity.getDate()));
                pst.setTime(4, Time.valueOf(entity.getDateIn()));
                try(ResultSet res = pst.executeQuery()){
                    if(res.next()){
                        result = new EmpLineDTO();
                        result.setLine(new LineService().getById(res.getInt("id_line")));
                        result.setDate(res.getDate("_date").toLocalDate());
                        result.setDateIn(res.getTime("date_in").toLocalTime());
                        if(res.getTime("date_out") != null){
                            result.setDateOut(res.getTime("date_out").toLocalTime());
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Method to save or update EmpLineDTO(relation to Line) to the database by employee
     * @param entityDTO EmpLineDTO
     * @param entityEmp Employee
     * @return true if save or update, false if not
     * @throws SQLException
     */
    @Override
    public boolean save(EmpLineDTO entityDTO, Employee entityEmp) throws SQLException {
        boolean result = false;
        if(entityDTO != null && entityEmp != null){
            EmpLineDTO aux = this.findByPK(entityDTO, entityEmp.getId());
            if(entityDTO.getDateOut() == null && aux == null){
                //INSERT
                try(PreparedStatement pst = this.conn.prepareStatement(INSERT)){
                    pst.setInt(1, entityEmp.getId());
                    pst.setInt(2, entityDTO.getLine().getId());
                    pst.setDate(3, Date.valueOf(entityDTO.getDate()));
                    pst.setTime(4, Time.valueOf(entityDTO.getDateIn()));
                    pst.setTime(5, null);
                    if(pst.executeUpdate() == 1){
                        result = true;
                    }
                }
            }else if(entityDTO.getDateOut() != null && aux.getDateOut() == null){
                //UPDATE
                try(PreparedStatement pst = this.conn.prepareStatement(UPDATE)){
                    pst.setTime(1, Time.valueOf(entityDTO.getDateOut()));
                    pst.setInt(2, entityEmp.getId());
                    pst.setInt(3, entityDTO.getLine().getId());
                    pst.setDate(4, Date.valueOf(entityDTO.getDate()));
                    pst.setTime(5, Time.valueOf(entityDTO.getDateIn()));
                    if(pst.executeUpdate() == 1){
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void close() throws Exception {

    }
}