<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>厂站编辑</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveJson.js"></script>
<script type="text/javascript">
	var etfML_ID='${ETF.ML_ID}';
  var etfSUBS_ID='${ETF.SUBS_ID}';
  //用来判断‘上一步’按钮应该跳转到哪个画面
  var etfPARAM=parent.tempPARAM;
  var etfSUBS_TYPE=parent.tempSUBS_TYPE;
  var tempCZMC=parent.tempCZMC;
  var tempVOLT_GRADE=parent.tempVOLT_GRADE;
  var tempWantedPerPage=parent.tempWantedPerPage;
</script>
</head>
<body  onload="loadVolt('${ETF.VOLT_GRADE}'),loadType('${ETF.ML_TYPE}')">
<div class="tab"><em>母线信息</em></div>
<div class="tab_con">
  <div class="main">
  	<form method="post" name="custana" id="mldetail">
     <table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10%" class="label"><font color="red">* </font>母线编号：</td>
        <td width="23%" class="dom"><input type="text" id="ML_NO" name="ML_NO" value="${ETF.ML_NO}" /></td>
        <td width="10%" class="label"><font color="red">* </font>母线名称：</td>
        <td width="23%" class="dom"><input type="text" id="ML_NAME" name="ML_NAME" value="${ETF.ML_NAME}" /></td>
        <td width="10%" class="label">电压等级：</td>
        <td width="23%" class="dom">
          <select id="VOLT_GRADE" name="VOLT_GRADE">
          </select>
        </td>
      </tr>
      <tr>
        <td width="10%" class="label">不平衡阈值：</td>
        <td width="23%" class="dom"><input type="text" id="NB_THRESHOLD" name="NB_THRESHOLD" value="${ETF.NB_THRESHOLD}" />%</td>
        <td width="10%" class="label">母线类型：</td>
        <td colspan="3" class="dom">
          <select id="ML_TYPE" name="ML_TYPE">
          </select>
        </td>
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
          	<input class="input3" type="button" id="saveMla" value="保存母线" onClick="saveMl();" />
            <input class="input3" type="button" id="deleteTg" value="删除母线" onClick="deleteMl();" />
          </td>
        </tr>
    </table>
   </form>
    </div>
    <script type="text/javascript">
       	//保存更改的母线信息
      function saveMl(){
      	if(mlCheck()){
      		var ML_BEF='${ETF.ML_NO}';
      		var ML_AF=$("#ML_NO").val();
      		var VOLT_BEF='${ETF.VOLT_GRADE}';
      		$.ajax({
           type:"POST",
           url:"112031.dow",
           dataType:"json",
           data:$("#mldetail").serialize()+'&SUBS_ID='+etfSUBS_ID+'&ML_ID='+etfML_ID+'&ML_BEFORE='+ ML_BEF+'&ML_NO='+ML_AF+'&FLG=UPD&PARAM='+etfPARAM+'&VOLT_BEFORE='+VOLT_BEF,
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
      	   //删除母线
      function deleteMl(){
      	var tempML_NO='${ETF.ML_NO}';
        if(confirm("是否删除该母线")){
	      		$.ajax({
           type:"POST",
           url:"112031.dow",
           dataType:"json",
           data:{SUBS_ID:etfSUBS_ID,ML_ID:etfML_ID,ML_NO:tempML_NO,FLG:'DEL',SUBS_TYPE:etfSUBS_TYPE},
           success:function(data){
                   	  if(data.MSG_TYP=='N'){
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
</div>
</body>
</html>