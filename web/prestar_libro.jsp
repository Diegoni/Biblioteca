<%
    if(session.getAttribute("user") == null){
        response.sendRedirect("login.jsp");
    }
    if(session.getAttribute("type") == null){
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
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

  </head>

  <body>

    <!-- Navigation -->
    <%@include file="nav-bar.jsp" %>

    <!-- Page Content -->
    <div class="container">
    <div class="container" id="form">
      <div class="row mt-5">
        <div class="col-12">
          <center><h1>Prestar libro</h1></center>
        </div>
      </div>
      <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="socio">Socio </label>
              <input type="text" class="form-control" list="datalistSocio" id='campoSocio'
                               onfocus="llenarDatalist('datalistSocio', 'ControladorSocio')"
                               name="atributosPrestamo">
              <datalist id="datalistSocio"></datalist>
            </div>
          </div>
          <div class="col-3">
            <label for="tit-libro" class="relleno">relleno</label>
              <div class="form-group input-group">
                <input type="hidden" class="form-control" id="tit-libro">
                <span class="input-group-btn">
                    <button class="btn btn-primary" type="button" 
                            data-content="Agregar un socio para prestamo" rel="popover" 
                            data-placement="right" 
                            data-trigger="hover" id="cargarSocio"
                            data-toggle="modal" data-target="#AgregarSocio">Añadir socio</button>
                </span>
              </div>
          </div>
      </div>
      <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="tit-libro">Titulo del libro</label>
              <div class="input-group">
              <input type="text" class="form-control" list="datalistLibro" id='titulo'
                            onfocus="llenarDatalist('datalistLibro', 'ControladorLibro')"
                            name="atributosPrestamo" onchange="defecto()" aria-describedby="basic-addon">
              <div class="input-group-append">
              <span class="input-group-text" id="basic-addon"><img id="confirm" src="img/lupa.png" style="width: 20px; height: 20px;"></span>
              </div>
                        <datalist id="datalistLibro"></datalist>
              
              </div>
                        
            </div>
          </div>
          <div class="col-3">
              <label for="tit-libro" class="relleno">relleno</label>
              <div class="form-group input-group">
                <input type="hidden" class="form-control" id="tit-libro">
                <span class="input-group-btn">
                    <button class="btn btn-primary" type="button" onclick="disponibilidad(0)">Verificar</button>
                </span>
              </div>
          </div>
      </div>
      <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="fecha">Fecha</label>
              <input type="date" class="form-control" id="fecha" disabled>
            </div>
          </div>
          <div class="col-3"></div>
      </div>
      <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="plazo">Plazo</label>
              <input type="text" class="form-control" id="plazo">
            </div>
          </div>
          <div class="col-3"></div>
      </div>
      <div class="row">
        <div class="col-8"></div>
        <div class="col-3">
            <button class="btn btn-secondary btn-lg " type="button" id="prestar" onclick="disponibilidad(1)">Prestar</button>
        </div>
      </div>
        

        <div class="alert_position" id="container-alert">

        </div>
        
        <br>
    </div>
    </div>
    <!-- /.container -->
    
        <div id="AgregarSocio" class="modal fade" role="dialog">
        <div class="modal-dialog" style="margin: 7rem auto;">

        <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Agregar socio</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <label for="carrera">Carrera</label>
                                <select  id="carrera" class="form-control" name="socio">
                                    
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" class="form-control" id="nombre" name="socio">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <label for="apellido">Apellido</label>
                                <input type="text" class="form-control" id="apellido" name="socio">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <label for="dni">DNI (Sin puntos)</label>
                                <input type="text" class="form-control" id="dni" name="socio">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" 
                            onclick="cargar('socio','ControladorSocio')">Agregar</button>
                </div>
            </div>

        </div>
    </div>

    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script>
        $('#cargarSocio').popover();
    </script>
    <script type="text/javascript" src="js/PopUp.js"></script>
    <script src="js/ajax.js" onload="llenarSelect('carrera','ControladorCarrera')"></script>
    <script src="js/verificacion.js"></script>
  </body>

</html>
