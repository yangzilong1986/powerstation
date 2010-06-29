<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>

<html>
  <head>
    <title><bean:message bundle="autorm" key="autorm.gatherQuality.Title" /></title>
    <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
    <script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/common/common-ajax.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
    <script type="text/javascript" language="javascript">
    $(document).ready(function(){
		$('#sjmd').change(dateTypeModeChange);
    });

    function dateTypeModeChange() {
    	var dateType = $('#sjmd').val();
    	if (dateType == "11") {//月
    		$('#DATA_DAY').hide();
    		$('#DATA_MONTH').show();
    		$('#DATA_YEAR').show();
    	} else {
    		$('#DATA_DAY').show();
    		$('#DATA_YEAR').hide();
    		$('#DATA_MONTH').hide();
    	}
    }
    
    </script>
  </head>
  <body>
    <div id="body">
      <div class="tab">
        <ul>
          <logic:equal name="gatherSuccessRatesForm" property="sqlcode" value="CBCGL0003">
          <li id="tab_1" class="tab_on"><a href="gatherSuccessRates.do?mothed=init&&sqlcode=CBCGL0003" onfocus="blur()">数据采集成功率</a></li>
          <li id="tab_2" class="tab_off"><a href="gatherSuccessRates.do?mothed=init&&sqlcode=CBWZL0003" onfocus="blur()">数据采集完整率</a></li>
          </logic:equal>
          <logic:equal name="gatherSuccessRatesForm" property="sqlcode" value="CBWZL0003">
          <li id="tab_1" class="tab_off"><a href="gatherSuccessRates.do?mothed=init&&sqlcode=CBCGL0003" onfocus="blur()">数据采集成功率</a></li>
          <li id="tab_2" class="tab_on"><a href="gatherSuccessRates.do?mothed=init&&sqlcode=CBWZL0003" onfocus="blur()">数据采集完整率</a></li>
          </logic:equal>
          <li class="clear"></li>
        </ul>
      </div>
      <div id="main">
        <div>
          <html:form action="/gatherSuccessRates" method="post" target="resultframe">
          <input type="hidden" name="mothed" value="state" />
          <html:hidden property="sqlcode" />
          <div id="tool">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td class="label"><bean:message bundle="autorm" key="autorm.gatherQuality.hint.label"/></td>
                <td colspan="8"><bean:message bundle="autorm" key="autorm.gatherQuality.hint"/></td>
              </tr>
              <tr>
              	<td class="label">供电单位：</td>
		            <td>
			          	<peis:selectlist name="org_no" sql="SL_COMMON_0002" styleId="org_no"/></td>
                <td width="66" class="label"><bean:message bundle="autorm" key="autorm.gatherQuality.sjmd.label" /></td>
                <td width="120" class="dom"><peis:selectlist name="sjmd" sql="SJMD0001" styleId="sjmd"/></td>
                <td class="label"><bean:message bundle="autorm" key="autorm.gatherQuality.tjsj.label" /></td>
                <td id="tjrq" colspan="3">
                  <span id="DATA_DAY" style="display:block;">
		            <html:text property="tjsj" styleId="date" style="width:80px;"/>
                    <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'date',null)">
                      <img name="dimg1" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle" border="0" alt=""/>
                    </a>
                  </span>
                  <span id="DATA_YEAR" style="display:none;">
                  	<html:text property="year" styleId="year" style="width:40px;"/>年
                  </span>
                  <span id="DATA_MONTH" style="display:none;">
                  	<html:text property="month" styleId="month" style="width:40px;"/>月
                  </span>
                </td>
                <td colspan="3">
	              <div class="t_right">
	                <input class="input" type="submit" id="query" value="<bean:message bundle="autorm" key="autorm.button.query"/>"/>
	              </div>
                </td>
              </tr>
            </table>
          </div>
          </html:form>
        </div>
        <div class="content">
          <div id="cont_1">
            <div class="target">
              <ul>
                <li class="target_on">
                  <a href="#" onclick="return false;">
                    <logic:equal name="gatherSuccessRatesForm" property="sqlcode" value="CBCGL0003">数据采集成功率</logic:equal>
                    <logic:equal name="gatherSuccessRatesForm" property="sqlcode" value="CBWZL0003">数据采集完整率</logic:equal>
                  </a>
                </li>
                
              </ul>	
            </div>
            <div style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 190));">
              <iframe class="dataframe" name="resultframe" scrolling="auto" src="<peis:contextPath/>/gatherSuccessRates.do?mothed=state&sqlcode=<bean:write name="gatherSuccessRatesForm" property="sqlcode"/>&sjmd=10&sjlx=10&ckfs=1&tjsj=<bean:write name="gatherSuccessRatesForm" property="tjsj"/>" 
              frameborder="0" width="100%" height="100%"></iframe>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
