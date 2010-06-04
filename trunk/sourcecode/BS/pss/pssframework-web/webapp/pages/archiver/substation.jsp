<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>变电站档案新增第三步</title>
<link rel="stylesheet" type="text/css" href="../../css/mainframe.css" />
<script type="text/javascript" src="../../js/jquery.js"></script>
<script type="text/javascript" src="../../js/frame/tableEx.js"></script>
<script type="text/javascript" src="../../js/frame/component.js"></script>
<script type="text/javascript" src="../../js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="../../js/frame/const.js"></script>
<script type="text/javascript" src="../../js/archive/archiveJson.js"></script>
<script type="text/javascript">
	//接收从主页面传过来的变电站标识
 var tempSUBS_ID=parent.tempSUBS_ID;
 //接受从后台传来的SUBS_ID
 var etfSUBS_ID='${ETF.SUBS_ID}';
  var etfSUBS_NO='${ETF.SUBS_NO}';
  var etfSUBS_TYPE='${ETF.SUBS_TYPE}';
  var etfPARAM=parent.tempPARAM;
  var tempWantedPerPage=parent.tempWantedPerPage;
	//加载页面，查询出该变电站详细信息
 function station(){
 	if(${empty ETF.TXN_CD}){
 	window.location.href="112034.dow?SUBS_ID="+tempSUBS_ID+"&FLG=QUERY";
 	}
 }
 //保存更改的变电站信息
 function SaveSt(){
 	if(subCheck()){
 		var SUBS_BEF='${ETF.SUBS_NO}';
 		var SUBS_AF=$("#SUBS_NO").val();
 		$.ajax({
      type:"POST",
      url:"112034.dow",
      dataType:"json",
      data:$("#sdetail").serialize()+'&SUBS_ID='+etfSUBS_ID+'&SUBS_BEFORE='+ SUBS_BEF+'&SUBS_NO='+SUBS_AF+'&FLG=UPD&SUBS_TYPE='+etfSUBS_TYPE,
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
function deleteSt(){
		if(confirm("是否删除该变电站")){
				$.ajax({
      type:"POST",
      url:"112021.dow",
      dataType:"json",
      data:{SUBS_ID:etfSUBS_ID,SUBS_NO:etfSUBS_NO,SUBS_TYPE:etfSUBS_TYPE},
      success:function(data){
              	  if(data.MSG_TYP=='N'){
              	  	alert(data.RSP_MSG);
              	  	if(etfPARAM==''||etfPARAM=="null"){
              	    window.parent.location.href="../../jsp/archive/addSubstationDetail.jsp";
              	  }else{
              	  	window.parent.location.href="112041.dow?WANTEDPERPAGE="+tempWantedPerPage;
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
<body onload="station(),loadOrg('${ETF.ORG_NO}'),loadVolt('${ETF.VOLT_GRADE}'),loadStatus('${ETF.SUBS_STATUS}')">
<div class="tab"><em>变电站信息</em></div>
<div class="tab_con">
  <div class="main">
  	<form method="post" name="custana" id="sdetail">
   <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td width="13%" class="label"><font color="red">* </font>变电站编号：</td>
            <td width="20%" class="dom"><input type="text" id="SUBS_NO" name="SUBS_NO" value="${ETF.SUBS_NO}"/></td>
            <td width="13%" class="label"><font color="red">* </font>变电站名称：</td>
            <td width="20%" class="dom"><input type="text" id="SUBS_NAME" name="SUBS_NAME" value="${ETF.SUBS_NAME}"/></td>
            <td width="13%" class="label">电压等级：</td>
            <td width="20%" class="dom">
              <select id="VOLT_GRADE" name="VOLT_GRADE">
              </select>
            </td>
          </tr>
          <tr>
            <td width="10%" class="label"><font color="red">* </font>管理单位：</td>
            <td width="23%" class="dom">
              <select id="orgNo" name="orgNo">
              </select>
            </td>
            <td width="10%" class="label">考核电量：</td>
            <td width="23%" class="dom"> <input type="text" id="EQ_CHK" name="EQ_CHK" value="${ETF.EQ_CHK}" style="width:120px">kWh</td>
            <td width="10%" class="label">主变台数：</td>
            <td width="23%" class="dom"><input type="text" id="MT_NUM" name="MT_NUM" value="${ETF.MT_NUM}"/></td>       
          </tr>
          <tr>
            <td class="label">  状  态  ：</td>
            <td class="dom">
              <select id="SUBS_STATUS" name="SUBS_STATUS" >
              </select>
            </td>
            <td class="label">站损阈值：</td>
            <td colspan="3" class="dom"> <input type="text" id="SUBS_THRESHOLD" name="SUBS_THRESHOLD" value="${ETF.SUBS_THRESHOLD}" style="width:120px">%</td>
          </tr>
          <tr>
            <td class="label">联系电话：</td>
            <td class="dom"><input type="text" id="PHONE" name="PHONE" value="${ETF.PHONE}"/></td>
            <td class="label">地址：</td>
            <td colspan="3" class="dom"><input type="text" id="SUBS_ADDR" name="SUBS_ADDR" value="${ETF.SUBS_ADDR}"/></td>
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
          	<input class="input4" type="button" id="deleteTg" value="保存变电站" onClick="SaveSt();" />
            <input class="input4" type="button" id="deleteTg" value="删除变电站" onClick="deleteSt();" />
          </td>
        </tr>
    </table>
  </form>
  </div>
</div>
</body>
</html>