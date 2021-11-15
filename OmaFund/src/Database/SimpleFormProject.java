package Database;

/**
 * @file SimpleFormProject.java
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

@WebServlet("/SimpleFormProject")
public class SimpleFormProject extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int projectID = -1;

	static void setProjectID(int id)
	{
		projectID = id;
	}
	
	static int getProjectID()
	{
		return projectID;
	}
	
	public SimpleFormProject() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  PrintWriter out = response.getWriter();
	  String title = request.getParameter("title");
      String description = request.getParameter("description");
      String addLine1 = request.getParameter("addLine1");
      String addLine2 = request.getParameter("addLine2");
      int goal;
      try
      {
    	  goal = Integer.parseInt(request.getParameter("goal"));
      }
      catch (NumberFormatException e)
      {
    	  goal = 0;
      }
      int percentage;
      try
      {
    	  percentage = Integer.parseInt(request.getParameter("donationPercent"));
      }
      catch (NumberFormatException e)
      {
    	  percentage = 0;
      }
      
      
      if (title.equals("") || description.equals("") || addLine1.equals("") 
    		  || addLine2.equals("") || (goal == 0) ||  percentage == 0)
      {
    	  String error = "Information Cannot Be Empty!";
    	  
    	  String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
    	            "transitional//en\">\n";
          out.println(docType + //
                "<html>\n" + //
                "<head><title>" + error + "</title></head>\n" + //
                "<body bgcolor=\"#f0f0f0\">\n" + //
                "<h1 align=\"center\">" + error + "</h1>\n");
          out.println("<meta http-equiv =\"refresh\" content=\"0.5; /OmaFund/createProject.html\" />");
      }
      else
      {
	      Connection connection = null;
	      String insertSql = " INSERT INTO Projects (title, description, address1, address2, goal, percentage, id) values (?, ?, ?, ?, ?, ?, ?)";
	
	      try {
	         DBConnection.getDBConnection();
	         connection = DBConnection.connection;
	         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
	         preparedStmt.setString(1, title);
	         preparedStmt.setString(2, description);
	         preparedStmt.setString(3, addLine1);
	         preparedStmt.setString(4, addLine2);
	         preparedStmt.setInt(5, goal);
	         preparedStmt.setInt(6, percentage);
	         preparedStmt.setInt(7, DBConnection.getID());
	         
	         preparedStmt.execute();
	         connection.close();
	      } 
	      catch (Exception e) {
	         e.printStackTrace();
	      }
	      String error = "";
    	  
    	  String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
    	            "transitional//en\">\n";
          out.println(docType + //
                "<html>\n" + //
                "<head><title>" + error + "</title></head>\n" + //
                "<body bgcolor=\"#f0f0f0\">\n" + //
                "<h1 align=\"center\">" + error + "</h1>\n");
	      out.println("<meta http-equiv =\"refresh\" content=\"0.5; /OmaFund/youIn.html\" />");
      }
   }
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
