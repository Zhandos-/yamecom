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

jQuery.validator.addMethod("notEqual", function(value, element, param) {
    return this.optional(element) || value != $(param).val();
}, "Новый пароль совпадает с текущей");


$(document).ready(function() {
    $('#form').validate({
        rules: {
            email: {
                required: true,
                email: true,
                remote: {
                    url: "checkemail",
                    type: "post"

                }
            },
            'phones[0].number': {
                phone: true,
                required: true
            }
        },
        messages: {
            email: {
                required: "Обязательное поле",
                email: jQuery.format("Неправильный адрес почты"),
                remote: "Данная почта зарегестрирована, введите другую"
            }
        }
        ,
        submitHandler: function(form) { // for demo
            $('btn').button('loading');
            $.ajax({
                type: "POST",
                url: "changeProfile",
                data: $("#form").serialize(),
//                contentType: "application/json;charset=UTF-8",
                dataType: "json",
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
    $('#form2').validate({
        rules: {
            password: {
                minlength: 6,
                required: true,
                notEqual: "#oldpassword"
            },
            oldpassword: {
                minlength: 6,
                required: true,
                remote: {
                    url: "isPasswordRight",
                    type: "post"

                }
            },
            repassword: {
                minlength: 6,
                equalTo: "#password",
                required: true
            }
        },
        messages: {
            password: {
                required: "Обязательное поле",
                minlength: jQuery.format("Минимальная длина {0}")
            },
            oldpassword: {
                required: "Обязательное поле",
                minlength: jQuery.format("Минимальная длина {0}"),
                remote: "Пароль не верный"


            },
            repassword: {
                required: "Обязательное поле",
                minlength: jQuery.format("Минимальная длина {0}"),
                equalTo: "Пароли не совпадают"

            }
        }
        ,
        submitHandler: function(form) { // for demo
            $('btn2').button('loading');
            $.ajax({
                type: "POST",
                url: "changePasssword",
                data: {password: $("#password").val()},
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
//        ,
//        highlight: function(label) {
////            $(label).closest('.form-group').removeClass('has-success').addClass('has-error');
//        }
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
        if (!hasMessage) {
            $("#tab-content").before("<div id=\"message\" class=\"alert alert-success\">Данные успешно сохранены.</div>");
            $("#password, #repassword , #oldpassword").val('');
            $("#message").fadeOut(5000);
            hasMessage = true;
        }
        else {
            $("#message").show();
            $("#message").fadeOut(5000);
        }
    }
    else
    {
        if (!hasError) {
            $('#btn').button('reset');
            $("#profileResult").before("<div id=\"error\" class=\"alert alert-danger\">Произошла ошибка при сохранении.</div>");
            $("#error").fadeOut(5000);
            hasError = true;
        } else
        {
            $("#error").show();
            $("#error").fadeOut(5000);
        }
    }
}
var hasError = false;
var hasMessage = false;