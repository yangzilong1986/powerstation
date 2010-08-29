<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<title><bean:message bundle="archive" key="archive.new.meter"/></title>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
  <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
  <script type="text/javascript">
    var termId=$.url.param("termId");
    var gpId=$.url.param("gpId");
    var contextPath='<peis:contextPath/>';
<<<<<<< .mine
   $(document).ready(function(){
      $("input[name='termId']").val(termId);
      $("input[name='gpId']").val(gpId);
   });
=======
    $(document).ready(function(){
       var termId=parent.termId;
       $("input[name='termId']").val(termId);
    });
>>>>>>> .r1071
   
   function forwardTerm(){
      //alert(termId);
      window.location.href=contextPath+"/archive/terminalAction1.do?action=showTerm2&termId="+termId+"&gpId="+gpId+"";
   }
<<<<<<< .mine
   
   //打开采集器页面=======
   //打开采集器页面
>>>>>>> .r1071

   function openCollector(){
     var url=contextPath+"/jsp/archive/addCollector2.jsp?termId="+termId;
     top.showDialogBox("采集器信息",url, 495,800);
   }
   //打开采集器编辑页面

   function showGm(gmId){
     var url=contextPath+"/archive/gmAction.do?action=showGmInfo&gmId="+gmId;
     top.showDialogBox("采集器信息",url, 495,800);
   }
   //删除采集器

   function delteGm(gmId){
     var url=contextPath+"/archive/gmAction.do?action=deleteGm&gmId="+gmId;
     if(confirm("确定要删除该采集器?")){
           $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
                 var termId=json['termId'];
	             var msg=json['msg'];
	             if(msg=="1"){
	                alert("删除成功");
	                parent.GB_hide();
	                top.getMainFrameObj().contentArea.location.href=contextPath+"/archive/gmList2Query.do?action=normalMode&sqlCode=AL_ARCHIVE_0023&termId="+termId+"&pageRows=20";
	             }else if(msg="3"){
	                alert("该采集器存在低压用户，不允许删除！");
	             }
	             else{
	                alert("删除失败");
	             }
	          }
            });
     }
   }
  </script>
<<<<<<< .mine
</head>
<body>
 <!-- 采集器信息 -->
     <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-50));">
       <div class="data2"><span>采集器列表</span><h1><a href="#" onclick="openCollector(); return false;"><img src="<peis:contextPath/>/img/bt_add.png" width="19" height="19" class="mgt5" /></a></h1></div>
       <div class="data2_con">
         <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <thead>
             <tr>
              <th>序号</th>
              <th>资产号</th>
              <th>采集器地址</th>
              <th>厂家</th>
              <th>型号</th>
              <th>操作</th>
             </tr>
            </thead>
            <tbody align="center">
                <logic:present name="PG_QUERY_RESULT">
                <logic:iterate id="gm" name="PG_QUERY_RESULT">
                  <tr  onclick="selectSingleRow(this)" style="cursor:pointer;">
                    <td>
                      <bean:write name="gm" property="rowNo" />
                    </td>
                    <td>
                      <bean:write name="gm" property="col1"/>
                    </td>
                    <td>
                      <bean:write name="gm" property="col2"/>
                    </td>
                    <td>
                      <bean:write name="gm" property="col3"/>
                    </td>
                    <td>t
                      <bean:write name="gm" property="col4"/>
                    </td>
                    <td>
                      <a href="javascript:showGm('<bean:write name="gm" property="col5"/>');"><bean:message  key="global.edit"/></a>|
                      <a href="javascript:delteGm('<bean:write name="gm" property="col5"/>')"><bean:message  key="global.delete"/></a>
                    </td>
                  </tr>
                </logic:iterate>
                </logic:present>
            </tbody>
          </table>
      </div>
     </div>
      <input type="hidden" name="termId" />
      <html:form action="/archive/gmList2Query.do">
      <html:hidden property="action" value="normalMode"/>
      <html:hidden property="pageType" value="page"/>
      <html:hidden property="sqlCode" value="AL_ARCHIVE_0023"/>
      <div class="pageContainer">
        <peis:paging actionForm="archiveQueryForm" rowsChange="true" />
      </div>
      <input type="hidden" name="gpId" />
      </html:form>
</body>
=======
 </head>
 <body>
  <!-- 采集器信息 -->
  <div class="tab_con" style="height: expression((( document.documentElement.clientHeight || document.body.clientHeight) -50 ) );">
   <div class="data2">
    <span>采集器列表</span>
    <h1>
     <a href="#" onclick="openCollector(); return false;"><img src="<peis:contextPath/>/img/bt_add.png" width="19" height="19" class="mgt5" />
     </a>
    </h1>
   </div>
   <div class="data2_con">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
     <thead>
      <tr>
       <th>
        序号
       </th>
       <th>
        资产号

       </th>
       <th>
        采集器地址
       </th>
       <th>
        厂家
       </th>
       <th>
        型号
       </th>
       <th>
        操作
       </th>
      </tr>
     </thead>
     <tbody align="center">
      <logic:present name="PG_QUERY_RESULT">
       <logic:iterate id="gm" name="PG_QUERY_RESULT">
        <tr onclick="selectSingleRow(this)" style="cursor: pointer;">
         <td>
          <bean:write name="gm" property="rowNo" />
         </td>
         <td>
          <bean:write name="gm" property="col1" />
         </td>
         <td>
          <bean:write name="gm" property="col2" />
         </td>
         <td>
          <bean:write name="gm" property="col3" />
         </td>
         <td>
          t
          <bean:write name="gm" property="col4" />
         </td>
         <td>
          <a href="javascript:showGm('<bean:write name="gm" property="col5"/>');"><bean:message key="global.edit" />
          </a>|
          <a href="javascript:delteGm('<bean:write name="gm" property="col5"/>')"><bean:message key="global.delete" />
          </a>
         </td>
        </tr>
       </logic:iterate>
      </logic:present>
     </tbody>
    </table>
   </div>
  </div>
  <html:form action="/archive/gmList2Query.do">
   <input type="hidden" name="termId" />
   <html:hidden property="action" value="normalMode" />
   <html:hidden property="pageType" value="page" />
   <html:hidden property="sqlCode" value="AL_ARCHIVE_0023" />
   <div class="pageContainer">
    <peis:paging actionForm="archiveQueryForm" rowsChange="true" />
   </div>
  </html:form>
 </body>
>>>>>>> .r1071
</html>
