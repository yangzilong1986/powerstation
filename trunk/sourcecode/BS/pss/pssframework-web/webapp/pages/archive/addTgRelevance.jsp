<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>台区档案录入</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">
//弹出式窗口
var opwindow = null;     //记录打开浏览窗口的对象
function windowPopup(url, wd, ht) {
    if(opwindow != null) {
        opwindow.close();
    }
    opwindow = open(url,'','height='+ht+',width='+wd+',top='+(screen.availHeight-ht)/2+', left='+(screen.availWidth-wd)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
}

var contextPath  = '${ctx}';
//打开新增变压器页面
function openTransformer(tgId){
   var url = "${ctx}/archive/traninfo/new?tgId="+tgId;
   windowPopup(url, 960, 575);
}
//打开新增总表页面
function openTotalMeter(tgId){
	var url = contextPath + '/archive/totalMeter?_method=create';
    windowPopup(url, 960, 575);
}
//打开新增终端页面
function openTerm(tgId){
   var url = contextPath + "/archive/terminalinfo/new?tgId=" + tgId;
   windowPopup(url, 960, 575);
} 
//上一步
function lastStep(){
  var tgCustFlag=jQuery("input[name='tgCustFlag']").val();
  if(tgCustFlag=="1"){//配变台区编辑
    //window.location.href = contextPath + "/archive/tgListQuery1.do?action=normalMode&sqlCode=AL_ARCHIVE_0028&pageRows=20";
     //window.location.href = contextPath + "/jsp/archive/selectTg.jsp";
     history.back();
  }else{//配变台区录入
    var tgId=jQuery("input[name='tgId']").val();
    window.location.href=contextPath+"/archive/addTgAction.do?action=showtginfoByTgID&tgId="+tgId;
  }
}

//完成
function finish(){
   var tgCustFlag=jQuery("input[name='tgCustFlag']").val();
   if(tgCustFlag=="1"){//配变台区编辑
     var url=contextPath+"/archive/addTgAction.do?action=addtginfo";
      if(tgCheck()){
        jQuery("#finish").attr("disabled",true);
        var data = getFormData("form");
        if(data){
            jQuery.ajax({
                type:'post',
                url:url,
                data:data,
                dataType:'json',
                success:function(json){
                    var msg=json['msg'];
                    jQuery("#finish").attr("disabled",false);
                    if(msg=="1"){
                      if(confirm("是否继续维护台区")==true){
                         //window.location.href = contextPath + "/archive/tgListQuery1.do?action=normalMode&sqlCode=AL_ARCHIVE_0028&pageRows=20";
                         window.location.href = contextPath + "/jsp/archive/selectTg.jsp";
                      }else{
                          var url1=contextPath+"/archive/commAction.do?action=clearSessionByTg"; //清除session
                          jQuery.ajax({
                            url: url1,
                            cache: false,
                            success: function(html) {
                              //window.location.href=contextPath+"/jsp/archive/tgTypeUpdate.jsp";
                              window.location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=tgTypeUpdate&selectType=0";
                            }
                          });
                      }
                    }else if(msg=="2"){
                       alert("该台区已经存在");
                    }else{
                       alert("保存失败");
                    }
                }
            });
        }
      }
   }else{//配变台区录入
      if(confirm("是否继续新增台区")==true){
         var url1=contextPath+"/archive/commAction.do?action=clearSessionByTg"; //清除session
         jQuery.ajax({
           url: url1,
           cache: false,
           success: function(html) {
             window.location.href=contextPath+"/jsp/archive/addtginfo.jsp";
           }
         });
      }else{
         //window.location.href=contextPath+"/jsp/archive/tgType.jsp"; 
         window.location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=tgType&selectType=1";
      }
   }
}
//打开变压器编辑页面
function showTran(tranId){
   var url=contextPath+"/archive/tranAction.do?action=showTranByLowCustNew&tranId="+tranId+"&tgFlag=1";
   windowPopup("变压器信息编辑",url, 575, 960);
}
//打开总表编辑页面
function showMeter(mpId,gpId){
   var url=contextPath+"/archive/meterAction.do?action=showMeterByLowCustNew&mpId="+mpId+"&gpId="+gpId+"&tgFlag=1";
   windowPopup("总表信息编辑",url, 575, 960);
}
//编辑终端
function showTerminal(termId,termType){
   if(termType=="03" || termType=="04"){ //配变终端
      var url=contextPath+"/archive/terminalAction3.do?action=addTermInit&termId="+termId+"&tgFlag=1&r=" + parseInt(Math.random() * 1000);
      windowPopup("配变终端信息编辑",url, 575, 960);
   }else{//集中器
      var url=contextPath+"/jsp/archive/lowCustTermFrame.jsp?termId="+termId+"&termTypeFlag=1&r=" + parseInt(Math.random() * 1000);
      windowPopup("集中器信息编辑",url, 575, 960);
   }
}
//删除变压器
   function delteTran(tranId){
     var url=contextPath+"/archive/tranAction.do?action=deleteTran&tranId="+tranId;
     if(confirm("确定要删除该变压器?")){
        jQuery.ajax({
          url: url,
          dataType:'json',
          cache: false,
          success: function(json){
            var msg=json['msg'];
             if(msg=="1"){
              alert("删除成功");
               //window.location.href=contextPath+"/archive/addTgAction.do?action=showDeviceInfoByTgID";
                //动态加载台区关联设备信息
				loadTgRelevevance();
              }else if(msg=="3"){
                  alert("变压器下存在终端（集中器）或电表，不允许删除");
              }
              else{
                 alert("删除失败");
              }
          }
        });
        
     }
   }
  //删除总表
   function delMeter(mpId){
     var url=contextPath+"/archive/meterAction.do?action=deleteTotalMeter&mpId="+mpId;
     if(confirm("确定要删除该总表?")){
       jQuery.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
              var msg=json['msg'];
               if(msg=="1"){
                alert("删除成功");
                //window.location.href=contextPath+"/archive/addTgAction.do?action=showDeviceInfoByTgID";
                 //动态加载台区关联设备信息
				loadTgRelevevance();
                }
                else{
                   alert("删除失败");
                }
            }
       });
     }
   }
  
//控件样式初始化
function inputAndSelectInit(){
var tgCustFlag=jQuery("input[name='tgCustFlag']").val();
 if(tgCustFlag=="1"){//配变台区编辑
   jQuery("input").attr("disabled","disabled").each(function(){
       jQuery(this).attr("disabled","");
   });
   jQuery("select").attr("disabled","disabled").each(function(){
       jQuery(this).attr("disabled","");
   });
   jQuery("textarea").attr("disabled","disabled").each(function(){
         jQuery(this).attr("disabled","");
     });
   //jQuery('#tab_1 > a').html("档案维护第三步");
 }else{
   //jQuery('#tab_1 > a').html("档案录入第三步");
 }
}
//select框初始化
function selectInit(){

   
}
//调用json方法获取list
function getJsonObjectList(htmlId,className,methodName,objectType){
  var objectId=jQuery("input[name='tgId']").val();
  var params = {
                 "objectId":objectId,
                 "methodsName":methodName,
                 "className":className
			   };
   var url=contextPath+"/archive/commAction.do?action=getJsonByListData&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   jQuery.ajax({
        url: url,
        dataType:'json',
        cache: false,
        success: function(json){
           var htmlTbody="";
           jQuery(json).each(function(i){ //遍历结果数组 
               htmlTbody+=buildTbody(objectType,json[i]);
		   }); 
		   jQuery("#"+htmlId).html(htmlTbody);
        }
    });
}
//动态加载台区关联设备信息
function loadTgRelevevance(){
  show_loading();
  getJsonObjectList("tranDataBody","TgService","getTransByTgId","12");
  getJsonObjectList("meterDataBody","TgService","getMeterByTgId","232");
  getJsonObjectList("termDataBody","TgService","getTerminalsByTgId","53");//加载终端列表
  remove_loading();
}

</script>
</head>
<body>

<div class="electric_lcon" id="electric_Con" style="margin: 5px;">
  <ul class=default id=electric_Con_1>
    <div class="tab"><span>台区信息</span></div>
    <div class="da_mid" style="display:block; overflow-y:auto; overflow-x: auto;width: expression((document.documentElement.clientWidth||document.body.clientWidth)-10); height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-35));">
      <div>
        <form:form action="/archive/tginfo" modelAttribute="tginfo">
          <table width="95%" border="0" cellspacing="0" cellpadding="0">
            <tr height="30">
              <td width="15%" align="right" class="green"><font color="red"><form:hidden path="tgId" />* </font>台区编号：</td>
              <td width="20%"><form:input path="tgNo" id="tgNo" cssClass="required input2" maxlength="16" cssStyle="width:145px;" /></td>
              <td width="10%" align="right" class="green">台区名称：</td>
              <td width="20%"><form:input path="tgName" id="tgName" cssClass="required input2" cssStyle="width:145px;" /></td>
              <td width="10%" align="right" class="green">管理单位：</td>
              <td width="25%"><form:select path="orgId" id="orgId" itemLabel="orgName" itemValue="orgId" items="${orglist}" cssStyle="width:145px;"></form:select></td>
            </tr>
            <tr height="30">
              <td width="15%" align="right" class="green">容　　量：</td>
              <td width="20%"><form:input path="tgCap" id="tgCap" cssClass="validate-number input2" cssStyle="width:135px;" /> kVA</td>
              <td width="10%" align="right" class="green">运行状态：</td>
              <td width="20%"><form:select path="runStatusCode" id="runStatusCode" itemLabel="name" itemValue="code" items="${statuslist}" cssStyle="width:145px;" /></td>
              <td width="15%" align="right" class="green">地　　址：</td>
              <td width="25%"><form:input path="instAddr" id="instAddr" cssStyle="width:145px;" /></td>
            </tr>
            <tr>
              <td width="100%" colspan="6" align="right">
                <input id="addTg" name="addTg" type="button" class="btnbg4" value="添加" />
                <input id="updateTg" name="updateTg" type="button" class="btnbg4" value="更新" />
                <input id="deleteTg" name="deleteTg" type="button" class="btnbg4" value="删除" />
              </td>
            </tr>
          </table>
        </form:form>
      </div>
      <div class="tr mgt10"><!-- <a onclick=""><img src='<pss:path type="bgcolor"/>/img/img2_bt.gif' width="15" height="15" /></a> --></div>
      <div class="mgt10">
        <div class="mgt10 da_top"><span>变压器列表信息</span>
          <h1><a onclick="openTransformer('${tginfo.tgId}')"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif' width="16" height="16" style="cursor: pointer;" /></a></h1>
        </div>
        <div class="da_con">
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <thead>
              <tr>
                <th>资产号</th>
                <th>名称</th>
                <th>容量(kVA)</th>
                <th>型号</th>
                <th>安装地址</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="mgt10 da_top"><span>台区考核表信息</span>
          <h1><a href="#"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif' width="16" height="16" /></a></h1>
        </div>
        <div class="da_con">
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <thead>
              <tr>
                <th>资产编号</th>
                <th>计量点名称</th>
                <th>表地址</th>
                <th>采集终端</th>
                <th>表类型</th>
                <th>设备厂家</th>
                <th>运行状态</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr bgcolor="#f3f3f3">
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="mgt10 da_top"><span>保护开关列表信息</span>
          <h1><a href="#"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif' width="16" height="16" /></a></h1>
        </div>
        <div class="da_con">
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <thead>
              <tr>
                <th width="14%">变压器编号</th>
                <th width="14%">变压器名称</th>
                <th width="10%">变压器型号</th>
                <th width="14%">&nbsp;</th>
                <th width="10%">&nbsp;</th>
                <th width="14%">&nbsp;</th>
                <th width="14%">&nbsp;</th>
                <th width="10%">&nbsp;</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr bgcolor="#f3f3f3">
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="mgt10 da_top"><span>集中器列表信息</span>
          <h1><a onclick="openTerm('${tginfo.tgId}')"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif' width="16" height="16" style="cursor: pointer;" /></a></h1>
        </div>
        <div class="da_con">
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <thead>
              <tr>
                <th>资产号</th>
                <th>逻辑地址</th>
                <th>终端类型</th>
                <th>相线</th>
                <th>设备厂家</th>
                <th>运行状态</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr bgcolor="#f3f3f3">
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </ul>
</div>
</body>
<script>
val =  new Validation(document.forms[0],{onSubmit:true,onFormValidate : function(result,form) {
 return result;
}}
);


jQuery(function(){
	jQuery("#addTg").click(function(){
	if(val.validate()){
      jQuery(this).attr("disabled","disabled");
      addtginfo();
      jQuery(this).attr("disabled","");
	}else{
		jQuery(this).attr("disabled","");
	}
  });


	jQuery("#updateTg").click(function(){
		if(val.validate()){
	      jQuery(this).attr("disabled","disabled");
	      updatetginfo();
	      jQuery(this).attr("disabled","");
		}else{
			jQuery(this).attr("disabled","");
		}
	  })
})


getData= function(type){
var data;
if(type == "add"){
  data = jQuery("form[id=tginfo]").not("#tgId").serialize(); 
}else {
	data = jQuery("form[id=tginfo]").serialize(); 
}
return data;
}

addtginfo = function(){
  var tgFromData = getData('add');
  var url="${ctx}/archive/tginfo.json";
  if(confirm("确定要保存该台区?")){
    jQuery.ajax({
         url: url,
         data:tgFromData,
         dataType:'json',
         type:'POST',
         cache: false,
         success: function(json){
           var msg = json['msg'];
           var isSucc = json['isSucc'];
           jQuery("#tgId").val(json['tgId']);
           alert(json['tgId'])
            alert(msg);
         },error:function(e){
             alert("error");
             alert(e.message);
         }
       });
  }
}

updatetginfo = function(){
	var tgFromData = getData("update");
	  var url="${ctx}/archive/tginfo/"+jQuery("#tgId").val()+'.json?_method=put';
	  if(confirm("确定要更新该台区?")){
	    jQuery.ajax({
	         url: url,
	         data:tgFromData,
	         dataType:'json',
	         type:'post',
	         cache: false,
	         success: function(json){
	           var msg=json['msg'];
	           var isSucc = json['isSucc'];
	   
	            alert(msg);
	         },error:function(e){
             alert("error")
	             alert(e.getMessage());
	         }
	       });
	  }
}

</script>
</html>