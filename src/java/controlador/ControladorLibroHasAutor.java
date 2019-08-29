/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import modelo.LibroHasAutor;

public class ControladorLibroHasAutor extends ControladorBase {
    
    LibroHasAutor modeloLA;
    
    @Override
    public void insertar(String[] parametros) {
        modeloLA = new LibroHasAutor();
        ArrayList idEnc = modeloLA.tomarID(parametros[1]);
        String temporal = idEnc.get(0).toString();
        String definitivo = temporal.replace("]", "").replace("[", "");
        modeloLA.setIdlibro(Integer.parseInt(definitivo));
        Integer.parseInt(((ArrayList) idEnc.get(0)).get(0).toString());
        for(int x = 2; x < parametros.length; x++){
            modeloLA.setIdAutor(Integer.parseInt(parametros[x]));
            modeloLA.insert();
        }
    }

    @Override
    public String autocompletar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar(String[] parametros) {
        modeloLA = new LibroHasAutor();
        modeloLA.delete(Integer.parseInt(parametros[1]));
        modeloLA.setIdlibro(Integer.parseInt(parametros[1]));
        for(int x = 2; x < parametros.length; x++){
            modeloLA.setIdAutor(Integer.parseInt(parametros[x]));
            modeloLA.insert();
        }
    }

    @Override
    public String consulta(String[] parametros) {
        modeloLA = new LibroHasAutor();
        ArrayList libro = modeloLA.selectUno(parametros[1]);
        String respuesta = "{"
                + "\"autores\" : \""+ libro.get(0) + "\" "
                + "}";
        return respuesta;
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
