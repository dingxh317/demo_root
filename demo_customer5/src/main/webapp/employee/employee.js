$(function() {
	$('.form_datetime').datetimepicker({
		language : 'zh', //汉化  
		weekStart : 1,
		todayBtn : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		minView : "month", //选择日期后，不会再跳转去选择时分秒 
		format : "yyyy-mm-dd", //选择日期后，文本框显示的日期格式 
		autoclose : true,//选择日期后自动关闭 
		showMeridian : 1
	});
});

//自定义插件
(function($) {
	$.myconfirm = function(info) {
		return window.confirm(info);
	}
})(jQuery);

//使用回调函数
function testCallback(callback, dom) {
	if ($.myconfirm("are you sure")) {
		callback(dom);
	}
}

//被回调的函数  
function a(dom) {
	var id = $(dom).attr("id");
	$.ajax({
		//url : "/${pageContext.request.contextPath}/employeeController/deleteEmployee.jing?id="+ id,
		url : "deleteEmployee.jing?id="+ id,
		type : "post",
		dataType : "json",
		success : function() {
			var tr = "#tr" + id;
			//alert($(tr).text());
			$(tr).remove();
		}

	});
}
//根据id删除数据 
function deleteEmployee(dom) {
	testCallback(a, dom);
}

//不使用回调函数
function updateEmployee(dom) {
	alert("没做呢！");
}

function validateEmail(dom){
	var email = $(dom).val();
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!filter.test(email)){
		alert('您的电子邮件格式不正确');
		//$(this).focus();//这种写法不好使
        setTimeout(function() { $(dom).focus(); }, 1);
	}
}

function validatePhone(dom){
	var phone = $(dom).val();
	var filter = /^0\d{2,3}-?\d{7,8}$/; 
	if (!filter.test(phone)){
		alert('您的电话号码格式不正确');
		setTimeout(function() { $(dom).focus(); }, 1);
	}
}