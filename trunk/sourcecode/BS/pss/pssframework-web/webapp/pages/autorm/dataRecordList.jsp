<<<<<<< .mine
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../archive/include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据录入记录</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css"/>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/jquery.autocomplete.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/selectOption.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveExcel.js"></script>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
<style type="text/css">
tr th { text-align: center; }
</style>

<script> 

function setTableStyle(){
    /**
     * even/odd rows
     */
    setRows($(".tableContainer table>tbody>tr:odd"));
    
    /**
     * mouseover/mouseout event
     */
    $(".tableContainer table>tbody>tr").each( function() {
        $(this).mouseover( function() {
            $(this).addClass("mouseover");
        });
        $(this).mouseout( function() {
            $(this).removeClass("mouseover");
        });
    });
}

var contextPath = "<peis:contextPath/>";
$(document).ready(function(){
	 autoCompleter("ASSET_NO","101",154);
    $('#btn_edit').bind("click", gotoEdit);
    $('#btn_del').bind("click", gotoDel);
    loadSelectOption3('101543.dow','${ETF.USER_NO}','USER_NO','0');//角色   
    
  if('${ETF.GP_TYPE}'==1 || '${ETF.GP_TYPE}'==3 || '${ETF.GP_TYPE}'=='' || '${ETF.GP_TYPE}'==null){
    $('th[name=2]').hide();
    $('th[name=3]').hide();
    $('td[name=2]').hide();
    $('td[name=3]').hide();
  }else if('${ETF.GP_TYPE}'==2){
  	$('th[name=1]').hide();
  	$('th[name=3]').hide();
  	$('td[name=1]').hide();
    $('td[name=3]').hide();
  	}else{
  	$('th[name=1]').hide();
  	$('th[name=2]').hide();
  	$('td[name=1]').hide();
  	$('td[name=2]').hide();
  	} 
});

/** 去编辑页面  */
function gotoEdit(){
    var selectTr = $('#tbody tr').filter(".selected");
    if(selectTr.length == 0){
        alert("请选中要编辑的行！");
        return;
    }
    var asset_no=$('td:eq(7)', selectTr).html();
    var DATADENS=$('td:eq(9)', selectTr).attr("name"); 
    var gp_id = $('td:eq(0)', selectTr).attr("gp_id");   
    var date="";
    if($('td:eq(9)', selectTr).attr("name")==20){
      var YEAR=$('td:eq(8)', selectTr).attr("ddate").substr(0,4);
      var MONTH_BZ=$('td:eq(8)', selectTr).attr("ddate").substr(4,2);
          date="&YEAR="+YEAR+"&MONTH_BZ="+MONTH_BZ;
    }else{
    	date="&DATE="+$('td:eq(8)', selectTr).html();
    	}
   var selectedDate="?asset_no="+asset_no+date+"&DATADENS="+DATADENS+"&gp_id="+gp_id;
    openEdit(selectedDate);
}

 function openEdit(selectedDate){
   var url="101541.dow"+selectedDate+"&output=jsp/autorm/dataInput.jsp&mod=yes";
   top.showDialogBox("数据录入",url, 400, 1250);
 }
 
 
 /** 删除录入记录  */
function gotoDel(){
    var selectTr = $('#tbody tr').filter(".selected");

    if(selectTr.length == 0){
        alert("请选中要删除的行！");
        return;
    }
    
    if(!window.confirm("是否要删除这条记录！")){
        return;
    }
    var ddate = $('td:eq(8)', selectTr).attr("ddate");
    var DATA_DENS = $('td:eq(9)', selectTr).attr("name");
    var gp_id = $('td:eq(0)', selectTr).attr("gp_id");
    var user_no = $('td:eq(14)', selectTr).attr("user_no");
    var selectedDate="DDATE="+ddate+"&DATA_DENS="+DATA_DENS+"&GP_ID="+gp_id+"&STAT_TIME1="+$('#STAT_TIME1').val()+"&END_TIME="+$('#STAT_TIME1').val();
    deleteRow(selectedDate);
}

function deleteRow(selectedDate){
	var url="101545.dow?"+selectedDate;
   $.ajax({
        type:"POST",
        url:url,
        dataType:"json",
        success:function(data){
                	  if(data.MSG_TYP=='N'){
                	  	alert(data.RSP_MSG);
                	  	window.location.reload();
                	  }
                	  else{alert(data.RSP_MSG);}
                },
        error :function(XMLHttpRequest,textStatus,errorThrown){
               alert("通迅失败");
               }
        })
}
</script>
</head>

<body>
  <div id="body">
  	<form action="101544.dow?output=/jsp/autorm/dataRecordList.jsp" method="post" name="queryElc" id="queryElc">
      <div id="main">
        <div id="tool">
          <table border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="70" class="label">表 号：</td>
              <td width="120" class="dom">
                <input type="text" name="ASSET_NO" value="${ETF.ASSET_NO}" id="ASSET_NO">
              </td>
            </tr>
            <tr>
            	<% int num2 = 1900; Date date=new Date(); int year=date.getYear()+num2;
              	int Day=date.getDate();
              	int month=date.getMonth()+1;
                String time=year+"-"+month+"-"+Day;
              	  %>
              <td width="70" class="label">开始时间：</td>
              <td width="120" class="dom_date">
                <input type="text" name="STAT_TIME1" value=<%=time%> id="STAT_TIME1" onfocus="peisDatePicker()" readonly="readonly">
              </td>
              <td width="70" class="label">结束时间：</td>
              <td width="120" class="dom_date">
                <input type="text" name="END_TIME1" value=<%=time%> id="END_TIME1" onfocus="peisDatePicker()" readonly="readonly">
              </td>
              <td width="70" class="label">录入人：</td>
              <td width="120" class="dom">
                <select name="USER_NO" id="USER_NO">
                </select>  
              </td>
            </tr>
          </table>
          <div class="t_botton">
            <div class="t_right">
              <input class="input1" type="button" id="btn_edit" value="编 辑"/>
              <input class="input1" type="button" id="btn_del" value="删 除"/>
              &nbsp;&nbsp;&nbsp;&nbsp;<input class="input1" type="submit" id="query" value="查 询"/>
            </div>
            <div class="clear"></div>
          </div>
        </div>
        <div class="content">
          <div id="cont_1">
            <div class="target">
              <ul>
                <li class="target_on"><a href="#" onClick="return false;">数据检查</a></li>
                <li class="clear"></li>
              </ul>
              <h1><img src="../../img/bt_data.gif" width="10" height="10" align="middle"" /> <a href="#">修改显示字段</a></h1>
            </div>
            <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 152));">
              <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <thead>
                <tr>
                  <th width="3%">序号</th>
                  <th name=1 width="10%">户号</th>
                  <th name=1 width="10%">户名</th>
                  <th name=2 width="10%">配变编号</th>
                  <th name=2 width="10%">配变名称</th>
                  <th name=3 width="10%">变电站编号</th>
                  <th name=3 width="10%">变电站名称</th>
                  <th width="10%">表号</th>
                  <th width="5%">数据时间</th>
                  <th width="5%">数据类型</th>
                  <th width="5%">正向有功总</th>
                  <th width="5%">反向有功总</th>
                  <th width="5%">正向无功总</th>
                  <th width="5%">反向无功总</th>
                  <th width="5%">追加人</th>
                </tr></thead>
                <tbody id="tbody" align="center">
                  <c:forEach items="${ETF.REC}" var="VIP">
                   <tr onClick="selectSingleRow(this);" style="cursor:pointer;" align="center">
                    <td gp_id="${VIP.GP_ID}">${VIP.ROWNUM}</td> 
                    <td name="1">${VIP.CUST_NO}</td> 
                    <td name="1">${VIP.CUST_NAME}</td> 
                    <td name="2">${VIP.TG_NO}</td> 
                    <td name="2">${VIP.TG_NAME}</td>
                    <td name="3">${VIP.SUBS_NO}</td> 
                    <td name="3">${VIP.SUBS_NAME}</td>
                    <td>${VIP.ASSET_NO}</td>
                    <td ddate="${VIP.DDATE}">${VIP.DDATE_FM}</td> 
                    <td name="${VIP.DATA_DENS}">${VIP.NAME}</td>
                    <td><fmt:formatNumber type="number" value="${VIP.P_ACT_TOTAL}" pattern="0.00"/></td>
                    <td><fmt:formatNumber type="number" value="${VIP.I_ACT_TOTAL}" pattern="0.00"/></td>
                    <td><fmt:formatNumber type="number" value="${VIP.P_REACT_TOTAL}" pattern="0.00"/></td>
                    <td><fmt:formatNumber type="number" value="${VIP.I_REACT_TOTAL}" pattern="0.00"/></td>
                    <td user_no="${ETF.USER_NO}">${VIP.USERNAME}</td>
                   </tr> 
                  </c:forEach>
                </tbody>
              </table>
            </div>
            <div class="pageContainer">
	            <a href="#"><img src="<peis:contextPath/>/img/bt_excel.gif" width="16" height="16" title="导出Excel" onclick="creatExcel()"/></a>　┆　共<span class="orange">${ETF.TOT_REC_NUM}</span>条　显示行数：
            
	            <select name='WantedPerPage' size='1' onchange="changePerRows()" id="WantedPerPage">
                            <option value='10'>10</option> 
                            <option value='20' selected>20</option> 
                            <option value='30'>30</option> 
                            <option value='40'>40</option> 
                            <option value='50'>50</option> 
                            <option value='100'>100</option> 
                            <option value='500'>500</option> 
	            </select>　
	            第<span class="orange">${ETF.PAG_NO}</span>页 / 共<span class="orange">${ETF.PAG_CNT}</span>页　转到：
            
	            <input type="text" id="TargetPage" name="TargetPage" value="1" /> 
	            <img align="middle" src="<peis:contextPath/>/img/bt_go.gif" width="35" height="21" border="0" style="cursor: pointer;" onclick="getTargetPage()"/>　
	            <div id="pageGuide" style="display:inline;"></div>
	        </div>
	       </div>   
        </div>
      </div>
    </form>
  </div>
  <script type="text/javascript">
  	if('${ETF.STAT_TIME1}'!=''){
  	  $('#STAT_TIME1').val('${ETF.STAT_TIME1}');
    }
    if('${ETF.END_TIME1}'!=''){
      $('#END_TIME1').val('${ETF.END_TIME1}'); 
    }
  	 var asset_no=$("#ASSET_NO").val();
  	 var tempWantedPerPage='${ETF.WANTEDPERPAGE}';
     var temppage='${ETF.PAG_NO}';
     var tempPAG_KEY="${ETF.PAG_KEY}"; 
     //动态生成默认页数

       if(tempWantedPerPage!=''){
        $("#WantedPerPage [value='"+tempWantedPerPage+"']").attr("selected","true");
       }
       //动态生成转到栏位的默认值

       if(temppage!=''){
       	$('#TargetPage').val(temppage)
       	}
       	 //动态生成换页图标

       var now_page='${ETF.PAG_NO}';
       var all_page='${ETF.PAG_CNT}';
       var first_disable_Style="<img style='margin-left:-5;margin-right:7;' src=\"<peis:contextPath/>/img/page_home.gif\" class=\"pointer\"/>\n";
       var forward_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_prew.gif\" class=\"pointer\"/>\n";
       var next_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_next.gif\" class=\"pointer\"/>\n";                                                     
       var end_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_end.gif\" class=\"pointer\"/>\n";
       var first_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvFirst('101544.dow','WantedPerPage')\"><img style='margin-left:-5;margin-right:7;' src=\"<peis:contextPath/>/img/page_home.gif\" class=\"pointer\"/></a>\n";
       var forward_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvForward('101544.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_prew.gif\" class=\"pointer\"/></a>\n";
       var next_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvNext('101544.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_next.gif\" class=\"pointer\"/></a>\n";
       var end_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvEnd('101544.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_end.gif\" class=\"pointer\"/></a>\n";
       if(all_page==''||all_page=='1'||all_page=='0'){
         $("#pageGuide").html(first_disable_Style+forward_disable_Style+next_disable_Style+end_disable_Style);
       }else if(now_page=='1'&&all_page>1){
         $("#pageGuide").html(first_disable_Style+forward_disable_Style+next_able_Style+end_able_Style);
       }else if(now_page==all_page){
         $("#pageGuide").html(first_able_Style+forward_able_Style+next_disable_Style+end_disable_Style);
       }else{
        $("#pageGuide").html(first_able_Style+forward_able_Style+next_able_Style+end_able_Style);
       }      
       function pagemvFirst(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=050000+&PAG_KEY="+tempPAG_KEY+"&"+$("#queryElc").serialize()+"&output=dataRecordList.jsp";
       }
       
       function pagemvForward(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=070000+&PAG_KEY="+tempPAG_KEY+"&"+$("#queryElc").serialize()+"&output=dataRecordList.jsp";
       }
       function pagemvNext(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=080000+&PAG_KEY="+tempPAG_KEY+"&"+$("#queryElc").serialize()+"&output=dataRecordList.jsp";
       }
       function pagemvEnd(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=060000+&PAG_KEY="+tempPAG_KEY+"&"+$("#queryElc").serialize()+"&output=dataRecordList.jsp";
       }
       function getTargetPage(){
        var page = padLeft($('#TargetPage').val(),4);
        var targetURL = "101544.dow?WANTEDPERPAGE="+$('#WantedPerPage').val()+"&PAG_KEY="+tempPAG_KEY + "&TIA_TYP=P&PAG_IDX=00"+page+"&"+$("#queryElc").serialize()+"&output=dataRecordList.jsp";
        window.location.href = targetURL;
       }
       
         //补0
      function padLeft(str,lenght){
        if(str.toString().length >= lenght)   
         return str;   
        else
         return padLeft("0"+str,lenght);
      }
       
       //换每显示数

       function changePerRows(){
       	window.location.href="101544.dow?WANTEDPERPAGE="+$("#WantedPerPage").val()+"&PAG_IDX=aa+&PAG_KEY="+tempPAG_KEY+"&"+$("#queryElc").serialize()+"&output=dataRecordList.jsp";
       	}
       	
  function creatExcel(){
    if('${ETF.GP_TYPE}'==1 || '${ETF.GP_TYPE}'==3){
    	$.ajax({
          type:"POST",
          url:"101547.dow",
          dataType:"json",
          data:$("#queryElc").serialize()+"&GP_TYPE="+'${ETF.GP_TYPE}',
          success:function(data){
          	getExcel(data.RPTNAME);
          }
         })
       }
      else if('${ETF.GP_TYPE}'==2){
      	$.ajax({
          type:"POST",
          url:"101547.dow",
          dataType:"json",
          data:$("#queryElc").serialize()+"&GP_TYPE="+'${ETF.GP_TYPE}',
          success:function(data){
          	getExcel(data.RPTNAME);
          }
         })
      	}
      else(
      	$.ajax({
          type:"POST",
          url:"101547.dow",
          dataType:"json",
          data:$("#queryElc").serialize()+"&GP_TYPE="+'${ETF.GP_TYPE}',
          success:function(data){
          	getExcel(data.RPTNAME);
          }
         })
      	)
     	
	}
 
  </script>
</body>
</html>
=======
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../archive/include/page.htm" %>
<%@include file="../common/blockloading.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据录入记录</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css"/>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/jquery.autocomplete.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/selectOption.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveExcel.js"></script>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
<style type="text/css">
tr th { text-align: center; }
</style>

<script> 

function setTableStyle(){
    /**
     * even/odd rows
     */
    setRows($(".tableContainer table>tbody>tr:odd"));
    
    /**
     * mouseover/mouseout event
     */
    $(".tableContainer table>tbody>tr").each( function() {
        $(this).mouseover( function() {
            $(this).addClass("mouseover");
        });
        $(this).mouseout( function() {
            $(this).removeClass("mouseover");
        });
    });
}

var contextPath = "<peis:contextPath/>";
$(document).ready(function(){
	 autoCompleter("ASSET_NO","101",154);
    $('#btn_edit').bind("click", gotoEdit);
    $('#btn_del').bind("click", gotoDel);
    loadSelectOption3('101543.dow','${ETF.USER_NO}','USER_NO','0');//角色   
    
  if('${ETF.GP_TYPE}'==1 || '${ETF.GP_TYPE}'==3 || '${ETF.GP_TYPE}'=='' || '${ETF.GP_TYPE}'==null){
    $('th[name=2]').hide();
    $('th[name=3]').hide();
    $('td[name=2]').hide();
    $('td[name=3]').hide();
  }else if('${ETF.GP_TYPE}'==2){
  	$('th[name=1]').hide();
  	$('th[name=3]').hide();
  	$('td[name=1]').hide();
    $('td[name=3]').hide();
  	}else{
  	$('th[name=1]').hide();
  	$('th[name=2]').hide();
  	$('td[name=1]').hide();
  	$('td[name=2]').hide();
  	} 
});

/** 去编辑页面  */
function gotoEdit(){
    var selectTr = $('#tbody tr').filter(".selected");
    if(selectTr.length == 0){
        alert("请选中要编辑的行！");
        return;
    }
    var asset_no=$('td:eq(7)', selectTr).html();
    var DATADENS=$('td:eq(9)', selectTr).attr("name"); 
    var gp_id = $('td:eq(0)', selectTr).attr("gp_id");   
    var date="";
    if($('td:eq(9)', selectTr).attr("name")==20){
      var YEAR=$('td:eq(8)', selectTr).attr("ddate").substr(0,4);
      var MONTH_BZ=$('td:eq(8)', selectTr).attr("ddate").substr(4,2);
          date="&YEAR="+YEAR+"&MONTH_BZ="+MONTH_BZ;
    }else{
    	date="&DATE="+$('td:eq(8)', selectTr).html();
    	}
   var selectedDate="?asset_no="+asset_no+date+"&DATADENS="+DATADENS+"&gp_id="+gp_id;
    openEdit(selectedDate);
}

 function openEdit(selectedDate){
   var url="101541.dow"+selectedDate+"&output=jsp/autorm/dataInput.jsp&mod=yes";
   top.showDialogBox("数据录入",url, 400, 1250);
 }
 
 
 /** 删除录入记录  */
function gotoDel(){
    var selectTr = $('#tbody tr').filter(".selected");

    if(selectTr.length == 0){
        alert("请选中要删除的行！");
        return;
    }
    
    if(!window.confirm("是否要删除这条记录！")){
        return;
    }
    var ddate = $('td:eq(8)', selectTr).attr("ddate");
    var DATA_DENS = $('td:eq(9)', selectTr).attr("name");
    var gp_id = $('td:eq(0)', selectTr).attr("gp_id");
    var user_no = $('td:eq(14)', selectTr).attr("user_no");
    var selectedDate="DDATE="+ddate+"&DATA_DENS="+DATA_DENS+"&GP_ID="+gp_id+"&STAT_TIME1="+$('#STAT_TIME1').val()+"&END_TIME="+$('#STAT_TIME1').val();
    deleteRow(selectedDate);
}

function deleteRow(selectedDate){
	var url="101545.dow?"+selectedDate;
   $.ajax({
        type:"POST",
        url:url,
        dataType:"json",
        success:function(data){
                	  if(data.MSG_TYP=='N'){
                	  	alert(data.RSP_MSG);
                	  	window.location.reload();
                	  }
                	  else{alert(data.RSP_MSG);}
                },
        error :function(XMLHttpRequest,textStatus,errorThrown){
               alert("通迅失败");
               }
        })
}
</script>
</head>

<body>
  <div id="body">
  	<form action="101544.dow?output=/jsp/autorm/dataRecordList.jsp" method="post" name="queryElc" id="queryElc">
      <div id="main">
        <div id="tool">
          <table border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="70" class="label">表 号：</td>
              <td width="120" class="dom">
                <input type="text" name="ASSET_NO" value="${ETF.ASSET_NO}" id="ASSET_NO">
              </td>
            </tr>
            <tr>
            	<% int num2 = 1900; Date date=new Date(); int year=date.getYear()+num2;
              	int Day=date.getDate();
              	int month=date.getMonth()+1;
                String time=year+"-"+month+"-"+Day;
              	  %>
              <td width="70" class="label">开始时间：</td>
              <td width="120" class="dom_date">
                <input type="text" name="STAT_TIME1" value=<%=time%> id="STAT_TIME1" onfocus="peisDatePicker()" readonly="readonly">
              </td>
              <td width="70" class="label">结束时间：</td>
              <td width="120" class="dom_date">
                <input type="text" name="END_TIME1" value=<%=time%> id="END_TIME1" onfocus="peisDatePicker()" readonly="readonly">
              </td>
              <td width="70" class="label">录入人：</td>
              <td width="120" class="dom">
                <select name="USER_NO" id="USER_NO">
                </select>  
              </td>
            </tr>
          </table>
          <div class="t_botton">
            <div class="t_right">
              <input class="input1" type="button" id="btn_edit" value="编 辑"/>
              <input class="input1" type="button" id="btn_del" value="删 除"/>
              &nbsp;&nbsp;&nbsp;&nbsp;<input class="input1" type="submit" id="query" value="查 询"/>
            </div>
            <div class="clear"></div>
          </div>
        </div>
        <div class="content">
          <div id="cont_1">
            <div class="target">
              <ul>
                <li class="target_on"><a href="#" onClick="return false;">数据检查</a></li>
                <li class="clear"></li>
              </ul>
              <h1><img src="../../img/bt_data.gif" width="10" height="10" align="middle"" /> <a href="#">修改显示字段</a></h1>
            </div>
            <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 152));">
              <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <thead>
                <tr>
                  <th width="3%">序号</th>
                  <th name=1 width="10%">户号</th>
                  <th name=1 width="10%">户名</th>
                  <th name=2 width="10%">配变编号</th>
                  <th name=2 width="10%">配变名称</th>
                  <th name=3 width="10%">变电站编号</th>
                  <th name=3 width="10%">变电站名称</th>
                  <th width="10%">表号</th>
                  <th width="5%">数据时间</th>
                  <th width="5%">数据类型</th>
                  <th width="5%">正向有功总</th>
                  <th width="5%">反向有功总</th>
                  <th width="5%">正向无功总</th>
                  <th width="5%">反向无功总</th>
                  <th width="5%">追加人</th>
                </tr></thead>
                <tbody id="tbody" align="center">
                  <c:forEach items="${ETF.REC}" var="VIP">
                   <tr onClick="selectSingleRow(this);" style="cursor:pointer;" align="center">
                    <td gp_id="${VIP.GP_ID}">${VIP.ROWNUM}</td> 
                    <td name="1">${VIP.CUST_NO}</td> 
                    <td name="1">${VIP.CUST_NAME}</td> 
                    <td name="2">${VIP.TG_NO}</td> 
                    <td name="2">${VIP.TG_NAME}</td>
                    <td name="3">${VIP.SUBS_NO}</td> 
                    <td name="3">${VIP.SUBS_NAME}</td>
                    <td>${VIP.ASSET_NO}</td>
                    <td ddate="${VIP.DDATE}">${VIP.DDATE_FM}</td> 
                    <td name="${VIP.DATA_DENS}">${VIP.NAME}</td>
                    <td><fmt:formatNumber type="number" value="${VIP.P_ACT_TOTAL}" pattern="0.00"/></td>
                    <td><fmt:formatNumber type="number" value="${VIP.I_ACT_TOTAL}" pattern="0.00"/></td>
                    <td><fmt:formatNumber type="number" value="${VIP.P_REACT_TOTAL}" pattern="0.00"/></td>
                    <td><fmt:formatNumber type="number" value="${VIP.I_REACT_TOTAL}" pattern="0.00"/></td>
                    <td user_no="${ETF.USER_NO}">${VIP.USERNAME}</td>
                   </tr> 
                  </c:forEach>
                </tbody>
              </table>
            </div>
            <div class="pageContainer">
	            <a href="#"><img src="<peis:contextPath/>/img/bt_excel.gif" width="16" height="16" title="导出Excel" onclick="creatExcel()"/></a>　┆　共<span class="orange">${ETF.TOT_REC_NUM}</span>条　显示行数：
            
	            <select name='WantedPerPage' size='1' onchange="changePerRows()" id="WantedPerPage">
                            <option value='10'>10</option> 
                            <option value='20' selected>20</option> 
                            <option value='30'>30</option> 
                            <option value='40'>40</option> 
                            <option value='50'>50</option> 
                            <option value='100'>100</option> 
                            <option value='500'>500</option> 
	            </select>　
	            第<span class="orange">${ETF.PAG_NO}</span>页 / 共<span class="orange">${ETF.PAG_CNT}</span>页　转到：
            
	            <input type="text" id="TargetPage" name="TargetPage" value="1" /> 
	            <img align="middle" src="<peis:contextPath/>/img/bt_go.gif" width="35" height="21" border="0" style="cursor: pointer;" onclick="getTargetPage()"/>　
	            <div id="pageGuide" style="display:inline;"></div>
	        </div>
	       </div>   
        </div>
      </div>
    </form>
  </div>
  <script type="text/javascript">
  	if('${ETF.STAT_TIME1}'!=''){
  	  $('#STAT_TIME1').val('${ETF.STAT_TIME1}');
    }
    if('${ETF.END_TIME1}'!=''){
      $('#END_TIME1').val('${ETF.END_TIME1}'); 
    }
  	 var asset_no=$("#ASSET_NO").val();
  	 var tempWantedPerPage='${ETF.WANTEDPERPAGE}';
     var temppage='${ETF.PAG_NO}';
     var tempPAG_KEY="${ETF.PAG_KEY}"; 
     //动态生成默认页数

       if(tempWantedPerPage!=''){
        $("#WantedPerPage [value='"+tempWantedPerPage+"']").attr("selected","true");
       }
       //动态生成转到栏位的默认值

       if(temppage!=''){
       	$('#TargetPage').val(temppage)
       	}
       	 //动态生成换页图标

       var now_page='${ETF.PAG_NO}';
       var all_page='${ETF.PAG_CNT}';
       var first_disable_Style="<img style='margin-left:-5;margin-right:7;' src=\"<peis:contextPath/>/img/page_home.gif\" class=\"pointer\"/>\n";
       var forward_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_prew.gif\" class=\"pointer\"/>\n";
       var next_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_next.gif\" class=\"pointer\"/>\n";                                                     
       var end_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_end.gif\" class=\"pointer\"/>\n";
       var first_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvFirst('101544.dow','WantedPerPage')\"><img style='margin-left:-5;margin-right:7;' src=\"<peis:contextPath/>/img/page_home.gif\" class=\"pointer\"/></a>\n";
       var forward_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvForward('101544.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_prew.gif\" class=\"pointer\"/></a>\n";
       var next_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvNext('101544.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_next.gif\" class=\"pointer\"/></a>\n";
       var end_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvEnd('101544.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_end.gif\" class=\"pointer\"/></a>\n";
       if(all_page==''||all_page=='1'||all_page=='0'){
         $("#pageGuide").html(first_disable_Style+forward_disable_Style+next_disable_Style+end_disable_Style);
       }else if(now_page=='1'&&all_page>1){
         $("#pageGuide").html(first_disable_Style+forward_disable_Style+next_able_Style+end_able_Style);
       }else if(now_page==all_page){
         $("#pageGuide").html(first_able_Style+forward_able_Style+next_disable_Style+end_disable_Style);
       }else{
        $("#pageGuide").html(first_able_Style+forward_able_Style+next_able_Style+end_able_Style);
       }      
       function pagemvFirst(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=050000+&PAG_KEY="+tempPAG_KEY+"&"+$("#queryElc").serialize()+"&output=dataRecordList.jsp";
       }
       
       function pagemvForward(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=070000+&PAG_KEY="+tempPAG_KEY+"&"+$("#queryElc").serialize()+"&output=dataRecordList.jsp";
       }
       function pagemvNext(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=080000+&PAG_KEY="+tempPAG_KEY+"&"+$("#queryElc").serialize()+"&output=dataRecordList.jsp";
       }
       function pagemvEnd(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=060000+&PAG_KEY="+tempPAG_KEY+"&"+$("#queryElc").serialize()+"&output=dataRecordList.jsp";
       }
       function getTargetPage(){
        var page = padLeft($('#TargetPage').val(),4);
        var targetURL = "101544.dow?WANTEDPERPAGE="+$('#WantedPerPage').val()+"&PAG_KEY="+tempPAG_KEY + "&TIA_TYP=P&PAG_IDX=00"+page+"&"+$("#queryElc").serialize()+"&output=dataRecordList.jsp";
        window.location.href = targetURL;
       }
       
         //补0
      function padLeft(str,lenght){
        if(str.toString().length >= lenght)   
         return str;   
        else
         return padLeft("0"+str,lenght);
      }
       
       //换每显示数

       function changePerRows(){
       	window.location.href="101544.dow?WANTEDPERPAGE="+$("#WantedPerPage").val()+"&PAG_IDX=aa+&PAG_KEY="+tempPAG_KEY+"&"+$("#queryElc").serialize()+"&output=dataRecordList.jsp";
       	}
       	
  function creatExcel(){
    if('${ETF.GP_TYPE}'==1 || '${ETF.GP_TYPE}'==3){
    	$.ajax({
          type:"POST",
          url:"101547.dow",
          dataType:"json",
          data:$("#queryElc").serialize()+"&GP_TYPE="+'${ETF.GP_TYPE}',
          success:function(data){
          	getExcel(data.RPTNAME);
          }
         })
       }
      else if('${ETF.GP_TYPE}'==2){
      	$.ajax({
          type:"POST",
          url:"101547.dow",
          dataType:"json",
          data:$("#queryElc").serialize()+"&GP_TYPE="+'${ETF.GP_TYPE}',
          success:function(data){
          	getExcel(data.RPTNAME);
          }
         })
      	}
      else(
      	$.ajax({
          type:"POST",
          url:"101547.dow",
          dataType:"json",
          data:$("#queryElc").serialize()+"&GP_TYPE="+'${ETF.GP_TYPE}',
          success:function(data){
          	getExcel(data.RPTNAME);
          }
         })
      	)
     	
	}
 
  </script>
</body>
<%@include file="../common/blockendloading.jsp"%>
</html>
>>>>>>> .r1071
