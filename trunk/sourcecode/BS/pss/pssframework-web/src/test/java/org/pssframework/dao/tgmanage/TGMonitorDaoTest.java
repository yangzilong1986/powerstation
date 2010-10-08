package org.pssframework.dao.tgmanage;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TGMonitorDaoTest {
    private TGMonitorDao tgMonitorDao;

    @Before
    public void setUp() throws Exception {
        tgMonitorDao = new TGMonitorDao();
    }

    @After
    public void tearDown() throws Exception {
        //tgMonitorDao = null;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFindCombBoxByTg() {
        List list = tgMonitorDao.findCombBoxByTg(102L, "getTotalMeterCombBoxByTgId");
        System.out.println(list.toString());
    }

}
