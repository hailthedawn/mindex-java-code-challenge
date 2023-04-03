package com.mindex.challenge.data;

public class ReportingStructure {
    private final Employee employee;
    private final int numberOfReports;

    public ReportingStructure(Employee employee, int numberOfReports) {
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    public int getDirectReports() {
        return numberOfReports;
    }
}
