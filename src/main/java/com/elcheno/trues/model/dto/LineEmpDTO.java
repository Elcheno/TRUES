package com.elcheno.trues.model.dto;

import com.elcheno.trues.model.domain.Employee;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class LineEmpDTO {
    /**
     * LineEmpDTO Class
     * @author Elcheno
     */
    private Employee employee;
    private LocalDate date;
    private LocalTime date_e;
    private LocalTime date_s;

    //CONSTRUCT
    public LineEmpDTO(Employee employee, LocalDate date, LocalTime date_e, LocalTime date_s) {
        this.employee = employee;
        this.date = date;
        this.date_e = date_e;
        this.date_s = date_s;
    }
    public LineEmpDTO(){
        this(null, null, null, null);
    }

    //GETTER AND SETTER
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalTime getDate_e() {
        return date_e;
    }
    public void setDate_e(LocalTime date_e) {
        this.date_e = date_e;
    }
    public LocalTime getDate_s() {
        return date_s;
    }
    public void setDate_s(LocalTime date_s) {
        this.date_s = date_s;
    }

    //EQUALS AND HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineEmpDTO that = (LineEmpDTO) o;
        return Objects.equals(employee, that.employee) && Objects.equals(date, that.date) && Objects.equals(date_e, that.date_e) && Objects.equals(date_s, that.date_s);
    }
    @Override
    public int hashCode() {
        return Objects.hash(employee, date, date_e, date_s);
    }
}
