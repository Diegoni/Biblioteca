package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Base del cual heredan todos los demás, se encarga de definir las
 * acciones principales que todos los servlets pueden llegar a utilizar y
 * diferentes acciones abstractas que requieran un procesamiento particular
 * según el controlador solicitado
 *
 * @author IES 9-024 LAVALLE.
 * @version 2.018.
 */
public abstract class ControladorBase extends HttpServlet {

    private int numeroDeError;

    /**
     * Procesa la petición de los métodos @GET y @POST segun la acción
     * especificada y genera la respuesta para
     *
     * @param request Sirve para obtener los parametros y argumentos obtenidos
     * del formulario
     * @param response Genera e imprime una respuesta a la petición según el
     * tipo de dato especificado
     * @throws ServletException Genera una excepción general de servlet ante un
     * error
     * @throws IOException Genera una excepción general de entrada/salida en
     * caso de que se use un archivo de disco o una redirección y no sea posible
     * abrirlo.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paquete;
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        HttpSession session = request.getSession();
        System.out.println("Muralla 1");
            if ((paquete = request.getParameter("envio")) != null) {
                System.out.println(paquete);
                String[] parametros = paquete.split(";,;");
                setNumeroDeError(-1);
                //Compruebo espacios en blanco
                if (!comprobarEspaciosEnBlanco(parametros)) {
                    //Luego Caracteres especiales
                    if (!comprobarCaracteresEspeciales(parametros)) {
                        //Comprobar si hay una sesion iniciada
                        System.out.println("Muralla 2");
                        if(session.getAttribute("user")!=null){
                            switch (parametros[0]) {
                                case "insertar":
                                    //Realizar esta accion solo si el usuario es administrador
                                    if(session.getAttribute("type")!=null){
                                        insertar(parametros);
                                    } else {
                                        response.sendRedirect("http://localhost:8080/BibliotecaBootstrap/index.jsp");
                                    }
                                    break;
                                case "autocompletar":
                                    System.out.println(session.getAttribute("user"));
                                    out.print(autocompletar());
                                    break;
                                case "consulta":
                                    out.print(consulta(parametros));
                                    break;
                                case "actualizar":
                                    //Realizar esta accion solo si el usuario es administrador
                                    if(session.getAttribute("type")!=null){
                                        actualizar(parametros);
                                    } else {
                                        response.sendRedirect("http://localhost:8080/BibliotecaBootstrap/index.jsp");
                                    }
                                    break;
                                case "eliminar":
                                    //Realizar esta accion solo si el usuario es administrador
                                    if(session.getAttribute("type")!=null){
                                        eliminar(Integer.parseInt(parametros[1]));
                                    } else {
                                        response.sendRedirect("http://localhost:8080/BibliotecaBootstrap/index.jsp");
                                    }
                                    break;
                                case "verificar":
                                    out.print(verificar(parametros, request));
                                    break;
                                case "":
                                    break;
                            }
                        } else {
                            if("verificar".equals(parametros[0])){
                                out.print(verificar(parametros, request));
                            } else {
                                response.sendRedirect("http://localhost:8080/BibliotecaBootstrap/index.jsp");
                            }
                        }
                    } else {
                        setNumeroDeError(2);
                    }
                } else {
                    setNumeroDeError(1);
                }
                switch (numeroDeError) {
                    case 0:
                        out.print(exito("Agregado correctamente."));
                        break;
                    case 1:
                        out.print(error("Por Favor, no deje espacios en blanco"));
                        break;
                    case 2:
                        out.print(error("No se permiten caracteres especiales (\\\",\\\\,?,¨,+,etc)."));
                        break;
                    case 3:
                        out.print(error("Por Favor, complete todos los campos antes de enviar."));
                        break;
                    case 4:
                        out.print(error("El Objeto ya se encuentra cargado"));
                        break;
                    case 5:
                        out.print(error("El codigo del libro ya existe"));
                    case 6:
                        out.print(error("Dato vinculado a otra tabla"));
                    default:
                        break;
                }
            }
        
    }

    /**
     * Método que se encarga de recibir todas las peticiones HTTP GET y de
     * redirigirlas al método de procesamiento
     *
     * @param request Contiene los datos de la petición
     * @param response Contiene la información de la respuesta.
     * @throws ServletException Genera una excepción general de servlet ante un
     * error
     * @throws IOException Genera una excepción general de entrada/salida en
     * caso de que se use un archivo de disco o una redirección y no sea posible
     * abrirlo.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Método que se encarga de recibir todas las peticiones
     * <code>HTTP POST</code> y de redirigirlas al método de procesamiento
     *
     * @param request Contiene los datos de la petición
     * @param response Contiene la información de la respuesta.
     * @throws ServletException Genera una excepción general de servlet ante un
     * error
     * @throws IOException Genera una excepción general de entrada/salida en
     * caso de que se use un archivo de disco o una redirección y no sea posible
     * abrirlo.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Recibe los parámetros procesados para insertar en la base una vez que se
     * a comprobado que sus valores son validos. En caso de que exista un error
     * al insertar asigna un numero de error.
     *
     * @param parametros Array con los parámetros obtenidos
     */
    public abstract void insertar(String[] parametros);

    /**
     * Genera y devuelve la respuesta a la peticion ajax con la informacion para
     * los autocompletado.
     *
     * @return Devuelve los objetos en formato JSON para el auto completado
     */
    public abstract String autocompletar();

    public abstract void actualizar(String[] parametros);

    /**
     * Recibe el arraylist con los datos de la base que utilizaremos para el
     * autocompletado para generar un JavaScript Object Notation con el formato
     * {"Etiqueta": "Valor1", "id": "Valor2"}. Valor1 es el nombre del elemento
     * a mostrar en la interfaz de usuario Valor2 es el identificador con el que
     * se va a reconocer al elemento
     *
     * @param array ArrayList previamente obtenido de realizar un select a la
     * base
     * @return JSON en formato String con las etiquetas
     */
    public String autocompletarJson(ArrayList array) {
        String json = "";
        for (int i = 0; i < array.size(); i++) {
            ArrayList previo = (ArrayList) array.get(i);
            json += "{\"etiqueta\": \"" + previo.get(1) + "\", \"id\" : \"" + previo.get(0) + "\"},";
        }
        String jsonBueno = json.replaceAll(",$", "]");
        return ("[" + jsonBueno);
    }

    public boolean comprobarEspaciosEnBlanco(String[] partes) {
        Pattern caracteres = Pattern.compile("\\s+");
        for (String parte : partes) {
            Matcher coincide = caracteres.matcher(parte);
            if (coincide.matches() || parte.equals("")) {
                return true;
            }
        }
        return false;
    }

    public boolean comprobarCaracteresEspeciales(String[] partes) {
        Pattern caracteres = Pattern.compile("\\p{Punct}");
        for (String parte : partes) {
            Matcher puntuacion = caracteres.matcher(parte);
            if (puntuacion.find()) {
                Pattern esCodigo = Pattern.compile("[-¿?¡!]");
                Matcher codigo = esCodigo.matcher(parte);
                if (!codigo.find()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Comprueba que todos los parámetros se hayan procesado correctamente
     *
     * @param rango define la cantidad de parámetros esperados según el
     * formulario enviado
     * @param longitudParametros tiene la longitud real del array formado de los
     * datos procesado
     * @return
     */
    public boolean comprobarEspacios(int rango, int longitudParametros) {
        return longitudParametros == rango;
    }

    public abstract String consulta(String[] parametros);

    /**
     * Formatea e imprime el mensaje de error correspondiente en caso de ser
     * necesario. Utiliza el siguiente formato JSON {"error":"mensaje_de_error"}
     *
     * @param mensajeDeError contiene el mensaje de error}
     * @return Devuelve el error formateado en JSON
     */
    public String error(String mensajeDeError) {
        return "{\"error\" : \"" + mensajeDeError + "\"}";
    }

    public String exito(String mensajeDeExito) {
        return "{\"exito\" : \"" + mensajeDeExito + "\"}";
    }

    public int getNumeroDeError() {
        return numeroDeError;
    }

    /**
     * Asigna el número de error segun el parámetro
     *
     * @param error número de error especificado
     */
    public void setNumeroDeError(int error) {
        this.numeroDeError = error;
    }
    
    public abstract void eliminar(int id);

    public abstract String verificar(String[] parametros, HttpServletRequest request);
}
