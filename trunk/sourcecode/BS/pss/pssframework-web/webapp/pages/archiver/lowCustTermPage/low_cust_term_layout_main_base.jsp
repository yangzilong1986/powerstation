<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../common/taglib.jsp"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>集中器及采集器信息</title>
		<tiles:insert attribute="css"></tiles:insert>
        <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
        <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
        <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/loading.css" />
        <!-- 公有的JS -->
        <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
        <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
        <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
        <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
        <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
        <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
		<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
        <script type="text/javascript" src="<peis:contextPath/>/js/frame/loading.js"></script>
        <script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
        <script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
		<tiles:insert attribute="private_js"></tiles:insert>
	</head>
	<body>
		<tiles:insert attribute="content"></tiles:insert>
	</body>
</html>
