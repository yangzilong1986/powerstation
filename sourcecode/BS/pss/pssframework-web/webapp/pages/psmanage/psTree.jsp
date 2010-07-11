<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>psTree</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/plugin/treeview/jquery.treeview.css" />
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/treeview/jquery.js"></script>
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/treeview/jquery.cookie.js"></script>
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/treeview/jquery.treeview.js"></script>
</head>
<body>
<div>
  <ul id="browser" class="filetree">
    <li><span class="folder">Folder 1</span>
      <ul>
        <li><span class="file">Item 1.1</span></li>
      </ul>
    </li>
    <li><span class="folder">Folder 2</span>
      <ul>
        <li><span class="folder">Subfolder 2.1</span>
          <ul id="folder21">
            <li><span class="file">File 2.1.1</span></li>
            <li><span class="file">File 2.1.2</span></li>
          </ul>
        </li>
        <li><span class="file">File 2.2</span></li>
      </ul>
    </li>
    <li class="closed"><span class="folder">Folder 3 (closed at start)</span>
      <ul>
        <li><span class="file">File 3.1</span></li>
      </ul>
    </li>
    <li><span class="file">File 4</span></li>
  </ul>
</div>
</body>
</html>