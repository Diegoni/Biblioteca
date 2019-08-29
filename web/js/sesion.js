$(document).ready(function (){
   $("#login").click(function (){
        comprobar();
   }); 
   $(document).keypress(function(e) { 
       var code = (e.keyCode ? e.keyCode : e.which); 
       console.log(code);
       if(code === 13){ 
           comprobar(); 
           
           return false; 
       } 
   });
});

function comprobar(){
    var user = $("#user").val();
    var pass = $("#pass").val();

    if(user === ""){
         $("#text-user").attr("class", "text-danger");
         $("#user").attr("class", "form-control is-invalid");
         $("#user").attr("placeholder", "Ingrese un usuario");
     }
     if(pass === ""){
         $("#text-pass").attr("class", "text-danger");
         $("#pass").attr("class", "form-control is-invalid");
         $("#pass").attr("placeholder", "Ingrese una contraseña");
     }
     if(user !== "" && pass !== ""){
         envio(user, pass);
     }
}

function envio(user, pass){
    var envio = "verificar;,;"+user+";,;"+pass;
    var xhr = new XMLHttpRequest();
    xhr.open('GET', 'ControladorUsuario?envio='+envio, true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            respuesta(xhr.responseText);

        } else {
        }
    };
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send();
}

function respuesta(data){
    if(data === "El usuario ingresado no existe"){
        $("#user").val("");
        $("#text-user").attr("class", "text-danger");
        $("#user").attr("class", "form-control is-invalid");
        $("#user").attr("placeholder", "El usuario ingresado no existe");
    }
    if(data === "La contraseña es incorrecta"){
        $("#pass").val("");
        $("#text-pass").attr("class", "text-danger");
        $("#pass").attr("class", "form-control is-invalid");
        $("#pass").attr("placeholder", "La contraseña es incorrecta");
    }
    if(data === "Ok"){
        $(location).attr('href', 'index.jsp');
    }
}




