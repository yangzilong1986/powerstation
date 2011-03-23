/*
 * 解码器基类
 */
package pep.bp.utils.decoder;


import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pep.bp.model.Dto;
import pep.bp.realinterface.conf.ProtocolConfig;


/**
 *
 * @author Thinkpad
 */
public abstract class Decoder {

    
    protected ProtocolConfig config;
    private final static Logger log = LoggerFactory.getLogger(Decoder.class);

    


    public abstract Map<String, Map<String, String>> decode(Object pack);

    public abstract void decode(Object pack, Dto dto);

    public abstract Map<String, Map<String, String>> decode_TransMit(Object pack, boolean IsWriteBack);

    public abstract void decode_TransMit(Object pack, Dto dto);
    /**
     * @return the config
     */
    public ProtocolConfig getConfig() {
        return config;
    }

    /**
     * @param config the config to set
     */
    public void setConfig(ProtocolConfig config) {
        this.config = config;
    }
}
