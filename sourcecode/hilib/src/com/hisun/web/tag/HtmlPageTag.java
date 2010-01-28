package com.hisun.web.tag;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class HtmlPageTag extends TagSupport {
    private static final String IMG_PTH = "image/";
    protected String txncode;
    protected String output;
    protected String action;
    protected PageData pageData;
    protected int width;
    protected int startPosition;
    protected int endPosition;
    protected int pageSize;
    protected int rowCount;
    protected int pageCount;
    protected int currPage;
    protected int currPlace;


    public HtmlPageTag() {

        this.txncode = "";


        this.output = "";


        this.action = "";


        this.pageData = null;


        this.width = 0;


        this.startPosition = 0;


        this.endPosition = 0;


        this.pageSize = 0;


        this.rowCount = 0;


        this.pageCount = 0;


        this.currPage = 1;


        this.currPlace = 3;

    }


    public int doStartTag() throws JspException {

        JspWriter writer = this.pageContext.getOut();

        try {

            init();

            drawList(writer);

        } catch (IOException ie) {

            throw new JspException(ie.toString());

        }

        return 0;
    }


    public void init() {

        if (this.pageData != null) {

            this.pageSize = this.pageData.getPageSize();

            this.rowCount = this.pageData.getRowCount();

            this.pageCount = this.pageData.getPageCount();

            this.currPage = this.pageData.getCurrPage();

            this.startPosition = this.pageData.getStartPosition();

            this.endPosition = this.pageData.getEndPosition();
        }

    }


    public void drawList(JspWriter writer) throws JspException, IOException {

        int startPage = 0;

        int endPage = 0;

        Page page = new Page();


        if ((this.pageCount <= Page.getGroupPages()) || (this.currPage <= this.currPlace)) {

            startPage = 1;

            endPage = (this.pageCount <= Page.getGroupPages()) ? this.pageCount : Page.getGroupPages();

        } else if (this.pageCount - this.currPage < Page.getGroupPages() - this.currPlace + 1) {

            startPage = this.pageCount - Page.getGroupPages() + 1;

            endPage = this.pageCount;

        } else {

            startPage = this.currPage - this.currPlace + 1;

            endPage = (this.pageCount <= startPage + Page.getGroupPages() - 1) ? this.pageCount : startPage + Page.getGroupPages() - 1;

        }


        writer.println("<script> MM_preloadImages('image/go.gif','image/go_gray.gif');</script>");

        writer.println("  <table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");

        writer.println("    <tr>");

        writer.println("      <td width=\"6%\">&nbsp;</td>");

        writer.println("      <td width=\"94%\" align=\"center\">共" + this.rowCount + "条 第" + this.currPage + "/" + this.pageCount + "页&nbsp;&nbsp;每页显示" + this.pageSize + "条&nbsp;&nbsp;");

        if (this.rowCount > 0) {

            if (this.currPage == 1) {

                writer.println("      <img src=\"image/first-02.gif\" alt=\"首页\" name=\"首页\" border=0 style=\"margin-top:5px\" align=absmiddle>");

                writer.println("      <img src=\"image/prev-02.gif\" alt=\"上一页\" name=\"上一页\" border=0 style=\"margin-top:5px\" align=absmiddle>");

            } else {

                writer.println("      <a href=\"#\" onmouseover=\"setStatusBar('转到首页');return true;\" onclick=\"javascript:doPageSubmit('" + this.action + "','" + this.txncode + "','" + this.output + "',1);return false\"><img src=\"" + "image/" + "first-01.gif\" alt=\"首页\" name=\"首页\" border=0 style=\"margin-top:5px\" align=absmiddle></a>");

                writer.println("      <a href=\"#\" onmouseover=\"setStatusBar('转到上一页');return true;\" onclick=\"javascript:doPageSubmit('" + this.action + "','" + this.txncode + "','" + this.output + "'," + (this.currPage - 1) + ");return false\"><img src=\"" + "image/" + "prev-01.gif\" alt=\"上一页\" name=\"上一页\" border=0 style=\"margin-top:5px\" align=absmiddle></a>");

            }

            for (int i = startPage; i <= endPage; ++i) {

                if (this.currPage == i) {

                    writer.println("<b>[" + i + "]</b>&nbsp;");

                } else {

                    writer.print("<a href=\"#\" onmouseover=\"setStatusBar('转到第 " + i + " 页');return true;\" onclick=\"javascript:doPageSubmit('" + this.action + "','" + this.txncode + "','" + this.output + "'," + i + ");return false\">");

                    writer.println("[" + i + "]</a>");

                    writer.print("&nbsp;");

                }

            }


            if (this.currPage == this.pageCount) {

                writer.println("      <img src=\"image/next-02.gif\" alt=\"下一页\" name=\"下一页\" border=0 style=\"margin-top:5px\" align=absmiddle>");

                writer.println("      <img src=\"image/last-02.gif\" alt=\"尾页\" name=\"尾页\" border=0 style=\"margin-top:5px\" align=absmiddle>");

            } else {

                writer.println("      <a href=\"#\" onmouseover=\"setStatusBar('转到下一页');return true;\" onclick=\"javascript:doPageSubmit('" + this.action + "','" + this.txncode + "','" + this.output + "'," + (this.currPage + 1) + ");return false\"><img src=\"" + "image/" + "next-01.gif\" alt=\"下一页\" name=\"下一页\" border=0 style=\"margin-top:5px\" align=absmiddle></a>");

                writer.println("      <a href=\"#\" onmouseover=\"setStatusBar('转到尾页');return true;\" onclick=\"javascript:doPageSubmit('" + this.action + "','" + this.txncode + "','" + this.output + "'," + this.pageCount + ");return false\"> <img src=\"" + "image/" + "last-01.gif\" alt=\"尾页\" name=\"尾页\" border=0 style=\"margin-top:5px\" align=absmiddle></a>");

            }

            writer.println("       转到&nbsp;");

            writer.println("        <input onkeydown=\"return doPageKeyHadler(this);\" maxlength=5 style=\"ime-mode:disabled;\" class=\"INPUT_TEXT\" name=\"page\" type=\"text\" style=\"width:48px\">");

            writer.println("      &nbsp;页");

            writer.println("      <a onmouseover=\"setStatusBar('转到指定页');return true;\" href=\"javascript:doGoPageSubmit('" + this.action + "','" + this.txncode + "','" + this.output + "')\"><img name=\"goPage\" src=\"" + "image/" + "go.gif\" border=0 align=absmiddle onMouseOver=\"MM_swapImage('goPage','','" + "image/" + "go_gray.gif', 1)\" style=\"margin-top:3px\" onMouseOut=\"MM_swapImgRestore()\"></a>");

            writer.println("      &nbsp;</td>");

        } else {

            writer.println("      <img src=\"image/first-02.gif\" alt=\"首页\" name=\"首页\" border=0 style=\"margin-top:5px\" align=absmiddle>");

            writer.println("      <img src=\"image/prev-02.gif\" alt=\"上一页\" name=\"上一页\" border=0 style=\"margin-top:5px\" align=absmiddle>");

            writer.println("      <img src=\"image/next-02.gif\" alt=\"下一页\" name=\"下一页\" border=0 style=\"margin-top:5px\" align=absmiddle>");

            writer.println("      <img src=\"image/last-02.gif\" alt=\"尾页\" name=\"尾页\" border=0 style=\"margin-top:5px\" align=absmiddle>");

            writer.println("       转到&nbsp;");

            writer.println("        <input name=\"page\" class=\"INPUT_TEXT\" type=\"text\" style=\"width:48px\" disabled>");

            writer.println("      &nbsp;页");

            writer.println("      <img name=\"goPage\" src=\"image/go.gif\" border=0 align=absmiddle onMouseOver=\"MM_swapImage('goPage','','image/go_gray.gif', 1)\" onMouseOut=\"MM_swapImgRestore()\">");

            writer.println("      &nbsp;</td>");

        }

        writer.println("    </tr>");

        writer.println("  </table>");


        writer.print("<input name=\"actions\" type=\"hidden\" value=\"" + this.action + "\">");

        writer.print("<input name=\"oldPage\" type=\"hidden\" value=\"" + this.currPage + "\">");

        writer.print("<input name=\"pageCount\" type=\"hidden\" value=\"" + this.pageCount + "\">");
    }


    public void release() {

        super.release();
    }


    public int getWidth() {

        return this.width;
    }


    public void setWidth(int width) {

        this.width = width;
    }


    public PageData getPageData() {

        return this.pageData;

    }


    public void setPageData(PageData pageData) {

        this.pageData = pageData;

    }


    public String getAction() {

        return this.action;

    }


    public void setAction(String action) {

        this.action = action;

    }


    public String getTxncode() {

        return this.txncode;

    }


    public void setTxncode(String txncode) {

        this.txncode = txncode;

    }


    public String getOutput() {

        return this.output;

    }


    public void setOutput(String output) {

        this.output = output;

    }

}