/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import model.entidades.GenerarInforme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * @author rafacampa9
 */
public class CtrlInicioEmpleado implements ActionListener{

    // ATRIBUTOS
    private InicioEmpleado init;
    private ButtonGroup grupo;
    
    // CONSTRUCTOR
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

    public void iniciar(){
        init.setTitle("Empleado");
        init.setLocationRelativeTo(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        HistoricoAlquiler hist = new HistoricoAlquiler();
        ListaDeudores morosos = new ListaDeudores();
        ViviendasSinOcupar viv = new ViviendasSinOcupar();
        
        hist.setTitle("Lista Arrendamientos");
        hist.setLocationRelativeTo(null);
        hist.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        morosos.setTitle("Lista Adeudados");
        morosos.setLocationRelativeTo(null);
        morosos.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        viv.setTitle("Viviendas Sin Ocupar");
        viv.setLocationRelativeTo(null);
        viv.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        ArrayList<InfoExtensaAlquiler> arrendamientos;
        ArrayList<Vivienda> viviendas;
        
        DefaultTableModel table = (DefaultTableModel) hist.tabla.getModel();
        DefaultTableModel table2 = (DefaultTableModel) morosos.tabla.getModel();
        DefaultTableModel table3 = (DefaultTableModel) viv.tabla.getModel();
        
        CrudSQL crud = new CrudSQL();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        
        if (e.getSource() == init.rbBack){
            Inicio st = new Inicio();
            CtrlInicio ctrlInit = new CtrlInicio(st);
            ctrlInit.iniciar();
            st.setVisible(true);
            init.setVisible(false);
            
        }
        
        if (e.getSource()== init.rbHist){
            arrendamientos = crud.historicoAlquiler();
            
            
            for (InfoExtensaAlquiler ar : arrendamientos){
                Object [] fila = {ar.getNumExp(), ar.getPrecio(), ar.isPagado(), 
                    formato.format(ar.getFechaEntrada()), 
                    formato.format(ar.getFechaSalida()), 
                    ar.getNombreCl(), ar.getNombrePr()};
                
                table.addRow(fila);
            }
            hist.setVisible(true);
            hist.btnInf.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GenerarInforme info = new GenerarInforme();
                    String rutaInforme = "D:\\\\Escritorio\\\\fp DAM\\\\2º Año\\\\Desarrollo de Interfaces\\\\Trabajo";
                    String nombreInforme = "SmartOcupation.jrxml";
                    info.generarInforme(rutaInforme, nombreInforme);
                }
                           
            });
            
        }
        
        if (e.getSource() == init.rbDeuda){
            arrendamientos = crud.deudas();
            
            for (InfoExtensaAlquiler ar: arrendamientos){
                Object [] fila = {ar.getNombreCl(), ar.getEdadCl(), ar.getEmpleoCl(),
                    ar.getNumExp(), ar.getPrecio(),
                    formato.format(ar.getFechaEntrada()), 
                    formato.format(ar.getFechaSalida())};
                
                table2.addRow(fila);
            }
            morosos.setVisible(true);
        }
        
        if (e.getSource() == init.rbDate){
            Fechas fecha = new Fechas();
            fecha.setTitle("Seleccionar Fechas");
            fecha.setLocationRelativeTo(null);
            fecha.setVisible(true);
            fecha.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            
            fecha.btnSend.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    Date fechaEntrada = fecha.fechaEntrada.getDate();
                    Date fechaSalida = fecha.fechaSalida.getDate();

                    if (fechaEntrada == null || fechaSalida == null) {
                        JOptionPane.showMessageDialog(null,
                                "Por favor, introduzca las fechas",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        fecha.setVisible(false);
                        hist.setVisible(true);
                        hist.btnInf.setVisible(false);
                        ArrayList<InfoExtensaAlquiler>arrendamientosFiltrados = 
                                crud.arrendamientosPorFecha(fechaEntrada, fechaSalida);

                        for (InfoExtensaAlquiler ar : arrendamientosFiltrados) {
                            Object[] fila = {ar.getNumExp(), ar.getPrecio(), ar.isPagado(),
                                    formato.format(ar.getFechaEntrada()), 
                                    formato.format(ar.getFechaSalida()),
                                    ar.getNombreCl(), ar.getNombrePr()};

                            table.addRow(fila);
                        }
                    }
                }
            });

        }
        
        if (e.getSource() == init.rbSin){
            viviendas = new ArrayList<>();
            viviendas = crud.viviendasSinOcupar();
            
            for (Vivienda v: viviendas){
                Object [] fila = {
                    v.getCod_ref(), v.getUbicacion(), v.getMetros(),
                    v.getNumRooms(), v.getNumBathrooms(), v.getPrecioMensual(),
                    v.getPropietario()
                };
                
                table3.addRow(fila);
            }
            
            viv.setVisible(true);
        }
    }
    
}
