/*
 * 实时通信接口
 */

package pep.bp.realinterface;
import pep.bp.realinterface.mto.*;
/**
 *
 * @author Thinkpad
 */
public interface ICollectInterface {
    /**
     * 参数设置
     * @param MTO 消息传输对象
     * @return 回执码
     * @throws Exception
     */
    public long writeEquipmentParameters(MessageTranObject MTO)throws Exception;

    /**
     * 参数读取
     * @param MTO 消息传输对象
     * @return 回执码
     * @throws Exception
     */
    public long readEquipmentParameters(MessageTranObject MTO)throws Exception;

    /**
     * 下发复位命令
     * @param MTO
     * @return
     * @throws Exception
     */
    public long writeResetCommands(MessageTranObject MTO)throws Exception;

    /**
     * 下发控制命令
     * @param MTO
     * @return
     * @throws Exception
     */
    public long writeControlCommands(MessageTranObject MTO)throws Exception;

    
}
