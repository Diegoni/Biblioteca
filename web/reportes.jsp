<!DOCTYPE html>
<%
    if(session.getAttribute("user") == null){
        response.sendRedirect("login.jsp");
    }
    if(session.getAttribute("type") == null){
        response.sendRedirect("login.jsp");
    }
%>
<html lang="es">

  <head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Prestar Libro</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/logo-nav.css" rel="stylesheet">
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="js/reportes.js"></script>

  </head>

  <body onload="opcion()">

    <!-- Navigation -->
    <%@include file="nav-bar.jsp" %>

    <!-- Page Content -->
    <div class="container">
    <div class="container" id="form">
      <div class="row mt-5">

        <div class="col-12">
          <center><h1>Crear reporte</h1></center>
        </div>

      </div>
        <div class="row" >
        <div class="col-12">
            <center>
                <div class="btn-group" style="margin-bottom: 10px">
                    <button type="button" class="btn btn-primary" style="width: auto" id="deudores">Deudores de libros</button>
                    <button type="button" class="btn btn-primary" style="width: auto" id="prestamos">Prestamos realizados</button>
                    <button type="button" class="btn btn-primary" style="width: auto" id="inventario">Inventario</button>
                    <button type="button" class="btn btn-primary" style="width: auto" id="libroscarrera">Libros por carrera</button>
                </div>
            </center>
        </div> 
      </div>
        
        <!-- Reporte duedores de libros -->
      <div class="row" id='campoSocioContainer' style="display: none">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="socio">Socio </label>
              <input type="text" class="form-control" list="datalistSocio" id='campoSocio'
                               onfocus="llenarDatalist('datalistSocio', 'ControladorSocio')"
                               name="filtro_reportes" placeholder="Para ver todos los socios, no llene el campo">
              <datalist id="datalistSocio"></datalist>
            </div>
          </div>
          <div class="col-3">
            
          </div>
      </div>
            <div class="row" id="carreraContainer" style="display: none">
                <div class="col-2"></div>
                <div class="col-6">
                    <div class="form-group">
                                      <label for="carrera">Carrera</label>
                                      <select  id="carrera" class="form-control" name="filtro_reportes">
                                          
                                      </select>
                                  </div>
                </div>
                <div class="col-3">

                </div>
            </div>
                    <div class="row" id="mesContainer" style="display: none">
                <div class="col-2"></div>
                <div class="col-6">
                    <div class="form-group">
                                      <label for="mes">Mes</label>
                                      <select  id="mes" class="form-control" name="filtro_reportes">
                                          <option value="0">Todos</option>
                                          <option value="1">Enero</option>
                                          <option value="2">Febrero</option>
                                          <option value="3">Marzo</option>
                                          <option value="4">Abril</option>
                                          <option value="5">Mayo</option>
                                          <option value="6">Junio</option>
                                          <option value="7">Julio</option>
                                          <option value="8">Agosto</option>
                                          <option value="9">Septiembre</option>
                                          <option value="10">Octubre</option>
                                          <option value="11">Noviembre</option>
                                          <option value="12">Diciembre</option>
                                      </select>
                                  </div>
                </div>
                <div class="col-3">

                </div>
            </div>
        <div class="row" id="libro-catContainer" style="display: none">
                <div class="col-2"></div>
                <div class="col-6">
                    <div class="form-group">
                                      <label for="libro-cat">Libros</label>
                                      <select  id="libro-cat" class="form-control" name="filtro_reportes">
                                          <option value="1">Todos</option>
                                          <option value="2">En biblioteca</option>
                                          <option value="3">Prestados</option>
                                      </select>
                                  </div>
                </div>
                <div class="col-3">

                </div>
            </div>
        <div class="row" id="btn" style="display: none">
        <div class="col-8"></div>
        <div class="col-3">
            <button class="btn btn-secondary btn-lg mb-3" type="button" id="generar" style="width: auto" >Crear reporte</button>
        </div>
      </div>
        <!-- Fin reporte -->

    </div>
    </div>

    <!-- Bootstrap core JavaScript -->
    
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="js/PopUp.js"></script>
    <script src="js/ajax.js" onload="llenarSelect('carrera','ControladorCarrera')" charset="UTF-8"></script>
    <script>
        function opcion(){
            var select = document.getElementById("carrera");
            var option = document.createElement("OPTION");
            option.setAttribute("value", "0");
            option.text = "Todas";
            select.add(option);

        }
    </script>
    <script src="js/verificacion.js"></script>
    
  </body>

</html>

