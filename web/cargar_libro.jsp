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

    <title>Añadir Libro</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/logo-nav.css" rel="stylesheet">

    
    <style>
                  #addAutor:hover{
                      cursor: pointer;
                  }
                  .autoresAgregados{
                      border: 1px solid #ced4da;
                      border-radius: 0.25rem;
                      height: 60px;
                      width: 100%;
                      color: #FFFFFF;
                      float: left;
                      overflow-y: scroll;
                  }
                  .item{
                      background-color: #ced4da;
                      height: 20px;
                      border-radius: 10px;
                      font-size: 12px;
                      margin: 2px;
                      display:inline-block;
                  }
                  .contenido{
                      margin: 5px;
                  }
                  .delete{
                      color: #808080;
                  }
                  .delete:hover{
                      color: #000000;
                      cursor:  pointer;
                  }
              </style>

  </head>

  <body>

    <!-- Navigation -->
    <%@include file="nav-bar.jsp" %>

    <!-- Page Content -->
    <div class="container">
    <div class="container"  id="form">
      <div class="row mt-5">
        <div class="col-12">
          <center><h1>Añadir libro</h1></center>
        </div>
      </div>
      <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="tit-libro">Titulo del libro</label>
              <input type="text" class="form-control" name="atributosLibro">
              <datalist id="datalistAutores"></datalist>
            </div>
          </div>
          <div class="col-3">
            
          </div>
      </div>
      <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="nom-autor">Nombre del autor/es</label>
              <div class="input-group">
                <input type="text" class="form-control" list="datalistAutores" 
                     id='campoAutor' onfocus="llenarDatalist('datalistAutores', 'ControladorAutor')" 
                     >
                <div class="input-group-append" onclick="crearElemento()">
                    <button class="input-group-text">&darr;</button>
                </div>
                <datalist id="datalistAutores"></datalist>
              </div>
              <div class="autoresAgregados mt-1" id="padre">
                  
              </div>
              
            </div>
          </div>
          <div class="col-3">
              <label for="tit-libro" class="relleno">relleno</label>
              <div class="form-group input-group">
                <input type="hidden" class="form-control" id="tit-libro">
                <span class="input-group-btn">
                    <button class="btn btn-primary" type="button" 
                    data-toggle="modal" data-target="#AgregarAutor">Añadir autor</button>
                </span>
              </div>
          </div>
      </div>
      <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="cod-lib">Código libro</label>
              <input type="text" class="form-control" id="codLib" name="atributosLibro">
            </div>
          </div>
          <div class="col-3"></div>
      </div>
      <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="tema">Tema</label>
              <select class="form-control" id="campoTemas" name="atributosLibro"></select>
            </div>
          </div>
          <div class="col-3">
              <label for="tit-libro" class="relleno">relleno</label>
              <div class="form-group input-group">
                <input type="hidden" class="form-control" id="tit-libro">
                <span class="input-group-btn">
                    <button class="btn btn-primary" type="button" 
                            data-toggle="modal" data-target="#AgregarTema">Añadir tema</button>
                </span>
              </div>
          </div>
      </div>
      <div class="row">
        <div class="col-8"></div>
        <div class="col-3">
            <button class="btn btn-secondary btn-lg " type="button" id="Cargar">Añadir</button>
        </div>
      </div>
        
        <div class="alert_position" id="container-alert">

        </div>
        <br>
    </div>
        </div>
    
    <!-- Pop up Agregar Autor -->
    <div id="AgregarAutor" class="modal fade" role="dialog">
        <div class="modal-dialog" style="margin: 7rem auto;">

        <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Agregar autor</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <label for="cod-lib">Nombre del autor</label>
                                <input type="text" class="form-control" id='nombre' name="autor">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <label for="cod-lib">Apellido del autor</label>
                                <input type="text" class="form-control" id='apellido' name="autor">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" 
                           onclick="cargar('autor','ControladorAutor')">Agregar</button>
                </div>
            </div>

        </div>
    </div>
    <!--Fin Pop up Agregar Autor -->
    
    <!-- Pop up Agregar Tema -->
    <div id="AgregarTema" class="modal fade" role="dialog">
        <div class="modal-dialog" style="margin: 7rem auto;">

        <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Agregar tema</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <label for="cod-lib">Nuevo tema</label>
                                <input type="text" class="form-control" id='nombreTema' name="tema">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" 
                           onclick="cargar('tema', 'ControladorTema')">Agregar</button>
                </div>
            </div>

        </div>
    </div>    
    
    
    
    
    
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="js/ajax.js" 
        onload="llenarSelect('campoUbicaciones','ControladorUbicacion'); 
            llenarSelect('campoTemas','ControladorTema')"></script>
    <script src="js/verificacion.js"></script>
        <script src="js/manejo_autores.js"></script>
  </body>

</html>
