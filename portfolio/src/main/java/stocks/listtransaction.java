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


@WebServlet("/listtransaction")
public class listtransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			BufferedReader reader = request.getReader();
			Parameters parameters = gson.fromJson(reader, Parameters.class);
        int id=0;
		if(getportfolio.isUUID(parameters.sessionToken))
        {
            ResultSet rs = StockFunctions.getUserID(parameters.sessionToken);
             if(rs.next())
             {
             id = rs.getInt(1);  
        	 String str ="Select * from transaction_table where user_id = "+id;
             PreparedStatement pstmt=StockFunctions.con.prepareStatement(str);
             ResultSet resultset = pstmt.executeQuery();
        	 JSONObject jsonObject = new JSONObject();
             JSONArray array = new JSONArray();
             while(resultset.next()) {
                 JSONObject record = new JSONObject();
                 record.put("Symbol", resultset.getString("symbol"));
                 record.put("Quantity", resultset.getString("quantity"));
                 record.put("Price", resultset.getString("price"));
                 record.put("Transaction Type", resultset.getString("transaction_type"));
                 record.put("Transaction Date", resultset.getString("transaction_date"));
                 array.put(record);
                 jsonObject.put("Transaction", array);
            	}
            	 PrintWriter out = response.getWriter();
			     response.setContentType("application/json");
			     response.setCharacterEncoding("UTF-8");
			     out.print(jsonObject.toString());
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
