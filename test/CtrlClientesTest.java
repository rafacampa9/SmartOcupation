
import controller.CtrlClientes;
import java.awt.event.ActionEvent;
import model.entidades.Cliente;
import model.sql.CrudSQL;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import view.Clientes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rafacampa9
 */
public class CtrlClientesTest {
    private CtrlClientes ctrlClientes;
    private CrudSQL crud;

    @Before
    public void setUp() {
        crud = new CrudSQL();
        Clientes clientesView = new Clientes();
        ctrlClientes = new CtrlClientes(new Cliente(), crud, clientesView);
    }

    @Test
    public void testInsertarCliente() {
        Cliente cliente = new Cliente();
        cliente.setDni("12345678A");
        cliente.setNombre("Juan Pérez");
        cliente.setEdad(30);
        cliente.setEmpleo("Programador");

        assertTrue(crud.insertar("CLIENTES", cliente));
    }

    @Test
    public void testEliminarCliente() {
        Cliente cliente = new Cliente();
        cliente.setDni("12345678A");

        assertTrue(crud.eliminar("CLIENTES", cliente));
    }

    @Test
    public void testLeerClientes() {
        assertNotNull(crud.leer("CLIENTES"));
    }

    @Test
    public void testActualizarCliente() {
        Cliente cliente = new Cliente();
        cliente.setDni("12345678A");
        cliente.setNombre("Juan Pérez");
        cliente.setEdad(31);
        cliente.setEmpleo("Desarrollador");

        assertTrue(crud.modificar("CLIENTES", cliente));
    }

    @Test
    public void testBuscarCliente() {
        Cliente cliente = new Cliente();
        cliente.setDni("12345678A");
        Clientes cl = new Clientes();
        
        ctrlClientes.actionPerformed(new ActionEvent(cl.btnSearch, ActionEvent.ACTION_PERFORMED, null));

        assertNotNull(crud.buscar("CLIENTES", cliente));
    }
}
