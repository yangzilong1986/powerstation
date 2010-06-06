<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../common/taglib.jsp"%>
<div id="form">
 <div class="tab_con" style="height: expression(((document.documentElement.clientHeight ||document.body.clientHeight) - 0 ) );">
  <input type="hidden" name="tgId" value="${tgId}" />
  <input type="hidden" name="orgNo" value="${orgNo}" />
  <input type="hidden" name="termId" value="${object_term.termId}" />
  <div class="main">
   <table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
    <tr>
     <td width="13%" class="label">
      <font color="red">* </font>资产编号：
     </td>
     <td width="20%" class="dom">
      <input type="text" name="assetNo" id="assetNo" value="${object_term.assetNo}" />
     </td>
     <td width="13%" class="label">
      <font color="red">* </font>逻辑地址：
     </td>
     <td width="20%" class="dom">
      <input type="text" name="logicalAddr" id="logicalAddr" value="${object_term.logicalAddr}" />
     </td>
     <td width="13%" class="label">
      所属台区：
     </td>
     <td width="20%" class="dom">
      <peis:selectlist name="tgId" sql="SL_ARCHIVE_0037" onChange="loadTgAssociatedInfo();" />
      <script type="text/javascript">
          $("#tgId").val(${tgId});
      </script>
      <input type="button" value="..." onclick="qry_adv_click({OBJECT_TYPE : '2',ARCHIVE_TYPE : '1',QUERY_TYPE : '1',IFRAME_NAME:'content',SINGLE_RESULT : '1'})" style="width: 22px; cursor: pointer;" />
     </td>
    </tr>
    <tr>
     <td class="label">
      当前状态：
     </td>
     <td class="dom">
      <peis:selectlist name="curStatus" sql="SL_ARCHIVE_0012" />
     </td>
     <td class="label">
      设备规约：
     </td>
     <td class="dom">
      <peis:selectlist name="protocolNo" sql="SL_ARCHIVE_0072" />
     </td>
     <td class="label">
      通讯方式：
     </td>
     <td class="dom">
      <peis:selectlist name="commMode" sql="SL_ARCHIVE_0011" />
     </td>
    </tr>
    <tr>
     <td class="label">
      相 线：
     </td>
     <td class="dom">
      <peis:selectlist name="wiringMode" sql="SL_ARCHIVE_0013" />
     </td>
     <td class="label">
      设备厂家：
     </td>
     <td class="dom">
      <peis:selectlist name="madeFac" sql="SL_ARCHIVE_0008" />
     </td>
     <td class="label">
      终端类型：
     </td>
     <td class="dom">
      <peis:selectlist name="termType" sql="SL_ARCHIVE_0014" />
     </td>
    </tr>
    <tr>
    <td class="label">
      机器编号：
     </td>
     <td class="dom">
      <peis:selectlist name="machNo" sql="SL_ARCHIVE_0063" extendProperty="disabled='disabled'" />
     </td>
     <td class="label">
      前置机通道：
     </td>
     <td class="dom">
      <input type="text" name="fepCnl" id="fepCnl" value="${object_term.fepCnl}" disabled="disabled"/>
     </td>
     <td class="label">
      通道类型：
     </td>
     <td class="dom">
      <peis:selectlist name="channelType" sql="SL_ARCHIVE_0064" />
     </td>
    </tr>
    <tr>
     <td class="label">
      产 权：
     </td>
     <td class="dom">
      <peis:selectlist name="pr" sql="SL_ARCHIVE_0016" />
     </td>
     <td class="label">
      安装日期：
     </td>
     <td class="dom_date">
      <input type="text" id="installDate" name="installDate" value="<fmt:formatDate value="${object_term.installDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
     </td>
     <td class="label">
      入库日期：
     </td>
     <td class="dom_date">
      <input type="text" name="leaveFacDate" id="leaveFacDate" value="<fmt:formatDate value="${object_term.leaveFacDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
     </td>
    </tr>
    <tr>
     <td class="label">
      安装单位：
     </td>
     <td class="dom">
      <input type="text" id="constrGang" name="constrGang" value="${object_term.constrGang}" />
     </td>
     <td class="label">
      终端型号：
     </td>
     <td class="dom">
      <peis:selectlist name="modelCode" sql="SL_ARCHIVE_0017" />
     </td>
     <td  class="label">SIM卡号：</td>
     <td  class="dom">
          <input type="text" id="simNo" name="simNo" value="${object_term.simNo}" />
     </td>
    </tr>
    <tr>
     <td colspan="2" align="center">
      <input type="checkbox" name="isac" id="isac" value="${object_term.isac!=" " ? "1" : "0"}" ${object_term.isac==1?"checked" : ""}>
      接交采
     </td>
     <td class="label">
      物理地址：
     </td>
     <td colspan="3" class="dom">
      <input type="text" id="physicsAddr" name="physicsAddr" style="width: 390px" value="${object_term.physicsAddr}" />
     </td>
    </tr>
   </table>
  </div>
  <div id="ACShow">
   <div class="data3">
    <span>交采信息</span>
   </div>
   <div class="data3_con">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
     <tr>
      <td width="13%" class="label">
       计量点序号：
      </td>
      <td width="10%" class="dom">
       <input type="text" id="gpSn" name="gpSn" value="${object_gp.gpSn}" />
      </td>
      <td width="13%" class="label">
       供电线路：
      </td>
      <td width="10%" class="dom">
       <peis:selectlist name="lineId" sql="SL_ARCHIVE_0018" associate="true" />
      </td>
      <td width="44%" colspan="2" align="center">
       <input type="checkbox" id="sucratCptId" name="sucratCptId" value="${object_gp.sucratCptId!=" " ? "1" : "0"}" ${object_gp.sucratCptId==1?"checked" : ""}>
       功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <input type="checkbox" id="computeFlag" name="computeFlag" value="${object_gp.computeFlag!=" " ? "1" : "0"}" ${object_gp.computeFlag==1 ? "checked" : ""}/>
       电量累计 变压器：
       <select name="tranId" id="tranId">
        <option value="">
         未接变压器
        </option>
       </select>
      </td>
     </tr>
     <tr>
      <td class="label">
       CT变比：
      </td>
      <td class="dom">
       <peis:selectlist name="ctRatio" sql="SL_ARCHIVE_0019" />
      </td>
      <td class="label">
       PT变比：
      </td>
      <td class="dom">
       <peis:selectlist name="ptRatio" sql="SL_ARCHIVE_0020" />
      </td>
      <td class="label">
       端口号：
      </td>
      <td class="dom">
       <input type="text" id="port" name="port" value="${object_gp.port}" />
      </td>
     </tr>
    </table>
   </div>
  </div>
  <div class="data3">
   <span>级联信息</span>
  </div>
  <div class="data3_con">
   <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
     <td width="20%" class="label">
      通信模式：
     </td>
     <td width="30%" class="dom">
      <select name="commPattern" id="commPattern" onChange="showDevice();">
       <option value="1">
        主
       </option>
       <option value="2">
        从
       </option>
      </select>
     </td>
     <td width="20%" class="label" id="deviceLable">
      下级设备：
     </td>
     <td width="30%" class="dom" id="deviceDom">
      <select name="termIdCasc" id="termIdCasc"></select>
      <logic:equal value="" name="termId">
      </logic:equal>
      <!-- 终端存在，但该终端所属台区不为空 -->
      <logic:notEqual value="" name="termId">
       <logic:notEqual name="tgId" value="">
       </logic:notEqual>
      </logic:notEqual>
      <!-- 终端存在，但该终端所属台区为空 -->
      <logic:notEqual value="" name="termId">
       <logic:equal name="tgId" value="">
        <select name="termIdCasc" id="termIdCasc">
         <option value=""></option>
        </select>
       </logic:equal>
      </logic:notEqual>
      <input type="hidden" name="termIdCascTemp" id="termIdCascTemp" value="${object_termCascade.termIdCasc}" />
     </td>
    </tr>
   </table>
  </div>
  <!-- <div id="assInfoShow">
     <div class="data3">
      <span>关联信息</span>
     </div>
     <div class="data3_con">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
       <tr id="results">
       </tr>
      </table>
     </div>
    </div>-->
  <div id="paramShow">
   <div class="data2">
    <span>终端参数</span>
   </div>
   <div class="data2_con">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
     <thead>
      <tr>
       <th>
        序号
       </th>
       <th>
        终端参数
       </th>
       <th>
        参数值
       </th>
      </tr>
     </thead>
     <tbody id="paramTblTbody">
      <logic:present name="termParas">
       <logic:iterate id="para" name="termParas">
        <tr>
         <td>
          <bean:write name="para" property="rowNo" />
         </td>
         <td>
          <bean:write name="para" property="col1" />
         </td>
         <td>
          <input type="text" id="paraValue" name="paraValue" value="<bean:write name='para' property='col3'/>" />
          <input type="hidden" id="paraCode" name="paraCode" value="<bean:write name='para' property='col2'/>" />
          <input type="hidden" id="commanditemCode" name="commanditemCode" value="<bean:write name='para' property='col4'/>" />
          <input type="hidden" id="dataitemCode" name="dataitemCode" value="<bean:write name='para' property='col5'/>" />
         </td>
        </tr>
       </logic:iterate>
      </logic:present>
     </tbody>
    </table>
   </div>
  </div>
  <div class="guidePanel">
   <input type="button" value="保 存" class="input1" id="save" onClick="submitByAjax();" />
  </div>
 </div>
</div>