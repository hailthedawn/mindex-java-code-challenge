package com.mindex.challenge.controller;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.exception.InvalidEmployeeIDException;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/employee/{employeeId}/compensation")
    public Compensation create(@PathVariable String employeeId, @RequestBody Compensation compensation
                               ) {
        LOG.debug("Received compensation create request for employee id [{}]", employeeId);

        return compensationService.create(employeeId, compensation);
    }

    @GetMapping("/employee/{employeeId}/compensation")
    public Compensation read(@PathVariable String employeeId) {
        LOG.debug("Received compensation get request for employee id [{}]", employeeId);

        return compensationService.read(employeeId);
    }


    @ExceptionHandler(InvalidEmployeeIDException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidEmployeeIdException(
            InvalidEmployeeIDException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
//        , HttpHeaders headers, HttpStatus status, WebRequest request
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

}
