/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import util.ConectorDB;

/**
 *
 * @author Tommy
 */
public class JasperReport extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param opcion
     * @param query
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws net.sf.jasperreports.engine.JRException
     */
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, int opcion, String query)
            throws ServletException, IOException, JRException {
        response.setContentType("text/html;charset=UTF-8");

        Map parameters = new HashMap ();
        ServletOutputStream out;
        String WHERE = "";
        
        byte[] fichero = null;
        ConectorDB CONECTOR = new ConectorDB();
        System.out.println("/"+query+"/");
        String finalQuery = "";

        if("".equals(query)){
            WHERE = "";
        } else {
            WHERE = "WHERE";
        }
        
        if(opcion == 1){
            finalQuery = "SELECT * FROM `rep_deudores_de_libros` "+ WHERE + query.replaceAll("AND?", "");
            System.out.println(finalQuery);
            ResultSet rs = CONECTOR.JasperReport(finalQuery);
            fichero = JasperRunManager.runReportToPdf("C:\\Users\\Gamer Amd\\Documents\\NetBeansProjects\\BibliotecaBootstrap\\src\\java\\jasperreport\\jrtemplates\\rep_deudores_de_libros.jasper", parameters, new JRResultSetDataSource(rs));
        } 
        else if(opcion == 2){
            finalQuery = "SELECT * FROM `rep_prestamos_realizados` "+ WHERE + query.replaceAll("AND?", "");
            System.out.println(finalQuery);
            ResultSet rs = CONECTOR.JasperReport(finalQuery);
            fichero = JasperRunManager.runReportToPdf("C:\\Users\\Gamer Amd\\Documents\\NetBeansProjects\\BibliotecaBootstrap\\src\\java\\jasperreport\\jrtemplates\\rep_prestamos_realizados.jasper", parameters, new JRResultSetDataSource(rs));
        } 
      
        String FileName = "informeDemo.pdf";

// Y enviamos el pdf a la salida del navegador como podríamos hacer con cualquier otro pdf
        response.setContentType ("application/pdf");
        response.setHeader ("Content-disposition", "inline; filename="+FileName);
        response.setHeader ("Cache-Control", "max-age=30");
        response.setHeader ("Pragma", "No-cache");
        response.setDateHeader ("Expires", 0);
        response.setContentLength (fichero.length);
        out = response.getOutputStream ();

        out.write (fichero, 0, fichero.length);
        out.flush ();
        out.close ();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String opcion = request.getParameter("opcion");
        String query = request.getParameter("query");
        int opcionInt = Integer.parseInt(opcion);
        System.out.println(opcion+"  "+query.replaceAll("AND?", ""));
        try {
            processRequest(request, response, opcionInt , query);
        } catch (JRException ex) {
            Logger.getLogger(JasperReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String opcion = request.getParameter("opcion");
        String query = request.getParameter("query");
        int opcionInt = Integer.parseInt(opcion);
        System.out.println(opcion+"  "+query.replaceAll("AND?", ""));
        try {
            processRequest(request, response, opcionInt , query);
        } catch (JRException ex) {
            Logger.getLogger(JasperReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
