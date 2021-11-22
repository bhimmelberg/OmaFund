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
		         String desc = rs.getString("description");
		         out.println("Title: " +  title + "<br />" + "Address: " +  addr1 + " " + addr2 + "<br />"
		        		 + "Description: " +  desc + "<br />");
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
