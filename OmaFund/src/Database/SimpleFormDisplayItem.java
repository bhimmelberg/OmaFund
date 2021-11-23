package Database;

/**
 * @file SimpleFormSignUp.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormDisplayItem")
public class SimpleFormDisplayItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SimpleFormDisplayItem() {
		super();
	}

	private String[] getUserInfo(int userID) {
		String[] userInfo = new String[3];
		
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    
		try {
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;
	         
	         String selectSQL = "SELECT * FROM UserInfo";
	         preparedStatement = connection.prepareStatement(selectSQL);
	         ResultSet rs = preparedStatement.executeQuery();
	         
	         while(rs.next()) {
	        	 if (rs.getInt("id") == userID)
	        	 {
	        		 userInfo[0] = rs.getString("first");
	        		 userInfo[1] = rs.getString("last");
	        		 userInfo[2] = rs.getString("email");
	        	 }
	         }
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return userInfo;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  PrintWriter out = response.getWriter();
	  
	  int itemId = 0;
      while(request.getParameter(itemId + "") == null)
      {
    	  itemId++;
      }
	  
      Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    
		try {
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;
	         
	         String selectSQL = "SELECT * FROM Items";
	         preparedStatement = connection.prepareStatement(selectSQL);
	         ResultSet rs = preparedStatement.executeQuery();
	         
	         while(rs.next())
	         {
	        	 if (rs.getInt("itemId") == itemId)
	        	 {
	        		 System.out.println("in Simple Form Display Item");
	        		 String name = rs.getString("name").trim();
			         double price = rs.getDouble("price");
			         String description = rs.getString("description");
			         String picture = rs.getString("picture");
			         String[] userInfo = getUserInfo(rs.getInt("id"));
			         if (rs.getInt("sold") > 0)
			         {
			        	 name = "This Item Has Been Sold";
			         }
			         out.println("<html>\r\n" + 
			         		"<head>\r\n" + 
			         		"<style>\r\n" + 
			         		"header {\r\n" + 
			         		"    background-color:white; \r\n" + 
			         		"    color:black;\r\n" + 
			         		"    text-align:center;\r\n" + 
			         		"    padding:5px;	 \r\n" + 
			         		"}\r\n" + 
			         		"nav {\r\n" + 
			         		"    background-color:grey;\r\n" + 
			         		"    color:black;\r\n" + 
			         		"    font-size:30px;\r\n" + 
			         		"    text-align:left;\r\n" + 
			         		"    padding:1px;\r\n" + 
			         		"}\r\n" + 
			         		"\r\n" + 
			         		"form{\r\n" + 
			         		"	display:inline-block;\r\n" + 
			         		"}\r\n" + 
			         		"\r\n" + 
			         		"sectionA {\r\n" + 
			         		"    background-color:grey;\r\n" + 
			         		"    color:black;\r\n" + 
			         		"    font-size:30px;\r\n" + 
			         		"    font-family: cooper black;\r\n" + 
			         		"    font-style: italic;\r\n" + 
			         		"    text-align:center;\r\n" + 
			         		"    width:550px;\r\n" + 
			         		"    padding:5px;\r\n" + 
			         		"}\r\n" + 
			         		"\r\n" + 
			         		"sectionB {\r\n" + 
			         		"    background-color:grey;\r\n" + 
			         		"    color:black;\r\n" + 
			         		"    font-size:20px;\r\n" + 
			         		"    font-family: lilyUPC;\r\n" + 
			         		"    text-align:center;\r\n" + 
			         		"    width:550px;\r\n" + 
			         		"    padding:5px;	 	 \r\n" + 
			         		"}\r\n" + 
			         		"\r\n" + 
			         		"body {\r\n" + 
			         		"	background-color:grey\r\n" + 
			         		"}\r\n" + 
			         		"\r\n" + 
			         		"div.container {\r\n" + 
			         		"	  text-align: center;\r\n" + 
			         		"      display:inline-block;\r\n" + 
			         		"      padding:50px;\r\n" + 
			         		"}\r\n" + 
			         		"\r\n" + 
			         		"</style>\r\n" + 
			         		"</head>\r\n" + 
			         		"\r\n" + 
			         		"<body>\r\n" + 
			         		"<header>\r\n" + 
			         		"<img src=\"/OmaFund/images/OmaFundLogo.PNG\">\r\n" + 
			         		"</header>\r\n" + 
			         		"\r\n" + 
			         		"<sectionA>\r\n" + 
			         		"<p>\r\n" + 
			         		name + 
			         		"</p>\r\n" + 
			         		"</sectionA>\r\n" + 
			         		"\r\n" + 
			         		"<sectionB>\r\n" + 
			         		"	<p>\r\n" + 
			         		description + 
			         		"	<br>\r\n" + 
			         		"$" + price + 
			         		"	<br>\r\n" + 
			         		"Seller: " + userInfo[0] + " " + userInfo[1] + 
			         		"<br>Email: " + userInfo[2] + 
			         		"	</p>\r\n" + 
			         		"	<p>\r\n" + 
			         		"    <img src=\"" + picture +"\" height=\"200\" width=\"200\" style=\"border: #EBEBEB 4px solid\"/>\r\n" + 
			         		"	</p>\r\n" + 
			         		"	<p>\r\n" + 
			         		"</sectionB>\r\n" + 
			         		"\r\n" + 
			         		"</body>\r\n" + 
			         		"</html>");
			         break;
	        	 }
	         }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
   }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
