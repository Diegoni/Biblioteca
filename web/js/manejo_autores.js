function reemplazo(campoId, datalistId) {
    var nombreVisible = document.getElementById(campoId).value;
    var valorIDReal = document.querySelector("#" + datalistId + " option[value='" + nombreVisible + "']").dataset.id;
    return valorIDReal;
}

function crearElemento(){
    var padre = document.getElementById("padre");
    var value = document.getElementById("campoAutor").value;
    var dataid = "Aut"+reemplazo("campoAutor", "datalistAutores");
    if(document.getElementById(dataid) === null){
        var autor = document.createElement("div");
        autor.setAttribute("id", dataid);
        autor.setAttribute("class", "item");
        var contenido = "<span class='contenido'> "+value+" <strong class='delete' onclick=\"cancelar('"+dataid+"')\">&#10005;</strong></span>";
        autor.innerHTML = contenido;
        padre.appendChild(autor);
        document.getElementById("campoAutor").value = "";
    } else {
        crearAlerta("container-alert", "Error", "Ya ha ingresado este autor", "danger");
    }  
}

function cancelar(id){
    var padre = document.getElementById("padre");
    var item = document.getElementById(id); 
    padre.removeChild(item);
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





                      