package controlador;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import modelo.Socio;


/**
* Este servlet se encarga de procesar todas las peticiones referentes a los 
* Socios (Selección,Insertación y/o modificación) y formatear la respuesta 
* adecuada según el resultado
* @author IES 9-024 LAVALLE.
* @version 2.018.
*/
public class ControladorSocio extends ControladorBase {

    Socio modelo;

    @Override
    public String autocompletar(){
        modelo = new Socio();
        return autocompletarJson(modelo.selectTodos());
    }

    @Override
    public void insertar(String[] partes) {
        if (comprobarEspacios(5, partes.length)) {
            modelo = new Socio();
            if (modelo.comprobarExistenciaDeRegistro(partes) == 0) {
                modelo.setCarrera(Integer.parseInt(partes[1]));
                modelo.setNombre(partes[2]);
                modelo.setApellido(partes[3]);
                modelo.setDNI(Integer.parseInt(partes[4]));
                modelo.insert();
                setNumeroDeError(0);
            } else {
                modelo.altaUsuario(partes[4]);
            }
        } else {
            setNumeroDeError(3);
        }
    }

    @Override
    public String consulta(String[] parametros) {
        modelo = new Socio();
        return prepararLista(modelo.devolverUsuario(parametros[1]));
    }
    
    private String prepararLista(ArrayList lista){
        String json = "";
        for (int i = 0; i < lista.size(); i++) {
            ArrayList previo = (ArrayList) lista.get(i);
            json += "{"
                + "\"usuario\":\""+previo.get(0)+"\","
                + "\"pass\": \""+previo.get(1)+"\""
                + "},";
        }
        String jsonBueno = json.replaceAll(",$", "]");
        return ("[" + jsonBueno);
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
