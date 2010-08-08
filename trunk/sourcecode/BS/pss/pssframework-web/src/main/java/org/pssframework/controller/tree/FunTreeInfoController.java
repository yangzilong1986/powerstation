/**
 * 
 */
package org.pssframework.controller.tree;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jcreate.e3.tree.Node;
import net.jcreate.e3.tree.TreeDirector;
import net.jcreate.e3.tree.TreeModel;
import net.jcreate.e3.tree.UserDataUncoder;
import net.jcreate.e3.tree.support.AbstractNodeComparator;
import net.jcreate.e3.tree.support.AbstractWebTreeModelCreator;
import net.jcreate.e3.tree.support.DefaultTreeDirector;
import net.jcreate.e3.tree.support.WebTreeNode;
import net.jcreate.e3.tree.xtree.PrvCheckXTreeBuilder;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.system.ResourceInfo;
import org.pssframework.model.tree.ResourceUncoder;
import org.pssframework.service.system.ResourceInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
 *
 */
@RequestMapping("/tree/fun")
@Controller
public class FunTreeInfoController extends BaseSpringController {

	private static final String VIEW = "/tree/checkTree";

	@Autowired
	private ResourceInfoManager resourceInfoManager;

	@RequestMapping
	public ModelAndView showCheckTree(ModelAndView modelAndView, HttpServletRequest pRequest,
			HttpServletResponse pResponse) throws Exception {

		List<ResourceInfo> resourceInfos = resourceInfoManager.findAll();

		String checkedBox = pRequest.getParameter("checked");

		UserDataUncoder orgUncoder = new ResourceUncoder();

		AbstractWebTreeModelCreator treeModelCreator = new AbstractWebTreeModelCreator() {
			@Override
			protected Node createNode(Object pUserData, UserDataUncoder pUncoder) {

				ResourceInfo resource = (ResourceInfo) pUserData;

				WebTreeNode result = new WebTreeNode(resource.getResourceName(), "FUN_" + resource.getResourceId());

				result.setValue(String.valueOf(resource.getResourceId()));

				return result;
			}
		};
		treeModelCreator.init(pRequest);

		TreeModel treeModel = treeModelCreator.create(resourceInfos, orgUncoder);

		TreeDirector director = new DefaultTreeDirector();

		director.setComparator(new AbstractNodeComparator() {
			@SuppressWarnings("rawtypes")
			@Override
			protected Comparable getComparableProperty(Node pNode) {

				ResourceInfo resource = (ResourceInfo) pNode.getUserData();

				return resource.getResourceId().intValue();
			}
		});

		PrvCheckXTreeBuilder treeBuilder = new PrvCheckXTreeBuilder();

		//CheckXTreeBuilder treeBuilder = new CheckXTreeBuilder();
		treeBuilder.setCascadeCheck(true);

		treeBuilder.init(pRequest);

		director.build(treeModel, treeBuilder);

		String treeScript = treeBuilder.getTreeScript();

		modelAndView.addObject("treeScript", treeScript);

		modelAndView.addObject("checked", checkedBox);

		//pRequest.setAttribute("treeScript", treeScript);

		modelAndView.setViewName(VIEW);

		return modelAndView;

	}

}
