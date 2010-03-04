// Avoid collisions
;if(window.jQuery) (function($){
 // Add function to jQuery namespace
 $.extend({
	 // converts xml documents and xml text to json object
	 json2html: function(id,xml,checkNm,extended){
	
	   if(!xml) return {}; // quick fail
	   if(id==undefined)return {}
	   if ($("#"+id).html()!=null || $("#"+id).html()!=""){
		   $("#"+id).html(null);
	   }
	   if(xml.charAt(0)=='/'){
		   xml = xml.substring(1,xml.length);
	   }
	   	$.get(xml, function(xmlCon){
	   	   var json = $.xml2json(xmlCon);
	   	   var bolrowid=false;
	   	   var rowid=json.rowid;
	   	   if(rowid =='true'){
	   		bolrowid=true;
	   		delete json.rowid;
	   	   }
	   	    var dataNm=0;
	   	    if(json.data!=undefined){
	   	    $.each(json.data, function(m,it){
	   	    	if(typeof m =='string'){
	   	    		dataNm=1;
	   	    	}else{
	   	    	dataNm++;
	   	    	}
	   	    });
	   	    }else{
	   	    	dataNm==0;
	   	    }
	   	    if(dataNm==0){
	   	    }
	   	    else if(dataNm==1){
		   		// set tr
			       var tr="";
			       var no = 0;
			       var num=true;
			       var ex="";
			       if(json.data.extend !=undefined){
			    	   ex = json.data.extend;
			    	   delete json.data.extend;
			       }
			    tr += "<tr "+ex+" id=\""+id+"_table_tr_"+no+"\">";
	   	    	$.each(json.data, function(i,item){
		   			   no = 1;
		   			if(i=="check"){
			    	   	   var ex="";
			    		   if(item.extend != undefined){
			    		   	   ex=item.extend;
			    		   }
			    			tr += "<td align='center'><input type=checkbox name="+checkNm+" "+ex+" value="+item+"></td>"; 
			    		   if(num && bolrowid){
			    			   tr += "<td align='center'>"+no+"</td>";
			    			   num=false;
			    		   }
			    	      }
			    	   else{
			    	   	   var ex="";
			    		    if(item.extend != undefined){
			    		   	   ex=item.extend;
			    		   }
			    		    if(num &&bolrowid){
				    			   tr += "<td align='center'>"+no+"</td>";
				    			   num=false;
				    		   }
			    			   tr += "<td "+ex+">"+item+"</td>";
			    	   }
	   	    	});
	   	     tr += "</tr>";
	   	     
	   	    /***************************************************/
		   	  if(json.total != undefined){
			   		var ex=json.total.extend ? json.total.extend:"";
					   	 tr += "<tr "+ex+" id=\""+id+"_table_tr_total\">";
					   	 var first=true;
				   	    	$.each(json.total, function(i,item){
					   			if(i=="check"){
						    	   	   var ex="";
						    		   if(item.extend != undefined){
						    		   	   ex=item.extend;
						    		   }
						    			tr += "<td align='center'><input type=checkbox name="+checkNm+" "+ex+" value="+item+"></td>"; 
						    			if(first){
						    				 tr += "<td "+ex+">合计</td>";
						    				 first=false;
						    			}
						    	      }
						    	   else{
						    	   	   var ex="";
						    		    if(item.extend != undefined){
						    		   	   ex=item.extend;
						    		   }
						    		    if(first){
						    				 tr += "<td "+ex+">合计</td>";
						    				 first=false;
						    		    }
						    			   tr += "<td "+ex+">"+item+"</td>";
						    	   }
				   	    	});
				   	    	
				   	     tr += "</tr>";
				   }
				   
	   	 /***************************************************/
		     $("#"+id).append($(tr));
	   	    }else{
	   	     var tr="";
	   	   $.each(json.data, function(i,item){
	   		// set tr
		       var no =i+1;;
		       var num=true;
		       var ex="";
		       if(json.data[i].extend !=undefined){
		    	   ex=json.data[i].extend;
		       }
		       tr += "<tr "+ex+" id=\""+id+"_table_tr_"+no+"\">";
		       var num=true;
		      	delete json.data[i].extend;
		       $.each(item, function(key, value){
		    	   if(key=="check"){
		    	   	   var ex="";
		    		   if(item[key].extend!=undefined){
		    		   	   ex=item[key].extend;
		    		   }
		    			tr += "<td align='center'><input type=checkbox name="+checkNm+" "+ex+" value="+value+"></td>"; 
		    		   
		    		   if(num && bolrowid){
		    			   tr += "<td align='center'>"+no+"</td>";
		    		   }
		    	      }
		    	   else{
		    	   	   var ex="";
		    		    if(item[key].extend!=undefined){
		    		   	   ex=item[key].extend;
		    		   }
		    		   if(num&&bolrowid){
		    			   tr += "<td align='center'>"+no+"</td>";
		    		   }
		    			   tr += "<td "+ex+">"+value+"</td>";
		    	   }
		    	   num=false;
	       });
		       tr += "</tr>";
	   	    });
	   	     /***************************************************/
	   	     
	   	  if(json.total != undefined){
	   		var ex=json.total.extend ? json.total.extend:"";
			   	 tr += "<tr "+ex+" id=\""+id+"_table_tr_total\">";
			   	 var first=true;
		   	    	$.each(json.total, function(i,item){
			   			if(i=="check"){
				    	   	   var ex="";
				    		   if(item.extend != undefined){
				    		   	   ex=item.extend;
				    		   }
				    			tr += "<td align='center'><input type=checkbox name="+checkNm+" "+ex+" value="+item+"></td>"; 
				    			if(first){
				    				 tr += "<td "+ex+">合计</td>";
				    				 first=false;
				    			}
				    	      }
				    	   else{
				    	   	   var ex="";
				    		    if(item.extend != undefined){
				    		   	   ex=item.extend;
				    		   }
				    		    if(first){
				    				 tr += "<td "+ex+">合计</td>";
				    				 first=false;
				    		    }
				    			   tr += "<td "+ex+">"+item+"</td>";
				    	   }
		   	    	});
		   	    	
		   	     tr += "</tr>";
		   }
		   
	   	 /***************************************************/
	   	$("#"+id).append($(tr)); 
	   	}
	   	    
	   	   var pathNm=0;
	   	   var path="";
	   	   if(xml.indexOf("../")>-1){
	   		pathNm = xml.split("../").length-1;
	   		for(i=0;i<pathNm;i++){
	   			path += "../";
	   		}
	   	   }
	   	   if(extended!=undefined){
	   		   path = extended;
	   		  $("#"+id).append($("<script type=\"text/javascript\" src=\""+path+"js/lineloss/lineloss.js\"></script>"))
	   	   }
	   	$("#"+id).append($("<script type=\"text/javascript\" src=\""+path+"js/frame/tableEx.js\"></script>"))
	   	   	})
 }
}); // extend $

})(jQuery);