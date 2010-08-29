
<%@include file="../../commons/taglibs.jsp"%>



<%@page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title><bean:message bundle="archive" key="archive.isTermSw.info"/></title>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/common-ajax.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/dwr/engine.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/dwr/util.js"></script>
</head>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
function editCheck() {
    var swName = document.getElementById("swName").value;
    var capacity = document.getElementById("capacity").value;
    if(swName == "") {
        alert("开关名称不能为空！");
        return false;
    }
    var integerPatern = /^[1-9]\d*$/;
    if(capacity == "") {
        alert("开关容量不能为空！");
        return false;
    }
    if(capacity != "" && !integerPatern.exec(capacity)) {
        alert("开关容量必须为数字！");
        return false;
    }
    return true;
}
</script>
<body bgcolor="#f7f7ff"  style="overflow-x:hidden;overflow-y:hidden;">
<ul class="e_title">
    <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.edit.sw"/></a>
  </ul>
  <div class="user_add_table">
  <html:form action="/archive/termSwitchAction" method="post" onsubmit="return editCheck();" target="SwPage">
  <input type="hidden" name="action" value="saveOrUpdate">
  <peis:text type="hidden" name="tswId"/>
  <table width="96%" border="0" align="center"  cellpadding="0" cellspacing="0" >
    <tr>
      <td id="msg" colspan="6"></td>
    </tr>
    <tr>
    <td align="right" width="15%"><bean:message bundle="archive" key="archive.sw.name"/>:</td>
    <td width="35%"><peis:text type="text" name="swName" extendProperty="class='user_add_input'"/></td>
	<td align="right" width="15%"><bean:message bundle="archive" key="archive.SW_SN"/>:</td>
	<td width="35%">
      <select name="swSn" class="user_add_input">
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="6">6</option>
        <option value="7">7</option>
        <option value="8">8</option>
      </select>
      <script type="text/javascript">
      document.getElementById("swSn").value = "<peis:param type="PAGE" paramName="swSn"/>";
      </script>
    </td>
    </tr>
    <tr>
    <td align="right"><bean:message bundle="archive" key="archive.sw.turn"/>:</td>
	<td>
      <select name="swTurn" class="user_add_input">
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
        <option value="6">6</option>
        <option value="7">7</option>
        <option value="8">8</option>
      </select>
      <script type="text/javascript">
      document.getElementById("swTurn").value = "<peis:param type="PAGE" paramName="swTurn"/>";
      </script>
    </td>
	<td align="right"><bean:message bundle="archive" key="archive.sw.capa"/>:</td>
	<td><input class="user_add_input" type="text" name="capacity" value="<bean:write name='capacity' format='#'/>" ></td>
    </tr>
    <tr>
	<td align="right"><bean:message bundle="archive" key="archive.sw.attr"/>:</td>
	<td><peis:selectlist name="swAttr" sql="COMMON0019" extendProperty="class='user_add_input'"/></td>
	<td align="right"><bean:message bundle="archive" key="archive.belongTerm"/></td>
    <td>
	   <logic:present name="custId">
		 <peis:selectlist name="termId" sql="TERMINGP0001" extendProperty="class='user_add_input'"/>
	   </logic:present>
	</td>
	</tr>
	<tr>
	<td align="right"><bean:message bundle="archive" key="archive.sw.status"/></td>
	<td><peis:selectlist name="swStatus" sql="TERMSW0001" extendProperty="class='user_add_input'"/></td>
	<td align="right"></td>
	<td></td>
	</tr>
	<tr>
	<td align="right" colspan="6">
   		<input type="submit" name="Update" value="<bean:message key='global.save'/>" class="input_ok">
   		<input type="button" value="<bean:message key='global.cancel'/>" onclick="javascript:parent.window.close();"  class="input_ok">
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	</td>
   	</tr>
  </table>
  </html:form>
   </div>
<iframe name="SwPage" frameborder=0 width=100% height=100%></iframe>
</body>
</html>
