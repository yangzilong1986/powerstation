/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.mina.common;

import java.util.TimerTask;

/**
 *
 * @author luxiaochung
 */
public class RtuUnrespPacketChecker extends TimerTask {
    private PepCommunicatorInterface communicator;
    
    public RtuUnrespPacketChecker(){
        super();
    }
    
    public RtuUnrespPacketChecker(PepCommunicatorInterface communicator){
        this();
        this.communicator = communicator;
    }
    
    public void setCommunicator(PepCommunicatorInterface communicator){
        this.communicator = communicator;
    }
    
    public void run() {
        communicator.checkUndespPackets();
    }

}
