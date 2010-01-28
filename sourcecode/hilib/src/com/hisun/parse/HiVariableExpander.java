package com.hisun.parse;


import com.hisun.message.HiContext;
import com.hisun.util.HiSymbolExpander;
import org.apache.commons.digester.substitution.VariableExpander;

public class HiVariableExpander implements VariableExpander {
    public String expand(String param) {

        HiContext ctx = HiContext.getCurrentContext();

        HiSymbolExpander symbolExpander = new HiSymbolExpander(ctx, "@PARA");

        return symbolExpander.expandSymbols(param);
    }
}