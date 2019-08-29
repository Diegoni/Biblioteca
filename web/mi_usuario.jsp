<%-- 
    Document   : historial_prestamos
    Created on : 09-nov-2018, 15:34:40
    Author     : Tomas Camargo
--%>
<%
    if(session.getAttribute("user") == null){
        response.sendRedirect("login.jsp");
        out.println(session.getAttribute("user"));   
    }
    String tipo;
    if(session.getAttribute("type") == null){
        tipo = "1";
    } else {
        tipo = "0";
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Mi usuario</title>

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
          <center><h1>Mi usuario</h1></center>
        </div>
      </div>
       
       <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="nombre">Nombre</label>
                                <input type="text" class="form-control" id="nombreUser" name="user">
            </div>
          </div>
          <div class="col-3"></div>
      </div>
              <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="nombre">Apellido</label>
                                <input type="text" class="form-control" id="apellidoUser" name="user">
            </div>
          </div>
          <div class="col-3"></div>
      </div>
              <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
            <label for="nombre">DNI (Sin puntos)</label>
                                <input type="text" class="form-control" id="dni" name="user">
            </div>
          </div>
          <div class="col-3"></div>
      </div>
       <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
            <label for="nombre">Usuario</label>
                        <input type="text" class="form-control" id="User" name="user">
            </div>
          </div>
          <div class="col-3"></div>
      </div>
        <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
            <label for="nombre">Contraseña</label>
            <input type="text" class="form-control" id="Pass" name="user">
            </div>
          </div>
          <div class="col-3"></div>
      </div>
       
      <div class="row">
        <div class="col-8"></div>
        <div class="col-3">
            <button class="btn btn-secondary btn-lg " type="button" id="editar" onclick="editarUsuario('ControladorUsuario', 'user')">Modificar</button>
        </div>
      </div>
       <div class="alert_position" id="container-alert">

        </div>
 
               
            </div>

        </div>
  
    
      
    
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="js/verificacion.js"></script>
    <script type="text/javascript" src="js/ajax.js" onload="preBusqueda(<%out.println(session.getAttribute("id"));%>, 'ControladorUsuario', <%out.println(tipo);%>), almacenarID(<%out.println(session.getAttribute("id"));%>)"></script>
    </body>
</html>



