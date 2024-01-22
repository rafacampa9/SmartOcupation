//*********************************** PACKAGES *********************************
package model.sql;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import model.entidades.Arrendamiento;
import model.entidades.Cliente;
import model.entidades.Entidad;
import model.entidades.InfoExtensaAlquiler;
import model.entidades.Propietario;
import model.entidades.Vivienda;

/**
 *
 * @author rafacampa9
 * 
 * Clase que se encarga de 
 * realizar las consultas
 * SQL a la BBDD
 */
public class CrudSQL {
    //*************************** ATRIBUTOS ************************************
    private final Conexion conn = new Conexion();
    
    //**************************** MÉTODOS**************************************
    /**
     * SELECT * FROM tabla
     * 
     * Dependiendo de la clase a la que vaya dirigida
     * la consulta, el LinkedHashSet almacenará objetos
     * de cualquiera de los cuatro posibles:
     * - ARRENDAMIENTOS
     * - CLIENTES
     * - PROPIETARIOS
     * - VIVIENDAS
     * 
     * Por eso, devolvemos una lista de Entidad
     * (interfaz de la que implementan las clases mencionadas
     * arriba)
     * 
     * @param tabla
     * @return 
     */
    public LinkedHashSet<Entidad> leer(String tabla){
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String select;
        /**
         * Si la tabla corresponde a ARRENDAMIENTOS:
         * 
         * Almacenamos los registros en un LinkedHashSet
         * listaArrendamientos y la devolvemos en la tabla
         */
        if (tabla.equals("ARRENDAMIENTOS")){
            LinkedHashSet<Entidad> listaArrendamientos = new LinkedHashSet<>();
            Arrendamiento ar;
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            select = "SELECT * FROM " + tabla;
            try {
                ps = conexion.prepareStatement(select);
                rs = ps.executeQuery();
                /**
                 * A nuestro objeto que instanciamos en cada iteración
                 * del bucle, le pasamos como valores los datos que nos
                 * devuelve la consulta, en función del campo correspondiente:
                 * - num_exp
                 * - fecha_entrada
                 * - fecha_salida
                 * - cliente
                 * - id_vivienda
                 * - pagado
                 */    
                while(rs.next()){
                    ar = new Arrendamiento();
                    ar.setNumExp(Integer.parseInt(
                            rs.getString("num_exp"))
                    );
                    ar.setFechaEntrada(formato.parse(
                            rs.getString("fecha_entrada"))
                    );
                    ar.setFechaSalida(formato.parse(
                            rs.getString("fecha_salida"))
                    );
                    ar.setCliente(rs.getString("cliente")
                    );
                    ar.setIdVivienda(Integer.parseInt(
                            rs.getString("id_vivienda"))
                    );
                    ar.setPagado(rs.getBoolean("pagado"
                    ));
                    // agregamos a la lista el objeto Arrendamiento
                    listaArrendamientos.add(ar);
                }
                /**
                 * Retornamos la lista
                 */
                return listaArrendamientos;
            } catch (SQLException | ParseException e){
                /**
                 * Si salta excepción, devolverá null
                 */
                e.printStackTrace();
                return null;
            }  finally {
                /**
                 * Pase lo que pase, cerramos la conexión
                 */
                try{
                    conexion.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
                    
            }
        }
        
        /**
         * En este caso la tabla es CLIENTES.
         * Los posibles valores son:
         * - dni
         * - nombre
         * - edad
         * - empleo
         */
        else if (tabla.equals("CLIENTES")){
            LinkedHashSet<Entidad> listaClientes = new LinkedHashSet<>();
            Cliente c;
            /**
             * De nuevo SELECT*FROM tabla;
             * ejecutamos la consulta
             */
            select = "SELECT * FROM " + tabla;
            try {
                ps = conexion.prepareStatement(select);
                rs = ps.executeQuery();
                 
                //iteramos sobre el resultado y seteamos nuestro objeto Cliente
                while(rs.next()){
                    c = new Cliente();
                    c.setDni(rs.getString("dni"));
                    c.setNombre(rs.getString("nombre"));
                    c.setEdad(Integer.parseInt(
                            rs.getString("edad")));
                    c.setEmpleo(rs.getString("empleo"));
                    listaClientes.add(c);
                }
                /**
                 * retornamos la listaClientes a la que le hemos agregado
                 * todos los clientes que tenemos en la tabla
                 */
                return listaClientes;
            } catch (SQLException e){
                /**
                 * Si nos salta la excepción,
                 * retornamos nulo
                 */
                e.printStackTrace();
                return null;
            } finally {
                /**
                 * Pase lo que pase,
                 * conexión cerrada
                 */
                try{
                    conexion.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
                    
            }
        }

        /**
         * Si es SELECT * FROM PROPIETARIOS
         */
        else if (tabla.equals("PROPIETARIOS")){
            LinkedHashSet<Entidad> listaPropietarios = new LinkedHashSet<>();
            Propietario pr;
            select = "SELECT * FROM " + tabla;
            try {
                ps = conexion.prepareStatement(select);
                rs = ps.executeQuery();
                
                /**
                 * Iteramos sobre la consulta y seteamos los atributos
                 * del objeto Propietario. Después, agregamos el propietario
                 * a listaPropietarios
                 */
                while(rs.next()){
                    pr = new Propietario();
                    pr.setDni(rs.getString("dni"));
                    pr.setNombre(rs.getString("nombre"));
                    listaPropietarios.add(pr);
                }
                /**
                 * Retornamos la lista de propietarios
                 */
                return listaPropietarios;
            
            } catch (SQLException e){
                /**
                 * Tratamos la posible excepción
                 * retornando null
                 */
                e.printStackTrace();
                return null;
            } finally {
                /**
                 * Pase lo que pase,
                 * conexión cerrada
                 */
                try{
                    conexion.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
                    
            }
        }
        /**
         * Sino , la tabla será VIVIENDAS
         * 
         * SELECT * FROM VIVIENDAS
         */
        else{
            LinkedHashSet<Entidad> listaViviendas= new LinkedHashSet<>();
            Vivienda v;
            select = "SELECT * FROM " + tabla;
            try {
                ps = conexion.prepareStatement(select);
                rs = ps.executeQuery();
                 
                /**
                 * Iteramos sobre cada registro de la consulta
                 * para setear nuestro objeto Vivienda. Después, añadimos
                 * la vivienda a listaViviendas
                 */
                while(rs.next()){
                    v = new Vivienda();
                    v.setCod_ref(Integer.parseInt(
                            rs.getString("cod_ref")));
                    v.setUbicacion(
                            rs.getString("ubicacion"));
                    v.setMetros(Integer.parseInt(
                            rs.getString("metros")));
                    v.setNumRooms(Integer.parseInt(
                            rs.getString("num_rooms")));
                    v.setNumBathrooms(Integer.parseInt(
                            rs.getString("num_bathrooms")));
                    v.setPrecioMensual(Double.valueOf(
                            rs.getString("precio_mensual")));
                    v.setPropietario(
                            rs.getString("propietario"));
                    listaViviendas.add(v);
                }
                /**
                 * retornamos la lista de viviendas
                 */
                return listaViviendas;
               
            } catch (SQLException e){
                /**
                 * Si salta la excepción, 
                 * retornamos null
                 */
                e.printStackTrace();
                return null;
                
            } finally {
                /**
                 * Pase lo que pase,
                 * cierre conexión
                 */
                try{
                    conexion.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
                    
            }
        }
    }
    
    /**
     * Método al que le pasamos el nombre de la tabla
     * y el objeto mediante la interfaz Entidad
     * (recordemos, Entidad es la interfaz que implementa
     * cada una de las clases que hacen referencia a las
     * tablas). El método devuelve true en caso de realizarse
     * la inserción.
     * 
     * @param tabla
     * @param objeto
     * @return 
     */
    public boolean insertar(String tabla, Entidad objeto) {
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        String insert = "";
        String[] columnas = null;


        if (null != tabla) /**
         * Determina qué tipo de objeto se está pasando y
         * ajusta las columnas y la sentencia SQL en
         * consecuencia
         */ switch (tabla)
        {
            case "ARRENDAMIENTOS" ->
            {
                /**
                 * Insertamos los campos
                 * de las tablas a los que
                 * se hace referencia después
                 * de tabla.
                 * En este caso, hace referencia
                 * a la tabla "ARRENDAMIENTOS
                 */
                insert = "INSERT INTO " + tabla +
                        " (num_exp,fecha_entrada, fecha_salida,"
                        + " cliente, id_vivienda, pagado)"
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                //nombre de las columnas
                columnas = new String[]{
                    "num_exp",
                    "fechaEntrada",
                    "fechaSalida",
                    "cliente",
                    "idVivienda",
                    "pagado"
                };
            }
            case "CLIENTES" ->
            {
                /**
                 * Insert de la tabla CLIENTES
                 */
                insert = "INSERT INTO " + tabla +
                        " (DNI, NOMBRE, EDAD, EMPLEO) VALUES (?, ?, ?, ?)";
                //nombre de las columnas
                columnas = new String[]{"dni", "nombre", "edad", "empleo"};
            }
            case "PROPIETARIOS" ->
            {
                /**
                 * INSERT INTO PROPIETARIOS
                 */
                insert = "INSERT INTO " + tabla +
                        " (DNI, NOMBRE) VALUES (?, ?)";
                //columns
                columnas = new String[]{"dni", "nombre"};
            }
            case "VIVIENDAS" ->
            {
                /**
                 * INSERT INTO VIVIENDAS
                 */
                insert = "INSERT INTO " + tabla +
                        " (cod_ref, ubicacion, metros, num_rooms, "
                        + "num_bathrooms,"
                        + " precio_mensual, propietario)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                // colums
                columnas = new String[]{"cod_ref","ubicacion", "metros",
                    "numRooms", "numBathrooms",
                    "precioMensual", "propietario"};
            }
            default ->
            {
            }
        }

        try {
            ps = conexion.prepareStatement(insert);

            /**
             * Obtenemos la clase a la que hace referencia 
             * según la tabla que mencione
             * 
             */
            Class<?> clase = objeto.getClass();
            for (int i = 0; i < columnas.length; i++) {
                String columna = columnas[i];
                String metodo;
                /**
                 * Si la columna es "pagado",
                 * metodo = isPagado
                 * 
                 * Si la columna es "num_exp"
                 * metodo = getNumExp
                 * 
                 * Si no, supongamos 
                 * atr = atributo => metodo = getAtributo
                 * 
                 * Para el resto de atributos de todas las
                 * clases posibles
                 * 
                 */
                if (columna.equals("pagado")) 
                    metodo = "is" + columna.
                                    substring(0,1).
                                    toUpperCase() + 
                            columna.substring(1);                
                else if (columna.equals("num_exp")) 
                    metodo = "getNumExp";
                else 
                    metodo = "get" + columna.
                                    substring(0, 1).
                                    toUpperCase() + 
                            columna.substring(1);
                
                /**
                 * Declaramos valor como el objeto cuyo valor es el método
                 * de la clase a la que hace referencia clase:
                 * - ARRENDAMIENTOS
                 * - CLIENTES
                 * - PROPIETARIOS
                 * - VIVIENDAS
                 * clase hace referencia a la clase que ha escogido de
                 * las cuatro de arriba, y obtenemos el método metodo.
                 * Invocamos al objeto y lo almacenamos en un Object llamado
                 * valor
                 */
                Object valor = clase.getMethod(metodo).invoke(objeto);

                /**
                 * La consulta almacena los valores
                 * que le pasamos en los métodos set dependiendo del tipo
                 * de dato que retorne. En nuestro caso:
                 * - String
                 * - Integer
                 * - Boolean
                 * - Double
                 * - Date
                 */
                
                if (valor instanceof String) {
                    ps.setString(i + 1, (String) valor);
                } else if (valor instanceof Integer) {
                    ps.setInt(i + 1, (Integer) valor);
                } else if (valor instanceof Boolean) {
                    ps.setBoolean(i + 1, (Boolean) valor);
                } else if (valor instanceof Double) {
                    ps.setDouble(i + 1, (Double) valor);
                } else if (valor instanceof Date){
                    java.sql.Date fechaSql = new java.sql.Date(
                            ((Date) valor).getTime());
                    ps.setDate(i + 1, fechaSql);
                }
            }
            /**
             * Ejecutamos la consulta y retornamos true en caso 
             * de que se realice correctamente
             */
            ps.execute();
            return true;
        } catch (SQLException | NoSuchMethodException
                | SecurityException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e){
            /**
             * Si falla, tratamos las excepciones posibles 
             * y devolvemos false.
             */
            e.printStackTrace();
            return false;
        }finally {
            /**
             * Pase lo que pase, cerramos
             * la conexión
             */
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }   
    }
    
    
    /**
     * Método al que le pasamos el nombre de la tabla.
     * Al igual que en los anteriores, posibles valores:
     * - ARRENDAMIENTOS
     * - CLIENTES
     * - PROPIETARIOS
     * - VIVIENDAS
     * 
     * Pasaremos uno de ellos como String y el objeto
     * equivalente a la tabla para realizar el método
     * UPDATE TABLA SET VALORES
     * 
     * @param tabla
     * @param objeto
     * @return 
     */    
    public boolean modificar(String tabla, Entidad objeto) {
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        String update = "";
        String[] columnas = null;

        /**
         * Si la tabla es ARRENDAMIENTOS,
         * pasamos como campos a modificar las columnas
         * de la tabla ARRENDAMIENTOS
         */
        if ("ARRENDAMIENTOS".equals(tabla)) {
            update = "UPDATE " + tabla +
                 " SET fecha_entrada = ?, fecha_salida = ?, "
                    + "cliente = ?, id_vivienda = ?, pagado = ?"
                    + " WHERE num_exp = ?";
            
            columnas = new String[]{"fechaEntrada", "fechaSalida", "cliente", 
                                    "idVivienda", "pagado", "num_exp"};
            /**
             *  CLIENTES
             */
        } else if ("CLIENTES".equals(tabla)) {
            update = "UPDATE " + tabla +
                 " SET NOMBRE = ?, EDAD = ?, EMPLEO = ?"
                    + " WHERE DNI = ?";
            columnas = new String[]{"nombre", "edad", "empleo", "dni"};
            
            /**
             * PROPIETARIOS
             */
        } else if ("PROPIETARIOS".equals(tabla)) {
            update = "UPDATE " + tabla +
                 " SET nombre = ? WHERE dni = ?";
            columnas = new String[]{"nombre", "dni"};
            
            /**
             * VIVIENDAS
             */
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

            //Obtenemos la clase del objeto pasado por parámetro en el método
            Class<?> clase = objeto.getClass();
            for (int i = 0; i < columnas.length; i++) {
                String columna = columnas[i];
                String metodo;
                /**
                 * Si una de las columnas es pagado 
                 * (de la tabla ARRENDAMIENTO), el 
                 * método es "isPagado"
                 * 
                 * Si es num_exp, el método es getNumExp
                 * 
                 * Si no es ninguno de los dos, el método
                 * será del tipo: 
                 * si tenemos atr => getAtr
                 */
                if (columna.equals("pagado")) 
                    metodo = "is" + columna.substring(0,1).
                            toUpperCase() 
                            + columna.substring(1);
                else if (columna.equals("num_exp")) 
                    metodo = "getNumExp";
                else 
                    metodo = "get" + columna.substring(0, 1).
                            toUpperCase() + 
                            columna.substring(1);
                
                /**
                 * Declaramos un objeto de la clase Object
                 * (clase padre de todas las clases) e 
                 * introducimos en el objeto el método 
                 * correspondiente
                 */
                Object valor = clase.getMethod(metodo).invoke(objeto);
                

                /**
                 *  Si el valor (método get) devuelve:
                 * - String
                 * - Integer
                 * - Boolean
                 * - Double
                 * - Date
                 * 
                 * introducimos el valor dentro del
                 * set de la consulta para introducir
                 * los valores 
                 */
                if (valor instanceof String) {
                    ps.setString(i + 1, (String) valor);
                } else if (valor instanceof Integer) {
                    ps.setInt(i + 1, (Integer) valor);
                } else if (valor instanceof Boolean) {
                    ps.setBoolean(i + 1, (Boolean) valor);
                } else if (valor instanceof Double) {
                    ps.setDouble(i + 1, (Double) valor);
                }else if (valor instanceof Date){
                    java.sql.Date fechaSql = 
                            new java.sql.Date(((Date) valor).getTime());
                    ps.setDate(i + 1, fechaSql);
                }
            }
            /**
             * Ejecutamos consulta y retornamos
             * true en caso de que se realice 
             * correctamente
             */
            ps.execute();
            return true;
        } catch (SQLException | NoSuchMethodException
                | SecurityException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e){
            e.printStackTrace();
            /**
             * Si salta la excepción,
             * retornamos false
             */
            return false;
        } finally {
            /**
             * Pase lo que pase,
             * cierre conexión
             */
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }   
    }
    
    
    
    /**
     * Método al que le pasamos la tabla y el objeto
     * que hace referencia al registro, y lo eliminamos
     * de la base de datos
     * 
     * Retorna true si el borrado se realiza correctamente
     * y falso en caso contrario (si salta una excepción)
     * 
     * @param tabla (nombre de la tabla)
     * @param objeto (registro)
     * @return 
     */
    public boolean eliminar (String tabla, Entidad objeto){
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        String delete = "";
        String [] columnas = null;

        /**
         * Dependiendo de que tabla sea, tiene su propio
         * id para acceder al registro y borrarlo (al igual
         * que para modificarlo)
         * 
         * - ARRENDAMIENTOS: id = num_exp
         * - CLIENTES: id = dni
         * - PROPIETARIOS: id = dni
         * - VIVIENDAS: id = cod_ref
         */
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

            /**
             * Al igual que en los casos anteriores,
             * determinamos a qué clase hace referencia
             * el objeto pasado por parámetro
             */
            Class<?> clase = objeto.getClass();
            /**
             *  Vemos sus columnas y obtenemos los
             * métodos que necesitaremos para la consulta
             */
            for (int i = 0; i < columnas.length; i++) {
                String columna = columnas[i];
                String metodo;
                
                
                /**
                 * Al igual que en los métodos anteriores,
                 * si si la columna es "pagado" el método 
                 * para manipular su valor es isPagado, etc
                 */
                if (columna.equals("pagado")) 
                    metodo = "is" + columna.substring(0,1).
                            toUpperCase() + columna.substring(1);
                else if (columna.equals("num_exp")) 
                    metodo = "getNumExp";
                else 
                    metodo = "get" + columna.substring(0, 1).
                            toUpperCase() + 
                            columna.substring(1);
                
                
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
                    java.sql.Date fechaSql = 
                            new java.sql.Date(((Date) valor).getTime());
                    ps.setDate(i + 1, fechaSql);
                }
            }
            /**
             * Ejecutamos la consulta y 
             * retornamos true en caso
             * de que se realice correctamente
             */
            ps.execute();
            return true;
        }catch (SQLException  | NoSuchMethodException
                | SecurityException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e){
            e.printStackTrace();
            /**
             * False en caso de que 
             * salte la excepción
             */
            return false;
        } finally {
            /**
             * Pase lo que pase, 
             * conexión cerrada
             */
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    /**
     * Método para buscar un registro
     * según el id que le pasemos.
     * Para saber su id, dependerá del
     * objeto en cuestión que le pasemos 
     * por parámetro. También pasamos el nombre
     * de la tabla para determinar a cual
     * hace referencia
     * 
     * retorna un objeto de la interfaz Entidad, que recordamos,
     * implementa cada una de las clases asociadas a cada tabla
     * 
     * @param tabla
     * @param objeto
     * @return 
     */
    public Entidad buscar(String tabla, Entidad objeto){
        Connection conexion = conn.conectarDB();
        PreparedStatement ps = null;
        String select = "";
        String[] columnas = null;


        if (!tabla.isEmpty())
        /**
         * Comprobamos a qué tabla hace referencia
         * y le pasamos la consulta y determinamos
         * las columnas necesarias (en este caso
         * solo los id)
         */ switch (tabla)
        {
            case "ARRENDAMIENTOS" ->
            {
                select = "SELECT * FROM " + tabla +
                        " WHERE num_exp = ?";
                columnas = new String[]{"num_exp"};
            }
            case "CLIENTES" ->
            {
                select = "SELECT * FROM " + tabla
                        + " WHERE DNI = ?";
                columnas = new String[]{"dni"};
            }
            case "PROPIETARIOS" ->
            {
                select = "SELECT * FROM " + tabla +
                        " WHERE dni = ?";
                columnas = new String[]{"dni"};
            }
            case "VIVIENDAS" ->
            {
                select = "SELECT * FROM " + tabla + " WHERE cod_ref = ?";
                columnas = new String[]{"cod_ref"};
            }
            default ->
            {
                columnas = null;
            }
        }

        try {
            ps = conexion.prepareStatement(select);

            /**
             * De nuevo, en clase almacenamos la 
             * clase a la que hace referencia el objeto
             */
            Class<?> clase = objeto.getClass();
            if (columnas != null){
                for (int i = 0; i < columnas.length; i++) {
                    String columna = columnas[i];
                    String metodo;

                    /**
                     * Obtenemos cada uno de los posibles
                     * métodos get
                     */
                    if (columna.equals("pagado")) 
                        metodo = "is" + columna.substring(0,1).
                                toUpperCase() + 
                                columna.substring(1);
                    else if (columna.equals("num_exp")) 
                        metodo = "getNumExp";
                    else 
                        metodo = "get" + columna.substring(0, 1).
                                toUpperCase() + 
                                columna.substring(1);

                    /**
                     * Este objeto almacena el método de la clase invocado
                     * por el objeto
                     */
                    Object valor = clase.getMethod(metodo).invoke(objeto);


                    /**
                     * Si el método devuelve uno de estos tipos
                     * de datos, almacenamos su valor en la
                     * consulta. 
                     */
                    if (valor instanceof String string) {
                        ps.setString(i + 1, string);
                    } else if (valor instanceof Integer integer) {
                        ps.setInt(i + 1, integer);
                    } else if (valor instanceof Boolean aBoolean) {
                        ps.setBoolean(i + 1, aBoolean);
                    } else if (valor instanceof Double aDouble) {
                        ps.setDouble(i + 1, aDouble);
                    }
                }
            }
                

            ResultSet rs = ps.executeQuery();
            Entidad entidad = null;
            /**
             * En este caso, no usamos while,
             * ya que solo queremos acceder
             * a un único registro
             */
            if (rs.next()){
                /**
                 * Dependiendo de la tabla que hemos pasado
                 * por parámetro, entidad se instanciará como
                 * una clase u otra
                 */
                if ("ARRENDAMIENTOS".equals(tabla)) {
                    entidad = new Arrendamiento();
                } else if ("CLIENTES".equals(tabla)) {
                    entidad = new Cliente();
                } else if ("PROPIETARIOS".equals(tabla)) {
                    entidad = new Propietario();
                } else {
                    entidad = new Vivienda();
                }
                
                /**
                 * Dependiendo de la clase a la que haga
                 * referencia el objeto entidad, rellenaremos
                 * el objeto de una forma u otra mediante sus
                 * métodos set
                 */
                if (entidad instanceof Arrendamiento){
                    Arrendamiento ar = new Arrendamiento();
                    SimpleDateFormat formato = 
                            new SimpleDateFormat("yyyy-MM-dd");
                    ar.setNumExp(
                            Integer.parseInt(rs.getString("num_exp"))
                    );
                    ar.setFechaEntrada(
                            formato.parse(
                                    rs.getString("fecha_entrada")
                            ));
                    ar.setFechaSalida(formato.parse(
                            rs.getString("fecha_salida")));
                    ar.setCliente(rs.getString("cliente"));
                    ar.setIdVivienda(Integer.parseInt(
                            rs.getString("id_vivienda")));
                    ar.setPagado(rs.getBoolean("pagado"));
                    /**
                     * Almacenamos en entidad el 
                     * objeto rellenado
                     */
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
                    v.setCod_ref(Integer.parseInt(
                            rs.getString("cod_ref")));
                    v.setMetros(Integer.parseInt(
                            rs.getString("metros")));
                    v.setNumRooms(Integer.parseInt(
                            rs.getString("num_rooms")));
                    v.setNumBathrooms(Integer.parseInt(
                            rs.getString("num_bathrooms")));
                    v.setUbicacion(rs.getString("ubicacion"));
                    v.setPropietario(rs.getString("propietario"));
                    v.setPrecioMensual(Double.valueOf(
                            rs.getString("precio_mensual")));
                    entidad = v;
                }
                /**
                 * retornamos el objeto entidad (el registro)
                 */
                return entidad;
                
            /**
             * Si no existe, 
             * retornará null
             */
            } else{
                return null;
            }
            
        }catch (SQLException | ParseException | NoSuchMethodException
                | SecurityException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException e){
            /**
             * Caso de excepción, devolvemos
             * null
             */
            return null;
        } finally {
            /**
             * Pase lo que pase,
             * cierre conexión
             */
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
    /**
     * Consulta multitabla a campos pertenecientes a las
     * cuatro tablas diferentes en los que sacamos el valor
     * del campo de cada columna que nos interese:
     * - ARRENDAMIENTOS a
     *  > num_exp
     *  > pagado
     *  > fecha_entrada
     *  > fecha salida
     * - CLIENTES c
     *  > nombre as nombreCL
     * - PROPIETARIOS p
     *  > nombre as nombrePr
     * - VIVIENDAS
     *  > precio_mensual
     * 
     * Devuelve una lista de la clase InfoExtensaAlquiler
     * 
     * @return 
     */
    public LinkedHashSet<InfoExtensaAlquiler> historicoAlquiler(){
        Connection conexion = conn.conectarDB();
        LinkedHashSet<InfoExtensaAlquiler> lista = new LinkedHashSet<>();
        InfoExtensaAlquiler a;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        /**
         * CONSULTA SQL
         */
        String consulta = 
                """
                    SELECT a.num_exp, v.precio_mensual, a.pagado, 
                    a.fecha_entrada, a.fecha_salida, cl.nombre nombreCl,
                    p.nombre nombrePr FROM ARRENDAMIENTOS a, CLIENTES cl, 
                    VIVIENDAS v, PROPIETARIOS p
                    WHERE cl.dni = a.cliente and a.id_vivienda = v.cod_ref 
                    and p.dni = v.propietario
                """;
        try{
            ps = conexion.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while (rs.next()){
                a = new InfoExtensaAlquiler();
                /**
                 * Construimos nuestro objeto InfoExtensaAlquiler a
                 */
                a.setNumExp(Integer.parseInt(
                        rs.getString("num_exp")));
                a.setFechaEntrada(formato.parse(
                        rs.getString("fecha_entrada")
                ));
                a.setFechaSalida(formato.parse(
                        rs.getString("fecha_salida")
                ));
                a.setNombreCl(rs.getString("nombreCl"));
                a.setPagado(rs.getBoolean("pagado"));
                a.setNombrePr(rs.getString("nombrePr"));
                a.setPrecio(Double.valueOf(
                        rs.getString("precio_mensual")
                ));
                /**
                 * Agregamos el objeto InfoExtensaAlquiler 'a'
                 * a la lista
                 */
                lista.add(a);   
            }
            /**
             * retornamos la lista LinkedHashSet
             * (no admite duplicados, al igual que
             * en la BBDD no tenemos valores duplicados)
             */
            return lista;
        } catch (SQLException | ParseException e){
            /**
             * En caso de excepción,
             * retornar null
             */
            return null;
        } finally {
            /**
             * Pase lo que pase,
             * conexión cerrada
             */
            try{
                conexion.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }           
    }
    
    
    
    /**
     * Consulta que nos devuelve los datos
     * del cliente, alquiler, vivienda y propietario
     * que tiene una deuda 
     * 
     * - ARRENDAMIENTOS a
     *  > num_exp
     *  > fecha_entrada
     *  > fecha_salida
     *  
     * - CLIENTES cl
     *  > nombre as nombreCL
     *  > edad
     *  > empleo
     * 
     * - VIVIENDAS v
     *  > precio_mensual
     * 
     * - PROPIETARIOS p
     *  > nombre as nombrePr
     * 
     * Las condiciones para esta consulta son:
     * 
     *  -   dni del cliente sea igual al dni reflejado
     *      en el campo cliente de ARRENDAMIENTOS
     *              Y
     *  -   id_vivienda de ARRENDAMIENTOS = cod_ref de 
     *      VIVIENDAS
     *              Y
     *  -   dni del propietario igual al propietario
     *      de VIVIENDAS
     *              Y
     *  -   pagado de ARRENDAMIENTOS = false
     * 
     * 
     * Devolvemos la consulta en orden descendente
     * según el nombre del cliente
     * 
     * Caso de excepción, retornaremos nulo
     * - 
     * @return 
     */
    public LinkedHashSet<InfoExtensaAlquiler> deudas(){
        //Conectamos
        Connection conexion = conn.conectarDB();
        LinkedHashSet<InfoExtensaAlquiler> lista = new LinkedHashSet<>();
        InfoExtensaAlquiler a;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
        /**
         * Escribimos la consulta
         */
        String consulta = """
                          SELECT cl.nombre nombreCl, cl.edad, cl.empleo, 
                          a.num_exp, v.precio_mensual, p.nombre nombrePr, 
                          a.fecha_entrada, a.fecha_salida
                          FROM ARRENDAMIENTOS a, CLIENTES cl, VIVIENDAS v, 
                          PROPIETARIOS p
                          WHERE cl.dni = a.cliente and 
                          a.id_vivienda = v.cod_ref and 
                          p.dni = v.propietario and 
                          a.pagado = False order by
                          nombreCl desc
                          """;
        
        try{
            ps = conexion.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            while (rs.next()){
                a = new InfoExtensaAlquiler();
                
                a.setNumExp(Integer.parseInt(
                        rs.getString("num_exp")
                ));
                a.setFechaEntrada(formato.parse(
                        rs.getString("fecha_entrada")
                ));
                a.setFechaSalida(formato.parse(
                        rs.getString("fecha_salida")
                ));
                a.setNombreCl(rs.getString("nombreCl"));
                a.setEdadCl(Integer.parseInt(rs.getString("edad")));
                a.setEmpleoCl(rs.getString("empleo"));
                a.setPrecio(Double.parseDouble(
                        rs.getString("precio_mensual")
                ));
                lista.add(a);
                
            }
            /**
             * retornamos la lista LinkedHashSet<InfoExtensaAlquiler>
             */
            return lista;
        } catch (SQLException | ParseException e){
            /**
             * Tratamos la excepción
             * devolviendo null
             */
            e.printStackTrace();
            return null;
        } finally {
            /**
             * Pase lo que pase,
             * cerramos conexión
             */
            try{
                conexion.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    
    /**
     * Método que devuleve un LinkedHashSet de los alquileres
     * según la fecha de entrada y de salida.
     * 
     * Pasamos como valores en la consulta:
     * - ARRENDAMIENTOS a
     *      > num_exp
     *      > pagado
     *      > fecha_entrada
     *      > fecha_salida
     * - CLIENTES c
     *      > nombre as nombreCL
     * - PROPIETARIOS
     *      > nombre as nombrePR
     * - VIVIENDAS
     *      > precio_mensual
     * 
     * Donde se producen las siguientes condiciones:
     * 
     *    - si dni de CLIENTES corresponde a cliente
     *      de ARRENDAMIENTOS
     *          Y
     *    - si id_vivienda de ARRENDAMIENTOS
     *      corresponde a cod_ref de VIVIENDAS
     *          Y
     *    - si dni de PROPIETARIOS corresponde
     *      a propietario de VIVIENDAS
     *          Y
     *    - fecha_entrada mayor o igual a la
     *      fecha introducida por parámetro
     *          Y
     *    - fecha_salida menor o igual que
     *      la pasada por parámetro
     * @param fechaEntrada
     * @param fechaSalida
     * @return 
     */
    public LinkedHashSet<InfoExtensaAlquiler> arrendamientosPorFecha(
            Date fechaEntrada, Date fechaSalida){
        //Conectamos
        Connection conexion = conn.conectarDB();
        LinkedHashSet<InfoExtensaAlquiler> lista = new LinkedHashSet<>();
        InfoExtensaAlquiler a;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
        /**
         * Consulta SQL
         */
        String consulta = """
                          SELECT a.num_exp, v.precio_mensual, a.pagado, 
                          a.fecha_entrada, a.fecha_salida,
                            cl.nombre nombreCl, p.nombre nombrePr
                          FROM ARRENDAMIENTOS a, CLIENTES cl, VIVIENDAS v, 
                          PROPIETARIOS p
                          WHERE cl.dni = a.cliente and 
                          a.id_vivienda = v.cod_ref and 
                          p.dni = v.propietario and
                          a.fecha_entrada >= ? 
                          and a.fecha_salida <= ?
                          """;
        
        try{
            /**
             * Introducimos los datos de 
             * fecha entrada y fecha salida en 
             * la consulta
             */
            ps = conexion.prepareStatement(consulta);
            ps.setDate(1, new java.sql.Date(fechaEntrada.getTime())); 
            ps.setDate(2, new java.sql.Date(fechaSalida.getTime())); 
            
            /**
             * Ejecutamos la query
             */
            rs = ps.executeQuery();
            
            while (rs.next()){
                a = new InfoExtensaAlquiler();
                
                a.setNumExp(Integer.parseInt(
                        rs.getString("num_exp")
                ));
                a.setFechaEntrada(formato.parse(
                        rs.getString("fecha_entrada")
                ));
                a.setFechaSalida(formato.parse(
                        rs.getString("fecha_salida")
                ));
                a.setNombreCl(rs.getString("nombreCl"));
                a.setPagado(rs.getBoolean("pagado"));
                a.setNombrePr(rs.getString("nombrePr"));
                a.setPrecio(Double.parseDouble(
                        rs.getString("precio_mensual")
                ));
                
                /**
                 * Agregamos el registro
                 * a la lista de registros
                 * 
                 * Cada registro es un objeto
                 * de la clase InfoExtensaAlquiler
                 */
                lista.add(a);
                
            }
            /**
             * Retornamos la lista
             */
            return lista;
        } catch (SQLException | ParseException e){
            /**
             * Caso de excepción retornamos
             * nulo
             */
            e.printStackTrace();
            return null;
        } finally {
            /**
             * Pase lo que pase,
             * cerramos conexión
             * 
             * caso excepción, 
             * la sacamos por consola
             */
            try{
                conexion.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    
    /**
     * Consulta que devuelve las viviendas que están sin ocupar.
     * 
     * Para esta consulta nos interesa los datos de 
     * VIVIENDAS v:
     *  - cod_ref
     *  - ubicacion
     *  - metros
     *  - num_rooms
     *  - num_bathrooms
     *  - precio_mensual
     *  - propietario
     * 
     * Donde se cumple la condición de que no
     * existe el valor de cod_ref del registro 
     * de la vivienda en la tabla ARRENDAMIENTOS
     * 
     * @return 
     */
    public LinkedHashSet<Vivienda> viviendasSinOcupar(){
        //Conectamos
        Connection conexion = conn.conectarDB();
        LinkedHashSet<Vivienda> lista = new LinkedHashSet<>();
        Vivienda v;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        /**
         * Consulta SQL
         */
        String consulta = """
                          SELECT v.cod_ref, v.ubicacion, v.metros,
                          v.num_rooms, v.num_bathrooms, v.precio_mensual,
                          v.propietario from viviendas v where not exists
                          (select 1 from arrendamientos a where 
                          v.cod_ref = a.id_vivienda)
                          """;
        
        try{
            /**
             * Preparamos la consulta y la ejecutamos
             */
            ps = conexion.prepareStatement(consulta);
            rs = ps.executeQuery();
            
            /**
             * Iteramos sobre la consulta
             */
            while (rs.next()){
                v = new Vivienda();
                /**
                 * Instanciamos un objeto Vivienda v
                 * y lo construimos con sus métodos
                 * set para finalmente añadirlo a la
                 * lista de viviendas
                 */
                v.setCod_ref(Integer.parseInt(
                        rs.getString("cod_ref")
                ));
                v.setUbicacion(rs.getString("ubicacion"));
                v.setMetros(Integer.parseInt(
                        rs.getString("metros")
                ));
                v.setNumRooms(Integer.parseInt(
                        rs.getString("num_rooms")
                ));
                v.setNumBathrooms(Integer.parseInt(
                        rs.getString("num_bathrooms")
                ));
                v.setPropietario(rs.getString("propietario"));
                v.setPrecioMensual(Double.parseDouble(
                        rs.getString("precio_mensual")
                ));
                lista.add(v);
                
            }
            /**
             * Retornamos la lista
             */
            return lista;
        } catch (SQLException e){
            /**
             * Si salta la excepción
             * SQLException, nos devolverá
             * nulo
             */
            e.printStackTrace();
            return null;
        } finally {
            /**
             * Pase lo que pase,
             * cerramos la conexión
             */
            try{
                conexion.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
        
