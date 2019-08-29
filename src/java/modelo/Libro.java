package modelo;

import java.util.ArrayList;

public class Libro implements IModelo {

    private String Titulo;
    private String codLibro;
    private int Tema;
    private int Ubicacion;
    private int Prestable;

    public Libro() {
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getCodLibro() {
        return codLibro;
    }

    public void setCodLibro(String codLibro) {
        this.codLibro = codLibro;
    }

    public int getTema() {
        return Tema;
    }

    public void setTema(int Tema) {
        this.Tema = Tema;
    }

    public int getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(int Ubicacion) {
        this.Ubicacion = Ubicacion;
    }

    public int getPrestable() {
        return Prestable;
    }

    public void setPrestable(int Prestable) {
        this.Prestable = Prestable;
    }

    @Override
    public void insert() {
        String insert = "INSERT INTO `biblioteca`.`libro`"
                + "	(`Titulo`, `Tema_idTema`, `Ubicacion_id_ubicacion`, `Prestable`)"
                + "VALUES"
                + "	('" + this.Titulo + "', " + this.Tema + ",1,1);";
        CONECTOR.ejecutarSentencia(insert);
    }

    @Override
    public int update(String identificador) {
        String update= "UPDATE libro SET Titulo = '"+this.Titulo+"',"
                + "Tema_idTema = "+this.Tema
                + " WHERE idLibro = "+identificador+";";
        
        System.out.println(update);
        return CONECTOR.ejecutarSentencia(update);
    }

    @Override
    public ArrayList selectTodos() {
        String select = "SELECT `idlibro`, `Titulo` FROM `libro` WHERE libro.Prestable = 1 ORDER BY `Titulo` ASC;";
        return CONECTOR.ejecutarConsulta(select);
    }

    public int contarLibros(String idlibro) {
        String select = "SELECT count(*) FROM `codLibro` "
                + "INNER JOIN libro ON codlibro.idlibro = libro.idlibro "
                + "WHERE libro.`idlibro` = "+idlibro+" AND libro.Prestable = 1";
        ArrayList cantidadLibros = CONECTOR.ejecutarConsulta(select);
        return Integer.parseInt(((ArrayList) cantidadLibros.get(0)).get(0).toString());
    }

    @Override
    public int comprobarExistenciaDeRegistro(String[] data) {
        String lo = "SELECT idlibro"
                + " FROM libro"
                + " WHERE libro.Titulo = \"" + data[1] + "\";";
        ArrayList existencia = CONECTOR.ejecutarConsulta(lo);
        if (existencia.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(((ArrayList) existencia.get(0)).get(0).toString());
        }
    }

    public ArrayList selectTodosConCantidad(String query) {
        String select = query;
        return CONECTOR.ejecutarConsulta(select);
    }
    public ArrayList selectUno(String id){
        String select = "SELECT idLibro, Titulo ,Tema_idTema FROM libro WHERE idLibro = "+id+";";
        ArrayList existencia = CONECTOR.ejecutarConsulta(select);
        return (ArrayList) existencia.get(0); 
    }

    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void updatePrestable(int idExistencia, int valor) {
        String update= "UPDATE libro SET Prestable = "+valor
                + " WHERE idLibro = "+idExistencia+";";
        
        System.out.println(update);
        CONECTOR.ejecutarSentencia(update);
    }
    
    public boolean comprobarPrestamo(String id){
        String select = "SELECT * FROM `prestamo` WHERE Libro_idlibro = "+id+" AND (Estado_idEstado = 1 OR Estado_idEstado = 2 or Estado_idEstado = 3);";
        ArrayList existencia = CONECTOR.ejecutarConsulta(select);
        return existencia.isEmpty();
    }
}
