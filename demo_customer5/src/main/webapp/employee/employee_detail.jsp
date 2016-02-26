<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../taglib/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>雇员信息</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/employee/employee.js"></script>
</head>

<body>
	<div class="container">
		<table class="table table-bordered">
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
				</tr>
			</thead>
			<tbody>
				<tr id="tr${employee.id }">
					<td>${employee.id }</td>
					<td>${employee.name }</a></td>
					<td>${employee.email }</td>
					<td>${employee.address }</td>
					<td>${employee.phoneNumber }</td>
					<td>${employee.remark }</td>
					<td><input type="text"
						value="${employee.getJoinTimeValue() }"
						class="input-append date form_datetime" size="16" readonly></td>
					<td>${employee.getStatusName() }</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>