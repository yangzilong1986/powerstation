<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="java.text.SimpleDateFormat" %>

<%@page import="peis.component.reportform.service.FieldTranslationService"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据记录检查</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css"/>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script>
<%!private static final SimpleDateFormat DATE_FORMAT_P = new SimpleDateFormat("yyyy-MM-dd");%>
<%List dataList = (List)request.getAttribute("DATA_LIST");%>
var contextPath = '<peis:contextPath/>';


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

</script>
</head>
<body>
 <html:form styleId="form1" action="/do/autorm/DataCheckModQuery" method="get">
 <html:hidden property="action" value="query"/>	
 <html:hidden property="page"/>
 <html:hidden property="pageSize"/>
  <div id="body">
    <div id="main">
      <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td class="label">异常类别：</td>
            <td class="dom">
              <html:select property="chkType">
           			<html:option value="">所有</html:option>
					<html:optionsCollection name="chkTypeList" label="label" value="value"/>
				</html:select>
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
            <td colspan="3"></td>
          </tr>
        </table>
        <div class="t_botton">
          <div class="t_right">
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
                <th width="8%">表号</th>
                <th width="8%">异常类型</th>
                <th width="25%">修正前内容</th>
                <th width="25%">修正后内容</th>
                <th width="5%">数据时间</th>
                <th width="5%">修正时间</th>
                <th width="5%">修正人</th>
              </tr></thead>
              <tbody id="DATA_TBODY">
              	<%
		      	if(dataList!=null){
		      		int sn = Integer.valueOf((String)request.getAttribute("PAGE_NUM")).intValue();
		      		for(Iterator i = dataList.iterator();i.hasNext();){
		      			Object[] datas = (Object[])i.next();
		      			out.print("<tr onClick='selectSingleRow(this);' style='cursor:pointer;'>");
		      			out.print("<td>"+sn+"</td>");
			      		out.print("<td>"+((datas[0]!=null)?datas[0]:"")+"</td>");
			      		out.print("<td>"+((datas[1]!=null)?FieldTranslationService.getValue(FieldTranslationService.TABLE_NAME_SCODE,"DC_CHK_TYPE",(String)datas[1]):"")+"</td>");
			      		out.print("<td>"+((datas[2]!=null)?datas[2]:"")+"</td>");
			      		out.print("<td>"+((datas[3]!=null)?datas[3]:"")+"</td>");
			      		out.print("<td>"+((datas[4]!=null)?datas[4]:"")+"</td>");
			      		out.print("<td>"+((datas[5]!=null)?DATE_FORMAT_P.format(datas[5]):"")+"</td>");
			      		out.print("<td>"+((datas[6]!=null)?datas[6]:"")+"</td><tr>");
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
