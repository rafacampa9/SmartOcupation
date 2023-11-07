
import controller.CtrlPropietario;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import model.entidades.Entidad;
import model.entidades.Propietario;
import model.sql.CrudSQL;
import org.junit.Before;
import org.junit.Test;
import view.Propietarios;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rafacampa9
 */
public class CtrlPropietarioTest {
    private Propietario pr;
    private CrudSQL crud;
    private Propietarios prs;
    private CtrlPropietario ctrl;
    
    @Before
    public void setUp() {
        pr = new Propietario();
        crud = new CrudSQL();
        prs = new Propietarios();
        ctrl = new CtrlPropietario(pr, crud, prs);
    }

    @Test
    public void testInsertar() {
        // Preparar datos para la prueba
        prs.jTextField1.setText("77777777A"); 
        prs.jTextField2.setText("Mikasa Ackerman"); 
        
        // Ejecutar método a probar
        ctrl.actionPerformed(new ActionEvent(prs.btnInsert, ActionEvent.ACTION_PERFORMED, null));
        
        // Verificar que se ha insertado correctamente
        ArrayList<Entidad> propietarios = crud.leer("PROPIETARIOS");
        boolean encontrado = false;
        for (Entidad entidad : propietarios) {
            Propietario prop = (Propietario) entidad;
            if (prop.getDni().equals("77777777A") && prop.getNombre().equals("Mikasa Ackerman")) {
                encontrado = true;
                break;
            }
        }
        assertTrue(encontrado);
    }

    @Test
    public void testLeer() {

        ctrl.actionPerformed(new ActionEvent(prs.btnRead, ActionEvent.ACTION_PERFORMED, null));
        

    }

    @Test
    public void testActualizar() {

        prs.jTextField1.setText("77777777A"); 
        prs.jTextField2.setText("Levi Ackerman"); 
        

        ctrl.actionPerformed(new ActionEvent(prs.btnUpdate, ActionEvent.ACTION_PERFORMED, null));
        

        Propietario prop = (Propietario) crud.buscar("PROPIETARIOS", pr);
        assertEquals("Levi Ackerman", prop.getNombre());
    }

    @Test
    public void testEliminar() {

        prs.jTextField1.setText("77777777A"); 
        

        ctrl.actionPerformed(new ActionEvent(prs.btnDelete, ActionEvent.ACTION_PERFORMED, null));
 
        Propietario prop = (Propietario) crud.buscar("PROPIETARIOS", pr);
        assertNull(prop);
    }

    @Test
    public void testBuscar() {
        
        prs.jTextField1.setText("77777777A"); 
        
        ctrl.actionPerformed(new ActionEvent(prs.btnBuscar, ActionEvent.ACTION_PERFORMED, null));
        
  
    }
}

