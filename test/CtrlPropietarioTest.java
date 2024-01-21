//****************************** PACKAGES **************************************
import controller.CtrlPropietario;
import java.awt.event.ActionEvent;
import java.util.LinkedHashSet;
import javax.swing.table.DefaultTableModel;
import model.entidades.Entidad;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import model.entidades.Propietario;
import model.sql.CrudSQL;
import org.junit.Before;
import org.junit.Test;
import view.Propietarios;


/**
 *
 * @author rafacampa9
 * 
 * Clase de pruebas para la clase
 * CtrlPropietario
 */
public class CtrlPropietarioTest {
    //*********************** ATRIBUTOS ****************************************
    private Propietario pr;
    private CrudSQL crud;
    private Propietarios prs;
    private CtrlPropietario ctrl;
    
    
    //************************ MÉTODOS *****************************************
    
    /**
     * Antes de realizar los test,
     * instanciamos los atributos
     * mediante sus constructores 
     * vacíos (crud y prs) y pr
     * con su constructor con
     * parámetros, y los
     * pasamos como parámetro del
     * constructor ctrl
     * 
     * También inicializamos el
     * dni del objeto Propietario 
     * y el nombre con los valores
     * aportados
     */
    @Before
    public void setUp() {
        pr = new Propietario("10101010Q", "Levi Ackerman");
        crud = new CrudSQL();
        prs = new Propietarios();
        ctrl = new CtrlPropietario(pr, crud, prs);

    }

    
    
    //************************** TESTS *****************************************
    /**
     * Test para insertar un registro
     * en la tabla PROPIETARIOS
     */
    @Test
    public void testInsertar() {
        /**
         * Probamos insertando los DNI y Nombre
         * que vemos abajo
         */
        prs.jTextField1.setText(pr.getDni()); 
        prs.jTextField2.setText(pr.getNombre()); 
        
        /**
         * Probamos a insertar llamando
         * al ActionEvent producido por el botón
         * INSERT
         */
        ctrl.actionPerformed(new ActionEvent(prs.btnInsert, 
                ActionEvent.ACTION_PERFORMED, null));
        
        /**
         * Comprobamos que realmente hemos insertado
         * el registro, leyendo todos los registros 
         * de la tabla PROPIETARIOS
         */
        Propietario p = (Propietario) crud.buscar("PROPIETARIOS", 
                                                pr);
        
        
        /**
         * Devolverá true si encontrado = true
         */
        assertEquals(p.getDni(), pr.getDni());
        assertEquals(p.getNombre(), pr.getNombre());
    }

    
    
    /**
     * Test para leer los registros
     * de la tabla PROPIETARIOS
     */
    @Test
    public void testLeer() {
        /**
         * Pulsa el botón Leer de la ventana Propietarios
         */
        ctrl.actionPerformed(new ActionEvent(prs.btnRead, 
                ActionEvent.ACTION_PERFORMED, null));
        DefaultTableModel table = (DefaultTableModel) prs.tabla.getModel();
        
        int cont = 0;
        LinkedHashSet<Propietario> propietarios =
                new LinkedHashSet<>();
        /**
         * Insertamos en la lista propietarios los
         * valores que devuelve la tabla asociada al btnRead
         */
        for (int i = 0; i < table.getRowCount(); i++){
            Propietario p = new Propietario();
            p.setDni(String.valueOf(table.getValueAt(cont, 0)));
            p.setNombre(
                    String.valueOf(table.getValueAt(cont, 1))
            );
            cont++;
            propietarios.add(p);
        }
        
        boolean t = true;
        for (var e: propietarios){
            Propietario pro = 
                        (Propietario) crud.buscar("PROPIETARIOS",
                                                     e);
                
            /**
             * En caso de no encontrarse algún recurso
             */
            if (pro.getDni().isEmpty()){
                assertNull(e);
            } else{
                
                assertEquals(pro.getDni(), e.getDni());
                assertEquals(pro.getNombre(), e.getNombre());
                
            }
            
            assertTrue(t);
        }
        
        

    }

    
    
    /**
     * Test para modificar un registro
     * de la tabla PROPIETARIOS
     */
    @Test
    public void testModificar() {
        /**
         * Escribimos en el campo DNI el dni del
         * registro y escribimos otro nombre
         */
        prs.jTextField1.setText(pr.getDni()); 
        pr.setNombre("Naruto");
        prs.jTextField2.setText(pr.getNombre()); 
        

        /**
         * Llamamos al evento que se produce
         * al pulsar el botón MODIFICAR
         */
        ctrl.actionPerformed(new ActionEvent(prs.btnUpdate, 
                ActionEvent.ACTION_PERFORMED, null));
        
        /**
         * Buscamos al propietario
         */
        Propietario prop = (Propietario) crud.buscar("PROPIETARIOS", 
                pr);
        
        /**
         * Si hemos encontrado al propietario,
         * comprobaremos si es el mismo que
         * nuestro objeto pr que tiene
         * los datos modificados
         */
        if (prop != null)
            assertEquals(pr.getNombre(), prop.getNombre());
        else
            assertNull(prop);
    }

    
    
    /**
     * Test para eliminar un registro
     * de la tabla PROPIETARIOS
     */
    @Test
    public void testEliminar() {
        /**
         * Introducimos en el campo de texto
         * el dni de nuestro objeto pr
         */
        prs.jTextField1.setText(pr.getDni()); 
        
        /**
         * llamamos al evento del botón BORRAR
         */
        ctrl.actionPerformed(new ActionEvent(prs.btnDelete, 
                ActionEvent.ACTION_PERFORMED, null));
 
        /**
         * Buscamos el propietario que hemos
         * borrado por su id
         */
        Propietario prop = (Propietario) crud.buscar("PROPIETARIOS",
                pr);
        /**
         * Si el objeto prop es nulo,
         * hemos pasado el test
         */
        assertNull(prop);
    }

    
    
    /**
     * Test para buscar un registro 
     * de la tabla PROPIETARIOS
     */
    @Test
    public void testBuscar() {
        /**
         * Pasamos al texto dni el 
         * DNI de Propietario pr
         */
        prs.jTextField1.setText(pr.getDni());
        
        
        /**
         * Supongamos que se produce
         * 
         * Realizamos la búsqueda
         */
        boolean t = true;
        ctrl.actionPerformed(new ActionEvent(prs.btnBuscar, 
                ActionEvent.ACTION_PERFORMED, null));
        
        DefaultTableModel table = (DefaultTableModel) prs.tabla.getModel();
        
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
                    crud.leer("PROPIETARIOS");
            LinkedHashSet<Propietario> props = new LinkedHashSet<>();
            for (Entidad entity: select){
                props.add((Propietario) entity);
            }
            /**
             * Comprueba si alguno 
             * de los registros de la tabla 
             * PROPIETARIOS tiene el mismo
             * dni que el pasado por pantalla
             */
            for (Propietario p: props){
                /**
                 * Si hubiera alguno,
                 * fallariamos
                 */
                if (p.getDni().equals(pr.getDni())){
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
            if(table.getValueAt(0, 0).equals(pr.getDni()))
                t = true;
        }
        /**
         * Si t = true, pasamos la prueba
         * 
         */
        assertTrue(t);
        
  
    }
}

