/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.db;

import pep.bp.model.Dto;
import pep.codec.protocol.gb.gb376.Packet376Event36;
import pep.codec.protocol.gb.gb376.PmPacket376EventBase;

/**
 *
 * @author Thinkpad
 */
public interface DataService {

    public void insertRecvData(Dto data);

    public void insertLBEvent(String rtua, Packet376Event36 event);

    public void insertEvent(String rtua, PmPacket376EventBase event);
}
