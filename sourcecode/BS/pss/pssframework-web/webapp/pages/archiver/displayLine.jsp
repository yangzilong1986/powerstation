<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../../include/page.htm" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>线路新增</title>
<link rel="stylesheet" type="text/css" href="../../css/mainframe.css" />
<link rel="stylesheet" type="text/css" href="../../css/archiveCss.css" />
<script type="text/javascript" src="../../js/jquery.js"></script>
<script type="text/javascript" src="../../js/frame/tableEx.js"></script>
<script type="text/javascript" src="../../js/frame/component.js"></script>
<script type="text/javascript" src="../../js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="../../js/frame/const.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  //供电单位
  $.handelCombox('orgTar','orgNo');
  //电压等级
  $.handelCombox('voltGradeTar','voltGrade');
  //变电站
  $.handelCombox('subsTar1','subsNo','id="subsNo1"');
  $.handelCombox('subsTar2','subsNo','id="subsNo2"');
  $.handelCombox('subsTar3','subsNo','id="subsNo3"');
});
//保存线路信息
function deleteEl(){
   if(confirm("是否删除该线路")==true){
      alert("删除成功");
   }
}
</script>
</head>
<body>
  <div id="body">
   <div id="main">
    <div id="detail">
      <div class="top">
        <div class="main">线路信息</div>
        <div class="left"></div>
        <div class="right"></div>
      </div>
    <div class="center" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 35));">
     <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr>
       <td width="15%" class="label"><font color="red">* </font>线路编号：</td>
       <td width="25%" class="dom">
         <input type="text" id="lineNo" name="lineNo" disabled="true">
       </td>
       <td width="15%" class="label"><font color="red">* </font>线路名称：</td>
       <td width="25%" class="dom"><input type="text" id="lineName" name="lineName" disabled="true"></td>
      </tr>
      <tr>
       <td width="15%" class="label">管理单位：</td>
       <td width="25%" class="dom">
         <select id="orgNo" name="orgNo"  disabled="true">
            <option value="44">广东省电力公司</option>
            <option value="4401"> -- 广州市电力公司</option>
            <option value="4402"> -- 韶关市电力公司</option>
            <option value="4403"> -- 深圳市电力公司</option>
            <option value="4404"> -- 珠海市电力公司</option>
            <option value="4405"> -- 汕头市电力公司</option>
            <option value="4406"> -- 佛山市电力公司</option>
            <option value="4407"> -- 江门市电力公司</option>
            <option value="4408"> -- 湛江市电力公司</option>
            <option value="440806"> -- -- 遂溪供电局</option>
            <option value="440807"> -- -- 徐闻供电局</option>
            <option value="440808"> -- -- 吴川供电局</option>
            <option value="440809"> -- -- 廉江供电局</option>
            <option value="440810"> -- -- 雷州供电局</option>
            <option value="4409"> -- 茂名市电力公司</option>
            <option value="4412"> -- 肇庆市电力公司</option>
            <option value="4413"> -- 惠州市电力公司</option>
            <option value="4414"> -- 梅州市电力公司</option>
            <option value="4415"> -- 汕尾市电力公司</option>
            <option value="4416"> -- 河源市电力公司</option>
            <option value="4417"> -- 阳江市电力公司</option>
            <option value="4418"> -- 清远市电力公司</option>
            <option value="4419"> -- 东莞市电力公司</option>
            <option value="4420"> -- 中山市电力公司</option>
            <option value="4421"> -- 潮州市电力公司</option>
            <option value="4422"> -- 揭阳市电力公司</option>
            <option value="4423"> -- 云浮市电力公司</option>
          </select>
          <script type="text/javascript">$("#orgNo").val("2");</script>
       </td>
       <td width="15%" class="label">电压等级：</td>
       <td width="25%" class="dom">
         <select id="VOLT_GRADE" name="VOLT_GRADE" disabled="true">
            <option value="1">110KV</option>
            <option value="2">220KV</option>
            <option value="3">500KV</option>
            <option value="4">10KV</option>
            <option value="5">35KV</option>
            <option value="6">380V</option>
            <option value="7">220V</option>
          </select>
          <script type="text/javascript">$("#VOLT_GRADE").val("1");</script>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label">线路类型：</td>
       <td width="25%" class="dom">
          <select name="lineType" id="lineType" disabled="true">
            <option value="0">主线</option>
            <option value="1">支线</option>
          </select>
       </td>
       <td width="15%" class="label">上线线路：</td>
       <td width="25%" class="dom">
          <select name="lastLine" id="lastLine" disabled="true">
            <option value=""></option>
          </select>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label"><font color="red">* </font>线损阀值：</td>
       <td width="25%" class="dom">
         <input type="text" id="lineThreshold" name="lineThreshold" style="width:130"/ disabled="true">%
       </td>
       <td width="15%" class="label">所属变电站1：</td>
       <td width="25%" class="dom">
          <select name="orgNo1" id="orgNo1" disabled="true">
            <option value="1">220kV霞山站</option>
            <option value="2">110kV宝满站</option>
            <option value="3">220kV椹北站</option>
            <option value="4">110kV良丰站</option>
            <option value="5">110kV官渡站</option>
            <option value="6">110kV滨海站</option>
          </select>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label">所属变电站2：</td>
       <td width="25%" class="dom">
         <select name="orgNo2" id="orgNo2" disabled="true">
            <option value="1">220kV霞山站</option>
            <option value="2">110kV宝满站</option>
            <option value="3">220kV椹北站</option>
            <option value="4">110kV良丰站</option>
            <option value="5">110kV官渡站</option>
            <option value="6">110kV滨海站</option>
          </select>
       </td>
       <td width="15%" class="label">所属变电站3：</td>
       <td width="25%" class="dom">
         <select name="orgNo3" id="orgNo3" disabled="true">
            <option value="1">220kV霞山站</option>
            <option value="2">110kV宝满站</option>
            <option value="3">220kV椹北站</option>
            <option value="4">110kV良丰站</option>
            <option value="5">110kV官渡站</option>
            <option value="6">110kV滨海站</option>
          </select>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label">线路状态：</td>
       <td width="25%" class="dom">
          <select name="lineStatus" id="lineStatus" disabled="true">
            <option value="01">投入</option>
            <option value="02">不投入</option>
            <option value="03">故障</option>
            <option value="04">检修</option>
          </select>
       </td>
       <td colspan="2"></td>
      </tr>
      <tr>
         <td rowspan="3" class="label">备　注：</td>
          <td class="dom" colspan="3">
            <textarea class="input_textarea3" name="remark" style="width:450;height:80" disabled="true"></textarea>
          </td>
      </tr>
     </table>
     <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td align="right">
            <input class="input" type="button" id="deleteTg" value="删除线路" onClick="deleteEl();" />
          </td>
        </tr>
     </table>
     </div>
    </div>
   </div>
  </div>
 </body>
</html>