package org.pssframework.model.tree;

import net.jcreate.e3.tree.UncodeException;
import net.jcreate.e3.tree.UserDataUncoder;

import org.pssframework.model.system.ResourceInfo;

public class ResourceUncoder implements UserDataUncoder {
	public Object getID(Object pUserData) throws UncodeException {
		ResourceInfo resourceInfo = (ResourceInfo) pUserData;
		return resourceInfo.getResourceId();
	}

	public Object getParentID(Object pUserData) throws UncodeException {
		ResourceInfo resourceInfo = (ResourceInfo) pUserData;
		return resourceInfo.getResourceUpperId();
	}

}
