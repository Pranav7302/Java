import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartTimeEmployee extends Employee {
    String shiftType;

    public PartTimeEmployee(int id,String empName, int salary, String shiftTiming) {
        super(id, empName, salary);
        this.shiftType = shiftTiming;
    }
    public static void insertIntoParttimeTable(PartTimeEmployee partTimeEmployee) throws SQLException {
        int a = partTimeEmployee.id;
        String empName = partTimeEmployee.empName;
        int salary = partTimeEmployee.salary;
        String shift = partTimeEmployee.shiftType;
        String query21 = "insert into parttimeemployee (empID,empName,salary,shifttype)" + "values(?,?,?,?)";
        PreparedStatement preparedStatement = MysqlCon.con.prepareStatement(query21);
        preparedStatement.setInt(1, a);
        preparedStatement.setString(2, empName);
        preparedStatement.setInt(3, salary);
        preparedStatement.setString(4, shift);
        preparedStatement.execute();

    }
    public static void updateParttime(String column,String value,int modifyId) throws SQLException {
        String query = "update parttimeemployee set "+column+" = ? where empID= ? ";
        PreparedStatement preparedStmt = MysqlCon.con.prepareStatement(query);
        preparedStmt.setString(1,value);
        preparedStmt.setInt(2, modifyId);
        preparedStmt.execute();
    }
    public static void deleteParttime(int empDel) throws SQLException {
        String query = "delete from parttimeemployee where empID= ? ";
        PreparedStatement preparedStmt = MysqlCon.con.prepareStatement(query);
        preparedStmt.setInt(1, empDel);
        preparedStmt.execute();
    }
    public static void searchParttime(int empSearch) throws SQLException, JSONException {
        ResultSet rs = MysqlCon.statement.executeQuery("select * from parttimeemployee where empID=" + empSearch);
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        while (rs.next()) {
            JSONObject record = new JSONObject();
            record.put("EmployeeID", rs.getInt("empId"));
            record.put("EmployeeName", rs.getString("empName"));
            record.put("EmployeeSalary", rs.getInt("salary"));
            record.put("Joining Date", rs.getString("joiningDate"));
            array.put(record);
            jsonObject.put("Permanent employee", array);
            MysqlCon.log.info(jsonObject.toString());
        }
    }
}
