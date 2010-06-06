<!-- 采集器列表私有JS -->
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../common/taglib.jsp"%>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';
var termId=parent.contentArea.termId;//取父页面的终端ID
var orgNo=parent.contentArea.orgNo;//取父页面的ORGNO
//初始化加载

$(function(){
  $("input[name='termId']").val(termId);
  $("input[name='orgNo']").val(orgNo);
})
//保存采集器信息

function save(){
    var url=contextPath+"/archive/gmInfoAction.do?action=saveOrUpdateGm";
      if(GMCheck()){
        $("#save").attr("disabled",true);
        var data = getFormData("form");
        if(data){
            jQuery.ajax({
                type:'post',
                url:url,
                data:data,
                dataType:'json',
                success:function(json){
                    var msg=json['msg'];
                    $("#save").attr("disabled",false);
                    if(msg=="1"){
                       alert("保存成功");
                       parent.GB_hide();
                       //top.getMainFrameObj().contentArea.location.href=contextPath+"/archive/gmList5Query.do?action=normalMode&sqlCode=AL_ARCHIVE_0023&termId="+termId+"&pageRows=20";
                       parent.contentArea.contentArea.location.href=contextPath + "/archive/gmInfoListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0023&termId="+termId+"&pageRows=20";
                    }else if(msg=="2"){
                       alert("资产编号已经存在");
                    }else{
                       alert("保存失败");
                    }
                }
            });
        }
      }
}
</script>