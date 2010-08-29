<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/../common/taglib.jsp"%>
<!-- 采集器信息 -->
<div class="tab_con" style="height: expression((( document.documentElement.clientHeight || document.body.clientHeight) -50 ) );">
 <div class="data2">
  <span>采集器列表</span>
  <h1>
   <a href="#" onclick="openCollector(); return false;"><img src="<peis:contextPath/>/img/bt_add.png" width="19" height="19" class="mgt5" />
   </a>
  </h1>
 </div>
 <div class="data2_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
   <thead>
    <tr>
     <th>序号</th>
     <th>资产号</th>
     <th>采集器地址</th>
     <th>厂家</th>
     <th>型号</th>
     <th>操作</th>
    </tr>
   </thead>
   <tbody align="center">
    <logic:present name="PG_QUERY_RESULT">
     <logic:iterate id="gm" name="PG_QUERY_RESULT">
      <tr onclick="selectSingleRow(this)" style="cursor: pointer;">
       <td>
        <bean:write name="gm" property="rowNo" />
       </td>
       <td>
        <bean:write name="gm" property="col1" />
       </td>
       <td>
        <bean:write name="gm" property="col2" />
       </td>
       <td>
        <bean:write name="gm" property="col3" />
       </td>
       <td>
        <bean:write name="gm" property="col4" />
       </td>
       <td>
        <a href="javascript:showGm('<bean:write name="gm" property="col5"/>');"><bean:message key="global.edit" />
        </a>|
        <a href="javascript:delteGm('<bean:write name="gm" property="col5"/>')"><bean:message key="global.delete" />
        </a>
       </td>
      </tr>
     </logic:iterate>
    </logic:present>
   </tbody>
  </table>
 </div>
</div>
<input type="hidden" name="termId" />
<input type="hidden" name="gpId" />
<html:form action="/archive/gmList5Query.do">
 <html:hidden property="action" value="normalMode" />
 <html:hidden property="pageType" value="page" />
 <html:hidden property="sqlCode" value="AL_ARCHIVE_0023" />
 <div class="pageContainer">
  <peis:paging actionForm="archiveQueryForm" rowsChange="true" />
 </div>
</html:form>