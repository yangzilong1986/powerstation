/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.codec.protocol.gb;

import pep.codec.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA3 {

    private long value;
    private boolean isNull;

    public DataTypeA3(long value) {
        this.value = value;
    }

    public DataTypeA3(byte[] array) {
        setArray(array, 0);
    }

    public void setArray(byte[] array) {
        setArray(array, 0);
    }

    public final void setArray(byte[] array, int beginPosition) {
        if (array.length - beginPosition < 4) {
            throw new IllegalArgumentException();
        } else {
            try {
                boolean s = (array[beginPosition + 3] & 0x10) == 0x10;
                boolean g = (array[beginPosition + 3] & 0x40) == 0x40;
                this.value = BcdUtils.bcdToInt(array, beginPosition, 3);
                this.value += (array[beginPosition + 3] & 0x0F) * java.lang.Math.pow(10, 6);
                if (s) {
                    this.value *= (-1.0);
                }
                if (g) {
                    this.value *= 1000;
                }
            } catch (Exception ex) {
                this.isNull = true;
            }
        }
    }

    public byte[] getArray() {
        long val;
        boolean s = (this.value < 0);
        boolean g = (this.value >= java.lang.Math.pow(10, 7));

        val = s ? -1 * this.value : this.value;
        val = g ? val / 1000 : val;

        byte[] rslt = BcdUtils.intTobcd(val, 4);

        if (s) {
            rslt[3] |= 0x10;
        }
        if (g) {
            rslt[3] |= 0x40;
        }

        return rslt;
    }

    public long getValue() {
        return this.value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (this.isNull) return "";
        return (new Long(this.value)).toString();
    }
}
