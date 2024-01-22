package controller;

//*************************** PACKAGES ****************************************
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.entidades.Cliente;
import model.entidades.Entidad;
import model.sql.CrudSQL;
import view.Clientes;
import view.InicioAdmin;

/**
 *
 * @author rafacampa9
 * 
 * Esta clase controla
 * la vista CLIENTES y
 * la integramos en
 * la clase Ctrl
 * 
 * Enlaza las clases del paquete
 * view Clientes e InicioAdmin
 * con las clases del paquete model
 * CrudSQL, Cliente y la interfaz
 * Entidad
 */


public class CtrlClientes implements ActionListener{
    
    //********************* ATRIBUTOS ******************************************
    private Cliente cliente;
    private final CrudSQL crud;
    private final Clientes cl;
    
    // ******************* CONSTRUCTOR *****************************************

    public CtrlClientes(Cliente cliente, CrudSQL crud, Clientes cl) 
    {
        this.cliente = cliente;
        this.crud = crud;
        this.cl = cl;
        this.cl.btnDelete.addActionListener(this);
        this.cl.btnRead.addActionListener(this);
        this.cl.btnInsert.addActionListener(this);
        this.cl.btnSearch.addActionListener(this);
        this.cl.btnUpdate.addActionListener(this);
        this.cl.txtEdad.addActionListener(this);
        this.cl.txtDNI.addActionListener(this);
        this.cl.txtEmpleo.addActionListener(this);
        this.cl.txtNombre.addActionListener(this);
        this.cl.btnBack.addActionListener(this);
    }
    
    
    
    //********************* MÉTODOS ********************************************

    
    /**
     * Para iniciar la vista
     * de la app en la clase
     * Ctrl
     * 
     * - TITULO 
     * - Posicionamiento en el centro de la pantalla
     * - No se puede modificar su tamaño
     * - Si se cierra, no cierra la app
     */
    public void iniciar(){
        cl.setTitle("Clientes");
        cl.setLocationRelativeTo(null);
        cl.setResizable(false);
    }

    
    /**
     * Limpiamos cada caja de texto
     * de la clase Clientes, pasándole
     * como parámetro a setText(null)
     */
    public void limpiar(){
        cl.txtDNI.setText(null);
        cl.txtNombre.setText(null);
        cl.txtEmpleo.setText(null);
        cl.txtEdad.setText(null);
    }
    
    
    /**
     * Limpiamos la tabla
     * de la ventana Clientes
     * pasándole al DefaultTableModel
     * declarado (igualado al modelo de la
     * tabla de la ventana) utilizando
     * su método setRowCount(n), donde
     * n = nº filas de la tabla
     */
    public void limpiaTabla(){
        DefaultTableModel table = (DefaultTableModel) cl.tabla.getModel();
        table.setRowCount(0);
    }
    
    
    /**
     * Método actionPerformed que sobrescribimos
     * por la interfaz ActionListener, y permite
     * estar a la escucha del evento ActionEvent
     * @param e, el cual se produce al pulsar
     * cualquier button, radioButton, JTextField...
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        /**
         * Si pulsamos el botón
         * INSERTAR de la ventana
         * CLIENTES
         *
         */
        if (e.getSource() == cl.btnInsert){
            /**
             * Asociamos el valor de cada
             * campo según su tipo: String o int
             * en este caso
             */
            String dni = cl.txtDNI.getText();
            String nombre = cl.txtNombre.getText();
            int edad;
            if (!cl.txtEdad.getText().isEmpty()){
                edad = Integer.parseInt(cl.txtEdad.getText());
            } else{
                edad = -1;
            }
            String empleo = cl.txtEmpleo.getText();
            
            /**
             * Si el campo de texto dni 
             * presenta un valor distinto de vacío
             *      Y
             * el campo de texto asociado a nombre
             * también presenta un valor distinto
             * a null
             */
            if (dni != null && !dni.isEmpty() && 
                    nombre != null && !nombre.isEmpty()) {
                /**
                 * Construimos el registro de la tabla
                 * CLIENTES mediante los valores proporcionados
                 */
                cliente.setDni(dni);
                cliente.setNombre(nombre);
                cliente.setEmpleo(empleo);
                if (edad != -1){
                    cliente.setEdad(edad);
                }

                /**
                 * Buscamos el objeto cliente
                 * con el método buscar de la
                 * clase CrudSQL. Recordemos
                 * que devuelve un objeto de la 
                 * interfaz Entidad, de la cual
                 * implementa Cliente. Para obtenerlo
                 * como tal, parseamos el objeto
                 * a Cliente
                 */
                Cliente existente = (Cliente) crud.buscar("CLIENTES", 
                        cliente);
                if (existente == null){
                    /**
                     * Si tras buscar el objeto, no lo encuentra
                     * porque es nuevo (ya que lo queremos insertar
                     * en la BBDD), realizamos la inserción con el método
                     * de CrudSQL crud.insertar(tabla, registro), donde
                     * en este caso la tabla es CLIENTES y el registro
                     * cliente
                     * 
                     * - Si devuelve true, muestra un mensaje de diálogo
                     *   donde vemos que el Registro se ha insertado
                     * 
                     * - Si devuelve false, muestra un mensaje de diálogo
                     *   donde vemos que ha habido un error
                     * 
                     * Limpiamos las tablas y los campos de texto
                     */
                    if(crud.insertar("CLIENTES", cliente)){
                        JOptionPane.showMessageDialog(null, 
                                "Registro insertado correctamente");
                        limpiar();
                        limpiaTabla();
             
                    }else{
                        JOptionPane.showMessageDialog(null, 
                                "Ha habido un error",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                        limpiar();
                        limpiaTabla();
                    }
                } else {
                    /**
                     * Si el registro ya existe
                     * en la BBDD
                     */
                    JOptionPane.showMessageDialog(
                            null, 
                            "Registro ya existente",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            
                /**
                 * Si dni es nulo o nombre es nulo
                 * 
                 * Para insertar un registro, es mínimo
                 * insertar tanto el nombre como el dni
                 */
            } else {
                
                JOptionPane.showMessageDialog(null, 
                        "Por favor, asegúrese de rellenar todos"
                                + " los campos",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);

            }
        }
        
        
        
        
        /**
         * Si pulsamos sobre LEER TODOS LOS REGISTROS
         * 
         * Limpia los datos de los campos y de la tabla
         * 
         * Declaramos un LinkedHashSet<Entidad> con valor 
         * igual al método leer de CrudSQL que devuelve
         * Select * From tabla, donde 
         * tabla = CLIENTES en este caso
         * 
         * 
         * Por tanto, declaramos un LinkedHashSet<Cliente> cls, ya 
         * que Cliente es la clase que hace referencia a 
         * la tabla CLIENTES. Después de esto, agregamos
         * el cliente devuelto por el método leer en cada uno 
         * de los registros que tiene cls a la lista de Cliente.
         * 
         * Por cada cliente, formamos una fila con un
         * array de la clase Object y agregamos la fila al modelo
         * de la tabla de la Clase Clientes del paquete view
         * 
         */
        if (e.getSource() == cl.btnRead){
            limpiar();
            limpiaTabla();
            LinkedHashSet<Entidad> cls = crud.leer("CLIENTES");
            LinkedHashSet<Cliente> clientes = new LinkedHashSet<>();
            for (Entidad cliente: cls){
                clientes.add((Cliente) cliente);
            }
            
            DefaultTableModel model = (DefaultTableModel) cl.tabla.getModel(); 

            for (Cliente cliente: clientes){
                Object[] fila = {cliente.getDni(), cliente.getNombre(),
                                cliente.getEdad(), cliente.getEmpleo()};
                // Agregar la fila al modelo
                model.addRow(fila); 
            }
        }
        
        
        /**
         * Si pulsamos el botón MODIFICAR 
         * para modificar un registro
         * 
         * Los campos mínimos a rellenar son 
         * DNI y NOMBRE
         * 
         * 
         */
        if (e.getSource() == cl.btnUpdate){
            String dni = cl.txtDNI.getText();
            String nombre = cl.txtNombre.getText();
            int edad;
            /**
             * Si el campo de texto edad es distinto
             * de vacío, obtenemos la edad
             * 
             * Si no, edad = -1
             */
            if (!cl.txtEdad.getText().isEmpty()){
                edad = Integer.parseInt(cl.txtEdad.getText());
            } else{
                edad = -1;
            }
                
            String empleo = cl.txtEmpleo.getText();
            
            /**
             * De nuevo, los campos a rellenar
             * necesarios serán dni y nombre
             */
            if (nombre != null && !nombre.isEmpty()
                    && dni!=null && !dni.isEmpty()) {
                cliente.setDni(dni);
                cliente.setNombre(nombre);
                cliente.setEmpleo(empleo);
                /**
                 * Si edad es distinto de -1,
                 * le introducimos la edad
                 * a cliente
                 */
                if (edad != -1){
                    cliente.setEdad(edad);
                } 
            
                /**
                 * Declaramos un cliente, que será 
                 * el encargado de buscar el cliente
                 * pasado por parámetro (RECORDEMOS
                 * QUE crud.buscar(tabla, registro) busca
                 * el registro en la tabla según el 
                 * id del registro (en este caso,
                 * cliente.dni
                 */
                Cliente existente = 
                        (Cliente)crud.buscar("CLIENTES", cliente);
                /**
                 * En caso de que la consulta
                 * nos devuleva un registro
                 */
                if (existente!=null){
                    /**
                     * Si modificamos (devuelve true
                     * si se realiza la modificación),
                     * devolverá un mensaje de diálogo
                     * que nos muestra que el registro
                     * se ha insertado
                     * 
                     * Si no, nos dice que "Ha habido un
                     * error"
                     */
                    if (crud.modificar("CLIENTES", cliente)){
                        JOptionPane.showMessageDialog(null, 
                                "Registro modificado correctamente.");
                        limpiar();
                        limpiaTabla();
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "Ha habido un error",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                        limpiar();
                        limpiaTabla();
                    }
                    /**
                     * En caso de que no exista el
                     * registro
                     */
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "No existe este registro a modificar",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    limpiar();
                    limpiaTabla();
                }
            /**
             * Si no se han rellenado los campos
             * dni y nombre nos muestra un mensaje
             * de diálogo diciendo que tenemos que
             * rellenar todos los campos necesarios
             */   
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Por favor, asegúrese de rellenar "
                                + "los campos DNI y NOMBRE",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                limpiar();
                limpiaTabla();
            }
        }
        
        
        /**
         * Si pulsamos BORRAR
         * 
         * DNI debe ser distinto de vacío
         */
        if (e.getSource() == cl.btnDelete){
            String dni = cl.txtDNI.getText();
            
            /**
             * Si el campo dni
             * ha sido escrito
             */
            if (dni != null && !dni.isEmpty()){
                cliente.setDni(dni);
                /**
                 * Si el método eliminar
                 * al que le pasamos por parámetro
                 * el objeto cliente, puede eliminarlo
                 * de la tabla CLIENTES, retornará true
                 */
                if(crud.eliminar("CLIENTES", cliente)){
                    /**
                     * Ventana de diálogo
                     */
                    JOptionPane.showMessageDialog(null, 
                            "Registro eliminado correctamente");
                    
                    
                
                    /**
                     * Si no se ha realizado la modificación
                     */
                } else {
                    /**
                     * Ventana de diálogo
                     */
                    JOptionPane.showMessageDialog(null, 
                            "No se ha podido modificar el"
                                    + " registro",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    
                }
                
            /**
             * Si no has rellenado el campo dni, no puede
             * buscar el registro para eliminarlo
             */
            } else{
                JOptionPane.showMessageDialog(null, "Por favor, "
                        + "escriba el DNI del usuario que desea eliminar",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                
            }
            /**
             * limpiamos los campos y la tabla
             */
            limpiar();
            limpiaTabla();

        }
        
        
        
        /**
         * Si pulsamos BUSCAR
         * 
         * DNI != null
         */
        if (e.getSource() == cl.btnSearch){
            /**
             * en primer lugar, limpiamos 
             * la tabla 
             */
            limpiaTabla();
            String dni = cl.txtDNI.getText();
            /**
             * Si el dni no es 
             * vacío
             */
            if (dni != null && !dni.isEmpty() ){
                cliente.setDni(dni);
                /**
                 * Buscamos el cliente con el método
                 * buscar de la tabla CLIENTES
                 * (comprobamos si existe su DNI
                 * en el registro de la BBDD)
                 * 
                 * 
                 */
                cliente = (Cliente) crud.buscar("CLIENTES", 
                                            cliente); 

                /**
                 * Si existe, lo añadimos a la tabla
                 * (que previamente está vacía)
                 */
                if (cliente != null) {
                    DefaultTableModel model =
                            (DefaultTableModel) cl.tabla.getModel();


                    // Agregar el resultado a la tabla
                    Object [] resultado = {cliente.getDni(),
                                           cliente.getNombre(),
                                           cliente.getEdad(), 
                                           cliente.getEmpleo()};
                    model.addRow(resultado);
                    
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "No se encontraron resultados");
                }
            } else {

                JOptionPane.showMessageDialog(null, 
                        "Por favor, ingrese un DNI",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
        
        /**
         * Si pulsamos VOLVER,
         * regresamos a la ventana
         * anterior
         */
        if (e.getSource() == cl.btnBack){
            InicioAdmin init = new InicioAdmin();
            CtrlInicioAdmin ctrl = new CtrlInicioAdmin(init);
            ctrl.iniciar();
            init.setVisible(true);
            cl.setVisible(false);
        }

    }
    
    
}
