package peis.interfaces.hicollect.datacell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import peis.common.DataConverter;
import peis.interfaces.hicollect.CommandItem;

/**
 * 
 * @author Zhangyu
 * @version 1.0
 * Create Date : 20090708
 */
public class TaskParamGrid implements SendDataCell {
    private static Logger logger = Logger.getLogger(TaskParamGrid.class);
    
    private static final String COMMANDITEM_DSONE = "10040065";         //定时发送1类数据任务设置命令项标识
    private static final String DATAITEM_DSONE_01 = "DI_1004006501";       //周期单位
    private static final String DATAITEM_DSONE_02 = "DI_1004006502";       //定时发送周期
    private static final String DATAITEM_DSONE_03 = "DI_1004006503";       //发送基准时间
    private static final String DATAITEM_DSONE_04 = "DI_1004006504";       //曲线数据抽取倍率R
    private static final String DATAITEM_DSONE_05 = "DI_1004006505";       //数据单元标识个数n
    private static final String DATAITEM_DSONE_06 = "DI_1004006506XXXX";   //数据单元标识个数n
    
    private static final String COMMANDITEM_DSTWO = "10040066";         //定时发送2类数据任务设置命令项标识
    private static final String DATAITEM_DSTWO_01 = "DI_1004006601";       //周期单位
    private static final String DATAITEM_DSTWO_02 = "DI_1004006602";       //定时发送周期
    private static final String DATAITEM_DSTWO_03 = "DI_1004006603";       //发送基准时间
    private static final String DATAITEM_DSTWO_04 = "DI_1004006604";       //曲线数据抽取倍率R
    private static final String DATAITEM_DSTWO_05 = "DI_1004006605";       //数据单元标识个数n
    private static final String DATAITEM_DSTWO_06 = "DI_1004006606XXXX";   //数据单元标识个数n
    
    private int dataSort;                       //数据分类 : 1 - 一类数据 2 - 二类数据
    private int sendupCycle;                    //定时发送周期 : 1~63
    private int sendupUnit;                     //定时发送周期单位 : 取值0~3依次表示分、时、日、月
    private Date baseTime;                      //发送基准时间
    private int extractCnt;                     //曲线数据抽取倍率 : 表示终端按此倍率抽取数据上送，如被抽取的数据的冻结密度m=2，即每30分钟冻结一个值，那么当R=2时，表示按60分钟抽取，R=1时，表示仍按30分钟抽取。
    private int[] gpSns;                        //测量点序号 : 包括测量点号、总加组号等
    private String[] commanditems;              //命令项标识
    
    public TaskParamGrid() {
        
    }

    public CommandItem renderCommandItem() throws Exception {
        CommandItem commandItem = new CommandItem();
        logger.info("---------------------------------------------------");
        logger.info("dataSort : " + dataSort);
        logger.info("sendupCycle : " + sendupCycle);
        logger.info("sendupUnit : " + sendupUnit);
        logger.info("baseTime : " + baseTime);
        logger.info("extractCnt : " + extractCnt);
        logger.info("gpSns : ");
        for(int i = 0; i < gpSns.length; i++) {
            logger.info("  " + gpSns[i]);
        }
        logger.info("commanditems : ");
        for(int i = 0; i < commanditems.length; i++) {
            logger.info("  " + commanditems[i]);
        }
        if(dataSort == 1) {
            commandItem.setIdentifier(COMMANDITEM_DSONE);
            logger.info("set commanditem code : " + COMMANDITEM_DSONE);
            Map datacellParam = new HashMap();
            datacellParam.put(DATAITEM_DSONE_01, "" + sendupUnit);
            logger.info("  set dataitem code : " + DATAITEM_DSONE_01 + " - " + sendupUnit);
            datacellParam.put(DATAITEM_DSONE_02, "" + sendupCycle);
            logger.info("  set dataitem code : " + DATAITEM_DSONE_02 + " - " + sendupCycle);
            datacellParam.put(DATAITEM_DSONE_03, renderDataItemBaseTime(baseTime));
            logger.info("  set dataitem code : " + DATAITEM_DSONE_03 + " - " + renderDataItemBaseTime(baseTime));
            datacellParam.put(DATAITEM_DSONE_04, "" + extractCnt);
            logger.info("  set dataitem code : " + DATAITEM_DSONE_04 + " - " + extractCnt);
            List listDA = renderDAlist(gpSns);
            List listDT = renderDTlist(commanditems);
            datacellParam.put(DATAITEM_DSONE_05, "" + (listDA.size() * listDT.size()));
            logger.info("  set dataitem code : " + DATAITEM_DSONE_05 + " - " + (listDA.size() * listDT.size()));
            Iterator itDA = listDA.iterator();
            String sDA, sDT;
            int i = 0;
            while(itDA.hasNext()) {
                sDA = (String) itDA.next();
                Iterator itDT = listDT.iterator();
                while(itDT.hasNext()) {
                    i++;
                    sDT = (String) itDT.next();
                    datacellParam.put(DATAITEM_DSONE_06.replaceAll("XXXX", DataConverter.fillTops("" + i, 4, '0')), (sDA + sDT));
                    logger.info("  set dataitem code : " + DATAITEM_DSONE_06.replaceAll("XXXX", DataConverter.fillTops("" + i, 4, '0')) + " - " + (sDA + sDT));
                }
            }
            commandItem.setDatacellParam(datacellParam);
        }
        else if(dataSort == 2) {
            commandItem.setIdentifier(COMMANDITEM_DSTWO);
            logger.info("set commanditem code : " + COMMANDITEM_DSTWO);
            Map datacellParam = new HashMap();
            datacellParam.put(DATAITEM_DSTWO_01, "" + sendupUnit);
            logger.info("  set dataitem code : " + DATAITEM_DSTWO_01 + " - " + sendupUnit);
            datacellParam.put(DATAITEM_DSTWO_02, "" + sendupCycle);
            logger.info("  set dataitem code : " + DATAITEM_DSTWO_02 + " - " + sendupCycle);
            datacellParam.put(DATAITEM_DSTWO_03, renderDataItemBaseTime(baseTime));
            logger.info("  set dataitem code : " + DATAITEM_DSTWO_03 + " - " + renderDataItemBaseTime(baseTime));
            datacellParam.put(DATAITEM_DSTWO_04, "" + extractCnt);
            logger.info("  set dataitem code : " + DATAITEM_DSTWO_04 + " - " + extractCnt);
            List listDA = renderDAlist(gpSns);
            List listDT = renderDTlist(commanditems);
            datacellParam.put(DATAITEM_DSTWO_05, "" + (listDA.size() * listDT.size()));
            logger.info("  set dataitem code : " + DATAITEM_DSTWO_05 + " - " + (listDA.size() * listDT.size()));
            Iterator itDA = listDA.iterator();
            String sDA, sDT;
            int i = 0;
            while(itDA.hasNext()) {
                sDA = (String) itDA.next();
                Iterator itDT = listDT.iterator();
                while(itDT.hasNext()) {
                    i++;
                    sDT = (String) itDT.next();
                    datacellParam.put(DATAITEM_DSTWO_06.replaceAll("XXXX", DataConverter.fillTops("" + i, 4, '0')), (sDA + sDT));
                    logger.info("  set dataitem code : " + DATAITEM_DSTWO_06.replaceAll("XXXX", DataConverter.fillTops("" + i, 4, '0')) + " - " + (sDA + sDT));
                }
            }
            commandItem.setDatacellParam(datacellParam);
        }
        logger.info("---------------------------------------------------");
        
        return commandItem;
    }
    
    /**
     * 
     * @param baseTime
     * @return
     * @throws Exception
     */
    private String renderDataItemBaseTime(Date baseTime) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); //
        String sBaseTime = dateFormat.format(baseTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseTime);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        week = (week == 0 ? 7 : week);
        return sBaseTime + week;
    }
    
    /**
     * 
     * @param gpSns
     * @return
     * @throws Exception
     */
    private List renderDAlist(int[] gpSns) throws Exception {
        //数据单元标识
        //    - 信息点DA
        List listDA = new ArrayList();
        if(gpSns.length == 1 && gpSns[0] == 0) {      //p0
            listDA.add("00" + "00");
        }
        else {
            //取得信息的序号最大值
            int max_pn = 0;
            for(int i = 0; i < gpSns.length; i++) {
                if(max_pn < gpSns[i] && gpSns[i] <= 64) {
                    max_pn = gpSns[i];
                }
            }
            int group_count = ((max_pn - 1) / 8) + 1;
            int[] i_groups = new int[group_count];
            //初始化

            for(int i = 0; i < i_groups.length; i++) {
                i_groups[i] = 0x00;
            }
            /*********************************************************************************************
             * 信息点组DA2                           *                     信息点元DA1
             ****D7**D6**D5**D4**D3**D2**D1**D0***********D7****D6****D5****D4****D3****D2****D1****D0****
             *    0   0   0   0   0   0   0   1     *     p8    p7    p6    p5    p4    p3    p2    p1
             *    0   0   0   0   0   0   1   0     *     p16   p15   p14   p13   p12   p11   p10   p9
             *    0   0   0   0   0   1   0   0     *     p24   p23   p22   p21   p20   p19   p18   p17
             *    0   0   0   0   1   0   0   0     *     p32   p31   p30   p29   p28   p27   p26   p25
             *    0   0   0   1   0   0   0   0     *     p40   p39   p38   p37   p36   p35   p34   p33
             *    0   0   1   0   0   0   0   0     *     p48   p47   p46   p45   p44   p43   p42   p41
             *    0   1   0   0   0   0   0   0     *     p56   p55   p54   p53   p52   p51   p50   p49
             *    1   0   0   0   0   0   0   0     *     p64   p63   p62   p61   p60   p59   p58   p57
             *********************************************************************************************/
            int x, y;
            for(int i = 0; i < gpSns.length; i++) {
                if(gpSns[i] <= 64) {
                    y = (gpSns[i] - 1) / 8;
                    x = (gpSns[i] - 1) - (y * 8);
                    i_groups[y] = i_groups[y] | (0x01 << x);
                }
            }
            
            for(int i = 0; i < i_groups.length; i++) {
                if(i_groups[i] > 0x00) {
                    listDA.add(DataConverter.int2Hex(i_groups[i], 1) + DataConverter.int2Hex((1 << i), 1));
                }
            }
        }
        return listDA;
    }
    
    /**
     * 
     * @param commanditems
     * @return
     * @throws Exception
     */
    private List renderDTlist(String[] commanditems) throws Exception {
        //    - 信息类DT
        List listDT = new ArrayList();
        //取得信息类标识序号最大值
        int max_fn = 0;
        int i_fn = 0;
        for(int i = 0; i < commanditems.length; i++) {
            if(commanditems[i].length() == 8) {
                i_fn = Integer.parseInt(commanditems[i].substring(4, 8));
                if(max_fn < i_fn && i_fn <= 248) {
                    max_fn = i_fn;
                }
            }
        }
        int group_count = ((max_fn - -1) / 8) + 1;
        int[] i_groups = new int[group_count];
        //初始化

        for(int i = 0; i < i_groups.length; i++) {
            i_groups[i] = 0x00;
        }
        /**********************************************************************
         * 信息类组DT2     *                      信息类元DT1
         ****D7~D0*************D7****D6****D5****D4****D3****D2****D1****D0****
         *     0         *     F8    F7    F6    F5    F4    F3    F2    F1
         *     1         *     F16   F15   F14   F13   F12   F11   F10   F9
         *     2         *     F24   F23   F22   F21   F20   F19   F18   F17
         *    ……         *     ……  ……   ……   ……   ……   ……  ……   ……
         *    30         *     F248  F247  F246  F245  F244  F243  F242  F241
         *    ……         *                        未定义
         *    255        *                        未定义
         *********************************************************************/
        int x, y;
        for(int i = 0; i < commanditems.length; i++) {
            if(commanditems[i].length() == 8) {
                i_fn = Integer.parseInt(commanditems[i].substring(4, 8));
                if(i_fn <= 248) {
                    y = (i_fn - 1) / 8;
                    x = (i_fn - 1) - (y * 8);
                    i_groups[y] = i_groups[y] | (0x01 << x);
                }
            }
        }
        
        for(int i = 0; i < i_groups.length; i++) {
            if(i_groups[i] > 0x00) {
                listDT.add(DataConverter.int2Hex(i_groups[i], 1) + DataConverter.int2Hex(i, 1));
            }
        }
        
        return listDT;
    }

    /**
     * @return the dataSort
     */
    public int getDataSort() {
        return dataSort;
    }

    /**
     * @param dataSort the dataSort to set
     */
    public void setDataSort(int dataSort) {
        this.dataSort = dataSort;
    }

    /**
     * @return the sendupCycle
     */
    public int getSendupCycle() {
        return sendupCycle;
    }

    /**
     * @param sendupCycle the sendupCycle to set
     */
    public void setSendupCycle(int sendupCycle) {
        this.sendupCycle = sendupCycle;
    }

    /**
     * @return the sendupUnit
     */
    public int getSendupUnit() {
        return sendupUnit;
    }

    /**
     * @param sendupUnit the sendupUnit to set
     */
    public void setSendupUnit(int sendupUnit) {
        this.sendupUnit = sendupUnit;
    }

    /**
     * @return the baseTime
     */
    public Date getBaseTime() {
        return baseTime;
    }

    /**
     * @param baseTime the baseTime to set
     */
    public void setBaseTime(Date baseTime) {
        this.baseTime = baseTime;
    }

    /**
     * @return the extractCnt
     */
    public int getExtractCnt() {
        return extractCnt;
    }

    /**
     * @param extractCnt the extractCnt to set
     */
    public void setExtractCnt(int extractCnt) {
        this.extractCnt = extractCnt;
    }

    /**
     * @return the gpSns
     */
    public int[] getGpSns() {
        return gpSns;
    }

    /**
     * @param gpSns the gpSns to set
     */
    public void setGpSns(int[] gpSns) {
        this.gpSns = gpSns;
    }

    /**
     * @return the commanditems
     */
    public String[] getCommanditems() {
        return commanditems;
    }

    /**
     * @param commanditems the commanditems to set
     */
    public void setCommanditems(String[] commanditems) {
        this.commanditems = commanditems;
    }
}
