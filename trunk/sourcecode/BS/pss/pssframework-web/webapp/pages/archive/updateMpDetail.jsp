<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>专电厂(电表信息)</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
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
	var tempMP_ID='${ETF.MP_ID}';
	var tempSUBS_ID='${ETF.SUBS_ID}';
	var tempGPSNPLUSE='${ETF.GPSNPLUSE}';
	var tempSUCRAT_CPT_ID='${ETF.SUCRAT_CPT_ID}';
	var tempCOMPUTE_FLAG='${ETF.COMPUTE_FLAG}';
	var tempSUCRAT_CPT_ID_MC='${ETF.SUCRAT_CPT_ID_MC}';
	var tempCOMPUTE_FLAG_MC='${ETF.COMPUTE_FLAG_MC}';
	var tempCUST_ID='${ETF.CUST_ID}';
	var tempCUST_NAME='${ETF.CUST_NAME}';
		//用来判断‘上一步’按钮应该跳转到哪个画面
  var etfPARAM=parent.tempPARAM;
  var etfSUBS_TYPE=parent.tempSUBS_TYPE;
  var tempCZMC=parent.tempCZMC;
  var tempVOLT_GRADE=parent.tempVOLT_GRADE;
  var tempWantedPerPage=parent.tempWantedPerPage;
  var tempMP_ADDR='${ETF.MP_ADDR}';
  var contextpath="<peis:contextPath/>";
$(document).ready(function(){
	loadSelectOption3('112045.dow','${ETF.TERM_ID}','TERM_ID','1'); //终端
	$('#LINE').hide();
  $('#LINEID').hide();
	//是否接脉冲
  if(tempGPSNPLUSE!=''){
  	$('#isPulse').attr("checked","true");
  	  if(tempSUCRAT_CPT_ID_MC=='1'){
  		$('#SUCRAT_CPT_ID_MC').attr("checked","true");
  		};
  		if(tempCOMPUTE_FLAG_MC=='1'){
  		$('#COMPUTE_FLAG_MC').attr("checked","true");
  		};
  	$('#pulseShow').show();
  	}else{
  		$('#pulseShow').hide();
  		}

  $('#isPulse').click(function(){
        if($('#isPulse').attr("checked")==true){
            $('#pulseShow').show();
        }else{
            $('#pulseShow').hide();
        }
    })
    
 //485信息
  if(tempSUCRAT_CPT_ID=='1'){
  		$('#SUCRAT_CPT_ID').attr("checked","true");
  		};
  		if(tempCOMPUTE_FLAG=='1'){
  		$('#COMPUTE_FLAG').attr("checked","true");
  		};
  $('#oneLine').click(function(){
        if($('#oneLine').attr("checked")==true){
            $('#specialCust').show();
            $('#specialCust1').show();
            loadsKx('');//下拉框馈线
            loadsUmp('02');//电表用途
        }else{
            $('#specialCust').hide();
            $('#specialCust1').hide();
            loadLines('');//下拉框线路信息
            loadsUmp('06');//电表用途   
        }
    })
    
//装表位置
if(tempMP_ADDR=='1' || tempMP_ADDR=='2' || tempMP_ADDR=='3'){
	$('#LINE').hide();
  $('#LINEID').hide();
	$("#change").html("主变：");
  loadTran('${ETF.OBJECT_ID}');//下拉框变压器
  loadsUmp('${ETF.MP_USEAGE}');//电表用途
  $('#oneLine').attr("disabled",true);
  $('#oneLine').attr("checked",false);
  $('#specialCust').hide();
  $('#specialCust1').hide();
}else if(tempMP_ADDR==5){
  $("#change").html("站用变：");
  loadTranZ('${ETF.OBJECT_ID}');//下拉框站用变
  loadsUmp('${ETF.MP_USEAGE}');
  $('#LINE').show();
  $('#LINEID').show();
  loadSelectOption3('112009.dow','${ETF.LINE_ID}','LINE_ID','1');
  $('#oneLine').attr("disabled",true);
  $('#oneLine').attr("checked",false);
  $('#specialCust').hide();
  $('#specialCust1').hide();
}else if(tempMP_ADDR==4){
	$('#LINE').hide();
  $('#LINEID').hide();
	$("#change").html("线路：");
	$('#oneLine').attr("disabled",false);
	if(tempCUST_ID!=''){
   	 $('#oneLine').attr("checked","true");
     $('#specialCust').show();
     $('#specialCust1').show();
     $('#specialCust3').val(tempCUST_NAME);
     $('#specialCust5').val(tempCUST_ID);
     loadsKx('${ETF.OBJECT_ID}');//下拉框馈线
     loadsUmp('${ETF.MP_USEAGE}');//电表用途
   }else{
   	 $('#specialCust').hide();
     $('#specialCust1').hide();
     loadLines('${ETF.OBJECT_ID}');//下拉框线路信息
     loadsUmp('${ETF.MP_USEAGE}');//电表用途
   	} 
	}	
});
function change(){
	if($('#MP_ADDR').val()==5 ){
	      $("#change").html("站用变：");
        loadTranZ('');//下拉框站用变
        loadsUmp('07');
        $('#LINE').show();
        $('#LINEID').show();
        $('#LINE_ID').attr("disabled",false);
        loadSelectOption3('112009.dow','${ETF.LINE_ID}','LINE_ID','1');
        $('#oneLine').attr("disabled",true);
        $('#oneLine').attr("checked",false);
        $('#specialCust').hide();
        $('#specialCust1').hide();
	      }
	  if($('#MP_ADDR').val()<4 ){
	  	  $('#LINE').hide();
        $('#LINEID').hide();
        $('#LINE_ID').attr("disabled",true);
	      $("#change").html("主变：");
         loadTran('');//下拉框主变
         loadsUmp('05');//电表用途
        $('#oneLine').attr("disabled",true);
        $('#oneLine').attr("checked",false);
        $('#specialCust').hide();
        $('#specialCust1').hide();
          }
	  if($('#MP_ADDR').val()==4 ){
	    	$('#LINE').hide();
        $('#LINEID').hide();
        $('#LINE_ID').attr("disabled",true);
	      $("#change").html("线路：");
	      loadLines('');//下拉框线路
	      $('#oneLine').attr("disabled",false);
	      loadsUmp('06');//电表用途
	     } 
}
 function loadSelectOption3(txnCode , value, id ,nallFlag){
	$("#"+id).load(txnCode+"?output=/jsp/queryadv/selectOption.jsp&SELECT_ID="+value+"&SUBS_ID="+tempSUBS_ID+"&NALL_FLAG="+nallFlag,null,function(){
		var converted = new Ext.form.ComboBox({
		    typeAhead: true,
		    triggerAction: 'all',
		    transform: id ,
		    forceSelection: true,
		     width:140               //宽度 
		});
		});
}
//接入终端
function addTerminalSubs(){
   var url=contextpath+"/jsp/archive/getTerminal.jsp?SUBS_ID="+tempSUBS_ID;
   top.showDialogBox("终端定位",url, 450, 750);
}
function specialCust(){
   var url=contextpath+"/jsp/archive/specialCust.jsp";
   top.showDialogBox("专线用户定位",url, 450, 750);
}

</script>
</head>
<body onload="loadsMp('${ETF.METER_TYPE}','${ETF.MP_USEAGE}','${ETF.PROTOCOL_NO}','${ETF.PT_RATIO}','${ETF.CT_RATIO}','${ETF.RATED_VOLT}','${ETF.RATED_EC}','${ETF.MADE_FACTORY}','${ETF.BTL}','${ETF.DEV_TYPE}',etfSUBS_TYPE),loadsMp2('${ETF.MEAS_MODE}','${ETF.WIRING_MODE}','${ETF.MP_DRCT}','${ETF.MP_ADDR}','${ETF.STATUS}','${ETF.METER_DIGITS}','${ETF.IS_MAS}',etfSUBS_TYPE),loadsPl('${ETF.PLUSE_CONSTANT}')">
<div class="tab"><em>电表信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 25));">
  <form method="post" name="mpu" id="mpudetail">
  <div class="main">
      <table border="0" cellpadding="0" cellspacing="0" align="center">
      <tr>
       <td width="10%" class="label">表 类 型：</td>
       <td width="23%" class="dom">
        <select name="METER_TYPE" id="METER_TYPE">
        </select>
       </td>
       <td width="10%" class="label"><font color="red">* </font>计量点名称：</td>
       <td width="23%" class="dom"><input type="text" id="MP_NAME" name="MP_NAME" value="${ETF.MP_NAME}"></td>
       <td width="10%" class="label">主 备 表：</td>
       <td width="23%" class="dom">
         <select name="IS_MAS" id="IS_MAS" style="width: 80">
         </select>        
       </td>
      </tr>
      <tr>
       <td width="10%" class="label"><font color="red">* </font>资产编号：</td>
       <td width="23%" class="dom"><input type="text" id="ASSET_NO" name="assetNo" value="${ETF.ASSET_NO}"/></td>
       <td width="10%" class="label"><font color="red">* </font>表 地 址：</td>
       <td width="23%" class="dom"><input type="text" id="GP_ADDR" name="gpAddr" value="${ETF.GP_ADDR}"/></td>
       <td width="10%" class="label">通讯端口：</td>
       <td width="23%" class="dom"><input type="text" id="PORT" name="port" value="${ETF.PORT}"/></td>
      </tr>
      <tr>
       <td width="10%" class="label">电表用途：</td>
       <td width="23%" class="dom">
        <select name="MP_USEAGE" id="MP_USEAGE">
        </select>
       </td>
       <td width="10%" class="label">表规约：</td>
       <td width="23%" class="dom">
        <select name="PROTOCOL_METER" id="PROTOCOL_METER">
        </select>
       </td>
        <td width="10%" class="label">设备厂家：</td>
        <td width="23%" class="dom">
          <select name="MADE_FACTORY" id="MADE_FACTORY">
          </select>
        </td>
      </tr>
      <tr>
       <td width="10%" class="label">CT变比：</td>
       <td width="23%" class="dom">
          <select name="CT_TIMES" id="CT_TIMES">
          </select>
       </td>
       <td width="10%" class="label">PT变比：</td>
       <td width="23%" class="dom">
          <select name="PT_TIMES" id="PT_TIMES">
          </select>
       </td>
        <td width="10%" class="label">波 特 率：</td>
        <td width="23%" class="dom">
          <select name="BTL" id="BTL">
          </select>
       </td>
      </tr>
       <tr>
       <td width="10%" class="label">额定电压：</td>
       <td width="23%" class="dom">
         <select name="RATED_VOLT" id="RATED_VOLT">
         </select>
       </td>
       <td width="10%" class="label">额定电流：</td>
       <td width="23%" class="dom">
         <select name="RATED_EC" id="RATED_EC">
         </select>
       </td>
        <td width="10%" class="label">装置类型：</td>
        <td width="23%" class="dom">
          <select name="DEV_TYPE" id="DEV_TYPE">
          </select>
       </td>       
      </tr>
      <tr>
       <td width="10%" class="label">计量方式：</td>
        <td width="23%" class="dom">
          <select name="MEAS_MODE" id="MEAS_MODE">
          </select>
       </td>
       <td width="10%" class="label">相　线：</td>
       <td width="23%" class="dom">
         <select name="WIRING_MODE" id="WIRING_MODE">
         </select>
       </td>
       <td width="10%" class="label">采集终端：</td>
       <td width="23%" class="dom">
         <select name="TERM_ID" id="TERM_ID" style="width: 115">
         </select>
         <!--input type="button" id="addTerminal" value="..." onclick="addTerminalSubs();" style="width:20px"/-->
       </td>
      </tr>
      <tr>
        <td width="10%" class="label">装表方向：</td>
        <td width="23%" class="dom">
          <select name="MP_DRCT" id="MP_DRCT">
          </select>
        </td>
        <td width="10%" class="label">出厂编号：</td>
        <td width="23%" class="dom"><input type="text" id="LEAVE_FAC_NO" name="LEAVE_FAC_NO" value="${ETF.LEAVE_FAC_NO}"/></td> 
        <td width="10%" class="label">状　态：</td>
        <td width="23%" class="dom">
          <select name="STATUS" id="STATUS">
          </select>
        </td>
      </tr>
      <tr>
        <td width="10%" class="label">装表位置：</td>
        <td width="23%" class="dom">
          <select name="MP_ADDR" id="MP_ADDR" onchange="change();">
          </select>
        </td>
        <td class="label" id="change">主变：</td>
        <td class="dom" id="TRAN">
          <select name="change1" id="change1" >
          </select>
        </td>
        <td width="10%" class="label" id="LINE">线路：</td>
        <td width="23%" class="dom" id="LINEID">
          <select name="LINE_ID" id="LINE_ID">
          </select>
        </td>
      </tr>
      <tr>
       <td width="10%" class="label">虚拟户号：</td>
       <td width="23%" class="dom">
       	 <input type="text" name="CUST_NO_PS" id="CUST_NO_PS" value="${ETF.CUST_NO_PS}"/>
       </td>
       <td width="10%" class="label">翻转位数：</td>
       <td width="23%" class="dom">
          <select name="METER_DIGITS" id="METER_DIGITS">
          </select>
       </td>
        <td width="10%" class="label"></td>
        <td width="23%" class="dom">
       </td>
      </tr> 
      <tr>
        <td width="2%" class="label">
          <input type="checkbox" name="isPulse" id="isPulse"/>接脉冲

        </td>
        <td width="4%" class="label">
          <input type="checkbox" name="oneLine" id="oneLine"/>专线用户
        </td>
        <td width="10%" class="label" id="specialCust">用户：</td>
        <td width="23%" class="dom" id="specialCust1">
         <input type="text" id="specialCust3" name="specialCust3" disabled="true"/>
         <input type="hidden" id="specialCust5" name="specialCust5"/>
         <input type="button" id="specialCust4" value="..." onclick="specialCust();" style="width:20px"/>
       </td>
      </tr>    
     </table>
    <div id="485Show">
      <div class="data3"><span>485信息</span></div>
      <div class="data3_con">
       <table border="0" cellpadding="0" cellspacing="0" width="100%">
           <tr>
             <td align="left" class="label"><font color="red">* </font>计量点序号：</td>
             <td class="dom"><input type="text" id="GP_SN" name="gpSn485" size="8" value="${ETF.GP_SN}"/></td>
             <td >
               <input type="checkbox" id="SUCRAT_CPT_ID" name="SUCRAT_CPT_ID" />功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <input type="checkbox" id="COMPUTE_FLAG" name="COMPUTE_FLAG" />电量累计
             </td>
           </tr>
        </table>
      </div>
    </div>
    <div id="pulseShow">
      <div class="data3"><span>脉冲信息</span></div>
      <div class="data3_con">
       <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td align="right"><font color="red">* </font>计量点序号：</td>
            <td align="left"><input type="text" id="GP_SNP" name="gpSnPluse" size="8" value="${ETF.GPSNPLUSE}"/></td>
            <td align="right">脉冲类型：</td>
            <td align="left">
                <select name="PLUSE_CONSTANT" id="PLUSE_CONSTANT">
                </select>
            </td>
            <td align="right">脉冲常数：</td>
            <td align="left"><input type="text" id="METER_CONSTANT" name="METER_CONSTANT" size="8" value="${ETF.METER_CONSTANT}"/></td>
            <td>
              <input type="checkbox" id="SUCRAT_CPT_ID_MC" name="SUCRAT_CPT_ID_MC"/>功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="checkbox" id="COMPUTE_FLAG_MC" name="COMPUTE_FLAG_MC"/>电量累计
            </td>
          </tr>
       </table>
      </div>
    </div>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
         <tr>
           <td align="right">
           	<input class="input2" type="button" id="saveTg" value="保存电表" onClick="saveEl();" />
            <input class="input2" type="button" id="deleteTg" value="删除电表" onClick="deleteEl();" />
           </td>
         </tr>
       </table>
  </div>
  </form>
</div>
<script type="text/javascript">
	  //保存更改的电表信息
      function saveEl(){
      		var tempRatCT=$("#CT_TIMES").find("option:selected").text();
          var tempRatPT=$("#PT_TIMES").find("option:selected").text();
      	if(mpCheck()){
      		var ASSET_BEF='${ETF.ASSET_NO}';
      		var GP_SNBEF='${ETF.GP_SN}';
      		var GPSNPLUSEBEF='${ETF.GPSNPLUSE}';
      		$.ajax({
           type:"POST",
           url:"112048.dow",
           dataType:"json",
           data:$("#mpudetail").serialize()+'&SUBS_ID='+tempSUBS_ID+'&MP_ID='+tempMP_ID+'&ASSET_BEFORE='+ ASSET_BEF
                +'&PARAM='+etfPARAM+'&GPSNPLUSE_B='+tempGPSNPLUSE+"&CT_TIMESGP="+tempRatCT
                +"&PT_TIMESGP="+tempRatPT+'&GP_SNBEFORE='+GP_SNBEF+'&GPSNPBEF='+GPSNPLUSEBEF,
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
     //删除电表
 function deleteEl(){
     	var tempASSET_NO='${ETF.ASSET_NO}';
   if(confirm("是否删除该电表")){
		   $.ajax({
        type:"POST",
        url:"112047.dow",
        dataType:"json",
        data:{SUBS_ID:tempSUBS_ID,MP_ID:tempMP_ID,FLG:'DEL',SUBS_TYPE:etfSUBS_TYPE,ASSET_NO:tempASSET_NO,PARAM:etfPARAM},
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
</body>
</html>