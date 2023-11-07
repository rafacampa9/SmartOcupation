/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entidades;

/**
 *
 * @author rafacampa9
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;
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
            //JasperViewer.viewReport(jp);
            JasperViewer jasperViewer = new JasperViewer(jp);
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setContentPane(jasperViewer.getContentPane());
            frame.setLocationRelativeTo(null);
            frame.setSize(700, 400);
            frame.setTitle("Informe");
            frame.setVisible(true);
            
        } catch (ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
