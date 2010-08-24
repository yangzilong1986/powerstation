/*
 * 主站轮召处理器
 */
package pep.bp.processor.planManager;

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
import pep.bp.db.PSService;
import pep.bp.db.RTTaskService;

import pep.bp.model.PSDAO;
import pep.bp.model.RealTimeTaskDAO;

import pep.bp.realinterface.mto.CollectObject_TransMit;
import pep.bp.realinterface.mto.CommandItem;

import pep.bp.utils.BaudRate;
import pep.bp.utils.Converter;
import pep.bp.utils.MeterType;
import pep.bp.utils.SerialPortPara;
import pep.bp.utils.UtilsBp;
import pep.codec.utils.BcdUtils;
import pep.system.SystemConst;

/**
 *
 * @author Thinkpad
 */
public class PlanJob implements Job {

    private final static Logger log = LoggerFactory.getLogger(PlanJob.class);
    private PSService psService;
    private RTTaskService rtTaskService;
    private PepCommunicatorInterface pepCommunicator;//通信代理器
    private RtuRespPacketQueue respQueue;//返回报文队列
    private ApplicationContext cxt;
    private Converter converter;
    private int circleUnit;
    private int sequenceCode = 0;

    private int getsequenceCode() {
        return sequenceCode++;
    }

    public PlanJob(PepCommunicatorInterface pepCommunicator, int circleUnit) {
        cxt = new ClassPathXmlApplicationContext(SystemConst.SPRING_BEANS);
        psService = (PSService) cxt.getBean("psService");
        rtTaskService = (RTTaskService) cxt.getBean("rtTaskService");
        converter = (Converter) cxt.getBean("converter");
        this.pepCommunicator = pepCommunicator;
        this.circleUnit = circleUnit;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String currentDay = UtilsBp.getThisDay();
        String currentHour = UtilsBp.getThisHour();
        List<PSDAO> psList = psService.getTestPSList(currentDay, currentHour);
        if (null != psList) {
            for (PSDAO ps : psList) {
                try {
                    DoPlan(this.pepCommunicator, ps);
                } catch (BPException ex) {
                    java.util.logging.Logger.getLogger(PlanJob.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void DoPlan(PepCommunicatorInterface pepCommunicator, PSDAO ps) throws BPException {
        CommandItem Item = new CommandItem();
        Item.setIdentifier("8000C037");
        CollectObject_TransMit object_trans = new CollectObject_TransMit();
        object_trans.setFuncode((byte) 4);//写数据（试跳）
        object_trans.setMeterAddr(ps.getGp_addr()); //表地址
        object_trans.setMeterType(MeterType.Meter645);
        object_trans.setPort((byte) 1);
        SerialPortPara spp = new SerialPortPara();
        spp.setBaudrate(BaudRate.bps_9600);
        spp.setCheckbit(0);
        spp.setStopbit(1);
        spp.setOdd_even_bit(1);
        spp.setDatabit(8);
        object_trans.setSerialPortPara(spp);
        object_trans.setTerminalAddr(ps.getLogicAddress());
        object_trans.setWaitforByte((byte) 5);
        object_trans.setWaitforPacket((byte) 10);
        object_trans.addCommandItem(Item);
        List<PmPacket376> packetList = converter.CollectObject_TransMit2PacketList(object_trans, new StringBuffer());
        if (null != packetList) {
            for (PmPacket376 pack : packetList) {
                //pack.getAddress().setMastStationId((byte) 2);

                RealTimeTaskDAO task = new RealTimeTaskDAO();
                task.setSendmsg(BcdUtils.binArrayToString(pack.getValue()));
                task.setSequencecode(rtTaskService.getSequnce());
                task.setLogicAddress(ps.getLogicAddress());
                task.setTask_type("2");
                this.rtTaskService.insertTask(task);

               // pepCommunicator.SendPacket(this.getsequenceCode(), pack);
                log.info("向终端：[" + ps.getLogicAddress() + "] 下发计划试跳报文（命令项;" + Item.getIdentifier() + "）：" + BcdUtils.binArrayToString(pack.getValue()));
            }
        }


    }
}