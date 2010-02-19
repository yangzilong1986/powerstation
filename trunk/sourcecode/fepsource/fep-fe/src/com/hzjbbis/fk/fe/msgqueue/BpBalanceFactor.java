package com.hzjbbis.fk.fe.msgqueue;

import com.hzjbbis.fk.model.ComRtu;
import com.hzjbbis.fk.model.RtuManage;

import java.util.*;

public class BpBalanceFactor {
    private static final BpBalanceFactor instance = new BpBalanceFactor();

    private final Map<Byte, DistrictFactor> factors = new HashMap();

    public static final BpBalanceFactor getInstance() {
        return instance;
    }

    public void travelRtus() {
        travelRtus(RtuManage.getInstance().getAllComRtu());
    }

    public void travelRtus(Collection<ComRtu> rtus) {
        this.factors.clear();
        for (ComRtu rtu : rtus) {
            byte districtCode = (byte) (rtu.getRtua() >>> 24);
            DistrictFactor factor = (DistrictFactor) this.factors.get(Byte.valueOf(districtCode));
            if (factor == null) {
                factor = new DistrictFactor();
                factor.districtCode = districtCode;
                this.factors.put(Byte.valueOf(factor.districtCode), factor);
            }
            factor.rtuCount += 1;
        }
    }

    public Collection<DistrictFactor> getAllDistricts() {
        List facts = new ArrayList(this.factors.values());
        List result = new ArrayList();
        while (facts.size() > 0) {
            result.add(removeMaxFactor(facts));
        }
        return result;
    }

    private DistrictFactor removeMaxFactor(List<DistrictFactor> facts) {
        DistrictFactor max = null;
        int pos = -1;
        for (int i = 0; i < facts.size(); ++i) {
            if ((max == null) || (max.rtuCount < ((DistrictFactor) facts.get(i)).rtuCount)) {
                max = (DistrictFactor) facts.get(i);
                pos = i;
            }
        }
        if (pos >= 0) facts.remove(pos);
        return max;
    }

    class DistrictFactor {
        public byte districtCode;
        public int rtuCount;

        DistrictFactor() {
            this.districtCode = 0;
            this.rtuCount = 0;
        }
    }
}