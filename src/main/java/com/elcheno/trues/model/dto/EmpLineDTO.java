package com.elcheno.trues.model.dto;

import com.elcheno.trues.model.domain.Line;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.util.Objects;

public class EmpLineDTO {
    /**
     * EmpLineDTO Class
     * @author Elcheno
     */
    private Line line;
    private LocalDate date;
    private LocalTime date_in;
    private LocalTime date_out;

    //CONSTRUCT
    public EmpLineDTO(Line line, LocalDate date, LocalTime date_in, LocalTime date_out) {
        this.line = line;
        this.date = date;
        this.date_in = date_in;
        this.date_out = date_out;
    }
    public EmpLineDTO(){
        this(null, null, null, null);
    }

    //GETTER AND SETTER
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
    public LocalTime getDateIn() {
        return date_in;
    }
    public void setDateIn(LocalTime date_in) {
        this.date_in = date_in;
    }
    public LocalTime getDateOut() {
        return date_out;
    }
    public void setDateOut(LocalTime date_out) {
        this.date_out = date_out;
    }

    //EQUALS AND HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpLineDTO that = (EmpLineDTO) o;
        return Objects.equals(line, that.line) && Objects.equals(date, that.date) && Objects.equals(date_in, that.date_in) && Objects.equals(date_out, that.date_out);
    }
    @Override
    public int hashCode() {
        return Objects.hash(line, date, date_in, date_out);
    }
}
