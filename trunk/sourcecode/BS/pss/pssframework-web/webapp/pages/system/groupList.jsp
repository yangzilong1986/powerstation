<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>

<html>
<head>
<title>群组管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<peis:contextPath/>/css/mainframe.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
<script type="text/javascript" language="javascript">
	var contextPath = "<peis:contextPath/>";
	var sSelectedGroupID = "";   
	function selectRow(sGroupID, oRow) {
    	sSelectedGroupID = sGroupID;
   		selectSingleRow(oRow);
   		parent.document.frames["groupContent"].location.href=contextPath + "/system/groupAction.do?action=showGroup&mothed=edit&groupId="+sSelectedGroupID+"&random=" + Math.random();
	}	
 　　　 
	//新增群组
	function newGroup() {
    	var str_url = contextPath + "/system/groupAction.do?action=showGroup&mothed=add&random=" + Math.random();
   		parent.document.frames["groupContent"].location.href=str_url;
	}
  
	function fresh(){
		parent.content.location.href = contextPath + '/system/groupAction.do?action=getGroupList&sqlcode=AL_SYSTEM_0100&random=' + Math.random();
	}
 	</script>
</head>

<body>
  <html:form action="/system/groupAction">
	<html:hidden property="action" value="getGroupList" />
	<html:hidden property="pageType" value="page" />
	<html:hidden property="sqlcode" value="AL_SYSTEM_0100" />
    <div>
      <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
        	<td width="70" class="label"><bean:message bundle="system" key="group.qzmc" />：</td>
        	<td width="120" class="dom"><html:text property="groupName" /></td>
        	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <html:submit styleClass="input1"><bean:message bundle="system" key="button.cx"/></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="button" name="new" class="input2" value='<bean:message bundle="system" key="group.button.xjqz"/>' onclick="newGroup()" />
            </td>
          </tr>
        </table>
      </div>
	  <div class="content">
	    <div id="cont_1">
          <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 65));">
      	    <table border="0" cellpadding="0" cellspacing="0" width="100%">
      		  <thead>
        		<tr>
        		  <th width="10%"><bean:message bundle="system" key="group.xh" /></th>
        		  <th width="30%"><bean:message bundle="system" key="group.qzmc" /></th>
        		  <th width="20%"><bean:message bundle="system" key="group.qzsx" /></th>
        		  <th width="20%"><bean:message bundle="system" key="group.qzlb" /></th>
        		  <th width="20%"><bean:message bundle="system" key="group.cjr" /></th>
        		</tr>
         	  </thead>
        	  <tbody align="center">
                <logic:present name="PG_QUERY_RESULT">
        		  <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
        			<tr onclick="selectRow('<bean:write name="datainfo" property="col1"/>', this)" style="cursor:pointer;">
      				  <td><bean:write name="datainfo" property="rowNo"/></td>
      				  <td><bean:write name="datainfo" property="col2" /></td>
      				  <td><bean:write name="datainfo" property="col4" /></td>
      				  <td><bean:write name="datainfo" property="col6" /></td>
      				  <td><bean:write name="datainfo" property="col8" /></td>
      			    </tr>
        		  </logic:iterate>
        		</logic:present>
        	  </tbody>
            </table>
  	      </div>
  	      <div class="pageContainer">
            <peis:pagination sql="AL_SYSTEM_0100" queryActionPath="/system/groupQueryAction" allowRowsChange="true" />
          </div>
  	    </div>
  	  </div>
    </div>
  </html:form>
</body>
</html>
