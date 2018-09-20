/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginFolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import connectionFolder.DBConnection;

/**
 *
 * @author Lori
 */
@ManagedBean(name = "login")
@SessionScoped

public class Login {

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    private String username;
    private String password;
    private int id;
    DBConnection db = new DBConnection();

    public String authenticate() {
//        this method does the auntetication
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.StrUrl, db.StrUid, db.StrPwd);
            String sql = "Select * FROM user WHERE username = '" + username + "' and password = '" + password + "'";
            Statement statement = (Statement) connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt("id");
                return "cars?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Wrong Credentials, please try again"));
                return null;
            }
        } catch (Exception e) {
            System.err.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Wrong Credentials, please try again"));
            return null;
        }   
    }
}
