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
	      String title = "Your Projects";
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
	            "transitional//en\">\n"; //
	      out.println(docType + //
	            "<html>\n" + //
	            "<head><title>" + title + "</title></head>\n" + //
	            "<body bgcolor=\"#f0f0f0\">\n" + //
	            "<h1 align=\"center\">" + title + "</h1>\n");

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
	            	String description = rs.getString("description");

	            	out.println(description);
	            }
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

   void search(int userID, HttpServletResponse response) throws IOException {
      
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
