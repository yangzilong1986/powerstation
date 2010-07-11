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
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/json2.js"></script>
<script type="text/javascript">
function StringBuffer(){
    this.data = [];
}

StringBuffer.prototype.append = function(){
    this.data.push(arguments[0]);
    return this;
}

StringBuffer.prototype.toString = function(){
    return this.data.join("");
}

$(function(){
    /*$('#test').datagrid({
        width: $(window).width(),
        height:$(window).height()-95,
        nowrap: false,
        striped: true,
        url: '',
        sortName: 'code',
        sortOrder: 'desc',
        idField: 'code',
        frozenColumns:[[
            {field:'ck',checkbox:true},
            {field:'type',title:'参数类型 ',width:100},
            {field:'code',title:'命令项标识',width:100}
        ]],
        columns:[[
            {field:'name',title:'命令项名称 / 数据项名称',width:200},
            {field:'value',title:'待设值',width:100},
            {field:'lstValue',title:'上次设置值',width:100},
            {field:'unit',title:'单位',width:100},
            {field:'result',title:'操作结果',width:200}
        ]],
        pagination:false,
        rownumbers:false,
        singleSelect:false
    });*/
});

var people = {
        "programmers": [
            { "firstName": "Brett", "lastName":"McLaughlin", "email": "brett@newInstance.com" },
            { "firstName": "Jason", "lastName":"Hunter", "email": "jason@servlets.com" },
            { "firstName": "Elliotte", "lastName":"Harold", "email": "elharo@macfaq.com" }
        ],
        "authors": [
            { "firstName": "Isaac", "lastName": "Asimov", "genre": "science fiction" },
            { "firstName": "Tad", "lastName": "Williams", "genre": "fantasy" },
            { "firstName": "Frank", "lastName": "Peretti", "genre": "christian fiction" }
        ],
        "musicians": [
            { "firstName": "Eric", "lastName": "Clapton", "instrument": "guitar" },
            { "firstName": "Sergei", "lastName": "Rachmaninoff", "instrument": "piano" }
        ]
};

function getJSONString() {
    alert(JSON.stringify(people));
    //alert(escape(JSON.stringify(people)));
}

function test() {
    //alert($("tr_10040001*").length);
    //getJSONString();
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
    var cilist = "10040001,10040003,10040004";
    var ciarray = cilist.split(',');
    for(var i = 0; i < ciarray.length; i++) {
        //alert($("tr[ci='" + ciarray[i] + "']").length);
        var sb = new StringBuffer();
        for(var j = 0; j < $("tr[ci='" + ciarray[i] + "']").length; j++) {
            //alert($($("tr[ci='10040001']")[j]).attr("di"));
            sb.append(',"' + $($("tr[ci='" + ciarray[i] + "']")[j]).attr("di") + '":"' + $("#" + $($("tr[ci='" + ciarray[i] + "']")[j]).attr("di")).val() + '"');
        }
        alert(sb.toString().substring(1));
    }
    
    /*var url = '<pss:path type="webapp"/>/eparam/termparam/down';
    $.ajax({
        type: 'POST',
        //contentType: 'application/json',
        url: url,
        data: 'dto=' + escape(JSON.stringify(dto)),
        dataType: 'json',
        success: function(data) {
            //alert(msg);
        },
        error: function() {
        }
    });*/
}

function read() {
    
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
          <td width="120"><input id="logicalAddr" name="logicalAddr" class="input2" value="" style="width: 140px; height: 18px;"/></td>
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
            <li onclick="setup()">设　置</li>
            <li onclick="read()">读　取</li>
          </ul>
        </div>
        <div class="content">
          <div id="cont_1">
            <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-99));">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <thead>
                  <tr>
                    <th width="7%" height="30" class="bg01">
                      <input align="middle" type="checkbox" name="selectAll" checked="checked" />
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
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040001" di="1004000102" class="clz10040001" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">终端作为启动站允许发送传输延时时间</td>
                      <td><input id="1004000102" value="1" /></td>
                      <td>&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040001" di="1004000103" class="clz10040001" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">终端等待从动站响应的超时时间</td>
                      <td><input id="1004000103" value="10" /></td>
                      <td>&nbsp;</td>
                      <td>秒</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040001" di="1004000104" class="clz10040001" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">终端等待从动站响应的重发次数</td>
                      <td><input id="1004000104" value="5" /></td>
                      <td>&nbsp;</td>
                      <td>次</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040001" di="1004000106" class="clz10040001" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">需要主站确认的通信服务（CON=1）的标志</td>
                      <td><input id="1004000106" value="00100000" /></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040001" di="1004000107" class="clz10040001" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">心跳周期</td>
                      <td><input id="1004000107" value="30" /></td>
                      <td>&nbsp;</td>
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
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040003" di="1004000302" class="clz10040003" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">备用IP地址和端口</td>
                      <td><input id="1004000302" value="192.168.1.101:1024" /></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040003" di="1004000303" class="clz10040003" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">APN</td>
                      <td><input id="1004000303" value="0000000000000000" /></td>
                      <td>&nbsp;</td>
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
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040004" di="1004000402" class="clz10040004" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">短信中心号码</td>
                      <td><input id="1004000402" value="13800571000" /></td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040004 主站电话号码和短信中心号码 -->
                  <!-- 
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040007" checked="checked" />
                      <img align="middle" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040007');" />
                    </td>
                    <td>通信参数</td>
                    <td colspan="4">终端IP地址和端口</td>
                    <td id="ciop10040007">&nbsp;</td>
                  </tr>
                   -->
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
                      <td>&nbsp;</td>
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
                      <td>&nbsp;</td>
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
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040008" di="1004000806" class="clz10040008" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">客户机模式下永久在线、时段在线模式重拨间隔</td>
                      <td>
                        <input id="1004000806" value="10" />
                      </td>
                      <td>&nbsp;</td>
                      <td>秒</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040008" di="1004000807" class="clz10040008" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">客户机模式下被动激活模式重拨次数</td>
                      <td>
                        <input id="1004000807" value="5" />
                      </td>
                      <td>&nbsp;</td>
                      <td>次</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040008" di="1004000808" class="clz10040008" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">客户机模式下被动激活模式连续无通信自动断线时间</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>min</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040008" di="1004000809" class="clz10040008" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">客户机模式下时段在线模式允许在线时段标志</td>
                      <td>
                        <input id="1004000809" value="000000000000000000000000" />
                      </td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  <!-- /10040008 终端上行通信工作方式（以太专网或虚拟专网） -->
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
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr ci="10040009" di="1004000902" class="clz10040009" style="display: none;">
                      <td height="25">&nbsp;</td>
                      <td colspan="2" align="right">事件重要性等级标志位</td>
                      <td>
                        <input id="1004000902" value="0000000000000000000000000000000000000000000000000000000000000000" />
                      </td>
                      <td>&nbsp;</td>
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
                  <tr class="cicontent">
                    <td height="25">
                      <input align="middle" type="checkbox" name="itemId" value="10040005" checked="checked" />
                      <img align="middle" lang="plus" src="<pss:path type="bgcolor"/>/img/plus1.gif" style="cursor: pointer;" onclick="folder(this, '10040005');" />
                    </td>
                    <td>终端属性</td>
                    <td colspan="4">终端上行通信消息认证参数设置</td>
                    <td id="ciop10040005">&nbsp;</td>
                  </tr>
                   -->
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