package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.exception.InvalidEmployeeIDException;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureImplTest {

    @Autowired
    private ReportingStructureService reportingStructureService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        reportingStructureService = new ReportingStructureImpl(employeeRepository);
    }

    private Employee createEmployeeObjectWithId(String employeeId){
        // set up Employee object to be returned by the mocked employeeRepository
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId(employeeId);
        return testEmployee;
    }

    /**
     * Ensure that the read() method returns the correct number of directReports
     */
    @Test
    public void read() {
        // set up Employee objects to be returned by the mocked employeeRepository
        Employee testEmployee1 = createEmployeeObjectWithId("1");
        Employee testEmployee2 = createEmployeeObjectWithId("2");
        Employee testEmployee3 = createEmployeeObjectWithId("3");
        Employee testEmployee4 = createEmployeeObjectWithId("4");

        // set up directReport Structure for employees
        List<Employee> directReportsFor3 = new ArrayList<>(); directReportsFor3.add(testEmployee4);
        testEmployee3.setDirectReports(directReportsFor3);
        List<Employee> directReportsFor1 = new ArrayList<>();
        directReportsFor1.add(testEmployee3);
        directReportsFor1.add(testEmployee2);
        testEmployee1.setDirectReports(directReportsFor1);

        // set up mock responses for Employee repository
        doReturn(testEmployee1).when(employeeRepository).findByEmployeeId("1");
        doReturn(testEmployee2).when(employeeRepository).findByEmployeeId("2");
        doReturn(testEmployee3).when(employeeRepository).findByEmployeeId("3");
        doReturn(testEmployee4).when(employeeRepository).findByEmployeeId("4");

        // Test the ReportingStructure Read method for all employees
        ReportingStructure reportingStructure4 = reportingStructureService.read("4");
        assert(reportingStructure4.getDirectReports()==0);
        ReportingStructure reportingStructure3 = reportingStructureService.read("3");
        assert(reportingStructure3.getDirectReports()==1);
        ReportingStructure reportingStructure2 = reportingStructureService.read("2");
        assert(reportingStructure2.getDirectReports()==0);

    }

    /**
     * Ensure that an InvalidEmployeeIDException is thrown when an invalid employee id is supplied to the read method.
     */
    @Test(expected = InvalidEmployeeIDException.class)
    public void testReadForInvalidEmployeeId() {
        String fakeEmployeeId = "fake_id";
        // Test the reporting structure Read method
        ReportingStructure readReportingStructure = reportingStructureService.read(fakeEmployeeId);

        // check that no Reporting Structure was returned
        assertNull(readReportingStructure);
    }
}