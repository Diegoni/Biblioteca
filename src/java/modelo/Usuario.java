package modelo;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import static modelo.IModelo.CONECTOR;

/**
* Esta clase tiene funciones para dos tipos de usuarios.<br>
* Administrador o Bedel (usuario tipo 1):
* <ul>
* <li>Cargar usuarios alumnos y bedeles.</li>
* <li>Modificar usuarios alumnos</li>
* <li>Eliminar usuarios alumnos</li>
* </ul>
* @author IES 9-024 LAVALLE.
* @version 2.019.
*/
public class Usuario implements IModelo{
    private String user;
    private String pass;
    private String nombre;
    private String apellido;
    private int DNI;
    private int tipoUsuario;
    private int socio;
    private int idUsuario;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    // Constructor:
    /**
     * Constructor de la clase Usuario (no se inicializan atributos).
     */
    public Usuario() {
    }

    /**
     * Este método (getUser) devuelve el user (usuario)
     * actualmente almacenado en memoria.
     *
     * @return user (de tipo String).
     */
    public String getUser() {
        return user;
    }

    /**
     * Este método (setUser) asigna el user (usuario)
     * almacenándolo en memoria.
     *
     * @param user (de tipo String).
     */
    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public int getSocio() {
        return socio;
    }

    public void setSocio(int socio) {
        this.socio = socio;
    }
    
    public ArrayList selectUsuario() {
        String select = "SELECT * FROM `usuario` WHERE `user` = '"+this.user+"' AND `pass` = '"+this.pass+"';";
        return CONECTOR.ejecutarConsulta(select);
    }

    @Override
    public void insert() {
        String insert = "INSERT INTO `usuario`(`user`, `pass`, `nombre`, `apellido`, "
                + "`dni`, `tipoUsuario`,`estado_actividad`) "
                + "VALUES ('"+this.user+"', '"+this.pass+"', '"+this.nombre+"', '"+this.apellido+"', "
                + ""+this.DNI+", 1, 0)";
        CONECTOR.ejecutarSentencia(insert);
    }

    @Override
    public int update(String identificador) {
        return 0;
    }
    
    public int updateUsuario(String identificador, String tipoUsuario, String idSocio) {
        String update = "";
        if(tipoUsuario.equals("vedel")){
                update= "UPDATE `usuario` SET `user`= '"+this.user+"', `pass`= '"+this.pass+"', "
                + "`nombre`= '"+this.nombre+"', "
                + "`apellido`= '"+this.apellido+"', `dni`= "+this.DNI+" WHERE `idUsuario` = "+identificador+";";
                
        } else {
            CONECTOR.ejecutarSentencia("UPDATE `usuario` SET `user`= '"+this.user+"', `pass`= '"+this.pass+"' WHERE `idUsuario` = "+identificador+";");
            update= "UPDATE `socio` SET `Nombre`= '"+this.nombre+"',`Apellido`= '"+this.apellido+"',"
                    + "`DNI`="+this.DNI+" WHERE idSocio = "+idSocio;
            
            
        }

        System.out.println(update);
        return CONECTOR.ejecutarSentencia(update);
    }

    @Override
    public ArrayList selectTodos() {
        String select;
        select = "SELECT usuario.idUsuario, usuario.user, usuario.pass, socio.Nombre,"+
        " socio.Apellido, socio.DNI, usuario.ultimo_ingreso" +
        " FROM `usuario` "+
        " JOIN socio"+
        " ON usuario.socio_idSocio = socio.idSocio"+
        " WHERE `tipoUsuario` = 2 AND usuario.estado_actividad = 0;";
        return CONECTOR.ejecutarConsulta(select);
    }
    
    public boolean usuarioRepetido(String user, String id){
        boolean existe = false;
        ArrayList respuesta;
        String select = "SELECT * FROM `usuario` WHERE `user` = '"+user+"' AND `idUsuario` != "+id;
        respuesta = CONECTOR.ejecutarConsulta(select);
        if(respuesta.isEmpty() != true){
            existe = true;
        }
        return existe;
    }

    @Override
    public int comprobarExistenciaDeRegistro(String[] data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Deprecated
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean deleteUser(int id){
        boolean respuesta = false;
        String select = "SELECT socio_idSocio FROM usuario where idUsuario = "+id;
        ArrayList resp = CONECTOR.ejecutarConsulta(select);
        if(((ArrayList) resp.get(0)).get(0) == null){
            String bajaUsuario = "CALL BajaUsuario("+id+")";
            CONECTOR.ejecutarSentencia(bajaUsuario);
        } else {
            String idSocio = ((ArrayList) resp.get(0)).get(0).toString();
            String bajaUsuario = "CALL BajaSocio("+idSocio+")";
            String bajaSocio = "CALL BajaUsuario("+id+")";
            String [] params = {bajaUsuario, bajaSocio}; 
            try {
              respuesta = CONECTOR.Transaction(params);
            } catch (SQLException ex) {
              Logger.getLogger(Socio.class.getName()).log(Level.SEVERE, null, ex);
              respuesta = false;
            }  
        }
        return respuesta;
    }
    
    public boolean comprobarExistencia(String campo, String valor, int flag){
        boolean existe = false;
        ArrayList respuesta;
        if(flag == 0){
            String select = "SELECT * FROM `usuario` WHERE `"+campo+"` = "+valor+"";
            respuesta = CONECTOR.ejecutarConsulta(select);
            if(respuesta.isEmpty() != true){
                existe = true;
            }
        } else {
            String select = "SELECT * FROM `usuario` WHERE `"+campo+"` = '"+valor+"'";
            respuesta = CONECTOR.ejecutarConsulta(select);
            if(respuesta.isEmpty() != true){
                existe = true;
            }
        }
        return existe; 
    }

    public ArrayList selectUno(String id, String tipo){
        ArrayList lista = new ArrayList();
        if("vedel".equals(tipo)){
            String select = "SELECT `user`, `pass`, `nombre`, `apellido`, "
                    + "`dni`, `socio_idSocio` FROM `usuario` WHERE `idUsuario` = "+id+";";
            lista = CONECTOR.ejecutarConsulta(select);
        } else if("alumno".equals(tipo)){
            String select = "SELECT usuario.user, usuario.pass, socio.Nombre, "
                    + "socio.Apellido, socio.DNI, usuario.socio_idSocio FROM `usuario` JOIN socio ON "
                    + "usuario.socio_idSocio = socio.idSocio WHERE usuario.idUsuario = "+id+";";
            lista = CONECTOR.ejecutarConsulta(select);
        }
        return lista;
    }

    public void registrarActividad(String id) {
        LocalDateTime ldt = LocalDateTime.now();
        String update = "UPDATE `usuario` SET `estado_actividad` = '0', `ultimo_ingreso` = '"+DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt)+
                "' WHERE `usuario`.`idUsuario` = "+id+";";
        CONECTOR.ejecutarSentencia(update);
    }
    
    public void bajaUsuariosAntiguos(){
        CONECTOR.ejecutarSentencia("CALL ALTABAJA_SOCIOS();");
    }
}
