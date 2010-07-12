/*
 * 实时通信接口
 */

package pep.bp.realinterface;
import java.util.*;
import pep.bp.realinterface.mto.*;
/**
 *
 * @author Thinkpad
 */
public interface ICollectInterface {
    /**
     * 参数设置
     * @param MTO 消息传输对象
     * @return 回执码(-1:表示失败)
     * @throws Exception
     */
    public long writeParameters(MessageTranObject MTO)throws Exception;

    /**
     * 参数读取
     * @param MTO 消息传输对象
     * @return 回执码（-1:表示失败）
     * @throws Exception
     */
    public long readParameters(MessageTranObject MTO)throws Exception;

    /**
     * 下发复位命令
     * @param MTO
     * @return 回执码（-1:表示失败）
     * @throws Exception
     */
    public long writeResetCommands(MessageTranObject MTO)throws Exception;


    /**
     * 实时召测
     * @param MTO
     * @return
     * @throws Exception
     */
    public long readData(MessageTranObject MTO)throws Exception;


    /**
     * 透明转发
     * @param MTO
     * @return
     * @throws Exception
     */
    public long transmitMsg(MessageTranObject MTO)throws Exception;

    /**
     * 获取参数设置结果
     * @param appId 回执码
     * @return 返回结果<"zdljdz#cldxh#commanditem", "result">
     * @throws Exception
     */
    public Map<String, String> getReturnByWriteParameter(long appId) throws Exception;
    
    /**
     * 获取参数设置结果
     * @param appId
     * @return 返回JSon格式结果
     * @throws Exception
     */
    public  String getReturnByWriteParameter_Json(long appId) throws Exception;
    
    /**
     * 获取参数读取结果
     * @param appId 回执码
     * @return 返回结果<"zdljdz#cldxh#commanditem", <"dataitem", "datavalue">>
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByReadParameter(long appId) throws Exception;

    /**
     * 获取参数读取结果
     * @param appId
     * @return 返回JSon格式结果
     * @throws Exception
     */
    public String getReturnByReadParameter_Json(long appId) throws Exception;


    /**
     * 获取复位操作结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, String> getReturnByWriteResetCommand(long appId) throws Exception;


    /**
     * 获取实时召测返回结果
     * @param appId
     * @return
     * @throws Exception
     */
    public Map<String, Map<String, String>> getReturnByReadData(long appId) throws Exception;
}
