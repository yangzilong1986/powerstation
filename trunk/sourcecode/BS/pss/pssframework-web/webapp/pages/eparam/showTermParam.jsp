<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>终端参数设置</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/application.js"></script>
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/json2.js"></script>
<script type="text/javascript">
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

function folder(imgObj, itemId) {
    if(imgObj.lang == 'plus') {
        imgObj.lang = 'minus';
        imgObj.src = '<pss:path type="bgcolor"/>/img/minus1.gif';
        $(".clz" + itemId).css("display", "block");
    }
    else {
        imgObj.lang = 'plus';
        imgObj.src = '<pss:path type="bgcolor"/>/img/plus1.gif';
        $(".clz" + itemId).css("display", "none");
    }
}

function setup() {
    /*var dto = {
            "mtoType": "100",
            "coList": [{
                "logicalAddr": "91010001",
                "equipProtocol": "100",
                "channelType": "1",
                "pwAlgorith": "0",
                "pwContent": "8888",
                "mpExpressMode": "3",
                "mpSn": "0",
                "commandItems": [{
                    "identifier": "10040001", 
                    "datacellParam": [{"dataItemCode": "1004000101", "dataItemValue": "5"},
                                      {"dataItemCode": "1004000102", "dataItemValue": "1"},
                                      {"dataItemCode": "1004000103", "dataItemValue": "10"},
                                      {"dataItemCode": "1004000104", "dataItemValue": "5"},
                                      {"dataItemCode": "1004000106", "dataItemValue": "00100000"},
                                      {"dataItemCode": "1004000107", "dataItemValue": "30"}]
                },
                {
                    "identifier": "10040003", 
                    "datacellParam": [{"dataItemCode": "1004000301", "dataItemValue": "192.168.1.100:1024"},
                                      {"dataItemCode": "1004000302", "dataItemValue": "192.168.1.101:1024"},
                                      {"dataItemCode": "1004000303", "dataItemValue": "0000000000000000"}]
                }]
            }]
    };*/
    /*var dto = {
            "type": "GW_376",
            "collectObjects": [{
                "logicalAddr": "91010001",
                "equipProtocol": "100",
                "channelType": "1",
                "pwAlgorith": "0",
                "pwContent": "8888",
                "mpExpressMode": "3",
                "mpSn": [0],
                "commandItems": [{
                    "identifier": "10040001", 
                    "datacellParam": {"1004000101": "5",
                                      "1004000102": "1",
                                      "1004000103": "10",
                                      "1004000104": "5",
                                      "1004000106": "00100000",
                                      "1004000107": "30"}
                },
                {
                    "identifier": "10040003", 
                    "datacellParam": {"1004000301": "192.168.1.100:1024",
                                      "1004000302": "192.168.1.101:1024",
                                      "1004000303": "0000000000000000"}
                }]
            }]
    };*/
    //alert(escape(JSON.stringify(dto)));
    disableOperation();
    var sb_dto = new StringBuffer();
    sb_dto.append('{');
    sb_dto.append('"mtoType":"' + $("#protocolNo").val() + '"').append(',');
    sb_dto.append('"coList":').append('[{');
    sb_dto.append('"logicalAddr":"' + $("#logicalAddr").val() + '"').append(',');
    sb_dto.append('"equipProtocol":"' + $("#protocolNo").val() + '"').append(',');
    sb_dto.append('"channelType":"' + $("#channelType").val() + '"').append(',');
    sb_dto.append('"pwAlgorith":"' + $("#pwAlgorith").val() + '"').append(',');
    sb_dto.append('"pwContent":"' + $("#pwContent").val() + '"').append(',');
    sb_dto.append('"mpExpressMode":"' + $("#mpExpressMode").val() + '"').append(',');
    sb_dto.append('"mpSn":"' + $("#mpSn").val() + '"').append(',');
    sb_dto.append('"commandItems":').append('[').append('{');
    var cilist = getSelectedCheckboxs();
    //alert(cilist);
    $("#opcilist").val(cilist);
    var ciarray = cilist.split(',');
    for(var i = 0; i < ciarray.length; i++) {
        //alert($("tr[ci='" + ciarray[i] + "']").length);
        if(i > 0) {
            sb_dto.append('{');
        }
        sb_dto.append('"identifier":"' + ciarray[i] + '"').append(',');
        sb_dto.append('"datacellParam":').append('[').append('{');
        //var sb = new StringBuffer();
        for(var j = 0; j < $("tr[ci='" + ciarray[i] + "']").length; j++) {
            //alert($($("tr[ci='10040001']")[j]).attr("di"));
            if(j > 0) {
                sb_dto.append('{');
            }
            sb_dto.append('"dataItemCode":"' + $($("tr[ci='" + ciarray[i] + "']")[j]).attr("di") + '"').append(',');
            sb_dto.append('"dataItemValue":"' + $("#" + $($("tr[ci='" + ciarray[i] + "']")[j]).attr("di")).val() + '"');
            if(j < $("tr[ci='" + ciarray[i] + "']").length - 1) {
                sb_dto.append('}').append(',');
            }
            //sb.append(',"' + $($("tr[ci='" + ciarray[i] + "']")[j]).attr("di") + '":"' + $("#" + $($("tr[ci='" + ciarray[i] + "']")[j]).attr("di")).val() + '"');
        }
        sb_dto.append('}').append(']');
        //alert(sb.toString().substring(1));
        
        if(i < ciarray.length - 1) {
            sb_dto.append('}').append(',');
        }
    }
    sb_dto.append('}').append(']');
    sb_dto.append('}]');
    sb_dto.append('}');
    //alert(sb_dto.toString());
    //alert(escape(sb_dto.toString()));
    initOpResult('正在设置...');
    var url = '<pss:path type="webapp"/>/eparam/termparam/down.json';
    var params = {
            "dto": sb_dto.toString(),
            "type": "setup"
    };
    $.ajax({
        type: 'POST',
        //contentType: 'application/json',
        url: url,
        data: jQuery.param(params),
        dataType: 'json',
        success: function(data) {
            //alert(data.collectId);
            //alert(data.fetchCount);
            setTimeout("fetchSetupResult(" + data.collectId + ", " + data.fetchCount + ", 'setup')", 3000);
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
            initOpResult('下发失败...');
            enableOperation();
        }
    });
}

function fetchSetupResult(collectId, fetchCount, type) {
    //alert(collectId + "," + fetchCount);
    //alert($("#opcilist").val());
    var url = '<pss:path type="webapp"/>/eparam/termparam/up.json';
    var params = {
            "collectId": collectId,
            "type": type
    };
    $.ajax({
        type: 'POST',
        url: url,
        data: jQuery.param(params),
        dataType: 'json',
        success: function(data) {
            var b = showSetupResult(data.resultMap, type);
            if(!b && fetchCount > 0) {
                setTimeout("fetchSetupResult(" + collectId + ", " + (fetchCount - 1) + ", '" + type + "')", 3000);
            }
            else {
                if(type == 'setup') {
                    initOpResult('设置超时');
                }
                else {
                    initOpResult('读取超时');
                }
                enableOperation();
            }
        },
        error: function() {
        }
    });
}

function showSetupResult(resultMap, type) {
    var cilist = $("#opcilist").val();
    var logicalAddr = $("#logicalAddr").val();
    var mpSn = $("#mpSn").val();
    var ciarray = cilist.split(',');
    var cilistTemp = new StringBuffer();
    for(var i = 0; i < ciarray.length; i++) {
        //alert(resultMap[logicalAddr + '#' + mpSn + "#" + ciarray[i]]);
        var result = resultMap[logicalAddr + '#' + mpSn + "#" + ciarray[i]];
        if(typeof result != "undefined") {
            if(type == 'setup') {
                $("#ciop" + ciarray[i]).html(result);
            }
            else {
                $("#ciop" + ciarray[i]).html("读取成功");
                for(var j = 0; j < $("tr[ci='" + ciarray[i] + "']").length; j++) {
                    //sb_dto.append('"dataItemCode":"' + $($("tr[ci='" + ciarray[i] + "']")[j]).attr("di") + '"').append(',');
                    //sb_dto.append('"dataItemValue":"' + $("#" + $($("tr[ci='" + ciarray[i] + "']")[j]).attr("di")).val() + '"');
                    var dataitemCode = $($("tr[ci='" + ciarray[i] + "']")[j]).attr("di");
                    //alert(dataitemCode);
                    $("#diop" + dataitemCode).html(result[dataitemCode]);
                }
            }
        }
        else {
            cilistTemp.append(',').append(ciarray[i]);
        }
    }
    if(cilistTemp.toString().length > 0) {
        $("#opcilist").val(cilistTemp.toString().substring(1));
        return false;
    }
    else {
        $("#opcilist").val('');
        return true;
    }
}

function read() {
    disableOperation();
    var sb_dto = new StringBuffer();
    sb_dto.append('{');
    sb_dto.append('"collectObjects":').append('[{');
    sb_dto.append('"logicalAddr":"' + $("#logicalAddr").val() + '"').append(',');
    sb_dto.append('"equipProtocol":"' + $("#protocolNo").val() + '"').append(',');
    sb_dto.append('"channelType":"' + $("#channelType").val() + '"').append(',');
    sb_dto.append('"pwAlgorith":"' + $("#pwAlgorith").val() + '"').append(',');
    sb_dto.append('"pwContent":"' + $("#pwContent").val() + '"').append(',');
    sb_dto.append('"mpExpressMode":"' + $("#mpExpressMode").val() + '"').append(',');
    sb_dto.append('"mpSn":["' + $("#mpSn").val() + '"]').append(',');
    sb_dto.append('"commandItems":').append('[').append('{');
    var cilist = getSelectedCheckboxs();
    //alert(cilist);
    $("#opcilist").val(cilist);
    var ciarray = cilist.split(',');
    for(var i = 0; i < ciarray.length; i++) {
        //alert($("tr[ci='" + ciarray[i] + "']").length);
        if(i > 0) {
            sb_dto.append('{');
        }
        sb_dto.append('"identifier":"' + ciarray[i] + '"');
        if(i < ciarray.length - 1) {
            sb_dto.append('}').append(',');
        }
    }
    sb_dto.append('}').append(']');
    sb_dto.append('}]');
    sb_dto.append('}');
    //alert(sb_dto.toString());
    //alert(escape(sb_dto.toString()));
    initOpResult('正在读取...');
    var url = '<pss:path type="webapp"/>/eparam/termparam/down.json';
    var params = {
            "dto": sb_dto.toString(),
            "type": "read",
            "mtoType": $("#protocolNo").val()
    };
    $.ajax({
        type: 'POST',
        //contentType: 'application/json',
        url: url,
        data: jQuery.param(params),
        dataType: 'json',
        success: function(data) {
            //alert(data.collectId);
            //alert(data.fetchCount);
            setTimeout("fetchSetupResult(" + data.collectId + ", " + data.fetchCount + ", 'read')", 3000);
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
            initOpResult('下发失败...');
            enableOperation();
        }
    });
}

function initOpResult(msg) {
    var cilist = $("#opcilist").val();
    var ciarray = cilist.split(',');
    for(var i = 0; i < ciarray.length; i++) {
        $("#ciop" + ciarray[i]).html(msg);
    }
}

function disableOperation() {
    $("#bSetup").attr("disabled", true);
    $("#bRead").attr("disabled", true);
    
    $("input[type=checkbox][name='selectAll']").each( function() {
        $(this).attr("disabled", true);
    });
    $("input[type=checkbox][name='itemId']").each( function() {
        $(this).attr("disabled", true);
    });
}

function enableOperation() {
    $("#bSetup").attr("disabled", false);
    $("#bRead").attr("disabled", false);
    
    $("input[type=checkbox][name='selectAll']").each( function() {
        $(this).attr("disabled", false);
    });
    $("input[type=checkbox][name='itemId']").each( function() {
        $(this).attr("disabled", false);
    });
}

function selectAll(obj, name) {
    setAllCheckboxState(name, $(obj).attr("checked"));
}

function getSelectedCheckboxs() {
    var selected_checkboxs = "";
    $("input[type=checkbox][name='itemId']").each( function() {
        if($(this).attr("checked")) {
            selected_checkboxs += "," + $(this).val();
        }
    });
    return ($.trim(selected_checkboxs).length > 0 ? selected_checkboxs.substring(1) : "");
}
</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
  <ul class=default id=electric_Con_1>
    <div id="inquiry" style="margin-top: 5px;">
      <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="100" height="30" align="right" class="green">终端逻辑地址：</td>
          <td width="120"><input id="cLogicalAddr" name="cLogicalAddr" class="input2" value="" style="width: 140px; height: 18px;"/></td>
          <td width="100" align="right">
            <img src="<pss:path type="bgcolor"/>/img/inquiry.gif" align="middle" width="62" height="21" onclick="test(); return false;" style="cursor: pointer;" />
          </td>
        </tr>
      </table>
    </div>
    <div id="bg" style="height: 30px; text-align: center;">
      <ul id=”datamenu_Option“ class="cb font1">
        <li class="curr" id=datamenu_Option_0 style="cursor: pointer;">终端参数</li>
        <!-- <li id=datamenu_Option_1 style="cursor: pointer;">测量点参数</li> -->
      </ul>
    </div>
    <div class="datamenu_lcon" id="datamenu_Con">
      <ul class=default id=datamenu_Con_0>
        <div id="bg2">
          <ul>
            <li id="bSetup" onclick="setup()">设　置</li>
            <li id="bRead" onclick="read()">读　取</li>
          </ul>
        </div>
        <div style="display: none;">
          <input type="hidden" id="protocolNo" name="protocolNo" value="100" />
          <input type="hidden" id="logicalAddr" name="logicalAddr" value="96123456" />
          <input type="hidden" id="channelType" name="channelType" value="1" />
          <input type="hidden" id="pwAlgorith" name="pwAlgorith" value="0" />
          <input type="hidden" id="pwContent" name="pwContent" value="8888" />
          <input type="hidden" id="mpExpressMode" name="mpExpressMode" value="3" />
          <input type="hidden" id="mpSn" name="mpSn" value="0" />
          <input type="hidden" id="opcilist" name="opcilist" value="" />
        </div>
        <div class="content">
          <div id="cont_1">
            <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-99));">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <thead>
                  <tr>
                    <th width="7%" height="30" class="bg01">
                      <input align="middle" type="checkbox" name="selectAll" checked="checked" onclick="selectAll(this, 'itemId')" />
                    </th>
                    <th width="8%" class="bg01">参数类型</th>
                    <th width="30%" class="bg01">参数名称</th>
                    <th width="18%" class="bg01">待设值</th>
                    <th width="18%" class="bg01">上次设置值</th>
                    <th width="9%" class="bg01">单位</th>
                    <th width="10%" class="bg01">操作结果</th>
                  </tr>
                </thead>
                <tbody class="tblcontent2">
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040001" checked="checked" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040001');" />
                    </td>
                    <td>通信参数</td>
                    <td colspan="4">终端上行通信口通信参数设置</td>
                    <td id="ciop10040001">&nbsp;</td>
                  </tr>
                  <!-- 10040001 终端上行通信口通信参数设置 -->
                    <tr ci="10040001" di="1004000101" class="clz10040001" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">终端数传机延时时间RTS</td>
                      <td>
                        <select id="1004000101">
                          <option value="1">20ms</option>
                          <option value="2">40ms</option>
                          <option value="3">60ms</option>
                          <option value="4">80ms</option>
                          <option value="5">100ms</option>
                          <option value="6">120ms</option>
                          <option value="7">140ms</option>
                          <option value="8">160ms</option>
                          <option value="9">180ms</option>
                          <option value="10">200ms</option>
                          <option value="11">220ms</option>
                          <option value="12">240ms</option>
                          <option value="13">260ms</option>
                          <option value="14">280ms</option>
                          <option value="15">300ms</option>
                        </select>
                      </td>
                      <td id="diop1004000101">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040001" di="1004000102" class="clz10040001" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">终端作为启动站允许发送传输延时时间</td>
                      <td><input id="1004000102" value="1" /></td>
                      <td id="diop1004000102">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040001" di="1004000103" class="clz10040001" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">终端等待从动站响应的超时时间</td>
                      <td><input id="1004000103" value="10" /></td>
                      <td id="diop1004000103">&nbsp;</td>
                      <td>秒</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040001" di="1004000104" class="clz10040001" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">终端等待从动站响应的重发次数</td>
                      <td><input id="1004000104" value="5" /></td>
                      <td id="diop1004000104">&nbsp;</td>
                      <td>次</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040001" di="1004000106" class="clz10040001" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">需要主站确认的通信服务（CON=1）的标志</td>
                      <td><input id="1004000106" value="00100000" /></td>
                      <td id="diop1004000106">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040001" di="1004000107" class="clz10040001" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">心跳周期</td>
                      <td><input id="1004000107" value="30" /></td>
                      <td id="diop1004000107">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040001 终端上行通信口通信参数设置  -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040003" checked="checked" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040003');" />
                    </td>
                    <td>通信参数</td>
                    <td colspan="4">主站IP地址和端口</td>
                    <td id="ciop10040003">&nbsp;</td>
                  </tr>
                  <!-- 10040003 主站IP地址和端口 -->
                    <tr ci="10040003" di="1004000301" class="clz10040003" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">主用IP地址和端口</td>
                      <td><input id="1004000301" value="192.168.1.100:1024" /></td>
                      <td id="diop1004000301">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040003" di="1004000302" class="clz10040003" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">备用IP地址和端口</td>
                      <td><input id="1004000302" value="192.168.1.101:1024" /></td>
                      <td id="diop1004000302">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040003" di="1004000303" class="clz10040003" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">APN</td>
                      <td><input id="1004000303" value="0000000000000000" /></td>
                      <td id="diop1004000303">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040003 主站IP地址和端口 -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040004" checked="checked" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040004');" />
                    </td>
                    <td>通信参数</td>
                    <td colspan="4">主站电话号码和短信中心号码</td>
                    <td id="ciop10040004">&nbsp;</td>
                  </tr>
                  <!-- 10040004 主站电话号码和短信中心号码 -->
                    <tr ci="10040004" di="1004000401" class="clz10040004" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">主站电话号码或主站手机号码</td>
                      <td><input id="1004000401" value="13888888888" /></td>
                      <td id="diop1004000401">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040004" di="1004000402" class="clz10040004" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">短信中心号码</td>
                      <td><input id="1004000402" value="13800571000" /></td>
                      <td id="diop1004000402">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040004 主站电话号码和短信中心号码 -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040007" checked="checked" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040007');" />
                    </td>
                    <td>通信参数</td>
                    <td colspan="4">终端IP地址和端口</td>
                    <td id="ciop10040007">&nbsp;</td>
                  </tr>
                  <!-- 10040007 终端IP地址和端口 -->
                    <tr ci="10040007" di="1004000701" class="clz10040007" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">终端IP地址</td>
                      <td><input id="1004000701" value="107.7.7.77" /></td>
                      <td id="diop1004000701">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040007" di="1004000702" class="clz10040007" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">子网掩码地址</td>
                      <td><input id="1004000702" value="255.128.0.0" /></td>
                      <td id="diop1004000702">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040007" di="1004000703" class="clz10040007" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">网关地址</td>
                      <td><input id="1004000703" value="107.7.7.1" /></td>
                      <td id="diop1004000703">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040007" di="1004000704" class="clz10040007" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">代理服务器代理类型</td>
                      <td>
                        <select id="1004000704">
                          <option value="0">不使用代理</option>
                          <option value="1">HTTP Connect代理</option>
                          <option value="2">Socks4代理</option>
                          <option value="3">Socks5代理</option>
                        </select>
                      </td>
                      <td id="diop1004000704">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040007" di="1004000705" class="clz10040007" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">代理服务器地址和端口</td>
                      <td><input id="1004000705" value="107.7.7.7:1024" /></td>
                      <td id="diop1004000705">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040007" di="1004000706" class="clz10040007" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">代理服务器连接方式</td>
                      <td>
                        <select id="1004000706">
                          <option value="0">无需验证</option>
                          <option value="1">需要用户名/密码</option>
                        </select>
                      </td>
                      <td id="diop1004000706">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040007" di="1004000707" class="clz10040007" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">代理服务器用户名长度</td>
                      <td><input id="1004000707" value="5" /></td>
                      <td id="diop1004000707">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040007" di="1004000708" class="clz10040007" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">代理服务器用户名</td>
                      <td><input id="1004000708" value="admin" /></td>
                      <td id="diop1004000708">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040007" di="1004000709" class="clz10040007" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">代理服务器密码长度</td>
                      <td><input id="1004000709" value="5" /></td>
                      <td id="diop1004000709">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040007" di="1004000710" class="clz10040007" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">代理服务器密码</td>
                      <td><input id="1004000710" value="admin" /></td>
                      <td id="diop1004000710">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040007" di="1004000711" class="clz10040007" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">终端侦听端口</td>
                      <td><input id="1004000711" value="7" /></td>
                      <td id="diop1004000711">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040007 终端IP地址和端口 -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040008" checked="checked" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040008');" />
                    </td>
                    <td>通信参数</td>
                    <td colspan="4">终端上行通信工作方式（以太专网或虚拟专网）</td>
                    <td id="ciop10040008">&nbsp;</td>
                  </tr>
                  <!-- 10040008 终端上行通信工作方式（以太专网或虚拟专网） -->
                    <tr ci="10040008" di="1004000801" class="clz10040008" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">TCP/UDP</td>
                      <td>
                        <select id="1004000801">
                          <option value="0">TCP</option>
                          <option value="1">UDP</option>
                        </select>
                      </td>
                      <td id="diop1004000801">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040008" di="1004000803" class="clz10040008" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">终端工作模式</td>
                      <td>
                        <select id="1004000803">
                          <option value="0">混合模式</option>
                          <option value="1">客户机模式</option>
                          <option value="2">服务器模式</option>
                        </select>
                      </td>
                      <td id="diop1004000803">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040008" di="1004000805" class="clz10040008" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">终端工作在客户机模式下的三种在线模式</td>
                      <td>
                        <select id="1004000805">
                          <option value="1">永久在线模式</option>
                          <option value="2">被动激活模式</option>
                          <option value="3">时段在线模式</option>
                        </select>
                      </td>
                      <td id="diop1004000805">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040008" di="1004000806" class="clz10040008" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">客户机模式下永久在线、时段在线模式重拨间隔</td>
                      <td>
                        <input id="1004000806" value="10" />
                      </td>
                      <td id="diop1004000806">&nbsp;</td>
                      <td>秒</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040008" di="1004000807" class="clz10040008" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">客户机模式下被动激活模式重拨次数</td>
                      <td>
                        <input id="1004000807" value="5" />
                      </td>
                      <td id="diop1004000807">&nbsp;</td>
                      <td>次</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040008" di="1004000808" class="clz10040008" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">客户机模式下被动激活模式连续无通信自动断线时间</td>
                      <td>
                        <input id="1004000808" value="10" />
                      </td>
                      <td id="diop1004000808">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040008" di="1004000809" class="clz10040008" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">客户机模式下时段在线模式允许在线时段标志</td>
                      <td>
                        <input id="1004000809" value="000000000000000000000000" />
                      </td>
                      <td id="diop1004000809">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040008 终端上行通信工作方式（以太专网或虚拟专网） -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040016" checked="checked" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040016');" />
                    </td>
                    <td>通信参数</td>
                    <td colspan="4">虚拟专网用户名、密码</td>
                    <td id="ciop10040016">&nbsp;</td>
                  </tr>
                  <!-- 10040016 虚拟专网用户名、密码 -->
                    <tr ci="10040016" di="1004001601" class="clz10040016" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">虚拟专网用户名</td>
                      <td>
                        <input id="1004001601" value="AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" />
                      </td>
                      <td id="diop1004001601">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040016" di="1004001602" class="clz10040016" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">虚拟专网密码</td>
                      <td>
                        <input id="1004001602" value="AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" />
                      </td>
                      <td id="diop1004001602">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040016 虚拟专网用户名、密码 -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040009" checked="checked" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040009');" />
                    </td>
                    <td>事件参数</td>
                    <td colspan="4">终端事件记录配置设置</td>
                    <td id="ciop10040009">&nbsp;</td>
                  </tr>
                  <!-- 10040009 终端上行通信工作方式（以太专网或虚拟专网） -->
                    <tr ci="10040009" di="1004000901" class="clz10040009" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">事件记录有效标志位</td>
                      <td>
                        <input id="1004000901" value="0000000000000000000000000000000000000000000000000000000000000000" />
                      </td>
                      <td id="diop1004000901">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040009" di="1004000902" class="clz10040009" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">事件重要性等级标志位</td>
                      <td>
                        <input id="1004000902" value="0000000000000000000000000000000000000000000000000000000000000000" />
                      </td>
                      <td id="diop1004000902">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040009 终端上行通信工作方式（以太专网或虚拟专网） -->
                  <!-- 
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040010" checked="checked" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040010');" />
                    </td>
                    <td>电表配置</td>
                    <td colspan="4">终端电能表/交流采样装置配置参数</td>
                    <td id="ciop10040010">&nbsp;</td>
                  </tr>
                   -->
                  <!-- 10040010 终端电能表/交流采样装置配置参数 -->
                  <!-- /10040010 终端电能表/交流采样装置配置参数 -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040012" checked="checked" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040012');" />
                    </td>
                    <td>状态量参数</td>
                    <td colspan="4">终端状态量输入参数</td>
                    <td id="ciop10040012">&nbsp;</td>
                  </tr>
                  <!-- 10040012 终端状态量输入参数 -->
                    <tr ci="10040012" di="1004001201" class="clz10040012" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">状态量接入标志位</td>
                      <td>
                        <input id="1004001201" value="11111111" />
                      </td>
                      <td id="diop1004001201">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040012" di="1004001202" class="clz10040012" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">状态量属性标志位</td>
                      <td>
                        <input id="1004001202" value="00000000" />
                      </td>
                      <td id="diop1004001202">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040012 终端状态量输入参数 -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040017" checked="checked" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040017');" />
                    </td>
                    <td>控制参数</td>
                    <td colspan="4">终端保安定值</td>
                    <td id="ciop10040017">&nbsp;</td>
                  </tr>
                  <!-- 10040017 终端保安定值 -->
                    <tr ci="10040017" di="1004001701" class="clz10040017" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">保安定值</td>
                      <td>
                        <input id="1004001701" value="1000" />
                      </td>
                      <td id="diop1004001701">&nbsp;</td>
                      <td>kW</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040017 终端保安定值 -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040005" checked="checked" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040005');" />
                    </td>
                    <td>终端属性</td>
                    <td colspan="4">终端上行通信消息认证参数设置</td>
                    <td id="ciop10040005">&nbsp;</td>
                  </tr>
                  <!-- 10040005 终端上行通信消息认证参数设置 -->
                    <tr ci="10040005" di="1004000501" class="clz10040005" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">消息认证方案号</td>
                      <td>
                        <input id="1004000501" value="0" />
                      </td>
                      <td id="diop1004000501">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040005" di="1004000502" class="clz10040005" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">消息认证方案参数</td>
                      <td>
                        <input id="1004000502" value="8888" />
                      </td>
                      <td id="diop1004000502">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040005 终端上行通信消息认证参数设置 -->
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </ul>
    </div>
  </ul>
</div>
</body>
</html>