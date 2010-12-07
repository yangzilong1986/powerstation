<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>



<logic:notEmpty name="CONTROL_GRATHERID">
<script type="text/javascript">
parent.document.getElementById("gatherID").value = "<bean:write name='CONTROL_GRATHERID'/>";
parent.fetch(1,0,'');
</script>
</logic:notEmpty>

<logic:notEmpty name="ADD_USER_SUCCESS">
<script type="text/javascript">
  top.getMainFrameObj().groupContent.flusUserList();
  parent.parent.GB_hide();
</script>
</logic:notEmpty>

<logic:notEmpty name="SYNCH_ADDR_SUCCESS">
<script type="text/javascript">
  <logic:notEmpty name="gatherID">
     parent.document.getElementById("gatherID").value = "<bean:write name='gatherID'/>";
     parent.fetch(1,0,'');
  </logic:notEmpty>
</script>
</logic:notEmpty>

<logic:notEmpty name="POWER_DEBASE_SUCCESS">
<script type="text/javascript">
  <logic:notEmpty name="gatherID">
     var gather_id = "<bean:write name='gatherID'/>";
     parent.showResult(1,0,gather_id);
  </logic:notEmpty>
</script>
</logic:notEmpty>
