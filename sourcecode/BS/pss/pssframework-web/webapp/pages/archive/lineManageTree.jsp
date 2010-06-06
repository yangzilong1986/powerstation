<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>对象树</title>
<link rel="stylesheet" type="text/css" href="../../css/treeframe.css" />
<script type="text/javascript" src="../../js/jquery.js"></script>
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
      <td width="16" style="padding-left:1px;"><a name='0.3301204558412191'></a><a href="#" onclick="return false;"><img src="../../img/tree/handledownlast.gif" alt="close node" border="0" width="16" height="22"></a></td>
      <td colspan="5" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 13, '44', '湛江供电局', '02');" style="cursor:hand;" nowrap="nowrap"><img src="../../img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;湛江供电局</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7969896755579414'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><a href="#" onclick="return false;"><img src="../../img/tree/handlerightmiddle.gif" alt="expand node" border="0" width="16" height="22"></a></td>
      <td colspan="4" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td  onclick="select(this, 13, '4401', '客户服务中心', '03');" style="cursor:hand;" nowrap="nowrap"><img src="../../img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;客户服务中心</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7969896755579916'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><a href="#" onclick="return false;"><img src="../../img/tree/handledownmiddle.gif" alt="expand node" border="0" width="16" height="22"></a></td>
      <td colspan="4" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 13, '4408', '雷州供电所', '03');" style="cursor:hand;" nowrap="nowrap"><img src="../../img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;雷州供电所</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.4226345484093218'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handlerightmiddle.gif" alt="" border="0" width="16" height="22"></td>
      <td colspan="3" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 13, '440806', '雷州财务部', '04');" style="cursor:hand;" nowrap="nowrap"><img src="../../img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;雷州财务部</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.4226345484093218'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handledownmiddle.gif" alt="" border="0" width="16" height="22"></td>
      <td colspan="3" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 13, '440806', '雷州市场部', '04');" style="cursor:hand;" nowrap="nowrap"><img src="../../img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;雷州市场部</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handledownmiddle.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="2" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 1, '1001', '白沙供电所', 'null');" style="cursor:hand;" nowrap><img src="../../img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;白沙供电所</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handlerightmiddle.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="1" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 1, '1001', '白沙主干线', 'null');" style="cursor:hand;" nowrap><img src="../../img/tree/object_line.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;白沙主干线</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handlerightlast.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="1" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 1, '1001', '合兴主干线', 'null');" style="cursor:hand;" nowrap><img src="../../img/tree/object_line.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;合兴主干线</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handledownmiddle.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="2" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 1, '1001', '北和供电所', 'null');" style="cursor:hand;" nowrap><img src="../../img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;北和供电所</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handlerightmiddle.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="1" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 1, '1001', '调罗线', 'null');" style="cursor:hand;" nowrap><img src="../../img/tree/object_line.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;调罗线</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handlerightmiddle.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="1" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 1, '1001', '康港线', 'null');" style="cursor:hand;" nowrap><img src="../../img/tree/object_line.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;康港线</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handlerightmiddle.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="1" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 1, '1001', '里港线', 'null');" style="cursor:hand;" nowrap><img src="../../img/tree/object_line.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;里港线</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handlerightmiddle.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="1" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 1, '1001', '未分配部门客户', 'null');" style="cursor:hand;" nowrap><img src="../../img/tree/object_line.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;未分配部门客户</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handlerightlast.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="1" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 1, '1001', '纡区线', 'null');" style="cursor:hand;" nowrap><img src="../../img/tree/object_line.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;纡区线</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7093978125384423'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handlerightlast.gif" alt="expand node" border="0" width="16" height="22"></td>
      <td colspan="2" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 1, '1001', '城内供电所', 'null');" style="cursor:hand;" nowrap><img src="../../img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;城内供电所</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.4226345484093218'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handlerightmiddle.gif" alt="" border="0" width="16" height="22"></td>
      <td colspan="3" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 13, '440806', '雷州计量部', '04');" style="cursor:hand;" nowrap="nowrap"><img src="../../img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;雷州计量部</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.4226345484093218'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/linevertical.gif" alt="" border="0" width="16" height="22"></td>
      <td width="16" style="padding-left:1px;"><img src="../../img/tree/handlerightlast.gif" alt="" border="0" width="16" height="22"></td>
      <td colspan="3" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 13, '440806', '雷州生计部', '04');" style="cursor:hand;" nowrap="nowrap"><img src="../../img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;雷州生计部</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr valign="middle">
      <td width="16" style="padding-left:1px;"><a name='0.7969896755579916'>&nbsp;</a></td>
      <td width="16" style="padding-left:1px;"><a href="#" onclick="return false;"><img src="../../img/tree/handlerightlast.gif" alt="expand node" border="0" width="16" height="22"></a></td>
      <td colspan="4" width="99%" style="padding-left:1px;">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td onclick="select(this, 13, '4408', '遂溪供电所', '03');" style="cursor:hand;" nowrap="nowrap"><img src="../../img/tree/object_org.gif" alt="" border="0" width="15" height="15" align="bottom">&nbsp;遂溪供电所</td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</div>
</body>
</html>