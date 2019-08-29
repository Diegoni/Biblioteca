
var IDENTIFICADOR;
function almacenarID(id){
    IDENTIFICADOR = id;
}

function buscar(id, controlador){
    var xhr = new XMLHttpRequest();
    xhr.open('GET', controlador + "?envio=consulta;,;Buscar;,;"+id);
    xhr.onload = function () {
        if (xhr.status === 200) {
            var jsonAjax = JSON.parse(xhr.responseText);
            llenarCampos(jsonAjax, controlador);
        } else {
        }
    };
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

var idSocio;
function llenarCampos(json, controlador){
    if(controlador === "ControladorAutor"){
        $("#nombreAutor").val(json.Nombre);
        $("#apellidoAutor").val(json.Apellido);
    } else if (controlador === "ControladorCarrera"){
        $("#nombreCarrera").val(json.Carrera);
    } else if (controlador === "ControladorUsuario"){
        console.log(json);
        $("#nombreUser").val(json[0].nombre);
        $("#apellidoUser").val(json[0].apellido);
        $("#dni").val(json[0].dni);
        $("#User").val(json[0].usuario);
        $("#Pass").val(json[0].pass);
        idSocio = ""+json[0].idSocio;
        var titulo = document.getElementById("tituloUsuario");
        if(titulo !== null){
            titulo.innerHTML = json[0].nombre+" "+json[0].apellido;
        }
    }
}
                            

function llenarTablaUsuarios(controlador){
    var xhr = new XMLHttpRequest();
    console.log("Rellenando");
    xhr.open('GET', controlador + "?envio=consulta;,;alumno");
    xhr.onload = function () {
        if (xhr.status === 200) {
            userTable(JSON.parse(xhr.responseText));
        } else {
        }
    };
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function userTable(Json){
    var table = document.getElementById("ABMusuarios");
    $(table).empty();
    console.log(Json);
    for(var i=0; i<Json.length; i++){
        var row = table.insertRow(i);
        row.setAttribute("id", Json[i].id);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);
        var cell5 = row.insertCell(4);
        var cell6 = row.insertCell(5);
        cell1.innerHTML = Json[i].nombre;
        cell2.innerHTML = Json[i].dni;
        cell3.innerHTML = Json[i].usuario;
        cell4.innerHTML = Json[i].pass;
        cell5.innerHTML = "<button class='btn btn-danger' data-toggle='modal' "+
                "data-target='#Eliminar' onclick='almacenarID("+Json[i].id+")' >Eliminar</button>";
        cell6.innerHTML = "<button class='btn btn-warning' data-toggle='modal' "+
                "data-target='#Editar' onclick=\"preBusqueda("+Json[i].id+", 'ControladorUsuario'), \n\
                almacenarID("+Json[i].id+")\">Editar</button>";
    }
}

function preBusqueda(id, controlador) {
    buscar(id+";,;alumno", controlador);
}

function eliminarUsuario(){
    var envio = "consulta;,;Buscar;,;"+IDENTIFICADOR;
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'ControladorPrestamo?envio='+envio, true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            if(xhr.responseText === "debe"){
                crearAlerta("container-alert", "Error en eliminación", "No puede dar de baja a este usuario, ya que es deudor", "danger");
            } else {
                $.post('ControladorUsuario', {
                   envio : "eliminar;,;"+IDENTIFICADOR 
                });
                crearAlerta("container-alert", "Hecho", "Usuario eliminado correctamente", "success");
                llenarTablaUsuarios();
            }

        } else {
        }
    };
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function editarUsuario(controlador, name){
    var envio = "consulta;,;Update;,;";
    var elementos = document.getElementsByName(name);
    for(var x=0;x<elementos.length;x++){
        envio += elementos[x].value+";,;";
    }
    envio += IDENTIFICADOR+";,;"+$("#tipoUsuario").val()+";,;"+idSocio;
    console.log(envio);
    $.get(controlador, {
            envio: envio
        });
    crearAlerta("container-alert", "Hecho", "Usuario editado correctamente", "success");
    llenarTablaUsuarios("ControladorUsuario");
}

function crearAlerta(id, titulo, mensaje, tipo){
    titulo = decodeURI(titulo);
    mensaje = decodeURI(mensaje);
    var html = `<div class="alert alert-${tipo} alert-dismissible" id="alerta-generada" style="display: none">
                    <a href="#" class="close" onclick="$('#alerta-generada').hide()" aria-label="close">&times;</a>
                    <strong>${titulo}</strong>
                    <br>${mensaje}
                </div>`;
    document.getElementById(id).innerHTML = html;
    $("#alerta-generada").fadeIn();
}

function altaUserBedel(){
    var vacio = false;
    var elementos = document.getElementsByName("user");
    for(var x=0;x<elementos.length;x++){
        if(elementos[x].value === ""){
            vacio = true;
        }
    }
    if(vacio === true){
        crearAlerta("container-alert", "Error", "Complete todos los campos", "danger");
    } else {
        var envio = "consulta;,;verificar;,;";
        for(var x=0;x<elementos.length;x++){
            envio += elementos[x].value+";,;";
        }
        console.log(envio);
        var xhr = new XMLHttpRequest();
        xhr.open('POST', 'ControladorUsuario?envio='+envio, true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                if(xhr.responseText === "user"){
                    crearAlerta("container-alert", "Error", "Este nombre de usuario ya existe", "danger");
                } else if(xhr.responseText === "dni"){
                    crearAlerta("container-alert", "Error", "El D.N.I. ya fue ingresado", "danger");
                } else {
                    $.post('ControladorUsuario', {
                       envio : envio
                    });
                    crearAlerta("container-alert", "Listo", "Usuario bedel agregado correctamente", "success");
                    $(elementos).val("");
                }


            } else {
            }
        };
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send();
    }
}


function inventarioLibros(tipo){
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'ControladorLibro?envio=consulta;,;Todos;,;'+tipo, true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            var jsonAjax = JSON.parse(xhr.responseText);
            if(tipo === '1'){
                librosAdmin(jsonAjax, "tablaCompletaVedeles");
            } else {
                librosSocio(jsonAjax, "tablaCompletaAlumnos");
            }
        } else {
        }
    };
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function librosSocio(Json, tabla){
    var table = document.getElementById(tabla);
    for(var i=0; i < Json.length; i++){
        var row = table.insertRow(i);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);
        cell1.innerHTML = Json[i].libro;
        cell2.innerHTML = Json[i].autor;
        cell3.innerHTML = Json[i].tema;
        cell4.innerHTML = Json[i].cantidad;
    }
}

function librosAdmin(Json, tabla){
    var table = document.getElementById(tabla);
    $(table).empty();
    console.log(Json);
    for(var i=0; i < Json.length; i++){
        var row = table.insertRow(i);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);
        var cell5 = row.insertCell(4);
        cell1.innerHTML = Json[i].titulo;
        cell2.innerHTML = Json[i].autor;
        cell3.innerHTML = Json[i].tema;
        cell4.innerHTML = Json[i].cantidad;
        cell5.innerHTML = "<button class='btn btn-danger' data-toggle='modal' data-target='#Eliminar' "+
            "onclick='llenarSelect("+Json[i].idlibro+")'"+    
            ">Eliminar</button>";
    }
}

function llenarSelect(id){
    var select = document.getElementById("codigos");
    $(select).empty();
    var envio = "consulta;,;"+id;
    $.post("ControladorCodLibro", {
        envio : envio
    }).done(function (data){
        for (var i = 0; i < data.length; i++) {
            var option = document.createElement("OPTION");
            option.setAttribute("value", data[i].codlibro);
            option.text = data[i].codlibro;
            select.add(option);
        }
    });
}

function eliminarLibro(){
    var select = document.getElementById("codigos");
    var envio = "consulta;,;eliminar;,;"+select.value;
    $.post("ControladorCodLibro", {
        envio : envio
    }).done(function (data){
        if (data.error === undefined) {
            crearAlerta("container-alert", "Hecho", "Eliminado correctamente", "success");
            inventarioLibros("1");
        } else {
            error(data.error);
        }
    });
}