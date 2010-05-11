<%@ include file="/e3/commons/Common.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
 
<link type='text/css' rel='stylesheet' href='<c:url value="/e3/commons/ext/resources/css/ext-all.css"/>' />
<script src="<c:url value="/e3/commons/ext/adapter/ext/ext-base.js"/>"></script>
<script src="<c:url value="/e3/commons/ext/ext-all.js"/>"></script>

<script>
function openURL(pURL){
  parent.mainFrame.location.href  = pURL;
}
function goHome(pURL){
 top.location.href = pURL;
}


    Ext.onReady(function(){

        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
       var viewport = new Ext.Viewport({
            layout:'border',
            items:[
                {
                    region:'west',
                    contentEl: 'tree',
                    split:true,
                    autoScroll:true,
                    collapsible: true,
                    width : 150,
                    margins:'0 0 0 0'
                },
                {
                    region:'center',
                   layout:'border',
                   items : [
                     {
                    region:'north',
                    contentEl: 'banner',
                    height : 80,
                    margins:'0 0 0 0'                     
                     },
                     {
                    region:'center',
                    contentEl: 'bodyFrame',
                    split:true,
                    autoScroll:true,
                    collapsible: true,
                    margins:'0 0 0 0'
                     
                     }
                     
                   ]
                }
                
            ]});
        });

</script>
</HEAD>
<BODY> 
<%= request.getAttribute("treeScript") %>
<div id="banner">
<table><tr><td>
<IMG oncontextmenu="return false" alt=图片 
src="<c:url value="/e3/ad/logo.gif"/>">
三号群:63787587  四号群：29740535  五号群:11627333 
</td></tr></table>
  
</div>
<div id="footer">
 <table width="100%" height="100%">
   <tr>
   <TD>
      <DIV align=center>
      <MARQUEE scrollAmount=2 behavior=alternate><SPAN 
      style="DISPLAY: none">&nbsp;</SPAN>&nbsp;&nbsp;
      
      <A 
href="http://auction1.taobao.com/auction/item_detail-db1-d5db5574047b20212ea4548283c2c2da.jhtml" 
target=_blank><IMG oncontextmenu="return false" alt=图片 
src="<c:url value="/e3/ad/T1Oz0cXipuAQEpgoUT_013352.jpg_m.jpg"/>"> 
</A>
     <A 
href="http://auction1.taobao.com/auction/item_detail-db1-b81b6212ffedec49bf3f2de0751da34b.jhtml" 
target=_blank><IMG oncontextmenu="return false" alt=图片 
src="<c:url value="/e3/ad/mayi.jpg"/>"> 
</A>
     <A 
href="http://auction1.taobao.com/auction/item_detail-db1-1634a8930478b3f3f531702960786b8f.jhtml" 
target=_blank><IMG oncontextmenu="return false" alt=图片 
src="<c:url value="/e3/ad/T1lz0cXiikd0Km8vZ0_034945.jpg_m.jpg"/>"> 
</A>      

     <A 
href="http://auction1.taobao.com/auction/item_detail-db1-1afaec71e45f402bfc91b726168f9dcd.jhtml" 
target=_blank><IMG oncontextmenu="return false" alt=图片 
src="<c:url value="/e3/ad/T1sARcXi8jBwExyfZ5_055633.jpg_m.jpg"/>"> 
</A>

     <A 
href="http://auction1.taobao.com/auction/item_detail-db1-30ef4e78de8dd3310633a2f169b3a7ae.jhtml" 
target=_blank><IMG oncontextmenu="return false" alt=图片 
src="<c:url value="/e3/ad/T10P4cXo8jAQDUTLA1_041136.jpg_m.jpg"/>"> 
</A>      
      </MARQUEE></DIV></TD>
   </tr>
 </table>
</div>

<div id="bodyFrame">
 <iframe name="mainFrame"  src="" frameborder="0" width="100%" height="100%"></iframe>
</div>
 






</BODY>
</HTML>


