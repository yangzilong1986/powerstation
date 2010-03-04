//*************************打开窗口*****************************************************//
var opwindow = null;     //记录打开浏览窗口的对象
function windowPopup(url, wd, ht) {
    if(opwindow != null) {
        opwindow.close();
    }
    opwindow = open(url,'','height='+ht+',width='+wd+',top='+(screen.availHeight-ht)/2+', left='+(screen.availWidth-wd)/2+', toolbar=no, menubar=no, scrollbars=auto, resizable=no, location=no, status=yes');
}
//**********************动态增加，删除，切换tab页****************************************************************//
var tabCounter = 1; //tab页数
var tabIdArray=new Array();//存放tabID

function addTab(){
    if(tabCounter==1){
    	$('#container').html("<span class='tab_on' id='tab"+tabCounter+"'><a href='javascript:changeTab("+tabCounter+")'>电表信息"+tabCounter+"</span></a>");
    	tabCounter++;
    }
	$('#container').append("<span class='tab_off' id='tab"+tabCounter+"'><a href='javascript:changeTab("+tabCounter+")'>电表信息"+tabCounter+"</span></a>");
    tabCounter++;
}

function delTab(){
  if(tabCounter==2 || tabCounter==1){
	  alert("无法删除!");
  }else{
	  if(confirm("是否确定删除该表")){
			$('#container .tab_on').remove();
			tabCounter--;
		}  
  }
}
//切换tab页
function changeTab(id){
  $('#container .tab_on').attr("class","tab_off");
  var tabNum="#"+"tab"+id;
  $(tabNum).attr("class","tab_on");
	
  //清空表单信息
  $('#assetNo').val('');
  $('#mpSn').val('');
  $('#mpAddr').val('');
  $('#madeFacTar').val('');
  $('#meterDigits').val('');
  $('#installDate').val('');
}
$(function() {
    $('#addMeter').click(addTab);
    $('#delMeter').click(delTab);
    
});