package com.company.service;

import com.company.dao.Employee;
import com.company.dao.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final Map<Integer, Employee> employeeById;
    private final Map<Integer, List<Employee>> reporteesById;
    private int ceoId;

    public EmployeeService(Map<Integer, Employee> employeeById, Map<Integer, List<Employee>> reporteesById) {
        this.employeeById = employeeById;
        this.reporteesById = reporteesById;
    }

    /**
     * This method reads the csv file from resources and
     * populates employeeById and reporteesById maps for later use.
     * @param path The path to the csv file
     * @throws IOException
     */
    private void readCsvFile(String path){
        try (InputStream is = EmployeeService.class.getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){

            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(fields[0]));
                employee.setFirstName(fields[1]);
                employee.setLastName(fields[2]);
                employee.setSalary(Integer.parseInt(fields[3]));
                if (fields.length < 5 || fields[4].isBlank()) {
                    ceoId = employee.getId();
                } else {
                    employee.setManagerId(Integer.parseInt(fields[4]));
                    List<Employee> liSub = reporteesById.getOrDefault(employee.getManagerId(), new ArrayList<>());
                    liSub.add(employee);
                    reporteesById.put(employee.getManagerId(), liSub);
                }
                employeeById.put(employee.getId(), employee);
            }
        } catch (IOException | NullPointerException e) {
            log.error("log_context=EmployeeService.class | method = readCsvFile | message=Exception occurred: {}", e.getMessage());
        }
    }

    /**
     * This method is responsible for finding all three required results.
     * @param path The path to the csv file
     * @return
     */
    public Result findResults(String path) {
        Result result = new Result();

        List<Employee> managersEarningLess = new ArrayList<>();
        List<Employee> managersEarningMore = new ArrayList<>();
        List<Employee> longManagerList = new ArrayList<>();

        readCsvFile(path);

        for (int managerId : reporteesById.keySet()) {
            int sum = 0;
            for (Employee emp : reporteesById.get(managerId)) {
                sum += emp.getSalary();
            }
            if (1.2 * sum / reporteesById.get(managerId).size() > employeeById.get(managerId).getSalary()) {
                managersEarningLess.add(employeeById.get(managerId));
            } else if (1.5 * sum / reporteesById.get(managerId).size() < employeeById.get(managerId).getSalary()) {
                managersEarningMore.add(employeeById.get(managerId));
            }
        }

        Map<Integer, Integer> depthMap = new HashMap<>();
        AtomicInteger counter=new AtomicInteger();
        rec(ceoId, depthMap, counter, longManagerList);
        result.setManagersEarningLess(managersEarningLess);
        result.setManagersEarningMore(managersEarningMore);
        result.setEmployeesWithLongReportingLine(longManagerList);
        return result;
    }

    /**
     * This method takes care of dfs traversal to find long reporting line.
     * Starts with the CEO and traverse down the hierarchy.
     * @param id Employee ID
     * @param depthMap To keep track of each employee's number of managers between them and the CEO. It is not necessary to have it.
     * @param counter To count the levels traversed
     * @param longManagerList Employee's with long reporting line will be added to this list
     */
    public void rec(int id, Map<Integer, Integer> depthMap, AtomicInteger counter, List<Employee> longManagerList) {
        if (reporteesById.get(id) != null){
            for (Employee emp : reporteesById.get(id)) {
                counter.incrementAndGet();
                rec(emp.getId(), depthMap, counter, longManagerList);
            }
        }
        depthMap.put(id, counter.get());
        if (counter.get()>5) {
            longManagerList.add(employeeById.get(id));
        }
        counter.decrementAndGet();
    }
}
