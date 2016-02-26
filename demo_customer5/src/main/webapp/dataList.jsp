<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../taglib/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>雇员列表</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/employee/employee.js"></script>
</head>

<body>
	<div class="container">
		<table class="table table-bordered">
			<caption>基本的表格布局<a href="${pageContext.request.contextPath}/index.jsp">添加雇员</a></caption>
			<thead>
				<tr>
					<th>id</th>
					<th>名称</th>
					<th>email</th>
					<th>地址</th>
					<th>电话号码</th>
					<th>备注</th>
					<th>加入日期</th>
					<th>员工状态</th>
					<th colspan="2" style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="employee">
					<tr id="tr${employee.id }">
						<td>${employee.id }</td>
						<td><a href="${pageContext.request.contextPath}/employeeController2/getEmployeeById.jing?name=${employee.name }&id=${employee.id }">${employee.name }</a></td>
						<td>${employee.email }</td>
						<td>${employee.address }</td>
						<td>${employee.phoneNumber }</td>
						<td>${employee.remark }</td>
						<!--  当 redis.xml中redisTemplate的valueSerializer设置成userJsonRedisSerializer时，页面这样取值(employee.getJoinTimeValue())就会报错， -->
						<td><input type="text"
							value="${employee.getJoinTimeValue() }"
							class="input-append date form_datetime" size="16" readonly></td>
						<td>${employee.getStatusName() }</td>
						<td><button type="button" id="${employee.id }"
								class="btn btn-default" onclick="return deleteEmployee(this)">删除</button></td>
						<td><button type="button" id="${employee.id }"
						class="btn btn-default" onclick="return updateEmployee(this)">修改</button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>