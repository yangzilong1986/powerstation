package com.hisun.teller.action;


import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.web.action.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class SetRoleAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    private static final String IMAGE0 = "books_close.gif";
    private static final String IMAGE1 = "tombs.gif";
    private static final String IMAGE2 = "tombs.gif";


    protected boolean endProcess(HttpServletRequest request, HiETF rspetf, Logger _log) {

        Set set = getSelectedItem(rspetf);

        createTreeXmlFile("pageprv.xml", set, request);


        return true;

    }


    private void createTreeXmlFile(String filename, Set set, HttpServletRequest request) {

        Element item;

        Iterator i;

        if (set == null) {

            set = new HashSet();

        }


        Map pages = (Map) ServletActionContext.getContext().getApplication().get("pages");

        Element menus = (Element) ServletActionContext.getContext().getApplication().get("menu");


        Iterator menuIter = menus.elementIterator();


        Element el = DocumentHelper.createElement("tree");

        el.addAttribute("id", "0");


        Element tree = el.addElement("item");

        tree.addAttribute("text", "root");

        tree.addAttribute("id", "1");

        tree.addAttribute("open", "1");

        tree.addAttribute("im0", "books_close.gif");

        tree.addAttribute("im1", "tombs.gif");

        tree.addAttribute("im2", "tombs.gif");

        while (menuIter.hasNext()) {

            Element e = (Element) menuIter.next();


            String name = e.attributeValue("name");

            String id = name;


            item = tree.addElement("item");

            item.addAttribute("text", name);

            item.addAttribute("id", id);


            item.addAttribute("im0", "books_close.gif");

            item.addAttribute("im1", "tombs.gif");

            item.addAttribute("im2", "tombs.gif");


            for (i = e.elementIterator(); i.hasNext();) {

                Iterator it;

                Element child = (Element) i.next();

                String c_id = child.attributeValue("id");


                Element page = (Element) pages.get(c_id);

                if (page == null) {

                    continue;

                }

                String c_name = page.attributeValue("name");

                Element second = item.addElement("item");

                second.addAttribute("text", c_name);

                second.addAttribute("im0", "books_close.gif");

                second.addAttribute("im1", "tombs.gif");

                second.addAttribute("im2", "tombs.gif");


                List list = page.selectNodes("Action");

                if (list.size() > 0) {

                    second.addAttribute("id", "fod_" + c_id);

                    for (it = list.iterator(); i.hasNext();) {

                        Element action = (Element) it.next();

                        String actnam = action.attributeValue("name");

                        String actid = action.attributeValue("id");


                        Element actitem = second.addElement("item");

                        actitem.addAttribute("text", actnam);

                        actitem.addAttribute("id", "act_" + c_id + actid);


                        actitem.addAttribute("im0", "books_close.gif");

                        actitem.addAttribute("im1", "tombs.gif");

                        actitem.addAttribute("im2", "tombs.gif");


                        if (set.contains(actid + ":" + c_id)) {

                            actitem.addAttribute("checked", "1");

                        } else {

                            Attribute attr = actitem.attribute("checked");

                            if (attr != null) actitem.remove(attr);

                        }

                    }

                } else {

                    second.addAttribute("id", "pag_" + c_id);


                    if (set.contains(c_id)) {

                        second.addAttribute("checked", "1");

                    } else {

                        Attribute attr = second.attribute("checked");

                        if (attr != null) {

                            second.remove(attr);

                        }

                    }

                }

            }

        }


        HttpServletResponse response = ServletActionContext.getResponse();

        response.setContentType("text/xml; charset=gb2312");

        try {

            response.getWriter().write("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + el.asXML());

            response.getWriter().flush();

        } catch (IOException e1) {

            e1.printStackTrace();

        }

    }


    private Set getSelectedItem(HiETF etf) {

        if (etf == null) {

            return null;

        }

        Set set = new HashSet();

        String[] nodes = StringUtils.split("GROUP.TXN_CD", ".");


        if ((nodes == null) || (nodes.length < 2)) {

            return null;

        }

        List lst = etf.getChildFuzzyEnd(nodes[0]);

        for (Iterator i = lst.iterator(); i.hasNext();) {

            HiETF child = (HiETF) i.next();

            String pageid = child.getChildValue("PAG_ID");

            String action = child.getChildValue(nodes[1]);

            if ((action != null) && (action.length() > 0)) {

                set.add(action + ":" + pageid);

            } else {

                set.add(pageid);

            }


        }


        return set;

    }

}