 <%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>




<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>线路编辑</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
	 <script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
  </head>
  <body>
  <html:form action="/archive/lineAction" onsubmit="return validatelineForm(this)" target="hideframe">
  	<html:hidden property="action" />
  	<input type="hidden" name="sublineFlag_old" value="<bean:write name="lineForm" property="sublineFlag"/>">
   <table width="98%" border=0 cellpadding=0 cellspacing=0 align=center class="e_detail_t">
    <tr>
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tleft.gif" width=10 height=28></td>
    <td width="100%" background="<peis:contextPath/>/img/e_detail_tmain.gif" class="functionTitle">线路编辑</td>  
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tright.gif" width=10 height=28></td>
    </tr>
   <tr align="center">
    <td class="contentLeft">　</td>
    <td>
    	 <table width="98%" align="center" border="0" cellpadding="0"cellspacing="1" >
    		<tr>
    			<td width="18%">线路编号（<font color="#ff0000">*</font>）：</td>
    			<html:hidden property="lineId"/>
    			<input type="hidden" name="oldlineNo" value='<bean:write name="lineForm" property="lineNo"/>'/>
    			<td width="38%"><html:text property="lineNo"/>
    			<logic:equal name="lineForm" property="action" value="save">
    			</logic:equal>
    			</td>
    			<td width="18%">线路名称（<font color="#ff0000">*</font>）：</td>
    			<td width="38%"><html:text property="lineName"/></td>
    		</tr>
    		<tr>
    			<td>电压等级：</td>
    			<td><peis:selectlist name="voltGrade" sql="AL_ARCHIVE_0013" extendProperty="class='mainSelect'" value=""/></td>
    			<td>线路状态：</td>
    			<td><peis:selectlist name="lineStatus" sql="AL_ARCHIVE_0014" extendProperty="class='mainSelect'" value=""/></td>
    		</tr>
    		<tr>
    			<td>所属单位:</td>
    			<td><peis:selectlist name="orgNo" sql="SL_COMMON_0001" extendProperty="class='mainSelect'" value=""/></td>
    			<td>线损阈值（<font color="#ff0000">*</font>）：</td>
    			<td><html:text property="lineThreshold"/></td>
    		</tr>
    		<tr>
    			<td>线路类型:</td>
    			<td>
    			<html:select   property="sublineFlag">  
                    <html:option   value= "1">支线</html:option>   
                    <html:option   value= "0">主线</html:option>   
                    </html:select> 
    			</td>
    			<td>上级线路：</td>
    			<td><peis:selectlist name="upperLine" sql="AL_ARCHIVE_0016" extendProperty="class='mainSelect'" value=""/> </td>
    		</tr>
    	     <tr>
    			<td>所属变电站:</td>
    			<td><peis:selectlist name="subsId" sql="AL_ARCHIVE_0015" extendProperty="class='mainSelect'" value=""/></td>
    			<td></td>
    			<td></td>
    		</tr>
    		<tr>
    			<td>备注:</td>
    		    <td align="left" colspan="3"><html:textarea   property="remark" cols="50" rows="6"> <bean:write name="lineForm" property="remark" /></html:textarea></td>
    		</tr>
    		<tr>
    		<td  align="center" colspan="3">
    		<html:submit styleClass="input_ok"><bean:message bundle="system" key="button.qd"/></html:submit>
    		<input type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' class="input_ok" onclick="window.close()"/>
    		</td>
    		</tr>
    	</table>
 	</td>
     <td class="contentRight">　</td>
	</tr>
    <tr valign="middle" bgcolor="#FFFFFF">
    <td ><img src="<peis:contextPath/>/img/e_detail_bleft.gif" width=10 height=27></td>
    <td align="right" background="<peis:contextPath/>/img/e_detail_bmain.gif">&nbsp;</td>
    <td ><img src="<peis:contextPath/>/img/e_detail_bright.gif" width=10 height=27></td>
    </tr>
    </table>
    <iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
    </html:form>
     <html:javascript formName="lineForm" />
  </body>
</html>
