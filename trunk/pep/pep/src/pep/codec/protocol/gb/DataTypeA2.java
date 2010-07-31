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
public class DataTypeA2 {

    private double value;
    private boolean isNull;

    public DataTypeA2(double value) {
        this.value = value;
    }

    public DataTypeA2(byte[] array) {
        setArray(array);
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    public final void setArray(byte[] array) {
        setArray(array, 0);
    }

    public void setArray(byte[] array, int beginPosition) {
        if (array.length - beginPosition < 2) {
            throw new IllegalArgumentException();
        } else {
            try {
                int val = BcdUtils.bcdToInt(array[beginPosition]) + (array[beginPosition + 1] & 0x0f) * 100;
                byte g = (byte) ((array[beginPosition + 1] & 0xE0) >> 5);
                double s = (array[beginPosition + 1] & 0x10) != 0 ? -1.0 : 1.0;
                switch (g) {
                    case 0x00:
                        this.value = val * s * 10000;
                        break;
                    case 0x01:
                        this.value = val * s * 1000;
                        break;
                    case 0x02:
                        this.value = val * s * 100;
                        break;
                    case 0x03:
                        this.value = val * s * 10;
                        break;
                    case 0x04:
                        this.value = val * s;
                        break;
                    case 0x05:
                        this.value = val * s / 10;
                        break;
                    case 0x06:
                        this.value = val * s / 100;
                        break;
                    case 0x07:
                        this.value = val * s / 1000;
                        break;
                }
                this.isNull = false;
            } catch (Exception ex) {
                this.isNull = true;
            }
        }
    }

    public byte[] getArray() {
        byte[] rslt = new byte[2];
        boolean neg = (this.value < 0);
        double base = java.lang.Math.abs(this.value);
        long lg = java.lang.Math.round(java.lang.Math.floor(java.lang.Math.log10(base)));

        int lgs = (int) (6 - lg);
        long base2 = java.lang.Math.round(base / java.lang.Math.pow(10, lg - 2));

        rslt[0] = BcdUtils.intToBcd((byte) (base2 % 100));
        rslt[1] = (byte) (base2 / 100);
        if (neg) {
            rslt[1] = (byte) (rslt[1] | 0x10);
        }
        rslt[1] = (byte) (rslt[1] | (lgs << 5));

        return rslt;
    }

    @Override
    public String toString() {
        if (this.isNull) return "";
        return (new Double(value)).toString();
    }
}
