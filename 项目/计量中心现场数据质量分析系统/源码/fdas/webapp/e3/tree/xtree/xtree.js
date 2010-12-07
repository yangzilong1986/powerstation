/*----------------------------------------------------------------------------\
|                       Cross Browser Tree Widget 1.17                        |
|-----------------------------------------------------------------------------|
|                          Created by Emil A Eklund                           |
|                  (http://webfx.eae.net/contact.html#emil)                   |
|                      For WebFX (c/)                      |
|-----------------------------------------------------------------------------|
| An object based tree widget,  emulating the one found in microsoft windows, |
| with persistence using cookies. Works in IE 5+, Mozilla and konqueror 3.    |
|-----------------------------------------------------------------------------|
|                   Copyright (c) 1999, 2010 Emil A Eklund                   |
|-----------------------------------------------------------------------------|
| This software is provided "as is", without warranty of any kind, express or |
| implied, including  but not limited  to the warranties of  merchantability, |
| fitness for a particular purpose and noninfringement. In no event shall the |
| authors or  copyright  holders be  liable for any claim,  damages or  other |
| liability, whether  in an  action of  contract, tort  or otherwise, arising |
| from,  out of  or in  connection with  the software or  the  use  or  other |
| dealings in the software.                                                   |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| This  software is  available under the  three different licenses  mentioned |
| below.  To use this software you must chose, and qualify, for one of those. |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Non-Commercial License          http://webfx.eae.net/license.html |
| Permits  anyone the right to use the  software in a  non-commercial context |
| free of charge.                                                             |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Commercial license           http://webfx.eae.net/commercial.html |
| Permits the  license holder the right to use  the software in a  commercial |
| context. Such license must be specifically obtained, however it's valid for |
| any number of  implementations of the licensed software.                    |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| GPL - The GNU General Public License    http://www.gnu.org/licenses/gpl.txt |
| Permits anyone the right to use and modify the software without limitations |
| as long as proper  credits are given  and the original  and modified source |
| code are included. Requires  that the final product, software derivate from |
| the original  source or any  software  utilizing a GPL  component, such  as |
| this, is also licensed under the GPL license.                               |
|-----------------------------------------------------------------------------|
| Dependencies: xtree.css (To set up the CSS of the tree classes)             |
|-----------------------------------------------------------------------------|
| 2001-01-10 | Original Version Posted.                                       |
| 2001-03-18 | Added getSelected and get/setBehavior  that can make it behave |
|            | more like windows explorer, check usage for more information.  |
| 2001-09-23 | Version 1.1 - New features included  keyboard  navigation (ie) |
|            | and the ability  to add and  remove nodes dynamically and some |
|            | other small tweaks and fixes.                                  |
| 2002-01-27 | Version 1.11 - Bug fixes and improved mozilla support.         |
| 2002-06-11 | Version 1.12 - Fixed a bug that prevented the indentation line |
|            | from  updating correctly  under some  circumstances.  This bug |
|            | happened when removing the last item in a subtree and items in |
|            | siblings to the remove subtree where not correctly updated.    |
| 2002-06-13 | Fixed a few minor bugs cased by the 1.12 bug-fix.              |
| 2002-08-20 | Added usePersistence flag to allow disable of cookies.         |
| 2002-10-23 | (1.14) Fixed a plus icon issue                                 |
| 2002-10-29 | (1.15) Last changes broke more than they fixed. This version   |
|            | is based on 1.13 and fixes the bugs 1.14 fixed withou breaking |
|            | lots of other things.                                          |
| 2003-02-15 | The  selected node can now be made visible even when  the tree |
|            | control  loses focus.  It uses a new class  declaration in the |
|            | css file '.webfx-tree-item a.selected-inactive', by default it |
|            | puts a light-gray rectangle around the selected node.          |
| 2003-03-16 | Adding target support after lots of lobbying...                |
|-----------------------------------------------------------------------------|
| Created 2000-12-11 | All changes are in the log above. | Updated 2003-03-16 |
\----------------------------------------------------------------------------*/

var webFXTreeConfig = {		
	autoExpand      : false,//选种节点时，是否自动把儿子节点展开来.全部设置为false.
	rootIcon        : this.imagePath + '/foldericon.png',
	openRootIcon    : this.imagePath + '/openfoldericon.png',
	folderIcon      :  this.imagePath + '/foldericon.png',
	openFolderIcon  :this.imagePath + '/openfoldericon.png',
	fileIcon        : this.imagePath + '/file.png',
	iIcon           : this.imagePath + '/I.png',
	lIcon           : this.imagePath + '/L.png',
	lMinusIcon      : this.imagePath + '/Lminus.png',
	lPlusIcon       : this.imagePath + '/Lplus.png',
	tIcon           : this.imagePath + '/T.png',
	tMinusIcon   : this.imagePath + '/Tminus.png',
	tPlusIcon       : this.imagePath + '/Tplus.png',
	blankIcon       : this.imagePath + '/blank.png',
	defaultText     : 'Tree Item',
	defaultAction   : 'javascript:void(0);',
	defaultBehavior : 'classic',
	usePersistence	: true,
    disableColor : "#9B9B9B",   // luohc [add] 2004-12-1. 不可用时的文本颜色。
	cascadeCheck: true,    // luohc. 定义checkbox树是否级联选择。 2005.02.15
	imagePath : "js/images",      // luohc. 图片路径.	2005.02.15  // 这个属性有点问题，请不要使用. 参加 setImagePath 方法.	
	setImagePath: function(path){  // 设置图片路径。直接设置 imagePath 属性不起作用。
		if(path == null) path = "js/images/";
		if(path.charAt(path.length-1) != '/') path += "/";
        //this.imagePath = path;
		this.rootIcon = path + "foldericon.png";
		this.openRootIcon = path + 'openfoldericon.png';
		this.folderIcon     =  path + 'foldericon.png';
		this.openFolderIcon  = path + 'openfoldericon.png';
		this.fileIcon       = path + 'file.png';
		this.iIcon           = path + 'I.png';
		this.lIcon           = path + 'L.png';
		this.lMinusIcon   =  path + 'Lminus.png';
		this.lPlusIcon     = path + 'Lplus.png';
		this.tIcon          = path + 'T.png';
		this.tMinusIcon  = path + 'Tminus.png';
		this.tPlusIcon     = path + 'Tplus.png';
		this.blankIcon     = path + 'blank.png';
	}	
	
};

var webFXTreeHandler = {
	idCounter : 0,
	idPrefix  : "webfx-tree-object-appllo-",
	all       : {},
	behavior  : null,
	selected  : null,
	onSelect  : null, /* should be part of tree, not handler */
	getId     : function() { return this.idPrefix + this.idCounter++; },
	toggle    : function (oItem) { this.all[oItem.id.replace('-plus','')].toggle(); },
	select    : function (oItem) { this.all[oItem.id.replace('-icon','')].select(); },
	focus     : function (oItem) { this.all[oItem.id.replace('-anchor','')].focus(); },
	blur      : function (oItem) { this.all[oItem.id.replace('-anchor','')].blur(); },
	keydown   : function (oItem, e) { return this.all[oItem.id].keydown(e.keyCode); },
	cookies   : new WebFXCookie(),
	insertHTMLBeforeEnd	:	function (oElement, sHTML) {
		if (oElement.insertAdjacentHTML != null) {
			oElement.insertAdjacentHTML("BeforeEnd", sHTML)
			return;
		}
		var df;	// DocumentFragment
		var r = oElement.ownerDocument.createRange();
		r.selectNodeContents(oElement);
		r.collapse(false);
		df = r.createContextualFragment(sHTML);
		oElement.appendChild(df);
	}
};

/*
 * WebFXCookie class
 */

function WebFXCookie() {
	this.cookieKey = "extreecookie";
	if (document.cookie.length) { 	        
		this.cookies = ' ' +  document.cookie; 
	}	
}

WebFXCookie.prototype.deleteCookie = function(){
	document.cookie = this.cookieKey + "=none";
	document.cookie = this.cookieKey + "=nothing; expires=Thu, 01-Jan-1970 00:00:01 GMT";
}

WebFXCookie.prototype.setCookie = function (key, value) {
	value = key + "=" + value + ";";
	this.cookies = ' ' +  document.cookie; 
	var values = this.getCookieValue(" " + this.cookieKey, this.cookies);
	if(values == null){		
		document.cookie = this.cookieKey + "=" + escape(value);
	}else{
		values = unescape(values);
		var start = values.indexOf(key + "=");
		if(start>=0){
			var end = values.indexOf(";", start);
			values = values.substring(0, start) + values.substring(end+1) + value;
		}else{
			values = values + value;
		}	
		document.cookie = this.cookieKey + "=" + escape(values);
	}
}

WebFXCookie.prototype.getCookie = function(key){
	this.cookies = ' ' +  document.cookie; 
	if(this.cookies == null) return null;
	var values = this.getCookieValue(" " + this.cookieKey, this.cookies);
    
	var value = null;
	if(values != null){
	   value = this.getCookieValue(key, unescape(values));
	}
	return value;
}

// 在 "key1=value1;key2=value2;" 这样的键值对字符串中获取某键值对应的值。
WebFXCookie.prototype.getCookieValue = function (key, values) {
	if(values == null) return null;
	var newKey = key + "=";
	var start = values.indexOf(newKey);
	if(start == -1) return null;
	start += newKey.length;
	var end = values.indexOf(";", start);
	if(end == -1) end = values.length;
	var value = values.substring(start, end); 
	return value;
}

/*
 * WebFXTreeAbstractNode class
 */

function WebFXTreeAbstractNode(sText, sAction) {
	this.childNodes  = [];
	this.id     = webFXTreeHandler.getId();
	this.text   = sText || webFXTreeConfig.defaultText;

	//this.action = sAction || webFXTreeConfig.defaultAction;
    
	if(sAction != null && sAction.length > 0){
		// luohc [add] 2004-12-07. 在new一个节点时可以省略javascript:前缀。
		var leftBracket = sAction.indexOf("(");   // 左括号.
		var rightBracket = sAction.indexOf(")");  // 右括号.
		var js = sAction.toLowerCase().indexOf("javascript:");  // 前缀 javascript: 。
		// 如果有左右括号(说明是方法)，但又没有javascript:前缀。
		if(leftBracket>0 && rightBracket>leftBracket && js!=0){
			sAction = "javascript:" + sAction;
		}
		this.action = sAction;
	}else{
		this.action = webFXTreeConfig.defaultAction;
	}

	this._last  = false;
	webFXTreeHandler.all[this.id] = this;
}

/*
 * To speed thing up if you're adding multiple nodes at once (after load)
 * use the bNoIdent parameter to prevent automatic re-indentation and call
 * the obj.ident() method manually once all nodes has been added.
 */

WebFXTreeAbstractNode.prototype.add = function (node, bNoIdent) {
	node.parentNode = this;
	this.childNodes[this.childNodes.length] = node;
	var root = this;
	if (this.childNodes.length >= 2) {
		this.childNodes[this.childNodes.length - 2]._last = false;
	}
	while (root.parentNode) { root = root.parentNode; }
	if (root.rendered) {
		if (this.childNodes.length >= 2) {
			document.getElementById(this.childNodes[this.childNodes.length - 2].id + '-plus').src = ((this.childNodes[this.childNodes.length -2].folder)?((this.childNodes[this.childNodes.length -2].open)?webFXTreeConfig.tMinusIcon:webFXTreeConfig.tPlusIcon):webFXTreeConfig.tIcon);
			this.childNodes[this.childNodes.length - 2].plusIcon = webFXTreeConfig.tPlusIcon;
			this.childNodes[this.childNodes.length - 2].minusIcon = webFXTreeConfig.tMinusIcon;
			this.childNodes[this.childNodes.length - 2]._last = false;
		}
		this._last = true;
		var foo = this;
		while (foo.parentNode) {
			for (var i = 0; i < foo.parentNode.childNodes.length; i++) {
				if (foo.id == foo.parentNode.childNodes[i].id) { break; }
			}
			if (i == foo.parentNode.childNodes.length - 1) { foo.parentNode._last = true; }
			else { foo.parentNode._last = false; }
			foo = foo.parentNode;
		}
		webFXTreeHandler.insertHTMLBeforeEnd(document.getElementById(this.id + '-cont'), node.toString());
		if ((!this.folder) && (!this.openIcon)) {
			this.icon = webFXTreeConfig.folderIcon;
			this.openIcon = webFXTreeConfig.openFolderIcon;
		}
		if (!this.folder) { this.folder = true; this.collapse(true); }
		if (!bNoIdent) { this.indent(); }
	}
	return node;
}

WebFXTreeAbstractNode.prototype.toggle = function() {
	//只有folder才会响应此时间，如果是floader
	//如果当前状态为open,则执行收拢操作，否则展开.
	if (this.folder) {
		if (this.open) { this.collapse(); }
		else { this.expand(); }
}	}

WebFXTreeAbstractNode.prototype.select = function() {
	//选种一个节点，实际上就是设置节点为焦点控件.
	document.getElementById(this.id + '-anchor').focus();
}

WebFXTreeAbstractNode.prototype.deSelect = function() {
	//反选，清楚当前节点的className,并设置当前选中节点为null
	document.getElementById(this.id + '-anchor').className = '';
	webFXTreeHandler.selected = null;
}

WebFXTreeAbstractNode.prototype.focus = function() {
	//如果已经存在选种节点，但不是自己的话，则取消已选种节点.
	if ((webFXTreeHandler.selected) && (webFXTreeHandler.selected != this)) { webFXTreeHandler.selected.deSelect(); }
	//设置当前选种节点为自己，注意：当前选种节点只有有一个.
	webFXTreeHandler.selected = this;
	//如果存在openIcon,并且 behavior是classic,则设置图标为openIcon
	//注意：this.id是节点项的唯一标识符
	if ((this.openIcon) && (webFXTreeHandler.behavior != 'classic')) { document.getElementById(this.id + '-icon').src = this.openIcon; }
	//设置当前选项的为选种状态.
	document.getElementById(this.id + '-anchor').className = 'selected';
	//设置焦点.
	document.getElementById(this.id + '-anchor').focus();
	//触发选种事件.
    //webFXTreeHandler.onSelect相当与一个事件监听器.
	if (webFXTreeHandler.onSelect) { webFXTreeHandler.onSelect(this); }
}

WebFXTreeAbstractNode.prototype.blur = function() {
	//失去焦点
	if ((this.openIcon) && (webFXTreeHandler.behavior != 'classic')) { document.getElementById(this.id + '-icon').src = this.icon; }
	document.getElementById(this.id + '-anchor').className = 'selected-inactive';
}

WebFXTreeAbstractNode.prototype.doExpand = function() {
	//展开节点.

	//设置图标为openIcon
	if (webFXTreeHandler.behavior == 'classic') { document.getElementById(this.id + '-icon').src = this.openIcon; }
	//如果存在儿子，则展开儿子。
	//设置儿子展开，是通过设置儿子所在的div的style状态为block就可以.
	if (this.childNodes.length) {  document.getElementById(this.id + '-cont').style.display = 'block'; }
	//设置当前状态为open状态
	this.open = true;
	//设置启用了持久化，则记录当前id为选种状态为1.
	if (webFXTreeConfig.usePersistence) {
		webFXTreeHandler.cookies.setCookie(this.id.substr(18,this.id.length - 18), '1');
}	}

//收拢节点.
WebFXTreeAbstractNode.prototype.doCollapse = function() {
	//设置图标为icon.
	if (webFXTreeHandler.behavior == 'classic') { document.getElementById(this.id + '-icon').src = this.icon; }
	//如果存在儿子，则收拢儿子
	//收拢儿子，是通过设置儿子所在的div的style状态为none就可以.
	if (this.childNodes.length) { document.getElementById(this.id + '-cont').style.display = 'none'; }
	//设置当前状态为flase
	this.open = false;
	//设置启用了持久化，则记录当前id为选种状态为0.
	if (webFXTreeConfig.usePersistence) {
		webFXTreeHandler.cookies.setCookie(this.id.substr(18,this.id.length - 18), '0');
}	}

//展开所有节点，包括儿子和自身
WebFXTreeAbstractNode.prototype.expandAll = function() {
	this.expandChildren();
	if ((this.folder) && (!this.open)) { this.expand(); }
}

//展开所有儿子节点
WebFXTreeAbstractNode.prototype.expandChildren = function() {
	//这个函数很有意思，实际上是用了递归，不过不是那么明显，递归
	//是由expandChildren,和expandAll构成的.
	for (var i = 0; i < this.childNodes.length; i++) {
		this.childNodes[i].expandAll();
} }

//关闭所有节点，包括儿子和自身
WebFXTreeAbstractNode.prototype.collapseAll = function() {
	this.collapseChildren();
	if ((this.folder) && (this.open)) { this.collapse(true); }
}

//关闭儿子节点
WebFXTreeAbstractNode.prototype.collapseChildren = function() {
	for (var i = 0; i < this.childNodes.length; i++) {
		this.childNodes[i].collapseAll();
} }

//缩排.
WebFXTreeAbstractNode.prototype.indent = function(lvl, del, last, level, nodesLeft) {
	/*
	 * Since we only want to modify items one level below ourself,
	 * and since the rightmost indentation position is occupied by
	 * the plus icon we set this to -2
	 */
	if (lvl == null) { lvl = -2; }
	var state = 0;
	for (var i = this.childNodes.length - 1; i >= 0 ; i--) {
		state = this.childNodes[i].indent(lvl + 1, del, last, level);
		if (state) { return; }
	}
	if (del) {
		if ((level >= this._level) && (document.getElementById(this.id + '-plus'))) {
			if (this.folder) {
				document.getElementById(this.id + '-plus').src = (this.open)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.lPlusIcon;
				this.plusIcon = webFXTreeConfig.lPlusIcon;
				this.minusIcon = webFXTreeConfig.lMinusIcon;
			}
			else if (nodesLeft) { document.getElementById(this.id + '-plus').src = webFXTreeConfig.lIcon; }
			return 1;
	}	}
	var foo = document.getElementById(this.id + '-indent-' + lvl);
	if (foo) {
		if ((foo._last) || ((del) && (last))) { foo.src =  webFXTreeConfig.blankIcon; }
		else { foo.src =  webFXTreeConfig.iIcon; }
	}
	return 0;
}

// luohc.
//获取树的跟节点
function getTreeRoot(){
   return tree_root;
}

/*
 * WebFXTree class
 */
var tree_root; // luohc
function WebFXTree(sText, sAction, sBehavior, sIcon, sOpenIcon, sOpen) {
	this.base = WebFXTreeAbstractNode;
	this.base(sText, sAction);
	this.icon      =  webFXTreeConfig.rootIcon;
	this.openIcon  =  webFXTreeConfig.openRootIcon;
	if(sIcon){
		this.icon = sIcon;
		this.openIcon  = sIcon;
	}
	if(sOpenIcon){
		this.openIcon = sOpenIcon;
	}
	/* Defaults to open */
//	if (webFXTreeConfig.usePersistence) {
	//	this.open  = (webFXTreeHandler.cookies.getCookie(this.id.substr(18,this.id.length - 18)) == '0')?false:true;
	//} else { this.open  = true; }
	this.open = sOpen;
	this.folder    = true;
	this.rendered  = false;
	this.onSelect  = null;
	if (!webFXTreeHandler.behavior) {  webFXTreeHandler.behavior = sBehavior || webFXTreeConfig.defaultBehavior; }

	tree_root = this; // luohc 2004-7-30.
}

WebFXTree.prototype = new WebFXTreeAbstractNode;

WebFXTree.prototype.setBehavior = function (sBehavior) {
	webFXTreeHandler.behavior =  sBehavior;
};

WebFXTree.prototype.getBehavior = function (sBehavior) {
	return webFXTreeHandler.behavior;
};

WebFXTree.prototype.getSelected = function() {
	if (webFXTreeHandler.selected) { return webFXTreeHandler.selected; }
	else { return null; }
}

WebFXTree.prototype.remove = function() { }

WebFXTree.prototype.expand = function() {
	this.doExpand();
}

WebFXTree.prototype.collapse = function(b) {
	if (!b) { this.focus(); }
	this.doCollapse();
}

WebFXTree.prototype.getFirst = function() {
	return null;
}

WebFXTree.prototype.getLast = function() {
	return null;
}

WebFXTree.prototype.getNextSibling = function() {
	return null;
}

WebFXTree.prototype.getPreviousSibling = function() {
	return null;
}

WebFXTree.prototype.keydown = function(key) {
	if (key == 39) {
		if (!this.open) { this.expand(); }
		else if (this.childNodes.length) { this.childNodes[0].select(); }
		return false;
	}
	if (key == 37) { this.collapse(); return false; }
	if ((key == 40) && (this.open) && (this.childNodes.length)) { this.childNodes[0].select(); return false; }
	return true;
}

WebFXTree.prototype.toString = function() {
	var str = "<div id=\"" + this.id + "\" ondblclick=\"webFXTreeHandler.toggle(this);\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\">" +
		"<img id=\"" + this.id + "-icon\" class=\"webfx-tree-icon\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"webFXTreeHandler.select(this);\">" +
		"<a href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onfocus=\"webFXTreeHandler.focus(this);\" onblur=\"webFXTreeHandler.blur(this);\"" +
		(this.target ? " target=\"" + this.target + "\"" : "") +
		">" + this.text + "</a></div>" +
		"<div id=\"" + this.id + "-cont\" class=\"webfx-tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	var sb = [];
	for (var i = 0; i < this.childNodes.length; i++) {
		sb[i] = this.childNodes[i].toString(i, this.childNodes.length);
	}
	this.rendered = true;
	return str + sb.join("") + "</div>";
};

/*
 * WebFXTreeItem class
 */

function WebFXTreeItem(sText, sAction, eParent, sIcon, sOpenIcon) {
	this.base = WebFXTreeAbstractNode;
	this.base(sText, sAction);
	/* Defaults to close */
	if (webFXTreeConfig.usePersistence) {
		this.open = (webFXTreeHandler.cookies.getCookie(this.id.substr(18,this.id.length - 18)) == '1')?true:false;
	} else { this.open = false; }
	if (sIcon) { 
		this.icon = sIcon; 
		this.openIcon = sIcon; 
	}
	if (sOpenIcon) { this.openIcon = sOpenIcon; }
	if (eParent) { eParent.add(this); }
}

WebFXTreeItem.prototype = new WebFXTreeAbstractNode;

WebFXTreeItem.prototype.remove = function() {
	var iconSrc = document.getElementById(this.id + '-plus').src;
	var parentNode = this.parentNode;
	var prevSibling = this.getPreviousSibling(true);
	var nextSibling = this.getNextSibling(true);
	var folder = this.parentNode.folder;
	var last = ((nextSibling) && (nextSibling.parentNode) && (nextSibling.parentNode.id == parentNode.id))?false:true;
	this.getPreviousSibling().focus();
	this._remove();

	if (parentNode.childNodes.length == 0) {
		document.getElementById(parentNode.id + '-cont').style.display = 'none';
		parentNode.doCollapse();
		parentNode.folder = false;
		parentNode.open = false;
	}

	if (!nextSibling || last) { parentNode.indent(null, true, last, this._level, parentNode.childNodes.length); }
	if ((prevSibling == parentNode) && !(parentNode.childNodes.length)) {
		prevSibling.folder = false;
		prevSibling.open = false;
		iconSrc = document.getElementById(prevSibling.id + '-plus').src;
		iconSrc = iconSrc.replace('minus', '').replace('plus', '');
		//document.getElementById(prevSibling.id + '-plus').src = iconSrc;
		//document.getElementById(prevSibling.id + '-icon').src = webFXTreeConfig.fileIcon;
	}
	if (document.getElementById(prevSibling.id + '-plus')) {
		if (parentNode == prevSibling.parentNode) {
			iconSrc = iconSrc.replace('minus', '').replace('plus', '');
			document.getElementById(prevSibling.id + '-plus').src = iconSrc;
}	
}	}

WebFXTreeItem.prototype._remove = function() {
	for (var i = this.childNodes.length - 1; i >= 0; i--) {
		this.childNodes[i]._remove();
 	}
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) {
			for (var j = i; j < this.parentNode.childNodes.length; j++) {
				this.parentNode.childNodes[j] = this.parentNode.childNodes[j+1];
			}
			this.parentNode.childNodes.length -= 1;
			if (i + 1 == this.parentNode.childNodes.length) { this.parentNode._last = true; }
			break;
	}	}
	webFXTreeHandler.all[this.id] = null;
	var tmp = document.getElementById(this.id);
	if (tmp) { tmp.parentNode.removeChild(tmp); }
	tmp = document.getElementById(this.id + '-cont');
	if (tmp) { tmp.parentNode.removeChild(tmp); }
}

WebFXTreeItem.prototype.expand = function() {
	this.doExpand();
	document.getElementById(this.id + '-plus').src = this.minusIcon;
}

WebFXTreeItem.prototype.collapse = function(b) {
	if (!b) { this.focus(); }
	this.doCollapse();
	document.getElementById(this.id + '-plus').src = this.plusIcon;
}

WebFXTreeItem.prototype.getFirst = function() {
	return this.childNodes[0];
}

WebFXTreeItem.prototype.getLast = function() {
	if (this.childNodes[this.childNodes.length - 1].open) { return this.childNodes[this.childNodes.length - 1].getLast(); }
	else { return this.childNodes[this.childNodes.length - 1]; }
}

WebFXTreeItem.prototype.getNextSibling = function() {
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) { break; }
	}
	if (++i == this.parentNode.childNodes.length) { return this.parentNode.getNextSibling(); }
	else { return this.parentNode.childNodes[i]; }
}

WebFXTreeItem.prototype.getPreviousSibling = function(b) {
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) { break; }
	}
	if (i == 0) { return this.parentNode; }
	else {
		if ((this.parentNode.childNodes[--i].open) || (b && this.parentNode.childNodes[i].folder)) { return this.parentNode.childNodes[i].getLast(); }
		else { return this.parentNode.childNodes[i]; }
} }

WebFXTreeItem.prototype.keydown = function(key) {
	if ((key == 39) && (this.folder)) {
		if (!this.open) { this.expand(); }
		else { this.getFirst().select(); }
		return false;
	}
	else if (key == 37) {
		if (this.open) { this.collapse(); }
		else { this.parentNode.select(); }
		return false;
	}
	else if (key == 40) {
		if (this.open) { this.getFirst().select(); }
		else {
			var sib = this.getNextSibling();
			if (sib) { sib.select(); }
		}
		return false;
	}
	else if (key == 38) { this.getPreviousSibling().select(); return false; }
	return true;
}

WebFXTreeItem.prototype.toString = function (nItem, nItemCount) {
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
	var str = "<div id=\"" + this.id + "\" ondblclick=\"webFXTreeHandler.toggle(this);\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\">" +
		indent +
		"<img id=\"" + this.id + "-plus\" src=\"" + ((this.folder)?((this.open)?((this.parentNode._last)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.tMinusIcon):((this.parentNode._last)?webFXTreeConfig.lPlusIcon:webFXTreeConfig.tPlusIcon)):((this.parentNode._last)?webFXTreeConfig.lIcon:webFXTreeConfig.tIcon)) + "\" onclick=\"webFXTreeHandler.toggle(this);\">" +
		"<img id=\"" + this.id + "-icon\" class=\"webfx-tree-icon\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"webFXTreeHandler.select(this);\">" +
		"<a href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onfocus=\"webFXTreeHandler.focus(this);\" onblur=\"webFXTreeHandler.blur(this);\"" +
		(this.target ? " target=\"" + this.target + "\"" : "") +
		">" + label + "</a></div>" +
		"<div id=\"" + this.id + "-cont\" class=\"webfx-tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	var sb = [];
	for (var i = 0; i < this.childNodes.length; i++) {
		sb[i] = this.childNodes[i].toString(i,this.childNodes.length);
	}
	this.plusIcon = ((this.parentNode._last)?webFXTreeConfig.lPlusIcon:webFXTreeConfig.tPlusIcon);
	this.minusIcon = ((this.parentNode._last)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.tMinusIcon);
	return str + sb.join("") + "</div>";
}
