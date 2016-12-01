<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico">
		<!-- 网站作者 -->
		<meta name="author" content="51区块研发团队">
		<!-- 网站关键词 -->
		<meta name="keywords" content="51区块,数字资产">
		<!-- 网站描述 -->
		<meta name="description" content="51区块">

		<title>登录页面 | 51区块</title>

		<!-- Bootstrap.css -->
		<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">

		<!-- bootstrapValidator.css -->
		<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap-validator/0.5.3/css/bootstrapValidator.min.css">

		<!-- public.css -->
		<link rel="stylesheet" href="/css/public.css">
		

		<!-- jQuery.js 1.11.1-->
		<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

		<!-- Bootstrap.js -->
		<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

		

		<!-- vue.js -->
		<script src="//cdn.bootcss.com/vue/1.0.24/vue.js"></script>

		<!-- jquery.cookie.js -->
		<script src="//cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>

		<!-- bootstrapValidator.js 表单验证-->
		<script src="//cdn.bootcss.com/bootstrap-validator/0.5.3/js/bootstrapValidator.min.js"></script>

		<link rel="stylesheet" href="/css/login.css">
		<link rel="stylesheet" href="/css/login-media.css">
	</head>
	<#include "hello.ftl" >
	<body>
	    <nav class="navbar navbar-default" role="navigation">
			<div class="container-fluid max-width">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					</button>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		      		
					<ul class="nav navbar-nav navbar-right">
						<li>
							<a href="/register.html">
								<span>
									还没有账号？
								</span>
								<u>
									直接注册 >>
								</u>
							</a>
						</li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>
	    <section class="container-fluid">
		    <div class="row login-contain ">
			    <div class="max-width">
			    	<div class="col-sm-6 col-sm-offset-1 hidden-xs">
			    		<!-- <img src="/images/login-bg.png" alt="" class="img-responsive"> -->
			    	</div>
			    	<!-- 登录框 -->
			    	<div class="col-sm-5">
			    		<div class="login-box" id="loginBox">
			    			<div class="login-header text-right">
			    				<!-- <img src="/images/logo.png" alt=""> -->
			    				<strong>登录</strong>
			    			</div>
			    			<div class="btn-group login-type-tab">
							  	<button type="button" class="btn btn-success col-xs-4" value="1">发行人</button>
							  	<button type="button" class="btn btn-default col-xs-4" value="2">受让人</button>
							  	<button type="button" class="btn btn-default col-xs-4" value="3">担保公司</button>
							</div>
							<form role="form">
							  	<div class="form-group">
							    	<input type="text" class="form-control" id="username" placeholder="请输入用户名" name='username' v-model="userInfo.user_name">
							  	</div>
							  	<div class="form-group" id="passwordBox">
							    	<input type="password" class="form-control" id="password" placeholder="请输入密码" name="password" v-model="userInfo.password">
							  	</div>
							  	<div class="row">
							  		<div class="col-xs-6">
							  			
								  		<label for="login-auto">
								  			<input type="checkbox" id="login-auto" v-model="userInfo.auto_login">
								  			下次自动登录
								  		</label>
							  		</div>
							  		<div class="col-xs-6 text-right">
							  			<a href="/find-password.html" class="text-muted"><u>忘记密码？</u></a>
							  		</div>
							  	</div>
							  	<button type="submit" class="btn btn-success submit-btn btn-absolutely" id="loginSubmit"  disabled="disabled">{{loginMessage}}</button>
							</form>
							<div class="text-center">
								还没有 51区块 账户？
								<p>
									<a href="/register" class="text-success">免费注册 >></a>
								</p>	
							</div>
			    		</div>
			    		
			    	</div>
			    </div>
		    	
		    </div>
		    <!-- 模态框 -->
		    <button type="button" class="btn btn-primary" data-toggle="modal" id="modalAlert" data-target=".bs-example-modal-sm" style="width:0px;height:0px;padding: 0px;margin: 0px;overflow: hidden; position: fixed;top: 100000000000px">友情提示</button>
			<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-sm">
					<div class="modal-content">

				        <div class="modal-header">
				          	<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
				          	<h4 class="modal-title" id="mySmallModalLabel">温馨提醒:</h4>
				        </div>
				        <div class="modal-body">
				          	密码
				        </div>
				      </div>
				</div>
			</div>
	    </section>
	    <!-- 模态框 -->
		<script src="/js/login.js"></script>
	</body>
</html>