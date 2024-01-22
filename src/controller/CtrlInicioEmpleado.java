//**************************** PACKAGES ****************************************
package controller;

import model.entidades.GenerarInforme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.entidades.InfoExtensaAlquiler;
import model.entidades.Vivienda;
import model.sql.CrudSQL;
import view.Fechas;
import view.HistoricoAlquiler;
import view.Inicio;
import view.InicioEmpleado;
import view.ListaDeudores;
import view.ViviendasSinOcupar;

/**
 *
 * Esta clase se encarga
 * de enlazar la clase InicioEmpleado
 * del paquete view con las entidades
 * - InfoExtensaAlquiler
 * - CrudSQL
 * - Vivienda
 * 
 * @author rafacampa9
 */
public class CtrlInicioEmpleado implements ActionListener{

    //************************* ATRIBUTOS *************************************
    private final InicioEmpleado init;
    private final ButtonGroup grupo;
    
    
    
    
    
    //************************ CONSTRUCTOR ************************************
    public CtrlInicioEmpleado(InicioEmpleado init) {
        this.init = init;
        this.grupo = new ButtonGroup();
        
        this.grupo.add(this.init.rbBack);
        this.grupo.add(this.init.rbDate);
        this.grupo.add(this.init.rbDeuda);
        this.grupo.add(this.init.rbHist);
        this.grupo.add(this.init.rbSin);
        
        
        this.init.rbDate.addActionListener(this);
        this.init.rbDeuda.addActionListener(this);
        this.init.rbHist.addActionListener(this);
        this.init.rbSin.addActionListener(this);
        this.init.rbBack.addActionListener(this);
    }

    
    //************************** MÉTODOS **************************************
    
    /**
     * Método para iniciar nuestra
     * vista de inicio empleado
     */
    public void iniciar(){
        init.setTitle("Empleado");
        init.setLocationRelativeTo(null);
        init.setResizable(false);
    }
    
    
    /**
     * actionPerformed sobrescrito que atiende
     * a la llamada del evento ActionEvent
     * @param e 
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * Inicializamos objetos de las clases
         * del paquete view que nos serán útiles
         */
        HistoricoAlquiler hist = new HistoricoAlquiler();
        ListaDeudores morosos = new ListaDeudores();
        ViviendasSinOcupar viv = new ViviendasSinOcupar();
        
        /**
         * Título y ubicación en la pantalla
         * donde aparecera la pantalla
         * 
         * Si se cierran, no cerrará la app
         */
        hist.setTitle("Lista Arrendamientos");
        hist.setLocationRelativeTo(null);

        
        morosos.setTitle("Lista Adeudados");
        morosos.setLocationRelativeTo(null);

        
        viv.setTitle("Viviendas Sin Ocupar");
        viv.setLocationRelativeTo(null);

        
        
        /**
         * Declaramos dos listas:
         * - InfoExtensaAlquiler
         * - Vivienda
         */
        LinkedHashSet<InfoExtensaAlquiler> arrendamientos;
        LinkedHashSet<Vivienda> viviendas;
        
        /**
         * Obtenemos el modelo de 
         * las tablas de cada ventana
         */
        DefaultTableModel table = (DefaultTableModel) hist.tabla.getModel();
        DefaultTableModel table2 = (DefaultTableModel) morosos.tabla.getModel();
        DefaultTableModel table3 = (DefaultTableModel) viv.tabla.getModel();
        
        /**
         * Para hacer las consultas sql
         */
        CrudSQL crud = new CrudSQL();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        
        
        
        /**
         * Si pulsamos VOLVER
         * 
         * Volvemos a la pantalla
         * de Inicio
         */
        if (e.getSource() == init.rbBack){
            Inicio st = new Inicio();
            CtrlInicio ctrlInit = new CtrlInicio(st);
            ctrlInit.iniciar();
            st.setVisible(true);
            init.setVisible(false);
            
        }
        
        
        
        /**
         * Si pulsamos el HISTÓRICO ALQUILER
         */       
        if (e.getSource()== init.rbHist){
            /**
             * Realizamos la consulta SQL del histórico
             * del alquiler (aquí no necesitamos
             * parámetros)
             */
            arrendamientos = crud.historicoAlquiler();
            
            /**
             * insertamos en una fila cada uno
             * de los registros que devuelve 
             * la lista historicoAlquiler()
             * 
             * vaciamos la tabla antes de 
             * llenarla
             */
            table.setRowCount(0);
            for (InfoExtensaAlquiler ar : arrendamientos){
                Object [] fila = {
                                ar.getNumExp(),
                                ar.getPrecio(), 
                                ar.isPagado(), 
                                formato.format(ar.getFechaEntrada()), 
                                formato.format(ar.getFechaSalida()), 
                                ar.getNombreCl(), ar.getNombrePr()
                            };
                
                
                /**
                 * Agregamos la fila
                 */
                table.addRow(fila);
            }
            /**
             * Hacemos visible
             * esta ventana, con su
             * title, tamaño no
             * redimensionable y 
             * un botón GENERAR INFORME
             * que genera un JasperReport
             */
            init.setVisible(false);
            hist.setVisible(true);
            hist.setTitle("Lista histórica de arrendamientos");
            hist.setResizable(false);
            
            /**
             * Atiende a la llamada de un evento
             * ActionEvent evt (botón GENERAR INFORME)
             */
            hist.btnInf.addActionListener((ActionEvent evt) ->
            {
                /**
                 * Instanciamos un objeto de
                 * la clase informe y realizamos
                 * su método
                 * generarInforme(rutaInforme, nombreInforme)
                 */
                GenerarInforme info = new GenerarInforme();
                String rutaInforme = "src\\resources";
                String nombreInforme = "SmartOcupation.jrxml";
                info.generarInforme(rutaInforme, nombreInforme);
            });
            
            hist.btnBack.addActionListener((ActionEvent) ->{
                hist.setVisible(false);
                init.setVisible(true);
            });
            
            
        }
        
        
        /**
         * Si pulsamos sobre LISTA DEUDAS
         */
        if (e.getSource() == init.rbDeuda){
            /**
             * Obtenemos las deudas
             * (tampoco es necesario ingresar
             * parámetros)
             */
            arrendamientos = crud.deudas();
            
            /**
             * Iteramos sobre los registros devueltos
             * y los agregamos a la tabla
             */
            table2.setRowCount(0);
            for (InfoExtensaAlquiler ar: arrendamientos){
                Object [] fila = {ar.getNombreCl(), ar.getEdadCl(), ar.getEmpleoCl(),
                    ar.getNumExp(), ar.getPrecio(),
                    formato.format(ar.getFechaEntrada()), 
                    formato.format(ar.getFechaSalida())};
                
                table2.addRow(fila);
            }
            /**
             * hacemos visible la ventana
             * de los morosos
             */
            init.setVisible(false);
            morosos.setVisible(true);
            morosos.setTitle("Lista de clientes sin pagar");
            morosos.setResizable(false);
            morosos.btnBack.addActionListener((ActionEvent)->{
                morosos.setVisible(false);
                init.setVisible(true);
            });
        }
                    
        
        /**
         * 
         * Si pulsamos sobre la LISTA DE ALQUILER según la fecha
         * hacemos visible la ventana que nos da a elegir
         * la fecha de entrada y de salida.
         */
        if (e.getSource() == init.rbDate){
            Fechas fecha = new Fechas();
            fecha.setTitle("Seleccionar Fechas");
            fecha.setLocationRelativeTo(null);
            fecha.setSize(310,333);
            fecha.setResizable(false);
            fecha.setVisible(true);
            fecha.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            
            /**
             * Una vez enviamos la información
             * de las fechas, atendemos a la 
             * llamada de un nuevo evento
             */
            fecha.btnSend.addActionListener((ActionEvent evt) ->
            {
                /**
                 * Obtenemos las fechas
                 * entrada y salida
                 */
                Date fechaEntrada = fecha.fechaEntrada.getDate();
                Date fechaSalida = fecha.fechaSalida.getDate();
                
                /**
                 * Si son nulas, mensaje de diálogo
                 * que nos pide que ingresemos las
                 * fechas
                 */
                if (fechaEntrada == null || fechaSalida == null) {
                    JOptionPane.showMessageDialog(null,
                            "Por favor, introduzca las fechas",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    
                    /**
                     * Si has ingresado las
                     * fechas utilizamos la misma ventana
                     * que para el histórico de alquiler.
                     * Simplemente, se devuelve la consulta
                     * según su fecha entrada y salida
                     * fecha >= fechaEntrada
                     * fecha <= fechaSalida
                     *
                     * También ocultamos la ventana
                     * InicioEmpleado
                     */
                } else {
                    init.setVisible(false);
                    fecha.setVisible(false);
                    hist.setVisible(true);
                    hist.btnInf.setVisible(false);
                    hist.setTitle("Lista Arrendamientos según fecha");
                    hist.setResizable(false);
                    /**
                     * Almacenamos en una lista la info extensa
                     * del alquiler que nos devuelve la consulta
                     * según la fecha
                     */
                    LinkedHashSet<InfoExtensaAlquiler>arrendamientosFiltrados
                            = crud.arrendamientosPorFecha(fechaEntrada,
                                    fechaSalida);
                    
                    /**
                     * Agregamos cada registro a la
                     * tabla para mostrarla (previamente
                     * la vaciamos)
                     */
                    table.setRowCount(0);
                    for (InfoExtensaAlquiler ar : arrendamientosFiltrados) {
                        Object[] fila = {
                            ar.getNumExp(),
                            ar.getPrecio(),
                            ar.isPagado(),
                            formato.format(ar.getFechaEntrada()),
                            formato.format(ar.getFechaSalida()),
                            ar.getNombreCl(), ar.getNombrePr()
                        };
                        
                        table.addRow(fila);
                    }
                    
                    hist.btnBack.addActionListener((ActionEvent) ->{
                        hist.setVisible(false);
                        init.setVisible(true);
                    });
                }
            });

        }
        
        
        
        /**
         * Si pulsamos sobre la consulta acerca
         * de las VIVIENDAS SIN OCUPAR
         */
        if (e.getSource() == init.rbSin){
            /**
             * la lista de viviendas serán los
             * registros que devuelve el método
             * viviendasSinOcupar()
             */
            viviendas = crud.viviendasSinOcupar();
            
            /**
             * Por cada registro, insertamos
             * una fila en la tabla
             */
            table3.setRowCount(0);
            for (Vivienda v: viviendas){
                Object [] fila = {
                    v.getCod_ref(), v.getUbicacion(), v.getMetros(),
                    v.getNumRooms(), v.getNumBathrooms(), v.getPrecioMensual(),
                    v.getPropietario()
                };
                
                table3.addRow(fila);
            }
            /**
             * Hacemos visible la ventana
             * de ViviendasSinOcupar y
             * ocultamos la ventana de inicio
             * empleados
             */
            init.setVisible(false);
            viv.setVisible(true);
            viv.setTitle("Lista de viviendas sin alquilar");
            viv.setResizable(false);
            
            /**
             * Si pulsamos el botón VOLVER 
             * de ViviendasSinOcupar
             */
            viv.btnBack.addActionListener((ActionEvent) ->
            {
                /**
                 * Volvemos a la pantalla IncioEmpleados
                 */
                viv.setVisible(false);
                init.setVisible(true);
            });
        }
    }
    
}
