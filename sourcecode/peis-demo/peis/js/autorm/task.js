
var baseTimeGw;
var extCntGw;
var startTimeMaster;
var priMaster;
var dataItems = "";
var dataTypeChoosedId = "";

// 改变任务类型
function changeTaskType() {
	var task_type = document.getElementsByName("taskType");
	var upLable = document.getElementById("upLable");
	var upDom = document.getElementById("upDom");
	var execLable = document.getElementById("execLable");
	var execDom = document.getElementById("execDom");
	if (task_type[0].checked == true)// 自动上传任务
	{
		upLable.style.display = "";
		upDom.style.display = "";
		execLable.style.display = "none";
		execDom.style.display = "none";
		if (document.all.action.value == "taskAdd" ) {
			document.all.task_id.value = task_id1;
		}
		document.all.excluSettingAuto.style.display = "";
		document.all.excluSettingMaster.style.display = "none";
		document.getElementById("taskType_hidden").value = "1"
	}
	if (task_type[1].checked == true)// 主站轮招任务
	{
		upLable.style.display = "none";
		upDom.style.display = "none";
		execLable.style.display = "";
		execDom.style.display = "";
		if (document.all.action.value == "taskAdd" ) {
			document.all.task_id.value = task_id2;
		}
		document.all.excluSettingAuto.style.display = "none";
		document.all.excluSettingMaster.style.display = "";
		document.getElementById("taskType_hidden").value = "2";
	}

}
//提交前的验证
function validator() {
	var task_id = document.getElementById("task_id").value;
	var taskName = document.getElementById("taskName").value;
	var task_type = document.getElementsByName("taskType");
	var sendupCycleGw = document.getElementById("sendupCycleGw").value;
	var execCycleMaster = document.getElementById("execCycleMaster").value;
	var extCntGw = document.getElementById("extCntGw").value;
    var action = document.getElementById("action");
    var dataItems_codes = document.getElementById("dataItems_codes").value;
	if (action.value == "taskAdd"
			|| action.value == "taskMod") {
		if (task_id == "") {
            alert(idError);
            return false;
		}
		if (taskName == "") {
            alert(nameError);
            return false;
		}
		
	
		if (task_type[0].checked == true)// 自动上传任务
	    {
	    
	    if (sendupCycleGw == "") {
            alert(sendExecError);
            return false;
		}
	      if (extCntGw == "") {
            alert(cqblError);
            return false;
		}
		
	    }
	    
	    if (task_type[1].checked == true)// 主站轮招任务
	   {
	   
	   	if (execCycleMaster == "") {
           alert(execExecError);
            return false;
		}
	   
	   }
		
		if (dataItems_codes == "") {
            alert(sjxError);
            return false;
		}
		if(taskName.length > 100)
		{
			alert(taskNameLength);
			return false;
		}
		
		
	}
	if (action.value == "taskMod"){
		if (window.confirm(modifInfo)) {
			return true;
		}else{
			return false;
		}
	}
	return true;
}

// 初始化页面信息function init() {
	// 获得数据对象
	//task_id1 = document.getElementById("task_id").value;
	var action = document.getElementById("action").value;
	var protocol_type = document.getElementsByName("protocol_type")[0];
	var task_type = document.getElementsByName("taskType");
	var task_id = document.getElementById("task_id");
	var task_name = document.getElementById("taskName");
	var sendup_cycle_gw = document.getElementById("sendupCycleGw");
	var audit_flag = document.getElementsByName("auditFlag");
	var gp_char = document.getElementsByName("gpChar")[0];
	var data_type = document.getElementsByName("dataType")[0];
	var exec_cyle_master = document.getElementById("execCycleMaster");
	var exec_unit_master = document.getElementsByName("execUnitMaster")[0];
	var sendupUnitGw = document.getElementsByName("sendupUnitGw")[0];
	// 获得按钮对象
	var add = document.getElementById("new");
	var save = document.getElementById("save");
	var modif = document.getElementById("modif");
	var del = document.getElementById("del");
	var copy = document.getElementById("copy");
	var excluSetting = document.getElementById("excluSetting");
	var chooseDataItem = document.getElementById("chooseDataItem");
	if(document.getElementById("flush").value == "flush"){
		parent.flushList();
	}
	document.getElementById("protocol_type_hidden").value = protocol_type.value;
	document.getElementById("task_id_hidden").value = task_id.value; 
	changeTaskType();
	if (action == "taskShow" && task_name.value == "")// 默认页面除新增按钮外 其余全部不可用	{
		save.disabled = true;
		modif.disabled = true;
		del.disabled = true;
		copy.disabled = true;
		excluSetting.disabled = true;
		chooseDataItem.disabled = true;
		protocol_type.disabled = true;
		task_type[0].disabled = true;
		task_type[1].disabled = true;
		task_id.disabled = true;
		task_name.disabled = true;
		sendup_cycle_gw.disabled = true;
		audit_flag[0].disabled = true;
		audit_flag[1].disabled = true;
		gp_char.disabled = true;
		data_type.disabled = true;
		exec_cyle_master.disabled = true;
		exec_unit_master.disabled = true;
		sendupUnitGw.disabled = true;
	} else if (action == "taskShow" && task_name.value != "")// 任务信息显示页面 按钮可用
	// 内容只读
	{
		excluSetting.disabled = true;
		chooseDataItem.disabled = true;
		protocol_type.disabled = true;
		task_type[0].disabled = true;
		task_type[1].disabled = true;
		task_id.disabled = true;
		task_name.disabled = true;
		sendup_cycle_gw.disabled = true;
		audit_flag[0].disabled = true;
		audit_flag[1].disabled = true;
		gp_char.disabled = true;
		data_type.disabled = true;
		exec_cyle_master.disabled = true;
		exec_unit_master.disabled = true;
		save.disabled = true;
		sendupUnitGw.disabled = true;
	} else if (action == "taskAdd")// 任务信息添加页面 保存按钮和内容区域可写 其余只读
	{
		add.disabled = true;
		modif.disabled = true;
		del.disabled = true;
		copy.disabled = true;
	}
	initDataItems_codes();
}


// 显示高级设置界面
function excluSettingShow() {

	var my_tips = document.getElementById("mytips");
	my_tips.style.display = "";
	my_tips.style.width = 250;
    //my_tips.style.height = 100;
    changeTaskType();
	//my_tips.style.left=event.clientX+10+document.body.scrollRight-200;
	my_tips.style.top=event.clientY+10+document.body.scrollTop;
	my_tips.style.left = event.clientX - 300;
	my_tips.style.top = event.clientY-180;
	baseTimeGw = document.getElementById("baseTimeGw");
	extCntGw = document.getElementById("extCntGw");
	startTimeMaster = document.getElementById("startTimeMaster");
    priMaster = document.getElementById("priMaster");
}

// 确定设置
function setting() {
	var my_tips = document.getElementById("mytips");
	my_tips.style.display = "none";
}

// 取消设置
function cancel() {
	var my_tips = document.getElementById("mytips");
	my_tips.style.display = "none";
}

// 任务删除
function doDel() {
	if (window.confirm(delectInfo)) {
		document.all.action.value = "taskDel";
		document.forms[0].submit();
	}
}



// 数据项设定结果function initDataItems_codes() {
	var codes = "";
	var dataItemTable = document.getElementById("dataItem");
	var dataItems_codes = document.getElementById("dataItems_codes");
	for (i = 1; i < dataItemTable.rows.length; i++) {
		codes += dataItemTable.rows[i].cells[1].innerHTML + "@@";
	}
	dataItems_codes.value = codes;
}

// 初始化数据项选择页面
function dataItemInit() {
	var protocol_type = document.getElementById("protocol_type");
	var dataType = document.getElementById("dataType");
	protocol_type.disabled = true;
	dataType.disabled = true;
	var flag = true;
	var items = document.getElementsByName("choose");
	var tableObj = document.getElementById("dataItems");
	for (i = 0; i < items.length; i++) {
		var value = "@@" + tableObj.rows[i + 1].cells[1].innerHTML + "@@"
		if (choosedItems.lastIndexOf(value) != -1 && value != "@@@@") {
			items[i].checked = true;
		} else {
			flag = false;
		}
	}
	document.getElementsByName("chooseAllItem")[0].checked = flag;
}

// 数据项全选 全清
function chooseAll() {
	var flag = document.getElementsByName("chooseAllItem")[0].checked;
	var items = document.getElementsByName("choose");
	for (i = 0; i < items.length; i++) {
		items[i].checked = flag;
	}
}

// 数据项设定function setDataItem() {
	var dataitem_codes = "";
	var dataitem_names = "";
	var items = document.getElementsByName("choose");
	var tableObj = document.getElementById("dataItems");
	// window.opener.clearTable();
	//var dataItemTable = opener.document.all.dataItem;
    var dataItemTable = top.getMainFrameObj().taskoperate.document.all.dataItem;
	while (dataItemTable.rows.length > 1) {
		dataItemTable.deleteRow(1);
	}
	// 叠加选择的数据项信息
	index = 1;
	for (i = 0; i < items.length; i++) {
		// var value = "@@"+tableObj.rows[i+2].cells[2].innerHTML+"@@"
		if (items[i].checked) {
			// dataitem_codes += tableObj.rows[i+2].cells[1].innerHTML + "@@";
			// dataitem_names += tableObj.rows[i+2].cells[2].innerHTML + "@@"
			// 将选择的数据项设置到前台显示
			var tr = dataItemTable.insertRow();
			tr.className = "trmainstyle";
			tr.insertCell().innerHTML = index++;
			tr.insertCell().innerHTML = tableObj.rows[i + 1].cells[1].innerHTML;
			tr.insertCell().innerHTML = tableObj.rows[i + 1].cells[2].innerHTML;
		}
	}
	//opener.initDataItems_codes();
	top.getMainFrameObj().taskoperate.initDataItems_codes();
	//this.window.close();
    top.getMainFrameObj().taskoperate.setTableStyle();
	parent.GB_hide();
}

//改变数据类型以后产生的连动效果
function changeDataType()
{
	var dataItemTable = document.all.dataItem;
	var dataItems_codes = document.getElementById("dataItems_codes");
	while (dataItemTable.rows.length > 1) {
		dataItemTable.deleteRow(1);
	}
	dataItems_codes.value = "";
}



