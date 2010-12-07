<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>


<html>
  <head>
    <title><bean:message bundle="archive" key="archive.new.ag"/></title>
    
    <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css"/>
    <script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/common/common-ajax.js"></script>
	<script type="text/javascript">
	var contextPath = "<peis:contextPath/>";
	
	
	function changeTerminal(termId){
		var custId = document.getElementsByName("custId")[0].value;
		if(termId=='0'){
			return false;
		}
		else{
			location.href = contextPath + "/archive/aAgRelaAction.do?action=queryGp&termId="+termId+"&custId="+custId;
		}
	}
	
	function saveRela() {
		var termId = document.getElementsByName("termId")[0].value;
		var aGId = document.getElementsByName("aGId")[0].value;
        var computeFlag = "0";
		if(document.getElementsByName("computeFlag")[0].checked){
			computeFlag = "1";
		}
		else{
			computeFlag = "0";
		}
        var sucratCptId = "0";
        if(document.getElementsByName("sucratCptId")[0].checked){
            sucratCptId = "1";
        }
        else{
            sucratCptId = "0";
        }
		var gpSns = getSelectedIdString();
		if(gpSns == "")
		{
		   alert(" <bean:message bundle="archive" key="archive.ag.hint1"/>");
		   return false;
		}
		var arrGpSn = gpSns.split(",");
		var computerMarks="";
		var pnMarks="";
		for(var i=0;i<arrGpSn.length;i++){
		    if(document.getElementById("ysf"+arrGpSn[i]))
		    {
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
		//alert(pnMarks);
		var custId = document.getElementsByName("custId")[0].value;
		//alert(custId);
		document.getElementById("save").disabled = true;
		var url = contextPath + "/archive/aAgRelaAction.do?action=saveOrUpdate&termId="+termId+"&aGId="+aGId+"&computeFlag="+computeFlag+"&sucratCptId="+sucratCptId;
		url += "&gpSns="+gpSns+"&computerMarks="+computerMarks+"&pnMarks="+pnMarks+"&custId="+custId;
		//alert(url);
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
   	  	   alert("<bean:message bundle="archive" key="archive.ag.hint2"/>");
   	  	   document.getElementById("save").disabled = false;
   	  	}	
   	  	if(msg=="1")
   	  	{  
   	  	   alert("<bean:message bundle="archive" key="archive.ag.hint3"/>");
   	  	   window.opener.location.href = window.opener.location.href;
	  	   window.close();
   	  	}
   	  	if(msg=="2"){
   	  		alert("<bean:message bundle="archive" key="archive.ag.hint4"/>");
   	  		document.getElementById("save").disabled = false;
   	  	} 
	}
	}
	
	function initButton()
	{
	   var termObj = document.all.termId;
	   if(termObj.options.length == 0 || termObj.value == '0')
       {
          document.getElementById("save").disabled = true;
       }
	}
	
	</script> 
  </head>
  
  <body style="overflow-x:hidden;overflow-y:hidden;" onload="initButton();">
    <html:form action="/archive/aAgRelaAction" method="post">
    <input type="hidden" name="action" value="saveOrUpdate">
    <peis:text type="hidden" name="custId"/>
      <table width="98%" border=0 cellpadding=0 cellspacing=0 align=center class="e_detail_t">
    <tr>
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tleft.gif" width=10 height=28></td>
    <td width="100%" background="<peis:contextPath/>/img/e_detail_tmain.gif" class="functionTitle"><bean:message bundle="archive" key="archive.new.ag"/></td>  
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tright.gif" width=10 height=28></td>
    </tr>
    <tr valign="top">
      <td class="contentLeft">　</td>
      <td>
      <table border="0" cellpadding="0" cellspacing="0" class="function_title">
		<tr>
		  <td class="functionLabe2" width="225" align="left">
		     <bean:message bundle="archive" key="archive.terminal.logicaladdr"/>：<peis:selectlist name="termId" sql="AR0005" onChange="changeTerminal(this.value);" extendProperty="class='user_add_input'"/>
          </td>
		  <td class="functionLabe2" width="225" height="24" align="left"> 
		     <bean:message bundle="archive" key="archive.ag.sn"/>：<peis:selectlist name="aGId" sql="RELA0002" extendProperty="class='user_add_input'"/>
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
                    <td><bean:write name="gp" property="col1"/></td>
                    <td>
                     <select name="ysf" id="ysf<bean:write name='gp' property='col1'/>">
                     	<option value="1">+</option>
                     	<option value="-1">-</option>
                     </select>
                    </td>
                    <td>
                     <select name="zfx" id="zfx<bean:write name='gp' property='col1'/>">
                     	<option value="1"><bean:message bundle="archive" key="archive.rela.p"/></option>
                     	<option value="-1"><bean:message bundle="archive" key="archive.rela.i"/></option>
                     </select>
                    </td>
                    <td>
                     <input type="checkbox" name="ItemID" value="<bean:write name="gp" property="col1"/>"/>
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
     <input id="save" class="input_ok" type="button" name="saveButton" onclick="saveRela();" value="<bean:message key='global.save'/>"/>&nbsp;&nbsp;
    </td>
    <td ><img src="<peis:contextPath/>/img/e_detail_bright.gif" width=10 height=27></td>
    </tr>
    </table> 
    </html:form>
  </body>
</html>
