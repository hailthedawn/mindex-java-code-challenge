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
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    private void throwInvalidEmployeeIdException(String employeeId) {
        throw new RuntimeException("Invalid employeeId: " + employeeId);

    }

    @Override
    public Compensation create(Compensation compensation) {
        String employeeId = compensation.getEmployee().getEmployeeId();
        LOG.debug("Creating compensation for employee [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            throwInvalidEmployeeIdException(employeeId);
        }

        compensation.setCompensationId(UUID.randomUUID().toString());

        compensation.setEmployee(employee);
        compensationRepository.insert(compensation);

        return compensation;
    }

    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Showing compensation for employee with id [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        Compensation compensation = compensationRepository.findByEmployee(employee);
        if (employee == null) {
            throwInvalidEmployeeIdException(employeeId);
        }

        return compensation;
    }
}
