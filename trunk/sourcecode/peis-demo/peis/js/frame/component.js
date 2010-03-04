// JavaScript Document

/**
 * 
 */
var pre_row = null;
function selectSingleRow(row) {
    $(row).addClass("selected");
    if(pre_row != null && pre_row != row) {
        $(pre_row).removeClass("selected");
    }
    pre_row = row;
}

/**
 * 
 */
function getSelectedCheckboxs() {
    var selected_checkboxs = "";
    $("input[type=checkbox][name='cb-item']").each( function() {
        if($(this).attr("checked")) {
            selected_checkboxs += "," + $(this).val();
        }
    });
    return ($.trim(selected_checkboxs).length > 0 ? selected_checkboxs.substring(1) : "");
}

/**
 * 
 */
function getSelectedCheckboxsByName(name) {
    var selected_checkboxs = "";
    $("input[type=checkbox][name='" + name + "']").each( function() {
        if($(this).attr("checked")) {
            selected_checkboxs += "," + $(this).val();
        }
    });
    return ($.trim(selected_checkboxs).length > 0 ? selected_checkboxs.substring(1) : "");
}

/**
 * 显示对话框
 */
function showDialogBox(caption, url, height, width) {
    GB_show(caption, url, height, width);
}

/**
 * greybox
 */
var GB_DONE = false;
var GB_ANIMATION = false;
var GB_HEIGHT = 600;
var GB_WIDTH = 800;
function GB_show(caption, url, height, width) {
    GB_HEIGHT = height || 600;
    GB_WIDTH = width || 800;
    if(!GB_DONE) {
        $(document.body).append("<div id='GB_overlay'><iframe></iframe></div><div id='GB_window'><div id='GB_caption'></div><div id='GB_close'></div></div>");
        $("#GB_close").click(GB_hide);
        //$("#GB_overlay").click(GB_hide);
        $(window).resize(GB_position);
        GB_DONE = true;
    }

    $("#GB_frame").remove();
    $("#GB_window").append("<iframe id='GB_frame' frameborder='0' scrolling='auto' src='" + url + "'></iframe>");
    $("#GB_caption").html(caption);
    $("#GB_overlay").show();
    GB_position();
    try {
        if(GB_ANIMATION) {
            $("#GB_window").slideDown("slow");
        }
        else {
            $("#GB_window").show();
        }
    }
    catch(e) {
        $("#GB_window").show();
    }
}

function GB_hide() {
    $("#GB_window, #GB_overlay").hide();
}

function GB_position() {
    var de = document.documentElement;
    var w = self.innerWidth || (de && de.clientWidth) || document.body.clientWidth;
    $("#GB_window").css({width: GB_WIDTH + "px", height: GB_HEIGHT + "px", left: ((w - GB_WIDTH) / 2) + "px" });
    $("#GB_frame").css("height", GB_HEIGHT - 22 + "px");
}

$.extend({
    includePath: '',
    include: function(file) {
        var files = typeof file == "string" ? [file]:file;
        for (var i = 0; i < files.length; i++) {
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";
            if ($(tag + "[" + link + "]").length == 0) document.write("<" + tag + attr + link + "></" + tag + ">");
        }
    }
});

$.includePath = '../../js/frame/';
$.include(['WdatePicker.js']);

function peisDatePicker() {
    WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd'});
}

function peisTimePicker() {
    WdatePicker({doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss'});
}

function peisPicker(dataFmt) {
    WdatePicker({doubleCalendar:false,dateFmt:dataFmt});
}