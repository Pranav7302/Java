package stocks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class StockFunctions {
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
	public static boolean checkSymbol(String symbol) {
        String pattern = "^[^a-zA-Z0-9\\s]+$";
        return Pattern.matches(pattern, symbol);
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

    public static boolean checkPurchasePrice(String price) {
        try {
            double priceValue = Double.parseDouble(price);
            if(priceValue!=0)
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

    public static boolean checkPurchaseDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String currentDateString = currentDate.format(formatter);
            if (date.equals(currentDateString)) {
             return true;
            } 
            else {
             return false; 
            }
        } catch (ParseException | NullPointerException e) {
            return false;
        }
    }
    public static boolean isUUID(String input) {
        try {
            UUID.fromString(input);
            return true;
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }
    }
    public static ResultSet getUserID(String tokens) throws SQLException
    {
    	PreparedStatement statement0 = StockFunctions.con.prepareStatement("select user_id from tokens_table where tokens = ?");
        statement0.setString(1,tokens);
        ResultSet rs = statement0.executeQuery();
		return rs;
    }
    public static void insertPortfolio(String user_id, String symbol, String quantity, String purchase_price, String purchase_date) throws SQLException
    {
    	PreparedStatement statement = con.prepareStatement("insert into portfolio_table (user_id,symbol,quantity,purchase_price,purchase_date)" + "values(?,?,?,?,?)");
        statement.setString(1, user_id);
        statement.setString(2, symbol);
        statement.setString(3, quantity);
        statement.setString(4, purchase_price);
        statement.setString(5, purchase_date);
        statement.execute();
    }
    public static void insertTransaction(String user_id, String symbol, String quantity, String purchase_price, String transaction_type, String purchase_date) throws SQLException
    {
    	PreparedStatement statement1 = con.prepareStatement("insert into transaction_table (user_id,symbol,quantity,price,transaction_type,transaction_date)" + "values(?,?,?,?,?,?)");
    	statement1.setString(1, user_id);
        statement1.setString(2, symbol);
        statement1.setString(3, quantity);
        statement1.setString(4, purchase_price);
        statement1.setString(5, transaction_type);
        statement1.setString(6, purchase_date);
        statement1.execute();
    }
}