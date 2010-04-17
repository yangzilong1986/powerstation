/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

/**
 *
 * @author luxiaochung
 */
public interface PmPacketDT {
    public PmPacketDT setValue(byte[] value);
    public byte[] getValue();
}
