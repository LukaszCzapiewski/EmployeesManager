package ŁC;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

class Employee extends Person implements Serializable, Comparable<Employee>{
    public Employee() {
        super();
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public Employee(long id, String name, String adressLine, LocalDate birthDate,String employerName, LocalDate employmentDate, BigDecimal salary, String jobtitle) {
        super(id,name,adressLine,birthDate);
        setId(id);
        setName(name);
        setAdressLine(adressLine);
        setBirthDate(birthDate);
        this.employerName = employerName;
        this.employmentDate = employmentDate;
        this.salary = salary;
        this.jobtitle = jobtitle;
        this.money = Integer.valueOf(String.valueOf(salary));
    }

    private String employerName;
    private LocalDate employmentDate;
    private BigDecimal salary;
    private String jobtitle;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    private int money;



    @Override
    public String toString() {
        return "Employee{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", adressLine='" + getAdressLine() + '\'' +
                ", birthDate=" + getBirthDate() +
                ", employerName='" + employerName + '\'' +
                ", employmentDate=" + employmentDate +
                ", salary=" + salary +
                ", jobtitle='" + jobtitle + '\'' +
                '}';
    }



    @Override
    public int hashCode() {
        return Objects.hash(employerName, employmentDate, salary, jobtitle);
    }


    @Override
    public int compareTo(Employee o) {
        return 0;
    }
}