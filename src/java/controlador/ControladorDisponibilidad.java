package controlador;

import javax.servlet.http.HttpServletRequest;
import modelo.*;

/**
 * Este servlet se encarga de procesar todas las peticiones referentes a la
 * disponibilidad de un libro para verificar si es posible prestar una copia del
 * mismo y devuelve una respuesta adecuada según el resultado.
 *
 * @author IES 9-024 LAVALLE.
 * @version 2.018.
 */
public class ControladorDisponibilidad extends ControladorBase {

    Libro modeloLibro = new Libro();
    Prestamo modeloPrestamo = new Prestamo();

    @Override
    public void insertar(String[] parametros) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String autocompletar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String consulta(String[] parametros) {
        int cantidad = modeloLibro.contarLibros(parametros[1]);
        int prestados = modeloPrestamo.contarPrestamos(parametros[1]);
        System.out.println(cantidad +" "+ prestados);
        String disponibilidad = "{\"Disponibilidad\" : \"" + (cantidad - prestados) + "\"}";
        System.out.println(disponibilidad);
        return disponibilidad;
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
