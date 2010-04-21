/*
 * 辅助项
 */
package pep.bp.realinterface.mto;

/**
 *
 * @author Thinkpad
 */
public class AssistParam {

    private String key;            //辅助项标识
    private String value;          //辅助项值

    public AssistParam() {
    }

    public AssistParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
