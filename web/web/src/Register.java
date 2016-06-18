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
 * @author Administrator
 */
@WebServlet(urlPatterns = {"/Register"})
public class Register extends HttpServlet {

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
        
        //Get user input
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        
        //Validation - need to be done later
        
        //Create connection objects
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
      
        String myDriver = "com.mysql.jdbc.Driver";
        String myUrl = "jdbc:mysql:///ngan_ngan";
        
        try{
            Class.forName(myDriver).newInstance();
            con = DriverManager.getConnection(myUrl,Credential.user, Credential.pass);
            //update db
            String query = "INSERT INTO Users(fname,lname,email,username,password) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, email);
            ps.setString(4, username);
            ps.setString(5, password);
            ps.executeUpdate();     
            
            out.println("New account for <b>" + username +"</b> has been created <br>");
            out.println("Click <a href='index.jsp'>here</a> to return to index");
            
            //close connection
            con.close();
            ps.close();
            rs.close();
        }
        catch(Exception ignore){ out.println(ignore.getMessage());}
        finally{
            if (con != null) try {con.close();} catch (SQLException ignore){}
            if (rs != null) try {rs.close();} catch (SQLException ignore){}
            if (ps != null) try {ps.close();} catch (SQLException ignore){}
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
