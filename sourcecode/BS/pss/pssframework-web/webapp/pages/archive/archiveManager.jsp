<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>

<html>
  <head>
    <title><bean:message bundle="archive" key="archive.manager" /></title>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
    <script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
    <script type="text/javascript">
    var contextPath = "<peis:contextPath/>";
    
    /**
     *
     */
	function checkQuery() {
        var selectedIndex = -1;
        var i = 0;
        for(i = 0; i < document.all.dxlx.length; i++) {
            if(document.all.dxlx[i].checked) {
                selectedIndex = i;
                break;
            }
        }
        if(selectedIndex < 0) {
            alert("<bean:message bundle="archive" key="archive.isnotchecked"/>");
            return false;
        }
    }

	var opwindow1 = null;     //记录打开浏览窗口的对象
    function windowPopup1(url, wd, ht) {
        if(opwindow1 != null) {
            opwindow1.close();
        }

        opwindow1 = open(url,'','height='+ht+',width='+wd+',top='+(screen.availHeight-ht)/2+', left='+(screen.availWidth-wd)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
    }
    
    function changeModel(model, nT) {
    	parent.document.frames["DTree"].treereload(model, nT);
    }

    function clickEvent(type,id,text){
    	alert(type);
    	alert(id);
    	alert(text);
    }

    function query(queryType) {
        if(queryType == "tree") {
        }
        else if(queryType == "objectNo") {
            document.getElementsByName("objectId")[0].value = "";
            document.getElementsByName("objectType")[0].value = "";
            if(window.parent.DTree.oldObject) {
                window.parent.DTree.oldObject.style.color = "000000";
                window.parent.DTree.oldObject.style.backgroundColor = "#ffffff";
            }
        }
        document.forms[0].submit();
    }

    function fresh() {
        parent.document.frames["DTree"].location.reload();
        parent.document.frames["DTree"].location = node.Target;
    }

    function newAdd() {
        var newDx = "1";
        /*var sys_object = document.getElementsByName("dxlx");
        for(var i = 0; i < sys_object.length; i++) {
            if(sys_object[i].checked) {
                newDx = sys_object[i].value;
                break;
            }
        }*/
        
        if(newDx == "1") {      //大用户
            var str_url = contextPath + "/archive/customerAction.do";
            str_url += "?action=beforeEdit";
            windowPopup1(str_url, 900, 600);
        }
        else if(newDx == "3"){  //低压用户
            alert("<bean:message bundle="archive" key="archive.addlowvolttoblowoftg"/>");
            return false;
        }
        else if(newDx == "2"){  //配变台区
            var str_url = contextPath + "/do/archive/gotoAddArchive.do";
            str_url += "?action=gotoAddTg";
            windowPopup1(str_url, 900, 600);
        }
    }
    </script>
  </head>
  <body onload="query('objectNo');">
    <html:form action="/archive/archiveQuery" method="post" target="archivepage">
      <html:hidden property="action" value="normalMode" />
      <html:hidden property="sortCol" value="OBJECT_NO" />
      <html:hidden property="sortMode" value="asc" />
      <html:hidden property="objectId" />
      <html:hidden property="objectType" />
      <input type="hidden" name="sqlCode" value="AL_ARCHIVE_1001">      <!-- SQL编码 -->
      <input type="hidden" name="showType" value="pag">                 <!-- 分页显示 -->
      <input type="hidden" name="pageRows" value="20">                  <!-- 每页行数 -->
      <div id="main">
        <div>
          <div id="tool">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td width="66" class="label"><bean:message bundle="archive" key="archive.objectNo" />：</td>
                <td width="120" class="dom"><input type="text" name="objectNo" /></td>
                <td width="160">
                  <input type="button" name="button" class="input" value="<bean:message key="global.query"/>" onclick="query('objectNo');" />
                  <!-- <input type="button" class="input" value="<bean:message bundle='archive' key='archive.synch'/>" onclick="" /> -->
                  <input type="button" class="input" value="<bean:message key='global.add'/>" onclick="newAdd();" />
                </td>
                <td align="right">
                  <!-- <input type="radio" name="dxlx" value="1" checked="checked" onclick="parent.changeTreeMode('99991002')" />
                  <bean:message bundle="archive" key="archive.cust.tab_bigcust" />
                  <input type="radio" name="dxlx" value="2" onclick="parent.changeTreeMode('99991003')" />
                  <bean:message bundle="archive" key="archive.pbtq" />
                  <input type="radio" name="dxlx" value="3" onclick="parent.changeTreeMode('99991003')" />
                  <bean:message bundle="archive" key="archive.custInTg" />
                  <input type="radio" name="dxlx" value="4" />
                  <bean:message bundle="archive" key="archive.bdz" /> -->
                </td>
              </tr>
            </table>
          </div>
        </div>
        <div class="content">
          <div id="cont_3">
            <div class="target">
              <ul>
                <li class="target_on">&nbsp;<bean:message bundle="archive" key="archive.info" />&nbsp;</li>
                <li class="clear"></li>
              </ul>
            </div>
            <div style="height: expression(((document.documentElement.clientHeight || document.body.clientHeight) - 72));">
              <iframe name="archivepage" src="" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
            </div>
          </div>
        </div>
      </div>
    </html:form>
  </body>
</html>