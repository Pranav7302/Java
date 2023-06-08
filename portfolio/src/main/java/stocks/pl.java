package stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

@WebServlet("/pl")
public class pl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			BufferedReader reader = request.getReader();
			Parameters parameters = gson.fromJson(reader, Parameters.class);
			 int id=0,market_price=0;
			 double profit_loss=0;
				if(getportfolio.isUUID(parameters.sessionToken))
		        {
					 PreparedStatement statement0 = StockFunctions.con.prepareStatement("select user_id from tokens_table where tokens = ?");
		             statement0.setString(1,parameters.sessionToken);
		             ResultSet rs = statement0.executeQuery();
		             
		             if(rs.next())
		             {
		             id = rs.getInt(1);  
		             PreparedStatement pstmt=StockFunctions.con.prepareStatement("Select symbol,quantity,purchase_price from portfolio_table where user_id = ?");
		             pstmt.setInt(1, id);
		             ResultSet resultset = pstmt.executeQuery();
                     while(resultset.next())
		             {
		        	     PreparedStatement pstmt1 = StockFunctions.con.prepareStatement("select market_price from stock_details where stock_name = ?");
		        	     pstmt1.setString(1, resultset.getString(1));
		        	     ResultSet rs1 = pstmt1.executeQuery();
		        	     if(rs1.next())
		        	     {
		        	    	market_price = rs1.getInt(1);
		        	     }
		        	     else
		        	     {
		        	    	 PrintWriter out2 = response.getWriter();
		    			     response.setContentType("application/json");
				             response.setCharacterEncoding("UTF-8");
				             out2.println("Market price not found for stock"); 
			     		 }
		        	    profit_loss = (resultset.getInt(2)*resultset.getInt(3)) - (resultset.getInt(2)* market_price); 
		        	    if(profit_loss>=0)
		        	    {
			            	PrintWriter out2 = response.getWriter();
						     response.setContentType("application/json");
			                response.setCharacterEncoding("UTF-8");
			                out2.println("Profit for stock: "+resultset.getString(1)+" is :"+profit_loss); 
		        	    }
		        	    else
		        	    {
		        	    	PrintWriter out2 = response.getWriter();
		   			     response.setContentType("application/json");
			                response.setCharacterEncoding("UTF-8");
			                out2.println("Loss for stock: "+resultset.getString(1)+" is :"+profit_loss);
		        	    }
		             }
		             }
		            else
		             {
		            	PrintWriter out2 = response.getWriter();
					    response.setContentType("application/json");
		                response.setCharacterEncoding("UTF-8");
		                out2.println("Session token invalid or expired, try with correct session token"); 
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
