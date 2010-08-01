<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>

<html>
<head>
<title>通用群组管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
<script type="text/javascript" language="javascript">
	var contextPath = "<peis:contextPath/>";
	var selectedGroupID = "";   
	function selectRow(groupID, oRow) {
    	selectedGroupID = groupID;
   		selectSingleRow(oRow);
	}
 　　
	//新增群组
	function newGroup() {
        var strGroupType = document.all.groupType.value;
        var strObjIdList = document.all.objectIdList.value;
        //alert(strGroupType + "# " + strObjIdList);
    	var params = {"groupType": strGroupType,
                      "objectIdList": strObjIdList
                     };
        var strUrl = contextPath + "/system/groupAction.do?action=createGroup&" + jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
        window.location.href = strUrl;
	}
 　　
    //加入群组
    function addToGroup() {
        if(selectedGroupID == ""){
            alert("请选择一个群组加入!");
            return;
        }
        
        var params = {"groupId": selectedGroupID,
                      "groupType": document.all.groupType.value,
                      "objectIdList": document.all.objectIdList.value
                     };
        var strUrl = contextPath + "/system/groupAction.do?action=addUserToGroup&" + jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
        $.get(strUrl, function(result){
            if("success" == result){
                alert("加入群组成功!");
                parent.GB_hide();
            }
            else {
                alert("加入群组失败!");
            }
        });
    }
 	</script>
</head>

<body>
  <html:form action="/system/commonGroupQuery">
	<html:hidden property="action" value="normalMode" />
	<html:hidden property="sqlCode" value="AL_SYSTEM_0200" />
    <html:hidden property="groupType"/>
    <html:hidden property="objectIdList"/>
    <div class="pad10">
      <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
        	<td width="70" class="label"><bean:message bundle="system" key="group.qzmc" />：</td>
        	<td width="120" class="dom"><html:text property="groupName"/></td>
        	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <html:submit styleClass="input1"><bean:message bundle="system" key="button.cx"/></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="button" name="add" class="input2" value='加入群组' onclick="addToGroup()" />
              <input type="button" name="new" class="input2" value='<bean:message bundle="system" key="group.button.xjqz"/>' onclick="newGroup()" />
            </td>
          </tr>
        </table>
      </div>
	  <div class="content">
	    <div id="cont_1">
          <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 100));">
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
            <peis:paging actionForm="commonGroupForm" rowsChange="true"/>
          </div>
  	    </div>
  	  </div>
    </div>
  </html:form>
</body>
</html>
