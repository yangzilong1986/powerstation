<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../common/taglib.jsp"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>集中器及采集器信息</title>
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
	<body style="overflow: hidden;">
		<div id="body">
			<div id="main">
				<div class="target">
					<!-- TAB页 -->
					<tiles:insert attribute="target"></tiles:insert>
				</div>
				<div class="content">
					<!-- 内容 -->
					<tiles:insert attribute="content"></tiles:insert>
				</div>
			</div>
 		</div>
	</body>
</html>
