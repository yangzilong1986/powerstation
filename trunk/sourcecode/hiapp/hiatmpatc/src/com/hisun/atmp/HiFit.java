package com.hisun.atmp;

import com.hisun.database.HiResultSet;
import com.hisun.exception.HiAppException;
import com.hisun.exception.HiException;
import com.hisun.message.HiContext;
import com.hisun.message.HiMessageContext;
import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;

public class HiFit {
    public static synchronized void loadFit(HiMessageContext ctx) throws HiException {
        ArrayList fitTable = getRootCtxFitTable();
        if (fitTable != null) {
            return;
        }
        reLoadFit(ctx);
    }

    public static synchronized void reLoadFit(HiMessageContext ctx) throws HiException {
        ArrayList fitTable = new ArrayList();

        String sqlCmd = "SELECT * FROM ATMFIT ORDER BY CAST(FITLEN AS BIGINT) DESC";
        HiResultSet rs = ctx.getDataBaseUtil().execQuerySQL(sqlCmd);
        try {
            for (int i = 0; i < rs.size(); ++i) {
                HiFitItem item = new HiFitItem();

                String value = null;
                value = rs.getValue(i, "FitTrk");
                item.fitTrk = NumberUtils.toInt(value.trim());
                if ((item.fitTrk != 2) && (item.fitTrk != 3)) {
                    throw new HiAppException(-1, "220316", "FitTrk");
                }

                value = rs.getValue(i, "FitOfs");
                item.fitOfs = NumberUtils.toInt(value.trim());
                if (item.fitOfs < 0) {
                    throw new HiAppException(-1, "220316", "FitOfs");
                }

                item.fitCtt = rs.getValue(i, "FitCtt").trim();

                value = rs.getValue(i, "FitLen");
                item.fitLen = NumberUtils.toInt(value.trim());

                if (item.fitLen <= 0) {
                    throw new HiAppException(-1, "220316", "FitLen");
                }

                value = rs.getValue(i, "CrdTrk");
                item.crdTrk = NumberUtils.toInt(value.trim());

                if ((item.crdTrk != 2) && (item.crdTrk != 3)) {
                    throw new HiAppException(-1, "220316", "CrdTrk");
                }

                value = rs.getValue(i, "CrdOfs");
                item.crdOfs = NumberUtils.toInt(value.trim());

                if (item.crdOfs < 0) {
                    throw new HiAppException(-1, "220316", "CrdOfs");
                }

                value = rs.getValue(i, "CrdLen");
                item.crdLen = NumberUtils.toInt(value.trim());

                if (item.crdLen < 0) {
                    throw new HiAppException(-1, "220316", "CrdLen");
                }

                item.crdFlg = rs.getValue(i, "CrdFlg").trim();

                value = rs.getValue(i, "CdCdOf");
                item.cdCdOf = NumberUtils.toInt(value.trim());

                if (item.cdCdOf < 0) {
                    throw new HiAppException(-1, "220316", "CdCdOf");
                }

                item.crdNam = rs.getValue(i, "CrdNam");

                item.bnkTyp = rs.getValue(i, "BnkTyp").trim();

                value = rs.getValue(i, "VaDtOf");
                item.vaDtOf = NumberUtils.toInt(value.trim());

                fitTable.add(item.toMap());
            }
        } catch (Exception e) {
            fitTable.clear();
            throw HiException.makeException(e);
        }
        setRootCtxFitTable(fitTable);
    }

    public static ArrayList getFitTable() {
        return getRootCtxFitTable();
    }

    private static ArrayList getRootCtxFitTable() {
        HiContext rootCtx = HiContext.getRootContext();
        ArrayList fittable = (ArrayList) rootCtx.getProperty("_FIT_TABLE");
        return fittable;
    }

    private static void setRootCtxFitTable(ArrayList fitTable) {
        HiContext rootCtx = HiContext.getRootContext();
        rootCtx.setProperty("_FIT_TABLE", fitTable);
    }
}