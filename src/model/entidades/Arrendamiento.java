package model.entidades;

//********************************* PACKAGES ***********************************
import java.util.Date;

/**
 *
 * @author rafacampa9
 * 
 *  Clase que representa
 *  los registros de la 
 *  tabla ARRENDAMIENTOS
 */
public class Arrendamiento implements Entidad{
    
    // ***************************ATRIBUTOS*************************************
    private int numExp, idVivienda;
    private Date fechaEntrada, fechaSalida;
    private boolean pagado;
    private String cliente;
    
    
    // *************************CONSTRUCTORES***********************************
    public Arrendamiento() {
    }

    public Arrendamiento(int numExp, int idVivienda, Date fechaEntrada, 
            Date fechaSalida, boolean pagado, String cliente) {
        this.numExp = numExp;
        this.idVivienda = idVivienda;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.pagado = pagado;
        this.cliente = cliente;
    }
    
    
    //**************************GETTER Y SETTER*********************************
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

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}
