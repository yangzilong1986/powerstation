/*
 * 短信通知处理器
 */

package pep.bp.processor;

/**
 *
 * @author Thinkpad
 */
public class SMSCheckProcessor implements Runnable{
    private final long TIME_BETWEEN_CHECKING = 5*1000;
    
    private final String selectSql = "select a.id, d.logical_addr, e.gp_addr"+
        "  from a_sms_receive a, a_sms_send b, e_loubao_event c, c_terminal d, c_gp e"+
        "  where a.telecode=b.telecode and a.msg=b.tag and to_number(b.tag)=c.lbsj_id"+
        "        and c.gp_sn=e.gp_sn and e.term_id=d.term_id";
    
    private final String deleteSql = "delete a_sms_receive where id=:id";
    
    @Override
    public void run(){
        for (;;){
            try {
                for(){
                //SmsRespProcessor.receiveLoubaoOperateMsg(id, rtua, lbAddress);
                //delete 
                }
                Thread.sleep(TIME_BETWEEN_CHECKING);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
}
