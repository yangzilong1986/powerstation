<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中继类型维护</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/greybox.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
<script type="text/javascript">
var relayType=$.url.param("relayType");
$(function(){
     $('#relayAddr1').hide();
     $('#relayAddr2').hide();
     $('#relayAddr3').hide();
     $('#relayAddr4').hide();
     $('#relayAddr1Label').hide();
     $('#relayAddr2Label').hide();
     $('#relayAddr3Label').hide();
     $('#relayAddr4Label').hide();
  $('#relayType').val(relayType);
  addrInit(relayType);
  $('#relayType').change(addrInit);
});
//保存
function save(){
   updateRelayType(top.getMainFrameObj());
   updateRelayType(parent);
   parent.GB_hide();
}

//更新父窗口relayType控件
function updateRelayType(object){
   relayType=$('#relayType').val();
   if(relayType==1){
     $('#relayAddr1',object.document).val($('#relayAddr1').val());
   }else if(relayType==2){
     $('#relayAddr1',object.document).val($('#relayAddr1').val());
     $('#relayAddr2',object.document).val($('#relayAddr2').val());
   }else if(relayType==3){
     $('#relayAddr1',object.document).val($('#relayAddr1').val());
     $('#relayAddr2',object.document).val($('#relayAddr2').val());
     $('#relayAddr3',object.document).val($('#relayAddr3').val());
   }else if(relayType==4){
     $('#relayAddr1',object.document).val($('#relayAddr1').val());
     $('#relayAddr2',object.document).val($('#relayAddr2').val());
     $('#relayAddr3',object.document).val($('#relayAddr3').val());
     $('#relayAddr4',object.document).val($('#relayAddr4').val());
   }
   $('#relayType',object.document).val($('#relayType').val());
}

//地址初始化
function addrInit(){
  relayType=$('#relayType').val();
  if(relayType==0 || relayType==9){
     $('#relayAddr1').hide();
     $('#relayAddr2').hide();
     $('#relayAddr3').hide();
     $('#relayAddr4').hide();
     $('#relayAddr1Label').hide();
     $('#relayAddr2Label').hide();
     $('#relayAddr3Label').hide();
     $('#relayAddr4Label').hide();
  }else if(relayType==1){
     $('#relayAddr1').show();
     $('#relayAddr1Label').show();
     $('#relayAddr2').hide();
     $('#relayAddr2Label').hide();
     $('#relayAddr3').hide();
     $('#relayAddr3Label').hide();
     $('#relayAddr4').hide();
     $('#relayAddr4Label').hide();
  }else if(relayType==2){
     $('#relayAddr1').show();
     $('#relayAddr1Label').show();
     $('#relayAddr2').show();
     $('#relayAddr2Label').show();
     $('#relayAddr3').hide();
     $('#relayAddr3Label').hide();
     $('#relayAddr4').hide();
     $('#relayAddr4Label').hide();
  }else if(relayType==3){
     $('#relayAddr1').show();
     $('#relayAddr1Label').show();
     $('#relayAddr2').show();
     $('#relayAddr2Label').show();
     $('#relayAddr3').show();
     $('#relayAddr3Label').show();
     $('#relayAddr4').hide();
     $('#relayAddr4Label').hide();
  }else if(relayType==4){
     $('#relayAddr1').show();
     $('#relayAddr1Label').show();
     $('#relayAddr2').show();
     $('#relayAddr2Label').show();
     $('#relayAddr3').show();
     $('#relayAddr3Label').show();
     $('#relayAddr4').show();
     $('#relayAddr4Label').show();
  }
}
</script>
</head>
<body style="overflow: hidden;">
<div id="main" style="height: 100%;">
  <div class="tab"><em>中继类型</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 80));">
    <div class="main">
     <table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
       <tr>
        <td width="25%" class="label">中继类型：</td>
        <td width="25%" class="dom">
          <peis:selectlist name="relayType" sql="SL_ARCHIVE_0036"/>
        </td>
       </tr>
       <tr>
        <td width="25%" class="label" id="relayAddr1Label">1级中继地址：</td>
        <td width="25%" class="dom">
          <input type="text" id="relayAddr1" name="relayAddr1" value="00000000001" />
        </td>
       </tr>
       <tr>
        <td width="25%" class="label" id="relayAddr2Label">2级中继地址：</td>
        <td width="25%" class="dom">
          <input type="text" id="relayAddr2"  name="relayAddr2" value="00000000002" />
        </td>
       </tr>
       <tr>
        <td width="25%" class="label" id="relayAddr3Label">3级中继地址：</td>
        <td width="25%" class="dom">
          <input type="text" id="relayAddr3" name="relayAddr3" value="00000000003" />
        </td>
       </tr>
       <tr>
        <td width="25%" class="label" id="relayAddr4Label">4级中继地址：</td>
        <td width="25%" class="dom">
          <input type="text" id="relayAddr4" name="relayAddr4" value="00000000004" />
        </td>
       </tr>
      </table>
    </div>
  </div>
  <div class="guidePanel">
    <input class="input1" type="button" value="保 存" onclick="save();" />
  </div>
</div>
</body>
</html>