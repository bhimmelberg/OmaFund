package Database;

/**
 * @file SimpleFormSale.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  PrintWriter out = response.getWriter();
	  //String title = request.getParameter("saleID");
	  String title = request.getParameter("saleID");
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");
//      
//	      Connection connection = null;
//	      String insertSql = " INSERT INTO UserInfo (username, password, email, first, last, age) values (?, ?, ?, ?, ?, ?)";
//	
//	      try {
//	         DBConnection.getDBConnection();
//	         connection = DBConnection.connection;
//	         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
//	         preparedStmt.setString(1, userName);
//	         preparedStmt.setString(2, password);
//	         preparedStmt.setString(3, email);
//	         preparedStmt.setString(4, firstName);
//	         preparedStmt.setString(5, lastName);
//	         preparedStmt.setInt(6, age);
//	         
//	         preparedStmt.execute();
//	         connection.close();
//	      } 
//	      catch (java.sql.SQLIntegrityConstraintViolationException e)
//	      {
//	    	  String error = "Error: Username Already Taken";
//	    	  String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
//	    	            "transitional//en\">\n";
//	    	  out.println(docType + //
//	                  "<html>\n" + //
//	                  "<head><title>" + error + "</title></head>\n" + //
//	                  "<body bgcolor=\"#f0f0f0\">\n" + //
//	                  "<h1 align=\"center\">" + error + "</h1>\n");
//	    	  out.println("<a href=/OmaFund/signUp.html>Sign Up</a> <br>");
//	    	  out.println("<a href=/OmaFund/logIn.html>Log In</a> <br>");
//	    	  return;
//	      }
//	      catch (Exception e) {
//	         e.printStackTrace();
//	      }
//	      out.println("<meta http-equiv =\"refresh\" content=\"0; /OmaFund/logIn.html\" />");
      }
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
