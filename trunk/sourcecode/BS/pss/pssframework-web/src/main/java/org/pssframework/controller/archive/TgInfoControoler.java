/**
 * 
 */
package org.pssframework.controller.archive;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pssframework.controller.BaseRestSpringController;
import org.pssframework.model.archive.TgInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Baocj
 *
 */
@Controller
@RequestMapping("/archive/tginfo")
public class TgInfoControoler extends BaseRestSpringController<TgInfo, java.lang.Long> {
	@Override
	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, TgInfo model) throws Exception {
		// TODO Auto-generated method stub
		return super._new(request, response, model);
	}

	@Override
	public ModelAndView delete(Long id) {
		// TODO Auto-generated method stub
		return super.delete(id);
	}

	@Override
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, TgInfo model) throws Exception {
		// TODO Auto-generated method stub
		return super.create(request, response, model);
	}

	@Override
	public ModelAndView edit(Long id) throws Exception {
		// TODO Auto-generated method stub
		return super.edit(id);
	}

	@Override
	public ModelAndView update(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return super.update(id, request, response);
	}

	@Override
	public ModelAndView show(Long id) throws Exception {
		// TODO Auto-generated method stub
		return super.show(id);
	}
}
