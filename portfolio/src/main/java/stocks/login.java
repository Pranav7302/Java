package stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();
    int emp_count=0;
    int id;
    ResultSet count;
    ResultSet matches;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException  {
	try {
		 BufferedReader reader = request.getReader();
         User user = gson.fromJson(reader, User.class);
         String query2 = ("select * from users_table where username= ? and password = ?"); 
         PreparedStatement preparedStatement1 = StockFunctions.con.prepareStatement(query2);
         preparedStatement1.setString(1,user.getUsername());
         preparedStatement1.setString(2,user.getPassword());
         count = preparedStatement1.executeQuery();
         if(count.next())
         {
           	emp_count = 1;
         }
         if(emp_count==1)
         {
        	 UUID uuid=UUID.randomUUID();
        	 id = count.getInt(1);
        	 String query21 = "insert into tokens_table (tokens,user_id)" + "values(?,?)";
             PreparedStatement preparedStatement = StockFunctions.con.prepareStatement(query21);
             preparedStatement.setString(1, uuid.toString());
             preparedStatement.setInt(2, id);
             preparedStatement.execute();
             PrintWriter out = response.getWriter();
 		     response.setContentType("application/json");
             response.setCharacterEncoding("UTF-8");
             out.println("Insertion successful");  
             out.println("Session token: "+ uuid);
         }
         else
         {
        	 PrintWriter out = response.getWriter();
 		    response.setContentType("application/json");
             response.setCharacterEncoding("UTF-8");
             out.print("Username or password is wrong check the credentials and try again");   
         }
		}catch(Exception e) {
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
