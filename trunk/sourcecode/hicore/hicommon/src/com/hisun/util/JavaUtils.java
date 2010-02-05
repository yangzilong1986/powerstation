package com.hisun.util;

import java.beans.Introspector;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.WeakHashMap;

public class JavaUtils {
    public static final char NL = 10;
    public static final char CR = 13;
    public static final String LS = System.getProperty("line.separator", new Character('\n').toString());

    static final String[] keywords = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while"};

    static final Collator englishCollator = Collator.getInstance(Locale.ENGLISH);
    static final char keywordPrefix = 95;
    private static WeakHashMap enumMap = new WeakHashMap();

    private static boolean checkForAttachmentSupport = true;
    private static boolean attachmentSupportEnabled = false;

    public static Class getWrapperClass(Class primitive) {
        if (primitive == Integer.TYPE) return Integer.class;
        if (primitive == Short.TYPE) return Short.class;
        if (primitive == Boolean.TYPE) return Boolean.class;
        if (primitive == Byte.TYPE) return Byte.class;
        if (primitive == Long.TYPE) return Long.class;
        if (primitive == Double.TYPE) return Double.class;
        if (primitive == Float.TYPE) return Float.class;
        if (primitive == Character.TYPE) {
            return Character.class;
        }
        return null;
    }

    public static String getWrapper(String primitive) {
        if (primitive.equals("int")) return "Integer";
        if (primitive.equals("short")) return "Short";
        if (primitive.equals("boolean")) return "Boolean";
        if (primitive.equals("byte")) return "Byte";
        if (primitive.equals("long")) return "Long";
        if (primitive.equals("double")) return "Double";
        if (primitive.equals("float")) return "Float";
        if (primitive.equals("char")) {
            return "Character";
        }
        return null;
    }

    public static Class getPrimitiveClass(Class wrapper) {
        if (wrapper == Integer.class) return Integer.TYPE;
        if (wrapper == Short.class) return Short.TYPE;
        if (wrapper == Boolean.class) return Boolean.TYPE;
        if (wrapper == Byte.class) return Byte.TYPE;
        if (wrapper == Long.class) return Long.TYPE;
        if (wrapper == Double.class) return Double.TYPE;
        if (wrapper == Float.class) return Float.TYPE;
        if (wrapper == Character.class) {
            return Character.TYPE;
        }
        return null;
    }

    public static Class getPrimitiveClassFromName(String primitive) {
        if (primitive.equals("int")) return Integer.TYPE;
        if (primitive.equals("short")) return Short.TYPE;
        if (primitive.equals("boolean")) return Boolean.TYPE;
        if (primitive.equals("byte")) return Byte.TYPE;
        if (primitive.equals("long")) return Long.TYPE;
        if (primitive.equals("double")) return Double.TYPE;
        if (primitive.equals("float")) return Float.TYPE;
        if (primitive.equals("char")) {
            return Character.TYPE;
        }
        return null;
    }

    public static boolean isJavaId(String id) {
        if ((id == null) || (id.equals("")) || (isJavaKeyword(id))) return false;
        if (!(Character.isJavaIdentifierStart(id.charAt(0)))) return false;
        for (int i = 1; i < id.length(); ++i)
            if (!(Character.isJavaIdentifierPart(id.charAt(i)))) return false;
        return true;
    }

    public static boolean isJavaKeyword(String keyword) {
        return (Arrays.binarySearch(keywords, keyword, englishCollator) >= 0);
    }

    public static String makeNonJavaKeyword(String keyword) {
        return '_' + keyword;
    }

    public static String getLoadableClassName(String text) {
        if ((text == null) || (text.indexOf("[") < 0) || (text.charAt(0) == '[')) {
            return text;
        }
        String className = text.substring(0, text.indexOf("["));
        if (className.equals("byte")) className = "B";
        else if (className.equals("char")) className = "C";
        else if (className.equals("double")) className = "D";
        else if (className.equals("float")) className = "F";
        else if (className.equals("int")) className = "I";
        else if (className.equals("long")) className = "J";
        else if (className.equals("short")) className = "S";
        else if (className.equals("boolean")) className = "Z";
        else className = "L" + className + ";";
        int i = text.indexOf("]");
        while (i > 0) {
            className = "[" + className;
            i = text.indexOf("]", i + 1);
        }
        return className;
    }

    public static String getTextClassName(String text) {
        if ((text == null) || (text.indexOf("[") != 0)) {
            return text;
        }
        String className = "";
        int index = 0;

        while ((index < text.length()) && (text.charAt(index) == '[')) {
            ++index;
            className = className + "[]";
        }
        if (index < text.length()) {
            if (text.charAt(index) == 'B') className = "byte" + className;
            else if (text.charAt(index) == 'C') className = "char" + className;
            else if (text.charAt(index) == 'D') className = "double" + className;
            else if (text.charAt(index) == 'F') className = "float" + className;
            else if (text.charAt(index) == 'I') className = "int" + className;
            else if (text.charAt(index) == 'J') className = "long" + className;
            else if (text.charAt(index) == 'S') className = "short" + className;
            else if (text.charAt(index) == 'Z') className = "boolean" + className;
            else {
                className = text.substring(index + 1, text.indexOf(";")) + className;
            }
        }
        return className;
    }

    public static String xmlNameToJava(String name) {
        if ((name == null) || (name.equals(""))) {
            return name;
        }
        char[] nameArray = name.toCharArray();
        int nameLen = name.length();
        StringBuffer result = new StringBuffer(nameLen);
        boolean wordStart = false;

        int i = 0;

        while ((i < nameLen) && (((isPunctuation(nameArray[i])) || (!(Character.isJavaIdentifierStart(nameArray[i])))))) {
            ++i;
        }
        if (i < nameLen) {
            result.append(nameArray[i]);

            wordStart = (!(Character.isLetter(nameArray[i]))) && (nameArray[i] != "_".charAt(0));
        } else if (Character.isJavaIdentifierPart(nameArray[0])) {
            result.append("_" + nameArray[0]);
        } else {
            result.append("_" + nameArray.length);
        }

        for (++i; i < nameLen; ++i) {
            char c = nameArray[i];

            if ((isPunctuation(c)) || (!(Character.isJavaIdentifierPart(c)))) {
                wordStart = true;
            } else {
                if ((wordStart) && (Character.isLowerCase(c))) {
                    result.append(Character.toUpperCase(c));
                } else {
                    result.append(c);
                }

                wordStart = (!(Character.isLetter(c))) && (c != "_".charAt(0));
            }
        }

        String newName = result.toString();

        if (Character.isUpperCase(newName.charAt(0))) {
            newName = Introspector.decapitalize(newName);
        }

        if (isJavaKeyword(newName)) {
            newName = makeNonJavaKeyword(newName);
        }
        return newName;
    }

    private static boolean isPunctuation(char c) {
        return (('-' == c) || ('.' == c) || (':' == c) || (183 == c) || (903 == c) || (1757 == c) || (1758 == c));
    }

    public static final String replace(String name, String oldT, String newT) {
        if (name == null) return "";

        StringBuffer sb = new StringBuffer(name.length() * 2);

        int len = oldT.length();
        try {
            int start = 0;
            int i = name.indexOf(oldT, start);

            while (i >= 0) {
                sb.append(name.substring(start, i));
                sb.append(newT);
                start = i + len;
                i = name.indexOf(oldT, start);
            }
            if (start < name.length()) sb.append(name.substring(start));
        } catch (NullPointerException e) {
        }
        return new String(sb);
    }

    public static boolean isEnumClass(Class cls) {
        Boolean b = (Boolean) enumMap.get(cls);
        if (b == null) {
            b = (isEnumClassSub(cls)) ? Boolean.TRUE : Boolean.FALSE;
            synchronized (enumMap) {
                enumMap.put(cls, b);
            }
        }
        return b.booleanValue();
    }

    private static boolean isEnumClassSub(Class cls) {
        try {
            Method[] methods = cls.getMethods();
            Method getValueMethod = null;
            Method fromValueMethod = null;
            Method setValueMethod = null;
            Method fromStringMethod = null;

            for (int i = 0; i < methods.length; ++i) {
                String name = methods[i].getName();

                if ((name.equals("getValue")) && (methods[i].getParameterTypes().length == 0)) {
                    getValueMethod = methods[i];
                } else if (name.equals("fromString")) {
                    Object[] params = methods[i].getParameterTypes();
                    if ((params.length == 1) && (params[0] == String.class)) {
                        fromStringMethod = methods[i];
                    }
                } else if ((name.equals("fromValue")) && (methods[i].getParameterTypes().length == 1)) {
                    fromValueMethod = methods[i];
                } else {
                    if ((!(name.equals("setValue"))) || (methods[i].getParameterTypes().length != 1)) continue;
                    setValueMethod = methods[i];
                }

            }

            if ((null != getValueMethod) && (null != fromStringMethod)) {
                return ((null != setValueMethod) && (setValueMethod.getParameterTypes().length == 1) && (getValueMethod.getReturnType() == setValueMethod.getParameterTypes()[0]));
            }

            return false;
        } catch (SecurityException e) {
        }
        return false;
    }

    public static String stackToString(Throwable e) {
        StringWriter sw = new StringWriter(1024);
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }

    public static final boolean isTrue(String value) {
        return (!(isFalseExplicitly(value)));
    }

    public static final boolean isTrueExplicitly(String value) {
        return ((value != null) && (((value.equalsIgnoreCase("true")) || (value.equals("1")) || (value.equalsIgnoreCase("yes")))));
    }

    public static final boolean isTrueExplicitly(Object value, boolean defaultVal) {
        if (value == null) return defaultVal;
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }
        if (value instanceof Integer) {
            return (((Integer) value).intValue() != 0);
        }
        if (value instanceof String) {
            return isTrueExplicitly((String) value);
        }
        return true;
    }

    public static final boolean isTrueExplicitly(Object value) {
        return isTrueExplicitly(value, false);
    }

    public static final boolean isTrue(Object value, boolean defaultVal) {
        return (!(isFalseExplicitly(value, !(defaultVal))));
    }

    public static final boolean isTrue(Object value) {
        return isTrue(value, false);
    }

    public static final boolean isFalse(String value) {
        return isFalseExplicitly(value);
    }

    public static final boolean isFalseExplicitly(String value) {
        return ((value == null) || (value.equalsIgnoreCase("false")) || (value.equals("0")) || (value.equalsIgnoreCase("no")));
    }

    public static final boolean isFalseExplicitly(Object value, boolean defaultVal) {
        if (value == null) return defaultVal;
        if (value instanceof Boolean) {
            return (!(((Boolean) value).booleanValue()));
        }
        if (value instanceof Integer) {
            return (((Integer) value).intValue() == 0);
        }
        if (value instanceof String) {
            return isFalseExplicitly((String) value);
        }
        return false;
    }

    public static final boolean isFalseExplicitly(Object value) {
        return isFalseExplicitly(value, true);
    }

    public static final boolean isFalse(Object value, boolean defaultVal) {
        return isFalseExplicitly(value, defaultVal);
    }

    public static final boolean isFalse(Object value) {
        return isFalse(value, true);
    }

    public static String mimeToJava(String mime) {
        if (("image/gif".equals(mime)) || ("image/jpeg".equals(mime))) {
            return "java.awt.Image";
        }
        if ("text/plain".equals(mime)) {
            return "java.lang.String";
        }
        if (("text/xml".equals(mime)) || ("application/xml".equals(mime))) {
            return "javax.xml.transform.Source";
        }
        if (("application/octet-stream".equals(mime)) || ("application/octetstream".equals(mime))) {
            return "org.apache.axis.attachments.OctetStream";
        }
        if ((mime != null) && (mime.startsWith("multipart/"))) {
            return "javax.mail.internet.MimeMultipart";
        }

        return "javax.activation.DataHandler";
    }

    public static String getUniqueValue(Collection values, String initValue) {
        int end;
        if (!(values.contains(initValue))) {
            return initValue;
        }

        StringBuffer unqVal = new StringBuffer(initValue);
        int beg = unqVal.length();
        while (Character.isDigit(unqVal.charAt(beg - 1))) {
            --beg;
        }
        if (beg == unqVal.length()) {
            unqVal.append('1');
        }
        int cur = --unqVal.length();
        while (true) {
            if (!(values.contains(unqVal.toString()))) break label193;
            if (unqVal.charAt(cur) >= '9') break;
            unqVal.setCharAt(cur, (char) (unqVal.charAt(cur) + '\1'));
        }

        do {
            if (cur-- <= beg) break label151;
        } while (unqVal.charAt(cur) >= '9');
        unqVal.setCharAt(cur, (char) (unqVal.charAt(cur) + '\1'));

        if (cur < beg) {
            label151:
            unqVal.insert(++cur, '1');
            ++end;
        }
        while (true) {
            if (cur < end) ;
            unqVal.setCharAt(++cur, '0');
        }

        label193:
        return unqVal.toString();
    }

    public static abstract interface ConvertCache {
        public abstract void setConvertedValue(Class paramClass, Object paramObject);

        public abstract Object getConvertedValue(Class paramClass);

        public abstract Class getDestClass();
    }
}