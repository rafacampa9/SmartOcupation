//************************** PACKAGES ******************************************
import controller.CtrlClientes;
import java.awt.event.ActionEvent;
import java.util.LinkedHashSet;
import javax.swing.table.DefaultTableModel;
import model.entidades.Cliente;
import model.entidades.Entidad;
import model.sql.CrudSQL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import view.Clientes;



/**
 *
 * @author rafacampa9
 */
public class CtrlClientesTest {
    //*********************** ATRIBUTOS ****************************************
    private Cliente cl;
    private CrudSQL crud;
    private Clientes c;
    private CtrlClientes ctrl;
    
    //************************ MÉTODOS *****************************************
    
    /**
     * Antes de realizar los test,
     * instanciamos los atributos
     * mediante sus constructores 
     * vacíos (cl, crud y c) y los
     * pasamos como parámetro del
     * constructor ctrl
     * 
     * También inicializamos el
     * dni del objeto Cliente 
     * ,el nombre, su edad y su empleo
     * con los valores aportados
     */
    @Before
    public void setUp() {
        cl = new Cliente("20202020K","Pelé", 
                "Futbolista", 82);
        crud = new CrudSQL();
        c = new Clientes();
        ctrl = new CtrlClientes(cl, crud, c);
        
    }
    
    
    
    
    
//********************************* TESTS **************************************   
    /**
     * Test para insertar un registro
     * en la tabla CLIENTES
     */
    @Test
    public void testInsertar() {
        /**
         * Probamos insertando un objeto de 
         * la clase Cliente que contenga todos
         * sus atributos
         */
        c.txtDNI.setText(cl.getDni()); 
        c.txtNombre.setText(cl.getNombre()); 
        c.txtEdad.setText(String.valueOf(cl.getEdad()));
        c.txtEmpleo.setText(cl.getEmpleo());
        
            
        
        /**
         * Probamos a insertar llamando
         * al ActionEvent producido por el botón
         * INSERT
         */
        ctrl.actionPerformed(new ActionEvent(c.btnInsert, 
                ActionEvent.ACTION_PERFORMED, null));
        
        /**
         * Comprobamos que realmente hemos insertado
         * el registro, leyendo todos los registros 
         * de la tabla CLIENTES
         */
        Cliente customer = (Cliente) crud.buscar("CLIENTES", 
                                                cl);
        
        
        /**
         * Devolverá true si los datos de la clase Cliente
         * están en la BBDD, pueda insertarse o no, ya que
         * en caso de que no pueda insertarse, será porque
         * el registro ya existe
         */
        assertEquals(customer.getDni(), cl.getDni());
        assertEquals(customer.getNombre(), cl.getNombre());
    }
    
    
    
    /**
     * Test para leer los registros
     * de la tabla CLIENTES
     */
    @Test
    public void testLeer(){
        /**
         * Pulsa el botón Leer de la ventana Propietarios
         */
        ctrl.actionPerformed(new ActionEvent(c.btnRead, 
                ActionEvent.ACTION_PERFORMED, null));
        DefaultTableModel table = (DefaultTableModel) c.tabla.getModel();
        
        int cont = 0;
        LinkedHashSet<Cliente> clientes =
                new LinkedHashSet<>();
        /**
         * Insertamos en la lista propietarios los
         * valores que devuelve la tabla asociada al btnRead
         */
        for (int i = 0; i < table.getRowCount(); i++){
            Cliente p = new Cliente();
            p.setDni(String.valueOf(table.getValueAt(cont, 0)));
            p.setNombre(
                    String.valueOf(table.getValueAt(cont, 1))
            );
            p.setEdad(Integer.parseInt(
                    String.valueOf(table.getValueAt(cont, 2))
                    ));
            p.setEmpleo(
                    String.valueOf(table.getValueAt(cont, 3)
                    ));
            cont++;
            clientes.add(p);
        }
        
        boolean t = true;
        for (var e: clientes){
            /**
             * En caso de no encontrarse algún registro
             */
            
            if (crud.buscar("CLIENTES", e) == null){
                t = false;
            /**
             * Si encontramos registros, comprobamos que sean iguales
             * al cliente
             */
            } else{
                Cliente cli = 
                        (Cliente) crud.buscar("CLIENTES", e);
                
                assertEquals(cli.getDni(), e.getDni());
                assertEquals(cli.getNombre(), e.getNombre());
                assertEquals(cli.getEdad(), e.getEdad());
                assertEquals(cli.getEmpleo(), e.getEmpleo());
            }
        }
        
        assertTrue(t);
    }
    
    
    
    /**
     * Test para modificar un registro
     * de la tabla CLIENTES
     */
    @Test
    public void testModificar(){
        /**
         * Escribimos en el campo DNI el dni del
         * registro y escribimos otro nombre
         */
        c.txtDNI.setText(cl.getDni()); 
        cl.setNombre("Pedro León");
        c.txtNombre.setText(cl.getNombre()); 
        c.txtEdad.setText(String.valueOf(cl.getEdad()));
        c.txtEmpleo.setText(cl.getEmpleo());
        

        /**
         * Llamamos al evento que se produce
         * al pulsar el botón MODIFICAR
         */
        ctrl.actionPerformed(new ActionEvent(c.btnUpdate, 
                ActionEvent.ACTION_PERFORMED, null));
        
        /**
         * Buscamos al propietario
         */
        Cliente cli = (Cliente) crud.buscar("CLIENTES", 
                cl);

        
        /**
         * Si hemos encontrado al propietario,
         * comprobaremos si es el mismo que
         * nuestro objeto pr que tiene
         * los datos modificados
         */
        if (cli != null)
            assertEquals(cl.getNombre(), cli.getNombre());
        else{
            assertNull(cli);
        }
    }
    
    
    
    /**
     * Test para eliminar un registro
     * de la tabla CLIENTES
     */
    @Test
    public void testEliminar(){
        /**
         * Introducimos en el campo de texto
         * el dni de nuestro objeto pr
         */
        c.txtDNI.setText(cl.getDni()); 
        
        /**
         * llamamos al evento del botón BORRAR
         */
        ctrl.actionPerformed(new ActionEvent(c.btnDelete, 
                ActionEvent.ACTION_PERFORMED, null));
 
        /**
         * Buscamos el propietario que hemos
         * borrado por su id
         */
        Cliente prop = (Cliente) crud.buscar("CLIENTES",
                cl);
        /**
         * Si el objeto prop es nulo,
         * hemos pasado el test
         */
        assertNull(prop);
    }
    
    
    
    /**
     * Test para buscar un registro
     * de la tabla CLIENTES
     */
    @Test
    public void testBuscar(){
        /**
         * Pasamos al texto dni el 
         * DNI de Propietario pr
         */
        c.txtDNI.setText(cl.getDni());
        
        
        /**
         * Supongamos que se produce
         * 
         * Realizamos la búsqueda
         */
        boolean t = true;
        ctrl.actionPerformed(new ActionEvent(c.btnSearch, 
                ActionEvent.ACTION_PERFORMED, null));
        
        DefaultTableModel table = (DefaultTableModel) c.tabla.getModel();
        
        /**
         * Supongamos que la tabla nos devuelve
         * una cantidad de filas distinta a 1
         * (no se produciría la búsqueda asociada
         * al evento del botón)
         */
        if(table.getRowCount() != 1){
            /**
             * SELECT * FROM PROPIETARIOS;
             */
            LinkedHashSet<Entidad> select = 
                    crud.leer("CLIENTES");
            LinkedHashSet<Cliente> props = new LinkedHashSet<>();
            for (Entidad entity: select){
                props.add((Cliente) entity);
            }
            /**
             * Comprueba si alguno 
             * de los registros de la tabla 
             * PROPIETARIOS tiene el mismo
             * dni que el pasado por pantalla
             */
            for (Cliente p: props){
                /**
                 * Si hubiera alguno,
                 * fallariamos
                 */
                if (p.getDni().equals(cl.getDni())){
                    t = false;
                }
            }
            
        } else{
            /**
             * En este caso, suponemos
             * que la búsqueda, si se produce,
             * puede dar error en el registro
             */
            t = false;
            if(table.getValueAt(0, 0).equals(cl.getDni()))
                t = true;
        }
        /**
         * Si t = true, pasamos la prueba
         * 
         */
        assertTrue(t);
        
    }
    
}
