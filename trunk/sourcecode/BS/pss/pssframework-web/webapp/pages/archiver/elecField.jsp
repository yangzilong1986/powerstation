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
 var etfPARAM=parent.tempPARAM;
 var tempWantedPerPage=parent.tempWantedPerPage;
 //保存更改的变电站信息
 function saveEle(){
 	if(elecCheck()){
 		var SUBS_BEF='${ETF.SUBS_NO}';
 		var SUBS_AF=$("#SUBS_NO").val();
 		$.ajax({
      type:"POST",
      url:"112022.dow",
      dataType:"json",
      data:$("#elstation").serialize()+'&SUBS_ID='+etfSUBS_ID+'&SUBS_BEFORE='+ SUBS_BEF+'&SUBS_NO='+SUBS_AF+'&FLG=UPD&SUBS_TYPE='+etfSUBS_TYPE,
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
function deleteEle(){
		if(confirm("是否删除该电厂")){
				$.ajax({
      type:"POST",
      url:"112021.dow",
      dataType:"json",
      data:{SUBS_ID:etfSUBS_ID,SUBS_NO:etfSUBS_NO},
      success:function(data){
              	  if(data.MSG_TYP=='N'){
              	  	alert(data.RSP_MSG);
              	  	if(etfPARAM==''||etfPARAM=="null"){
              	    window.parent.location.href="<peis:contextPath/>/jsp/archive/addElectricDetail.jsp";
              	  }else{
              	  	window.parent.location.href="112042.dow?WANTEDPERPAGE="+tempWantedPerPage;
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
<body onload="loadOrg('${ETF.ORG_NO}'),loadVolt('${ETF.VOLT_GRADE}'),loadPla('${ETF.PLA_CHAR}')"">
<div class="tab"><em>电厂信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 25));">
  <form method="post" name="els" id="elstation">
  <div class="main">
     <table border="0" cellpadding="0" cellspacing="0" width="900" align="center">
	        <tr>
	          <td width="13%" class="label"><font color="red">* </font>电厂编号：</td>
	          <td width="20%" class="dom"><input type="text" id="SUBS_NO" name="SUBS_NO" value="${ETF.SUBS_NO}" /></td>
	          <td width="13%" class="label"><font color="red">* </font>电厂名称：</td>
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
		        <td class="label">装机容量：</td>
		        <td class="dom"> <input type="text" id="MT_CAPA" name="MT_CAPA" value="${ETF.MT_CAPA}" style="width: 115px;" >kW</td>
		        <td class="label">主变台数：</td>
		        <td class="dom"><input type="text" id="MT_NUM" name="MT_NUM" value="${ETF.MT_NUM}" /></td>        
		      </tr>
		      <tr>
		        <td class="label">电厂性质：</td>
		        <td class="dom">
		          <select id="PLA_CHAR" name="PLA_CHAR">
		          </select>
		        </td>
		        <td class="label">联系电话：</td>
		        <td colspan="3" class="dom"> <input type="text" id="PHONE" name="PHONE" value="${ETF.PHONE}"></td>
		      </tr>
		      <tr>
		        <td class="label">地　址：</td>
		        <td colspan="3" class="dom2"><input type="text" id="SUBS_ADDR" name="SUBS_ADDR" value="${ETF.SUBS_ADDR}" /></td>
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
        	<input class="input2" type="button" id="saveTg" value="保存电厂" onClick="saveEle();" />
          <input class="input2" type="button" id="deleteTg" value="删除电厂" onClick="deleteEle();" />
        </td>
      </tr>
    </table>
  </div>
  </form>
</div>
</body>
</html>