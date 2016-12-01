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
		userInfo:{
			// 注册
			user_name: "",
			password: "",
			// 绑定手机
			telephone: "",
			verify: "",
			// 填写信息
			registerType: "",
			companyType: "",
			companyName: "",
			companyBusiness: "",
			aptitudeFile: "",
			license: "",
			legalPerson: "",
			contactPhone: "",
			site: "",
			email: "",
			// 绑定银行卡
			cardUsername: "",
			cardNumber: "",
			bank: ""
		},
        tabIndex: 0,
        api: {
            registerApi: "/user/register",
            telephoneApi: "/user/bindMobile",
            findMobile: "/user/findMobile",
            findUserName: "/user/findUserName",
            sendVerification: "/user/sendVerification"
        }
	},
    methods: {
        tabFun: function(){
            this.tabIndex ++;
            if(this.tabIndex > 3){
                this.tabIndex = 3;
                // 跳转首页
                window.location="/login.html";
            }
            $(".nav-tabs > li").removeClass('active').eq(register.tabIndex).addClass('active');
            $(".tab-content > div").removeClass('active').eq(register.tabIndex).addClass('active');
        }
    }
});
$.ajaxSetup({
   headers: { 'HTTP_AUTHORIZATION':  $.cookie("Authorization")  }
});
// 发送验证码
$("#verifyBtn").on('click', function(event) {
	event.preventDefault();
	/* Act on the event */
	var that = $(this);
    var num = $(this).attr("value");
    // 手机号码格式不对
    if($("#phone").parent().hasClass('has-error') || $("#phone").val().length < 11){
        $("#modalAlert").click();
        $(".modal-body").html(" 请输入正确的手机号码");
    }else{
        $(this).attr("value",num+1);
        console.log("发送");
        var timer = null;
        var time = 60; 
        that.addClass('disabled  active');
        timer = setInterval(function(){
            time --;
            if(time <= 0){
                clearInterval(timer);
                that.removeClass('disabled  active');
                that.text("重新发送");
            }else{
                that.text(time+"秒后重新发送");
            }
        },1000);
        $.post(register.api.sendVerification,{
            telephone: register.userInfo.telephone
        },function(data){
            if(data.meta.code === 200){
                console.log("已发送");
            }
        })
    }
});
// 跳过按钮
$(".btn-skip").on('click', function(event) {
    event.preventDefault();
    /* Act on the event */
    // 清除数据
    console.log("清除数据");
    $("#tab-"+(register.tabIndex+1)+" input").val();
    // 清除提示、图标
    console.log("清除提示、图标");
    $("#tab-"+(register.tabIndex+1)).data('bootstrapValidator').resetForm();
    // 下一步
    console.log("下一步");
    register.tabFun();
});
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
                    threshold :  6,
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                        url: register.api.findUserName,//验证地址
                        message: '用户已存在',//提示消息
                        delay :  1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
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
    }).on('success.form.bv', function(e) {
		e.preventDefault();
		// 注册事件
		console.log(register.userInfo.user_name);
        console.log(register.userInfo.password);
		$.post(register.api.registerApi,{user_name:register.userInfo.user_name,password:register.userInfo.password},function(data){
			console.log(data);
			if(data.meta.code === 200) {
                // 清除旧cookie,生成新cookie;
                $.cookie("Authorization",null);
                $.cookie("Authorization",data.data.token);
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
                    threshold :  4,
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                        url: register.api.findMobile,//验证地址
                        message: '手机号码已存在',//提示消息
                        delay :  1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
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
    }).on('success.form.bv', function(e) {
		e.preventDefault();
        if($("#verifyBtn").attr("value") <= 0){
            $("#modalAlert").click();
            $(".modal-body").html("请点击发送验证码按钮");
            $('form').bootstrapValidator('disableSubmitButtons', false);
            return;
        }else{
            console.log(register.userInfo.telephone);
            console.log($.cookie("Authorization"));
            $.post(register.api.telephoneApi,{
                telephone: register.userInfo.telephone,
                verify: register.userInfo.verify
            },function(data){
                if(data.meta.code === 200){
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
            companyName: {
                message: '企业全称错误',
                validators: {
                    notEmpty: {
                        message: '企业全称不能为空'
                    }
                }
            },
            companyBusiness: {
                message: '主营业务错误',
                validators: {
                    notEmpty: {
                        message: '主营业务不能为空'
                    }
                }
            },
            aptitudeFile: {
                message: '资质文件错误',
                validators: {
                    notEmpty: {
                        message: '资质文件编码不能为空'
                    }
                }
            },
            license: {
                message: '营业执照错误',
                validators: {
                    notEmpty: {
                        message: '营业执照编码不能为空'
                    }
                }
            },
            legalPerson: {
                message: '法人代表错误',
                validators: {
                    notEmpty: {
                        message: '法人代表不能为空'
                    }
                }
            },
            site: {
                message: '地址错误',
                validators: {
                    notEmpty: {
                        message: '地址不能为空'
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
    }).on('success.form.bv', function(e) {
		e.preventDefault();
		// 注册事件
		$.post("/test",{
			registerType:register.userInfo.registerType,
			companyName:register.userInfo.companyName,
			companyBusiness:register.userInfo.companyBusiness,
			aptitudeFile:register.userInfo.aptitudeFile,
			license:register.userInfo.license,
			legalPerson:register.userInfo.legalPerson,
			contactPhone:register.userInfo.contactPhone,
			site:register.userInfo.site,
			email:register.userInfo.email,
			companyType:register.userInfo.companyType,
		},function(data){

			console.log("提交信息成功");
			console.log(data);
            register.tabFun();
		})
		
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
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		// 银行卡绑定事件
		$.post("/test",{
			cardUsername:register.userInfo.cardUsername,
			cardNumber:register.userInfo.cardNumber,
			bank:register.userInfo.bank
		},function(data){

			console.log("绑定银行卡");
			console.log(data);
            register.tabFun();
		})
		
	});
});	
