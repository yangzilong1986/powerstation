
if (typeof commonLossDataDeal == "undefined")
	commonLossDataDeal = new Object();

if (typeof commonLossDataDeal.constData == "undefined")
	commonLossDataDeal.constData = {
		key4all : [ "as"],
		key4detail : ['lineloss_transmit_model'
		              ,'lineloss_model_gp_list'
		             ,'lineloss_model_detail'
		             ,'lineloss_model_gp_add']
	}
commonLossDataDeal.renderData = function(file,p,checkbox) {
	if (file == undefined) {
		alert("参数必须完整")
		return false;
	}
	this.file = file;
	this.path = p? p:"";
	this.checkbox=checkbox?checkbox:"";
}
var RenderData = commonLossDataDeal.renderData;
commonLossDataDeal.renderData.prototype = {
	_file_check : function() {
		if (this.file == undefined) {
			alert("文件路径不存在!")
			return false;
		} else {
			return true;
		}
	},

	/**
	 * 判断是否存在此关键字
	 */
	_init_data : function() {
		if (this.file.indexOf('.htm') != -1) {
			
			var index = this.file.indexOf('.');
			var keyword = this.file.substring(0, index);
			if (commonLossDataDeal.constData.key4all[keyword] != 0) {
				if (this._loop_data(this.file, keyword, commonLossDataDeal.constData.key4all)) {
					// this._load_data(this.file);
				}
			}
			if (commonLossDataDeal.constData.key4detail[keyword] != 0) {
				if (this._loop_data(this.file, keyword, commonLossDataDeal.constData.key4detail)) {
					this._load_data(this.file);
				}
			}
		}
	},

	/**
	 * 循环判断
	 */
	_loop_data : function(file, keyword, array) {
		var bol = false;
		for ( var key in array) {
			if (keyword == array[key]) {
				bol = true;
				break;
			}
		}
		return bol;
	},
	/**
	 * 加载文件数据
	 */
	_load_data : function() {
		if (this._file_check(this.file)) {
			if (this.file.indexOf('.htm') != -1) {
				var index = this.file.indexOf('.');
				var name = this.file.substring(0, index);
				var datapath = '';
				if(this.path == ''){
					datapath = "../../jsp/lineloss/xml/"+name + ".xml";
				}else{
					datapath =  this.path;
				}
				try{
				// 加载数据区
					$.json2html(name, datapath,this.checkbox);
				}catch(e){
					alert(e.message);
				}
			}
		}
	}
}

getRenderData = function(file,p,checkbox) {
	if(typeof file =='string'){
		this.file=file;
	}else{
		this.file=file.attr('file');
	}
	var data = new RenderData(this.file,p,checkbox);
	return data;
}