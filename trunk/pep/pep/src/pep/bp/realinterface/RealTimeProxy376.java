/*
 * 基于376国网规范的实时交互代理类
 */
package pep.bp.realinterface;
import java.util.*;
import pep.bp.realinterface.mto.*;
import pep.codec.protocol.gb.gb376.*;
/**
 *
 * @author Thinkpad
 */
public class RealTimeProxy376 implements ICollectInterface {
    private static long ID;
    private final long FAILCODE = -1;
    private final byte AFN_SETPARA = 4; //AFN：设置参数

    private final byte FUNCODE_DOWM_1 = 1;//PRM =1 功能码：1 （发送/确认）
    private long getID(){
        ID++;
        return ID;
    }

    
    /**
     * 参数设置
     * @param MTO 消息传输对象
     * @return 回执码(-1:表示失败)
     * @throws Exception
     */
    public long writeEquipmentParameters(MessageTranObject MTO) throws Exception{
        if((null == MTO) || (MTO.getType() != MTOType.GW_376))
            return FAILCODE;
        else{
            MTO_376 mto = (MTO_376)MTO;
            for(CollectObject obj:mto.getCollectObjects()){
                PmPacket376 packet = new PmPacket376();
                packet.setAfn(AFN_SETPARA);//AFN
                packet.getAddress().setRtua(obj.getLogicalAddr()); //逻辑地址
                packet.getControlCode().setIsUpDirect(false);
                packet.getControlCode().setIsOrgniger(true);
                packet.getControlCode().setFunctionKey(FUNCODE_DOWM_1);
                packet.getControlCode().setIsDownDirectFrameCountAvaliable(true);
                packet.getControlCode().setDownDirectFrameCount((byte)0);
//                for(int i=0;i<=obj.getMpSn().length-1;i++){
//                    for(String )
//                }
//                packet.getDataBuffer().setValue(obj.);
            }
            return getID();
        }
    }

    /**
     * 参数读取
     * @param MTO 消息传输对象
     * @return 回执码（-1:表示失败）
     * @throws Exception
     */
    public long readEquipmentParameters(MessageTranObject MTO) throws Exception{
        return getID();
    }

    /**
     * 下发复位命令
     * @param MTO
     * @return 回执码（-1:表示失败）
     * @throws Exception
     */
    public long writeResetCommands(MessageTranObject MTO) throws Exception{
        return getID();
    }

    /**
     * 下发控制命令
     * @param MTO
     * @return 回执码（-1:表示失败）
     * @throws Exception
     */
    public long writeControlCommands(MessageTranObject MTO) throws Exception{
        return getID();
    }

    /**
     * 实时召测
     * @param MTO
     * @return
     * @throws Exception
     */
    public long readRealtimeData(MessageTranObject MTO) throws Exception{
        return getID();
    }

    /**
     * 透明转发
     * @param MTO
     * @return
     * @throws Exception
     */
    public long transmitMsg(MessageTranObject MTO) throws Exception{
        return getID();
    }

    /**
     * 获取参数设置结果
     * @param appId 回执码
     * @return 返回结果<"zdljdz#cldxh#commanditem", "result">
     * @throws Exception
     */
    public Map<String, String> getReturnByWEP(long appId) throws Exception{
        return null;
    }

    /**
     * 获取参数设置结果
     * @param appId
     * @return 返回JSon格式结果
     * @throws Exception
     */
    public String getReturnByWEP_Json(long appId) throws Exception{
        return "";
    }

    /**
     * 获取参数读取结果
     * @param appId 回执码
     * @return 返回结果<"zdljdz#cldxh#commanditem", <"dataitem", "datavalue">>
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByREP(long appId) throws Exception{
        return null;
    }

    /**
     * 获取参数读取结果
     * @param appId
     * @return 返回JSon格式结果
     * @throws Exception
     */
    public String getReturnByREP_Json(long appId) throws Exception{
        return "";
    }

    /**
     * 获取复位操作结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, String> getReturnByWRC(long appId) throws Exception{
        return null;
    }

    /**
     * 获取下发控制命令返回结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, String> getReturnByWCC(long appId) throws Exception{
        return null;
    }

    /**
     * 获取实时召测返回结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByRRD(long appId) throws Exception{
        return null;
    }
}
