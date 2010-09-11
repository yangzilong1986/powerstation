/**
 * 
 */
package org.pssframework.controller.tree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.pssframework.model.system.UserInfo;
import org.pssframework.model.tree.LeafInfo;
import org.pssframework.security.OperatorDetails;
import org.pssframework.service.system.UserInfoManager;
import org.pssframework.service.tree.LeafInfoManager;
import org.pssframework.support.system.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

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

	private final String PARENT_ID = "leafParentId";

	private final String PARENT_TYPE = "leafParentType";

	private final static String ROOT_IMG = "/style/default/bgcolor/green-stategrid/img/tree_icon03.gif";
	private final static String ORG_IMG = "/style/default/bgcolor/green-stategrid/img/tree_icon03.gif";

	private final static String TG_IMG = "/style/default/bgcolor/green-stategrid/img/tree_icon08.gif";

	private OperatorDetails user;

	private UserInfo userInfo;

	@Autowired
	private UserInfoManager userInfoManager;

	/** 
	 * 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性
	 **/
	public void setLeafInfoManager(LeafInfoManager manager) {
		this.leafInfoManager = manager;
	}

	/** 列表 */
	@RequestMapping
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, LeafInfo leafInfo) {
		ModelAndView result = new ModelAndView();
		result.addObject("leafInfo", this.showExtLoadTree(request, response));
		result.setViewName("/tree/ExtLoadTree");
		return result;
	}

	/** 进入新增 */
	@RequestMapping(value = "/new")
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, LeafInfo leafInfo)
			throws Exception {
		return new ModelAndView("/tree/new", "leafInfo", leafInfo);
	}

	/** 显示 */
	@ResponseBody
	@RequestMapping(value = "/{id}")
	public String show(@PathVariable java.lang.Long id, LeafInfo leafInfo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return loadExtSubsTree(request, leafInfo);
	}

	/** 编辑 */
	@RequestMapping(value = "/{id}/edit")
	public String edit(ModelMap model, @PathVariable java.lang.Long id) throws Exception {
		LeafInfo leafInfo = (LeafInfo) leafInfoManager.getById(id);
		model.addAttribute("leafInfo", leafInfo);
		return "/tree/edit";
	}

	/** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, LeafInfo leafInfo)
			throws Exception {
		leafInfoManager.save(leafInfo);
		return new ModelAndView(LIST_ACTION);
	}

	/** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(ModelMap model, @PathVariable java.lang.Long id, @Valid LeafInfo leafInfo,
			BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (errors.hasErrors())
			return "/tree/edit";
		leafInfoManager.update(leafInfo);
		return (LIST_ACTION);
	}

	private String showExtLoadTree(final HttpServletRequest pRequest, final HttpServletResponse pResponse) {
		Long parentID = 0L;

		user = SpringSecurityUtils.getCurrentUser();

		userInfo = userInfoManager.findUserByLoginName(user.getUsername());

		OrgInfo orginfo = userInfo.getOrgInfo();

		if (userInfo.getEmpNo() != 0L) {
			if (orginfo != null) {
				parentID = orginfo.getOrgId();
			}
		} else {

		}

		WebTreeDynamicNode rootNode = new WebTreeDynamicNode(orginfo.getOrgName(), SystemConst.TREE_ORG + "_"
				+ parentID, new UserDataUncoder() {

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
				+ PARENT_TYPE + "=" + SystemConst.TREE_ORG, pRequest));

		rootNode.setIcon(RequestUtil.getUrl(ROOT_IMG, pRequest));

		rootNode.setOpenIcon(RequestUtil.getUrl(ROOT_IMG, pRequest));

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
	public String loadExtSubsTree(final HttpServletRequest pRequest, LeafInfo leafInfo) throws Exception {

		final Long parentID = leafInfo.getLeafParentId();
		final String parentTYPE = leafInfo.getLeafParentType();

		PageRequest pageRequest = bindPageRequest(pRequest, leafInfo, DEFAULT_SORT_COLUMNS);

		Page nodesPages = leafInfoManager.findByPageRequest(pageRequest);

		AbstractWebTreeModelCreator treeModelCreator = new AbstractWebTreeModelCreator() {
			@Override
			protected Node createNode(Object pUserData, UserDataUncoder pUncoder) {
				LeafInfo leaf = (LeafInfo) pUserData;
				WebTreeDynamicNode result = new WebTreeDynamicNode(leaf.getLeafName(), leaf.getLeafType() + "_"
						+ leaf.getLeafId());
				result.setSubTreeURL(getUrl("/tree/" + leaf.getLeafId() + "?" + PARENT_ID + "=" + leaf.getLeafId()
						+ "&" + PARENT_TYPE + "=" + leaf.getLeafType() + "&random=" + Math.random()));

				result.setValue(String.valueOf(leaf.getLeafId()));
				result.setTip(leaf.getLeafName());

				//台区
				if (SystemConst.TREE_TG.equals(leaf.getLeafType())) {
					result.setIcon(getUrl(TG_IMG));
					result.setAction("javascript:showTg(" + leaf.getLeafId() + ")");
				} else if (SystemConst.TREE_ORG.equals(leaf.getLeafType())) {
					result.setIcon(getUrl(ORG_IMG));
				}

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

		return treeScript;

	}

}
