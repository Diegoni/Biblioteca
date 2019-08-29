package controlador;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import modelo.Carrera;

/**
* Este servlet se encarga de procesar todas las peticiones referentes a la 
* Carrera (Selección,Insertación y/o modificación) y formatear la respuesta 
* adecuada según el resultado
* @author IES 9-024 LAVALLE.
* @version 2.018.
*/

public class ControladorCarrera extends ControladorBase {

    private Carrera modelo;

    /**
     * @param partes
     * @deprecated No tiene utlilidad actualmente en el sistema
     */
    @Override
    public void insertar(String[] partes) {
        System.out.println(partes[1]);
        if (comprobarEspacios(2, partes.length)) {
            modelo = new Carrera();
            if (modelo.comprobarExistenciaDeRegistro(partes) == 0) {
                modelo.setNombreCarrera(partes[1]);
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
        modelo = new Carrera();
        return autocompletarJson(modelo.selectTodos());
    }

    /**
     * @param parametros
     * @return 
     * @deprecated No tiene utlilidad actualmente en el sistema
     */
    @Override
    public String consulta(String[] parametros) {
        if(parametros[1].equals("Buscar")){
           return obtenerCarrera(parametros[2]);
        }
        if(parametros[1].equals("Update")){
            actualizarCarrera(parametros);
        }
        return "";
    }


    public void actualizarCarrera(String[] parametros) {
        modelo = new Carrera();
        byte[] bytes = parametros[2].getBytes(StandardCharsets.ISO_8859_1);
        String nombre = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(nombre);
        modelo.setNombreCarrera(nombre);
        modelo.update(parametros[3]);
        setNumeroDeError(0);
    }
    

    @Override
    public void eliminar(int id) {
        modelo = new Carrera();
        int respuesta = modelo.delete(id);
                if(respuesta != 0){
            setNumeroDeError(0);
        } else {
            setNumeroDeError(6);
        }
    }

    @Override
    public void actualizar(String[] parametros) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String verificar(String[] parametros, HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String obtenerCarrera(String id) {
        modelo = new Carrera();
        ArrayList libro = modelo.selectUno(id);
        String respuesta = "{"
                + "\"Carrera\" : \""+libro.get(0)+"\""
                + "}";
        System.out.println(respuesta);
        return respuesta;
    }
}
