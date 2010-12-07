/*******************************************************************************
 * Copyright (c) 2010 PSS Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PSS Corporation - initial API and implementation
 *******************************************************************************/
/*
 *	Sub class that adds a check box in front of the tree item icon
 *
 *	Created by Erik Arvidsson (http://webfx.eae.net/contact.html#erik)
 *
 *	Disclaimer:	This is not any official WebFX component. It was created due to
 *				demand and is just a quick and dirty implementation. If you are
 *				interested in this functionality the contact us
 *				http://webfx.eae.net/contact.html
 *
 *	Notice that you'll need to add a css rule the sets the size of the input box.
 *	Something like this will do fairly good in both Moz and IE
 *	
 *	input.tree-check-box {
 *		width:		auto;
 *		margin:		0;
 *		padding:	0;
 *		height:		14px;
 *		vertical-align:	middle;
 *	}
 *
 */

 var disableColor = webFXTreeConfig.disableColor;

var checkboxTreeHandler = {
  onChecked : null, /*选种一个节点*/
  onUnchecked : null /*取消一个节点*/
};

function WebFXCheckBoxTreeItem(sText, sValue, sAction, eParent, sIcon, sOpenIcon, bChecked, disabled) {
	this.base = WebFXTreeItem;
	this.base(sText, sAction, eParent, sIcon, sOpenIcon);	
	this._checked = bChecked;	
	this._disabled = false;
	if(disabled) this._disabled = disabled;

    // luohc 2004-7-30.
	this.value = sValue==null?"":sValue;	
	if(bChecked != null && bChecked == true){
		var checkedObject = new CheckedObject(this.id, sText, sValue)
		checkedValues.put(this.id, checkedObject );
		if (checkboxTreeHandler.onChecked)
		{
          checkboxTreeHandler.onChecked(this);
		}
	}
}

WebFXCheckBoxTreeItem.prototype = new WebFXTreeItem;

WebFXCheckBoxTreeItem.prototype.toString = function (nItem, nItemCount) {
	var foo = this.parentNode;
	var indent = '';
	if (nItem + 1 == nItemCount) { this.parentNode._last = true; }
	var i = 0;
	while (foo.parentNode) {
		foo = foo.parentNode;
		indent = "<img id=\"" + this.id + "-indent-" + i + "\" src=\"" + ((foo._last)?webFXTreeConfig.blankIcon:webFXTreeConfig.iIcon) + "\">" + indent;
		i++;
	}
	this._level = i;
	if (this.childNodes.length) { this.folder = 1; }
	else { this.open = false; }
	if ((this.folder) || (webFXTreeHandler.behavior != 'classic')) {
		if (!this.icon) { this.icon = webFXTreeConfig.folderIcon; }
		if (!this.openIcon) { this.openIcon = webFXTreeConfig.openFolderIcon; }
	}
	else if (!this.icon) { this.icon = webFXTreeConfig.fileIcon; }
	var label = this.text.replace(/</g, '&lt;').replace(/>/g, '&gt;');

	var str = "<div id=\"" + this.id + "\" ondblclick=\"webFXTreeHandler.toggle(this);\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\">";
	str += indent;
	str += "<img id=\"" + this.id + "-plus\" src=\"" + ((this.folder)?((this.open)?((this.parentNode._last)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.tMinusIcon):((this.parentNode._last)?webFXTreeConfig.lPlusIcon:webFXTreeConfig.tPlusIcon)):((this.parentNode._last)?webFXTreeConfig.lIcon:webFXTreeConfig.tIcon)) + "\" onclick=\"webFXTreeHandler.toggle(this);\">"
	
	// insert check box
	var tempStr = "<input type=\"checkbox\"" + " class=\"tree-check-box\"" +
		(this._checked ? " checked=\"checked\"" : "") +
		" onclick=\"webFXTreeHandler.all[this.parentNode.id].setChecked(this.checked)\"" +
		" value=\"" + this.value + "\" id=\"" + this.id + "-input\" name=\"" + this.value 
		+ "-input\" " + (this._disabled?" disabled ": "") + ">";
	str += tempStr;	
	// end insert checkbox
	
	//str += "<img id=\"" + this.id + "-icon\" class=\"webfx-tree-icon\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"webFXTreeHandler.select(this);\"><a href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onfocus=\"webFXTreeHandler.focus(this);\" onblur=\"webFXTreeHandler.blur(this);\">" + label + "</a></div>";
	//黄云辉修改，把span修改成
	tempStr = "<img id=\"" + this.id + "-icon\" class=\"webfx-tree-icon\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"webFXTreeHandler.select(this);\"><a  href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onfocus=\"webFXTreeHandler.focus(this);\" onblur=\"webFXTreeHandler.blur(this);\"  >" + label + "</a></div>";
	str += tempStr;

	str += "<div id=\"" + this.id + "-cont\" class=\"webfx-tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	for (var i = 0; i < this.childNodes.length; i++) {
		str += this.childNodes[i].toString(i,this.childNodes.length);
	}
	str += "</div>";
	this.plusIcon = ((this.parentNode._last)?webFXTreeConfig.lPlusIcon:webFXTreeConfig.tPlusIcon);
	this.minusIcon = ((this.parentNode._last)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.tMinusIcon);
	return str;
}

WebFXCheckBoxTreeItem.prototype.getChecked = function () {
	//var divEl = document.getElementById(this.id);
	//var inputEl = divEl.getElementsByTagName("INPUT")[0];
	var inputEl = getCheckBox(this.id);
	return this._checked = inputEl.checked;
};

WebFXCheckBoxTreeItem.prototype.setChecked = function (bChecked) {
    // 这个方法被罗洪臣修改 2004-7-30.
	/*
	if (bChecked != this.getChecked()) {
		var divEl = document.getElementById(this.id);
		var inputEl = divEl.getElementsByTagName("INPUT")[0];
		this._checked = inputEl.checked = bChecked;
		
		if (typeof this.onchange == "function")
			this.onchange();
	}*/
   getCheckBox(this.id).checked = bChecked;    
	this._checked = bChecked;	
	doCheck(this, bChecked);
	if (typeof this.onchange == "function"){
		this.onchange(bChecked);
	}	
};

/*****   以下是递归选择CheckBox的方法   罗洪臣   2004-7-30   *******/
//使用Map对象记录当前选项棒定的checkbox的值，注意：只有选种了节点才会存在
//checkedValues中.因此，只要获取checkedValues中的值，就可以找出当前处于选种状态
//的节点项
// Map 是个自定义的类， 相当与java里面的HashMap
var checkedValues = new Map();

//用来记录已选种节点
function CheckedObject(id, text, value){
	this.id = id;
	this.text = text;
	this.value = value;
}

//toString方法，同java中的toString方法.
CheckedObject.prototype.toString = function(){
	var str = "\nid = " + this.id + "\ntext = " + this.text + "\nvalue = " + this.value + "\n";
	return str;
}


//设置某个节点的选种状态.
// true为选种，false为未选种.理论上这个方法应该是WebFXTree的，而不是全局方法.
function setCheckedValues(node, bChecked){
    //如果选种，则加入.如果未选种，则删除.
	if(bChecked){		
		checkedValues.put(node.id, new CheckedObject(node.id, node.text, node.value) );		
		if (checkboxTreeHandler.onChecked)
		{
          checkboxTreeHandler.onChecked(node);
		}

	}else{		
		checkedValues.remove(node.id);
		if (checkboxTreeHandler.onUnchecked)
		{
          checkboxTreeHandler.onUnchecked(node);
		}

	}
}

//根据节点id信息，获取checkbox对象.
function getCheckBox(id){
	return document.getElementById(id + "-input");
}
//执行check
function doCheck(item, bChecked){ 
	//设置check.
	setCheckedValues(item, bChecked);	
	// 是否级联选择.
	//如果是级联选择。则要求儿子也执行check.
	if(webFXTreeConfig.cascadeCheck){
		checkChildren(item, bChecked);
		//如果是取消，则取消父亲选择状态，否则设置父亲为选择状态.
		if(!bChecked){		
			 unCheckParents(item);
		}else{
			checkParents(item);
		}
	}
}

// 设置父节点为选中状态.如果子节点都被选中的话。
function checkParents(item){
	var pnode = item.parentNode;
	if(pnode instanceof WebFXCheckBoxTreeItem){
		//检查所有儿子是否选种，如果存在未选种儿子，则退出方法
		//否则递归选择肤浅节点.
		/*
		for(var i=0; i<pnode.childNodes.length; i++){
			 var node = pnode.childNodes[i];
			 var cbx = getCheckBox(node.id);
			 if(!cbx.checked) {				 				 
				 pnode.getCheckBox().checked = false;
				 setCheckedValues(pnode, false);
				 return;
			 }
		 } 
		 */		 		 
		 getCheckBox(pnode.id).checked = true;
		 setCheckedValues(pnode, true);
		 checkParents(pnode);
	}   
}

// 子节点未选中时，其父节点置为未选中状态.
//TODO:这样似乎有点不合适，应该是，当所有兄弟节点都未选种的情况下
//才设置父亲节点处于未选种状态.
function unCheckParents(item){
	var pNode = item.parentNode;
	if (pNode instanceof WebFXCheckBoxTreeItem == false )
	{
		return;
	}
	var isCheckParent = false;
	var childNodes = pNode.childNodes;
	for(var i=0; i<childNodes.length; i++){
	  var child = childNodes[i];
	  if ( child instanceof WebFXCheckBoxTreeItem == false){
	    continue;
	  }
	  var cbx = getCheckBox(child.id);
	  if(cbx.checked){
	    isCheckParent = true;
	    break;
	  }
	}//
    

	if (isCheckParent)
	{
		return;
	}


		var cbx = getCheckBox(pNode.id);
	    cbx.checked = false;
	   setCheckedValues(pNode, false);		   
	   unCheckParents(pNode);
	


}

// 循环当前点击节点的所有子节点.
function checkChildren(item, bChecked){
	checkNode(item); // 加载、展开子节点.
	for(var i=0; i<item.childNodes.length; i++){
	    var node = item.childNodes[i];		
		if(node instanceof WebFXCheckBoxTreeItem){
			checkChildren(node, bChecked);
		    var cbx = getCheckBox(node.id);		 
			if(!cbx.disabled){
				cbx.checked = bChecked;
				setCheckedValues(node, bChecked);
			}			
		}
	}	
}

// 加载、展开子节点
function checkNode(item){  
	if (webFXTreeConfig.autoExpand){
	  if(item.loadChildren) item.loadChildren();
      if(item.childNodes.length > 0)  item.expand();
	}
}


// 获取选取的对象.
function getCheckObjects(includeDisabled){
	var array = new Array();
	var values = checkedValues.getValues();
	for(var i=0; i<values.length; i++){
		var obj = values[i];
		if(obj != null){
			if(includeDisabled){
				array[array.length] = obj;
			}else{
			    var cbx = getCheckBox(obj.id);
			    if(!cbx.disabled) array[array.length] = obj;
			}
		}
	}
	return array;
}


// 获取所有的选中CheckBox的值. 
// includeDisabled 是否包括disabled状态的CheckBox, 默认值是false.
function getCheckValues(includeDisabled){	
	var array = new Array();
	var values = checkedValues.getValues();
	for(var i=0; i<values.length; i++){
		var obj = values[i];
		if(obj != null){
			if(includeDisabled){
				array[array.length] = obj.value;
			}else{
			    var cbx = getCheckBox(obj.id);
			    if(!cbx.disabled) array[array.length] = obj.value;
			}
		}
	}
	return array;
}


// 返回选中 checkbox 的文本。
function getCheckTexts(includeDisabled){		
	var array = new Array();
	var values = checkedValues.getValues();
	for(var i=0; i<values.length; i++){
		var obj = values[i];
		if(obj != null){
			if(includeDisabled){
				array[array.length] = obj.text;
			}else{
			    var cbx = getCheckBox(obj.id);
			    if(!cbx.disabled) array[array.length] = obj.text;
			}
		}
	}
	return array;
}

// 设置CheckBox的选中状态.
function setCheckBox(value, checked, recursive){
	var cbxs = document.getElementsByName(value + "-input");
	if(cbxs && cbxs.length >0){
		for(var i=0; i<cbxs.length; i++){
			var cbx = cbxs[i];
			   //if(!cbx.disabled)
			   cbx.checked = checked;		
			   var id = cbx.id.substring(0, cbx.id.length-6);	        
			   var node = webFXTreeHandler.all[id];
			   setCheckedValues(node, checked);
			   if(recursive){				   
				   doCheck(node, checked);
			   }
		}
	}
}

// 设置CheckBox的可用状态.
function disableCheckBox(id, disabled, recursive){
	var cbxs = document.getElementsByName(id + "-input");
	if(cbxs && cbxs.length >0){
		for(var i=0; i<cbxs.length; i++){
			var cbx = cbxs[i];
			cbx.disabled = disabled;	
			var id = cbx.id.substring(0, cbx.id.length-6);
	        var span = document.getElementById(id+ "-anchor");
	        if(span){
				if(disabled) span.style.color = disableColor;
				else span.style.color = "black";
			}
			if(recursive){			
				var node = webFXTreeHandler.all[id];
				disabledChildren(node, disabled);
			}			
		}
	}	
}

function disabledChildren(node, disabled){
  for(var i=0; i<node.childNodes.length; i++){
	  var item = node.childNodes[i];
	  var id = item.id;
	  var cbx = document.getElementById(id + "-input");
	  if(cbx)cbx.disabled  = disabled;	 
	  var span = document.getElementById(id + "-anchor");
	  if(span){
	    	if(disabled) span.style.color = disableColor;
		    	else span.style.color = "black";
	  }
	  disabledChildren(item, disabled);
  }
}

// 隐藏CheckBox.
function visiableCheckBox(visiable){
	if(visiable) disp = "";
	else disp = "none";
	var cbxs = document.getElementsByTagName("INPUT");
	if(cbxs && cbxs.length >0){
		for(var i=0; i<cbxs.length; i++){
			var cbx = cbxs[i];
			if(cbx.type.toUpperCase() == "CHECKBOX"){
				cbx.style.display = disp;
			}
		}
	}
}

