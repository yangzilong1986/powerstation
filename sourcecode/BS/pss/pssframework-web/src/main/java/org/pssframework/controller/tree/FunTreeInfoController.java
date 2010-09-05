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
import net.jcreate.e3.tree.xtree.XTreeBuilder;

import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.system.ResourceInfo;
import org.pssframework.model.system.RoleInfo;
import org.pssframework.model.tree.ResourceUncoder;
import org.pssframework.service.system.ResourceInfoManager;
import org.pssframework.service.system.RoleInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Administrator
 *
 */
@RequestMapping("/tree/fun")
@Controller
public class FunTreeInfoController extends BaseSpringController {

	private static final String VIEW_CHECK = "/tree/checkTree";
	private static final String VIEW_SIMPLE_ROLE = "/tree/simpleTree";

	@Autowired
	private ResourceInfoManager resourceInfoManager;

	@Autowired
	private RoleInfoManager roleInfoManager;

	/**
	 * 提供系统可以选择的功能树
	 * @param modelAndView
	 * @param pRequest
	 * @param pResponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checktree")
	public ModelAndView showValidFunCheckTree(ModelAndView modelAndView, HttpServletRequest pRequest,
			HttpServletResponse pResponse) throws Exception {

		List<ResourceInfo> resourceInfos = resourceInfoManager.findAllValid();

		String checkedBox = pRequest.getParameter("checked");

		UserDataUncoder resourceUncoder = new ResourceUncoder();

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

		TreeModel treeModel = treeModelCreator.create(resourceInfos, resourceUncoder);

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

		modelAndView.setViewName(VIEW_CHECK);

		return modelAndView;

	}

	@RequestMapping("/simpleTree/{roleId}")
	public ModelAndView showSimpleRoleTree(ModelAndView modelAndView, @PathVariable Long roleId,
			HttpServletRequest pRequest, HttpServletResponse pResponse) throws Exception {

		RoleInfo roleInfo = this.roleInfoManager.getById(roleId);

		List<ResourceInfo> resourceInfos = roleInfo.getResourceInfoList();

		if (resourceInfos == null || resourceInfos.size() == 0) {
			resourceInfos.add(new ResourceInfo(0L, "暂无"));
		} else {
			resourceInfos.add(new ResourceInfo(0L, "功能树"));
		}

		UserDataUncoder resourceUncoder = new ResourceUncoder();

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

		TreeModel treeModel = treeModelCreator.create(resourceInfos, resourceUncoder);

		TreeDirector director = new DefaultTreeDirector();

		director.setComparator(new AbstractNodeComparator() {
			@SuppressWarnings("rawtypes")
			@Override
			protected Comparable getComparableProperty(Node pNode) {

				ResourceInfo resource = (ResourceInfo) pNode.getUserData();

				return resource.getResourceId().intValue();
			}
		});

		XTreeBuilder treeBuilder = new XTreeBuilder();//构造树Builder

		treeBuilder.init(pRequest);

		treeBuilder.setRootExpand(true);

		director.build(treeModel, treeBuilder);//执行构造		

		String treeScript = treeBuilder.getTreeScript();

		modelAndView.addObject("treeScript", treeScript);

		//pRequest.setAttribute("treeScript", treeScript);

		modelAndView.setViewName(VIEW_SIMPLE_ROLE);

		return modelAndView;

	}
}
