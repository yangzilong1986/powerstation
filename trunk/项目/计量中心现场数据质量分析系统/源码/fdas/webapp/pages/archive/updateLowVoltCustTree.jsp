<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>对象树</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/treeframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript">
var selBgColor = "#0A726A";
var selColor = "FFFFFF";
var unSelBgColor = "#FFFFFF";
var unSelColor = "000000";

var preObject = null;

/**
 * 选择对象
 * @param {Object} obj
 * @param {int} objectType    : 对象类型
 * @param {String} objectId   : 对象编号
 * @param {String} objectName : 对象名称
 * @param {String} orgType    : 部门类型
 */
function select(obj, objectType, objectId, objectName, orgType) {
    $(obj).css("background-color", selBgColor);
    $(obj).css("color", selColor);
    if(preObject != null && preObject != obj) {
        $(preObject).css("background-color", unSelBgColor);
        $(preObject).css("color", unSelColor);
    }
    preObject = obj;
    parent.nodeEvent(objectType, objectId, objectName, orgType);
}
</script>
</head>
<body>
<div class="treeContainer">
  <table border="0" cellspacing="0" cellpadding="0">
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.3301204558412191'></a><a href="#" onClick="return false;"><img src="<peis:contextPath/>/img/tree/handledownlast.gif" alt="close node" border="0" width="16" height="22"></a></td>
      <td colspan="5" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onClick="select(this, 2, '44', '金钱二公变95(0804255330)', '02');" style="cursor:hand;" nowrap="nowrap"><img src="<peis:contextPath/>/img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;金钱二公变95(0804255330)</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7969896755579916'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><a href="#" onClick="return false;"><img src="<peis:contextPath/>/img/tree/handledownlast.gif" alt="expand node" border="0" width="16" height="22"></a></td>
      <td colspan="4" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onClick="select(this, 7, '4408', '金钱二0804255', '03');" style="cursor:hand;" nowrap="nowrap"><img src="<peis:contextPath/>/img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;金钱二0804255</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.4226345484093218'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;">&nbsp;</td>
      <td width="16" style="padding-left:1px;"><img src="<peis:contextPath/>/img/tree/handledownmiddle.gif" alt="" border="0" width="16" height="22"></td>
      <td colspan="3" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onClick="select(this, 3, '440806', '8D271166(配变终端)', '04');" style="cursor:hand;" nowrap="nowrap"><img src="<peis:contextPath/>/img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;8D271166(配变终端)</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;">&nbsp;</td>
      <td width="16" style="padding-left:1px;"><img src="<peis:contextPath/>/img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="<peis:contextPath/>/img/tree/handlerightlast.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="2" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onClick="select(this, 4, '1001', '电表1(A080613)', 'null');" style="cursor:hand;" nowrap><img src="<peis:contextPath/>/img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;电表1(A080613)</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.4226345484093218'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;">&nbsp;</td>
      <td width="16" style="padding-left:1px;"><img src="<peis:contextPath/>/img/tree/handlerightmiddle.gif" alt="" border="0" width="16" height="22"></td>
      <td colspan="3" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onClick="select(this, 5, '440806', '8D480740(集中器)', '04');" style="cursor:hand;" nowrap="nowrap"><img src="<peis:contextPath/>/img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;8D480740(集中器)</td>
          </tr>
        </table>
      </td>
    </tr>
	<tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;">&nbsp;</td>
      <td width="16" style="padding-left:1px;"><img src="<peis:contextPath/>/img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="<peis:contextPath/>/img/tree/handlerightmiddle.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="2" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onClick="select(this, 4, '1001', '电表2(A080614)', 'null');" style="cursor:hand;" nowrap><img src="<peis:contextPath/>/img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;电表2(A080614)</td>
          </tr>
        </table>
      </td>
    </tr>
	<tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;">&nbsp;</td>
      <td width="16" style="padding-left:1px;"><img src="<peis:contextPath/>/img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="<peis:contextPath/>/img/tree/handlerightlast.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="2" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onClick="select(this, 6, '1001', '低压用户(5)', 'null');" style="cursor:hand;" nowrap><img src="<peis:contextPath/>/img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;低压用户(5)</td>
          </tr>
        </table>
      </td>
    </tr>
	 <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.4226345484093218'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;">&nbsp;</td>
      <td width="16" style="padding-left:1px;"><img src="<peis:contextPath/>/img/tree/handlerightlast.gif" alt="" border="0" width="16" height="22"></td>
      <td colspan="3" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onClick="select(this, 13, '440806', '未接集中器用户(2)', '04');" style="cursor:hand;" nowrap="nowrap"><img src="<peis:contextPath/>/img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;未接集中器用户(2)</td>
          </tr>
        </table>
      </td>
    </tr>
   </table>
</div>
</body>
</html>