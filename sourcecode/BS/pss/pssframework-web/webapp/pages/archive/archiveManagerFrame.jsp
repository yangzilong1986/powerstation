<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>

<html:html lang="true">
<head>
  <html:base />
  <title>archiveManagerFrame</title>
  <meta http-equiv="Content-Language" content="zh-cn">
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
  <script type="text/javascript">
  var contextPath = "<peis:contextPath/>";
  
  /**
   * objectType : 对象类型
   * objectId : 对象标识
   * objectName : 对象名称
   * orgType : 单位类型
   */
  function nodeEvent(objectType, objectId, objectName, orgType) {
      if("13" == objectType || "1" == objectType || "2" == objectType) {    //所选节点对象类型为部门、专变用户或配变台区
          archive.document.getElementsByName("objectId")[0].value = objectId;
          archive.document.getElementsByName("objectType")[0].value = objectType;
          archive.query("tree");
      }
  }
  
  /**
   * modeId : 模型编号
   */
  function changeTreeMode(modeId) {
      document.getElementsByName("DTree")[0].src = contextPath + "/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=" + modeId + "&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype=#null";
  }
  </script>
</head>
<body>
  <div id="body">
    <div class="tab">
      <ul>
        <li class="tab_on">
          <bean:message bundle="archive" key="archive.dagl" />
        </li>
        <li class="clear"></li>
      </ul>
    </div>
    <div id="main" style="height: expression(((document.documentElement.clientHeight || document.body.clientHeight) - 48));">
      <table border="0" cellpadding="0" cellspacing="0" style="width: 100%; height: 100%;">
        <tr>
          <td id="tree" width="200" height="100%" style="border-right: 1px solid #085952;"">
            <iframe width="100%" height="100%" frameborder="0" name="DTree" src="<peis:contextPath/>/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=99991002&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype=#null"></iframe>
          </td>
          <td height="100%">
            <iframe width="100%" height="100%" frameborder="0" name="archive" src="<peis:contextPath/>/archive/gotoArchiveAction.do?action=archive"></iframe>
          </td>
        </tr>
      </table>
    </div>
  </div>
</body>
</html:html>
