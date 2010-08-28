<script language="JavaScript"> 
<!-- 
var gt = unescape('%3e'); 
var popup = null; 
var over = "Launch Pop-up Navigator"; 
var url ='${pageContext.request.contextPath}/security/login'
popup = window.open(url, '_blank', 'top=0,left=0,resizable=no,titlebar=yes,menubar=no,status=no,location=no,scrollbars=no,width='+(screen.availWidth - 10)+',height=' + (screen.availHeight - 30)); 
if(popup != null) {
    if(popup.opener == null) {
        popup.opener = self;
    }
    else {
        popup.opener.close();
    }
} 
// --> 
</script> 
