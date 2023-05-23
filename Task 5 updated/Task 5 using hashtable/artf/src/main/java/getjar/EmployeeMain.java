package getjar;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.google.gson.Gson;

import task4.sqljar.EmployeeTable;
@WebServlet( urlPatterns ="/hello")
public class EmployeeMain extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();
    boolean avail;
    Connection con;
    ResultSet count;
    int emp_count;
    static Logger log = Logger.getLogger(EmployeeMain.class.getName());
    protected void doGet(HttpServletRequest request, 
                                   HttpServletResponse response) throws ServletException, IOException {
    	 Hashtable<Integer,Employee> employeeTable = new Hashtable<>();
         Employee e1=new Employee(1,"pranav",21,32000);
         Employee e2=new Employee(2,"rosen",21,32000);
         Employee e3=new Employee(3,"jeeva",23,37000);
        
         employeeTable.put(1,e1);
         employeeTable.put(2,e2);
         employeeTable.put(3,e3);
        String code = request.getParameter("employeecode").trim();
        Integer n=Integer.parseInt(code);
		Employee emp = (Employee) EmployeeTable.searchInHashTable(employeeTable, n);
        if(emp!=null)
           {        
        	try {
//				ResultSet resultset = EmployeeTable.searchInTable(n);    		    Employee e1 = new Employee();
				
		             e1.setId(emp.id);   
		             e1.setName(emp.name);
		             e1.setAge(emp.age);
		             e1.setSalary(emp.salary);   
		             String userJsonString = this.gson.toJson(e1);   
				     PrintWriter out = response.getWriter();
				     response.setContentType("application/json");
				     response.setCharacterEncoding("UTF-8");
				     out.print(userJsonString);
				     out.flush();
                     log.info("Employee ID is present ");
        	
        	} catch (JSONException e) {
				e.printStackTrace();
			}
        }
        else
        {
        	PrintWriter out = response.getWriter();
		     response.setContentType("application/json");
		     response.setCharacterEncoding("UTF-8");
		     out.print("Employee ID not present");
		     out.flush();
            log.info("Employee ID not present");
        }
    }
}