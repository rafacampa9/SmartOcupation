/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 */
public class CtrlArrendamiento implements ActionListener{
        
    // ATRIBUTOS
    private Arrendamiento ar;
    private CrudSQL crud;
    private Alquiler alq;
    
    // CONSTRUCTOR

    public CtrlArrendamiento(Arrendamiento ar, CrudSQL crud, Alquiler alq) {
        this.ar = ar;
        this.crud = crud;
        this.alq = alq;
        this.alq.btnInsert.addActionListener(this);
        this.alq.btnDelete.addActionListener(this);
        this.alq.btnRead.addActionListener(this);
        this.alq.btnUpdate.addActionListener(this);
        this.alq.btnBack.addActionListener(this);
        this.alq.btnSearch.addActionListener(this);
        this.alq.txtDniCliente.addActionListener(this);
        this.alq.txtIdVivienda.addActionListener(this);
        this.alq.txtNumExp.addActionListener(this);
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

    private void dateChooserPropertyChange (PropertyChangeEvent e){
        if ("date".equals(e.getPropertyName())) {
            if (ar != null) { // Verificar si ar está inicializada
                if (e.getSource() == alq.fechaEntrada) {
                    ar.setFechaEntrada(alq.fechaEntrada.getDate());
                } else if (e.getSource() == alq.fechaSalida) {
                    ar.setFechaSalida(alq.fechaSalida.getDate());
                }
            }
        }
    }
    
    
    public void iniciar(){
        alq.setTitle("Arrendamientos");
        alq.setLocationRelativeTo(null);
        alq.setResizable(false);
        alq.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    
    public void limpiar(){
        alq.fechaEntrada.setDate(null);
        alq.fechaSalida.setDate(null);
        alq.txtDniCliente.setText(null);
        alq.txtIdVivienda.setText(null);
        alq.txtNumExp.setText(null);
    }
    
    public void limpiarTabla(){
        DefaultTableModel table = (DefaultTableModel) alq.tabla.getModel();
        table.setRowCount(0);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    
        int numExp, idVivienda;
        
        if (e.getSource() == alq.btnBack){
            InicioAdmin init = new InicioAdmin();
            CtrlInicioAdmin ctrl = new CtrlInicioAdmin(init);
            ctrl.iniciar();
            init.setVisible(true);
            alq.setVisible(false);
        }
        if (e.getSource() == alq.btnInsert){   
            if (!alq.txtIdVivienda.getText().isEmpty() && alq.txtIdVivienda.getText()!=null)
                idVivienda = Integer.parseInt(alq.txtIdVivienda.getText());
            else
                idVivienda = -1;
            Date fechaEntrada = alq.fechaEntrada.getDate();
            Date fechaSalida = alq.fechaSalida.getDate();
            String dniCliente =alq.txtDniCliente.getText();
            
            String pagado = (String) alq.cbPagado.getSelectedItem();

            // Validar los campos según tus requisitos
            if (fechaEntrada == null || fechaSalida == null ||
                dniCliente == null || idVivienda == -1 || pagado == null) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
            } else{
                ar.setFechaEntrada(fechaEntrada);
                ar.setFechaSalida(fechaSalida);
                ar.setCliente(dniCliente);
                ar.setIdVivienda(idVivienda);
                ar.setPagado(pagado.equals("Sí"));
                
                
                Arrendamiento existente = (Arrendamiento) crud.buscar("ARRENDAMIENTOS", ar);
                if (existente == null){
                    if (crud.insertar("ARRENDAMIENTOS", ar)){
                        JOptionPane.showMessageDialog(null, 
                            "Registro insertado correctamente");
                        limpiar();
                        limpiarTabla();
    
                    } else {
                        JOptionPane.showMessageDialog(null, 
                            "Ha habido un error", 
                            "ERROR", 
                            JOptionPane.ERROR_MESSAGE);
                        limpiar();
                        limpiarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            null, 
                            "Registro ya existente",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                
            }
 
        }
        
        
        if (e.getSource()==alq.btnRead){
            limpiar();
            limpiarTabla();
            ArrayList<Entidad> cls = crud.leer("ARRENDAMIENTOS");
            ArrayList<Arrendamiento> arrendamientos = new ArrayList<>();
            for (Entidad arr: cls){
                arrendamientos.add((Arrendamiento) arr);
            }
            
            DefaultTableModel model = (DefaultTableModel) alq.tabla.getModel(); 
            
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
            
            
            for (Arrendamiento arr: arrendamientos){
                Object[] fila = {
                    arr.getNumExp(), formato.format(arr.getFechaEntrada()),
                    formato.format(arr.getFechaSalida()), arr.getCliente(),
                    arr.getIdVivienda(), arr.isPagado()
                };
                model.addRow(fila); 
            }
        }
        
        if (e.getSource() == alq.btnUpdate){
            if (!alq.txtNumExp.getText().isEmpty() && alq.txtNumExp.getText()!= null)
                numExp = Integer.parseInt(alq.txtNumExp.getText());
            else
                numExp = -1;
            if (!alq.txtIdVivienda.getText().isEmpty() && alq.txtIdVivienda.getText()!=null)
                idVivienda = Integer.parseInt(alq.txtIdVivienda.getText());
            else
                idVivienda = -1;
            Date fechaEntrada = alq.fechaEntrada.getDate();
            Date fechaSalida = alq.fechaSalida.getDate();
            String dniCliente =alq.txtDniCliente.getText();
            
            String pagado = (String) alq.cbPagado.getSelectedItem();
            
            
            if (numExp != -1 && idVivienda != -1 && dniCliente != null && !dniCliente.isEmpty()
                    && fechaEntrada != null && fechaSalida!=null) {
                ar.setNumExp(numExp);
                ar.setFechaEntrada(fechaEntrada);
                ar.setFechaSalida(fechaSalida);
                ar.setCliente(dniCliente);
                ar.setIdVivienda(idVivienda);
                ar.setPagado(pagado.equals("Sí"));
                
                Arrendamiento existente = (Arrendamiento) crud.buscar("ARRENDAMIENTOS", ar);
                if (existente != null){
                    if (crud.modificar("ARRENDAMIENTOS", ar)){
                        JOptionPane.showMessageDialog(null, "Registro modificado correctamente.");
                        limpiar();
                        limpiarTabla();
                    } else {
                        JOptionPane.showMessageDialog(null, 
                            "Ha habido un error",
                                  "ERROR",
                                    JOptionPane.ERROR_MESSAGE
                        );
                        limpiar();
                        limpiarTabla();
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Ha habido un error",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
   
                
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Por favor, introduzca el número de expediente",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                limpiar();
                limpiarTabla();
            }
        }
        
        if (e.getSource() == alq.btnDelete){
            if (!alq.txtNumExp.getText().isEmpty() && alq.txtNumExp.getText()!=null)
                numExp = Integer.parseInt(alq.txtNumExp.getText());
             else
                numExp = -1;
            
            
            if (numExp != -1){
                ar.setNumExp(numExp);
                
                if(crud.eliminar("ARRENDAMIENTOS", ar)){
                    JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
                    limpiar();
                    limpiarTabla();
                
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "Ha habido un error",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    limpiar();
                    limpiarTabla();
                }
            } else{
                JOptionPane.showMessageDialog(null,
                        "Por favor, escriba el número de expediente del alquiler que desea eliminar",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                limpiar();
                limpiarTabla();
            }
        }
        
        if (e.getSource() == alq.btnSearch){
            limpiarTabla();
            if (!alq.txtNumExp.getText().isEmpty() && alq.txtNumExp.getText()!=null)
                numExp = Integer.parseInt(alq.txtNumExp.getText());
             else
                numExp = -1;
            if (numExp != -1){
                ar.setNumExp(numExp);
                ar = (Arrendamiento) crud.buscar("ARRENDAMIENTOS", ar); 

                if (ar != null) {
                    DefaultTableModel model = (DefaultTableModel) alq.tabla.getModel();
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");

                    // Limpiar antes de agregar el resultado
                    model.setRowCount(0);

                    // Agregar el resultado a la tabla
                    Object [] resultado = {
                        ar.getNumExp(), formato.format(ar.getFechaEntrada()),
                        formato.format(ar.getFechaSalida()), ar.getCliente(),
                        ar.getIdVivienda(), ar.isPagado()
                    };
                    model.addRow(resultado);
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontraron resultados");
                    limpiar();
                }
            } else {
                limpiar();
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un DNI",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

    

