<%
    int admin = 0;
    if(session.getAttribute("type") == null){
        admin = -1;
    } else {
        admin = 1;
    }
%>
<link href="css/logo-nav.css" rel="stylesheet">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href="#">
          <img src="img/NUEVOLOGO copy.png" width="12%" height="12%" alt="">
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <button class="btn btn-default" type="button" style="background-color: #343a40; text-align: left;"><a href="index.jsp">Inicio</a></button>
            </li>
            <li class="nav-item" style="<%if(admin == -1){out.write("display : none;");}%>">
              <div class="dropdown">
                <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" style="background-color: #343a40; text-align: left;">Prestamo
                <span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <li><a href="prestar_libro.jsp">Prestar libro</a></li>
                    <li><a href="historial_prestamos.jsp">Historial</a></li>
                    
                </ul>
              </div>
            </li>
            <li class="nav-item" style="width: 10px;"></li>
            <li class="nav-item">
                <div class="dropdown">
                    <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" style="background-color: #343a40; text-align: left;">Libros
                    <span class="caret"></span></button>
                    <ul class="dropdown-menu">
                        <li><a href="cargar_libro.jsp" style="<%if(admin == -1){out.write("display : none;");}%>">Añadir</a></li>
                        <li><a href="modificar_libro.jsp" style="<%if(admin == -1){out.write("display : none;");}%>">Modificar</a></li>
                        <li><a href="inventario.jsp">Inventario</a></li>
                    </ul>
                  </div>
            </li>
            <li class="nav-item" style="width: 10px;"></li>
            <li class="nav-item" style="<%if(admin == -1){out.write("display : none;");}%>">
                <div class="dropdown">
                    <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" style="background-color: #343a40; text-align: left;">Gestion
                    <span class="caret"></span></button>
                    <ul class="dropdown-menu">
                        <!--<li><a href="reportes.jsp">Reportes</a></li>-->
                        <li><a href="carreras.jsp">Carreras</a></li>
                        <li><a href="autores.jsp">Autores</a></li>
                        <li><a href="usuario.jsp">Usuarios</a></li>
                    </ul>
                </div>
            </li>
                        <li class="nav-item" style="width: 10px;"></li>
            <li class="nav-item" style="<%if(admin == -1){out.write("display : none;");}%>">
                <div class="dropdown">
                    <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" style="background-color: #343a40; text-align: left;">Bedel
                    <span class="caret"></span></button>
                    <ul class="dropdown-menu">
                        <!--<li><a href="reportes.jsp">Reportes</a></li>-->
                        <li><a href="crear-bedel.jsp">Crear bedel</a></li>
                        <li><a href="mi_usuario.jsp">Mi usuario</a></li>

                    </ul>
                </div>
            </li>
            <li class="nav-item" style="width: 10px;"></li>
            <li class="nav-item" style="<%if(admin == 1){out.write("display : none;");}%>">
                <div class="dropdown">
                    <button class="btn btn-default" type="button" style="background-color: #343a40; text-align: left;" 
                            onclick="window.location.replace('../BibliotecaBootstrap/mi_usuario.jsp');" >Mi usuario</button>
                </div>
            </li>
            <li class="nav-item">
                <div class="dropdown">
                    <a href="logout.jsp"><button class="btn btn-default" type="button" style="background-color: #343a40; text-align: left;">Salir</button>
                    </a>
                </div>
                <script>
                    function cerrarSesion(){
                        $.post('ControladorUsuario', {
                            envio : "verificar;,;invalidate"
                        });
                        location.replace("login.jsp");
                    }
                </script>
            </li>
          </ul>
        </div>
      </div>
    </nav>
                


