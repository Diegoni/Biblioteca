package controlador;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.CodLibro;
import modelo.Libro;
import modelo.Prestamo;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Exportacion extends HttpServlet {

    Workbook workbook; // new HSSFWorkbook() for generating `.xls` file
    CreationHelper createHelper;
    CellStyle EstiloDeTitulo;
    CellStyle EstiloDeCelda;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String select = "SELECT \n" +
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
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"Exportacion.xlsx");
        workbook = new XSSFWorkbook();
        createHelper = workbook.getCreationHelper();
        this.setEstiloDeTitulo();
        this.setEstiloDeCelda();

        Libro modeloLibro = new Libro();
        CodLibro modeloCodigo = new CodLibro();
        Prestamo modeloPrestamo = new Prestamo();

        String[] prestamos = {"Numero de Préstamo", "Libro", "DNI", "Nombre", "Apellido", "Carrera", "Plazo", "Fecha de Inicio", "Fecha estimada de Finalización", "Estado"};
        String[] librosDetalle = {"Código de Libro", "Título", "Autor", "Tema"};
        String[] librosCantidad = {"Cantidad", "Título", "Autor", "Tema"};

        crearHoja(prestamos, modeloPrestamo.selectTodos(), "Préstamos");
        crearHoja(librosDetalle, modeloCodigo.selectTodos(), "Libros");
        crearHoja(librosCantidad, modeloLibro.selectTodosConCantidad(select), "Cantidad por Libro");

        workbook.write(response.getOutputStream());
        workbook.close();

    }

    private void crearHoja(String[] columnas, ArrayList informe, String nombrePagina) {
        Sheet sheet = workbook.createSheet(nombrePagina);
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(EstiloDeTitulo);
        }
        int rowNum = 1;
        for (int i = 0; i < informe.size(); i++) {
            ArrayList previo = (ArrayList) informe.get(i);
            Row row = sheet.createRow(rowNum++);
            for (int j = 0; j < previo.size(); j++) {
                row.createCell(j).setCellValue(previo.get(j).toString());
                row.getCell(j).setCellStyle(EstiloDeCelda);
            }
        }
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void setEstiloDeTitulo() {
        EstiloDeTitulo = workbook.createCellStyle();
        EstiloDeTitulo.setFillForegroundColor(IndexedColors.INDIGO.getIndex());
        EstiloDeTitulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        EstiloDeTitulo.setFont(font);
        EstiloDeTitulo.setBorderBottom(BorderStyle.THIN);
        EstiloDeTitulo.setBorderTop(BorderStyle.THIN);
        EstiloDeTitulo.setBorderRight(BorderStyle.THIN);
        EstiloDeTitulo.setBorderLeft(BorderStyle.THIN);
    }

    public void setEstiloDeCelda() {
        EstiloDeCelda = workbook.createCellStyle();
        EstiloDeCelda.setBorderBottom(BorderStyle.THIN);
        EstiloDeCelda.setBorderTop(BorderStyle.THIN);
        EstiloDeCelda.setBorderRight(BorderStyle.THIN);
        EstiloDeCelda.setBorderLeft(BorderStyle.THIN);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
