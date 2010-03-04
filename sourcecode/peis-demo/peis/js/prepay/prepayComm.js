var sendResult =  {'data':[{'logicalAddr':'12080825','success':'','fail':' ','all':' '},
                           {'logicalAddr':'12080826','success':'','fail':' ','all':' '},
                           {'logicalAddr':'12080119','success':'1,2','fail':'3,4','all':'1,2,3,4'},
                           {'logicalAddr':'12080120','success':'1,2','fail':'3,4','all':'1,2,3,4'},
                           {'logicalAddr':'12080122','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080123','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080124','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080125','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080126','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080127','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080128','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080129','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080130','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080131','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080132','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080133','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080134','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080135','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080136','success':'','fail':'','all':'1,2,3,4'},
                           {'logicalAddr':'12080137','success':'','fail':'','all':'1,2,3,4'}]};
//对象全选 全清
function doClickAll() {
	var flag = document.getElementById("chooseAll").checked;
	var items = document.getElementsByName("cb-item");
	for (i = 0; i < items.length; i++) {
		items[i].checked = flag;
	}
}

// 设置结果内容
function setText(idList, content,tdIndex){
	if(tdIndex!=null){
		if (idList != null) {
	        var idArr = idList.split(",");
	        for (var i=0; i < idArr.length; i++){
	            //设置结果内容
	        	var idText="#"+idArr[i]+" "+"td:eq("+tdIndex+")";
	        	$(idText).html(content);
	        }
	    }	
	}else{
		if (idList != null) {
	        var idArr = idList.split(",");
	        for (var i=0; i < idArr.length; i++){
	            //设置结果内容
	        	var idText="#"+idArr[i];
	        	$(idText).html(content);
	        }
	    }	
	}
    
}

//按钮灰掉
function disabledButton() {
  document.getElementById("queryPower").disabled = true;
  document.getElementById("docDown").disabled = true;
  document.getElementById("otherControl").disabled = true;
  document.getElementById("query").disabled = true;
}
//按钮恢复
function enabledButton(){
  document.getElementById("queryPower").disabled = false;
  document.getElementById("docDown").disabled = false;
  document.getElementById("otherControl").disabled = false;
  document.getElementById("query").disabled = false;
	
}
//数组比较
Array.prototype.contains = function(obj) {   
    var boo = false;   
    for(var i=0;i<this.length;i++) {   
        if(typeof obj == "object") {   
            if(this[i].equals(obj)) {   
                boo = true;   
                break;   
            }                          
        }else {   
            if(this[i] == obj)  {   
                   boo = true;   
                   break;                  
            }   
        }              
    }   
    return boo;   
}

function filter(a1, a2) {   
    var myAry = [];   
    var fun = function(item,ary) {   
        var boo = false;   
        for(var i=0;i<ary.length;i++) {   
            if(item==ary[i]) {   
                  boo = true;   
                  break;   
            }   
        }   
        return boo;                                
    }      
    for(var i=0;i<a1.length;i++) {   
        if(!a2.contains(a1[i]))   
            myAry.push(a1[i]);                     
    }              
    return myAry;   
}
function getNoReturnTask(resultObj){
    var logicalAddr = resultObj.logicalAddr;
    var success = resultObj.success;
    var fail = resultObj.fail;
    var all = resultObj.all;
    var allArr = all.split(",");
    noreturn = "";
    for(var j = 0; j < allArr.length; j++) {
        if(((success + ",").indexOf(allArr[j] + ",") < 0) && ((fail + ",").indexOf(allArr[j] + ",") < 0)) {
            noreturn += "," + allArr[j];
        }
    }
    if(noreturn != "") {
        noreturn = noreturn.substring(1);
    }
    return noreturn;
}


function getJqueryId(id,num){
	var jqueryId="";
	if(num!=null){
		jqueryId="#"+id+" "+"td:eq("+num+")";
		return jqueryId;
	}else{
		jqueryId="#"+id;
		return jqueryId;
	}
}

//数据时间切换
function switchDataTime(){
  var dateType=$("#dateType").val();
  if(dateType==1){
     $("#DATA_DAY").show();
     $("#DATA_YEAR").hide();
     $("#DATA_MONTH").hide();
     $("#DATA_ANYTIME").hide();
  }else if(dateType==2){
     $("#DATA_DAY").hide();
     $("#DATA_YEAR").show();
     $("#DATA_MONTH").show();
     $("#DATA_ANYTIME").hide();
  }else if(dateType==5){
     $("#DATA_DAY").hide();
     $("#DATA_YEAR").hide();
     $("#DATA_MONTH").hide();
     $("#DATA_ANYTIME").show();
  }
}

