/*
 * 主站轮召处理器
 */
package pep.bp.processor.polling;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import pep.codec.protocol.gb.gb376.PmPacket376;
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
import pep.bp.realinterface.mto.CommandItem;
import pep.bp.utils.Converter;
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
                DoTask(this.pepCommunicator, task);
            }
        }
    }

    private void DoTask(PepCommunicatorInterface pepCommunicator, TermTaskDAO task) {
        List<CommanddItemDAO> CommandItemList = task.getCommandItemList();
        for (CommanddItemDAO commandItemDao : CommandItemList) {
            CollectObject object = new CollectObject();
            CommandItem Item = new CommandItem();
            Item.setIdentifier(commandItemDao.getCommandItemCode());
            object.AddCommandItem(Item);

            object.setLogicalAddr(task.getLogicAddress());
            object.setMpSn(new int[]{task.getGp_sn()});
            PmPacket376 packet = new PmPacket376();
            packet.getAddress().setMastStationId((byte) 2);
            converter.CollectObject2Packet(object, packet, task.getAFN(), new StringBuffer(), new StringBuffer());
            pepCommunicator.SendPacket(this.getsequenceCode(), packet);
            log.info("下发轮召报文（命令项;" + Item.getIdentifier() + "）：" + BcdUtils.binArrayToString(packet.getValue()));
        }
    }
}
