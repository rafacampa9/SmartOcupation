package model.entidades;

/**
 *
 * @author rafacampa9
 */
public class Cliente implements Entidad{
    
    //************************ ATRIBUTOS****************************************
    private String dni, nombre, empleo;
    private int edad;
    
    
    //***********************CONSTRUCTORES**************************************
    public Cliente() {
    }

    public Cliente(String dni, String nombre, String empleo, int edad) {
        this.dni = dni;
        this.nombre = nombre;
        this.empleo = empleo;
        this.edad = edad;
    }

    //********************** GETTER Y SETTER************************************
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

    public String getEmpleo() {
        return empleo;
    }

    public void setEmpleo(String empleo) {
        this.empleo = empleo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    //********************** TOSTRING *****************************************

    @Override
    public String toString() {
        return "Cliente{" + 
                "dni=" + dni + 
                ", nombre=" + nombre + 
                ", empleo=" + empleo + 
                ", edad=" + edad + 
                '}';
        
    }
    
    
}
