<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="page" required="true" type="cn.org.rapid_framework.page.Page" description="Page.java" %>
<%@ attribute name="pageSizeSelectList" type="java.lang.Number[]" required="false"  %>
<%@ attribute name="isShowPageSizeList" type="java.lang.Boolean" required="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/pss.tld" prefix="pss"%>
<%
// set default values
Integer[] defaultPageSizes = new Integer[]{10,20,50,100,200};
if(jspContext.getAttribute("pageSizeSelectList") == null) {
    jspContext.setAttribute("pageSizeSelectList", defaultPageSizes);
}

if(jspContext.getAttribute("isShowPageSizeList") == null) {
    jspContext.setAttribute("isShowPageSizeList", true); 
} 
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td id="page1_footer" class="cb">
      <div id="footer_left">
        <div id="left"><c:choose><c:when test="${page.firstPage}"><img src="<pss:path type="bgcolor"/>/img/page_left0.gif" style="border:0" ></c:when><c:otherwise><a href="javascript:simpleTable.togglePage(1);"><img src="<pss:path type="bgcolor"/>/img/page_left1.gif" style="border:0" ></a></c:otherwise></c:choose></div>
        <div id="toleft"><c:choose><c:when test="${page.hasPreviousPage}"><a href="javascript:simpleTable.togglePage(${page.previousPageNumber});"><img src="<pss:path type="bgcolor"/>/img/page_toleft1.gif" style="border:0" ></a></c:when><c:otherwise><img src="<pss:path type="bgcolor"/>/img/page_toleft0.gif" style="border:0" ></c:otherwise></c:choose></div>
        <p>&nbsp;&nbsp;&nbsp; </p>
        <p class="font3">页面<input class="font1" style="text-align: right;" type="text" id="page" name="page" value="${page.thisPageNumber}"/> / ${page.lastPageNumber} </p>
        <div id="toright"><c:choose><c:when test="${page.hasNextPage}"><a href="javascript:simpleTable.togglePage(${page.nextPageNumber});"><img src="<pss:path type="bgcolor"/>/img/page_toright1.gif" style="border:0" ></a></c:when><c:otherwise><img src="<pss:path type="bgcolor"/>/img/page_toright0.gif" style="border:0" ></c:otherwise></c:choose></div>
        <div id="right"><c:choose><c:when test="${page.lastPage}"><img src="<pss:path type="bgcolor"/>/img/page_right0.gif" style="border:0"></c:when><c:otherwise><a href="javascript:simpleTable.togglePage(${page.lastPageNumber});"><img src="<pss:path type="bgcolor"/>/img/page_right1.gif" style="border:0" ></a></c:otherwise></c:choose></div>
        <p>&nbsp;&nbsp;&nbsp; </p>
        <p class="font3">每页显示<c:if test="${isShowPageSizeList}"><select onChange="simpleTable.togglePageSize(this.value)"><c:forEach var="item" items="${pageSizeSelectList}"><option value="${item}" ${page.pageSize == item ? 'selected' : '' }>${item}</option></c:forEach></select></c:if>条记录</p>
      </div>
      <div id="footer_right" class="font2"><img src="<pss:path type="bgcolor"/>/img/footer_line.gif" width="2" height="27" align="absmiddle"/>共 ${page.totalCount} 条记录，显示 ${page.thisPageFirstElementNumber} 到 ${page.thisPageLastElementNumber}</div>
    </td>
  </tr>
</table>