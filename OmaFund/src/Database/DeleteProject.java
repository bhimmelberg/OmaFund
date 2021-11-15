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

@WebServlet("/DeleteProject")
public class DeleteProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static int projectID = -1;
	
	public DeleteProject() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  PrintWriter out = response.getWriter();
	  String delProject = request.getParameter("delProject");
  
	  response.setContentType("text/html");

	  
      Connection connection = null;
      DBConnection.getDBConnection();
      connection = DBConnection.connection;
      try {
         

    	String insertSql = "DELETE FROM Projects WEHRE title =  ? AND WHERE id = ?";
    	PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
    	preparedStmt.setString(1, delProject);
    	preparedStmt.setInt(2, DBConnection.getID());
    	preparedStmt.execute();

         
         out.println("<form action=\"DeleteProject\" method=\"POST\">\r\n" + 
         		"	\r\n" + 
         		"		<label for=\"userName\">Project to Delete: </label><input type=\"text\" name=\"delProject\"> <br />\r\n" +  
         		"		\r\n" + 
         		"		<input type=\"submit\" value=\"Delete\" />\r\n" + 
         		"	</form>");
         preparedStmt.close();
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
