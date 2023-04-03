package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.exception.InvalidEmployeeIDException;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ReportingStructureController {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;


    @GetMapping("/employee/{id}/reporting-structure")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received reporting structure get request for id [{}]", id);

        return reportingStructureService.read(id);
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
}
