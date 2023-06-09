package stoplimit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
public class Stock {
	public static final String checkStockAvlbl = "select * from stop_limit where symbol = ?";
    public static final String updatePurchaseHistory = "insert into purchase_history (symbol,price,quantity) values (?,?,?)";
    public static final String updateStopLimit = "update stop_limit set tradedQty = ?"; 
    public static final String selectTradedQty = "select tradedQty from stop_limit where symbol = ?";
 String symbol;
 String price;
 String quantity;
 public Stock(String symbol, String price, String quantity) {
		super();
		this.symbol = symbol;
		this.price = price;
		this.quantity = quantity;
	}
 static Connection con;
	static {
     try {
     	Context initContext = new InitialContext();
         Context envContext = (Context) initContext.lookup("java:/comp/env");
         DataSource dataSource = (DataSource) envContext.lookup("jdbc/MyDatabase");
 	    con = dataSource.getConnection();
     } catch (SQLException e) {
         throw new RuntimeException(e);
     } catch (NamingException e) {
			e.printStackTrace();
		}
 }
    public static Double takeStopPrice(Stock stock) throws SQLException
	{
    	Double price = 0.0;
    	PreparedStatement statement0 = con.prepareStatement(checkStockAvlbl);
    	statement0.setString(1, stock.symbol);
    	ResultSet rs = statement0.executeQuery();
    	if(rs.next())
    	{
    	 price=rs.getDouble(4);
    	}
    return price;
    }
    public static boolean canTrade(Stock stock) throws SQLException
	{
		PreparedStatement statement0 = con.prepareStatement(checkStockAvlbl);
		statement0.setString(1, stock.symbol);
		ResultSet rs = statement0.executeQuery();
		if(rs.next())
		{
		int maxQuantity = rs.getInt(3);
		int tradedQuantity = rs.getInt(7);
		if(maxQuantity>tradedQuantity)
		{
			return true;
		}
		else
		{
			return false;
		}
		}
		return false;
	}
    public static boolean checkPrice(String price) {
     try {
        double priceValue = Double.parseDouble(price);
        if(priceValue>=200 && priceValue<=215)
        {
        return true;
        }
        else
        {
        	return false;
        }             
        } catch (NumberFormatException | NullPointerException e) {
        return false;
    }
}
public static boolean checkQuantity(String quantity) {
    try {
        int quantity_value = Integer.parseInt(quantity);
        if(quantity_value!=0)
        {
        return true;
        }
        else
        {
        	return false;
        }
        } catch (NumberFormatException | NullPointerException e) {
        return false;
    }
}
    public static boolean available(String symbol) throws SQLException
    {
	PreparedStatement statement0 = con.prepareStatement(checkStockAvlbl);
    statement0.setString(1, symbol);
	ResultSet rs = statement0.executeQuery();
    if(rs.next())
    {
    	return true;
    }
    else
    {
    	return false;
    }
    }
    public static boolean checkFirstPurchase(Stock stock) throws SQLException
    {
	PreparedStatement statement0 = con.prepareStatement(checkStockAvlbl);
	statement0.setString(1, stock.symbol);
    ResultSet rs = statement0.executeQuery();
    if(rs.next())
    {
    	return true;
    }
    else
    {
    	return false;
    }	
    }
    public static void insertPurchaseHistory(Stock stock) throws SQLException
    {
    	int tradedQty = 0,maxQuantity = 0;
	PreparedStatement statement0 = con.prepareStatement(checkStockAvlbl);
	statement0.setString(1, stock.symbol);
	ResultSet rs = statement0.executeQuery();
	if(rs.next())
	{
	maxQuantity = rs.getInt(3);
	tradedQty = rs.getInt(7);
	}
	int newQty = Integer.parseInt(stock.quantity) + tradedQty;
	PreparedStatement statement2 = con.prepareStatement(updatePurchaseHistory);
	statement2.setString(1, stock.symbol);
	statement2.setString(2,stock.price);
	if (newQty>maxQuantity)
	{
		int diffQty = newQty-maxQuantity;
		statement0.setString(3,String.valueOf(newQty-diffQty));		
		statement0.execute();
	}
	else
	{
		statement0.setString(3,String.valueOf(newQty));
	    statement0.execute();
	}
	}
    public static void insertStopLimit(Stock stock) throws SQLException
    {
    	int maxQuantity =0,tradedQty=0;
	PreparedStatement statement0 = con.prepareStatement(checkStockAvlbl);
	statement0.setString(1, stock.symbol);
	ResultSet rs = statement0.executeQuery();
	if(rs.next())
	{
	maxQuantity = rs.getInt(3);
	tradedQty = rs.getInt(7);
	}
	int newQty = Integer.parseInt(stock.quantity) + tradedQty;
	PreparedStatement statement1 = con.prepareStatement(updateStopLimit);
	if (newQty>maxQuantity)
	{
		int diffQty = newQty-maxQuantity;
		statement1.setString(1, String.valueOf(newQty-diffQty));
		statement1.execute();
	}
	else
	{
		statement1.setString(1,String.valueOf(newQty));
		statement1.execute();
	}
    }
}
