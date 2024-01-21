/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.entidades;

/**
 *
 * @author rafacampa9
 */
public class Propietario implements Entidad{
    
    //************************ATRIBUTOS*****************************************
    private String dni, nombre;
    
    //**********************CONSTRUCTORES**************************************
    public Propietario() {
    }

    public Propietario(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }
    
    //********************* GETTER Y SETTER ************************************

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
