package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

import org.apache.log4j.Logger;

import com.google.gson.Gson;
@WebServlet( urlPatterns ="/UpdateDetails")
public class UpdateEmployee extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();
    ResultSet count;
    int emp_count;
    static Logger log = Logger.getLogger(UpdateEmployee.class.getName());
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
                    String query="update employeenew set name=?,age=?,salary=? where id=?";  
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setString (1,e.getName());
                    preparedStmt.setInt  (2,e.getAge());
                    preparedStmt.setInt  (3,e.getSalary());
                    preparedStmt.setInt (4,e.getId());
                    preparedStmt.execute();
                    PrintWriter out = response.getWriter();
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    out.print("Updated Successfully");
                    log.info("Updated successfully");
                    out.flush();
                    con.close();
                    }
                    else
                    {
                    	 PrintWriter out = response.getWriter();
                         response.setContentType("text/plain");
                         response.setCharacterEncoding("UTF-8");
                         out.print("Employee ID not found");
                         log.info("Updation unsuccessful");
                         out.flush();
                         con.close();
                    }
                    }catch(Exception exception) {
                        System.out.println(exception);
                    }
    }
}