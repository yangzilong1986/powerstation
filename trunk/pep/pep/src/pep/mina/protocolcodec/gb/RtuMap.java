/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.mina.protocolcodec.gb;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author luxiaochung
 */
public class RtuMap {
    private Map<String,RtuCommunicationInfo> rtuSessionMap;

    public RtuMap(){
        rtuSessionMap = new TreeMap<String,RtuCommunicationInfo> ();
    }

    public synchronized void putRtuSession(String rtua, RtuCommunicationInfo rtuInfo){
        rtuSessionMap.put(rtua, rtuInfo);
    }

    public synchronized RtuCommunicationInfo getRtuSession(String rtua){
        return rtuSessionMap.get(rtua);
    }

    public synchronized void deleteRtuSession(String rtua){
        rtuSessionMap.remove(rtua);
    }
}
