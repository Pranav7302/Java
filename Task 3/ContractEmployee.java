import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContractEmployee extends Employee{
    String contractPeriod;

    public ContractEmployee(int id, String empName, int salary, String contractPeriod) {
        super(id,empName, salary);
        this.contractPeriod = contractPeriod;
    }
    public static void insertIntoContractTable(ContractEmployee contractEmployee) throws SQLException {
        int a = contractEmployee.id;
        String empName = contractEmployee.empName;
        int salary = contractEmployee.salary;
        String period = contractEmployee.contractPeriod;
        String query21 = "insert into contractemployee (empID,empName,salary,period)" + "values(?,?,?,?)";
        PreparedStatement preparedStatement = MysqlCon.con.prepareStatement(query21);
        preparedStatement.setInt(1, a);
        preparedStatement.setString(2, empName);
        preparedStatement.setInt(3, salary);
        preparedStatement.setString(4, period);
        preparedStatement.execute();

    }
    public static void updateContract(String column, String value,int modifyId) throws SQLException {
        String query = "update contractemployee set "+column+" = ? where empID= ? ";
        PreparedStatement preparedStmt = MysqlCon.con.prepareStatement(query);
        preparedStmt.setString(1,value);
        preparedStmt.setInt(2, modifyId);
        preparedStmt.execute();
    }
    public static void deleteContract(int empDel) throws SQLException {
        String query = "delete from contractemployee where empID= ? ";
        PreparedStatement preparedStmt = MysqlCon.con.prepareStatement(query);
        preparedStmt.setInt(1, empDel);
        preparedStmt.execute();
    }
    public static void searchContract(int empSearch) throws SQLException {
        ResultSet rs = MysqlCon.statement.executeQuery("select * from contractemployee where empID=" + empSearch);
        while (rs.next()) {
            MysqlCon.log.info("Employee ID: " + rs.getInt(1)+"  " + "Employee name: " + rs.getString(2)+"  " + "Employee salary: " + rs.getString(3)+"  " + "Contract period:  " + rs.getString(4));
        }
    }
}
