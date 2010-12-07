<%@ page contentType="text/html; charset=utf-8" %>




<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>刷新终端</title>
<link href="<peis:contextPath/>/css/style.css" rel="stylesheet" type="text/css"/>
<link href="<peis:contextPath/>/css/main.css"  rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#btn_ref').bind("click",refTerminal);
});

//刷新终端方法
function refTerminal(){
	
}

</script>
</head>
<body>
<div id="body">
    <div class="tab">
      <ul>
        <li id="tab_1" class="tab_on"><a href="#" onClick="return false;" onFocus="blur()">刷新终端</a></li>
        <li class="clear"></li>
      </ul>
    </div>
<div id="main">
<table align="center" border="1" cellpadding="0" cellspacing="0" width="99%" height="100%">
	<tr>
		<td align="center">
		<button id="btn_ref" style="width:200px;height:100px;cursor:pointer;line-height:22px;font-size:12px;color:#fff;"  >刷新终端</button>	
		</td>
	</tr>
</table>
</div></div>
</body>
</html>