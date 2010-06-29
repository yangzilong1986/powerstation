<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据检查</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css"/>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script>
<%
	List dataList = (List)request.getAttribute("DATA_LIST");
%>
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

var info = [];
var data = {};
function openModView(gpId,dDate,dataType,dataDens,chkType){
	data['gpId'] = gpId;
	data['dDate'] = dDate;
	data['dataType'] = dataType;
	data['dataDens'] = dataDens;
	data['chkType'] = chkType;

	$('#DATA_TBODY tr').each(function(){
		if($('td:eq(0)',$(this)).attr("gpId") == gpId)
		$(this).children().each(function(i){
			if(i == 11)
				return false;
			info[i] = $(this).html();
		});
	});
	windowPopup1(contextPath+"/jsp/autorm/elecModifyView.jsp",1000,500);
}

</script>
</head>
<body>
 <html:form styleId="form1" action="/do/autorm/DataCheckQuery" method="get">
 <html:hidden property="action" value="query"/>	
 <html:hidden property="page"/>
 <html:hidden property="pageSize"/>
  <div id="body">
    <div id="main">
      <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td class="label">电量类别：</td>
            <td class="dom">
           		<html:select property="dataType">
           			<html:option value="">所有</html:option>
					<html:optionsCollection name="dataTypeList" label="label" value="value"/>
				</html:select>
            </td>
            <td class="label">异常类别：</td>
            <td class="dom">
              <html:select property="chkType">
           			<html:option value="">所有</html:option>
					<html:optionsCollection name="chkTypeList" label="label" value="value"/>
				</html:select>
            </td>
            <td class="label">数据密度：</td>
            <td class="dom">
              <html:select property="dataDens">
           			<html:option value="">所有</html:option>
					<html:optionsCollection name="dataDensList" label="label" value="value"/>
			  </html:select>
            </td>
            <td class="label">状态：</td>
            <td class="dom">
            	<html:select property="status">
                <html:option value="">所有</html:option>
                <html:option value="1">已修正</html:option>
                <html:option value="0">未修正</html:option>
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
                <th width="10%">户号</th>
                <th width="15%">户名</th>
                <th width="10%">表号</th>
                <th width="8%">日期</th>
                <th width="5%">总</th>
                <th width="5%">尖</th>
                <th width="5%">峰</th>
                <th width="5%">平</th>
                <th width="5%">谷</th>
                <th width="15%">异常描述</th>
                <th width="6%">操作</th>
              </tr></thead>
              <tbody id="DATA_TBODY">
              	<%
		      	if(dataList!=null){
		      		int sn = Integer.valueOf((String)request.getAttribute("PAGE_NUM")).intValue();
		      		for(Iterator i = dataList.iterator();i.hasNext();){
		      			Object[] datas = (Object[])i.next();
		      			out.print("<tr onClick='selectSingleRow(this);' style='cursor:pointer;'>");
		      			out.print("<td gpId="+((datas[0]!=null)?datas[0]:"")+">"+sn+"</td>");
			      		out.print("<td>"+((datas[5]!=null)?datas[5]:"")+"</td>");
			      		out.print("<td>"+((datas[6]!=null)?datas[6]:"")+"</td>");
			      		out.print("<td>"+((datas[7]!=null)?datas[7]:"")+"</td>");
			      		out.print("<td>"+((datas[1]!=null)?datas[1]:"")+"</td>");
			      		if(datas[9] != null){
			      			Object[] elecDatas = (Object[])datas[9];
			      			for(int k = 0;k < 5; k++){
			      				out.print("<td>"+((elecDatas[k]!=null)?elecDatas[k]:"")+"</td>");
			      			}
			      		}else
			      			out.print("<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>");
			      		
			      		out.print("<td>"+((datas[8]!=null)?datas[8]:"")+"</td>");
			      		out.print("<td><a href=\"javascript:openModView('"+datas[0]+"','"+datas[1]+"','"+datas[2]+"','"+datas[3]+"','"+datas[4]+"')\">修正</a></td><tr>");
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
