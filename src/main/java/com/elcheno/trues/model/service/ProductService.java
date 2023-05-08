package com.elcheno.trues.model.service;

import com.elcheno.trues.model.dao.ProductDAO;
import com.elcheno.trues.model.domain.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    /**
     * Product Service Class
     * @see ProductDAO
     * @author Elcheno
     */

    private ProductDAO productDAO;

    //CONSTRUCT
    public ProductService(){
        productDAO = new ProductDAO();
    }

    public List<Product> getAll() throws SQLException {
        return productDAO.findAll();
    }

    public Product getById(int id) throws SQLException {
        return productDAO.findById(id);
    }

    public List<Product> getByIdLine(int idLine) throws SQLException {
        return productDAO.findByIdLine(idLine);
    }

    public List<Product> getByIdLineDateNow(int idLine) throws SQLException {
        return productDAO.findByIdLineDateNow(idLine);
    }

    public boolean save(Product product) throws SQLException {
        return productDAO.save(product);
    }

    public boolean remove(Product product) throws SQLException {
        return productDAO.delete(product);
    }
}
