<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>厂站档案编辑第二步</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/jquery.autocomplete.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveJson.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveExcel.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
$(document).ready(function(){
    autoCompleter("czmc","45",154);
});
</script>
</head>
<body onload="loadVols('${ETF.VOLT_GRADE}')">
<form action="112043.dow" method="post" name="swst"> 
<div id="body">
   <jsp:include page="archiveTabs.jsp" flush="true">
      <jsp:param value="4" name="pageType"/>
    </jsp:include>
	<div id="main">
		<div id="tool">
      <div class="opbutton1"><input type="submit" name="query" id="query" value="查 询" class="input1" /></div>
      <table border="0" cellpadding="0" cellspacing="0" >
         <tr>
           <td width="70" class="label" align="center">开关站：</td>
           <td width="180">
              <input type="text" name="czmc" id="czmc" value="请输入厂站号或厂站名" onFocus="OnEnter(this)" onBlur="OnExit(this)"  style="color:#C5C5C5"/></td>
           <td width="70" class="label" align="center">电压等级：</td>
           <td width="180" class="dom">
             <select id="VOLT_GRADE" name="VOLT_GRADE">
             </select>
           </td>
           <td width="33%"></td>
         </tr>
      </table>
		</div>
		<div class="content">
			<div id="cont_1">
				<div class="target4">
          <ul>
            <li class="target_on">
              <a href="#" onclick="setTarget(1); return false;">开关站列表</a>
            </li>
            <li class="clear"></li>
          </ul>
				</div>
				<div class="tableContainer" style="height: expression((( document.documentElement.clientHeight || document.body.clientHeight) -   183 ) ); width:expression((( document.documentElement.clientWidth||document.body.clientWidth)-46 ) );">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<thead>
						<tr>
						  <th>序号</th>
						  <th>开关站编号</th>
						  <th>开关站名称</th>
						  <th>电压等级</th>
						  <th>管理单位</th>
						  <th>状态</th>
						  <th>操作</th>
						</tr></thead>
	          <tbody id="tbody">
	            <c:forEach items="${ETF.REC}" var="VIP">
                <tr onClick="selectSingleRow(this);" style="cursor:pointer;" align="center">
                 <td>${VIP.ROWNUM}</td> 
                 <td name="${VIP.SUBS_ID}" orgNo="${VIP.ORG_NO}">${VIP.SUBS_NO}</td> 
                 <td>${VIP.SUBS_NAME}</td> 
                 <td>${VIP.VOLT_GRADE}</td> 
                 <!--td>${VIP.LINE_NAME}</td-->
                 <td>${VIP.ORG_NAME}</td>   
                 <td>${VIP.SUBS_STATUS}</td> 
                 <td><a href="javascript:deleteTg('${VIP.SUBS_ID}','${VIP.SUBS_NO}');">删除</a></td>
                </tr> 
              </c:forEach>
	          </tbody>
        	</table>
    		</div>
	      <div class="pageContainer">
	        <a href="#"><img src="<peis:contextPath/>/img/bt_excel.gif" width="16" height="16" title="导出Excel" onclick="creatExcel()"/></a>　┆　共<span class="orange">${ETF.TOT_REC_NUM}</span>条　显示行数：

	        <select name='WantedPerPage' size='1' onchange="changePerRows()" id="WantedPerPage">
	        	            <option value='5'>5</option> 
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
	      <div class="guidePanel">
			    <input type="button" id="last" value="上一步" class="input1" onclick="lastStep();" />
			    &nbsp;&nbsp;&nbsp;&nbsp;
			    <input type="button" id="next" value="下一步" class="input1" onclick="nextStep();" />
			  </div>
		  </div>
		</div>
	</div>
	 <script language=javascript>
       //默认值问题

       var tempCZMC = '${ETF.CZMC}';
       var tempVOLT_GRADE='${ETF.VOLT_GRADE}';
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
      	//动态生成VOLT_GRADE的默认值

       if(tempVOLT_GRADE!=''){
       	$('#VOLT_GRADE').val(tempVOLT_GRADE)
       	}
       //动态生成变电站的默认值

       if(tempCZMC!=''){
       	$('#czmc').val(tempCZMC)
       	}
       	
        //动态生成换页图标

       var now_page='${ETF.PAG_NO}';
       var all_page='${ETF.PAG_CNT}';
       var first_disable_Style="<img style='margin-left:-5;margin-right:7;' src=\"<peis:contextPath/>/img/page_home.gif\" class=\"pointer\"/>\n";
       var forward_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_prew.gif\" class=\"pointer\"/>\n";
       var next_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_next.gif\" class=\"pointer\"/>\n";                                                     
       var end_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_end.gif\" class=\"pointer\"/>\n";
       var first_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvFirst('112043.dow','WantedPerPage')\"><img style='margin-left:-5;margin-right:7;' src=\"<peis:contextPath/>/img/page_home.gif\" class=\"pointer\"/></a>\n";
       var forward_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvForward('112043.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_prew.gif\" class=\"pointer\"/></a>\n";
       var next_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvNext('112043.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_next.gif\" class=\"pointer\"/></a>\n";
       var end_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvEnd('112043.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_end.gif\" class=\"pointer\"/></a>\n";
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
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=050000+&PAG_KEY="+tempPAG_KEY+"&VOLT_GRADE="+tempVOLT_GRADE+"&SUBS_NM="+tempCZMC;
       }
       
       function pagemvForward(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=070000+&PAG_KEY="+tempPAG_KEY+"&VOLT_GRADE="+tempVOLT_GRADE+"&CZMC="+tempCZMC;
       }
       function pagemvNext(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=080000+&PAG_KEY="+tempPAG_KEY+"&VOLT_GRADE="+tempVOLT_GRADE+"&CZMC="+tempCZMC;
       }
       function pagemvEnd(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=060000+&PAG_KEY="+tempPAG_KEY+"&VOLT_GRADE="+tempVOLT_GRADE+"&CZMC="+tempCZMC;
       }
       function getTargetPage(){
        var page = padLeft($('#TargetPage').val(),4);
        var targetURL = "112043.dow?WANTEDPERPAGE="+$('#WantedPerPage').val()+"&PAG_KEY="+tempPAG_KEY + "&TIA_TYP=P&PAG_IDX=00"+page+"&VOLT_GRADE="+tempVOLT_GRADE+"&CZMC="+tempCZMC;
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
       		window.location.href="112043.dow?WANTEDPERPAGE="+$("#WantedPerPage").val()+"&PAG_IDX=aa+&PAG_KEY="+tempPAG_KEY+"&CZMC="+tempCZMC+"&VOLT_GRADE="+tempVOLT_GRADE;
       	}
function OnEnter( field ) { 
  if( field.value == field.defaultValue ) {
     field.value = "";
     document.all.czmc.style.color="#000000";
  } 
}

function OnExit( field ) { 
  if( field.value == "" ) { 
    field.value = field.defaultValue;
    document.all.czmc.style.color="#C5C5C5"; 
  } 
}
//上一步

function lastStep(){
  window.location.href="<peis:contextPath/>/jsp/archive/updateStranSubstation.jsp";
}
//下一步

function nextStep(){
   var tgNo=$(".selected td:eq(1)").attr("name");  //取到选中变电站

   var tgNo1=$(".selected td:eq(1)").attr("orgNo");  //取到选中变电站

   if(tgNo==null){
     alert("请选中一个开关站！");
   }else{
     window.location.href="<peis:contextPath/>/jsp/archive/addSwitchStation.jsp?SUBS_ID="+tgNo+"&PARAM=1&ORG_NO="+tgNo1+"&CZMC="+tempCZMC+"&VOLT_GRADE="+tempVOLT_GRADE+"&WANTEDPERPAGE="+tempWantedPerPage+"&SUBS_TYPE=3";
   }
}
//删除变电站

function deleteTg(param,param1){
   if(confirm("是否删除该开关站")==true){
     $.ajax({
      type:"POST",
      url:"112021.dow",
      dataType:"json",
      data:{SUBS_ID:param,SUBS_NO:param1,SUBS_TYPE:"3"},
      success:function(data){
              	  if(data.MSG_TYP=='N'){
              	  	alert(data.RSP_MSG);
              	  	window.swst.submit();
              	  }
              	  else{alert(data.RSP_MSG);}
              },
      error :function(XMLHttpRequest,textStatus,errorThrown){
             alert("通迅失败");
             }
      })
   }
}

function creatExcel(){

	$.ajax({
      type:"POST",
      url:"112051.dow",
      dataType:"json",
      data:{CZMC:tempCZMC,VOLT_GRADE:tempVOLT_GRADE,FLG:"SWS"},
      success:function(data){
      	getExcel(data.RPTNAME);
      }
     })
     	
	}
     </script>
</div>
</form>
</body>
</html>
