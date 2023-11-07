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
import model.entidades.Propietario;
import model.sql.CrudSQL;
import view.InicioAdmin;
import view.Propietarios;

/**
 *
 * @author rafacampa9
 */
public class CtrlPropietario implements ActionListener{
    
    // ATRIBUTOS
    private Propietario pr;
    private CrudSQL crud;
    private Propietarios prs;
    
    // CONSTRUCTOR

    public CtrlPropietario(Propietario pr, CrudSQL crud, Propietarios prs) {
        this.pr = pr;
        this.crud = crud;
        this.prs = prs;
        this.prs.jTextField1.addActionListener(this);
        this.prs.jTextField2.addActionListener(this);
        this.prs.btnInsert.addActionListener(this);
        this.prs.btnRead.addActionListener(this);
        this.prs.btnDelete.addActionListener(this);
        this.prs.btnUpdate.addActionListener(this);
        this.prs.btnBuscar.addActionListener(this);
        this.prs.btnVolver.addActionListener(this);
    }
    
    
    public void iniciar(){
        prs.setTitle("Propietarios");
        prs.setLocationRelativeTo(null);
        prs.setResizable(false);
        prs.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    
    public void limpiar(){
        prs.jTextField1.setText(null);
        prs.jTextField2.setText(null);
    }
    
    public void limpiaTabla(){
        DefaultTableModel table = (DefaultTableModel) prs.tabla.getModel();
        table.setRowCount(0);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prs.btnInsert){
            String dni = prs.jTextField1.getText();
            String nombre = prs.jTextField2.getText();

            if (dni != null && !dni.isEmpty() && nombre != null && !nombre.isEmpty()) {
                pr.setDni(dni);
                pr.setNombre(nombre);
            
                Propietario existente = (Propietario)crud.buscar("PROPIETARIOS", pr);
                
                if (existente == null){
                    if(crud.insertar("PROPIETARIOS", pr)){
                        JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                        limpiar();
                        limpiaTabla();
             
                    }else{
                        JOptionPane.showMessageDialog(null, "Ha habido un error",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                        limpiar();
                        limpiaTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Registro ya existente",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, asegúrese de rellenar todos los campos",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                limpiar();
                limpiaTabla();
            }
        }
        
        if (e.getSource() == prs.btnRead){
            limpiar();
            limpiaTabla();
            ArrayList<Entidad> props = crud.leer("PROPIETARIOS");
            ArrayList<Propietario> propietarios = new ArrayList<>();
            for (Entidad propietario: props){
                propietarios.add((Propietario) propietario);
            }
            
            DefaultTableModel model = (DefaultTableModel) prs.tabla.getModel(); 

            for (Propietario propietario: propietarios){
                Object[] fila = {propietario.getDni(), propietario.getNombre()};
                model.addRow(fila); // Agrega la fila al modelo
            }
        }
        
        if (e.getSource() == prs.btnUpdate) {
            String dni = prs.jTextField1.getText();
            String nombre = prs.jTextField2.getText();
            
            if (nombre != null && !nombre.isEmpty()) {
                pr.setDni(dni);
                pr.setNombre(nombre);
                
                Propietario existente = (Propietario) crud.buscar("PROPIETARIOS", pr);
                if (existente != null){
                    if (crud.modificar("PROPIETARIOS", pr)){
                        JOptionPane.showMessageDialog(null, "Registro modificado correctamente.");
                        limpiar();
                        limpiaTabla();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha habido un error",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                        limpiar();
                        limpiaTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El registro que pretende modificar no existe",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    limpiar();
                    limpiaTabla();
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, asegúrese de rellenar todos los campos",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                limpiar();
                limpiaTabla();
            }
        }
        
        if (e.getSource() == prs.btnDelete){
            String dni = prs.jTextField1.getText();
            
            if (dni != null && !dni.isEmpty()){
                pr.setDni(dni);
                
                if(crud.eliminar("PROPIETARIOS", pr)){
                    JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
                    limpiar();
                    limpiaTabla();
                
                } else {
                    JOptionPane.showMessageDialog(null, "Ha habido un error",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    limpiar();
                    limpiaTabla();
                }
            } else{
                JOptionPane.showMessageDialog(null, "Por favor, escriba el DNI del usuario que desea eliminar",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                limpiar();
                limpiaTabla();
            }
        }
        
        if (e.getSource() == prs.btnBuscar){
            limpiaTabla();
            String dni = prs.jTextField1.getText();
            if (!dni.isEmpty()){
                pr.setDni(dni);
                pr = (Propietario) crud.buscar("PROPIETARIOS", pr); // Realizar la búsqueda

                if (pr != null) {
                    DefaultTableModel model = (DefaultTableModel) prs.tabla.getModel();

                    // Limpiar la tabla antes de agregar el resultado
                    model.setRowCount(0);

                    // Agregar el resultado a la tabla
                    Object [] resultado = {pr.getDni(), pr.getNombre()};
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
        
        
        if (e.getSource()== prs.btnVolver){
            InicioAdmin init = new InicioAdmin();
            CtrlInicioAdmin ctrl = new CtrlInicioAdmin(init);
            ctrl.iniciar();
            init.setVisible(true);
            prs.setVisible(false);
        }
            
            
            
    }
    
}
