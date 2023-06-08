package stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

@WebServlet("/add")
public class add extends HttpServlet {
	private Gson gson = new Gson();
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
	try {
		int id = 0;
		BufferedReader reader = request.getReader();
		Parameters parameters = gson.fromJson(reader, Parameters.class);
        if(StockFunctions.isUUID(parameters.sessionToken))
        	{
        	if(StockFunctions.checkSymbol(parameters.symbol))
        	{
        		if(StockFunctions.checkQuantity(parameters.quantity))
        		{
        			if(StockFunctions.checkPurchasePrice(parameters.purchasePrice))
        			{
        				if(StockFunctions.checkPurchaseDate(parameters.purchaseDate))
        				{
        					 PrintWriter out = response.getWriter();
        					 response.setContentType("application/json");
        		             response.setCharacterEncoding("UTF-8");
        		             out.println("Parameters are validated succeesfully");  
        		             ResultSet rs = StockFunctions.getUserID(parameters.sessionToken);
        		             if(rs.next())
        		             {
        		              id = rs.getInt(1);  
        		              StockFunctions.insertPortfolio(String.valueOf(id), parameters.symbol, parameters.quantity, parameters.purchasePrice,parameters.purchaseDate);
        		              PrintWriter out2 = response.getWriter();
        		  		      response.setContentType("application/json");
           	                  response.setCharacterEncoding("UTF-8");
           	                  out2.println("Successfully added to portfolio table"); 
        		             }
        		             else
        		             {
        		            	 PrintWriter out2 = response.getWriter();
        		     		      response.setContentType("application/json");
              	                  response.setCharacterEncoding("UTF-8");
              	                  out2.println("Session token invalid or expired, try with coreect session token");
        		             }
        		        }
        				else
        				{
        					 PrintWriter out = response.getWriter();
        					 response.setContentType("application/json");
        		             response.setCharacterEncoding("UTF-8");
        		             out.println("Enter the current date for purchase date, the valid format is (dd-MM-yyyy");  
        				}
        			}
        			else
        			{
        				 PrintWriter out = response.getWriter();
        				 response.setContentType("application/json");
        	             response.setCharacterEncoding("UTF-8");
        	             out.println("Enter the value in decimal for purchase price");  
        			}
        		}
        		else
        		{
        			 PrintWriter out = response.getWriter();
        			 response.setContentType("application/json");
                     response.setCharacterEncoding("UTF-8");
                     out.println("Enter the integer value for quantity");  
        		}
        	}
        	else
        	{
        		 PrintWriter out = response.getWriter();
     		     response.setContentType("application/json");
                 response.setCharacterEncoding("UTF-8");
                 out.println("Enter only special characters for symbols");  
        	}
        	}
        else
        {
        	 PrintWriter out = response.getWriter();
 		     response.setContentType("application/json");
             response.setCharacterEncoding("UTF-8");
             out.println("Enter a valid session token");  
        }
		}catch(Exception e)
		{
			PrintWriter out = response.getWriter();
		    response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.println(e);  
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
