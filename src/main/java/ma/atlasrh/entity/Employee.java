package ma.atlasrh.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, unique = true, length = 30)
    private String employeeNumber;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String lastName;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String firstName;

    @NotBlank
    @Email
    @Size(max = 150)
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String jobTitle;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate hireDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    protected Employee() {
    }

    public Employee(String employeeNumber, String lastName, String firstName, String email,
                    String jobTitle, LocalDate hireDate, Department department, Employee manager) {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.jobTitle = jobTitle;
        this.hireDate = hireDate;
        this.department = department;
        this.manager = manager;
    }

    public Long getId() { return id; }
    public String getEmployeeNumber() { return employeeNumber; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getEmail() { return email; }
    public String getJobTitle() { return jobTitle; }
    public LocalDate getHireDate() { return hireDate; }
    public Department getDepartment() { return department; }
    public Employee getManager() { return manager; }

    public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setEmail(String email) { this.email = email; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    public void setDepartment(Department department) { this.department = department; }
    public void setManager(Employee manager) { this.manager = manager; }
}
