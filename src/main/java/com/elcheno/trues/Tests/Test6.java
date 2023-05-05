package com.elcheno.trues.Tests;

import com.elcheno.trues.model.dao.LineDAO;
import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.service.LineService;

import java.sql.SQLException;
import java.util.List;

public class Test6 {
    private final static LineService lineService = new LineService();

    public static void main(String[] args) {
        try {
            Line aux = lineService.getById(1);
            System.out.println(aux.toString());
        } catch (SQLException e) {
            System.out.println("Error al buscar la Linea");
            System.err.println(e.getMessage());
        }

        System.out.println("--------------------------------------------------");

        try {
            List<Line> aux = lineService.getAll();
            for (Line line : aux) {
                System.out.println(line.toString());
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar todas las Lineas");
            System.err.println(e.getMessage());
        }

//        System.out.println("--------------------------------------------------");

//        Line aux = new Line();
//        aux.setDescription("Linea3");
//        try {
//            System.out.println(lineService.save(aux));
//        } catch (SQLException e) {
//            System.out.println("Error al guardar la Linea");
//            System.err.println(e.getMessage());
//        }

        System.out.println("--------------------------------------------------");

        Line aux2 = new Line();
        aux2.setId(3);
        aux2.setDescription("Line3");
        try {
            System.out.println(lineService.save(aux2));
        } catch (SQLException e) {
            System.out.println("Error al actualizar la Linea");
            System.err.println(e.getMessage());
        }

        System.out.println("--------------------------------------------------");

        Line aux3 = new Line();
        aux3.setId(3);
        aux3.setDescription("Line3");
        try {
            System.out.println(lineService.remove(aux3));
        } catch (SQLException e) {
            System.out.println("Error al eliminar la Linea");
            System.err.println(e.getMessage());
        }
    }
}
