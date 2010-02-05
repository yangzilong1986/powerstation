package com.hisun.util;

import com.hisun.common.util.HiByteUtil;
import com.hisun.exception.HiException;
import org.apache.commons.lang.ArrayUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class HiByteBuffer implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte[] _buffer;
    private int _length;
    private int _capacity;
    private int _scale;

    public HiByteBuffer() {
        this(20);
    }

    public HiByteBuffer(int sz) {
        this._buffer = null;

        this._length = 0;

        this._capacity = 0;

        this._scale = 64;

        this._buffer = new byte[sz];
        this._capacity = sz;
        this._length = 0;
    }

    public HiByteBuffer(int sz, int scale) {
        this._buffer = null;

        this._length = 0;

        this._capacity = 0;

        this._scale = 64;

        this._buffer = new byte[sz];
        this._capacity = sz;
        this._length = 0;
        this._scale = scale;
    }

    public HiByteBuffer(byte[] data) {
        this._buffer = null;

        this._length = 0;

        this._capacity = 0;

        this._scale = 64;

        append(data);
    }

    public HiByteBuffer(byte[] data, boolean isRef) {
        this._buffer = null;

        this._length = 0;

        this._capacity = 0;

        this._scale = 64;

        if (isRef) {
            this._buffer = data;
            this._length = data.length;
        } else {
            append(data);
        }
    }

    public void append(byte[] data, int offset, int length) {
        if (length == 0) return;
        if (this._capacity <= length) {
            byte[] buffer1 = new byte[this._length + getScaleSize(length)];
            if (this._buffer != null) System.arraycopy(this._buffer, 0, buffer1, 0, this._length);
            this._buffer = buffer1;
        }

        System.arraycopy(data, offset, this._buffer, this._length, length);
        this._length += length;
        this._capacity = (this._buffer.length - this._length);
    }

    public void append(byte[] data) {
        if (data == null) {
            data = new byte[0];
        }
        append(data, 0, data.length);
    }

    public void clear() {
        this._length = 0;
        this._capacity = this._buffer.length;
    }

    public void append(String data) {
        append(data.getBytes());
    }

    public void append(byte b) {
        append(new byte[]{b});
    }

    public void append(int b) {
        append((byte) b);
    }

    public void replace(int idx, String data) {
        replace(idx, data.getBytes());
    }

    public void replace(int idx, byte b) {
        replace(idx, new byte[]{b});
    }

    public void replace(int idx, int b) {
        replace(idx, (byte) b);
    }

    public void replace(int idx, byte[] bs) {
        replace(idx, bs, 0, bs.length);
    }

    public void replace(int idx, byte[] bs, int offset, int len) {
        if (len == 0) {
            return;
        }

        for (int i = 0; (i < len) && (idx + i < this._length); ++i) {
            this._buffer[(idx + i)] = bs[(i + offset)];
        }
        append(bs, i + offset, len - i);
    }

    public void insert(int idx, String data) {
        insert(idx, data.getBytes());
    }

    public void insert(int idx, byte b) {
        insert(idx, new byte[]{b});
    }

    public void insert(int idx, int b) {
        insert(idx, (byte) b);
    }

    public void insert(int idx, byte[] bs) {
        insert(idx, bs, 0, bs.length);
    }

    public void insert(int idx, byte[] bs, int offset, int length) {
        if (length == 0) {
            return;
        }
        int len = ((idx > this._length) ? idx - this._length : 0) + length;
        if (this._capacity <= len) {
            byte[] buffer1 = new byte[this._length + getScaleSize(len)];
            if (this._buffer != null) {
                if (idx <= this._length) {
                    System.arraycopy(this._buffer, 0, buffer1, 0, idx);
                    System.arraycopy(this._buffer, idx, buffer1, idx + length, this._length - idx);
                } else {
                    System.arraycopy(this._buffer, 0, buffer1, 0, this._length);
                }
            }
            this._buffer = buffer1;
        } else {
            System.arraycopy(this._buffer, idx, this._buffer, idx + length, this._length - idx);
        }

        System.arraycopy(bs, offset, this._buffer, idx, length);
        this._length += len;
        this._capacity = (this._buffer.length - len);
    }

    public void writeInt(int i) {
        append(HiByteUtil.intToByteArray(i));
    }

    public void writeShort(short s) {
        append(HiByteUtil.shortToByteArray(s));
    }

    public void writeLong(long l) {
        append(HiByteUtil.longToByteArray(l));
    }

    public byte[] getBytes() {
        if (this._length == 0) {
            return new byte[0];
        }
        byte[] buffer = new byte[this._length];
        System.arraycopy(this._buffer, 0, buffer, 0, this._length);
        return buffer;
    }

    public int length() {
        return this._length;
    }

    public String substr(int offset, int length) {
        return new String(subbyte(offset, length));
    }

    public byte[] subbyte(int offset, int length) {
        if (offset + length > this._length) {
            throw new IllegalArgumentException(offset + "+" + length + ">" + this._length);
        }

        byte[] buffer = new byte[length];
        System.arraycopy(this._buffer, offset, buffer, 0, length);
        return buffer;
    }

    public byte charAt(int offset) {
        return this._buffer[offset];
    }

    public int indexOf(int b, int offset) {
        if (offset > this._length) {
            throw new IllegalArgumentException(offset + ">" + this._length);
        }
        for (int i = offset; i < this._length; ++i) {
            if (this._buffer[i] == b) return i;
        }
        return -1;
    }

    public int indexOf(byte[] bb, int offset) {
        if ((bb == null) || (bb.length == 0) || (offset < 0)) {
            return -1;
        }
        while (true) {
            offset = ArrayUtils.indexOf(this._buffer, bb[0], offset);

            if (offset == -1) {
                return -1;
            }

            if (bb.length > this._length - offset) {
                return -1;
            }

            int i = 0;
            for (i = 0; i < bb.length; ++i) {
                if (this._buffer[(offset + i)] != bb[i]) {
                    break;
                }
            }
            if (i == bb.length) {
                return offset;
            }

            ++offset;
        }
    }

    public void repeat(byte b, int num) {
        if (num <= 0) {
            return;
        }
        byte[] nb = new byte[num];
        for (int i = 0; i < num; ++i) {
            nb[i] = b;
        }
        append(nb, 0, num);
    }

    public String toString() {
        if (this._buffer == null) return null;
        return new String(this._buffer, 0, this._length);
    }

    public String toString(String encoding) throws HiException {
        try {
            if (this._buffer == null) return null;
            return new String(this._buffer, 0, this._length, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new HiException(e);
        }
    }

    public byte[] remove(int offset, int length) {
        byte[] tmps = subbyte(offset, length);

        System.arraycopy(this._buffer, offset + length, this._buffer, offset, this._buffer.length - offset - length);

        return tmps;
    }

    private int getScaleSize(int len) {
        if (len / this._scale == 0) {
            return this._scale;
        }
        return ((len / this._scale + 1) * this._scale);
    }

    public static void main(String[] args) {
        HiByteBuffer buf = new HiByteBuffer();
        buf.append("333");
        buf.append("444");
        buf.insert(2, "555");
        buf.replace(2, "88888888888".getBytes(), 3, 5);
        System.out.println(buf.toString());
    }
}