<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>



<html:html lang="true">
<head>
  <title><bean:message bundle="archive" key="archive.bitcust.info" /></title>
  <meta http-equiv="Content-Language" content="zh-cn">
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
  <script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
  <script type="text/javascript">
  var contextPath = '<peis:contextPath/>';
  var lastPageNo = "1";
  function changeParamPage(pageNo){
      var custid = document.all.custid.value;
      eval("document.all.g" + lastPageNo + ".className='e_titleoff'");
      lastPageNo = pageNo;
      eval("document.all.g" + pageNo + ".className='e_titleon'");
      if(pageNo == "1") {
          fcssz.document.location = contextPath + "/archive/customerAction.do?&action=detail&custid=" + custid + "&rand=" + Math.round();
      }
      else {
          fcssz.document.location = contextPath + "/archive/customerAction.do?&action=showDeviceInfoByCustID&custid=" + custid + "&rand=" + Math.round();
      }
  }
</script>

</head>
<body style="overflow-x: auto; overflow-y: hidden;">
  <input type="hidden" id="custid" value="<bean:write name="custid"/>" />
  <div class="equip_title">
    <ul class="e_title">
      <li id="g1" class="e_titleon">
        <a href="javascript:changeParamPage(1);" onfocus="blur()"><bean:message bundle="archive" key="archive.cust.info" /></a>
      </li>
      <li id="g2" class="e_titleoff">
        <a href="javascript:changeParamPage(2);" onfocus="blur()"><bean:message bundle="archive" key="archive.device" /></a>
      </li>
    </ul>
  </div>

  <table width="100%" height="561" border=0 cellpadding=0 cellspacing=0 align=center class="e_detail_t">
    <tr>
      <td width="100%" height="100%">
        <iframe name="fcssz" src="" frameborder="0" width="100%" height="100%" scrolling="auto"></iframe>
      </td>
    </tr>
  </table>
  
  <script type="text/javascript">
  changeParamPage(1); 
  </script>
</body>

</html:html>
