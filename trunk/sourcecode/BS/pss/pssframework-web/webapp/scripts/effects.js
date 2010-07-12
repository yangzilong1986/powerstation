/*---------------------------------------SwitchTab------------------------------------------*/
function SwitchTab(IDprefix,order,TagAmount,status){
	switch (status){
		case "S1":
			var TagObj=OOO(IDprefix+"Option","li");
			var TagArr=new Array();
			for (var i=0;i<TagObj.length;i++){
				TagArr.push(TagObj[i].id.split("_Option_")[1]);
			}
			for (var i=0;i<TagArr.length;i++){
				OOO(IDprefix+"Option_"+TagArr[i]).className=(TagArr[i]==order)?"curr":"";
				OOO(IDprefix+"Con_"+TagArr[i]).style.display=(TagArr[i]==order)?"":"none";
			}
			break;
		default:
			for (var i=0;i<TagAmount;i++){
				OOO(IDprefix+"Option_"+i).className=(i==order)?"curr":"";
				OOO(IDprefix+"Con_"+i).className=(i==order)?"default":"disNone";
			}
			return;
	}
}

/*---------------------------------------showSort------------------------------------------*/
function showSort(obj){
	$("#"+obj+" h3").click(function(){
		this.className=(this.className=="open")?"":"open";
		$("#EFF_ul_"+this.id.substr(7)).get(0).className=(this.className=="open")?"open":"";
	});
}
/*---------------------------------------OOO------------------------------------------*/
function OOO(obj,ele){
	if (obj&&!ele){return document.getElementById(obj);}
	else if (obj&&ele){return document.getElementById(obj).getElementsByTagName(ele);}
	else {return false;}
}
