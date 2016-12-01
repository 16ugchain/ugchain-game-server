// Vue
var loginData = new Vue({
	el: "#loginBox",
	data:{
		userInfo: {
			username: "",
			password: "",
			loginAuto: false,
			identity: "1"
		},
		loginMessage: "登录",
		
	},
	methods: {
		
	}
})
// 使用者身份切换
$(".login-type-tab").on('click', 'button', function(event) {
	event.preventDefault();
	/* Act on the event */
	$(this).addClass('btn-success').removeClass('btn-default').siblings().removeClass('btn-success').addClass('btn-default');
	loginData.userInfo.identity = $(this).attr("value");
});
// 表单验证
$(function() {
    $('form').bootstrapValidator({
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
                        min: 4,
                        max: 18,
                        message: '用户名长度必须在6到18位之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '用户名只能包含大写、小写、数字和下划线'
                    }
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
                    different: {//不能和用户名相同
                     	field: 'username',
                     	message: '不能和用户名相同'
                 	}
                }
            }
        }
    }).on('success.form.bv', function(e) {
    	 // 全部验证通过，提交校验
		e.preventDefault();
		loginData.loginMessage = "正在登录..."
		$.post("/login",loginData.userInfo,function(data){
			if(data.meta.code === 200 && data.meta.message === "success"){
				console.log("成功");
				loginData.loginMessage = "登录成功";
				// 清除旧cookie,生成新cookie;
				$.cookie("loginAuto",null);
				$.cookie("loginAuto",data.data.token);
				// 跳转首页
				window.location="/"
			}else{ 
				var tip = "";
				if(data.meta.code === 6003){
					// 密码错误事件
					
					tip = "密码错误，请重试"
	
					// $("#password").addClass('has-error').children('i').addClass('glyphicon-remove').parent().append('<small class="help-block" data-bv-validator="error" data-bv-for="password" data-bv-result="VALID" style="display: block;">密码错误</small>')
				}else if(data.meta.code === 500){
					// 空账号事件
					tip = "空账号，请重试"
				}else if(data.meta.code === 6010){
					// 角色错误
					tip = "用户角色不匹配，请重新选择用户角色"
					// console.log("空账号事件");
				}else if(data.meta.code === 6001){
					// 用户不逊在
					tip = "账户不存在，请重试"
					// console.log("空账号事件");
				}else{
					// 网络异常事件
					tip = "网络异常，请刷新页面重试"
				}
				loginData.loginMessage = "登录"
				$("form").data('bootstrapValidator').resetForm(); 
				$("#username").val("");
				$("#password").val("");
				$("#modalAlert").click();
				$(".modal-body").html(tip);
			}
		})
	});
});