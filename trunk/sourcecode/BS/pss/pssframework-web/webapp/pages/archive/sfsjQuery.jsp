<%@ page contentType="text/html; charset=gb2312" %>




<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<title>������ݲ�ѯ</title>
<link href="<peis:contextPath />/style/style.css" rel="stylesheet" type="text/css">
</head>
<body class="functionpg" onload="Init()">
<html:form action="/yhxxQueryAction" method="post">
<html:hidden property="action" value="getQuery"/>
<table width="1400" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td height="5"></td>
  </tr>
  <tr>
    <td class="conditionTableStyle">
      <table width="57%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="10%" align="right" height="25"></td>
              <html:text property="hm"/>
              <html:text property="hh"/>
              <html:hidden property="sqlcode" value="11"/>
               <html:hidden property="pageType" value="allFir"/>
          <td width="25%" height="25" align="right">
            <input type="submit" value=" �� ѯ " class="bottonStyle_01"/>
          </td>
        </tr>
     </table>
    </td>
  </tr>
  <tr>
    <td height="5"></td>
  </tr>
  <tr>
    <td class="mainTableStyle">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="tablestyle">
        <tr align="center" class="trheadstyle">
          <td width="3%" rowspan="2">���</td>
          <td width="45%" colspan="8">�û���Ϣ</td>
          <td width="6%" rowspan="2">����ʱ��</td>
          <td width="25%" colspan="5">�����й�(kwh)</td>
          <td width="5%" rowspan="2">�����й���(kwh)</td>
          <td width="5%" rowspan="2">�����޹���(kvarh)</td>
          <td width="5%" rowspan="2">�����޹���(kvarh)</td>
          <td width="6%" rowspan="2">�����й�����(kw)</td>
        </tr>
        <tr align="center" class="trheadstyle">
          <td width="4%">����</td>
          <td width="3%">����</td>
          <td width="5%">����</td>
          <td width="10%">����</td>
          <td width="5%">�豸��ַ</td>
          <td width="5%">�豸״̬</td>
          <td width="5%">���״̬</td>
          <td width="8%">���ֺ�</td>
          <td>��</td>
          <td>��</td>
          <td>��</td>
          <td>ƽ</td>
          <td>��</td>
        </tr>
        <logic:present name="PG_QUERY_RESULT">
        <logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number" offset="1"> 
          <tr align="center" class="trmainstyle">
            <td height="20"><bean:write name="number"/></td>
            <td><bean:write name="datainfo" property="col2"/></td>
            <td><bean:write name="datainfo" property="col3"/></td>
            <td><bean:write name="datainfo" property="col4"/></td>
        </tr>
        </logic:iterate>
        <tr class="trbottomstyle">
          <td width="1300" colspan="19" align="center">
               <peis:pagination sql="11" queryActionPath="yhxxQueryAction.do" allowRowsChange="true"/>
               <html:hidden property="sqlcode" value="11"/>
            <html:hidden property="pageRows"/>
            <html:hidden property="pageType" value="all"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </td>
        </tr>
        </logic:present>
      </table>
    </td>
  </tr>
</table>
</html:form>
<iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
<script type="text/javascript" language="javascript">
hideframe.location = "about:blank";
</script>
</body>
</html>
