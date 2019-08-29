package modelo;

import java.util.ArrayList;

/**
* Esta clase permite guardar un nuevo estado, modificar un estado ya cargado, o leer todos los estados existentes en la base de datos. Los estados por defecto puede ser uno de los siguientes: extraviado, en préstamo, devuelto y vencido.
* @author IES 9-024 LAVALLE.
* @version 2.018.
*/
public class Estado implements IModelo{
    private String Estado;

    // Constructor:
    /**
     * Constructor de la clase Estado (no se inicializan atributos).
     */
    public Estado() {
    }

    /**
     * Este método (getEstado) devuelve el estado actualmente almacenado en memoria.
     * @return DNI (de tipo int).
     */
    public String getEstado() {
        return Estado;
    }

    /**
     * Este método (setEstado) asigna el nuevo estado almacenándolo en memoria.
     * @param Estado (de tipo String).
     */
    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    /**
     * Este método (insert) envía la petición para insertar un nuevo estado en la base de datos.
     */
    @Override
    public void insert() {
        String insert = "INSERT INTO `biblioteca`.`estado`\n"
                + "(`Estado`)"
                + "VALUES"
                + "('" + this.Estado + "');";
        CONECTOR.ejecutarSentencia(insert);
    }

    /**
     * Este método (update) envía la petición para modificar un estado ya existente en la base de datos.
     */
    @Override
    public int update(String identificador) {
        String update = "UPDATE"
                + "     `estado`"
                + "SET"
                + "`Estado` = '" + this.Estado
                + "' WHERE `idEstado` = " + identificador + ";";
        return CONECTOR.ejecutarSentencia(update);
    }

    /**
     * Este método (selectTodos) envía un String que contiene la consulta que permite obtener los estados (ya cargados en la base de datos).
     * @return (se retornan todos los estados obtenidos desde la base de datos).
     */
    @Override
    public ArrayList selectTodos() {
        String select = "SELECT `idEstado`,`Estado` FROM `estado`;";
        return CONECTOR.ejecutarConsulta(select);
    }

    @Override
    public int comprobarExistenciaDeRegistro(String[] data) {
        String lo = "SELECT count(*) FROM estado WHERE Estado = '" + data[1] + "';";
        return Integer.parseInt(((ArrayList) CONECTOR.ejecutarConsulta(lo).get(0)).get(0).toString());
    }

    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
