package modelo;

import java.util.ArrayList;
import util.ConectorDB;

/**
* Esta clase permite guardar u obtener la ubicación física de cada libro.
* @author IES 9-024 LAVALLE.
* @version 2.018.
*/
public class Ubicacion implements IModelo{

    private String Ubicacion;
    ConectorDB conector = new ConectorDB();

    // Constructor:
    /**
     * Constructor de la clase Ubicación (no se inicializan atributos).
     */
    public Ubicacion() {
    }

    /**
     * Este método (getUbicacion) devuelve la ubicación actualmente almacenada en memoria.
     * @return Ubicacion (de tipo String).
     */
    public String getUbicacion() {
        return Ubicacion;
    }

    /**
     * Este método (setUbicacion) asigna la nueva ubicación almacenándola en memoria.
     * @param Ubicacion (de tipo String).
     */
    public void setUbicacion(String Ubicacion) {
        this.Ubicacion = Ubicacion;
    }

    /**
     * Este método (selectTodos) envía un String que contiene la consulta que permite obtener los sectores físicos (ya cargados en la base de datos) en donde se almacenan los libros.
     * @return (se retornan todos los sectores físicos obtenidos desde la base de datos).
     */
        @Override
    public ArrayList selectTodos(){
        String select = "SELECT * FROM `ubicacion` ORDER BY `sector` ASC;";
        return conector.ejecutarConsulta(select);
    }

    /**
     * Este método (insert) envía la petición para insertar una nueva ubicación física en la base de datos.
     */    
    @Override
    public void insert() {
        String insert = "INSERT INTO `biblioteca`.`ubicacion`"
                + " (`sector`) "
                + "VALUES"
                + " ('" + this.Ubicacion + "');";
        conector.ejecutarSentencia(insert);
    }
     /** 
     * @deprecated 
     * No tiene utlilidad actualmente en el sistema
     */ 
    @Override
    public int update(String identificador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int comprobarExistenciaDeRegistro(String[] data) {
        String lo = "SELECT count(*) FROM ubicacion WHERE sector = '" + data[1] + "';";
        return Integer.parseInt(((ArrayList) CONECTOR.ejecutarConsulta(lo).get(0)).get(0).toString());
    }

    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
