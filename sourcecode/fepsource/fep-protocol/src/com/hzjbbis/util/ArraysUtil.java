package com.hzjbbis.util;

import java.util.ArrayList;
import java.util.List;

public class ArraysUtil {
    public static boolean contains(Object[] elements, Object element) {
        return (indexOf(elements, element) > -1);
    }

    public static int indexOf(Object[] elements, Object element) {
        if ((elements == null) || (element == null)) {
            return -1;
        }

        int index = -1;
        for (int i = 0; i < elements.length; ++i) {
            if (elements[i] == null) {
                continue;
            }

            if (elements[i].equals(element)) {
                index = i;
                break;
            }
        }

        return index;
    }

    public static List asList(Object[] elements) {
        return asList(elements, 0, elements.length);
    }

    public static List asList(Object[] elements, int offset, int length) {
        int size = length;
        if (offset + length >= elements.length) {
            size = elements.length - offset;
        }

        List l = new ArrayList(size);
        for (int i = 0; i < length; ++i) {
            int index = offset + i;
            if (index >= elements.length) {
                break;
            }
            l.add(elements[index]);
        }

        return l;
    }

    public static String asString(Object[] elements) {
        if (elements == null) {
            return "null";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < elements.length; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(elements[i].toString());
        }
        sb.append("]");

        return sb.toString();
    }
}