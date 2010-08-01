<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title><bean:message bundle="system" key="user.edit.title"/></title>
<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEX.js"></script>
</head>

<body>
<html:form action="/system/userAddAction" onsubmit="return submitDisposal(this);" method="post" target="hideframe">
  <input type="hidden" name="action" value="saveOrUpdate"/>
  <div id="divUser" style="position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;">
    <div class="tab3">
      <ul class="e_title">
        <li class="tab_on"><a href="javascript:showUsernfo();" onfocus="blur()"><bean:message bundle="system" key="user.czyxx"/></a></li>
        <li class="tab_off"><a href="javascript:showFw();" onfocus="blur()"><bean:message bundle="system" key="user.ywfw"/></a></li>
        <li class="tab_off"><a href="javascript:showGw();" onfocus="blur()"><bean:message bundle="system" key="user.gwjs"/></a></li>
        <li class="tab_off"><a href="javascript:showQx();" onfocus="blur()"><bean:message bundle="system" key="user.czqx"/></a></li>
      </ul>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td class="contentLeft"></td>
        <td height="20" colspan="3">
          <html:hidden property="userId"/>
        </td>
        <td class="contentRight"></td>
      </tr>
      <tr>
        <td class="contentLeft"></td>
        <td bgcolor="#FFFFFF" colspan="3">
          <table width="99%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="15%" height="30" align="right"><bean:message bundle="system" key="user.zh"/>：<font color="#ff0000">*</font></td>
              <td width="30%" align="left">
                <logic:equal name="userForm" property="userId" value="">
                  <html:text property="userNo" style='width: 200px;'/>
                </logic:equal>
                <logic:notEqual name="userForm" property="userId" value="">
                  <html:text property="userNo" style='width: 200px;' readonly="true"/>
                </logic:notEqual>
                <input type="hidden" name="oldUserNo" value='<bean:write name="userForm" property="userNo"/>'/>
              </td>
              <td width="15%" height="30" align="right"><bean:message bundle="system" key="user.mc"/>：<font color="#ff0000">*</font></td>
              <td width="30%" align="left">
                <html:text property="username" style='width: 200px;'/>
              </td>
            </tr>
            <tr>
              <td align="right"><bean:message bundle="system" key="user.mm"/>：<font color="#ff0000">*</font></td>
              <td align="left">
                <logic:equal name="userForm" property="userId" value="">
                  <html:password property="passwd" style='width: 200px;'/>
                </logic:equal>
                <logic:notEqual name="userForm" property="userId" value="">
                  <html:password property="passwd" style='width: 200px;' readonly="true"/>
                </logic:notEqual>
              </td>
              <td align="right"><bean:message bundle="system" key="user.qrmm"/>：<font color="#ff0000">*</font></td>
              <td align="left">
                <logic:equal name="userForm" property="userId" value="">
                  <html:password property="passwd1" style='width: 200px;'/>
                </logic:equal>
                <logic:notEqual name="userForm" property="userId" value="">
                  <html:password property="passwd1" style='width: 200px;' readonly="true"/>
                </logic:notEqual>
              </td>
            </tr>
            <tr>
              <td align="right"><bean:message bundle="system" key="user.ssdw"/>：</td>
              <td align="left">
                <peis:selectlist styleId="orgNo" name="orgNo" sql="SL_COMMON_0001" />
              </td>
              <td align="right"><bean:message bundle="system" key="user.dh"/>：</td>
              <td align="left">
                <html:text property="phone" style='width: 200px;'/>
              </td>
            </tr>
            <tr>
              <td align="right"><bean:message bundle="system" key="user.zt"/>：</td>
              <td align="left">
                <peis:selectlist name="status" sql="COMMON0034" />
              </td>
            </tr>
          </table>
          <table align="center">
            <tr>
              <td width="100%" height="30" align="center">
                <logic:notEqual name="isEdit" scope="request" value="false">
                  <input type="submit" name="queding2" value='<bean:message bundle="system" key="button.qd"/>' class="input1"/> &nbsp;
                </logic:notEqual>
                <input type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' class="input1" onclick="top.GB_hide()"/>
              </td>
            </tr>
          </table>
        </td>
        <td class="contentRight"></td>
      </tr>
    </table>
  </div>
  <div id="divFw" style="display: none;position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;">
    <div class="tab3">
      <ul class="e_title">
        <li class="tab_off"><a href="javascript:showUsernfo();" onfocus="blur()"><bean:message bundle="system" key="user.czyxx"/></a></li>
        <li class="tab_on"><a href="javascript:showFw();" onfocus="blur()"><bean:message bundle="system" key="user.ywfw"/></a></li>
        <li class="tab_off"><a href="javascript:showGw();" onfocus="blur()"><bean:message bundle="system" key="user.gwjs"/></a></li>
        <li class="tab_off"><a href="javascript:showQx();" onfocus="blur()"><bean:message bundle="system" key="user.czqx"/></a></li>
      </ul>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td class="contentLeft"></td>
        <td height="20" colspan="3"></td>
        <td class="contentRight"></td>
      </tr>
      <tr>
        <td class="contentLeft"></td>
        <td bgcolor="#FFFFFF" colspan="3">
          <div class="tableContainer" style="width: 99%;" align="center">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <thead>
                <tr>
                  <th width="20%"><bean:message bundle="system" key="user.xh"/></th> 
                  <th width="40%"><bean:message bundle="system" key="user.dxmc"/></th>
                  <th width="20%"><bean:message bundle="system" key="user.sq"/><input type="checkbox" name="select1" onclick="selectAllBox(this,'sRole1')"/></th>
                  <th width="20%"><bean:message bundle="system" key="user.ksq"/><input type="checkbox" name="select2" onclick="selectAllBox(this,'rRole1')"/></th>
                </tr> 
              </thead>
              <tbody>
                <logic:present name="fw">
                  <logic:iterate id="datainfo" name="fw" indexId="number" offset="1">
                    <tr align="center" class="trmainstyle">
                      <td height="20"><bean:write name="number" /></td>
                      <td height="20"><bean:write name="datainfo" property="col2" /></td>
                      <logic:equal name="datainfo" property="col4" value="0">
                        <td>
                          <input type="checkbox" id="sRole1" name="sRole1" onclick="event(this,<bean:write name="number"/>)" value="<bean:write name="datainfo" property="col1"/>" />
                        </td>
                        <td>
                          <input type="checkbox" id="rRole1" name="rRole1" value="<bean:write name="datainfo" property="col1"/>" disabled />
                        </td>
                      </logic:equal>
                      <logic:equal name="datainfo" property="col4" value="1">
                        <td>
                          <input type="checkbox" id="sRole1" name="sRole1" onclick="event(this,<bean:write name="number"/>)" value="<bean:write name="datainfo" property="col1"/>" checked />
                        </td>
                        <td>
                          <logic:equal name="datainfo" property="col5" value="0">
                            <input type="checkbox" id="rRole1" name="rRole1" value="<bean:write name="datainfo" property="col1"/>" />
                          </logic:equal>
                          <logic:equal name="datainfo" property="col5" value="1">
                            <input type="checkbox" id="rRole1" name="rRole1" value="<bean:write name="datainfo" property="col1"/>" checked />
                          </logic:equal>
                        </td>
                      </logic:equal>
                    </tr>
                  </logic:iterate>
                </logic:present>
              </tbody>
            </table>
          </div>
          <table align="center">
            <tr>
              <td width="100%" height="30" align="center">
                <logic:notEqual name="isEdit" scope="request" value="false">
                  <input type="submit" name="queding2" value='<bean:message bundle="system" key="button.qd"/>' class="input1"/> &nbsp;
                </logic:notEqual>
                <input type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' class="input1" onclick="top.GB_hide()"/>
              </td>
            </tr>
          </table>
        </td>
        <td class="contentRight"></td>
      </tr>
    </table>
  </div>
  <div id="divGw" style="display: none;position: absolute; top: 0px; left: 0px; width: 100%; height: 100%; OVERFLOW-Y:AUTO;">
    <div class="tab3">
      <ul class="e_title">
        <li class="tab_off"><a href="javascript:showUsernfo();" onfocus="blur()"><bean:message bundle="system" key="user.czyxx"/></a></li>
        <li class="tab_off"><a href="javascript:showFw();" onfocus="blur()"><bean:message bundle="system" key="user.ywfw"/></a></li>
        <li class="tab_on"><a href="javascript:showGw();" onfocus="blur()"><bean:message bundle="system" key="user.gwjs"/></a></li>
        <li class="tab_off"><a href="javascript:showQx();" onfocus="blur()"><bean:message bundle="system" key="user.czqx"/></a></li>
      </ul>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td class="contentLeft"></td>
        <td height="20" colspan="3"></td>
        <td class="contentRight"></td>
      </tr>
      <tr>
        <td class="contentLeft"></td>
        <td bgcolor="#FFFFFF" colspan="3">
        <div class="tableContainer" style="width: 99%;">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <thead>
              <tr class=trheadStyle>
                <th width="10%"><bean:message bundle="system" key="user.xh"/></th> 
                <th width="20%"><bean:message bundle="system" key="user.dxmc"/></th>
                <th width="10%"><bean:message bundle="system" key="user.sq"/><input type="checkbox" name="select1" onclick="selectAllBox(this,'sRole2')"/></th>
                <th width="10%"><bean:message bundle="system" key="user.ksq"/><input type="checkbox" name="select2" onclick="selectAllBox(this,'rRole2')"/></th>
              </tr> 
            </thead>
            <tbody>
              <logic:present name="gw">
                <logic:iterate id="datainfo" name="gw" indexId="number" offset="1">
                  <tr align="center" class="trmainstyle">
                    <td height="20"><bean:write name="number" /></td>
                    <td height="20"><bean:write name="datainfo" property="col2" /></td>
                    <logic:equal name="datainfo" property="col4" value="0">
                      <td>
                        <input type="checkbox" id="sRole2" name="sRole2" onclick="event(this,<bean:write name="number"/>)" value="<bean:write name="datainfo" property="col1"/>" />
                      </td>
                      <td>
                        <input type="checkbox" id="rRole2" name="rRole2" value="<bean:write name="datainfo" property="col1"/>" disabled />
                      </td>
                    </logic:equal>
                    <logic:equal name="datainfo" property="col4" value="1">
                      <td>
                        <input type="checkbox" id="sRole2" name="sRole2" onclick="event(this,<bean:write name="number"/>)" value="<bean:write name="datainfo" property="col1"/>" checked />
                      </td>
                      <td>
                        <logic:equal name="datainfo" property="col5" value="0">
                          <input type="checkbox" id="rRole2" name="rRole2" value="<bean:write name="datainfo" property="col1"/>" />
                        </logic:equal>
                        <logic:equal name="datainfo" property="col5" value="1">
                          <input type="checkbox" id="rRole2" name="rRole2" value="<bean:write name="datainfo" property="col1"/>" checked />
                        </logic:equal>
                      </td>
                    </logic:equal>
                  </tr>
                </logic:iterate>
              </logic:present>
            </tbody>
           </table>
         </div>
          <table align="center">
            <tr>
              <td width="100%" height="30" align="center">
                <logic:notEqual name="isEdit" scope="request" value="false">
                  <input type="submit" name="queding2" value='<bean:message bundle="system" key="button.qd"/>' class="input1"/> &nbsp;
                </logic:notEqual>
                <input type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' class="input1" onclick="top.GB_hide()"/>
              </td>
            </tr>
          </table>
        </td>
        <td class="contentRight"></td>
      </tr>
    </table>
  </div>
  <div id="divQx" style="display: none;position: absolute; top: 0px; left: 0px; width: 100%; height: 100%; OVERFLOW-Y:AUTO;">
    <div class="tab3">
      <ul class="e_title">
        <li class="tab_off"><a href="javascript:showUsernfo();" onfocus="blur()"><bean:message bundle="system" key="user.czyxx"/></a></li>
        <li class="tab_off"><a href="javascript:showFw();" onfocus="blur()"><bean:message bundle="system" key="user.ywfw"/></a></li>
        <li class="tab_off"><a href="javascript:showGw();" onfocus="blur()"><bean:message bundle="system" key="user.gwjs"/></a></li>
        <li class="tab_on"><a href="javascript:showQx();" onfocus="blur()"><bean:message bundle="system" key="user.czqx"/></a></li>
      </ul>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td class="contentLeft"></td>
        <td height="20" colspan="3"></td>
        <td class="contentRight"></td>
      </tr>
      <tr>
        <td class="contentLeft"></td>
        <td bgcolor="#FFFFFF" colspan="3">
          <div class="tableContainer" style="width: 99%;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <thead>
                <tr class="trheadStyle">
                  <th width="10%"><bean:message bundle="system" key="user.xh"/></th>
                  <th width="20%"><bean:message bundle="system" key="user.dxmc"/></th>
                  <th width="10%"><bean:message bundle="system" key="user.sq"/><input type="checkbox" name="select1" onclick="selectAllBox(this,'sRole3')"/></th>
                  <th width="10%"><bean:message bundle="system" key="user.ksq"/><input type="checkbox" name="select2" onclick="selectAllBox(this,'rRole3')"/></th>
                </tr>
              </thead>
              <tbody>
                <logic:present name="qx">
                  <logic:iterate id="datainfo" name="qx" indexId="number" offset="1">
                    <tr align="center" class="trmainstyle">
                      <td height="20"><bean:write name="number" /></td>
                      <td height="20"><bean:write name="datainfo" property="col2" /></td>
                      <logic:equal name="datainfo" property="col4" value="0">
                        <td>
                          <input type="checkbox" id="sRole3" name="sRole3" onclick="event(this,<bean:write name="number"/>)" value="<bean:write name="datainfo" property="col1"/>" />
                        </td>
                        <td>
                          <input type="checkbox" id="rRole3" name="rRole3" value="<bean:write name="datainfo" property="col1"/>" disabled />
                        </td>
                      </logic:equal>
                      <logic:equal name="datainfo" property="col4" value="1">
                        <td>
                          <input type="checkbox" id="sRole3" name="sRole3" onclick="event(this,<bean:write name="number"/>)" value="<bean:write name="datainfo" property="col1"/>" checked />
                        </td>
                        <td>
                          <logic:equal name="datainfo" property="col5" value="0">
                            <input type="checkbox" id="rRole3" name="rRole3" value="<bean:write name="datainfo" property="col1"/>" />
                          </logic:equal>
                          <logic:equal name="datainfo" property="col5" value="1">
                            <input type="checkbox" id="rRole3" name="rRole3" value="<bean:write name="datainfo" property="col1"/>" checked />
                          </logic:equal>
                        </td>
                      </logic:equal>
                    </tr>
                  </logic:iterate>
                </logic:present>
              </tbody>
            </table>
          </div>
          <table align="center">
            <tr>
              <td width="100%" height="30" align="center">
                <logic:notEqual name="isEdit" scope="request" value="false">
                  <input type="submit" name="queding2" value='<bean:message bundle="system" key="button.qd"/>' class="input1"/> &nbsp;
                </logic:notEqual>
                <input type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' class="input1" onclick="top.GB_hide()"/>
              </td>
            </tr>
          </table>
        </td>
        <td class="contentRight"></td>
      </tr>
    </table>
  </div>
</html:form>
<html:javascript formName="userForm" />
<iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
</body>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
//
function showUsernfo() {
    document.all.divUser.style.display = "";
    document.all.divFw.style.display = "none";
    document.all.divGw.style.display = "none";
    document.all.divQx.style.display = "none";
}
function showQx() {
    document.all.divUser.style.display = "none";
    document.all.divFw.style.display = "none";
    document.all.divGw.style.display = "none";
    document.all.divQx.style.display = "";
}
function showFw() {
    document.all.divUser.style.display = "none";
    document.all.divFw.style.display = "";
    document.all.divGw.style.display = "none";
    document.all.divQx.style.display = "none";
}
function showGw() {
    document.all.divUser.style.display = "none";
    document.all.divFw.style.display = "none";
    document.all.divGw.style.display = "";
    document.all.divQx.style.display = "none";
}

// 判断岗位是否选择上
function gwIsChecked() {
    var th = document.forms[0];
    for(var i=0;i<th.elements.length;i++) {
       var o=th.elements[i];
       if(o.name=="sRole2" && o.checked)
        {
           return true;
        }
    }
    return false;
} 

// 判断业务范围是否选择上

function fwIsChecked() {
    var th=document.forms[0];
    for(var i=0;i<th.elements.length;i++) {
        var o=th.elements[i];
        if(o.name=="sRole1" && o.checked) {
            return true;
        }
    }
    return false;
}

//提交处理
function submitDisposal(form) {
    var password =document.getElementsByName("passwd");
    var password1 =document.getElementsByName("passwd1");
    
    if(password[0].value!=password1[0].value) {
        alert('<bean:message bundle="system" key="errors.passwd.different"/>');
        return false;
    }
    
    if(validateUserForm(form)){
        if(!fwIsChecked() && gwIsChecked()){
            return confirm('<bean:message bundle="system" key="user.message.fw.null"/>');
        }
        
        if(fwIsChecked() && !gwIsChecked()){
            return confirm('<bean:message bundle="system" key="user.message.gw.null"/>');
        }
        
        if(!fwIsChecked() && !gwIsChecked()){
            return confirm('<bean:message bundle="system" key="user.message.fw.and.gw.null"/>');
        }
    }
    else{
        return false;
    }
}

function event(obj,no) {
	var name=obj.name;
	var rname = "r"+name.substring(1);
	var box=document.getElementsByName(rname)[no-1];
	if(obj.checked){
		box.disabled=false;
	}else{
		if(box.checked){
			box.click()
		}
		box.disabled=true;
	}
}
</script>
</html>
