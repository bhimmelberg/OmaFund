import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datamodel.Kcustom;
import util.UtilDB;

@WebServlet("/MyServletHibernateDB")
public class MyServletHibernateDB extends HttpServlet {
   private static final long serialVersionUID = 1L; 

   public MyServletHibernateDB() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");

      // #1
     // UtilDB.createEmployees("Table", "Kevin", "Schroer", "402-621-0620", "2899 Road Q", "$800");
     // UtilDB.createEmployees("user4", "44");
      
      // #2
      retrieveDisplayData(response.getWriter());
   }

   void retrieveDisplayData(PrintWriter out) {
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");
      out.println("<ul>");
      List<Kcustom> listEmployees = UtilDB.listEmployees();
      for (Kcustom employee : listEmployees) {
         System.out.println(employee.getId() + ", " //
               + employee.getItem() + ", " //
               + employee.getFirstName() + ", " //
               + employee.getLastName() + ", " //
               + employee.getPhone() + ", " //
               + employee.getAddress() + ", " //
               + employee.getPrice());

         out.println("<li>" + employee.getId() + ", " //
        		 + employee.getItem() + ", " //
                 + employee.getFirstName() + ", " //
                 + employee.getLastName() + ", " //
                 + employee.getPhone() + ", " //
                 + employee.getAddress() + ", " //
                 + employee.getPrice() + "</li>");
      }
      out.println("</ul>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
