
<%@ page language="java" pageEncoding="UTF-8"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据录入</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css"/>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/jquery.autocomplete.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
<script type="text/javascript">
/** 设置数据输入域状态  */
function setDataInputStatus(flag) { 
    $("#elecDataTable input").each(function() {
        $(this).attr("disabled", flag);
    });
}
var contextPath = "<peis:contextPath/>";
//初始化
$(document).ready(function(){         
   autoCompleter("ASSET_NO","101",154);
   if('${ETF.GP_TYPE}'==1 || '${ETF.GP_TYPE}'==3 || '${ETF.GP_TYPE}'=='' || '${ETF.GP_TYPE}'==null){
    $('td[name=2]').hide();
    $('td[name=3]').hide();
  }else if('${ETF.GP_TYPE}'==2){
  	$('td[name=1]').hide();
  	$('td[name=3]').hide();
  	}else{
  	$('td[name=1]').hide();
  	$('td[name=2]').hide();
  	$('td[name=4]').hide();
  	}
  	
    /* 编辑数据 */
    $('#btn_save').bind("click", editElecData4Ajax);   
   
    if('${ETF.EMPTY}'=='' || '${ETF.EMPTY}'==null || '${ETF.EMPTY}'=='0' || '${ETF.EXISE}'=='0'){
    	if('${ETF.EXISE}'=='0'){
    		alert("电表信息不存在");
    		} 
    		setDataInputStatus(true);
    	  dateCheckInto();  	 
    	 if('${ETF.MOD}'=='yes'){
          setDataInputStatus(false);
          dateCheck();
      }  
    }else if('${ETF.MOD}'=='yes'){
    	setDataInputStatus(false);
          dateCheck();
    	}    	
    	else{
    		setDataInputStatus(false);
    		dateCheckInto();
    	}
});

function dateCheckInto(){	
	  	//判断日期默认值显示
  	if('${ETF.DATADENS}'==20){
  		$("#month").attr("checked",true);
  		$("#DATA_DAY").hide();
      $("#DATA_YEAR").show();
      $("#dtYear").val('${ETF.YEAR}');
      $("#DATA_MONTH").show();
      $("#dtMonth").val('${ETF.MONTH}');
  		}
  		else if('${ETF.DATADENS}'==10){
  		$("#day").attr("checked",true);	 			
  		$("#DATA_DAY").show();
  		$("#dtDate").val('${ETF.DATE}');
  		$("#DATA_YEAR").hide();
  		$("#DATA_MONTH").hide();
  		}else if ('${ETF.DATADENS}'==11){
  			$("#rmday").attr("checked",true);
  		  $("#DATA_DAY").show();
  		  $("#dtDate").val('${ETF.DATE}');
  		  $("#DATA_YEAR").hide();
  		  $("#DATA_MONTH").hide();
  			}
	}

function dateCheck(){
  if('${ETF.DATADENS}'==20){
     $("#month").attr("checked",true);
     $("#rmday").attr("disabled",true);
     $("#day").attr("disabled",true);
     $("#DATA_DAY").hide();
     $("#DATA_YEAR").show();
     $("#dtYear").val('${ETF.YEAR}');
     $("#dtYear").attr("disabled",true);
     $("#DATA_MONTH").show();
     $("#dtMonth").val('${ETF.MONTH_BZ}'.replace(/\b(0+)/gi,""));
     $("#dtMonth").attr("disabled",true);
     $("#ASSET_NO").attr("disabled",true);
     $("#btn_query_cust").attr("disabled",true);
   }
   else if('${ETF.DATADENS}'==10){
     $("#rmday").attr("disabled",true);
     $("#day").attr("checked",true); 
     $("#month").attr("disabled",true);                                
     $("#DATA_DAY").show();
     $("#dtDate").val('${ETF.DATE}');
     $("#dtDate").attr("disabled",true);
     $("#DATA_YEAR").hide();
     $("#DATA_MONTH").hide();
     $("#ASSET_NO").attr("disabled",true);
     $("#btn_query_cust").attr("disabled",true);
   }else if ('${ETF.DATADENS}'==11){
     $("#rmday").attr("checked",true);
     $("#day").attr("disabled",true);
     $("#month").attr("disabled",true);
     $("#DATA_DAY").show();
     $("#dtDate").val('${ETF.DATE}');
     $("#dtDate").attr("disabled",true);
     $("#DATA_YEAR").hide();
     $("#DATA_MONTH").hide();
     $("#ASSET_NO").attr("disabled",true);
     $("#btn_query_cust").attr("disabled",true);
  }
}

function editElecData4Ajax(){
	if(!checkElecData()){
        alert("数据格式问题!");
        return;
    }
  if(checkElecDataInfoP()){
  	alert("正向有功总不等于正向有功尖、正向有功峰、正向有功平、正向有功谷之和")
  	return;
  	}
  if(checkElecDataInfoI()){
  	alert("反向有功总不等于反向有功尖、反向有功峰、反向有功平、反向有功谷之和")
  	return;
  	}
	if('${ETF.MOD}'=='' || '${ETF.MOD}'==null){
		var dataDens=$('input[type=radio][name=dataDens][checked=true]').val();
		var month_bz="";
		if(dataDens==20){
    	if($("select[name='dtMonth']").val().length==1){
    	      month_bz="0"+$("select[name='dtMonth']").val();
    		  }else{month_bz=$("select[name='dtMonth']").val();}
    }
		$.ajax({
      type:"POST",
      url:"101542.dow",
      dataType:"json",
      data:$("#elcdata").serialize()+"&dataDens="+dataDens+"&date="+$("input[name='dtDate']").val()+"&YEAR="+$("select[name='dtYear']").val()
    	       +"&MONTH="+$("select[name='dtMonth']").val()+"&MONTH_BZ="+month_bz+"&ASSET_NO="+$("#ASSET_NO").val(),
      success:function(data){
      	if(data.MSG_TYP=="N"){
      		alert(data.RSP_MSG);
      		clearDataInfo();
      		setDataInputStatus(true);
      		}
      	else{alert(data.RSP_MSG);}
      }
       })
		}
		else{
			$.ajax({
      type:"POST",
      url:"101546.dow",
      dataType:"json",
      data:$("#elcdata").serialize()+"&dataDens=${ETF.DATADENS}&GP_ID=${ETF.GP_ID}&DDATE=${ETF.DDATE}",
      success:function(data){
      	if(data.MSG_TYP=="N"){
      		alert(data.RSP_MSG);
      		parent.GB_hide();
      		top.getMainFrameObj().recordpage.queryElc.submit() ;
      		}
      	else{alert(data.RSP_MSG);}
      }
       })
			}
	}


/** 检查电量数据的有效性 */
function checkElecData(){
    var flag = true;
    $('#elecDataTable input').each(function(){
        if(isNaN($(this).val()) && $(this).val()!= "保 存"){
            $(this).val("");
            $(this).focus();
            flag = false;
            return false;
        }
    });
    return flag;
}

function checkElecDataInfoP(){
	 var P_TOTAL=$(':input[name=P_ACT_TOTAL]').val();
   var P_SHARP=$(':input[name=P_ACT_SHARP]').val();
    var P_PEAK=$(':input[name=P_ACT_PEAK]').val();
   var P_LEVEL=$(':input[name=P_ACT_LEVEL]').val();
  var P_VALLEY=$(':input[name=P_ACT_VALLEY]').val();  
  if(P_TOTAL=='' || P_TOTAL==null){
  	P_TOTAL=0;
  	P_TOTAL=0;
  	}
  if(P_SHARP=='' || P_SHARP==null){
  	P_SHARP=0;
  	}
  if(P_PEAK=='' || P_PEAK==null){
  	P_PEAK=0;
  	}
  if(P_LEVEL=='' || P_LEVEL==null){
  	P_LEVEL=0;
  	}
  if(P_VALLEY=='' || P_VALLEY==null){
  	P_VALLEY=0;
  	}
    if(parseFloat(P_TOTAL)!=parseFloat((parseFloat(P_SHARP)+parseFloat(P_PEAK)+parseFloat(P_LEVEL)+parseFloat(P_VALLEY)).toFixed(10))){
    	return true;
    	}
    return false;
	}

function checkElecDataInfoI(){
	    
   var I_TOTAL=$(':input[name=I_ACT_TOTAL]').val();
   var I_SHARP=$(':input[name=I_ACT_SHARP]').val();
    var I_PEAK=$(':input[name=I_ACT_PEAK]').val();
   var I_LEVEL=$(':input[name=I_ACT_LEVEL]').val();
  var I_VALLEY=$(':input[name=I_ACT_VALLEY]').val();
  
   if(I_TOTAL=='' || I_TOTAL==null){
  	I_TOTAL=0;
  	}
  if(I_SHARP=='' || I_SHARP==null){
  	I_SHARP=0;
  	}
  if(I_PEAK=='' || I_PEAK==null){
  	I_PEAK=0;
  	}
  if(I_LEVEL=='' || I_LEVEL==null){
  	I_LEVEL=0;
  	}
  if(I_VALLEY=='' || I_VALLEY==null){
  	I_VALLEY=0;
  	}
    if(parseFloat(I_TOTAL)!=parseFloat((parseFloat(I_SHARP)+parseFloat(I_PEAK)+parseFloat(I_LEVEL)+parseFloat(I_VALLEY)).toFixed(10))){
    	return true;
    	}
    return false; 
	}

function clearData(){
    $('#CUST_NO').html('');
    $('#CUST_NAME').html('');
    $('#GP_ADDR').html('');
    $('#CT_RATIO').html('');
    $('#PT_RATIO').html('');
    
    $(':input[name=P_ACT_TOTAL]').val('');
    $(':input[name=P_ACT_SHARP]').val('');
    $(':input[name=P_ACT_PEAK]').val('');
    $(':input[name=P_ACT_LEVEL]').val('');
    $(':input[name=P_ACT_VALLEY]').val('');
    
    $(':input[name=I_ACT_TOTAL]').val('');
    $(':input[name=I_ACT_SHARP]').val('');
    $(':input[name=I_ACT_PEAK]').val('');
    $(':input[name=I_ACT_LEVEL]').val('');
    $(':input[name=I_ACT_VALLEY]').val('');

    $(':input[name=P_REACT_TOTAL]').val('');
    $(':input[name=I_REACT_TOTAL]').val('');
}

function clearDataInfo(){
    $(':input[name=P_ACT_TOTAL]').val('');
    $(':input[name=P_ACT_SHARP]').val('');
    $(':input[name=P_ACT_PEAK]').val('');
    $(':input[name=P_ACT_LEVEL]').val('');
    $(':input[name=P_ACT_VALLEY]').val('');
    
    $(':input[name=I_ACT_TOTAL]').val('');
    $(':input[name=I_ACT_SHARP]').val('');
    $(':input[name=I_ACT_PEAK]').val('');
    $(':input[name=I_ACT_LEVEL]').val('');
    $(':input[name=I_ACT_VALLEY]').val('');

    $(':input[name=P_REACT_TOTAL]').val('');
    $(':input[name=I_REACT_TOTAL]').val('');
}

</script>
</head>

<body>
  <div id="body">
    <div id="main2">
      <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="90" class="label">表 号：</td>
            <td width="120" class="dom">
              <input type="text" name="ASSET_NO" value="${ETF.ASSET_NO}" id="ASSET_NO">
            </td>
            <td width="80" class="label">数据密度：</td>
            <td width="200">
            	<input type="radio" id="rmday" name="dataDens" value="11" onclick="switchDataTime()" checked>抄表日
              <input type="radio" id="day" name="dataDens" value="10" onclick="switchDataTime()">日冻结
              <input type="radio" id="month" name="dataDens" value="20" onclick="switchDataTime()">月冻结
            </td>
            <% int num2 = 1900; Date date=new Date(); int year=date.getYear()+num2;
            	int Day=date.getDate();
            	int month=date.getMonth()+1;
              String time=year+"-"+month+"-"+Day;
            	  %>
            <td width="70" class="label">数据时间：</td>
            <td width="150" class="dom_date">
            	<span id="DATA_DAY"> 
                <input type="text" name="dtDate" id="dtDate" value=<%=time%> onfocus="peisDatePicker()" readonly="readonly">
              </span>
              <!-- 年 -->
              <span id="DATA_YEAR" style="display:none;">
                 <select name="dtYear" id="dtYear">
                    <option value="2005">2005</option>
                    <option value="2006">2006</option>
                    <option value="2007">2007</option>
                    <option value="2008">2008</option>
                    <option value="2009">2009</option>
                    <option value="2010">2010</option>
                    <option value="2011">2011</option>
                    <option value="2012">2012</option>
                    <option value="2013">2013</option>
                    <option value="2014">2014</option>
                    <option value="2015">2015</option>
                 </select>年
              </span>
              <!-- 月 -->
              <span id="DATA_MONTH" style="display:none;">
                 <select name="dtMonth" id="dtMonth">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                 </select>月
              </span>
            </td>
            <td colspan="3"><input class="input1" type="submit" id="btn_query_cust" value="查 询" onclick="queryCustInform();"/></td>
          </tr>
         <tr>
         	    <td name="1" width="90" class="label">户 号：</td>
              <td name="1" width="120" class="dom" id="CUST_NO" name="CUST_NO">${ETF.CUST_NO}</td>
              <td name="1" width="80" class="label">户 名：</td>
              <td name="1" width="150" class="dom" id="CUST_NAME" name="CUST_NAME">${ETF.CUST_NAME}</td>

              <td name="2" width="90" class="label">变压器资产号：</td>
              <td name="2" width="120" class="dom" id="" name="">${ETF.ASSET_TRAN}</td>
              <td name="2" width="80" class="label">变压器名：</td>
              <td name="2" width="150" class="dom" id="" name="">${ETF.TRAN_NAME}</td>

              <td name="3" width="90" class="label">变电站名：</td>
              <td name="3" width="120" class="dom" id="SUBS_NAME" name="SUBS_NAME">${ETF.SUBS_NAME}</td>
              <td name="3" width="80" class="label">计量点名称：</td>
              <td name="3" width="200" class="dom" id="MP_NAME" name="MP_NAME">${ETF.MP_NAME}</td>

              <td name="4" width="70" class="label">管理单位：</td>
              <td name="4" width="180" class="dom" id="ORG_NO" name="ORG_NO">${ETF.ORG_NAME}</td>
              <td name="4" width="70" class="label">安装地址：</td>
              <td name="4" width="120" class="dom" id="GP_ADDR" name="GP_ADDR">${ETF.GP_ADDR}</td>

              <td name="5" width="70" class="label">CT变比：</td>
              <td name="5" width="120" class="dom" id="CT_RATIO" name="CT_RATIO">${ETF.CT_RATIO}</td>
              <td name="5" width="70" class="label">PT变比：</td>
              <td name="5" width="120" class="dom" id="PT_RATIO" name="PT_RATIO">${ETF.PT_RATIO}</td>
          
          </tr>
        </table>
      </div>
      <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
          <tr>
            <td colspan="9"></td>
          </tr>
          <tr>
            <td width="70" class="label">数据信息</td>
            <td colspan="8"></td>
          </tr>
        </table>
      <form name="elcdata" id="elcdata">
        <input type="hidden" name="GP_ID" value="${ETF.GP_ID}" id="GP_ID">
        <input type="hidden" name="ORG_NO" value="${ETF.ORG_NO}" id="ORG_NO">
        <table id="elecDataTable" width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
          <tr>
            <td width="86" class="label">正向有功总：</td>
            <td width="80" class="dom">
              <input type="text" name="P_ACT_TOTAL" id="P_ACT_TOTAL" value="<fmt:formatNumber type="number" value="${ETF.P_ACT_TOTAL}" pattern="0.00"/>" >
            </td>
            <td width="86" class="label">反向有功总：</td>
            <td width="80" class="dom">
              <input type="text" name="I_ACT_TOTAL" id="I_ACT_TOTAL" value="<fmt:formatNumber type="number" value="${ETF.I_ACT_TOTAL}" pattern="0.00"/>" >
            </td>
            <td width="86" class="label">正向无功总：</td>
            <td width="80" class="dom">
              <input type="text" name="P_REACT_TOTAL" id="P_REACT_TOTAL" value="<fmt:formatNumber type="number" value="${ETF.P_REACT_TOTAL}" pattern="0.00"/>" >
            </td>
            <td colspan="3"></td>
          </tr>
          <tr>
            <td width="86" class="label">正向有功尖：</td>
            <td width="80" class="dom">
              <input type="text" name="P_ACT_SHARP" id="P_ACT_SHARP" value="<fmt:formatNumber type="number" value="${ETF.P_ACT_SHARP}" pattern="0.00"/>" >
            </td>
            <td width="86" class="label">反向有功尖：</td>
            <td width="80" class="dom">
              <input type="text" name="I_ACT_SHARP" id="I_ACT_SHARP" value="<fmt:formatNumber type="number" value="${ETF.I_ACT_SHARP}" pattern="0.00"/>" >
            </td>
            <td width="86" class="label">反向无功总：</td>
            <td width="80" class="dom">
              <input type="text" name="I_REACT_TOTAL" id="I_REACT_TOTAL" value="<fmt:formatNumber type="number" value="${ETF.I_REACT_TOTAL}" pattern="0.00"/>" >
            </td>
            <td colspan="3"></td>
          </tr>
          <tr>
            <td width="86" class="label">正向有功峰：</td>
            <td width="80" class="dom">
              <input type="text" name="P_ACT_PEAK" id="P_ACT_PEAK" value="<fmt:formatNumber type="number" value="${ETF.P_ACT_PEAK}" pattern="0.00"/>" >
            </td>
            <td width="86" class="label">反向有功峰：</td>
            <td width="80" class="dom">
              <input type="text" name="I_ACT_PEAK" id="I_ACT_PEAK" value="<fmt:formatNumber type="number" value="${ETF.I_ACT_PEAK}" pattern="0.00"/>" >
            </td>
            <td colspan="5"></td>
          </tr>
          <tr>
            <td width="86" class="label">正向有功平：</td>
            <td width="80" class="dom">
              <input type="text" name="P_ACT_LEVEL" id="P_ACT_LEVEL" value="<fmt:formatNumber type="number" value="${ETF.P_ACT_LEVEL}" pattern="0.00"/>" >
            </td>
            <td width="86" class="label">反向有功平：</td>
            <td width="80" class="dom">
              <input type="text" name="I_ACT_LEVEL" id="I_ACT_LEVEL" value="<fmt:formatNumber type="number" value="${ETF.I_ACT_LEVEL}" pattern="0.00"/>" >
            </td>
            <td colspan="5"></td>
          </tr>
          <tr>
            <td width="86" class="label">正向有功谷：</td>
            <td width="80" class="dom">
              <input type="text" name="P_ACT_VALLEY" id="P_ACT_VALLEY" value="<fmt:formatNumber type="number" value="${ETF.P_ACT_VALLEY}" pattern="0.00"/>" >
            </td>
            <td width="86" class="label">反向有功谷：</td>
            <td width="80" class="dom">
              <input type="text" name="I_ACT_VALLEY" id="I_ACT_VALLEY" value="<fmt:formatNumber type="number" value="${ETF.I_ACT_VALLEY}" pattern="0.00"/>" >
            </td>
            <td colspan="5"></td>
          </tr>
          <tr>
            <td colspan="6" align="center">
              <input id="btn_save" class="input1" type="button" value="保 存">
            </td>
            <td colspan="3"></td>
          </tr>
        </table>
      </form>
      </div>
    </div>
  </div> 
  <script>
  	
  	//时间切换  	
  	function switchDataTime(){
  		var dataDens=$('input[type=radio][name=dataDens][checked=true]').val();
  		if(dataDens==20){
  			$("#DATA_DAY").hide();
        $("#DATA_YEAR").show();
        $("#DATA_MONTH").show();
  		}else{
  			$("#DATA_DAY").show();
        $("#DATA_YEAR").hide();
        $("#DATA_MONTH").hide();
  			}
  		}
  		
    //查询记录
    function queryCustInform(){
    	var dataDens=$('input[type=radio][name=dataDens][checked=true]').val();
    	if($("#ASSET_NO").val()=='' || $("#ASSET_NO").val()==null){
    		alert("表号不允许为空");
    		}
    	else{
    		var month_bz="";
    		if(dataDens==20){
    			if($("select[name='dtMonth']").val().length==1){
    	   	      month_bz="0"+$("select[name='dtMonth']").val();
    	   		  }else{month_bz=$("select[name='dtMonth']").val();}
    			}
    	   url="101541.dow?ASSET_NO="+$("#ASSET_NO").val()+"&dataDens="+dataDens+"&DATE="+$("input[name='dtDate']").val()+"&YEAR="+$("select[name='dtYear']").val()
    	       +"&MONTH="+$("select[name='dtMonth']").val()+"&MONTH_BZ="+month_bz+"&output=/jsp/autorm/dataInput.jsp";
         window.location.href=url;
    	}
   }
  </script>
</body>
</html>
=======
<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../common/blockloading.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据录入</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css"/>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/jquery.autocomplete.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
<script type="text/javascript">
/** 设置数据输入域状态  */
function setDataInputStatus(flag) { 
    $("#elecDataTable input").each(function() {
        $(this).attr("disabled", flag);
    });
}
var contextPath = "<peis:contextPath/>";
//初始化
$(document).ready(function(){         
   autoCompleter("ASSET_NO","101",154);
   if('${ETF.GP_TYPE}'==1 || '${ETF.GP_TYPE}'==3 || '${ETF.GP_TYPE}'=='' || '${ETF.GP_TYPE}'==null){
    $('td[name=2]').hide();
    $('td[name=3]').hide();
  }else if('${ETF.GP_TYPE}'==2){
  	$('td[name=1]').hide();
  	$('td[name=3]').hide();
  	}else{
  	$('td[name=1]').hide();
  	$('td[name=2]').hide();
  	$('td[name=4]').hide();
  	}
  	
    /* 编辑数据 */
    $('#btn_save').bind("click", editElecData4Ajax);   
   
    if('${ETF.EMPTY}'=='' || '${ETF.EMPTY}'==null || '${ETF.EMPTY}'=='0' || '${ETF.EXISE}'=='0'){
    	if('${ETF.EXISE}'=='0'){
    		alert("电表信息不存在");
    		} 
    		setDataInputStatus(true);
    	  dateCheckInto();  	 
    	 if('${ETF.MOD}'=='yes'){
          setDataInputStatus(false);
          dateCheck();
      }  
    }else if('${ETF.MOD}'=='yes'){
    	setDataInputStatus(false);
          dateCheck();
    	}    	
    	else{
    		setDataInputStatus(false);
    		dateCheckInto();
    	}
});

function dateCheckInto(){	
	  	//判断日期默认值显示
  	if('${ETF.DATADENS}'==20){
  		$("#month").attr("checked",true);
  		$("#DATA_DAY").hide();
      $("#DATA_YEAR").show();
      $("#dtYear").val('${ETF.YEAR}');
      $("#DATA_MONTH").show();
      $("#dtMonth").val('${ETF.MONTH}');
  		}
  		else if('${ETF.DATADENS}'==10){
  		$("#day").attr("checked",true);	 			
  		$("#DATA_DAY").show();
  		$("#dtDate").val('${ETF.DATE}');
  		$("#DATA_YEAR").hide();
  		$("#DATA_MONTH").hide();
  		}else if ('${ETF.DATADENS}'==11){
  			$("#rmday").attr("checked",true);
  		  $("#DATA_DAY").show();
  		  $("#dtDate").val('${ETF.DATE}');
  		  $("#DATA_YEAR").hide();
  		  $("#DATA_MONTH").hide();
  			}
	}

function dateCheck(){
  if('${ETF.DATADENS}'==20){
     $("#month").attr("checked",true);
     $("#rmday").attr("disabled",true);
     $("#day").attr("disabled",true);
     $("#DATA_DAY").hide();
     $("#DATA_YEAR").show();
     $("#dtYear").val('${ETF.YEAR}');
     $("#dtYear").attr("disabled",true);
     $("#DATA_MONTH").show();
     $("#dtMonth").val('${ETF.MONTH_BZ}'.replace(/\b(0+)/gi,""));
     $("#dtMonth").attr("disabled",true);
     $("#ASSET_NO").attr("disabled",true);
     $("#btn_query_cust").attr("disabled",true);
   }
   else if('${ETF.DATADENS}'==10){
     $("#rmday").attr("disabled",true);
     $("#day").attr("checked",true); 
     $("#month").attr("disabled",true);                                
     $("#DATA_DAY").show();
     $("#dtDate").val('${ETF.DATE}');
     $("#dtDate").attr("disabled",true);
     $("#DATA_YEAR").hide();
     $("#DATA_MONTH").hide();
     $("#ASSET_NO").attr("disabled",true);
     $("#btn_query_cust").attr("disabled",true);
   }else if ('${ETF.DATADENS}'==11){
     $("#rmday").attr("checked",true);
     $("#day").attr("disabled",true);
     $("#month").attr("disabled",true);
     $("#DATA_DAY").show();
     $("#dtDate").val('${ETF.DATE}');
     $("#dtDate").attr("disabled",true);
     $("#DATA_YEAR").hide();
     $("#DATA_MONTH").hide();
     $("#ASSET_NO").attr("disabled",true);
     $("#btn_query_cust").attr("disabled",true);
  }
}


/** ajax 取电量数据 */
function getElecData4Ajax(){
    parent.method = "add";
    var assetNo = $.trim($('#assetNo').val());
    var custNo = $('#custNo').html();
    //已经查询到的数据 
    if(custNo != null && custNo != ""){
        dealDataInput();
    }
}

function editElecData4Ajax(){
	if(!checkElecData()){
        alert("数据格式问题!");
        return;
    }
	if('${ETF.MOD}'=='' || '${ETF.MOD}'==null){
		var dataDens=$('input[type=radio][name=dataDens][checked=true]').val();
		var month_bz="";
		if(dataDens==20){
    	if($("select[name='dtMonth']").val().length==1){
    	      month_bz="0"+$("select[name='dtMonth']").val();
    		  }else{month_bz=$("select[name='dtMonth']").val();}
    }
		$.ajax({
      type:"POST",
      url:"101542.dow",
      dataType:"json",
      data:$("#elcdata").serialize()+"&dataDens="+dataDens+"&date="+$("input[name='dtDate']").val()+"&YEAR="+$("select[name='dtYear']").val()
    	       +"&MONTH="+$("select[name='dtMonth']").val()+"&MONTH_BZ="+month_bz+"&ASSET_NO="+$("#ASSET_NO").val(),
      success:function(data){
      	if(data.MSG_TYP=="N"){
      		alert(data.RSP_MSG);
      		clearDataInfo();
      		setDataInputStatus(true);
      		}
      	else{alert(data.RSP_MSG);}
      }
       })
		}
		else{
			$.ajax({
      type:"POST",
      url:"101546.dow",
      dataType:"json",
      data:$("#elcdata").serialize()+"&dataDens=${ETF.DATADENS}&GP_ID=${ETF.GP_ID}&DDATE=${ETF.DDATE}",
      success:function(data){
      	if(data.MSG_TYP=="N"){
      		alert(data.RSP_MSG);
      		parent.GB_hide();
      		top.getMainFrameObj().recordpage.queryElc.submit() ;
      		}
      	else{alert(data.RSP_MSG);}
      }
       })
			}
	}


/** 检查电量数据的有效性 */
function checkElecData(){
    var flag = true;
    $('#elecDataTable input').each(function(){
        if(isNaN($(this).val()) && $(this).val()!= "保 存"){
            $(this).val("");
            $(this).focus();
            flag = false;
            return false;
        }
    });
    return flag;
}


function clearData(){
    $('#CUST_NO').html('');
    $('#CUST_NAME').html('');
    $('#GP_ADDR').html('');
    $('#CT_RATIO').html('');
    $('#PT_RATIO').html('');
    
    $(':input[name=P_ACT_TOTAL]').val('');
    $(':input[name=P_ACT_SHARP]').val('');
    $(':input[name=P_ACT_PEAK]').val('');
    $(':input[name=P_ACT_LEVEL]').val('');
    $(':input[name=P_ACT_VALLEY]').val('');
    
    $(':input[name=I_ACT_TOTAL]').val('');
    $(':input[name=I_ACT_SHARP]').val('');
    $(':input[name=I_ACT_PEAK]').val('');
    $(':input[name=I_ACT_LEVEL]').val('');
    $(':input[name=I_ACT_VALLEY]').val('');

    $(':input[name=P_REACT_TOTAL]').val('');
    $(':input[name=I_REACT_TOTAL]').val('');
}

function clearDataInfo(){
    $(':input[name=P_ACT_TOTAL]').val('');
    $(':input[name=P_ACT_SHARP]').val('');
    $(':input[name=P_ACT_PEAK]').val('');
    $(':input[name=P_ACT_LEVEL]').val('');
    $(':input[name=P_ACT_VALLEY]').val('');
    
    $(':input[name=I_ACT_TOTAL]').val('');
    $(':input[name=I_ACT_SHARP]').val('');
    $(':input[name=I_ACT_PEAK]').val('');
    $(':input[name=I_ACT_LEVEL]').val('');
    $(':input[name=I_ACT_VALLEY]').val('');

    $(':input[name=P_REACT_TOTAL]').val('');
    $(':input[name=I_REACT_TOTAL]').val('');
}

//编辑电量数据
function editDataFromOtherPage(dataDens){
    parent.method = "mod";
    $('#custNo').html(parent.assetObj.custNo);
    $('#custName').html(parent.assetObj.custName);
    $('#installAddr').html(parent.assetObj.installAddr);
    $('#ct').html(parent.assetObj.ct);
    $('#pt').html(parent.assetObj.pt);
    $('input[type=radio][name=dataDens]').each(function(){
        if(this.value == dataDens){
            this.checked = true;
        }
    });
    if("10" == dataDens){
        fillData4Json(parent.dayDataObj);
        setDataInputStatus(false);
    }
    else if("20" == dataDens){
        fillData4Json(parent.monDataObj);
        setDataInputStatus(false);
    }
}
</script>
</head>

<body>
  <div id="body">
    <div id="main2">
      <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="90" class="label">表 号：</td>
            <td width="120" class="dom">
              <input type="text" name="ASSET_NO" value="${ETF.ASSET_NO}" id="ASSET_NO">
            </td>
            <td width="80" class="label">数据密度：</td>
            <td width="200">
            	<input type="radio" id="rmday" name="dataDens" value="11" onclick="switchDataTime()" checked>抄表日
              <input type="radio" id="day" name="dataDens" value="10" onclick="switchDataTime()">日冻结
              <input type="radio" id="month" name="dataDens" value="20" onclick="switchDataTime()">月冻结
            </td>
            <% int num2 = 1900; Date date=new Date(); int year=date.getYear()+num2;
            	int Day=date.getDate();
            	int month=date.getMonth()+1;
              String time=year+"-"+month+"-"+Day;
            	  %>
            <td width="70" class="label">数据时间：</td>
            <td width="150" class="dom_date">
            	<span id="DATA_DAY"> 
                <input type="text" name="dtDate" id="dtDate" value=<%=time%> onfocus="peisDatePicker()" readonly="readonly">
              </span>
              <!-- 年 -->
              <span id="DATA_YEAR" style="display:none;">
                 <select name="dtYear" id="dtYear">
                    <option value="2005">2005</option>
                    <option value="2006">2006</option>
                    <option value="2007">2007</option>
                    <option value="2008">2008</option>
                    <option value="2009">2009</option>
                    <option value="2010">2010</option>
                    <option value="2011">2011</option>
                    <option value="2012">2012</option>
                    <option value="2013">2013</option>
                    <option value="2014">2014</option>
                    <option value="2015">2015</option>
                 </select>年
              </span>
              <!-- 月 -->
              <span id="DATA_MONTH" style="display:none;">
                 <select name="dtMonth" id="dtMonth">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                 </select>月
              </span>
            </td>
            <td colspan="3"><input class="input1" type="submit" id="btn_query_cust" value="查 询" onclick="queryCustInform();"/></td>
          </tr>
         <tr>
         	    <td name="1" width="90" class="label">户 号：</td>
              <td name="1" width="120" class="dom" id="CUST_NO" name="CUST_NO">${ETF.CUST_NO}</td>
              <td name="1" width="80" class="label">户 名：</td>
              <td name="1" width="150" class="dom" id="CUST_NAME" name="CUST_NAME">${ETF.CUST_NAME}</td>

              <td name="2" width="90" class="label">变压器资产号：</td>
              <td name="2" width="120" class="dom" id="" name="">${ETF.ASSET_TRAN}</td>
              <td name="2" width="80" class="label">变压器名：</td>
              <td name="2" width="150" class="dom" id="" name="">${ETF.TRAN_NAME}</td>

              <td name="3" width="90" class="label">变电站名：</td>
              <td name="3" width="120" class="dom" id="SUBS_NAME" name="SUBS_NAME">${ETF.SUBS_NAME}</td>
              <td name="3" width="80" class="label">计量点名称：</td>
              <td name="3" width="200" class="dom" id="MP_NAME" name="MP_NAME">${ETF.MP_NAME}</td>

              <td name="4" width="70" class="label">管理单位：</td>
              <td name="4" width="180" class="dom" id="ORG_NO" name="ORG_NO">${ETF.ORG_NAME}</td>
              <td name="4" width="70" class="label">安装地址：</td>
              <td name="4" width="120" class="dom" id="GP_ADDR" name="GP_ADDR">${ETF.GP_ADDR}</td>

              <td name="5" width="70" class="label">CT变比：</td>
              <td name="5" width="120" class="dom" id="CT_RATIO" name="CT_RATIO">${ETF.CT_RATIO}</td>
              <td name="5" width="70" class="label">PT变比：</td>
              <td name="5" width="120" class="dom" id="PT_RATIO" name="PT_RATIO">${ETF.PT_RATIO}</td>
          
          </tr>
        </table>
      </div>
      <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
          <tr>
            <td colspan="9"></td>
          </tr>
          <tr>
            <td width="70" class="label">数据信息</td>
            <td colspan="8"></td>
          </tr>
        </table>
      <form name="elcdata" id="elcdata">
        <input type="hidden" name="GP_ID" value="${ETF.GP_ID}" id="GP_ID">
        <input type="hidden" name="ORG_NO" value="${ETF.ORG_NO}" id="ORG_NO">
        <table id="elecDataTable" width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
          <tr>
            <td width="86" class="label">正向有功总：</td>
            <td width="80" class="dom">
              <input type="text" name="P_ACT_TOTAL" id="P_ACT_TOTAL" value="<fmt:formatNumber type="number" value="${ETF.P_ACT_TOTAL}" pattern="0.0000"/>" >
            </td>
            <td width="86" class="label">反向有功总：</td>
            <td width="80" class="dom">
              <input type="text" name="I_ACT_TOTAL" id="I_ACT_TOTAL" value="<fmt:formatNumber type="number" value="${ETF.I_ACT_TOTAL}" pattern="0.0000"/>" >
            </td>
            <td width="86" class="label">正向无功总：</td>
            <td width="80" class="dom">
              <input type="text" name="P_REACT_TOTAL" id="P_REACT_TOTAL" value="<fmt:formatNumber type="number" value="${ETF.P_REACT_TOTAL}" pattern="0.0000"/>" >
            </td>
            <td colspan="3"></td>
          </tr>
          <tr>
            <td width="86" class="label">正向有功尖：</td>
            <td width="80" class="dom">
              <input type="text" name="P_ACT_SHARP" id="P_ACT_SHARP" value="<fmt:formatNumber type="number" value="${ETF.P_ACT_SHARP}" pattern="0.0000"/>" >
            </td>
            <td width="86" class="label">反向有功尖：</td>
            <td width="80" class="dom">
              <input type="text" name="I_ACT_SHARP" id="I_ACT_SHARP" value="<fmt:formatNumber type="number" value="${ETF.I_ACT_SHARP}" pattern="0.0000"/>" >
            </td>
            <td width="86" class="label">反向无功总：</td>
            <td width="80" class="dom">
              <input type="text" name="I_REACT_TOTAL" id="I_REACT_TOTAL" value="<fmt:formatNumber type="number" value="${ETF.I_REACT_TOTAL}" pattern="0.0000"/>" >
            </td>
            <td colspan="3"></td>
          </tr>
          <tr>
            <td width="86" class="label">正向有功峰：</td>
            <td width="80" class="dom">
              <input type="text" name="P_ACT_PEAK" id="P_ACT_PEAK" value="<fmt:formatNumber type="number" value="${ETF.P_ACT_PEAK}" pattern="0.0000"/>" >
            </td>
            <td width="86" class="label">反向有功峰：</td>
            <td width="80" class="dom">
              <input type="text" name="I_ACT_PEAK" id="I_ACT_PEAK" value="<fmt:formatNumber type="number" value="${ETF.I_ACT_PEAK}" pattern="0.0000"/>" >
            </td>
            <td colspan="5"></td>
          </tr>
          <tr>
            <td width="86" class="label">正向有功平：</td>
            <td width="80" class="dom">
              <input type="text" name="P_ACT_LEVEL" id="P_ACT_LEVEL" value="<fmt:formatNumber type="number" value="${ETF.P_ACT_LEVEL}" pattern="0.0000"/>" >
            </td>
            <td width="86" class="label">反向有功平：</td>
            <td width="80" class="dom">
              <input type="text" name="I_ACT_LEVEL" id="I_ACT_LEVEL" value="<fmt:formatNumber type="number" value="${ETF.I_ACT_LEVEL}" pattern="0.0000"/>" >
            </td>
            <td colspan="5"></td>
          </tr>
          <tr>
            <td width="86" class="label">正向有功谷：</td>
            <td width="80" class="dom">
              <input type="text" name="P_ACT_VALLEY" id="P_ACT_VALLEY" value="<fmt:formatNumber type="number" value="${ETF.P_ACT_VALLEY}" pattern="0.0000"/>" >
            </td>
            <td width="86" class="label">反向有功谷：</td>
            <td width="80" class="dom">
              <input type="text" name="I_ACT_VALLEY" id="I_ACT_VALLEY" value="<fmt:formatNumber type="number" value="${ETF.I_ACT_VALLEY}" pattern="0.0000"/>" >
            </td>
            <td colspan="5"></td>
          </tr>
          <tr>
            <td colspan="6" align="center">
              <input id="btn_save" class="input1" type="button" value="保 存">
            </td>
            <td colspan="3"></td>
          </tr>
        </table>
      </form>
      </div>
    </div>
  </div> 
  <script>
  	
  	//时间切换  	
  	function switchDataTime(){
  		var dataDens=$('input[type=radio][name=dataDens][checked=true]').val();
  		if(dataDens==20){
  			$("#DATA_DAY").hide();
        $("#DATA_YEAR").show();
        $("#DATA_MONTH").show();
  		}else{
  			$("#DATA_DAY").show();
        $("#DATA_YEAR").hide();
        $("#DATA_MONTH").hide();
  			}
  		}
  		
    //查询记录
    function queryCustInform(){
    	var dataDens=$('input[type=radio][name=dataDens][checked=true]').val();
    	if($("#ASSET_NO").val()=='' || $("#ASSET_NO").val()==null){
    		alert("表号不允许为空");
    		}
    	else{
    		var month_bz="";
    		if(dataDens==20){
    			if($("select[name='dtMonth']").val().length==1){
    	   	      month_bz="0"+$("select[name='dtMonth']").val();
    	   		  }else{month_bz=$("select[name='dtMonth']").val();}
    			}
    	   url="101541.dow?ASSET_NO="+$("#ASSET_NO").val()+"&dataDens="+dataDens+"&DATE="+$("input[name='dtDate']").val()+"&YEAR="+$("select[name='dtYear']").val()
    	       +"&MONTH="+$("select[name='dtMonth']").val()+"&MONTH_BZ="+month_bz+"&output=/jsp/autorm/dataInput.jsp";
         window.location.href=url;
    	}
   }
  </script>
</body>
<%@include file="../common/blockendloading.jsp"%>
</html>
>>>>>>> .r1071
