/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.common.message;

/**
 *
 * @author Thinkpad
 */
public class MessageType {

    private final String desc;
    public static final MessageType MSG_INVAL = new MessageType("非法类型报文");

    private MessageType(String msgType) {
        desc = msgType;
    }

    public String toString() {
        return desc;
    }
}
