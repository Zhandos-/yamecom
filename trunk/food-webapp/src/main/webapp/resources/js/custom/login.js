jQuery.validator.addMethod('phone', function(phone_number, element) {
    return this.optional(element) || phone_number.length > 9 &&
            phone_number.match(/^((8|\+7).?\(\d{3}\).?\d{3}.?\d{2}.?\d{2})$/);
}, 'Не корректный номер'
        );
jQuery.validator.addMethod('code', function(phone_number, element) {
    return this.optional(element) || phone_number.length > 4 &&
            phone_number.match(/^(\d{2} \d{4})$/);
}, 'неправильный номер'
        );
$(document).ready(function() {
    $('#form').validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            password: {
                minlength: 6,
                required: true
            }
        },
        messages: {
            email: {
                required: "Обязательное поле",
                email: jQuery.format("Неправильный адрес почты")
            },
            password: {
                required: "Обязательное поле",
                minlength: jQuery.format("Минимальная длина {0}")
            }
        }
        ,
        submitHandler: function(form) { // for demo
            $('btn').button('loading');
            $.ajax({
                type: "POST",
                url: "login",
                data: {email: $("#email").val(), password: $("#password").val()},
//                contentType: "application/json;charset=UTF-8",
//                dataType: "json",
                success: OnSuccess,
                failure: function(response) {
                    alert(response.d);
                },
                error: function(response) {
                    alert(response.d);
                }
            });
            return false;
        }
        ,
        highlight: function(label) {
//            $(label).closest('.form-group').removeClass('has-success').addClass('has-error');
        }
//        ,
//        success: function(label) {
//            label
//
//                    .closest('.form-group').addClass('has-success');
//
//        }
    });

});

function OnSuccess(response) {

    if (response)
    {
        $("#btn").button('loading');
        $("#form").fadeOut();
        window.location.href = './';
    }
    else
    {
        if (!hasError) {
            $('#btn').button('reset');
            $("#result").before("<div class=\"alert alert-danger\">Неправильный логин или пароль</div>");
            hasError = true;
        }
    }
}
var hasError = false;