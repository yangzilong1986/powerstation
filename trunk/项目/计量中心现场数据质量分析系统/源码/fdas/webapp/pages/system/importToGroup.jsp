<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<title><bean:message bundle="system" key="group.page.title" /></title>
<link href="<peis:contextPath/>/css/window.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" language="javascript"
	src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
var errorQzmc='<bean:message bundle="system" key="group.errorQzmc"/>';
var sSelectedGroupID = "";   
function selectRow(sGroupID, oRow) {
 	sSelectedGroupID = sGroupID;
   	selectSingleRow(oRow);
}		
		
function showAddGroup() {
    document.all.divGroupInfo.style.display = "";
    document.all.divGroupList.style.display = "none";
}

function showGroupList() {
    document.all.divGroupInfo.style.display = "none";
    document.all.divGroupList.style.display = "";;
}


function importToExistGroup(form) {
	if(sSelectedGroupID==null || sSelectedGroupID==""){
		alert("<bean:message bundle="system" key="group.error.noselect"/>");
		return;
	}
	form.s_id.value=sSelectedGroupID;
	form.userList.value = window.opener.document.forms[0].userList.value
    form.action.value="importToExistGroup";
    form.submit();
}

function importToNewGroup(form) {
	var GName = trim(document.all.GName.value);
	if(GName==""){
		alert(errorQzmc);
		document.all.GName.focus();
        return;
	}
	
	form.userList.value = window.opener.document.forms[0].userList.value
    form.action.value="importToNewGroup";
    form.submit();
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
</head>

<body>
<html:form action="/importToGroupAction">
	<html:hidden property="userList" value="" />
	<html:hidden property="pageFlag" value="query" />
	<html:hidden property="s_id" value="" />
	<html:hidden property="action" value="initPage" />
	<html:hidden property="pageType" value="page" />
	<peis:text name="pageRows" type="hidden" />
	<html:hidden property="sqlcode" value="AL_SYSTEM_0100" />
	<div id="divGroupInfo"
		style="display: none;position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;">
	<div class="equip_title">
	<ul class="e_title">
		<li class="e_titleoff"><a href="javascript:showGroupList();"
			onFocus="blur()"><bean:message bundle="system"
			key="group.tab.add.exist" /></a></li>
		<li class="e_titleon"><a href="javascript:showAddGroup();"
			onFocus="blur()"><bean:message bundle="system"
			key="group.tab.add.new" /></a></li>
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
			<td bgcolor="#FFFFFF" colspan="3">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="30" align="right"><bean:message bundle="system"
						key="group.qzxxxx" /></td>
					<td align="left"></td>
					<td height="30" align="right"></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td width="20%" height="30" align="right"><bean:message
						bundle="system" key="group.qzmc" />:<font color="#ff0000">*</font></td>
					<td width="70%" align="left" colspan="3"><input type="text"
						name="GName" value="" style="width: 200px;"> <input
						type="checkbox" name="shareFlag" value="1"><bean:message
						bundle="system" key="group.sfgk" /> <bean:message bundle="system"
						key="group.qzlb" />: <peis:selectlist name="GType" sql="AL_SYSTEM_0102"
						extendProperty="class='mainSelect'" /> <html:hidden
						property="GType" disabled="true" /></td>
				</tr>
				<tr>
					<td align="right"><bean:message bundle="system" key="group.bz" />:</td>
					<td align="left"><textarea name="remark" cols="45" rows="5"></textarea>
					</td>
					<td align="right">&nbsp;</td>
					<td align="left">&nbsp;</td>
				</tr>
				<tr>
					<td align="right">&nbsp;</td>
					<td align="left">&nbsp;</td>
					<td align="right">&nbsp;</td>
					<td align="left">&nbsp;</td>
				</tr>
				<tr>
					<td align="right">&nbsp;</td>
					<td align="left"><input type="button" name="queding2"
						value='<bean:message bundle="system" key="group.button.ok"/>'
						class="input_ok" onClick="importToNewGroup(this.form)" /> &nbsp; <input
						type="button" name="cancel2"
						value='<bean:message bundle="system" key="group.button.cancel"/>'
						class="input_ok" onClick="window.close()" /></td>
				</tr>
			</table>
			</td>
			<td class="contentRight"></td>
		</tr>
	</table>
	</div>
	<div id="divGroupList"
		style="position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;">
	<div class="equip_title">
	<ul class="e_title">
		<li class="e_titleon"><a href="javascript:showGroupList();"
			onFocus="blur()"><bean:message bundle="system"
			key="group.tab.add.exist" /></a></li>
		<li class="e_titleoff"><a href="javascript:showAddGroup();"
			onFocus="blur()"><bean:message bundle="system"
			key="group.tab.add.new" /></a></li>
	</ul>
	</div>
	<table width="98%" align="center" border="0" cellpadding="0"
		cellspacing="1">
		<tr>
			<td width="66" class="label"></td>
			<td width="120" class="dom"></td>
			<td></td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td class="contentLeft"></td>
			<td height="20" colspan="3"><bean:message bundle="system"
				key="group.qzmc" />: <input type="text" name="GNameQuery" value="">
			<input type="submit"
				value="<bean:message bundle="system" key="button.cx"/>"
				class="input"></td>
			<td class="contentLeft"></td>
		</tr>

		<tr>
			<td class="contentLeft"></td>
			<td bgcolor="#FFFFFF" colspan="3">
			<div id="tableContainer" class="tableContainer"
				style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 150));">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<thead>
					<tr>
						<th><bean:message bundle="system" key="group.xh" /></th>
						<th><bean:message bundle="system" key="group.qzmc" /></th>
						<th><bean:message bundle="system" key="group.qzsx" /></th>
						<th><bean:message bundle="system" key="group.qzlb" /></th>
						<th><bean:message bundle="system" key="group.cjr" /></th>
					</tr>
				</thead>
				<logic:present name="PG_QUERY_RESULT">
					<logic:iterate id="datainfo" name="PG_QUERY_RESULT">
						<tr class="trmainstyle"
							onclick="selectRow('<bean:write name="datainfo" property="col1"/>', this)"
							style="cursor:pointer;">
							<td><bean:write name="datainfo" property="rowNo" /></td>
							<td><bean:write name="datainfo" property="col2" /></td>
							<td><bean:write name="datainfo" property="col4" /></td>
							<td><bean:write name="datainfo" property="col6" /></td>
							<td><bean:write name="datainfo" property="col8" /></td>
						</tr>
					</logic:iterate>
				</logic:present>
			</table>
			</div>
			<div class="pageContainer"><peis:pagination
				sql="AL_SYSTEM_0100" queryActionPath="importGroupQueryAction"
				allowRowsChange="true" /></div>
			<table align="center">
				<tr>
					<td width="100%" height="30" align="center"><input
						type="button" name="queding2"
						value='<bean:message bundle="system" key="group.button.ok"/>'
						class="input_ok" / onClick="importToExistGroup(this.form)">
					&nbsp; <input type="button" name="cancel2"
						value='<bean:message bundle="system" key="group.button.cancel"/>'
						class="input_ok" onClick="window.close()" /></td>
				</tr>
			</table>
			</td>
			<td class="contentRight"></td>
		</tr>
	</table>
	</div>

</html:form>
<iframe name="hideframe" src="" scrolling="no" width="0" height="0"
	frameborder="0"> </iframe>
</body>
</html>
