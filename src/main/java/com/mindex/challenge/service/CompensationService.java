package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.impl.CompensationServiceImpl;

public interface CompensationService {

    Compensation read(String employeeId);

    Compensation create(Compensation compensation);
}
