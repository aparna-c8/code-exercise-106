package com.company.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Result {

    List<Employee> managersEarningLess;
    List<Employee> managersEarningMore;
    List<Employee> employeesWithLongReportingLine;

    @Override
    public String toString() {
        return "\nManagers earning less than their Subordinates: \n"
                + managersEarningLess + "\nManagers earning more than their Subordinates: \n"
                + managersEarningMore + "\nEmployees having long reporting line: \n" + employeesWithLongReportingLine;
    }
}
