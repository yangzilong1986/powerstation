<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript" src="${ctx}/scripts/json2.js"></script>
<script type="text/javascript">
var inputText = ""; 
var inputLabel = "";
var excelFlag=0; //导出EXCEL标记 1:各费率电能示值 2：当前功率 3:电压电流4：实时数据
var otherDataFlag=0; //其他数据项标识  1：实时召测其他数据项 0：实时召测各费率电能示值，当前功率，电压电流，实时数据
var timeData; //其他数据项页面中的数据时间
var dataGap; //其他数据项页面中的数据间隔
var points; //其他数据项页面中的点数
var protocolNo;//其他数据项页面中的规约号

function inputTextStyle() {
    $('#objectNo').attr({style: "color: #aaaaaa;"});
    $('#objectNo').blur( function() {
        if(this.value == ''){
            this.value = inputText;
            $('#objectNo').attr({style: "color: #aaaaaa;"});
        }
    });
    $('#objectNo').focus( function() {
        if(this.value == inputText){
            this.value='';
            $('#objectNo').attr({style: "color: black;"});
        }
    });
}

function initInputText() {
    if($("input[name='sysObject'][checked]").val() == "1") {
        inputText = inputText1;
        inputLabel = inputLabel1;
    }
    else if($("input[name='sysObject'][checked]").val() == "2") {
        inputText = inputText2;
        inputLabel = inputLabel2;
    }
    else if($("input[name='sysObject'][checked]").val() == "3") {
        inputText = inputText3;
        inputLabel = inputLabel3;
    }
    else if($("input[name='sysObject'][checked]").val() == "4") {
        inputText = inputText4;
        inputLabel = inputLabel4;
    }
    $("#objectLabel").html(inputLabel);
    $("#label").val(inputText);
    $("#objectNo").val(inputText);
}

</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
<ul class=default id=electric_Con_1>
	<div><form:form name="/system/user" modelAttribute="pageRequest">
		<table border="0" cellpadding="0" cellspacing="0">
			<tr height="30px">
				<td width="100" class="green" align="right">单位：</td>
				<td width="120"><select name="orgId" style="width: 150px;">
					<c:forEach var="item" items="${orglist}">
						<option <c:if test="${item.orgId eq pageRequest.orgId}">selected</c:if> value="<c:out value="${item.orgId}"/>"><c:out
							value="${item.orgName}" /></option>
					</c:forEach>
				</select></td>
				<td><input class="btnbg4" type="submit" name="query" id="query" value="查询" /></td>
			</tr>
		</table>
	</form:form></div>
	<div id="bg" style="height: 30px; text-align: center;">
	<ul id=”datamenu_Option“ class="cb font1">
		<li class="curr" id=datamenu_Option_0 style="cursor: pointer;">客户一览</li>
	</ul>
	</div>
	<div 
		style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) -105));">
	<table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
		<thead class="tableHeader">
			<tr>
				<th style="width: 5px;"></th>
				<th style="width: 5px;" align="center"><input type="checkbox"
					onclick="setAllCheckboxState('ItemID',this.checked)"></th>
				<!-- 排序时为th增加sortColumn即可,new SimpleTable('sortColumns')会为tableHeader自动增加排序功能; -->
				<th sortColumn="objNo">姓名</th>
				<th sortColumn="objName">联系号码</th>
				<th sortColumn="objType">权限</th>
			</tr>
		</thead>
		<tbody class="tableBody" id="dataBody">
			<c:forEach items="${page.result}" var="item" varStatus="status">
				<tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
					<td>${page.thisPageFirstElementNumber + status.index}</td>
					<td><input type="checkbox" name="ItemID"
						value="<c:out value="${item.empNo}"/>"></td>
					<td><c:out value='${item.name}' />&nbsp;</td>
					<td><c:out value='${item.mobile}' />&nbsp;</td>
					<td>&nbsp;</td>
					</tr>
			</c:forEach>
		</tbody>
	</table>
	<simpletable:pageToolbar page="${page}"></simpletable:pageToolbar></div>
</ul>
</div>
<iframe id="resultframe" src="${ctx}/jsp/autorm/fastReadingAction.jsp" width="0" height="0" frameborder="0"></iframe>
</body>
<script type="text/javascript">
</script>
<script type="text/javascript">
		$(document).ready(function() {
			// 分页需要依赖的初始化动作
			window.simpleTable = new SimpleTable('model','${page.thisPageNumber}','${page.pageSize}','${pageRequest.sortColumns}');
		});
	</script>
</html>
