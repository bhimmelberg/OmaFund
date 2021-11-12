package Database;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
   public static Connection connection = null;
   private static int userID = -1;

   public static void getDBConnection() {
      System.out.println("-------- MySQL JDBC Connection Testing ------------");
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e) {
         System.out.println("Where is your MySQL JDBC Driver?");
         e.printStackTrace();
         return;
      }
      System.out.println("MySQL JDBC Driver Registered!");

      connection = null;
      try {
         UtilProp.loadProperty();
         //connection = DriverManager.getConnection(getURL(), getUserName(), getPassword());
         connection = DriverManager.getConnection("jdbc:mysql://ec2-3-135-240-102.us-east-2.compute.amazonaws.com:3306/OmaFund?enabledTLSProtocols=TLSv1.2", 
        	 "bhimmelremote", "Ellamae2001!");
      } catch (Exception e) {
         System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return;
      }

      if (connection != null) {
         System.out.println("You made it, take control your database now!");
      } else {
         System.out.println("Failed to make connection!");
      }
   }

   static String getURL() {
      String url = UtilProp.getProp("url");
      System.out.println("[DBG] URL: " + url);
      return url;
   }

   static String getUserName() {
      String usr = UtilProp.getProp("user");
      System.out.println("[DBG] URL: " + usr);
      return usr;
   }

   static String getPassword() {
      String pwd = UtilProp.getProp("password");
      System.out.println("[DBG] URL: " + pwd);
      return pwd;
   }
   
   static void setID(int id) {
	   userID = id;
   }
   
   static int getID() {
	   return userID;
   }
}