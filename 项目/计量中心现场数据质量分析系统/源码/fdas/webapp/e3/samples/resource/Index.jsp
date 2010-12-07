<%@ include file="/e3/commons/Common.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
</HEAD>
<BODY> 
<table>
    <tr>
      <td colspan="3">
E3.Resource是E3平台下一个用于管理静态资源(css,js,imager等)的组件,E3.Resource<br>
功能特性:<br>
1.	采用Servlet实现对资源的压缩处理，对系统设计、开发没任何要求，只需要在系统部署阶段，添加些配置文件就可以或可以获得资源压缩服务，所以使用E3.Resource没有什么风险.<br>
2.	动态压缩js,css等静态资源<br>
3.	提供多种压缩策略，有jsMin, cssMin, GZip等压缩算法，也可以根据需要自定义压缩算法<br>
4.	能够对压缩结果进行cache处理，支持内存cache,  ehcahce等多种cache算法，也可以根据需要自定义cache算法.<br>      
      </td>
    </tr>
    <tr>
      <td><a href="http://code.google.com/p/ie3/downloads/list">E3.Resource下载</a></td>
      <td></td>
      <td></td>            
    </tr>
    
  </table>
</BODY>
</HTML>


