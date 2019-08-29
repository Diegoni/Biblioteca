/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasperreport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import util.ConectorDB;

public class ReportePrestamos {
        
    
	public static void main(String[] args) throws JRException, FileNotFoundException, IOException {
            ArrayList array = new ArrayList();
            array.add("`reporte_prestamos`.`Socio`");
            ConectorDB CONECTOR = new ConectorDB();
            ResultSet rs = CONECTOR.JasperReport("SELECT "+ 
            "`prestamo`.`numPrestamo` AS `Numero de prestamo`, "+
            "CONCAT_WS(' ', `socio`.`Nombre`, `socio`.`Apellido`) AS `Socio`, "+
            "`socio`.`DNI`, "+
            "`carrera`.`nombreCarrera` AS `Carrera`, "+
            "`libro`.`Titulo` AS `Titulo de libro`, "+
            "`prestamo`.`Fecha` AS `Fecha de prestamo`, "+
            "`prestamo`.`Fecha` + INTERVAL `prestamo`.`Plazo` DAY AS `Fecha de devolución` "+
            "FROM "+
            "`prestamo` "+
            "JOIN `socio` ON `prestamo`.`Socio_idSocio` = `socio`.`idSocio` "+
            "JOIN `libro` ON `prestamo`.`Libro_idlibro` = `libro`.`idlibro` "+
            "JOIN `carrera` ON `socio`.`Carrera_idCarrera` = `carrera`.`idCarrera` "+
            "WHERE "+
            "`prestamo`.`Estado_idEstado` != 4;");
            JasperReport reporte; //Creo el objeto reporte
         // Ubicacion del Reporte
            //param.put("CONDITION1", array.get(1));
            String path = "C:\\Users\\Gamer Amd\\Documents\\NetBeansProjects\\BibliotecaBootstrap\\src\\java\\jasperreport\\jrtemplates\\reportes_biblioteca.jasper";
            try {
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path); //Cargo el reporte al objeto
            JasperPrint jprint = JasperFillManager.fillReport(path, new HashMap(), new JRResultSetDataSource(rs)); //Llenado del Reporte con Tres parametros ubicacion,parametros,conexion a la base de datos
            JasperViewer viewer = new JasperViewer(jprint,false); //Creamos la vista del Reporte
            viewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Le agregamos que se cierre solo el reporte cuando lo cierre el usuario
            viewer.setVisible(true); //Inicializamos la vista del Reporte

            

 
            File file = new File("C:\\backups\\file.xls");
            FileOutputStream fos = new FileOutputStream(file,true);
 
            JRXlsExporter exporterXLS = new JRXlsExporter();
            exporterXLS.setExporterInput(new SimpleExporterInput(jprint));
            exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(fos));
            exporterXLS.exportReport();
 
            fos.flush();
            fos.close();
            
            File file1 = new File("C:\\backups\\file.pdf");
            FileOutputStream fos1 = new FileOutputStream(file1,true);
            
            JRPdfExporter exporterPDF = new JRPdfExporter();
            exporterPDF.setExporterInput(new SimpleExporterInput(jprint));
            exporterPDF.setExporterOutput(new SimpleOutputStreamExporterOutput(fos1));
            exporterPDF.exportReport();
 
            fos1.flush();
            fos1.close();
            
        } catch (JRException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }   

        // coding For Excel:
            
    }
}


