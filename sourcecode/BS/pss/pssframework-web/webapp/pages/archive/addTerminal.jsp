<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>终端</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
</head>
<body>
<div class="electric_lcon" id=electric_Con>
  <ul class="default" id="electric_Con_1" style="padding: 5px;">
    <div class="tab"><span>终端信息</span></div>
    <div class="da_con"><form:form action="/archive/terminalinfo" modelAttribute="traninfo">
      <form:hidden path="equipId" />
      <table border="0" cellpadding="0" cellspacing="0" align="center">
        <tr>
          <td width="15%" align="right" class="green"><font color="red">* </font>资产编号：</td>
          <td width="20%"><form:input path="assetNo" cssClass="required input2" maxlength="20" cssStyle="width:145px;" /></td>
          <td width="10%" align="right" class="green"><font color="red">* </font>逻辑地址：</td>
          <td width="20%"><form:input path="logicalAddr" cssClass="required input2" maxlength="20" cssStyle="width:145px;" /></td>
          <td width="10%" align="right" class="green">当前状态：</td>
          <td width="25%"><form:select path="curStatus" id="curStatus" itemLabel="name" itemValue="code" onchange="" items="${statuslist}" cssStyle="width:145px;" /></td>
        </tr>
        <tr>
          <td width="15%" align="right" class="green">设备规约：</td>
          <td width="20%"><form:select path="protocolNo" id="protocolNo" itemLabel="name" itemValue="code" onchange="" items="${protocollist}" cssStyle="width:145px;" /></td>
          <td width="10%" align="right" class="green">通讯方式：</td>
          <td width="20%"><form:select path="commMode" id="commMode" itemLabel="name" itemValue="code" onchange="" items="${commlist}" cssStyle="width:145px;" /></td>
          <td width="10%" align="right" class="green">相　　线：</td>
          <td width="25%"><form:select path="wiringMode" id="wiringMode" itemLabel="name" itemValue="code" onchange="" items="${wiringlist}" cssStyle="width:145px;" /></td>
        </tr>
        <tr>
          <td width="15%" align="right" class="green">设备厂家：</td>
          <td width="20%"><form:select path="madeFac" id="madeFac" itemLabel="name" itemValue="code" onchange="" items="${faclist}" cssStyle="width:145px;" /></td>
          <td width="10%" align="right" class="green">终端类型：</td>
          <td width="20%"><form:select path="termType" id="termType" itemLabel="name" itemValue="code" onchange="" items="${typelist}" cssStyle="width:145px;" /></td>
          <td width="10%" align="right" class="green">产　　权：</td>
          <td width="25%"><form:select path="pr" id="pr" itemLabel="name" itemValue="code" onchange="" items="${prlist}" cssStyle="width:145px;" /></td>
        </tr>
        <tr>
          <td width="15%" align="right" class="green">安装日期：</td>
          <td width="20%"><form:input path="installDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" cssClass="input_time" readonly="readonly" cssStyle="width:145px;"></form:input></td>
          <td width="10%" class="green">安装单位：</td>
          <td width="20%"><form:input path="instAddr"></form:input></td>
          <td width="10%" class="green">安装地址：</td>
          <td width="25%"><form:input path="instAddr"></form:input></td>
        </tr>
      </table>
    </form:form>
    <div class="guidePanel"><input type="button" id="save" value="保 存" onClick="save();" /></div>
    </div>
  </ul>
</div>
</body>
</html>
