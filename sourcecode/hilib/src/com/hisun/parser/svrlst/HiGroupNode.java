package com.hisun.parser.svrlst;


import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;


public class HiGroupNode {
    private ArrayList childs;
    private String name;
    private String desc;
    private String status;


    public HiGroupNode() {

        this.childs = new ArrayList();


        this.status = "0";
    }


    public void setStatus(String status) {

        this.status = status;

    }


    public String getStatus() {

        return this.status;
    }


    public void setStopStatus() {

        this.status = "0";

    }


    public void setErrorStatus() {

        this.status = "1";

    }


    public void setStartStatus() {

        this.status = "2";

    }


    public void setPauseStatus() {

        this.status = "3";

    }


    public String getStatusMsg() {

        if (this.status.equals("0")) return "关闭";

        if (this.status.equals("1")) return "失败";

        if (this.status.equals("2")) return "启动";

        if (this.status.equals("3")) {

            return "暂停";

        }

        return "关闭";

    }


    public void addServer(Object o) {

        this.childs.add(o);

        if (o instanceof HiServerNode) {

            HiServerNode node = (HiServerNode) o;

            node.setGroupNode(this);

        }

    }


    public int size() {

        return this.childs.size();

    }


    public void clear() {

        this.childs.clear();

    }


    public HiServerNode getServer(int idx) {

        return ((HiServerNode) this.childs.get(idx));

    }


    public HiServerNode getServer(String name) {

        for (int i = 0; i < size(); ++i) {

            if (StringUtils.equalsIgnoreCase(getServer(i).getName(), name)) return getServer(i);

        }

        return null;

    }


    public String getDesc() {

        if (this.desc == null) return this.name;

        return this.desc;

    }


    public void setDesc(String desc) {

        this.desc = desc;

    }


    public String getName() {

        return this.name;

    }


    public void setName(String name) {

        this.name = name;

    }


    public String toString() {

        return this.name + ":" + this.desc;

    }


    public String toHTML() {

        StringBuffer buffer = new StringBuffer();

        buffer.append("<table width = '80%' border='1' cellspacing='0' bordercolor='#003399'> \n");


        buffer.append("<tr bgcolor='#CCFFFF'> <td> <a href='javascript:selectAll()'><font size='2'>全选</font> </a> <a href='javascript:clearAll()'><font size='2'>清空</font> </a>服务名 </td>  <td> 状态 </td>  <td> 操作时间 </td> <td> 描述 </td> </tr>\n");


        for (int i = 0; i < size(); ++i) {

            buffer.append(getServer(i).toHTML());

        }

        buffer.append("</table> \n");

        return buffer.toString();

    }

}