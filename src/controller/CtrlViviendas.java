//******************************* PACKAGES *************************************
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.entidades.Entidad;
import model.entidades.Vivienda;
import model.sql.CrudSQL;
import view.InicioAdmin;
import view.Viviendas;

/**
 *
 * @author rafacampa9
 * 
 * 
 * Esta clase engloba objetos de las 
 * clases del paquete model:
 * - Entidad (interfaz)
 * - Vivienda
 * - CludSQL (model.sql)
 * 
 * Del paquete view:
 * - InicioAdmin
 * - Viviendas
 */
public class CtrlViviendas implements ActionListener{
    
    //************************** ATRIBUTOS ************************************
    private Vivienda vivienda;
    private final CrudSQL crud;
    private final Viviendas v;
    
    //************************** CONSTRUCTOR ***********************************

    public CtrlViviendas(Vivienda vivienda, 
                        CrudSQL crud, 
                        Viviendas v) {
        this.vivienda = vivienda;
        this.crud = crud;
        this.v = v;
        this.v.btnBack.addActionListener(this);
        this.v.btnDelete.addActionListener(this);
        this.v.btnInsert.addActionListener(this);
        this.v.btnRead.addActionListener(this);
        this.v.btnSearch.addActionListener(this);
        this.v.btnUpdate.addActionListener(this);
        this.v.txtCodRef.addActionListener(this);
        this.v.txtDNI.addActionListener(this);
        this.v.txtNumBath.addActionListener(this);
        this.v.txtNumRooms.addActionListener(this);
        this.v.txtPrecio.addActionListener(this);
        this.v.txtUbi.addActionListener(this);
        this.v.txtMetros.addActionListener(this);
    }
    
    
    
    /**
     * Para iniciar la clase 
     * Viviendas del paquete view
     */
    public void iniciar(){
        v.setTitle("VIVIENDAS");
        v.setLocationRelativeTo(null);
        v.setResizable(false);
    }
    
    
    /**
     * Limpiamos el contenido
     * de los campos de texto
     */
    public void limpiar(){
        v.txtCodRef.setText(null);
        v.txtDNI.setText(null);
        v.txtNumBath.setText(null);
        v.txtNumRooms.setText(null);
        v.txtPrecio.setText(null);
        v.txtUbi.setText(null);
        v.txtMetros.setText(null);
    }
    
    
    /**
     * Vaciamos la tabla de filas
     */
    public void limpiarTabla(){
        DefaultTableModel table = (DefaultTableModel) v.tabla.getModel();
        table.setRowCount(0);
    }
    
    
    
    
    /**
     * Método que implementamos de ActionListener
     * para estar a la esucha del ActionEvent
     * @param e 
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * Declaramos las variables que nos 
         * interesan
         */
        int numRooms, numBath, metros, codRef;
        Double precio;
        
        
        /**
         * Si pulsamos el botón VOLVER
         * 
         * Volvemos a la ventana InicioAdmin
         */
        if (e.getSource() == v.btnBack){
            InicioAdmin init = new InicioAdmin();
            CtrlInicioAdmin ctrl = new CtrlInicioAdmin(init);
            ctrl.iniciar();
            init.setVisible(true);
            v.setVisible(false);
        }
        
        
        
        /**
         * Si pulsamos sobre el botón
         * INSERTAR
         * 
         * No permite insertar el cod_ref,
         * nos lo genera la propia tabla
         * de la base de datos
         */
        if (e.getSource() == v.btnInsert){
            String dni = v.txtDNI.getText();
            String ubicacion = v.txtUbi.getText();
            
            /**
             * Si alguno de los campos que no son
             * imprescindibles están vacíos o no
             */
            if (!v.txtMetros.getText().isEmpty())
                metros = Integer.parseInt(v.txtMetros.getText());
            else
                metros = -1;
            if (!v.txtNumRooms.getText().isEmpty()){
                numRooms = Integer.parseInt(v.txtNumRooms.getText());
            } else{
                numRooms = -1;
            }
            if (!v.txtNumBath.getText().isEmpty()){
                numBath = Integer.parseInt(v.txtNumBath.getText());
            }else {
                numBath = -1;
            }
            if (!v.txtPrecio.getText().isEmpty()){
                precio = Double.valueOf(v.txtPrecio.getText());
            } else {
                precio = -1.0;
            }
            
            /**
             * Añadimos todos los valores que tengamos en los campos de texto
             * al objeto vivienda mediante sus métodos set
             */
            vivienda.setPropietario(dni);
            vivienda.setUbicacion(ubicacion);
            if (metros != -1)
                vivienda.setMetros(metros);
            if (numBath != -1){
                vivienda.setNumBathrooms(numBath);
            }
            if (numRooms != -1)
                vivienda.setNumRooms(numRooms);
            if (precio != -1.0)
                vivienda.setPrecioMensual(precio);
            
            /**
             * Buscamos el objeto que hemos construido
             * en la lista de registros para ver si estaba
             * ya anteriormente
             */
            Vivienda existente = (Vivienda) crud.buscar("VIVIENDAS",
                                                    vivienda);
            
            /**
             * Si no existía el registro, si se produce
             * la inserción correctamente (el método insertar
             * devuelve true), nos lo indica con un mensaje
             * de diálogo
             */
            if (existente == null){
                if (crud.insertar("VIVIENDAS", vivienda)){
                    JOptionPane.showMessageDialog(null,
                            "Registro insertado correctamente");

                /**
                 * Si no, nos dice que el registro no ha podido
                 * insertare
                 */
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "No se ha podido insertar "
                                    + "el registro",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
                
            /**
             * Si ya existe el registro, 
             * nos dará error
             */
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Registro ya existente",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE
                );
            }
                
                

        } 
        
        
        
        /**
         * Si pulsamos el botón LEER
         */
        if (e.getSource() == v.btnRead){
            //Limpiamos la tabla
            limpiarTabla();
            /**
             * SELECT * FROM VIVIENDAS;
             */
            LinkedHashSet<Entidad> cls = crud.leer("VIVIENDAS");
            LinkedHashSet<Vivienda> viviendas = new LinkedHashSet<>();
            /**
             * Agregamos cada registro que haya devuelto
             * la consulta en un objeto Vivienda
             */
            for (Entidad viv: cls){
                viviendas.add((Vivienda) viv);
            }
            
            /**
             * Obtenemos el modelo de la tabla
             * y agregamos los registros como
             * filas
             */
            DefaultTableModel model = (DefaultTableModel) v.tabla.getModel(); 

            for (Vivienda viv: viviendas){
                Object[] fila = {viv.getCod_ref(), viv.getUbicacion(),
                                viv.getMetros(), viv.getNumRooms(),
                                viv.getNumBathrooms(),viv.getPropietario(),
                                viv.getPrecioMensual()};
                model.addRow(fila); 
            }
        }
        
        
        
        /**
         * Si pulsamos el botón BORRAR
         */
        if (e.getSource() == v.btnDelete){
            /**
             * Si el campo de texto de cod_ref no es
             * nulo, almacenamos su valor en codRef
             * 
             * Sino, codRef = .1
             */
            if (!v.txtCodRef.getText().isEmpty())
                codRef = Integer.parseInt(v.txtCodRef.getText());
             else
                codRef = -1;
            
            /**
             * Si codRef no es -1
             */
            if (codRef != -1){
                /**
                 * Le agregamos a vivienda el valor
                 * de codRef en su atributo cod_ref
                 */
                vivienda.setCod_ref(codRef);
                
                /**
                 * Si el método eliminar para la vivienda
                 * devuelve true, nos lo indicará con un
                 * mensaje de diálogo, sino lo mismo
                 */
                if(crud.eliminar("VIVIENDAS", vivienda)){
                    JOptionPane.showMessageDialog(null,
                            "Registro eliminado correctamente");

                
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "No se ha podido eliminar"
                                    + " el registro",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);

                }
                
                /**
                 * Si no has escrito el código de
                 * referencia de la vivienda, te
                 * pedirá que lo ingreses
                 */
            } else{
                JOptionPane.showMessageDialog(null, 
                        "Por favor, ingrese el número de referencia "
                                + "de la vivienda que desea eliminar",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);

            }
            
         
        }
        
        
        
        /**
         * Si pulsamos MODIFICAR
         */
        if (e.getSource() == v.btnUpdate){

            /**
             * Comprobamos qué campos de texto están
             * vacíos y cuáles no
             */
            if (!v.txtCodRef.getText().isEmpty())
                codRef = Integer.parseInt(v.txtCodRef.getText());
            else
                codRef = -1;
            
            if (!v.txtMetros.getText().isEmpty())
                metros = Integer.parseInt(v.txtMetros.getText());
            else
                metros = -1;
            
            if (!v.txtNumBath.getText().isEmpty())
                numBath = Integer.parseInt(v.txtNumBath.getText());
            else
                numBath = -1;
            
            if (!v.txtNumRooms.getText().isEmpty())
                numRooms = Integer.parseInt(v.txtNumRooms.getText());
            else
                numRooms = -1;
            
            if (!v.txtPrecio.getText().isEmpty())
                precio = Double.valueOf(v.txtPrecio.getText());
            else
                precio = -1.0;
            
            String ubicacion = v.txtUbi.getText();
            String propietario = v.txtDNI.getText();
            
            /**
             * Si el código de referencia no es vacío,
             * modificamos solo los valores que 
             * introduzcamos en los campos de texto
             * (si no introducimos nada en un campo
             * de texto, se mantendrá el anterior si 
             * existe)
             */
            if (codRef != -1) {
                vivienda.setCod_ref(codRef);
                if (!ubicacion.isEmpty())
                    vivienda.setUbicacion(ubicacion);
                if (!propietario.isEmpty())
                    vivienda.setPropietario(propietario);
                if (metros != -1)
                    vivienda.setMetros(metros);
                if (numBath != -1)
                    vivienda.setNumBathrooms(numBath);
                if (numRooms != -1)
                    vivienda.setNumRooms(numRooms);
                if (precio != -1.0)
                    vivienda.setPrecioMensual(precio);
                
                /**
                 * Buscamos la vivienda según el cod_ref pasado
                 */
                Vivienda existente = (Vivienda) crud.buscar("VIVIENDAS",
                        vivienda);
                
                /**
                 * Si existe, modificamos
                 * el registro con el método modificar(tabla,registro)
                 */
                if (existente!=null){
                    if (crud.modificar("VIVIENDAS", vivienda)){
                        JOptionPane.showMessageDialog(null, 
                                "Registro modificado correctamente.");
                        
                    /**
                     * Si modificar devuelve false, nos dirá
                     * que no ha podido ser modificado
                     */
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "No se ha podido modificar el registro.",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);

                    }
                    
                /**
                 * Si la consulta no devuelve nada
                 */
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "No existe el registro",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);

                }
   
            /**
             * Si el campo de texto que del
             * código de referencia es vacío
             */    
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Por favor, introduzca el código de referencia",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
            }
            
           
        }
        
        
        
        /**
         * Si pulsamos sobre el botón BUSCAR
         */
        if (e.getSource() == v.btnSearch){
            limpiarTabla();
            /**
             * Si la caja de texto que 
             * corresponde a cod_ref 
             * no está vacía, le pasamos
             * ese valor. Sino, -1
             */
            if (!v.txtCodRef.getText().isEmpty())
                codRef = Integer.parseInt(v.txtCodRef.getText());
            else
                codRef = -1;
            
            
            /**
             * Si es != -1
             */
            if (codRef != -1){
                /**
                 * le pasamos al objeto vivienda el valor
                 * codRef para su atributo cod_ref
                 */
                vivienda.setCod_ref(codRef);
                
                /**
                 * buscamos el registro cuyo cod_ref
                 * es el mismo que vivienda.getCod_Ref()
                 */
                vivienda = (Vivienda) crud.buscar("VIVIENDAS",
                        vivienda); 

                
                /**
                 * Si el resultado no es nulo, obtenemos el modelo
                 * de la tabla, y construimos cada fila para agregarla
                 */
                if (vivienda != null) {
                    DefaultTableModel model = 
                            (DefaultTableModel) v.tabla.getModel();
                    

                    Object [] resultado = {
                                           vivienda.getCod_ref(),
                                           vivienda.getUbicacion(),
                                           vivienda.getMetros(),
                                           vivienda.getNumRooms(),
                                           vivienda.getNumBathrooms(), 
                                           vivienda.getPropietario(),
                                           vivienda.getPrecioMensual()
                                        };
                    model.addRow(resultado);

                /**
                 * Si no existe el registro
                 */
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "No se encontraron resultados");
                }
            } else {
                /**
                 * Si no ingresas el código de referencia
                 */
                JOptionPane.showMessageDialog(null, 
                        "Por favor, ingrese el código de referencia",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
            }
            
            
        }
        
        
    }
        
}
    
    

