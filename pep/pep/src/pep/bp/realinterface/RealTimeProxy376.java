/*
 * 基于376国网规范的实时交互代理类
 */
package pep.bp.realinterface;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import pep.bp.db.RTTaskService;
import pep.bp.model.RealTimeTask;
import pep.bp.realinterface.mto.*;
import pep.codec.protocol.gb.gb376.*;
import pep.codec.utils.BcdUtils;

/**
 *
 * @author Thinkpad
 */
public class RealTimeProxy376 implements ICollectInterface {

    private static int ID;
    private final int FAILCODE = -1;
    private final byte AFN_SETPARA = 4; //AFN：设置参数
    private final byte FUNCODE_DOWM_1 = 1;//PRM =1 功能码：1 （发送/确认）
    private final static Logger log = LoggerFactory.getLogger(RealTimeProxy376.class);
    private RTTaskService taskService;
    private int getID() {
        ID++;
        return ID;
    }

    private void InjectDataIteam(PmPacket376 packet,CommandItem commandItem,byte AFN)
    {
        StringBuffer DataBuf = new StringBuffer();
        Map<String,String> datacellParam = commandItem.getDatacellParam();
        int FN = Integer.parseInt(commandItem.getIdentifier().substring(4,7 ));
        if (AFN == AFN_SETPARA) //设置参数
        {
            switch(FN)
            {
                //终端上行通信口通信参数设置
                case 1:{
                    Iterator iterator = datacellParam.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        if(key=="1004000106" || key=="1004000107")//需要主站确认的通信服务（CON=1）的标志
                            ;
                        else
                            packet.getDataBuffer().put(datacellParam.get(key).getBytes());
                            
                    }
                }
                //主站IP地址和端口
                case 3:{

                }
                //主站电话号码和短信中心号码
                case 4:{

                }
            }

        }
    }

    public RealTimeProxy376(){
        ApplicationContext cxt = new ClassPathXmlApplicationContext("beans.xml");
        taskService = (RTTaskService) cxt.getBean("taskService");
    }

     /**
     * 参数设置
     * @param MTO 消息传输对象
     * @return 回执码(-1:表示失败)
     * @throws Exception
     */
    public long writeEquipmentParameters(MessageTranObject MTO) throws Exception {
        try {
            if ((null == MTO) || (MTO.getType() != MTOType.GW_376)) {
                return FAILCODE;

            } else {
                int sequenceCode = getID();
                MTO_376 mto = (MTO_376) MTO;
                for (CollectObject obj : mto.getCollectObjects()) {
                    PmPacket376 packet = new PmPacket376();
                    packet.setAfn(AFN_SETPARA);//AFN
                    packet.getAddress().setRtua(obj.getLogicalAddr()); //逻辑地址
                    packet.getControlCode().setIsUpDirect(false);
                    packet.getControlCode().setIsOrgniger(true);
                    packet.getControlCode().setFunctionKey(FUNCODE_DOWM_1);
                    packet.getControlCode().setIsDownDirectFrameCountAvaliable(true);
                    packet.getControlCode().setDownDirectFrameCount((byte) 0);
                    int[] MpSn = obj.getMpSn();
                    StringBuffer DataBuffer = new StringBuffer();
                    PmPacket376DT dt = null;
                    for (int i = 0; i <= MpSn.length - 1; i++) {
                        PmPacket376DA da = new PmPacket376DA();
                        da.setPn(MpSn[i]);
                        List<CommandItem> CommandItems = obj.getCommandItems();
                        for (CommandItem commandItem : CommandItems) {
                            dt = new PmPacket376DT();
                            int fn = Integer.parseInt(commandItem.getIdentifier().substring(4, 8));//10+03+0002(protocolcode+afn+fn)
                            dt.setFn(fn);
                            packet.getDataBuffer().putDA(da);
                            packet.getDataBuffer().putDT(dt);
                            //注入设置的值

                            InjectDataIteam(packet,commandItem,AFN_SETPARA);
                        }
                        
                        
                        RealTimeTask task = new RealTimeTask();
                        task.setSendmsg(BcdUtils.binArrayToString(packet.getValue()));
                        task.setSequencecode(sequenceCode);
                        this.taskService.insertTask(task);
                    }
                }
                return sequenceCode;
            }
        } catch (NumberFormatException numberFormatException) {
            log.error(numberFormatException.getMessage());
            return -1;
        }
        catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage());
            return -1;
        }
    }

    /**
     * 参数读取
     * @param MTO 消息传输对象
     * @return 回执码（-1:表示失败）
     * @throws Exception
     */
    public long readEquipmentParameters(MessageTranObject MTO) throws Exception {
        return getID();
    }

    /**
     * 下发复位命令
     * @param MTO
     * @return 回执码（-1:表示失败）
     * @throws Exception
     */
    public long writeResetCommands(MessageTranObject MTO) throws Exception {
        return getID();
    }

    /**
     * 下发控制命令
     * @param MTO
     * @return 回执码（-1:表示失败）
     * @throws Exception
     */
    public long writeControlCommands(MessageTranObject MTO) throws Exception {
        return getID();
    }

    /**
     * 实时召测
     * @param MTO
     * @return
     * @throws Exception
     */
    public long readRealtimeData(MessageTranObject MTO) throws Exception {
        return getID();
    }

    /**
     * 透明转发
     * @param MTO
     * @return
     * @throws Exception
     */
    public long transmitMsg(MessageTranObject MTO) throws Exception {
        return getID();
    }

    /**
     * 获取参数设置结果
     * @param appId 回执码
     * @return 返回结果<"zdljdz#cldxh#commanditem", "result">
     * @throws Exception
     */
    public Map<String, String> getReturnByWEP(long appId) throws Exception {
        return null;
    }

    /**
     * 获取参数设置结果
     * @param appId
     * @return 返回JSon格式结果
     * @throws Exception
     */
    public String getReturnByWEP_Json(long appId) throws Exception {
        return "";
    }

    /**
     * 获取参数读取结果
     * @param appId 回执码
     * @return 返回结果<"zdljdz#cldxh#commanditem", <"dataitem", "datavalue">>
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByREP(long appId) throws Exception {
        return null;
    }

    /**
     * 获取参数读取结果
     * @param appId
     * @return 返回JSon格式结果
     * @throws Exception
     */
    public String getReturnByREP_Json(long appId) throws Exception {
        return "";
    }

    /**
     * 获取复位操作结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, String> getReturnByWRC(long appId) throws Exception {
        return null;
    }

    /**
     * 获取下发控制命令返回结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, String> getReturnByWCC(long appId) throws Exception {
        return null;
    }

    /**
     * 获取实时召测返回结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByRRD(long appId) throws Exception {
        return null;
    }
}
