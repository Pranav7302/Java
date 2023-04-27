public class ContractEmployee extends Employee{
    String contractPeriod;

    @Override
    public String toString() {
        return "ContractEmployee{" +
                "contractPeriod='" + contractPeriod + '\'' +
                ", empName='" + empName + '\'' +
                ", salary=" + salary +
                '}';
    }

    public ContractEmployee(String empName, int salary, String contractPeriod) {
        super(empName, salary);
        this.contractPeriod = contractPeriod;
    }
}
