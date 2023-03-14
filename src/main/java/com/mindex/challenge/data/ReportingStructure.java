package com.mindex.challenge.data;

public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;

    public ReportingStructure(Employee employee, int numberOfReports) {
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    public int getDirectReports() {
        return numberOfReports;
    }

//    * READ
//    * HTTP Method: GET
//    * URL: localhost:8080/reporting-structure/{id}
//    * RESPONSE: ReportingStructure
}
