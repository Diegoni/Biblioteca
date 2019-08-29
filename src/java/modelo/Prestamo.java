package modelo;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Esta clase permite crear un nuevo préstamo, modificar préstamo ya cargado
 * (solo se puede modificar su estado), o leer todos los préstamos existentes en
 * la base de datos.
 *
 * @author IES 9-024 LAVALLE.
 * @version 2.018.
 */
public class Prestamo implements IModelo {

    private int Socio;
    private int Libro;
    private int numPrestamo;
    private Date Fecha;
    private int Plazo;
    private int Estado;

    // Constructor:
    /**
     * Constructor de la clase Prestamo (no se inicializan atributos).
     */
    public Prestamo() {
    }

    /**
     * Este método (getEstado) devuelve el id del estado del préstamo
     * actualmente almacenado en memoria.
     *
     * @return Estado (de tipo int).
     */
    public int getEstado() {
        return Estado;
    }

    /**
     * Este método (setEstado) asigna el id del estado del préstamo
     * almacenándolo en memoria.
     *
     * @param Estado (de tipo int).
     */
    public void setEstado(int Estado) {
        this.Estado = Estado;
    }

    /**
     * Este método (getSocio) devuelve el id del socio del préstamo actualmente
     * almacenado en memoria.
     *
     * @return Socio (de tipo int).
     */
    public int getSocio() {
        return Socio;
    }

    /**
     * Este método (setSocio) asigna el id del socio que realizó el pedido de
     * préstamo almacenándolo en memoria.
     *
     * @param Socio (de tipo int).
     */
    public void setSocio(int Socio) {
        this.Socio = Socio;
    }

    /**
     * Este método (getLibro) devuelve el id del libro del préstamo actualmente
     * almacenado en memoria.
     *
     * @return Libro (de tipo int).
     */
    public int getLibro() {
        return Libro;
    }

    /**
     * Este método (setLibro) asigna el id del libro prestado almacenándolo en
     * memoria.
     *
     * @param Libro (de tipo int).
     */
    public void setLibro(int Libro) {
        this.Libro = Libro;
    }

    /**
     * Este método (getNumPrestamo) devuelve el número del préstamo actualmente
     * almacenado en memoria.
     *
     * @return numPrestamo (de tipo int).
     */
    public int getNumPrestamo() {
        return numPrestamo;
    }

    /**
     * Este método (setNumPrestamo) asigna el número del préstamo almacenándolo
     * en memoria.
     *
     * @param numPrestamo (de tipo int).
     */
    public void setNumPrestamo(int numPrestamo) {
        this.numPrestamo = numPrestamo;
    }

    /**
     * Este método (getFecha) devuelve la fecha del día en que se realizó el
     * préstamo actualmente almacenado en memoria.
     *
     * @return Fecha (de tipo Date).
     */
    public Date getFecha() {
        return Fecha;
    }

    /**
     * Este método (setFecha) asigna la fecha en que se realizó el préstamo
     * almacenándolo en memoria.
     *
     * @param Fecha (de tipo Date).
     */
    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    /**
     * Este método (getPlazo) devuelve la fecha del día en que se realizó el
     * préstamo actualmente almacenado en memoria.
     *
     * @return Fecha (de tipo Date).
     */
    public int getPlazo() {
        return Plazo;
    }

    /**
     * Este método (setPlazo) asigna la fecha de entrega del préstamo
     * almacenándolo en memoria.
     *
     * @param Plazo (de tipo int).
     */
    public void setPlazo(int Plazo) {
        this.Plazo = Plazo;
    }

    /**
     * Este método (insert) envía la petición para insertar un nuevo préstamo en
     * la base de datos.
     */
    @Override
    public void insert() {
        String insert = "INSERT INTO `prestamo`"
                + "(`Socio_idSocio`, `Libro_idlibro`, `numPrestamo`, `Fecha`, `Plazo`, `Estado_idEstado`)\n"
                + "VALUES\n"
                + "(" + this.Socio + "," + this.Libro + "," + this.numPrestamo + ",'" + this.Fecha.toString() + "'," + this.Plazo + ",1);";
        CONECTOR.ejecutarSentencia(insert);
    }

    /**
     * Este método (update) envía la petición para modificar un préstamo ya
     * existente en la base de datos.
     * @return 
     */
    @Override
    public int update(String identificador) {
        String update = "UPDATE	`prestamo` SET `Estado_idEstado` = 4 WHERE `numPrestamo` =" + identificador + ";";
        return CONECTOR.ejecutarSentencia(update);
    }

    /**
     * Este método (selectTodos) envía un String que contiene la consulta que
     * permite obtener todos los préstamos existentes en la base de datos.
     *
     * @return (se retornan todos los préstamos obtenidos desde la base de
     * datos).
     */
    @Override
    public ArrayList selectTodos() {
        String select = "SELECT prestamo.numPrestamo, libro.Titulo, socio.DNI,\n" +
                        "socio.Nombre, socio.Apellido, carrera.nombreCarrera, prestamo.Plazo, prestamo.Fecha,\n" +
                        "date_add(Fecha,INTERVAL Plazo DAY),estado.Estado\n" +
                        "FROM\n" +
                        "prestamo\n" +
                        "INNER JOIN libro ON prestamo.Libro_idlibro = libro.idlibro\n" +
                        "JOIN socio ON prestamo.Socio_idSocio = socio.idSocio\n" +
                        "JOIN estado ON prestamo.Estado_idEstado = estado.idEstado\n" +
                        "JOIN carrera ON socio.Carrera_idCarrera = carrera.idCarrera;";
        return CONECTOR.ejecutarConsulta(select);
    }

    /**
     * Este método (contarPrestamos) envía un String que contiene la consulta
     * que permite obtener la cantidad de préstamos existentes en la base de
     * datos a partir del id de un libro.
     *
     * @param idlibro
     * @return (se retorna la cantidad de los préstamos existentes en la base de
     * datos a partir del id ingresado).
     */
    public int contarPrestamos(String idlibro) {
        String select = "SELECT count(*) "
                + "FROM `prestamo` "
                + "WHERE `Libro_idLibro` = " + idlibro + " "
                + "AND (`Estado_idEstado` = 1 OR Estado_idEstado = 2);";
        ArrayList cantidadLibros = CONECTOR.ejecutarConsulta(select);
        return Integer.parseInt(((ArrayList) cantidadLibros.get(0)).get(0).toString());
    }

    /**
     * Este método obtiene el numero del ultimo prestamo y lo devuelve como un
     * entero.
     *
     * @return (int) Numero del ultimo prestamo regitrado;
     */
    public int ultimoNumPrestamo() {
        String select = "SELECT max(numPrestamo) FROM `prestamo`;";
        if(((ArrayList) CONECTOR.ejecutarConsulta(select).get(0)).get(0) == null){
            return 0;
        } else {
            return Integer.parseInt(((ArrayList) CONECTOR.ejecutarConsulta(select).get(0)).get(0).toString());
        }
    }

    public boolean comprobarDeuda(String id){
        boolean existe = false;
        String select = "SELECT socio_idSocio FROM usuario where idUsuario = "+id;
        ArrayList resp = CONECTOR.ejecutarConsulta(select);
        System.out.println(resp.get(0));
        if(((ArrayList) resp.get(0)).get(0) == null){
            existe = false;
        } else {
            select = "SELECT * FROM `prestamo` WHERE Socio_idSocio = "+((ArrayList) resp.get(0)).get(0).toString()+" AND Estado_idEstado != 4";
            ArrayList respuesta = CONECTOR.ejecutarConsulta(select);
            if(respuesta.isEmpty() != true){
                existe = true;
            }
        }
        return existe; 
    }
    
    public ArrayList comprobarDeudaConInfo(String id){
        String select = "SELECT prestamo.Socio_idSocio, libro.Titulo, prestamo.Fecha \n" +
                        "FROM biblioteca.prestamo \n" +
                        "INNER JOIN libro \n" +
                        "ON prestamo.Libro_idlibro = libro.idlibro\n" +
                        "WHERE Socio_idSocio = "+id+" AND Estado_idEstado != 4;";
        ArrayList lista = CONECTOR.ejecutarConsulta(select);
        return lista; 
    }
            
    @Override
    public int comprobarExistenciaDeRegistro(String[] data) {
        throw new UnsupportedOperationException("No soportado");
    }

    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
