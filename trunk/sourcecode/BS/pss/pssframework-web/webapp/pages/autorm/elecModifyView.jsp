<%/** 数据修正页面  */ %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据修正页面</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css"/>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script>
var contextPath = "<peis:contextPath/>";
var info;
var data;
$(document).ready(function(){
	
	$("#progress").ajaxStart(function(){
		$(this).show();
	});
	$("#progress").ajaxStop(function(){
		$('#query').attr("disabled",false);
		$(this).hide();
	});

	
	info = window.opener.info;
	data = window.opener.data;
  	fillTable(info,data);
  	$('#btn_mod').bind("click",modElecDatas);
});


function modElecDatas(){
	var url = contextPath + "/do/autorm/DataCheckQuery.do?action=update&" + $('#form1').serialize() + "&page=&r=" + parseInt(Math.random() * 1000);
	$.ajax({
		type:"GET",
		url:url,
		cache:false,
		success:function(html){
			if(html==""){
				alert("更新成功！");
				window.opener.document.getElementById("form1").submit();
			}else{
				alert(html);
			}
			window.close();
		},
		error: function(error){
        	alert('数据库异常，请检查数据库连接！');
   		}
	});

	
}


/** 填充表格  */
function fillTable(info,data){
	
	$("input:hidden").each(function(){
		$(this).val(data[$(this).attr("name")]);
	});
	
	
	$('#TABLE1 tr').each(function(i){
		i++;
		if(info[i] && i<5 )
			$('td:eq(1)',$(this)).html(info[i]);
		else{
			$('td:eq(1)',$(this)).each(function(n){
				$(this).html("原始值<input size='5'  readonly='readonly' name='oldValues' value='"+(info[i]=="&nbsp;"?"":info[i])+"'>修改值<input size='5' name='newValues'  value='"+(info[i]=="&nbsp;"?"":info[i])+"'>");
			});
		}
	});
}





</script>
</head>
<body>
	<div id="body">
		<div id="progress" style="display:none;">操作执行中&#8230;</div>
		<form id="form1">
		<input type="hidden" name="gpId">
		<input type="hidden" name="chkType">
		<input type="hidden" name="dataDens">
		<input type="hidden" name="dataType">
		<input type="hidden" name="dDate">
		<div id="main">
			<div class="content" >
				<div id="cont_1">
					<div class="tableContainer" style="height: expression((( document . documentElement . clientHeight || document . body . clientHeight) -   80 ) );">
						<table id="TABLE1" border="0" cellpadding="0" cellspacing="0" width="50%">
							<tr><td>户名</td><td></td></tr>
							<tr><td>户号</td><td></td></tr>
							<tr><td>表号</td><td></td></tr>
							<tr><td>时间</td><td></td></tr>
							<tr><td>总示数</td><td></td></tr>
							<tr><td>尖示数</td><td></td></tr>
							<tr><td>峰示数</td><td></td></tr>
							<tr><td>平示数</td><td></td></tr>
							<tr><td>谷示数</td><td></td></tr>
							<tr><td colspan="2" align="center"><input id="btn_mod" type="button" value="修改"><input type="button" value="关闭" onclick="window.close()"></td></tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
</body>
</html>