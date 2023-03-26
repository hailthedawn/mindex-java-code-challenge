package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
        String employeeId = compensation.getEmployee().getEmployeeId();
        LOG.debug("Creating compensation for employee [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
//        String effectiveDate = "04/02/2022";
//        compensation.setEffectiveDate(effectiveDate);
        compensation.setCompensationId(UUID.randomUUID().toString());

        compensation.setEmployee(employee);
        LOG.debug("Employeeeeeee: "+employee.getFirstName() + employee.getEmployeeId());
        LOG.debug("COmpensationnnnnnnn: "+compensation.getEmployee().getEmployeeId() + compensation.getEmployee().getFirstName()+compensation.getSalary());
        compensationRepository.insert(compensation);

        return compensation;
    }

    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Showing compensation for employee with id [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        Compensation compensation = compensationRepository.findByEmployee(employee);
        if (compensation == null) {
            throw new RuntimeException("Invalid employeeId provided: " + employeeId);
        }

        return compensation;
    }
}
