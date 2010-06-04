<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
 
<html> 

	<html:javascript formName="addyhxxForm" cdata="false" />
	<head>
		<title><bean:message bundle="archive" key="yhxx.title"/></title>
	</head>
	<body>

	
		<html:form action="/addyhxx" onsubmit="return validateAddyhxxForm(this)">
		<html:hidden property="action" value="saveOrUpdate"/>
		<html:hidden property="className" value="DaYhxx"/>
		<bean:message bundle="archive" key="yhxx.dz"/><html:text property="dz"/><html:errors property="dz" /><br/>
		<bean:message bundle="archive" key="yhxx.hm"/><html:text property="hm"/><html:errors property="hm" /><br/>
		<bean:message bundle="archive" key="yhxx.hh"/><html:text property="hh"/><html:errors property="hh" /><br/>
		<bean:message bundle="archive" key="yhxx.lxdh"/><html:text property="lxdh"/><html:errors property="lxdh" /><br/>
		<bean:message bundle="archive" key="yhxx.hyfl"/><html:text property="hyfl"/><html:errors property="hyfl" /><br/>
		<bean:message bundle="archive" key="yhxx.jlfs"/><html:text property="jlfs"/><html:errors property="jlfs" /><br/>
		<bean:message bundle="archive" key="yhxx.lxr"/><html:text property="lxr"/><html:errors property="lxr" /><br/>
		<bean:message bundle="archive" key="yhxx.dydj"/><html:text property="dydj"/><html:errors property="dydj" /><br/>
		<bean:message bundle="archive" key="yhxx.sdrl"/><html:text property="sdrl"/><html:errors property="sdrl" /><br/>
		<html:submit value="È·¶¨"/><html:cancel/>
		</html:form>
	</body>
</html>

