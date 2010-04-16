/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.mina.protocolcodec.gb;

import java.util.Map;
import java.util.TreeMap;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author luxiaochung
 */
public class RtuMap {
    private Map<String,IoSession> rtuSessionMap;

    public RtuMap(){
        rtuSessionMap = new TreeMap<String,IoSession> ();
    }

    public synchronized void putRtuSession(String rtua, IoSession session){
        rtuSessionMap.put(rtua, session);
    }

    public synchronized IoSession getRtuSession(String rtua){
        return rtuSessionMap.get(rtua);
    }

    public synchronized void deleteRtuSession(String rtua){
        rtuSessionMap.remove(rtua);
    }
}
