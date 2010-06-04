<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>操作&amp;数据</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/archiveCss.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveJson.js"></script>
<script type="text/javascript">
  var etfTRAN_ID='${ETF.TRAN_ID}';
  var etfSUBS_ID='${ETF.SUBS_ID}'; 
  var etfSUBS_TYPE='${ETF.SUBS_TYPE}'; 
  //用来判断‘上一步’按钮应该跳转到哪个画面
  var etfPARAM=parent.tempPARAM;
  var etfSUBS_TYPE=parent.tempSUBS_TYPE;
  var tempCZMC=parent.tempCZMC;
  var tempVOLT_GRADE=parent.tempVOLT_GRADE;
  var tempWantedPerPage=parent.tempWantedPerPage;
 $(document).ready(function(){
  	var tempCIRCLE_CNT='${ETF.CIRCLE_CNT}';
  	$('#CIRCLE_CNT').attr("value",tempCIRCLE_CNT);
    var htmlVolt="";
  	if(tempCIRCLE_CNT==3){
  		   htmlVolt=htmlVolt+"<td width=\"13%\" class=\"label\">高压侧电压等级：</td>";
         htmlVolt=htmlVolt+"<td width=\"23%\" class=\"dom\">";
         htmlVolt=htmlVolt+"<select id=\"F_VOLT\" name=\"F_VOLT\" onchange=\"changeFv()\">";
         htmlVolt=htmlVolt+"</select>";
         htmlVolt=htmlVolt+"</td>";
         htmlVolt=htmlVolt+"<td width=\"13%\" class=\"label\" id=\"mid\">中压侧电压等级：</td>";
         htmlVolt=htmlVolt+"<td width=\"23%\" class=\"dom\" id=\"mid2\">";
         htmlVolt=htmlVolt+"<select id=\"S_VOLT\" name=\"S_VOLT\"  onchange=\"changeSv()\">";
         htmlVolt=htmlVolt+"</select>";
         htmlVolt=htmlVolt+"</td>";
         htmlVolt=htmlVolt+"<td width=\"13%\" class=\"label\">低压侧电压等级：</td>";
         htmlVolt=htmlVolt+"<td width=\"23%\" class=\"dom\">";
         htmlVolt=htmlVolt+"<select id=\"T_VOLT\" name=\"T_VOLT\">";
         htmlVolt=htmlVolt+"</select>";
         htmlVolt=htmlVolt+"</td>";
         $('#selVolt').html(htmlVolt);
  		}else{
  			 htmlVolt=htmlVolt+"<td width=\"13%\" class=\"label\">高压侧电压等级：</td>";
         htmlVolt=htmlVolt+"<td width=\"23%\" class=\"dom\">";
         htmlVolt=htmlVolt+"<select id=\"F_VOLT\" name=\"F_VOLT\" onchange=\"changeFv()\">";
         htmlVolt=htmlVolt+"</select>";
         htmlVolt=htmlVolt+"</td>";
         htmlVolt=htmlVolt+"<td width=\"13%\" class=\"label\">低压侧电压等级：</td>";
         htmlVolt=htmlVolt+"<td width=\"23%\" class=\"dom\">";
         htmlVolt=htmlVolt+"<select id=\"T_VOLT\" name=\"T_VOLT\">";
         htmlVolt=htmlVolt+"</select>";
         htmlVolt=htmlVolt+"</td>";
         $('#selVolt').html(htmlVolt);
  			}  		
  });
</script>
</head>
<body onload="loadVolt('${ETF.F_VOLT}'),loadsVolt('${ETF.S_VOLT}','${ETF.T_VOLT}','${ETF.MODEL_CODE}','${ETF.TRAN_TYPE}')">
<div class="tab"><em>变压器信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 25));">
  <div class="main">
   <form method="post" name="custana" id="tdetail">
   <table border="0" cellpadding="0" cellspacing="0">
     <tr>
       <td width="13%" class="label"><font color="red">* </font>变压器编号：</td>
       <td width="23%" class="dom"><input type="text" id="ASSET_NO" name="ASSET_NO" value="${ETF.ASSET_NO}" /></td>
       <td width="13%" class="label"><font color="red">* </font>变压器名称：</td>
       <td width="23%" class="dom"><input type="text" id="TRAN_NAME" name="TRAN_NAME" value="${ETF.TRAN_NAME}" /></td>
       <td width="13%" class="label">变压器型号：</td>
       <td width="23%" class="dom">
         <select id="TRAN_CODE" name="TRAN_CODE">
         </select>
       </td>
     </tr>
     <tr>
       <td width="13%" class="label"> 容  量：</td>
       <td width="23%" class="dom"> <input type="text" id="TRAN_CAPA" name="TRAN_CAPA" value="${ETF.TRAN_CAPA}">kVA</td>
       <td width="13%" class="label">变损阈值：</td>
       <td width="23%" class="dom"> <input type="text" id="TRAN_THRESHOLD" name="TRAN_THRESHOLD" value="${ETF.TRAN_THRESHOLD}">%</td>
       <td width="13%" class="label"> 圈  数：</td>
       <td width="23%" class="dom"> 
         <select id="CIRCLE_CNT" name="CIRCLE_CNT" onchange="changeCnt()">
         	 <option value="2">2</option>
         	 <option value="3">3</option>
         </select>	
       </td>
     </tr>
     <tr id="selVolt">
     </tr>
     <tr>
     	 <td width="13%" class="label">变压器类型：</td>
       <td width="23%" class="dom">
       	<select id="TRAN_TYPE" name="TRAN_TYPE">
         </select>
       </td>
       <td class="label">安装地址：</td>
       <td colspan="5" class="dom"><input type="text" id="INSTALL_ADDR" name="INSTALL_ADDR" value="${ETF.INSTALL_ADDR}" /></td>
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
          	<input class="input3" type="button" id="saveTd" value="保存变压器" onClick="saveTdd();" />
            <input class="input3" type="button" id="deleteTg" value="删除变压器" onClick="deleteTdd();" />
          </td>
        </tr>
    </table>
  </form>
  </div>
  <script type="text/javascript">
  	
  //根据圈数来决定电压等级显示。
  function changeCnt(){
  	var selectVolt=$("#CIRCLE_CNT").val();
  	var htmlVolt1=""
  	if(selectVolt==3){
  		   htmlVolt1=htmlVolt1+"<td width=\"13%\" class=\"label\">高压侧电压等级：</td>";
         htmlVolt1=htmlVolt1+"<td width=\"23%\" class=\"dom\">";
         htmlVolt1=htmlVolt1+"<select id=\"F_VOLT\" name=\"F_VOLT\" onchange=\"changeFv()\">";
         htmlVolt1=htmlVolt1+"</select>";
         htmlVolt1=htmlVolt1+"</td>";
         htmlVolt1=htmlVolt1+"<td width=\"13%\" class=\"label\" id=\"mid\">中压侧电压等级：</td>";
         htmlVolt1=htmlVolt1+"<td width=\"23%\" class=\"dom\" id=\"mid2\">";
         htmlVolt1=htmlVolt1+"<select id=\"S_VOLT\" name=\"S_VOLT\" onchange=\"changeSv()\">";
         htmlVolt1=htmlVolt1+"</select>";
         htmlVolt1=htmlVolt1+"</td>";
         htmlVolt1=htmlVolt1+"<td width=\"13%\" class=\"label\">低压侧电压等级：</td>";
         htmlVolt1=htmlVolt1+"<td width=\"23%\" class=\"dom\">";
         htmlVolt1=htmlVolt1+"<select id=\"T_VOLT\" name=\"T_VOLT\">";
         htmlVolt1=htmlVolt1+"</select>";
         htmlVolt1=htmlVolt1+"</td>";
         $('#selVolt').html(htmlVolt1);
         loadVolt('${ETF.F_VOLT}');
         loadsVolt('${ETF.S_VOLT}','${ETF.T_VOLT}','${ETF.MODEL_CODE}');
  		}else{
  			 htmlVolt1=htmlVolt1+"<td width=\"13%\" class=\"label\">高压侧电压等级：</td>";
         htmlVolt1=htmlVolt1+"<td width=\"23%\" class=\"dom\">";
         htmlVolt1=htmlVolt1+"<select id=\"F_VOLT\" name=\"F_VOLT\" onchange=\"changeFv()\">";
         htmlVolt1=htmlVolt1+"</select>";
         htmlVolt1=htmlVolt1+"</td>";
         htmlVolt1=htmlVolt1+"<td width=\"13%\" class=\"label\">低压侧电压等级：</td>";
         htmlVolt1=htmlVolt1+"<td width=\"23%\" class=\"dom\">";
         htmlVolt1=htmlVolt1+"<select id=\"T_VOLT\" name=\"T_VOLT\">";
         htmlVolt1=htmlVolt1+"</select>";
         htmlVolt1=htmlVolt1+"</td>";
         $('#selVolt').html(htmlVolt1);
         loadVolt('${ETF.F_VOLT}');
         loadsVolt('','${ETF.T_VOLT}','${ETF.MODEL_CODE}');
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
  	  
  	//保存更改的变压器信息
   function saveTdd(){
   	if(tranDeCheck()){
   		var ASSET_BEF='${ETF.ASSET_NO}';
   		var ASSET_AF=$("#ASSET_NO").val();
   		$.ajax({
        type:"POST",
        url:"112030.dow",
        dataType:"json",
        data:$("#tdetail").serialize()+'&SUBS_ID='+etfSUBS_ID+'&TRAN_ID='+etfTRAN_ID+'&ASSET_BEFORE='+ ASSET_BEF+'&ASSET_NO='+ASSET_AF+'&FLG=UPD',
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
   //删除变压器
   function deleteTdd(){
   	var tempASSET_NO='${ETF.ASSET_NO}';
   if(confirm("是否删除该变压器")){
				$.ajax({
      type:"POST",
      url:"112030.dow",
      dataType:"json",
      data:{SUBS_ID:etfSUBS_ID,TRAN_ID:etfTRAN_ID,ASSET_NO:tempASSET_NO,FLG:'DEL',SUBS_TYPE:etfSUBS_TYPE},
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