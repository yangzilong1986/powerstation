
// 图形部分。
function _buildPic(xml) {
	
}

if (typeof commonLossDataDeal == "undefined")
	commonLossDataDeal = new Object();

if (typeof commonLossDataDeal.constData == "undefined")
	commonLossDataDeal.constData = {
		key4all : [ "lineloss_query_switchgear_data"
		            ,"lineloss_query_line_data"
		            ,"lineloss_query_tie_line_data"
		            ,"lineloss_query_bus_data"
		            ,"lineloss_query_transformer_data"
		            ,"lineloss_query_substation_data"
		            ,"lineloss_query_voltage_data"
		            ,"lineloss_query_majorgrid_data"
		            ,"lineloss_query_allgrid_data"
		            ],
		key4detail : [ "lineloss_query_switchgear_data_detail_supply"
		               ,"lineloss_query_switchgear_data_detail_sale"
		               ,"lineloss_query_switchgear_data_chart_data"
		               ,"lineloss_query_line_data_detail_supply"
		               ,"lineloss_query_line_data_detail_sale"
		               ,"lineloss_query_line_data_chart_data"
		               ,"lineloss_query_tie_line_data_detail_supply"
		               ,"lineloss_query_tie_line_data_detail_sale"
		               ,"lineloss_query_tie_line_data_chart_data"
		               ,"lineloss_query_bus_data_detail_supply"
		               ,"lineloss_query_bus_data_detail_sale"
		               ,"lineloss_query_bus_data_chart_data"
		               ,"lineloss_query_transformer_data_detail_supply"
		               ,"lineloss_query_transformer_data_detail_sale"
		               ,"lineloss_query_transformer_data_chart_data"
		               ,"lineloss_query_substation_data_detail_supply"
		               ,"lineloss_query_substation_data_detail_sale"
		               ,"lineloss_query_substation_data_chart_data"
		               ,"lineloss_query_voltage_data_detail_supply"
		               ,"lineloss_query_voltage_data_detail_sale"
		               ,"lineloss_query_voltage_data_detail_part"
		               ,"lineloss_query_voltage_data_chart_data"
		               ,"lineloss_query_majorgrid_data_detail_supply"
		               ,"lineloss_query_majorgrid_data_detail_sale"
		               ,"lineloss_query_majorgrid_data_chart_data"
		               ,"lineloss_query_allgrid_data_detail_supply"
		               ,"lineloss_query_allgrid_data_detail_sale"
		               ,"lineloss_query_allgrid_data_chart_data"
		               ],
		key4chart : [ "lineloss_query_switchgear_data_chart_chart"
		              ,"lineloss_query_line_data_chart_chart"
		              ,"lineloss_query_tie_line_data_chart_chart"
			            ,"lineloss_query_bus_data_chart_chart"
			            ,"lineloss_query_transformer_data_chart_chart"
			            ,"lineloss_query_substation_data_chart_chart"
			            ,"lineloss_query_voltage_data_chart_chart"
			            ,"lineloss_query_majorgrid_data_chart_chart"
			            ,"lineloss_query_allgrid_data_chart_chart"
		              ]
	}
commonLossDataDeal.renderData = function(file,path,chartxml,chartpath,w,h) {
	if (file == undefined) {
		alert("参数必须完整")
		return false;
	}
	this.file = file;
	this.path = path? path:"";
	this.chartxml=chartxml?chartxml:"";
	this.chartpath = chartpath?chartpath:"";
	this.w=w?w:"700";
	this.h=h?h:"400";
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
			if (commonLossDataDeal.constData.key4chart[keyword] != 0) {
				if (this._loop_data(this.file, keyword, commonLossDataDeal.constData.key4chart)) {
					
					if(this.chartxml=='' || this.chartpath==''){
						return;
					}else{
						this._load_chart(this.file);
					}
					
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
					$.json2html(name, datapath, "",'../../');
				}catch(e){
					alert(e.message);
				}
			}
		}
	},
	_load_chart:function(){
		if (this._file_check(this.file)) {
			if (this.file.indexOf('.htm') != -1) {
				var index = this.file.indexOf('.');
				var name = this.file.substring(0, index);
				
				var chart_obj = new FusionCharts(this.chartpath, name, this.w, this.h);
				chart_obj.setDataURL(this.chartxml);
				chart_obj.render(name);
				
			}
		}
	}
}

/** ****************************************************** */
/**
 * jQuery operation
 */
$( function() {
	getRenderData($.url)._init_data();
});
getRenderData = function(file,path,chartxml,chartpath,w,h) {
	this.path=path;
	if(typeof file =='string'){
		this.file=file;
	}else{
		this.file=file.attr('file');
		this.dateType=file.param('dateType');
		if (this.file.indexOf('.htm') != -1 && this.dateType != undefined) {
			var index = this.file.indexOf('.');
			var keyword = this.file.substring(0, index);
			var xml=".xml";
			if(this.dateType =='1'){
				keyword += "_day";
				keyword += xml;
			}else if(this.dateType =='2'){
				keyword += "_month";
				keyword += xml;
			}else if(this.dateType =='3'){
				keyword += "_year";
				keyword += xml;
			}else if(this.dateType =='4'){
				keyword += "_any";
				keyword += xml;
			}else{
				
			}
			this.path = file.attr('path').substring(0, file.attr('path').lastIndexOf('/')+1)+"xml/"+keyword;
		}
	}
	var data = new RenderData(this.file,this.path,chartxml,chartpath,w,h);
	return data;
}

popTop=function(obj,captation,path,w,h,para){
	this.w=w?w:900;
	this.h=h?h:575;
	this.captation=captation?captation:"详细信息";
	this.para=para?para:new Object();
	try{
		top.showDialogBox(this.captation, path, this.h, this.w);
	}catch(e){
		alert("popTop=function "+e.message);
	}
}
pop=function(obj,captation,path,w,h,para){
	this.w=w?w:860;
	this.h=h?h:530;
	this.captation=captation?captation:"详细信息";
	this.para=para?para:new Object();
	try{
		parent.showDialogBox(this.captation, path, this.h, this.w);
	}catch(e){
		alert("pop=function "+e.message);
	}
}
pop1=function(obj,captation,path,w,h,para){
	this.w=w?w:860;
	this.h=h?h:530;
	this.captation=captation?captation:"详细信息";
	this.para=para?para:new Object();
	try{
		parent.parent.showDialogBox(this.captation, path, this.h, this.w);
	}catch(e){
		alert("pop=function "+e.message);
	}
}
/** ****************************************************** */
