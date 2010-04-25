/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.mina.common;

import pep.codec.protocol.gb.PmPacket;

/**
 *
 * @author luxiaochung
 */
public class SequencedPmPacket {
    public enum Status{SUSSESS,TO_BE_CONTINUE,TIME_OUT,NOT_ONLINE;};
    public int sequence;
    public PmPacket pack;
    public Status status;

    public SequencedPmPacket(int sequence, PmPacket pack,Status status){
        this.sequence = sequence;
        this.pack = pack;
        this.status = status;
    }
}
