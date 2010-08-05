<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="system.user.edit.title" /></title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
</head>
<body>
<form:form action="/system/user" onsubmit="return submitDisposal(this);" method="post" target="hideframe"
	modelAttribute="user">
	<div id="divUser" style="position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;">
	<div id="bg">
	<ul id=datamenu_Option class="cb font1">
		<li class="curr"><a href="javascript:showUsernfo();" onfocus="blur()"><spring:message
			code="system.user.czyxx" /></a></li>
		<li><a href="javascript:showFw();" onfocus="blur()"><spring:message code="system.user.ywfw" /></a></li>
		<li><a href="javascript:showGw();" onfocus="blur()"><spring:message code="system.user.gwjs" /></a></li>
		<li><a href="javascript:showQx();" onfocus="blur()"><spring:message code="system.user.czqx" /></a></li>
	</ul>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="contentLeft"></td>
			<td height="20" colspan="3"></td>
			<td class="contentRight"></td>
		</tr>
		<tr>
			<td class="contentLeft"></td>
			<td bgcolor="#FFFFFF" colspan="3" align="center">
			<table width="99%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="15%" height="30" align="right"><font color="#ff0000">*</font><spring:message code="system.user.zh" />：</td>
					<td width="30%" align="left"><form:input path="staffNo" cssStyle="width:150"/></td>
					<td width="15%" height="30" align="right"><font color="#ff0000">*</font><spring:message code="system.user.mc" />：</td>
					<td width="30%" align="left"><form:input path="name" cssStyle="width:150"/></td>
				</tr>
				<tr>
					<td align="right"><font color="#ff0000">*</font><spring:message code="system.user.mm" />：</td>
					<td align="left"><form:password path="passwd" showPassword="true" cssStyle="width:150"/></td>
					<td align="right"><font color="#ff0000">*</font><spring:message code="system.user.qrmm" />：</td>
					<td align="left"><input type="password" name="passwd" id="passwd_rep" value="${user.passwd}" style="width:150"></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="system.user.ssdw" />：</td>
					<td align="left"><form:select path="orgInfo.orgId" items="${orgInfo}" disabled="${disabled}" id="orgId"
						itemLabel="orgName" itemValue="orgId" cssStyle="width:150px;" /></td>
					<td align="right"><spring:message code="system.user.dh" />：</td>
					<td align="left"><form:input path="mobile" cssStyle="width:150"/></td>
				</tr>
				<tr>
					<td align="right"><spring:message code="system.user.zt" />：</td>
					<td align="left"><form:select path="enable" items="${userStat}" disabled="${disabled}" id="enable"
						itemLabel="name" itemValue="code" cssStyle="width:150px;" /></td>
				</tr>
			</table>
			<table align="center">
				<tr>
					<td width="100%" height="30" align="center"><input type="submit" name="queding2"
						value='<spring:message code="system.button.qd" />' "/> &nbsp; <input type="button" name="cancel2"
						value='<spring:message code="system.button.qx" />' " onclick="top.GB_hide()" /></td>
				</tr>
			</table>
			</td>
			<td class="contentRight"></td>
		</tr>
	</table>
	</div>
	<div id="divFw" style="display: none; position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;">
	<div id="bg">
	<ul id=datamenu_Option class="cb font1">
		<li><a href="javascript:showUsernfo();" onfocus="blur()"><spring:message code="system.user.czyxx" /></a></li>
		<li class="curr"><a href="javascript:showFw();" onfocus="blur()"><spring:message code="system.user.ywfw" /></a></li>
		<li><a href="javascript:showGw();" onfocus="blur()"><spring:message code="system.user.gwjs" /></a></li>
		<li><a href="javascript:showQx();" onfocus="blur()"><spring:message code="system.user.czqx" /></a></li>
	</ul>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="contentLeft"></td>
			<td height="20" colspan="3"></td>
			<td class="contentRight"></td>
		</tr>
		<tr>
			<td class="contentLeft"></td>
			<td bgcolor="#FFFFFF" colspan="3" align="center">
			<div class="tableContainer" style="width: 99%;" align="center">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th width="20%"><spring:message code="system.user.xh" /></th>
						<th width="40%"><spring:message code="system.user.dxmc" /></th>
						<th width="20%"><spring:message code="system.user.sq" /><input type="checkbox" name="select1"
							onclick="selectAllBox(this,'sRole1')" /></th>
						<th width="20%"><spring:message code="system.user.ksq" /><input type="checkbox" name="select2"
							onclick="selectAllBox(this,'rRole1')" /></th>
					</tr>
				</thead>
				<tbody>
					<tr align="center" class="trmainstyle">
						<td height="20"></td>
						<td height="20"></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			</div>
			<table align="center">
				<tr>
					<td width="100%" height="30" align="center"><input type="submit" name="queding2"
						value='<spring:message code="system.button.qd" />' "/> &nbsp; <input type="button" name="cancel2"
						value='<spring:message code="system.button.qx" />' " onclick="top.GB_hide()" /></td>
				</tr>
			</table>
			</td>
			<td class="contentRight"></td>
		</tr>
	</table>
	</div>
	<div id="divGw"
		style="display: none; position: absolute; top: 0px; left: 0px; width: 100%; height: 100%; OVERFLOW-Y: AUTO;">
	<div id="bg">
	<ul id=datamenu_Option class="cb font1">
		<li><a href="javascript:showUsernfo();" onfocus="blur()"><spring:message code="system.user.czyxx" /></a></li>
		<li><a href="javascript:showFw();" onfocus="blur()"><spring:message code="system.user.ywfw" /></a></li>
		<li class="curr"><a href="javascript:showGw();" onfocus="blur()"><spring:message code="system.user.gwjs" /></a></li>
		<li><a href="javascript:showQx();" onfocus="blur()"><spring:message code="system.user.czqx" /></a></li>
	</ul>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="contentLeft"></td>
			<td height="20" colspan="3"></td>
			<td class="contentRight"></td>
		</tr>
		<tr>
			<td class="contentLeft"></td>
			<td bgcolor="#FFFFFF" colspan="3" align="center">
			<div class="tableContainer" style="width: 99%;" align="center">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<thead>
					<tr class=trheadStyle>
						<th width="10%"><spring:message code="system.user.xh" /></th>
						<th width="20%"><spring:message code="system.user.dxmc" /></th>
						<th width="10%"><spring:message code="system.user.sq" /><input type="checkbox" name="select1"
							onclick="selectAllBox(this,'sRole2')" /></th>
						<th width="10%"><spring:message code="system.user.ksq" /><input type="checkbox" name="select2"
							onclick="selectAllBox(this,'rRole2')" /></th>
					</tr>
				</thead>
				<tbody>
					<tr align="center" class="trmainstyle">
						<td height="20"></td>
						<td height="20"></td>
					</tr>
				</tbody>
			</table>
			</div>
			<table align="center">
				<tr>
					<td width="100%" height="30" align="center"><input type="submit" name="queding2"
						value='<spring:message code="system.button.qd" />' "/> &nbsp; <input type="button" name="cancel2"
						value='<spring:message code="system.button.qx" />' " onclick="top.GB_hide()" /></td>
				</tr>
			</table>
			</td>
			<td class="contentRight"></td>
		</tr>
	</table>
	</div>
	<div id="divQx"
		style="display: none; position: absolute; top: 0px; left: 0px; width: 100%; height: 100%; OVERFLOW-Y: AUTO;">
	<div id="bg">
	<ul id=datamenu_Option class="cb font1">
		<li><a href="javascript:showUsernfo();" onfocus="blur()"><spring:message code="system.user.czyxx" /></a></li>
		<li><a href="javascript:showFw();" onfocus="blur()"><spring:message code="system.user.ywfw" /></a></li>
		<li><a href="javascript:showGw();" onfocus="blur()"><spring:message code="system.user.gwjs" /></a></li>
		<li class="curr"><a href="javascript:showQx();" onfocus="blur()"><spring:message code="system.user.czqx" /></a></li>
	</ul>
	</div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="contentLeft"></td>
			<td height="20" colspan="3"></td>
			<td class="contentRight"></td>
		</tr>
		<tr>
			<td class="contentLeft"></td>
			<td bgcolor="#FFFFFF" colspan="3" align="center">
			<div class="tableContainer" style="width: 99%;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<thead>
					<tr class="trheadStyle">
						<th width="10%"><spring:message code="system.user.xh" /></th>
						<th width="20%"><spring:message code="system.user.dxmc" /></th>
						<th width="10%"><spring:message code="system.user.sq" /><input type="checkbox" name="select1"
							onclick="selectAllBox(this,'sRole3')" /></th>
						<th width="10%"><spring:message code="system.user.ksq" /><input type="checkbox" name="select2"
							onclick="selectAllBox(this,'rRole3')" /></th>
					</tr>
				</thead>
				<tbody>
					<tr align="center" class="trmainstyle">
						<td height="20"></td>
						<td height="20"></td>
					</tr>
				</tbody>
			</table>
			</div>
			<table align="center">
				<tr>
					<td width="100%" height="30" align="center"><input type="submit" name="queding2"
						value='<spring:message code="system.button.qd" />' "/> &nbsp; <input type="button" name="cancel2"
						value='<spring:message code="system.button.qx" />' " onclick="top.GB_hide()" /></td>
				</tr>
			</table>
			</td>
			<td class="contentRight"></td>
		</tr>
	</table>
	</div>
</form:form>
<iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
</body>
<script type="text/javascript">
var contextPath = "${ctx}";
//
function showUsernfo() {
    document.all.divUser.style.display = "";
    document.all.divFw.style.display = "none";
    document.all.divGw.style.display = "none";
    document.all.divQx.style.display = "none";
}
function showQx() {
    document.all.divUser.style.display = "none";
    document.all.divFw.style.display = "none";
    document.all.divGw.style.display = "none";
    document.all.divQx.style.display = "";
}
function showFw() {
    document.all.divUser.style.display = "none";
    document.all.divFw.style.display = "";
    document.all.divGw.style.display = "none";
    document.all.divQx.style.display = "none";
}
function showGw() {
    document.all.divUser.style.display = "none";
    document.all.divFw.style.display = "none";
    document.all.divGw.style.display = "";
    document.all.divQx.style.display = "none";
}

// 判断岗位是否选择上
function gwIsChecked() {
    var th = document.forms[0];
    for(var i=0;i<th.elements.length;i++) {
       var o=th.elements[i];
       if(o.name=="sRole2" && o.checked)
        {
           return true;
        }
    }
    return false;
} 

// 判断业务范围是否选择上

function fwIsChecked() {
    var th=document.forms[0];
    for(var i=0;i<th.elements.length;i++) {
        var o=th.elements[i];
        if(o.name=="sRole1" && o.checked) {
            return true;
        }
    }
    return false;
}

//提交处理
function submitDisposal(form) {
    var password =document.getElementsByName("passwd");
    var password1 =document.getElementsByName("passwd1");
    
    if(password[0].value!=password1[0].value) {
        alert('<bean:message bundle="system" key="errors.passwd.different"/>');
        return false;
    }
    
    if(validateUserForm(form)){
        if(!fwIsChecked() && gwIsChecked()){
            return confirm('<spring:message code="system.user.message.fw.null" />');
        }
        
        if(fwIsChecked() && !gwIsChecked()){
            return confirm('<spring:message code="system.user.message.gw.null" />');
        }
        
        if(!fwIsChecked() && !gwIsChecked()){
            return confirm('<spring:message code="system.user.message.fw.and.gw.null" />');
        }
    }
    else{
        return false;
    }
}

function event(obj,no) {
	var name=obj.name;
	var rname = "r"+name.substring(1);
	var box=document.getElementsByName(rname)[no-1];
	if(obj.checked){
		box.disabled=false;
	}else{
		if(box.checked){
			box.click()
		}
		box.disabled=true;
	}
}
</script>
</html>
