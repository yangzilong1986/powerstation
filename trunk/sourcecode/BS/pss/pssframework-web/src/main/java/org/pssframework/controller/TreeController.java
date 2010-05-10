/**
 * 
 */
package org.pssframework.controller;

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

import org.pssframework.model.Leaf;
import org.pssframework.model.TreeInfo;
import org.pssframework.service.TreeInfoManager;
import org.pssframework.support.WebTreeDynamicNode;
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
public class TreeController extends BaseRestSpringController<TreeInfo, java.lang.Long> {

	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = null;

	private TreeInfoManager treeInfoManager;

	private final String LIST_ACTION = "redirect:/tree";

	/** 
	 * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性
	 **/
	public void setTreeInfoManager(TreeInfoManager manager) {
		this.treeInfoManager = manager;
	}

	/** 列表 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, TreeInfo treeInfo) {
		PageRequest<Map> pageRequest = newPageRequest(request, DEFAULT_SORT_COLUMNS);
		//pageRequest.getFilters(); //add custom filters

		Page page = new Page<TreeInfo>(pageRequest, 0);

		ModelAndView result = toModelAndView(page, pageRequest);
		result.addObject("treeInfo", this.showExtLoadTree(request, response));
		result.setViewName("/tree/complex");
		return result;
	}

	/** 进入新增 */
	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TreeInfo treeInfo)
			throws Exception {
		return new ModelAndView("/tree/new", "treeInfo", treeInfo);
	}

	/** 显示 */
	@Override
	public ModelAndView show(@PathVariable java.lang.Long id) throws Exception {
		TreeInfo treeInfo = (TreeInfo) treeInfoManager.getById(id);
		return new ModelAndView("/tree/show", "treeInfo", treeInfo);
	}

	/** 编辑 */
	@Override
	public ModelAndView edit(@PathVariable java.lang.Long id) throws Exception {
		TreeInfo treeInfo = (TreeInfo) treeInfoManager.getById(id);
		return new ModelAndView("/tree/edit", "treeInfo", treeInfo);
	}

	/** 保存新增 */
	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TreeInfo treeInfo)
			throws Exception {
		treeInfoManager.save(treeInfo);
		return new ModelAndView(LIST_ACTION);
	}

	/** 保存更新 */
	@Override
	public ModelAndView update(@PathVariable java.lang.Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TreeInfo treeInfo = (TreeInfo) treeInfoManager.getById(id);
		bind(request, treeInfo);
		treeInfoManager.update(treeInfo);
		return new ModelAndView(LIST_ACTION);
	}

	/** 删除 */
	@Override
	public ModelAndView delete(@PathVariable java.lang.Long id) {
		treeInfoManager.removeById(id);
		return new ModelAndView(LIST_ACTION);
	}

	/** 批量删除 */
	@Override
	public ModelAndView batchDelete(java.lang.Long[] items) {
		for (int i = 0; i < items.length; i++) {
			treeInfoManager.removeById(items[i]);
		}
		return new ModelAndView(LIST_ACTION);
	}

	private String showExtLoadTree(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
		WebTreeDynamicNode rootNode = new WebTreeDynamicNode("进创集团", "org" + "001");
		rootNode.setSubTreeURL(RequestUtil.getUrl("/tree?_method=" + "loadExtSubOrgs&parentID=" + "001", pRequest));

		DefaultTreeModel treeModel = new DefaultTreeModel();
		treeModel.addRootNode(rootNode);
		TreeDirector director = new DefaultTreeDirector();
		director.setComparator(new DefaultNodeComparator());
		ExtTreeBuilder treeBuilder = new ExtLoadTreeBuilder();
		treeBuilder.init(pRequest);
		//treeBuilder.setTitle("请选择节点!");
		director.build(treeModel, treeBuilder);
		String treeScript = treeBuilder.getTreeScript();
		return treeScript;
	}

	public String loadExtSubOrgs(final HttpServletRequest pRequest, final HttpServletResponse pResponse)
			throws Exception {
		final String parentID = pRequest.getParameter("parentId");
		PageRequest<Map> pageRequest = newPageRequest(pRequest, DEFAULT_SORT_COLUMNS);

		//pageRequest.getFilters(); //add custom filters
		Page nodesPages = treeInfoManager.findByPageRequest(pageRequest);

		AbstractWebTreeModelCreator treeModelCreator = new AbstractWebTreeModelCreator() {
			@Override
			protected Node createNode(Object pUserData, UserDataUncoder pUncoder) {
				Leaf leaf = (Leaf) pUserData;
				WebTreeDynamicNode result = new WebTreeDynamicNode(leaf.getLeafName(), "org" + leaf.getLeafId());
				result.setSubTreeURL(getUrl("/servlet/xtreeServlet?_actionType=" + "loadExtSubOrgs&parentId="
						+ leaf.getLeafId()));

				result.setValue(leaf.getLeafId());
				return result;
			}
		};
		treeModelCreator.init(pRequest);

		TreeModel treeModel = treeModelCreator.create(nodesPages.getResult(), new UserDataUncoder() {

			public Object getParentID(Object arg) throws UncodeException {
				// TODO Auto-generated method stub
				return ((Leaf) arg).getLeafId();
			}

			public Object getID(Object arg) throws UncodeException {
				// TODO Auto-generated method stub
				return ((Leaf) arg).getLeafParentId();
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
		return null;
	}
}
