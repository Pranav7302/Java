package web;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
@WebServlet( urlPatterns ="/ReadDetails")
public class ReadEmployee extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();
    ResultSet count;
    int emp_count;
    static Logger log = Logger.getLogger(ReadEmployee.class.getName());
    protected void doPost(HttpServletRequest request, 
                                   HttpServletResponse response) throws ServletException, IOException {
    	   Employee e = gson.fromJson(request.getReader(), Employee.class);
    	 Connection con = null;
    	   try {
               Context initContext = new InitialContext();
               Context envContext = (Context) initContext.lookup("java:/comp/env");
               DataSource dataSource = (DataSource) envContext.lookup("jdbc/MyDatabase");
               con = dataSource.getConnection();
        Statement stmt = con.createStatement();
        count = stmt.executeQuery("select count(*) from employeenew where id="+e.id);
        while(count.next())
        {
        	emp_count = count.getInt(1);
        }
        if(emp_count>0)
         {
          Statement statement=con.createStatement(); 
          ResultSet resultset=statement.executeQuery("select* from employeenew where id="+e.id);  
          Employee e1=new Employee();
          while(resultset.next())  {
             e1.setId(resultset.getInt(1));   
             e1.setName(resultset.getString(2));
             e1.setAge(resultset.getInt(3));
             e1.setSalary(resultset.getInt(4));   
            }
         String userJsonString = this.gson.toJson(e1); 
         PrintWriter out = response.getWriter();
         response.setContentType("application/json");
         response.setCharacterEncoding("UTF-8");
         out.print(userJsonString);
         log.info("Printed successfully...");
         out.flush();
        }
        else
        {
        	 PrintWriter out = response.getWriter();
             response.setContentType("application/json");
             response.setCharacterEncoding("UTF-8");
             out.print("Employee Id not found");
           	 log.warn("Employee Id not found");
        }
        }catch(Exception exception) {
            System.out.println(exception);
        }
         
}

}