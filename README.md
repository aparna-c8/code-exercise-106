# **Code exercise 106**

##### BIG COMPANY is employing a lot of employees. Company would like to analyze its organizationalstructure and identify potential improvements. Board wants to make sure that every manager earns at least 20% more than the average salary of its direct subordinates, but no more than 50% more than that average. Company wants to avoid too long reporting lines, therefore we would like to identify all employees which have more than 4 managers between them and the CEO.

###### You are given a CSV file which contains information about all the employees. File structure looks like this:
Id,firstName,lastName,salary,managerId

Each line represents an employee (CEO included). CEO has no manager specified. Number of rows can be up to 1000.
Write a simple program which will read the file and report:
- which managers earn less than they should, and by how much
- which managers earn more than they should, and by how much
- which employees have a reporting line which is too long, and by how much
  ###### Key points:
* use only Java SE (any version), and Junit (any version) for tests.
* use maven for project structure and build
* your application should read data from a file and print out output to console. No GUIs needed.
* code will be assessed on correctness, simplicity, readability and cleanliness
* If you have any doubts make a sensible assumption and document it

### Assumptions
1. The csv file data contains values for all the fields except for manager id in case the employee is CEO.
2. There are no more than one entry which doesn't have managerId.
3. There are no circular dependency between reporting managers.