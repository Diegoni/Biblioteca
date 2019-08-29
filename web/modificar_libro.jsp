<%-- 
    Document   : modificar_libro
    Created on : 09-nov-2018, 13:00:30
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

    <title>Modificar Libro</title>

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
          <center><h1>Modificar libro</h1></center>
        </div>
      </div>
      <input type="hidden" id="id">
      <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="busca">Busqueda</label>
              <input type="text" class="form-control" id="busca" list="datalistLibro" 
                               type="text" onfocus="llenarDatalist('datalistLibro', 'ControladorLibro')">
                        <datalist id="datalistLibro"></datalist>
            </div>
          </div>
          <div class="col-3">
            <label for="tit-libro" class="relleno">relleno</label>
              <div class="form-group input-group">
                <input type="hidden" class="form-control" id="tit-libro">
                <span class="input-group-btn">
                    <button class="btn btn-primary" type="button" id="Busqueda">Buscar</button>
                    <button class="btn btn-primary" type="button" id="Cancelar" style="display: none;">Cancelar</button>
                </span>
              </div>
          </div>
      </div>
      <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="titulo">Título del Libro</label>
              <input type="text" class="form-control" id="titulo" name="atributosLibro">
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
              
          </div>
      </div>
      <div class="row">
          <div class="col-2"></div>
          <div class="col-6">
            <div class="form-group">
              <label for="campoTemas">Tema</label>
              <select class="form-control"  id="campoTemas" name="atributosLibro" ></select>
            </div>
          </div>
          <div class="col-3">

          </div>
      </div>
      <div class="row">
        <div class="col-8"></div>
        <div class="col-3">
            <button class="btn btn-secondary btn-lg " type="button" id="modificar">Modificar</button>
        </div>
      </div>
      
      <div class="alert_position" id="container-alert">

        </div>
        <br>
    </div>
        </div>
    
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="js/PopUp.js"></script>
    <script>
            var elemnt = document.getElementsByName("atributosLibro");
            $(elemnt).attr('disabled', true);
            $("#campoAutor").attr('disabled', true);
            $('#modificar').attr('disabled', true);
            $("#Cancelar").click(function (){
                $('#busca').attr('disabled', false);
                $('#modificar').attr('disabled', true);
                $(elemnt).val("");
                $("#campoAutor").val("");
                $(this).hide();
                $('#Busqueda').show();
                $(elemnt).attr('disabled', true);
                $("#campoAutor").attr('disabled', true);
                var padre = document.getElementById("padre").innerHTML = "";
            }); 
        </script>
        <script src="js/ajax.js" onload="llenarSelect('campoTemas','ControladorTema'), llenarDatalist('datalistAutores', 'ControladorAutor')"></script>
    <script src="js/verificacion.js"></script>
        <script src="js/manejo_autores.js"></script>
    </body>
</html>
