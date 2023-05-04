package com.elcheno.trues.model.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class EmpLineDTO {
    /**
     * EmpLine Class
     * @author Elcheno
     */
    private Line line;
    private LocalDate date;
    private LocalTime date_e;
    private LocalTime date_s;

    //CONSTRUCT
    public EmpLineDTO(Line line, LocalDate date, LocalTime date_e, LocalTime date_s) {
        this.line = line;
        this.date = date;
        this.date_e = date_e;
        this.date_s = date_s;
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
        EmpLineDTO that = (EmpLineDTO) o;
        return Objects.equals(line, that.line) && Objects.equals(date, that.date) && Objects.equals(date_e, that.date_e) && Objects.equals(date_s, that.date_s);
    }
    @Override
    public int hashCode() {
        return Objects.hash(line, date, date_e, date_s);
    }
}
