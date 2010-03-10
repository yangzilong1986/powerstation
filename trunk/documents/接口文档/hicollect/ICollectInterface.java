package peis.interfaces.hicollect;

import java.util.List;
import java.util.Map;

/**
 * 实时通讯服务接口
 * @author Zhangyu
 * @version 1.0
 * Create Date : 20090625
 */
public interface ICollectInterface {
    /**
     * 得到超时时间，单位s
     * @return
     */
    public int getTmOut();
    
    /**
     * 设置超时时间，单位s
     * @param tmOut
     */
    public void setTmOut(int tmOut);
    
    /**
     * 得到服务器状态信息
     * @param machNo
     * @return
     * @throws Exception
     */
    public ServerStatus getServerStatus(String machNo) throws Exception;
    
    /**
     * 参数设置 - 国网系列[100,101,102,106]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByWEP(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 参数命令项<CommandItem>
     * @param appId : 应用ID
     * @return : true - 下发参数设置命令成功 false - 下发参数设置命令失败
     * @throws Exception
     */
    public boolean writeEquipmentParameters(List<CollectObject> collectObjects, List<CommandItem> commandItems, long appId) throws Exception;
    
    /**
     * 参数读取 - 国网系列[100,101,102,106]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByREP(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 参数命令项标识
     * @param appId : 应用ID
     * @return : true - 下发参数读取命令成功 false - 下发参数读取命令失败
     * @throws Exception
     */
    public boolean readEquipmentParameters(List<CollectObject> collectObjects, String[] commandItems, long appId) throws Exception;
    
    /**
     * 参数读取 - 国网系列[100,101,102,106]
     * 支持带数据单元
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByREP(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 参数命令项<CommandItem>
     * @param appId : 应用ID
     * @return : true - 下发参数读取命令成功 false - 下发参数读取命令失败
     * @throws Exception
     */
    public boolean readEquipmentParameters(List<CollectObject> collectObjects, List<CommandItem> commandItems, long appId) throws Exception;
    
    /**
     * 下发复位命令 - 国网系列[100,106]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByWRC(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 复位命令项<CommandItem>
     * @param appId : 应用ID
     * @return : true - 下发复位命令成功 false - 下发复位命令失败
     * @throws Exception
     */
    public boolean writeResetCommands(List<CollectObject> collectObjects, List<CommandItem> commandItems, long appId) throws Exception;
    
    /**
     * 下发控制命令 - 国网系列[100,106]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByWCC(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 控制命令项<CommandItem>
     * @param appId : 应用ID
     * @return : true - 下发控制命令成功 false - 下发控制命令失败
     * @throws Exception
     */
    public boolean writeControlCommands(List<CollectObject> collectObjects, List<CommandItem> commandItems, long appId) throws Exception;
    
    /**
     * 实时召测 - 国网系列[100,106]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByRRD(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 召测命令项标识
     * @param assistParams : 辅助参数，对召测国网二类数据有效<AssistParam>
     * @param appId : 应用ID
     * @return : true - 下发实时召测命令成功 false - 下发实时召测命令失败
     * @throws Exception
     */
    public boolean readRealtimeData(List<CollectObject> collectObjects, String[] commandItems, List<AssistParam> assistParams, long appId) throws Exception;
    
    /**
     * 获取参数设置结果
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem", "result">
     * @throws Exception
     */
    public Map<String, String> getReturnByWEP(long appId) throws Exception;
    
    /**
     * 获取参数读取结果
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem", <"dataitem", "datavalue">>
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByREP(long appId) throws Exception;
    
    /**
     * 获取复位命令下发结果 - 国网系列[100,106]
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem", "result">
     * @throws Exception
     */
    public Map<String, String> getReturnByWRC(long appId) throws Exception;
    
    /**
     * 获取控制命令下发结果 - 国网系列[100,106]
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem", "result">
     * @throws Exception
     */
    public Map<String, String> getReturnByWCC(long appId) throws Exception;
    
    /**
     * 获取实时召测结果
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem#dataitem", <"datatime", "datavalue">>
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByRRD(long appId) throws Exception;
    
    /**
     * 小圈轮召启动 - 国网系列[100,106]
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems
     * @param appId
     * @param cyclCount : 小圈轮召次数
     * @param cycleTime : 小圈轮召时间
     * @return
     * @throws Exception
     */
    public boolean callLittliCycle(List<CollectObject> collectObjects, String[] commandItems, long appId, int cyclCount, int cycleTime) throws Exception;
    
    /**
     * 小圈轮召停止 - 国网系列[100,106]
     * @param appId
     * @return
     * @throws Exception
     */
    public boolean stopLittliCycle(long appId) throws Exception;
    
    /**
     * 发送自定义命令
     * @param collectObject : 对象<CollectObject>
     * @param customFrame : 自定义帧
     * @return
     * @throws Exception
     */
    public boolean sendCustomFrame(CollectObject collectObject, String customFrame) throws Exception;
    
    /**
     * 参数设置 - 浙规系列[120,121,122,123,124,125,126,129]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByWEPZJ(long)} or {@link #getReturnByWEP(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 参数命令项<CommandItem>
     * @param appId : 应用ID
     * @return : true - 下发参数设置命令成功 false - 下发参数设置命令失败
     * @throws Exception
     */
    public boolean writeEquipmentParametersZJ(List<CollectObject> collectObjects, List<CommandItem> commandItems, long appId) throws Exception;
    
    /**
     * 参数设置 - 浙规系列[120,121,122,123,124,125,126,129]
     *   实时写对象参数  07H
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByWEPZJ(long)} or {@link #getReturnByWEP(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 参数命令项<CommandItem>
     * @param assistParams : 辅助参数<AssistParam>
     * @param appId : 应用ID
     * @return : true - 下发参数设置命令成功 false - 下发参数设置命令失败
     * @throws Exception
     */
    public boolean writeEquipmentParametersZJ(List<CollectObject> collectObjects, List<CommandItem> commandItems, List<AssistParam> assistParams, long appId) throws Exception;
    
    /**
     * 参数读取 - 浙规系列[120,121,122,123,124,125,126,129]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByREPZJ(long)} or {@link #getReturnByREP(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 参数命令项标识     * @param appId : 应用ID
     * @return : true - 下发参数读取命令成功 false - 下发参数读取命令失败
     * @throws Exception
     */
    public boolean readEquipmentParametersZJ(List<CollectObject> collectObjects, String[] commandItems, long appId) throws Exception;
    
    /**
     * 实时召测 - 浙规系列[120,121,122,123,124,125,126,129]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByRRDZJ(long)} or {@link #getReturnByRRD(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 召测命令项标识
     * @param assistParams : 辅助参数<AssistParam>
     * @param appId : 应用ID
     * @return : true - 下发实时召测命令成功 false - 下发实时召测命令失败
     * @throws Exception
     */
    public boolean readRealtimeDataZJ(List<CollectObject> collectObjects, String[] commandItems, List<AssistParam> assistParams, long appId) throws Exception;
    
    /**
     * 获取参数设置结果 - 浙规系列[120,121,122,123,124,125,126,129]
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem", "result">
     * @throws Exception
     */
    public Map<String, String> getReturnByWEPZJ(long appId) throws Exception;
    
    /**
     * 获取参数读取结果 - 浙规系列[120,121,122,123,124,125,126,129]
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem", <"dataitem", "datavalue">>
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByREPZJ(long appId) throws Exception;
    
    /**
     * 获取实时召测结果 - 浙规系列[120,121,122,123,124,125,126,129]
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem#dataitem", <"datatime", "datavalue">>
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByRRDZJ(long appId) throws Exception;
    
    /**
     * 集中器实时召测命令 - 广东集抄规约[126]
     * 
     * --------------------------------------------------------------------------------
     *  20119010    0100    （当前）正向有功总电能     *  20119011    0101    （当前）费率1正向有功电能
     *  20119012    0102    （当前）费率2正向有功电能
     *  20119013    0103    （当前）费率3正向有功电能
     *  20119014    0104    （当前）费率4正向有功电能
     *  20119410    0100    （上月）正向有功总电能     *  20119411    0101    （上月）费率1正向有功电能
     *  20119412    0102    （上月）费率2正向有功电能
     *  20119413    0103    （上月）费率3正向有功电能
     *  20119414    0104    （上月）费率4正向有功电能
     *  20119810    0100    （上上月）正向有功总电能     *  20119811    0101    （上上月）费率1正向有功电能
     *  20119812    0102    （上上月）费率2正向有功电能
     *  20119813    0103    （上上月）费率3正向有功电能
     *  20119814    0104    （上上月）费率4正向有功电能
     *  20119A10    0100    （上上上月）正向有功总电能     *  20119A11    0101    （上上上月）费率1正向有功电能
     *  20119A12    0102    （上上上月）费率2正向有功电能
     *  20119A13    0103    （上上上月）费率3正向有功电能
     *  20119A14    0104    （上上上月）费率4正向有功电能
     *  2011C010    4001    日期
     *  2011C010    4002    周日
     *  2011C011    4003    时间
     *  20119090    BE2E    当日零点电量
     *  20119090    BE2F    当日零点电量日期
     *  20119091    BE2E    上1日零点电量     *  20119091    BE2F    上1日零点电量日期     *  20119092    BE2E    上2日零点电量     *  20119092    BE2F    上2日零点电量日期     *  20119093    BE2E    上3日零点电量     *  20119093    BE2F    上3日零点电量日期     *  20119094    BE2E    上4日零点电量     *  20119094    BE2F    上4日零点电量日期     *  20119095    BE2E    上5日零点电量     *  20119095    BE2F    上5日零点电量日期     *  20119096    BE2E    上6日零点电量     *  20119096    BE2F    上6日零点电量日期     *  20119097    BE2E    上7日零点电量     *  20119097    BE2F    上7日零点电量日期     *  2011E133    2B60    A相失压次数     *  2011E136    2B61    B相失压次数     *  2011E139    2B62    C相失压次数     *  2011E122    2B70    A相失流次数     *  2011E124    2B71    B相失流次数     *  2011E126    2B72    C相失流次数     *  20118EA0    4050    电表状态字
     *  2011E142    2B80    A相电流反向次数     *  2011E146    2B81    B相电流反向次数     *  2011E14A    2B82    C相电流反向次数     *  2011E14A    2B82    最近一次编程时间     *  2011E211    F030    编程次数
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByRRDZJ(long)} or {@link #getReturnByRRD(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param concentratorObjects : <Concentrator>
     * @param commandItems
     * @param assistParams : <AssistParam>
     * @param appId : 应用ID
     * @return
     * @throws Exception
     */
    public boolean gdConcRTMR(List<Concentrator> concentratorObjects, String[] commandItems, List<AssistParam> assistParams, long appId) throws Exception;
    
    /**
     * 取消集中器召测命令 - 广东集抄规约[126]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByCGCRTMR(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : <CollectObject>
     * @param appId : 应用ID
     * @return
     * @throws Exception
     */
    public boolean cancelGdConcRTMR(List<CollectObject> collectObjects, long appId) throws Exception;
    
    /**
     * 获取取消集中器召测命令返回结果 - 广东集抄规约[126]
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz", "result">
     * @throws Exception
     */
    public Map<String, String> getReturnByCGCRTMR(long appId) throws Exception;
    
    /**
     * 集中器对表拉合闸控制 - 广东集抄规约[126]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByGCCTL(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param concentratorObjects : <Concentrator>
     * @param commandItems : <CommandItem>
     * @param assistParams : <AssistParam>
     * @param appId : 应用ID
     * @return
     * @throws Exception
     */
    public boolean gdConcCTL(List<Concentrator> concentratorObjects, List<CommandItem> commandItems, List<AssistParam> assistParams, long appId) throws Exception;
    
    /**
     * 获取集中器对表拉合闸控制返回结果 - 广东集抄规约[126]
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#clddz#commanditem", "result">
     * @throws Exception
     */
    public Map<String, String> getReturnByGCCTL(long appId) throws Exception;
    
    /**
     * 集中器抄收日常综合数据 - 广东集抄规约[126]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByRRDZJ(long)} or {@link #getReturnByRRD(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects
     * @param commandItems
     * @param assistParams
     * @param appId
     * @return
     * @throws Exception
     */
    public boolean gdConcRDSD(List<CollectObject> collectObjects, String[] commandItems, List<AssistParam> assistParams, long appId) throws Exception;
    
    /**
     * 集中器抄收重点户整点数据 - 广东集抄规约[126]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByRRDZJ(long)} or {@link #getReturnByRRD(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects
     * @param commandItems
     * @param assistParams
     * @param appId
     * @return
     * @throws Exception
     */
    public boolean gdConcRFHD(List<CollectObject> collectObjects, String[] commandItems, List<AssistParam> assistParams, long appId) throws Exception;
    
    /**
     * 集中器抄收测量点其他数据 - 广东集抄规约[126]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByRRDZJ(long)} or {@link #getReturnByRRD(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects
     * @param commandItems
     * @param assistParams
     * @param appId
     * @return
     * @throws Exception
     */
    public boolean gdConcRGOD(List<CollectObject> collectObjects, String[] commandItems, List<AssistParam> assistParams, long appId) throws Exception;
    
    /**
     * 集中器增加、修改、删除表号 - 广东集抄规约[126]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByGCCRUM(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects
     * @param commandItems
     * @param assistParams
     * @param appId
     * @return
     * @throws Exception
     */
    public boolean gdConcCRUM(List<CollectObject> collectObjects, List<CommandItem> commandItems, List<AssistParam> assistParams, long appId) throws Exception;
    
    /**
     * 获取集中器增加、修改、删除表号返回结果 - 广东集抄规约[126]
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem", "result">
     * @throws Exception
     */
    public Map<String, String> getReturnByGCCRUM(long appId) throws Exception;
    
    /**
     * 集中器抄收电表表号     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByGCRMI(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects
     * @param commandItem
     * @param assistParams
     * @param appId
     * @return
     * @throws Exception
     */
    public boolean gdConcRMI(List<CollectObject> collectObjects, String[] commandItem, List<AssistParam> assistParams, long appId) throws Exception;
    
    /**
     * 获取集中器抄收电表表号返回结果 - 广东集抄规约[126]
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem", <"dataitem", "datavalue">>
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByGCRMI(long appId) throws Exception;
    
    /**
     * 参数设置 - 102系列[146]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByWEP102(long)} or {@link #getReturnByWEP(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 参数命令项<CommandItem>
     * @param appId : 应用ID
     * @return : true - 下发参数设置命令成功 false - 下发参数设置命令失败
     * @throws Exception
     */
    public boolean writeEquipmentParameters102(List<CollectObject> collectObjects, List<CommandItem> commandItems, long appId) throws Exception;
    
    /**
     * 参数读取 - 102系列[146]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByREP102(long)} or {@link #getReturnByREP(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 参数命令项标识
     * @param appId : 应用ID
     * @return : true - 下发参数读取命令成功 false - 下发参数读取命令失败
     * @throws Exception
     */
    public boolean readEquipmentParameters102(List<CollectObject> collectObjects, List<CommandItem> commandItems, long appId) throws Exception;
    
    /**
     * 实时召测 - 102系列[146]
     * 
     * --------------------------------------------------------------------------------
     * 获取返回结果接口 : @see {@link #getReturnByRRD102(long)} or {@link #getReturnByRRD(long)}
     * --------------------------------------------------------------------------------
     * 
     * @param collectObjects : 对象<CollectObject>
     * @param commandItems : 召测命令项标识
     * @param assistParams : 辅助参数<AssistParam>
     * @param appId : 应用ID
     * @return : true - 下发实时召测命令成功 false - 下发实时召测命令失败
     * @throws Exception
     */
    public boolean readRealtimeData102(List<CollectObject> collectObjects, List<CommandItem> commandItems, List<AssistParam> assistParams, long appId) throws Exception;
    
    /**
     * 获取参数设置结果 - 102系列[146]
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem", "result">
     * @throws Exception
     */
    public Map<String, String> getReturnByWEP102(long appId) throws Exception;
    
    /**
     * 获取参数读取结果 - 102系列[146]
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem", <"dataitem", "datavalue">>
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByREP102(long appId) throws Exception;
    
    /**
     * 获取实时召测结果 - 102系列[146]
     * @param appId : 应用ID
     * @return : 返回结果<"zdljdz#cldxh#commanditem#dataitem", <"datatime", "datavalue">>
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByRRD102(long appId) throws Exception;
}
