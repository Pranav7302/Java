package web;

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

@WebServlet("/ListEmployee")
public class ListEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 Connection con = null;
  	   try {
             Context initContext = new InitialContext();
             Context envContext = (Context) initContext.lookup("java:/comp/env");
             DataSource dataSource = (DataSource) envContext.lookup("jdbc/MyDatabase");
             con = dataSource.getConnection();
		        Statement statement=con.createStatement(); 
		        ResultSet resultset=statement.executeQuery("select* from employeenew");  
		        Employee e1=new Employee();
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
   
		         }		   
		        }catch(Exception exception) {
		            System.out.println(exception);
		        }
	}

}
