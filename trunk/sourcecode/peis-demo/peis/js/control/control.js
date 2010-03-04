var opwindow = null;
//对象全选 全清
function doClickAll() {
	var flag = document.getElementById("chooseAll").checked;
	var items = document.getElementsByName("cb-item");
	for (i = 0; i < items.length; i++) {
		items[i].checked = flag;
	}
}

//获得选中的对象
function getChoose() {
	var chooseList = "";
	var items = document.getElementsByName("cb-item");
	for (i = 0; i < items.length; i++) {
		if (items[i].checked) {
			chooseList += "," + items[i].value;
		}
	}
	return chooseList;
}

//按钮提交
function doSubmit(obj) {
    document.getElementById('button_div').style.display='none';
    var info1 = '请先选择要操作的对象！';
    var info2 = '遥控参数';
    var info3 = '限电时间';
    var info4 = '开关轮次';
    var info5 = '告警延时';
    var info6 = '是否重新设定？';
    var info7 = '选择‘确定’重新设定，选择‘取消’直接进行控制';
	var chooseList = getChoose();
	if (chooseList == "") {
		alert(info1);
	} 
	else {
	    if (obj == "remote") {
	       var exp = document.getElementsByName("exp_select")[0];
	       var expValue = exp.item(exp.selectedIndex).text;
	       var turn = document.getElementsByName("turn_select")[0];
	       var turnValue = turn.item(turn.selectedIndex).text;
	       var lapse = document.getElementsByName("lapse_select")[0];
	       var lapseValue = lapse.item(lapse.selectedIndex).text;
	       var info = info2+"：\n";
	       info += "\n";
	       info += info3+"："+expValue+"\n";
	       info += info4+"："+turnValue+"\n";
	       info += info5+"："+lapseValue+"\n";
	       info += "\n";
	       info += info6+"\n"+info7;
	       var conRes = window.confirm(info);
	       if(conRes){
	           var remote_Param = document.getElementById("remoteParam");
		       remote_Param.style.display = "";
		       remote_Param.style.left = (screen.availWidth - 600) / 2;
		       remote_Param.style.top = (screen.availHeight) / 4;
		       document.getElementById("chooseValue").value = chooseList;
	       }
	       else {
	       	   document.getElementById("chooseValue").value = chooseList;
		       document.getElementById("method").value = obj;
		       document.getElementById("turn").value = document.getElementsByName("turn_select")[0].value;
		       document.getElementById("lapse").value = document.getElementsByName("lapse_select")[0].value;
		       document.getElementById("expTime").value = document.getElementsByName("exp_select")[0].value;
		       var my_tips = document.getElementById("mytips");
		   	   my_tips.style.display = "";
		       my_tips.style.left = (screen.availWidth - 121) / 2;
		       my_tips.style.top = (screen.availHeight) / 4;
	       }
	    }
	    else if (obj == "keep") {
	    	var keep_Param = document.getElementById("keepParam");
		    keep_Param.style.display = "";
		    keep_Param.style.left = (screen.availWidth - 600) / 2;
		    keep_Param.style.top = (screen.availHeight) / 4;
		    document.getElementById("chooseValue").value = chooseList;
	    }
	    else {
	        document.getElementById("chooseValue").value = chooseList;
		    document.getElementById("method").value = obj;
		    document.getElementById("turn").value = document
				.getElementsByName("turn_select")[0].value;
		    document.getElementById("lapse").value = document
				.getElementsByName("lapse_select")[0].value;
		    document.getElementById("expTime").value = document
				.getElementsByName("exp_select")[0].value;
		    var my_tips = document.getElementById("mytips");
		    my_tips.style.display = "";
		    my_tips.style.left = (screen.availWidth - 240) / 2;
		    my_tips.style.top = (screen.availHeight) / 4;
	    }
	}
}

//设置遥控参数并且提交
function setParamAndSubmit() {
	document.getElementById('remoteParam').style.display='none';
    document.getElementById("method").value = "remote";
	document.getElementById("turn").value = document.getElementsByName("turn_select")[0].value;
	document.getElementById("lapse").value = document.getElementsByName("lapse_select")[0].value;
	document.getElementById("expTime").value = document.getElementsByName("exp_select")[0].value;
	var my_tips = document.getElementById("mytips");
	my_tips.style.display = "";
	my_tips.style.left = (screen.availWidth - 240) / 2;
	my_tips.style.top = (screen.availHeight) / 4;
}
 
//设置保电参数并且提交
function setKeepParamAndSubmit() {
	document.getElementById('keepParam').style.display = 'none';
    document.getElementById("method").value = "keep";
	document.getElementById("keepTime").value = document.getElementsByName("keepTime_select")[0].value;
	var my_tips = document.getElementById("mytips");
	my_tips.style.display = "";
	my_tips.style.left = (screen.availWidth - 160) / 2;
	my_tips.style.top = (screen.availHeight) / 4;
}
 
function showButton() {
    var butt_div = document.getElementById("button_div");
    butt_div.style.display = "";
    butt_div.style.left = event.clientX - 45;
    butt_div.style.top = event.clientY + 12;
}
 
function queryByObject(logic_addrs) {
    var url = contextPath+"/do/control/remoteAction.do?action=remoteQueryByAddr&logic_addrs="+logic_addrs;
    window.location.href = url;
}
 
//小圈轮召启动后的应用ID
var turnAppId1;
var turnAppId2;
 
//起动小圈轮召
function startTurn(){
    var info1 = '请先选择要操作的对象！';
    var logic_addr = getChoose();
    if(logic_addr != "") {
		turnFlag = true;
		var conRes = window.confirm("是否确定要启动小圈轮召!");
		if(!conRes){
			return;
		}
		//设置小圈轮召按钮不可用
		document.getElementById("startBt").disabled = true;
        logic_addr = logic_addr.substring(1);
        var params = {"logic_addr": logic_addr
				     };
		//var url = contextPath + "/do/control/remoteAction.do?action=startTurnCycle&" + jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
		//$.get(url, function(data) {
		//	if(data == null) {
				//设置小圈轮召按钮可用
		//		document.getElementById("startBt").disabled = false;
		//	}
		//	var result = data.split(",");
			//alert("result=" + result[0] + "," + result[1] + "," + result[2]);
		//	if (result.length != 3) {
				//alert("result.length != 3");
				//设置小圈轮召按钮可用
		//		document.getElementById("startBt").disabled = false;
		//		return;
		//	}
		//	if ("success" == result[2]) {
		//		turnAppId1 = result[0];
		//		turnAppId2 = result[1];
				//初始化信息
		//		initAllText();
		//		fetch(logic_addr);
		//	}
		//	else {
				alert("小圈轮召启动失败!");
				document.getElementById("startBt").disabled = false;
		//		//设置小圈轮召按钮可用
		///		document.getElementById("startBt").disabled = false;
		//	}
		//});
   // }
   // else {
   // 	alert(info1);
   // }
    }
}
 
// 获得所有逻辑地址
function getAllLogicAddrString() {
    var sResult = "";
    try {
        if(typeof document.all.ItemID != "undefined" && typeof document.all.ItemID.length == "undefined") {
            sResult += "," + document.all.ItemID.value + "";
        }
        else if(typeof document.all.ItemID != "undefined" && typeof document.all.ItemID.length != "undefined") {
            for(var i = 0; i < document.all.ItemID.length; i++) {
                sResult += "," + document.all.ItemID[i].value + "";
            }
        }
        if(sResult.indexOf(",") != -1) {
            sResult = sResult.substring(1);
        }
    }
    catch(e) {
    }
    return sResult;
}
 
//设置负荷信息
function setResultInfo(selectedItemList, rInfo) {
    var vItem = selectedItemList.split(',');
    for(var i = 0; i < vItem.length; i++) {
        if(vItem[i].length > 0) {
            document.getElementById(vItem[i] + "_before").innerHTML = rInfo;
            document.getElementById(vItem[i] + "_now").innerHTML = rInfo;
            document.getElementById(vItem[i] + "_time").innerHTML = rInfo;
            document.getElementById(vItem[i] + "_down").innerHTML = rInfo;
        }
    }
}
 
//初始化所有当前读取的信息
function initAllText(){
	var allLogicAddr = getAllLogicAddrString();
	setResultInfo(allLogicAddr, '');
	document.getElementById("power_before").innerHTML = "0";
	document.getElementById("power_now").innerHTML = "0";
	document.getElementById("downPower").innerHTML = "0";
}
 
//停止小圈轮召
function stopTurn(){
	//设置小圈轮召标示为False, 停止刷新数据
	turnFlag = false;
	var logic_addr = getChoose();
	if(logic_addr != "") {
		logic_addr = logic_addr.substring(1);
	}
	
	if (turnAppId1 == null || turnAppId1 == "" || turnAppId2 == null || turnAppId2 == "") {
		alert("小圈轮召已经停止!");
		return;
	}
	
	var conRes = window.confirm("是否确定要停止小圈轮召!");
	if(!conRes){
		return;
	}
	//设置停止小圈轮召按钮不可用
	document.getElementById("stopBt").disabled = true;
	var params = {"turnAppId1": turnAppId1,
				  "turnAppId2": turnAppId2,
				  "logic_addr": logic_addr
				 };
	var url = contextPath + "/do/control/remoteAction.do?action=stopTurnCycle&" + jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
	$.get(url, function(data) {
		turnAppId1 = null;
		turnAppId2 = null;
		//设置停止小圈轮召按钮可用
		document.getElementById("stopBt").disabled = false;
		//设置小圈轮召按钮可用
		document.getElementById("startBt").disabled = false;
	});
}
//对象全选
function chooseAllItem() {
	var flag = document.getElementById("chooseAll").checked;
	var items = document.getElementsByName("cb-item");
	for (i = 0; i < items.length; i++) {
		items[i].checked = flag;
	}
}
//获得选择结果，将结果写入页面的chooseValue域 并且返回选择个数
function addAllItems() {
	var items = document.getElementsByName("cb-item");
	var values = "";
	var chooseNum = 0;
	for (i = 0; i < items.length; i++) {
		if (items[i].checked) {
			values += "," + items[i].value;
			chooseNum++;
		}
	}
	document.getElementById("chooseValue").value = values;
	return chooseNum;
}

//打开窗口
function openNewWindow(url, width, ht) {
	if (opwindow != null) {
		opwindow.close();
	}
	feature = 'width=' + width + ',height=' + ht
			+ ',menubar=no,toolbar=no,location=no,';
	feature += 'top=' + (screen.availHeight - ht) / 2 + ', left='
			+ (screen.availWidth - width) / 2
			+ 'scrollbars=auto,status=no,modal=yes';
	opwindow = window.open(url, '', feature);
}

//权限验证
function validate() {
	//var pas = document.getElementById("password").value;
	//var method = document.getElementById("method").value;
	//document.getElementById("password").value = "";
	//document.getElementById("method").value = "";
	//document.getElementById("mytips").style.display = "none";
	//var str_url = contextPath + "/do/control/remoteAction.do";
	//str_url = str_url + "?action=controlVali";
	//str_url = str_url + "&password=" + pas + "&method=" + method;
	//var callback = validateProcessAjaxResponse;
	//executeXhr(callback, str_url);
}

//权限验证ajax
function validateProcessAjaxResponse() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			eval(req.responseText);
		} else {

		}
	}
}
//控制提交
function remote(type) {
	var actionUrl = contextPath + "/do/control/remoteAction.do?action=" + type;
	document.forms[1].action = actionUrl;
	document.forms[1].submit();
}

//小圈轮召标志False: 小圈轮召停止， True: 小圈轮召起动
var turnFlag = false;

//负荷刷新
function fetch(logic_addr) {
	//alert("turnFlag=" + turnFlag);
	//停止小圈轮召
	if (turnFlag == false) {
		return;
	}
	var chooseValue = document.getElementById("chooseValue").value;
	var flush = "no";
	if (chooseValue == "") {
		flush = "yes";
	}
	var str_url = contextPath + "/do/control/remoteAction.do";
	str_url = str_url + "?action=flushPower";
	str_url = str_url + "&flush=" + flush + "&logic_addr="+logic_addr;
	var callback = fetchProcessAjaxResponse;
	executeXhr2(callback, str_url);
}

function fetchProcessAjaxResponse() {
	if (req2.readyState == 4) {
		if (req2.status == 200) {
			eval(req2.responseText);
		} else {

		}
	}
}
//遥信
function remoteSensing(logic_addr) {
	document.getElementById("click_logic").value = logic_addr;
	document.getElementById("method").value = "sensing";
	//var my_tips = document.getElementById("mytips");
	//my_tips.style.display = "";
	//my_tips.style.left = (screen.availWidth - 121) / 2;
	//my_tips.style.top = (screen.availHeight) / 4;
	sensing();
}
//显示遥信状态
function sensing() {
	//var logic_addr = document.getElementById("click_logic").value;
	//var turn = document.getElementsByName("turn_select")[0].value;
	//var lapse = document.getElementsByName("lapse_select")[0].value;
	//var exp = document.getElementsByName("exp_select")[0].value;
	//var url = contextPath
	//		+ "/do/control/remoteAction.do?action=sensing&logic_addr="
	//		+ logic_addr + "&turn=" + turn + "&lapse=" + lapse + "&exp=" + exp;
	//openNewWindow(url, 600, 400);
	var url="../../jsp/control/sensing.html";
	top.showDialogBox("遥信状态",url, 575, 900);
}