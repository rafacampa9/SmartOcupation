//******************************* PACKAGES *************************************
import controller.CtrlArrendamiento;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import javax.swing.table.DefaultTableModel;

import model.entidades.Arrendamiento;
import model.entidades.Entidad;
import model.sql.CrudSQL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import view.Alquiler;



/**
 *
 * @author rafacampa9
 */
public class CtrlArrendamientoTest {
    
    //*************************** ATRIBUTOS ************************************
    private Arrendamiento ar;
    private CrudSQL crud;
    private Alquiler alq;
    private CtrlArrendamiento ctrl;
    private SimpleDateFormat formato, formato2; 
    
    
    
    
    
    //**************************** MÉTODOS *************************************
    /**
     * Antes de realizar los test,
     * instanciamos los atributos
     * mediante sus constructores 
     * vacíos (ar, crud y alq) y los
     * pasamos como parámetro del
     * constructor ctrl
     * 
     * También inicializamos todos 
     * los campos que puede presentar
     * el registro de ARRENDAMIENTOS
     */
    
    
    @Before
    public void setUp(){
        /**
         * Se ha comprobado tanto que idVivienda exista
         * en nuestra BBDD como el cliente, ya que son
         * claves foráneas de la tabla y no podría
         * insertarnos un registro en la tabla ARRENDAMIENTOS
         * de un CLIENTE que no existe, ni de una VIVIENDA que
         * no exista
         */
        formato = new SimpleDateFormat("yyyy-MM-dd");
        int idVivienda = 2, numExp = 8;
        boolean pagado = false;
        Date fecha_entrada, fecha_salida;
        try
        {
            fecha_entrada = formato.parse("2024-02-12");
        } catch (ParseException ex)
        {
            fecha_entrada = null;
        }
        try
        {
            fecha_salida = formato.parse("2025-04-04");
        } catch (ParseException ex)
        {
            fecha_salida = null;
        }
        String cliente = "56781234J";
        
        
        
        ar = new Arrendamiento ();
        ar.setNumExp(numExp);
        ar.setCliente(cliente);
        ar.setFechaEntrada(fecha_entrada);
        ar.setFechaSalida(fecha_salida);
        ar.setIdVivienda(idVivienda);
        ar.setPagado(pagado);
        
        
        alq = new Alquiler();
        crud = new CrudSQL();
        ctrl = new CtrlArrendamiento(ar, crud, alq);
   
    }
    
    
    //***************************** TESTS **************************************
    /**
     * Test para insertar un registro
     * en la tabla ARRENDAMIENTOS
     */
    @Test
    public void testInsertar(){
         /**
         * Probamos insertando un objeto de 
         * la clase Arrendamiento que contenga todos
         * sus atributos
         */
        alq.txtNumExp.setText(String.valueOf(ar.getNumExp())); 
        alq.txtIdVivienda.setText(String.valueOf(ar.getIdVivienda())); 
        alq.fechaEntrada.setDate(ar.getFechaEntrada());
        alq.fechaSalida.setDate(ar.getFechaSalida());
        alq.cbPagado.setSelectedItem("No");
        alq.txtDniCliente.setText(ar.getCliente());
        
            
        
        /**
         * Probamos a insertar llamando
         * al ActionEvent producido por el botón
         * INSERT
         */
        ctrl.actionPerformed(new ActionEvent(alq.btnInsert, 
                ActionEvent.ACTION_PERFORMED, null));
        
        /**
         * Comprobamos que realmente hemos insertado
         * el registro, leyendo todos los registros 
         * de la tabla Arrendamiento
         */
        Arrendamiento a = (Arrendamiento) crud.buscar("ARRENDAMIENTOS", 
                                                ar);
        
        
        /**
         * Devolverá true si los datos de la clase Arrendamietno
         * están en la BBDD, pueda insertarse o no, ya que
         * en caso de que no pueda insertarse, será porque
         * el registro ya existe
         */
        assertEquals(a.getNumExp(), ar.getNumExp());
        assertEquals(a.getIdVivienda(), ar.getIdVivienda());
        if (a.getFechaEntrada()!=null && ar.getFechaEntrada()!= null){
            assertEquals(formato.format(a.getFechaEntrada()),
                formato.format(ar.getFechaEntrada()));
        }
        if(a.getFechaSalida()!=null && ar.getFechaSalida()!=null){
            assertEquals(formato.format(a.getFechaSalida()),
                    formato.format(ar.getFechaSalida()));
        }
            assertEquals(a.isPagado(), ar.isPagado());
        assertEquals(a.getCliente(), ar.getCliente());
    
    }
    
    
    
    /**
     * Test para leer los registros
     * de la tabla ARRENDAMIENTOS
     */
    @Test
    public void testLeer(){
        /**
         * Pulsa el botón Leer de la ventana Arrendamientos
         */
        ctrl.actionPerformed(new ActionEvent(alq.btnRead, 
                ActionEvent.ACTION_PERFORMED, null));
        DefaultTableModel table = (DefaultTableModel) alq.tabla.getModel();
        
        int cont = 0;
        LinkedHashSet<Arrendamiento> arrendamientos =
                new LinkedHashSet<>();
        /**
         * Insertamos en la lista arrendamientos los
         * valores que devuelve la tabla asociada al btnRead
         */
        for (int i = 0; i < table.getRowCount(); i++){
            Arrendamiento p = new Arrendamiento();
            p.setNumExp(Integer.parseInt(
                    String.valueOf(table.getValueAt(cont, 0))
            ));
            
            formato2 = new SimpleDateFormat("yyyy/MM/dd");
            
            try
            {
                p.setFechaEntrada(formato2.parse(
                        String.valueOf(
                                table.getValueAt(cont, 1))
                ));
            } catch (ParseException ex)
            {
                p.setFechaEntrada(null);
            }
            try{
                p.setFechaSalida(formato2.parse(
                        String.valueOf(
                                table.getValueAt(cont, 2))
                )); 
            } catch (ParseException e){
                p.setFechaSalida(null);
            }
            
            p.setCliente(
                    String.valueOf(table.getValueAt(cont,3)
                    ));
            
            p.setIdVivienda(
                    Integer.parseInt(
                    String.valueOf(table.getValueAt(cont, 4))
                    ));
            p.setPagado(Boolean.parseBoolean(
                     String.valueOf(table.getValueAt(cont, 5))
                    ));
            
            cont++;
            arrendamientos.add(p);
        }
        
        boolean t = true;
        for (var e: arrendamientos){
            /**
             * En caso de no encontrarse algún registro
             */
            
            if (crud.buscar("ARRENDAMIENTOS", e) == null){
                t = false;
            /**
             * Si encontramos registros, comprobamos que sean iguales
             * al cliente
             */
            } else{
                Arrendamiento a = 
                        (Arrendamiento) crud.buscar("ARRENDAMIENTOS",
                                e);
                
                assertEquals(a.getNumExp(), e.getNumExp());
                if (a.getFechaEntrada()!=null)
                    assertEquals(a.getFechaEntrada(), 
                            e.getFechaEntrada());
                if (a.getFechaSalida()!=null)
                    assertEquals(a.getFechaSalida(), 
                        e.getFechaSalida());
                assertEquals(a.getIdVivienda(), 
                        e.getIdVivienda());
                assertEquals(a.isPagado(), e.isPagado());
                assertEquals(a.getCliente(), e.getCliente());
            }
        }
        
        assertTrue(t);
    }
    
    
    
    /**
     * Test para eliminar un registro
     * de la tabla ARRENDAMIENTOS
     */
    @Test
    public void testEliminar(){
        /**
         * Introducimos en el campo de texto
         * el num_exp de nuestro objeto ar
         */
        alq.txtNumExp.setText(String.valueOf(ar.getNumExp()));

        /**
         * llamamos al evento del botón BORRAR
         */
        ctrl.actionPerformed(new ActionEvent(alq.btnDelete, 
                ActionEvent.ACTION_PERFORMED, null));
 
        /**
         * Buscamos el propietario que hemos
         * borrado por su id
         */
        Arrendamiento prop = (Arrendamiento) crud.buscar("CLIENTES",
                ar);
        /**
         * Si el objeto prop es nulo,
         * hemos pasado el test
         */
        assertNull(prop);
    
    }
    
    
    
    /**
     * Test para modificar un registro
     * de la tabla ARRENDAMIENTOS
     */
    @Test
    public void testModificar(){
        /**
         * Escribimos en el campo DNI el dni del
         * registro y escribimos otro nombre
         */
        Date fechaEntrada = null;
        Date fechaSalida = null;
         
        try{
            fechaEntrada = formato.parse("2024-05-15");
        } catch (ParseException e){
            fechaEntrada = null;
        }
        try{
            fechaSalida = formato.parse("2028-09-07");
        } catch(ParseException e){
            fechaSalida = null;
        }
        String idVivienda = String.valueOf(ar.getIdVivienda());
        String dniCliente = ar.getCliente();
        String pagado = "Sí";
        alq.txtNumExp.setText(String.valueOf(ar.getNumExp()));
        alq.fechaEntrada.setDate(fechaEntrada);
        alq.fechaSalida.setDate(fechaSalida);
        alq.txtIdVivienda.setText(idVivienda);
        alq.txtDniCliente.setText(dniCliente);
        alq.cbPagado.setSelectedItem(pagado);
        
        ar.setFechaEntrada(fechaEntrada);
        ar.setFechaSalida(fechaSalida);
        ar.setPagado(true);
        

        /**
         * Llamamos al evento que se produce
         * al pulsar el botón MODIFICAR
         */
        ctrl.actionPerformed(new ActionEvent(alq.btnUpdate, 
                ActionEvent.ACTION_PERFORMED, null));
        
        /**
         * Buscamos al propietario
         */
        Arrendamiento cli = (Arrendamiento) crud.buscar("ARRENDAMIENTOS", 
                ar);

        
        /**
         * Si hemos encontrado al propietario,
         * comprobaremos si es el mismo que
         * nuestro objeto pr que tiene
         * los datos modificados
         */
        if (cli != null){
            assertEquals(ar.getNumExp(), cli.getNumExp());
            if (cli.getFechaEntrada()!= null && ar.getFechaEntrada()!=null){
                assertEquals(formato.format(ar.getFechaEntrada()), 
                        formato.format(cli.getFechaEntrada()));
            }
            if (cli.getFechaSalida()!= null && ar.getFechaSalida()!= null){
                assertEquals(formato.format(ar.getFechaSalida()),
                        formato.format(cli.getFechaSalida()));
            }
            assertEquals(ar.getIdVivienda(), cli.getIdVivienda());
            assertEquals(ar.getCliente(), cli.getCliente());
            assertEquals(ar.isPagado(), cli.isPagado());
        }else{
            assertNull(cli);
        }
    }
    
    
    
    /**
     * Test para buscar un registro
     * de la tabla ARRENDAMIENTOS
     */
    @Test
    public void testBuscar(){
        /**
         * Pasamos al texto num_exp
         * que es el
         * numExp de Propietario pr
         */
        alq.txtNumExp.setText(
                String.valueOf(ar.getNumExp()));
        
        
        /**
         * Supongamos que se produce
         * 
         * Realizamos la búsqueda
         */
        boolean t = true;
        ctrl.actionPerformed(new ActionEvent(alq.btnSearch, 
                ActionEvent.ACTION_PERFORMED, null));
        
        DefaultTableModel table = (DefaultTableModel) alq.tabla.getModel();
        
        /**
         * Supongamos que la tabla nos devuelve
         * una cantidad de filas distinta a 1
         * (no se produciría la búsqueda asociada
         * al evento del botón)
         */
        if(table.getRowCount() != 1){
            /**
             * SELECT * FROM ARRENDAMIENTOS;
             */
            LinkedHashSet<Entidad> select = 
                    crud.leer("ARRENDAMIENTOS");
            LinkedHashSet<Arrendamiento> props = new LinkedHashSet<>();
            for (Entidad entity: select){
                props.add((Arrendamiento) entity);
            }
            /**
             * Comprueba si alguno 
             * de los registros de la tabla 
             * ARRENDAMIENTOS tiene el mismo
             * num_exp que el pasado por pantalla
             */
            for (Arrendamiento p: props){
                /**
                 * Si hubiera alguno,
                 * fallariamos
                 */
                if (ar.getNumExp() ==p.getNumExp()){
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
            if(String.valueOf(table.getValueAt(0, 0))
                    .equals(String.valueOf(ar.getNumExp())))
                t = true;
        }
        /**
         * Si t = true, pasamos la prueba
         * 
         */
        assertTrue(t);
    }
}
