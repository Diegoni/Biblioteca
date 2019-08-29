package modelo;

import java.util.ArrayList;
import util.ConectorDB;

/**
 * Esta clase permite guardar u obtener las temáticas (categorías) de los
 * libros.
 *
 * @author IES 9-024 LAVALLE.
 * @version 2.018.
 */
public class Tema implements IModelo {

    String NombreTema;
    ConectorDB conector = new ConectorDB();

    // Constructor:
    /**
     * Constructor de la clase Tema (no se inicializan atributos).
     */
    public Tema() {
    }

    /**
     * Este método (getNombreTema) devuelve el tema actualmente almacenado en
     * memoria.
     *
     * @return NombreTema (de tipo String).
     */
    public String getNombreTema() {
        return NombreTema;
    }

    /**
     * Este método (setNombreTema) asigna el nuevo tema almacenándolo en
     * memoria.
     *
     * @param NombreTema (de tipo String).
     */
    public void setNombreTema(String NombreTema) {
        this.NombreTema = NombreTema;
    }

    /**
     * Este método (selectTodos) envía un String que contiene la consulta que
     * permite obtener los temas (ya cargados en la base de datos).
     *
     * @return (se retornan todos los temas obtenidos desde la base de datos).
     */
    @Override
    public ArrayList selectTodos() {
        String select = "SELECT * FROM `tema` ORDER BY `idTema`;";
        return conector.ejecutarConsulta(select);
    }

    /**
     * Este método (insert) envía la petición para insertar un nuevo tema en la
     * base de datos.
     */
    @Override
    public void insert() {
        String insert = "INSERT INTO `biblioteca`.`tema`"
                + " (`NombreTema`) "
                + "VALUES"
                + " ('" + this.NombreTema + "');";
        conector.ejecutarSentencia(insert);
    }

    @Override
    public int update(String identificador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int comprobarExistenciaDeRegistro(String[] data) {
        String lo = "SELECT count(*) FROM tema WHERE NombreTema = '" + data[1] + "';";
        return Integer.parseInt(((ArrayList) CONECTOR.ejecutarConsulta(lo).get(0)).get(0).toString());
    }

    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
