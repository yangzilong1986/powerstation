//***********************切换表头**************************************************/
var theadBigCust="<tr><th width='30'>序号</th><th width='45'nowarp>明细</th><th width='80'>供电单位</th><th width='85'>户号</th><th width='115'>户名</th><th width='85'>变压器号</th><th width='75'>变压器名</th><th width='75'>表号</th><th width='90'>变压器容量(kVA)</th><th width='95'>最大负荷(kW)</th><th width='95'>最大负荷时间</th><th width='80'>最大负载率(%)</th><th width='75'>平均负荷(kW)</th><th width='80'>平均负载率(%)</th></tr>";
var theadTg="<tr><th>序号</th><th>明细</th><th>供电单位</th><th>台区号</th><th>台区名</th><th>变压器号</th><th>变压器名称</th><th>变压器容量(kVA)</th><th>表号</th><th>最大负荷</th><th>最大负荷时间</th><th>最大负载率(%)</th><th>平均负荷(kW)</th><th>平均负载率(%)</th></tr>";
var theadDayBigCust="<tr><th></th><th>供电单位(用电类别)</th><th>参与统计户数</th><th>合格户数</th><th>不合格户数</th><th>合格率(%)</th></tr>";
var theadDayTG="<tr><th>序号</th><th>供电单位</th><th>参与统计户数</th><th>合格户数</th><th>不合格户数</th><th>合格率(%)</th></tr>";
var theadAnyTimeBigCust="<tr><th>序号</th><th>供电单位(用电类别)</th><th>日均参与统计户数</th><th>日均合格户数</th><th>日均不合格户数</th><th>合格率(%)</th></tr>";
var theadAnyTimeTG="<tr><th>序号</th><th>供电单位</th><th>日均参与统计户数</th><th>日均合格户数</th><th>日均不合格户数</th><th>合格率(%)</th></tr>";
var theadLoadBigCust="<tr><th>序号</th><th>供电单位</th><th>户号</th><th>户名</th><th>变压器号</th><th>变压器名</th><th>表号</th><th>最大负荷</th><th>最大负荷时间</th><th>平均负荷(kW)</th><th>负荷率(%)</th><th>明细</th></tr>";
var theadLoadTg="<tr><th>序号</th><th>供电单位</th><th>台区号</th><th>台区名</th><th>变压器号</th><th>变压器名称</th><th>表号</th><th>最大负荷</th><th>最大负荷时间</th><th>平均负荷(kW)</th><th>负荷率(%)</th><th>明细</th></tr>";
var theadElecThreePhaseCust="<tr><th width='4%'>序号</th><th width='5%'>电流明细</th><th width='6%'>供电单位</th><th width='8%'>户号</th><th width='10%'>户名</th><th width='8%'>变压器号</th><th width='8%'>变压器名</th><th width='8%'>表号</th><th width='10%'>不平衡累计时间(分)</th><th width='7%'>不平衡次数</th><th width='8%'>最大不平衡率(%)</th><th width='8%'>平均不平衡率(%)</th><th width='8%'>最小不平衡率(%)</th></tr>";
var theadElecThreePhaseTg="<tr><th>序号</th><th>电流明细</th><th>供电单位</th><th>台区号</th><th>台区名</th><th>变压器号</th><th>变压器名</th><th>表号</th><th>时间</th><th>不平衡累计时间(分)</th><th>不平衡次数</th><th>最大不平衡率(%)</th><th>平均不平衡率(%)</th><th>最小不平衡率(%)</th></tr>";
function switchThead(objectType){//负载率
	if(objectType==1){
		$("#DATA_THEAD").html(theadBigCust);
	}else if(objectType==2){
		$("#DATA_THEAD").html(theadTg);
	}
} 

function switchTheadLoadPass(objectType,dateType){//负载率合格率
	if(objectType==1){//专变
		if(dateType==5){
			$("#DATA_THEAD").html(theadAnyTimeBigCust);	
		}else{
			$("#DATA_THEAD").html(theadDayBigCust);
		}
	}else if(objectType==2){
		if(dateType==5){
			$("#DATA_THEAD").html(theadAnyTimeTG);	
		}else{
			$("#DATA_THEAD").html(theadDayTG);
		}
	}
	
}

function switchTheadLoadElec(objectType){//负荷率
	if(objectType==1){
		$("#DATA_THEAD").html(theadLoadBigCust);
	}else if(objectType==2){
		$("#DATA_THEAD").html(theadLoadTg);
	}
}

function switchTheadElecThree(objectType){ //电流三相不平衡率
	if(objectType==1){
		$("#DATA_THEAD").html(theadElecThreePhaseCust);
	}else if(objectType==2){
		$("#DATA_THEAD").html(theadElecThreePhaseTg);
	}
}

//展开事件
function _myChange(numb,size){
	for(var i=1;i<size;i++){
		$("#DATA_TBODY_table_tr_"+(numb + i)).toggle('fast');	
	}
	// change img
	//var flip = 0;
	if($("#img"+numb).attr("src").indexOf('minus1')!=-1){
		$("#img"+numb).attr("src","../../img/tree2/plus1.gif");
	}else{
		$("#img"+numb).attr("src","../../img/tree2/minus1.gif");
	}
}
