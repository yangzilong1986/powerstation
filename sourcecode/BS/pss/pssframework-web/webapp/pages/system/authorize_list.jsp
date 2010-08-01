<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<html>
  <head>
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css"/>
  <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
  <script type="text/javascript">
     var contextPath = "<peis:contextPath/>";
     function init(){
     	var name = document.getElementById("orgNo").value;
     	var trs = document.getElementsByTagName("tr");
     	for(i=0; i < trs.length; i++){
     		if(trs[i].id == name){
     			trs[i].style.display = "";
     		}
     	}
     	setSnByOrgNo(name);
     }
     
     function setSnByOrgNo(orgNo){
     	var sn = 1;
     	var sns = document.getElementsByName("sn_" + orgNo);
     	if (sns != null && sns.length > 0){
     		for (i = 0; i < sns.length; i++){
     			sns[i].innerHTML = sn++;
     		}
     	}
     }
     
     function changeOrg(name) {
     	document.all.selectAll1.checked = false;
     	document.all.selectAll2.checked = false;
     	var trs = document.getElementsByTagName("tr");
     	//trs.style.display="none";
     	for(i=2; i < trs.length-1; i++){
     		trs[i].style.display = "none";
     		if(trs[i].id == name){
     			trs[i].style.display = "";
     		}
     	}
     	setSnByOrgNo(name);
     }  
  
     function event(obj) {
	    var obj1 = obj.parentNode.parentNode;
	    var inputs = obj1.getElementsByTagName("input");
	    var box = inputs[1];
	    if(obj.checked){
		    box.disabled=false;
	    }
	    else {
		  if(box.checked){
			box.click()
		  }
		  box.disabled=true;
	    }
     }
     
     function doClickDeptAll(obj,indexId,id) {
        var name=document.getElementById("orgNo").value;
        var trs=document.getElementsByTagName("tr");
        try {
    	var ItemID= document.getElementsByName(id);
        if(typeof ItemID != "undefined" && typeof ItemID.length == "undefined") {
              if(obj.checked) {
                  if(!ItemID.checked) {
                     for(i=0;i<trs.length;i++){
     					if(trs[i].id==name){
			     			inputs=trs[i].getElementsByTagName("input");
			     			inputs[indexId].click();
     					}
     			     }
                  }
              }
              else {
                if(ItemID.checked) {
                    for(i=0;i<trs.length;i++){
     					if(trs[i].id==name){
			     			inputs=trs[i].getElementsByTagName("input");
			     			inputs[indexId].click();
     					}
     				}
                }
             }
        }
        else if(typeof ItemID != "undefined" && typeof ItemID.length != "undefined") {
            if(obj.checked) {
               for(i=0;i<trs.length;i++){
     				if(trs[i].id==name){
			     		inputs=trs[i].getElementsByTagName("input");
			     		if(!inputs[indexId].checked) {
                        	inputs[indexId].click();
                   		}
     				}
     			}
            }
            else {
                for(i=0;i<trs.length;i++){
     				if(trs[i].id==name){
			     		inputs=trs[i].getElementsByTagName("input");
			     		if(inputs[indexId].checked) {
                        	inputs[indexId].click();
                   		}
     				}
     			}
            }
        }
    }
    catch(e) {
    }
}
</script>
</head>
  <body onload="init()">
    <html:form action="/system/groupAction" target="hideframe">
    <div id="body">
      <div class="tab">
        <ul>
          <li id="tab_1" class="tab_on">
            <a href="#" onClick="return false;" onFocus="blur()">群组授权</a>
          </li>
          <li class="clear"></li>
        </ul>
      </div>
      <div id="main">
        <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0">      
          <tr>
            <td width="70" class="label"><bean:message bundle="system" key="group.ssdw"/>：</td>
            <td width="120" class="dom">
              <input type="hidden" name="groupId" value='<bean:write name="groupForm" property="groupId"/>' />
              <input type="hidden" name="action" value="saveAuthorize"/>
              <peis:selectlist name="orgNo" sql="SL_COMMON_0001" extendProperty="class='mainSelect'" onChange="changeOrg(this.options[this.options.selectedIndex].value)"/>
            </td>
          </tr>
        </table>
        <div class="clear"></div>
      </div>
      <div id="tableContainer" class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-190));">
        <table id="userList" border="0" cellpadding="0" cellspacing="0" width="100%">
          <thead><tr>
            <th width="20%"><bean:message bundle="system" key="group.zh"/></th>
            <th width="30%"><bean:message bundle="system" key="group.mc"/></th>
            <th width="20%"><bean:message bundle="system" key="group.sq"/><input type="checkbox" id="selectAll1" onclick="doClickDeptAll(this,0,id)"/></th>
            <th width="20%"><bean:message bundle="system" key="group.kbj"/><input type="checkbox" id="selectAll2" onclick="doClickDeptAll(this,1,id)"/></th>
          </tr></thead>
          <tbody align="center">
            <logic:present name="PG_QUERY_RESULT">
              <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
                <tr id='<bean:write name="datainfo" property="col4"/>' style="display: none;">
                  <td><bean:write name="datainfo" property="col2"/></td>
                  <td><bean:write name="datainfo" property="col3"/></td>
                  <logic:equal name='datainfo' property="col6" value="">
                    <td><input type="checkbox" name="read" value="<bean:write name='datainfo' property='col1'/>" onclick="event(this)"/></td>
                    <td><input type="checkbox" name="edit" value="<bean:write name='datainfo' property='col1'/>" disabled/></td>
                  </logic:equal>     
                  <logic:equal name='datainfo' property="col6" value="0">
                    <td><input type="checkbox" name="read" value="<bean:write name='datainfo' property='col1'/>" onclick="event(this)" checked/></td>
                    <td><input type="checkbox" name="edit" value="<bean:write name='datainfo' property='col1'/>"/></td>
                  </logic:equal>   
                  <logic:equal name='datainfo' property="col6" value="1">
                    <td><input type="checkbox" name="read" value="<bean:write name='datainfo' property='col1'/>" onclick="event(this)" checked/></td>
                    <td><input type="checkbox" name="edit" value="<bean:write name='datainfo' property='col1'/>" checked/></td>
                  </logic:equal> 
                </tr>
              </logic:iterate>
            </logic:present>
          </tbody> 
        </table>  
      </div>  
      <div class="nullContainer"></div>
      <table align="center">
        <tr>
          <td width="100%" height="30" align="center">
            <input type="submit" name="queding2" value='<bean:message bundle="system" key="button.qd"/>' class="input1"/> &nbsp;
            <input type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' class="input1" onclick="parent.GB_hide();"/>
          </td>
        </tr>
      </table>
      </div>
    </div>
    </html:form>
    <iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
  </body>
</html>
