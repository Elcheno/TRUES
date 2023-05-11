package com.elcheno.trues.model.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Product {
    /**
     * Product Class
     * @author Elcheno
     */
    private int id;
    private int cod;
    private String description;
    private Line line;
    private LocalDate date;

    //CONSTRUCT
    public Product(int cod, String description, Line line, LocalDate date){
        this.cod = cod;
        this.description = description;
        this.line = line;
        this.date = date;
    }
    public Product(){
        this(0,"",null,null);
    }

    //GETTER AND SETTER
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCod() {
        return cod;
    }
    public void setCod(int cod) {
        this.cod = cod;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Line getLine() {
        return line;
    }
    public void setLine(Line line) {
        this.line = line;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    //EQUALS AND HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return cod == product.cod;
    }
    @Override
    public int hashCode() {
        return Objects.hash(cod);
    }

    //TO STRING
    @Override
    public String toString() {
        return "Product{" +
                "cod=" + cod +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
