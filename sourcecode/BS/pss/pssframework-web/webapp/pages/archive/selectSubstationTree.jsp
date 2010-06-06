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
</script>
<style>
  .textfocuse{
    background:#0A726A;
    color:#FFFFFF;
    }
</style>
</head>
<body>
<div class="treeContainer">
  <ul id="browser" class="filetree">
    <li><img src="<peis:contextPath/>/img/tree/object_tran.gif" alt="" border="0" width="15" height="15" align="bottom">${ETF.SUBS_NAME}变电站

      <ul>
        <li class="closed" id="terminal"><div><img src="<peis:contextPath/>/img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">终端</div>
          <ul>
              <c:forEach items="${ETF.TERMINAL}" var="MSG">
                <li>
                  <table style="display:inline;margin-left:-2pt;margin-top:-3pt;"><tr>
                    <td><img src="<peis:contextPath/>/img/tree/object_line.gif" alt="" border="0" width="15" height="15" align="bottom"></td>
                    <td>${MSG.ASSET_NO}</td></tr>
                  </table>
                </li>
              </c:forEach>
          </ul>
        </li>
        <li class="closed" id="tran"><div><img src="<peis:contextPath/>/img/tree/object_tg.gif" alt="" border="0" width="15" height="15" align="bottom">变压器</div>
           <ul>
              <c:forEach items="${ETF.TRAN}" var="MSG">
                <li class="closed" onclick="queryMeterMp('TRAN','${MSG.SUBS_ID}','${MSG.TRAN_ID}')" name="queryMeterMp" id="${MSG.TRAN_ID}">
                  <table style="display:inline;margin-left:-2pt;margin-top:-3pt;"><tr>
                    <td><img style="float:left;" src="<peis:contextPath/>/img/tree/object_tg.gif" alt="" border="0" width="15" height="15" align="bottom"></td>
                    <td>${MSG.TRAN_NAME}</td></tr>
                  </table>
                  <ul>
                  </ul>
                </li>
              </c:forEach>
            </ul>
        </li>
        <li  id="ml"><div><img src="<peis:contextPath/>/img/tree/object_line.gif" alt="" border="0" width="15" height="15" align="bottom">母线</div>
            <ul>
              <c:forEach items="${ETF.ML}" var="MSG">
                <li class="closed" onclick="queryMeterMp('ML','${MSG.SUBS_ID}','${MSG.ML_ID}')" id="${MSG.ML_ID}">
                  <table style="display:inline;margin-left:-2pt;margin-top:-3pt;"><tr>
                    <td><img src="<peis:contextPath/>/img/tree/object_line.gif" alt="" border="0" width="15" height="15" align="bottom"></td>
                    <td>${MSG.ML_NAME}</td></tr>
                  </table>
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
                   for (var i = 0 ; i < datalength ; i++)
                       datahtmlall += "<li><div class='content1'>" + dataall[i].MP_NAME + "</div></li>";
                   //$("li[id='"+liid+"'] > ul").html(datahtmlall);
    branches = $(datahtmlall).prependTo("li[id='"+liid+"'] > ul");
    $("#browser").treeview({
      add: branches
    });
                 }
               },
         error:function(data){
                alert("通讯错误");
                 }
    })
    }
}

</script>
</body>
</html>