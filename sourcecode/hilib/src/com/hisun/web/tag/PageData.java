package com.hisun.web.tag;


import java.util.ArrayList;
import java.util.List;


public class PageData {
    public static final PageData DEFAULT = new PageData();

    protected int pageSize = 40;

    protected int rowCount = 0;

    protected int pageCount = 0;

    protected int currPage = 1;

    protected int startPosition = 0;

    protected int endPosition = 0;
    protected List list;
    protected Object appendix;
    protected int currGroup = 1;
    protected int oldGroup = 1;

    protected int groupPage = 0;


    public List getList() {

        return this.list;

    }


    public int getPageSize() {

        return this.pageSize;

    }


    public int getRowCount() {

        return this.rowCount;

    }


    public int getPageCount() {

        return this.pageCount;

    }


    public int getCurrPage() {

        return this.currPage;

    }


    public int getStartPosition() {

        return this.startPosition;

    }


    public int getEndPosition() {

        return this.endPosition;

    }


    public Object getAppendix() {

        return this.appendix;

    }


    public void setAppendix(Object appendix) {

        this.appendix = appendix;

    }


    public int getCurrGroup() {

        return this.currGroup;

    }


    public int getOldGroup() {

        return this.oldGroup;

    }


    public PageData(int currPage, int pageSize, int rowCount, List list) {

        this.currPage = currPage;

        this.pageSize = pageSize;

        this.rowCount = rowCount;

        this.list = list;

        init();

    }


    public PageData(Page page, List list) {

        this.currPage = page.getPage();

        this.pageSize = page.getPageSize();

        this.rowCount = page.getRowCount();

        this.list = list;

        init();

    }


    public PageData(int currPage, int pageSize, List list) {

        this.pageSize = pageSize;

        this.currPage = currPage;

        this.list = setData(list);

    }


    public PageData(int currPage, int pageSize, List tempList, int groupPage, int oldGroup, int currGroup) {

        this.currPage = currPage;

        this.pageSize = pageSize;

        this.oldGroup = oldGroup;

        this.currGroup = currGroup;

        this.rowCount = tempList.size();

        this.groupPage = groupPage;

        int startLine = 0;

        if (groupPage > 0) startLine = (currPage - 1) % groupPage * pageSize;

        int endLine = startLine + pageSize;

        int len = tempList.size();

        if (endLine > len) endLine = len;

        List dataList = new ArrayList();

        for (int i = startLine; i < endLine; ++i) {

            dataList.add(tempList.get(i));

        }


        if ((startLine >= len) && (len > 10)) {

            for (i = len - 10; i < len; ++i) {

                dataList.add(tempList.get(i));

            }

            this.currPage -= 1;

        }

        this.list = dataList;


        if (currPage <= 0) currPage = 1;

        int start = (currPage - 1) * pageSize;

        int end = start + pageSize;

        if (end > this.rowCount + (currGroup - 1) * pageSize * groupPage) {

            end = this.rowCount + (currGroup - 1) * pageSize * groupPage;

        }

        if (this.rowCount != 0) {

            this.startPosition = (start + 1);

            this.endPosition = end;

        }

    }


    private List setData(List list) {

        this.rowCount = list.size();

        this.pageCount = ((this.rowCount % this.pageSize == 0) ? this.rowCount / this.pageSize : this.rowCount / this.pageSize + 1);

        if (this.currPage <= 0) this.currPage = 1;

        if ((this.pageCount > 0) && (this.currPage > this.pageCount)) this.currPage = this.pageCount;

        int absolute = (this.currPage - 1) * this.pageSize;

        int end = absolute + this.pageSize;

        if (end > this.rowCount) {

            end = this.rowCount;

        }

        this.startPosition = (absolute + 1);

        this.endPosition = end;

        return list.subList(absolute, end);

    }


    public PageData() {

        this.list = new ArrayList();

    }


    public String toString() {

        StringBuffer sb = new StringBuffer();

        sb.append("pageSize = ").append(this.pageSize).append(" ; \r\n");

        sb.append("rowCount = ").append(this.rowCount).append(" ; \r\n");

        sb.append("pageCount = ").append(this.pageCount).append(" ; \r\n");

        sb.append("currPage = ").append(this.currPage).append(" ; \r\n");

        sb.append("startPosition = ").append(this.startPosition).append(" ; \r\n");

        sb.append("endPosition = ").append(this.endPosition).append(" ; \r\n");

        sb.append("list = \r\n").append(this.list).append(" ; \r\n");

        return sb.toString();

    }


    private void init() {

        if (this.rowCount != 0)

            this.pageCount = ((this.rowCount % this.pageSize == 0) ? this.rowCount / this.pageSize : this.rowCount / this.pageSize + 1);

        if (this.currPage <= 0) this.currPage = 1;

        if (this.currPage > this.pageCount) this.currPage = this.pageCount;

        int start = (this.currPage - 1) * this.pageSize;

        int end = start + this.pageSize;

        if (end > this.rowCount) {

            end = this.rowCount;

        }

        if (this.rowCount != 0) {

            this.startPosition = (start + 1);

            this.endPosition = end;

        }

    }

}