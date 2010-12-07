<% /** 数据检查的frames页面 */ %>
<%@ page language="java" pageEncoding="UTF-8" %>

 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据检查</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css"/>
<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/prototype/prototype.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/multiframe.js"></script>
<script type="text/javascript">

/** 是否是主控窗口 */
_blnControllerWindow = true;
/* 总控窗体 */
var _mainControllerWindow;

var contextPath = "<peis:contextPath/>";
var _mf;				//主
var _page_prefix = 'mf_page_';

/** 当前的工作方式 ,默认情况是新增数据 */
var cur_method = "add";

/** 设置当前的工作方式 （新增或者编辑）*/
function setMethod(method){
	cur_method = method;
}
/** 获得当前的工作方式 （新增或者编辑）*/
function getCurMethod(){
	return cur_method;
}


/** 顶层框架集保存的信息  */
var _myPageInfo = {
	assetNo:null, //表号
	dDate:null,	  //数据日期
	dataDens:null	//数据密度
};

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

	if(element.up(0).hasClassName("tab_off")){
		element.up(0).removeClassName("tab_off");
		element.up(0).addClassName("tab_on");
	}
	element.up(0).siblings().each(function(li){
		if(li.hasClassName('tab_on')){
			li.removeClassName("tab_on");
			li.addClassName("tab_off");
			return false;
		}
	});

	_mf.openFramePage(new Eric.FramePage({src:contextPath+element.name, id:_page_prefix+element.id}),falg);
}


function openCallbackFramePage(para){
	openFramePage(new MyMockEvent($('baseInfo')),'work_main_nav',true, para);
}



/**
 * 通用多窗体页面加载页面函数
 * 	event	-- 事件对象
 * 	idPrefix-- id编号增补的前缀
 */
function openFramePage(event,idPrefix,overridable,para) {
	event.stop();
	var element = event.element();
	if (!element || element.tagName.toLowerCase() !== 'a') {
		return;
	}
	var str = '';
	if(para){
		str = para;
	}
	/* 打开页面，重置选择的样式 */
	_mf.openFramePage(new Eric.FramePage({src:contextPath+element.name+"&"+str, id:getPageId(element, idPrefix)}), overridable);
	
	if(element.up(0).hasClassName("tab_off")){
		element.up(0).removeClassName("tab_off");
		element.up(0).addClassName("tab_on");
	}
	element.up(0).siblings().each(function(li){
		if(li.hasClassName('tab_on')){
			li.removeClassName("tab_on");
			li.addClassName("tab_off");
			return false;
		}
	});
	
}

function getPageId(element, idPrefix) {
	return idPrefix + element.id;
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
        <li><a id="baseInfo" name="/do/autorm/DataInputQuery.do?action=queryCustInfo">数据录入</a></li>
      	<!-- 数据修正链接  -->
        <li><a id="iptRecQuery" name="/do/autorm/DataIptRecListQuery.do?action=query&page=1&pageSize=20" onfocus="blur()">录入查询</a></li>
        <li class="clear"></li>
      </ul>
    </div>
    <div id="main" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 44));"> 
      <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr>
          <td>
          	<div id="multiframe_work_area">
			</div><!-- multiframe_work_area -->
          </td>
        </tr>
      </table>
    </div>
  </div>
</body>
</html>
