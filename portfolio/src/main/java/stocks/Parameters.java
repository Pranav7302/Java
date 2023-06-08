package stocks;

public class Parameters {
String sessionToken;
String symbol;
String quantity;
String purchasePrice;
String purchaseDate;
String sellingPrice;
String sellingDate;
String transaction_type;
public Parameters(String sessionToken, String symbol, String quantity, String purchasePrice, String purchaseDate,
		String sellingPrice, String sellingDate, String transaction_type) {
	super();
	this.sessionToken = sessionToken;
	this.symbol = symbol;
	this.quantity = quantity;
	this.purchasePrice = purchasePrice;
	this.purchaseDate = purchaseDate;
	this.sellingPrice = sellingPrice;
	this.sellingDate = sellingDate;
	this.transaction_type = transaction_type;
}
public String getSessionToken() {
	return sessionToken;
}
public void setSessionToken(String sessionToken) {
	this.sessionToken = sessionToken;
}
public String getSymbol() {
	return symbol;
}
public void setSymbol(String symbol) {
	this.symbol = symbol;
}
public String getQuantity() {
	return quantity;
}
public void setQuantity(String quantity) {
	this.quantity = quantity;
}
public String getPurchasePrice() {
	return purchasePrice;
}
public void setPurchasePrice(String purchasePrice) {
	this.purchasePrice = purchasePrice;
}
public String getPurchaseDate() {
	return purchaseDate;
}
public void setPurchaseDate(String purchaseDate) {
	this.purchaseDate = purchaseDate;
}
public String getSellingPrice() {
	return sellingPrice;
}
public void setSellingPrice(String sellingPrice) {
	this.sellingPrice = sellingPrice;
}
public String getSellingDate() {
	return sellingDate;
}
public void setSellingDate(String sellingDate) {
	this.sellingDate = sellingDate;
}
public String getTransaction_type() {
	return transaction_type;
}
public void setTransaction_type(String transaction_type) {
	this.transaction_type = transaction_type;
}
}
