package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;


public class Compensation {

    private String compensationId;
    @DBRef
    private Employee employee;
    @JsonFormat(pattern="MM/dd/yyyy")
    private LocalDate effectiveDate;
    private double salary;

    public void setCompensationId(String compensationId) {
        this.compensationId = compensationId;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public double getSalary() {
        return salary;
    }
}
