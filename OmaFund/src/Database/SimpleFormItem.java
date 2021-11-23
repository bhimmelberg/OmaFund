package Database;

/**
 * @file SimpleFormSignUp.java
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

@WebServlet("/SimpleFormItem")
public class SimpleFormItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SimpleFormItem() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  PrintWriter out = response.getWriter();
	  String name = request.getParameter("name");
      String description = request.getParameter("description");
      String picture = request.getParameter("picture");
      double price;
      try
      {
    	  price = Double.parseDouble(request.getParameter("price"));
      }
      catch (NumberFormatException e)
      {
    	  price = 0;
      }
      
      
      if (name.equals("") || description.equals("") || price == 0)
      {
    	  String error = "Information Cannot Be Empty!";
    	  
    	  String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
    	            "transitional//en\">\n";
          out.println(docType + //
                "<html>\n" + //
                "<head><title>" + error + "</title></head>\n" + //
                "<body bgcolor=\"#f0f0f0\">\n" + //
                "<h1 align=\"center\">" + error + "</h1>\n");
          out.println("<a href=/OmaFund/home.html>Home</a> <br>");
          out.println("<a href=/OmaFund/signUp.html>Sign Up</a> <br>");
      }
      else
      {
	      Connection connection = null;
	      String insertSql = "INSERT INTO Items (name, price, description, picture, id, projectId) values (?, ?, ?, ?, ?, ?)";
	
	      try {
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;
	         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
	         preparedStmt.setString(1, name);
	         preparedStmt.setDouble(2, price);
	         preparedStmt.setString(3, description);
	         preparedStmt.setString(4, picture);
	         preparedStmt.setInt(5, DBConnection.getID());
	         preparedStmt.setInt(6, DBConnection.getProjectID());
	         
	         preparedStmt.execute();
	         connection.close();
	      }
	      catch (Exception e) {
	         e.printStackTrace();
	      }
	      out.println("<meta http-equiv =\"refresh\" content=\"0.5; /OmaFund/youIn.html\" />");
      }
   }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
