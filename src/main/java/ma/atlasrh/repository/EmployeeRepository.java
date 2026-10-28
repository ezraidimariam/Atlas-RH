package ma.atlasrh.repository;

import ma.atlasrh.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmployeeNumber(String employeeNumber);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByDepartmentId(Long departmentId);
    boolean existsByManagerId(Long managerId);

    @Query("select e from Employee e where (:query is null or lower(e.firstName) like lower(concat('%', :query, '%')) or lower(e.lastName) like lower(concat('%', :query, '%')) or lower(e.email) like lower(concat('%', :query, '%')) or lower(e.employeeNumber) like lower(concat('%', :query, '%'))) and (:departmentId is null or e.department.id = :departmentId)")
    Page<Employee> search(@Param("query") String query, @Param("departmentId") Long departmentId, Pageable pageable);
}
