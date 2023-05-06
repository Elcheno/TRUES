package com.elcheno.trues.Tests;

import com.elcheno.trues.model.dao.LineDAO;
import com.elcheno.trues.model.dao.ProductDAO;
import com.elcheno.trues.model.domain.Line;
import com.elcheno.trues.model.domain.Product;
import com.elcheno.trues.model.service.LineService;
import com.elcheno.trues.model.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class Test7 {
    private final static ProductService productService = new ProductService();
    private final static LineService lineService = new LineService();

    public static void main(String[] args) {
        try {
            List<Product> aux = productService.getAll();
            for(Product p : aux){
                System.out.println(p.toString());
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar todos los productos");
            System.err.println(e.getMessage());
        }

        System.out.println("--------------------------------------------------");

        try {
            Product aux = productService.getById(1);
            System.out.println(aux.toString());
        } catch (SQLException e) {
            System.out.println("Error al buscar el producto");
            System.err.println(e.getMessage());
        }

//        System.out.println("--------------------------------------------------");
//
//        try {
//            Product aux = new Product();
//            aux.setId(3);
//            aux.setCod(3421);
//            aux.setDescription("Product3");
//            aux.setLine(lineService.getById(1));
//            aux.setDate(java.time.LocalDate.now());
//            System.out.println(productService.save(aux));
//        } catch (SQLException e) {
//            System.out.println("Error al buscar la Linea");
//            System.err.println(e.getMessage());
//        }
//
//        System.out.println("--------------------------------------------------");
//
//        try {
//            Product aux = productService.getById(3);
//            System.out.println(productService.remove(aux));
//        } catch (SQLException e) {
//            System.out.println("Error al buscar el producto");
//            System.err.println(e.getMessage());
//        }
    }
}
