var Model = new Object();
/**
 * 
 * @param modelType
 *            模型类型
 * @param objectType
 *            对象类型
 * @param objectId
 *            对象id -1表示为该对象类型下面全部对象
 * @param objectName
 *            对象名称
 * @param extend
 */
Model = function(modelType, objectType, objectId, objectName, extend) {
	if (typeof modelType == undefined) {
		return;
	}
	if (typeof objectType == undefined) {
		return;
	}

	if (typeof objectId == undefined) {
		return;
	}
	this.modelType = modelType;
	this.objectType = objectType;
	this.objectId = objectId;
	this.objectName = objectName;
	this.extend = extend ? extend : "";
}
Model.prototype = {
	_setUrl : function() {
		var url = "?";
		var paras = {
			modelType :this.modelType,
			objectType :this.objectType,
			objectId :this.objectId,
			objectName :this.objectName
		}
		url += $.param(paras);
		return url;

	}
}
var frm = Model;
/**
 * 时间类型切换
 */
function meterDTChange() {
	var dateType = $('#dateType').val();
	if (dateType == "1") {// 日
		$('#DATA_DAY_START').show();
		$('#DATA_DAY_END').hide();
		$('#DATA_YEAR').hide();
		$('#DATA_MONTH').hide();
	} else if (dateType == "2") {// 月
		$('#DATA_DAY_START').hide();
		$('#DATA_DAY_END').hide();
		$('#DATA_YEAR').show();
		$('#DATA_MONTH').show();
	} else if (dateType == "3") {
		$('#DATA_DAY_START').hide();
		$('#DATA_DAY_END').hide();
		$('#DATA_YEAR').show();
		$('#DATA_MONTH').hide();
	} else if (dateType == "4") {
		$('#DATA_DAY_START').show();
		$('#DATA_DAY_END').show();
		$('#DATA_YEAR').hide();
		$('#DATA_MONTH').hide();
	}
}
/**
 * 模型重算
 */
calculate_model = function() {
	if (confirm('确认线损重算？')) {
		popTop(1, "模型组成", "../../jsp/lineloss/lineloss_model_calculate.html?dateType=1", 320, 255)
	}
}
/**
 * 加载模型
 */
load_model = function() {
	if ($.url.param('modelType')) {
		var xmlpath = '../../jsp/lineloss/xml/lineloss_model_list_' + $.url.param('modelType') + '.xml';
		getRenderData($.url, xmlpath, 'cb-item')._init_data();
	}
}
/**
 * 计量点信息
 */
load_model_gp = function() {
	if ($.url.param('modelType')) {
		var io = null;
		if ($.url.param('io') != undefined) {
			io = $.url.param('io');
		}
		var xmlpath = "";
		if (io == null) {
			xmlpath = '../../jsp/lineloss/xml/lineloss_model_gp_list_' + $.url.param('modelType') + '.xml';
		} else {
			xmlpath = '../../jsp/lineloss/xml/lineloss_model_gp_list_' + $.url.param('modelType') + '_'
					+ io + '.xml';
		}
		getRenderData($.url, xmlpath, 'cb-item')._init_data();
	    if($.url.param('modelType')=='switchgear'){
	    	$('#common').hide();
	        $('#tg').show();
	    }
	}
}
/**
 * 初始化
 */
init_model = function() {
	if (confirm('确认初始化模型？')) {
		if ($.url.param('modelType')) {
			var xmlpath = '../../jsp/lineloss/xml/lineloss_model_list_' + $.url.param('modelType') + '.xml';
			getRenderData($.url, xmlpath, 'cb-item')._init_data();
		}
	}
}
/**
 * 删除模型
 */
delete_model = function() {
	var checked = getSelectedCheckboxs();
	if (!checked) {
		alert('请选择要删除的模型');
		return;
	}
	var array = checked;
	try {
		array = checked.split(',')
	} catch (e) {
	}
	if (confirm('确认删除模型？')) {
		if ($.isArray(array)) {
			$.each(array, function(i, item) {
				$("#lineloss_transmit_model_table_tr_" + item).hide();
			})
		} else {
			$("#lineloss_transmit_model_table_tr_" + array).hide();
		}
	}
}

/**
 * 编辑模型
 */
edit_model = function() {
	var checked = getSelectedCheckboxs();
	if (!checked) {
		alert('请选择要编辑的模型');
		return;
	}
	if (checked.indexOf(',') != -1) {
		alert('请选择具体一个模型进行编辑！')
		return;
	}
	if (confirm('确认编辑模型？')) {
		popTop(1, "模型组成", "../../jsp/lineloss/lineloss_model_detail.html?modelType="
				+ $('#modelType').val(), 900, 575)
	}
}

/**
 * 添加模型计量点
 */
add_model_gp = function() {
	pop1(1, "添加计量点", "../../jsp/lineloss/lineloss_model_gp_add.html?modelType=" + $('#modelType').val(),
			700, 450)
}

/**
 * 添加模型计量点
 */
add_model_gp_list = function() {
	if ($.url.param('modelType')) {
		var xmlpath = '../../jsp/lineloss/xml/lineloss_model_gp_add_' + $.url.param('modelType') + '.xml';
		getRenderData($.url, xmlpath, 'cb-item')._init_data();
	}}
/**
 * 编辑模型计量点
 */
edit_model_gp = function() {
	var checked = getSelectedCheckboxs();
	if (!checked) {
		alert('请选择要编辑的计量点');
		return;
	}
	if (checked.indexOf(',') != -1) {
		alert('请选择具体一个计量点进行编辑！')
		return;
	}
	if (confirm('确认编辑计量点？')) {
		pop1(1, "编辑计量点", "../../jsp/lineloss/lineloss_model_gp_edit.html?modelType="
				+ $('#modelType').val(), 320, 255)
	}
}

/**
 * 删除模型计量点
 */
delete_model_gp = function() {
	var checked = getSelectedCheckboxs();
	if (!checked) {
		alert('请选择要删除的模型计量点');
		return;
	}
	var array = checked;
	try {
		array = checked.split(',')
	} catch (e) {
	}
	if (confirm('确认删除所选模型计量点？')) {
		if ($.isArray(array)) {
			$.each(array, function(i, item) {
				$("#lineloss_model_detail_table_tr_" + item).hide();
			})
		} else {
			$("#lineloss_model_detail_table_tr_" + array).hide();
		}
	}
}

popTop = function(obj, captation, path, w, h, para) {
	this.w = w ? w : 800;
	this.h = h ? h : 600;
	this.captation = captation ? captation : "详细信息";
	this.para = para ? para : new Object();
	try {
		top.showDialogBox(this.captation, path, this.h, this.w);
	} catch (e) {
		alert("popTop=function " + e.message);
	}
}
pop = function(obj, captation, path, w, h, para) {
	this.w = w ? w : 800;
	this.h = h ? h : 600;
	this.captation = captation ? captation : "详细信息";
	this.para = para ? para : new Object();
	try {
		parent.showDialogBox(this.captation, path, this.h, this.w);
	} catch (e) {
		alert("pop=function " + e.message);
	}
}
pop1 = function(obj, captation, path, w, h, para) {
	this.w = w ? w : 800;
	this.h = h ? h : 600;
	this.captation = captation ? captation : "详细信息";
	this.para = para ? para : new Object();
	try {
		showDialogBox(this.captation, path, this.h, this.w);
	} catch (e) {
		alert("pop=function " + e.message);
	}
}
