/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.common.message;

/**
 *
 * @author Thinkpad
 */
public interface MessageLoader {

    IMessage loadMessage(String serializedString);

    String serializeMessage(IMessage message);
}
