/**
 * 
 */
package org.pssframework.controller.tree;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jcreate.e3.tree.Node;
import net.jcreate.e3.tree.TreeDirector;
import net.jcreate.e3.tree.TreeModel;
import net.jcreate.e3.tree.UncodeException;
import net.jcreate.e3.tree.UserDataUncoder;
import net.jcreate.e3.tree.ext.ExtLoadTreeBuilder;
import net.jcreate.e3.tree.ext.ExtSubTreeBuilder;
import net.jcreate.e3.tree.ext.ExtTreeBuilder;
import net.jcreate.e3.tree.support.AbstractWebTreeModelCreator;
import net.jcreate.e3.tree.support.DefaultNodeComparator;
import net.jcreate.e3.tree.support.DefaultTreeDirector;
import net.jcreate.e3.tree.support.DefaultTreeModel;
import net.jcreate.e3.tree.support.RequestUtil;
import net.jcreate.e3.tree.support.WebTreeBuilder;
import net.jcreate.e3.tree.support.WebTreeDynamicNode;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.model.tree.LeafInfo;
import org.pssframework.service.system.OrgInfoManager;
import org.pssframework.service.tree.LeafInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author Baocj
 *
 */
@Controller
@RequestMapping("/tree")
public class LeafController extends BaseRestSpringController<LeafInfo, java.lang.Long> {

	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	private LeafInfoManager leafInfoManager;

	private final String LIST_ACTION = "redirect:/tree";

	private final String PARENT_ID = "parentId";
	private final String PARENT_TYPE = "parentType";

	@Autowired
	private OrgInfoManager orgInfoManager;

	/** 
	 * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性
	 **/
	public void setLeafInfoManager(LeafInfoManager manager) {
		this.leafInfoManager = manager;
	}

	/** 列表 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, LeafInfo leafInfo) {

		ModelAndView result = new ModelAndView();
		result.addObject("leafInfo", this.showExtLoadTree(request, response));

		result.setViewName("/tree/ExtLoadTree");

		return result;
	}

	/** 进入新增 */
	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, LeafInfo leafInfo)
			throws Exception {
		return new ModelAndView("/tree/new", "leafInfo", leafInfo);
	}

	/** 显示 */
	@Override
	public ModelAndView show(@PathVariable java.lang.Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		loadExtSubOrgs(request, response);
		return null;
	}

	/** 编辑 */
	@Override
	public ModelAndView edit(@PathVariable java.lang.Long id) throws Exception {
		LeafInfo leafInfo = (LeafInfo) leafInfoManager.getById(id);
		return new ModelAndView("/tree/edit", "leafInfo", leafInfo);
	}

	/** 保存新增 */
	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, LeafInfo leafInfo)
			throws Exception {
		leafInfoManager.save(leafInfo);
		return new ModelAndView(LIST_ACTION);
	}

	/** 保存更新 */
	@Override
	public ModelAndView update(@PathVariable java.lang.Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LeafInfo leafInfo = (LeafInfo) leafInfoManager.getById(id);
		bind(request, leafInfo);
		leafInfoManager.update(leafInfo);
		return new ModelAndView(LIST_ACTION);
	}

	private String showExtLoadTree(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
		//TODO
		final String parentID = "4";

		OrgInfo orginfo = orgInfoManager.getById(Long.parseLong(parentID));

		Object obj = null;

		WebTreeDynamicNode rootNode = new WebTreeDynamicNode(orginfo.getOrgName(), "ORG_" + orginfo.getOrgId(),
				new UserDataUncoder() {

					public Object getParentID(Object arg0) throws UncodeException {
						// TODO Auto-generated method stub
						return null;
					}

					public Object getID(Object arg0) throws UncodeException {
						// TODO Auto-generated method stub
						return ((OrgInfo) arg0).getOrgId();
					}
				});
		rootNode.setSubTreeURL(RequestUtil.getUrl("/tree/" + parentID + "?" + PARENT_ID + "=" + parentID + "&"
				+ PARENT_TYPE + "=ORG", pRequest));

		DefaultTreeModel treeModel = new DefaultTreeModel();
		treeModel.addRootNode(rootNode);
		TreeDirector director = new DefaultTreeDirector();
		director.setComparator(new DefaultNodeComparator());
		ExtTreeBuilder treeBuilder = new ExtLoadTreeBuilder();
		treeBuilder.init(pRequest);
		director.build(treeModel, treeBuilder);
		String treeScript = treeBuilder.getTreeScript();
		return treeScript;
	}

	@SuppressWarnings("unchecked")
	public void loadExtSubOrgs(HttpServletRequest pRequest, HttpServletResponse pResponse) throws Exception {

		final String parentID = pRequest.getParameter(PARENT_ID);
		PageRequest<Map> pageRequest = newPageRequest(pRequest, DEFAULT_SORT_COLUMNS);
		pageRequest.getFilters().put(PARENT_ID, parentID);
		pageRequest.getFilters().put(PARENT_TYPE, pRequest.getParameter(PARENT_TYPE));

		Page nodesPages = leafInfoManager.findByPageRequest(pageRequest);

		AbstractWebTreeModelCreator treeModelCreator = new AbstractWebTreeModelCreator() {
			@Override
			protected Node createNode(Object pUserData, UserDataUncoder pUncoder) {
				LeafInfo leaf = (LeafInfo) pUserData;
				WebTreeDynamicNode result = new WebTreeDynamicNode(leaf.getLeafName(), leaf.getLeafType() + "_"
						+ leaf.getLeafId());
				result.setSubTreeURL(getUrl("/tree/" + leaf.getLeafId() + "&" + PARENT_ID + "=" + leaf.getLeafId()
						+ "&" + PARENT_TYPE + "=" + leaf.getLeafType() + "&random=" + Math.random()));

				result.setValue(leaf.getLeafId());
				return result;
			}
		};
		treeModelCreator.init(pRequest);

		TreeModel treeModel = treeModelCreator.create(nodesPages.getResult(), new UserDataUncoder() {

			public Object getParentID(Object arg) throws UncodeException {

				return parentID;
			}

			public Object getID(Object arg) throws UncodeException {
				return ((LeafInfo) arg).getLeafId();
			}
		});

		TreeDirector director = new DefaultTreeDirector();
		director.setComparator(new DefaultNodeComparator());
		WebTreeBuilder treeBuilder = new ExtSubTreeBuilder();
		treeBuilder.init(pRequest);
		director.build(treeModel, treeBuilder);

		String treeScript = treeBuilder.getTreeScript();

		pResponse.setBufferSize(1024 * 10);
		pResponse.setContentType("text/json;charset=utf-8");
		pResponse.getWriter().write(treeScript);
		pResponse.flushBuffer();

	}
}
