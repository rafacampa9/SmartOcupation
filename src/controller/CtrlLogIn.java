//***************************** PACKAGES ***************************************
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.Inicio;
import view.InicioAdmin;
import view.LogInAdmin;

/**
 *
 * @author rafacampa9
 */
public class CtrlLogIn implements ActionListener{

    //******************* ATRIBUTOS ********************************************
    private LogInAdmin log = new LogInAdmin();
    
    
    
    
    //****************** CONSTRUCTOR *******************************************

    public CtrlLogIn(LogInAdmin log) {
        this.log = log;
        this.log.btnEnviar.addActionListener(this);
        this.log.txtPass.addActionListener(this);
        this.log.txtUser.addActionListener(this);
    }
    
    
    /**
     * Para iniciar la ventana
     * de LogInAdmin
     */
    public void iniciar(){
        log.setTitle("Administrador");
        log.setLocationRelativeTo(null);
        log.setResizable(false);
    }

    
    /**
     * actionPerformed sobrescrito por la interfaz
     * ActionListener que atiende a la llamada del
     * evento de la clase ActionEvent
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        /**
         * Si pulsamos el botón ENVIAR
         */
        if (e.getSource() == log.btnEnviar){
            /**
             * SI:
             * USER:        "admin"
             * PASSWORD:    "12345678"
             * 
             * Accedes a la ventana init
             * de la clase InicioAdmin
             * 
             */
            if (log.txtUser.getText().equals("admin") && 
                    log.txtPass.getText().equals("12345678")){
                InicioAdmin init = new InicioAdmin();
                CtrlInicioAdmin ctrlAd = new CtrlInicioAdmin(init);
                ctrlAd.iniciar();
                init.setVisible(true);
                log.setVisible(false);
                /**
                 * Si no introduces los datos correctamente
                 * mostrará un mensaje de diálogo informando
                 * del error y volverás a la ventana de Inicio
                 */
            } else{
                JOptionPane.showMessageDialog(null, 
                        "Error al introducir los datos",
                        "WARNING",
                        JOptionPane.WARNING_MESSAGE);
                Inicio init = new Inicio();
                CtrlInicio ctrlInit = new CtrlInicio(init);
                ctrlInit.iniciar();
                init.setVisible(true);
                log.setVisible(false);
            }
        }
    }
    
}
