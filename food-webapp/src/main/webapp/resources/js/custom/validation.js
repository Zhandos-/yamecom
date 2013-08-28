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
            name: {
                required: false
            },
            surname: {
                required: false
            },
            email: {
                required: true,
                email: true,
                remote: {
                    url: "checkemail",
                    type: "post"

                }
            },
            password: {
                minlength: 6,
                required: true
            },
            repassword: {
                minlength: 6,
                equalTo: "#password",
                required: true
            },
            phone: {
                phone: true,
                required: true
            }
        },
        messages: {
            name: "Обязательное поле",
            surname: "Обязательное поле",
            email: {
                required: "Обязательное поле",
                email: jQuery.format("Неправильный адрес почты"),
                remote: "Данная почта зарегестрирована, введите другую"
            },
            password: {
                required: "Обязательное поле",
                minlength: jQuery.format("Минимальная длина {0}")

            },
            repassword: {
                required: "Обязательное поле",
                minlength: jQuery.format("Минимальная длина {0}"),
                equalTo: "Пароли не совпадают"

            }
        }
        ,
        highlight: function(label) {
            $(label).closest('.form-control').removeClass('success').addClass('error');
        },
        success: function(label) {
            label
                    .text('OK!').addClass('valid')
                    .closest('.form-control').addClass('success');

        }
    });

});