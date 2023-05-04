package com.elcheno.trues.model.domain;

import java.time.LocalDate;

public class Product {
    /**
     * Product Class
     * @author Elcheno
     */
    private int id;
    private int cod;
    private String nombre;
    private String description;
    private Line line;
    private LocalDate date;

    //CONSTRUCT
    public Product(int cod, String nombre, String description){
        this.cod = cod;
        this.nombre = nombre;
        this.description = description;
    }
    public Product(){
        this(0,"","");
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
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
