// JavaScript Document

$(document).ready( function() {
    /**
     * even/odd rows
     */
    setRows($(".tableContainer table>tbody>tr:odd"));
    setRows($(".graphContainer table>tbody>tr:odd"));
    setRows($(".tblcontent table>tbody>tr:odd"));
    
    /**
     * 
     */
    $(".tableContainer table>tbody>tr").each( function() {
        $(this).mouseover( function() {
            $(this).addClass("mouseover");
        });
        $(this).mouseout( function() {
            $(this).removeClass("mouseover");
        });
    });
    
    /**
     * 
     */
    $(".graphContainer table>tbody>tr").each( function() {
        $(this).mouseover( function() {
            $(this).addClass("mouseover");
        });
        $(this).mouseout( function() {
            $(this).removeClass("mouseover");
        });
    });
    
    /**
     * 
     */
    $(".tblcontent table>tbody>tr").each( function() {
        $(this).mouseover( function() {
            $(this).addClass("mouseover");
        });
        $(this).mouseout( function() {
            $(this).removeClass("mouseover");
        });
    });
});

/**
 * 
 * @param {Object} row
 */
function setRows(row) {
    row.addClass("odd");
}