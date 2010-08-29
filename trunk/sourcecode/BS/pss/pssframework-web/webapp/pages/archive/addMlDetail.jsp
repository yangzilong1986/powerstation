<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>操作&amp;数据</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/archiveCss.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveJson.js"></script>
<script type="text/javascript">
	var tempSUBS_ID=<%=request.getParameter("SUBS_ID")%>;
	var tempPARAM=<%=request.getParameter("PARAM")%>;
	var tempSUBS_TYPE=<%=request.getParameter("SUBS_TYPE")%>;
	var tempCZMC="<%=request.getParameter("CZMC")%>";
  var tempVOLT_GRADE="<%=request.getParameter("VOLT_GRADE")%>";
  var tempWantedPerPage="<%=request.getParameter("WANTEDPERPAGE")%>";
</script>
</head>
<body style="overflow: hidden;" onload="loadVolt('${ETF.CODE}'),loadType('${ETF.CODE_TYPE}')">
<div id="main" style="height: 100%;">
	<form method="post" name="ml" id="mldetail">
  <div class="tab"><em>母线信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 80));">
    <div class="main">
     <table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10%" class="label"><font color="red">* </font>母线编号：</td>
        <td width="23%" class="dom"><input type="text" id="ML_NO" name="ML_NO" value="" /></td>
        <td width="10%" class="label"><font color="red">* </font>母线名称：</td>
        <td width="23%" class="dom"><input type="text" id="ML_NAME" name="ML_NAME" value="" /></td>
        <td width="10%" class="label">电压等级：</td>
        <td width="23%" class="dom">
          <select id="VOLT_GRADE" name="VOLT_GRADE">
          </select>
        </td>
      </tr>
      <tr>
        <td width="10%" class="label">不平衡阈值：</td>
        <td width="23%" class="dom"><input type="text" id="NB_THRESHOLD" name="NB_THRESHOLD" value="" />%</td>
        <td width="10%" class="label">母线类型：</td>
        <td colspan="3" class="dom">
          <select id="ML_TYPE" name="ML_TYPE">
          </select>
        </td>
      </tr>
      <tr>
        <td rowspan="3" class="label">备　注：</td>
        <td class="dom" colspan="5">
          <textarea class="input_textarea3" name="REMARK" id="REMARK" style="width:600;height:80"></textarea>
        </td>
      </tr>
    </table>
    </div>
  </div>
  <div class="guidePanel">
    <input class="input1" type="button" id="save" value="保 存" onclick="saveMl();" />
  </div>
  </form>
</div>
<script type="text/javascript">
//保存母线信息
function saveMl(){
	if(mlCheck()){
		 $.ajax({
      type:"POST",
      url:"112031.dow",
      dataType:"json",
      data:$("#mldetail").serialize()+'&FLG=ADD&SUBS_ID='+tempSUBS_ID+'&SUBS_TYPE='+tempSUBS_TYPE,
      success:function(data){
      	     if(data.MSG_TYP=='E'){
              	  	alert(data.RSP_MSG);
              	  }
             else{
             	  alert(data.RSP_MSG);
             	  if(data.SUBS_TYPE=='1'){
             	    top.getMainFrameObj().location.reload();
               	}
               	if(data.SUBS_TYPE=='2'){
               		 top.getMainFrameObj().location.reload();
               	}
               	if(data.SUBS_TYPE=='3'){
               		 top.getMainFrameObj().location.reload();
               	}
               	parent.GB_hide();
                }
              },
      error :function(XMLHttpRequest,textStatus,errorThrown){
             alert("通迅失败");
             }
      })
	   }
 }
 </script>
</body>
</html>