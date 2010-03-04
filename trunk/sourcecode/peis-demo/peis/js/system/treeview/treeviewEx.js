//*****************************************************************************
//
//*****************************************************************************





//*****************************************************************************
/**
 *
 */
function createFolderCheckBoxTrail(parentFolder, folderLabel, checkBoxDOMID, value, checked) {
    var newFolder = insFld(parentFolder, setFolderEx(1, folderLabel, null));
    var sTrailHTML = "<hr noshade style='border:1 dashed  red' size='1'><td align=right valign=middle class=paddingRight5><input id=" + checkBoxDOMID + " type=checkbox value=\"" + value + "\" onclick=\"selectCheckBox('" + checkBoxDOMID + "')\"";
    if(checked) {
        sTrailHTML += " checked";
    }
    sTrailHTML += "></td>";
    newFolder.trailHTML = sTrailHTML;
    return newFolder;
}

/**
 *
 */
function createItemCheckBoxTrail(parentFolder, itemLabel, checkBoxDOMID, value, checked, image) {
    var newItem = insDoc(parentFolder, setItemEx(1, itemLabel, null, image));
    var sTrailHTML = "<hr noshade style='border:1 dashed  red' size='1'><td align=right valign=middle class=paddingRight5><input id=" + checkBoxDOMID + " type=checkbox value=\"" + value + "\" onclick=\"selectCheckBox('" + checkBoxDOMID + "')\"";
    if(checked) {
        sTrailHTML += " checked";
    }
    sTrailHTML += "></td>";
    newItem.trailHTML = sTrailHTML;
    return newItem;
}

///////////////////////////////////////
/**
 *
 */
function getParentId(id) {
    var iLastPlace = id.lastIndexOf("_");
    var parentId = "";
    if(iLastPlace != -1) {
        parentId = id.substring(0, iLastPlace);
    }
    else {
        parentId = "ALL";
    }
    return parentId;
}

/**
 *
 */
function getItemLevel(id) {
    var iLevel = 0;
    if(id == "ALL") {
        iLevel = 0;
    }
    else if(id != null && id.length > 3 && id.substring(0, 3) == "BOX") {
        var temp = id;
        var iPlace = temp.indexOf("_");
        while(iPlace != -1) {
            iLevel++;
            temp = temp.substring(iPlace + 1);
            iPlace = temp.indexOf("_");
        }

        iLevel++;
    }
    else {
        iLevel = -1;
    }

    return iLevel;
}
