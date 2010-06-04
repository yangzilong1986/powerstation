<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include/page.htm" %>
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>专线用户定位</title>
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
  <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
  <script type="text/javascript">
  </script>
 </head>
 <body onload="loadQuery()">
<form action="112038.dow" method="post" name="custSP"> 
 <div id="main">
  <div id="tool">
    <div class="opbutton1">
      <input type="submit" id="query" value="查 询" class="input1" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="save" value="选定用户" class="input2" onclick="finish();" />
    </div>
    <table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="100" class="label">用户：</td>
        <td width="120" class="dom">
          <input type="text" id="cust" name="cust"/>
        </td>
        <td colspan="3"></td>
      </tr>
    </table>
  </div>
  <div class="content">
   <div id="cont_1">
    <div class="tableContainer" style="height: expression((( document.documentElement.clientHeight || document.body.clientHeight) -  60) );">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <thead>
         <tr>
          <th>序号</th>
          <th>户号</th>
          <th>户名</th>
          <th>供电单位</th>
          <th>行业</th>
          <th>状态</th>
         </tr>
        </thead>
        <tbody>
          <c:forEach items="${ETF.REC}" var="VIP">
            <tr onClick="selectSingleRow(this);" style="cursor:pointer;" align="center">
             <td>${VIP.ROWNUM}</td> 
             <td name="${VIP.CUST_ID}">${VIP.CUST_NO}</td> 
             <td>${VIP.CUST_NAME}</td> 
             <td>${VIP.ORG_NAME}</td> 
             <td>${VIP.TRADE_NAME}</td>  
             <td>${VIP.NAME}</td>
            </tr> 
          </c:forEach>
        </tbody>
       </table>
    </div>
    <div class="pageContainer">
	        共<span class="orange">${ETF.TOT_REC_NUM}</span>条　显示行数：

	        <select name='WantedPerPage' size='1' onchange="changePerRows()" id="WantedPerPage">
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
 <script type="text/javascript">
 	 //默认值问题

       var tempCUST = '${ETF.CUST}';
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
       //动态生成变电站的默认值

       if(tempCUST!=''){
       	$('#cust').val(tempCUST)
       	}
       	
        //动态生成换页图标

       var now_page='${ETF.PAG_NO}';
       var all_page='${ETF.PAG_CNT}';
       var first_disable_Style="<img style='margin-left:-5;margin-right:7;' src=\"<peis:contextPath/>/img/page_home.gif\" class=\"pointer\"/>\n";
       var forward_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_prew.gif\" class=\"pointer\"/>\n";
       var next_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_next.gif\" class=\"pointer\"/>\n";                                                     
       var end_disable_Style="<img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_end.gif\" class=\"pointer\"/>\n";
       var first_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvFirst('112038.dow','WantedPerPage')\"><img style='margin-left:-5;margin-right:7;' src=\"<peis:contextPath/>/img/page_home.gif\" class=\"pointer\"/></a>\n";
       var forward_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvForward('112038.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_prew.gif\" class=\"pointer\"/></a>\n";
       var next_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvNext('112038.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_next.gif\" class=\"pointer\"/></a>\n";
       var end_able_Style="<a href=\"#\" id=\"queryNext\" onclick=\"pagemvEnd('112038.dow','WantedPerPage')\"><img style='margin-left:7;margin-right:7;' src=\"<peis:contextPath/>/img/page_end.gif\" class=\"pointer\"/></a>\n";
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
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=050000+&PAG_KEY="+tempPAG_KEY+"&CUST="+tempCUST;
       }
       
       function pagemvForward(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=070000+&PAG_KEY="+tempPAG_KEY+"&CUST="+tempCUST;
       }
       function pagemvNext(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=080000+&PAG_KEY="+tempPAG_KEY+"&CUST="+tempCUST;
       }
       function pagemvEnd(servce,param1){ 
        var strparam1 = document.getElementById(param1).value;
        window.location.href=servce+"?"+param1+"="+strparam1+"&"+"&TIA_TYP=P&PAG_IDX=060000+&PAG_KEY="+tempPAG_KEY+"&CUST="+tempCUST;
       }
       function getTargetPage(){
        var page = padLeft($('#TargetPage').val(),4);
        var targetURL = "112038.dow?WANTEDPERPAGE="+$('#WantedPerPage').val()+"&PAG_KEY="+tempPAG_KEY + "&TIA_TYP=P&PAG_IDX=00"+page+"&CUST="+tempCUST;
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
       		window.location.href="112038.dow?WANTEDPERPAGE="+$("#WantedPerPage").val()+"&PAG_IDX=aa+&PAG_KEY="+tempPAG_KEY+"&CUST="+tempCUST;
       	}
   function finish(){
    var spCust=$(".selected td:eq(2)").html();  //取到选中行的专线用户
    var spCustId=$(".selected td:eq(1)").attr("name");
    if(spCust==null){
       alert("请选择用户");
    }else{
        $('#specialCust3',parent.document).val(spCust);
         $('#specialCust5',parent.document).val(spCustId);
        if(top.getMainFrameObj()!=null){
	      top.getMainFrameObj().mainframe.$('#specialCust3').val(spCust);
	      top.getMainFrameObj().mainframe.$('#specialCust5').val(spCustId);
	    }
	    parent.GB_hide();
    } 
  }
   function loadQuery(){
   	if(${empty ETF.TXN_CD}){
   		 window.location.href="112038.dow?WANTEDPERPAGE="+$("#WantedPerPage").val()+"&LOAD=YES";
   		}
   	}
  </script>
  </div>
</form>
 </body>
</html>