/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import modelo.CodLibro;

/**
 *
 * @author Tommy
 */
public class ControladorCodLibro extends ControladorBase{
    
    CodLibro modeloCodLib;
    
    @Override
    public void insertar(String[] parametros) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String autocompletar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar(String[] parametros) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String consulta(String[] parametros) {
        modeloCodLib  = new CodLibro();
        if(parametros[1].equals("eliminar")){
            modeloCodLib.borrarCodigo(parametros[2]);
            setNumeroDeError(0);
        } else {
            return prepararConsulta(parametros[1]);
        }
        return "";
    }
    
    private String prepararConsulta(String id){
        modeloCodLib  = new CodLibro();
        ArrayList lista = modeloCodLib.selectUno(id);
        String json = "";
        for (int i = 0; i < lista.size(); i++) {
            ArrayList previo = (ArrayList) lista.get(i);
            json += "{\"codlibro\": \""+previo.get(0)+"\"},";
        }
        String jsonBueno = json.replaceAll(",$", "]");
        return "[" + jsonBueno;  
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
