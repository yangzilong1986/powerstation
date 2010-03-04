//*********************专变用户档案录入验证***************************************************//
function custCheck() {
	if (""==$("input[name='custNo']").val()) {
		alert("户号不能为空！");
		return false;
	}
	if (""==$("input[name='custName']").val()) {
		alert("户名不能为空！");
		return false;
	}
	var doublePatrn = /^([1]|[0]|([0]\.\d*))$/;
	var integerPatrn = /^[1-9]\d*$/;
	var number = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
	var strContractCapa = $("input[name='contractCapa']").val();
	if (strContractCapa != "" && !number.exec(strContractCapa)) {
		alert("合同容量" + "不是数字！");
		return false;
	}
	var strRunCapa = $("input[name='runCapa']").val();
	if (strRunCapa != "" && !number.exec(strRunCapa)) {
		alert("运行容量" + "不是数字！");
		return false;
	}
	var strSecuCapa = $("input[name='secuCapa']").val();
	if (strSecuCapa != "" && !number.exec(strSecuCapa)) {
		alert("保安容量" + "不是数字！");
		return false;
	}
	var strPowerFactor = $("input[name='powerFactor']").val();
	if (strPowerFactor != "" && !number.exec(strPowerFactor)) {
		alert("功率因数" + "不是整型！");
		return false;
	}
	var strRmDay = $("input[name='rmDay']").val();
	if (strRmDay != "") {
		var intRmDay = parseInt(strRmDay);
		if (!intRmDay || intRmDay < 0 || intRmDay > 28) {
			alert("抄表日必须是在0～28之间的整数！");
			return false;
		}
	}
	return true;
}
//*********************电表信息录入验证***************************************************//
function meterCheck() {
	if ("" == $("input[name='GP_ID']").val()) {
		alert("表序号不能为空");
		return false;
	}
	if ("" == $("input[name='assetNo']").val()) {
		alert("电表资产编号不能为空");
		return false;
	}
	if ("" == $("input[name='gpAddr']").val()) {
		alert("表地址不能为空！");
		return false;
	}
	if ("" == $("input[name='port']").val()) {
		alert("通讯端口不能为空！");
		return false;
	}
	if ("" == $("input[name='gpSn485']").val()) {
		alert("485计量点序号不能为空！");
		return false;
	}
	if($('#isPulse').attr("checked")==true){
		if ("" == $("input[name='gpSnPluse']").val()) {
			alert("脉冲计量点序号不能为空！");
			return false;
		}	
	}
	return true;
}
//*********************远传表信息录入验证***************************************************//
function remoteMeterCheck() {
	if ("" == $("input[name='assetNo']").val()) {
		alert("电表资产编号不能为空");
		return false;
	}
	if ("" == $("input[name='gpAddr']").val()) {
		alert("表地址不能为空！");
		return false;
	}
	if ("" == $("input[name='port']").val()) {
		alert("通讯端口不能为空！");
		return false;
	}
	if ("" == $("input[name='logicAddr']").val()) {
		alert("终端逻辑地址不能为空！");
		return false;
	}
	return true;
}
//*********************终端信息录入验证***************************************************//
function terminalCheck(){
	if ("" == $("input[name='assetNo']").val()) {
		alert("资产编号不能为空");
		return false;
	}
	if ("" == $("input[name='logicalAddr']").val()) {
		alert("逻辑地址不能为空");
		return false;
	}
	return true;
}
//********************配变/台区信息录入验证********************************************//
function tgCheck(){
	if ("" == $("input[name='tgNo']").val()) {
		alert("台区号不能为空");
		return false;
	}
	if ("" == $("input[name='tgName']").val()) {
		alert("台区名不能为空");
		return false;
	}
	var number = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
	var tgCapa = $("input[name='tgCapa']").val();
	if (tgCapa != "" && !number.exec(tgCapa)) {
		alert("容量" + "不是整型！");
		return false;
	}
	return true;
}
//*******************变压器信息验证****************************************************************//
function tranCheck(){
	if ("" == $("input[name='assetNo']").val()) {
		alert("资产编号不能为空");
		return false;
	}
	if ("" == $("input[name='tranName']").val()) {
		alert("变压器名称不能为空");
		return false;
	}
	var number = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
	var tgCapa = $("input[name='tgCapa']").val();
	if (tgCapa != "" && !number.exec(tgCapa)) {
		alert("容量" + "不是整型！");
		return false;
	}
	return true;
}
//*******************总表录入验证****************************************************************//
function totalMeterCheck(){
	if ("" == $("input[name='gpSn']").val()) {
		alert("计量点序号不能为空");
		return false;
	}
	if ("" == $("input[name='mpName']").val()) {
		alert("计量点名称不能为空");
		return false;
	}
	if ("" == $("input[name='assetNo']").val()) {
		alert("资产编号不能为空");
		return false;
	}
	return true;
}
//*******************线路信息验证****************************************************************//
function lineCheck(){
	if ("" == $("input[name='lineNo']").val()) {
		alert("线路编号不能为空");
		return false;
	}
	if ("" == $("input[name='lineName']").val()) {
		alert("线路名称不能为空");
		return false;
	}
	if ("" == $("input[name='lineThreshold']").val()) {
		alert("线损阈值不能为空");
		return false;
	}
	return true;
}
//*******************集中器信息验证****************************************************************//
function modelTermCheck(){
    if ("" == $("input[name='assetNoMT']").val()) {
		alert("资产编号不能为空");
		return false;
	}
	if ("" == $("input[name='logicalAddrMT']").val()) {
		alert("逻辑地址不能为空");
		return false;
	}
	return true;
}
//*******************采集器信息验证****************************************************************//
function GMCheck(){
    if ("" == $("input[name='assetNoGM']").val()) {
		alert("资产编号不能为空");
		return false;
	}
	if ("" == $("input[name='logicalAddrGM']").val()) {
		alert("采集器地址不能为空");
		return false;
	}
	return true;
}
//*******************变电站信息验证****************************************************************//
function subCheck(){
	if ("" == $("input[name='SUBS_NO']").val()) {
		alert("变电站编号不能为空");
		return false;
	}
	if ("" == $("input[name='SUBS_NAME']").val()) {
		alert("变电站名称不能为空");
		return false;
	}
	return true;
}
//*******************电厂信息验证****************************************************************//
function elecCheck(){
	if ("" == $("input[name='ELEC_NO']").val()) {
		alert("电厂编号不能为空");
		return false;
	}
	if ("" == $("input[name='ELEC_NAME']").val()) {
		alert("电厂名称不能为空");
		return false;
	}
	return true;
}
//*******************开关站信息验证****************************************************************//
function swCheck(){
	if ("" == $("input[name='SW_NO']").val()) {
		alert("开关编号不能为空");
		return false;
	}
	if ("" == $("input[name='SW_NAME']").val()) {
		alert("开关名称不能为空");
		return false;
	}
	return true;
}
//*******************母线信息验证****************************************************************//
function mlCheck(){
	if ("" == $("input[name='ML_NO']").val()) {
		alert("母线编号不能为空");
		return false;
	}
	if ("" == $("input[name='ML_NAME']").val()) {
		alert("母线名称不能为空");
		return false;
	}
	return true;
}
//*******************电厂(变压器)信息验证****************************************************************//
function tranDeCheck(){
	if ("" == $("input[name='ASSET_NO']").val()) {
		alert("变压器编号不能为空");
		return false;
	}
	if ("" == $("input[name='TRAN_NAME']").val()) {
		alert("变压器名称不能为空");
		return false;
	}
	var number = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
	var tgCapa = $("input[name='tgCapa']").val();
	if (tgCapa != "" && !number.exec(tgCapa)) {
		alert("容量" + "不是整型！");
		return false;
	}
	return true;
}