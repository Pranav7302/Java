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
  
        
        String code = request.getParameter("employeecode").trim();
        Integer n=Integer.parseInt(code);
        Context initContext = null;
		try {
			initContext = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}
        Context envContext = null;
		try {
			envContext = (Context) initContext.lookup("java:/comp/env");
		} catch (NamingException e) {
			e.printStackTrace();
		}
        DataSource dataSource = null;
		try {
			dataSource = (DataSource) envContext.lookup("jdbc/MyDatabase");
		} catch (NamingException e) {
			e.printStackTrace();
		}
        try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        try {
			count = stmt.executeQuery("select count(*) from employeenew where id="+code);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        try {
			while(count.next())
			{
			 emp_count = count.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
         if(emp_count>0)
           {        
        	try {
				ResultSet resultset = EmployeeTable.searchInTable(n);
    		    Employee e1 = new Employee();
				while(resultset.next())  {
		             e1.setId(resultset.getInt(1));   
		             e1.setName(resultset.getString(2));
		             e1.setAge(resultset.getInt(3));
		             e1.setSalary(resultset.getInt(4));   
		             String userJsonString = this.gson.toJson(e1);   
				     PrintWriter out = response.getWriter();
				     response.setContentType("application/json");
				     response.setCharacterEncoding("UTF-8");
				     out.print(userJsonString);
				     out.flush();
                     log.info("Employee ID is present ");
        	}
        	} catch (JSONException | SQLException e) {
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