/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author Hoa
 */
@WebServlet(urlPatterns = {"/GetLastData"})
public class GetLastData extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        /* Create Connection Objects */
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        
      
        String myDriver = "com.mysql.jdbc.Driver";
        String myUrl = "jdbc:mysql:///ngan_ngan";
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(myUrl,Credential.user, Credential.pass);
            //out.println("<h>Connected</h>");
            
            
            
            String query = "SELECT * FROM Temp";
            
            stm = con.createStatement();
            rs = stm.executeQuery(query);
            
            int lastData = 0;
            if (!rs.isBeforeFirst())
                out.println("No data");
            else {
                while (rs.next()) {
                    lastData = rs.getInt(1);
                }
                out.println(lastData);
            }
            
            
            out.close();
            con.close();
            stm.close();
            rs.close();
        }
        catch(Exception e){}
        finally{
            if (con != null) try {con.close();} catch (SQLException ignore){}
            if (rs != null) try {rs.close();} catch (SQLException ignore){}
            if (stm != null) try {stm.close();} catch (SQLException ignore){}
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
