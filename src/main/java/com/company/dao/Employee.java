package com.company.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {

    public int id;
    public String firstName;
    public String lastName;
    public int salary;
    public int managerId;

    @Override
    public String toString() {
        return "Employee -> ID: " + id + "| Name: " + firstName+" "+lastName +"| Salary: " + salary+"| Manager ID:"+managerId;
    }
}
