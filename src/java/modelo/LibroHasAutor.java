/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Tommy
 */
public class LibroHasAutor implements IModelo{
    
    private int idlibro;
    private int idAutor;

    public LibroHasAutor() {
    }

    public int getIdlibro() {
        return idlibro;
    }

    public void setIdlibro(int idlibro) {
        this.idlibro = idlibro;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }
    
    
    @Override
    public void insert() {
        String insert = "INSERT INTO `biblioteca`.`libro_has_autor`"
                + " (`libro_idlibro`, `autor_idAutor`) "
                + "VALUES"
                + " ('" + this.idlibro + "','" + this.idAutor + "');";
        System.out.println(insert);
        CONECTOR.ejecutarSentencia(insert);
    }
    
    public ArrayList tomarID(String titulo) {
        String select = "SELECT `idlibro` FROM `libro` WHERE `Titulo` = '"+titulo+"'";
        return CONECTOR.ejecutarConsulta(select);
    }

    @Override
    public int update(String identificador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList selectTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int comprobarExistenciaDeRegistro(String[] data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(int id) {
        String delete = "DELETE FROM `libro_has_autor` WHERE `libro_idlibro` = "+id;
        return CONECTOR.ejecutarSentencia(delete);
    }
    
    public ArrayList selectUno(String id){
        String select = "SELECT group_concat(`autor`.`Nombre`, ' ', `autor`.Apellido separator '##') as autor\n" +
                        "FROM `libro_has_autor` \n" +
                        "INNER JOIN autor\n" +
                        "ON libro_has_autor.autor_idAutor = autor.idAutor\n" +
                        "WHERE `libro_idlibro` = "+id;
        ArrayList existencia = CONECTOR.ejecutarConsulta(select);
        System.out.println(select);
        return (ArrayList) existencia.get(0); 
    }
}
