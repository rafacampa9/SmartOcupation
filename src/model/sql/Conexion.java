/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author rafacampa9
 */
public class Conexion {
    private Connection conn = null;
    private final String BASE = "SmartOcupation";
    private final String USER = "admin";
    private final String PASSWORD = "12345678";
    private final String URL = "jdbc:postgresql://localhost:5432/" + BASE;
    
    
    public Connection conectarDB(){
        try{
            Class.forName("org.postgresql.Driver");
            conn = (Connection) DriverManager.getConnection(
                    this.URL, 
                    this.USER, 
                    this.PASSWORD
            );
        } catch (SQLException e){
            System.err.println(e.toString());
        } catch (ClassNotFoundException e){
            System.err.println(e.toString());
        }
        
        return conn;
        
    }
}
