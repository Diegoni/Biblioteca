package modelo;

import java.util.ArrayList;

/**
* Esta clase permite guardar una nueva carrera, modificar una carrera ya cargada, o leer todas las carreras existentes en la base de datos.
* @author IES 9-024 LAVALLE.
* @version 2.018.
*/
public class Carrera implements IModelo{
    private String NombreCarrera;

    // Constructor:
    /**
     * Constructor de la clase Carrera (no se inicializan atributos).
     */
    public Carrera() {
    }

    /**
     * Este método (getNombreCarrera) devuelve la carrera actualmente almacenada en memoria.
     * @return NombreCarrera (de tipo String).
     */
    public String getNombreCarrera() {
        return NombreCarrera;
    }

    /**
     * Este método (setNombreCarrera) asigna la nueva carrera almacenándola en memoria.
     * @param NombreCarrera (de tipo String).
     */
    public void setNombreCarrera(String NombreCarrera) {
        this.NombreCarrera = NombreCarrera;
    }
    
    /**
     * Este método (insert) envía la petición para insertar una nueva carrera en la base de datos.
     */
    @Override
    public void insert(){
     String insert = "INSERT INTO `carrera`" +
            "(`nombreCarrera`)" +
            "VALUES" +
            "('"+this.NombreCarrera+"');";
     CONECTOR.ejecutarSentencia(insert);
    }

    /**
     * Este método (update) envía la petición para modificar una carrera ya existente en la base de datos.
     */
    @Override
    public int update(String identificador) {
        String update = "UPDATE `carrera` SET `nombreCarrera` = '"+this.NombreCarrera
                +"' WHERE `carrera`.`idCarrera` = "+identificador+";";
        System.out.println(update);
        return CONECTOR.ejecutarSentencia(update);
    }

    /**
     * Este método (selectTodos) envía un String que contiene la consulta que permite obtener las carreras (ya cargados en la base de datos).
     * @return (se retornan todas las carreras obtenidas desde la base de datos).
     */
    @Override
    public ArrayList selectTodos() {
        String select = "SELECT `idCarrera`,`nombreCarrera` FROM `carrera` ORDER BY `nombreCarrera` ASC;";
        return CONECTOR.ejecutarConsulta(select);
    }

    @Override
    public int comprobarExistenciaDeRegistro(String[] data) {
        String lo = "SELECT count(*) FROM carrera WHERE nombreCarrera = '" + data[1] + "';";
        return Integer.parseInt(((ArrayList) CONECTOR.ejecutarConsulta(lo).get(0)).get(0).toString());
    }
    
    @Override
    public int delete(int id){
        String delete = "DELETE FROM `carrera` WHERE `idCarrera` = "+id+";";
        return CONECTOR.ejecutarSentencia(delete);
    }

    public ArrayList selectUno(String id) {
        String select = "SELECT nombreCarrera FROM biblioteca.carrera WHERE idCarrera = "+id+";";
        ArrayList existencia = CONECTOR.ejecutarConsulta(select);
        System.out.println(select);
        return (ArrayList) existencia.get(0); 
    }
}
