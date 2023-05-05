package com.elcheno.trues.model.service;

import com.elcheno.trues.model.dao.LineDAO;
import com.elcheno.trues.model.domain.Line;
import java.sql.SQLException;
import java.util.List;

public class LineService {
    /**
     * Service class for LineDAO
     * @see LineDAO
     * @autor Elcheno
     */
    private LineDAO lineDAO;

    //CONSTRUCT
    public LineService() {
        lineDAO = new LineDAO();
    }

    public List<Line> getAll() throws SQLException {
        return lineDAO.findAll();
    }

    public Line getById(int id) throws SQLException {
        return lineDAO.findById(id);
    }

    public boolean save(Line line) throws SQLException {
        return lineDAO.save(line);
    }

    public boolean remove(Line line) throws SQLException {
        return lineDAO.delete(line);
    }
}
