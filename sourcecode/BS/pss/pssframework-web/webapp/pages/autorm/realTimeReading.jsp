<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript" src="${ctx}/scripts/json2.js"></script>
<script type="text/javascript">
var inputText = ""; 
var inputLabel = "";
var excelFlag=0;        //导出EXCEL标记 1:各费率电能示值 2：当前功率 3:电压电流4：实时数据
var otherDataFlag=0;    //其他数据项标识  1：实时召测其他数据项 0：实时召测各费率电能示值，当前功率，电压电流，实时数据
var timeData;           //其他数据项页面中的数据时间
var dataGap;            //其他数据项页面中的数据间隔
var points;             //其他数据项页面中的点数
var protocolNo;         //其他数据项页面中的规约号

function enableButton() {
    $("#dqgflData").attr("disabled", false);
    $("#dqglData").attr("disabled", false);
    $("#dydlData").attr("disabled", false);
}

function disableButton() {
    $("#dqgflData").attr("disabled", true);
    $("#dqglData").attr("disabled", true);
    $("#dydlData").attr("disabled", true);
}

//查询
function queryData(obj) {
    getReferenceForm(obj).action='${ctx}/autorm/realTimeReading';
}

function StringBuffer() {
    this.data = [];
}

StringBuffer.prototype.append = function() {
    this.data.push(arguments[0]);
    return this;
}

StringBuffer.prototype.toString = function() {
    return this.data.join("");
}

function read(commanditems) {
    var sb_dto = new StringBuffer();

    var e = [];
    e = getCheckedAsString("ItemID");
    var length= e.length;

    sb_dto.append('{');
    sb_dto.append('"collectObjects":').append('[');
    for(var loop = 0; loop < length; loop++) {
        if(loop != 0) {
            sb_dto.append(',');
        }
        var logicalAddr;
        var gpsn;
        var data = e[loop].split("_");
        logicalAddr = data[0];
        gpsn = data[1];
        sb_dto.append('{');
        sb_dto.append('"logicalAddr":"' + logicalAddr + '"').append(',');
        sb_dto.append('"equipProtocol":"' + $("#equipProtocol").val() + '"').append(',');
        sb_dto.append('"channelType":"' + $("#channelType").val() + '"').append(',');
        sb_dto.append('"pwAlgorith":"' + $("#pwAlgorith").val() + '"').append(',');
        sb_dto.append('"pwContent":"' + $("#pwContent").val() + '"').append(',');
        sb_dto.append('"mpExpressMode":"' + $("#mpExpressMode").val() + '"').append(',');
        sb_dto.append('"mpSn":["' + gpsn + '"]').append(',');
        sb_dto.append('"commandItems":').append('[').append('{');
        var cilist = commanditems;
        var ciarray = cilist.split(',');
        for(var i = 0; i < ciarray.length; i++) {
            if(i > 0) {
                sb_dto.append('{');
            }
            sb_dto.append('"identifier":"' + ciarray[i] + '"');
            if(i < ciarray.length - 1) {
                sb_dto.append('}').append(',');
            }
        }
        
        sb_dto.append('}').append(']');
        sb_dto.append('}');
    }
    
    sb_dto.append(']}');
    
    var url = '<pss:path type="webapp"/>/autorm/realTimeReading/down.json';
    $.ajax({
        type: 'POST',
        url: url,
        data: "dto=" + sb_dto.toString(),
        dataType: 'json',
        success: function(data) {
            fetchReturnResult(data.collectId, 10, 100);
        },
        error: function() {
        }
    });
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
            "collectId": appIds,
            "fetchCount": iFetchCount,
            "otherDataFlag":otherDataFlag,
            "timeData":timeData,
            "dataGap":dataGap,
            "points":points,
            "proNo":protocolNo,
            "random":Math.random()
    };
    
    var url = '<pss:path type="webapp"/>/autorm/realTimeReading/up.json';
    $.ajax({
        type: "POST",
        url: url,
        data: params,
        success: function(data) {
            viewTds(data, commanditems, (iFetchCount - 1));
            if(totalNums <= 0) {
                iFetchCount = 0;
            }
            if(iFetchCount > 0) {
                setTimeout("fetchReturnResult('" + appIds + "', " + (iFetchCount - 1) +", '"+ commanditems+ "')", 3000);
            }
            else {
                enableButton();
            }
        },
        error:function(){
               //alert("error")
          }
    });

    if(iFetchCount == 0) {
        enableButton();
        //alert("操作结束");
    }
}

$(document).ready(function() {
    // 分页需要依赖的初始化动作
    window.simpleTable = new SimpleTable('model','${page.thisPageNumber}','${page.pageSize}','${pageRequest.sortColumns}');

    $("#inquiryBtn").click( function() {
        $("#userInfo").submit();
    });
});
</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
  <ul class=default id=electric_Con_1>
    <div id="inquiry" style="margin-top: 5px;"><form:form name="/autorm/realTimeReading" modelAttribute="userInfo">
      <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="100" height="30" class="green" align="right">单 位：</td>
          <td width="120">
            <select name="orgId" style="width: 140px;">
              <c:forEach var="item" items="${orglist}">
                <option <c:if test="${item.orgId eq pageRequest.orgId}">selected</c:if> value="<c:out value="${item.orgId}"/>"><c:out value="${item.orgName}" /></option>
              </c:forEach>
            </select>
          </td>
          <td width="100" class="green" align="right">台 区：</td>
          <td width="120">
            <select name="objId" style="width: 140px;">
              <c:set value="${model.objId}" var="tg"></c:set>
              <c:forEach var="item" items="${tglist}">
                <option <c:if test="${item.tgId eq pageRequest.objId}">selected</c:if> value="<c:out value="${item.tgId}"/>"><c:out value="${item.tgName}" /></option>
              </c:forEach>
            </select>
          </td>
          <td width="100" class="green" align="right">逻辑地址：</td>
          <td width="120" class="dom"><input value="${pageRequest.logicalAddr}" name="logicalAddr" id="logicalAddr1" style="width: 140px; height: 20px;"/></td>
          <td width="100" align="right">
            <img id="inquiryBtn" src="<pss:path type="bgcolor"/>/img/inquiry.gif" align="middle" width="62" height="21" onclick="return false;" style="cursor: pointer;" />
          </td>
        </tr>
      </table></form:form>
    </div>
    <div id="bg" style="height: 30px; text-align: center;">
      <ul id="datamenu_Option" class="cb font1">
        <li class="curr" id=datamenu_Option_0 style="cursor: pointer;">实时召测</li>
      </ul>
      <input type="hidden" id="protocolNo" name="protocolNo" value="GW_376" />
      <input type="hidden" id="equipProtocol" name="equipProtocol" value="100" />
      <input type="hidden" id="logicalAddr" name="logicalAddr" value="96123456" />
      <input type="hidden" id="channelType" name="channelType" value="1" />
      <input type="hidden" id="pwAlgorith" name="pwAlgorith" value="0" />
      <input type="hidden" id="pwContent" name="pwContent" value="8888" />
      <input type="hidden" id="mpExpressMode" name="mpExpressMode" value="3" />
      <input type="hidden" id="mpSn" name="mpSn" value="0" />
    </div>
    <div class="tableContainer" style="height: expression(((document.documentElement.clientHeight ||document.body.clientHeight)-135));">
      <table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
        <thead class="tableHeader">
          <tr>
            <th width="5" align="center"><input type="checkbox" onclick="setAllCheckboxState('ItemID',this.checked)"></th>
            <th width="25" align="center">序号</th>
            <!-- 排序时为th增加sortColumn即可,new SimpleTable('sortColumns')会为tableHeader自动增加排序功能; -->
            <th sortColumn="objNo" >对象编号</th>
            <th sortColumn="objName" >对象名称</th>
            <th sortColumn="objType" >对象类型</th>
            <th sortColumn="logicalAddr" >终端地址</th>
            <th sortColumn="mpNo" >电表局号</th>
            <th sortColumn="gpSn" >测量点序号</th>
          </tr>
        </thead>
        <tbody class="tableBody" id="dataBody">
          <c:forEach items="${page.result}" var="item" varStatus="status">
          <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
            <td align="center"><input type="checkbox" name="ItemID" value="<c:out value="${item.logicalAddr}"/>_<c:out value="${item.gpSn}"/>"></td>
            <td align="center">${page.thisPageFirstElementNumber + status.index}<input type="hidden" name="tdID" value="<c:out value="${item.logicalAddr}"/>_<c:out value="${item.gpSn}"/>"/></td>
            <td><c:out value='${item.objNo}' />&nbsp;</td>
            <td><c:out value='${item.objName}' />&nbsp;</td>
            <td><c:out value='${item.objType}' />&nbsp;</td>
            <td><c:out value='${item.logicalAddr}' />&nbsp;</td>
            <td><c:out value='${item.mpNo}' />&nbsp;</td>
            <td><c:out value='${item.gpSn}' />&nbsp;</td>
          </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    <div style="height: 30px; background: #DBEAEB; vertical-align: middle; text-align: right; border-left: 1px #85C0B4 solid; border-right: 1px #85C0B4 solid; border-bottom: 1px #85C0B4 solid;"><simpletable:pageToolbar page="${page}"></simpletable:pageToolbar></div>
    <div style="height: 40px; background: #CBEEEC; text-align: center; padding-top: 10px;">
      <input type="button" id="dqgflData" name="dqgflData" value="表码示值" onclick="readDqgflData()" class="btnbg4" />
      &nbsp;&nbsp;
      <input type="button" id="dqglData" name="dqglData" value="当前功率" onclick="readDqglData()" class="btnbg4" />
      &nbsp;&nbsp;
      <input type="button" id="dydlData" name="dydlData" value="电压电流" onclick="readDydlData()" class="btnbg4" />
    </div>
  </ul>
</div>
</body>
<script type="text/javascript">
var contextPath = "${ctx}";
var totalNums = 0;

//当前各费率电能示值
function readDqgflData() {
    var sysObject = "2";
    if(!hasOneChecked("ItemID")) {
        alert('请选择要操作的对象!');
        return;
    }
    //if(confirm('确定执行召测[当前各费率]?')) {
    if(true) {
        excelFlag = 1;
        deleteDataitemTd();
        disableButton();
        insertTd('正向有功总(kWh)', '0100');    //正向有功总
        insertTd('正向有功尖(kWh)', '0101');    //正向有功尖
        insertTd('正向有功峰(kWh)', '0102');    //正向有功峰
        insertTd('正向有功平(kWh)', '0103');    //正向有功平
        insertTd('正向有功谷(kWh)', '0104');    //正向有功谷
        insertTd('正向无功总(kvarh)', 'A000');  //正向无功总
        insertTd('反向有功总(kWh)', '0200');    //反向有功总
        insertTd('反向无功总(kvarh)', 'A100');  //反向无功总
        otherDataFlag = 0 ;
        read('100C0129,100C0130,100C0131,100C0132');
    }
}

//当前功率
function readDqglData() {
    if(!hasOneChecked("ItemID")){
        alert('请选择要操作的对象!');
        return;
    }
    //if(confirm('确定执行召测[当前功率]?')) {
    if(true) {
        excelFlag = 2;
        deleteDataitemTd();
        disableButton();
        insertTd('当前总有功功率(kW)', '2300');  //当前总有功功率
        insertTd('当前总无功功率(kW)', '2400');  //当前总无功功率
        insertTd('当前总功率因数(%)', '2600');      //当前总功率因数
        otherDataFlag = 0 ;
        read('100C0025');
    }
}

//电压电流
function readDydlData() {
    if(!hasOneChecked("ItemID")) {
        alert('请选择要操作的对象!');
        return;
    }
    //if(confirm('确定执行召测[电压电流]?')) {
    if(true) {
        excelFlag = 3;
        deleteDataitemTd();
        disableButton();
        insertTd('A相电压(V)', '2101');     //A相电压
        insertTd('B相电压(V)', '2102');     //B相电压
        insertTd('C相电压(V)', '2103');     //C相电压
        insertTd('A相电流(A)', '2201');     //A相电流
        insertTd('B相电流(A)', '2202');     //B相电流
        insertTd('C相电流(A)', '2203');     //C相电流
        otherDataFlag = 0 ;
        read('100C0025');
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
                td2.innerHTML = "&nbsp;";
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
                td2.innerHTML = "&nbsp;";
            }
        }
    }
}

function viewTds(data, commanditems, fetchCount) {
    if(data != null) {
        var jsonObj =  eval("(" + data + ")");
        //var jsonObj =  data ;
        var key = null;
        var value = null;
        for(var i = 0; i < jsonObj.data.length; i++) {
            key = jsonObj.data[i].key;
            //alert("key="+key);
            //alert($("#"+key).html())
            value = jsonObj.data[i].value;
            //balert(value);
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
    var valueArr = [];
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
