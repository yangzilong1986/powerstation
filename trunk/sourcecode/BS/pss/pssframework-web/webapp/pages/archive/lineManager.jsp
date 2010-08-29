<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>




<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>线路管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css"/>
	<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
	<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
	<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
	<script type="text/javascript" language="javascript">
		var contextPath = "<peis:contextPath/>";
		var errorDept='<bean:message bundle="system" key="dept.errorDept"/>';
		var sSelectedOrgID = "";   
		function selectRow(sOrgID, oRow) {
    		sSelectedOrgID = sOrgID;
   		 selectSingleRow(oRow);
		}
		
	//新增线路
	function newOrg() {
	var selectId=parent.document.frames("DTree").selectId;
    var str_url = contextPath + "/archive/lineAction.do?action=beforeEdit&orgNo="+selectId+"&random=" + Math.random();
    windowPopup(str_url, 650, 400);
	}
	//刷新
	function fresh(orgNo){
		parent.document.frames["line"].location.href=contextPath+'/archive/lineAction.do?action=getQuery&orgNo='+orgNo+'&random='+ Math.random();
		parent.document.frames("DTree").location=contextPath+'/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=99991001&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype=#null'; 
	}

	//编辑线路
	function editLine() {
   
   	 if(sSelectedOrgID == ""||sSelectedOrgID==null) {
        alert("请选择需要编辑的线路！");
        return;
   	 }
   	 var str_url = contextPath + "/archive/lineAction.do?action=beforeEdit&lineID=" + sSelectedOrgID + "&random=" + Math.random();
   	 windowPopup(str_url, 650, 400);
}
//删除线路
	function delLine() {
   
   	 if(sSelectedOrgID == ""||sSelectedOrgID==null) {
        alert("请选择需要删除的线路！");
        return;
   	 }
   	 //var str_url = contextPath + "/archive/lineAction.do?action=delete&lineID=" + sSelectedOrgID + "&random=" + Math.random();
   	 if (window.confirm("确定要删除该线路吗？")) {
   	 var params = {"action": "delete",
                      "lineID": sSelectedOrgID};
        var url = contextPath + "/archive/lineAction.do";
        $.ajax({
            type: "POST",
            url: url,
            cache: false,
            data: params,
            success: function(data) {
                var result = data.split("#")[0];
                var message = data.split("#")[1];
                alert(message);
                if(result == "1") {     // 删除成功
                    $("#QueryForm").submit();
                }
            }
        });
	}
}

    function query(queryType) {
    
      if(queryType == "objectNo") {
          
            if(window.parent.DTree.oldObject) {
                window.parent.DTree.oldObject.style.color = "000000";
                window.parent.DTree.oldObject.style.backgroundColor = "#ffffff";
            }
        }
        document.forms[0].submit();
    }
	
 	</script>
  </head>
  
  <body>
  <html:form  styleId="QueryForm" action="/archive/lineAction" method="post">
      <html:hidden property="action" value="getQuery" />
      
  <div id="main">
  	<div class="content">
  	<div id="tool">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td width="66" class="label">线路编号：</td>
                <td width="120" class="dom"><input type="text" name="lineNo" value="<bean:write name="lineNo" />"/></td>
                <td width="66" class="label">线路：</td>
                <td width="120" class="dom"><input type="text" name="lineName"  value="<bean:write name="lineName" />"/></td>
                <td width="66" class="label">供电单位：</td>
                <td width="120" class="dom"><peis:selectlist name="orgNo" sql="SL_COMMON_0001"/></td>
                <td width="160">
                  <input type="button" name="button" class="input" value="<bean:message key="global.query"/>" onclick="query('objectNo');" />
                  <!-- <input type="button" class="input" value="<bean:message bundle='archive' key='archive.synch'/>" onclick="" /> -->
                </td>
                <td align="right">
                  
                </td>
              </tr>
            </table>
          </div>
    	<div id="cont_3">
            <div class="target">
              <ul>
                <li class="target_on">&nbsp;本单位下的线路&nbsp;</li>
                <li class="clear"></li>
              </ul>	
            </div>
            <div id="tool">
             <logic:equal name="isEdit" value="1" scope="request">
             <div class="t_botton">
              <div class="t_left">
                <input type="button" class="input" name="add" value='<bean:message bundle="system" key="button.xz"/>' onclick="newOrg()"/>
                <input type="button" class="input" name="edit" value='<bean:message bundle="system" key="button.bj"/>' onclick="editLine()"/>
                <input type="button" class="input" name="del" value='<bean:message bundle="system" key="button.sc"/>' onclick="delLine()"/>
              </div>
              <div class="clear"></div>
            </div>
            </logic:equal>
            </div>
      	<div id="tableContainer" class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 120));">
    	  <table border="0" cellpadding="0" cellspacing="0" width="100%">
   		  <thead><tr>
		    <th>序号</th> 
		    <th>线路编号</th> 
		    <th>线路名称</th> 
		    <th>变电站</th> 
		    <th>是否支线</th>
		    <th>上级线路</th>  	
		    <th>电压等级</th> 
		    <th>线路状态</th> 
		    <th>线损阈值</th>
		    <th>所属单位</th>
		    <th>备注</th>
		 	</tr> </thead>
    <logic:present name="PG_QUERY_RESULT">
        <logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number"> 
          <tr align="center" onclick="selectRow('<bean:write name="datainfo" property="col1"/>', this)" style="cursor:pointer;">
          <td> <bean:write name="datainfo" property="rowNo"/></td>
 		  <td> <bean:write name="datainfo" property="col2"/></td>
          <td> <bean:write name="datainfo" property="col3"/></td>
          <td> <bean:write name="datainfo" property="col4"/></td>
          <td> <bean:write name="datainfo" property="col5"/></td>
          <td> <bean:write name="datainfo" property="col6"/></td>
          <td> <bean:write name="datainfo" property="col7"/></td>
          <td> <bean:write name="datainfo" property="col8"/></td>
          <td> <bean:write name="datainfo" property="col9"/></td>
          <td> <bean:write name="datainfo" property="col10"/></td>
          <td> <bean:write name="datainfo" property="col11"/></td>

          </tr>
        </logic:iterate>
        </logic:present>
        </table>
    	</div>
    	</div>
    	</div>
    </div>
   </html:form>
  </body>
</html>
