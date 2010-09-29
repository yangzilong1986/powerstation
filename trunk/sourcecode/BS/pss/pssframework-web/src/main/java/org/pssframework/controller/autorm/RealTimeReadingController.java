package org.pssframework.controller.autorm;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.pssframework.controller.BaseSpringController;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.query.autorm.ReadTimReadingQuery;
import org.pssframework.service.atuorm.RealTimeReadingManager;
import org.pssframework.service.system.OrgInfoManager;
import org.pssframework.util.DataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pep.bp.realinterface.mto.MTO_376;
import pep.bp.realinterface.mto.MessageTranObject;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * 数据召测
 * 
 * @author Administrator
 */
@Controller
@RequestMapping("/autorm/realTimeReading")
public class RealTimeReadingController extends BaseSpringController {
    // 默认多列排序,example: username desc,createTime asc
    protected static final String DEFAULT_SORT_COLUMNS = null;

    private static final String VIEW = "/autorm/realTimeReading";

    @Autowired
    private OrgInfoManager orgInfoManager;

    @Autowired
    private RealTimeReadingManager realTimeReadingManager;

    @SuppressWarnings("unchecked")
    @RequestMapping
    public ModelAndView list(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
        ReadTimReadingQuery timReadingQuery = new org.pssframework.query.autorm.ReadTimReadingQuery();
        PageRequest<Map> pageRequest = bindPageRequest(request, timReadingQuery, DEFAULT_SORT_COLUMNS);
        Page page = this.realTimeReadingManager.findByPageRequest(pageRequest); //获取数据模型
        modelAndView.setViewName(VIEW);
        Map mapRequest = new LinkedHashMap();
        getInitOption(modelAndView, mapRequest);
        modelAndView.addObject("page", page);
        modelAndView.addObject("pageRequest", pageRequest);

        return modelAndView;
    }

    /**
     * 下拉框
     *
     * @param model
     * @param mapRequest
     */
    private void getInitOption(ModelAndView model, Map<String, ?> mapRequest) {
        model.addObject("orglist", this.getOrgOptions(mapRequest));
    }

    /**
     * 
     * @param mapRequest
     * @return
     */
    private List<OrgInfo> getOrgOptions(Map<String, ?> mapRequest) {
        return this.orgInfoManager.findByPageRequest(mapRequest);
    }

    /**
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/down")
    public ModelAndView down(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String jsonString = request.getParameter("dto");
        ObjectMapper objectMapper = new ObjectMapper();
        MessageTranObject mto_376 = null;
        try {
            mto_376 = objectMapper.readValue(jsonString, MTO_376.class);
        }
        catch(JsonParseException e) {
            logger.debug(e.getMessage());
        }
        catch(JsonMappingException e) {
            logger.debug(e.getMessage());
        }
        catch(IOException e) {
            logger.debug(e.getMessage());
        }

        long collectId = this.realTimeReadingManager.send(mto_376);
        logger.info("collectId : " + collectId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("collectId", collectId);
        return modelAndView;
    }

    /**
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/up")
    public ModelAndView up(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long collectionId = Long.parseLong(request.getParameter("collectId"));
        Map<String, Map<String, String>> mto = realTimeReadingManager.getReturnByRRTD(new Long[] { collectionId });
        logger.info(mto.toString());
        ObjectMapper holder = new ObjectMapper();
        JSONObject jsonObject = DataConverter.map2json(mto);
        holder.writeValue(response.getOutputStream(), jsonObject);
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }
}
