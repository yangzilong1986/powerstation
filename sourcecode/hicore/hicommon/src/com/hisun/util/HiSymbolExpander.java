package com.hisun.util;

import com.hisun.message.HiContext;

public class HiSymbolExpander {
    private HiContext _source;
    private String _namespace;
    private static final int STATE_START = 0;
    private static final int STATE_DOLLAR = 1;
    private static final int STATE_COLLECT_SYMBOL_NAME = 2;

    public HiSymbolExpander(HiContext source, String namespace) {
        this._source = source;
        this._namespace = namespace;
    }

    public String expandSymbols(String text) {
        StringBuffer result = new StringBuffer(text.length());
        char[] buffer = text.toCharArray();
        int state = 0;
        int blockStart = 0;
        int blockLength = 0;
        int symbolStart = -1;
        int symbolLength = 0;
        int i = 0;
        int braceDepth = 0;
        boolean anySymbols = false;

        while (i < buffer.length) {
            char ch = buffer[i];

            switch (state) {
                case 0:
                    if (ch == '$') {
                        state = 1;
                        ++i;
                    }

                    ++blockLength;
                    ++i;
                    break;
                case 1:
                    if (ch == '{') {
                        state = 2;
                        ++i;

                        symbolStart = i;
                        symbolLength = 0;
                        braceDepth = 1;
                    }

                    if (ch == '$') {
                        anySymbols = true;

                        if (blockLength > 0) {
                            result.append(buffer, blockStart, blockLength);
                        }
                        result.append(ch);

                        ++i;
                        blockStart = i;
                        blockLength = 0;
                        state = 0;
                    }

                    ++blockLength;

                    state = 0;
                    break;
                case 2:
                    if (ch != '}') {
                        if (ch == '{') {
                            ++braceDepth;
                        }
                        ++i;
                        ++symbolLength;
                    }

                    --braceDepth;

                    if (braceDepth > 0) {
                        ++i;
                        ++symbolLength;
                    }

                    if (symbolLength == 0) {
                        blockLength += 3;
                    }

                    if (blockLength > 0) {
                        result.append(buffer, blockStart, blockLength);
                    }
                    if (symbolLength > 0) {
                        String variableName = text.substring(symbolStart, symbolStart + symbolLength);

                        result.append(expandSymbol(variableName));

                        anySymbols = true;
                    }

                    ++i;
                    blockStart = i;
                    blockLength = 0;

                    state = 0;

                    continue;
            }

        }

        if (!(anySymbols)) {
            return text;
        }

        if (state == 1) {
            ++blockLength;
        }
        if (state == 2) {
            blockLength += symbolLength + 2;
        }
        if (blockLength > 0) {
            result.append(buffer, blockStart, blockLength);
        }
        return result.toString();
    }

    private String expandSymbol(String name) {
        String value = null;
        if ((name.indexOf(64) == 0) && (name.indexOf(46) != -1)) value = this._source.getStrProp(name);
        else value = this._source.getStrProp(this._namespace, name);
        if (value != null) {
            return value;
        }
        return "${" + name + "}";
    }
}