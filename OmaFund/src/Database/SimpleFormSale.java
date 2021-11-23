package Database;

/**
 * @file SimpleFormSale.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSale")
public class SimpleFormSale extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SimpleFormSale() {
		super();
	}
	
	private int moneyRaised = 0;
	private int projectDonationPercentage = 0;

	private String getItemHTMLString(int projectId) {
		String itemHTMLString = "";
		
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
		try {
			 DBConnection.getDBConnection();
	         connection = DBConnection.connection;
	         
	         String selectSQL = "SELECT * FROM Items";
	         preparedStatement = connection.prepareStatement(selectSQL);
	         ResultSet rs = preparedStatement.executeQuery();
	         moneyRaised= 0;
	         while(rs.next())
	         {
	        	 if (projectId == rs.getInt("projectId"))
	        	 {
		        	 int itemId = rs.getInt("itemId");
			         String name = rs.getString("name").trim();
			         boolean sold = (rs.getInt("sold") > 0);
			         itemHTMLString += "<p>"+ name + "</p>\r\n" + 
			         		"	<form style=\"float: left\" action=\"SimpleFormDisplayItem\" method=\"POST\">\r\n" + 
			         		"	   	<input type=\"submit\" name=\"" + itemId + "\" value=\"View Item\" />\r\n" + 
			         		"	</form>\r\n" + 
			         		"	<br>\r\n" + 
			         		"	<br>";
			         System.out.println("Item ID:" + itemId);
			         if (sold)
			         {
			        	 moneyRaised += rs.getInt("price") * ((double)projectDonationPercentage / 100);
			        	 System.out.println("Donation Percentage: " + projectDonationPercentage);
			         }
	        	 }
	         }
		}
		catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (preparedStatement != null)
	               preparedStatement.close();
	         } catch (SQLException se2) {
	         }
	         try {
	            if (connection != null)
	               connection.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
	      }
		
		return itemHTMLString;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  PrintWriter out = response.getWriter();
	  String header = "Project Info:";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + header + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + header + "</h1>\n");
      
      //This saleIndex variable is the index of the button they pressed
      int saleIndex = 0;
      while(request.getParameter(saleIndex + "") == null)
      {
    	  saleIndex++;
      }
      
        Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    
		try {
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;
	         
	         String selectSQL = "SELECT * FROM Projects";
	         preparedStatement = connection.prepareStatement(selectSQL);
	         ResultSet rs = preparedStatement.executeQuery();
	         
	         int index = 0;
	         while(rs.next())
	         {
	        	 if (index == saleIndex)
	        	 {
		         String title = rs.getString("title").trim();
		         String addr1 = rs.getString("address1").trim();
		         String addr2 = rs.getString("address2").trim();
		         int goal = rs.getInt("goal");
		         String desc = rs.getString("description");
		         int projectId = rs.getInt("projectId");
		         DBConnection.setProjectID(projectId);
		         System.out.println("Project ID - Sale: " + projectId);
		         projectDonationPercentage = rs.getInt("percentage");
		         
		         String itemHTMLString = getItemHTMLString(projectId);
		         double goalPercentagePrecise = ((double)moneyRaised / (double)goal) * 100;
		         System.out.println("Money Raised: " + moneyRaised);
		         System.out.println("Goal: " + goal);
		         System.out.println("Goal % Precise: " + goalPercentagePrecise);
		         int goalPercentage = (int)goalPercentagePrecise;
		         if (goalPercentage > 100)
		         {
		        	 goalPercentage = 100;
		         }
		         else if (goalPercentage < 0)
		         {
		        	 goalPercentage = 0;
		         }
		         int goalPercentageRounded = 5*Math.round(goalPercentage/5);
		         System.out.println("Goal %: " + goalPercentage);
		         System.out.println("Goal % rounded: " + goalPercentageRounded);
		         
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
		         		"    color:C40000;\r\n" + 
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
		         		"sectionC {\r\n" + 
		         		"    background-color:grey;\r\n" + 
		         		"    color:black;\r\n" + 
		         		"    font-size:20px;\r\n" + 
		         		"    font-family: lilyUPC;\r\n" + 
		         		"    text-align:left;\r\n" + 
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
		         		"<nav>\r\n" + 
		         		"	<form style=\"float: left\" action=\"SimpleFormMaps\" method=\"POST\">\r\n" + 
		         		"		<input type=\"submit\" value=\"Back\" />\r\n" + 
		         		"	</form>\r\n" + 
		         		"\r\n" + 
		         		"	<form style=\"float: right\" action=\"/OmaFund/item.html\">\r\n" + 
		         		"    	<input type=\"submit\" value=\"Add Item to Sale\" />\r\n" + 
		         		"	</form>\r\n" + 
		         		"</nav>\r\n" + 
		         		"\r\n" + 
		         		"<sectionA>\r\n" + 
		         		"<p>\r\n" + 
		         		title + 
		         		"</p>\r\n" + 
		         		"</sectionA>\r\n" + 
		         		"\r\n" + 
		         		"<sectionB>\r\n" + 
		         		"	<p>\r\n" + 
		         		desc + 
		         		"	<br>\r\n" + 
		         		addr1 + 
		         		"	<br>\r\n" + 
		         		addr2 + "<br><br>" +
		         		"Each Item Will Contribute " + projectDonationPercentage + "% of their Profits" + "<br>" +
		         		"Goal: $" + goal +
		         		"	</p>\r\n" + 
		         		"	<p>\r\n" + 
		         		"    <img src=\"/OmaFund/images/goal" + goalPercentageRounded + ".png\" height=\"200\" width=\"200\" style=\"border: #EBEBEB 4px solid\"/>\r\n" + 
		         		"	</p>\r\n" + 
		         		"	<p>\r\n" + 
		         		"	"+ "Currently at: " +goalPercentage + "%\r\n" + 
		         		"	</p>\r\n" + 
		         		"</sectionB>\r\n" + 
		         		"<hr color=\"black\">\r\n" + 
		         		"\r\n" + 
		         		"<sectionC>\r\n" + 
		         		itemHTMLString +
		         		"</sectionC>\r\n" + 
		         		"\r\n" + 
		         		"</body>\r\n" + 
		         		"</html>");
		         
		         break;
	        	 }
	        	 index++;
	         }
	         
	         preparedStatement.close();
	         connection.close();
	      } catch (SQLException se) {
	         se.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (preparedStatement != null)
	               preparedStatement.close();
	         } catch (SQLException se2) {
	         }
	         try {
	            if (connection != null)
	               connection.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
	      }
      }
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
