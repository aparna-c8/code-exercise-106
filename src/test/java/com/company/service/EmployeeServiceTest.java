package com.company.service;

import com.company.dao.Employee;
import com.company.dao.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;
    private Map<Integer, Employee> employeeById;
    private Map<Integer, List<Employee>> reporteesById;

    @BeforeEach
    void setUp() {
        employeeById = new HashMap<>();
        reporteesById = new HashMap<>();
        employeeService = new EmployeeService(employeeById, reporteesById);
    }

    @Test
    void test_readCsvFile() {
        Result result = employeeService.findResults("/mock.csv");
        assert (!result.getManagersEarningMore().isEmpty());
    }
    @Test
    void test_managerEarningLessThanReportees() {
        employeeById.put(1, new Employee(1, "CEO", "X", 60000, -1)); //CEO
        employeeById.put(2, new Employee(2, "Manager1", "Y", 40000, 1));
        employeeById.put(3, new Employee(3, "Manager2", "Z", 70000, 2));
        employeeById.put(4, new Employee(4, "Emp", "A", 30000, 2));
        reporteesById.put(1, List.of(employeeById.get(2)));
        reporteesById.put(2, List.of(employeeById.get(3), employeeById.get(4)));
        Result result = employeeService.findResults("dummy");
        assert !result.getManagersEarningLess().isEmpty();
        assert result.getManagersEarningMore().isEmpty();
        assert result.getEmployeesWithLongReportingLine().isEmpty();
    }
    @Test
    void test_managerEarningMoreThanReportees() {
        employeeById.put(1, new Employee(1, "CEO", "X", 100000, -1)); //CEO
        employeeById.put(2, new Employee(2, "Manager1", "Y", 40000, 1));
        employeeById.put(3, new Employee(3, "Manager2", "Z", 70000, 1));
        employeeById.put(4, new Employee(4, "Emp", "A", 30000, 2));
        reporteesById.put(1, List.of(employeeById.get(2), employeeById.get(3)));
        reporteesById.put(2, List.of(employeeById.get(4)));
        Result result = employeeService.findResults("dummy");
        assert result.getManagersEarningLess().isEmpty();
        assert !result.getManagersEarningMore().isEmpty();
        assert result.getEmployeesWithLongReportingLine().isEmpty();
    }
    @Test
    void test_employeesWithLongReportingLine() {
        // Build a long hierarchy of managers (CEO → M1 → M2 → M3 → M4 → M5 → M6 → Emp)
        for (int i = 1; i <= 8; i++) {
            employeeById.put(i, new Employee(i, "Emp" + i, "Role" + i, 10000 * i, i == 1 ? -1 : i - 1));
        }
        for (int i = 1; i < 8; i++) {
            reporteesById.put(i, List.of(employeeById.get(i + 1)));
        }
        employeeService.setCeoId(1);
        Result result = employeeService.findResults("dummy");
        assert !result.getEmployeesWithLongReportingLine().isEmpty();
        assertEquals(2, result.getEmployeesWithLongReportingLine().size());
    }

    @Test
    void test_singleEmployee() {
        employeeById.put(1, new Employee(1, "CEO", "X", 100000, -1)); //CEO
        Result result = employeeService.findResults("dummy");
        assert result.getManagersEarningLess().isEmpty();
        assert result.getManagersEarningMore().isEmpty();
        assert result.getEmployeesWithLongReportingLine().isEmpty();
    }

}