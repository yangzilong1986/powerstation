/*
 * 主站轮召处理器
 */
package pep.bp.processor.polling;

import java.util.List;
import java.util.logging.Level;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import pep.codec.protocol.gb.gb376.PmPacket376;
import pep.common.exception.BPException;
import pep.mina.common.PepCommunicatorInterface;
import pep.mina.common.RtuRespPacketQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pep.bp.db.TaskService;
import pep.bp.model.CommanddItemDAO;

import pep.bp.model.TermTaskDAO;
import pep.bp.realinterface.mto.CollectObject;
import pep.bp.realinterface.mto.CollectObject_TransMit;
import pep.bp.realinterface.mto.CommandItem;
import pep.bp.utils.AFNType;
import pep.bp.utils.BaudRate;
import pep.bp.utils.Converter;
import pep.bp.utils.MeterType;
import pep.bp.utils.SerialPortPara;
import pep.codec.utils.BcdUtils;
import pep.system.SystemConst;

/**
 *
 * @author Thinkpad
 */
public class PollingJob implements Job {

    private final static Logger log = LoggerFactory.getLogger(PollingJob.class);
    private TaskService taskService;
    private PepCommunicatorInterface pepCommunicator;//通信代理器
    private RtuRespPacketQueue respQueue;//返回报文队列
    private ApplicationContext cxt;
    private Converter converter;
    private int circleUnit;
    private int sequenceCode = 0;

    private int getsequenceCode() {
        return sequenceCode++;
    }

    public PollingJob(PepCommunicatorInterface pepCommunicator, int circleUnit) {
        cxt = new ClassPathXmlApplicationContext(SystemConst.SPRING_BEANS);
        taskService = (TaskService) cxt.getBean("taskService");
        converter = (Converter) cxt.getBean("converter");
        this.pepCommunicator = pepCommunicator;
        this.circleUnit = circleUnit;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<TermTaskDAO> TermTaskList = taskService.getPollingTask(circleUnit);
        if (null != TermTaskList) {
            for (TermTaskDAO task : TermTaskList) {
                try {
                    DoTask(task);
                } catch (BPException ex) {
                    java.util.logging.Logger.getLogger(PollingJob.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void DoTask(TermTaskDAO task) throws BPException {
        List<CommanddItemDAO> CommandItemList = task.getCommandItemList();
        for (CommanddItemDAO commandItemDao : CommandItemList) {
            CollectObject object = new CollectObject();
            CommandItem Item = new CommandItem();
            String CommandCode = commandItemDao.getCommandItemCode();
            Item.setIdentifier(CommandCode);
            object.AddCommandItem(Item);

            object.setLogicalAddr(task.getLogicAddress());
            object.setMpSn(new int[]{task.getGp_sn()});
            
            if(task.getAFN() == AFNType.AFN_TRANSMIT){  //中继任务
                CollectObject_TransMit object_trans = new CollectObject_TransMit();
                object_trans.setFuncode((byte)1);//读数据
                object_trans.setMeterAddr(task.getGp_addr()); //表地址
                object_trans.setMeterType(MeterType.Meter645);
                object_trans.setPort((byte)1);
                object_trans.setMpSn(task.getGp_sn());
                SerialPortPara spp = new SerialPortPara();
                spp.setBaudrate(BaudRate.bps_9600);
                spp.setCheckbit(0);
                spp.setStopbit(1);
                spp.setOdd_even_bit(1);
                spp.setDatabit(8);
                object_trans.setSerialPortPara(spp);
                object_trans.setTerminalAddr(task.getLogicAddress());
                object_trans.setWaitforByte((byte)5);
                object_trans.setWaitforPacket((byte)10);
                object_trans.addCommandItem(Item);
                List<PmPacket376> packetList =  converter.CollectObject_TransMit2PacketList(object_trans,  new StringBuffer());
                if(null != packetList){
                    for(PmPacket376 pack:packetList){
                        pack.getAddress().setMastStationId((byte) 2);
                        this.pepCommunicator.SendPacket(this.getsequenceCode(), pack,1);
                        log.info("向终端：["+task.getLogicAddress()+"] 下发轮召报文（命令项;" + Item.getIdentifier() + "）：" + BcdUtils.binArrayToString(pack.getValue()));
                    }
                }
            }
                
            else {
                PmPacket376 packet = new PmPacket376();
                packet.getAddress().setMastStationId((byte) 2);
                converter.CollectObject2Packet(object, packet, task.getAFN(), new StringBuffer(), new StringBuffer());
                pepCommunicator.SendPacket(this.getsequenceCode(), packet,1);
                log.info("向终端：["+task.getLogicAddress()+"] 下发轮召报文（命令项;" + Item.getIdentifier() + "）：" + BcdUtils.binArrayToString(packet.getValue()));
            }            
        }
    }
}
