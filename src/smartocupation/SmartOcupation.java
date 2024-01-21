/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package smartocupation;


import controller.CtrlInicio;
import view.Inicio;


/**
 *
 * @author rafacampa9
 */
public class SmartOcupation {

    /**
     * @param args the command line argumentsa
     */
    public static void main(String[] args) {
     
        Inicio init = new Inicio();
        CtrlInicio ctrlInit = new CtrlInicio(init);
        ctrlInit.iniciar();
        init.setVisible(true);
    }
    
    
}
