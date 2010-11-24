/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pep.codec.protocol.gb;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA12Test {

    public DataTypeA12Test() {
    }

    @Test
    public void testSomeMethod() {
        DataTypeA12 a12 = new DataTypeA12(999999999999L);
        long value = a12.getValue();
        assertEquals(value,999999999999L);
    }

}