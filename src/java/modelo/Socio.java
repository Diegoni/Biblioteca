package modelo;

import java.sql.SQLException;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* Esta clase permite guardar un nuevo socio u obtener los datos ya cargados de un socio existente.
* @author IES 9-024 LAVALLE.
* @version 2.018.
*/
public class Socio implements IModelo {

    private String Nombre;
    private String Apellido;
    private int Carrera;
    private int DNI;

    // Constructor:
    /**
     * Constructor de la clase Socio (no se inicializan atributos).
     */
    public Socio() {
    }

    /**
     * Este método (getDNI) devuelve el número de documento actualmente almacenado en memoria.
     * @return DNI (de tipo int).
     */
    public int getDNI() {
        return DNI;
    }

    /**
     * Este método (setDNI) asigna un número de documento nuevo almacenándolo en memoria.
     * @param DNI (de tipo int).
     */
    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    /**
     * Este método (getNombre) devuelve el nombre del socio actualmente almacenado en memoria.
     * @return DNI (de tipo int).
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * Este método (setNombre) asigna el nombre del nuevo socio almacenándolo en memoria.
     * @param Nombre (de tipo String).
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    /**
     * Este método (setApellido) asigna el apellido del nuevo socio almacenándolo en memoria.
     * @param Apellido (de tipo String).
     */
    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    /**
     * Este método (getCarrera) devuelve la carrera actualmente almacenada en memoria.
     * @return DNI (de tipo int).
     */
    public int getCarrera() {
        return Carrera;
    }

    /**
     * Este método (setCarrera) asigna la nueva carrera almacenándola en memoria.
     * @param Carrera (de tipo String).
     */
    public void setCarrera(int Carrera) {
        this.Carrera = Carrera;
    }

    /**
     * Este método (insert) envía la petición para insertar un nuevo socio en la base de datos.
     */
    @Override
    public void insert() {
        LocalDateTime ldt = LocalDateTime.now();
        int numAleatorio=(int)Math.floor(Math.random()*899999+1);
        String userTemp = "";
        String[] nombre = this.Nombre.split(" ");
        String[] apellido = this.Apellido.split(" ");
        boolean existe = true;
        while(existe == true){
            userTemp = nombre[0].toLowerCase()+apellido[0].toLowerCase()+
                (int)Math.floor(Math.random()*8999+1);
            userTemp = Normalizer.normalize(userTemp, Normalizer.Form.NFD);
            userTemp = userTemp.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            existe = comprobarExisteciaDeUsuario(userTemp);
        }

        String insert = "INSERT INTO `biblioteca`.`socio`"
                + "	(`Carrera_idCarrera`,`Nombre`,`Apellido`, `DNI`, `estado_actividad`)"
                + "VALUES"
                + "	(" + this.Carrera + ",'" + this.Nombre + "', '" + this.Apellido + "'," + this.DNI + ", 0);";
        
        String insertUsuario = "INSERT INTO `usuario`(`user`, `pass`, `tipoUsuario`, `socio_idSocio`, "
                + "`estado_actividad`, `ultimo_ingreso`) VALUES ('"+userTemp+"', '"+(numAleatorio + 100000)+"', 2, (SELECT last_insert_id())"
                + ", 0, '"+DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt)+"');";
        
        String [] params = {insert, insertUsuario}; 
        try {
            CONECTOR.Transaction(params);
        } catch (SQLException ex) {
            Logger.getLogger(Socio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList devolverUsuario(String dni){
        String select = "SELECT usuario.user, usuario.pass from usuario\n" +
        "inner join socio\n" +
        "on usuario.socio_idSocio = socio.idSocio\n" +
        "where socio.DNI = "+dni;
        return CONECTOR.ejecutarConsulta(select);
    }
    
    
    private boolean comprobarExisteciaDeUsuario(String usuario){
        String sql = "SELECT * FROM `usuario` where user = '"+usuario+"';";
        ArrayList existencia = CONECTOR.ejecutarConsulta(sql);
        return !existencia.isEmpty();
    }

    /**
     * Este método (update) envía la petición para modificar un socio ya existente en la base de datos.
     */
    @Override
    public int update(String identificador) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Este método (selectTodos) envía un String que contiene la consulta que permite obtener los socios (ya cargados en la base de datos).
     * @return (se retornan todos los socios obtenidos desde la base de datos).
     */
    @Override
    public ArrayList selectTodos() {
        String select = "SELECT `idSocio`, concat(`Nombre`,' ',`Apellido`,' - DNI: ',`DNI`) AS `Socio` FROM `socio` "
                + "WHERE `estado_actividad` = 0;";
        return CONECTOR.ejecutarConsulta(select);
    }

    @Override
    public int comprobarExistenciaDeRegistro(String[] data) {
        String lo = "SELECT count(*) FROM socio WHERE DNI = " + data[4] + ";";
        return Integer.parseInt(((ArrayList) CONECTOR.ejecutarConsulta(lo).get(0)).get(0).toString());
    }

    public void altaUsuario(String dni){
        String update = "UPDATE socio INNER JOIN usuario ON socio.idSocio = usuario.socio_idSocio SET socio.estado_actividad = 0, usuario.estado_actividad = 0 WHERE socio.DNI = "+dni;
        CONECTOR.ejecutarSentencia(update);
    }
    
    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}