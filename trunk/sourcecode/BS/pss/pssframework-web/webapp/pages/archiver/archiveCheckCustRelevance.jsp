<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>

<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>客户详细信息</title>
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
  <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
  <script type="text/javascript">
var contextPath = '<peis:contextPath/>';
//更新审核状态
function updateCheckStatus(checkStatus,custId,batchNo,insertTime){
   if(confirm("是否执行该操作")==true){
     var params={
        'CHECK_STATUS':checkStatus,
        'CUST_ID':custId,
        'BATCH_NO':batchNo,
        'INSERT_TIME':insertTime
      };
     $.ajax({
      type:"POST",
      url:"114007.dow",
      dataType:"json",
      data:params,
      success:function(data){
               if(data.MSG_TYP=='N'){ //更新成功
              	  	alert(data.RSP_MSG);
              	  	parent.GB_hide();
              	  	parent.archiveCheckForm.submit();
               }else{alert(data.RSP_MSG);}
            },
      error :function(XMLHttpRequest,textStatus,errorThrown){
              alert("更新失败");
           }
      })
   }
}
</script>
 </head>
 <body>
  <div id="body">
     <div class="data2">
      <span>操作状态</span>
     </div>
     <div class="data2_con">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="13%" class="label" align="center">
                                  操作类型：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.CZ}
          </td>
          <td width="13%" class="label" align="center">
                                  归档时间：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.INSERT_TIME}
          </td>
        </tr>
      </table>
     </div>
     <div class="data2">
      <span>客户信息</span>
     </div>
     <div class="data2_con">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="13%" class="label" align="center">
                                  客户名称：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.CONSUMERNAME}
          </td>
          <td width="13%" class="label" align="center">
                                  客户户号：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.CONSUMERID}
          </td>
          <td width="13%" class="label" align="center">
                                 客户地址：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.USERADDR}
          </td>
        </tr>
        <tr>
          <td width="13%" class="label" align="center">
                                  所属供电所：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.ORG_NAME}
          </td>
          <td width="13%" class="label" align="center">
                                  所属线路：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.LINE_NAME}
          </td>
          <td width="13%" class="label" align="center">
                                 联系人：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.LINKMAN}
          </td>
        </tr>
        <tr>
          <td width="13%" class="label" align="center">
                                  联系电话：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.LINKPHONE}
          </td>
          <td colspan="4"></td>
        </tr>
      </table>
     </div>
     <div class="data2">
      <span>终端信息</span>
     </div>
     <div class="data2_con">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="13%" class="label" align="center">
                                  终端编号：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.DDPASSETNO}
          </td>
          <td width="13%" class="label" align="center">
                                  终端逻辑地址：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.TERMADDRESS}
          </td>
          <td width="13%" class="label" align="center">
             <font color="red">* </font>所属用户：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.CONSUMERNAME}
          </td>
        </tr>
        <tr>
          <td width="13%" class="label" align="center">
                                  终端生产厂家：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.MADEFAC}
          </td>
          <td width="13%" class="label" align="center">
                                  终端电话号码：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.DDPPHONENO}
          </td>
          <td width="13%" class="label" align="center">
             <font color="red">* </font>终端型号：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.TERMTYPE}
          </td>
        </tr>
      </table>
     </div>
     <div class="data2">
      <span>电表信息</span>
     </div>
     <div class="data2_con">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="13%" class="label" align="center">
                                  电表局号：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.AMMASSETNO}
          </td>
          <td width="13%" class="label" align="center">
                                  生产厂家：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.FACTORYNAME}
          </td>
          <td width="13%" class="label" align="center">
                                 电表地址：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.METERADDR}
          </td>
        </tr>
        <tr>
          <td width="13%" class="label" align="center">
             CT变比：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.CT}
          </td>
          <td width="13%" class="label" align="center">
             PT变比：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.PT}
          </td>
          <td width="13%" class="label" align="center">
             <font color="red">* </font>所属用户：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.CONSUMERNAME}
          </td>
        </tr>
        <tr>
          <td width="13%" class="label" align="center">
             <font color="red">* </font>所属终端：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.DDPASSETNO}
          </td>
          <td colspan="4"></td>
        </tr>
      </table>
     </div>
     <div class="data2">
      <span>当前状态</span>
     </div>
     <div class="data2_con">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="13%" class="label" align="center">
                                  审核状态：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.SHZT}
          </td>
          <td width="13%" class="label" align="center">
                                  处理状态：
          </td>
          <td width="20%" class="dom" align="center">
            ${ETF.DL}
          </td>
        </tr>
      </table>
     </div>
    <div class="guidePanel">
     <input type="button" id="last" value="审核通过" class="input2" onclick="updateCheckStatus(20,'${ETF.CUST_ID}','${ETF.BATCH_NO}','${ETF.INSERT_TIME}');" />
     &nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" id="finish" value="审核不通过" class="input3" onclick="updateCheckStatus(30,'${ETF.CUST_ID}','${ETF.BATCH_NO}','${ETF.INSERT_TIME}');" />
     &nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" id="finish" value="取消" class="input1" onclick="javascript:parent.GB_hide();" />
    </div>
   </div>
 </body>
</html>
