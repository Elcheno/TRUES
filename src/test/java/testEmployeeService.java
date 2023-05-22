import com.elcheno.trues.model.domain.Employee;
import com.elcheno.trues.model.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testEmployeeService {
    static EmployeeService employeeService;

    @BeforeAll
    public static void setUpClass(){
        employeeService = new EmployeeService();
    }

    @Test
    @DisplayName("Save Employee")
    void save() throws SQLException {
        Employee aux = new Employee();
        aux.setDni("87654321M");
        aux.setCod(4440);
        aux.setName("Felipe");
        aux.setLastName("Apellido");
        aux.setEmpLinesDTO(null);
        assertEquals(true, employeeService.save(aux));
    }

    @Test
    @DisplayName("Remove Employee")
    void remove() throws SQLException {
        List<Employee> auxList = employeeService.getAll();
        if(auxList != null){
            Employee aux = auxList.get(0);
            assertEquals(true, employeeService.remove(aux));
        }
    }

    @Test
    @DisplayName("Get Employee By cod")
    void getByCod() throws SQLException {
        List<Employee> auxList = employeeService.getAll();
        if(auxList != null){
            Employee aux = auxList.get(0);
            assertEquals(aux, employeeService.getByCod(aux.getCod()));
        }
    }

    @Test
    @DisplayName("Get Employee By id")
    void getById() throws SQLException {
        List<Employee> auxList = employeeService.getAll();
        if(auxList != null){
            Employee aux = auxList.get(0);
            assertEquals(aux, employeeService.getById(aux.getId()));
        }
    }
}
