/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.utils;

/**
 *
 * @author Thinkpad
 */
public interface AFNType {
    static byte AFN_CONFIRM = 0x00; //AFN：确认/否认
    static byte AFN_RESET = 0x00; //AFN：复位
    static byte AFN_SETPARA = 0x00; //AFN：设置参数
    static byte AFN_GETPARA = 0x00; //AFN：读取参数
    static byte AFN_TRANSMIT = 0x00; //AFN：数据转发
    static byte AFN_READREALDATA = 0x00; //AFN：读实时数据
}
