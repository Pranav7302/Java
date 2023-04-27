import java.util.Date;

public class PermanentEmployee extends Employee{
    String joiningDate;

    @Override
    public String toString() {
        return "PermanentEmployee{" +
                "joiningDate='" + joiningDate + '\'' +
                ", empName='" + empName + '\'' +
                ", salary=" + salary +
                '}';
    }

    public PermanentEmployee(String empName, int salary, String joiningDate) {
        super( empName, salary);
        this.joiningDate = joiningDate;
    }
}
