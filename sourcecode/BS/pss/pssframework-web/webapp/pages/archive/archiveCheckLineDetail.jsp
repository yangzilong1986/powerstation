<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>线路详细信息</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveExcel.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
//更新审核状态
function updateCheckStatus(id,checkStatus){
   if(confirm("是否执行该操作")==true){
     var params={
        'ZJKID':id,
        'SHZT':checkStatus,
        'TABLE_NAME':'LAPF'
      };
     $.ajax({
      type:"POST",
      url:"114003.dow",
      dataType:"json",
      data:params,
      success:function(data){
               if(data.MSG_TYP=='N'){
              	  	alert(data.RSP_MSG);
              	  	archiveCheckForm.submit();
               }else{alert(data.RSP_MSG);}
            },
      error :function(XMLHttpRequest,textStatus,errorThrown){
              alert("更新失败");
           }
      })
   }
}
//关闭窗口提交父页面表单
function extra(){
   top.getMainFrameObj().archiveCheckForm.submit();
}
//导出excel
function creatExcel(){
    var START_TIME =$("input[name='START_TIME']").val();
    var END_TIME =$("input[name='END_TIME']").val(); 
    var CHECK_STATUS =$("input[name='CHECK_STATUS']").val(); 
	$.ajax({
      type:"POST",
      url:"114017.dow",
      dataType:"json",
      data:{"START_TIME":START_TIME,"END_TIME":END_TIME,"CHECK_STATUS":CHECK_STATUS},
      success:function(data){
      	getExcel(data.RPTNAME);
      }
    })
}
</script>
</head>
<body style="overflow: hidden;">
<form name="archiveCheckForm" action="114004.dow" method="post" id="mainform">
<input type="hidden" name="WANTEDPERPAGE" value="${ETF.WANTEDPERPAGE}">
<input type="hidden" name="START_TIME" value="${ETF.START_TIME}">
<input type="hidden" name="END_TIME" value="${ETF.END_TIME}">
<input type="hidden" name="CHECK_STATUS" value="${ETF.CHECK_STATUS}">
<div id="main">
  <div class="content">
    <div id="cont_1">
      <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 85));">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <thead>
            <tr>
              <th>序号</th>
              <th>营销操作</th>
              <th>归档时间</th>
              <th>线路名称</th>
              <th>电压等级</th>
              <th>变电站名称</th>
              <th>所属部门</th>
              <th>处理状态</th>
              <th>审核状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody id="tbody">
             <c:forEach items="${ETF.REC}" var="OBJECT" varStatus="status">
                <tr onClick="selectSingleRow(this);" style="cursor:pointer;" align="left">
                 <td align="center">${OBJECT.ROW_ID}<br></td>
                 <td align="center">${OBJECT.CZ}<br></td>
                 <td align="center">${OBJECT.INSERTTIME}</td> 
                 <td align="center">${OBJECT.LINENAME}</td> 
                 <td align="center">${OBJECT.VOLT_GRADE}</td> 
                 <td align="center">${OBJECT.SUBS_NAME}</td>
                 <td align="center">${OBJECT.ORG_NAME}</td>
                 <td align="center">${OBJECT.DL}</td>
                 <td align="center">${OBJECT.SHZT}</td> 
                 <td align="center">
                   <a href="javascript:updateCheckStatus('${OBJECT.ZJKID}','20');">通过</a>
                   |<a href="javascript:updateCheckStatus('${OBJECT.ZJKID}','30');">不通过</a>
                 </td> 
                </tr> 
              </c:forEach>
          </tbody>
        </table>
      </div>
      <div class="pageContainer">
        <!-- 导出EXCEL -->
        <a href="#"><img src="<peis:contextPath/>/img/bt_excel.gif" width="16" height="16" title="导出Excel" onclick="creatExcel()"/></a>　┆　共<span class="orange">${ETF.TOT_REC_NUM}</span>条　显示行数：
        <!-- 换行 -->
        <select name='WantedPerPage' size='1' onchange="changePerRows()" id="WantedPerPage">
            <option value='5'>5</option> 
            <option value='10'>10</option> 
            <option value='20'>20</option> 
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
<script type="text/javascript">
 var WantedPerPage = '${ETF.WANTEDPERPAGE}'
 $("#WantedPerPage").val(WantedPerPage);
 
 var now_page='${ETF.PAG_NO}';
 var all_page='${ETF.PAG_CNT}';
    var first_disable_Style="<img style='margin-left:-5;margin-right:7;' src=\"<peis:contextPath/>/img/page_home.gif\" class=\"pointer\"/>\n";
    var forward_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_prew.gif\" class=\"pointer\"/>\n";
    var next_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_next.gif\" class=\"pointer\"/>\n";                                                     
    var end_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_end.gif\" class=\"pointer\"/>\n";
    var first_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvFirst('114004.dow','WantedPerPage')\"><img style='margin-left:-5;margin-right:7;' src=\"<peis:contextPath/>/img/page_home.gif\" class=\"pointer\"/></a>\n";
    var forward_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvForward('114004.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_prew.gif\" class=\"pointer\"/></a>\n";
    var next_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvNext('114004.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_next.gif\" class=\"pointer\"/></a>\n";
    var end_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvEnd('114004.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_end.gif\" class=\"pointer\"/></a>\n";
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
     window.location.href=servce+"?"+param1+"="+strparam1+"&PAG_NO=1"+"&"+$("#mainform").serialize();
    }
    function pagemvForward(servce,param1){
     var strparam1 = document.getElementById(param1).value;
     window.location.href=servce+"?"+param1+"="+strparam1+"&PAG_NO="+${ETF.PAG_NO - 1}+"&"+$("#mainform").serialize();
    }
    function pagemvNext(servce,param1){
     var strparam1 = document.getElementById(param1).value;
     window.location.href=servce+"?"+param1+"="+strparam1+"&PAG_NO="+${ETF.PAG_NO + 1}+"&"+$("#mainform").serialize();
    }
    function pagemvEnd(servce,param1){
     var strparam1 = document.getElementById(param1).value;
     window.location.href=servce+"?"+param1+"="+strparam1+"&PAG_NO="+${ETF.PAG_CNT + 0}+"&"+$("#mainform").serialize();
    }
    function getTargetPage(){
 	   hideContractCapa(); 
 	   var page = $("#TargetPage").val() ;
        var targetURL = "114004.dow?PAG_NO="+page+"&"+$("#mainform").serialize();
        window.location.href = targetURL;
    }
    //换每显示数
    function changePerRows(){
       $("input[name='WANTEDPERPAGE']").val($("#WantedPerPage").val());
       archiveCheckForm.submit();
    }
</script>
</body>
</html>