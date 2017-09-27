<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'login.jsp' starting page</title>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<script src="js/jquery.js" type="text/javascript"></script>
<script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script>
	$().ready(function() {
		//bom编程
		if (self != top) {
			top.location = self.location;
		
		}

		$("#image").click(function() {
			$(this).attr("src", "ur?type=randomImage&name=" + Math.random());
		})
	})
</script>
</head>

<body>

	<div style="width:600px;margin:20px auto;margin-top:120px;">
		<form id="form" action="ur?type=doLogin" method="post"
			class="form-horizontal" role="form">
			<div class="form-group">
				<label class="col-xs-2 control-label"> 账号：</label>
				<div class="col-xs-6">
					<input type="text" name="username" class="form-control"
						placeholder="请输入账号" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"> 密码：</label>
				<div class="col-xs-6">
					<input type="password" name="password" class="form-control"
						placeholder="请输入密码" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-2 control-label"> 验证：</label>
				<div class="col-xs-6">
					<input type="password" name="random" class="form-control"
						placeholder="请输入验证码" />
				</div>
				<div class="col-xs-4">
					<img id="image" src="ur?type=randomImage" />
				</div>
			</div>
			<div id="mes" style="height:40px;"></div>
			<div class="form-group" style="width:200px;margin-left:180px;">
				<input id="login" type="submit" class="btn btn-primary" value="登录" />

			</div>

		</form>
	</div>



</body>
</html>
