import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PermanentEmployee extends Employee {
    String joiningDate;

    public PermanentEmployee(int id, String empName, int salary, String joiningDate) {
        super(id, empName, salary);
        this.joiningDate = joiningDate;
    }

    public static void insertIntoPermanentTable(PermanentEmployee permanentEmployee) throws SQLException {
        int a = permanentEmployee.id;
        String empName = permanentEmployee.empName;
        int salary = permanentEmployee.salary;
        String date = permanentEmployee.joiningDate;
        String query21 = "insert into permanentemployee (empID,empName,salary,joiningDate)" + "values(?,?,?,?)";
        PreparedStatement preparedStatement = MysqlCon.con.prepareStatement(query21);
        preparedStatement.setInt(1, a);
        preparedStatement.setString(2, empName);
        preparedStatement.setInt(3, salary);
        preparedStatement.setString(4, date);
        preparedStatement.execute();

    }

    public static void updatePermanent(String column, String value, int modifyId) throws SQLException {
        String query = "update permanentemployee set " + column + " = ? where empID= ? ";
        PreparedStatement preparedStmt = MysqlCon.con.prepareStatement(query);
        preparedStmt.setString(1, value);
        preparedStmt.setInt(2, modifyId);
        preparedStmt.execute();
    }

    public static void deletePermanent(int empDel) throws SQLException {
        String query = "delete from permanentemployee where empID= ? ";
        PreparedStatement preparedStmt = MysqlCon.con.prepareStatement(query);
        preparedStmt.setInt(1, empDel);
        preparedStmt.execute();
    }

    public static void searchPermanent(int empSearch) throws SQLException {
        ResultSet rs = MysqlCon.statement.executeQuery("select * from permanentemployee where empID=" + empSearch);
        while (rs.next()) {
            MysqlCon.log.info("Employee ID: " + rs.getInt(1)+"  " + "Employee name: " + rs.getString(2)+"  " + "Employee salary: " + rs.getString(3)+"  " + "Joining Date:  " + rs.getString(4));
        }
    }
}
