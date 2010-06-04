<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../common/taglib.jsp"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>采集器列表</title>
		<tiles:insert attribute="css"></tiles:insert>
		<!-- 公有的JS -->
		<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
		<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
		<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
		<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
		<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
		<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
		<tiles:insert attribute="private_js"></tiles:insert>
	</head>
	<body>
		<tiles:insert attribute="content"></tiles:insert>
	</body>
</html>
