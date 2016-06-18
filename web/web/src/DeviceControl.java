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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Hoa
 */
@WebServlet(urlPatterns = {"/DeviceControl"})
public class DeviceControl extends HttpServlet {

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
        
        //Check whether user's still logged
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null)
        {
            out.println("You have been logged out, click <a href='login.html'>here</a> to login again");
            return;
        }
        
        /* Create Connection Objects */
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        
        
        String myUrl = "jdbc:mysql:///ngan_ngan";
        //Display devices list
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(myUrl,Credential.user, Credential.pass);
            stm = con.createStatement();

            //Get User ID
            String usersQuery = "SELECT uid FROM Users WHERE username = '" + username + "'";
            rs = stm.executeQuery(usersQuery);           
            if (!rs.isBeforeFirst())
            {
                out.println("No record");
            }
            else
            {
                rs.next();
                int uid = rs.getInt("uid");
                
                //Get list of devices
                String query = "SELECT D.did, D.dname, D.dstatus, D.dkey as did, dname, dstatus, dkey FROM Devices D, User_Contain_Devices UCD" 
                        + " WHERE D.did = UCD.did AND UCD.uid = " + uid;
                rs = stm.executeQuery(query);
                
                out.println("<table");
                out.println("<tr>");
                out.println("<th width='100'>Device ID</th>");
                out.println("<th width='100'>Device Name</th>");
                out.println("<th width='100'>Device Status</th>");
                out.println("<th width='100'></th>");
                while(rs.next())
                {
                    out.println("<tr>");
                    String dname = rs.getString("dname");
                    String did = rs.getString("did");
                    String dstatus = rs.getString("dstatus");
                    String dkey = rs.getString("dkey");
                    out.println("<td align='center'>"+ did + "</td>");
                    out.println("<td align='center'><a href='deviceInfo.jsp?dkey=" + dkey + "'>" + dname + "</a></td>");
                    out.println("<td align='center'>" + dstatus + "</td>");
                    out.println("<td><input type='button' value='Remove'/></td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
            
                
            
            //Confirmation message
            
            //Redirect to DeviceControl serlvet
            //request.getRequestDispatcher("addNewDevice.html").forward(request, response);
            
            //close connection
            con.close();
            stm.close();
            rs.close();
        }
        catch(Exception ignore){out.println(ignore.getMessage());}
        finally{
            if (con != null) try {con.close();} catch (SQLException ignore){}
            if (rs != null) try {rs.close();} catch (SQLException ignore){}
            if (stm != null) try {stm.close();} catch (SQLException ignore){}
        }
        
        
        
        
        
        //Add new device
        request.getRequestDispatcher("addNewDevice.html").include(request, response);
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