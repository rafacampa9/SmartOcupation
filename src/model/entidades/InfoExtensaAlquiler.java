package model.entidades;

import java.util.Date;

/**
 *
 * @author rafacampa9
 */
public class InfoExtensaAlquiler {
    
    //**********************ATRIBUTOS*******************************************
    private int numExp, idVivienda, metros, numRooms, numBathrooms, edadCl;
    private Double precio;
    private boolean pagado;
    private String nombreCl, nombrePr, ubicacion, empleoCl;
    private Date fechaEntrada, fechaSalida;
    
    //******************** CONSTRUCTORES****************************************

    public InfoExtensaAlquiler() {
    }

    public InfoExtensaAlquiler(int numExp, int idVivienda, int metros, 
            int numRooms, int numBathrooms, int edadCl, Double precio,
            boolean pagado, String nombreCl, String nombrePr, String ubicacion,
            String empleo, Date fechaEntrada, Date fechaSalida) {
        this.numExp = numExp;
        this.idVivienda = idVivienda;
        this.metros = metros;
        this.numRooms = numRooms;
        this.numBathrooms = numBathrooms;
        this.edadCl = edadCl;
        this.precio = precio;
        this.pagado = pagado;
        this.nombreCl = nombreCl;
        this.nombrePr = nombrePr;
        this.ubicacion = ubicacion;
        this.empleoCl = empleoCl;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        
    }
    
    
    //************************GETTERS AND SETTERS ******************************
    public int getEdadCl() {
        return edadCl;
    }

    public void setEdadCl(int edadCl) {
        this.edadCl = edadCl;
    }

    public String getEmpleoCl() {
        return empleoCl;
    }

    // GETTER Y SETTER
    public void setEmpleoCl(String empleoCl) {
        this.empleoCl = empleoCl;
    }

    public int getNumExp() {
        return numExp;
    }

    public void setNumExp(int numExp) {
        this.numExp = numExp;
    }

    public int getIdVivienda() {
        return idVivienda;
    }

    public void setIdVivienda(int idVivienda) {
        this.idVivienda = idVivienda;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public String getNombreCl() {
        return nombreCl;
    }

    public void setNombreCl(String nombreCl) {
        this.nombreCl = nombreCl;
    }

    public String getNombrePr() {
        return nombrePr;
    }

    public void setNombrePr(String nombrePr) {
        this.nombrePr = nombrePr;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    
}
