package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.exception.InvalidEmployeeIDException;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;

@Service
public class ReportingStructureImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     *
     * @param id - the employee id whose ReportingStructure is being queried
     * @return ReportingStructure object with the directReport count
     */
    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Retrieving reporting structure for employee with id [{}]", id);

        Employee currEmployee = employeeRepository.findByEmployeeId(id);

        if (currEmployee == null) {
            throw new InvalidEmployeeIDException("Invalid employeeId: " + id);
        }

        Stack<Employee> empStack = new Stack<>();
        empStack.push(currEmployee);
        int empCtr = 0;
        while(empStack.size()!=0) {
            Employee employee = employeeRepository.findByEmployeeId(empStack.pop().getEmployeeId());
            List<Employee> directReports = employee.getDirectReports();
            if(directReports != null) {
                empCtr += directReports.size();
                empStack.addAll(directReports);
            }

        }
        return new ReportingStructure(currEmployee, empCtr);
    }
}
