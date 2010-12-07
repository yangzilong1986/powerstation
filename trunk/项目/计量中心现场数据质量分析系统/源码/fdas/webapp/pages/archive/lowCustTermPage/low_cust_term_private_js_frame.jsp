<!-- 集中器维护私有JS -->
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/../common/taglib.jsp"%>
<script type="text/javascript">
  var contextPath = '<peis:contextPath/>';
  var termId=$.url.param("termId");
  var orgNo;//采集器录入的时候需要这个字段
   $(document).ready(function(){
      $('#contentArea').attr("src",contextPath+"/archive/lowCustTermAndTgTermAction.do?action=forward2LowCustTermDebug&termId="+termId+"");
  });
   function setTarget(i) {
    if(i == 1) {
       $('#te').attr("class","target_on");
       $('#gm').attr("class","target_off");
       frames['contentArea'].forwardTerm();//返回集中器页面
    }
    else if(i == 2) {
       $('#te').attr("class","target_off");
       $('#gm').attr("class","target_on");
       frames['contentArea'].saveAndShowCollect(); //保存集中器信息，并跳转到采集器页面
    }
  }
</script>