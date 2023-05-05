package com.elcheno.trues.model.dao;

import com.elcheno.trues.model.connections.ConnectionMySQL;
import com.elcheno.trues.model.domain.Product;
import com.elcheno.trues.model.service.LineService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements iDAO<Product>{
    /**
     * ProductDAO class
     * @author Elcheno
     */

    private final static String FINDALL = "SELECT * FROM product";
    private final static String FINDBYID = "SELECT * FROM product WHERE id = ?";
    private final static String INSERT = "INSERT INTO product (cod, _description, id_line, date_p) VALUES (?,?,?,?)";
    private final static String UPDATE = "UPDATE product SET cod = ?, _description = ?, id_line = ?, date_p = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM product WHERE id = ?";

    private Connection conn; //CONNECTOR TO THE DATABASE
    private LineService lineService; //CONNECTOR TO THE LINE SERVICE

    //CONSTRUCT
    public ProductDAO(Connection conn) {
        this.conn=conn;
        this.lineService = new LineService();
    }
    public ProductDAO(){
        this.conn= ConnectionMySQL.getConnect();
        this.lineService = new LineService();
    }

    /**
     * Method to find all products to the database
     * @return List of all products
     * @throws SQLException
     */
    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> result = new ArrayList<>();
        try(PreparedStatement pst = this.conn.prepareStatement(FINDALL)){
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
                    Product aux = new Product();
                    aux.setId(res.getInt("id"));
                    aux.setCod(res.getInt("cod"));
                    aux.setDescription(res.getString("_description"));
                    aux.setLine(this.lineService.getById(res.getInt("id_line")));
                    aux.setDate(res.getDate("date_p").toLocalDate());
                    result.add(aux);
                }
            }
        }
        return result;
    }

    /**
     * Method to find a product by id to the database
     * @param id id of the product
     * @return Product
     * @throws SQLException
     */
    @Override
    public Product findById(int id) throws SQLException {
        Product result = null;
        try(PreparedStatement pst = this.conn.prepareStatement(FINDBYID)){
            pst.setInt(1,id);
            try(ResultSet res = pst.executeQuery()){
                if(res.next()){
                    result = new Product();
                    result.setId(res.getInt("id"));
                    result.setCod(res.getInt("cod"));
                    result.setDescription(res.getString("_description"));
                    result.setLine(this.lineService.getById(res.getInt("id_line")));
                    result.setDate(res.getDate("date_p").toLocalDate());
                }
            }
        }
        return result;
    }

    @Override
    public boolean save(Product entity) throws SQLException {
        boolean result = false;
        if(entity!=null){
            Product aux = this.findById(entity.getId());
            if(aux==null){
                //INSERT
                try(PreparedStatement pst = this.conn.prepareStatement(INSERT)){
                    pst.setInt(1,entity.getCod());
                    pst.setString(2,entity.getDescription());
                    pst.setInt(3, entity.getLine().getId());
                    pst.setString(4, entity.getDate().toString());
                    if(pst.executeUpdate()==1){
                        result = true;
                    }
                }
            }else{
                //UPDATE
                try(PreparedStatement pst = this.conn.prepareStatement(UPDATE)){
                    pst.setInt(1,entity.getCod());
                    pst.setString(2,entity.getDescription());
                    pst.setInt(3, entity.getLine().getId());
                    pst.setString(4, entity.getDate().toString());
                    pst.setInt(5,entity.getId());
                    if(pst.executeUpdate()==1){
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean delete(Product entity) throws SQLException {
        boolean result = false;
        if(entity!=null && entity.getId()>0){
            try(PreparedStatement pst = this.conn.prepareStatement(DELETE)){
                pst.setInt(1,entity.getId());
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