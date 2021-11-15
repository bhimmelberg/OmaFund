package Database;

import java.io.File;
import java.io.FileWriter;
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

@WebServlet("/YourProjects")
public class YourProjects extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public YourProjects() {
      super();
   }

   
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   int userID = DBConnection.getID();
	   response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	     // String title = "Your Projects";
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
	            "transitional//en\">\n"; //
	      
	      out.println("<html>\r\n" + 
	      		"<head>\r\n" + 
	      		"<style>\r\n" + 
	      		"header {\r\n" + 
	      		"    background-color:white;\r\n" + 
	      		"    color:black;\r\n" + 
	      		"    text-align:center;\r\n" + 
	      		"    padding:5px;	 \r\n" + 
	      		"}\r\n" + 
	      		"nav {\r\n" + 
	      		"    line-height:30px;\r\n" + 
	      		"    background-color:#eeeeee;\r\n" + 
	      		"    height:300px;\r\n" + 
	      		"    width:60px;\r\n" + 
	      		"    float:left;\r\n" + 
	      		"    padding:5px;	      \r\n" + 
	      		"}\r\n" + 
	      		"\r\n" + 
	      		"label{\r\n" + 
	      		"display:inline-block;\r\n" + 
	      		"width:200px;\r\n" + 
	      		"margin-right:30px;\r\n" + 
	      		"text-align:right;\r\n" + 
	      		"}\r\n" + 
	      		"\r\n" + 
	      		"fieldset{\r\n" + 
	      		"border:none;\r\n" + 
	      		"width:500px;\r\n" + 
	      		"margin:0px auto;\r\n" + 
	      		"}\r\n" + 
	      		"\r\n" + 
	      		"section {\r\n" + 
	      		"    width:550px;\r\n" + 
	      		"    float:left;\r\n" + 
	      		"    text-align:left;\r\n" + 
	      		"    padding:10px;	 	 \r\n" + 
	      		"}\r\n" + 
	      		"footer {\r\n" + 
	      		"    position:absolute;\r\n" + 
	      		"	bottom:0;\r\n" + 
	      		"	width:100%;\r\n" + 
	      		"    background-color:black;\r\n" + 
	      		"    color:white;\r\n" + 
	      		"    clear:both;\r\n" + 
	      		"    text-align:center;\r\n" + 
	      		"    padding:5px;	 	 \r\n" + 
	      		"}\r\n" + 
	      		"</style>\r\n" + 
	      		"</head>\r\n" + 
	      		"\r\n" + 
	      		"<body>\r\n" + 
	      		"<header>\r\n" + 
	      		"<img src=\"/OmaFund/images/OmaFundLogo.PNG\">\r\n" + 
	      		"</header>" +
	      		"<nav>\r\n" + 
     			"\r\n" + 
     			"<form action=\"/OmaFund/youIn.html\">\r\n" + 
     			"    <input type=\"submit\" value=\"Home\" />\r\n" + 
     			"</form>\r\n" + 
     			"</nav>");
	      
	     /* out.println(docType + //
	            "<html>\n" + //
	            "<head><title>" + title + "</title></head>\n" + //
	            "<body bgcolor=\"#f0f0f0\">\n" + //
	            "<h1 align=\"center\">" + title + "</h1>\n" +
	            "<html>\n" + //
     			"<nav>\r\n" + 
     			"\r\n" + 
     			"<form action=\"/OmaFund/youIn.html\">\r\n" + 
     			"    <input type=\"submit\" value=\"Home\" />\r\n" + 
     			"</form>\r\n" + 
     			"</nav>");*/

	      Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      try {
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;

	  
	         String selectSQL = "SELECT * FROM Projects";
	         preparedStatement = connection.prepareStatement(selectSQL);   
	         ResultSet rs = preparedStatement.executeQuery();

	         while (rs.next()) {
	            int theUserID = rs.getInt("id");

	            if (userID == theUserID)
	            {
	            	String description = rs.getString("title");
	            	out.println("<section>" + description + "</section><br />");
	            	
	            }
	         }
	         
	         out.println("<form action=\"DeleteProject\" method=\"POST\">\r\n" + 
	         		"	\r\n" + 
	         		"		<label for=\"userName\">Project to Delete: </label><input type=\"text\" name=\"delProject\"> <br />\r\n" +  
	         		"		\r\n" + 
	         		"		<input type=\"submit\" value=\"Delete\" />\r\n" + 
	         		"	</form>");
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

   void search(int userID, HttpServletResponse response) throws IOException {
      
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
