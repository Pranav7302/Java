public class PartTimeEmployee extends Employee {
    String shiftType;

    @Override
    public String toString() {
        return "PartTimeEmployee{" +
                "shiftType='" + shiftType + '\'' +
                ", empName='" + empName + '\'' +
                ", salary=" + salary +
                '}';
    }

    public PartTimeEmployee(String empName, int salary, String shiftTiming) {
        super( empName, salary);
        this.shiftType = shiftTiming;
    }
}
