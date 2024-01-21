//****************************** PACKAGES **************************************
package model.sql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author rafacampa9
 */
public class Conexion {
    
    //************************ATRIBUTOS*****************************************
    private Connection conn = null;
    private final String BASE = "SmartOcupation";
    private final String USER = "admin";
    private final String PASSWORD = "12345678";
    private final String URL = "jdbc:postgresql://localhost:5432/" + BASE;
    
    
    
    
    
    //****************************MÉTODOS***************************************
    /**
     * Método que nos devuelve un objeto de tipo
     * Connection conectado a la base de Datos 
     * cuyos valores son URL, USER y PASSWORD. 
     * 
     * El SGBD que se ha utilizado para este
     * proyecto es PostgreSQL
     * 
     * @return 
     */
    public Connection conectarDB(){
        try{
            /**
             * Conectamos
             */
            Class.forName("org.postgresql.Driver");
            conn = (Connection) DriverManager.getConnection(
                    this.URL, 
                    this.USER, 
                    this.PASSWORD
            );
        } catch (SQLException e){
            /**
             * Excepción de SQL
             */
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            /**
             * Excepción por no encontrar
             * la clase
             */
            e.printStackTrace();
        }
        
        /**
         *   Retornamos nuestro objeto 
         *   conn
         */
        return conn;
        
    }
}
