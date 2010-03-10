package peis.interfaces.hicollect;

/**
 * 表对象（广东集抄）
 * @author Zhangyu
 * @version 1.0
 * Create Date : 20091012
 */
public class ConcMeter {
    private String meterAddr;       //表地址
    private int meterSn;            //表序号
    private int forwardCnt;         //中继级数
    private String forwardMeter1;   //中继表1
    private String forwardMeter2;   //中继表2
    private String forwardMeter3;   //中继表3
    private String forwardMeter4;   //中继表4
    
    public ConcMeter() {
    }

    public String getMeterAddr() {
        return meterAddr;
    }

    public void setMeterAddr(String meterAddr) {
        this.meterAddr = meterAddr;
    }

    public int getMeterSn() {
        return meterSn;
    }

    public void setMeterSn(int meterSn) {
        this.meterSn = meterSn;
    }

    public int getForwardCnt() {
        return forwardCnt;
    }

    public void setForwardCnt(int forwardCnt) {
        this.forwardCnt = forwardCnt;
    }

    public String getForwardMeter1() {
        return forwardMeter1;
    }

    public void setForwardMeter1(String forwardMeter1) {
        this.forwardMeter1 = forwardMeter1;
    }

    public String getForwardMeter2() {
        return forwardMeter2;
    }

    public void setForwardMeter2(String forwardMeter2) {
        this.forwardMeter2 = forwardMeter2;
    }

    public String getForwardMeter3() {
        return forwardMeter3;
    }

    public void setForwardMeter3(String forwardMeter3) {
        this.forwardMeter3 = forwardMeter3;
    }

    public String getForwardMeter4() {
        return forwardMeter4;
    }

    public void setForwardMeter4(String forwardMeter4) {
        this.forwardMeter4 = forwardMeter4;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + forwardCnt;
        result = prime * result + ((forwardMeter1 == null) ? 0 : forwardMeter1.hashCode());
        result = prime * result + ((forwardMeter2 == null) ? 0 : forwardMeter2.hashCode());
        result = prime * result + ((forwardMeter3 == null) ? 0 : forwardMeter3.hashCode());
        result = prime * result + ((forwardMeter4 == null) ? 0 : forwardMeter4.hashCode());
        result = prime * result + ((meterAddr == null) ? 0 : meterAddr.hashCode());
        result = prime * result + meterSn;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        ConcMeter other = (ConcMeter) obj;
        if(forwardCnt != other.forwardCnt)
            return false;
        if(forwardMeter1 == null) {
            if(other.forwardMeter1 != null)
                return false;
        }
        else if(!forwardMeter1.equals(other.forwardMeter1))
            return false;
        if(forwardMeter2 == null) {
            if(other.forwardMeter2 != null)
                return false;
        }
        else if(!forwardMeter2.equals(other.forwardMeter2))
            return false;
        if(forwardMeter3 == null) {
            if(other.forwardMeter3 != null)
                return false;
        }
        else if(!forwardMeter3.equals(other.forwardMeter3))
            return false;
        if(forwardMeter4 == null) {
            if(other.forwardMeter4 != null)
                return false;
        }
        else if(!forwardMeter4.equals(other.forwardMeter4))
            return false;
        if(meterAddr == null) {
            if(other.meterAddr != null)
                return false;
        }
        else if(!meterAddr.equals(other.meterAddr))
            return false;
        if(meterSn != other.meterSn)
            return false;
        return true;
    }
}
