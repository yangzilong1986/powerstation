/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Thinkpad
 */
public class UtilsBp {
    public static String getNow(){
        Date nowTime=new Date(System.currentTimeMillis()); //取系统时间
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        return sDateFormat.format(nowTime);
    }
}
