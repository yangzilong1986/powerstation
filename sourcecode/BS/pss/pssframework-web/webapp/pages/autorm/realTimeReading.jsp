<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">
var inputText = ""; 
var inputLabel = "";
var excelFlag=0; //导出EXCEL标记 1:各费率电能示值 2：当前功率 3:电压电流4：实时数据
var otherDataFlag=0; //其他数据项标识  1：实时召测其他数据项 0：实时召测各费率电能示值，当前功率，电压电流，实时数据
var timeData; //其他数据项页面中的数据时间
var dataGap; //其他数据项页面中的数据间隔
var points; //其他数据项页面中的点数
var protocolNo;//其他数据项页面中的规约号

function inputTextStyle() {
    $('#objectNo').attr({style: "color: #aaaaaa;"});
    $('#objectNo').blur( function() {
        if(this.value == ''){
            this.value = inputText;
            $('#objectNo').attr({style: "color: #aaaaaa;"});
        }
    });
    $('#objectNo').focus( function() {
        if(this.value == inputText){
            this.value='';
            $('#objectNo').attr({style: "color: black;"});
        }
    });
}

function initInputText() {
    if($("input[name='sysObject'][checked]").val() == "1") {
        inputText = inputText1;
        inputLabel = inputLabel1;
    }
    else if($("input[name='sysObject'][checked]").val() == "2") {
        inputText = inputText2;
        inputLabel = inputLabel2;
    }
    else if($("input[name='sysObject'][checked]").val() == "3") {
        inputText = inputText3;
        inputLabel = inputLabel3;
    }
    else if($("input[name='sysObject'][checked]").val() == "4") {
        inputText = inputText4;
        inputLabel = inputLabel4;
    }
    $("#objectLabel").html(inputLabel);
    $("#label").val(inputText);
    $("#objectNo").val(inputText);
}

/*$(document).ready( function() {
    $('[name="sysObject"]:radio').click(function(){
      var sysObject=$('[name="sysObject"][checked=true]:radio').val();
      $('#objectNo').unbind();
      $('#logicalAddr').unbind();
      if(sysObject==1){
         $("#objectLabel").html("用　户：");
         inputText=inputText1;
         autoCompleter("logicalAddr","50",120);
      }else if(sysObject==2){
         $("#objectLabel").html("配变/台区：");
         inputText=inputText2;
         autoCompleter("logicalAddr","51",120);
      }else if(sysObject==3){
         $("#objectLabel").html("低　压：");
         inputText=inputText3;
         autoCompleter("logicalAddr","52",120);
      }else if(sysObject==4){
         $("#objectLabel").html("变电站：");
         inputText=inputText4;
         autoCompleter("logicalAddr","53",120);
      }
      $('#objectNo').val(inputText);
      $("#label").val(inputText);
      autoCompleter("objectNo",sysObject,154);
   })
    initInputText();
    inputTextStyle(); 
    enableButton();
    autoCompleter("objectNo","1",154);
    autoCompleter("logicalAddr","50",120);
});
*/

function checkForm(oform) {
    if($('#objectNo').val() == inputText) {
        $('#objectNo').val('');
    }
    return true;
}

function enableButton() {
    if($("input[name='sysObject'][checked]").val() == "1") {
        $("#dqgflData").attr("disabled", false);
        $("#dqglData").attr("disabled", false);
        $("#dydlData").attr("disabled", false);
        //$("#sydlData").attr("disabled", false);
        $("#sssjData").attr("disabled", true);
    }
    if($("input[name='sysObject'][checked]").val() == "2") {
        $("#dqgflData").attr("disabled", false);
        $("#dqglData").attr("disabled", false);
        $("#dydlData").attr("disabled", false);
        //$("#sydlData").attr("disabled", true);
        $("#sssjData").attr("disabled", true);
    }
    if($("input[name='sysObject'][checked]").val() == "3") {
        $("#dqgflData").attr("disabled", false);
        $("#dqglData").attr("disabled", true);
        $("#dydlData").attr("disabled", true);
        //$("#sydlData").attr("disabled", true);
        $("#sssjData").attr("disabled", false);
    }
    if($("input[name='sysObject'][checked]").val() == "4") {
        $("#dqgflData").attr("disabled", false);
        $("#dqglData").attr("disabled", false);
        $("#dydlData").attr("disabled", false);
        //$("#sydlData").attr("disabled", true);
        $("#sssjData").attr("disabled", true);
    }
}

function disableButton() {
    $("#dqgflData").attr("disabled", true);
    $("#dqglData").attr("disabled", true);
    $("#dydlData").attr("disabled", true);
    //$("#sydlData").attr("disabled", true);
    $("#sssjData").attr("disabled", true);
}

//查询
function queryData(){
    if($("input[name='sysObject'][checked]").val() == "1") {
        $("input[name='sqlCode']").val("AL_AUTORM_1101");
    }
    else if($("input[name='sysObject'][checked]").val() == "2") {
        $("input[name='sqlCode']").val("AL_AUTORM_1102");
    }
    else if($("input[name='sysObject'][checked]").val() == "3") {
        $("input[name='sqlCode']").val("AL_AUTORM_1103");
    }
    else if($("input[name='sysObject'][checked]").val() == "4") {
        $("input[name='sqlCode']").val("AL_AUTORM_1104");
    }
    readingQueryForm.submit();
}

//高级查询
function comfirmAfterQuery(data){
    var objectId = data.OBJECT_ID;
    $("input[name='objectId']").val(objectId);
    //$("input[name='sqlCode']").val("AL_AUTORM_1004");
    if($("input[name='sysObject'][checked]").val() == "1") {
        $("input[name='sqlCode']").val("AL_AUTORM_1201");
    }
    else if($("input[name='sysObject'][checked]").val() == "2") {
        $("input[name='sqlCode']").val("AL_AUTORM_1202");
    }
    else if($("input[name='sysObject'][checked]").val() == "3") {
        $("input[name='sqlCode']").val("AL_AUTORM_1203");
    }
    else if($("input[name='sysObject'][checked]").val() == "4") {
        $("input[name='sqlCode']").val("AL_AUTORM_1204");
    }
    readingQueryForm.submit();
}

//打开高级查询页面
function openAdvancedQuery(){
  var sysObject = $("input[name='sysObject'][checked]").val();
  qry_adv_click({OBJECT_TYPE : sysObject,ARCHIVE_TYPE : '0',QUERY_TYPE : '0'});
}


//导出excel
function creatExcel(){
   var tableObj = document.getElementById('dataBody');
   if(tableObj.rows.length <= 0){
        alert("没有数据可导！");
        return;
   }
   var rowObjArr=paramBuild();
   // arrayIndex 参数组装  
   var arrayIndex = [];
   for(var i=0;i<$("#dataBody tr:eq(0)").children().length;i++){
      arrayIndex.push(i);
   }
   var xmlId = "";

   if(excelFlag == "1"){ //各费率电能示值

      xmlId = "realTimeReading_1";
   }else if(excelFlag == "2"){ //当前功率

   var sysObject = $("input[type=radio][checked]").val();
   
   if(sysObject == "3"){  //如果是低压集抄
      if(excelFlag == "1"){ //各费率电能示值
         xmlId = "realTimeReading_5";
      }
   }else if(sysObject == "4"){//变电站
      if(excelFlag == "1"){ //各费率电能示值
         xmlId = "realTimeReading_6";
      }
   }
   }
   else{
     if(excelFlag == "1"){ //各费率电能示值
        xmlId = "realTimeReading_1";
     }
   }
   if(excelFlag == "2"){ //当前功率
      xmlId = "realTimeReading_2";
   }else if(excelFlag == "3"){ //电流电压
      xmlId = "realTimeReading_3";
   }else if(excelFlag == "4"){ //实时数据
      xmlId = "realTimeReading_4";
   }else {
      xmlId = "realTimeReading_0"
   }
   // 通过下面的参数传到后台  
    var params = {"rowObjArr": rowObjArr,
                  "arrayIndex": arrayIndex,
                  "requestType":"ajax",
                  "xmlId":xmlId
                 };
    //***********导出EXCEL方法块*************
    var url=contextPath+"/autorm/getReadingQueryAction.do?action=exportExcel";
    $.ajax({
      type: "POST",
	  url: url,
	  data:params,
	  success: function(fileName){
	    var param = {
	          "fileName":encodeURI(fileName,"utf-8")
	    }
	    window.location.href =contextPath+"/autorm/getReadingQueryAction.do?action=downLoadFile&" + jQuery.param(param) + "&r=" + parseInt(Math.random() * 1000);
	  }
	});	 
}
//参数组装
function paramBuild(){
    var rowObjArr = [];
    var rowObj;
   $("#dataBody tr").each(function(i){
       var cbObj=$(this).find("input[type=checkbox]").attr("checked");
       if(cbObj == true){ //选中的checkbox
          rowObj=[];
          for(var j=2;j<$(this).children().length;j++){
             rowObj.push($(this).find("td:eq("+j+")").html());
          }
          rowObjArr.push(rowObj);
       }
   });
   return rowObjArr;
}



//打开其他数据项
function openOtherData(){
   var sSelectedList = getSelectedIdString(0);
   if(sSelectedList == "") {
       alert(chooseObject);
       return;
   }
   var sysObject=$("input[name='sysObject'][checked]").val();
   var url = contextPath + "/do/autorm/otherDataAction.do?action=init&sysObject="+sysObject;
   top.showDialogBox("其他数据项", url, 575, 980);
}

</script>
</head>
<body>
<form:form>
  <div class="electric_lcon" id="electric_Con">
  <ul class=default id=electric_Con_1>
    <div><form:form action="/autorm/realTimeReading" modelAttribute="realTimeReadingInfo">
      <table border="0" cellpadding="0" cellspacing="0">
        <tr height="30px">
          <td width="100" class="green" align="right">单位：</td>
          <td width="120"><form:select path="orgId" items="${orglist}" disabled="${disabled}" id="orgId"
            itemLabel="orgName" itemValue="orgId" cssStyle="width:150px;" /></td>
          <td width="100" class="green" align="right">台区：</td>
          <td width="150"><form:select path="objId" items="${tglist}" disabled="${disabled}" id="tgId"
            itemLabel="tgName" itemValue="tgId" cssStyle="width:150px;" /></td>
          <td width="100" class="green" align="right">集中器地址：</td>
          <td width="120" class="dom"><form:input path="termAddr" /></td>
        </tr>
        <tr height="30px">
          <td align="center" colspan='5'><input type="button" id="dqgflData" name="dqgflData" value="各费率电能示值"
            onclick="readDqgflData()" class="btnbg4" /> <input type="button" class="btnbg4" id="dqglData"
            name="dqglData" value="当前功率" onclick="readDqglData()" /> <input class="btnbg4" type="button" id="dydlData"
            name="dydlData" value="当前电流" onclick="readDydlData()" /> <input class="btnbg4" type="button" id="otherData"
            name="otherData" value="其他数据项" onclick="openOtherData()" /> <!-- <input type="button" onclick="openAdvancedQuery();" value="高级查询" /> --></td>
          <td><input class="btnbg4" type="button" name="query" id="query" value="查询" onclick="queryData()" /></td>
        </tr>
      </table>
    </form:form></div>
    <div id="bg" style="height: 30px; text-align: center;">
    <ul id=”datamenu_Option“ class="cb font1">
      <li class="curr" id=datamenu_Option_0 style="cursor: pointer;">实时召测</li>
    </ul>
    </div>
    <div class="tableContainer"
      style="height: expression(((     document.documentElement.clientHeight || document.body.clientHeight) -105 ) );">
    <table id="object_table" border="0" cellpadding="0" cellspacing="0" width="100%">
      <thead>
        <tr>
          <th width="3%"><input type="checkbox" name="selectAll" onclick="selectAllCB()" checked /></th>
          <th>对象编号</th>
          <th>对象名称</th>
          <th>对象类型</th>
          <th>终端地址</th>
          <th>电表局号</th>
          <th>测量点序号</th>
        </tr>
      </thead>
      <tbody id="dataBody">
        <!-- 增加排序功能 -->
        <c:forEach items="${PG_QUERY_RESULT}" var="item" varStatus="status">
          <tr>
            <td align="center"><input type="checkbox" name="ItemID" value="<c:out value="${item.GP_ID}"/>" checked />
            <c:if test="${item.PROTOCOL_NO == 126}">
              <input type="hidden" name="tdID"
                value="<c:out value="${item.LOGICAL_ADDR}"/>_<c:out value="${item.GP_ADDR}"/>" />
            </c:if> <c:if test="${item.PROTOCOL_NO != 126}">
              <input type="hidden" name="tdID"
                value="<c:out value="${item.LOGICAL_ADDR}"/>_<c:out value="${item.GP_SN}"/>" />
            </c:if></td>
            <td align="center"><c:out value="${status.count}" /></td>
            <td><c:out value="${item.OBJECT_NO}" /></td>
            <td><c:out value="${item.OBJECT_NAME}" /></td>
            <td align="center"><c:out value="${item.OBJECTTYPE}" /></td>
            <td align="center"><c:out value="${item.LOGICAL_ADDR}" /></td>
            <td align="center"><c:out value="${item.ASSET_NO}" /></td>
            <td align="center"><c:out value="${item.GP_SN}" /></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    </div>
  </ul>
  </div>
</form:form>
<iframe id="resultframe" src="${ctx}/jsp/autorm/fastReadingAction.jsp" width="0" height="0" frameborder="0"></iframe>
</body>
<script type="text/javascript">
var contextPath = "${ctx}";
var readingTitle = '<bean:message bundle="autorm" key="autorm.realTimeReading.title.reading"/>';
var overTimeTitle = '<bean:message bundle="autorm" key="autorm.realTimeReading.title.overtime"/>';
var chooseObject = '<bean:message bundle="autorm" key="autorm.realTimeReading.title.chooseObject"/>';

var totalNums = 0;

//当前各费率电能示值
function readDqgflData() {
    var sSelectedList = getSelectedIdString(0);
    if(sSelectedList == "") {
        alert(chooseObject);
        return;
    }
    excelFlag = 1;
    deleteDataitemTd();
    if($("input[name='sysObject'][checked]").val() == "1") {        // 专变用户
        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygz"/>(kWh)', 'DI_0100');    //正向有功总

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygj"/>(kWh)', 'DI_0101');    //正向有功尖

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygf"/>(kWh)', 'DI_0102');    //正向有功峰

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygp"/>(kWh)', 'DI_0103');    //正向有功平

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygg"/>(kWh)', 'DI_0104');    //正向有功谷

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxwgz"/>(kvarh)', 'DI_A000');  //正向无功总

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.fxygz"/>(kWh)', 'DI_0200');    //反向有功总

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.fxwgz"/>(kvarh)', 'DI_A100');  //反向无功总

    }
    else if($("input[name='sysObject'][checked]").val() == "2") {   // 配变/台区
        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygz"/>(kWh)', 'DI_0100');    //正向有功总

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygj"/>(kWh)', 'DI_0101');    //正向有功尖

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygf"/>(kWh)', 'DI_0102');    //正向有功峰

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygp"/>(kWh)', 'DI_0103');    //正向有功平

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygg"/>(kWh)', 'DI_0104');    //正向有功谷

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxwgz"/>(kvarh)', 'DI_A000');  //正向无功总

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.fxygz"/>(kWh)', 'DI_0200');    //反向有功总

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.fxwgz"/>(kvarh)', 'DI_A100');  //反向无功总

    }
    else if($("input[name='sysObject'][checked]").val() == "3") {   // 低压集抄
        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygz"/>(kWh)', 'DI_0100');    //正向有功总

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygj"/>(kWh)', 'DI_0101');    //正向有功尖

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygf"/>(kWh)', 'DI_0102');    //正向有功峰

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygp"/>(kWh)', 'DI_0103');    //正向有功平

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygg"/>(kWh)', 'DI_0104');    //正向有功谷

    }
    else if($("input[name='sysObject'][checked]").val() == "4") {   // 变电站

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygz"/>(kWh)', 'DI_0100');    //正向有功总

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxwgz"/>(kvarh)', 'DI_A000');  //正向无功总

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.fxygz"/>(kWh)', 'DI_0200');    //反向有功总

        insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.fxwgz"/>(kvarh)', 'DI_A100');  //反向无功总

    }
    disableButton();
    otherDataFlag = 0 ;
    var sCommanditems = "";
    sCommanditems += "100#1#100C0129,100C0130,100C0131,100C0132;";                                          // 100
    sCommanditems += "101#1#100C0129;";                                                                     // 101
    sCommanditems += "102#1#100C0129;";                                                                     // 102
    sCommanditems += "106#1#100C0033,100C0034;";                                                            // 106
    //sCommanditems += "122#1#2000901F,20009110,20009020,20009120;";                                          // 122 - 大项
    //sCommanditems += "122#1#20009010,20009011,20009012,20009013,20009014,20009110,20009020,20009120;";      // 122 - 小项
    //sCommanditems += "123#1#2000901F,20009110,20009020,20009120;";                                          // 123 - 大项
    sCommanditems += "123#1#20009010,20009011,20009012,20009013,20009014,20009110,20009020,20009120;";      // 123 - 小项
    //sCommanditems += "124#1#2000901F,20009110,20009020,20009120;";                                          // 124 - 大项
    sCommanditems += "124#1#20009010,20009011,20009012,20009013,20009014,20009110,20009020,20009120;";      // 124 - 小项
    sCommanditems += "125#1#20009010,20009011,20009012,20009013,20009014,20009110,20009020,20009120;";      // 125 - 小项
    sCommanditems += "126#1#20169010,20169011,20169012,20169013,20169014;";                                 // 126
    sCommanditems += "127#1#20009010,20009011,20009012,20009013,20009014,20009110,20009020,20009120;";      // 127 - 小项
    sCommanditems += "146#1#21010002;";                                                                     // 146
    resultframe.fastReadingActionForm.action.value = "send";
    resultframe.fastReadingActionForm.sys_object.value = $("input[name='sysObject'][checked]").val();
    resultframe.fastReadingActionForm.r_selectlist.value = sSelectedList;
    resultframe.fastReadingActionForm.r_commanditems.value = sCommanditems;
    resultframe.fastReadingActionForm.submit();
}

//当前功率
function readDqglData() {
    var sSelectedList = getSelectedIdString(0);
    if(sSelectedList == "") {
        alert(chooseObject);
        return;
    }
    excelFlag=2;
    deleteDataitemTd();
    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.dqzyggl"/>(kW)', 'DI_2300');  //当前总有功功率

    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.dqzwggl"/>(kW)', 'DI_2400');  //当前总无功功率

    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.dqzglys"/>', 'DI_2600');      //当前总功率因数

    disableButton();
    otherDataFlag = 0 ;
    var sCommanditems = "";
    sCommanditems += "100#1#100C0025;100#3#100C0017,100C0018;";                                             // 100
    sCommanditems += "101#1#100C0025;100#3#100C0017,100C0018;";                                             // 101
    sCommanditems += "106#1#100C0025;106#3#100C0017,100C0018;";                                             // 106
    //sCommanditems += "122#1#2000B630,2000B640,2000B650;";                                                   // 122
    sCommanditems += "123#1#2000B630,2000B640,2000B650;";                                                   // 123
    sCommanditems += "124#1#2000B630,2000B640,2000B650;";                                                   // 124
    sCommanditems += "125#1#2000B630,2000B640,2000B650;";                                                   // 125
    sCommanditems += "127#1#2000B630,2000B640,2000B650;";                                                   // 127
    sCommanditems += "146#1#21010018,21010019;";                                                            // 146
    resultframe.fastReadingActionForm.action.value = "send";
    resultframe.fastReadingActionForm.sys_object.value = $("input[name='sysObject'][checked]").val();
    resultframe.fastReadingActionForm.r_selectlist.value = sSelectedList;
    resultframe.fastReadingActionForm.r_commanditems.value = sCommanditems;
    resultframe.fastReadingActionForm.submit();
}

//电压电流
function readDydlData() {
    var sSelectedList = getSelectedIdString(0);
    if(sSelectedList == "") {
        alert(chooseObject);
        return;
    }
    excelFlag=3;
    deleteDataitemTd();
    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.Axdy"/>(V)', 'DI_2101');     //A相电压

    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.Bxdy"/>(V)', 'DI_2102');     //B相电压

    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.Cxdy"/>(V)', 'DI_2103');     //C相电压

    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.Axdl"/>(A)', 'DI_2201');     //A相电流

    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.Bxdl"/>(A)', 'DI_2202');     //B相电流

    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.Cxdl"/>(A)', 'DI_2203');     //C相电流

    disableButton();
    otherDataFlag = 0 ;
    var sCommanditems = "";
    sCommanditems += "100#1#100C0025;";                                                                     // 100
    sCommanditems += "101#1#100C0025;";                                                                     // 101
    sCommanditems += "106#1#100C0025;";                                                                     // 106
    //sCommanditems += "122#1#2000B61F,2000B62F;";                                                            // 122 - 大项
    //sCommanditems += "122#1#2000B611,2000B612,2000B613,2000B621,2000B622,2000B623;";                        // 122 - 大项
    //sCommanditems += "123#1#2000B61F,2000B62F;";                                                            // 123 - 大项
    sCommanditems += "123#1#2000B611,2000B612,2000B613,2000B621,2000B622,2000B623;";                        // 122 - 大项
    //sCommanditems += "124#1#2000B61F,2000B62F;";                                                            // 124 - 大项
    sCommanditems += "124#1#2000B611,2000B612,2000B613,2000B621,2000B622,2000B623;";                        // 122 - 大项
    sCommanditems += "125#1#2000B611,2000B612,2000B613,2000B621,2000B622,2000B623;";                        // 125 - 大项
    sCommanditems += "127#1#2000B611,2000B612,2000B613,2000B621,2000B622,2000B623;";                        // 127 - 大项
    sCommanditems += "146#1#21010020,21010021;";                                                            // 146
    resultframe.fastReadingActionForm.action.value = "send";
    resultframe.fastReadingActionForm.sys_object.value = $("input[name='sysObject'][checked]").val();
    resultframe.fastReadingActionForm.r_selectlist.value = sSelectedList;
    resultframe.fastReadingActionForm.r_commanditems.value = sCommanditems;
    resultframe.fastReadingActionForm.submit();
}

//剩余电量（费）

function readSydlData() {
    var sSelectedList = getSelectedIdString(0);
    if(sSelectedList == "") {
        alert(chooseObject);
        return;
    }
    deleteDataitemTd();
    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.sydlf"/>(kWh)', 'DI_BE28');    
    disableButton();
    otherDataFlag = 0 ;
    var sCommanditems = "";
    sCommanditems += "106#3#100C0023;";
    resultframe.fastReadingActionForm.action.value = "send";
    resultframe.fastReadingActionForm.sys_object.value = $("input[name='sysObject'][checked]").val();
    resultframe.fastReadingActionForm.r_selectlist.value = sSelectedList;
    resultframe.fastReadingActionForm.r_commanditems.value = sCommanditems;
    resultframe.fastReadingActionForm.submit();
}

//集中器实时数据[广东集抄规约]
function readSssjData() {
    var sSelectedList = getSelectedIdString(0);
    if(sSelectedList == "") {
        alert(chooseObject);
        return;
    }
    excelFlag=4;
    deleteDataitemTd();
    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygz"/>(kWh)', 'DI_0100');    //正向有功总

    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygj"/>(kWh)', 'DI_0101');    //正向有功尖

    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygf"/>(kWh)', 'DI_0102');    //正向有功峰

    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygp"/>(kWh)', 'DI_0103');    //正向有功平

    insertTd('<bean:message bundle="autorm" key="autorm.realTimeReading.title.zxygg"/>(kWh)', 'DI_0104');    //正向有功谷

    disableButton();
    otherDataFlag = 0 ;
    var sCommanditems = "";
    sCommanditems += "126#1#20119010,20119011,20119012,20119013,20119014;";
    resultframe.fastReadingActionForm.action.value = "sendSssj";
    resultframe.fastReadingActionForm.sys_object.value = $("input[name='sysObject'][checked]").val();
    resultframe.fastReadingActionForm.r_selectlist.value = sSelectedList;
    resultframe.fastReadingActionForm.r_commanditems.value = sCommanditems;
    resultframe.fastReadingActionForm.submit();
}

//其他数据项召测
function readOtherData(commd,dataItemArray,date,timeStep,point,proNo){
    var sSelectedList = getSelectedIdString(0);
    if(sSelectedList == "") {
       alert(chooseObject);
       return;
    }
    deleteDataitemTd();
    for (var key in dataItemArray)
    {
       insertTd(dataItemArray[key],key);
    }
    timeData = date; //窗口参数赋值给主页面
    dataGap = timeStep; //窗口参数赋值给主页面
    points = point; //窗口参数赋值给主页面
    otherDataFlag = 1;
    sCommanditems = commd;


    protocolNo = proNo;
    //alert(commd);

    resultframe.fastReadingActionForm.action.value = "send";
    resultframe.fastReadingActionForm.sys_object.value = $("input[name='sysObject'][checked]").val();
    resultframe.fastReadingActionForm.r_selectlist.value = sSelectedList;
    resultframe.fastReadingActionForm.r_commanditems.value = sCommanditems;
    resultframe.fastReadingActionForm.points.value = points;
    resultframe.fastReadingActionForm.submit();
}

//打开其他数据项
function openOtherData(){
   var sSelectedList = getSelectedIdString(0);
   if(sSelectedList == "") {
       alert(chooseObject);
       return;
   }
   var sysObject=$("input[name='sysObject'][checked]").val();
   var url = contextPath + "/do/autorm/otherDataAction.do?action=init&sysObject="+sysObject;
   top.showDialogBox("其他数据项", url, 575, 960);
}


function fetchReturnResult(appIds, sFetchCount, commanditems) {
    var iFetchCount = 0;
    try {
        iFetchCount = parseInt(sFetchCount);
    }
    catch(e) {
        iFetchCount = 0;
    }
    var params = {
            "r_appids": appIds,
            "fetchCount": iFetchCount,
            "otherDataFlag":otherDataFlag,
            "timeData":timeData,
            "dataGap":dataGap,
            "points":points,
            "proNo":protocolNo
    };
    var url = contextPath + "/autorm/fastReadingAction.do?action=fetchByAjax&" + jQuery.param(params) + "&random=" + Math.random();
    $.get(url, function(data) {
        //alert(" ******data****** : \n [ " + data + " ] ");
        viewTds(data, commanditems, (iFetchCount - 1));
        //alert(totalNums);
        if(totalNums <= 0) {
            iFetchCount = 0;
        }
        if(iFetchCount > 0) {
            setTimeout("fetchReturnResult('" + appIds + "', " + (iFetchCount - 1) +", '"+ commanditems+ "')", 3000);
        }
        else {
            enableButton();
        }
    });
    if(iFetchCount == 0) {
        enableButton();
        //alert("操作结束");
    }
}

function insertTd(title, code) {
    var rows = document.all.object_table.rows;
    var ids = document.all.ItemID;
    var tids = document.all.tdID;
    var th = document.createElement("th");
    th.innerHTML = title;
    rows[0].appendChild(th);
    for(var i = 1; i < rows.length; i++) {
        var row = rows[i];
        //一条数据

        if(typeof document.all.ItemID != "undefined" && typeof document.all.ItemID.length == "undefined") {
            if(document.all.ItemID.checked) {
                var td1 = row.insertCell();
                resendId=document.all.ItemID.value;
                td1.id = document.all.tdID.value +"_"+ code;
                td1.innerHTML = "正在发送...";
                totalNums++;
            }
            else {
                var td2 = row.insertCell();
                td2.id = "";
                td2.innerHTML = "";
           }
        }
        //多条数据
        else if(typeof document.all.ItemID != "undefined" && typeof document.all.ItemID.length != "undefined") {
            if(ids[i-1].checked) {
                var td1 = row.insertCell();
                resendId=ids[i-1].value;
                td1.id = tids[i-1].value +"_"+ code;
                td1.innerHTML = "正在发送...";
                totalNums++;
            }
            else {
                var td2 = row.insertCell();
                td2.id = "";
                td2.innerHTML = "";
            }
        }
    }
}

function viewTds(data, commanditems, fetchCount) {
    /*if(data != null){
        var result=data.split(";");
        var command=commanditems.split(",");
        var rows = document.all.object_table.rows;
        var ids = document.all.ItemID;
        var tids = document.all.tdID;
        var cells = rows.item(0).cells.length;
        for(var i = 1; i < rows.length; i++) {
            for(var j=8;j<cells;j++){
                for(var k = 0; k < result.length-1; k++){
                   var resu = result[k] ;
                   var re2 =resu.split(":");
                   for(var m = 0; m < command.length; m++){
                       var com= command[m];
                       var tem=rows[i].cells[j].id ;
                       var n= tem.lastIndexOf("#");
                       var bm = tem.substring(0,n+1)+com+tem.substring(n,tem.length);
                       if(re2[0] == bm) {
                           rows[i].cells[j].innerHTML=re2[1];
                           break;
                       }
                       else {
                           //rows[i].cells[j].innerHTML="等待超时..";
                       }
                   }
               }
               if(fetchCount == 0 && rows[i].cells[j].innerHTML=="正在发送..."){
                   rows[i].cells[j].innerHTML="等待超时.."; 
               }
            }
        }
    }*/
    if(data != null) {
        var jsonObj = eval("(" + data + ")");
        var key = null;
        var value = null;
        for(var i = 0; i < jsonObj.data.length; i++) {
            key = jsonObj.data[i].key;
            value = jsonObj.data[i].value;
            if(value == "-1") {
                value = "无效值";
            }
            try {
                if($("#" + key).html() != null && $("#" + key).html() == "正在发送...") {
                    if(otherDataFlag == "1"){ //如果是其他数据项，展示曲线数据
                       insertCurData(key,value);
                    }else{
                       $("#" + key).html(value);
                    }
                    totalNums--;
                }
            }
            catch(e) {
            }
        }
        if(fetchCount == 0 && totalNums > 0) {
            var rows = document.all.object_table.rows;
            var ids = document.all.ItemID;
            var tids = document.all.tdID;
            var cells = rows.item(0).cells.length;
            for(var j = 1; j < rows.length; j++) {
                for(var k = 8; k < cells; k++) {
                    if(rows[j].cells[k].innerHTML == "正在发送...") {
                        rows[j].cells[k].innerHTML = "等待超时..";
                    }
                }
            }
        }
    }
}

//插入曲线数据
function insertCurData(key,value){
    $("#" + key).html("");
    var valueArr = [] ;
    valueArr = value.split("|");
    for(var i=0;i<valueArr.length;i++){
       $("#" + key).append(valueArr[i]+"<br/>");
    }
}
function deleteDataitemTd() {
    var rows = document.all.object_table.rows;
    var cells = rows.item(0).cells.length;
    for(var j = cells - 1; j > 7; j--) {
        for(var i = 0; i < rows.length; i++) {
            rows[i].deleteCell(j);
        }
    }
}

function deleteRow(){
    var table = document.all.object_table;
    table.deleteRow(-1);
    resultframe.fastReadingActionForm.isAll.value = "";
}

function notGetResult(){
    var isAll=resultframe.fastReadingActionForm.isAll.value;
    var rows =document.all.object_table.rows;
    var ids=document.all.ItemID;
    var cells = rows.item(0).cells.length;
    var result="";
    var resendId; 
    var total=new Array(0,0);   
    for(var i=1;i<rows.length;i++) {
        for(var j=8;j<cells;j++) {
            if(isAll != '1') {
                if(rows[i].cells[j].innerText == readingTitle) {
                    rows[i].cells[j].innerHTML=overTimeTitle;
                    if(resendId == null) {
                        resendId=ids[i-1].value;
                        result+=","+resendId;
                    }
                }
            }
            else {
                if(rows[i].cells[j].innerText==readingTitle){
                    rows[i].cells[j].innerHTML = overTimeTitle;
                    if(resendId == null) {
                        resendId = ids[i-1].value;
                        result += "," + resendId;
                    }
                }
                else {
                    //alert(rows[i].cells[j].innerText);
                    total[j-8]+=parseFloat(rows[i].cells[j].innerText);
                }
            }
        }
        resendId = null;
    }
    
    if(isAll=='1') {
        insertRow(total);
    }
    if(result.indexOf(",") != -1) {
        result = result.substring(1);
    }
    //alert(result);
    resultframe.fastReadingActionForm.r_resend.value = result;
    enableButton();
}
</script>
</html>
