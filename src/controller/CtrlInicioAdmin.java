/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.entidades.Arrendamiento;
import model.entidades.Cliente;
import model.entidades.Propietario;
import model.entidades.Vivienda;
import model.sql.CrudSQL;
import view.Alquiler;
import view.Clientes;
import view.Inicio;
import view.InicioAdmin;
import view.Propietarios;
import view.Viviendas;

/**
 *
 * @author rafacampa9
 */
public class CtrlInicioAdmin implements ActionListener{

    // ATRIBUTOS
    InicioAdmin init = new InicioAdmin();
    
    // CONSTRUCTOR
    
    public CtrlInicioAdmin(InicioAdmin init) {
        this.init = init;
        this.init.rbArrendamientos.addActionListener(this);
        this.init.rbClientes.addActionListener(this);
        this.init.rbInicio.addActionListener(this);
        this.init.rbPropietarios.addActionListener(this);
        this.init.rbViviendas.addActionListener(this);
    }
    
    public void iniciar(){
        init.setTitle("Administrador");
        init.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CrudSQL crud = new CrudSQL();
        if (e.getSource() == init.rbClientes){
            Cliente cliente = new Cliente();
            Clientes cl = new Clientes();
            
            CtrlClientes ctrl = new CtrlClientes(cliente, crud, cl);
            ctrl.iniciar();
            cl.setVisible(true);
            init.setVisible(false);
        }
        
        if (e.getSource() == init.rbPropietarios){
            Propietario propietario = new Propietario();
            Propietarios prop = new Propietarios();
            
            CtrlPropietario ctrl = new CtrlPropietario (propietario, crud, prop);
            ctrl.iniciar();
            prop.setVisible(true);
            init.setVisible(false);
        }
        
        if (e.getSource() == init.rbInicio){
            Inicio inicio = new Inicio();
            CtrlInicio ctrl = new CtrlInicio(inicio);
            ctrl.iniciar();
            inicio.setVisible(true);
            init.setVisible(false);
        }
        
        if (e.getSource() == init.rbViviendas){
            Vivienda vivienda = new Vivienda();
            Viviendas v = new Viviendas();
            CtrlViviendas ctrl = new CtrlViviendas(vivienda, crud, v);
            ctrl.iniciar();
            v.setVisible(true);
            init.setVisible(false);
        }
        
        if (e.getSource() == init.rbArrendamientos){
            Arrendamiento arr = new Arrendamiento();
            Alquiler alq = new Alquiler();
            CtrlArrendamiento ctrl = new CtrlArrendamiento(arr, crud, alq);
            ctrl.iniciar();
            alq.setVisible(true);
            init.setVisible(false);
            
        }
    }
    
}
