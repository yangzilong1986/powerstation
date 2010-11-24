/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.codec.protocol.gb.gb376;

import java.util.Date;
import pep.codec.utils.BcdDataBuffer;

/**
 *
 * @author luxiaochung
 */
public abstract class PmPacket376EventBase {
    protected int erc;
    protected Date eventTime;
    protected String eventDetail;

    protected abstract void DecodeEventDetail(BcdDataBuffer eventData, int len);

    public Date getEventTime(){
        return this.eventTime;
    }

    public int GetEventCode(){
        return this.erc;
    }
    
    public String getEventDetail(){
        return this.eventDetail;
    }

    public String getEventCodeString() {
        switch (this.erc) {
            case 1:
                return "数据初始化和版本变更";
            case 2:
                return "参数丢失";
            case 3:
                return "参数变更";
            case 4:
                return "状态量变位";
            case 5:
                return "遥控跳闸";
            case 6:
                return "功控跳闸";
            case 7:
                return "电控跳闸";
            case 8:
                return "电能表参数变更";
            case 9:
                return "电流回路异常";
            case 10:
                return "电压回路异常";
            case 11:
                return "相序异常";
            case 12:
                return "电能表时间超差";
            case 13:
                return "电表故障信息";
            case 14:
                return "终端停/上电";
            case 15:
                return "谐波越限告警";
            case 16:
                return "直流模拟量越限";
            case 17:
                return "电压/电流不平衡越限";
            case 18:
                return "电容器投切自锁";
            case 19:
                return "购电参数设置";
            case 20:
                return "消息认证错误";
            case 21:
                return "终端故障";
            case 22:
                return "有功总电能量差动越限";
            case 23:
                return "电控告警事件";
            case 24:
                return "电压越限";
            case 25:
                return "电流越限";
            case 26:
                return "视在功率越限";
            case 27:
                return "电能表示度下降";
            case 28:
                return "电能量超差";
            case 29:
                return "电能表飞走";
            case 30:
                return "电能表停走";
            case 31:
                return "终端485抄表失败";
            case 32:
                return "终端与主站通信流量超门限";
            case 33:
                return "电能表运行状态字变位";
            case 34:
                return "CT异常";
            case 35:
                return "发现未知电表";
            case 36:
                return "电表未知异常";
            default:
                return "未知异常(" + new Integer(this.erc).toString()+")";
        }
    }

    @Override
    public String toString() {
        return  "事件代码="+new Integer(this.erc).toString()+
                " 事件时间="+this.eventTime.toString()+
                " 事件描述"+ getEventDetail();
    }
}
