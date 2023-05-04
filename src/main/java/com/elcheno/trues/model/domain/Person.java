package com.elcheno.trues.model.domain;

import java.util.Objects;

public class Person {
    /**
     * Person class
     * @author Elcheno
     */
    private int id;
    private String dni;
    private String name;
    private String lastName;

    //CONSTRUCT
    public Person(String dni, String name, String lastName){
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
    }

    //GETTER AND SETTER
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //EQUALS AND HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //TOSTRING

    @Override
    public String toString() {
        return  "id=" + id +
                ", dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
