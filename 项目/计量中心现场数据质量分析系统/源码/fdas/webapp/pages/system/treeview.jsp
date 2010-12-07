<%@ page contentType="text/html; charset=UTF-8" %>




<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>treeview</title>
<link href="<peis:contextPath/>/style/treeviewStyle.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/treeview/ua.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/treeview/ftiens4.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/treeview/treeviewEx.js"></script>
<script type="text/javascript" language="javascript">
function selectCheckBox(id) {
    if(document.getElementById(id).checked == true) {
        if(id == "ALL") {
            for(var i = 0; i < checkboxCount; i++) {
                if(document.getElementById(checkboxIDArray[i]).checked == false) {
                    document.getElementById(checkboxIDArray[i]).checked = true;
                }
            }
        }
        else if(id != null && id.length > 3 && id.substring(0, 3) == "BOX") {
            var parentId = getParentId(id);
            var idLength = id.length;

            //判断根结点
            if(document.getElementById("ALL").checked == false) {
                document.getElementById("ALL").checked = true;
            }

            //判断上级结点
            if(parentId != "ALL") {
                if(document.getElementById(parentId).checked == false) {
                    document.getElementById(parentId).checked = true;
                }
                while(getParentId(parentId) != "ALL") {
                    parentId = getParentId(parentId);
                    if(document.getElementById(parentId).checked == false) {
                        document.getElementById(parentId).checked = true;
                    }
                }
            }

            for(var i = 0; i < checkboxCount; i++) {
                //判断下级结点
                if(checkboxIDArray[i].length > idLength + 1 && checkboxIDArray[i].indexOf(id + "_") != -1) {
                    if(document.getElementById(checkboxIDArray[i]).checked == false) {
                        document.getElementById(checkboxIDArray[i]).checked = true;
                    }
                }
            }
        }
    }
    else {
        if(id == "ALL") {
            for(var i = 0; i < checkboxCount; i++) {
                if(document.getElementById(checkboxIDArray[i]).checked == true) {
                    document.getElementById(checkboxIDArray[i]).checked = false;
                }
            }
        }
        else if(id != null && id.length > 3 && id.substring(0, 3) == "BOX") {
            var parentId = getParentId(id);
            var idLength = id.length;
            var idLevel = getItemLevel(id);

            var allFalseFlag = true;   // true: 全为false ; false: 不全为false
            for(var i = 0; i < checkboxCount; i++) {
                //判断同级结点是否全为false (allFalseFlag == true)
                if(allFalseFlag == true
                    && getItemLevel(checkboxIDArray[i]) == idLevel
                    && getParentId(checkboxIDArray[i]) == parentId
                    && document.getElementById(checkboxIDArray[i]).checked == true) {
                    allFalseFlag = false;
                }

                //判断下级结点
                if(checkboxIDArray[i].length > idLength + 1 && checkboxIDArray[i].indexOf(id + "_") != -1) {
                    if(document.getElementById(checkboxIDArray[i]).checked == true) {
                        document.getElementById(checkboxIDArray[i]).checked = false;
                    }
                }
            }

            if(allFalseFlag == true) {
                if(document.getElementById(parentId).checked == true) {
                    document.getElementById(parentId).checked = false;
                    selectCheckBox(parentId);
                }
            }
        }
    }
}
</script>
</head>

<body>
<peis:jsTreeview type="1" treeID="roleManage"/>

<script type="text/javascript" language="javascript">
var bTreeviewLoaded = true;
</script>
</body>

</html>
