/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.mina.common;

/**
 *
 * @author luxiaochung
 */
public interface RtuConnectListener {
    public void rtuConnect(RtuConnectEvent event);
    public void rtuDisconnect(RtuDisconnectEvent event);
}
