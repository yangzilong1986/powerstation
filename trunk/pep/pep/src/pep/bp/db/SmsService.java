/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import java.util.List;
import pep.bp.model.Dto;
import pep.bp.model.SMSDAO;
import pep.codec.protocol.gb.gb376.Packet376Event36;
import pep.codec.protocol.gb.gb376.PmPacket376EventBase;

/**
 *
 * @author Thinkpad
 */
public interface SmsService {

    public List<SMSDAO> getRecvSMS();

    public void deleteRecvSMS(int smsid);

}
