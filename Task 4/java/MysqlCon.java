import org.apache.log4j.Logger;
import org.json.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.function.Supplier;

class MysqlCon{
    static Connection con;

    static {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pranav", "root", "pranavcs02");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    static Statement statement;

    static {
        try {
            statement = MysqlCon.con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    static Logger log = Logger.getLogger(MysqlCon.class.getName());
    public static void jason(String name)
    {
        try {
            String str ="Select * from "+name;
            PreparedStatement pstmt=con.prepareStatement(str);
            ResultSet rs = pstmt.executeQuery();
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            while(rs.next()) {
                JSONObject record = new JSONObject();
                record.put("EmployeeID", rs.getInt("empId"));
                record.put("EmployeeName", rs.getString("empName"));
                record.put("EmployeeSalary", rs.getInt("salary"));
                if(name.equals("permanentemployee")) {
                    record.put("Joining Date", rs.getString("joiningDate"));
                    array.put(record);
                    jsonObject.put("Permanent employee", array);
                } else if (name.equals("parttimeemployee")) {
                    record.put("Shift Type", rs.getString("shifttype"));
                    array.put(record);
                    jsonObject.put("Part time employee", array);
                } else if (name.equals("contractemployee")) {
                    record.put("Contract period", rs.getString("period"));
                    array.put(record);
                    jsonObject.put("Contract employee", array);
                }
            }
            log.info(jsonObject.toString());
        } catch (SQLException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkAvailable(String table,int id) throws SQLException {
        boolean s = false;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = con.prepareStatement("SELECT empID FROM "+table+" WHERE EXISTS (SELECT empID FROM "+table+" WHERE empID = ?)");
        preparedStatement.setInt(1,id);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            s = resultSet.getBoolean(1);
        }
        return s;
    }
    public static boolean checkIfDateIsValid(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try {
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    public static void main(String args[]) {
        try {
            Scanner scanner = new Scanner(System.in);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Statement stmt = con.createStatement();
            int a, empChoice = 1;
            int choice = 1;
            int dateOk = 0;
            int dateOk1 = 0;
            int id;
            boolean avail = false;
            String date, joiningDate2;
            do {
                log.info("Enter your choice\n1.Create Employee\n2.Modify Employee\n3.Delete Employee\n4.List Employees\n5.Employee search\n6.Exit");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1: {
                        log.info("Enter Employee Id ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        log.info("Enter the Employee name");
                        String empName = scanner.nextLine();
                        log.info("Enter the salary");
                        int salary = scanner.nextInt();
                        log.info("Enter your choice\n1.Permanent Employee\n2.Part-Time Employee\n3.Contract Employee");
                        empChoice = scanner.nextInt();
                        switch (empChoice) {
                            case 1:
                                do {
                                    log.info("Enter the joining date in the format 'YYYY-MM-DD': ");
                                    date = scanner.next();
                                    if (checkIfDateIsValid(date)) {
                                        dateOk = 1;
                                    } else {
                                        log.info("Date format you have entered is wrong");
                                    }
                                } while (dateOk != 1);
                                dateOk = 0;
                                PermanentEmployee permanentEmployee = new PermanentEmployee(id,empName, salary, date);
                                PermanentEmployee.insertIntoPermanentTable(permanentEmployee);
                                break;
                            case 2:
                                log.info("Enter the shift type");
                                String shiftType = scanner.next();
                                PartTimeEmployee partTimeEmployee = new PartTimeEmployee(id,empName, salary, shiftType);
                                PartTimeEmployee.insertIntoParttimeTable(partTimeEmployee);
                                break;
                            case 3:
                                scanner.nextLine();
                                log.info("Enter the contract period");
                                String period = scanner.nextLine();
                                ContractEmployee contractEmployee = new ContractEmployee(id,empName, salary, period);
                                ContractEmployee.insertIntoContractTable(contractEmployee);
                                break;
                            default:
                                log.info("Enter the correct choice");
                        }
                        log.info("Employee created successfully");
                    }
                    break;
                    case 2: {
                        log.info("Select the type of emplolyee\nEnter your choice\n1.Permanent Employee\n2.Part-Time Employee \n3.Contract Employee");
                        int choice2 = scanner.nextInt();
                        log.info("Enter the employee id you want to modify");
                        int modifyId = scanner.nextInt();
                        switch (choice2) {
                            case 1:
                                avail = checkAvailable("permanentemployee",modifyId);
                                if(avail)
                                    {
                                        log.info("Select what data you want to modify\n1.Employee name\n2.Salary\n3.Joining date");
                                        int choice3 = scanner.nextInt();
                                        scanner.nextLine();
                                        switch (choice3) {
                                            case 1:
                                                log.info("Enter the name");
                                                String name = scanner.next();
                                                PermanentEmployee.updatePermanent("empName",name,modifyId);
                                                break;
                                            case 2:
                                                log.info("Enter the salary");
                                                int salary2 = scanner.nextInt();
                                                PermanentEmployee.updatePermanent("salary", String.valueOf(salary2),modifyId);
                                                break;
                                            case 3:
                                                do {
                                                    log.info("Enter the joining date in the format 'YYYY-MM-DD': ");
                                                    joiningDate2 = scanner.next();
                                                    if (checkIfDateIsValid(joiningDate2)) {
                                                        dateOk1 = 1;
                                                    } else {
                                                        log.info("Date format you have entered is wrong");
                                                    }
                                                } while (dateOk1 != 1);
                                                dateOk1 = 0;
                                                PermanentEmployee.updatePermanent("joiningDate",joiningDate2,modifyId);
                                                break;
                                            default:
                                                log.warn("Enter the right choice");
                                        }
                                        log.info("Modification done successfully");
                                }
                                else {
                                    log.warn("Id not found in permanent employee list");
                                }
                            break;
                            case 2: {
                                boolean s1 = checkAvailable("parttimeemployee",modifyId);
                                if(s1)
                                {
                                log.info("Select what data you want to modify\n1.Employee name\n2.Salary\n3.Shift type");
                                int choice4 = scanner.nextInt();
                                scanner.nextLine();
                                switch (choice4) {
                                    case 1:
                                        log.info("Enter the name");
                                        String name = scanner.next();
                                        PartTimeEmployee.updateParttime("empName", name, modifyId);
                                        break;
                                    case 2:
                                        log.info("Enter the salary");
                                        int salary2 = scanner.nextInt();
                                        PartTimeEmployee.updateParttime("salary", String.valueOf(salary2),modifyId);
                                        break;
                                    case 3:
                                        log.info("Enter the Shift type");
                                        String shiftType2 = scanner.next();
                                        PartTimeEmployee.updateParttime("shifttype",shiftType2,modifyId);
                                        break;
                                    default:
                                   //     log.warn("Enter the right choice");
                                }
                                log.info("Modification done successfully");
                                }
                                else
                                {
                                       log.warn("Employee Id not found in Part time employee list");
                                }
                            }
                            break;
                            case 3: {
                                boolean s2 = checkAvailable("contractemployee",modifyId);
                                    if(s2)
                                    {
                                log.info("Select what data you want to modify\n1.Employee name\n2.Salary\n3.Contract period");
                                int choice5 = scanner.nextInt();
                                scanner.nextLine();
                                switch (choice5) {
                                    case 1:
                                        log.info("Enter the name");
                                        String name = scanner.next();
                                        ContractEmployee.updateContract("empName",name,modifyId);
                                        break;
                                    case 2:
                                        log.info("Enter the salary");
                                        int salary2 = scanner.nextInt();
                                        ContractEmployee.updateContract("salary", String.valueOf(salary2),modifyId);
                                        break;
                                    case 3:
                                        log.info("Enter the contract period");
                                        String contractPeriod2 = scanner.next();
                                        ContractEmployee.updateContract("period",contractPeriod2,modifyId);
                                        break;
                                    default:
                                        log.warn("Enter the right choice");
                                }
                                log.info("Modification done successfully");
                                    }
                                    else {
                                        log.warn("Employee ID not found in Contract employee list");
                                    }
                            }
                        }
                    }
                    break;
                    case 3: {
                        log.info("Select the type of emplolyee\nEnter your choice\n1.Permanent Employee\n2.Part-Time Employee \n3.Contract Employee");
                        int choice2 = scanner.nextInt();
                        log.info("Enter the Employee Id");
                        int empDel = scanner.nextInt();
                        switch (choice2) {
                            case 1: {
                                if(checkAvailable("permanentemployee",empDel)) {
                                    PermanentEmployee.deletePermanent(empDel);
                                    log.info("Employee deleted successfully");
                                }
                                else {
                                    log.warn("Employee ID not found in permanent employee list");
                                }
                            }
                            break;
                            case 2: {
                                if(checkAvailable("parttimeemployee",empDel)) {
                                    PartTimeEmployee.deleteParttime(empDel);
                                    log.info("Employee deleted successfully");
                                }
                                else{
                                   log.warn("Employee ID not found in part time employee list");
                                }
                            }
                            break;
                            case 3: {
                                if(checkAvailable("contractemployee",empDel)) {
                                   ContractEmployee.deleteContract(empDel);
                                   log.info("Employee deleted successfully");
                                }
                                else {
                                   log.warn("Employee ID not found in contract employee list");
                                }
                            }
                            default:
                                log.warn("Enter the right choice");
                        }
                    }
                    break;
                    case 4: {
                        log.info("Enter your choice\n1.List all employees\n2.List permanent employees\n3.List Part-time employees\n4.List contract employees");
                        int choice2 = scanner.nextInt();
                        switch (choice2) {
                            case 1: {
                                jason("permanentemployee");
                                jason("parttimeemployee");
                                jason("contractemployee");
                            }
                            break;
                            case 2: {
                                jason("permanentemployee");
                            }
                            break;
                            case 3: {
                                jason("parttimeemployee");
                            }
                            break;
                            case 4: {
                                jason("contractemployee");
                            }
                            break;
                            default:
                                log.warn("Enter the right choice");
                        }
                    }
                    break;
                    case 5: {
                        log.info("Select the type of emplolyee\nEnter your choice\n1.Permanent Employee\n2.Part-Time Employee \n3.Contract Employee");
                        int choice2 = scanner.nextInt();
                        log.info("Enter the Employee id to search");
                        int empSearch = scanner.nextInt();
                        switch (choice2) {
                            case 1: {
                                if(checkAvailable("permanentemployee",empSearch)) {
                                    PermanentEmployee.searchPermanent(empSearch);
                                }
                                else {
                                    log.warn("Employee ID not found in permanent employee list");
                                }
                            }
                            break;
                            case 2: {
                                if(checkAvailable("parttimeemployee",empSearch)) {
                                   PartTimeEmployee.searchParttime(empSearch);
                                }
                                else {
                                    log.warn("Employee ID not found parttime employee list");
                                }
                            }
                            break;
                            case 3: {
                                if(checkAvailable("contractemployee",empSearch)) {
                                    ContractEmployee.searchContract(empSearch);
                                }
                                else {
                                    log.warn("Employee ID not found in contract employee list");
                                }
                            }
                            break;
                            default:
                                log.warn("Enter the right choice");
                        }
                    }
                    break;
                    case 6: {
                           choice =7;
                    }
                    break;
                    default:
                        log.warn("Enter the right choice");
                }
            } while (choice != 7);
            con.close();
        } catch (Exception e) {
            log.info((Supplier<String>) e);
        }
    }
}