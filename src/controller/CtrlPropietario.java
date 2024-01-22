//***************************** PACKAGES ***************************************
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.entidades.Entidad;
import model.entidades.Propietario;
import model.sql.CrudSQL;
import view.InicioAdmin;
import view.Propietarios;

/**
 *
 * @author rafacampa9
 * 
 * Esta clase enlaza las clases del 
 * paquete model:
 * - model.entidades
 *  > Entidad
 *  > Propietario
 * 
 * - model.sql
 *  > CrudSQL
 * 
 * - view
 *  > InicioAdmin
 *  > Propietario
 */
public class CtrlPropietario implements ActionListener{
    
    //************************* ATRIBUTOS *************************************
    private Propietario pr;
    private final CrudSQL crud;
    private final Propietarios prs;
    
    
    
    
    
    //************************ CONSTRUCTOR *************************************

    public CtrlPropietario(Propietario pr, 
                            CrudSQL crud, 
                            Propietarios prs) {
                        this.pr = pr;
                        this.crud = crud;
                        this.prs = prs;
                        this.prs.jTextField1.addActionListener(this);
                        this.prs.jTextField2.addActionListener(this);
                        this.prs.btnInsert.addActionListener(this);
                        this.prs.btnRead.addActionListener(this);
                        this.prs.btnDelete.addActionListener(this);
                        this.prs.btnUpdate.addActionListener(this);
                        this.prs.btnBuscar.addActionListener(this);
                        this.prs.btnVolver.addActionListener(this);
                    }

    
    
    
    //************************** MÉTODOS ***************************************
    
    /**
     * Para iniciar la ventana PROPIETARIOS
     * TÍTULO: Propietarios
     * UBICACIÓN: centro de la panalla
     * REDIMENSIONABLE: NO
     * CIERRA APP: NO
     */
    public void iniciar(){
        prs.setTitle("Propietarios");
        prs.setLocationRelativeTo(null);
        prs.setResizable(false);
    }

    
    /**
     * Para limpiar los dos campos de 
     * texto que tenemos en la clase
     * Propietarios del paquete view
     */
    public void limpiar(){
        prs.jTextField1.setText(null);
        prs.jTextField2.setText(null);
    }
    
    
    
    /**
     * Para limpiar la tabla de la 
     * ventana 
     */
    public void limpiaTabla(){
        DefaultTableModel table = (DefaultTableModel) prs.tabla.getModel();
        table.setRowCount(0);
    }
    
    
    
    /**
     * Atiende a los eventos producidos
     * por el ActionEvent
     * @param e 
     * 
     * Método sobrescrito por la 
     * interfaz ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        /**
         * Si pulsamos el botón de
         * INSERTAR
         */
        if (e.getSource() == prs.btnInsert){
            String dni = prs.jTextField1.getText();
            String nombre = prs.jTextField2.getText();

            
            /**
             * Si dni NO es vacío y 
             * nombre NO es vacío
             */
            if (!dni.isEmpty() 
                    && !nombre.isEmpty()) {
                pr.setDni(dni);
                pr.setNombre(nombre);
            
                /**
                 * Obtenemos los valores del propietario
                 */
                Propietario existente = 
                        (Propietario)crud.buscar("PROPIETARIOS", 
                                                pr);
                
                /**
                 * Si NO existe, insertamos
                 */
                if (existente == null){
                    /**
                     * Si el método insertar pr en
                     * la tabla PROPIETARIOS devuelve
                     * true, nos lo indica con
                     * un mensaje de diálogo
                     * 
                     */
                    if(crud.insertar("PROPIETARIOS", pr)){
                        JOptionPane.showMessageDialog(null, 
                                "Registro insertado correctamente");
                        
                        
                    }else{
                        /**
                         * Si no, nos comenta que
                         * ha habido un error
                         */
                        JOptionPane.showMessageDialog(null, 
                                "No se ha podido insertar el"
                                        + " registro",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
 
                    }
                    /**
                     * Si al buscar el registro que queremos
                     * insertar ya existe, nos devuelve
                     * un mensaje de diálogo indicándolo
                     */
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Registro ya existente",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            
            /**
             * Si no has rellenado todos los
             * campos
             */
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Por favor, asegúrese de rellenar "
                                + "todos los campos",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);

            }
            
        }
        
        
        
        /**
         * Si pulsamos el botón de
         * LEER
         */
        if (e.getSource() == prs.btnRead){
            limpiaTabla();
            /**
             * SELECT * FROM PROPIETARIOS;
             */
            LinkedHashSet<Entidad> props = crud.leer("PROPIETARIOS");
            LinkedHashSet<Propietario> propietarios = new LinkedHashSet<>();
            /**
             * Añadimos a la lista de la clase Propietario
             * los registros de la lista de la
             * interfaz Entidad
             */
            for (Entidad propietario: props){
                propietarios.add((Propietario) propietario);
            }
            
            /**
             * Obtenemos el modelo de la tabla
             */
            DefaultTableModel model = (DefaultTableModel) prs.tabla.getModel(); 

            /**
             * Agregamos las filas
             */
            for (Propietario propietario: propietarios){
                Object[] fila = {
                            propietario.getDni(),
                            propietario.getNombre()
                            };
                model.addRow(fila); 
            }
        }
        
        
        
        
        
        /**
         * Si pulsamos sobre el botón
         * MODIFICAR
         */
        if (e.getSource() == prs.btnUpdate) {
            limpiaTabla();
            String dni = prs.jTextField1.getText();
            String nombre = prs.jTextField2.getText();
            
            /**
             * Si dni no es nulo 
             * y nombre tampoco
             */
            if (nombre != null && !nombre.isEmpty()) {
                pr.setDni(dni);
                pr.setNombre(nombre);
                
                /**
                 * Buscamos el propietario cuyos valores
                 * hemos pasado por parámetro
                 */
                Propietario existente = (Propietario) crud.buscar(
                        "PROPIETARIOS", pr);
                /**
                 * Si existe el registro, modificamos 
                 * el registro de la BBDD con el método 
                 * modificar, pasándole como valores los
                 * presentados por el objeto pr, que nos devolverá
                 * true en caso de poder realizarse. Nos avisa
                 * con una ventana de diálogo.
                 */
                if (existente != null){
                    if (crud.modificar("PROPIETARIOS", pr)){
                        JOptionPane.showMessageDialog(null, 
                                "Registro modificado correctamente.");

                    /**
                     * Excepción al realizar
                     * la modificación del registro
                     */
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "No se ha podido modificar el "
                                        + "registro",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);

                    }
                  /**
                   * Los valores del registro
                   * pasado no coinciden con ninguno 
                   * en la base de datos
                   */
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "El registro que pretende modificar no existe",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);

                }
               
            /**
             * Si no has rellenado
             * todos los campos
             */
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Por favor, asegúrese de "
                                + "rellenar todos los campos",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);

            }
        }
        
        
        
        
        /**
         * Si pulsamos sobre el botón
         * BORRAR
         */
        if (e.getSource() == prs.btnDelete){
            limpiaTabla();
            String dni = prs.jTextField1.getText();
            
            /**
             * Si los valores para el dni
             * no son nulos
             */
            if (!dni.isEmpty()){
                pr.setDni(dni);
                
                /**
                 * Si al realizar el método eliminar
                 * pasando como parámetro el objeto
                 * cuyo dni es el ingresado en el campo
                 * de texto, nos notifica de que ha sido
                 * eliminado correctamente
                 */
                if(crud.eliminar("PROPIETARIOS", pr)){
                    JOptionPane.showMessageDialog(null, 
                            "Registro eliminado correctamente");

                /**
                 * Si no se ha podido eliminar,
                 * nos lo indica con un mensaje 
                 * de diálogo
                 */
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "El registro no ha podido ser"
                                    + " eliminado",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);

                }
            /**
             * Si no has ingresado el DNI
             */
            } else{
                JOptionPane.showMessageDialog(null, 
                        "Por favor, ingrese el DNI del "
                                + "usuario que desea eliminar",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);

            }
        }
        
        
        
        
        /**
         * Si pulsamos sobre el botón
         * BUSCAR
         */
        if (e.getSource() == prs.btnBuscar){
            limpiaTabla();
            String dni = prs.jTextField1.getText();
            
            /**
             * Si el campo dni no es nulo
             */
            if (!dni.isEmpty()){
                pr.setDni(dni);
                /**
                 * Otenemos el valor del
                 * resto de los campos con el
                 * método buscar, que devuelve
                 * un objeto de la interfaz
                 * Entidad. Parseamos a Propietario
                 */
                pr = (Propietario) crud.buscar("PROPIETARIOS",
                        pr);

                /**
                 * Si tras buscar el Propietario, el objeto
                 * no es nulo, rellenamos la tabla
                 */
                
                if (pr != null) {
                    DefaultTableModel model = 
                            (DefaultTableModel) prs.tabla.getModel();

                    
                    Object [] resultado = {pr.getDni(), pr.getNombre()};
                    model.addRow(resultado);
                
                    /**
                     * Si es nulo, nos informa que
                     * no hay resultados
                     */
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "No se encontraron resultados");

                }
                
                /**
                 * Si no has ingresado un DNI,
                 * te pide que lo hagas de nuevo
                 */
            } else {

                JOptionPane.showMessageDialog(null, 
                        "Por favor, ingrese un DNI",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
            }
        }
        
        
        
        /**
         * Si pulsamos el botón VOLVER,
         * volvemos a la ventana InicioAdmin
         */
        if (e.getSource()== prs.btnVolver){
            InicioAdmin init = new InicioAdmin();
            CtrlInicioAdmin ctrl = new CtrlInicioAdmin(init);
            ctrl.iniciar();
            init.setVisible(true);
            prs.setVisible(false);
        }
        
        
           
            
    }
    
}
