package modelo;

import java.util.ArrayList;
import util.ConectorDB;

public interface IModelo {
    final ConectorDB CONECTOR = new ConectorDB();
    /**
     * Insertar un nuevo elemento en la tabla del modelo
     * Previamente haber seteado los valores utilizando sus respectivos set´--´
     */
    public void insert();
    /**
     * Actualiza un elemento existente segun el identificador unico
     * @param identificador id unico del objeto a modificar
     */
    public int update(String identificador);
    /**
     * Selecciona todos los elementos de la tabla 
     * @return ArrayList de los elementos consultados
     */
    public ArrayList selectTodos();   
    /**
     * Busca en la base coincidencias con los parametros
     * para saber si el objeto esta repetido
     * @param data Recibe los datos de identificacion 
     * @return 0 = no existe // 1 = Esta repetido
     */
    public int comprobarExistenciaDeRegistro(String[] data);
    
    public int delete(int id);
}