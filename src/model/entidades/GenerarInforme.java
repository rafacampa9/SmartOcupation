//************************ PACKAGES ********************************************
package model.entidades;
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

/**
 *
 *
 * Clase utilizada para generar
 * un informe JasperReport
 * 
 * 
 * @author rafacampa9
 */
public class GenerarInforme {
    
    /**
     * No devuelve nada este método, simplemente
     * se encarga de generar el informe en
     * la ruta que pasamos por el parámetro
     * @param rutaInforme
     * 
     * y cuyo nombre será el String 
     * pasado por el parámetro 
     * @param nombreInforme 
     * 
     */
    public void generarInforme(String rutaInforme, String nombreInforme) {
        
        
        try{
            /**
             * Conectamos con la base de datos
             */
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/SmartOcupation",
                        "admin",
                        "12345678"
        );
            //Path del informe
            String reportPath = rutaInforme + "\\" + nombreInforme;
            /**
             * Compilamos el reporte
             */
            JasperReport jr = 
                    JasperCompileManager.compileReport(reportPath);
            
            /**
             * Rellenamos el reporte y lo almacenamos en un
             * JasperPrint
             */
            JasperPrint jp = JasperFillManager.fillReport(jr, 
                                                    null, 
                                                    conn);
            //JasperViewer.viewReport(jp);
            /**
             * Instanciamos un nuevo JasperViewer pasándole
             * por el constructor el JasperPrint
             */
            JasperViewer jasperViewer = new JasperViewer(jp);
            /**
             * Asociamos el JasperViewer a un JFrame para poder cerrar
             * el reporte sin que se cierre la app
             */
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setContentPane(jasperViewer.getContentPane());
            frame.setLocationRelativeTo(null);
            frame.setSize(700, 400);
            frame.setTitle("Informe");
            frame.setVisible(true);
            
            
        /**
         * Tratamos las posibles excepciones
         */
        } catch (ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
