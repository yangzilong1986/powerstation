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
public class DataTypeA9A25Base {

    protected long value;
    protected boolean isNull;

    protected DataTypeA9A25Base() {
        super();
    }

    public void setArray(byte[] array) {
        setArray(array, 0);
    }

    public void setArray(byte[] array, int beginPosition) {
        if (array.length - beginPosition < 3) {
            throw new IllegalArgumentException();
        } else {
            try {
                boolean s = (array[beginPosition + 2] & 0x80) == 0x80;
                this.value = BcdUtils.bcdToInt(array[beginPosition]);
                this.value += BcdUtils.bcdToInt(array[beginPosition + 1]) * 100;
                this.value += BcdUtils.bcdToInt(array[beginPosition + 2] & 0x7F) * 10000;
                if (s) {
                    this.value *= -1;
                }
                this.isNull = false;
            } catch (Exception ex) {
                this.isNull = true;
            }
        }
    }

    public byte[] getArray() {
        boolean s = this.value < 0;
        long base = s ? -this.value : this.value;
        byte[] rslt = BcdUtils.intTobcd(this.value, 3);
        if (s) {
            rslt[2] |= 0x80;
        }

        return rslt;
    }
}
