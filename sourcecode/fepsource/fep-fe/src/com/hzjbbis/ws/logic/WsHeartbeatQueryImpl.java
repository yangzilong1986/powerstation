package com.hzjbbis.ws.logic;

import com.hzjbbis.fk.fe.filecache.HeartbeatPersist;
import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;
import com.hzjbbis.fk.utils.HexDump;
import org.apache.log4j.Logger;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebService(endpointInterface = "com.hzjbbis.ws.logic.WsHeartbeatQuery")
public class WsHeartbeatQueryImpl implements WsHeartbeatQuery {
    private static final Logger log = Logger.getLogger(WsHeartbeatQueryImpl.class);

    public int heartCount(int rtua) {
        try {
            ComRtu rtu = RtuManage.getInstance().getComRtuInCache(rtua);
            return ((rtu == null) ? -1 : rtu.getHeartbeatCount());
        } catch (Exception ex) {
            log.error("heartCount,error:" + ex.getLocalizedMessage(), ex);
        }
        return -1;
    }

    public long lastHeartbeatTime(int rtua) {
        ComRtu rtu = RtuManage.getInstance().getComRtuInCache(rtua);
        return ((rtu == null) ? 0L : rtu.getLastHeartbeat());
    }

    public int totalRtuWithHeartByA1(byte a1) {
        int sum = 0;
        try {
            List list = new ArrayList(RtuManage.getInstance().getAllComRtu());
            for (ComRtu rtu : list) {
                int rtua = rtu.getRtua() & 0xFF000000;
                int ia1 = a1 << 24 & 0xFF000000;
                if ((rtua == ia1) && (rtu.getHeartbeatCount() > 0)) ++sum;
            }
        } catch (Exception ex) {
            log.error("totalRtuWithHeartByA1:a1=" + HexDump.toHex(a1) + ",error:" + ex.getLocalizedMessage(), ex);
            return (sum = -1);
        }
        return sum;
    }

    public int totalRtuWithHeartByA1Time(byte a1, Date beginTime) {
        int sum = 0;
        try {
            List list = new ArrayList(RtuManage.getInstance().getAllComRtu());
            for (ComRtu rtu : list) {
                int rtua = rtu.getRtua() & 0xFF000000;
                int ia1 = a1 << 24 & 0xFF000000;
                if (rtu.getLastHeartbeatTime() == null) continue;
                if ((rtua == ia1) && (rtu.getHeartbeatCount() > 0) && (beginTime.before(rtu.getLastHeartbeatTime())))
                    ++sum;
            }
        } catch (Exception ex) {
            log.error("totalRtuWithHeartByA1Time:a1=" + HexDump.toHex(a1) + ",error:" + ex.getLocalizedMessage(), ex);
            return (sum = -1);
        }
        return sum;
    }

    public String queryHeartbeatInfo(int rtua) {
        return HeartbeatPersist.getInstance().queryHeartbeatInfo(rtua);
    }

    public String queryHeartbeatInfoByDate(int rtua, int date) {
        return HeartbeatPersist.getInstance().queryHeartbeatInfo(rtua, date);
    }
}