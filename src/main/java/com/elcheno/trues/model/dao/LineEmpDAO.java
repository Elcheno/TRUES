package com.elcheno.trues.model.dao;

import com.elcheno.trues.model.connections.ConnectionMySQL;
import com.elcheno.trues.model.dto.LineEmpDTO;

import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.service.EmployeeService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LineEmpDAO implements iDAOdto<LineEmpDTO, Line> {
    /**
     * LineEmpDAO Class
     * @Author Elcheno
     */

    private final static String FINDALL = "SELECT id_emp, _date, date_in, date_out FROM line_emp WHERE id_line = ?;";

    private Connection conn;//CONNECTION TO DATABASE
    //CONSTRUCT
    public LineEmpDAO(Connection conn){
        this.conn = conn;
    }
    public LineEmpDAO(){
        this.conn = ConnectionMySQL.getConnect();
    }

    /**
     * Find all LineEmpDTO by Line
     * @param entity Line
     * @return List<LineEmpDTO>
     * @throws SQLException
     */
    @Override
    public List<LineEmpDTO> findAll(Line entity) throws SQLException {
        List<LineEmpDTO> result = null;
        if(entity != null){
            try(PreparedStatement pst = this.conn.prepareStatement(FINDALL)){
                pst.setInt(1, entity.getId());
                try(ResultSet res = pst.executeQuery()){
                    EmployeeService employeeService = new EmployeeService();
                    while(res.next()){
                        LineEmpDTO lineEmpDTO = new LineEmpDTO();
                        lineEmpDTO.setEmployee(employeeService.getById(res.getInt("id_emp")));
                        lineEmpDTO.setDate(res.getDate("_date").toLocalDate());
                        lineEmpDTO.setDate_s(res.getTime("date_in").toLocalTime());
                        lineEmpDTO.setDate_e(res.getTime("date_out").toLocalTime());
                        result.add(lineEmpDTO);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean save(LineEmpDTO entityDTO, Line entityLine) throws SQLException {
        return false;
    }

    @Override
    public void close() throws Exception {
    }
}
