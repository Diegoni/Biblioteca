package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
* Esta clase permite la comunicación entre la aplicación y la base de datos.
* @author IES 9-024 LAVALLE.
* @version 2.018.
*/
public final class ConectorDB {

    private final String UNIFORM_RESOURCE_LOCATION = "jdbc:mysql://localhost/biblioteca";
    private final String USER = "bibliotecario";
    private final String PASSWORD = "ies9024";

    private Connection conexion;
    
    /**
     * Devuelve la conexión a la base de datos almacenada en memoria.
     * @return Connection conexion. 
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * No recibe argumentos/parametros ni inicializa atributos, ejecuta método GenerarBackUp.
     */
    public ConectorDB() {
        GenerarBackUp();
    }

    /**
     * Inicia la conexión a la base de datos.
     */
    public void IniciarConexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(UNIFORM_RESOURCE_LOCATION, USER, PASSWORD);
            System.out.println("Conexión.");
        } catch (SQLException e) {
            System.out.println("Conexión: Error.");
            System.out.println("Revisar Contraseña");
        } catch (ClassNotFoundException e) {
            System.out.println("Clase del Driver no encontrada");
        }     
    }
    
    /**
    * Ejecuta una consulta de tipo SELECT a la base de datos.
    * @param Select String: Cadena que contiene la consulta a ejecutar.
    * @return ArrayList Lista_completa: Contiene los elementos obtenidos de la consulta SELECT.
    */
    public ArrayList ejecutarConsulta(String Select) {
        this.IniciarConexion();
        System.out.println(Select);
        ArrayList Lista_completa = new ArrayList();
        ResultSet rs;
        ResultSetMetaData rsmd;
        try {
            Statement sentencia = this.conexion.createStatement();
            rs = sentencia.executeQuery(Select);
            rsmd = rs.getMetaData();
            int Columnas = rsmd.getColumnCount();
            while (rs.next()) {
                ArrayList Fila = new ArrayList();
                for (int i = 1; i <= Columnas; i++) {
                    Fila.add(rs.getString(i));
                }
                Lista_completa.add(Fila);
            }

            sentencia.close(); // <- Cierre de sentencia.

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        this.CerrarConexion(); // <- Cierre de conexión.

        return Lista_completa;
    }

    /**
     * Ejecuta una consulta INSERT, DELETE o UPDATE a la base de datos.
     * @param Sentencia String: Cadena que contiene la consulta a ejecutar.
     * @return int Existencia_de_registro: Su valor va a ser igual a 
     * la cantidad de tuplas afectadas. 0 = Error.
     */
    public int ejecutarSentencia(String Sentencia) {
        this.IniciarConexion();
        int Existencia_de_registro = 0;
        try {
            Statement sentencia = this.conexion.createStatement();
            Existencia_de_registro = sentencia.executeUpdate(Sentencia);

            sentencia.close(); // <- Cierre de sentencia.

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        this.CerrarConexion(); // <- Cierre de conexión.
        
        return Existencia_de_registro;
    }
    
    /**
     * Cierra la conexión a la base de datos.
     */    
    public void CerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConectorDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Deprecated
    public ResultSet JasperReport(String Select) {
        System.out.println(Select);
        this.IniciarConexion();
        ResultSet rs = null;
        try {
            Statement sentencia = this.conexion.createStatement();
            rs = sentencia.executeQuery(Select);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;    
    }
    
    /**
     * Realiza una transacción a la base de datos, con las cadenas de texto que recibe.
     * @param params String[]: Cadenas con consultas a ejecutar. 
     * @return boolean respuesta:
     * <ul>
     * <li>true si la transacción se realizó correctamente.</li> 
     * <li>false para el fallo de la misma.</li>
     * </ul>
     */
    public boolean Transaction(String [] params) throws SQLException{
        boolean respuesta;
        this.IniciarConexion();
        this.conexion.setAutoCommit(false);
        try {
            Statement sentencia = this.conexion.createStatement();
            for(String param : params){
                System.out.println(param);
                sentencia.executeUpdate(param);
            }
            conexion.commit();
            respuesta = true;

        } catch (SQLException ex) {
            
            conexion.rollback();
            
            ex.printStackTrace();
            respuesta = false;
        }
        
        this.CerrarConexion(); // <- Cierre de conexión.
        return respuesta;
    }
    
    /**
     * Realiza un backup diario de la base de datos.
     * @see BackUp
     */
    public void GenerarBackUp(){
        Date date = new Date();
        Timer temporizador = new Timer();
        temporizador.schedule(new BackUp(), date, 86400000);
    }
}