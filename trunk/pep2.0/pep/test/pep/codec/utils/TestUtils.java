/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.utils;

/**
 *
 * @author luxiaochung
 */
public class TestUtils {
    public static boolean byteArrayEquals(byte[] source, byte[] dest){
        boolean result;

        result = source.length==dest.length;
        for (int i=0; i<source.length; i++){
            if (source[i]!=dest[i])
                return false;
        }
        return result;
    }

}
