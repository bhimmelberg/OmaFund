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

@WebServlet("/SimpleFormLogIn")
public class SimpleFormLogIn extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormLogIn() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String userNameEntry = request.getParameter("userName");
      String passwordEntry = request.getParameter("password");
      search(userNameEntry, passwordEntry, response);
   }

   void search(String userNameEntry, String passwordEntry, HttpServletResponse response) throws IOException {
	  int loggedIn = 0;
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Log In";
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

         if (userNameEntry.isEmpty() || passwordEntry.isEmpty()) {
        	 String error = "Error: Username or Password Cannot be Empty!";
        	 
             out.println(docType + //
                   "<html>\n" + //
                   "<head><title>" + error + "</title></head>\n" + //
                   "<body bgcolor=\"#f0f0f0\">\n" + //
                   "<h1 align=\"center\">" + error + "</h1>\n");
             out.println("<a href=/OmaFund/logIn.html>Log In</a> <br>");
             out.println("<a href=/OmaFund/home.html>Home</a> <br>");
         }
         else {
            String selectSQL = "SELECT * FROM UserInfo WHERE username LIKE ? AND password LIKE ?";
            String theUserName = userNameEntry;
            String thePassword = passwordEntry;
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theUserName);
            preparedStatement.setString(2, thePassword);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            String userName = rs.getString("userName").trim();
            String password = rs.getString("password").trim();
            
            //SAVED ID NUMBER WE NEED FOR REST OF STUFF
            DBConnection.setID(rs.getInt("id"));
            DBConnection.setOmaFundUserName(userName);

            if (userName.equals(userNameEntry) && password.equals(passwordEntry))
            {
            	//logged in
            	System.out.println("You Are Logged In!");
            	loggedIn = 1;
            	String error2 = "";
          	  
          	  String docType2 = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
          	            "transitional//en\">\n";
                out.println(docType2 + //
                      "<html>\n" + //
                      "<head><title>" + error2 + "</title></head>\n" + //
                      "<body bgcolor=\"#f0f0f0\">\n" + //
                      "<h1 align=\"center\">" + error2 + "</h1>\n");
      	      out.println("<meta http-equiv =\"refresh\" content=\"0.5; /OmaFund/youIn.html\" />");
            }
         }
         
         if(loggedIn == 0)
         {
        	 String error = "Username or Password is Incorrect!";
        	 
             out.println(docType + //
                   "<html>\n" + //
                   "<head><title>" + error + "</title></head>\n" + //
                   "<body bgcolor=\"#f0f0f0\">\n" + //
                   "<h1 align=\"center\">" + error + "</h1>\n");
             out.println("<a href=/OmaFund/logIn.html>Log In</a> <br>");
             out.println("</body></html>");
         }
         
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
