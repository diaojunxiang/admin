<%@page import="util.Pagination"%>
<%@ page language="java" import="java.util.*,entity.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<title>My JSP 'list.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css"
	href="bootstrap/css/bootstrap.min.css">
<style>
#main {
	width: 650px;
	margin: 10px auto;
}

.fenye {
	float: right;
	margin: 0;
	padding: 0;
}

.fenye li {
	list-style: none;
	float: left;
	padding: 6px 12px;
	font-size: 14px;
	lin-height: 140%;
	border: 1px solid #ddd;
	text-align: center;
	color: #337ab7;
	margin-left: -1px;
}

.fenye li:hover {
	background: #eee;
	cursor: pointer;
}

.fenye li:first-child {
	border-top-left-radius: 4px;
	border-bottom-left-radius: 4px;
}

.fenye li:last-child {
	border-top-right-radius: 4px;
	border-bottom-right-radius: 4px;
}

.fenye .active {
	color: #fff;
	background-color: #337ab7;
	border-color: #337ab7;
}

.fenye .active:hover {
	color: #fff;
	background-color: #337ab7;
	border-color: #337ab7;
}

#mes {
	float: right;
	width: 200px;
	height: 36px;
	line-height: 30px;
	letter-spacing: 3px;
	color: red;
	letter-spacing: 3px;
}

#search {
	overflow: hidden;
	margin-bottom: 20px;
	margin-top: 30px;
}

#search #searchBtn {
	
}

#search label {
	float: left;
	margin-right: 10px;
	height: 30px;
	line-height: 30px;
}

#search .value {
	width: 120px;
	height: 30px;
	float: left;
	margin-right: 20px;
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				var ye = ${p.ye};
				var maxYe = ${p.maxYe};
				var selectId = 0;
				$("#pre").click(
						function() {
							if (ye > 1) {
								var name = $("#name").val();
								
								window.location.href = "sub?type=search&ye="
										+ (ye - 1) + "&name=" + name;
							} else {
								// 				alert("已经是第一页了");
								showMes("已经是第一页了");
							}
						})
				$("#next").click(
						function() {
							if (ye < maxYe) {
								var name = $("#name").val();
								window.location.href = "sub?type=search&ye="
										+ (ye + 1) + "&name=" + name;
							} else {
								showMes("已经是最后一页了");
							}
						})
				$("[name=numPage]").click(
						function() {
							var name = $("#name").val();
							window.location.href = "sub?type=search&ye="
									+ $(this).html() + "&name=" + name;
						})
				$("#add").click(function() {
					window.location.href = "sub?type=showAdd";
				});
				function showMes(mes) {
					$("#mes").html(mes);
					setTimeout(function() {
						$("#mes").html("");
					}, 1000);
				}
				$("#modify").click(
						function() {
							if (selectId == 0) {
								showMes("请选中一条数据");
							} else {
								location.href = "sub?type=showModify&selectId="
										+ selectId;
							}
						})
				$("#delete").click(
						function() {
							if (selectId == 0) {
								showMes("请选中一条数据");
							} else {
								var type = confirm("确认要删除数据吗?");
								if (type) {
									location.href = "sub?type=delete&selectId="
											+ selectId;
								}
							}
						})
				$("tbody tr").click(function() {
					selectId = $(this).data("id");
					$("tbody tr").css("background", "none");
					$(this).css("background", "#337ab7");
				})
			})
</script>
</head>


<body>
<%-- 	<%
		List<BanJi> list = (List<BanJi>) request.getAttribute("subs");
	%> --%>
	<div id="main">
		<div id="search">
			<form action="sub" method="post">
				<input type="hidden" name="type" value="search"> <label>名称</label>
				<input id="name" type="text" name="name" class="form-control value"
					value="${condition.name }" /> 
				<input id="searchBtn" type="submit" value="查找"
					class="btn btn-primary" />
			</form>
		</div>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>名称</th>
				</tr>
			</thead>
			<tbody>
				<%-- <%
					for (int i = 0; i < list.size(); i++) {
				%> --%>
				<c:forEach var="sub" items="${subs}">
				<tr data-id="${sub.id}">
					<td>${sub.name }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>

		<ul class="fenye">
			<li id="pre">上一页</li>
			<c:forEach begin="${p.begin}" end="${p.end}" varStatus="status">
			<li name="numPage" <c:if test="${p.ye==status.index }">class="active"</c:if>>${status.index}</li>
			</c:forEach>
			<li id="next">下一页</li>
		</ul>
		<div id="mes"></div>
		<div class="btn-group">
			<button id="add" type="button" class="btn btn-danger">新增</button>
			<button id="modify" type="button" class="btn btn-primary">修改</button>
			<button id="delete" type="button" class="btn btn-primary">删除</button>
		</div>
	</div>
</body>
</html>
