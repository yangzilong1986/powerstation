package com.hisun.parse;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;

public class HiRule extends Rule {
    protected String methodName;
    protected String paramType;
    protected boolean useExactMatch;

    /**
     * @deprecated
     */
    public HiRule(Digester digester, String methodName) {
        this(methodName);
    }

    /**
     * @deprecated
     */
    public HiRule(Digester digester, String methodName, String paramType) {
        this(methodName, paramType);
    }

    public HiRule(String methodName) {
        this(methodName, null);
    }

    public HiRule(String methodName, String paramType) {
        this.methodName = null;

        this.paramType = null;

        this.useExactMatch = false;

        this.methodName = methodName;
        this.paramType = paramType;
    }

    public boolean isExactMatch() {
        return this.useExactMatch;
    }

    public void setExactMatch(boolean useExactMatch) {
        this.useExactMatch = useExactMatch;
    }

    public void end() throws Exception {
        Object child = this.digester.peek(0);
        Object parent = this.digester.peek(this.digester.getCount() - 1);

        if (super.getDigester().getLogger().isDebugEnabled()) {
            if (child == null) {
                this.digester.getLogger().debug("[HiRule]{" + this.digester.getMatch() + "} Call [NULL CHILD]." + this.methodName + "(" + parent + ")");
            } else {
                this.digester.getLogger().debug("[HiRule]{" + this.digester.getMatch() + "} Call " + child.getClass().getName() + "." + this.methodName + "(" + parent + ")");
            }

        }

        Class[] paramTypes = new Class[1];
        if (this.paramType != null) {
            Class paramClass = this.digester.getClassLoader().loadClass(this.paramType);
            paramTypes[0] = paramClass;

            Class parentClass = parent.getClass();
            if (!(paramClass.getName().equals(parentClass.getName()))) {
                for (int i = 1; i < this.digester.getCount(); ++i) {
                    parent = this.digester.peek(i);
                    if (paramClass.getName().equals(parent.getClass().getName())) {
                        break;
                    }
                }
            }
        } else {
            paramTypes[0] = parent.getClass();
        }

        if (this.useExactMatch) {
            MethodUtils.invokeExactMethod(child, this.methodName, new Object[]{parent}, paramTypes);
        } else {
            MethodUtils.invokeMethod(child, this.methodName, new Object[]{parent}, paramTypes);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("HiRule[");
        sb.append("methodName=");
        sb.append(this.methodName);
        sb.append(", paramType=");
        sb.append(this.paramType);
        sb.append("]");
        return sb.toString();
    }
}