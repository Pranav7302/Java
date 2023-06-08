package stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

@WebServlet("/sell")
public class sell extends HttpServlet {
	private Gson gson = new Gson();
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = 0;
			int quantity = 0;
			BufferedReader reader = request.getReader();
			Parameters parameters = gson.fromJson(reader, Parameters.class);
	        if(StockFunctions.isUUID(parameters.sessionToken))
	        	{
	        	if(StockFunctions.checkSymbol(parameters.symbol))
	        	{
	        		if(StockFunctions.checkQuantity(parameters.quantity))
	        		{
	        			if(StockFunctions.checkPurchasePrice(parameters.sellingPrice))
	        			{
	        				if(StockFunctions.checkPurchaseDate(parameters.sellingDate))
	        				{
	        				     if(parameters.transaction_type.equalsIgnoreCase("sell"))
	        				     {
	        					 PrintWriter out = response.getWriter();
        	     	        	 response.setContentType("application/json");
	        		             response.setCharacterEncoding("UTF-8");
	        		             out.println("Parameters are validated succeesfully");  
	        		             ResultSet rs = StockFunctions.getUserID(parameters.sessionToken);
	        		             if(rs.next())
	        		              {
	        		              id = rs.getInt(1);
	        		              PreparedStatement statement2 = StockFunctions.con.prepareStatement("select quantity from portfolio_table where user_id = ? and symbol = ?");
	        	     	          statement2.setString(1, String.valueOf(id));
	        		              statement2.setString(2, parameters.symbol);
	        		              ResultSet rs1 = statement2.executeQuery();
	        	     	          if(rs1.next())
	        	     	          {
	        	     	        	  quantity = rs1.getInt(1);
	        	     	          }
	        	     	          else
	        	     	          {
	        	     	        	 PrintWriter out2 = response.getWriter();
	        	     	        	 response.setContentType("application/json");
	        	     	        	 response.setCharacterEncoding("UTF-8");
	              	                 out2.println("No stocks are present");
	        	     	          }
	        	     	          int sellingQuantity=Integer.parseInt(parameters.quantity);
	        	     	          if(quantity>=sellingQuantity)
	        	     	          {
	        	     	          int remainingQuantity = quantity-sellingQuantity;
	        	     	          if(remainingQuantity>0)
	        	     	          {
	        	     	          PreparedStatement statement3 = StockFunctions.con.prepareStatement("update portfolio_table set quantity = ? where user_id = ? and symbol = ? ");
		        	     	      statement3.setString(1, String.valueOf(remainingQuantity));
	        	     	          statement3.setString(2, String.valueOf(id));
	        	     	          statement3.setString(3, parameters.symbol);
	        	     	          statement3.execute();
	        	     	          }
	        	     	          else
	        	     	          {
	        	     	           PreparedStatement statement3 = StockFunctions.con.prepareStatement("delete from portfolio_table where user_id = ? and symbol = ?");
			        	     	   statement3.setString(1, String.valueOf(id));
			        	     	   statement3.setString(2, parameters.symbol);
		        	     	       statement3.execute();
	        	     	          }
	            		          StockFunctions.insertTransaction(String.valueOf(id), parameters.symbol, parameters.quantity, parameters.sellingPrice,parameters.transaction_type,parameters.sellingDate);
	        	     	          PrintWriter out2 = response.getWriter();
	        	     	          response.setContentType("application/json");
	           	                  response.setCharacterEncoding("UTF-8");
	           	                  out2.println("Transaction Successful and added to transaction table successfully");
	        	     	          }
	        	     	          else
	        	     	           {
	        	     	        	 PrintWriter out2 = response.getWriter();
	        	     	        	 response.setContentType("application/json");
	              	                 response.setCharacterEncoding("UTF-8");
	              	                 out2.println("There is no enough stocks");
	        	     	           }
	        		             }
	        		             else
	        		               {
	        		            	 PrintWriter out2 = response.getWriter();
	        	     	        	 response.setContentType("application/json");
	              	                 response.setCharacterEncoding("UTF-8");
	              	                 out2.println("Session token invalid or expired, enter a valid session token");
	        		                }
	        				    }
	        		            else
	        		            {
	        		            	 PrintWriter out2 = response.getWriter();
	        	     	        	 response.setContentType("application/json");
	              	                 response.setCharacterEncoding("UTF-8");
	              	                 out2.println("To sell a stock the transaction type should be sell");
	        		            }
	        		        }
	        				else
	        				{
	        					 PrintWriter out = response.getWriter();
	        		             response.setContentType("text/plain");
	        		             response.setCharacterEncoding("UTF-8");
	        		             out.println("Enter the current date for selling date, the valid format is (dd-MM-yyyy)");  
	        				}
	        			}
	        			else
	        			{
	        				 PrintWriter out = response.getWriter();
    	     	        	 response.setContentType("application/json");
	        	             response.setCharacterEncoding("UTF-8");
	        	             out.println("Enter a decimal value for selling price");  
	        			}
	        		}
	        		else
	        		{
	        			 PrintWriter out = response.getWriter();
	     	        	 response.setContentType("application/json");
	                     response.setCharacterEncoding("UTF-8");
	                     out.println("Enter an integer value for quantity");  
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
