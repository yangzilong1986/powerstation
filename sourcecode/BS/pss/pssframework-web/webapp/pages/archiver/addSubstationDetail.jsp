<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>变电站档案新增第二步</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveJson.js"></script>
<script type="text/javascript">
	var tempSUBS_NO='${ETF.SUBS_NO}';
	var tempSUBS_ID='${ETF.SUBS_ID}';
//上一步
function lastStep(){
  window.location.href="<peis:contextPath/>/jsp/archive/addStranSubstation.jsp";
}
function submitfun(param){
	if(subCheck()){
   $.ajax({
      type:"POST",
      url:"112034.dow",
      dataType:"json",
      data:$("#station").serialize()+'&flg=ADD&ajax=a&SUBS_BEFORE='+tempSUBS_NO+'&SUBS_ID='+tempSUBS_ID,
      success:function(data){
      	     if(data.MSG_TYP=='E'){
              	  	alert(data.RSP_MSG);
              	  }
             else{
              if(param=='FINSH'){
              	    if(confirm("是否继续新增变电站")){
                         window.location.href="<peis:contextPath/>/jsp/archive/addSubstationDetail.jsp";
                       }else{
                        window.location.href="<peis:contextPath/>/jsp/archive/addStranSubstation.jsp";
                        }
              	}
              	else{
                     window.location.href="<peis:contextPath/>/jsp/archive/addSubstation.jsp?SUBS_ID="+data.SUBS_ID+"&ORG_NO="+data.ORGNO+"&SUBS_TYPE=1";
              		}
                }
              },
      error :function(XMLHttpRequest,textStatus,errorThrown){
             alert("通迅失败");
             }
      })
  }
}

</script>
</head>
<body onload="loadOrg('${ETF.ORGNO}'),loadVolt('${ETF.VOLT_GRADE}'),loadStatus('${ETF.SUBS_STATUS}')">
<div id="body">
  <jsp:include page="archiveTabs.jsp" flush="true">
      <jsp:param value="4" name="pageType"/>
    </jsp:include>
  <div id="main">
  	<form method="post" name="custana" id="station">
    <div class="tab"><em>变电站基本信息</em></div>
    <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 123));">
      <div class="main">
        <table border="0" cellpadding="0" cellspacing="0" width="900" align="center">
          <tr>
            <td width="13%" class="label"><font color="red">* </font>变电站编号：</td>
            <td width="20%" class="dom"><input type="text" id="SUBS_NO" name="SUBS_NO" value="${ETF.SUBS_NO}" /></td>
            <td width="13%" class="label"><font color="red">* </font>变电站名称：</td>
            <td width="20%" class="dom"><input type="text" id="SUBS_NAME" name="SUBS_NAME" value="${ETF.SUBS_NAME}" /></td>
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
            <td class="label">考核电量：</td>
            <td class="dom"> <input type="text" id="EQ_CHK" name="EQ_CHK" value="${ETF.EQ_CHK}" style="width: 115px;" >kWh</td>
            <td class="label">主变台数：</td>
            <td class="dom"><input type="text" id="MT_NUM" name="MT_NUM" value="${ETF.MT_NUM}" /></td>       
          </tr>
          <tr>
            <td class="label">  状  态  ：</td>
            <td class="dom">
              <select id="SUBS_STATUS" name="SUBS_STATUS">
              </select>
            </td>
            <td class="label">站损阈值：</td>
            <td colspan="3" class="dom"> <input type="text" id="SUBS_THRESHOLD" name="SUBS_THRESHOLD" value="${ETF.SUBS_THRESHOLD}" style="width: 115px;" >&nbsp;&nbsp;&nbsp;%</td>
          </tr>
          <tr>
            <td class="label">联系电话：</td>
            <td class="dom"><input type="text" id="PHONE" name="PHONE" value="${ETF.PHONE}" /></td>
            <td class="label">地址：</td>
            <td colspan="3" class="dom"><input type="text" id="SUBS_ADDR" name="SUBS_ADDR" value="${ETF.SUBS_ADDR}" /></td>
          </tr>
          <tr>
            <td rowspan="3" class="label">备　注：</td>
            <td class="dom" colspan="5">
              <textarea class="input_textarea3" name="REMARK" id="REMARK" style="width:600;height:80">${ETF.REMARK}</textarea>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div class="guidePanel">
      <input type="button" id="last" value="上一步" class="input1" onclick="lastStep();" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="next" value="下一步" class="input1" onclick="submitfun('NEXT');" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="finish" value="完 成" class="input1" onclick="submitfun('FINSH');" />
    </div>
    </form>
  </div>
</div>
</body>
</html>