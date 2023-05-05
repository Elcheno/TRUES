package com.elcheno.trues.Tests;

import com.elcheno.trues.model.dao.LineDAO;
import com.elcheno.trues.model.domain.Line;

import java.sql.SQLException;
import java.util.List;

public class Test5 {
    private final static LineDAO lineDAO = new LineDAO();

    public static void main(String[] args) {
        try {
            List<Line> aux = lineDAO.findAll();
            for(Line l : aux){
                System.out.println(l.toString());
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar todas las Lineas");
            System.err.println(e.getMessage());
        }
    }
}
