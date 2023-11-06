/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.Inicio;
import view.InicioEmpleado;
import view.LogInAdmin;

/**
 *
 * @author rafacampa9
 */
public class CtrlInicio implements ActionListener{

    
    // ATRIBUTOS
    private Inicio init = new Inicio();
    
    // CONSTRUCTOR  
    public CtrlInicio(Inicio init) {
        this.init = init;
        this.init.rbAdmin.addActionListener(this);
        this.init.rbEmp.addActionListener(this);
        this.init.rbExit.addActionListener(this);
    }
    
    public void iniciar(){
        init.setTitle("SMART OCUPATION");
        init.setLocationRelativeTo(null);
        init.setResizable(false);
    }   

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == init.rbAdmin){
            LogInAdmin l = new LogInAdmin();
            CtrlLogIn log = new CtrlLogIn(l);
            log.iniciar();
            l.setVisible(true);
            init.setVisible(false);
        }
        
        if (e.getSource() == init.rbEmp){
            InicioEmpleado i = new InicioEmpleado();
            CtrlInicioEmpleado ctrl = new CtrlInicioEmpleado(i);
            ctrl.iniciar();
            i.setVisible(true);
            init.setVisible(false);
        }
        
        if (e.getSource() == init.rbExit){
            System.exit(0);
        }
    }
    
    
}
