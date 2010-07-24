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
package org.pssframework.controller;


/**
 * 标准的rest方法列表
 * <pre>
 * /userinfo                => index()  
 * /userinfo/new            => _new()  注意: 不使用/userinfo/add => add()的原因是ad会被一些浏览器当做广告URL拦截
 * /userinfo/{id}           => show()  
 * /userinfo/{id}/edit      => edit()  
 * /userinfo        POST    => create()  
 * /userinfo/{id}   PUT     => update()  
 * /userinfo/{id}   DELETE  => delete()  
 * /userinfo        DELETE  => batchDelete()  
 * </pre>
 * 
 * @author badqiu
 */
public class BaseRestSpringController<Entity, PK> extends BaseSpringController {

}