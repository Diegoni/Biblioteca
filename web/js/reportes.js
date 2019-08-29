var flag = 0;
$(document).ready(
function (){
    $("#deudores").click(function (){
        $("#btn").show();
        $("#campoSocio").show();
        $("#carrera").show();
        $("#mes").hide();
        $("#libro-cat").hide();
        $("#campoSocioContainer").show();
        $("#carreraContainer").show();
        $("#mesContainer").hide();
        $("#libro-catContainer").hide();
        flag = 1;
    });
    $("#prestamos").click(function (){
        $("#btn").show();
        $("#campoSocio").show();
        $("#carrera").show();
        $("#mes").show();
        $("#libro-cat").hide();
        $("#campoSocioContainer").show();
        $("#carreraContainer").show();
        $("#mesContainer").show();
        $("#libro-catContainer").hide();
        flag = 2;
    });
    $("#libroscarrera").click(function (){
        $("#btn").show();
        $("#campoSocio").hide();
        $("#carrera").show();
        $("#mes").hide();
        $("#libro-cat").hide();
        $("#campoSocioContainer").hide();
        $("#carreraContainer").show();
        $("#mesContainer").hide();
        $("#libro-catContainer").hide();
        flag = 3;
    });
    $("#inventario").click(function (){
        $("#btn").show();
        $("#campoSocio").hide();
        $("#carrera").hide();
        $("#mes").hide();
        $("#libro-cat").show();
        $("#campoSocioContainer").hide();
        $("#carreraContainer").hide();
        $("#mesContainer").hide();
        $("#libro-catContainer").show();
        flag = 4;
    }); 
    
    $("#generar").click(function (){
        var condiciones = [
            verificarInput("#campoSocio"),verificarSelect("#carrera"),
            verificarInput("#mes"),verificarSelect("#libro-cat")
        ];
        var envio = "";
        for(var x=0; x<condiciones.length; x++){
            envio += query(x, condiciones[x]);
        }
        console.log(flag +" "+ envio);
        
        //$.post("JasperReport",
        //{opcion : flag, query : envio});
        
        window.open("JasperReport?opcion="+flag+"&"+"query="+envio, "_blank");
    });
}    
);

function verificarInput(id){
    if($(id).css("display")==="block"){
        return $(id).val();
    } else {
        return null;
    }
}

function verificarSelect(id){
    if($(id).css("display")==="block"){
        return $(id+' option:selected').text();
    } else {
        return null;
    }
}

function query(option, value){
    switch (option){
        case 0 :
            if(value !== null && value !== ""){
                if(value !== "Todas"){
                    return " `Socio` = '"+value.split(" -", 1)+"' AND";
                }
                return "";
            } else {
                return "";  
            }
        case 1 :
            if(value !== null){
                if(value !== "Todas"){
                    return " `Carrera` = '"+value+"' AND";
                }
                return "";
            } else {
                return "";  
            }
        case 2 :
            if(value !== null){
                if(value !== "0"){
                    return " MONTH(`Fecha de prestamo`) = "+value+" AND";
                }
                return "";
            } else {
                return "";  
            }
        case 3 :
            if(value !== null){
                if(value === "Todos"){
                    return "";
                }
                else if(value === "En biblioteca"){
                    
                }
                else if(value === "Prestados"){
                    
                }
            } else {
                return "";  
            }
    }
}



    
