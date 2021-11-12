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

@WebServlet("/SimpleFormSignUp")
public class SimpleFormEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SimpleFormEdit() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  PrintWriter out = response.getWriter();
	  String userName = request.getParameter("userName");
      String password = request.getParameter("password");
      String firstName = request.getParameter("firstName");
      String lastName = request.getParameter("lastName");
      int age;
      try
      {
    	  age = Integer.parseInt(request.getParameter("age"));
      }
      catch (NumberFormatException e)
      {
    	  age = 0;
      }
      String email = request.getParameter("email");
      
      if (userName.equals("") && password.equals("") && firstName.equals("") 
    		  && lastName.equals("") && (age == 0) &&  email.equals(""))
      {
    	  String error = "Gotta Edit Som'n";
    	  
    	  String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
    	            "transitional//en\">\n";
          out.println(docType + //
                "<html>\n" + //
                "<head><title>" + error + "</title></head>\n" + //
                "<body bgcolor=\"#f0f0f0\">\n" + //
                "<h1 align=\"center\">" + error + "</h1>\n");
          out.println("<meta http-equiv =\"refresh\" content=\"0; /OmaFund/youIn.html\" />");
      }
      else
      {
	      Connection connection = null;
	      //String insertSql = "SELECT username, password, email, first, last, age FROM UserInfo WHERE id = ?";
	
	      try {
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;
	         //PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
	         //preparedStmt.setInt(1, DBConnection.getID());
	         
	         if(userName != "")
	         {
	        	String insertSql = "UPDATE UserInfo SET username = ? WHERE id = ?";
	        	PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
	        	preparedStmt.setString(1, userName);
	        	preparedStmt.setInt(2, DBConnection.getID());
	        	preparedStmt.execute();
	         }
	         if(password != "")
	         {
	        	 
	         }
	         if(firstName != "")
	         {
	        	 
	         }
	         if(lastName != "")
	         {
	        	 
	         }
	         if(age != 0)
	         {
	        	 
	         }
	         
	         
	         
	         connection.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
//	      out.println("<meta http-equiv=\"refresh\" content=\"0; /OmaFund/logIn.html\" />");
	      out.println("<a href=/OmaFund/logIn.html>Log In</a> <br>");
//	      out.println("</body></html>");
      }
   }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
