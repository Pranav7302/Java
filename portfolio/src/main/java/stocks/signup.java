package stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

@WebServlet("/signup")
public class signup extends HttpServlet {
	private Gson gson = new Gson();
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);
        try {
        	boolean usernameAck=true; 
        	boolean passwordValidation = true;
            if (user.getUsername().length() <= 6 || !user.getUsername().matches("[a-zA-Z0-9_]+")) {
            	PrintWriter out = response.getWriter();
			     response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");
	            out.println("UserName didn't match the requirements try again with correct format"); 
	            usernameAck=false;
            }
            if(usernameAck)
            {	
    	    try {
    	        PreparedStatement statement = StockFunctions.con.prepareStatement("SELECT * FROM users_table WHERE username = ?");
    	        statement.setString(1, user.getUsername());
    	        ResultSet resultSet = statement.executeQuery();
    	        
    	        if (resultSet.next()) {
    	        	 PrintWriter out = response.getWriter();
				     response.setContentType("application/json");
    	             response.setCharacterEncoding("UTF-8");
    	             out.println("UserName already exists Try with different username");  
    	             usernameAck=false;
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
            }
            if(usernameAck)
            {
            	 PrintWriter out = response.getWriter();
			     response.setContentType("application/json");
	             response.setCharacterEncoding("UTF-8");
	             out.println("UserName validated successfully");
	             if (user.getPassword().length() <= 8 || !user.getPassword().matches(".*[A-Z].*") || !user.getPassword().matches(".*[a-z].*") || !user.getPassword().matches(".*\\d.*") || !user.getPassword().matches(".*[^A-Za-z0-9].*") || user.getPassword().matches(".*\\s.*"))
	         	 {
	     	      passwordValidation = false;	
	     	     }
	             if(passwordValidation == true)
	             {
	            	 out.println("password validation also successful");
	            	 PreparedStatement statement = StockFunctions.con.prepareStatement("insert into users_table (username,password)" + "values(?,?)");
	     	         statement.setString(1, user.getUsername());
	     	         statement.setString(2, user.getPassword());
	     	         statement.execute();	     	
	     	         PrintWriter out1 = response.getWriter();
				     response.setContentType("application/json");
		             response.setCharacterEncoding("UTF-8");
   	                 out1.println("User signup successful");  
	             }
	             else
	             {
    	             out.println("Password didn't meet the requirements try again with new password");  
	             }
            }
        }catch(Exception e)
        {
        	PrintWriter out2 = response.getWriter();
		    response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out2.println(e); 
         }	
	}
	public void destroy() {
        if (StockFunctions.con != null) {
            try {
                ((AutoCloseable) StockFunctions.con).close();
            } catch (Exception e) {
            }
        }
    }
}
