package ma.atlasrh.repository;

import ma.atlasrh.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmployeeNumber(String employeeNumber);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByDepartmentId(Long departmentId);
    boolean existsByManagerId(Long managerId);
}
