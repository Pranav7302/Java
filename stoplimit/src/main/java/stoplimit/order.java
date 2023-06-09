package stoplimit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;


@WebServlet("/order")
public class order extends HttpServlet {
	private Gson gson = new Gson();
    private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		Stock stock = gson.fromJson(reader, Stock.class);
		
	try {
		Double price = Double.parseDouble(stock.price);
		if(Stock.available(stock.symbol))
	    { 
	     if(Stock.checkPrice(stock.price))
	     {
		  if(Stock.checkQuantity(stock.quantity))
		   {
			if(Stock.canTrade(stock))
			{
			 if(Stock.checkFirstPurchase(stock))
			 {
				if(price == Stock.takeStopPrice(stock) )
				{
					Stock.insertPurchaseHistory(stock);
					Stock.insertStopLimit(stock);
					if(Stock.canTrade(stock) == false)
					{
						PrintWriter out2 = response.getWriter();
					    response.setContentType("application/json");
			            response.setCharacterEncoding("UTF-8");
			            JSONObject record = new JSONObject();
			            record.put("Record", "All orders are filled");
			            out2.println(record);
			             out2.println("success");
			             out2.println(price);
			             out2.println( Stock.takeStopPrice(stock));
					}
				}
				else
				{
					PrintWriter out2 = response.getWriter();
				    response.setContentType("application/json");
		            response.setCharacterEncoding("UTF-8");
		            JSONObject record = new JSONObject();
		            record.put("Trading not triggered", "First purchase price should be equal to stop price");
		            out2.println(record);
				}
			 }
			 else
			 {
				 Stock.insertPurchaseHistory(stock);
				 Stock.insertStopLimit(stock);
				 if(Stock.canTrade(stock) == false)
					{
						PrintWriter out2 = response.getWriter();
					    response.setContentType("application/json");
			            response.setCharacterEncoding("UTF-8");
			            JSONObject record = new JSONObject();
			            record.put("Record", "All orders are filled");
			            out2.println(record);
					}
			 }
			}
			else
			{
				PrintWriter out2 = response.getWriter();
			    response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");
	            JSONObject record = new JSONObject();
	            record.put("Cannot be traded ", "All orders are filled");
	            out2.println(record);
			}
		}
		else
		{
			PrintWriter out2 = response.getWriter();
		    response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JSONObject record = new JSONObject();
            record.put("Input validation failed ", "Quantity must be an integer value");
            out2.println(record);
		}
	}
	else
	{
		PrintWriter out2 = response.getWriter();
	    response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject record = new JSONObject();
        record.put("Input validation failed ", "Price must be a decimal value ranging between 200 and 215");
        out2.println(record);
	}
	}
	else
	{
		PrintWriter out2 = response.getWriter();
	    response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject record = new JSONObject();
        record.put("Stock not found", "Stock not available in stop_limit table");
        out2.println(record);
        out2.println("not found");
	}
	
	}catch(Exception e)
	{
		PrintWriter out2 = response.getWriter();
	    response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject record = new JSONObject();
        try {
			record.put("Exception", e);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
        out2.println(record);
        
	}
	}

}
