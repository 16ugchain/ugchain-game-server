var register = new Vue({
    el: "#register-box",
    data: {
        titles: [
            {
                title: "1.注册",
                style: "z-index:4",
                active: true,
                href: "#tab-1"
            },
            {
                title: "2.绑定手机",
                style: "z-index:3",
                active: false,
                href: "#tab-2"
            },
            {
                title: "3.填写信息",
                style: "z-index:2",
                active: false,
                href: "#tab-3"
            },
            {
                title: "4.绑定银行卡",
                style: "z-index:1",
                active: false,
                href: "#tab-4"
            },
        ],
        userInfo: {
            // 注册
            user_name: "",
            password: "",
            // 绑定手机
            telephone: "",
            verify: "",
            // 填写信息
            registerName: "",
            papersType: "1",
            papersNum: "",
            papersImg: [],

            contactPhone: "",
            email: "",
            // 绑定银行卡
            cardUsername: "",
            cardNumber: "",
            bank: "",
            imageId: ""
        },
        tabIndex: 0,
        api: {
            registerApi: "/user/register",
            telephoneApi: "/user/bindMobile",
            bindCreditCard: "/user/bindCreditCard",
            findMobile: "/user/findMobile",
            findUserName: "/user/findUserName",
            sendVerification: "/user/sendVerification",
            upload: "/user/upload",
            authenticate: "/user/authenticate"
        }
    },
    methods: {
        tabFun: function () {
            this.tabIndex++;
            if (this.tabIndex > 3) {
                this.tabIndex = 3;
                // 跳转首页
                window.location = "/login.html";
            }
            $(".nav-tabs > li").removeClass('active').eq(register.tabIndex).addClass('active');
            $(".tab-content > div").removeClass('active').eq(register.tabIndex).addClass('active');
        }
    }
});
$.ajaxSetup({
    headers: {'HTTP_AUTHORIZATION': $.cookie("Authorization")}
});
// 发送验证码
$("#verifyBtn").on('click', function (event) {
    event.preventDefault();
    /* Act on the event */
    var that = $(this);
    var num = $(this).attr("value");
    // 手机号码格式不对
    if ($("#phone").parent().hasClass('has-error') || $("#phone").val().length < 11) {
        $("#modalAlert").click();
        $(".modal-body").html(" 请输入正确的手机号码");
    } else {
        $(this).attr("value", num + 1);
        console.log("发送");
        var timer = null;
        var time = 60;
        that.addClass('disabled  active');
        timer = setInterval(function () {
            time--;
            if (time <= 0) {
                clearInterval(timer);
                that.removeClass('disabled  active');
                that.text("重新发送");
            } else {
                that.text(time + "秒后重新发送");
            }
        }, 1000);
        $.post(register.api.sendVerification, {
            telephone: register.userInfo.telephone
        }, function (data) {
            if (data.meta.code === 200) {
                console.log("已发送");
            }
        })
    }
});
// 跳过按钮
$(".btn-skip").on('click', function (event) {
    event.preventDefault();
    /* Act on the event */
    // 清除数据
    console.log("清除数据");
    $("#tab-" + (register.tabIndex + 1) + " input").val();
    // 清除提示、图标
    console.log("清除提示、图标");
    $("#tab-" + (register.tabIndex + 1)).data('bootstrapValidator').resetForm();
    // 下一步
    console.log("下一步");
    register.tabFun();
});
$("#file-1").on("change", function () {
    var form1 = document.createElement("form");
    form1.id = "upload";
    form1.name = "upload";
    document.body.appendChild(form1);
    form1.appendChild(document.getElementById("file-1"));
    var input1 = document.createElement("input");
    var sub = document.createElement("input");
    sub.type = "submit";
    form1.appendChild(sub);
    input1.type = "hidden";
    input1.name = "type";
    input1.value = register.userInfo.papersType;
    form1.appendChild(input1);
    form1.action = register.api.upload;
    form1.method = "POST";
    form1.enctype = "multipart/form-data";
    form1.autocomplete="off";
    $("#upload").attr("onsubmit","return saveImg()");
    sub.click();
    document.body.removeChild(form1);
});
// 图片上传
function saveImg(){
    $("#upload").ajaxSubmit(function(message) {
        console.log(message);
    });
    return false;
}
// 表单验证
$(function () {
    // 注册
    $('#tab-1').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '用户名长度必须在6到18位之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '用户名只能包含大写、小写、数字和下划线'
                    },
                    threshold: 6,
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        url: register.api.findUserName,//验证地址
                        message: '用户已存在',//提示消息
                        delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'//请求方式
                    },
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '密码长度必须在6到18位之间'
                    },
                    identical: {//相同
                        field: 'password', //需要进行比较的input name值
                        message: '两次密码不一致'
                    },
                }
            },
            repassword: {
                validators: {
                    notEmpty: {
                        message: '密码不为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '密码长度必须在6到18位之间'
                    },
                    identical: {//相同
                        field: 'password', //需要进行比较的input name值
                        message: '两次密码不一致'
                    },
                }
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        // 注册事件
        console.log(register.userInfo.user_name);
        console.log(register.userInfo.password);
        $.post(register.api.registerApi, {
            user_name: register.userInfo.user_name,
            password: register.userInfo.password
        }, function (data) {
            console.log(data);
            if (data.meta.code === 200) {
                // 清除旧cookie,生成新cookie;
                $.cookie("Authorization", null);
                $.cookie("Authorization", data.data.token);
                console.log(data.meta.message);
                // 下一步操作
                register.tabFun();
            }
        })
    });
    // 绑定手机
    $('#tab-2').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            telephone: {
                message: '手机格式错误',
                validators: {
                    notEmpty: {
                        message: '手机号码不能为空'
                    },
                    stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入11位手机号码'
                    },
                    regexp: {
                        regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                        message: '请输入正确的手机号码'
                    },
                    threshold: 4,
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                        url: register.api.findMobile,//验证地址
                        message: '手机号码已存在',//提示消息
                        delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'//请求方式
                    }
                }
            },
            verify: {
                message: '验证码错误',
                validators: {
                    notEmpty: {
                        message: '验证码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 6,
                        message: '请输入6位验证码'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        if ($("#verifyBtn").attr("value") <= 0) {
            $("#modalAlert").click();
            $(".modal-body").html("请点击发送验证码按钮");
            $('#tab-2').bootstrapValidator('disableSubmitButtons', false);
            return;
        } else {
            console.log(register.userInfo.telephone);
            console.log($.cookie("Authorization"));
            $.post(register.api.telephoneApi, {
                telephone: register.userInfo.telephone,
                verification: register.userInfo.verify
            }, function (data) {
                if (data.meta.code === 200) {
                    console.log("绑定手机成功");
                    console.log(data);
                    register.tabFun();
                }
            })
        }
    });
    // 填写信息
    $('#tab-3').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            registerName: {
                message: '注册人真实姓名格式错误',
                validators: {
                    notEmpty: {
                        message: '注册人真实姓名不能为空'
                    }
                }
            },
            papersNum: {
                message: '证件号码格式错误',
                validators: {
                    notEmpty: {
                        message: '证件号码不能为空'
                    },
                    regexp: {
                        regexp: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/,
                        message: '身份证件格式错误'
                    }
                }
            },
            email: {
                message: '电子邮箱错误',
                validators: {
                    notEmpty: {
                        message: '电子邮箱不能为空'
                    },
                    emailAddress: {     //　　邮箱格式校验
                        message: '邮箱格式错误'
                    }
                }
            },
            contactPhone: {
                validators: {
                    notEmpty: {
                        message: '联系电话不能为空'
                    },
                    regexp: {
                        regexp: /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)/,
                        message: '联系电话格式错误'
                    }
                }
            },
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        if ($('img.kv-preview-data.file-preview-image').length > 0) {
            for (var i = 0; i < $('img.kv-preview-data.file-preview-image').length; i++) {
                register.userInfo.papersImg.push($('img.kv-preview-data.file-preview-image').eq(i).attr("src"))
            }
            console.log(register.userInfo);
            console.log(register.userInfo.papersImg);
            //  填写信息事件
            $.post(register.api.authenticate, {
                // 真实姓名
                real_name: register.userInfo.registerName,
                // 证件类型
                identity_type: register.userInfo.papersType,
                // 证件号码
                identity: register.userInfo.papersNum,
                // 证件图片
                // img:register.userInfo.papersImg,
                imgId: register.userInfo.imageId,
                // 固话
                fixed_line: register.userInfo.contactPhone,
                // 邮箱
                email: register.userInfo.email
            }, function (data) {

                console.log("提交信息成功");
                console.log(data);
                register.tabFun();
            })
        } else {
            $("#modalAlert").click();
            $(".modal-body").html(" 请上传照片哦~");
            $('#tab-3').bootstrapValidator('disableSubmitButtons', false);
        }
    });
    // 绑定银行卡
    $('#tab-4').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            cardUsername: {
                message: '持卡人姓名错误',
                validators: {
                    notEmpty: {
                        message: '持卡人姓名不能为空'
                    }
                }
            },
            cardNumber: {
                message: '银行卡号错误',
                validators: {
                    notEmpty: {
                        message: '银行卡号码不能为空'
                    },
                    stringLength: {
                        min: 16,
                        max: 19,
                        message: '银行卡号格式错误'
                    },
                    regexp: {
                        regexp: /^\d{16}|\d{19}$/,
                        message: '请输入正确的银行卡号'
                    }
                }
            },
            bank: {
                message: '开户银行错误',
                validators: {
                    notEmpty: {
                        message: '开户银行不能为空'
                    }
                }
            },
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        // 银行卡绑定事件
        $.post(register.api.bindCreditCard, {
            creditCardOwner: register.userInfo.cardUsername,
            creditCardId: register.userInfo.cardNumber,
            creditCardBank: register.userInfo.bank
        }, function (data) {

            console.log("绑定银行卡");
            console.log(data);
            register.tabFun();
        })

    });
});	
