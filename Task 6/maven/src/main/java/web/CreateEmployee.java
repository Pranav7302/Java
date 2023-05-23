package web;
import java.io.BufferedReader;
import java.io.IOException;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

@WebServlet( urlPatterns ="/InsertDetails")
public class CreateEmployee extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ResultSet count;
    int emp_count;
    Connection con = null;
    static Logger log = Logger.getLogger(CreateEmployee.class.getName());
    private Gson gson = new Gson();
    protected void doPost(HttpServletRequest request, 
                                   HttpServletResponse response) throws ServletException, IOException {
    
            BufferedReader reader = request.getReader();
            Employee e = gson.fromJson(reader, Employee.class);
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
        if(emp_count==0)
        {
        String query="insert into employeenew (id,name,age,salary)"+"values(?,?,?,?)";  
        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setInt (1,e.getId());
        preparedStmt.setString (2,e.getName());
        preparedStmt.setInt  (3,e.getAge());
        preparedStmt.setInt  (4,e.getSalary());
        preparedStmt.execute();
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        out.print("Inserted Successfully");
        log.info("Insertion successful");
        out.flush();
        con.close();
        }
        else
        {
        	 PrintWriter out = response.getWriter();
             response.setContentType("text/plain");
             response.setCharacterEncoding("UTF-8");
             out.print("Employee ID already exists, try with different ID");
             log.warn("Try with new Emp ID");
             out.flush();
             con.close();
        }
        }catch(Exception exception) {
            log.info(exception);
        }
}
}