package peis.interfaces.hicollect;

public class Constant {
    /**
     * 设备规约
     */
    public static final String EQUIP_PROTOCOL_698 = "100";          //698规约
    public static final String EQUIP_PROTOCOL_SCSL = "101";         //698规约-四川双流版
    public static final String EQUIP_PROTOCOL_SCMS = "102";         //698规约-四川眉山版
    public static final String EQUIP_PROTOCOL_GW = "106";           //国网规约
    public static final String EQUIP_PROTOCOL_TJMKB = "107";        //天津模块表规约
    public static final String EQUIP_PROTOCOL_ZJ = "120";           //浙江规约
    public static final String EQUIP_PROTOCOL_ZJZB = "121";         //浙江增补规约
    public static final String EQUIP_PROTOCOL_GDDYH = "122";        //广东大用户规约
    public static final String EQUIP_PROTOCOL_GDDYH2 = "123";       //广东大用户规约（第二版）
    public static final String EQUIP_PROTOCOL_GDPB = "124";         //广东配变规约
    public static final String EQUIP_PROTOCOL_JXPB = "125";         //江西配变规约
    public static final String EQUIP_PROTOCOL_GDJC = "126";         //广东集抄规约
    public static final String EQUIP_PROTOCOL_JCDYH = "127";        //江西大用户规约
    public static final String EQUIP_PROTOCOL_ZJZDY = "129";        //浙江自定义规约
    public static final String EQUIP_PROTOCOL_GDBDZ = "146";        //广东变电站规约
    
    /**
     * 测量点规约
     */
    public static final String MP_PROTOCOL_645 = "10";              //全国DLT645电表规约（97版）
    public static final String MP_PROTOCOL_645NEW = "11";           //多功能电能表通信协议 (即新版654规约)
    public static final String MP_PROTOCOL_ZJDB = "20";             //浙江电表规约（三相三线电流电压按A，B，C相排列）
    public static final String MP_PROTOCOL_ZJDB2 = "21";            //浙江电表规约（三相三线电流电压按A，C，B相排列）

    /**
     * 测量点表示方式
     */
    public static final int MP_EXPRESSMODE_ALL = 1;                 //所有测量点
    public static final int MP_EXPRESSMODE_BLOCK = 2;               //分段形式
    public static final int MP_EXPRESSMODE_LIST = 3;                //列表形式
}
