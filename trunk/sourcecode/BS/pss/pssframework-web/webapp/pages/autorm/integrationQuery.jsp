<% /** 综合查询页面  */  %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<title>综合查询</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css"/>
<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/prototype/prototype.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/multiframe.js"></script>
<script type="text/javascript">

/** 是否是主控窗口 */
_blnControllerWindow = true;
/* 总控窗体 */
var _mainControllerWindow;

var contextPath = "<peis:contextPath/>";
var _page_prefix = 'mf_page_';

/** 顶层框架集保存的信息  */
var _myPageInfo = {
	/** 顶层保存当前的custId */
	currCustId:null,
	/** 顶层保存当前的custNo */
	currCustNo:null,
	currOrgNo:null
};

/** 获得当前的用户编号 */
function getCurrCustNo() {
	return _myPageInfo.currCustNo;	
}
/** 设置当前的用户编号 */
function setCurrCustNo(sCustNo) {
	_myPageInfo.currCustNo = sCustNo;	
}

/** 获得当前的单位编号 */
function getCurrOrgNo() {
	return _myPageInfo.currOrgNo;	
}
/** 设置当前的单位编号 */
function setCurrOrgNo(sOrgNo) {
	_myPageInfo.currOrgNo = sOrgNo;
}


document.observe('dom:loaded', function(){
	/** 主控窗口就是本身 */
	_mainControllerWindow = window;
	/** 初始化页面 */
	_mf = new Eric.MultiFrame($('multiframe_work_area'));
	
	var index = 0;
	$A($('nav_ul').descendants()).each(function(li){
		var a = li.down();
		if(a !=  null) {
			if(index++ == 0 )
				li.addClassName("tab_on");
			else
				li.addClassName("tab_off");
		}
	});

	/* 监听点击事件 */
	$('nav_area').observe('click', eventOpenFramePage);	// 通用按钮点击事件

	/* 默认打开工作平台页面 */
	eventOpenFramePage(new MyMockEvent('baseInfo'));
});


/** 打开指定事件的工作页面 */
function eventOpenFramePage(eventTag) {

	/** 控制标识。表示该页面是否每次要重新打开  */
	var falg = false;
	var event = eventTag||window.event;
	event.stop();
	var element = event.element();
	if (!element || element.tagName.toLowerCase() !== 'a') {
		return;
	}
	// 如果按钮当前没有激活，不操作
	if (element.disabled) {
		return;
	}

	/** 如果custNo编号为空，不容许打开其他的标签 */
	var custNo = _mainControllerWindow.getCurrCustNo();

	if(element.id!='baseInfo' && custNo.blank()){
		alert('用户编号不存在,请先查询基本信息！！！！！');
		return;
	}

	
	if(element.up(0).hasClassName("tab_off")){
		element.up(0).removeClassName("tab_off");
		element.up(0).addClassName("tab_on");
	}
	element.up(0).siblings().each(function(li){
		if(li.hasClassName('tab_on')){
			li.removeClassName("tab_on");
			li.addClassName("tab_off")
			return false;
		}
	})
	
	if(element.id!='baseInfo')
		falg = true;

	if(element.id == 'eventData' && element.name.charAt(element.name.length-1)=="=")
		element.name = element.name + '' +  custNo;
		
	
	_mf.openFramePage(new Eric.FramePage({src:contextPath+element.name, id:_page_prefix+element.id}),falg);
}



/** Mock Event */
function MyMockEvent(element) {
	this.srcElement = $(element);
}
MyMockEvent.prototype = {
	element: function() {
		return this.srcElement;
	},
	stop: function() {}
};


</script>
</head>
<body>
  <div id="body">
    <div id="nav_area" class="tab2">
      <ul id="nav_ul">
        <li><a id="baseInfo" name="/jsp/autorm/singleUserQuery.jsp">基本信息</a></li>
        <li><a id="meterdayData" name="/jsp/autorm/meterDayData.jsp">表码数据</a></li>
        <li><a id="powerdayData" name="/jsp/autorm/powerDayData.jsp">功率数据</a></li>
        <li><a id="voltdayData" name="/jsp/autorm/voltDayData.jsp">电压数据</a></li>
        <li><a id="ecurdayData" name="/jsp/autorm/ecurDayData.jsp">电流数据</a></li>
        <li><a id="pfdayData" name="/jsp/autorm/pfDayData.jsp">功率因数</a></li>
        <li><a id="elecQuantityData" name="/jsp/autorm/elecQuantity4singleQuery.jsp">电量数据</a></li>
        <li><a id="eventData" name="/singleUserQuery.do?action=queryEvent&object_no=">事件数据</a></li>
        <li class="clear"></li>
      </ul>
    </div>
    <div id="main" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 44));"> 
      <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr>
          <td>
          	<div id="multiframe_work_area">
			</div><!-- multiframe_work_area -->
            <!--  iframe id="mainObj" src="<peis:contextPath/>/jsp/autorm/singleUserQuery.jsp" width="100%" height="100%" frameborder="0" scrolling="no"></iframe-->
          </td>
        </tr>
      </table>
    </div>
  </div>
</body>
</html>