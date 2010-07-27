<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>psTree</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/plugin/treeview/jquery.treeview.css" />
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/plugin/treeview/screen.css" />
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/treeview/jquery.js"></script>
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/treeview/jquery.cookie.js"></script>
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/treeview/jquery.treeview.js"></script>
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

});
</script>
</head>
<body>
<div>
  <ul id="browser" class="filetree">
    <li><span class="folder">乾龙1#</span>
      <ul>
        <li><span class="folder">91010001</span>
          <ul id="folder21">
            <li><span class="file"><a href="<pss:path type="webapp"/>/pages/psmanage/psMonitor.jsp?psId=1" target="psMonitorFrame">CS0001</a></span></li>
            <li><span class="file"><a href="<pss:path type="webapp"/>/pages/psmanage/psMonitor.jsp?psId=2" target="psMonitorFrame">CS0002</a></span></li>
          </ul>
        </li>
      </ul>
    </li>
    <li><span class="folder">乾龙2#</span>
      <ul>
        <li><span class="folder">91010002</span>
          <ul id="folder21">
            <li><span class="file"><a href="<pss:path type="webapp"/>/pages/psmanage/psMonitor.jsp?psId=3" target="psMonitorFrame">CS0003</a></span></li>
            <li><span class="file"><a href="<pss:path type="webapp"/>/pages/psmanage/psMonitor.jsp?psId=4" target="psMonitorFrame">CS0004</a></span></li>
          </ul>
        </li>
        <li><span class="folder">91010003</span>
          <ul id="folder21">
            <li><span class="file"><a href="<pss:path type="webapp"/>/pages/psmanage/psMonitor.jsp?psId=5" target="psMonitorFrame">CS0005</a></span></li>
            <li><span class="file"><a href="<pss:path type="webapp"/>/pages/psmanage/psMonitor.jsp?psId=6" target="psMonitorFrame">CS0006</a></span></li>
          </ul>
        </li>
      </ul>
    </li>
  </ul>
</div>
</body>
</html>