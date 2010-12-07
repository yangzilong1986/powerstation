<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>psTree</title>
<style type="text/css">
li {white-space: nowrap;}

a.selected:link {background-color: #72CE28; color: #104F4F; font-weight: bold;}
a.selected:hover {background-color: #72CE28; color: #104F4F; font-weight: bold;}
a.selected:visited {background-color: #72CE28; color: #104F4F; font-weight: bold;}
a.selected:active {background-color: #72CE28; color: #104F4F; font-weight: bold;}

a:link {background-color: #FFFFFF; color: #000000;}
a:hover {background-color: #FFFFFF; color: #000000;}
a:visited {background-color: #FFFFFF; color: #000000;}
a:active {background-color: #FFFFFF; color: #000000;}
</style>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/plugin/treeview/jquery.treeview.css" />
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/plugin/treeview/screen.css" />
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/treeview/jquery.js"></script>
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/treeview/jquery.cookie.js"></script>
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/treeview/jquery.treeview.js"></script>
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.query.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    
    // first example
    $("#navigation").treeview({
        persist: "location",
        collapsed: true,
        unique: true
    });
    
    // second example
    $("#browser").treeview();
    $("#add").click(function() {
        var branches = $("<li><span class='folder'>New Sublist</span><ul>" + 
            "<li><span class='file'>Item1</span></li>" + 
            "<li><span class='file'>Item2</span></li></ul></li>").appendTo("#browser");
        $("#browser").treeview({
            add: branches
        });
        branches = $("<li class='closed'><span class='folder'>New Sublist</span><ul><li><span class='file'>Item1</span></li><li><span class='file'>Item2</span></li></ul></li>").prependTo("#folder21");
        $("#browser").treeview({
            add: branches
        });
    });
    
    // third example
    $("#red").treeview({
        animated: "fast",
        collapsed: true,
        unique: true,
        persist: "cookie",
        toggle: function() {
            window.console && console.log("%o was toggled", this);
        }
    });
    
    // fourth example
    $("#black, #gray").treeview({
        control: "#treecontrol",
        persist: "cookie",
        cookieId: "treeview-black"
    });

    /*var psType = $.query.get('type');
    $("a.psobj").each( function() {
        if("psmon" == psType) {
            $(this).attr('href', '<pss:path type="webapp"/>/pages/psmanage/psMonitor.jsp?psId=1&random=' + Math.random());
            $(this).attr('target', 'psMonitorFrame');
        }
        else if("rmttest" == psType) {
            $(this).attr('href', '<pss:path type="webapp"/>/pages/psmanage/psRemoteTest.jsp?psId=1&random=' + Math.random());
            $(this).attr('target', 'psRemoteTestFrame');
        }
        else if("rmttpsw" == psType) {
            $(this).attr('href', '<pss:path type="webapp"/>/pages/psmanage/psRemoteTpSw.jsp?psId=1&random=' + Math.random());
            $(this).attr('target', 'psRemoteTpSwFrame');
        }
    });*/

    $("a.psobj").click( function() {
        selectSingleRow(this);
    });
});

/**
 * 
 */
var pre_row = null;
function selectSingleRow(row) {
    $(row).addClass("selected");
    if(pre_row != null && pre_row != row) {
        $(pre_row).removeClass("selected");
    }
    pre_row = row;
}
</script>
</head>
<body style="overflow: auto;">
<div>
  <ul id="browser" class="filetree">
    <c:forEach items="${tree}" var="item" varStatus="status">
    <li><span class="folder"><c:out value='${item.treeNodeName}'/></span>
      <ul>
        <c:forEach items="${item.children}" var="cItem" varStatus="cStatus">
        <li><span class="folder"><c:out value='${cItem.treeNodeName}'/></span>
          <ul>
            <c:forEach items="${cItem.children}" var="ccItem" varStatus="ccStatus">
            <li><span class="file"><a id="a_${ccStatus.index}" class="psobj" href="<pss:path type="webapp"/>/psmanage/psmon/psmonitor/<c:out value='${ccItem.treeNodeId}'/>" target="psMonitorFrame"><c:out value='${ccItem.treeNodeName}'/></a></span></li>
            </c:forEach>
          </ul>
        </li>
        </c:forEach>
      </ul>
    </li>
    </c:forEach>
  </ul>
</div>
</body>
</html>