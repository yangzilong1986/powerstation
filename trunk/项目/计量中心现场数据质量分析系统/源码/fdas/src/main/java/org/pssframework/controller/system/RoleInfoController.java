package org.pssframework.controller.system;

import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_IS_SUCC;
import static org.pssframework.support.system.SystemConst.CONTROLLER_AJAX_MESSAGE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_EDIT;
import static org.pssframework.support.system.SystemConst.CONTROLLER_METHOD_TYPE_NEW;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_FAIL;
import static org.pssframework.support.system.SystemConst.MSG_CREATED_SUCCESS;
import static org.pssframework.support.system.SystemConst.MSG_DELETE_SUCCESS;
import static org.pssframework.support.system.SystemConst.MSG_UPDATE_FAIL;
import static org.pssframework.support.system.SystemConst.MSG_UPDATE_SUCCESS;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.pssframework.base.BaseQuery;
import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.system.AuthorityInfo;
import org.pssframework.model.system.RoleInfo;
import org.pssframework.service.system.AuthorityInfoManager;
import org.pssframework.service.system.RoleInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.google.common.collect.Lists;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system/role")
public class RoleInfoController extends BaseRestSpringController<RoleInfo, Long> {
    private static final String VIEW_MANAGER = "/system/roleManagerFrame";
    private static final String VIEW_QUERY = "/system/roleList";
    private static final String VIEW_DETAIL = "/system/roleDetail";
    private static final String VIEW_EDIT = "/system/editRolePage";

    // 默认多列排序,example: rolename desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = null;

    @Autowired
    private RoleInfoManager roleInfoManager;

    @Autowired
    private AuthorityInfoManager authorityInfoManager;

    /** 新建 */
    @RequestMapping(value = "/new")
    public String _new(ModelMap result, RoleInfo roleInfo, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        result.addAttribute("role", roleInfo);
        result.addAttribute("authorityInfoList", getAllAuthority());
        result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_NEW);
        return VIEW_EDIT;
    }

    /** 保存新增,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
    @RequestMapping(method = RequestMethod.POST)
    public String create(ModelMap model, @Valid RoleInfo roleInfo, BindingResult errors, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        this.logger.debug("role.{}", "create");
        boolean isSucc = true;
        String msg = MSG_CREATED_SUCCESS;
        Long roleId = 0L;
        try {
            this.roleInfoManager.saveOrUpdate(roleInfo);
            roleId = roleInfo.getRoleId();
        }
        catch(Exception e) {
            isSucc = false;
            msg = MSG_CREATED_FAIL;
            logger.debug(e.getMessage());
        }

        model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg).addAttribute(
                "roleId", roleId);

        return VIEW_DETAIL;
    }

    /** 删除 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap model, @PathVariable Long id) {
        this.logger.debug("role.{},{}", "delete", id);
        boolean isSucc = true;
        String msg = MSG_DELETE_SUCCESS;
        try {
            this.roleInfoManager.removeById(id);
            //Flash.current().success(msg);
        }
        catch(Exception e) {
            isSucc = false;
            msg = e.getMessage();
            logger.error(e.getMessage());
            //Flash.current().error(msg);

        }
        model.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
        return VIEW_QUERY;
    }

    @RequestMapping(value = "detail")
    public ModelAndView detail(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
        modelAndView.setViewName(VIEW_DETAIL);
        return modelAndView;
    }

    /** 编辑 */
    @RequestMapping(value = "/{id}/edit")
    public String edit(ModelMap result, @PathVariable Long id) throws Exception {
        RoleInfo roleInfo = this.roleInfoManager.getById(id);
        result.addAttribute("role", roleInfo);
        result.addAttribute("authorityInfoList", getAllAuthority());
        result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);
        return VIEW_EDIT;
    }

    private List<AuthorityInfo> getAllAuthority() {
        List<AuthorityInfo> authorityInfos = Lists.newArrayList();
        authorityInfos = authorityInfoManager.findAll();
        return authorityInfos;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "list")
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,
            RoleInfo roleInfo) {
        BaseQuery baseQuery = new BaseQuery();
        PageRequest<Map> pageRequest = bindPageRequest(request, baseQuery, DEFAULT_SORT_COLUMNS);
        Page page = this.roleInfoManager.findByPageRequest(pageRequest);//获取数据模型
        modelAndView.addObject("page", page);
        modelAndView.addObject("pageRequest", pageRequest);
        modelAndView.setViewName(VIEW_QUERY);
        return modelAndView;
    }

    @RequestMapping(value = "/manager")
    public ModelAndView manager(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
        modelAndView.setViewName(VIEW_MANAGER);
        return modelAndView;
    }

    /**显示
     * 
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(ModelMap result, @PathVariable Long id) throws Exception {
        RoleInfo roleInfo = this.roleInfoManager.getById(id);
        result.addAttribute("role", roleInfo);
        result.addAttribute(CONTROLLER_METHOD_TYPE, CONTROLLER_METHOD_TYPE_EDIT);
        return VIEW_DETAIL;
    }

    /** 保存更新,@Valid标注spirng在绑定对象时自动为我们验证对象属性并存放errors在BindingResult  */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String update(ModelMap modelMap, @Valid RoleInfo role, BindingResult errors, @PathVariable Long id,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean isSucc = true;
        String msg = MSG_UPDATE_SUCCESS;

        try {
            logger.debug("bind roleInfo {} from request", role);
            RoleInfo roleInfoDb = this.roleInfoManager.getById(id);
            bind(request, roleInfoDb);
            this.roleInfoManager.saveOrUpdate(roleInfoDb);
        }
        catch(Exception e) {
            this.logger.error(e.getMessage());
            isSucc = false;
            msg = MSG_UPDATE_FAIL;
        }
        modelMap.addAttribute(CONTROLLER_AJAX_IS_SUCC, isSucc).addAttribute(CONTROLLER_AJAX_MESSAGE, msg);
        return VIEW_EDIT;
    }

}
