<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>操作&amp;数据</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/archiveCss.css" />
<script type="text/javascript" src="../../js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveJson.js"></script>
<script type="text/javascript">
	var tempSUBS_ID=<%=request.getParameter("SUBS_ID")%>;
	//用来判断‘上一步’按钮应该跳转到哪个画面
	var tempPARAM="<%=request.getParameter("PARAM")%>";
	var tempSUBS_TYPE=<%=request.getParameter("SUBS_TYPE")%>;
	var tempCZMC="<%=request.getParameter("CZMC")%>";
  var tempVOLT_GRADE="<%=request.getParameter("VOLT_GRADE")%>";
  var tempWantedPerPage="<%=request.getParameter("WANTEDPERPAGE")%>";
  function changeCnt(){
  	var selectVolt=$("#CIRCLE_CNT").val();
  	if(selectVolt==2){
  		$('#mid').hide();
  		$('#mid2').hide();
  		$('#S_VOLT').attr("value","");
  		}
  		if(selectVolt==3){
  		$('#mid').show();
  		$('#mid2').show();
  		loadsVolt('','','');
  		}
  	}
  
</script>
</head>
<body style="overflow: hidden;" onload="loadVolt(''),loadsVolt('','','')"> 
<div id="main" style="height: 100%;">
	<form method="post" name="custana" id="tddetail">
    <div class="tab"><em>变压器信息</em></div>
    <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 80));">
      <div class="main">
       <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="13%" class="label"><font color="red">* </font>变压器编号：</td>
          <td width="23%" class="dom"><input type="text" id="ASSET_NO" name="ASSET_NO" value="" /></td>
          <td width="13%" class="label"><font color="red">* </font>变压器名称：</td>
          <td width="23%" class="dom"><input type="text" id="TRAN_NAME" name="TRAN_NAME" value="" /></td>
          <td width="13%" class="label">变压器型号：</td>
          <td width="23%" class="dom">
            <select id="TRAN_CODE" name="TRAN_CODE">
            </select>
          </td>
        </tr>
        <tr>
          <td width="13%" class="label"> 容     量：</td>
          <td width="23%" class="dom"> <input type="text" id="TRAN_CAPA" name="TRAN_CAPA" value="">kVA</td>
          <td width="13%" class="label">变损阈值：</td>
          <td width="23%" class="dom"> <input type="text" id="TRAN_THRESHOLD" name="TRAN_THRESHOLD" value="">%</td>
          <td width="13%" class="label"> 圈     数：</td>
          <td width="23%" class="dom">
            <select id="CIRCLE_CNT" name="CIRCLE_CNT" onchange="changeCnt()">
            	<option value="2">2</option>
            	<option value="3" selected>3</option>
            </select>
          </td>
        </tr>
        <tr id="selVolt">
        	<td width="13%" class="label">高压侧电压等级：</td>
          <td width="23%" class="dom">
            <select id="F_VOLT" name="F_VOLT" onchange="changeFv()">
            </select>
          </td>
          <td width="13%" class="label" id="mid">中压侧电压等级：</td>
          <td width="23%" class="dom" id="mid2">
            <select id="S_VOLT" name="S_VOLT" onchange="changeSv()">
            </select>
          </td>
          <td width="13%" class="label">低压侧电压等级: </td>
          <td width="23%" class="dom">
            <select id="T_VOLT" name="T_VOLT">
            </select>
          </td>
        </tr>
        <tr>
        	<td width="13%" class="label">变压器类型：</td>
          <td width="23%" class="dom">
          	<select id="TRAN_TYPE" name="TRAN_TYPE">
            </select>
          </td>
          <td class="label">安装地址：</td>
          <td colspan="3" class="dom"><input type="text" id="INSTALL_ADDR" name="INSTALL_ADDR" value="" /></td>          
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
      <input class="input1" type="button" id="save" value="保 存" onclick="saveTd();" />
    </div>
  </form>
</div>
<script type="text/javascript">
	function saveTd(){
	if(tranDeCheck()){
		 $.ajax({
      type:"POST",
      url:"112030.dow",
      dataType:"json",
      data:$("#tddetail").serialize()+'&FLG=ADD&SUBS_ID='+tempSUBS_ID+'&SUBS_TYPE='+tempSUBS_TYPE,
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

//由高压侧电压等级，决定中和低压侧电压等级下拉框中数据
 function changeFv(){
 var tempFv=$("#F_VOLT option:selected").attr("name");
   loadFv(tempFv);
 	}
 
 	
 	//由中压侧电压等级，决定低压侧电压等级下拉框中数据
 	function changeSv(){
 	var tempSv=$("#S_VOLT option:selected").attr("name");
   loadSv(tempSv);
 		}	
</script>
</body>
</html>