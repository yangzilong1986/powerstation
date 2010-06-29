<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<%@include file="../common/blockloading.jsp"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="<peis:contextPath/>/js/common/calendar_second.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/task.js"></script>
    <link href="<peis:contextPath/>/css/mainframe.css" rel="stylesheet"type="text/css">
    <script type="text/javascript">
    <logic:present name="addsuccess">
      alert("添加成功！");
      callPreMachine();
    </logic:present>
     
    <logic:present name="modsuccess">
      alert("修改成功！");
      callPreMachine();
    </logic:present>
    
    <logic:present name="delsuccess">
      alert("删除成功！");
      callPreMachine();
    </logic:present>
     
    var task_id1 = "";
    var task_id2 = "";
    <logic:present name="task_id2">
      task_id2 = '<bean:write name="task_id2" scope="request"/>';
    </logic:present>
    var delectInfo = '<bean:message bundle="gather" key="gather.task.info.delete"/>';
    var modifInfo = '<bean:message bundle="gather" key="gather.task.info.modif"/>';
    var nameError = '<bean:message bundle="gather" key="gather.task.info.taskNameEmpty"/>';
    var idError = '<bean:message bundle="gather" key="gather.task.info.taskIdEmpty"/>';
    var sendExecError = '<bean:message bundle="gather" key="gather.task.info.sendExec"/>';
    var execExecError = '<bean:message bundle="gather" key="gather.task.info.execExec"/>';
    var cqblError = '<bean:message bundle="gather" key="gather.task.info.cqbl"/>';
    var sjxError = '<bean:message bundle="gather" key="gather.task.info.sjx"/>';
    var taskNameLength = '<bean:message bundle="gather" key="gather.task.info.taskNameLong"/>';
    var taskIdExist = '<bean:message bundle="gather" key="gather.task.error.taskid"/>';
    
    //调用平台方法
    function callPreMachine(){
        var serverNum = 0;
        var preMachs = parent.document.getElementsByName("preMachTask");
        var machNum = preMachs.length;
        <logic:present name="preMachList">
            <logic:iterate id="datainfo" name="preMachList">
                var ipPort = '<bean:write name="datainfo" property="col1"/>';
                var serverList = '<bean:write name="datainfo" property="col2"/>';
                var serverArr = serverList.split(",");
                for(var i = 0; i < serverArr.length; i++){
                    var strUrl = "http://" + ipPort + "/ig/" + serverArr[i] + ".dow?_MSG_TYP=RELOAD";
                    //alert(strUrl + "," + serverNum + "," + machNum);
                    preMachs[serverNum%machNum].src = strUrl;
                    serverNum++;
                }
            </logic:iterate>
        </logic:present>
        //Url = "http://10.151.156.206:9002/ig/MTRMGECA.dow?_MSG_TYP=RELOAD";
    }
    
    $(document).ready(function(){
        init();
        setTableStyle();
    });
    </script>
  </head>
  <body>
    <html:form action="/autorm/taskAction"  onsubmit="return validator()">
    <input type="hidden" id="flush" name="flush" value="<bean:write name="flush" scope="request"/>"/>
    <input type="hidden" id="action" name="action" value="<bean:write name="action" scope="request"/>"/>
    <input type="hidden" id="protocol_type_hidden" name="protocol_type_hidden" />
    <input type="hidden" id="task_id_hidden" name="task_id_hidden" />
    <input type="hidden" id="taskType_hidden" name="taskType_hidden"/>
    <input type="hidden" id="dataItems_codes" name="dataItems_codes"/>

    <div class="pad5">
      <html:button styleId="new" property="new" styleClass="input1" disabled="false" onclick="formSubmit('taskAddShow')"><bean:message bundle="gather" key="gather.task.button.new"/></html:button>
      <html:button styleId="modif" property="modif" styleClass="input1" disabled="false" onclick="beginMod()"><bean:message bundle="gather" key="gather.task.button.modif"/></html:button>
      <html:button styleId="save" property="save" styleClass="input1" disabled="false" onclick="doSave()"><bean:message bundle="gather" key="gather.task.button.save"/></html:button>
      <html:button styleId="delete" property="delete" styleClass="input1" disabled="false" onclick="doDel()"><bean:message bundle="gather" key="gather.task.button.delete"/></html:button>
      <html:submit styleId="copy" property="copy" styleClass="input1" disabled="false" onclick="javascript:document.all.action.value = 'taskCopy'"><bean:message bundle="gather" key="gather.task.button.copy"/></html:submit>
    </div>
    <div class="tab"><em><bean:message bundle="gather" key="gather.task.lable.taskInfo"/></em></div>
    <div class="tab_con">
      <div class="main">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td width="15%" class="label"><bean:message bundle="gather" key="gather.task.lable.protocol_type"/>：</td>
            <td width="25%" class="dom">
              <peis:selectlist name="protocol_type" onChange="changProtocol()" sql="AL_TASKSET_1006" />
            </td>
            <td width="15%" class="label"><bean:message bundle="gather" key="gather.task.lable.task_type"/>：</td>
            <td width="25%">
              <html:radio property="taskType" value="1" onclick="changeTaskType()"><bean:message bundle="gather" key="gather.task.lable.autoup"/></html:radio>
              <html:radio property="taskType" value="2" onclick="changeTaskType()"><bean:message bundle="gather" key="gather.task.lable.master"/></html:radio>
            </td>
          </tr>
          <tr>
            <td width="15%" class="label"><bean:message bundle="gather" key="gather.task.lable.taskid"/>：</td>
            <td width="25%" class="dom"><html:text styleId="task_id" property="task_id" onkeyup="this.value=this.value.replace(/\D/gi,'')"/></td>
            <td width="15%" class="label"><bean:message bundle="gather" key="gather.task.lable.taskname"/>：</td>
            <td width="25%" class="dom"><html:text styleId="taskName" property="taskName" size="40" maxlength="40"/></td>
          </tr>
          <tr>
            <td id="upLable" width="15%" class="label" style="display:"><bean:message bundle="gather" key="gather.task.lable.sendupcycle"/>：</td>
            <td id="upDom" width="25%" style="display:">
              <html:text styleId="sendupCycleGw" property="sendupCycleGw" style="width: 96;"  onkeyup="this.value=this.value.replace(/\D/gi,'')"/>
              <peis:selectlist name="sendupUnitGw"  sql="GATHER0006"/>
            </td>
            <td id="upLableZj" width="15%" class="label" style="display:none"><bean:message bundle="gather" key="gather.task.lable.sendupcycle"/>：</td>
            <td id="upDomZj" width="25%" style="display:none">
              <html:text styleId="sendupIntervalTimeZj" property="sendupIntervalTimeZj" style="width: 96;"  onkeyup="this.value=this.value.replace(/\D/gi,'')"/>
              <peis:selectlist name="sendupIntervalTimeunitZj"  sql="GATHER0006"/>
            </td>
            <td id="execLable" width="15%" class="label" style="display:none"><bean:message bundle="gather" key="gather.task.lable.execcycle"/>：</td>
            <td id="execDom" width="25%" style="display:none"><html:text styleId="execCycleMaster" property="execCycleMaster" style="width: 70;"  onkeyup="this.value=this.value.replace(/\D/gi,'')"/>
              <peis:selectlist name="execUnitMaster" sql="GATHER0006"/>
            </td>
            <td width="15%" class="label"><bean:message bundle="gather" key="gather.task.lable.auditflag"/>：</td>
            <td width="25%">
              <html:radio styleId="auditFlag" property="auditFlag" value="1"><bean:message bundle="gather" key="gather.task.lable.audit_yes"/></html:radio>
              <html:radio styleId="auditFlag" property="auditFlag" value="0"><bean:message bundle="gather" key="gather.task.lable.audit_no"/></html:radio>
            </td>
          </tr>
          <tr>
            <td width="15%" class="label"><bean:message bundle="gather" key="gather.task.lable.infopointtype"/>：</td>
            <%  String gpCharSql = (String)request.getAttribute("gpCharSql");
                  gpCharSql = (gpCharSql == null ? "AL_AUTORM_0100" : gpCharSql);
            %>
            <td width="25%" class="dom"><peis:selectlist name="gpChar" sql="<%=gpCharSql%>"/></td>
            <td width="15%" class="label"><bean:message bundle="gather" key="gather.task.lable.datatype"/>：</td>
            <logic:present name="dataTypeSql">
              <%  String dataTypeSql = (String)request.getAttribute("dataTypeSql");
                  dataTypeSql = (dataTypeSql == null ? "AL_AUTORM_0006" : dataTypeSql);
              %>
              <td width="25%" class="dom"><peis:selectlist name="dataType" sql="<%=dataTypeSql%>" onChange="changeDataType()"/></td>
            </logic:present>
            <logic:notPresent name="dataTypeSql">
              <td width="25%" class="dom"><peis:selectlist name="dataType" sql="AL_AUTORM_0006" onChange="changeDataType()"/></td>
            </logic:notPresent>
          </tr>
          <tr>
            <td colspan="4" align="right">
              <html:button styleId="excluSetting" property="excluSetting" styleClass="input3" onclick="excluSettingShow()"><bean:message bundle="gather" key="gather.task.button.excluSetting"/></html:button>
              &nbsp;
            </td>
          </tr>
        </table>
      </div>
      <iframe style="position:absolute;z-index:10;width:expression(this.nextSibling.offsetWidth);height:expression(this.nextSibling.offsetHeight);top:expression(this.nextSibling.offsetTop);left:expression(this.nextSibling.offsetLeft);" frameborder="0" ></iframe>
      <div id="mytips" style="z-index:11; position:absolute; background-color:#85C0B4; width:121; height:16; border:1px solid gray; display:none;">
        <table id="excluSettingAuto" style="display: none">
          <tr>
            <td width="50%"> 基 准 时 间:</td>
            <td width="50%"><html:text styleId="baseTimeGw" property="baseTimeGw"  onfocus="setday(this)" readonly="true"></html:text></td>
          </tr>
          <tr>
            <td> 抽 取 倍 率:</td>
            <td><html:text styleId="extCntGw" property="extCntGw" onkeyup="this.value=this.value.replace(/\D/gi,'')"></html:text></td>
          </tr>
        </table> 
        <table id="excluSettingAutoZj" style="display: none">
          <tr>
            <td width="50%">采样基准时间:</td>
            <td width="50%">
              <html:text styleId="collectBaseTimeZj" property="collectBaseTimeZj" style="width: 96;"  onkeyup="this.value=this.value.replace(/\D/gi,'')"/>
              <peis:selectlist name="collectBaseTimeunitZj"  sql="GATHER0006"/>
            </td>
          </tr>
          <tr>
            <td>采样间隔时间:</td>
            <td>
              <html:text styleId="collectIntervalTimeZj" property="collectIntervalTimeZj" style="width: 96;"  onkeyup="this.value=this.value.replace(/\D/gi,'')"/>
              <peis:selectlist name="collectIntervalTimeunitZj"  sql="GATHER0006"/>
            </td>
          </tr>
          <tr>
            <td>上送基准时间:</td>
            <td>
              <html:text styleId="sendupBaseTimeZj" property="sendupBaseTimeZj" style="width: 96;"  onkeyup="this.value=this.value.replace(/\D/gi,'')"/>
              <peis:selectlist name="sendupBaseTimeunitZj" sql="GATHER0006"/>
            </td>
          </tr>
          <tr>
            <td>上送数据频率:</td>
            <td>
              <html:text styleId="sendupDataFreqZj" property="sendupDataFreqZj" onkeyup="this.value=this.value.replace(/\D/gi,'')"/>
            </td>
          </tr>
          <tr>
            <td>任务保存点数:</td>
            <td>
              <html:text styleId="taskSavePointZj" property="taskSavePointZj" onkeyup="this.value=this.value.replace(/\D/gi,'')"/>
            </td>
          </tr>
          <tr>
            <td>任务执行次数:</td>
            <td>
              <html:text styleId="taskExecTimesZj" property="taskExecTimesZj" onkeyup="this.value=this.value.replace(/\D/gi,'')"/>
            </td>
          </tr>
          <tr>
            <td> 执 行 任 务:</td>
            <td>
              <html:text styleId="execTaskZj" property="execTaskZj" onkeyup="this.value=this.value.replace(/\D/gi,'')"/>
            </td>
          </tr>
        </table> 
        <table id="excluSettingMaster" style="display: none">
          <tr>
            <td width="50%">执行开始时间:</td>
            <td width="50%"><html:text styleId="startTimeMaster" property="startTimeMaster"  onfocus="setday(this)"  readonly="true"></html:text></td>
          </tr>
          <tr>
            <td> 优 先 级:</td>
            <td><peis:selectlist name="priMaster" sql="AL_AUTORM_0011"/>
            </td>
          </tr>
        </table>
        <table>
          <tr>
            <td width="80"> 任 务 标 志:</td>
            <td>
              <peis:selectlist name="startupFlag" sql="AL_AUTORM_0016"/>
            </td>
          </tr>
        </table>
        <table align="center">
          <tr>
            <td colspan="2">
              <input type="button" class="input1" onclick="setting()" value="<bean:message bundle="gather" key="gather.task.button.submit"/>"/>&nbsp;&nbsp;
              <input type="button" class="input1" onclick="cancel()" value="<bean:message bundle="gather" key="gather.task.button.cancel"/>"/>
            </td>
          </tr>
        </table>
      </div>
      <div class="data1">
        <span><bean:message bundle="gather" key="gather.task.title.dataItemList"/></span>
        <h1><html:button styleId="chooseDataItem" onclick="chooseData()" property="chooseDataItem" styleClass="input3"><bean:message bundle="gather" key="gather.task.button.choosedataitem"/></html:button></h1>
        <h1><html:button styleId="downbt" onclick="downMove()" property="downbt" styleClass="input1">下移</html:button>&nbsp;&nbsp;&nbsp;&nbsp;</h1>
        <h1><html:button styleId="upbt" onclick="upMove()" property="upbt" styleClass="input1">上移</html:button>&nbsp;&nbsp;</h1>
      </div>
      <div class="data1_con">
        <div class="main" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 264));">
          <table id="dataItem" border="0" cellpadding="0" cellspacing="0" width="100%">
            <thead><tr>
              <th width="15%"><bean:message bundle="gather" key="gather.task.table.th.rowno"/></th>
              <th width="20%"><bean:message bundle="gather" key="gather.task.table.th.dataitemcode"/></th>
              <th width="60%"><bean:message bundle="gather" key="gather.task.table.th.dataitemname"/></th>
            </tr></thead>
            <tbody align="center">
              <logic:present name="dataItems">
                <logic:iterate id="datainfo" name="dataItems">
                  <tr onclick="selectSingleRow(this);" style="cursor:pointer;">
                    <td><bean:write name="datainfo" property="rowNo"/></td>
                    <td><bean:write name="datainfo" property="col1"/></td>
                    <td><bean:write name="datainfo" property="col2"/></td>
                  </tr>
                </logic:iterate>
              </logic:present>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    </html:form>
  </body>

<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
// 改变规约类型
function changProtocol() {
	var protocol_type = document.getElementById("protocol_type");
	window.location = contextPath + "/autorm/taskAction.do?action=taskAddShow&protocol_type=" + protocol_type.value;
}

// 提交表单并设置action执行方法
function formSubmit(value) {
    if(validator()){
        document.forms[0].action.value = value; 
        document.forms[0].submit();
    }
}

//保存
function doSave(){
    if(validator()){
        var method = document.all.action.value;
        if("taskMod" == method){ //修改任务
            document.forms[0].submit();
            return;
        }
        //增加任务执行以下代码
        var protocol_type = document.getElementById("protocol_type").value;
        var task_id = document.getElementById("task_id").value;
        var task_type = $("input[type=radio][name=taskType][checked=true]").val();
        var params = {"protocol_type": protocol_type,
                      "task_id": task_id,
                      "task_type": task_type
                     };
        var url = contextPath + "/autorm/taskAction.do?action=isExistsTaskId&" + jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
        $.get(url, function(idExist){
            if("YES" == idExist){
                alert(taskIdExist);
            }
            else {
                document.forms[0].submit();
            }
        });
    }
}

//获得选择行的位置
function getSelectedRowIndex(){
    var selectIndex = -1;
    $(".main table>tbody>tr").each( function() {
        if($(this).hasClass("selected")) {
            selectIndex = this.rowIndex;
        }
    });
    return selectIndex;
}

//交换两行的数据并设置otherRowObj选中
function changeRowData(oneRowObj, otherRowObj){
    var dataItemTable = document.getElementById("dataItem");
    if(oneRowObj == null || otherRowObj == null || oneRowObj.cells.length != otherRowObj.cells.length){
        return;
    }
    var rowObjTemp = [];
    for(var i = 0; i < oneRowObj.cells.length; i++){
        rowObjTemp.push(dataItemTable.rows[oneRowObj.rowIndex].cells[i].innerHTML);
    }
    //alert("oneRowObj.index=" + oneRowObj.rowIndex + ", otherRowObj.index=" + otherRowObj.rowIndex);
    for(var i = 1; i < oneRowObj.cells.length; i++){
        dataItemTable.rows[oneRowObj.rowIndex].cells[i].innerHTML = dataItemTable.rows[otherRowObj.rowIndex].cells[i].innerHTML;
    }
    for(var i = 1; i < otherRowObj.cells.length; i++){
        dataItemTable.rows[otherRowObj.rowIndex].cells[i].innerHTML = rowObjTemp[i];
    }
    $(oneRowObj).removeClass("selected");
    selectSingleRow(otherRowObj);
}

//上移
function upMove(){
    var selectedRowIndex = getSelectedRowIndex();
    var dataItemTable = document.getElementById("dataItem");
    if(selectedRowIndex < 0){
        alert("请选择一行上移！");
        return;
    }
    else if(selectedRowIndex == 1){
        alert("已经到第一行！");
        return;
    }
    var oneRowObj = dataItemTable.rows[selectedRowIndex];
    var otherRowObj = dataItemTable.rows[selectedRowIndex-1];
    changeRowData(oneRowObj, otherRowObj);
    setDataItemCode();
}

//下移
function downMove(){
    var selectedRowIndex = getSelectedRowIndex();
    var dataItemTable = document.getElementById("dataItem");
    if(selectedRowIndex < 0){
        alert("请选择一行下移！");
        return;
    }
    else if(selectedRowIndex == dataItemTable.rows.length-1){
        alert("已经到到最后一行！");
        return;
    }
    var oneRowObj = dataItemTable.rows[selectedRowIndex];
    var otherRowObj = dataItemTable.rows[selectedRowIndex+1];
    changeRowData(oneRowObj, otherRowObj);
    setDataItemCode();
}

// 数据项设定结果
function setDataItemCode() {
	var codes = "";
	var dataItemTable = document.getElementById("dataItem");
	var dataItems_codes = document.getElementById("dataItems_codes");
	for (i = 1; i < dataItemTable.rows.length; i++) {
		codes += dataItemTable.rows[i].cells[1].innerHTML + "@@";
	}
	dataItems_codes.value = codes;
    setTableStyle();
}

//设置表格Style
function setTableStyle(){
    /**
     * even/odd rows
     */
    setRows($(".main table>tbody>tr:odd"));
    
    /**
     * mouseover/mouseout event
     */
    $(".main table>tbody>tr").each( function() {
        $(this).mouseover( function() {
            $(this).addClass("mouseover");
        });
        $(this).mouseout( function() {
            $(this).removeClass("mouseover");
        });
    });
}
  
// 打开数据项选择页面
function chooseData() {
	var dataItemTable = document.getElementById("dataItem");
	dataItems = "@@";
	for (i = 1; i < dataItemTable.rows.length; i++) {
		dataItems += dataItemTable.rows[i].cells[1].innerHTML + "@@";
	}
	var protocol_type = document.getElementById("protocol_type").value;
	var dataType = document.getElementById("dataType").value;
	var gpChar = document.getElementById("gpChar").value;
	var url = contextPath + "/autorm/taskAction.do?action=openDataItem&gpChar=" + gpChar 
            + "&dataType=" + dataType
			+ "&protocol_type=" + protocol_type 
            + "&choosed=" + dataItems;
	var wd = 900;
	var ht = 575;
	//windowPopup(url, wd, ht);
    top.showDialogBox("数据项选择", url, ht, wd);
}

// 修改
function beginMod() {
	// 获得数据对象
	var protocol_type = document.getElementById("protocol_type");
	var task_type = document.getElementsByName("taskType");
	var task_id = document.getElementById("task_id");
	var task_name = document.getElementById("taskName");
	var sendup_cycle_gw = document.getElementById("sendupCycleGw");
    var sendupUnitGw = document.getElementById("sendupUnitGw");
    var sendupIntervalTimeZj = document.getElementById("sendupIntervalTimeZj");
    var sendupIntervalTimeunitZj = document.getElementById("sendupIntervalTimeunitZj");
	var audit_flag = document.getElementsByName("auditFlag");
	var gpChar = document.getElementById("gpChar");
	var data_type = document.getElementById("dataType");
	var exec_cyle_master = document.getElementById("execCycleMaster");
	var exec_unit_master = document.getElementById("execUnitMaster");
	// 获得按钮对象
	var add = document.getElementById("new");
	var save = document.getElementById("save");
	var modif = document.getElementById("modif");
	var del = document.getElementById("delete");
	var copy = document.getElementById("copy");
	var excluSetting = document.getElementById("excluSetting");
	var chooseDataItem = document.getElementById("chooseDataItem");
    var upbt = document.getElementById("upbt");
    var downbt = document.getElementById("downbt");
	// 设置按钮状态
	excluSetting.disabled = false;
	chooseDataItem.disabled = false;
    upbt.disabled = false;
    downbt.disabled = false;
	add.disabled = true;
	modif.disabled = true;
	del.disabled = true;
	copy.disabled = true;
	save.disabled = false;

	protocol_type.disabled = true;
	task_type[0].disabled = false;
	task_type[1].disabled = false;
	task_id.disabled = true;
	task_name.disabled = false;
	sendup_cycle_gw.disabled = false;
    sendupUnitGw.disabled = false;
    sendupIntervalTimeZj.disabled = false;
    sendupIntervalTimeunitZj.disabled = false;
	audit_flag[0].disabled = false;
	audit_flag[1].disabled = false;
	gpChar.disabled = false;
	data_type.disabled = false;
	exec_cyle_master.disabled = false;
	exec_unit_master.disabled = false;
	task_type[0].disabled = true;
	task_type[1].disabled = true;
	document.all.action.value = "taskMod";
}
</script>
<%@include file="../common/blockendloading.jsp"%>
</html>
