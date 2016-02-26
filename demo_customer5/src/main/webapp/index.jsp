<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../taglib/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>添加雇员</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/employee/employee.js"></script>
</head>

<body>
	<div>
		<div class="jumbotron">
			<h1>欢迎登陆页面！</h1>
			<p>这是一个添加信息页面。</p>
			<p>
				<a class="btn btn-primary btn-lg" role="button"> 了解更多</a>
			</p>
		</div>
	</div>
	<form name="addEmployee" id="addEmployeeForm" class="form-horizontal" method="post"
		action="${pageContext.request.contextPath}/employeeController2/addEmployee.jing">
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">姓名：</label>
			<div class="col-sm-2">
				<input type="text" class="form-control" name="name" id="name" maxlength="32" placeholder="请输入名字">
			</div>
		</div>
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">邮箱：</label>
			<div class="col-sm-2">
				<input type="text" class="form-control" name="email" id="email" onblur="validateEmail(this)" placeholder="请输入邮箱">
			</div>
		</div>
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">地址：</label>
			<div class="col-sm-2">
				<input type="text" class="form-control" name="address" id="address" placeholder="请输入地址">
			</div>
		</div>
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">电话：</label>
			<div class="col-sm-2">
				<input type="text" class="form-control" name="phoneNumber" id="phoneNumber" onblur="validatePhone(this)" placeholder="请输入电话号码">
			</div>
		</div>
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label">日期：</label>
			<div class="col-sm-2">
				<input type="text" name="createTime" class="input-append date form_datetime form-control" readonly /><br />
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-2 control-label">状态：</label>
			<div class="col-sm-2">
				<select name="status" class="form-control">
					<option value="0">停用</option>
					<option value="1">启用</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-2 control-label">备注：</label>
			<div class="col-sm-4">
				<textarea name="remark" class="form-control" rows="5" maxlength="256"></textarea>
			</div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-2 control-label"></label>
			<div class="col-sm-2">
				<input type="submit" value="提交" class="btn btn-default"><br />
			</div>
		</div>
	</form>

</body>
</html>