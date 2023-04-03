package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.exception.InvalidEmployeeIDException;
import com.mindex.challenge.service.CompensationService;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    @Autowired
    private CompensationService compensationService;

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    CompensationRepository compensationRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        compensationService = new CompensationServiceImpl(compensationRepository, employeeRepository);
    }


    /**
     * Test that compensation id was created successfully
     */
    @Test
    public void testCreate() {
        // set up Employee object to be returned by the mocked employeeRepository
        String testEmployeeId = "1111";
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId(testEmployeeId);
        doReturn(testEmployee).when(employeeRepository).findByEmployeeId(testEmployeeId);

        // Set up the compensation object to be created
        Compensation testCompensation = new Compensation();
        testCompensation.setSalary(72850);
        testCompensation.setEffectiveDate(LocalDate.parse("08/09/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        // Create the Compensation for the specified employee
        Compensation createdCompensation = compensationService.create(testEmployeeId, testCompensation);

        // verify that a findByEmployee was issued to the compensationRepository
        verify(compensationRepository, times(1)).insert(testCompensation);
        // check that a Compensation was created
        assertNotNull(createdCompensation.getCompensationId());
        // assert that the created Compensation has the same parameters that we specified
        assertCompensationEquivalence(createdCompensation, testCompensation);
    }

    /**
     * Test that compensation was not created because provided employee id is invalid
     */
    @Test(expected = InvalidEmployeeIDException.class)
    public void testCreateForInvalidEmployeeId() {

        // set up compensation object to be created
        Compensation testCompensation = new Compensation();
        testCompensation.setCompensationId("fake_id");
        testCompensation.setEffectiveDate(LocalDate.now());
        // set up Employee associated with the Compensation
        Employee testEmployee = new Employee();
        String testEmployeeId = "abc_testId"; // invalid id
        testEmployee.setEmployeeId(testEmployeeId);
        testCompensation.setEmployee(testEmployee);

        // Create the Compensation for the specified employee
        Compensation createdCompensation = compensationService.create(testEmployeeId, testCompensation);

        // assert that compensation wasn't created
        assert createdCompensation != null;
        assertNull(createdCompensation.getCompensationId());

    }

    /**
     * Test that an existing Compensation was read successfully
     */
    @Test
    public void testRead() {
        // set up Employee object to be returned by the mocked employeeRepository
        String testEmployeeId = "121213";
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId(testEmployeeId);
        doReturn(testEmployee).when(employeeRepository).findByEmployeeId(testEmployeeId);

        // Set up Compensation object to be returned by the mocked compensationRepository
        Compensation testCompensation = new Compensation();
        String compensationId = "comp_12";
        testCompensation.setCompensationId(compensationId);
        testCompensation.setEmployee(testEmployee);
        doReturn(testCompensation).when(compensationRepository).findByEmployee(testEmployee);

        // Test the compensation Read method
        Compensation readCompensation = compensationService.read(testEmployeeId);

        // check that a Compensation was returned
        assertNotNull(readCompensation.getCompensationId());
        // verify that a findByEmployee call was made to the compensationRepository
        verify(compensationRepository, times(1)).findByEmployee(testEmployee);
        // assert that the created Compensation has the same parameters that we specified
        assertCompensationEquivalence(readCompensation, testCompensation);
    }

    /**
     * Ensure that an InvalidEmployeeIDException is thrown when an invalid employee id is supplied to the read method.
     */
    @Test(expected = InvalidEmployeeIDException.class)
    public void testReadForInvalidEmployeeId() {
        String fakeEmployeeId = "fake_id";
        // Test the compensation Read method
        Compensation readCompensation = compensationService.read(fakeEmployeeId);

        // check that no Compensation was returned
        assertNull(readCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary(),0);
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }

}