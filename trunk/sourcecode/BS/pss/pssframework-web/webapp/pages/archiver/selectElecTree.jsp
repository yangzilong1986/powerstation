<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>对象树</title>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/archive/tree/jquery.treeview.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/archive/jquery.cookie.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/tree/jquery.treeview.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
  $("#browser").treeview();
  });
//接收从主页面传过来的变电站标识

var tempSUBS_ID=<%=request.getParameter("subs_id")%>;
</script>
<style>
		body {
    margin: 0;
    font-family: Arial, Helvetica, sans-serif;
    font-size: 13px;
    color: #333;
    overflow-x: auto;
    overflow-y: auto;
    scrollbar-3dlight-color: #8cb6e7;
    scrollbar-arrow-color: #46617f;
    scrollbar-base-color: #CFCFCF;
    scrollbar-darkshadow-color: #FFF;
    scrollbar-face-color: #ebf4fe;
    scrollbar-highlight-color: #ebf4fe;
    scrollbar-shadow-color: #8cb6e7;
}
table, td, div, h1 {
    font-family: Arial, Helvetica, sans-serif;
    font-size: 13px;
    word-break: break-all; word-wrap: break-word;
}
  .textfocuse{
    background:#0A726A;
    color:#FFFFFF;
    }
</style>
</head>
<body>
<div class="treeContainer">
  <ul id="browser" class="filetree">
    <li>
    	<table style="display:inline;margin-left:-2pt;margin-top:-3pt;" ><tr>
      	<td><img src="<peis:contextPath/>/img/tree/object_tran.gif" alt="" width="15" height="15" align="bottom"></td>
        <td onclick="select(this,'${ETF.SUBS_ID}','${ETF.SUBS_ID}','SUBS')"> ${ETF.SUBS_NAME}</td>
      </tr></table>
      <ul>
        <li id="terminal"><div><img src="<peis:contextPath/>/img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom"> 终端</div>
          <ul>
              <c:forEach items="${ETF.TERMINAL}" var="MSG">
              	<li class="closed" id="${MSG.TERM_ID}">
              		<table style="display:inline;margin-left:-2pt;margin-top:-3pt;" ><tr>
              			<td><img src="<peis:contextPath/>/img/tree/object_org.gif" alt="" width="15" height="15" align="bottom"></td>
              		  <td onclick="select(this,'${MSG.TERM_ID}','${MSG.OBJECT_ID}','TERM')"> ${MSG.LOGICAL_ADDR}</td>
              		</tr></table>
              	</li>
              </c:forEach>
          </ul>
        </li>
        <li id="tran"><div><img src="<peis:contextPath/>/img/tree/object_tg.gif" alt="" border="0" width="15" height="15" align="bottom"> 主变</div>
           <ul>
              <c:forEach items="${ETF.TRAN}" var="MSG">
                <li class="closed" onclick="queryMeterMp('TRAN','${MSG.OBJECT_ID}','${MSG.TRAN_ID}')" name="queryMeterMp" id="${MSG.TRAN_ID}">
                		<table style="display:inline;margin-left:-2pt;margin-top:-3pt;" ><tr>
              			  <td><img src="<peis:contextPath/>/img/tree/object_tg.gif" alt="" width="15" height="15" align="bottom"></td>
              		    <td onclick="select(this,'${MSG.TRAN_ID}','${MSG.OBJECT_ID}','TRAN')"> ${MSG.TRAN_NAME}</td>
              		  </tr></table>
                	<ul>
                	</ul>
                </li>
              </c:forEach>
            </ul>
        </li>
        <li id="ml"><div><img src="<peis:contextPath/>/img/tree/object_line.gif" alt="" border="0" width="15" height="15" align="bottom"> 母线</div>
            <ul>
              <c:forEach items="${ETF.ML}" var="MSG">
                <li class="closed" onclick="queryMeterMp('ML','${MSG.SUBS_ID}','${MSG.ML_ID}')" id="${MSG.ML_ID}">
                		<table style="display:inline;margin-left:-2pt;margin-top:-3pt;" ><tr>
              			  <td><img src="<peis:contextPath/>/img/tree/object_line.gif" alt="" width="15" height="15" align="bottom"></td>
              		    <td onclick="select(this,'${MSG.ML_ID}','${MSG.SUBS_ID}','ML')"> ${MSG.ML_NAME}</td>
              		  </tr></table>
                	<ul>
                	</ul>
                </li>
              </c:forEach>
            </ul>
        </li>
      </ul>
    </li>
  </ul>
</div>
<script type="text/javascript">
	$(document).ready(function(){
    $("table").click(function(){
      $(this).addClass("textfocuse");
      $("table").not(this).removeClass("textfocuse");
      });
    });
	//查电表信息

function queryMeterMp(FLG,SUBS_ID,liid){
	if($("li[id='"+liid+"'] > ul > li").length > 0){ //有电表信息则什么都不做
	  return false;
	 }else{
	 	$.ajax({ 
          type:"POST",
          url:"112112.dow",
          dataType:"json",
          data:{FLG:FLG,SUBS_ID:SUBS_ID,ID:liid},
         success:function(data){
         	       var datahtmlall = "";
         	       var allMsg=data.MSG;
         	       if(allMsg!=undefined){
         	         var datalength = data.MSG.length;
         	         var dataall = data.MSG;
         	         for (var i = 0 ; i < datalength ; i++){
         	             datahtmlall += "<li class='closed'>";
         	             datahtmlall +="<table style=\"display:inline;margin-left:-2pt;margin-top:-3pt;\" ><tr>";
         	             datahtmlall +="<td><img src='<peis:contextPath/>/img/tree/object_org.gif' alt='' width='15' height='15' align='bottom'></td>";
         	             datahtmlall +="<td onclick=\"select(this,"+dataall[i].MP_ID+","+dataall[i].SUBS_ID+","+"'MP')\">"+ dataall[i].MP_NAME+"</td>";
         	             datahtmlall +="</tr></table></li>";
         	           }
         	           branches = $(datahtmlall).prependTo("#"+liid+" > ul");
         	           
                      $("#browser").treeview({
                       add: branches
                           });
                 }
                 $("table").click(function(){
                    $(this).addClass("textfocuse");
                    $("table").not(this).removeClass("textfocuse");
                   });
               },
         error:function(data){
         	      alert("通讯错误");
                 }
    })
	 	}
}

	function select(obj, objectId, subsId,objectI) {
    if(objectI=="SUBS"){
      window.parent.mainframe.location.href="112022.dow?SUBS_ID="+tempSUBS_ID+"&FLG=QUERY&OPEN_NEW=elecField.jsp";
    }
    if(objectI=="TERM"){
      window.parent.mainframe.location.href="112035.dow?TERM_ID="+objectId+"&SUBS_ID="+subsId+"&FLG=QUERY&SUBS_TYPE=2";
    }
    if(objectI=="TRAN"){
      window.parent.mainframe.location.href="112030.dow?TRAN_ID="+objectId+"&SUBS_ID="+subsId+"&FLG=QUERY&SUBS_TYPE=2";
    }
    if(objectI=="ML"){
  	  window.parent.mainframe.location.href="112031.dow?ML_ID="+objectId+"&SUBS_ID="+subsId+"&FLG=QUERY&SUBS_TYPE=2";
    }
     if(objectI=="MP"){
  	  window.parent.mainframe.location.href="112046.dow?MP_ID="+objectId+"&SUBS_ID="+subsId+"&FLG=QUERY";
    }
}
</script>
</body>
</html>