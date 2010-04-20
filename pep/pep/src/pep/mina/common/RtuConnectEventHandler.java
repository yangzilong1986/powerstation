/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.mina.common;

import java.util.ArrayList;

/**
 *
 * @author luxiaochung
 */
public class RtuConnectEventHandler {
    private ArrayList<RtuConnectListener> listeners = new ArrayList <RtuConnectListener> ();

    public void addRtuConnectListener(RtuConnectListener listener) {
        listeners.add(listener);
    }

    public void removeRtuConnectListener(RtuConnectListener listener) {
        listeners.remove(listener);
    }

    public void fireRtuConnect(String rtua) {
        RtuConnectEvent event = new RtuConnectEvent(rtua);
        for (RtuConnectListener listener: listeners)
            listener.rtuConnect(event);
    }

    public void fireRtuDisconnect(String rtua){
        RtuDisconnectEvent event = new RtuDisconnectEvent(rtua);
        for (RtuConnectListener listener: listeners)
            listener.rtuDisconnect(event);
    }
}
