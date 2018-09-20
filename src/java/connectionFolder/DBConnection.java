/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionFolder;


import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Lori
 */
public class DBConnection {
   
    // URL of the database and the credencials of the account
    public String StrUrl = "jdbc:mysql://localhost:3306/easycars?autoReconnect=true&useSSL=false";
    public String StrUid = "root";
    public String StrPwd = "";
    
  //  useSSL = false;
    
    
    private Connection connect;
    
    public void getConnected(){
      Connection connection;
      try{
          
      setConnection(DriverManager.getConnection(StrUrl, StrUid, StrPwd));
      
      
    }catch(Exception e){
        System.err.println(e);
    }

    }
    public Connection getConnection(){
        return connect;
    }

    
    public void setConnection(Connection connect) {
        this.connect = connect;
    }
}