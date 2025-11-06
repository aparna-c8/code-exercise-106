import com.company.dao.Employee;
import com.company.dao.Result;
import com.company.service.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<Integer, Employee> employeeById = new HashMap<>();
        Map<Integer, List<Employee>> reporteesById = new HashMap<>();
        EmployeeService service = new EmployeeService(employeeById, reporteesById);
        Result result = service.findResults("/employee.csv");
        System.out.println(result.toString());
    }
}
