<<<<<<< .mine
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../archive/include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据手工录入</title>
<link rel="stylesheet" type="text/css" href="../../css/main.css"/>
<script type="text/javascript" src="../../js/jquery.js"></script>
<script type="text/javascript"> 

function changeTab(index){
    if(index == "1") {       //数据录入
        $("#target_1").css("display", "block");
        $("#target_2").css("display", "none");
    }
    else if(index == "2") {  //录入记录查询
    	top.getMainFrameObj().recordpage.queryElc.submit() ;
        $("#target_1").css("display", "none");
        $("#target_2").css("display", "block");
    }
}

function init(){
    inputpage.document.location  = "<peis:contextPath/>/jsp/autorm/dataInput.jsp";
    recordpage.document.location  = "<peis:contextPath/>/jsp/autorm/dataRecordList.jsp";
}
</script>
</head>
<body onload="init()">
  <div id="body">
    <div id="target_1">
      <div id="nav_area" class="tab2">
        <ul id="nav_ul">
          <li class="tab_on"><a href="javascript:changeTab(1);" onfocus="blur()">数据录入</a></li>
          <li class="tab_off"><a href="javascript:changeTab(2);" onfocus="blur()">录入记录查询</a></li>
          <li class="clear"></li>
        </ul>
        <h1><a href="#"><img src="../../img/bt_help.gif" width="14" height="15" /></a></h1>
      </div>
      <div id="main" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 44));"> 
        <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
          <tr>
            <td>
              <iframe name="inputpage" frameborder="0" width="100%" height="100%" scrolling="no" src=""></iframe>
            </td>
          </tr>
        </table>
      </div>
    </div>
    
    <div id="target_2" style="display: none;">
      <div id="nav_area" class="tab2">
        <ul id="nav_ul">
          <li class="tab_off"><a href="javascript:changeTab(1);" onfocus="blur()">数据录入</a></li>
          <li class="tab_on"><a href="javascript:changeTab(2);" onfocus="blur()">录入记录查询</a></li>
          <li class="clear"></li>
        </ul>
        <h1><a href="#"><img src="../../img/bt_help.gif" width="14" height="15" /></a></h1>
      </div>
      <div id="main" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 44));"> 
        <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
          <tr>
            <td>
              <iframe name="recordpage" frameborder="0" width="100%" height="100%" scrolling="no" src="" id="recordpage"></iframe>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</body>
</html>
=======
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../archive/include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据手工录入</title>
<link rel="stylesheet" type="text/css" href="../../css/main.css"/>
<script type="text/javascript" src="../../js/jquery.js"></script>
<script type="text/javascript"> 

function changeTab(index){
    if(index == "1") {       //数据录入
        $("#target_1").css("display", "block");
        $("#target_2").css("display", "none");
    }
    else if(index == "2") {  //录入记录查询
    	top.getMainFrameObj().recordpage.queryElc.submit() ;
        $("#target_1").css("display", "none");
        $("#target_2").css("display", "block");
    }
}

function init(){
    inputpage.document.location  = "<peis:contextPath/>/jsp/autorm/a_cyj_test.jsp";
    recordpage.document.location  = "<peis:contextPath/>/jsp/autorm/dataRecordList.jsp";
}
</script>
</head>
<body onload="init()">
  <div id="body">
    <div id="target_1">
      <div id="nav_area" class="tab2">
        <ul id="nav_ul">
          <li class="tab_on"><a href="javascript:changeTab(1);" onfocus="blur()">数据录入</a></li>
          <li class="tab_off"><a href="javascript:changeTab(2);" onfocus="blur()">录入记录查询</a></li>
          <li class="clear"></li>
        </ul>
        <h1><a href="#"><img src="../../img/bt_help.gif" width="14" height="15" /></a></h1>
      </div>
      <div id="main" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 44));"> 
        <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
          <tr>
            <td>
              <iframe name="inputpage" frameborder="0" width="100%" height="100%" scrolling="no" src=""></iframe>
            </td>
          </tr>
        </table>
      </div>
    </div>
    
    <div id="target_2" style="display: none;">
      <div id="nav_area" class="tab2">
        <ul id="nav_ul">
          <li class="tab_off"><a href="javascript:changeTab(1);" onfocus="blur()">数据录入</a></li>
          <li class="tab_on"><a href="javascript:changeTab(2);" onfocus="blur()">录入记录查询</a></li>
          <li class="clear"></li>
        </ul>
        <h1><a href="#"><img src="../../img/bt_help.gif" width="14" height="15" /></a></h1>
      </div>
      <div id="main" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 44));"> 
        <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
          <tr>
            <td>
              <iframe name="recordpage" frameborder="0" width="100%" height="100%" scrolling="no" src="" id="recordpage"></iframe>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</body>
</html>
>>>>>>> .r837
