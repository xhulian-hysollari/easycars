/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import connectionFolder.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Lori
 */
@ManagedBean(name = "cars")
@RequestScoped

public class ManageCars {

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
     * @return the car_id
     */
    public int getCar_id() {
        return car_id;
    }

    /**
     * @param car_id the car_id to set
     */
    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    /**
     * @return the car_make
     */
    public String getCar_make() {
        return car_make;
    }

    /**
     * @param car_make the car_make to set
     */
    public void setCar_make(String car_make) {
        this.car_make = car_make;
    }

    /**
     * @return the car_model
     */
    public String getCar_model() {
        return car_model;
    }

    /**
     * @param car_model the car_model to set
     */
    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    /**
     * @return the body_type
     */
    public String getBody_type() {
        return body_type;
    }

    /**
     * @param body_type the body_type to set
     */
    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }

    /**
     * @return the first_registration
     */
    public Date getFirst_registration() {
        return first_registration;
    }

    /**
     * @param first_registration the first_registration to set
     */
    public void setFirst_registration(Date first_registration) {
        this.first_registration = first_registration;
    }

    /**
     * @return the fuel_type
     */
    public String getFuel_type() {
        return fuel_type;
    }

    /**
     * @param fuel_type the fuel_type to set
     */
    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the created_by
     */
    public String getCreated_by() {
        return created_by;
    }

    /**
     * @param created_by the created_by to set
     */
    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
    
    @ManagedProperty(value = "#{login.username}")

    private String username;
    
    private int car_id;
    private String car_make;
    private String car_model;
    private String body_type;
    private Date first_registration;
    private String fuel_type;
    private String owner;
    private String created_by;

    DBConnection db = new DBConnection();

    public List<ManageCars> carList() throws ClassNotFoundException {
        List<ManageCars> cars = new ArrayList<>();
//        Create a collection with the records from the database

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.StrUrl, db.StrUid, db.StrPwd);
            Statement stm = connection.createStatement();
            String sql = "select * from cars";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                ManageCars car = new ManageCars();
                car.setCar_id(rs.getInt("id"));
                car.setCar_make(rs.getString("make"));
                car.setCar_model(rs.getString("model"));
                car.setBody_type(rs.getString("body_type"));
                car.setFuel_type(rs.getString("fuel_type"));
                car.setOwner(rs.getString("owner"));
                car.setFirst_registration(rs.getDate("first_registration"));
                car.setCreated_by(rs.getString("created_by"));
                cars.add(car);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cars;
    }

    public void edit() throws ClassNotFoundException {
//        It retrives the one record with the ID passed from the F param in the edit in the front end**

        List<ManageCars> cars = carList();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int record_id = Integer.parseInt(request.getParameter("id"));

        for (ManageCars car : cars) {

            if (car.getCar_id() == record_id) {
                this.setCar_id(car.car_id);
                this.setCar_make(car.car_make);
                this.setCar_model(car.car_model);
                this.setBody_type(car.body_type);
                this.setFuel_type(car.fuel_type);
                this.setFirst_registration(car.first_registration);
                this.setOwner(car.owner);
            }
        }
    }

    public void delete() throws ClassNotFoundException, SQLException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int record_id = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.StrUrl, db.StrUid, db.StrPwd);
            String sql = "delete from cars where id = " + record_id + "";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();
        } catch (Exception ex) {
             ex.printStackTrace();
        }
    }

    public void create() {
        try {
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String final_date = formatter.format(first_registration);
//            Converts the object in a certain datatype**
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.StrUrl, db.StrUid, db.StrPwd);
            String sql = "INSERT INTO cars (make, model, body_type, first_registration, fuel_type, owner, created_by) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, car_make);
            pst.setString(2, car_model);
            pst.setString(3, body_type);
            pst.setString(4, final_date);
            pst.setString(5, fuel_type);
            pst.setString(6, owner);
            pst.setString(7, username);
            pst.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception Occured in the process :" + ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getMessage()));
        }
    }
    
    public void update() throws ClassNotFoundException {
        try {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        int record_id = Integer.parseInt(request.getParameter("update_id"));
       
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(db.StrUrl, db.StrUid, db.StrPwd);
            
            String sql = "update cars set make = ?, model = ?, body_type = ?, fuel_type = ?, owner = ? where id = ?";
            
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, this.getCar_make());
            pst.setString(2, this.getCar_model());
            pst.setString(3, this.getBody_type());
            pst.setString(4, this.getFuel_type());
            pst.setString(5, this.getOwner());
            pst.setInt(6, record_id);
                        
            int count = pst.executeUpdate();
            if (count > 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Record Updated"));

            }
            else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Something Wrong"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.toString()));
        }
    }
 public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public String logout() {
        HttpSession session = getSession();
        session.invalidate();
        return "index";
    }

}
