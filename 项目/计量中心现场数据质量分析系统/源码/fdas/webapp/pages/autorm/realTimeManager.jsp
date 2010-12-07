<%@ page contentType="text/html; charset=utf-8" %>





<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<title>实时抄表</title>
<link href="<hualong:contextPath/>/style/style.css" rel="stylesheet" type="text/css">
</head>

  <body>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" align=center>
    <tr>
    <td width="20%">
   <!--   <iframe height="400" frameborder="0" width="100%" name="DTree" src="<peis:contextPath/>/gotoArchiveAction.do?action=dTree" ></iframe>-->
   <iframe height="400" frameborder="0" width="100%" name="DTree" src="<peis:contextPath/>/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=99991001&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype=#null" ></iframe>
    </td>
    <td width="80%">
    <iframe  height="400" frameborder="0" width="100%" name="archive" src="<peis:contextPath/>/jsp/autorm/realTimeReading.jsp" ></iframe>
    </td>
    </tr>
    </table>
  </body>

</html>
