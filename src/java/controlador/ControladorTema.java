package controlador;

import javax.servlet.http.HttpServletRequest;
import modelo.Tema;

/**
* Este servlet se encarga de procesar todas las peticiones referentes al 
* Tema/Categoría temática (Selección,Insertación y/o modificación) y formatear la respuesta 
* adecuada según el resultado
* @author IES 9-024 LAVALLE.
* @version 2.018.
*/
public class ControladorTema extends ControladorBase {

    Tema modelo;

    @Override
    public void insertar(String[] partes) {
        if (comprobarEspacios(2, partes.length)) {
            modelo = new Tema();
            if (modelo.comprobarExistenciaDeRegistro(partes) == 0) {
                modelo.setNombreTema(partes[1]);
                modelo.insert();
                setNumeroDeError(0);
            } else {
                setNumeroDeError(4);
            }
        } else {
            setNumeroDeError(3);
        }
    }

    @Override
    public String autocompletar() {
        modelo = new Tema();
        return autocompletarJson(modelo.selectTodos());
    }

    /**
     * @deprecated No tiene utlilidad actualmente en el sistema
     */
    @Override
    public String consulta(String[] parametros) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar(String[] parametros) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String verificar(String[] parametros, HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
