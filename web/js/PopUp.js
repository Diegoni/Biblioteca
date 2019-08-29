function Popup(span, modal){
    var mdl = document.getElementById(modal);
    var spn = document.getElementById(span);
    mdl.style.display = "block";
    spn.addEventListener("click", function () {
        mdl.style.display = "none";
    });
}

function cerrar(modal){
    console.log("llego");
    var mdl = document.getElementById(modal);
    mdl.style.display = "none";
}