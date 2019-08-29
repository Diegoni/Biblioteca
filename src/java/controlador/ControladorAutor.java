package controlador;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

import modelo.Autor;

public class ControladorAutor extends ControladorBase {

    Autor modelo;

    @Override
    public void insertar(String[] parametros) {
        if (comprobarEspacios(3, parametros.length)) {
            modelo = new Autor();
            System.out.println(Arrays.toString(parametros));
            if (modelo.comprobarExistenciaDeRegistro(parametros) == 0) {
                modelo.setNombre(parametros[1]);
                modelo.setApellido(parametros[2]);
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
        modelo = new Autor();
        return autocompletarJson(modelo.selectTodos());
    }


    @Override
    public String consulta(String[] parametros) {
        if (parametros[1].equals("Buscar")) {
            return obtenerLibro(parametros[2]);
        }
        if (parametros[1].equals("Update")) {
            actualizarAutor(parametros);
        }
        return "";
    }

    public void actualizarAutor(String[] parametros) {
        modelo = new Autor();
        byte[] bytes = parametros[2].getBytes(StandardCharsets.ISO_8859_1);
        String nombre = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(nombre);
        byte[] bytes1 = parametros[3].getBytes(StandardCharsets.ISO_8859_1);
        String apellido = new String(bytes1, StandardCharsets.UTF_8);
        System.out.println(apellido);
        modelo.setNombre(nombre);
        modelo.setApellido(apellido);
        modelo.update(parametros[4]);
        setNumeroDeError(0);
    }

    private String obtenerLibro(String id) {
        modelo = new Autor();
        ArrayList libro = modelo.selectUno(id);
        String respuesta = "{"
                + "\"Nombre\" : \"" + libro.get(0) + "\", "
                + "\"Apellido\" : \"" + libro.get(1) + "\""
                + "}";
        System.out.println(respuesta);
        return respuesta;

    }

    @Override
    public void actualizar(String[] parametros) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(int id) {
        modelo = new Autor();
        int respuesta = modelo.delete(id);
        if (respuesta != 0) {
            setNumeroDeError(0);
        } else {
            setNumeroDeError(6);
        }
    }

    @Override
    public String verificar(String[] parametros, HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
