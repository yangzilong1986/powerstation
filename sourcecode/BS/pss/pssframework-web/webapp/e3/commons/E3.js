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
 function JsLoader(){
        //定义载入成功事件
        this.onsuccess=function(){};
        //定义失败事件
        this.onfailure=function(){alert('加载js出错!')};
 
   this.load=function(url){             
            //获取所有的<script>标记
            var ss=document.getElementsByTagName("script");
            //判断指定的文件是否已经包含，如果已包含则触发onsuccess事件并返回
            for (i=0;i<ss.length;i++){
                if (ss[i].src && ss[i].src.indexOf(url)!=-1){
                    this.onsuccess();
                    return;
                }
            }
            //创建script结点,并将其属性设为外联JavaScript文件
            s=document.createElement("script");
            s.type="text/javascript";
            s.src=url;
            //获取head结点，并将<script>插入到其中
            var head=document.getElementsByTagName("head")[0];
            head.appendChild(s);
            
            //获取自身的引用
            var self=this;
            //对于IE浏览器，使用readystatechange事件判断是否载入成功
            //对于其他浏览器，使用onload事件判断载入是否成功
            //s.onload=s.onreadystatechange=function(){
            s.onload=s.onreadystatechange=function(){
                //在此函数中this指针指的是s结点对象，而不是JsLoader实例,
                //所以必须用self来调用onsuccess事件，下同。
                if (this.readyState && this.readyState=="loading") return;
                self.onsuccess();
            }
            s.onerror=function(){
                head.removeChild(s);
                self.onfailure();
            }
        };
    }
