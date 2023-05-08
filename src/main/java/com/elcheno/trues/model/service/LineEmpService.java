package com.elcheno.trues.model.service;

import com.elcheno.trues.model.dao.LineEmpDAO;
import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.dto.LineEmpDTO;

import java.sql.SQLException;
import java.util.List;

public class LineEmpService {
    private LineEmpDAO lineEmpDAO;

    public LineEmpService(){
        lineEmpDAO = new LineEmpDAO();
    }

    public List<LineEmpDTO> getAll(Line line) throws SQLException {
        return lineEmpDAO.findAll(line);
    }
}
