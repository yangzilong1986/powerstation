= What is this =

 This is a manual jquery.msgbox for version 6.0

= Box structure =

                background
   +--------------wrapper---------------+
   |+-----------titleWrapper-----------+|
   ||+------titleLi-------+--closeLi--+||
   ||| title              | closeIcon |||
   ||+--------------------+-----------+||
   |+----------------------------------+|
   |+---------------main---------------+|
   ||                                  ||
   ||              content             ||
   ||                                  ||
   |+----------------------------------+|
   +------------------------------------+

= Parameters instruction =

  * width             : (number)   the width of the box
  * height            : (number)   the height of the box
  * autoClose         : (number)   time(seconds) after which the box will be auto-closed
  * title             : (string)   the title of the box
  * wrapperClass      : (string)   the class of wrapper
  * titleClass        : (string)   the class of titleLi
  * closeClass        : (string)   the class of closeLi
  * titleWrapperClass : (string)   the class of titleWrapper
  * mainClass         : (string)   the class of main
  * bgClass           : (string)   the class of background
  * buttonClass       : (string)   the class of buttons in content
  * inputboxClass     : (string)   the class of input boxes in content
  * content           : (string)   the content to show (support HTML)
                      : (object)   {type: 'text', content: 'hello, world'}
                                   type supported: text, url(get, ajax), iframe, input, alert, confirm
                                   content: text(for text, alert, confirm) or 
                                            url(for url, get, ajax, iframe) or 
                                            hint(for input)
  * onClose           : (function) event when the box is closing
  * closeIcon         : (string)   the string to show in closeIcon region
                      : (object)   {type: 'image', content: 'close.gif'}
                                   type supported: string, image
                                   content: the text or the url of the close icon
  * bgOpacity         : (number)   the opacity of the background
  * onAjaxed          : (function) event after ajax loaded
  * onInputed         : (function) a function to get what the user inputed
  * drag              : (boolean)  enable drag or not
  * animation         : (number)   use an animation effect
                                   0 or >3: do not use
                                   1: slide down (default)
                                   2: from left to right
                                   3: from left to right and top to bottom
                         
= Public properties and functions =
  
  provided using: $mb = $.msgbox(...)
  
  * $mb.value                     read only 
    the return value of the box
  * $mb.close()       
    close the box
  * $mb.setAutoClose(t)           t: number
    set the time(t) after which the box will be auto-closed from now on
  * $mb.setHeight(h)              h: number
    change the height of the box to h
  * $mb.setWidth(w)               w: number
    change the width of the box to w
  * $mb.setTitle(t)               t: string
    change the title of the box to t
  * setWrapperClass(wc)           wc: string
    change the class of wrapper to wc
  * setTitleClass(tc)             tc: string
    change the class of titleLi to tc
  * setCloseClass(cc)             cc: string
    change the class of closeLi to cc
  * setTitleWrapperClass(twc)     twc: string
    change the class of titleWrapper to twc
  * setMainClass(mc)              mc: string
    change the class of main to mc
  * setBgClass(bgc)               bgc: string
    change the class of background to bgc
  * setButtonClass(bc)            bc: string
    change the class of button in content to bc
  * setInputboxClass(ic)          ic: string
    change the class of input boxes in content to ic
  * setContent(c)                 c: string or {type:type, content:content}
    change the content to c (similar as parameter content)
  * setBgOpacity(bo)              bo: number
    change the opacity of the background to bo
  * setOnClose(oc)                oc: function
    change the closing event to oc
  * setOnAjaxed(oa)               oa: function
    change the event after ajax loaded to oa
  * setOnInputed(oi)              oi: function
    change the evnet when input closed to oi
  * setAnimation(a)               a: number
    change the animation
  * setCloseIcon(ci)              ci string or {type:type, content:content}
    set the close icon to ci(similar as parameter closeIcon)
    
= Examples =

  * simple way
    $.msgbox("hello, world!");
  * set width and height
    $.msgbox({
        closeIcon: 'Close', // closeIcon: {type:'image', content:'close.gif'}
        height:500,
        width:600,
        content:'Hello, world!' // content:{type:'text', content:'Hello, world!'}
    });
  * set type as alert, set animation, disable dragging, and set auto-closing
    $.msgbox({
        content:{type:'alert', content:'Warning'},
        animation:0,
        drag:false,
        autoClose: 10
    });
  * set type as confirm
    $.msgbox({
        height:150,
        width:250,
        content:{type:'confirm', content: 'Hello, world?'},
        onClose:function(v){
            if(v) alert("You've clicked 'Yes' button!");
            else alert("You've clicked 'No' button or closed me!");
        }
    });    
  * event when closing
    $.msgbox({
        height:500,
        width:600,
        content:'Hello, world!',
        onClose: function(){alert('You¡£¡£¡£have closed me!')}
    });
  * set type as ajax
    $.msgbox({
        height:500,
        width:600,
        content:{type:'ajax', content:'http://pwwang.com'},
        title: 'PWWANG.COM',
        onAjaxed: function(data){alert(data)}
    });
  * set type as input
    $mb = $.msgbox({
        height:500,
        width:600,
        content:{type:'input', content:'Input: '},
        title: 'Input',
        onInputed: function(v){alert('You inputed:'+v)} 
        // or you can get the value by $mb.value somewhere else
    });	
  * public functions
    $mb = $.msgbox({
        height:500,
        width:600,
        content:{type:'iframe',content:'demo_iframe.htm'},
        title: 'Public functions',
        closeIcon:{type:'icon',content:'close.gif'}
    });
    // in demo_iframe.htm
    top.$mb
        .setHeight(550)
        .setWidth(650)
        .setTitle("Public functions demo")
        .setWrapperClass("msgbox_wrapper1")
        .setTitleClass("msgbox_title1")
        .setCloseClass("msgbox_close1")
        .setTitleWrapperClass("msgbox_title_wrapper1")
        .setMainClass("msgbox_main1")
        .setBgClass("msgbox_bg1")
        .setButtonClass("msgbox_button1")
        .setInputboxClass("msgbox_inputbox1")
        .setContent({type:'text', content:'Hello, world!'})
        .setBgOpacity(.9)
        .setOnClose(function(){alert(1)})
        .setAnimation(3)
        .setCloseIcon("Close")
        .setAutoClose(30);
        