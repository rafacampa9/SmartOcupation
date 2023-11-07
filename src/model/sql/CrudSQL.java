/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.sql;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.entidades.Arrendamiento;
import model.entidades.Cliente;
import model.entidades.Entidad;
import model.entidades.InfoExtensaAlquiler;
import model.entidades.Propietario;
import model.entidades.Vivienda;

/**
 *
 * @author rafacampa9
 */
public class CrudSQL {
    // ATRIBUTOS
    private Conexion conn = new Conexion();
    
    // MÉTODOS
    public ArrayList<Entidad> leer(String tabla){
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String select;
        
        if (tabla.equals("ARRENDAMIENTOS")){
            ArrayList<Entidad> listaArrendamientos = new ArrayList<>();
            Arrendamiento ar;
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            select = "SELECT * FROM " + tabla;
            try {
                ps = conexion.prepareStatement(select);
                rs = ps.executeQuery();
                    
                while(rs.next()){
                    ar = new Arrendamiento();
                    ar.setNumExp(Integer.parseInt(rs.getString("num_exp")));
                    ar.setFechaEntrada(formato.parse(rs.getString("fecha_entrada")));
                    ar.setFechaSalida(formato.parse(rs.getString("fecha_salida")));
                    ar.setCliente(rs.getString("cliente"));
                    ar.setIdVivienda(Integer.parseInt(rs.getString("id_vivienda")));
                    ar.setPagado(rs.getBoolean("pagado"));
                    listaArrendamientos.add(ar);
                }
                return listaArrendamientos;
            } catch (SQLException | ParseException e){
                System.err.println(e.toString());
                return null;
            }  finally {
                try{
                    conexion.close();
                } catch (SQLException e){
                    System.err.println(e.toString());
                }
                    
            }
        }
           
        else if (tabla.equals("CLIENTES")){
            ArrayList<Entidad> listaClientes = new ArrayList<>();
            Cliente c;

            select = "SELECT * FROM " + tabla;
            try {
                ps = conexion.prepareStatement(select);
                rs = ps.executeQuery();
                    
                while(rs.next()){
                    c = new Cliente();
                    c.setDni(rs.getString("dni"));
                    c.setNombre(rs.getString("nombre"));
                    c.setEdad(Integer.parseInt(rs.getString("edad")));
                    c.setEmpleo(rs.getString("empleo"));
                    listaClientes.add(c);
                }
                return listaClientes;
            } catch (SQLException e){
                System.err.println(e.toString());
                return null;
            } finally {
                try{
                    conexion.close();
                } catch (SQLException e){
                    System.err.println(e.toString());
                }
                    
            }
        }

        else if (tabla.equals("PROPIETARIOS")){
            ArrayList<Entidad> listaPropietarios = new ArrayList<>();
            Propietario pr;
            select = "SELECT * FROM " + tabla;
            try {
                ps = conexion.prepareStatement(select);
                rs = ps.executeQuery();
                    
                while(rs.next()){
                    pr = new Propietario();
                    pr.setDni(rs.getString("dni"));
                    pr.setNombre(rs.getString("nombre"));
                    listaPropietarios.add(pr);
                }
                return listaPropietarios;
                
            } catch (SQLException e){
                System.err.println(e.toString());
                return null;
            } finally {
                try{
                    conexion.close();
                } catch (SQLException e){
                    System.err.println(e.toString());
                }
                    
            }
        }

        else{
            ArrayList<Entidad> listaViviendas= new ArrayList<>();
            Vivienda v;
            select = "SELECT * FROM " + tabla;
            try {
                ps = conexion.prepareStatement(select);
                rs = ps.executeQuery();
                    
                while(rs.next()){
                    v = new Vivienda();
                    v.setCod_ref(Integer.parseInt(rs.getString("cod_ref")));
                    v.setUbicacion(rs.getString("ubicacion"));
                    v.setMetros(Integer.parseInt(rs.getString("metros")));
                    v.setNumRooms(Integer.parseInt(rs.getString("num_rooms")));
                    v.setNumBathrooms(Integer.parseInt(rs.getString("num_bathrooms")));
                    v.setPrecioMensual(Double.parseDouble(rs.getString("precio_mensual")));
                    v.setPropietario(rs.getString("propietario"));
                    listaViviendas.add(v);
                }
                return listaViviendas;
                
            } catch (SQLException e){
                System.err.println(e.toString());
                return null;
                
            } finally {
                try{
                    conexion.close();
                } catch (SQLException e){
                    System.err.println(e.toString());
                }
                    
            }
        }
    }
    
    public boolean insertar(String tabla, Entidad objeto) {
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        String insert = "";
        String[] columnas = null;

        // Determina qué tipo de objeto se está pasando y ajusta las columnas y la sentencia SQL en consecuencia
        if ("ARRENDAMIENTOS".equals(tabla)) {
            insert = "INSERT INTO " + tabla +
                 " (fecha_entrada, fecha_salida, cliente, id_vivienda, pagado)"
                 + "VALUES (?, ?, ?, ?, ?)";
            columnas = new String[]{"fechaEntrada", "fechaSalida", "cliente", "idVivienda", "pagado"};
        } else if ("CLIENTES".equals(tabla)) {
            insert = "INSERT INTO " + tabla +
                 " (DNI, NOMBRE, EDAD, EMPLEO) VALUES (?, ?, ?, ?)";
            columnas = new String[]{"dni", "nombre", "edad", "empleo"};
        } else if ("PROPIETARIOS".equals(tabla)) {
            insert = "INSERT INTO " + tabla +
                 " (DNI, NOMBRE) VALUES (?, ?)";
            columnas = new String[]{"dni", "nombre"};
        } else if ("VIVIENDAS".equals(tabla)) {
            insert = "INSERT INTO " + tabla +
                 " (ubicacion, metros, num_rooms, num_bathrooms, precio_mensual, propietario)"
                 + "VALUES (?, ?, ?, ?, ?, ?)";
            columnas = new String[]{"ubicacion", "metros", "numRooms", "numBathrooms", "precioMensual", "propietario"};
        }

        try {
            ps = conexion.prepareStatement(insert);

            // Utiliza reflection para obtener los valores de las propiedades del objeto
            Class<?> clase = objeto.getClass();
            for (int i = 0; i < columnas.length; i++) {
                String columna = columnas[i];
                String metodo;
                
                if (columna.equals("pagado")) 
                    metodo = "is" + columna.substring(0,1).toUpperCase() + columna.substring(1);
                else if (columna.equals("num_exp")) 
                    metodo = "getNumExp";
                else 
                    metodo = "get" + columna.substring(0, 1).toUpperCase() + columna.substring(1);
                
                Object valor = clase.getMethod(metodo).invoke(objeto);

                // El tipo de dato de la columna determinará el método de set correspondiente
                if (valor instanceof String) {
                    ps.setString(i + 1, (String) valor);
                } else if (valor instanceof Integer) {
                    ps.setInt(i + 1, (Integer) valor);
                } else if (valor instanceof Boolean) {
                    ps.setBoolean(i + 1, (Boolean) valor);
                } else if (valor instanceof Double) {
                    ps.setDouble(i + 1, (Double) valor);
                } else if (valor instanceof Date){
                    java.sql.Date fechaSql = new java.sql.Date(((Date) valor).getTime());
                    ps.setDate(i + 1, fechaSql);
                }
            }

            ps.execute();
            return true;
        } catch (SQLException | NoSuchMethodException
                | SecurityException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e){
            System.err.println(e.toString());
            return false;
        }finally {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println(e.toString());
            }
        }   
    }
        
    public boolean modificar(String tabla, Entidad objeto) {
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        String update = "";
        String[] columnas = null;

        // Determina qué tipo de objeto se está pasando y ajusta las columnas y la sentencia SQL en consecuencia
        if ("ARRENDAMIENTOS".equals(tabla)) {
            update = "UPDATE " + tabla +
                 " SET fecha_entrada = ?, fecha_salida = ?, "
                    + "cliente = ?, id_vivienda = ?, pagado = ?"
                    + " WHERE num_exp = ?";
            
            columnas = new String[]{"fechaEntrada", "fechaSalida", "cliente", 
                                    "idVivienda", "pagado", "num_exp"};
        } else if ("CLIENTES".equals(tabla)) {
            update = "UPDATE " + tabla +
                 " SET NOMBRE = ?, EDAD = ?, EMPLEO = ?"
                    + " WHERE DNI = ?";
            columnas = new String[]{"nombre", "edad", "empleo", "dni"};
        } else if ("PROPIETARIOS".equals(tabla)) {
            update = "UPDATE " + tabla +
                 " SET nombre = ? WHERE dni = ?";
            columnas = new String[]{"nombre", "dni"};
        } else if ("VIVIENDAS".equals(tabla)) {
            update = "UPDATE " + tabla +
                 " SET ubicacion = ?, metros = ?, "
                    + "num_rooms = ?, num_bathrooms = ?, "
                    + "precio_mensual = ?, propietario = ? "
                 + "WHERE cod_ref = ?";
            columnas = new String[]{"ubicacion", "metros", "numRooms", "numBathrooms", 
                                    "precioMensual", "propietario", "cod_ref"};
        }

        try {
            ps = conexion.prepareStatement(update);

            // Utiliza reflection para obtener los valores de las propiedades del objeto
            Class<?> clase = objeto.getClass();
            for (int i = 0; i < columnas.length; i++) {
                String columna = columnas[i];
                String metodo;
                if (columna.equals("pagado")) 
                    metodo = "is" + columna.substring(0,1).toUpperCase() + columna.substring(1);
                else if (columna.equals("num_exp")) 
                    metodo = "getNumExp";
                else 
                    metodo = "get" + columna.substring(0, 1).toUpperCase() + columna.substring(1);
                
                Object valor = clase.getMethod(metodo).invoke(objeto);
                

                // El tipo de dato de la columna determinará el método de set correspondiente
                if (valor instanceof String) {
                    ps.setString(i + 1, (String) valor);
                } else if (valor instanceof Integer) {
                    ps.setInt(i + 1, (Integer) valor);
                } else if (valor instanceof Boolean) {
                    ps.setBoolean(i + 1, (Boolean) valor);
                } else if (valor instanceof Double) {
                    ps.setDouble(i + 1, (Double) valor);
                }else if (valor instanceof Date){
                    java.sql.Date fechaSql = new java.sql.Date(((Date) valor).getTime());
                    ps.setDate(i + 1, fechaSql);
                }
            }
            
            ps.execute();
            return true;
        } catch (SQLException | NoSuchMethodException
                | SecurityException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e){
            System.err.println(e.toString());
            return false;
        } finally {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println(e.toString());
            }
        }   
    }
    
    public boolean eliminar (String tabla, Entidad objeto){
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        String delete = "";
        String [] columnas = null;

        // Determina qué tipo de objeto se está pasando y ajusta las columnas y la sentencia SQL en consecuencia
        if ("ARRENDAMIENTOS".equals(tabla)) {
            delete = "DELETE FROM " + tabla
                    + " WHERE num_exp = ?";
            
            columnas = new String[]{"num_exp"};
        } else if ("CLIENTES".equals(tabla)) {
            delete = "DELETE FROM " + tabla 
                    + " WHERE DNI = ?";
            columnas = new String[]{"dni"};
        } else if ("PROPIETARIOS".equals(tabla)) {
            delete = "DELETE FROM " + tabla +
                 " WHERE dni = ?";
            columnas = new String[]{"dni"};
        } else if ("VIVIENDAS".equals(tabla)) {
            delete = "DELETE FROM " + tabla 
                 + " WHERE cod_ref = ?";
            columnas = new String[]{"cod_ref"};
        }

        try {
            ps = conexion.prepareStatement(delete);

            // Utiliza reflection para obtener los valores de las propiedades del objeto
            Class<?> clase = objeto.getClass();
            for (int i = 0; i < columnas.length; i++) {
                String columna = columnas[i];
                String metodo;
                
                if (columna.equals("pagado")) 
                    metodo = "is" + columna.substring(0,1).toUpperCase() + columna.substring(1);
                else if (columna.equals("num_exp")) 
                    metodo = "getNumExp";
                else 
                    metodo = "get" + columna.substring(0, 1).toUpperCase() + columna.substring(1);
                
                
                Object valor = clase.getMethod(metodo).invoke(objeto);

                // El tipo de dato de la columna determinará el método de set correspondiente
                if (valor instanceof String) {
                    ps.setString(i + 1, (String) valor);
                } else if (valor instanceof Integer) {
                    ps.setInt(i + 1, (Integer) valor);
                } 
            }

            ps.execute();
            return true;
        }catch (SQLException  | NoSuchMethodException
                | SecurityException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e){
            System.err.println(e.toString());
            return false;
        } finally {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println(e.toString());
            }
        }
    }
    
    public Entidad buscar(String tabla, Entidad objeto){
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        String select = "";
        String[] columnas = null;
        
       

        // Determina qué tipo de objeto se está pasando y ajusta las columnas y la sentencia SQL en consecuencia
        if ("ARRENDAMIENTOS".equals(tabla)) {
            select = "SELECT * FROM " + tabla +
                    " WHERE num_exp = ?";
            
            columnas = new String[]{"num_exp"};
        } else if ("CLIENTES".equals(tabla)) {
            select = "SELECT * FROM " + tabla 
                    + " WHERE DNI = ?";
            columnas = new String[]{"dni"};
        } else if ("PROPIETARIOS".equals(tabla)) {
            select = "SELECT * FROM " + tabla +
                 " WHERE dni = ?";
            columnas = new String[]{"dni"};
        } else if ("VIVIENDAS".equals(tabla)) {
            select = "SELECT * FROM " + tabla + " WHERE cod_ref = ?";
            columnas = new String[]{"cod_ref"};
        }

        try {
            ps = conexion.prepareStatement(select);

            
            Class<?> clase = objeto.getClass();
            for (int i = 0; i < columnas.length; i++) {
                String columna = columnas[i];
                String metodo;
                
                if (columna.equals("pagado")) 
                    metodo = "is" + columna.substring(0,1).toUpperCase() + columna.substring(1);
                else if (columna.equals("num_exp")) 
                    metodo = "getNumExp";
                else 
                    metodo = "get" + columna.substring(0, 1).toUpperCase() + columna.substring(1);
                
                Object valor = clase.getMethod(metodo).invoke(objeto);

                
                if (valor instanceof String) {
                    ps.setString(i + 1, (String) valor);
                } else if (valor instanceof Integer) {
                    ps.setInt(i + 1, (Integer) valor);
                } else if (valor instanceof Boolean) {
                    ps.setBoolean(i + 1, (Boolean) valor);
                } else if (valor instanceof Double) {
                    ps.setDouble(i + 1, (Double) valor);
                }
            }

            ResultSet rs = ps.executeQuery();
            Entidad entidad = null;
            if (rs.next()){
                
                if ("ARRENDAMIENTOS".equals(tabla)) {
                    entidad = new Arrendamiento();
                } else if ("CLIENTES".equals(tabla)) {
                    entidad = new Cliente();
                } else if ("PROPIETARIOS".equals(tabla)) {
                    entidad = new Propietario();
                } else {
                    entidad = new Vivienda();
                }
                
                if (entidad instanceof Arrendamiento){
                    Arrendamiento ar = new Arrendamiento();
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    ar.setNumExp(Integer.parseInt(rs.getString("num_exp")));
                    ar.setFechaEntrada(formato.parse(rs.getString("fecha_entrada")));
                    ar.setFechaSalida(formato.parse(rs.getString("fecha_salida")));
                    ar.setCliente(rs.getString("cliente"));
                    ar.setIdVivienda(Integer.parseInt(rs.getString("id_vivienda")));
                    ar.setPagado(rs.getBoolean("pagado"));
                    entidad = ar;
                } else if (entidad instanceof Cliente){
                    Cliente cl = new Cliente();
                    cl.setDni(rs.getString("dni"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setEdad(Integer.parseInt(rs.getString("edad")));
                    cl.setEmpleo(rs.getString("empleo"));
                    entidad = cl;
                } else if (entidad instanceof Propietario){
                    Propietario pr = new Propietario();
                    pr.setDni(rs.getString("dni"));
                    pr.setNombre(rs.getString("nombre"));
                    entidad = pr;
                } else if (entidad instanceof Vivienda){
                    Vivienda v = new Vivienda();
                    v.setCod_ref(Integer.parseInt(rs.getString("cod_ref")));
                    v.setMetros(Integer.parseInt(rs.getString("metros")));
                    v.setNumRooms(Integer.parseInt(rs.getString("num_rooms")));
                    v.setNumBathrooms(Integer.parseInt(rs.getString("num_bathrooms")));
                    v.setUbicacion(rs.getString("ubicacion"));
                    v.setPropietario(rs.getString("propietario"));
                    v.setPrecioMensual(Double.parseDouble(rs.getString("precio_mensual")));
                    entidad = v;
                }
                return entidad;
            } else{
                // No se encontraron resultados
                return null;
            }
            
        }catch (SQLException | ParseException | NoSuchMethodException
                | SecurityException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e){
            System.err.println(e.toString());
            return null;
        } finally {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println(e.toString());
            }
        }
    }
    
    public ArrayList<InfoExtensaAlquiler> historicoAlquiler(){
        Connection conexion = conn.conectarDB();
        ArrayList<InfoExtensaAlquiler> lista = new ArrayList<>();
        InfoExtensaAlquiler a;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String consulta = """
                          SELECT a.num_exp, v.precio_mensual, a.pagado, a.fecha_entrada, 
                          a.fecha_salida, cl.nombre nombreCl, p.nombre nombrePr
                          FROM ARRENDAMIENTOS a, CLIENTES cl, VIVIENDAS v, PROPIETARIOS p
                          WHERE cl.dni = a.cliente and a.id_vivienda = v.cod_ref 
                          and p.dni = v.propietario
                          """;
        try{
            ps = conexion.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while (rs.next()){
                a = new InfoExtensaAlquiler();
                a.setNumExp(Integer.parseInt(rs.getString("num_exp")));
                a.setFechaEntrada(formato.parse(rs.getString("fecha_entrada")));
                a.setFechaSalida(formato.parse(rs.getString("fecha_salida")));
                a.setNombreCl(rs.getString("nombreCl"));
                a.setPagado(rs.getBoolean("pagado"));
                a.setNombrePr(rs.getString("nombrePr"));
                a.setPrecio(Double.parseDouble(rs.getString("precio_mensual")));
                lista.add(a);   
            }
            return lista;
        } catch (SQLException | ParseException e){
            System.err.println(e.toString());
            return null;
        } finally {
            try{
                conexion.close();
            } catch (SQLException e){
                System.err.println(e.toString());
            }
        }           
    }
    
    public ArrayList<InfoExtensaAlquiler> deudas(){
        Connection conexion = conn.conectarDB();
        ArrayList<InfoExtensaAlquiler> lista = new ArrayList<>();
        InfoExtensaAlquiler a;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
        String consulta = """
                          SELECT cl.nombre nombreCl, cl.edad, cl.empleo, a.num_exp, v.precio_mensual, 
                          p.nombre nombrePr, a.fecha_entrada, a.fecha_salida
                          FROM ARRENDAMIENTOS a, CLIENTES cl, VIVIENDAS v, PROPIETARIOS p
                          WHERE cl.dni = a.cliente and a.id_vivienda = v.cod_ref and 
                          p.dni = v.propietario and a.pagado = False order by
                          nombreCl desc
                          """;
        
        try{
            ps = conexion.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while (rs.next()){
                a = new InfoExtensaAlquiler();
                
                a.setNumExp(Integer.parseInt(rs.getString("num_exp")));
                a.setFechaEntrada(formato.parse(rs.getString("fecha_entrada")));
                a.setFechaSalida(formato.parse(rs.getString("fecha_salida")));
                a.setNombreCl(rs.getString("nombreCl"));
                a.setEdadCl(Integer.parseInt(rs.getString("edad")));
                a.setEmpleoCl(rs.getString("empleo"));
                a.setPrecio(Double.parseDouble(rs.getString("precio_mensual")));
                lista.add(a);
                
            }
            
            return lista;
        } catch (SQLException | ParseException e){
            System.err.println(e.toString());
            return null;
        } finally {
            try{
                conexion.close();
            } catch (SQLException e){
                System.err.println(e.toString());
            }
        }
    }
    
    public ArrayList<InfoExtensaAlquiler> arrendamientosPorFecha(Date fechaEntrada, Date fechaSalida){
        Connection conexion = conn.conectarDB();
        ArrayList<InfoExtensaAlquiler> lista = new ArrayList<>();
        InfoExtensaAlquiler a;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
        String consulta = """
                          SELECT a.num_exp, v.precio_mensual, a.pagado, a.fecha_entrada, a.fecha_salida,
                            cl.nombre nombreCl, p.nombre nombrePr
                          FROM ARRENDAMIENTOS a, CLIENTES cl, VIVIENDAS v, PROPIETARIOS p
                          WHERE cl.dni = a.cliente and a.id_vivienda = v.cod_ref and p.dni = v.propietario and
                          a.fecha_entrada >= ? and a.fecha_salida <= ?
                          """;
        
        try{
            ps = conexion.prepareStatement(consulta);
            ps.setDate(1, new java.sql.Date(fechaEntrada.getTime())); 
            ps.setDate(2, new java.sql.Date(fechaSalida.getTime())); 
            
            rs = ps.executeQuery();
            
            while (rs.next()){
                a = new InfoExtensaAlquiler();
                
                a.setNumExp(Integer.parseInt(rs.getString("num_exp")));
                a.setFechaEntrada(formato.parse(rs.getString("fecha_entrada")));
                a.setFechaSalida(formato.parse(rs.getString("fecha_salida")));
                a.setNombreCl(rs.getString("nombreCl"));
                a.setPagado(rs.getBoolean("pagado"));
                a.setNombrePr(rs.getString("nombrePr"));
                a.setPrecio(Double.parseDouble(rs.getString("precio_mensual")));
                lista.add(a);
                
            }
            
            return lista;
        } catch (SQLException | ParseException e){
            System.err.println(e.toString());
            return null;
        } finally {
            try{
                conexion.close();
            } catch (SQLException e){
                System.err.println(e.toString());
            }
        }
    }
    
    public ArrayList<Vivienda> viviendasSinOcupar(){
        Connection conexion = conn.conectarDB();
        ArrayList<Vivienda> lista = new ArrayList<>();
        Vivienda v;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
        String consulta = """
                          SELECT v.cod_ref, v.ubicacion, v.metros,
                          v.num_rooms, v.num_bathrooms, v.precio_mensual,
                          v.propietario from viviendas v where not exists
                          (select 1 from arrendamientos a where 
                          v.cod_ref = a.id_vivienda)
                          """;
        
        try{
            ps = conexion.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while (rs.next()){
                v = new Vivienda();
                
                v.setCod_ref(Integer.parseInt(rs.getString("cod_ref")));
                v.setUbicacion(rs.getString("ubicacion"));
                v.setMetros(Integer.parseInt(rs.getString("metros")));
                v.setNumRooms(Integer.parseInt(rs.getString("num_rooms")));
                v.setNumBathrooms(Integer.parseInt(rs.getString("num_bathrooms")));
                v.setPropietario(rs.getString("propietario"));
                v.setPrecioMensual(Double.parseDouble(rs.getString("precio_mensual")));
                lista.add(v);
                
            }
            
            return lista;
        } catch (SQLException e){
            System.err.println(e.toString());
            return null;
        } finally {
            try{
                conexion.close();
            } catch (SQLException e){
                System.err.println(e.toString());
            }
        }
    }

}
        
