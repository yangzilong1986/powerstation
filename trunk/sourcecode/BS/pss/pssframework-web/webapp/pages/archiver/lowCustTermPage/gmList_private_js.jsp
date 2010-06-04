<!-- 采集器列表私有JS -->
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../common/taglib.jsp"%>
<script type="text/javascript">
   var contextPath='<peis:contextPath/>';
   var termId = parent.termId;
   $(document).ready(function(){
      
   });
   
   function forwardTerm(){
      //alert(termId);
      window.location.href=contextPath+"/archive/lowCustTermAndTgTermAction.do?action=forward2LowCustTermDebug&termId="+termId+"";
   }
   
   //打开采集器页面

   function openCollector(){
     var url=contextPath+"/arhicve/gmInfo.do";
     parent.parent.showDialogBox("采集器信息",url, 495,800);
   }
   //打开采集器编辑页面

   function showGm(gmId){
     var url=contextPath+"/archive/gmInfoAction.do?action=showGmInfoByTermDebug&gmId="+gmId;
     parent.parent.showDialogBox("采集器信息",url, 495,800);
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
	             var msg=json['msg'];
	             if(msg=="1"){
	                alert("删除成功");
	                window.location.href = contextPath + "/archive/gmInfoListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0023&termId="+termId+"&pageRows=20";
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