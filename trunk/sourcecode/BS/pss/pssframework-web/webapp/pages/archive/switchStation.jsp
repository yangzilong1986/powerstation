<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>操作&amp;数据</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveJson.js"></script>
<script type="text/javascript">
	//接收从主页面传过来的变电站标识
 var tempSUBS_ID=<%=request.getParameter("subs_id")%>;
 //接受从后台传来的SUBS_ID
 var etfSUBS_ID='${ETF.SUBS_ID}';
  var etfSUBS_NO='${ETF.SUBS_NO}';
   var etfSUBS_TYPE='${ETF.SUBS_TYPE}';
     //用来判断‘上一步’按钮应该跳转到哪个画面
  var etfPARAM=parent.tempPARAM;
  var etfSUBS_TYPE=parent.tempSUBS_TYPE;
  var tempWantedPerPage=parent.tempWantedPerPage;
	//加载页面，查询出该开关站详细信息
 function stationaa(){
 	if(${empty ETF.TXN_CD}){
 	window.location.href="112024.dow?SUBS_ID="+tempSUBS_ID+"&FLG=QUERY";
 	}
 }
 //保存更改的开关站信息
 function SaveSws(){
 	if(swCheck()){
 		var SUBS_BEF='${ETF.SUBS_NO}';
 		var SUBS_AF=$("#SUBS_NO").val();
 		$.ajax({
      type:"POST",
      url:"112024.dow",
      dataType:"json",
      data:$("#swsdetail").serialize()+'&SUBS_ID='+etfSUBS_ID+'&SUBS_BEFORE='+ SUBS_BEF+'&SUBS_NO='+SUBS_AF+'&FLG=UPD&SUBS_TYPE='+etfSUBS_TYPE,
      success:function(data){
              	  if(data.MSG_TYP=='E'){
              	  	alert(data.RSP_MSG);
              	  }
              	  else{alert(data.RSP_MSG);
              	  	window.parent.treeframe.location.reload();
              	  	window.location.reload();
              	  	}
              },
      error :function(XMLHttpRequest,textStatus,errorThrown){
             alert("通迅失败");
             }
      })
    }
 	}
//删除变电站
function deleteSws(){
		if(confirm("是否删除该开关站")){
				$.ajax({
      type:"POST",
      url:"112021.dow",
      dataType:"json",
      data:{SUBS_ID:etfSUBS_ID,SUBS_NO:etfSUBS_NO},
      success:function(data){
              	  if(data.MSG_TYP=='N'){
              	  	alert(data.RSP_MSG);
              	  	if(etfPARAM==''||etfPARAM=="null"){
              	    window.parent.location.href="<peis:contextPath/>/jsp/archive/addSwitchStationDetail.jsp";
              	  }else{
              	  	window.parent.location.href="112043.dow?WANTEDPERPAGE="+tempWantedPerPage;
              	  	}
              	  }
              	  else{alert(data.RSP_MSG);}
              },
      error :function(XMLHttpRequest,textStatus,errorThrown){
             alert("通迅失败");
             }
      })
			}  
}
</script>
</head>
<body onload="stationaa(),loadOrg('${ETF.ORG_NO}'),loadVolt('${ETF.VOLT_GRADE}')">
<div class="tab"><em>开关站信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 50));">
  	<form method="post" name="swsd" id="swsdetail">
  <div class="main">
	   	  <table border="0" cellpadding="0" cellspacing="0" width="900" align="center">
		      <tr>
		        <td width="13%" class="label"><font color="red">* </font>开关站编号：</td>
		        <td width="20%" class="dom"><input type="text" id="SUBS_NO" name="SUBS_NO" value="${ETF.SUBS_NO}" /></td>
		        <td width="13%" class="label"><font color="red">* </font>开关站名称：</td>
		        <td width="20%" class="dom"><input type="text" id="SUBS_NAME" name="SUBS_NAME" value="${ETF.SUBS_NAME}" /></td>
		        <td width="13%" class="label">电压等级：</td>
		        <td width="20%" class="dom">
		          <select id="VOLT_GRADE" name="VOLT_GRADE">
		          </select>
		        </td>
		      </tr>
		      <tr>
		        <td class="label"><font color="red">* </font>管理单位：</td>
		        <td class="dom">
		          <select id="orgNo" name="orgNo">
		          </select>
		        </td>
	        	<td class="label">地址：</td>
	        	<td colspan="3" width="54%" class="dom2"><input type="text" id="SUBS_ADDR" name="SUBS_ADDR" value="${ETF.SUBS_ADDR}" /></td>
	      	</tr>
		      <tr>
		        <td rowspan="3" class="label">备　注：</td>
		        <td class="dom" colspan="5">
		          <textarea class="input_textarea3" name="REMARK" id="REMARK" style="width:600;height:80">${ETF.REMARK}</textarea>
		        </td>
		      </tr>
	    	</table>
	    	<table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td align="right">
          	<input class="input4" type="button" id="deleteTg" value="保存开关站" onClick="SaveSws();" />
            <input class="input4" type="button" id="deleteTg" value="删除开关站" onClick="deleteSws();" />
          </td>
        </tr>
    </table>
	    </div>
	  </form>
</div>
</body>
</html>