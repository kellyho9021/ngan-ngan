/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Khoi
 */
@WebServlet(urlPatterns = {"/Binh"})
public class Binh extends HttpServlet {

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
            stm = con.createStatement();
            
            String deviceAction = request.getParameter("action");
            String deviceStatus = request.getParameter("status");
            String strTemp = request.getParameter("temp");
            
            //update action
            if (deviceAction != null)
            {
                String actionQuery = "UPDATE DeviceCtrl SET action = '" + deviceAction + "' WHERE did = 1";
                stm.executeUpdate(actionQuery);
            }
            
            //update status
            if (deviceStatus != null)
            {
                String statusQuery = "UPDATE DeviceCtrl SET status = '" + deviceStatus + "' WHERE did = 1";
                stm.executeUpdate(statusQuery);
            }
            
            //update temp
            if (strTemp != null)
            {
                int temp = Integer.parseInt(strTemp);
                String query = "INSERT INTO Temp VALUES(" + temp + ")";
                stm.executeUpdate(query);
            }
            
            //print out the current action/status
            String deviceQuery = "SELECT * FROM DeviceCtrl";
            rs = stm.executeQuery(deviceQuery);
            
            if (!rs.isBeforeFirst())
                out.println("Cannot find the device");
            else
            {
                rs.next();
                out.println(rs.getString("action") + " " + rs.getString("status"));
            }
            
                                   
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
