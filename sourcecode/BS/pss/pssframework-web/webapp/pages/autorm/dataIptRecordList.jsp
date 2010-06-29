<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.text.SimpleDateFormat" %>

<%@page import="peis.component.reportform.service.FieldTranslationService"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据录入记录</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css"/>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/multiframe.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script>

<%!
private static final SimpleDateFormat DATE_FORMAT_P = new SimpleDateFormat("yyyy-MM-dd");
private static final SimpleDateFormat DATE_FORMAT_F1 = new SimpleDateFormat("yyyyMMdd");
private static final SimpleDateFormat DATE_FORMAT_F2 = new SimpleDateFormat("yyyyMM");
%>
<%List dataList = (List)request.getAttribute("DATA_LIST");%>
var contextPath = '<peis:contextPath/>';
/* 总控窗体 */
var _mainControllerWindow;


/** 分页所需要 函数 */
function getUrl(page){
	$("input[name=page]").val(page);
	$("form")[0].submit();
}

function changePageRows(obj){
   var pageRows = obj.value;
   $("input[name=pageSize]").val(pageRows);
   $("form")[0].submit();
}

function goTargetPage() {
   var pageNum=$("input[name=custompage]").val();
   $("input[name=page]").val(pageNum);
   $("form")[0].submit();
}


$(document).ready(function(){
	_mainControllerWindow = getControllerWindow(window);

	/** 临时处理问题  */
	$('#DATA_TBODY tr').each(function(){
		$('td:eq(5)',this).html()=="日"?$('td:eq(5)',this).html("日冻结"):$('td:eq(5)',this).html("月冻结");
	});
	
	
	
	$('#btn_edit').bind("click",gotoEdit);
	$('#btn_del').bind("click",gotoDel);
	
});



/** 去编辑页面  */
function gotoEdit(){
	var selectTr = $('#DATA_TBODY tr').filter(".selected");

	if(selectTr.length == 0){
		alert("请选中编辑行！");
		return;
	}
	
	/** 表号 */
	_mainControllerWindow._myPageInfo.assetNo = $('td:eq(3)',selectTr).html();

	/** 如果是月数据，自动补上01 */
	_mainControllerWindow._myPageInfo.dDate = $('td:eq(5)',selectTr).html()=="日冻结"?$('td:eq(4)',selectTr).html():$('td:eq(4)',selectTr).html()+"-01";
	_mainControllerWindow._myPageInfo.dataDens = $('td:eq(5)',selectTr).html()=="日冻结"?10:20;
	_mainControllerWindow.openCallbackFramePage(jQuery.param(_mainControllerWindow._myPageInfo));
	_mainControllerWindow.setMethod("update");
}



function gotoDel(){
	var selectTr = $('#DATA_TBODY tr').filter(".selected");

	if(selectTr.length == 0){
		alert("请选中要删除的行！");
		return;
	}
	
	if(!window.confirm("是否要删除这条记录！")){
		return;
	}
	var recId = $('td:eq(0)',selectTr).attr("recId");
	var url = contextPath+"/do/autorm/DataIptRecListQuery.do?action=delete&page=&recId=" + recId + "&r=" + parseInt(Math.random() * 1000);
	$.ajax({
	  type:"GET",
	  url:url,
	  success: function(msg){
	     alert( "删除成功！" );
	     window.location.reload();
	   }
	});
}


$(document).ready(function(){
	selectorTableTd(4);
});

function selectorTableTd(n){
	$("#DATA_TBODY td").each(function(i){
		if(i%11 == n){
			$(this).html(_to_data($(this).html()));
		}
	});
}

/** 数字转百分数 */
function _to_data(str){
	if(str.length == 6)
		return str.substr(0,4)+"-"+str.substr(4,2);
	else if(str.length == 8)
		return str.substr(0,4)+"-"+str.substr(4,2) + "-" + str.substr(6,2);
	return str;
}


</script>
</head>
<body>
 <html:form styleId="form1" action="/do/autorm/DataIptRecListQuery" method="get">
 <html:hidden property="action" value="query"/>	
 <html:hidden property="page"/>
 <html:hidden property="pageSize"/>
  <div id="body">
    <div id="main">
      <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td class="label">表号：</td>
            <td class="dom">
            	<html:text property="assetNo"/>
            </td>
         </tr>
         <tr>
            <td class="label">开始时间：</td>
            <td class="dom_date">
            	<html:text property="startDate" styleId="kssj"/>
              <a onClick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'kssj',null)">
                <img class="date" name="dimg1" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" alt="" />
              </a>
            </td>
            <td class="label">结束时间：</td>
            <td class="dom_date">
              <html:text property="endDate" styleId="jssj"/>
              <a onClick="event.cancelBubble=true;" href="javascript:showCalendar('dimg2',false,'jssj',null)">
                <img class="date" name="dimg2" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" alt="" />
              </a>
            </td>
            <td class="label">录入人：</td>
            <td class="dom_date">
            	 <html:select property="userNo">
           			<html:option value="">所有</html:option>
					<html:optionsCollection name="userList" label="label" value="value"/>
				</html:select>
            </td>
          </tr>
        </table>
        <div class="t_botton">
          <div class="t_right">
          	<input class="input" type="button" id="btn_edit" value="编  辑"/>
          	<input class="input" type="button" id="btn_del" value="删   除"/>
            <input class="input" type="submit" id="query" value="查 询"/>
          </div>
          <div class="clear"></div>
        </div>
      </div>
      <div class="content">
        <div id="cont_1">
          <div class="target">
            <ul>
              <li class="target_on"><a href="#" onClick="return false;">数据检查</a></li>
              <li class="clear"></li>
            </ul>
          </div>
          <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 218));">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <thead><tr>
                <th width="3%">序号</th>
                <th width="10%">户号</th>
                <th width="10%">户名</th>
                <th width="10%">表号</th>
                <th width="5%">数据时间</th>
                <th width="5%">数据类型</th>
                <th width="5%">正向有功总</th>
                <th width="5%">反向有功总</th>
                <th width="5%">正向无功总</th>
                <th width="5%">反向无功总</th>
                <th width="5%">追加人</th>
              </tr></thead>
              <tbody id="DATA_TBODY">
              	<%
		      	if(dataList!=null){
		      		int sn = Integer.valueOf((String)request.getAttribute("PAGE_NUM")).intValue();
		      		for(Iterator i = dataList.iterator();i.hasNext();){
		      			Object[] datas = (Object[])i.next();
		      			out.print("<tr onClick='selectSingleRow(this);' style='cursor:pointer;'>");
		      			out.print("<td recId ='"+datas[0]+"' >"+sn+"</td>");
			      		out.print("<td>"+((datas[1]!=null)?datas[1]:"")+"</td>");
			      		out.print("<td>"+((datas[2]!=null)?datas[2]:"")+"</td>");
			      		out.print("<td>"+((datas[3]!=null)?datas[3]:"")+"</td>");
			      		out.print("<td>"+((datas[4]!=null)?datas[4]:"")+"</td>");
			      		out.print("<td>"+((datas[5]!=null)?datas[5]:"")+"</td>");
			      		out.print("<td>"+((datas[6]!=null)?datas[6]:"")+"</td>");
			      		out.print("<td>"+((datas[7]!=null)?datas[7]:"")+"</td>");
			      		out.print("<td>"+((datas[8]!=null)?datas[8]:"")+"</td>");
			      		out.print("<td>"+((datas[9]!=null)?datas[9]:"")+"</td>");
			      		out.print("<td>"+((datas[10]!=null)?datas[10]:"")+"</td></tr>");
			      		sn++;
		      		}
		      	}%>
              </tbody>
            </table>
          </div>
          <div class="pageContainer">
          	<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center"><tr><td>
		    	<bean:write name="PAGE_NAV" ignore="true" filter="false"/></td></tr>
		    </table>
          </div>
        </div>
      </div>
    </div>
  </div>
  </html:form>
</body>
</html>
