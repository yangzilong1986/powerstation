// Avoid collisions
;if(window.jQuery) (function($){
 // Add function to jQuery namespace
 $.extend({
	 // converts xml documents and xml text to json object
	 json2html: function(id,xml,checkNm,extended){
	   if(!xml) return {}; // quick fail
	   if(id==undefined)return {}
	   if ($("#"+id).html()!=null && $("#"+id).html()!=""){
		   $("#"+id).html(null);
	   }
	   	$.get(xml, function(xmlCon){
	   	   var json = $.xml2json(xmlCon);
	   	   var bolrowid=false;
	   	   var rowid=json.rowid;
	   	   if(rowid =='true'){
	   		bolrowid=true;
	   	   }
	   	   $.each(json.data, function(i,item){
	   		   if(typeof i =='string'){
	   			   var tr="";
	   			   var no=1;
	   			   var style="";
	   		   }else{
	       // set tr
	       var tr="";
	       var no =i+1;
	       var style="";
	       if(json.data[i].js !=undefined && json.data[i].style != undefined){
	    	   style="style='cursor:pointer;"+json.data[i].style+"'";
	    	   tr += "<tr "+style+" id=\""+id+"_table_tr_"+no+"\" "+json.data[i].js+">";
	       }else if(json.data[i].js ==undefined && json.data[i].style==undefined){
	    	   tr += "<tr id=\""+id+"_table_tr_"+no+"\">";
	       }else {
	    	   if(json.data[i].js !=undefined){
	    		   style="style='cursor:pointer;'";
	    	   }else{
	    		   style="style='"+json.data[i].style+"'";
	    	   }
	    	   tr += "<tr "+style+" id=\""+id+"_table_tr_"+no+"\" "+json.data[i].js+">";
	       }
	       var num=true;
	       $.each(item, function(key, value){
	    	   if(key=="check"){
	    		   var check=item[key].checked;
	    		   var disable=item[key].disabled;
	    		    if($._check(check) && $._disable(disable)){
	    			   
	    			   tr += "<td align='center'><input type=checkbox id="+checkNm+"_"+no+" name=\""+checkNm+"\" checked disabled value="+value+"></td>"; 
	    		   }else if(!$._check(check) && !$._disable(disable)){
	    			   tr += "<td align='center'><input type=checkbox id="+checkNm+"_"+no+" name=\""+checkNm+"\" value="+value+"></td>"; 
	    		   }
	    		   else{
	    			   if($._check(check)){
	    				   tr += "<td align='center'><input type=checkbox id="+checkNm+"_"+no+" name=\""+checkNm+"\" checked  value="+value+"></td>"; 
	    			   }else{
	    				   tr += "<td align='center'><input type=checkbox id="+checkNm+"_"+no+" name=\""+checkNm+"\" disabled  value="+value+"></td>"; 
	    			   }
	    		   }
	    		   
	    		   if(num&&bolrowid){
	    			   tr += "<td align='center'>"+no+"</td>";
	    		   }
	    		   
	    	      }else if(key=='js' || key=='style'){
	    	    	  
	    	      }
	    	   else{
	    		   var style="";
	    		   var js="";
	    		   if(item[key].style != undefined){
	    			   style +="style=\""+item[key].style+"\"";
	    		   }
	    		   if(item[key].js != undefined){
	    			   js+=item[key].js;
	    		   }
	    		   if(num&&bolrowid){
	    			   tr += "<td "+style+">"+no+"</td>";
	    		   }
	    			   tr += "<td "+style+" "+js+">"+value+"</td>";
	    	   }
	    	   num=false;
	       });
	       
	       tr += "</tr>";
	       $("#"+id).append($(tr));
	   	   } 
		  });
	   	   var pathNm=0;
	   	   var path="";
	   	   if(xml.indexOf("../")>-1){
	   		pathNm = xml.split("../").length-1;
	   		for(i=0;i<pathNm;i++){
	   			path += "../";
	   		}
	   	   }
	   	  
	   	$("#"+id).append($("<script type=\"text/javascript\" src=\""+path+"js/frame/tableEx.js\"></script>"))
	   	})
 },
	  
 
 // Convert text to XML DOM
 _check: function(str) {
	 var const_checked="true,checked";
   	 var const_unchecked="false,unchecked";
   	 var bolchk=false;
  if(const_checked.indexOf(str)!=-1){
	  bolchk=true;
  }else if(const_unchecked.indexOf(str)!=-1){
	  bolchk=false;
  }
  return bolchk;
 },
 _disable:function(str){
   	 var const_disabled="true,disabled";
   	 var const_undisabled="false,undisabled";
	 var boldisable=false;
	  if(const_disabled.indexOf(str)!=-1){
		  boldisable=true;
	  }else if(const_undisabled.indexOf(str)!=-1){
		  boldisable=false;
	  }
	  return boldisable;
	 
 },
 _js:function(str) {
	 },
	 _style:function(str){
		 
	 }
}); // extend $

})(jQuery);