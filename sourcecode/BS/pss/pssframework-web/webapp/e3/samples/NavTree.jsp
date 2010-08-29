<%@ include file="/e3/commons/Common.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="e3" uri="/e3/tree/E3Tree.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<script>
function openURL(pURL){
  parent.mainFrame.location.href  = pURL;
}
function goHome(pURL){
 top.location.href = pURL;
}  



    function treeRenderAfterHandler(pTree){
     
       var viewport = new Ext.Viewport({
            layout:'border',
            items:[
                {
                region: 'west',
                id: 'treeDiv', // see Ext.getCmp() below
                contentEl : 'tree',
                split: true,
                width: 200,
                minSize: 175,
                maxSize: 400,
                collapsible: true,
                margins: '0 0 0 0',
                layout: {
                    type: 'accordion',
                    animate: true
                }
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
                     
                     },
                     {
                     collapsible:true,
                     title : 'E3毛绒玩具推荐[ <a href="http://shop35115979.taobao.com/" target="_blank">www.shuilianyue.com</a>]',
                    region:'south',
                    contentEl: 'footer',
                    height : 140,
                    margins:'0 0 0 0'                     
                     
                     }
                     
                   ]
                }
                
            ]});

           // Ext.getCmp("treeDiv").doLayout();
            Ext.getCmp("treeDiv").on("resize",function(pPanel,pWidth,pHeight){
              pTree.setHeight(pHeight-3);
              pTree.setWidth(pWidth-2);
            });
            pTree.setHeight(Ext.getCmp("treeDiv").getInnerHeight()-3);
            pTree.setWidth(Ext.getCmp("treeDiv").getInnerWidth()-2);
           setTimeout(
				function() {
				    Ext.get("banner").dom.style.display="block";
				    Ext.get("footer").dom.style.display="block";
				    Ext.get("bodyFrame").dom.style.display="block";
					Ext.get('loading').remove();
					Ext.get('loading-mask').fadeOut({remove:true});
				}, 250
			  );            
        }

</script>
<style>
.loading-indicator {
  BACKGROUND-POSITION: left top;
  PADDING-LEFT: 20px;
  FONT-SIZE: 11px;
  BACKGROUND-IMAGE: url(img/loading.gif);
  BACKGROUND-REPEAT: no-repeat;
  HEIGHT: 18px;
  TEXT-ALIGN: left
}

#loading-mask {
  Z-INDEX: 20000;
  LEFT: 0px;
  WIDTH: 100%;
  POSITION: absolute;
  TOP: 0px;
  HEIGHT: 100%;
  BACKGROUND-COLOR: white
}

#loading {
  PADDING-RIGHT: 2px;
  PADDING-LEFT: 2px;
  Z-INDEX: 20001;
  LEFT: 45%;
  PADDING-BOTTOM: 2px;
  PADDING-TOP: 2px;
  POSITION: absolute;
  TOP: 40%;
  HEIGHT: auto
}

#loading IMG {
  MARGIN-BOTTOM: 5px
}

#loading .loading-indicator {
  PADDING-RIGHT: 10px;
  PADDING-LEFT: 10px;
  BACKGROUND: white;
  PADDING-BOTTOM: 10px;
  MARGIN: 0px;
  FONT: bold 13px tahoma, arial, helvetica;
  COLOR: #555;
  PADDING-TOP: 10px;
  HEIGHT: auto;
  TEXT-ALIGN: center
}

.logintext {
  FONT: bold 12px tahoma, arial, sans-serif
}
</style>
</HEAD>
<BODY>
<!--显示loding区域-->
<DIV id=loading-mask></DIV>
<DIV id=loading>
<DIV class=loading-indicator><IMG style="MARGIN-RIGHT: 8px" height=32
  src="<c:url value="/e3/commons/img/loading.gif"/>" width=32 align=absMiddle>loading...</DIV>
</DIV>
<div id="banner" style="display: none">
<table>
  <tr>
    <td><IMG oncontextmenu="return false" alt=图片 src="<c:url value="/e3/ad/logo.gif"/>"> 三号群:63787587
    四号群：29740535 五号群:11627333</td>
  </tr>
</table>
</div>
<div id="footer" style="display: none">
<table width="100%" height="100%">
  <tr>
    <TD>
    <DIV align=center><MARQUEE scrollAmount=2 behavior=alternate><SPAN style="DISPLAY: none">&nbsp;</SPAN>&nbsp;&nbsp;
    <A href="http://auction1.taobao.com/auction/item_detail-db1-d5db5574047b20212ea4548283c2c2da.jhtml" target=_blank><IMG
      oncontextmenu="return false" alt=图片 src="<c:url value="/e3/ad/T1Oz0cXipuAQEpgoUT_013352.jpg_m.jpg"/>"> </A> <A
      href="http://auction1.taobao.com/auction/item_detail-db1-b81b6212ffedec49bf3f2de0751da34b.jhtml" target=_blank><IMG
      oncontextmenu="return false" alt=图片 src="<c:url value="/e3/ad/mayi.jpg"/>"> </A> <A
      href="http://auction1.taobao.com/auction/item_detail-db1-1634a8930478b3f3f531702960786b8f.jhtml" target=_blank><IMG
      oncontextmenu="return false" alt=图片 src="<c:url value="/e3/ad/T1lz0cXiikd0Km8vZ0_034945.jpg_m.jpg"/>"> </A> <A
      href="http://auction1.taobao.com/auction/item_detail-db1-1afaec71e45f402bfc91b726168f9dcd.jhtml" target=_blank><IMG
      oncontextmenu="return false" alt=图片 src="<c:url value="/e3/ad/T1sARcXi8jBwExyfZ5_055633.jpg_m.jpg"/>"> </A> <A
      href="http://auction1.taobao.com/auction/item_detail-db1-30ef4e78de8dd3310633a2f169b3a7ae.jhtml" target=_blank><IMG
      oncontextmenu="return false" alt=图片 src="<c:url value="/e3/ad/T10P4cXo8jAQDUTLA1_041136.jpg_m.jpg"/>"> </A> </MARQUEE></DIV>
    </TD>
  </tr>
</table>
</div>
<div id="bodyFrame" style="display: none"><iframe name="mainFrame" scrolling="auto" src="" frameborder="0"
  width="100%" height="100%"></iframe></div>
<%
	String contextPath = request.getContextPath();
	String path = null;
	if ("/".equals(contextPath)) {
		path = "";
	} else {
		path = contextPath;
	}
%>
<%
	java.util.List datas = new java.util.ArrayList();
	java.util.Map root = new java.util.HashMap();
	root.put("id", "root");
	root.put("parentId", null);
	root.put("name", "E3-演示系统");
	root.put("action", path + "/e3/samples/table/Blank.jsp");
	datas.add(root);

	java.util.Map tree = new java.util.HashMap();
	tree.put("id", "tree");
	tree.put("parentId", "root");
	tree.put("name", "E3.Tree");
	tree.put("action", path + "/e3/samples/table/Blank.jsp");
	datas.add(tree);
	//-----------------------------------begin ext tree ----------------------
	java.util.Map tree_ext = new java.util.HashMap();
	tree_ext.put("id", "tree_ext");
	tree_ext.put("parentId", "tree");
	tree_ext.put("name", "ext");
	tree_ext.put("action", path + "/e3/samples/table/Blank.jsp");
	datas.add(tree_ext);

	java.util.Map tree_ext_simple = new java.util.HashMap();
	tree_ext_simple.put("id", "tree_ext_simple");
	tree_ext_simple.put("parentId", "tree_ext");
	tree_ext_simple.put("name", "普通树");
	tree_ext_simple.put("action", path + "/servlet/xtreeServlet?_actionType=showExtTree");
	datas.add(tree_ext_simple);

	java.util.Map tree_ext_right_menu = new java.util.HashMap();
	tree_ext_right_menu.put("id", "tree_ext_right_menu");
	tree_ext_right_menu.put("parentId", "tree_ext");
	tree_ext_right_menu.put("name", "右键菜单树");
	tree_ext_right_menu.put("action", path + "/servlet/xtreeServlet?_actionType=showExtRightMenuTree");
	datas.add(tree_ext_right_menu);

	java.util.Map tree_ext_check = new java.util.HashMap();
	tree_ext_check.put("id", "tree_ext_check");
	tree_ext_check.put("parentId", "tree_ext");
	tree_ext_check.put("name", "复选树");
	tree_ext_check.put("action", path + "/servlet/xtreeServlet?_actionType=showExtPrvCheckTree");
	datas.add(tree_ext_check);

	java.util.Map tree_ext_outlook = new java.util.HashMap();
	tree_ext_outlook.put("id", "tree_ext_outlook");
	tree_ext_outlook.put("parentId", "tree_ext");
	tree_ext_outlook.put("name", "Outlook树");
	tree_ext_outlook.put("action", path + "/servlet/xtreeServlet?_actionType=showOutlookTree");
	datas.add(tree_ext_outlook);

	java.util.Map tree_ext_load = new java.util.HashMap();
	tree_ext_load.put("id", "tree_ext_load");
	tree_ext_load.put("parentId", "tree_ext");
	tree_ext_load.put("name", "动态树");
	tree_ext_load.put("action", path + "/servlet/xtreeServlet?_actionType=showExtLoadTree");
	datas.add(tree_ext_load);

	java.util.Map tree_ext_drag = new java.util.HashMap();
	tree_ext_drag.put("id", "tree_ext_drag");
	tree_ext_drag.put("parentId", "tree_ext");
	tree_ext_drag.put("name", "拖拽树");
	tree_ext_drag.put("action", path + "/servlet/xtreeServlet?_actionType=showExtDragTree");
	datas.add(tree_ext_drag);

	java.util.Map tree_ext_icon = new java.util.HashMap();
	tree_ext_icon.put("id", "tree_ext_icon");
	tree_ext_icon.put("parentId", "tree_ext");
	tree_ext_icon.put("name", "自定义图标");
	tree_ext_icon.put("action", path + "/servlet/xtreeServlet?_actionType=showExtIconTree");
	datas.add(tree_ext_icon);

	java.util.Map tree_ext_toggle = new java.util.HashMap();
	tree_ext_toggle.put("id", "tree_ext_toggle");
	tree_ext_toggle.put("parentId", "tree_ext");
	tree_ext_toggle.put("name", "切换树");
	tree_ext_toggle.put("action", path + "/e3/samples/tree/ToggleTree.jsp");
	datas.add(tree_ext_toggle);

	java.util.Map tree_ext_dbl_tree = new java.util.HashMap();
	tree_ext_dbl_tree.put("id", "tree_ext_dbl_tree");
	tree_ext_dbl_tree.put("parentId", "tree_ext");
	tree_ext_dbl_tree.put("name", "多树");
	tree_ext_dbl_tree.put("action", path + "/servlet/xtreeServlet?_actionType=showExt2IconTree");
	datas.add(tree_ext_dbl_tree);

	//-----------------------------------end ext tree ----------------------

	//-----------------------------------begin xtree tree ----------------------
	java.util.Map tree_xtree = new java.util.HashMap();
	tree_xtree.put("id", "tree_xtree");
	tree_xtree.put("parentId", "tree");
	tree_xtree.put("name", "xtree");
	tree_xtree.put("action", path + "/e3/samples/table/Blank.jsp");
	datas.add(tree_xtree);

	java.util.Map tree_xtree_simple = new java.util.HashMap();
	tree_xtree_simple.put("id", "tree_xtree_simple");
	tree_xtree_simple.put("parentId", "tree_xtree");
	tree_xtree_simple.put("name", "简单树");
	tree_xtree_simple.put("action", path + "/servlet/xtreeServlet?_actionType=showTree");
	datas.add(tree_xtree_simple);

	java.util.Map tree_xtree_radio = new java.util.HashMap();
	tree_xtree_radio.put("id", "tree_xtree_radio");
	tree_xtree_radio.put("parentId", "tree_xtree");
	tree_xtree_radio.put("name", "单选树");
	tree_xtree_radio.put("action", path + "/servlet/xtreeServlet?_actionType=showRadioTree");
	datas.add(tree_xtree_radio);

	java.util.Map tree_xtree_check = new java.util.HashMap();
	tree_xtree_check.put("id", "tree_xtree_check");
	tree_xtree_check.put("parentId", "tree_xtree");
	tree_xtree_check.put("name", "复选树");
	tree_xtree_check.put("action", path + "/servlet/xtreeServlet?_actionType=showCheckTree");
	datas.add(tree_xtree_check);

	java.util.Map tree_xtree_composite = new java.util.HashMap();
	tree_xtree_composite.put("id", "tree_xtree_composite");
	tree_xtree_composite.put("parentId", "tree_xtree");
	tree_xtree_composite.put("name", "组合树");
	tree_xtree_composite.put("action", path + "/servlet/xtreeServlet?_actionType=showCompositeTree");
	datas.add(tree_xtree_composite);

	java.util.Map tree_xtree_mix = new java.util.HashMap();
	tree_xtree_mix.put("id", "tree_xtree_mix");
	tree_xtree_mix.put("parentId", "tree_xtree");
	tree_xtree_mix.put("name", "混合树");
	tree_xtree_mix.put("action", path + "/servlet/xtreeServlet?_actionType=showMixTree");
	datas.add(tree_xtree_mix);

	java.util.Map tree_xtree_load = new java.util.HashMap();
	tree_xtree_load.put("id", "tree_xtree_load");
	tree_xtree_load.put("parentId", "tree_xtree");
	tree_xtree_load.put("name", "动态树");
	tree_xtree_load.put("action", path + "/servlet/xtreeServlet?_actionType=showLoadTree");
	datas.add(tree_xtree_load);

	java.util.Map tree_xtree_radio_load = new java.util.HashMap();
	tree_xtree_radio_load.put("id", "tree_xtree_radio_load");
	tree_xtree_radio_load.put("parentId", "tree_xtree");
	tree_xtree_radio_load.put("name", "动态radio树");
	tree_xtree_radio_load.put("action", path + "/servlet/xtreeServlet?_actionType=showRadioLoadTree");
	datas.add(tree_xtree_radio_load);

	java.util.Map tree_xtree_composite_load = new java.util.HashMap();
	tree_xtree_composite_load.put("id", "tree_xtree_composite_load");
	tree_xtree_composite_load.put("parentId", "tree_xtree");
	tree_xtree_composite_load.put("name", "动态组合树");
	tree_xtree_composite_load.put("action", path + "/servlet/xtreeServlet?_actionType=showCompositeLoadTree");
	datas.add(tree_xtree_composite_load);

	//-----------------------------------end xtree tree ----------------------
	java.util.Map table = new java.util.HashMap();
	table.put("id", "table");
	table.put("parentId", "root");
	table.put("name", "E3.Table");
	table.put("action", path + "/e3/samples/table/Blank.jsp");
	datas.add(table);

	java.util.Map data1 = new java.util.HashMap();
	data1.put("id", "std");
	data1.put("parentId", "table");
	data1.put("name", "1:标准");
	data1.put("action", path + "/e3/samples/table/Blank.jsp");
	datas.add(data1);

	java.util.Map data2 = new java.util.HashMap();
	data2.put("id", "table_simpleTable");
	data2.put("parentId", "std");
	data2.put("name", "1:简单");
	data2.put("action", path + "/servlet/tableServlet?_actionType=showSimpleTable");
	datas.add(data2);

	java.util.Map data3 = new java.util.HashMap();
	data3.put("id", "table_decorator_simple");
	data3.put("parentId", "std");
	data3.put("name", "2:修饰器");
	data3.put("action", path + "/servlet/tableServlet?_actionType=showDecoratorTable");
	datas.add(data3);

	java.util.Map data4 = new java.util.HashMap();
	data4.put("id", "table_pageTable");
	data4.put("parentId", "std");
	data4.put("name", "3:分页");
	data4.put("action", path + "/servlet/tableServlet?_actionType=showPageTable");
	datas.add(data4);

	java.util.Map data5 = new java.util.HashMap();
	data5.put("id", "table_stateTable");
	data5.put("parentId", "std");
	data5.put("name", "4:记录分页状态");
	data5.put("action", path + "/servlet/tableServlet?_actionType=showStateTable&_e3State=true");
	datas.add(data5);

	java.util.Map data6 = new java.util.HashMap();
	data6.put("id", "table_columnGroup");
	data6.put("parentId", "std");
	data6.put("name", "5:表头分组");
	data6.put("action", path + "/e3/samples/table/JspTable.jsp");
	datas.add(data6);

	java.util.Map data7 = new java.util.HashMap();
	data7.put("id", "table_noData");
	data7.put("parentId", "std");
	data7.put("name", "6:没有记录表");
	data7.put("action", path + "/e3/samples/table/EmptyTable.jsp");
	datas.add(data7);

	java.util.Map data71 = new java.util.HashMap();
	data71.put("id", "table_NoTable");
	data71.put("parentId", "std");
	data71.put("name", "7:显示序号");
	data71.put("action", path + "/e3/samples/table/NoTable.jsp");
	datas.add(data71);

	java.util.Map data72 = new java.util.HashMap();
	data72.put("id", "table_RowStyleTable");
	data72.put("parentId", "std");
	data72.put("name", "8:设置行样式");
	data72.put("action", path + "/e3/samples/table/RowStyleTable.jsp");
	datas.add(data72);

	java.util.Map data73 = new java.util.HashMap();
	data73.put("id", "table_RowEventTable");
	data73.put("parentId", "std");
	data73.put("name", "9:设置行事件");
	data73.put("action", path + "/e3/samples/table/RowEventTable.jsp");
	datas.add(data73);

	java.util.Map data74 = new java.util.HashMap();
	data74.put("id", "table_JdbcPageTable");
	data74.put("parentId", "std");
	data74.put("name", "10:JDBC 分页");
	data74.put("action", path + "/servlet/tableServlet?_actionType=showJdbcPageTable");
	datas.add(data74);

	java.util.Map data75 = new java.util.HashMap();
	data75.put("id", "table_HbnPageTable");
	data75.put("parentId", "std");
	data75.put("name", "11: Hibernate分页");
	data75.put("action", path + "/servlet/tableServlet?_actionType=showHbnPageTable");
	datas.add(data75);

	java.util.Map data76 = new java.util.HashMap();
	data76.put("id", "table_SelectTable");
	data76.put("parentId", "std");
	data76.put("name", "12: 全选/反选");
	data76.put("action", path + "/servlet/tableServlet?_actionType=showSelectAllTable");
	datas.add(data76);

	java.util.Map data77 = new java.util.HashMap();
	data77.put("id", "table_ajaxTable");
	data77.put("parentId", "std");
	data77.put("name", "13: ajax表格");
	data77.put("action", path + "/servlet/tableServlet?_actionType=showAjaxTable");
	datas.add(data77);

	java.util.Map data8 = new java.util.HashMap();
	data8.put("id", "skin");
	data8.put("parentId", "std");
	data8.put("name", "皮肤");
	datas.add(data8);

	java.util.Map data9 = new java.util.HashMap();
	data9.put("id", "skin002");
	data9.put("parentId", "skin");
	data9.put("name", "valuelist皮肤");
	data9.put("action", path + "/servlet/tableServlet?_actionType=showSkinTable&skin=E3001_001");
	datas.add(data9);

	java.util.Map data10 = new java.util.HashMap();
	data10.put("id", "skin003");
	data10.put("parentId", "skin");
	data10.put("name", "dispalytag皮肤");
	data10.put("action", path + "/servlet/tableServlet?_actionType=showSkinTable&skin=E3001_002");
	datas.add(data10);

	java.util.Map data11 = new java.util.HashMap();
	data11.put("id", "skin004");
	data11.put("parentId", "skin");
	data11.put("name", "图片导航条皮肤");
	data11.put("action", path + "/servlet/tableServlet?_actionType=showSkinTable&skin=E3001_003");
	datas.add(data11);

	java.util.Map data12 = new java.util.HashMap();
	data12.put("id", "ext");
	data12.put("parentId", "table");
	data12.put("name", "2:ext");
	data12.put("action", path + "/e3/samples/table/Blank.jsp");
	datas.add(data12);

	java.util.Map data13 = new java.util.HashMap();
	data13.put("id", "extsimple");
	data13.put("parentId", "ext");
	data13.put("name", "简单表格");
	data13.put("action", path + "/servlet/tableServlet?_actionType=showExtTable");
	datas.add(data13);

	java.util.Map data14 = new java.util.HashMap();
	data14.put("id", "ext14");
	data14.put("parentId", "ext");
	data14.put("name", "lazy load表格");
	data14.put("action", path + "/e3/samples/table/ExtTable.jsp");
	datas.add(data14);

	java.util.Map multiTable = new java.util.HashMap();
	multiTable.put("id", "multiTable");
	multiTable.put("parentId", "ext");
	multiTable.put("name", "多表");
	multiTable.put("action", path + "/e3/samples/table/MultiTable.jsp");
	datas.add(multiTable);

	java.util.Map data15 = new java.util.HashMap();
	data15.put("id", "ext15");
	data15.put("parentId", "ext");
	data15.put("name", "右键菜单");
	data15.put("action", path + "/e3/samples/table/ExtRightMenuTable.jsp");
	datas.add(data15);

	java.util.Map data16 = new java.util.HashMap();
	data16.put("id", "ext16");
	data16.put("parentId", "ext");
	data16.put("name", "查询数据");
	data16.put("action", path + "/e3/samples/table/ExtQueryTable.jsp");
	datas.add(data16);

	java.util.Map data17 = new java.util.HashMap();
	data17.put("id", "ext17");
	data17.put("parentId", "ext");
	data17.put("name", "导出excel");
	data17.put("action", path + "/servlet/tableServlet?_actionType=showExportTable");
	datas.add(data17);

	java.util.Map resource = new java.util.HashMap();
	resource.put("id", "resource");
	resource.put("parentId", "root");
	resource.put("name", "E3.Resource");
	resource.put("action", path + "/e3/samples/table/Blank.jsp");
	datas.add(resource);

	java.util.Map id = new java.util.HashMap();
	id.put("id", "id");
	id.put("parentId", "root");
	id.put("name", "E3.ID");
	id.put("action", path + "/e3/samples/table/Blank.jsp");
	datas.add(id);

	java.util.Map calendar = new java.util.HashMap();
	calendar.put("id", "calendar");
	calendar.put("parentId", "root");
	calendar.put("name", "E3.Calendar");
	calendar.put("action", path + "/e3/samples/table/Blank.jsp");
	datas.add(calendar);

	request.setAttribute("datas", datas);
%>
<e3:tree createDiv="true" var="data" items="datas" builder="OutlookExtTree" defaultSort="false">
  <e3:node id="B${data.id}" parentId="B${data.parentId}" name="${data.name}"
    action="javascript:openURL('${data.action}')" />
</e3:tree>
</BODY>
</HTML>
