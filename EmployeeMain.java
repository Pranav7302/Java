import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Scanner;

public class EmployeeMain {
    private static boolean checkIfDateIsValid(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try {
            format.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    public static void main(String[] args) {
        int key = 1, a, empChoice = 1;
        int choice = 1;
        int dateOk=0;
        int dateOk1=0;
        String date,joiningDate2;
        Scanner scanner = new Scanner(System.in);
        Hashtable<Integer, PermanentEmployee> permanentEmployeeHashtable = new Hashtable<>();
        Hashtable<Integer, PartTimeEmployee> partTimeEmployeeHashtable = new Hashtable<>();
        Hashtable<Integer, ContractEmployee> contractEmployeeHashtable = new Hashtable<>();
        do {
            System.out.print("Enter your choice\n1.Create Employee\n2.Modify Employee\n3.Delete Employee\n4.List Employees\n5.Employee search\n6.Exit");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                {
                    a = key++;
                    System.out.println("Employee Id is: " + a +"\nEnter the Employee name");
                    String empName = scanner.nextLine();
                    System.out.println("Enter the salary");
                    int salary = scanner.nextInt();
                    System.out.println("Enter your choice\n1.Permanent Employee\n2.Part-Time Employee\n3.Contract Employee");
                    empChoice = scanner.nextInt();
                    switch (empChoice) {
                        case 1:
                            do {
                                System.out.println("Enter the joining date in the format 'YYYY-MM-DD': ");
                                date = scanner.next();
                                if(checkIfDateIsValid(date))
                                {
                                    dateOk =1;
                                }
                                else {
                                    System.out.println("Date format you have entered is wrong");
                                }
                            }while(dateOk!=1);
                            dateOk=0;
                            PermanentEmployee permanentEmployee = new PermanentEmployee(empName, salary, date);
                            permanentEmployeeHashtable.put(a, permanentEmployee);
                            break;
                        case 2:
                            System.out.println("Enter the shift type");
                            String shiftType = scanner.next();
                            PartTimeEmployee partTimeEmployee = new PartTimeEmployee(empName, salary, shiftType);
                            partTimeEmployeeHashtable.put(a, partTimeEmployee);
                            break;
                        case 3:
                            scanner.nextLine();
                            System.out.println("Enter the contract period");
                            String period = scanner.nextLine();
                            ContractEmployee contractEmployee = new ContractEmployee(empName, salary, period);
                            contractEmployeeHashtable.put(a, contractEmployee);
                            break;
                        default:
                            System.out.println("Enter the correct choice");
                    }
                    System.out.println("Employee created successfully");
                }
                break;
                case 2:
                {
                    System.out.println("Select the type of emplolyee\nEnter your choice\n1.Permanent Employee\n2.Part-Time Employee \n3.Contract Employee");
                    int choice2 = scanner.nextInt();
                    System.out.println("Enter the employee id you want to modify");
                    int modifyId = scanner.nextInt();
                    if(partTimeEmployeeHashtable.containsKey(modifyId) || permanentEmployeeHashtable.containsKey(modifyId) || contractEmployeeHashtable.containsKey(modifyId)) {
                        switch (choice2) {
                            case 1:
                                if(permanentEmployeeHashtable.containsKey(modifyId))
                                {
                                System.out.println("Select what data you want to modify\n1.Employee name\n2.Salary\n3.Joining date");
                                int choice3 = scanner.nextInt();
                                scanner.nextLine();
                                switch (choice3) {
                                    case 1:
                                        System.out.println("Enter the name");
                                        String name = scanner.next();
                                        int salary1 = permanentEmployeeHashtable.get(modifyId).salary;
                                        String joiningDate = permanentEmployeeHashtable.get(modifyId).joiningDate;
                                        PermanentEmployee employee1 = new PermanentEmployee(name, salary1, joiningDate);
                                        permanentEmployeeHashtable.put(modifyId, employee1);
                                        break;
                                    case 2:
                                        System.out.println("Enter the salary");
                                        int salary2 = scanner.nextInt();
                                        String name1 = permanentEmployeeHashtable.get(modifyId).empName;
                                        String joiningDate1 = permanentEmployeeHashtable.get(modifyId).joiningDate;
                                        PermanentEmployee employee2 = new PermanentEmployee(name1, salary2, joiningDate1);
                                        permanentEmployeeHashtable.put(modifyId, employee2);
                                        break;
                                    case 3:
                                        do {
                                            System.out.println("Enter the joining date in the format 'YYYY-MM-DD': ");
                                            joiningDate2= scanner.next();
                                            if(checkIfDateIsValid(joiningDate2))
                                            {
                                                dateOk1 =1;
                                            }
                                            else {
                                                System.out.println("Date format you have entered is wrong");
                                            }
                                        }while(dateOk1!=1);
                                        dateOk1=0;
                                        int salary3 = permanentEmployeeHashtable.get(modifyId).salary;
                                        String name2 = permanentEmployeeHashtable.get(modifyId).empName;
                                        PermanentEmployee employee3 = new PermanentEmployee(name2, salary3, joiningDate2);
                                        permanentEmployeeHashtable.put(modifyId, employee3);
                                        break;
                                }
                                    System.out.println("Modification done successfully");
                                }
                                else
                                {
                                    System.out.println("Id not found in permanent employee list");
                                }
                                break;
                            case 2: {
                                if(partTimeEmployeeHashtable.containsKey(modifyId)) {
                                    System.out.println("Select what data you want to modify\n1.Employee name\n2.Salary\n3.Shift type");
                                    int choice4 = scanner.nextInt();
                                    scanner.nextLine();
                                    switch (choice4) {
                                        case 1:
                                            System.out.println("Enter the name");
                                            String name = scanner.next();
                                            int salary1 = partTimeEmployeeHashtable.get(modifyId).salary;
                                            String shiftType = partTimeEmployeeHashtable.get(modifyId).shiftType;
                                            PartTimeEmployee employee1 = new PartTimeEmployee(name, salary1, shiftType);
                                            partTimeEmployeeHashtable.put(modifyId, employee1);
                                            break;
                                        case 2:
                                            System.out.println("Enter the salary");
                                            int salary2 = scanner.nextInt();
                                            String name1 = partTimeEmployeeHashtable.get(modifyId).empName;
                                            String shiftType1 = partTimeEmployeeHashtable.get(modifyId).shiftType;
                                            PartTimeEmployee employee2 = new PartTimeEmployee(name1, salary2, shiftType1);
                                            partTimeEmployeeHashtable.put(modifyId, employee2);
                                            break;
                                        case 3:
                                            System.out.println("Enter the Shift type");
                                            String shiftType2 = scanner.next();
                                            int salary3 = partTimeEmployeeHashtable.get(modifyId).salary;
                                            String name2 = partTimeEmployeeHashtable.get(modifyId).empName;
                                            PartTimeEmployee employee3 = new PartTimeEmployee(name2, salary3, shiftType2);
                                            partTimeEmployeeHashtable.put(modifyId, employee3);
                                            break;
                                    }
                                    System.out.println("Modification done successfully");
                                }
                                else
                                {
                                    System.out.println("Employee Id not found in Part time employee list");
                                }
                            }
                            break;
                            case 3: {
                                if(partTimeEmployeeHashtable.containsKey(modifyId)) {
                                    System.out.println("Select what data you want to modify\n1.Employee name\n2.Salary\n3.Contract period");
                                    int choice5 = scanner.nextInt();
                                    scanner.nextLine();
                                    switch (choice5) {
                                        case 1:
                                            System.out.println("Enter the name");
                                            String name = scanner.next();
                                            int salary1 = contractEmployeeHashtable.get(modifyId).salary;
                                            String contractPeriod = contractEmployeeHashtable.get(modifyId).contractPeriod;
                                            ContractEmployee employee1 = new ContractEmployee(name, salary1, contractPeriod);
                                            contractEmployeeHashtable.put(modifyId, employee1);
                                            break;
                                        case 2:
                                            System.out.println("Enter the salary");
                                            int salary2 = scanner.nextInt();
                                            String name1 = contractEmployeeHashtable.get(modifyId).empName;
                                            String contractPeriod1 = contractEmployeeHashtable.get(modifyId).contractPeriod;
                                            ContractEmployee employee2 = new ContractEmployee(name1, salary2, contractPeriod1);
                                            contractEmployeeHashtable.put(modifyId, employee2);
                                            break;
                                        case 3:
                                            System.out.println("Enter the Shift type");
                                            String contractPeriod2 = scanner.next();
                                            int salary3 = contractEmployeeHashtable.get(modifyId).salary;
                                            String name2 = contractEmployeeHashtable.get(modifyId).empName;
                                            ContractEmployee employee3 = new ContractEmployee(name2, salary3, contractPeriod2);
                                            contractEmployeeHashtable.put(modifyId, employee3);
                                            break;
                                    }
                                    System.out.println("Modification done successfully");
                                }
                                else {
                                    System.out.println("Employee ID not found in Contract employee list");
                                }
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Employee ID not found");
                    }
                }
                break;
                case 3:
                {
                    System.out.println("Enter the Employee Id");
                    int empDel = scanner.nextInt();
                    if(permanentEmployeeHashtable.containsKey(empDel) || partTimeEmployeeHashtable.containsKey(empDel) || contractEmployeeHashtable.containsKey(empDel))
                    {
                        permanentEmployeeHashtable.remove(empDel);
                        partTimeEmployeeHashtable.remove(empDel);
                        contractEmployeeHashtable.remove(empDel);
                        System.out.println("Deletion successful");
                    }
                    else {
                        System.out.println("Employee ID not found");
                    }
                }
                break;
                case 4:
                {
                    System.out.println("Enter your choice\n1.List all employees\n2.List permanent employees\n3.List Part-time employees\n4.List contract employees");
                    int choice2 = scanner.nextInt();
                    switch(choice2)
                    {
                        case 1: {
                            if (permanentEmployeeHashtable.isEmpty() && partTimeEmployeeHashtable.isEmpty() && contractEmployeeHashtable.isEmpty()) {
                                System.out.println("No employees found");
                            }
                            if (!permanentEmployeeHashtable.isEmpty()) {
                                System.out.println(permanentEmployeeHashtable);
                            }
                            if (!partTimeEmployeeHashtable.isEmpty()) {
                                System.out.println(partTimeEmployeeHashtable);
                            }
                            if (!contractEmployeeHashtable.isEmpty()) {
                                System.out.println(contractEmployeeHashtable);
                            }
                        }
                        break;
                        case 2: {
                            if (!permanentEmployeeHashtable.isEmpty()) {
                                System.out.println(permanentEmployeeHashtable);
                            }
                            else {
                                System.out.println("No permanent employees");
                            }
                        }
                        break;
                        case 3:
                        {
                            if (!partTimeEmployeeHashtable.isEmpty()) {
                                System.out.println(partTimeEmployeeHashtable);
                            }
                            else {
                                System.out.println("No Part time employees");
                            }
                        }
                        break;
                        case 4: {
                            if (!contractEmployeeHashtable.isEmpty()) {
                                System.out.println(contractEmployeeHashtable);
                            } else {
                                System.out.println("No contract employees");
                            }
                        }
                        break;
                    }
                }
                break;
                case 5:
                {
                    System.out.println("Enter the Employee id to search");
                    int empSearch = scanner.nextInt();
                    if(permanentEmployeeHashtable.containsKey(empSearch))
                    {
                        System.out.println(permanentEmployeeHashtable.get(empSearch));
                    }
                    else if(partTimeEmployeeHashtable.containsKey(empSearch))
                    {
                        System.out.println(partTimeEmployeeHashtable.get(empSearch));
                    }
                    else if (contractEmployeeHashtable.containsKey(empSearch))
                    {
                        System.out.println(contractEmployeeHashtable.get(empSearch));
                    }
                    else
                    {
                        System.out.println("No employee found");
                    }
                }
                break;
                case 6: {
                    choice = 7;
                }
                    break;
            }
        }while (choice != 7) ;
    }
}
