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
import model.entidades.Cliente;
import model.entidades.Entidad;
import model.sql.CrudSQL;
import view.Clientes;
import view.InicioAdmin;

/**
 *
 * @author rafacampa9
 */
public class CtrlClientes implements ActionListener{
    
    // ATRIBUTOS
    private Cliente cliente;
    private CrudSQL crud;
    private Clientes cl;
    
    // CONSTRUCTOR

    public CtrlClientes(Cliente cliente, CrudSQL crud, Clientes cl) {
        this.cliente = cliente;
        this.crud = crud;
        this.cl = cl;
        this.cl.btnDelete.addActionListener(this);
        this.cl.btnRead.addActionListener(this);
        this.cl.btnInsert.addActionListener(this);
        this.cl.btnSearch.addActionListener(this);
        this.cl.btnUpdate.addActionListener(this);
        this.cl.txtEdad.addActionListener(this);
        this.cl.txtDNI.addActionListener(this);
        this.cl.txtEmpleo.addActionListener(this);
        this.cl.txtNombre.addActionListener(this);
        this.cl.btnBack.addActionListener(this);
    }

    public void iniciar(){
        cl.setTitle("Propietarios");
        cl.setLocationRelativeTo(null);
        cl.setResizable(false);
        cl.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    
    public void limpiar(){
        cl.txtDNI.setText(null);
        cl.txtNombre.setText(null);
        cl.txtEmpleo.setText(null);
        cl.txtEdad.setText(null);
    }
    
    public void limpiaTabla(){
        DefaultTableModel table = (DefaultTableModel) cl.tabla.getModel();
        table.setRowCount(0);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cl.btnInsert){
            String dni = cl.txtDNI.getText();
            String nombre = cl.txtNombre.getText();
            int edad;
            if (!cl.txtEdad.getText().isEmpty()){
                edad = Integer.parseInt(cl.txtEdad.getText());
            } else{
                edad = -1;
            }
            String empleo = cl.txtEmpleo.getText();

            if (dni != null && !dni.isEmpty() && nombre != null && !nombre.isEmpty()) {
                cliente.setDni(dni);
                cliente.setNombre(nombre);
                cliente.setEmpleo(empleo);
                if (edad != -1){
                    cliente.setEdad(edad);
                }

            
                if(crud.insertar("CLIENTES", cliente)){
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
                JOptionPane.showMessageDialog(null, "Por favor, asegúrese de rellenar todos los campos",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                limpiar();
                limpiaTabla();
            }
        }
        
        if (e.getSource() == cl.btnRead){
            limpiar();
            limpiaTabla();
            ArrayList<Entidad> cls = crud.leer("CLIENTES");
            ArrayList<Cliente> clientes = new ArrayList<>();
            for (Entidad cliente: cls){
                clientes.add((Cliente) cliente);
            }
            
            DefaultTableModel model = (DefaultTableModel) cl.tabla.getModel(); 

            for (Cliente cliente: clientes){
                Object[] fila = {cliente.getDni(), cliente.getNombre(),
                                cliente.getEdad(), cliente.getEmpleo()};
                model.addRow(fila); // Agrega la fila al modelo
            }
        }
        
        if (e.getSource() == cl.btnUpdate){
            String dni = cl.txtDNI.getText();
            String nombre = cl.txtNombre.getText();
            int edad;
            if (!cl.txtEdad.getText().isEmpty()){
                edad = Integer.parseInt(cl.txtEdad.getText());
            } else{
                edad = -1;
            }
                
            String empleo = cl.txtEmpleo.getText();
            
            if (nombre != null && !nombre.isEmpty()) {
                cliente.setDni(dni);
                cliente.setNombre(nombre);
                cliente.setEmpleo(empleo);
            if (edad != -1){
                cliente.setEdad(edad);
            } 
   
                if (crud.modificar("CLIENTES", cliente)){
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
                JOptionPane.showMessageDialog(null, "Por favor, asegúrese de rellenar todos los campos necesarios",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                limpiar();
                limpiaTabla();
            }
        }
        
        if (e.getSource() == cl.btnDelete){
            String dni = cl.txtDNI.getText();
            
            if (dni != null && !dni.isEmpty()){
                cliente.setDni(dni);
                
                if(crud.eliminar("CLIENTES", cliente)){
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
        
        if (e.getSource() == cl.btnSearch){
            limpiaTabla();
            String dni = cl.txtDNI.getText();
            if (!dni.isEmpty()){
                cliente.setDni(dni);
                cliente = (Cliente) crud.buscar("CLIENTES", cliente); 

                if (cliente != null) {
                    DefaultTableModel model = (DefaultTableModel) cl.tabla.getModel();

                    // Limpiar antes de agregar el resultado
                    model.setRowCount(0);

                    // Agregar el resultado a la tabla
                    Object [] resultado = {cliente.getDni(), cliente.getNombre(),
                                           cliente.getEdad(), cliente.getEmpleo()};
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
        
        if (e.getSource() == cl.btnBack){
            InicioAdmin init = new InicioAdmin();
            CtrlInicioAdmin ctrl = new CtrlInicioAdmin(init);
            ctrl.iniciar();
            init.setVisible(true);
            cl.setVisible(false);
        }
    }
    
    
}
