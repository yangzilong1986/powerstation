if (typeof const_select == "undefined")
	const_select = new Object();
const_select = [ {
	id :'orgNo',
	select : {
		extend :'id="orgNo" name="orgNo"',
		option : [ {
			value :44,
			label :'广东省电力公司'
		}, {
			value :4401,
			label :'-- 广州市电力公司'
		}, {
			value :4402,
			label :'-- 韶关市电力公司'
		}, {
			value :4403,
			label :'-- 深圳市电力公司'
		}, {
			value :4404,
			label :'-- 珠海市电力公司'
		}, {
			value :4405,
			label :'-- 汕头市电力公司'
		}, {
			value :4406,
			label :'-- 佛山市电力公司'
		}, {
			value :4407,
			label :'-- 江门市电力公司'
		}, {
			value :4408,
			label :'-- 湛江市电力公司'
		}, {
			value :440806,
			label :'-- -- 遂溪供电局'
		}, {
			value :440807,
			label :'-- -- 徐闻供电局'
		}, {
			value :440808,
			label :'-- -- 吴川供电局'
		}, {
			value :440809,
			label :'-- -- 廉江供电局'
		}, {
			value :440810,
			label :'-- -- 雷州供电局'
		}, {
			value :4409,
			label :'-- 茂名市电力公司'
		}, {
			value :4412,
			label :'-- 肇庆市电力公司'
		}, {
			value :4413,
			label :'-- 惠州市电力公司'
		}, {
			value :4414,
			label :'-- 梅州市电力公司'
		}, {
			value :4415,
			label :'-- 汕尾市电力公司'
		}, {
			value :4416,
			label :'-- 河源市电力公司'
		}, {
			value :4417,
			label :'-- 阳江市电力公司'
		}, {
			value :4418,
			label :'-- 清远市电力公司'
		}, {
			value :4419,
			label :'-- 东莞市电力公司'
		}, {
			value :4420,
			label :'-- 中山市电力公司'
		}, {
			value :4421,
			label :'-- 潮州市电力公司'
		}, {
			value :4422,
			label :'-- 揭阳市电力公司'
		}, {
			value :4423,
			label :'-- 云浮市电力公司'
		} ]
	}
}, {
	id :'curStatus',
	select : {
		extend :'id="curStatus" name="curStatus"',
		option : [ {
			value :-1,
			label :'全部'
		}, {
			value :1,
			label :'运行'
		}, {
			value :2,
			label :'待调'
		}, {
			value :6,
			label :'故障'
		}, {
			value :7,
			label :'停运'
		} ]
	}
}
/**
 * 当前状态1
 */
, {
	id :'curStatus1',
	select : {
		extend :'id="curStatus1" name="curStatus1"',
		option : [ {
			value :1,
			label :'运行'
		}, {
			value :2,
			label :'待调'
		}, {
			value :6,
			label :'故障'
		}, {
			value :7,
			label :'停运'
		} ]
	}
}

/**
 * 通讯方式
 */
//----------------------------start------------------------------------------
, {
	id :'commMode',
	select : {
		extend :'id="commMode" name="commMode"',
		option : [ {
			value :-1,
			label :'全部'
		},{
			value :1,
			label :'GPRS'
		}, {
			value :2,
			label :'CDMA'
		}, {
			value :3,
			label :'230M'
		}, {
			value :4,
			label :'232串口'
		}, {
			value :5,
			label :'有线网络'
		}, {
			value :6,
			label :'短信'
		}, {
			value :7,
			label :'电力专线'
		} ]
	}
} 
/**
 * 通讯方式1
 */
//----------------------------start------------------------------------------
, {
	id :'commMode1',
	select : {
		extend :'id="commMode1" name="commMode1"',
		option : [ {
			value :1,
			label :'GPRS'
		}, {
			value :2,
			label :'CDMA'
		}, {
			value :3,
			label :'230M'
		}, {
			value :4,
			label :'232串口'
		}, {
			value :5,
			label :'有线网络'
		}, {
			value :6,
			label :'短信'
		}, {
			value :7,
			label :'电力专线'
		} ]
	}
} 
//-------------------------------end---------------------------------------------
/**
 * 终端厂家
 */
//----------------------------start------------------------------------------
, {
	id :'divFactory',
	select : {
		extend :'id="divFactory" name="divFactory"',
		option : [{
			value :0,
			label :'全部'
		}, {
			value :1,
			label :'杭州百富电子'
		}, {
			value :2,
			label :'北京双电有限公司'
		}, {
			value :3,
			label :'万达信息'
		}, {
			value :4,
			label :'广州科立'
		}, {
			value :5,
			label :'北京煜邦电力技术有限公司'
		}, {
			value :6,
			label :'长沙威胜信息技术有限公司'
		}, {
			value :7,
			label :'南京瑞电有限公司'
		},{
			value :8,
			label :'上海华冠电子设备有限责任公司'
		},{
			value :9,
			label :'浙江电力高技术开发有限公司'
		},{
			value :10,
			label :'威斯顿'
		},{
			value :11,
			label :'天津光电有限公司'
		},{
			value :12,
			label :'北京中电飞华电子技术有限公司'
		},{
			value :13,
			label :'科陆'
		},{
			value :14,
			label :'浙江华立电子技术有限公司'
		},{
			value :15,
			label :'银川银光'
		}]
	}
}
//-------------------------------end---------------------------------------------
/**
 * 终端厂家1
 */
//----------------------------start------------------------------------------
, {
	id :'divFactory1',
	select : {
		extend :'id="divFactory1" name="divFactory1"',
		option : [{
			value :1,
			label :'杭州百富电子'
		}, {
			value :2,
			label :'北京双电有限公司'
		}, {
			value :3,
			label :'万达信息'
		}, {
			value :4,
			label :'广州科立'
		}, {
			value :5,
			label :'北京煜邦电力技术有限公司'
		}, {
			value :6,
			label :'长沙威胜信息技术有限公司'
		}, {
			value :7,
			label :'南京瑞电有限公司'
		},{
			value :8,
			label :'上海华冠电子设备有限责任公司'
		},{
			value :9,
			label :'浙江电力高技术开发有限公司'
		},{
			value :10,
			label :'威斯顿'
		},{
			value :11,
			label :'天津光电有限公司'
		},{
			value :12,
			label :'北京中电飞华电子技术有限公司'
		},{
			value :13,
			label :'科陆'
		},{
			value :14,
			label :'浙江华立电子技术有限公司'
		},{
			value :15,
			label :'银川银光'
		}]
	}
}
//-------------------------------end---------------------------------------------
/**
 * 电压等级
 */
//----------------------------start------------------------------------------
, {
	id :'voltGrade',
	select : {
		extend :'id="voltGrade" name="voltGrade"',
		option : [{
			value :1,
			label :'110kV'
		}, {
			value :2,
			label :'220kV'
		}, {
			value :3,
			label :'500kV'
		}, {
			value :4,
			label :'10kV'
		}, {
			value :5,
			label :'35kV'
		}, {
			value :6,
			label :'380V'
		}, {
			value :7,
			label :'220V'
		} ]
	}
} 
//-------------------------------end---------------------------------------------
/**
 * 计量方式
 */
//----------------------------start------------------------------------------
, {
	id :'measMode',
	select : {
		extend :'id="measMode" name="measMode"',
		option : [{
			value :1,
			label :'高供高计'
		}, {
			value :2,
			label :'高供低计'
		}, {
			value :3,
			label :'低供低计'
		} ]
	}
} 
//-------------------------------end---------------------------------------------
/**
 * 供电线路
 */
//----------------------------start------------------------------------------
, {
	id :'lineNo',
	select : {
		extend :'id="lineNo" name="lineNo"',
		option : [{
			value :1,
			label :'白沙主干线'
		}, {
			value :2,
			label :'合兴主干线路'
		}, {
			value :3,
			label :'调罗线'
		}, {
			value :4,
			label :'康港线'
		}, {
			value :5,
			label :'里港线'
		}, {
			value :6,
			label :'城内北门线'
		}]
	}
} 
//-------------------------------end---------------------------------------------
/**
 * 变电站
 */
//----------------------------start------------------------------------------
, {
	id :'subsNo',
	select : {
		extend :'id="subsNo" name="subsNo"',
		option : [{
			value :-1,
			label :'全部'
		},{
			value :1,
			label :'220kV霞山站'
		}, {
			value :2,
			label :'110kV宝满站'
		}, {
			value :3,
			label :'220kV椹北站'
		}, {
			value :4,
			label :'110kV良丰站'
		}, {
			value :5,
			label :'110kV官渡站'
		}, {
			value :6,
			label :'110kV滨海站'
		}]
	}
} 
//-------------------------------end---------------------------------------------
/**
 * 大小比较
 */
//----------------------------start------------------------------------------
, {
	id :'equal',
	select : {
		extend :'id="equal" name="equal"',
		option : [{
			value :1,
			label :'>'
		}, {
			value :2,
			label :'='
		}, {
			value :3,
			label :'<'
		}]
	}
} 
//-------------------------------end---------------------------------------------
/**
 * 日，月，年，任意
 */
//----------------------------start------------------------------------------
, {
	id :'dataAll',
	select : {
		extend :'id="dataAll" name="dataAll"',
		option : [{
			value :1,
			label :'日'
		}, {
			value :2,
			label :'月'
		}, {
			value :3,
			label :'年'
		}, {
			value :4,
			label :'任意'
		}]
	}
} 
//-------------------------------end---------------------------------------------
/**
 * 计量点名称
 */
//----------------------------start------------------------------------------
, {
	id :'GpName',
	select : {
		extend :'id="GpNames" name="GpNames"',
		option : [{
			value :1,
			label :'省网关口'
		}, {
			value :2,
			label :'上网计量点'
		}, {
			value :3,
			label :'旁路'
		}, {
			value :4,
			label :'钱江I线'
		}]
	}
} 
//-------------------------------end---------------------------------------------
/**
 * 日期下拉框-月
 */
//----------------------------start------------------------------------------
, {
	id :'Month',
	select : {
		extend :'id="selectMonth" name="selectMonth"',
		option : [{
			value :1,
			label :'01'
		}, {
			value :2,
			label :'02'
		}, {
			value :3,
			label :'03'
		}, {
			value :4,
			label :'04'
		}, {
			value :5,
			label :'05'
		}, {
			value :6,
			label :'06'
		}, {
			value :7,
			label :'07'
		}, {
			value :8,
			label :'08'
		}, {
			value :9,
			label :'09'
		}, {
			value :10,
			label :'10'
		}, {
			value :11,
			label :'11'
		}, {
			value :12,
			label :'12'
		}]
	}
} 
//-------------------------------end---------------------------------------------
/**
 * 日期下拉框-年
 */
//----------------------------start------------------------------------------
, {
	id :'Year',
	select : {
		extend :'id="selectYear" name="selectYear"',
		option : [{
			value :2000,
			label :'2000'
		}, {
			value :2001,
			label :'2001'
		}, {
			value :2002,
			label :'2002'
		}, {
			value :2003,
			label :'2003'
		}, {
			value :2004,
			label :'2004'
		}, {
			value :2005,
			label :'2005'
		}, {
			value :2006,
			label :'2006'
		}, {
			value :2007,
			label :'2007'
		}, {
			value :2008,
			label :'2008'
		}, {
			value :2009,
			label :'2009'
		}, {
			value :2010,
			label :'2010'
		}, {
			value :2011,
			label :'2011'
		}, {
			value :2012,
			label :'2012'
		}]
	}
} 
//-------------------------------end---------------------------------------------

/**
 * 输入、输出
 */
//----------------------------start------------------------------------------
, {
	id :'io',
	select : {
		extend :'id="io" name="io"',
		option : [{
			value :1,
			label :'输入'
		}, {
			value :2,
			label :'输出'
		}]
	}
} 
//-------------------------------end---------------------------------------------
/**
 * 计量方向
 */
//----------------------------start------------------------------------------
, {
	id :'direct',
	select : {
		extend :'id="direct" name="direct"',
		option : [{
			value :1,
			label :'正向'
		}, {
			value :2,
			label :'反向'
		}]
	}
} 
//-------------------------------end---------------------------------------------
/**
 * 台区名称
 */
//----------------------------start------------------------------------------
, {
	id :'tg',
	select : {
		extend :'id="tg" name="tg"',
		option : [{
			value :1,
			label :'清水台区'
		}, {
			value :2,
			label :'大亚湾台区'
		}, {
			value :3,
			label :'茅园村台区'
		}, {
			value :4,
			label :'甲子园台区'
		}, {
			value :5,
			label :'跳水台区'
		}]
	}
} 
//-------------------------------end---------------------------------------------
]

//-------------------------------------------------------------------------------
/**
 * function
 */
// Avoid collisions
;
if (window.jQuery)
	( function($) {
		// Add function to jQuery namespace
		$.extend( {
			handelCombox : function(nodeId, dataId,custom, extended) {
				if (nodeId == undefined || dataId == undefined) {
					return {};
				}
				
				var bolcustom= custom==undefined ? false : true;
				
				$.each(const_select, function(i, item) {
					if (item.id == dataId) {
						var extend=" ";
						if(!bolcustom){
						 extend += item.select.extend ? item.select.extend : "";
						}else{
							 extend += custom;
						}
						var sel = "<select" + extend + ">";
						$.each(item.select.option, function(j, sig) {
							var value = sig.value ? sig.value : "";
							var label = sig.label ? sig.label : "";
							sel += "<option value=\"" + value + "\">" + label + "</option>"
						})
						sel += "</select>"
							$("#"+nodeId).prepend($(sel));
						// break;
						return false;
					}
				})
			},
			handelInput : function(nodeId, dataId, extended) {
			}
		}); // extend $
	})(jQuery);
//-------------------------------------------------------------------------------