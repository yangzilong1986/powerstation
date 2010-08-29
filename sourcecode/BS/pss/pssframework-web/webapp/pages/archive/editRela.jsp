<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>


<html>
  <head>
    <title><bean:message bundle="archive" key="archive.edit.ag"/></title>
    
    <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css"/>
    <script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/common/common-ajax.js"></script>
	<script type="text/javascript">
	var contextPath = "<peis:contextPath/>";
	
	
	function changeTerminal(termId){
		var custId = document.getElementsByName("custId")[0].value;
		if(termId == '0') {
			return false;
		}
		else {
			location.href = contextPath + "/archive/aAgRelaAction.do?action=queryGp&termId=" + termId + "&custId=" + custId + "&random=" + Math.random();
			
		}
	}
	
	function init() {
        var computeFlag = '<peis:param type="PAGE" paramName="computeFlag"/>';
        if(computeFlag == '0' || computeFlag == '')
        {
           document.getElementsByName("computeFlag")[0].checked = false;
        }
        var sucratCptId = '<peis:param type="PAGE" paramName="sucratCptId"/>';
        if(sucratCptId == '0' || sucratCptId == '')
        {
           document.getElementsByName("sucratCptId")[0].checked = false;
        }
		var gpSns = '<bean:write name="gpSns"/>';
		var arrGpSn = gpSns.split(",");
		for(var i=0;i<arrGpSn.length;i++){
            try {
			    document.getElementById(arrGpSn[i]).checked = 'true';
            }
            catch(e) {
            }
		}
		
		var computerMarks = '<bean:write name="computerMarks"/>';
		var arrCM = computerMarks.split(",");
		for(var i=0;i<arrCM.length;i++){
			var cm = arrCM[i];
			var gp_id = cm.split("_")[0];
			var cmValue = cm.split("_")[1];
            try {
			    document.getElementById("ysf"+gp_id).value = cmValue;
            }
            catch(e) {
            }
		}
		
		var pnMarks = '<bean:write name="pnMarks"/>';
		var arrPM = pnMarks.split(",");
		for(var i=0;i<arrPM.length;i++){
			var pm = arrPM[i];
			var gp_id = pm.split("_")[0];
			var pmValue = pm.split("_")[1];
            try {
			    document.getElementById("zfx"+gp_id).value = pmValue;
            }
            catch(e) {
            }
		}
	}
	
	function editRela() {
		document.getElementsByName("editBt")[0].disabled = true;
		var termId = document.getElementsByName("termId")[0].value;
		var aGId = document.getElementsByName("aGId")[0].value;
        var computeFlag = "0";
		if(document.getElementsByName("computeFlag")[0].checked){
			computeFlag = "1";
		}
		else{
			computeFlag = "0";
		}
        var sucratCptId = "";
        if(document.getElementsByName("sucratCptId")[0].checked){
            var sucratCptId = "1";
        }
        else{
            var sucratCptId = "0";
        }
		var gpSns = getSelectedIdString();
        var computerMarks="";
        var pnMarks="";
        if(gpSns != "") {
    		var arrGpSn = gpSns.split(",");
    		for(var i=0;i<arrGpSn.length;i++){
    			computerMarks += "," + document.getElementById("ysf"+arrGpSn[i]).value;
    			pnMarks += "," + document.getElementById("zfx"+arrGpSn[i]).value;
    		}
        }
		if(computerMarks.indexOf(",")!=-1){
			computerMarks = computerMarks.substring(1);
		}
		if(pnMarks.indexOf(",")!=-1){
			pnMarks = pnMarks.substring(1);
		}
		var custId = document.getElementsByName("custId")[0].value;
		var url = contextPath + "/archive/aAgRelaAction.do?action=editAAGRela&termId="+termId+"&aGId="+aGId+"&computeFlag="+computeFlag+"&sucratCptId=" + sucratCptId;
		url += "&gpSns="+gpSns+"&computerMarks="+computerMarks+"&pnMarks="+pnMarks+"&custId="+custId;
		var callback = recive;
		executeXhr2(callback,url);
	}
	
	function recive() {
		if(req2.readyState == 4){
		var msg = "";
		//获得返回后的XML文件
   	  	var xmldoc 				= 	req2.responseXML;
   	  	
   	  	//得到所有的新的消息记录
   	  	var message_nodes 		= 	xmldoc.getElementsByTagName("msgs"); 
   	  	var n_messages 			= 	message_nodes.length;
   	  	for (var i = 0; i < n_messages; i++) {
   	  		//得到数据
   	  	   
   	  	   msg			=	message_nodes[i].getElementsByTagName("msg")[0].firstChild.nodeValue;
   	  	   //alert(msg);
   	  	}
   	  	if(msg=="0")
   	  	{
   	  	   alert("<bean:message bundle='archive' key='archive.db.error'/>");
   	  	   document.getElementsByName("editBt")[0].disabled = false;
   	  	}	
   	  	if(msg=="1")
   	  	{  
   	  	   alert("<bean:message bundle='archive' key='archive.editSuccess.ag'/>");
   	  	   window.opener.location.href = window.opener.location.href;
	  	   window.close();
   	  	}
	}
	}
	
	</script> 
  </head>

  <body onload="init();" style="overflow-x:hidden;overflow-y:hidden;">
  <html:form action="/archive/aAgRelaAction" method="post">
  <input type="hidden" name="action" value="editAAGRela">
    <peis:text type="hidden" name="custId"/>
      <table width="98%" border="0" cellpadding="0" cellspacing="0" align="center" class="e_detail_t">
    <tr>
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tleft.gif" width=10 height=28></td>
    <td width="100%" background="<peis:contextPath/>/img/e_detail_tmain.gif" class="functionTitle"><bean:message bundle="archive" key="archive.edit.ag"/></td>  
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tright.gif" width=10 height=28></td>
    </tr>
    <tr valign="top">
      <td class="contentLeft">　</td>
      <td>
      <table border="0" cellpadding="0" cellspacing="0" class="function_title">
		<tr>
		  <td class="functionLabe2" width="225" align="left">
		     <bean:message bundle="archive" key="archive.terminal.LOGICAL_ADDR"/>：<peis:selectlist name="termId" sql="AR0005" onChange="changeTerminal(this.value);" extendProperty="class='user_add_input' disabled='true'"/>
          </td>
		  <td class="functionLabe2" width="225" height="24" align="left"> 
		     <bean:message bundle="archive" key="archive.ag.sn"/>：<peis:selectlist name="aGId" sql="RELA0002" extendProperty="class='user_add_input' disabled='true'"/>
		  </td>
		  <td class="functionLabe2" width="150" height="24" align="left"> 
		     <bean:message bundle="archive" key="archive.ag.computeFlag"/>：<input type="checkbox" name="computeFlag" value="1" checked="checked"/>
		  </td>
          <td class="functionLabe2" width="150" height="24" align="left"> 
             <bean:message bundle="archive" key="archive.ag.sucratCptId"/>：<input type="checkbox" name="sucratCptId" value="1" checked="checked"/>
          </td>
		</tr>
      </table>
      <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 103));">
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <thead><tr>
                  <th width="25%"><bean:message bundle="archive" key="archive.gp.gpSn"/></th>
                  <th width="25%"><bean:message bundle="archive" key="archive.ag.comFlag"/></th>
                  <th width="25%"><bean:message bundle="archive" key="archive.ag.pnFlag"/></th>
                  <th width="25%"><bean:message bundle="archive" key="archive.label.check"/></th>
            </tr></thead>
            <tbody>
                  <logic:present name="gpList">
                  <logic:iterate id="gp" name="gpList" >
                  <tr onmouseover="this.style.cursor='hand';">
                    <td><bean:write name="gp" property="gpSn"/></td>
                    <td>
                     <select name="ysf" id="ysf<bean:write name='gp' property='gpSn'/>">
                     	<option value="1">+</option>
                     	<option value="-1">-</option>
                     </select>
                    </td>
                    <td>
                     <select name="zfx" id="zfx<bean:write name='gp' property='gpSn'/>">
                     	<option value="1"><bean:message bundle="archive" key="archive.forward"/></option>
                     	<option value="-1"><bean:message bundle="archive" key="archive.reverse"/></option>
                     </select>
                    </td>
                    <td>
                     <input type="checkbox" name="ItemID" value="<bean:write name="gp" property="gpSn"/>" id="<bean:write name="gp" property="gpSn"/>"/>
                    </td>
                  </tr>
                  </logic:iterate>
                  </logic:present>
                </tbody>
            </table>
            </div>
      </td>
      <td class="contentRight">　</td>
    </tr>
    <tr valign="top" bgcolor="#FFFFFF">
    <td ><img src="<peis:contextPath/>/img/e_detail_bleft.gif" width=10 height=27></td>
    <td align="right" background="<peis:contextPath/>/img/e_detail_bmain.gif">
     <input class="input_ok" type="button" name="editBt" onclick="editRela();" value="<bean:message bundle='archive' key='archive.label.save'/>"/>
    </td>
    <td ><img src="<peis:contextPath/>/img/e_detail_bright.gif" width=10 height=27></td>
    </tr>
    </table> 
    </html:form>  
  </body>
</html>
