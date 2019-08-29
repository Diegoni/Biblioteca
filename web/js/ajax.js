$(document).ready(function (){
    var d= new Date();
    var dia = d.getDate(); 
    if(dia < 10){
        dia = "0"+dia;
    }
    var mes =  (d.getMonth() + 1);
    if(mes < 10){
        mes = "0"+mes;
    }
    var anio = d.getFullYear(); 
    var fechatotal = anio + "-"+ mes +"-" + dia;
    $("#fecha").val(fechatotal);
    console.log(fechatotal);
});


var historial;
var filtroElegido;

function ajax(direccion, parametros, elementoPadre, flag) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', direccion + parametros);
    xhr.onload = function () {
        if (xhr.status === 200) {
            var jsonAjax = JSON.parse(xhr.responseText);
            if (flag === 1) {
                cargaDataList(elementoPadre, jsonAjax);
            } else if (flag === 2) {
                cargarOption(elementoPadre, jsonAjax);
            } else if (flag === 3) {
                historial = jsonAjax;
                cargarHistorial(elementoPadre, historial);
            } else if (flag === 4) {
                cargarABM(elementoPadre, jsonAjax);
            } else if (flag === 5){
                CargarUserTable(elementoPadre, jsonAjax);
            }
        } else {
        }
    };
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function CargarUserTable(table, Json){
    if(table === null){
        
    } else {
        $(table).empty();
        console.log(Json);
        for(var i=0; i < Json.length; i++){
            var row = table.insertRow(i);
            row.setAttribute("id", Json[i].id);
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);
            var cell5 = row.insertCell(4);
            var cell6 = row.insertCell(5);
            var cell7 = row.insertCell(6);
            cell1.innerHTML = Json[i].nombre;
            cell2.innerHTML = Json[i].ingreso;
            cell3.innerHTML = Json[i].dni;
            cell4.innerHTML = Json[i].usuario;
            cell5.innerHTML = Json[i].pass;
            cell6.innerHTML = "<button class='btn btn-danger' data-toggle='modal' "+
                    "data-target='#Eliminar' onclick='almacenarID("+Json[i].id+")' >Eliminar</button>";
            cell7.innerHTML = "<button class='btn btn-warning' data-toggle='modal' "+
                    "data-target='#Editar' onclick=\"preBusqueda("+Json[i].id+", 'ControladorUsuario'), \n\
                    almacenarID("+Json[i].id+")\">Editar</button>";
        }
    }
}

function cargarABM(table, Json){
    var controlador;
    console.log(Json);
    if(table.getAttribute("id") === "ABMcarreras"){
        controlador = 'ControladorCarrera';
    } else {
        controlador = 'ControladorAutor';
    }
    console.log(controlador);
    for (var i = 0; i < Json.length; i++) {
        var row = table.insertRow(i);
        row.setAttribute("id", Json[i].id);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        cell1.setAttribute("id", "modificada");
        cell1.innerHTML = Json[i].etiqueta;
        
        cell2.innerHTML = "<button class='btn btn-danger' data-toggle='modal' "+
                "data-target='#Eliminar' onclick='almacenarID("+Json[i].id+")'>Eliminar</button>";
        cell3.innerHTML = "<button class='btn btn-warning' data-toggle='modal' "+
                "data-target='#Editar' onclick=\"buscar("+Json[i].id+", '"+controlador+"'), \n\
                almacenarID("+Json[i].id+")\">Editar</button>";
    }
}

function cargaDataList(elementoPadre, Json) {
    for (var i = 0; i < Json.length; i++) {
        var nuevoOption = document.createElement("OPTION");
        nuevoOption.setAttribute("value", Json[i].etiqueta);
        nuevoOption.setAttribute("data-id", Json[i].id);
        nuevoOption.setAttribute("name", "optionLibro");
        elementoPadre.appendChild(nuevoOption);
    }
}

function cargarOption(select, Json) {
    for (var i = 0; i < Json.length; i++) {
        var option = document.createElement("OPTION");
        option.setAttribute("value", Json[i].id);
        option.text = Json[i].etiqueta;
        select.add(option);
    }
}

function cargarHistorial(table, Json) {
    for (var i = 0; i < Json.length; i++) {
        var row = table.insertRow(i);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);
        var cell5 = row.insertCell(4);
        var cell6 = row.insertCell(5);
        var cell7 = row.insertCell(6);
        var cell8 = row.insertCell(7);
        cell1.innerHTML = Json[i].numPrestamo;
        cell2.innerHTML = Json[i].libro;
        cell3.innerHTML = Json[i].socio;
        cell4.innerHTML = Json[i].carrera;
        cell5.innerHTML = Json[i].fecha;
        cell6.innerHTML = Json[i].devolucion;
        cell7.innerHTML = Json[i].estado;
        if (Json[i].estado !== "Devuelto.") {
            cell8.innerHTML = "<button class=\"btn btn-danger\" onclick=\"devolver(this)\" data-id=" + Json[i].numPrestamo + ">Devolver</button>";
        }
    }
}

function devolver(button) {
    var envio = "actualizar;,;" + $(button).data("id");
    $.post('ControladorPrestamo', {
        "envio": envio
    }).done(function (data) {
        if (data.error === undefined) {
            exito(data.exito);
            location.reload();
        } else {
            error(data.error);
        }
    });
}

function llenarABMUsuarios(idtable, controlador){
    var ABM = document.getElementById(idtable);
    ajax(controlador, "?envio=consulta;,;alumno", ABM, 5);
}

function llenarABM(idtable, controlador) {
    var ABM = document.getElementById(idtable);
    ajax(controlador, "?envio=autocompletar", ABM, 4);
}

function llenarDatalist(datalist, controlador) {
    var DATALIST = document.getElementById(datalist);
    $(DATALIST).empty();
    ajax(controlador, "?envio=autocompletar", DATALIST, 1);
}

function llenarSelect(idselect, controlador) {
    var SELECT = document.getElementById(idselect);
    $(SELECT).empty();
    ajax(controlador, "?envio=autocompletar", SELECT, 2);
}

function llenarHistorial(idtable, controlador) {
    var TABLE = document.getElementById(idtable);
    ajax(controlador, "?envio=consulta", TABLE, 3);
}

function cargar(Name, controlador) {
    var vacio = false;
    var atributos = [];

    atributos = document.getElementsByName(Name);
    for (var x = 0; x < atributos.length; x++) {
        if(atributos[x].value === ""){
            vacio = true;
        }
    }
    if(vacio === true){
        crearAlerta("container-alert", "Error", "Complete todos los campos", "danger");
    } else {
        var envio = "insertar;,;";
        for (var x = 0; x < atributos.length; x++) {
            envio += atributos[x].value + ";,;";
        }
        console.log(envio);
        $.post(controlador, {
            envio: envio
        }).done(function (data) {
            if (data.error === undefined) {        
                if(controlador === "ControladorSocio"){
                    $.post(controlador, {
                        envio : "consulta;,;"+atributos[3].value
                    }).done(function (data){
                        console.log(data);
                        crearAlerta("container-alert", "Hecho", "Usuario: "+data[0].usuario + " "+escape("Contraseña")+": "+data[0].pass , "success");
                        for (var x = 1; x < atributos.length; x++) {
                            atributos[x].value = "";
                        }
                    });
                } else if(controlador === "ControladorTema"){
                    $("#campoTemas").empty();
                    llenarSelect('campoTemas','ControladorTema'); 
                } else {
                    crearAlerta("container-alert", "Hecho", "Agregado correctamente", "success");
                }
            } else {
                error(data.error);
            }
        });

    }   
}

function reemplazo(campoId, datalistId) {
    var nombreVisible = document.getElementById(campoId).value;
    console.log(nombreVisible);
    if(document.querySelector("#" + datalistId + " option[value='" + nombreVisible + "']") === null){
        return null;
    } else {
        return document.querySelector("#" + datalistId + " option[value='" + nombreVisible + "']").dataset.id;
    }
}
function prestarLibro() {
    console.log("Llego");
    var envio = "insertar;,;";
    envio += $('#fecha').val() + ";,;";
    envio += $('#plazo').val() + ";,;";
    envio += reemplazo("campoSocio", "datalistSocio") + ";,;";
    envio += reemplazo("titulo", "datalistLibro");
    console.log(envio);
    $.post('ControladorPrestamo', {
        envio: envio
    }).done(function (data) {
        if (data.error === undefined) {
            $('#plazo').val("");
            $('#campoSocio').val("");
            $('#titulo').val("");
            crearAlerta("container-alert", "Listo", escape("El prestamo se realizó correctamente"), "success");
        } else {
            crearAlerta("container-alert", "Error", "Algo salio mal", "danger");
        }
    });
    

}



//Cargar libro
$(document).ready(function () {
    
    $('#Cargar').click(function () {
        var vacio = false;
        var atributosLibro = [];
        var autores = [];
        autores = document.getElementsByClassName("item");
        atributosLibro = document.getElementsByName("atributosLibro");
        for (var x = 0; x < atributosLibro.length; x++) {
            if(atributosLibro[x].value === ""){
                vacio = true;
            }
        }
        if(autores.length <= 0){
            vacio = true;
        }
        if(vacio === true){
            crearAlerta("container-alert", "Error", "Complete todos los campos", "danger");
        } else {
            var envio = "insertar;,;";
            for (var x = 0; x < atributosLibro.length; x++) {
                envio += atributosLibro[x].value + ";,;";
            }
            console.log(envio);
            $.post('ControladorLibro', {
                envio: envio
            }).done(function (data) {
                if (data.error === undefined) {
                    var envio = "insertar;,;"+atributosLibro[0].value+";,;";
                    for (var x = 0;x < autores.length; x++){
                        envio += autores[x].getAttribute("id") + ";,;";
                    }
                    console.log(envio.replace(/Aut/g, ""));
                    $.post('ControladorLibroHasAutor', {
                        envio: envio.replace(/Aut/g, "")
                    });
                    $(atributosLibro[0]).val("");
                    $(atributosLibro[1]).val("");
                    $("#padre").html("");
                    crearAlerta("container-alert", "Listo", escape("El libro se agregó correctamente"), "success");
                } else {
                    error(data.error);
                }
            });
        }
    });
});


function disponibilidad(cargar) {
    
    if(cargar === 0){
        if($("#titulo").val() === ""){
            console.log("Entrando al metodo");
            crearAlerta("container-alert", escape("Error en verificación"), "No ha ingresado un libro", "danger");
        } else {
            confirmarDisponibilidad(cargar);
        }
    } else if(cargar === 1){
        if($('#fecha').val() === "" || $('#plazo').val() === "" || $('#campoSocio').val() === "" || $('#titulo').val() === ""){
            crearAlerta("container-alert", "Error en prestamo", "Complete todos los campos para poder continuar", "danger");
        } else {
            confirmarDisponibilidad(cargar);
        }
    }
}

function confirmarDisponibilidad(cargar){
    var url = reemplazo("titulo", "datalistLibro");
    if(url === null){
        crearAlerta("container-alert", "El libro no existe", "NO edite los datos seleccionados o asegurese de haberlo agregado", "danger");
    } else {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', "ControladorDisponibilidad?envio=consulta;,;" + url);
        xhr.onload = function () {
            if (xhr.status === 200) {
                var jsonAjax = JSON.parse(xhr.responseText);
                if (jsonAjax.Disponibilidad <= 0) {
                    $("#confirm").attr("src", "img/Cross.png");
                    if (cargar == 1) {
                        error("Libro sin stock");
                    }
                } else {
                    $("#confirm").attr("src", "img/check.png");
                    if (cargar === 1) {
                        verificarDeudas();
                    }
                }
            } else {
                error("Error de conexión");
            }
        };
        xhr.send('idLibro=' + $('#titulo').val());
    }

    
}

function verificarDeudas() {
    var socio = reemplazo("campoSocio", "datalistSocio");
    if(socio === null){
        crearAlerta("container-alert", "El socio no existe", "NO edite los datos seleccionados o asegurese de haberlo agregado", "danger");
    } else {
        var envio = "consulta;,;prestamo;,;"+socio;
        $.post('ControladorPrestamo',{
        envio : envio
        }).done(function (data){
            if(data.deuda === "no"){
                prestarLibro();
            } else {
                crearAlerta("container-alert", "Error en prestamo", "El socio debe: "+data.titulo+" desde "+data.fecha, "danger");
            }
        });
    }
    
}

function defecto() {
    $("#confirm").attr("src", "img/lupa.png");
}

// ***************************************************

/**
 * Resumen: Determina y muestra un mensaje ante errores en las consultas
 * 
 * Descripción: La función se encarga de mostrar un mensaje de error en un área 
 * previamente preparada de la intezfaz y de ocultarlo luego de haber 
 * pasado unos 5 segundos. Obtiene mediante una cadena de 
 * carácteres el mensaje a mostrar. El evento que la dispara es al producirse 
 * un error en el backend cuando este emite la respuesta.
 * 
 * @param {String} textoDeError Determina el texto de error a mostrar en 
 * la interfaz 
 */

function error(textoDeError) {
    $("#confirmacion").text(textoDeError);
    $("#confirmacion").css("backgroundColor", "red");
    mostrarConfirmacion();

}
function exito(textoDeExito) {
    $("#confirmacion").text(textoDeExito);
    $("#confirmacion").css("backgroundColor", "green");
    mostrarConfirmacion();
}
function mostrarConfirmacion() {
    $("#confirmacion").fadeIn();
    var counter = 0;
    var interval = setInterval(function () {
        counter++;
        // Display 'counter' wherever you want to display it.
        if (counter === 3) {
            // Display a login box
            $("#confirmacion").fadeOut();
            clearInterval(interval);
        }
    }, 1000);

}


/**
 * Función que formatea la interfaz y vacia los campos ante los cambios de 
 * tipo de filtro para amenizar la interacción
 */
function cambiarFiltro() {
    var filtro = $("#filtro");
    var tipoFiltro = $("#campoEstado");
    var placeholder;
    if (tipoFiltro.val() === "fecha") {
        placeholder = "Filtrar (FORMATO: AAAA-MM-DD)";
    } else {
        placeholder = "Filtrar";
    }
    filtro.attr("placeholder", placeholder);
    var tabla = document.getElementById("historial");
    filtro.val("");
    filtrar();
}
/**
 * Resumen: Función que se utilizar para obtener valores del filtro.
 * 
 * Descripción: Función que se dispara en el momento que se escribe en el input de
 * filtro (ID: 'filtro'), toma los valores y dispara la función 
 * salidaConFiltro() para permitir el filtrado del historial.
 */
function filtrar() {
    var filtro = $("#filtro");
    var tipoFiltro = $("#campoEstado");
    $("#historial tr").remove();
    salidaConFiltro(tipoFiltro, filtro.val());
}
/**
 * Resumen: Obtiene los filtros y formatea la salida.
 * 
 * Descripción: Función que en primer lugar determina el tipo de filtro y 
 * luego comprueba los valores con el Objeto JSON obtenido previamente
 * y si alguno de los valores (Según el filtro) coincide lo agrega a la 
 * tabla de la lista.
 * 
 * @param {DOM Element (Select)} tipoFiltro Determina el tipo de filtro a utilizar.
 * @param {String} filtroDato Determina el valor actual del input con el que se va a filtrar.
 */
function salidaConFiltro(tipoFiltro, filtroDato) {
    var Json = historial;
    var tabla = document.getElementById("historial");
    for (var i = 0; i < Json.length; i++) {
        var row = tabla.insertRow(i);
        switch (tipoFiltro.val()) {
            case "libro":
                if (!Json[i].libro.toString().toLowerCase().includes(filtroDato.toLowerCase()))
                    continue;
                break;
            case "socio":
                if (!Json[i].socio.toString().toLowerCase().includes(filtroDato.toLowerCase()))
                    continue;
                break;
            case "fecha":
                if (!Json[i].fecha.toString().toLowerCase().includes(filtroDato.toLowerCase()))
                    continue;
                break;
            case "estado":
                if (!Json[i].estado.toString().toLowerCase().includes(filtroDato.toLowerCase()))
                    continue;
                break;
            case "carrera":
                if (!Json[i].carrera.toString().toLowerCase().includes(filtroDato.toLowerCase()))
                    continue;
                break;
        }
        var celdaNumeroDePrestamo = row.insertCell(0);
        var celdaLibro = row.insertCell(1);
        var celdaSocio = row.insertCell(2);
        var celdaCarrera = row.insertCell(3);
        var celdaFecha = row.insertCell(4);
        var celdaEstado = row.insertCell(5);
        var celdaDevolucion = row.insertCell(6);
        celdaNumeroDePrestamo.innerHTML = Json[i].numPrestamo;
        celdaLibro.innerHTML = Json[i].libro;
        celdaSocio.innerHTML = Json[i].socio;
        celdaFecha.innerHTML = Json[i].fecha;
        celdaEstado.innerHTML = Json[i].estado;
        celdaCarrera.innerHTML = Json[i].carrera;
        if (Json[i].estado !== "Devuelto.") {
            celdaDevolucion.innerHTML = "<button class=\"btn btn-danger\" onclick=\"devolver(this)\" data-id=" + Json[i].numPrestamo + ">Devolver</button>";
        }
        console.log(Json[i].fecha);
    }
}


// #####################   MODIFICAR LIBROS ##############################


$(document).ready(function () {
    $('#Busqueda').click(function () {
        var atributosLibro = [];
        var envio = "consulta;,;Buscar;,;"+reemplazo("busca","datalistLibro");
        console.log(envio);
        $.post('ControladorLibro', {
            envio: envio
        }).done(function (data) {
            $('#id').val(data.id);
            $('#titulo').val(data.titulo);
            var campos = document.getElementsByName("atributosLibro");
            $('#campoTemas [value="'+data.tema+'"]').prop('selected', true);
            $('#Busqueda').hide();
            $('#Cancelar').show();
            $(campos).attr('disabled', false);
            $('#busca').attr('disabled', true);
            $('#modificar').attr('disabled', false);
            $('#campoAutor').attr('disabled', false);
            var envio = "consulta;,;"+reemplazo("busca","datalistLibro");
                console.log(envio);
            $.post('ControladorLibroHasAutor', {
                envio: envio
            }).done(function (data) {
                recuperarElemento(data.autores);
            });
        });
    });
});

function recuperarElemento(data){
    console.log(data);
    var elemento = data.split("##");
    
    console.log(elemento);
    var padre = document.getElementById("padre");
    for(var x=0; x<elemento.length; x++){
        var value = elemento[x];
        var dataid = document.querySelector("#datalistAutores option[value='" + elemento[x] + "']").dataset.id;
        var autor = document.createElement("div");
        autor.setAttribute("id", dataid);
        autor.setAttribute("class", "item");
        var contenido = "<span class='contenido'> "+value+" <strong class='delete' onclick=\"cancelar('"+dataid+"')\">&#10005;</strong></span>";
        autor.innerHTML = contenido;
        padre.appendChild(autor);
    }    
}

$(document).ready(function () {
    $('#modificar').click(function () {
        if($('#titulo').val() === "" || $('#campoTemas').val() === "" || document.getElementsByClassName("item").length === 0){
            crearAlerta("container-alert", "Error en modificar", "Complete todos los campos", "danger");
        } else {
        var envio = "consulta;,;Update;,;"+$('#titulo').val()+";,;"+$('#campoTemas').val()+";,;"+$('#id').val();
        console.log(envio);
        $.post('ControladorLibro', {
            envio: envio
        }).done(function (data) {
            
            var campos = document.getElementsByName("atributosLibro");
            $(campos).val("").attr('disabled', true);
            $('#busca').attr('disabled', false);
            $('#modificar').attr('disabled', true);
            $("#campoAutor").attr('disabled', true);
            $('#Cancelar').hide();
            $('#Busqueda').show();
            
            var autores = [];
            autores = document.getElementsByClassName("item");
            var envio = "actualizar;,;"+$('#id').val()+";,;";
            for (var x = 0;x < autores.length; x++){
                envio += autores[x].getAttribute("id") + ";,;";
            }
            var padre = document.getElementById("padre").innerHTML = "";
            console.log(envio.replace(/Aut/g, ""));
            $.post('ControladorLibroHasAutor', {
                envio: envio.replace(/Aut/g, "")
            });
            crearAlerta("container-alert", "Hecho", "Datos de libro modificados correctamente", "success");
        });
        
        }
    });
});


/*##########ABM##########*/
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

function eliminar(controlador){
    var envio = "eliminar;,;"+IDENTIFICADOR;
    $.post(controlador, {
            envio: envio
        }).done(function (data) {
            if (data.error === undefined) {
                exito(data.exito);
                
                if(controlador === "ControladorCarrera"){
                    $("#ABMcarreras").empty();
                    llenarABM("ABMcarreras", controlador);
                    crearAlerta("container-alert", "Hecho", "Carrera eliminada correctamente", "success");
                }
                else if(controlador === "ControladorAutor"){
                    $("#ABMautores").empty();
                    llenarABM("ABMautores", controlador);
                    crearAlerta("container-alert", "Hecho", "Autor eliminado correctamente", "success");
                } 
            } else {
                error(data.error);
                if(controlador === "ControladorCarrera"){
                    crearAlerta("container-alert", "No se puede eliminar", "Carrera vinculada a uno o más socios", "danger");
                }
                else if(controlador === "ControladorAutor"){
                    crearAlerta("container-alert", "No se puede eliminar", "Autor vinculado a uno o más libros", "danger");
                }
            }
        });
    
}

function editar(controlador, name){
    var envio = "consulta;,;Update;,;";
    var elementos = document.getElementsByName(name);
    for(var x=0;x<elementos.length;x++){
        envio += elementos[x].value+";,;";
    }
    envio += IDENTIFICADOR;
    console.log(envio + controlador);
    $.get(controlador, {
            envio: envio
        }).done(function (data) {
            console.log(data);
            if (data.error === undefined) {
                exito(data.exito);
                console.log(data);
                if(controlador === "ControladorCarrera"){
                    $("#ABMcarreras").empty();
                    llenarABM("ABMcarreras", controlador);
                    crearAlerta("container-alert", "Hecho", "Carrera editada correctamente", "success");
                }
                else if(controlador === "ControladorAutor"){
                    console.log("entra");
                    $("#ABMautores").empty();
                    llenarABM("ABMautores", controlador);
                    crearAlerta("container-alert", "Hecho", "Autor editado correctamente", "success");
                } 
            } else {
                error(data.error);
            }
        });
}

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

function agregarCarrera(){
    var vacio = false;
    var values = document.getElementsByName("añadirCarrera");
    for(var x=0; x<values.length; x++){
        if(values[x].value === ""){
            vacio = true;
        }
    }
    if(vacio === true){
        crearAlerta("container-alert", "Error", "Complete los campos", "danger");
    } else {
        var envio = "insertar;,;";
        for(var x=0; x<values.length; x++){
            envio += values[x].value+";,;";
        }
        console.log(envio);
        $.post('ControladorCarrera', {
            envio : envio
        }).done(function (data) {
            if (data.error === undefined) {
                exito(data.exito);
                $("#ABMcarreras").empty();
                llenarABM("ABMcarreras", "ControladorCarrera");
                crearAlerta("container-alert", "Hecho", "Carrera añadida correctamente", "success");
            } else {
                error(data.error);
            }
        });
    }
}



function preBusqueda(id, controlador, tipo) {
    if(tipo === "1"){
        buscar("alumno;,;"+id, controlador);
    } else {
        buscar("vedel;,;"+id, controlador);
    }
    
}
var CodLib;
function almacenarCodLib(cod){
    CodLib = cod;
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
                }).done(function (data) {
                    if (data.error === undefined) {
                        crearAlerta("container-alert", "Hecho", "Usuario eliminado correctamente", "success");
                        llenarABMUsuarios("ABMusuarios", "ControladorUsuario");
                    } else {
                        error(data.error);
                    }
                });

            }

        } else {
        }
    };
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function editarUsuario(controlador, name){
    var vacio = false;
    var envio = "consulta;,;Update;,;";
    var elementos = document.getElementsByName(name);
    for(var x=0;x<elementos.length;x++){
        if(elementos[x].value === ""){
            vacio = true;
        } 
    }
    if(vacio === true){
        crearAlerta("container-alert", "Error", "Complete todos los campos", "danger");
    } else {
        for(var x=0;x<elementos.length;x++){
            envio += elementos[x].value+";,;";
        }
        envio += IDENTIFICADOR+";,;2;,;"+idSocio;
        console.log(envio);
        var xhr = new XMLHttpRequest();
        xhr.open('GET', controlador+'?envio='+envio, true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                if(xhr.responseText === "ok"){
                    crearAlerta("container-alert", "Hecho", "Usuario editado correctamente", "success");
                    llenarABMUsuarios("ABMusuarios", "ControladorUsuario");
                } else if(xhr.responseText === "existe"){
                    crearAlerta("container-alert", "Error", "El usuario ya existe, ingrese uno distinto", "danger");
                }

            } else {
            }
        };
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send(); 
    }

    
}
/*##########ABM##########*/



/*##########crearUsuario##########*/
/**
 * 
 * @param {type} Name
 * @returns {undefined}
 * @deprecated 
 */
function crearUsuario(Name){
    var atributos = [];
    var envio = "insertar;,;";
    atributos = document.getElementsByName(Name);
    for (var x = 0; x < atributos.length; x++) {
        envio += atributos[x].value + ";,;";
    }
    console.log(envio);
}

function crearAlerta(id, titulo, mensaje, tipo){
    console.log(mensaje);
    titulo = decodeURI(titulo);
    mensaje = decodeURI(mensaje);
    var html = `<div class="alert alert-${tipo} alert-dismissible" id="alerta-generada" style="display: none">
                    <a href="#" class="close" onclick="$('#alerta-generada').hide()" aria-label="close">&times;</a>
                    <strong>${titulo}</strong>
                    <br>${mensaje}
                </div>`;
    console.log(html);
    document.getElementById(id).innerHTML = html;
    $("#alerta-generada").fadeIn();
}

function BajaUsuariosAntiguos(){
    var envio = "consulta;,;baja";
    $.post("ControladorUsuario", {
        envio : envio
    });
    llenarABMUsuarios("ABMusuarios", "ControladorUsuario");
}

