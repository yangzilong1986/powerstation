<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>远程跳合闸</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">
function StringBuffer() {
    this.data = [];
}

StringBuffer.prototype.append = function() {
    this.data.push(arguments[0]);
    return this;
}

StringBuffer.prototype.toString = function() {
    return this.data.join("");
}

function remoteTripping() {
    alert("remoteTripping");
}

function remoteSwitching() {
    alert("remoteSwitching");
}
</script>
</head>
<body>
<div style="background: #dbeaeb; border: 1px #add0d5 solid; height:expression((document.documentElement.clientHeight||document.body.clientHeight));">
  <div style="display: none;">
    <input type="hidden" id="protocolNo" name="protocolNo" value="100" />
    <input type="hidden" id="logicalAddr" name="logicalAddr" value="96123456" />
    <input type="hidden" id="meterAddr" name="meterAddr" value="12345678" />
    <input type="hidden" id="meterType" name="meterType" value="100" />
    <input type="hidden" id="funcode" name="funcode" value="4" />
    <input type="hidden" id="port" name="port" value="1" />
    <input type="hidden" id="baudrate" name="baudrate" value="6" />
    <input type="hidden" id="stopbit" name="stopbit" value="1" />
    <input type="hidden" id="checkbit" name="checkbit" value="1" />
    <input type="hidden" id="odd_even_bit" name="odd_even_bit" value="1" />
    <input type="hidden" id="databit" name="databit" value="6" />
    <input type="hidden" id="waitforPacket" name="waitforPacket" value="100" />
    <input type="hidden" id="waitforByte" name="waitforByte" value="10" />
  </div>
  <div style="text-align: center; padding-top: expression((parentNode.clientHeight/2)-120)">
    <input type="button" id="rmtTestBtn" value="跳闸" style="width: 150px; height: 50px; cursor: pointer; font-size: 18px; font-weight: bolder;" onclick="remoteTripping()" />
    <input type="button" id="rmtTestBtn" value="合闸" style="width: 150px; height: 50px; cursor: pointer; font-size: 18px; font-weight: bolder;" onclick="remoteSwitching()" />
  </div>
  <div id="resultMsg" style="text-align: center; padding: 20px; font-family: serif; font-size: 16px;"></div>
</div>
</body>
</html>