<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<title><bean:message bundle="system" key="notice.ggxx"/></title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<link href="<peis:contextPath/>/css/style.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
</script>
</head>

<body>
<html:form action="/noticeAction" onsubmit="return submitDisposal();" method="post" target="hideframe">
<input type="hidden" name="action" value="saveOrUpdate"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="contentLeft"></td>
    <td class="switchLabelTitle" colspan="3" style="border-bottom: solid 1px #97BDFB;">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
      	<html:hidden property="noticeId"/>
      	<html:hidden property="publisher"/>
      	<html:hidden property="publishDate"/>
        <tr>
          <td width="15%"><bean:message bundle="system" key="notice.ggzt"/>:</td>
         <td><bean:write name="noticeForm" property="noticeSubj"/></td>
        </tr>
        <tr>
          <td width="15%"><bean:message bundle="system" key="notice.ggnr"/>:</td>
         <td ><bean:write name="noticeForm" property="content" /></td>
        </tr>
         <tr>
          <td width="15%"><bean:message bundle="system" key="notice.yxsj"/>:</td>
         <td ><bean:write property="validdate"  name="noticeForm"/></td>
        </tr>
      </table>
    </td>
    <td class="contentRight"></td>
  </tr>
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
          <td width="100%" height="30" colspan="2" align="center">
            <input class="input1" type="button" name="queding2" value='<bean:message bundle="system" key="button.qd"/>' onclick="top.GB_hide();"/> &nbsp;
            <input class="input1" type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' onclick="top.GB_hide();"/>
          </td>
        </tr>
      </table>
    </td>
    <td class="contentRight"></td>
  </tr>
</table>
</html:form>
<iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
</body>

</html>
