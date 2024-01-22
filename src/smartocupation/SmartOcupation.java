//************************** PACKAGES ******************************************
package smartocupation;


import controller.CtrlInicio;
import view.Inicio;


/**
 *
 * @author rafacampa9
 */
public class SmartOcupation {

    /**
     * 
     *Método main
     * 
     * @param args the command line argumentsa
     */
    public static void main(String[] args) {
     
        Inicio init = new Inicio();
        CtrlInicio ctrlInit = new CtrlInicio(init);
        ctrlInit.iniciar();
        init.setVisible(true);
    }
    
    
}
