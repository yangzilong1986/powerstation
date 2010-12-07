<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>


<html>
  <head>
  <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css"/>
    <script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/dataQuery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
    <script type="text/javascript">
    var lastShowType;
    var contextPath = "<peis:contextPath/>";
    var date;
    var year;
    var month;
    var opwindow = null;     //记录打开浏览窗口的对象
    function doSubmit(objectID,objectType)
    {
       document.getElementsByName("object_id")[0].value = objectID;
       document.getElementsByName("object_type")[0].value = objectType;
       document.forms[0].submit();
    }
    </script>
  </head>
  
  <body onload="elecInit()">
    <html:form action="/do/autorm/elecQuery" method="post">
      <html:hidden property="action" value="getQuery"/>
      <html:hidden property="pageType" value="page"/>
      <html:hidden property="sqlcode"/>
      <html:hidden property="showType"/>
      <html:hidden property="object_id"/>
      <html:hidden property="object_type"/>
      <div id="main">
        <div>
          <div id="tool">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td class="label"><bean:message bundle="autorm" key="autorm.dataquery.lable.help"/>：</td>
                <td colspan="4"><bean:message bundle="autorm" key="autorm.dataquery.lable.help.meter"/></td>
              </tr>
              <tr>
                <td width="286" colspan="2">
                  <html:radio property="sys_object" value="1" onclick="parent.changeTreeMode('99991002')"><bean:message bundle="autorm" key="autorm.dataquery.lable.zbyh"/></html:radio>
                  <html:radio property="sys_object" value="2" onclick="parent.changeTreeMode('99991003')"><bean:message bundle="autorm" key="autorm.dataquery.lable.pbtq"/></html:radio>
                  <html:radio property="sys_object" value="3" onclick="parent.changeTreeMode('99991003')"><bean:message bundle="autorm" key="autorm.dataquery.lable.dyyh"/></html:radio>
                  <html:radio property="sys_object" value="4"><bean:message bundle="autorm" key="autorm.dataquery.lable.bdz"/></html:radio>
                </td>
                <td width="66" class="label"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxbh"/>：</td>
                <td width="120" class="dom"><peis:text name="object_no" type="text" /></td>
                <td></td>
              </tr>
              <tr>
                <td width="66" class="label"><bean:message bundle="autorm" key="autorm.dataquery.lable.sjlx"/>：</td>
                <td width="220" class="dom"><peis:selectlist name="dateType" sql="QUERY0001" onChange="meterDTChange()"/></td>
                <td class="label"><bean:message bundle="autorm" key="autorm.dataquery.lable.sjsj"/>：</td>
                <td>
                  <div id="DATA_DAY" style="display: block;">
                    <peis:text name="date" type="text" styleClass="styleDate" />
                    <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'date',null)">
                      <img name="dimg1" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle" border="0" alt=""/>
                    </a>
                  </div>
                  <div id="DATA_MONTH" style="display: none;">
                    <peis:text name="year" type="text" styleClass="styleYear" /><bean:message bundle="autorm" key="autorm.dataquery.lable.year"/>
                    <peis:text name="month" type="text" styleClass="styleMonth" /><bean:message bundle="autorm" key="autorm.dataquery.lable.month"/>
                  </div>
                </td>
                <td align="right">
                    <html:submit styleClass="input" onclick="document.getElementsByName('object_type')[0].value = '';"><bean:message bundle="autorm" key="autorm.dataquery.lable.query"/></html:submit>
                    <input type="button" class="input" value="<bean:message bundle='autorm' key='autorm.dataquery.lable.zhcx'/>"/>
                </td>
              </tr>
            </table>
          </div>
        </div>
        <div class="content">
          <div id="cont_1">
            <div class="target">
              <ul>
                <li class="target_off" id="show1"><a href="#" onclick="changeShowType(1)"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxyg"/></a></li>
                <li class="target_off" id="show2"><a href="#" onclick="changeShowType(2)"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxyg"/></a></li>
                <li class="target_off" id="show3"><a href="#" onclick="changeShowType(3)"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxwg"/></a></li>
                <li class="target_off" id="show4"><a href="#" onclick="changeShowType(4)"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxwg"/></a></li>
                <li class="target_off" id="show5"><a href="#" onclick="changeShowType(5)"><bean:message bundle="autorm" key="autorm.dataquery.lable.wg"/></a></li>
                <li class="clear"></li>
              </ul> 
            </div>
              <table align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr id="QUERY_TR_1" style="display: none">
                  <td colspan="8" width="100%">
                  <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 172));">
                    <table id="data_table_1" align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
                      <thead><tr>
                        <th width="40"><bean:message bundle="autorm" key="autorm.dataquery.lable.no"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxbh"/></th>
                        <th width="120"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxmc"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.cld"/></th>
                        <th width="80" id="DATA_TIME_1" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.sjsj"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxygz"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxygj"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxygf"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxygp"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxygg"/></th>
                        <th width="80" id="LIST_1" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.ck"/></th>
                        <th width="80" id="LIST_6" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.ck"/></th>
                      </tr></thead>
                      <tbody>
                        <logic:present name="PG_QUERY_RESULT">
                        <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
                        <tr>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col1"/></td>
                          <td><bean:write name="datainfo" property="col2"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td width="80" id="DATA_VALUE_1" style="display: none"><bean:write name="datainfo" property="col32"/></td>
                          <td><bean:write name="datainfo" property="col4"/></td>
                          <td><bean:write name="datainfo" property="col5"/></td>
                          <td><bean:write name="datainfo" property="col6"/></td>
                          <td><bean:write name="datainfo" property="col7"/></td>
                          <td><bean:write name="datainfo" property="col8"/></td>
                          <td id="LINE_TD_1" style="display: none">
                            <a href="javascript:meterDetail(900,450,'<bean:write name="datainfo" property="col33"/>','<bean:write name="datainfo" property="col34"/>','<bean:write name="datainfo" property="col1"/>',2)"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckqx"/></a>
                          </td>
                          <td id="DAY_TD_1" style="display: none">
                            <a href="javascript:meterDetail(900,600,'<bean:write name="datainfo" property="col33"/>','<bean:write name="datainfo" property="col34"/>','<bean:write name="datainfo" property="col1"/>',3)"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckrsj"/></a>
                          </td>
                        </tr>
                        </logic:iterate>
                        </logic:present>
                      </tbody>
                    </table>
                    </div>
                  </td>
                </tr>
                <tr id="QUERY_TR_2" style="display: none">
                  <td colspan="8" width="100%">
                  <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 172));">
                    <table id="data_table_2" align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
                      <thead><tr>
                        <th width="40"><bean:message bundle="autorm" key="autorm.dataquery.lable.no"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxbh"/></th>
                        <th width="120"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxmc"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.cld"/></th>
                        <th width="80" id="DATA_TIME_2" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.sjsj"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxygz"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxygj"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxygf"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxygp"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxygg"/></th>
                        <th width="80" id="LIST_2" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.ck"/></th>
                        <th width="80" id="LIST_6" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.ck"/></th>
                      </tr></thead>
                      <tbody>
                        <logic:present name="PG_QUERY_RESULT">
                        <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
                        <tr>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col1"/></td>
                          <td><bean:write name="datainfo" property="col2"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td width="80" id="DATA_VALUE_2" style="display: none"><bean:write name="datainfo" property="col32"/></td>
                          <td><bean:write name="datainfo" property="col9"/></td>
                          <td><bean:write name="datainfo" property="col10"/></td>
                          <td><bean:write name="datainfo" property="col11"/></td>
                          <td><bean:write name="datainfo" property="col12"/></td>
                          <td><bean:write name="datainfo" property="col13"/></td>
                          <td id="LINE_TD_2" style="display: none">
                            <a href="javascript:meterDetail(900,450,'<bean:write name="datainfo" property="col33"/>','<bean:write name="datainfo" property="col34"/>','<bean:write name="datainfo" property="col1"/>',2)"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckqx"/></a>
                          </td>
                          <td id="DAY_TD_2" style="display: none">
                            <a href="javascript:meterDetail(900,600,'<bean:write name="datainfo" property="col33"/>','<bean:write name="datainfo" property="col34"/>','<bean:write name="datainfo" property="col1"/>',3)"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckrsj"/></a>
                          </td>
                        </tr>
                        </logic:iterate>
                        </logic:present>
                      </tbody>
                    </table>
                    </div>
                  </td>
                </tr>
                <tr id="QUERY_TR_3" style="display: none">
                  <td colspan="8" width="100%">
                   <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 172));">
                    <table id="data_table_3" align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
                      <thead><tr>
                        <th width="40"><bean:message bundle="autorm" key="autorm.dataquery.lable.no"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxbh"/></th>
                        <th width="120"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxmc"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.cld"/></th>
                        <th width="80" id="DATA_TIME_3" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.sjsj"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxwgz"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxwgj"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxwgf"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxwgp"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxwgg"/></th>
                        <th width="80" id="LIST_3" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.ck"/></th>
                        <th width="80" id="LIST_6" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.ck"/></th>
                      </tr></thead>
                      <tbody>
                      <logic:present name="PG_QUERY_RESULT">
                      <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
                        <tr>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col1"/></td>
                          <td><bean:write name="datainfo" property="col2"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td width="80" id="DATA_VALUE_3" style="display: none"><bean:write name="datainfo" property="col32"/></td>
                          <td><bean:write name="datainfo" property="col14"/></td>
                          <td><bean:write name="datainfo" property="col15"/></td>
                          <td><bean:write name="datainfo" property="col16"/></td>
                          <td><bean:write name="datainfo" property="col17"/></td>
                          <td><bean:write name="datainfo" property="col18"/></td>
                          <td id="LINE_TD_3" style="display: none">
                            <a href="javascript:meterDetail(900,450,'<bean:write name="datainfo" property="col33"/>','<bean:write name="datainfo" property="col34"/>','<bean:write name="datainfo" property="col1"/>',2)"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckqx"/></a>
                          </td>
                          <td id="DAY_TD_3" style="display: none">
                            <a href="javascript:meterDetail(900,600,'<bean:write name="datainfo" property="col33"/>','<bean:write name="datainfo" property="col34"/>','<bean:write name="datainfo" property="col1"/>',3)"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckrsj"/></a>
                          </td>
                        </tr>
                        </logic:iterate>
                        </logic:present>
                      </tbody>
                    </table>
                    </div>
                  </td>
                </tr>
                <tr id="QUERY_TR_4" style="display: none">
                  <td colspan="8" width="100%">
                  <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 172));">
                    <table id="data_table_4" align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
                      <thead><tr>
                        <th width="40"><bean:message bundle="autorm" key="autorm.dataquery.lable.no"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxbh"/></th>
                        <th width="120"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxmc"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.cld"/></th>
                        <th width="80" id="DATA_TIME_4" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.sjsj"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxwgz"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxwgj"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxwgf"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxwgp"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxwgg"/></th>
                        <th width="80" id="LIST_4" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.ck"/></th>
                        <th width="80" id="LIST_6" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.ck"/></th>
                      </tr></thead>
                      <tbody>
                        <logic:present name="PG_QUERY_RESULT">
                        <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
                        <tr>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col1"/></td>
                          <td><bean:write name="datainfo" property="col2"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td width="80" id="DATA_VALUE_4" style="display: none"><bean:write name="datainfo" property="col32"/></td>
                          <td><bean:write name="datainfo" property="col19"/></td>
                          <td><bean:write name="datainfo" property="col20"/></td>
                          <td><bean:write name="datainfo" property="col21"/></td>
                          <td><bean:write name="datainfo" property="col22"/></td>
                          <td><bean:write name="datainfo" property="col23"/></td>
                          <td id="LINE_TD_4" style="display: none">
                            <a href="javascript:meterDetail(900,450,'<bean:write name="datainfo" property="col33"/>','<bean:write name="datainfo" property="col34"/>','<bean:write name="datainfo" property="col1"/>',2)"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckqx"/></a>
                          </td>
                          <td id="DAY_TD_4" style="display: none">
                            <a href="javascript:meterDetail(900,600,'<bean:write name="datainfo" property="col33"/>','<bean:write name="datainfo" property="col34"/>','<bean:write name="datainfo" property="col1"/>',3)"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckrsj"/></a>
                          </td>
                        </tr>
                        </logic:iterate>
                        </logic:present>
                      </tbody>
                    </table>
                    </div>
                  </td>
                </tr>
                <tr id="QUERY_TR_5" style="display: none">
                  <td colspan="8" width="100%">
                  <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 172));">
                    <table id="data_table_5" align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
                      <thead><tr>
                        <th width="40"><bean:message bundle="autorm" key="autorm.dataquery.lable.no"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxbh"/></th>
                        <th width="120"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxmc"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.cld"/></th>
                        <th width="80" id="DATA_TIME_1" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.sjsj"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.xxwg1"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.xxwg2"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.xxwg3"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.xxwg4"/></th>
                        <th width="80" id="LIST_5" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.ck"/></th>
                        <th width="80" id="LIST_6" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.ck"/></th>
                      </tr></thead>
                      <tbody>
                        <logic:present name="PG_QUERY_RESULT">
                        <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
                        <tr>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col1"/></td>
                          <td><bean:write name="datainfo" property="col2"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td width="80" id="DATA_VALUE_5" style="display: none"><bean:write name="datainfo" property="col32"/></td>
                          <td><bean:write name="datainfo" property="col24"/></td>
                          <td><bean:write name="datainfo" property="col25"/></td>
                          <td><bean:write name="datainfo" property="col26"/></td>
                          <td><bean:write name="datainfo" property="col27"/></td>
                          <td id="LINE_TD_5" style="display: none">
                            <a href="javascript:meterDetail(900,450,'<bean:write name="datainfo" property="col33"/>','<bean:write name="datainfo" property="col34"/>','<bean:write name="datainfo" property="col1"/>',2)"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckqx"/></a>
                          </td>
                          <td id="DAY_TD_5" style="display: none">
                            <a href="javascript:meterDetail(900,600,'<bean:write name="datainfo" property="col33"/>','<bean:write name="datainfo" property="col34"/>','<bean:write name="datainfo" property="col1"/>',3)"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckrsj"/></a>
                          </td>
                        </tr>
                        </logic:iterate>
                        </logic:present>
                      </tbody>
                    </table>
                    </div>
                  </td>
                </tr>
                <tr id="QUERY_TR_6" style="display: none">
                  <td colspan="8" width="100%">
                  <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 172));">
                    <table id="data_table_6" align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
                      <thead><tr>
                        <th width="40"><bean:message bundle="autorm" key="autorm.dataquery.lable.no"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxbh"/></th>
                        <th width="120"><bean:message bundle="autorm" key="autorm.dataquery.lable.dxmc"/></th>
                        <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.cld"/></th>
                        <th width="80" id="DATA_TIME_6" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.sjsj"/></th>
                        <th width="110"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxygzdxl"/></th>
                        <th width="160"><bean:message bundle="autorm" key="autorm.dataquery.lable.zxygzdxlfssj"/></th>
                        <th width="110"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxygzdxl"/></th>
                        <th width="160"><bean:message bundle="autorm" key="autorm.dataquery.lable.fxygzdxlfssj"/></th>
                        <th width="80" id="LIST_6" style="display: none"><bean:message bundle="autorm" key="autorm.dataquery.lable.ck"/></th>
                      </tr></thead>
                      <tbody>
                        <logic:present name="PG_QUERY_RESULT">
                        <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
                        <tr>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col1"/></td>
                          <td><bean:write name="datainfo" property="col2"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td width="80" id="DATA_VALUE_6" style="display: none"><bean:write name="datainfo" property="col32"/></td>
                          <td><bean:write name="datainfo" property="col28"/></td>
                          <td><bean:write name="datainfo" property="col29"/></td>
                          <td><bean:write name="datainfo" property="col30"/></td>
                          <td><bean:write name="datainfo" property="col31"/></td>
                          <td id="DAY_TD_6" style="display: none">
                            <a href="javascript:meterDetail(900,600,'<bean:write name="datainfo" property="col33"/>','<bean:write name="datainfo" property="col34"/>','<bean:write name="datainfo" property="col1"/>',3)"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckrsj"/></a>
                          </td>
                        </tr>
                        </logic:iterate>
                        </logic:present>
                      </tbody>
                    </table>
                    </div>
                  </td>
                </tr>
              </table>
            <div class="pageContainer" id="PAGE_DAY" style="display: ;">
              <peis:pagination queryActionPath="do/autorm/meterQuery" allowRowsChange="true"/>
            </div>
          </div>
        </div>
      </div>
     </html:form>
  </body>
</html>