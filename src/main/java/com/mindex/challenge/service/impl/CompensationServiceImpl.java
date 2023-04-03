package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.exception.InvalidEmployeeIDException;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    public CompensationServiceImpl(CompensationRepository compensationRepository, EmployeeRepository employeeRepository) {
        this.compensationRepository = compensationRepository;
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Throw a Runtime Exception mentioning that an invalid employee id was specified
     * @param employeeId - the invalid employeeId supplied
     */
    private void throwInvalidEmployeeIdException(String employeeId) {
        throw new InvalidEmployeeIDException("Invalid employeeId: " + employeeId);
    }

    /**
     * @param employeeId
     * @param compensation - The compensation object data as POSTed by the client
     * @return compensation object as it was created
     */
    @Override
    public Compensation create(String employeeId, Compensation compensation) {
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

    /**
     *
     * @param employeeId - The employee whose compensation is being requested
     * @return compensation object for specified employee
     */
    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Showing compensation for employee with id [{}]", employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            throwInvalidEmployeeIdException(employeeId);
        }

        return compensationRepository.findByEmployee(employee);
    }
}
