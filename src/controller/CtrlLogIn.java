/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import view.Inicio;
import view.InicioAdmin;
import view.LogInAdmin;

/**
 *
 * @author rafacampa9
 */
public class CtrlLogIn implements ActionListener{

    //ATRIBUTOS
    private LogInAdmin log = new LogInAdmin();
    
    // CONSTRUCTOR

    public CtrlLogIn(LogInAdmin log) {
        this.log = log;
        this.log.btnEnviar.addActionListener(this);
        this.log.txtPass.addActionListener(this);
        this.log.txtUser.addActionListener(this);
    }
    
    public void iniciar(){
        log.setTitle("Administrador");
        log.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == log.btnEnviar){
            if (log.txtUser.getText().equals("admin") && 
                    log.txtPass.getText().equals("12345678")){
                InicioAdmin init = new InicioAdmin();
                CtrlInicioAdmin ctrlAd = new CtrlInicioAdmin(init);
                ctrlAd.iniciar();
                init.setVisible(true);
                log.setVisible(false);
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
