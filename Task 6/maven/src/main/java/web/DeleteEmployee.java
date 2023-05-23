package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import java.sql.Statement;
import com.google.gson.Gson;
@WebServlet( urlPatterns ="/DeleteDetails")
public class DeleteEmployee extends HttpServlet{
	   private static final long serialVersionUID = 1L;
	    static Logger log = Logger.getLogger(ReadEmployee.class.getName());
   private Gson gson = new Gson();
   ResultSet count;
   int emp_count;
    protected void doPost(HttpServletRequest request, 
                                   HttpServletResponse response) throws ServletException, IOException {
          
    	BufferedReader reader = request.getReader();
        Employee e = gson.fromJson(reader, Employee.class);
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
        String query="delete from employeenew where id=?";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setInt (1,e.id);
        preparedStmt.execute();
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        out.print("Deleted successfully");
        log.info("Deletion successful");
        out.flush();
        }
        else
        {
        	PrintWriter out = response.getWriter();
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            out.print("Employee ID not found");
            log.warn("Employee ID not found");
            out.flush();
        }
        }catch(Exception exception) {
            log.info(exception);
        }
         
}
}