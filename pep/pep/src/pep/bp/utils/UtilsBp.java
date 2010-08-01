/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.bp.utils;

import java.text.SimpleDateFormat;


/**
 *
 * @author Thinkpad
 */
public class UtilsBp {
    public static String getNow(){
        SimpleDateFormat sDateFormat   =   new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;

    }
}
