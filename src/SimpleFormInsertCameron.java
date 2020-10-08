
/**
 * @file SimpleFormInsertCameron.java
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

@WebServlet("/SimpleFormInsertCameron")
public class SimpleFormInsertCameron extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsertCameron() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String userName = request.getParameter("userName");
      String email = request.getParameter("email");
      String phone = request.getParameter("phone");
      String address = request.getParameter("address");

      Connection connection = null;
      String insertSql = " INSERT INTO myTableCameronTE (id, MYUSER, EMAIL, PHONE, ADDRESS) values (default, ?, ?, ?, ?)";

      try {
         DBConnectionCameron.getDBConnection(getServletContext());
         connection = DBConnectionCameron.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, userName);
         preparedStmt.setString(2, email);
         preparedStmt.setString(3, phone);
         preparedStmt.setNString(4, address);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Success! \nContact has been inserted in Contact List";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>User Name</b>: " + userName + "\n" + //
            "  <li><b>Email</b>: " + email + "\n" + //
            "  <li><b>Phone</b>: " + phone + "\n" + //
            "  <li><b>Address</b>: " + address + "\n" + //
            "</ul>\n");

      out.println("<a href=/techexercise-cameron/simpleFormSearchCameron.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
