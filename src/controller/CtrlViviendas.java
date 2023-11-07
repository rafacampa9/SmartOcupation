/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
 */
public class CtrlViviendas implements ActionListener{
    
    //ATRIBUTOS
    private Vivienda vivienda;
    private CrudSQL crud;
    private Viviendas v;
    
    // CONSTRUCTOR

    public CtrlViviendas(Vivienda vivienda, CrudSQL crud, Viviendas v) {
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
    
    public void iniciar(){
        v.setTitle("VIVIENDAS");
        v.setLocationRelativeTo(null);
        v.setResizable(false);
        v.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    
    public void limpiar(){
        v.txtCodRef.setText(null);
        v.txtDNI.setText(null);
        v.txtNumBath.setText(null);
        v.txtNumRooms.setText(null);
        v.txtPrecio.setText(null);
        v.txtUbi.setText(null);
        v.txtMetros.setText(null);
    }
    
    public void limpiarTabla(){
        DefaultTableModel table = (DefaultTableModel) v.tabla.getModel();
        table.setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int numRooms, numBath, metros, codRef;
        Double precio;
        
        if (e.getSource() == v.btnBack){
            InicioAdmin init = new InicioAdmin();
            CtrlInicioAdmin ctrl = new CtrlInicioAdmin(init);
            ctrl.iniciar();
            init.setVisible(true);
            v.setVisible(false);
        }
        
        if (e.getSource() == v.btnInsert){
            String dni = v.txtDNI.getText();
            String ubicacion = v.txtUbi.getText();
            
            
            if (!v.txtMetros.getText().isEmpty() && v.txtMetros.getText() != null)
                metros = Integer.parseInt(v.txtMetros.getText());
            else
                metros = -1;
            if (!v.txtNumRooms.getText().isEmpty() && v.txtNumRooms.getText() != null){
                numRooms = Integer.parseInt(v.txtNumRooms.getText());
            } else{
                numRooms = -1;
            }
            if (!v.txtNumBath.getText().isEmpty() && v.txtNumBath.getText() != null){
                numBath = Integer.parseInt(v.txtNumBath.getText());
            }else {
                numBath = -1;
            }
            if (!v.txtPrecio.getText().isEmpty() && v.txtPrecio.getText() != null){
                precio = Double.parseDouble(v.txtPrecio.getText());
            } else {
                precio = -1.0;
            }
            

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
            
            Vivienda existente = (Vivienda) crud.buscar("VIVIENDAS", vivienda);
            
            if (existente == null){
                if (crud.insertar("VIVIENDAS", vivienda)){
                    JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                    limpiar();
                    limpiarTabla();
    
                } else {
                    JOptionPane.showMessageDialog(null, "Ha habido un error",
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
        
        if (e.getSource() == v.btnRead){
            limpiar();
            limpiarTabla();
            ArrayList<Entidad> cls = crud.leer("VIVIENDAS");
            ArrayList<Vivienda> viviendas = new ArrayList<>();
            for (Entidad viv: cls){
                viviendas.add((Vivienda) viv);
            }
            
            DefaultTableModel model = (DefaultTableModel) v.tabla.getModel(); 

            for (Vivienda viv: viviendas){
                Object[] fila = {viv.getCod_ref(), viv.getUbicacion(),
                                viv.getMetros(), viv.getNumRooms(),
                                viv.getNumBathrooms(),viv.getPropietario(),
                                viv.getPrecioMensual()};
                model.addRow(fila); // Agrega la fila al modelo
            }
        }
        
        if (e.getSource() == v.btnDelete){
            if (!v.txtCodRef.getText().isEmpty() && v.txtCodRef.getText()!=null)
                codRef = Integer.parseInt(v.txtCodRef.getText());
             else
                codRef = -1;
            
            
            if (codRef != -1){
                vivienda.setCod_ref(codRef);
                
                if(crud.eliminar("VIVIENDAS", vivienda)){
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
                        "Por favor, escriba el número de referencia de la vivienda que desea eliminar",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                limpiar();
                limpiarTabla();
            }
        }
        
        if (e.getSource() == v.btnUpdate){

            if (!v.txtCodRef.getText().isEmpty() && v.txtCodRef.getText()!= null)
                codRef = Integer.parseInt(v.txtCodRef.getText());
            else
                codRef = -1;
            
            if (!v.txtMetros.getText().isEmpty() && v.txtMetros.getText()!=null)
                metros = Integer.parseInt(v.txtMetros.getText());
            else
                metros = -1;
            
            if (!v.txtNumBath.getText().isEmpty() && v.txtNumBath.getText() != null)
                numBath = Integer.parseInt(v.txtNumBath.getText());
            else
                numBath = -1;
            
            if (!v.txtNumRooms.getText().isEmpty() && v.txtNumRooms.getText() != null)
                numRooms = Integer.parseInt(v.txtNumRooms.getText());
            else
                numRooms = -1;
            
            if (!v.txtPrecio.getText().isEmpty() && v.txtPrecio.getText() != null)
                precio = Double.parseDouble(v.txtPrecio.getText());
            else
                precio = -1.0;
            
            String ubicacion = v.txtUbi.getText();
            String propietario = v.txtDNI.getText();
            
            if (codRef != -1) {
                vivienda.setCod_ref(codRef);
                vivienda.setUbicacion(ubicacion);
                vivienda.setPropietario(propietario);
                if (metros != -1)
                    vivienda.setMetros(metros);
                if (numBath != -1)
                    vivienda.setNumBathrooms(numBath);
                if (numRooms != -1)
                    vivienda.setNumRooms(numRooms);
                if (precio != -1.0)
                    vivienda.setPrecioMensual(precio);
                
                Vivienda existente = (Vivienda) crud.buscar("VIVIENDAS", vivienda);
                
                if (existente!=null){
                    if (crud.modificar("VIVIENDAS", vivienda)){
                        JOptionPane.showMessageDialog(null, "Registro modificado correctamente.");
                        limpiar();
                        limpiarTabla();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha habido un error",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                        limpiar();
                        limpiarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ha habido un error",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    limpiar();
                    limpiarTabla();
                }
   
                
            } else {
                JOptionPane.showMessageDialog(null, 
                        "Por favor, introduzca el código de referencia",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                limpiar();
                limpiarTabla();
            }
        }
        
        if (e.getSource() == v.btnSearch){
            limpiarTabla();
            if (!v.txtCodRef.getText().isEmpty() && v.txtCodRef.getText()!=null)
                codRef = Integer.parseInt(v.txtCodRef.getText());
            else
                codRef = -1;
            
            if (codRef != -1){
                vivienda.setCod_ref(codRef);
                vivienda = (Vivienda) crud.buscar("VIVIENDAS", vivienda); 

                if (vivienda != null) {
                    DefaultTableModel model = (DefaultTableModel) v.tabla.getModel();

                    // Limpiar antes de agregar el resultado
                    model.setRowCount(0);

                    // Agregar el resultado a la tabla
                    Object [] resultado = {vivienda.getCod_ref(), vivienda.getUbicacion(),
                                           vivienda.getMetros(), vivienda.getNumRooms(),
                                           vivienda.getNumBathrooms(), vivienda.getPropietario(),
                                           vivienda.getPrecioMensual()};
                    model.addRow(resultado);
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontraron resultados");
                    limpiar();
                }
            } else {
                limpiar();
                JOptionPane.showMessageDialog(null, "Por favor, ingrese el código de referencia",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
            }
        }
    }
        
}
    
    

