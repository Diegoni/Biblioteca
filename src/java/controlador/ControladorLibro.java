package controlador;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import modelo.Libro;
import modelo.CodLibro;


/**
* Este servlet se encarga de procesar todas las peticiones referentes a la 
* Libro (Selección,Insertación y/o modificación) y formatear la respuesta 
* adecuada según el resultado
* @author IES 9-024 LAVALLE.
* @version 2.018.
*/

public class ControladorLibro extends ControladorBase {

    Libro modeloLibro;
    CodLibro modeloCodigo;

    @Override
    public void insertar(String[] parametros) {
        if (comprobarEspacios(4, parametros.length)) {
            modeloLibro = new Libro();
            modeloCodigo = new CodLibro();
            int idExistencia = modeloLibro.comprobarExistenciaDeRegistro(parametros);
            if (idExistencia == 0) {
                modeloLibro.setTitulo(parametros[1]); 
                modeloLibro.setTema(Integer.parseInt(parametros[3]));
                modeloLibro.insert();
                idExistencia = modeloLibro.comprobarExistenciaDeRegistro(parametros);
            }
            if (modeloCodigo.comprobarCodigo(parametros[2])) {
                setNumeroDeError(5);
            } else {
                modeloCodigo.setCodlibro(parametros[2]);
                modeloCodigo.setIdlibro(idExistencia);
                modeloCodigo.insert();
                modeloLibro.updatePrestable(idExistencia, 1);
                setNumeroDeError(0);
            }
        } else {
            setNumeroDeError(3);
        }
    }

    @Override
    public String autocompletar() {
        modeloLibro = new Libro();
        return autocompletarJson(modeloLibro.selectTodos());
    }

  
    @Override
    public String consulta(String[] parametros) {
        if(parametros[1].equals("Buscar")){
           return obtenerLibro(parametros[2]);
        }
        if(parametros[1].equals("Update")){
            return actualizarLibro(parametros);
        }
        if(parametros[1].equals("Todos")){
            return todosLosLibros(Integer.parseInt(parametros[2]));
        }
        return "";
    }
    
    private String todosLosLibros(int tipo){
        modeloLibro = new Libro();
        String select;
        String jsonBueno = "";
        if(tipo == 1){
            select = "SELECT libro.idlibro, (SELECT COUNT(codLibro.codLibro) "
                    + "FROM codLibro WHERE codlibro.idlibro = libro_has_autor.libro_idlibro ) "
                    + "as Cantidad, libro.Titulo, GROUP_CONCAT(' ', autor.Nombre, ' ', "
                    + "autor.Apellido) AS Autor, tema.NombreTema FROM libro_has_autor "
                    + "INNER JOIN libro ON libro_has_autor.libro_idlibro = libro.idlibro "
                    + "INNER JOIN autor ON libro_has_autor.autor_idAutor = autor.idAutor "
                    + "INNER JOIN tema ON libro.Tema_idTema = tema.idTema WHERE "
                    + "libro.Prestable = 1 GROUP BY libro.Titulo";
            ArrayList lista = modeloLibro.selectTodosConCantidad(select);
            String json = "";
            for (int i = 0; i < lista.size(); i++) {
            ArrayList previo = (ArrayList) lista.get(i);
            json += "{\"idlibro\": \"" + previo.get(0) + "\", \"cantidad\": \""
                    + previo.get(1) + "\",  \"titulo\": \"" + previo.get(2) + "\", "
                    + "  \"autor\": \"" + previo.get(3) + "\", "
                    + "\"tema\" : \""+previo.get(4)+"\"},";
            }
            jsonBueno = json.replaceAll(",$", "]");
            System.out.println(jsonBueno);
        } else {
            select = "SELECT \n" +
                        "(SELECT\n" +
                        "COUNT(codLibro.codLibro)\n" +
                        "FROM codLibro\n" +
                        "WHERE codlibro.idlibro = libro_has_autor.libro_idlibro\n" +
                        ") \n" +
                        "as Cantidad,\n" +
                        "libro.Titulo,\n" +
                        "GROUP_CONCAT(' ', autor.Nombre, ' ', autor.Apellido) AS Autor,\n" +
                        "tema.NombreTema\n" +
                        "FROM libro_has_autor \n" +
                        "INNER JOIN \n" +
                        "libro ON libro_has_autor.libro_idlibro = libro.idlibro \n" +
                        "INNER JOIN \n" +
                        "autor ON libro_has_autor.autor_idAutor = autor.idAutor\n" +
                        "INNER JOIN\n" +
                        "tema ON libro.Tema_idTema = tema.idTema WHERE libro.Prestable = 1 \n" +
                        "GROUP BY libro.Titulo;";
            ArrayList lista = modeloLibro.selectTodosConCantidad(select);
            String json = "";
            for (int i = 0; i < lista.size(); i++) {
            ArrayList previo = (ArrayList) lista.get(i);
            json += "{\"cantidad\": \"" + previo.get(0) + "\", \"libro\": \""
                    + previo.get(1) + "\",  \"autor\": \"" + previo.get(2) + "\", "
                    + "\"tema\" : \""+previo.get(3)+"\"},";
            }
            jsonBueno = json.replaceAll(",$", "]");
            System.out.println(jsonBueno);
             
        }
        return "[" + jsonBueno;   
    }
    
    private String actualizarLibro(String[] parametros){
        modeloLibro = new Libro();
        modeloLibro.setTitulo(parametros[2]);
        modeloLibro.setTema(Integer.parseInt(parametros[3]));
        int libro = modeloLibro.update(parametros[4]);
        return Integer.toString(libro);
    }
    private String obtenerLibro(String id){
        modeloLibro = new Libro();
        ArrayList libro = modeloLibro.selectUno(id);
        String respuesta = "{"
                + "\"id\" : \""+ libro.get(0) + "\", "
                + "\"titulo\":\""+libro.get(1)+"\","
                + "\"tema\":\""+libro.get(2)+"\""
                + "}";
        return respuesta;
        
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
