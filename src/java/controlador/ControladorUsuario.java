/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modelo.Usuario;

/**
 *
 * @author Tommy
 */
public class ControladorUsuario extends ControladorBase{
    Usuario modeloUsuario;
    private int idUsuario;
    
    @Override
    public void insertar(String[] parametros) {
        modeloUsuario = new Usuario();
        modeloUsuario.setNombre(parametros[2]);
        modeloUsuario.setApellido(parametros[3]);
        modeloUsuario.setDNI(Integer.parseInt(parametros[4]));
        modeloUsuario.setUser(parametros[5]);
        modeloUsuario.setPass(parametros[6]);
        modeloUsuario.insert();
    }

    @Override
    public String autocompletar() {
        return null;
    }

    @Override
    public void actualizar(String[] parametros) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String consulta(String[] parametros) {
        modeloUsuario = new Usuario();
        /*
        if(parametros[1].equals("vedel")){
            modeloUsuario.setTipoUsuario(1);
            System.out.println(idUsuario);
            modeloUsuario.setIdUsuario(idUsuario);
            return prepararLista(modeloUsuario.selectTodos());
            
        }
        */
        if(parametros[1].equals("alumno")){
            modeloUsuario.setTipoUsuario(2);
            return prepararLista(modeloUsuario.selectTodos());
        }
        if(parametros[1].equals("Buscar")){
            System.out.println(parametros);
            return prepararListaEdicion(modeloUsuario.selectUno(parametros[3], parametros[2]));
        }
        if(parametros[1].equals("Update")){
            if(modeloUsuario.usuarioRepetido(parametros[5], parametros[7]) == true){
                return "existe";
            } else {
                byte[] bytes;
                bytes = parametros[2].getBytes(StandardCharsets.ISO_8859_1);
                String nombre = new String(bytes, StandardCharsets.UTF_8);
                modeloUsuario.setNombre(nombre);
                bytes = parametros[3].getBytes(StandardCharsets.ISO_8859_1);
                String apellido = new String(bytes, StandardCharsets.UTF_8);
                modeloUsuario.setApellido(apellido);
                modeloUsuario.setDNI(Integer.parseInt(parametros[4]));
                modeloUsuario.setUser(parametros[5]);
                modeloUsuario.setPass(parametros[6]);
                modeloUsuario.updateUsuario(parametros[7], parametros[8], parametros[9]);
                return "ok";
            }
        }
        if(parametros[1].equals("verificar")){
            return verificarExistencia(parametros);
        }
        if(parametros[1].equals("baja")){
            modeloUsuario.bajaUsuariosAntiguos();
        }
        return "";
    }

    @Override
    public void eliminar(int id) {
        modeloUsuario = new Usuario();
        boolean respuesta = modeloUsuario.deleteUser(id);
        if(respuesta == true){
            setNumeroDeError(0);
        } else {
            setNumeroDeError(1);
        }
        
    }
    
    @Override
    public String verificar(String[] parametros, HttpServletRequest request){
        if("invalidate".equals(parametros[1])){
            HttpSession session = request.getSession(false);
            session.invalidate();
            return "Sesion invalidada";
        } else {
            modeloUsuario = new Usuario();
            ArrayList all = new ArrayList();
            for (String parametro : parametros) {
                System.out.println(parametro);
            }
            if(modeloUsuario.comprobarExistencia("user", parametros[1], 1)==false){
                return "El usuario ingresado no existe";
            } else {
                modeloUsuario.setUser(parametros[1]);
                modeloUsuario.setPass(parametros[2]);
                if((all = modeloUsuario.selectUsuario()).isEmpty() == false){
                    
                    ArrayList registro = (ArrayList) all.get(0);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", registro.get(1));
                    session.setAttribute("name", registro.get(3)+" "+registro.get(4));
                    session.setAttribute("id", registro.get(0));
                    idUsuario = Integer.parseInt(registro.get(0).toString());
                    String type = (String) registro.get(6);
                    if("1".equals(type)){
                        session.setAttribute("type", registro.get(6));
                        modeloUsuario.registrarActividad(registro.get(0).toString());
                    } else {
                        modeloUsuario.registrarActividad(registro.get(0).toString());
                    }
                    return "Ok";
                }
                if(modeloUsuario.selectUsuario().isEmpty() == true){
                    return "La contraseña es incorrecta";
                }
            }
        }
        return "";
    }
    
    private String prepararLista(ArrayList lista){
        String json = "";
        for (int i = 0; i < lista.size(); i++) {
            ArrayList previo = (ArrayList) lista.get(i);
            json += "{"
                + "\"id\" : \""+ previo.get(0) + "\", "
                + "\"usuario\":\""+previo.get(1)+"\","
                + "\"pass\": \""+previo.get(2)+"\","
                + "\"nombre\":\""+previo.get(3)+ " " +previo.get(4)+"\","
                + "\"dni\":\""+previo.get(5)+"\","
                + "\"ingreso\":\""+previo.get(6)+"\""
                + "},";
        }
        String jsonBueno = json.replaceAll(",$", "]");
        return ("[" + jsonBueno);
    }
    
    private String prepararListaEdicion(ArrayList lista){
        String json = "";
        for (int i = 0; i < lista.size(); i++) {
            ArrayList previo = (ArrayList) lista.get(i);
            json += "{"
                + "\"usuario\":\""+previo.get(0)+"\","
                + "\"pass\": \""+previo.get(1)+"\","
                + "\"nombre\": \""+previo.get(2)+"\","
                + "\"apellido\": \""+previo.get(3)+"\","                    
                + "\"dni\":\""+previo.get(4)+"\", "
                + "\"idSocio\":\""+previo.get(5)+"\" "
                + "},";
        }
        String jsonBueno = json.replaceAll(",$", "]");
        return ("[" + jsonBueno);
    }
    
    private String verificarExistencia(String[] parametros){
        modeloUsuario = new Usuario();
        if(modeloUsuario.comprobarExistencia("user", parametros[5], 1) == true){
            return "user";
        } else if(modeloUsuario.comprobarExistencia("dni", parametros[4], 0) == true){
            return "dni";
        } else {
            insertar(parametros);
            return "Ok";
        }
    }

}
