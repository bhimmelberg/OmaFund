import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.io.IOException;  //not pppp
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import Database.DBConnection;


//pupu

/**
 * Servlet implementation class Home
 */

@SuppressWarnings("unused")
@WebServlet("/Home")
public class Home extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public Home() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      search(response);
   }

   void search(HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Items in List";
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
         //Order the table alphabetically based on store
         String selectSQL = "SELECT * FROM UserInfo";
         preparedStatement = connection.prepareStatement(selectSQL);
         ResultSet rs = preparedStatement.executeQuery();
         
         while (rs.next()) {
            String username = rs.getString("username").trim();
            String password = rs.getString("password").trim();
            String email = rs.getString("email").trim();
            String first = rs.getString("first").trim();
            String last = rs.getString("last").trim();
            int age = rs.getInt("age");
            
            out.println("Username: " + username + " | ");
            out.println("Password: " + password + " | ");
            out.println("Email:" + email + " | ");
            out.println("First Name: " + first + " | ");
            out.println("Last Name: " + last + " | ");
            out.println("Age: " + age + "<br><br>");

         }
         out.println("<a href=/shoppinglist/add_item.html>Add New Items</a> <br>");
         out.println("</body></html>");
         rs.close();
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

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}