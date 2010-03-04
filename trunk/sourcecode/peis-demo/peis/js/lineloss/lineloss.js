var Model = new Object();
Model = function(obj) {
	if(obj !=undefined){
		this.file = obj.attr('file');
		this.obj = obj;
	}else{
		return false;
	}
}
Model.prototype = {
	_check_model : function() {
		if (this.file.indexOf('.htm') != -1) {
			var index = this.file.indexOf('.');
			var keyword= this.file.substring(0, index);
			switch (keyword) {
			/********************************************/
			//台区
			case 'lineloss_query_switchgear':
				this._model_lineloss_query_switchgear();
				break;
			case 'lineloss_query_switchgear_data':
				this._model_lineloss_query_switchgear_data();
				break;
			case 'lineloss_query_switchgear_data_detail':
				this._model_lineloss_query_switchgear_data_detail();
				break;
			case 'lineloss_query_switchgear_data_chart':
				this._model_lineloss_query_switchgear_data_chart();
				break;
			case 'lineloss_query_switchgear_data_chart_data':
				this._model_lineloss_query_switchgear_data_chart_data();
				break;
			case 'lineloss_query_switchgear_data_chart_chart':
				this._model_lineloss_query_switchgear_data_chart_chart();
				break;
			case 'lineloss_query_switchgear_data_detail_supply':
				this._model_lineloss_query_switchgear_data_detail_supply();
				break;
			case 'lineloss_query_switchgear_data_detail_sale':
				this._model_lineloss_query_switchgear_data_detail_sale();
				break;
			/********************************************/
			//线路
			case 'lineloss_query_line':
				this._model_lineloss_query_line();
				break;
			case 'lineloss_query_line_data':
				this._model_lineloss_query_line_data();
				break;
			case 'lineloss_query_line_data_detail':
				this._model_lineloss_query_line_data_detail();
				break;
			case 'lineloss_query_line_data_chart':
				this._model_lineloss_query_line_data_chart();
				break;
			case 'lineloss_query_line_data_chart_data':
				this._model_lineloss_query_line_data_chart_data();
				break;
			case 'lineloss_query_line_data_chart_chart':
				this._model_lineloss_query_line_data_chart_chart();
				break;
			case 'lineloss_query_line_data_detail_supply':
				this._model_lineloss_query_line_data_detail_supply();
				break;
			case 'lineloss_query_line_data_detail_sale':
				this._model_lineloss_query_line_data_detail_sale();
				break;
			/********************************************/
			//联络线
			case 'lineloss_query_tie_line':
				this._model_lineloss_query_line();
				break;
			case 'lineloss_query_tie_line_data':
				this._model_lineloss_query_tie_line_data();
				break;
			case 'lineloss_query_tie_line_data_detail':
				this._model_lineloss_query_tie_line_data_detail();
				break;
			case 'lineloss_query_tie_line_data_chart':
				this._model_lineloss_query_tie_line_data_chart();
				break;
			case 'lineloss_query_tie_line_data_chart_data':
				this._model_lineloss_query_tie_line_data_chart_data();
				break;
			case 'lineloss_query_tie_line_data_chart_chart':
				this._model_lineloss_query_tie_line_data_chart_chart();
				break;
			case 'lineloss_query_tie_line_data_detail_supply':
				this._model_lineloss_query_tie_line_data_detail_supply();
				break;
			case 'lineloss_query_tie_line_data_detail_sale':
				this._model_lineloss_query_tie_line_data_detail_sale();
				break;
			/********************************************/
			//母线
			case 'lineloss_query_bus':
				this._model_lineloss_query_bus();
				break;
			case 'lineloss_query_bus_data':
				this._model_lineloss_query_bus_data();
				break;
			case 'lineloss_query_bus_data_detail':
				this._model_lineloss_query_bus_data_detail();
				break;
			case 'lineloss_query_bus_data_chart':
				this._model_lineloss_query_bus_data_chart();
				break;
			case 'lineloss_query_bus_data_chart_data':
				this._model_lineloss_query_bus_data_chart_data();
				break;
			case 'lineloss_query_bus_data_chart_chart':
				this._model_lineloss_query_bus_data_chart_chart();
				break;
			case 'lineloss_query_bus_data_detail_supply':
				this._model_lineloss_query_bus_data_detail_supply();
				break;
			case 'lineloss_query_bus_data_detail_sale':
				this._model_lineloss_query_bus_data_detail_sale();
				break;
			/********************************************/
			//变损
			case 'lineloss_query_transformer':
				this._model_lineloss_query_transformer();
				break;
			case 'lineloss_query_transformer_data':
				this._model_lineloss_query_transformer_data();
				break;
			case 'lineloss_query_transformer_data_detail':
				this._model_lineloss_query_transformer_data_detail();
				break;
			case 'lineloss_query_transformer_data_chart':
				this._model_lineloss_query_transformer_data_chart();
				break;
			case 'lineloss_query_transformer_data_chart_data':
				this._model_lineloss_query_transformer_data_chart_data();
				break;
			case 'lineloss_query_transformer_data_chart_chart':
				this._model_lineloss_query_transformer_data_chart_chart();
				break;
			case 'lineloss_query_transformer_data_detail_supply':
				this._model_lineloss_query_transformer_data_detail_supply();
				break;
			case 'lineloss_query_transformer_data_detail_sale':
				this._model_lineloss_query_transformer_data_detail_sale();
				break;
			/********************************************/
			//站损
			case 'lineloss_query_substation':
				this._model_lineloss_query_substation();
				break;
			case 'lineloss_query_substation_data':
				this._model_lineloss_query_substation_data();
				break;
			case 'lineloss_query_substation_data_detail':
				this._model_lineloss_query_substation_data_detail();
				break;
			case 'lineloss_query_substation_data_chart':
				this._model_lineloss_query_substation_data_chart();
				break;
			case 'lineloss_query_substation_data_chart_data':
				this._model_lineloss_query_substation_data_chart_data();
				break;
			case 'lineloss_query_substation_data_chart_chart':
				this._model_lineloss_query_substation_data_chart_chart();
				break;
			case 'lineloss_query_substation_data_detail_supply':
				this._model_lineloss_query_substation_data_detail_supply();
				break;
			case 'lineloss_query_substation_data_detail_sale':
				this._model_lineloss_query_substation_data_detail_sale();
				break;
			/********************************************/
			//分压voltage
			case 'lineloss_query_voltage':
				this._model_lineloss_query_voltage();
				break;
			case 'lineloss_query_voltage_data':
				this._model_lineloss_query_voltage_data();
				break;
			case 'lineloss_query_voltage_data_detail':
				this._model_lineloss_query_voltage_data_detail();
				break;
			case 'lineloss_query_voltage_data_chart':
				this._model_lineloss_query_voltage_data_chart();
				break;
			case 'lineloss_query_voltage_data_chart_data':
				this._model_lineloss_query_voltage_data_chart_data();
				break;
			case 'lineloss_query_voltage_data_chart_chart':
				this._model_lineloss_query_voltage_data_chart_chart();
				break;
			case 'lineloss_query_voltage_data_detail_supply':
				this._model_lineloss_query_voltage_data_detail_supply();
				break;
			case 'lineloss_query_voltage_data_detail_sale':
				this._model_lineloss_query_voltage_data_detail_sale();
				break;
			/********************************************/
			//主网网损
			case 'lineloss_query_majorgrid':
				this._model_lineloss_query_majorgrid();
				break;
			case 'lineloss_query_majorgrid_data':
				this._model_lineloss_query_majorgrid_data();
				break;
			case 'lineloss_query_majorgrid_data_detail':
				this._model_lineloss_query_majorgrid_data_detail();
				break;
			case 'lineloss_query_majorgrid_data_chart':
				this._model_lineloss_query_majorgrid_data_chart();
				break;
			case 'lineloss_query_majorgrid_data_chart_data':
				this._model_lineloss_query_majorgrid_data_chart_data();
				break;
			case 'lineloss_query_majorgrid_data_chart_chart':
				this._model_lineloss_query_majorgrid_data_chart_chart();
				break;
			case 'lineloss_query_majorgrid_data_detail_supply':
				this._model_lineloss_query_majorgrid_data_detail_supply();
				break;
			case 'lineloss_query_majorgrid_data_detail_sale':
				this._model_lineloss_query_majorgrid_data_detail_sale();
				break;
			/********************************************/
			//全网损耗
			case 'lineloss_query_allgrid':
				this._model_lineloss_query_allgrid();
				break;
			case 'lineloss_query_allgrid_data':
				this._model_lineloss_query_allgrid_data();
				break;
			case 'lineloss_query_allgrid_data_detail':
				this._model_lineloss_query_allgrid_data_detail();
				break;
			case 'lineloss_query_allgrid_data_chart':
				this._model_lineloss_query_allgrid_data_chart();
				break;
			case 'lineloss_query_allgrid_data_chart_data':
				this._model_lineloss_query_allgrid_data_chart_data();
				break;
			case 'lineloss_query_allgrid_data_chart_chart':
				this._model_lineloss_query_allgrid_data_chart_chart();
				break;
			case 'lineloss_query_allgrid_data_detail_supply':
				this._model_lineloss_query_allgrid_data_detail_supply();
				break;
			case 'lineloss_query_allgrid_data_detail_sale':
				this._model_lineloss_query_allgrid_data_detail_sale();
				break;
			/********************************************/
			default:
				break;
			}
		}
	},
	/************************************************************/
	_model_lineloss_query_switchgear:function(){
		// 供电单位。
		$.handelCombox('orgTar', 'orgNo');

		$.handelCombox('equalTar', 'equal');
		
		// year
		$.handelCombox('DATA_YEAR', 'Year');
		
		// month
		$.handelCombox('DATA_MONTH', 'Month');

		$.handelCombox('dataAllTar', 'dataAll', 'name="dateType" id="dateType" onChange="meterDTChange()"');

		$('#dateType').val(2);

		$('#dateType').change();
		
		// 查询按钮事件。
		$("#query").click( function() {
			var params={dateType:$('#dateType').val()};
			var str = $.param(params);
			var suff= $.url.attr('path').substring(0,$.url.attr('path').lastIndexOf("/")+1);
			var path = $("#targetFrame").attr("src")+"?"+str;
			targetFrame.getRenderData($.url.setUrl(suff+path))._load_data();
		});
	}
	,_model_lineloss_query_switchgear_data:function(){
		var dateType=$.url.param('dateType');
		$('#dateType').val($.url.param('dateType'));
	},
	_model_lineloss_query_switchgear_data_detail:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_switchgear_data_detail_supply',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_switchgear_data_chart:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_switchgear_data_chart_chart',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_switchgear_data_chart_data:function(){
	}
	,_model_lineloss_query_switchgear_data_chart_chart:function(){
		var xml="";
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			xml = '../../jsp/lineloss/xml/lineloss_query_switchgear_data_chart_chart_day.xml';
			break;
		case '2':
			xml = '../../jsp/lineloss/xml/lineloss_query_switchgear_data_chart_chart_month.xml';
			break;
		case '3':
			xml = '../../jsp/lineloss/xml/lineloss_query_switchgear_data_chart_chart_year.xml';
			break;
		case '4':
			xml = '../../jsp/lineloss/xml/lineloss_query_switchgear_data_chart_chart_any.xml';
			break;
		default:
			xml = '../../jsp/lineloss/xml/lineloss_query_switchgear_data_chart_chart_day.xml';
			break;
		}
		getRenderData($.url.attr('file'),null,xml,'../../FusionCharts/MSLine.swf','800','450')._init_data();
	}
	,_model_lineloss_query_switchgear_data_detail_supply:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$("#colNm").html('日期');
				$("#col_08").hide();
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_switchgear_data_detail_supply').html()!=null){
						$('#'+this.obj.param('date')).siblings().hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
				$("#colNm").html('装表日');
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$("#colNm").html('日期');
				$("#col_08").hide();
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_switchgear_data_detail_supply').html()!=null){
						$('#'+this.obj.param('date')).siblings().hide();
					}
				}
			}else{
				
			}
		}
	}
	,_model_lineloss_query_switchgear_data_detail_sale:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$("#colNm").html('日期');
				$("#col_08").hide();
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_switchgear_data_detail_sale').html()!=null){
						$('#'+this.obj.param('date')).siblings().hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
				$("#colNm").html('装表日');
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$("#colNm").html('日期');
				$("#col_08").hide();
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_switchgear_data_detail_supply').html()!=null){
						$('#'+this.obj.param('date')).siblings().hide();
					}
				}
			}else{
				
			}
		}
	},
	/************************************************************/
	//线路
	_model_lineloss_query_line:function(){
		// 供电单位。
		$.handelCombox('orgTar', 'orgNo');

		$.handelCombox('lineTar', 'lineNo');
		
		$.handelCombox('equalTar', 'equal');
		
				// year
		$.handelCombox('DATA_YEAR', 'Year');
		
		// month
		$.handelCombox('DATA_MONTH', 'Month');

		$.handelCombox('dataAllTar', 'dataAll', 'name="dateType" id="dateType" onChange="meterDTChange()"');

		$('#dateType').val(2);

		$('#dateType').change();

		// 查询按钮事件。
		$("#query").click( function() {
			var params={dateType:$('#dateType').val()};
			var str = $.param(params);
			var suff= $.url.attr('path').substring(0,$.url.attr('path').lastIndexOf("/")+1);
			var path = $("#targetFrame").attr("src")+"?"+str;
			targetFrame.getRenderData($.url.setUrl(suff+path))._load_data();
		});
	}
	,_model_lineloss_query_line_data:function(){
		var dateType=$.url.param('dateType');
		$('#dateType').val($.url.param('dateType'));
	},
	_model_lineloss_query_line_data_detail:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_line_data_detail_supply',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_line_data_chart:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_line_data_chart_chart',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_line_data_chart_data:function(){
	}
	,_model_lineloss_query_line_data_chart_chart:function(){
		var xml="";
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			xml = '../../jsp/lineloss/xml/lineloss_query_line_data_chart_chart_day.xml';
			break;
		case '2':
			xml = '../../jsp/lineloss/xml/lineloss_query_line_data_chart_chart_month.xml';
			break;
		case '3':
			xml = '../../jsp/lineloss/xml/lineloss_query_line_data_chart_chart_year.xml';
			break;
		case '4':
			xml = '../../jsp/lineloss/xml/lineloss_query_line_data_chart_chart_any.xml';
			break;
		default:
			xml = '../../jsp/lineloss/xml/lineloss_query_line_data_chart_chart_day.xml';
			break;
		}
		getRenderData($.url.attr('file'),null,xml,'../../FusionCharts/MSLine.swf','800','450')._init_data();
	}
	
	,_model_lineloss_query_line_data_detail_supply:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$('#col_09').hide()
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_line_data_detail_supply').html()!=null){
						$('#lineloss_query_line_data_detail_supply>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$('#col_09').hide()
				if(this.obj.param('date')!=undefined){
					
				}
			}else{
				
			}
		}
	}
	,_model_lineloss_query_line_data_detail_sale:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$('#col_10').hide()
				if(this.obj.param('date')!= undefined){
					if($('#lineloss_query_line_data_detail_sale').html()!=null){
						$('#lineloss_query_line_data_detail_sale>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$('#col_10').hide()
				if(this.obj.param('date')!=undefined){
				}
			}else{
				
			}
		}
	},
	/************************************************************/
	//联络线路
	_model_lineloss_query_tie_line:function(){
		// 供电单位。
		$.handelCombox('orgTar', 'orgNo');

		$.handelCombox('lineTar', 'lineNo');
		
				// year
		$.handelCombox('DATA_YEAR', 'Year');
		
		// month
		$.handelCombox('DATA_MONTH', 'Month');
		
		$.handelCombox('equalTar', 'equal');

		$.handelCombox('dataAllTar', 'dataAll', 'name="dateType" id="dateType" onChange="meterDTChange()"');

		$('#dateType').val(2);

		$('#dateType').change();

		// 查询按钮事件。
		$("#query").click( function() {
			var params={dateType:$('#dateType').val()};
			var str = $.param(params);
			var suff= $.url.attr('path').substring(0,$.url.attr('path').lastIndexOf("/")+1);
			var path = $("#targetFrame").attr("src")+"?"+str;
			targetFrame.getRenderData($.url.setUrl(suff+path))._load_data();
		});
	}
	,_model_lineloss_query_tie_line_data:function(){
		var dateType=$.url.param('dateType');
		$('#dateType').val($.url.param('dateType'));
	},
	_model_lineloss_query_tie_line_data_detail:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_tie_line_data_detail_supply',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_tie_line_data_chart:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_tie_line_data_chart_chart',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_tie_line_data_chart_data:function(){
	}
	,_model_lineloss_query_tie_line_data_chart_chart:function(){
		var xml="";
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			xml = '../../jsp/lineloss/xml/lineloss_query_tie_line_data_chart_chart_day.xml';
			break;
		case '2':
			xml = '../../jsp/lineloss/xml/lineloss_query_tie_line_data_chart_chart_month.xml';
			break;
		case '3':
			xml = '../../jsp/lineloss/xml/lineloss_query_tie_line_data_chart_chart_year.xml';
			break;
		case '4':
			xml = '../../jsp/lineloss/xml/lineloss_query_tie_line_data_chart_chart_any.xml';
			break;
		default:
			xml = '../../jsp/lineloss/xml/lineloss_query_tie_line_data_chart_chart_day.xml';
			break;
		}
		getRenderData($.url.attr('file'),null,xml,'../../FusionCharts/MSLine.swf','800','450')._init_data();
	}
	
	,_model_lineloss_query_tie_line_data_detail_supply:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$('#col_09').hide()
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_tie_line_data_detail_supply').html()!=null){
						$('#lineloss_query_tie_line_data_detail_supply>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$('#col_09').hide()
				if(this.obj.param('date')!=undefined){
					
				}
			}else{
				
			}
		}
	}
	,_model_lineloss_query_tie_line_data_detail_sale:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$('#col_10').hide()
				if(this.obj.param('date')!= undefined){
					if($('#lineloss_query_tie_line_data_detail_sale').html()!=null){
						$('#lineloss_query_tie_line_data_detail_sale>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$('#col_10').hide()
				if(this.obj.param('date')!=undefined){
				}
			}else{
				
			}
		}
	},
	/************************************************************/
	//母线
	_model_lineloss_query_bus:function(){
		// 供电单位。
		$.handelCombox('orgTar', 'orgNo');

		$.handelCombox('subsTar', 'subsNo');
		
		$.handelCombox('equalTar', 'equal');
				// year
		$.handelCombox('DATA_YEAR', 'Year');
		
		// month
		$.handelCombox('DATA_MONTH', 'Month');

		$.handelCombox('dataAllTar', 'dataAll', 'name="dateType" id="dateType" onChange="meterDTChange()"');

		$('#dateType').val(2);

		$('#dateType').change();

		// 查询按钮事件。
		$("#query").click( function() {
			var params={dateType:$('#dateType').val()};
			var str = $.param(params);
			var suff= $.url.attr('path').substring(0,$.url.attr('path').lastIndexOf("/")+1);
			var path = $("#targetFrame").attr("src")+"?"+str;
			targetFrame.getRenderData($.url.setUrl(suff+path))._load_data();
		});
	}
	,_model_lineloss_query_bus_data:function(){
		var dateType=$.url.param('dateType');
		$('#dateType').val($.url.param('dateType'));
	},
	_model_lineloss_query_bus_data_detail:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_bus_data_detail_supply',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_bus_data_chart:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_bus_data_chart_chart',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_bus_data_chart_data:function(){
	}
	,_model_lineloss_query_bus_data_chart_chart:function(){
		var xml="";
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			xml = '../../jsp/lineloss/xml/lineloss_query_bus_data_chart_chart_day.xml';
			break;
		case '2':
			xml = '../../jsp/lineloss/xml/lineloss_query_bus_data_chart_chart_month.xml';
			break;
		case '3':
			xml = '../../jsp/lineloss/xml/lineloss_query_bus_data_chart_chart_year.xml';
			break;
		case '4':
			xml = '../../jsp/lineloss/xml/lineloss_query_bus_data_chart_chart_any.xml';
			break;
		default:
			xml = '../../jsp/lineloss/xml/lineloss_query_bus_data_chart_chart_day.xml';
			break;
		}
		getRenderData($.url.attr('file'),null,xml,'../../FusionCharts/MSLine.swf','800','450')._init_data();
	}
	,_model_lineloss_query_bus_data_detail_supply:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$('#col_11').hide()
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_bus_data_detail_supply').html()!=null){
						$('#lineloss_query_bus_data_detail_supply>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$('#col_11').hide()
				if(this.obj.param('date')!=undefined){
					
				}
			}else{
				
			}
		}
		
	}
	,_model_lineloss_query_bus_data_detail_sale:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_bus_data_detail_sale').html()!=null){
						$('#lineloss_query_bus_data_detail_sale>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				if(this.obj.param('date')!=undefined){
				}
			}else{
				
			}
		}
	}
	/************************************************************/
	//变损
	,_model_lineloss_query_transformer:function(){
		// 供电单位。
		$.handelCombox('orgTar', 'orgNo');

		$.handelCombox('subsTar', 'subsNo');
		
				// year
		$.handelCombox('DATA_YEAR', 'Year');
		
		// month
		$.handelCombox('DATA_MONTH', 'Month');
		
		$.handelCombox('equalTar', 'equal');

		$.handelCombox('dataAllTar', 'dataAll', 'name="dateType" id="dateType" onChange="meterDTChange()"');

		$('#dateType').val(2);

		$('#dateType').change();

		// 查询按钮事件。
		$("#query").click( function() {
			var params={dateType:$('#dateType').val()};
			var str = $.param(params);
			var suff= $.url.attr('path').substring(0,$.url.attr('path').lastIndexOf("/")+1);
			var path = $("#targetFrame").attr("src")+"?"+str;
			targetFrame.getRenderData($.url.setUrl(suff+path))._load_data();
		});
	}
	,_model_lineloss_query_transformer_data:function(){
		var dateType=$.url.param('dateType');
		$('#dateType').val($.url.param('dateType'));
	},
	_model_lineloss_query_transformer_data_detail:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_transformer_data_detail_supply',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_transformer_data_chart:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_transformer_data_chart_chart',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_transformer_data_chart_data:function(){
	}
	,_model_lineloss_query_transformer_data_chart_chart:function(){
		var xml="";
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			xml = '../../jsp/lineloss/xml/lineloss_query_transformer_data_chart_chart_day.xml';
			break;
		case '2':
			xml = '../../jsp/lineloss/xml/lineloss_query_transformer_data_chart_chart_month.xml';
			break;
		case '3':
			xml = '../../jsp/lineloss/xml/lineloss_query_transformer_data_chart_chart_year.xml';
			break;
		case '4':
			xml = '../../jsp/lineloss/xml/lineloss_query_transformer_data_chart_chart_any.xml';
			break;
		default:
			xml = '../../jsp/lineloss/xml/lineloss_query_transformer_data_chart_chart_day.xml';
			break;
		}
		getRenderData($.url.attr('file'),null,xml,'../../FusionCharts/MSLine.swf','800','450')._init_data();
	}
	,_model_lineloss_query_transformer_data_detail_supply:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$('#col_11').hide()
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_transformer_data_detail_supply').html()!=null){
						$('#lineloss_query_transformer_data_detail_supply>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
					
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$('#col_11').hide()
				if(this.obj.param('date')!=undefined){
					
				}
			}else{
				
			}
		}
		
	}
	,_model_lineloss_query_transformer_data_detail_sale:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_transformer_data_detail_sale').html()!=null){
						$('#lineloss_query_transformer_data_detail_sale>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				if(this.obj.param('date')!=undefined){
				}
			}else{
				
			}
		}
	}
	/************************************************************/
	//站损
	,_model_lineloss_query_substation:function(){
		// 供电单位。
		$.handelCombox('orgTar', 'orgNo');

		$.handelCombox('subsTar', 'subsNo');
				// year
		$.handelCombox('DATA_YEAR', 'Year');
		
		// month
		$.handelCombox('DATA_MONTH', 'Month');
		
		$.handelCombox('equalTar', 'equal');

		$.handelCombox('dataAllTar', 'dataAll', 'name="dateType" id="dateType" onChange="meterDTChange()"');

		$('#dateType').val(2);

		$('#dateType').change();

		// 查询按钮事件。
		$("#query").click( function() {
			var params={dateType:$('#dateType').val()};
			var str = $.param(params);
			var suff= $.url.attr('path').substring(0,$.url.attr('path').lastIndexOf("/")+1);
			var path = $("#targetFrame").attr("src")+"?"+str;
			targetFrame.getRenderData($.url.setUrl(suff+path))._load_data();
		});
	}
	,_model_lineloss_query_substation_data:function(){
		var dateType=$.url.param('dateType');
		$('#dateType').val($.url.param('dateType'));
	},
	_model_lineloss_query_substation_data_detail:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_substation_data_detail_supply',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_substation_data_chart:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_substation_data_chart_chart',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_substation_data_chart_data:function(){
	}
	,_model_lineloss_query_substation_data_chart_chart:function(){
		var xml="";
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			xml = '../../jsp/lineloss/xml/lineloss_query_substation_data_chart_chart_day.xml';
			break;
		case '2':
			xml = '../../jsp/lineloss/xml/lineloss_query_substation_data_chart_chart_month.xml';
			break;
		case '3':
			xml = '../../jsp/lineloss/xml/lineloss_query_substation_data_chart_chart_year.xml';
			break;
		case '4':
			xml = '../../jsp/lineloss/xml/lineloss_query_substation_data_chart_chart_any.xml';
			break;
		default:
			xml = '../../jsp/lineloss/xml/lineloss_query_substation_data_chart_chart_day.xml';
			break;
		}
		getRenderData($.url.attr('file'),null,xml,'../../FusionCharts/MSLine.swf','800','450')._init_data();
	}
	,_model_lineloss_query_substation_data_detail_supply:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$('#col_11').hide()
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_substation_data_detail_supply').html()!=null){
						$('#lineloss_query_substation_data_detail_supply>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
					
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$('#col_11').hide()
				if(this.obj.param('date')!=undefined){
					
				}
			}else{
				
			}
		}
		
	}
	,_model_lineloss_query_substation_data_detail_sale:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_substation_data_detail_sale').html()!=null){
						$('#lineloss_query_substation_data_detail_sale>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				if(this.obj.param('date')!=undefined){
				}
			}else{
				
			}
		}
	}
	/************************************************************/
	//分压损
	,_model_lineloss_query_voltage:function(){
		// 供电单位。
		$.handelCombox('orgTar', 'orgNo');

		$.handelCombox('subsTar', 'subsNo');
		
		$.handelCombox('equalTar', 'equal');
				// year
		$.handelCombox('DATA_YEAR', 'Year');
		
		// month
		$.handelCombox('DATA_MONTH', 'Month');

		$.handelCombox('dataAllTar', 'dataAll', 'name="dateType" id="dateType" onChange="meterDTChange()"');

		$('#dateType').val(2);

		$('#dateType').change();

		// 查询按钮事件。
		$("#query").click( function() {
			var params={dateType:$('#dateType').val()};
			var str = $.param(params);
			var suff= $.url.attr('path').substring(0,$.url.attr('path').lastIndexOf("/")+1);
			var path = $("#targetFrame").attr("src")+"?"+str;
			targetFrame.getRenderData($.url.setUrl(suff+path))._load_data();
		});
	}
	,_model_lineloss_query_voltage_data:function(){
		var dateType=$.url.param('dateType');
		$('#dateType').val($.url.param('dateType'));
	},
	_model_lineloss_query_voltage_data_detail:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_voltage_data_detail_supply',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_voltage_data_chart:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_voltage_data_chart_data',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_voltage_data_chart_data:function(){
	}
	,_model_lineloss_query_voltage_data_chart_chart:function(){
		var xml="";
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			xml = '../../jsp/lineloss/xml/lineloss_query_voltage_data_chart_chart_day.xml';
			break;
		case '2':
			xml = '../../jsp/lineloss/xml/lineloss_query_voltage_data_chart_chart_month.xml';
			break;
		case '3':
			xml = '../../jsp/lineloss/xml/lineloss_query_voltage_data_chart_chart_year.xml';
			break;
		case '4':
			xml = '../../jsp/lineloss/xml/lineloss_query_voltage_data_chart_chart_any.xml';
			break;
		default:
			xml = '../../jsp/lineloss/xml/lineloss_query_voltage_data_chart_chart_day.xml';
			break;
		}
		getRenderData($.url.attr('file'),null,xml,'../../FusionCharts/MSLine.swf','800','450')._init_data();
	}
	,_model_lineloss_query_voltage_data_detail_supply:function(){
		if(this.obj.param('dateType') != undefined){
			
			if(this.obj.param('dateType')=='1'){
				$('#col_11').hide();
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_voltage_data_detail_supply').html()!=null){
						$('#lineloss_query_voltage_data_detail_supply>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
					
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$('#col_11').hide()
				if(this.obj.param('date')!=undefined){
					
				}
			}else{
				
			}
		}
		
	}
	,_model_lineloss_query_voltage_data_detail_sale:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_voltage_data_detail_sale').html()!=null){
						$('#lineloss_query_voltage_data_detail_sale>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				if(this.obj.param('date')!=undefined){
				}
			}else{
				
			}
		}
	}
	/************************************************************/
	//主网
	,_model_lineloss_query_majorgrid:function(){
		// 供电单位。
		$.handelCombox('orgTar', 'orgNo');

		// year
		$.handelCombox('DATA_YEAR', 'Year');
		
		// month
		$.handelCombox('DATA_MONTH', 'Month');
		
		$.handelCombox('equalTar', 'equal');

		$.handelCombox('dataAllTar', 'dataAll', 'name="dateType" id="dateType" onChange="meterDTChange()"');

		$('#dateType').val(2);

		$('#dateType').change();

		// 查询按钮事件。
		$("#query").click( function() {
			var params={dateType:$('#dateType').val()};
			var str = $.param(params);
			var suff= $.url.attr('path').substring(0,$.url.attr('path').lastIndexOf("/")+1);
			var path = $("#targetFrame").attr("src")+"?"+str;
			targetFrame.getRenderData($.url.setUrl(suff+path))._load_data();
		});
	}
	,_model_lineloss_query_majorgrid_data:function(){
		var dateType=$.url.param('dateType');
		$('#dateType').val($.url.param('dateType'));
	},
	_model_lineloss_query_majorgrid_data_detail:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_majorgrid_data_detail_supply',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_majorgrid_data_chart:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_majorgrid_data_chart_chart',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_majorgrid_data_chart_data:function(){
	}
	,_model_lineloss_query_majorgrid_data_chart_chart:function(){
		var xml="";
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			xml = '../../jsp/lineloss/xml/lineloss_query_majorgrid_data_chart_chart_day.xml';
			break;
		case '2':
			xml = '../../jsp/lineloss/xml/lineloss_query_majorgrid_data_chart_chart_month.xml';
			break;
		case '3':
			xml = '../../jsp/lineloss/xml/lineloss_query_majorgrid_data_chart_chart_year.xml';
			break;
		case '4':
			xml = '../../jsp/lineloss/xml/lineloss_query_majorgrid_data_chart_chart_any.xml';
			break;
		default:
			xml = '../../jsp/lineloss/xml/lineloss_query_majorgrid_data_chart_chart_day.xml';
			break;
		}
		getRenderData($.url.attr('file'),null,xml,'../../FusionCharts/MSLine.swf','800','450')._init_data();
	}
	,_model_lineloss_query_majorgrid_data_detail_supply:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$('#col_09').hide()
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_majorgrid_data_detail_supply').html()!=null){
						$('#lineloss_query_majorgrid_data_detail_supply>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
					
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$('#col_09').hide()
				if(this.obj.param('date')!=undefined){
					
				}
			}else{
				
			}
		}
		
	}
	,_model_lineloss_query_majorgrid_data_detail_sale:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$('#col_10').hide()
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_majorgrid_data_detail_sale').html()!=null){
						$('#lineloss_query_majorgrid_data_detail_sale>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$('#col_10').hide()
				if(this.obj.param('date')!=undefined){
				}
			}else{
			}
		}
	}
	/************************************************************/
	//全网
	,_model_lineloss_query_allgrid:function(){
		// 供电单位。
		$.handelCombox('orgTar', 'orgNo');

		$.handelCombox('equalTar', 'equal');
		
				// year
		$.handelCombox('DATA_YEAR', 'Year');
		
		// month
		$.handelCombox('DATA_MONTH', 'Month');
		

		$.handelCombox('dataAllTar', 'dataAll', 'name="dateType" id="dateType" onChange="meterDTChange()"');

		$('#dateType').val(2);

		$('#dateType').change();

		// 查询按钮事件。
		$("#query").click( function() {
			var params={dateType:$('#dateType').val()};
			var str = $.param(params);
			var suff= $.url.attr('path').substring(0,$.url.attr('path').lastIndexOf("/")+1);
			var path = $("#targetFrame").attr("src")+"?"+str;
			targetFrame.getRenderData($.url.setUrl(suff+path))._load_data();
		});
	}
	,_model_lineloss_query_allgrid_data:function(){
		var dateType=$.url.param('dateType');
		$('#dateType').val($.url.param('dateType'));
	},
	_model_lineloss_query_allgrid_data_detail:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_allgrid_data_detail_supply',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_allgrid_data_chart:function(){
		$('#dateType').val($.url.param('dateType'))
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			$('#date').html('2009-09-01')
			break;
		case '2':
			$('#date').html('2009-09')
			break;
		case '3':
			break;
		case '4':
			$('#date').html('09-01到09-11')
			break;
		default:
			break;
		}
		tab_toggle('cont_1','target_on','target_off','lineloss_query_allgrid_data_chart_chart',$.param({dateType:$.url.param('dateType'),date:$.url.param('date')}))
	}
	,_model_lineloss_query_allgrid_data_chart_data:function(){
	}
	,_model_lineloss_query_allgrid_data_chart_chart:function(){
		var xml="";
		var dateType = $.url.param('dateType');
		switch (dateType) {
		case '1':
			xml = '../../jsp/lineloss/xml/lineloss_query_allgrid_data_chart_chart_day.xml';
			break;
		case '2':
			xml = '../../jsp/lineloss/xml/lineloss_query_allgrid_data_chart_chart_month.xml';
			break;
		case '3':
			xml = '../../jsp/lineloss/xml/lineloss_query_allgrid_data_chart_chart_year.xml';
			break;
		case '4':
			xml = '../../jsp/lineloss/xml/lineloss_query_allgrid_data_chart_chart_any.xml';
			break;
		default:
			xml = '../../jsp/lineloss/xml/lineloss_query_allgrid_data_chart_chart_day.xml';
			break;
		}
		getRenderData($.url.attr('file'),null,xml,'../../FusionCharts/MSLine.swf','800','450')._init_data();
	}
	,_model_lineloss_query_allgrid_data_detail_supply:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$('#col_09').hide()
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_allgrid_data_detail_supply').html()!=null){
						$('#lineloss_query_allgrid_data_detail_supply>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
					
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$('#col_09').hide()
				if(this.obj.param('date')!=undefined){
					
				}
			}else{
				
			}
		}
		
	}
	,_model_lineloss_query_allgrid_data_detail_sale:function(){
		if(this.obj.param('dateType') != undefined){
			if(this.obj.param('dateType')=='1'){
				$('#col_10').hide()
				if(this.obj.param('date')!=undefined){
					if($('#lineloss_query_allgrid_data_detail_sale').html()!=null){
						$('#lineloss_query_allgrid_data_detail_sale>tr:not([name*='+this.obj.param('date')+"])").hide();
					}
				}
			}else if(this.obj.param('dateType')=='2'){
			}else if(this.obj.param('dateType')=='3'){
				
			}else if(this.obj.param('dateType')=='4'){
				$('#col_10').hide()
				if(this.obj.param('date')!=undefined){
				}
			}else{
			}
		}
	}
	/************************************************************/
}

/**
 * 初始化加载
 */
$( function() {
	new Model($.url)._check_model();
});

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
 * tab页切换
 * @param div_id
 * @param cls_on
 * @param cla_off
 * @param clicked_id
 * @param extend
 * @return
 */
function tab_toggle(div_id, cls_on, cla_off, clicked_id,extend) {
	$("#" + div_id + " ul li").click( function() {
		if ($(this).attr('class') == cla_off) {
			if ($(this).siblings().attr('class') == cls_on) {
				$(this).siblings().removeClass(cls_on).addClass(cla_off)
			}
			$(this).attr('class', cls_on);
		} else {
			return true;
		}
		 this.extend=extend?extend:"";
		var file = $(this).attr('id') + '.html'+"?"+this.extend;
		$('#targetFrame').attr('src', file);
	})
	$('#' + clicked_id).click();
}

