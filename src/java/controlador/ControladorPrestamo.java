package controlador;

import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import modelo.Prestamo;

/**
 * Este servlet se encarga de procesar todas las peticiones referentes a los
 * Préstamos (Selección,Insertación y/o modificación) y formatear la respuesta
 * adecuada según el resultado
 *
 * @author IES 9-024 LAVALLE.
 * @version 2.018.
 */
public class ControladorPrestamo extends ControladorBase {

    private Prestamo modelo;

    @Override
    public void insertar(String[] parametros) {
        if (comprobarEspacios(5, parametros.length)) {
            modelo = new Prestamo();
            modelo.setFecha(Date.valueOf(parametros[1]));
            int ultimoPrestamo = modelo.ultimoNumPrestamo() + 1;
            modelo.setNumPrestamo(ultimoPrestamo);
            modelo.setPlazo(Integer.parseInt(parametros[2]));
            modelo.setSocio(Integer.parseInt(parametros[3]));
            modelo.setLibro(Integer.parseInt(parametros[4]));
            modelo.insert();
            setNumeroDeError(0);
        } else {
            setNumeroDeError(3);
        }
    }

    /**
     * @deprecated No tiene utlilidad actualmente en el sistema
     */
    @Override
    public String autocompletar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String consulta(String[] parametros) {
        if(parametros.length > 1){
            if(parametros[1].equals("Buscar")){
                return verificarPrestamo(parametros[2]);
            }
            if(parametros[1].equals("prestamo")){
                return verificarPrestamoConInfo(parametros[2]);
            }
            return "";
        } else {
            modelo = new Prestamo();
            String json = "";
            ArrayList array = modelo.selectTodos();
            for (int i = 0; i < array.size(); i++) {
                ArrayList previo = (ArrayList) array.get(i);
                json += "{\"numPrestamo\": \"" + previo.get(0) + "\", \"libro\": \""
                        + previo.get(1) + "\", \"socio\": \"" + previo.get(3) + " "
                        + previo.get(4) + "\", \"carrera\": \"" + previo.get(5) + "\", "
                        + " \"fecha\": \""+previo.get(7)+"\","
                        + " \"devolucion\": \""+previo.get(8)+"\","
                        + "\"estado\" : \""+previo.get(9)+"\"},";
            }
            String jsonBueno = json.replaceAll(",$", "]");
            System.out.println(jsonBueno);
            return "[" + jsonBueno;
        }
        
    }
    
    private String verificarPrestamo(String id){
        modelo = new Prestamo();
        if(modelo.comprobarDeuda(id) == true){
            return "debe";
        } else {
            return "ok";
        }
    }
    
    private String verificarPrestamoConInfo(String id){
        modelo = new Prestamo();
        ArrayList resp;
        if((resp = modelo.comprobarDeudaConInfo(id)).isEmpty() == true){
            String respuesta = "{\"deuda\": \"no\"}";
            return respuesta;
        } else {
            String respuesta = "{"
                + "\"deuda\" : \"si\", "
                + "\"titulo\":\""+((ArrayList)resp.get(0)).get(1)+"\","
                + "\"fecha\":\""+((ArrayList)resp.get(0)).get(2)+"\""
                + "}";
            return respuesta;
        }
    }
    
    @Override
    public void actualizar(String[] parametros) {
        modelo.update(parametros[1]);
        setNumeroDeError(0);
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
