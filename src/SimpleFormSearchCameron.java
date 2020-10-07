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

@WebServlet("/SimpleFormSearchCameron")
public class SimpleFormSearchCameron extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearchCameron() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keywordphone = request.getParameter("keywordphone");
      String keywordemail = request.getParameter("keywordemail");
      search(keywordphone, keywordemail, response);
      
   }

   void search(String keywordphone, String keywordemail, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
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
         DBConnectionCameron.getDBConnection(getServletContext());
         connection = DBConnectionCameron.connection;

         if (keywordemail.isEmpty() && keywordphone.isEmpty()) {
            String selectSQL = "SELECT * FROM myTableCameronTE";
            preparedStatement = connection.prepareStatement(selectSQL);
         }
         else {
            String selectSQL = "SELECT * FROM myTableCameronTE WHERE EMAIL LIKE ? AND PHONE LIKE ?";
            String theUserName = keywordemail + "%";
            String phoneTerm = keywordphone + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theUserName);
            preparedStatement.setString(2, phoneTerm);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String userName = rs.getString("myuser").trim();
            String email = rs.getString("email").trim();
            String phone = rs.getString("phone").trim();

            if (userName.contains(keywordemail) || phone.contains(keywordemail) || keywordemail.isEmpty()) {
               out.println("ID: " + id + ", ");
               out.println("User: " + userName + ", ");
               out.println("Email: " + email + ", ");
               out.println("Phone: " + phone + "<br>");
            }
         }
         out.println("<a href=/webproject/simpleFormSearchCameron.html>Search Data</a> <br>");
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
