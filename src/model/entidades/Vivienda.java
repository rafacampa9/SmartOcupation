package model.entidades;


/**
 *
 * @author rafacampa9
 */
public class Vivienda implements Entidad{
    
    //**********************ATRIBUTOS*******************************************
    private String ubicacion, propietario;
    private int metros, numRooms, numBathrooms, cod_ref;
    private Double precioMensual;
    
    
    //*********************CONSTRUCTORES****************************************
    public Vivienda() {
    }

    public Vivienda(String ubicacion, int metros, int numRooms, 
            int numBathrooms, int cod_ref, double precioMensual, 
            String propietario) {
        this.ubicacion = ubicacion;
        this.metros = metros;
        this.numRooms = numRooms;
        this.numBathrooms = numBathrooms;
        this.cod_ref = cod_ref;
        this.precioMensual = precioMensual;
        this.propietario = propietario;
    }
    
    
    // ********************GETTER Y SETTER**************************************

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public int getMetros() {
        return metros;
    }

    public void setMetros(int metros) {
        this.metros = metros;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public int getCod_ref() {
        return cod_ref;
    }

    public void setCod_ref(int cod_ref) {
        this.cod_ref = cod_ref;
    }

    public double getPrecioMensual() {
        return precioMensual;
    }

    public void setPrecioMensual(Double precioMensual) {
        this.precioMensual = precioMensual;
    } 
}
