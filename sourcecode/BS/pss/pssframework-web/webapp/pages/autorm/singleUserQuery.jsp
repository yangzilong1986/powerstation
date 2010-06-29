<% /** 单用户查询页面  */  %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<%@page import="peis.util.StringHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="peis.component.reportform.service.FieldTranslationService"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.Format"%>
<%@page import="java.util.Date"%><html>
<head>
<title>监测用户详细信息</title>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/prototype/prototype.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/multiframe.js"></script>
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">
<link href="<peis:contextPath />/css/mainframe.css" rel="stylesheet" type="text/css">
<script>

/* 总控窗体 */
var _mainControllerWindow;
var custId = '<%=request.getAttribute("custId")%>';
var custNo = '<%=request.getAttribute("custNo")%>';
var orgNo = '<%=request.getAttribute("orgNo")%>';

_mainControllerWindow = getControllerWindow(window);
document.observe('dom:loaded', function() {
	_mainControllerWindow = getControllerWindow(window);
	if (!_mainControllerWindow) {
		throw new Error('总控窗体未能找到！');
	}
	if(!custNo.blank() && custNo != 'null' ){
		_mainControllerWindow.setCurrCustNo(custNo);
		_mainControllerWindow.setCurrOrgNo(orgNo);
	}else{
		_mainControllerWindow.setCurrCustNo("");
		_mainControllerWindow.setCurrOrgNo("");
	}
	
});
</script>
<%!private final static  Format DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
Object[] baseInfo = new Object[14];
%>
<%
	/** 获得电表信息 */
	List mpInfos = (List)request.getAttribute("MP_INFO");

	/** 获得终端信息 */
	List tmInfos = (List)request.getAttribute("TM_INFO");
	
	/** 获得终端运行信息 */
	List equipRunInfos = (List)request.getAttribute("EQUIP_RUN_INFO");
	
	try{
		for(int i=0;i<baseInfo.length;i++){
			baseInfo[i] = "";
		}
		if(request.getAttribute("BASE_INFO")!=null)
			baseInfo = (Object[]) request.getAttribute("BASE_INFO");
	}catch(Exception e){
		e.printStackTrace();
	}
%>


</head>
<body id="body">
	<form action="<peis:contextPath />/singleUserQuery.do" method="post">
		户号：<input value="<%=(request.getParameter("custNo")==null)?"":request.getParameter("custNo")%>" size="12" name="custNo">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     	户名：<input value="<%=(request.getParameter("custName")==null)?"":request.getParameter("custName")%>" size="25" name="custName">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     	<input type="hidden" value="query" name="action">
     	<input class="input2" type="submit" value="查询">
    </form>
	<div id="main">
      <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="66" class="label">户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
            <td width="120" class="dom"><%=baseInfo[1]%></td>
            <td width="66" class="label">户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
            <td width="220" class="dom"><%=baseInfo[2]%></td>
            <td width="66" class="label">供电单位：</td>
            <td width="120" class="dom"><%=baseInfo[3]%></td>
          </tr>
          <tr>
            <td width="66" class="label">行&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业：</td>
            <td width="120" class="dom"><%=baseInfo[4]%></td>
            <td width="66" class="label">用电类别：</td>
            <td width="220" class="dom"><%=baseInfo[5]==""?"":FieldTranslationService.getValue(FieldTranslationService.TABLE_NAME_SCODE,"ELEC_APP_TYPE",baseInfo[5].toString())%></td>
            <td width="66" class="label">运行容量：</td>
            <td width="120" class="dom"><%=baseInfo[6]==null?"":baseInfo[6]%></td>
          </tr>
          <tr>
            <td width="66" class="label">抄表区号：</td>
            <td width="120" class="dom"><%=baseInfo[7]==null?"":baseInfo[7]%></td>
            <td width="66" class="label">抄&nbsp;&nbsp;表&nbsp;&nbsp;日：</td>
            <td width="220" class="dom"><%=baseInfo[8]%></td>
            <td width="66" class="label">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</td>
            <td width="120" class="dom"><%=baseInfo[9]%></td>
          </tr>
              <tr>
            <td width="66" class="label">联&nbsp;&nbsp;系&nbsp;&nbsp;人：</td>
            <td width="120" class="dom"><%=baseInfo[10]%></td>
            <td width="66" class="label">联系电话：</td>
            <td width="220" class="dom"><%=baseInfo[11]%></td>
            <td width="66" class="label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
            <td width="120" class="dom"><%=baseInfo[12]==null?"":baseInfo[12]%></td>
          </tr>
        </table>
        </div>	 
		    <!--电表区域-->
		    <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		        <tr>
		        <td class="functionLabel" width=100>电表信息</td>
		      </tr>
		    </table>
		    
		    <table width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
		      <tr>
		        <!--标题头-->
		        <th>序号</th>
		        <th>表号</th>
		        <th>测量点名称</th>
		        <th>测量点序号</th>
		        <th>CT</th>
		        <th>PT</th>
		        <th>表类型</th>
				<th>生产厂家</th>
				<th>通讯地址</th>
				<th>关联终端</th>
				<th>表规约</th>
				<th>出厂编号</th>
		      </tr>
		      	<%
		      	if(mpInfos!=null && mpInfos.size()>0){
		      		int index = 1;
		      		for(Iterator i = mpInfos.iterator();i.hasNext();){
		      			Object[] mpInfo = (Object[])i.next();
			      		out.print("<tr style=\"cursor:hand;\" id=\"meterRow\">");
			      		out.print("<td>"+ index++ +"</td>");
			      		out.print("<td>"+(mpInfo[0]==null?"":mpInfo[0])+"</td>");
			      		out.print("<td>"+(mpInfo[1]==null?"":mpInfo[1])+"</td>");
			      		out.print("<td>"+(mpInfo[2]==null?"":mpInfo[2])+"</td>");
			      		out.print("<td>"+(mpInfo[3]==null?"":mpInfo[3])+"</td>");
			      		out.print("<td>"+(mpInfo[4]==null?"":mpInfo[4])+"</td>");
			      		out.print("<td>"+(mpInfo[5]==null?"":FieldTranslationService.getValue(FieldTranslationService.TABLE_NAME_SCODE,"METER_TYPE",mpInfo[5].toString()))+"</td>");
			      		out.print("<td>"+(mpInfo[6]==null?"":FieldTranslationService.getValue(FieldTranslationService.TABLE_NAME_SCODE,"MADE_FAC",mpInfo[6].toString()))+"</td>");
			      		out.print("<td>"+(mpInfo[7]==null?"":mpInfo[7])+"</td>");
			      		out.print("<td>"+(mpInfo[8]==null?"":mpInfo[8])+"</td>");
			      		out.print("<td>"+(mpInfo[9]==null?"":FieldTranslationService.getValue(FieldTranslationService.TABLE_NAME_SCODE,"PROTOCOL_METER",mpInfo[9].toString()))+"</td>");
			      		out.print("<td>"+(mpInfo[10]==null?"":mpInfo[10])+"</td></tr>");
		      		}
		      	}
		      	%>
		    </table>
		    
		    <!--终端信息-->
		    <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		        <tr>
		        <td class="functionLabel" width=100>终端信息</td>
		      </tr>
		    </table>
		    <table width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
		      <tr class="trheadstyle">
		        <!--标题头-->
		        <th>资产号</th>
		        <th>终端地址</th>
		        <th>出厂编号</th>
		        <th>生产厂家</th>
		        <th>当前状态</th>
		        <th>通讯方式</th>
		        <th>电话号码</th>
		        <th>设备规约</th>
		        <th>型号</th>
		        <th>安装日期</th>
		      </tr>
		     	<%
		      	if(tmInfos!=null){
		      		int index = 1;
		      		for(Iterator i = tmInfos.iterator();i.hasNext();){
		      			Object[] tmInfo = (Object[])i.next();
		      			out.print("<tr style=\"cursor:hand;\" id=\"meterRow\">");
			      		out.print("<td>"+(tmInfo[1]==null?"":tmInfo[1])+"</td>");
			      		out.print("<td>"+(tmInfo[2]==null?"":tmInfo[2])+"</td>");
			      		out.print("<td>"+(tmInfo[3]==null?"":tmInfo[3])+"</td>");
			      		out.print("<td>"+(tmInfo[4]==null?"":FieldTranslationService.getValue(FieldTranslationService.TABLE_NAME_SCODE,"MADE_FAC",tmInfo[4].toString()))+"</td>");
			      		out.print("<td>"+(tmInfo[5]==null?"":FieldTranslationService.getValue(FieldTranslationService.TABLE_NAME_SCODE,"CUR_STATUS",tmInfo[5].toString()))+"</td>");
			      		out.print("<td>"+(tmInfo[6]==null?"":FieldTranslationService.getValue(FieldTranslationService.TABLE_NAME_SCODE,"COMM_MODE",tmInfo[6].toString()))+"</td>");
			      		out.print("<td>"+(tmInfo[7]==null?"":tmInfo[7])+"</td>");
			      		out.print("<td>"+(tmInfo[8]==null?"":FieldTranslationService.getValue(FieldTranslationService.TABLE_NAME_SCODE,"PROTOCOL_TERM",tmInfo[8].toString()))+"</td>");
			      		out.print("<td>"+(tmInfo[9]==null?"":FieldTranslationService.getValue(FieldTranslationService.TABLE_NAME_SCODE,"MODEL_CODE",tmInfo[9].toString()))+"</td>");
			      		out.print("<td>"+(tmInfo[10]==null?"":DATE_FORMAT.format(tmInfo[10]))+"</td></tr>");
		      		}
		      	}%>
		    </table>	
		    <!--总加组区域-->
		    <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		        <tr>
		        <td class="functionLabel" width=100>
		           设备运行信息
		        </td>
		        
		      </tr>
		    </table>
		    
		    <table width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
		      <tr>
		        <!--标题头-->
		        <th >终端逻辑地址</th>
		        <th>运行状态</th>
		        <th width="300">运行描述</th>
		        <th width="120">故障持续天数</th>
		        <th>最近通讯时间</th>
		        <th>昨日在线率</th>
		        <th>昨日总流量(kb)</th>
		      </tr>
		      <logic:present name="EQUIP_RUN_INFO" >
		      <logic:iterate id="dataList" name="EQUIP_RUN_INFO">
		      	<tr style="cursor:hand;" id="meterRow">
		      		<logic:present name="dataList" >
	      			<logic:iterate id="datas" name="dataList" offset="1">
	      				<td align="center">
	      					<logic:present name="datas" >
	      						<bean:write name="datas"/>
	      					</logic:present>
	      				</td>
	      			</logic:iterate>
	      			</logic:present>
		      	</tr>
		      </logic:iterate>
		      	</logic:present>
		    </table>

</div>
<iframe name="hideFrame" src="" frameborder=0 width=0 height=0></iframe>
</body>
</html>