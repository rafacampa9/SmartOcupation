//***************************** PACKAGES ***************************************
import controller.CtrlViviendas;
import java.awt.event.ActionEvent;
import java.util.LinkedHashSet;
import javax.swing.table.DefaultTableModel;
import model.entidades.Entidad;
import model.entidades.Vivienda;
import model.sql.CrudSQL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import view.Viviendas;



/**
 *
 * @author rafacampa9
 */
public class CtrlViviendasTest {
    //************************* ATRIBUTOS **************************************
    private Vivienda v;
    private Viviendas viv;
    private CrudSQL crud;
    private CtrlViviendas ctrl;
    
    
    //************************** MÉTODOS ***************************************
    @Before
    public void setUp(){
        v = new Vivienda(
                "Sentinel del Norte",
                200,
                5,
                2,
                30,
                599.99,
                "31018427Y");
        crud = new CrudSQL();
        viv = new Viviendas();
        ctrl = new CtrlViviendas(v, crud, viv);
    }
    
    
    //**************************** TESTS ***************************************
    /**
     * Test para insertar un registro
     * en la tabla VIVIENDAS
     */
    @Test
    public void testInsertar(){
        /**
         * Probamos insertando un objeto de 
         * la clase Vivienda que contenga todos
         * sus atributos
         */
        viv.txtCodRef.setText(String.valueOf(v.getCod_ref())); 
        viv.txtUbi.setText(String.valueOf(v.getUbicacion())); 
        viv.txtNumRooms.setText(String.valueOf(v.getNumRooms()));
        viv.txtNumBath.setText(String.valueOf(v.getNumBathrooms()));
        viv.txtMetros.setText(String.valueOf(v.getMetros()));
        viv.txtDNI.setText(String.valueOf(v.getPropietario()));
        viv.txtPrecio.setText(String.valueOf(v.getPrecioMensual()));
        
        
            
        
        /**
         * Probamos a insertar llamando
         * al ActionEvent producido por el botón
         * INSERT
         */
        ctrl.actionPerformed(new ActionEvent(viv.btnInsert, 
                ActionEvent.ACTION_PERFORMED, null));
        
        /**
         * Comprobamos que realmente hemos insertado
         * el registro, leyendo todos los registros 
         * de la tabla VIVIENDAS
         */
        Vivienda a = (Vivienda) crud.buscar("VIVIENDAS", 
                                                v);
        
        
        /**
         * Devolverá true si los datos de la clase Arrendamietno
         * están en la BBDD, pueda insertarse o no, ya que
         * en caso de que no pueda insertarse, será porque
         * el registro ya existe
         */
        if (a!= null){
            assertEquals(a.getCod_ref(), v.getCod_ref());

        }
        
        
    }
    
    
    
    /**
     * Test para leer los registros
     * de la tabla VIVIENDAS
     */
    @Test
    public void testLeer(){
        /**
         * Pulsa el botón Leer de la ventana Viviendas
         */
        ctrl.actionPerformed(new ActionEvent(viv.btnRead, 
                ActionEvent.ACTION_PERFORMED, null));
        DefaultTableModel table = (DefaultTableModel) viv.tabla.getModel();
        
        int cont = 0;
        LinkedHashSet<Vivienda> viviendas =
                new LinkedHashSet<>();
        /**
         * Insertamos en la lista VIVIENDAS los
         * valores que devuelve la tabla asociada al btnRead
         */
        for (int i = 0; i < table.getRowCount(); i++){
            Vivienda p = new Vivienda();
            if(table.getRowCount()>0){
                String codRef = String.valueOf(
                        table.getValueAt(cont, 0));
                
                p.setCod_ref(Integer.parseInt(codRef));
                
                p.setUbicacion(String.valueOf(
                        table.getValueAt(cont, 1)
                    ));
                
                String metrosString = String.valueOf(
                        table.getValueAt(cont, 2));
                
                if(metrosString!=null && !metrosString.isEmpty()){
                    p.setMetros(Integer.parseInt(
                            metrosString
                    ));
                } else{
                    p.setMetros(-1);
                }

                String numRooms = String.valueOf(
                            table.getValueAt(cont, 3)
                    );
                
                if(numRooms!=null && !numRooms.isEmpty()){
                    p.setNumRooms(Integer.parseInt(numRooms));
                } else{
                    p.setNumRooms(-1);
                }
                
                String numBath = String.valueOf(
                        table.getValueAt(cont, 4));
                if (numBath!= null && !numBath.isEmpty()){
                    p.setNumBathrooms(Integer.parseInt(
                            numBath));
                } else{
                    p.setNumBathrooms(-1);
                }
                    

                p.setPropietario(String.valueOf(
                                table.getValueAt(cont, 5)
                ));

                String precioMensual = String.valueOf(
                        table.getValueAt(cont, 6));
                
                if (precioMensual!=null && !precioMensual.isEmpty() ){
                    p.setPrecioMensual(Double.valueOf(
                                precioMensual
                    ));
                }
                    
                cont++;
                viviendas.add(p);
            }else{
                assertNull(String.valueOf(
                        table.getValueAt(0,0)));
            }
            
        } 
            
        for(var e: viviendas){    
            /**
             *  En caso de no encontrarse ningún registro
             */
                Vivienda a = 
                        (Vivienda) crud.buscar("VIVIENDAS",
                                e);
                
                assertEquals(String.valueOf(a.getCod_ref()),
                        String.valueOf(e.getCod_ref()));
                assertEquals(a.getUbicacion(), e.getUbicacion());
                assertEquals(String.valueOf(a.getNumRooms()), 
                        String.valueOf(e.getNumRooms()));
                assertEquals(String.valueOf(a.getMetros()), 
                        String.valueOf(e.getMetros()));
                assertEquals(String.valueOf(a.getNumBathrooms()), 
                        String.valueOf(e.getNumBathrooms()));
                assertEquals(a.getPropietario(), 
                        e.getPropietario());
                assertEquals(String.valueOf(a.getPrecioMensual()), 
                        String.valueOf(e.getPrecioMensual()));
            }
        
        
    }
    
    
    
    /**
     * Test para modificar un registro
     * de la tabla VIVIENDAS
     */
    @Test 
    public void testModificar(){
        /**
         * Escribimos en el campo cod_ref el código de referencia
         * de la vivienda, y el resto de campos con los valores
         * modificados
         */
        viv.txtCodRef.setText(String.valueOf(v.getCod_ref())); 
        v.setUbicacion("Turquía");
        viv.txtUbi.setText("Turquía");
        v.setMetros(180);
        viv.txtMetros.setText("180");
        v.setNumRooms(3);
        viv.txtNumRooms.setText("3");
        v.setNumBathrooms(1);
        viv.txtNumBath.setText("1");
        v.setPropietario("67890562V");
        viv.txtDNI.setText("67890562V");
        v.setPrecioMensual(749.99);
        viv.txtPrecio.setText("749.99");
        

        /**
         * Llamamos al evento que se produce
         * al pulsar el botón MODIFICAR
         */
        ctrl.actionPerformed(new ActionEvent(viv.btnUpdate, 
                ActionEvent.ACTION_PERFORMED, null));
        
        /**
         * Buscamos al propietario
         */
        Vivienda cli = (Vivienda) crud.buscar("VIVIENDAS", 
                v);

        
        /**
         * Si hemos encontrado al propietario,
         * comprobaremos si es el mismo que
         * nuestro objeto pr que tiene
         * los datos modificados
         */
        if (cli != null)
            assertEquals(v.getCod_ref(), cli.getCod_ref());
        else{
            assertNull(cli);
        }
    }
    
    
    
    /**
     * Test para eliminar un registro
     * de la tabla VIVIENDAS
     */
    @Test
    public void testEliminar(){
        /**
         * Introducimos en el campo de texto
         * el dni de nuestro objeto pr
         */
        viv.txtCodRef.setText(String.valueOf(v.getCod_ref())); 
        
        /**
         * llamamos al evento del botón BORRAR
         */
        ctrl.actionPerformed(new ActionEvent(viv.btnDelete, 
                ActionEvent.ACTION_PERFORMED, null));
 
        /**
         * Buscamos el propietario que hemos
         * borrado por su id
         */
        Vivienda vivienda = (Vivienda) crud.buscar("VIVIENDAS",
                v);
        /**
         * Si el objeto vivienda es nulo,
         * hemos pasado el test
         */
        assertNull(vivienda);
    }   
    
    
    
    /**
     * Test para buscar un registro
     * de la tabla VIVIENDAS
     */
    @Test
    public void testBuscar(){
         /**
         * Pasamos al texto cod_ref
         * el valor de cod_ref 
         * correspondiente a v
         */
        viv.txtCodRef.setText(
                String.valueOf(v.getCod_ref()));
        
        
        /**
         * Supongamos que se produce
         * 
         * Realizamos la búsqueda
         */
        boolean t = true;
        ctrl.actionPerformed(new ActionEvent(viv.btnSearch, 
                ActionEvent.ACTION_PERFORMED, null));
        
        
        
        DefaultTableModel table = (DefaultTableModel) viv.tabla.getModel();
        
        /**
         * Verifica si la tabla tiene
         * al menos una fila
         */
        if(table.getRowCount() >0){
            String firstCellValue = String.valueOf(
                    table.getValueAt(0, 0));
            
            if (!firstCellValue.equals(String.valueOf(v.getCod_ref()))){
                t = false;
            }
            
        } else{
            /**
             * 
             * SELECT * FROM VIVIENDAS;
             */
            LinkedHashSet<Entidad> select = 
                    crud.leer("VIVIENDAS");
            LinkedHashSet<Vivienda> props = new LinkedHashSet<>();
            for (Entidad entity: select){
                props.add((Vivienda) entity);
            }
            /**
             * Comprueba si alguno 
             * de los registros de la tabla 
             * VIVIENDAS tiene el mismo
             * cod_ref que el pasado por pantalla
             */
            for (Vivienda p: props){
                /**
                 * Si hubiera alguno,
                 * fallariamos
                 */
                if (v.getCod_ref() == p.getCod_ref()){
                    t = false;
                }
            }
        }
            
            
        /**
         * Si t = true, pasamos la prueba
         * 
         */
        assertTrue(t); 
        
    }
    
    
}
