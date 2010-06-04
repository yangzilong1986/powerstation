<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>终端信息</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/archiveCss.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveJson.js"></script>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
<script type="text/javascript">
	var tempSUBS_ID=<%=request.getParameter("SUBS_ID")%>;
	//用来判断‘上一步’按钮应该跳转到哪个画面
	var tempPARAM=<%=request.getParameter("PARAM")%>;
	var tempSUBS_TYPE=<%=request.getParameter("SUBS_TYPE")%>;
	var tempCZMC="<%=request.getParameter("CZMC")%>";
  var tempVOLT_GRADE="<%=request.getParameter("VOLT_GRADE")%>";
  var tempWantedPerPage="<%=request.getParameter("WANTEDPERPAGE")%>";
$(document).ready(function(){
  $('#ACShow').hide();
  $('#ISAC').click(function(){
        if($('#ISAC').attr("checked")==true){
        	  //loadsTg2('');
        	  loadSelectOption3('407051.dow','${ETF.LINE_ID}','LINE_ID','1');
        	  loadsTg3('','');
        	  $('#ISAC').val("1");
        	if($('#PROTOCOL_NO').val()=='123'){
        	  $('#GP_SN').val("0");
        	}
            $('#ACShow').show();
        }else{
            $('#ACShow').hide();
        }
    })
    
   if($('#PROTOCOL_NO').val()==null){
    $('#ISAC').attr("disabled",true);
   }
 function loadSelectOption3(txnCode , value, id ,nallFlag){
	$("#"+id).load(txnCode+"?output=/jsp/queryadv/selectOption.jsp&SELECT_ID="+value+"&NALL_FLAG="+nallFlag,null,function(){
		var converted = new Ext.form.ComboBox({
		    typeAhead: true,
		    triggerAction: 'all',
		    transform: id ,
		    forceSelection: true,
		    width:140               //宽度 
		});
		});
}

loadSelectOption2('112053.dow','${ETF.MACH_NO}','MACH_NO','1');

loadSelectOption2('112054.dow','${ETF.CHANNEL_TYPE}','CHANNEL_TYPE','1')

function loadSelectOption2(txnCode , value, id ,nallFlag){
	$("#"+id).load(txnCode+"?output=/jsp/queryadv/selectOption.jsp&SELECT_ID="+value+"&NALL_FLAG="+nallFlag);
}

});
    


//保存
 function saveTerm(){
 	  var tempRatCT=$("#CT_RATIO").find("option:selected").text();
	  var tempRatPT=$("#PT_RATIO").find("option:selected").text();
	   if(termCheck()){
   	$.ajax({
      type:"POST",
      url:"112032.dow",
      dataType:"json",
      data:$("#termdetail").serialize()+'&SUBS_ID='+tempSUBS_ID+'&FLG=ADD'+'&SUBS_TYPE='+tempSUBS_TYPE+"&CT_TIMESGP="+tempRatCT+"&PT_TIMESGP="+tempRatPT,
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
	  // parent.GB_hide();
   }
}

function paramListData(){
	var COMM_P="";
	var PROTOCOL_P="";
	var TERM_P="";
  if($("#COMM_MODE").val()==null){
  	COMM_P=1;
  	}else{COMM_P=$("#COMM_MODE").val();}
  	
  if($("#PROTOCOL_NO").val()==null){
  	PROTOCOL_P=146;
  	}else{PROTOCOL_P=$("#PROTOCOL_NO").val();
  		}
  if(PROTOCOL_P==123){
  	$('#ISAC').attr("disabled",false); 
  	$('#GP_SN').val("0");
    $('#GP_SN').attr("disabled",true);
    $('#ADDR').hide();    
    $('#PHYSICS').hide();
    $('#PHYSICS_ADDR').attr("disabled",true);
  	}
  	
  	if(PROTOCOL_P!=123&&PROTOCOL_P!=146){
  	$('#ISAC').attr("disabled",false);  
  	}
  if(PROTOCOL_P==146){
  	$('#ISAC').attr("checked",false);
  	$('#ISAC').attr("disabled",true);
  	$('#ACShow').hide();
  	$('#ADDR').show();    
    $('#PHYSICS').show();
    $('#PHYSICS_ADDR').attr("disabled",false);
  	}
  	
  if($("#TERM_TYPE").val()==null){
  	TERM_P=08;
  	}else{TERM_P=$("#TERM_TYPE").val();}	
  $.ajax({
     type:"POST",
     url:"112050.dow",
     dataType:"json",
     data:{COMM_MODE:COMM_P,PROTOCOL_NO:PROTOCOL_P,TERM_TYPE:TERM_P},
     success:function(data){
     	var context="";
     	if(data.TERMP!=undefined){
     	var alldata=data.TERMP;
     	var alldatalength=alldata.length;
     	for(var i=0;i<alldatalength;i++){
     		context += "<tr>";
     		context += "<td style=\"display:none;\"> <input type=\"text\" name=\"NUMS\" value=\""+ alldatalength +"\" style=\"width: 200px; height: 21px;\" />" + "</td>";
     		context += "<td style=\"display:none;\"> <input type=\"text\" name=\"PARA_CODE_"+i+"\" value=\""+ alldata[i].PARA_CODE +"\" style=\"width: 200px; height: 21px;\" />" + "</td>";
     		context += "<td style=\"display:none;\"> <input type=\"text\" name=\"COMMANDITEM_CODE_"+i+"\" value=\""+ alldata[i].COMMANDITEM_CODE +"\" style=\"width: 200px; height: 21px;\" />" + "</td>";
     		context += "<td style=\"display:none;\"> <input type=\"text\" name=\"DATAITEM_CODE_"+i+"\" value=\""+ alldata[i].DATAITEM_CODE +"\" style=\"width: 200px; height: 21px;\" />" + "</td>";
        context += "<td style=\"display:none;\"> <input type=\"text\" name=\"DATAITEM_NAME_"+i+"\" value=\""+ alldata[i].DATAITEM_NAME +"\" style=\"width: 200px; height: 21px;\" />" + "</td>";
     		context += "<td align=\"center\">" + alldata[i].ROWNUM + "</td>";
     		context += "<td>" + alldata[i].DATAITEM_NAME + "</td>";
     		context += "<td> <input type=\"text\" name=\"DATAITEM_VALUE_"+i+"\" value=\""+ alldata[i].DATAITEM_VALUE +"\" style=\"width: 200px; height: 21px;\" />" + "</td>";
     		context += "</tr>";
      		}
      	 }
      $("#tbodypm").html(context);
       }
    })
	}
</script>
</head>
<body style="overflow: hidden;" onload="loadsTg1('','','','','',''),paramListData()">
<div id="main" style="height: 100%;">
  <div class="tab"><em>终端信息</em></div>
  <form method="post" name="term" id="termdetail">
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 80));">
    <div class="main">
      <table border="0" cellpadding="0" cellspacing="0">
           <tr>
            <td width="10%" class="label"><font color="red">* </font>资产编号：</td>
            <td width="23%" class="dom"><input type="text" name="assetNo" id="assetNo" value=""/></td>
            <td width="10%" class="label"><font color="red">* </font>逻辑地址：</td>
            <td width="3" class="dom"><input type="text" name="logicalAddr" id="logicalAddr" value=""/></td>
           </tr>
           <tr>
            <td width="10%" class="label">当前状态：</td>
            <td width="23%" class="dom">
              <select name="CUR_STATUS" id="CUR_STATUS">
              </select>
            </td>
            <td width="10%" class="label">设备规约：</td>
            <td width="23%" class="dom">
             <select name="PROTOCOL_NO" id="PROTOCOL_NO" onchange="paramListData()">
             </select>
            </td>
            <td width="10%" class="label">通讯方式：</td>
            <td width="24%" class="dom"> 
               <select name="COMM_MODE" id="COMM_MODE" onchange="paramListData()">
              </select>           
            </td>
           </tr>
           <tr>
            <td width="10%" class="label">相　线：</td>
            <td width="23%" class="dom">
             <select name="WIRING_MODE" id="WIRING_MODE">
             </select>
            </td>
            <td width="10%" class="label">设备厂家：</td>
            <td width="23%" class="dom">
              <select name="MADE_FAC" id="MADE_FAC">
              </select>
            </td>
            <td width="10%" class="label">终端类型：</td>
            <td width="24%" class="dom">
            <select name="TERM_TYPE" id="TERM_TYPE">
            	<option value="08">关口电能量终端</option>
            </select>
            </td>
           </tr>
           <tr>
            <td width="10%" class="label">终端型号：</td>
            <td width="23%" class="dom">
              <select name="MODEL_CODE" id="MODEL_CODE">
             </select>
            </td>
            <td width="10%" class="label">出厂编号：</td>
            <td width="23%" class="dom"><input type="text" name="LEAVE_FAC_NO" id="LEAVE_FAC_NO" value=""/></td>
            <td width="10%" class="label">产　权：</td>
            <td width="24%" class="dom">
             <select name="PR" id="PR">
             </select>
            </td>
           </tr>
           <tr>
            <td width="10%" class="label">机器编号：</td>
            <td width="23%" class="dom">
              <select name="MACH_NO" id="MACH_NO">
             </select>
            </td>
            <td width="10%" class="label">前置机通道：</td>
            <td width="23%" class="dom"><input type="text" name="FEP_CNL" id="FEP_CNL" value=""/></td>
            <td width="10%" class="label">通道类型：</td>
            <td width="24%" class="dom">
             <select name="CHANNEL_TYPE" id="CHANNEL_TYPE">
             </select>
            </td>
           </tr>
           
           <tr>
           <% int num2 = 1900; Date date=new Date(); int year=date.getYear()+num2;
            	int Day=date.getDate();
            	int month=date.getMonth()+1;
              String time=year+"-"+month+"-"+Day;
            	  %>
            <td width="10%" class="label">安装日期：</td>
            <td width="23%" class="dom_date">
             <input type="text" id="INSTALL_DATE" name="INSTALL_DATE" value=<%=time%> onfocus="peisDatePicker()" readonly="readonly" />
            </td>            
            <td width="10%" class="label" id="ADDR">物理地址：</td>
            <td width="23%" class="dom" id="PHYSICS">
             <input type="text" id="PHYSICS_ADDR" name="PHYSICS_ADDR"/>
            </td>
            <td width="10%" class="label">安装单位：</td>
            <td width="23%" class="dom">
             <input type="text" id="CONSTR_GANG" name="CONSTR_GANG" />
            </td>
           </tr>
           <tr>
            <td width="10%" colspan="2" align="center">
             <input type="checkbox" name="ISAC" id="ISAC" value="0">接交采
            </td>
           </tr>
      </table>
    </div>
    <div id="ACShow">
      <div class="data3"><span>交采信息</span></div>
      <div class="data3_con">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
           <tr>
            <td width="10%" class="label">计量点序号：</td>
            <td width="18%" class="dom">
              <input type="text" id="GP_SN" name="GP_SN"/>
            </td>
            <td width="10%" class="label">供电线路：</td>
            <td width="18%" class="dom">
             <select name="LINE_ID" id="LINE_ID">
             </select>
             <!--input type="button" id="searchLine" value="..." onclick="searchLine();" style="width:20px"/-->
            </td>
            <td width="28%"  colspan="2" align="center">
             <input type="checkbox" id="SUCRAT_CPT_ID" name="SUCRAT_CPT_ID" checked/>功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <input type="checkbox" id="COMPUTE_FLAG" name="COMPUTE_FLAG" checked/>电量累计
            </td>
           </tr>
           <tr>
            <td width="10%" class="label">端 口 号：</td>
            <td width="18%" class="dom_date">
             <input type="text" id="PORT" name="PORT" style="width: 46px" />
            </td>
            <td width="10%" class="label">CT变比：</td>
            <td width="18%" class="dom">
             <select name="CT_RATIO" id="CT_RATIO">
             </select>
            </td>
            <td width="10%" class="label">PT变比：</td>
            <td width="18%" class="dom">
             <select name="PT_RATIO" id="PT_RATIO">
             </select>
            </td>
           </tr>
          </table>
      </div>
    </div>
    <div id="paramShow">
      <div class="data2"><span>终端参数</span></div>
      <div class="data2_con">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <thead>
            <tr>
              <th>序号</th>
              <th>终端参数</th>
              <th>参数值</th>
            </tr>
          </thead>
          <tbody id="tbodypm">
            <!--tr>
              <td align="center">1</td>
              <td>密码算法编号</td>
              <td><input type="text" id="" name="" value="101" style="width: 200px; height: 21px;" /></td>
            </tr>
            <tr>
              <td align="center">2</td>
              <td>密钥</td>
              <td><input type="password" id="" name="" value="6666" style="width: 200px; height: 21px;" /></td>
            </tr-->
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <div class="guidePanel">
    <input class="input1" type="button" id="save" value="保 存" onclick="saveTerm();" />
  </div>
  </form>
</div>
</body>
</html>
