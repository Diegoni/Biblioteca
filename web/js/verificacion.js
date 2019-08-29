$(document).ready(function () {
    $(".campo-formulario").keypress(function () {
        if ($(this).val().length > 120) {
            $(this).val($(this).val().substr(0, 120));
            return false;
        }
    });

    $("#plazo").keyup(function () {
        $(this).val(($(this).val() + '').replace(/[^0-9]/g, ''));
    });
    $("#dni").keyup(function () {
        $(this).val(($(this).val() + '').replace(/[^0-9]/g, ''));
        if ($(this).val().length > 8) {
            $(this).val($(this).val().substr(0, 8));
            return false;
        }
    });
});