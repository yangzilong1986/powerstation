
//数组比较
Array.prototype.contains = function(obj) {   
    var boo = false;   
    for(var i=0;i<this.length;i++) {   
        if(typeof obj == "object") {   
            if(this[i].equals(obj)) {   
                boo = true;   
                break;   
            }                          
        }else {   
            if(this[i] == obj)  {   
                   boo = true;   
                   break;                  
            }   
        }              
    }   
    return boo;   
}

function filter(a1, a2) {   
    var myAry = [];   
    var fun = function(item,ary) {   
        var boo = false;   
        for(var i=0;i<ary.length;i++) {   
            if(item==ary[i]) {   
                  boo = true;   
                  break;   
            }   
        }   
        return boo;                                
    }      
    for(var i=0;i<a1.length;i++) {   
        if(!a2.contains(a1[i]))   
        myAry.push(a1[i]);                     
    }              
    return myAry;   
}   

/**
 * 得到已选择队列
 * type:0-不加引号，1-加引号

 */
function getSelectedIdString(type) {
    var sResult = "";
    try {
        if(type == 1) {
            if(typeof document.all.ItemID != "undefined" && typeof document.all.ItemID.length == "undefined") {
                 if(document.all.ItemID.checked) {
                     sResult += ",'" + document.all.ItemID.value + "'";
                 }
            }
            else if(typeof document.all.ItemID != "undefined" && typeof document.all.ItemID.length != "undefined") {
                for(var i = 0; i < document.all.ItemID.length; i++) {
                    if(document.all.ItemID[i].checked) {
                        sResult += ",'" + document.all.ItemID[i].value + "'";
                    }
                }
            }
        }
        else {
            if(typeof document.all.ItemID != "undefined" && typeof document.all.ItemID.length == "undefined") {
                 if(document.all.ItemID.checked) {
                     sResult += "," + document.all.ItemID.value;
                 }
            }
            else if(typeof document.all.ItemID != "undefined" && typeof document.all.ItemID.length != "undefined") {
                for(var i = 0; i < document.all.ItemID.length; i++) {
                    if(document.all.ItemID[i].checked) {
                        sResult += "," + document.all.ItemID[i].value;
                    }
                }
            }
        }
        if(sResult.indexOf(",") != -1) {
            sResult = sResult.substring(1);
        }
    }
    catch(e) {
    }

    return sResult;
}

/**
 * 全选checkbox
 */
function selectAllCB() {
    try {
        if(typeof document.all.ItemID != "undefined" && typeof document.all.ItemID.length == "undefined") {
            if(document.all.selectAll.checked) {
                if(!document.all.ItemID.checked) {
                    document.all.ItemID.click();
                }
            }
            else {
                if(document.all.ItemID.checked) {
                    document.all.ItemID.click();
                }
            }
        }
        else if(typeof document.all.ItemID != "undefined" && typeof document.all.ItemID.length != "undefined") {
            if(document.all.selectAll.checked) {
                for(var i = 0; i < document.all.ItemID.length; i++) {
                    if(!document.all.ItemID[i].checked) {
                        document.all.ItemID[i].click();
                    }
                }
            }
            else {
                for(var i = 0; i < document.all.ItemID.length; i++) {
                    if(document.all.ItemID[i].checked) {
                        document.all.ItemID[i].click();
                    }
                }
            }
        }
    }
    catch(e) {
    }
}

function setTableStyle(){
    /**
     * even/odd rows
     */
    setRows($(".tableContainer table>tbody>tr:odd"));
    
    /**
     * mouseover/mouseout event
     */
    $(".tableContainer table>tbody>tr").each( function() {
        $(this).mouseover( function() {
            $(this).addClass("mouseover");
        });
        $(this).mouseout( function() {
            $(this).removeClass("mouseover");
        });
    });
}

function selectAllBox(select,id) {
    try {
        var ItemID= document.getElementsByName(id);
        if(typeof ItemID != "undefined" && typeof ItemID.length == "undefined") {
            if(select.checked) {
                if(!ItemID.checked) {
                    ItemID.click();
                }
            }
            else {
                if(ItemID.checked) {
                   ItemID.click();
                }
            }
        }
        else if(typeof ItemID != "undefined" && typeof ItemID.length != "undefined") {
            if(select.checked) {
                for(var i = 0; i < ItemID.length; i++) {
                    if(!ItemID[i].checked) {
                        ItemID[i].click();
                    }
                }
            }
            else {
                for(var i = 0; i < ItemID.length; i++) {
                    if(ItemID[i].checked) {
                       ItemID[i].click();
                    }
                }
            }
        }
    }
    catch(e) {
    }
}