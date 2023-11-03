/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author rafacampa9
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


public class GenerarInforme {
    public void generarInforme(String rutaInforme, String nombreInforme) {
        
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/SmartOcupation",
                        "admin",
                        "12345678"
        );
            String reportPath = rutaInforme + "\\" + nombreInforme;
            JasperReport jr = JasperCompileManager.compileReport(reportPath);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
            JasperViewer.viewReport(jp);
   
        } catch (ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
