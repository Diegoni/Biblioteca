<%-- 
    Document   : historial_prestamos
    Created on : 09-nov-2018, 15:34:40
    Author     : Tomas Camargo
--%>
<%
    if(session.getAttribute("user") == null){
        response.sendRedirect("login.jsp");
    }
    if(session.getAttribute("type") == null){
        response.sendRedirect("login.jsp");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Historial de prestamos</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/logo-nav.css" rel="stylesheet">

  </head>
    <body>
        <jsp:include page="nav-bar.jsp"/>
        <div class="container">
    <div class="container"  id="form">
      <div class="row mt-5">
        <div class="col-12">
          <center><h1>Historial de prestamos</h1></center>
        </div>
      </div>
      <div id="row">
          <div class="form-group row">
            <label for="filtro" class="col-sm-2 col-form-label">Filtrar por:</label>
            <div class="col-2">
                <select id="campoEstado" class="form-control" onchange="cambiarFiltro()">
                        <option value="libro">Libro</option>
                        <option value="socio">Socio</option>
                        <option value="fecha">Fecha</option>
                        <option value="estado">Estado</option>
                        <option value="carrera">Carrera</option>
                    </select>
              </div>
            <div class="col-8">
                    <span id="espaciado"></span>
                    <input class="form-control" id="filtro"  placeholder="Filtrar" onkeyup="filtrar()">
                </div>
            </div>
            </div>
        <div class="scrolleable">
            <table class="table">
                <thead>
                    <tr>
                        <td>Nro de Préstamo</td>
                        <td>Libro</td>
                        <td>Socio</td>
                        <td>Carrera</td>
                        <td>Fecha de prestamo</td>
                        <td>Fecha de devolución</td>
                        <td>Estado</td>
                        <td>Devolución</td>
                    </tr>
                </thead>
                <tbody id="historial">

                </tbody>
            </table>
        </div>
            <center>
        <label >Exportar a Excel</label>
        <form action="Exportacion" name="exportar">
            <button type="submit" class="btn" formtarget="exportar" style="background-color: #207347;">
                <img src="img/excel-logo.png" width="30px" height="30px">
            </button>
        </form>
    </center>
      
        <br>
    </div>
        </div>
    
        <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="js/PopUp.js"></script>
    <script type="text/javascript" src="js/ajax.js" onload="llenarHistorial('historial', 'ControladorPrestamo')"></script>
    </body>
</html>
