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
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO implements iDAO<Product>{
    /**
     * ProductDAO class
     * @author Elcheno
     */

    private final static String FINDALL = "SELECT * FROM product";
    private final static String FINDBYID = "SELECT * FROM product WHERE id = ?";
    private final static String FINDBYIDLINE = "SELECT * FROM product WHERE id_line = ?";
    private final static String FINDBYIDLINEDATENOW = "SELECT * FROM product WHERE id_line = ? AND date_p = ?";
    private final static String INSERT = "INSERT INTO product (cod, _description, id_line, date_p) VALUES (?,?,?,?)";
    private final static String UPDATE = "UPDATE product SET cod = ?, _description = ?, id_line = ?, date_p = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM product WHERE id = ?";

    private Connection conn; //CONNECTOR TO THE DATABASE
    private LineService lineService; //CONNECTOR TO THE LINE SERVICE
    private Logger logger = Logger.getLogger(String.valueOf(this.getClass()));

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

    /**
     * Method to find a product by id_line to the database
     * @param id id_line of the product
     * @return List of products
     * @throws SQLException
     */
    public List<Product> findByIdLine(int id) {
        List<Product> result = new ArrayList<>();
        if(id>0){
            try(PreparedStatement pst = this.conn.prepareStatement(FINDBYIDLINE)){
                pst.setInt(1,id);
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
            } catch (SQLException e) {
                logger.log(Level.INFO, e.getMessage());
            }
        }
        return result;
    }

    public List<Product> findByIdLineDateNow(int id) throws SQLException {
        List<Product> result = null;
        if(id > 0)
            try(PreparedStatement pst = this.conn.prepareStatement(FINDBYIDLINEDATENOW)){
                pst.setInt(1, id);
                pst.setDate(2, Date.valueOf(java.time.LocalDate.now()));
                try(ResultSet res = pst.executeQuery()){
                    result = new ArrayList<>();
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
     * Method to find a product by id line to the database
     * @param entity id line of the product
     * @return true if the product was found
     * @throws SQLException
     */
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

    /**
     * Method to delete a product by id to the database
     * @param entity id of the product
     * @return true if the product was deleted
     * @throws SQLException
     */
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
