package controller;


//****************************** PACKAGES **************************************
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.entidades.Arrendamiento;
import model.entidades.Entidad;
import model.sql.CrudSQL;
import view.Alquiler;
import view.InicioAdmin;

/**
 *
 * @author rafacampa9
 * 
 * Esta clase relaciona las siguientes
 * clases de los siguientes paquetes:
 * - model.entidades
 *  > Arrendamiento
 *  > Entidad
 * 
 * - model.sql
 *  > CrudSQL
 * 
 * - view
 *  > Alquiler
 *  > InicioAdmin
 */
public class CtrlArrendamiento implements ActionListener{
        
    // ****************************ATRIBUTOS************************************
    private Arrendamiento ar;
    private final CrudSQL crud;
    private final Alquiler alq;
    
    
    //***************************** CONSTRUCTORES ******************************

    public CtrlArrendamiento(Arrendamiento ar, CrudSQL crud, Alquiler alq) 
    {
        this.ar = ar;
        this.crud = crud;
        this.alq = alq;
        /**
         * Agregamos los ActionListener
         */
        this.alq.btnInsert.addActionListener(this);
        this.alq.btnDelete.addActionListener(this);
        this.alq.btnRead.addActionListener(this);
        this.alq.btnUpdate.addActionListener(this);
        this.alq.btnBack.addActionListener(this);
        this.alq.btnSearch.addActionListener(this);
        this.alq.txtDniCliente.addActionListener(this);
        this.alq.txtIdVivienda.addActionListener(this);
        this.alq.txtNumExp.addActionListener(this);
        /**
         * Para fecha es PropertyChangeListener
         * @param PropertyChangeEvent e
         */
        this.alq.fechaEntrada.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                dateChooserPropertyChange(e);
            }
        });
        this.alq.fechaSalida.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                dateChooserPropertyChange(e);
            }
        });
        this.alq.cbPagado.addActionListener(this);
    }

    /**
     * La clase JDateChooser es importada
     * del paquete calendar 
     * "com.toedter.calendar.JDateChooser"
     * y está asociada al evento
     * PropertyChangeEvent
     * @param e 
     */
    private void dateChooserPropertyChange (PropertyChangeEvent e)
    {
        if ("date".equals(e.getPropertyName())) {
            //Verificamos si ar está iniciada
            if (ar != null) { 
                /**
                 * Llamamos al setFechaEntrada del atributo Arrendamiento ar
                 * y le pasamos la fecha de entrada del Alquiler
                 * 
                 * Lo mismo más abajo con alq.fechaSalida
                 */
                if (e.getSource() == alq.fechaEntrada) {
                    ar.setFechaEntrada(alq.fechaEntrada.getDate());
                } else if (e.getSource() == alq.fechaSalida) {
                    ar.setFechaSalida(alq.fechaSalida.getDate());
                }
            }
        }
    }
    
    /**
     * Para iniciar la ventana
     * de la clase Alquiler
     * a través de este método de 
     * esta clase
     * 
     * - TITLE: Arrendamientos
     * - LOCATION: CENTER
     * - NO se cierra la app al cerrar la ventana
     */
    public void iniciar(){
        alq.setTitle("Arrendamientos");
        alq.setLocationRelativeTo(null);
        alq.setResizable(false);
    }
    
    /**
     * Vaciamos los campos de texto
     * y de fecha de la ventana
     * alq menos la tabla
     */
    
    public void limpiar(){
        
        alq.fechaEntrada.setDate(null);
        alq.fechaSalida.setDate(null);
        alq.txtDniCliente.setText(null);
        alq.txtIdVivienda.setText(null);
        alq.txtNumExp.setText(null);
    }
    
    /**
     * Vaciamos los registros de la tabla
     */
    public void limpiarTabla(){
        DefaultTableModel table = (DefaultTableModel) alq.tabla.getModel();
        table.setRowCount(0);
    }
    
    
    /**
     * Evento sobrescrito de la interfaz ActionListener
     * que atiende a la llamada del evento producido
     * por el objeto de la clase ActionEvent
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    
        /**
         * Declaramos las variables que harán
         * referencia al num_exp del Arrendamiento
         * (id de la tabla ARRENDAMIENTOS) y idVivienda
         * (id de la tabla VIVIENDAS que es clave
         * foránea en la tabla ARRENDAMIENTOS)
         */
        int numExp, idVivienda;
        
        /**
         * Si pulsamos volver, volvemos a la
         * ventana de InicioAdmin
         */
        if (e.getSource() == alq.btnBack){
            InicioAdmin init = new InicioAdmin();
            CtrlInicioAdmin ctrl = new CtrlInicioAdmin(init);
            ctrl.iniciar();
            init.setVisible(true);
            alq.setVisible(false);
        }
        
        
        
        
        /**
         * Si pulsamos INSERTAR
         */
        if (e.getSource() == alq.btnInsert){   
            /**
             * Cuando el la caja del texto que contiene el
             * número de referencia del alquiler no es nulo
             * 
             * 
             * Si es nulo, id = -1
             */
            if (!alq.txtIdVivienda.getText().isEmpty())
                idVivienda = Integer.parseInt(alq.txtIdVivienda.getText());
            else
                idVivienda = -1;
            Date fechaEntrada = alq.fechaEntrada.getDate();
            Date fechaSalida = alq.fechaSalida.getDate();
            String dniCliente =alq.txtDniCliente.getText();
            
            String pagado = (String) alq.cbPagado.getSelectedItem();

            // Validar los campos según tus requisitos
            /**
             * En inserción, no puede haber un campo
             * vacío
             */
            if (fechaEntrada == null || fechaSalida == null ||
                dniCliente == null || idVivienda == -1 || pagado == null) {
                JOptionPane.showMessageDialog(null, 
                        "Por favor, complete todos los campos",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                
            /**
             * Si es vacío, construimos nuestro objeto
             * Arrendamiento ar
             */    
            } else{
                ar.setFechaEntrada(fechaEntrada);
                ar.setFechaSalida(fechaSalida);
                ar.setCliente(dniCliente);
                ar.setIdVivienda(idVivienda);
                ar.setPagado(pagado.equals("Sí"));
                
                /**
                 * Parseamos a Arrendamiento el resultado de la consulta
                 * buscar cuyo parámetros hacen referencia a la tabla 
                 * "ARRENDAMIENTOS" y al objeto Arrendamiento ar para
                 * ver si se encuentra dentro de nuestra tabla
                 */
                Arrendamiento existente = 
                        (Arrendamiento) crud.buscar(
                                                "ARRENDAMIENTOS",
                                                ar
                        );
                /**
                 * Si no existe el registro
                 */
                if (existente == null){
                    
                    /**
                     * Si se produce la inserción, devuelve
                     * true y nos muestra un mensaje de diálogo
                     * indicándonos la correcta inserción
                     */
                    if (crud.insertar("ARRENDAMIENTOS", ar)){
                        JOptionPane.showMessageDialog(null, 
                            "Registro insertado correctamente");
                        limpiar();
    
                    } else {
                        /**
                         * Si no se produce la inserción, devuelve 
                         * false y nos lo muestra en un mensaje de 
                         * diálogo
                         */
                        JOptionPane.showMessageDialog(null, 
                            "Lo sentimos, "
                                    + "no se ha podido insertar "
                                    + "el registro.", 
                            "ERROR", 
                            JOptionPane.ERROR_MESSAGE);
                        limpiar();
                        

                    }
                    
                    
                } else {
                    /**
                     * Si existe, no podemos insertarlo de 
                     * nuevo. Si quisieramos modificarlo, tendríamos
                     * que pulsar en MODIFICAR.
                     */
                    JOptionPane.showMessageDialog(
                            null, 
                            "Registro ya existente",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                
            }
 
        }
        
        
        
        
        
        /**
         * Si pulsamos LEER
         */
        if (e.getSource()==alq.btnRead){
            limpiarTabla();
            /**
             * Declaramos un LinkedHashSet con el método leer 
             * de CrudSQL crud al que le pasamos la tabla
             * ARRENDAMIENTOS
             */
            LinkedHashSet<Entidad> cls = 
                    crud.leer("ARRENDAMIENTOS");
            
            LinkedHashSet<Arrendamiento> arrendamientos = 
                    new LinkedHashSet<>();
            
            /**
             * Iteramos sobre la consulta SELECT que ya la tenemos
             * en el LinkedHashSet<Entidad> y lo agregamos al
             * LinkedHashSet<Arrendamiento> parseándolo
             */
            for (Entidad arr: cls){
                arrendamientos.add((Arrendamiento) arr);
            }
            
            DefaultTableModel model = (DefaultTableModel) alq.tabla.getModel(); 
            
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
            
            /**
             * Agregamos cada fila al
             * modelo de la tabla
             */
            for (Arrendamiento arr: arrendamientos){
                Object[] fila = {
                    arr.getNumExp(), formato.format(arr.getFechaEntrada()),
                    formato.format(arr.getFechaSalida()), arr.getCliente(),
                    arr.getIdVivienda(), arr.isPagado()
                };
                model.addRow(fila); 
            }
        }
        
        
        
        
        /**
         * Si pulsamos  MODIFICAR
         */
        if (e.getSource() == alq.btnUpdate){
            limpiarTabla();
            /**
             * Si El número de expediente del alquiler no es vacío,
             * si lo es, le pasamos -1
             * 
             * Lo mismo con el idVivienda
             */
            if (!alq.txtNumExp.getText().isEmpty())
                numExp = Integer.parseInt(alq.txtNumExp.getText());
            else
                numExp = -1;
            if (!alq.txtIdVivienda.getText().isEmpty())
                idVivienda = Integer.parseInt(alq.txtIdVivienda.getText());
            else
                idVivienda = -1;
            Date fechaEntrada = alq.fechaEntrada.getDate();
            Date fechaSalida = alq.fechaSalida.getDate();
            String dniCliente =alq.txtDniCliente.getText();
            
            String pagado = (String) alq.cbPagado.getSelectedItem();
            
            /**
             * Si numExp != -1, idVivienda != 1, y los demás campos 
             * son distintos de vacío
             */
            if (numExp != -1 
                && idVivienda != -1 
                    && dniCliente != null 
                    && fechaEntrada != null 
                    && fechaSalida!=null) {
                ar.setNumExp(numExp);
                ar.setFechaEntrada(fechaEntrada);
                ar.setFechaSalida(fechaSalida);
                ar.setCliente(dniCliente);
                ar.setIdVivienda(idVivienda);
                ar.setPagado(pagado.equals("Sí"));
                
                //Parseamos la búsqueda del registro como Arrendamiento
                Arrendamiento existente = 
                        (Arrendamiento) crud.buscar(
                                            "ARRENDAMIENTOS", 
                                            ar
                                        );
                
                /**
                 * Si hemos encontrado el registro que 
                 * deseamos modificar con los nuevos
                 * datos
                 */
                if (existente != null){
                    /**
                     * Llamamos al método modificar de CrudSQL y
                     * si nos devuelve true, nos indica con un
                     * mensaje de dialogo que se ha modificado
                     */
                    if (crud.modificar("ARRENDAMIENTOS", ar)){
                        JOptionPane.showMessageDialog(null, 
                                "Registro modificado correctamente.");
                        limpiar();
                    /**
                     * Si no no se puede modificar pese a encontrar
                     * el registro (una excepción) nos lo notifica
                     */
                    } else {
                        JOptionPane.showMessageDialog(null, 
                            "Lo sentimos, no se ha podido insertar"
                                    + " el registro.",
                                  "ERROR",
                                    JOptionPane.ERROR_MESSAGE
                        );
                        limpiar();

                    }
                    
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Lo sentimos, el registro que desea modificar"
                                    + " no existe.",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE
                    );
                    limpiar();
                }
   
            /**
             * Si no has introducido el id de la tabla
             * num_exp
             */    
            } else {
                
                JOptionPane.showMessageDialog(null, 
                        "Por favor, introduzca todos los datos.",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);

            }
        }
        
        
        
        
        /**
         * Si pulsamos BORRAR
         */
        if (e.getSource() == alq.btnDelete){
            limpiarTabla();
            /**
             * Solo nos interesa el id de la tabla
             * num_exp
             */
            if (!alq.txtNumExp.getText().isEmpty())
                numExp = Integer.parseInt(alq.txtNumExp.getText());
             else
                numExp = -1;
            
            /**
             * Si numExp != -1, le pasamos este valor
             * a nuestro objeto Arrendamiento ar
             */
            if (numExp != -1){
                ar.setNumExp(numExp);
                
                /**
                 * Si eliminar devuelve true, se ha realizado
                 * el borrado del registro correctamente y lo
                 * indicamos con un mensaje de diálogo
                 */
                if(crud.eliminar("ARRENDAMIENTOS", ar)){
                    JOptionPane.showMessageDialog(null, 
                            "Registro eliminado correctamente");

                
                /**
                 * Si eliminar devuelve false, es que no se ha podido
                 * rellenar el registro, posiblemente, porque
                 * el registro cuyo num_exp tiene ar no existe en la 
                 * BBDD
                 */
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "Lo sentimos, no se ha podido eliminar"
                                    + "el registro. Compruebe los"
                                    + " valores a introducir.",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);

                }
                /**
                 * Si no has escrito el nº expediente
                 */
            } else{
                JOptionPane.showMessageDialog(null,
                        "Por favor, escriba el número de "
                                + "expediente del alquiler que "
                                + "desea eliminar",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        
        
        
        /**
         * Si pulsamos BUSCAR
         */
        if (e.getSource() == alq.btnSearch){
            limpiarTabla();
            /**
             * De nuevo, solo nos interesa el
             * id de la tabla ARRENDAMIENTOS
             * num_exp
             */
            if (!alq.txtNumExp.getText().isEmpty())
                numExp = Integer.parseInt(alq.txtNumExp.getText());
             else
                numExp = -1;
            
            /**
             * Si hemos escrito el num_exp
             */
            if (numExp != -1){
                ar.setNumExp(numExp);
                
                ar = (Arrendamiento) crud.buscar("ARRENDAMIENTOS",
                                                ar); 
                
                /**
                 * Si hemos encontrado el registro,
                 * rellenamos el modelo de la tabla
                 * con todos los datos del registro
                 */
                if (ar != null) {
                    DefaultTableModel model = 
                            (DefaultTableModel) alq.tabla.getModel();
                    SimpleDateFormat formato = 
                            new SimpleDateFormat("yyyy/MM/dd");


                    // Agregar el resultado a la tabla
                    Object [] resultado = {
                                    ar.getNumExp(), 
                                    formato.format(ar.getFechaEntrada()),
                                    formato.format(ar.getFechaSalida()), 
                                    ar.getCliente(),
                                    ar.getIdVivienda(), 
                                    ar.isPagado()
                                };
                    model.addRow(resultado);
                    
                /**
                 * Si no nos encuentra el registro en la tabla,
                 * nos lo muestra con un mensaje de diálogo
                 */
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No se encontraron resultados");
                    /**
                     * Limpiamos los campos y la 
                     * tabla
                     */
                    limpiar();
                    limpiarTabla();

                }
                
             /**
              * Si no hemos ingresado
              * el nº de expediente
              */
            } else {

                JOptionPane.showMessageDialog(null, 
                        "Por favor, ingrese el número de"
                                + " expediente del alquiler que"
                                + " desea buscar.",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                /**
                 * Limpiamos los campos y la 
                 * tabla
                 */
                limpiar();
                limpiarTabla();
            }

        }

        
    }
}

    

