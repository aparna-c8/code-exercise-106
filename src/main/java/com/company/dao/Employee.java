package com.company.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    public Employee(int id, String firstName, String lastName, int salary, int managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

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
