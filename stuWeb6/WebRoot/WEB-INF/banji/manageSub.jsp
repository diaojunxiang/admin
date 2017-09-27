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

<title>My JSP 'manageSub.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css"
	href="bootstrap/css/bootstrap.min.css">
<style>
* {
	margin: 0;
	padding: 0;
}

#main {
	width: 650px;
	margin: 30px auto;
}

ul {
	clear: both;
	overflow: hidden;
	height: 150px;
	border: 1px solid #ccc;
	pading: 10px;
	overflow: hidden;
}

ul li {
	float: left;
	width: 100px;
	height: 30px;
	background: #337ab7;
	list-style: none;
	margin: 10px 20px 20px 10px;
	text-align: center;
	line-height: 30px;
	color: #fff;
	font-size: 16px;
	cursor: pointer;
	border-radius: 3px;
}

.selected {
	background: #d9534f;
}

.btnGroup button {
	float: left;
	margin-right: 10px;
}

#mes {
	float: left;
	width: 200px;
	height: 36px;
	line-height: 30px;
	letter-spacing: 3px;
	color: red;
	letter-spacing: 3px;
}

#bj {
	width: 120px;
	height: 30px;
	margin-bottom: 10px;
	font-size: 20px;
	font-weight: bold;
	font-size: 20px;
	font-size: 20px
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						//jquery动态生成的元素，事件不能用的解决办法
						$(document).on("click", "li", function() {
							$(this).toggleClass("selected");
						})
						$("#add")
								.click(
										function() {
											var subIds = new Array();
											$("#noSub li")
													.each(
															function(index,
																	element) {
																if ($(this)
																		.attr(
																				"class") == "selected") {
																	subIds
																			.push($(
																					this)
																					.data(
																							"subid"));
																}
															})
											if (subIds.length > 0) {
												$
														.ajax({
															url : "bj",
															data : "type=addSub&bjId="
																	+ $("#bj").val()
																	+ "&subIds="
																	+ subIds,
															type : "post",
															dataType : "text",
															success : function(
																	data) {
																// 		location.href = "bj?type=manageSub&selectId="
																// 		+ $(
																// 		"#bj")
																// 		.data(
																// 		"bjid");
																var strs = data
																		.split("-|-");
																var subs = JSON
																		.parse(strs[0]);
																var noSubs = JSON
																		.parse(strs[1]);
																var subsStr = "";
																for (var i = 0; i < subs.length; i++) {

																	subsStr += "<li data-subid="+subs[i].id+">"
																			+ subs[i].name
																			+ "</li>"

																}
																$("#sub")
																		.html(
																				subsStr);
																var noSubsStr = "";
																for (var i = 0; i < noSubs.length; i++) {

																	noSubsStr += "<li data-subid="+noSubs[i].id+">"
																			+ noSubs[i].name
																			+ "</li>"

																}
																$("#noSub")
																		.html(
																				noSubsStr);

															}
														})
											} else {
												showMes("请选中一条数据");
											}
										});
						$("#delete")
								.click(
										function() {
											var subIds = new Array();
											$("#sub li")
													.each(
															function(index,
																	element) {
																if ($(this)
																		.attr(
																				"class") == "selected") {
																	subIds
																			.push($(
																					this)
																					.data(
																							"subid"));
																}
															})
											if (subIds.length > 0) {
												$
														.ajax({
															url : "bj",
															data : "type=deleteSub&bjId="
																	+ $("#bj").val()
																	+ "&subIds="
																	+ subIds,
															type : "post",
															dataType : "text",
															success : function(
																	data) {
																// 		location.href = "bj?type=manageSub&selectId="
																// 		+ $(
																// 		"#bj")
																// 		.data(
																// 		"bjid");
																var strs = data
																		.split("-|-");
																var subs = JSON
																		.parse(strs[0]);
																var noSubs = JSON
																		.parse(strs[1]);
																var subsStr = "";
																for (var i = 0; i < subs.length; i++) {

																	subsStr += "<li data-subid="+subs[i].id+">"
																			+ subs[i].name
																			+ "</li>"

																}
																$("#sub")
																		.html(
																				subsStr);
																var noSubsStr = "";
																for (var i = 0; i < noSubs.length; i++) {

																	noSubsStr += "<li data-subid="+noSubs[i].id+">"
																			+ noSubs[i].name
																			+ "</li>"

																}
																$("#noSub")
																		.html(
																				noSubsStr);

															}
														})
											} else {
												showMes("请选中一条数据");
											}
										});
						
						$("#bj")
						.change(
								function() {
									
										$
												.ajax({
													url : "bj",
													data : "type=searchSub&bjId="
															+ $("#bj").val(),
													type : "post",
													dataType : "text",
													success : function(
															data) {
														// 		location.href = "bj?type=manageSub&selectId="
														// 		+ $(
														// 		"#bj")
														// 		.data(
														// 		"bjid");
														var strs = data
																.split("-|-");
														var subs = JSON
																.parse(strs[0]);
														var noSubs = JSON
																.parse(strs[1]);
														var subsStr = "";
														for (var i = 0; i < subs.length; i++) {

															subsStr += "<li data-subid="+subs[i].id+">"
																	+ subs[i].name
																	+ "</li>"

														}
														$("#sub")
																.html(
																		subsStr);
														var noSubsStr = "";
														for (var i = 0; i < noSubs.length; i++) {

															noSubsStr += "<li data-subid="+noSubs[i].id+">"
																	+ noSubs[i].name
																	+ "</li>"

														}
														$("#noSub")
																.html(
																		noSubsStr);

													}
												})
									
								});

						

						function showMes(mes) {
							$("#mes").html(mes);
							setTimeout(function() {
								$("#mes").html("");
							}, 1000);
						}
					})
</script>
</head>


<body>
	<%-- 	<%
		List<BanJi> list = (List<BanJi>) request.getAttribute("subs");
	%> --%>
	<div id="main">
		<select id="bj"><c:forEach items="${bjs}" var="banji">
				<option <c:if test="${banji.id==bj.id }">selected</c:if> data-bjid="${banji.id}" value="${banji.id}">${banji.name}</option>
			</c:forEach></select>
		<ul id="sub">
			<c:forEach items="${bj.subs}" var="sub">
				<li data-subid="${sub.id}">${sub.name}</li>
			</c:forEach>
		</ul>
		<div class="btnGroup">
			<button id="add" type="button" class="btn btn-danger">↑</button>
			<button id="delete" type="button" class="btn btn-danger">↓</button>
		</div>
		<div id="mes"></div>

		<ul id="noSub">
			<c:forEach items="${noSubs}" var="sub">
				<li data-subid="${sub.id}">${sub.name}</li>
			</c:forEach>
		</ul>




	</div>
</body>
</html>
