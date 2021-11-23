package Database;

/**
 * @file SimpleFormProject.java
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

@WebServlet("/ItemSold")
public class ItemSold extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static int projectID = -1;
	
	public ItemSold() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  PrintWriter out = response.getWriter();
	  
	  int itemId = 0;
      while(request.getParameter(itemId + "") == null)
      {
    	  itemId++;
      }
  
	  response.setContentType("text/html");

	  
      Connection connection = null;
      try {
    	DBConnection.getDBConnection();
        connection = DBConnection.connection;
    	String insertSql = "UPDATE Items SET sold = ? WHERE itemId = ?";
    	PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
    	preparedStmt.setInt(1, 1);
    	preparedStmt.setInt(2, itemId);
    	preparedStmt.execute();
        
    	out.println("<meta http-equiv =\"refresh\" content=\"0.5; /OmaFund/YourItems\" />");
    	
//	     out.println("<form action=\"DeleteProject\" method=\"POST\">\r\n" + 
//	     		"	\r\n" + 
//	     		"		<label for=\"userName\">Project to Delete: </label><input type=\"text\" name=\"delProject\"> <br />\r\n" +  
//	     		"		\r\n" + 
//	     		"		<input type=\"submit\" value=\"Delete\" />\r\n" + 
//	     		"	</form>");
	     connection.close();

      } catch (Exception e) {
         e.printStackTrace();
      }
      
   }
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
