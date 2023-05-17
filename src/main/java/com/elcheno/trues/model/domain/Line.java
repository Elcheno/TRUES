package com.elcheno.trues.model.domain;

import com.elcheno.trues.model.dto.LineEmpDTO;
import com.elcheno.trues.model.service.ProductService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Line {
    /**
     * Line Class
     * @author Elcheno
     */
    private int id;
    private String description;
    private List<LineEmpDTO> lineEmpsDTO;
    private List<Product> products;

    //CONSTRUCT
    public Line(String description){
        this.description = description;
        products = new ArrayList<>();
    }
    public Line(){
        this("");
    }

    //GETTER AND SETTER
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<LineEmpDTO> getLineEmpsDTO() {
        return lineEmpsDTO;
    }
    public void setLineEmpsDTO(List<LineEmpDTO> lineEmpsDTO) {
        this.lineEmpsDTO = lineEmpsDTO;
    }
    public void addLineEmpsDTO(LineEmpDTO lineEmp){
        if(!lineEmpsDTO.contains(lineEmp)){
            this.lineEmpsDTO.add(lineEmp);
        }
    }
    public List<Product> getProducts() {
        if(products == null){
            ProductService productService = new ProductService();
            products = productService.getByIdLine(id);
        }
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public void addProduct(Product product){
        if(!products.contains(product)){
            this.products.add(product);
        }
    }

    //EQUALS AND HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return id == line.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //TOSTRING
    @Override
    public String toString() {
        return "Line{" +
                "description='" + description + '\'' +
                "products='" + products + '\'' +
                '}';
    }
}
