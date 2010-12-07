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
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/effects.js"></script>
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

function chkSelected(type, cate) {
    if($("#cLogicalAddr").val() == '') {
        alert('请输入要设置终端的逻辑地址');
        return false;
    }
    
    var cilist = '';
    if(cate == 1) {        // 终端参数
        var cilist = getSelectedCheckboxs1();
    }
    else if(cate == 2) {   // 测量点参数
        var cilist = getSelectedCheckboxs2();
    }
    else if(cate == 4) {   // 直流模拟量参数
        var cilist = getSelectedCheckboxs4();
    }

    if(cilist == '') {
        if(type == 'setup') {   // 设置
            alert('请选择要设置的参数');
            return false;
        }
        else {                  // 读取
            alert('请选择要读取的参数');
            return false;
        }
    }

    if(type == 'setup') {   // 设置
        if(!confirm("确定要对终端[" + $("#cLogicalAddr").val() + "]设置所选的参数吗？")) {
            return false;
        }
    }
    else {                  // 读取
        if(!confirm("确定要对终端[" + $("#cLogicalAddr").val() + "]读取所选的参数吗？")) {
            return false;
        }
    }

    return true;
}

function setup(cate) {
    if(chkSelected('setup', cate)) {
        disableOperation();
        var sb_dto = new StringBuffer();
        sb_dto.append('{');
        sb_dto.append('"mtoType":"' + $("#protocolNo").val() + '"').append(',');
        sb_dto.append('"coList":').append('[{');
        sb_dto.append('"logicalAddr":"' + $("#cLogicalAddr").val() + '"').append(',');
        sb_dto.append('"equipProtocol":"' + $("#protocolNo").val() + '"').append(',');
        sb_dto.append('"channelType":"' + $("#channelType").val() + '"').append(',');
        sb_dto.append('"pwAlgorith":"' + $("#pwAlgorith").val() + '"').append(',');
        sb_dto.append('"pwContent":"' + $("#pwContent").val() + '"').append(',');
        sb_dto.append('"mpExpressMode":"' + $("#mpExpressMode").val() + '"').append(',');
        if(cate == 1) {        // 终端参数
            sb_dto.append('"mpSn":"' + $("#mpSn").val() + '"').append(',');
        }
        else if(cate == 2) {   // 测量点参数
            sb_dto.append('"mpSn":"' + $("#gpSn").val() + '"').append(',');
        }
        else if(cate == 4) {   // 直流模拟量参数
            sb_dto.append('"mpSn":"' + $("#agSn").val() + '"').append(',');
        }
        sb_dto.append('"commandItems":').append('[').append('{');
        var cilist = "";
        if(cate == 1) {        // 终端参数
            cilist = getSelectedCheckboxs1();
        }
        else if(cate == 2) {   // 测量点参数
            cilist = getSelectedCheckboxs2();
        }
        else if(cate == 4) {   // 直流模拟量参数
            cilist = getSelectedCheckboxs4();
        }
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
                setTimeout("fetchResult(" + data.collectId + ", " + data.fetchCount + ", 'setup', " + cate + ")", 3000);
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
                initOpResult('下发失败...');
                enableOperation();
            }
        });
    }
}

function fetchResult(collectId, fetchCount, type, cate) {
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
            var b = showResult(data.resultMap, type, cate);
            if(!b && fetchCount > 0) {
                setTimeout("fetchResult(" + collectId + ", " + (fetchCount - 1) + ", '" + type + "', " + cate + ")", 3000);
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

function showResult(resultMap, type, cate) {
    var cilist = $("#opcilist").val();
    var logicalAddr = $("#cLogicalAddr").val();
    var mpSn = $("#mpSn").val();
    if(cate == 1) {        // 终端参数
        mpSn = $("#mpSn").val();
    }
    else if(cate == 2) {   // 测量点参数
        mpSn = $("#gpSn").val();
    }
    else if(cate == 4) {   // 直流模拟量参数
        mpSn = $("#agSn").val();
    }
    
    var ciarray = cilist.split(',');
    var cilistTemp = new StringBuffer();
    for(var i = 0; i < ciarray.length; i++) {
        //alert(resultMap[logicalAddr + '#' + mpSn + "#" + ciarray[i]]);
        var result = resultMap[logicalAddr + '#' + mpSn + "#" + ciarray[i]];
        if(typeof result != "undefined") {
            if(type == 'setup') {
                if(result == "1") {
                    $("#ciop" + ciarray[i]).html("设置成功");
                }
                else if(result == "2") {
                    $("#ciop" + ciarray[i]).html("设置失败");
                }
                else {
                    $("#ciop" + ciarray[i]).html("未知[" + result + "]");
                }
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

function read(cate) {
    if(chkSelected('read', cate)) {
        disableOperation();
        var sb_dto = new StringBuffer();
        sb_dto.append('{');
        sb_dto.append('"collectObjects":').append('[{');
        sb_dto.append('"logicalAddr":"' + $("#cLogicalAddr").val() + '"').append(',');
        sb_dto.append('"equipProtocol":"' + $("#protocolNo").val() + '"').append(',');
        sb_dto.append('"channelType":"' + $("#channelType").val() + '"').append(',');
        sb_dto.append('"pwAlgorith":"' + $("#pwAlgorith").val() + '"').append(',');
        sb_dto.append('"pwContent":"' + $("#pwContent").val() + '"').append(',');
        sb_dto.append('"mpExpressMode":"' + $("#mpExpressMode").val() + '"').append(',');
        if(cate == 1) {        // 终端参数
            sb_dto.append('"mpSn":["' + $("#mpSn").val() + '"]').append(',');
        }
        else if(cate == 2) {   // 测量点参数
            sb_dto.append('"mpSn":["' + $("#gpSn").val() + '"]').append(',');
        }
        else if(cate == 4) {   // 直流模拟量参数
            sb_dto.append('"mpSn":["' + $("#agSn").val() + '"]').append(',');
        }
        sb_dto.append('"commandItems":').append('[').append('{');
        var cilist = "";
        if(cate == 1) {        // 终端参数
            cilist = getSelectedCheckboxs1();
        }
        else if(cate == 2) {   // 测量点参数
            cilist = getSelectedCheckboxs2();
        }
        else if(cate == 4) {   // 直流模拟量参数
            cilist = getSelectedCheckboxs4();
        }
        //alert(cilist);
        $("#opcilist").val(cilist);
        var ciarray = cilist.split(',');
        for(var i = 0; i < ciarray.length; i++) {
            //alert($("tr[ci='" + ciarray[i] + "']").length);
            if(i > 0) {
                sb_dto.append('{');
            }
            
            if('10040010' == ciarray[i]) {
                //sb_dto.append('"identifier":"' + ciarray[i] + '"');
                sb_dto.append('"identifier":"' + ciarray[i] + '"').append(',');
                sb_dto.append('"datacellParam":').append('{');
                sb_dto.append('"1004001001": "1"');
                sb_dto.append('}').append(',');
                sb_dto.append('"circleDataItems":').append('{');
                sb_dto.append('"dataItemGroups":').append('[').append('{');
                sb_dto.append('"dataItemList":').append('[').append('{');
                sb_dto.append('"dataItemCode": "10040010020001"').append(',');
                sb_dto.append('"dataItemValue": "1"');
                sb_dto.append('}').append(']');
                sb_dto.append('}').append(']');
                sb_dto.append('}');
            }
            else {
                sb_dto.append('"identifier":"' + ciarray[i] + '"');
            }
            
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
                setTimeout("fetchResult(" + data.collectId + ", " + data.fetchCount + ", 'read', " + cate + ")", 3000);
            },
            error: function(XmlHttpRequest, textStatus, errorThrown){
                initOpResult('下发失败...');
                enableOperation();
            }
        });
    }
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
    
    $("input[type=checkbox][name='selectAll1']").each( function() {
        $(this).attr("disabled", true);
    });
    $("input[type=checkbox][name='itemId1']").each( function() {
        $(this).attr("disabled", true);
    });

    $("input[type=checkbox][name='selectAll2']").each( function() {
        $(this).attr("disabled", true);
    });
    $("input[type=checkbox][name='itemId2']").each( function() {
        $(this).attr("disabled", true);
    });

    $("input[type=checkbox][name='selectAll4']").each( function() {
        $(this).attr("disabled", true);
    });
    $("input[type=checkbox][name='itemId4']").each( function() {
        $(this).attr("disabled", true);
    });
}

function enableOperation() {
    $("#bSetup").attr("disabled", false);
    $("#bRead").attr("disabled", false);
    
    $("input[type=checkbox][name='selectAll1']").each( function() {
        $(this).attr("disabled", false);
    });
    $("input[type=checkbox][name='itemId1']").each( function() {
        $(this).attr("disabled", false);
    });

    $("input[type=checkbox][name='selectAll2']").each( function() {
        $(this).attr("disabled", false);
    });
    $("input[type=checkbox][name='itemId2']").each( function() {
        $(this).attr("disabled", false);
    });

    $("input[type=checkbox][name='selectAll4']").each( function() {
        $(this).attr("disabled", false);
    });
    $("input[type=checkbox][name='itemId4']").each( function() {
        $(this).attr("disabled", false);
    });
}

function selectAll(obj, name) {
    setAllCheckboxState(name, $(obj).attr("checked"));
}

function getSelectedCheckboxs1() {
    var selected_checkboxs = "";
    $("input[type=checkbox][name='itemId1']").each( function() {
        if($(this).attr("checked")) {
            selected_checkboxs += "," + $(this).val();
        }
    });
    return ($.trim(selected_checkboxs).length > 0 ? selected_checkboxs.substring(1) : "");
}

function getSelectedCheckboxs2() {
    var selected_checkboxs = "";
    $("input[type=checkbox][name='itemId2']").each( function() {
        if($(this).attr("checked")) {
            selected_checkboxs += "," + $(this).val();
        }
    });
    return ($.trim(selected_checkboxs).length > 0 ? selected_checkboxs.substring(1) : "");
}

function getSelectedCheckboxs4() {
    var selected_checkboxs = "";
    $("input[type=checkbox][name='itemId4']").each( function() {
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
          <td width="100" height="30" align="right" class="green">逻辑地址：</td>
          <td width="120"><input id="cLogicalAddr" name="cLogicalAddr" class="input2" value="96123455" style="width: 140px; height: 20px;"/></td>
          <td width="100" align="right">
            <img src="<pss:path type="bgcolor"/>/img/inquiry.gif" align="middle" width="62" height="21" onclick="return false;" style="cursor: pointer;" />
          </td>
        </tr>
      </table>
    </div>
    <div id="bg" style="height: 30px; text-align: center;">
      <ul id=”datamenu_Option“ class="cb font1">
        <li class="curr" id=datamenu_Option_0 style="cursor: pointer;" onmouseover="SwitchTab('datamenu_',0,3)">终端参数</li>
        <li id=datamenu_Option_1 style="cursor: pointer;" onmouseover="SwitchTab('datamenu_',1,3)">测量点参数</li>
        <li id=datamenu_Option_2 style="cursor: pointer;" onmouseover="SwitchTab('datamenu_',2,3)">直流模拟量参数</li>
      </ul>
    </div>
    <div style="display: none;">
      <input type="hidden" id="protocolNo" name="protocolNo" value="100" />
      <!-- <input type="hidden" id="logicalAddr" name="logicalAddr" value="96123456" /> -->
      <input type="hidden" id="channelType" name="channelType" value="1" />
      <input type="hidden" id="pwAlgorith" name="pwAlgorith" value="0" />
      <input type="hidden" id="pwContent" name="pwContent" value="8888" />
      <input type="hidden" id="mpExpressMode" name="mpExpressMode" value="3" />
      <input type="hidden" id="mpSn" name="mpSn" value="0" />
      <input type="hidden" id="opcilist" name="opcilist" value="" />
    </div>
    <div class="datamenu_lcon" id="datamenu_Con">
      <ul class=default id=datamenu_Con_0>
        <div id="bg2">
          <ul>
            <li id="bSetup" onclick="setup(1)">设　置</li>
            <li id="bRead" onclick="read(1)">读　取</li>
          </ul>
        </div>
        <div class="content">
          <div id="cont_1">
            <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-89));">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <thead>
                  <tr>
                    <th width="7%" height="30" class="bg01">
                      <input align="middle" type="checkbox" name="selectAll1" onclick="selectAll(this, 'itemId1')" />
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
                      <input align="middle" type="checkbox" name="itemId1" value="10040001" />
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
                      <input align="middle" type="checkbox" name="itemId1" value="10040003" />
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
                      <input align="middle" type="checkbox" name="itemId1" value="10040009" />
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
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId1" value="10040010" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040010');" />
                    </td>
                    <td>电表配置</td>
                    <td colspan="4">终端电能表/交流采样装置配置参数</td>
                    <td id="ciop10040010">&nbsp;</td>
                  </tr>
                  <!-- 10040010 终端电能表/交流采样装置配置参数 -->
                    <tr ci="10040010" di="1004001001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次电能表/交流采样装置配置数量</td>
                      <td>
                        <input id="1004001001" value="1" />
                      </td>
                      <td id="diop1004001001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010020001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置序号</td>
                      <td>
                        <input id="10040010020001" value="1" />
                      </td>
                      <td id="diop10040010020001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010030001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置所属测量点号</td>
                      <td>
                        <input id="10040010030001" value="1" />
                      </td>
                      <td id="diop10040010030001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010040001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置通信波特率</td>
                      <td>
                        <select id="10040010040001">
                          <option value="0">无需设置或使用默认</option>
                          <option value="1">600</option>
                          <option value="2">1200</option>
                          <option value="3">2400</option>
                          <option value="4">4800</option>
                          <option value="5">7200</option>
                          <option value="6">9600</option>
                          <option value="7">19200</option>
                        </select>
                      </td>
                      <td id="diop10040010040001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010050001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置通信端口号</td>
                      <td>
                        <input id="10040010050001" value="1" />
                      </td>
                      <td id="diop10040010050001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010060001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置通信协议类型</td>
                      <td>
                        <select id="10040010060001" style="width: 150px;">
                          <option value="1">DL/T 645—1997</option>
                          <option value="2">交流采样装置通信协议</option>
                          <option value="30">DL/T 645—2007</option>
                          <option value="31">“串行接口连接窄带低压载波通信模块”接口协议</option>
                          <option value="100">漏保规约</option>
                        </select>
                      </td>
                      <td id="diop10040010060001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010070001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置通信地址</td>
                      <td>
                        <input id="10040010070001" value="000000000001" />
                      </td>
                      <td id="diop10040010070001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010080001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置通信密码</td>
                      <td>
                        <input id="10040010080001" value="000000000000" />
                      </td>
                      <td id="diop10040010080001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010100001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置电能费率个数</td>
                      <td>
                        <select id="10040010100001">
                          <option value="000001">1个</option>
                          <option value="000010">2个</option>
                          <option value="000011">3个</option>
                          <option value="000100">4个</option>
                          <option value="000101">5个</option>
                          <option value="000110">6个</option>
                          <option value="000111">7个</option>
                          <option value="001000">8个</option>
                          <option value="001001">9个</option>
                          <option value="001010">10个</option>
                          <option value="001011">11个</option>
                          <option value="001100">12个</option>
                          <option value="001101">13个</option>
                          <option value="001110">14个</option>
                          <option value="001111">15个</option>
                          <option value="010000">16个</option>
                          <option value="010001">17个</option>
                          <option value="010010">18个</option>
                          <option value="010011">19个</option>
                          <option value="010100">20个</option>
                          <option value="010101">21个</option>
                          <option value="010110">22个</option>
                          <option value="010111">23个</option>
                          <option value="011000">24个</option>
                          <option value="011001">25个</option>
                          <option value="011010">26个</option>
                          <option value="011011">27个</option>
                          <option value="011100">28个</option>
                          <option value="011101">29个</option>
                          <option value="011110">30个</option>
                          <option value="011111">31个</option>
                          <option value="100000">32个</option>
                          <option value="100001">33个</option>
                          <option value="100010">34个</option>
                          <option value="100011">35个</option>
                          <option value="100100">36个</option>
                          <option value="100101">37个</option>
                          <option value="100110">38个</option>
                          <option value="100111">39个</option>
                          <option value="101000">40个</option>
                          <option value="101001">41个</option>
                          <option value="101010">42个</option>
                          <option value="101011">43个</option>
                          <option value="101100">44个</option>
                          <option value="101101">45个</option>
                          <option value="101110">46个</option>
                          <option value="101111">47个</option>
                          <option value="110000">48个</option>
                        </select>
                      </td>
                      <td id="diop10040010100001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010120001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置有功电能示值的整数位个数</td>
                      <td>
                        <select id="10040010120001">
                          <option value="00">4位</option>
                          <option value="01">5位</option>
                          <option value="10">6位</option>
                          <option value="11">7位</option>
                        </select>
                      </td>
                      <td id="diop10040010120001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010130001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置有功电能示值的小数位个数</td>
                      <td>
                        <select id="10040010130001">
                          <option value="00">1位</option>
                          <option value="01">2位</option>
                          <option value="10">3位</option>
                          <option value="11">4位</option>
                        </select>
                      </td>
                      <td id="diop10040010130001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010140001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置所属采集器通信地址</td>
                      <td>
                        <input id="10040010140001" value="000000000000" />
                      </td>
                      <td id="diop10040010140001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010150001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置所属的用户大类号</td>
                      <td>
                        <input id="10040010150001" value="0000" />
                      </td>
                      <td id="diop10040010150001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040010" di="10040010160001" class="clz10040010" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">本次配置第1块电能表/交流采样装置所属的用户小类号</td>
                      <td>
                        <input id="10040010160001" value="0000" />
                      </td>
                      <td id="diop10040010160001">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040010 终端电能表/交流采样装置配置参数 -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId1" value="10040012" />
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
                      <input align="middle" type="checkbox" name="itemId1" value="10040061" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040061');" />
                    </td>
                    <td>模拟量参数</td>
                    <td colspan="4">直流模拟量接入参数</td>
                    <td id="ciop10040061">&nbsp;</td>
                  </tr>
                  <!-- 10040061 直流模拟量接入参数 -->
                    <tr ci="10040061" di="1004006101" class="clz10040061" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">直流模拟量接入标志位</td>
                      <td>
                        <input id="1004006101" value="11111111" />
                      </td>
                      <td id="diop1004006101">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040061 直流模拟量接入参数 -->
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </ul>
      <ul class=disNone id=datamenu_Con_1>
        <div id="bg2">
          <ul>
            <li id="bSetup" onclick="setup(2)">设　置</li>
            <li id="bRead" onclick="read(2)">读　取</li>
          </ul>
          <div style="float: right; padding-right: 10px;">
            <select id="gpSn" name="gpSn">
              <option value="1">测量点1</option>
              <option value="2">测量点2</option>
              <option value="3">测量点3</option>
              <option value="4">测量点4</option>
              <option value="5">测量点5</option>
              <option value="6">测量点6</option>
              <option value="7">测量点7</option>
              <option value="8">测量点8</option>
              <option value="9">测量点9</option>
            </select>
          </div>
        </div>
        <div class="content">
          <div id="cont_2">
            <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-89));">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <thead>
                  <tr>
                    <th width="7%" height="30" class="bg01">
                      <input align="middle" type="checkbox" name="selectAll2" onclick="selectAll(this, 'itemId2')" />
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
                      <input align="middle" type="checkbox" name="itemId2" value="10040025" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040025');" />
                    </td>
                    <td>基本参数</td>
                    <td colspan="4">测量点基本参数</td>
                    <td id="ciop10040025">&nbsp;</td>
                  </tr>
                  <!-- 10040025 测量点基本参数 -->
                    <tr ci="10040025" di="1004002501" class="clz10040025" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">电压互感器倍率</td>
                      <td>
                        <input id="1004002501" value="1" />
                      </td>
                      <td id="diop1004002501">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040025" di="1004002502" class="clz10040025" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">电流互感器倍率</td>
                      <td>
                        <input id="1004002502" value="1" />
                      </td>
                      <td id="diop1004002502">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040025" di="1004002503" class="clz10040025" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">额定电压</td>
                      <td>
                        <input id="1004002503" value="220.0" />
                      </td>
                      <td id="diop1004002503">&nbsp;</td>
                      <td>V</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040025" di="1004002504" class="clz10040025" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">额定电流</td>
                      <td>
                        <input id="1004002504" value="5.0" />
                      </td>
                      <td id="diop1004002504">&nbsp;</td>
                      <td>A</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040025" di="1004002505" class="clz10040025" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">额定负荷</td>
                      <td>
                        <input id="1004002505" value="50.0000" />
                      </td>
                      <td id="diop1004002505">&nbsp;</td>
                      <td>kVA</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040025" di="1004002507" class="clz10040025" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">单相表接线相</td>
                      <td>
                        <select id="1004002507">
                          <option value="00">不确定</option>
                          <option value="01">A相</option>
                          <option value="10">B相</option>
                          <option value="11">C相</option>
                        </select>
                      </td>
                      <td id="diop1004002507">&nbsp;</td>
                      <td>kVA</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040025" di="1004002508" class="clz10040025" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">电源接线方式</td>
                      <td>
                        <select id="1004002508">
                          <option value="00">不确定</option>
                          <option value="01">三相三线</option>
                          <option value="10">三相四线</option>
                          <option value="11">单相表</option>
                        </select>
                      </td>
                      <td id="diop1004002508">&nbsp;</td>
                      <td>kVA</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040025 测量点基本参数 -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId2" value="10040026" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040026');" />
                    </td>
                    <td>限值参数</td>
                    <td colspan="4">测量点限值参数</td>
                    <td id="ciop10040026">&nbsp;</td>
                  </tr>
                  <!-- 10040026 测量点限值参数 -->
                    <tr ci="10040026" di="1004002601" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">电压合格上限</td>
                      <td>
                        <input id="1004002601" value="300.00" />
                      </td>
                      <td id="diop1004002601">&nbsp;</td>
                      <td>V</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002602" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">电压合格下限</td>
                      <td>
                        <input id="1004002602" value="150.00" />
                      </td>
                      <td id="diop1004002602">&nbsp;</td>
                      <td>V</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002603" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">电压断相门限</td>
                      <td>
                        <input id="1004002603" value="100.00" />
                      </td>
                      <td id="diop1004002603">&nbsp;</td>
                      <td>V</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002604" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">电压上上限（过压门限）</td>
                      <td>
                        <input id="1004002604" value="360.00" />
                      </td>
                      <td id="diop1004002604">&nbsp;</td>
                      <td>V</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002605" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">过压越限持续时间</td>
                      <td>
                        <input id="1004002605" value="10" />
                      </td>
                      <td id="diop1004002605">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002606" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">过压越限恢复系数</td>
                      <td>
                        <input id="1004002606" value="150.00" />
                      </td>
                      <td id="diop1004002606">&nbsp;</td>
                      <td>%</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002607" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">电压下下限（欠压门限）</td>
                      <td>
                        <input id="1004002607" value="120.00" />
                      </td>
                      <td id="diop1004002607">&nbsp;</td>
                      <td>V</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002608" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">欠压越限持续时间</td>
                      <td>
                        <input id="1004002608" value="10" />
                      </td>
                      <td id="diop1004002608">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002609" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">欠压越限恢复系数</td>
                      <td>
                        <input id="1004002609" value="50.0" />
                      </td>
                      <td id="diop1004002609">&nbsp;</td>
                      <td>%</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002610" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">相电流上上限（过流门限）</td>
                      <td>
                        <input id="1004002610" value="8.000" />
                      </td>
                      <td id="diop1004002610">&nbsp;</td>
                      <td>A</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002611" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">过流越限持续时间</td>
                      <td>
                        <input id="1004002611" value="10" />
                      </td>
                      <td id="diop1004002611">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002612" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">过流越限恢复系数</td>
                      <td>
                        <input id="1004002612" value="120.00" />
                      </td>
                      <td id="diop1004002612">&nbsp;</td>
                      <td>%</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002613" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">相电流上限（额定电流门限）</td>
                      <td>
                        <input id="1004002613" value="6.000" />
                      </td>
                      <td id="diop1004002613">&nbsp;</td>
                      <td>A</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002614" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">超额定电流越限持续时间</td>
                      <td>
                        <input id="1004002614" value="10" />
                      </td>
                      <td id="diop1004002614">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002615" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">超额定电流越限恢复系数</td>
                      <td>
                        <input id="1004002615" value="110.00" />
                      </td>
                      <td id="diop1004002615">&nbsp;</td>
                      <td>%</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002616" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">零序电流上限</td>
                      <td>
                        <input id="1004002616" value="2.000" />
                      </td>
                      <td id="diop1004002616">&nbsp;</td>
                      <td>A</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002617" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">零序电流超限越限持续时间</td>
                      <td>
                        <input id="1004002617" value="10" />
                      </td>
                      <td id="diop1004002617">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002618" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">零序电流超限越限恢复系数</td>
                      <td>
                        <input id="1004002618" value="120.00" />
                      </td>
                      <td id="diop1004002618">&nbsp;</td>
                      <td>%</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002619" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">视在功率上上限</td>
                      <td>
                        <input id="1004002619" value="60.0000" />
                      </td>
                      <td id="diop1004002619">&nbsp;</td>
                      <td>kVA</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002620" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">视在功率超上上限越限持续时间</td>
                      <td>
                        <input id="1004002620" value="10" />
                      </td>
                      <td id="diop1004002620">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002621" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">视在功率超上上限越限恢复系数</td>
                      <td>
                        <input id="1004002621" value="150.00" />
                      </td>
                      <td id="diop1004002621">&nbsp;</td>
                      <td>%</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002622" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">视在功率上限</td>
                      <td>
                        <input id="1004002622" value="50.0000" />
                      </td>
                      <td id="diop1004002622">&nbsp;</td>
                      <td>kVA</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002623" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">视在功率超上限越限持续时间</td>
                      <td>
                        <input id="1004002623" value="10" />
                      </td>
                      <td id="diop1004002623">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002624" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">视在功率超上限越限恢复系数</td>
                      <td>
                        <input id="1004002624" value="120.00" />
                      </td>
                      <td id="diop1004002624">&nbsp;</td>
                      <td>%</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002625" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">三相电压不平衡限值</td>
                      <td>
                        <input id="1004002625" value="120.00" />
                      </td>
                      <td id="diop1004002625">&nbsp;</td>
                      <td>%</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002626" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">三相电压不平衡越限持续时间</td>
                      <td>
                        <input id="1004002626" value="10" />
                      </td>
                      <td id="diop1004002626">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002627" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">三相电压不平衡越限恢复系数</td>
                      <td>
                        <input id="1004002627" value="120.00" />
                      </td>
                      <td id="diop1004002627">&nbsp;</td>
                      <td>%</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002628" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">三相电流不平衡限值</td>
                      <td>
                        <input id="1004002628" value="120.00" />
                      </td>
                      <td id="diop1004002628">&nbsp;</td>
                      <td>%</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002629" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">三相电流不平衡越限持续时间</td>
                      <td>
                        <input id="1004002629" value="10" />
                      </td>
                      <td id="diop1004002629">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002630" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">三相电流不平衡越限恢复系数</td>
                      <td>
                        <input id="1004002630" value="120.00" />
                      </td>
                      <td id="diop1004002630">&nbsp;</td>
                      <td>%</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040026" di="1004002631" class="clz10040026" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">连续失压时间限值</td>
                      <td>
                        <input id="1004002631" value="20" />
                      </td>
                      <td id="diop1004002631">&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040026 测量点限值参数 -->
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </ul>
      <ul class=disNone id=datamenu_Con_2>
        <div id="bg2">
          <ul>
            <li id="bSetup" onclick="setup(4)">设　置</li>
            <li id="bRead" onclick="read(4)">读　取</li>
          </ul>
          <div style="float: right; padding-right: 10px;">
            <select id="agSn" name="agSn">
              <option value="1">第1路直流模拟量</option>
              <option value="2">第2路直流模拟量</option>
              <option value="3">第3路直流模拟量</option>
              <option value="4">第4路直流模拟量</option>
              <option value="5">第5路直流模拟量</option>
              <option value="6">第6路直流模拟量</option>
              <option value="7">第7路直流模拟量</option>
              <option value="8">第8路直流模拟量</option>
            </select>
          </div>
        </div>
        <div class="content">
          <div id="cont_3">
            <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-99));">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <thead>
                  <tr>
                    <th width="7%" height="30" class="bg01">
                      <input align="middle" type="checkbox" name="selectAll4" onclick="selectAll(this, 'itemId4')" />
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
                      <input align="middle" type="checkbox" name="itemId4" value="10040081" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040081');" />
                    </td>
                    <td>模拟量参数</td>
                    <td colspan="4">直流模拟量输入变比</td>
                    <td id="ciop10040081">&nbsp;</td>
                  </tr>
                  <!-- 10040081 直流模拟量输入变比 -->
                    <tr ci="10040081" di="1004008101" class="clz10040081" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">直流模拟量量程起始值</td>
                      <td>
                        <input id="1004008101" value="0" />
                      </td>
                      <td id="diop1004008101">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040081" di="1004008102" class="clz10040081" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">直流模拟量量程终止值</td>
                      <td>
                        <input id="1004008102" value="100" />
                      </td>
                      <td id="diop1004008102">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040081 直流模拟量输入变比 -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId4" value="10040082" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040082');" />
                    </td>
                    <td>模拟量参数</td>
                    <td colspan="4">直流模拟量限值</td>
                    <td id="ciop10040082">&nbsp;</td>
                  </tr>
                  <!-- 10040082 直流模拟量限值 -->
                    <tr ci="10040082" di="1004008201" class="clz10040082" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">直流模拟量上限</td>
                      <td>
                        <input id="1004008201" value="5" />
                      </td>
                      <td id="diop1004008201">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040082" di="1004008202" class="clz10040082" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">直流模拟量下限</td>
                      <td>
                        <input id="1004008202" value="50" />
                      </td>
                      <td id="diop1004008202">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040082 直流模拟量限值 -->
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId4" value="10040083" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040083');" />
                    </td>
                    <td>模拟量参数</td>
                    <td colspan="4">直流模拟量冻结参数</td>
                    <td id="ciop10040083">&nbsp;</td>
                  </tr>
                  <!-- 10040083 直流模拟量冻结参数 -->
                    <tr ci="10040083" di="1004008301" class="clz10040083" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">直流模拟量冻结密度</td>
                      <td>
                        <select id="1004008301">
                          <option value="0">不冻结</option>
                          <option value="1">15分钟</option>
                          <option value="2">30分钟</option>
                          <option value="3">60分钟</option>
                          <option value="254">5分钟</option>
                          <option value="255">1分钟</option>
                        </select>
                      </td>
                      <td id="diop1004008301">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040083 直流模拟量冻结参数 -->
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