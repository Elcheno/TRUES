package com.elcheno.trues.Tests;

import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.service.LineService;
import com.elcheno.trues.model.service.ProductService;

import java.sql.SQLException;

public class Test8 {
    private final static LineService lineService = new LineService();
    private final static ProductService productService = new ProductService();

    public static void main(String[] args) {
        try {
            Line line = lineService.getById(1);
            System.out.println(line.toString());
            System.out.println("Products:" + line.getProducts());
        } catch (SQLException e) {
            System.out.println("Error al obtener la linea");
            System.err.println(e.getMessage());
        }
    }
}
