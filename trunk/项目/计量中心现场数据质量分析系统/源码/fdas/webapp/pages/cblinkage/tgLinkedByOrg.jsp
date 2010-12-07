<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<select id="${formId}" name="${formName}" style="width: 140px;" onchange="${actionChange}">
  <c:if test="${defType eq 0}"></c:if>
  <c:if test="${defType eq 1}"><option value="-1">所有台区</option></c:if>
  <c:if test="${defType eq 2}"><option value="-1">请选择台区</option></c:if>
  <c:forEach var="item" items="${tglist}">
    <option <c:if test="${item.tgId eq tgId}">selected</c:if> value="<c:out value="${item.tgId}"/>"><c:out value="${item.tgName}" /></option>
  </c:forEach>
</select>