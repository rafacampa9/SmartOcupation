package controller;

// ***************************** PACKAGES **************************************
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.Inicio;
import view.InicioEmpleado;
import view.LogInAdmin;

/**
 *
 * @author rafacampa9
 * 
 * 
 * Esta clase controla la interación
 * de las clases del paquete view:
 * - Inicio
 * - InicioEmpleado
 * - LogInAdmin
 */
public class CtrlInicio implements ActionListener{

    
    //********************* ATRIBUTOS *****************************************
    private Inicio init = new Inicio();
    
    
    
    //******************** CONSTRUCTOR *****************************************  
    public CtrlInicio(Inicio init) {
        this.init = init;
        this.init.rbAdmin.addActionListener(this);
        this.init.rbEmp.addActionListener(this);
        this.init.rbExit.addActionListener(this);
    }
    
    
    
    
    
    //************************ MÉTODOS *****************************************
    /**
     * Para iniciar la ventana init mediante
     * la clase CtrlInicio
     */
    public void iniciar(){
        init.setTitle("SMART OCUPATION");
        init.setLocationRelativeTo(null);
        init.setResizable(false);
    }   
    
    
/**
 * Método que sobrescribimos de la interfaz ActionListener
 * que hace que nos mantengamos a la escucha del ActionEvent
 * @param e 
 */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        /**
         * Si pulsamos en entrar como
         * modo Administrador,
         * iniciamos las clases correspondientes
         */
        if (e.getSource() == init.rbAdmin){
            LogInAdmin l = new LogInAdmin();
            CtrlLogIn log = new CtrlLogIn(l);
            log.iniciar();
            l.setVisible(true);
            init.setVisible(false);
        }
        
        
        /**
         * Si pulsamos en entrar como
         * modo Empleado,
         * iniciamos las clases correspondientes
         */
        if (e.getSource() == init.rbEmp){
            InicioEmpleado i = new InicioEmpleado();
            CtrlInicioEmpleado ctrl = new CtrlInicioEmpleado(i);
            ctrl.iniciar();
            i.setVisible(true);
            init.setVisible(false);
        }
        
        
        /**
         * Si pulsamos el botón de salir,
         * cerraremos la app
         */
        if (e.getSource() == init.rbExit){
            System.exit(0);
        }
    }
    
    
}
