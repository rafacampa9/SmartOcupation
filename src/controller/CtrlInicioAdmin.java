//******************************** PACKAGES ************************************
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import model.entidades.Arrendamiento;
import model.entidades.Cliente;
import model.entidades.Propietario;
import model.entidades.Vivienda;
import model.sql.CrudSQL;
import view.Alquiler;
import view.Clientes;
import view.Inicio;
import view.InicioAdmin;
import view.Propietarios;
import view.Viviendas;

/**
 *
 * @author rafacampa9
 * 
 * 
 * Esta clase controla la iteración de objetos
 * de las clases del paquete model:
 * 
 * - model.entidades:
 *  > Arrendamiento
 *  > Cliente
 *  > Propietario
 *  > Vivienda
 * 
 * - model.sql:
 *  > CrudSQL
 * 
 * - view:
 *  > Alquiler
 *  > Clientes
 *  > Inicio
 *  > InicioAdmin
 *  > Propietarios
 *  > Viviendas
 */ 
public class CtrlInicioAdmin implements ActionListener{

    //************************* ATRIBUTOS **************************************
    private InicioAdmin init = new InicioAdmin();
    private final ButtonGroup grupo;
    
    
    
    
    //************************ CONSTRUCTOR *************************************
    
    public CtrlInicioAdmin(InicioAdmin init) {
        this.init = init;
        this.init.rbArrendamientos.addActionListener(this);
        this.init.rbClientes.addActionListener(this);
        this.init.rbInicio.addActionListener(this);
        this.init.rbPropietarios.addActionListener(this);
        this.init.rbViviendas.addActionListener(this);
        this.grupo = new ButtonGroup();
        
        this.grupo.add(init.rbArrendamientos);
        this.grupo.add(init.rbClientes);
        this.grupo.add(init.rbInicio);
        this.grupo.add(init.rbPropietarios);
        this.grupo.add(init.rbViviendas);
    }
    
    
    //************************* MÉTODOS ****************************************
    
    /**
     * Para iniciar la ventana Administrador
     * centrada en la pantalla y no 
     * redimensionable
     */
    public void iniciar(){
        init.setTitle("Administrador");
        init.setLocationRelativeTo(null);
        init.setResizable(false);
    }

    
    /**
     * Método que sobrescribimos de la interfaz
     * ActionListener que atiende a la escucha
     * del evento ActionEvent
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * Declaramos un objeto CrudSQL
         * y lo inicializamos
         */
        CrudSQL crud = new CrudSQL();
        
        
        /**
         * Si pulsamos sobre Clientes
         */
        if (e.getSource() == init.rbClientes){
            Cliente cliente = new Cliente();
            Clientes cl = new Clientes();
            /**
             * Iniciamos la clase CtrlClientes y hacemos
             * visible la ventana de la clase Clientes
             */
            CtrlClientes ctrl = new CtrlClientes(cliente, crud, cl);
            ctrl.iniciar();
            cl.setVisible(true);
            init.setVisible(false);
        }
        
        
        /**
         * Si pulsamos sobre Propietarios,
         * iniciaremos un objeto de la clase CtrlPropietario
         * para poder interactuar con la ventana
         * Porpietarios
         */
        if (e.getSource() == init.rbPropietarios){
            Propietario propietario = new Propietario();
            Propietarios prop = new Propietarios();
            
            CtrlPropietario ctrl = new CtrlPropietario (
                                    propietario,crud, prop);
            ctrl.iniciar();
            prop.setVisible(true);
            init.setVisible(false);
        }
        
        
        
        
        /**
         * Si pulsamos sobre Viviendas,
         * iniciaremos un objeto de la clase
         * CtrlViviendas y atenderemos a 
         * la ventana Viviendas
         */
        if (e.getSource() == init.rbViviendas){
            Vivienda vivienda = new Vivienda();
            Viviendas v = new Viviendas();
            CtrlViviendas ctrl = new CtrlViviendas(vivienda, crud, v);
            ctrl.iniciar();
            v.setVisible(true);
            init.setVisible(false);
        }
        
        
        
        
        
        /**
         * Si pulsmos sobre Arrendamientos,
         * iniciaremos un objeto de la clase
         * CtrlArrendamiento y atenderemos
         * a la ventana Alquiler
         */
        if (e.getSource() == init.rbArrendamientos){
            Arrendamiento arr = new Arrendamiento();
            Alquiler alq = new Alquiler();
            CtrlArrendamiento ctrl = new CtrlArrendamiento(arr, crud, alq);
            ctrl.iniciar();
            alq.setVisible(true);
            init.setVisible(false);   
        }
        
        
        
        
        /**
         * Si pulsamos sobre Inicio,
         * volveremos a la pantalla de Inicio
         */
        if (e.getSource() == init.rbInicio){
            Inicio inicio = new Inicio();
            CtrlInicio ctrl = new CtrlInicio(inicio);
            ctrl.iniciar();
            inicio.setVisible(true);
            init.setVisible(false);
        }
    }
    
}
