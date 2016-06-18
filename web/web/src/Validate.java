/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hoa
 */

import java.sql.*;

public class Validate {
    public static boolean checkLogin(String username, String password)
    {
        boolean result = false;
        
        /* Create Connection Objects */
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        
        String myUrl = "jdbc:mysql:///ngan_ngan";
        
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(myUrl,Credential.user, Credential.pass);
            stm = con.createStatement();
            
            String query = "SELECT * FROM Users WHERE username='" + username + "' AND password='" + password + "'";
            rs = stm.executeQuery(query);
            
            if (rs.next())
                return true;
            
            //close connection
            con.close();
            stm.close();
            rs.close();
        }
        catch(Exception ignore){}
        finally{
            if (con != null) try {con.close();} catch (SQLException ignore){}
            if (rs != null) try {rs.close();} catch (SQLException ignore){}
            if (stm != null) try {stm.close();} catch (SQLException ignore){}
        }
        return result;
    }
}
