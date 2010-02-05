package com.hisun.pipeline;

import org.apache.hivemind.service.MethodSignature;

class FilterMethodAnalyzer {
    private Class _serviceInterface;

    FilterMethodAnalyzer(Class serviceInterface) {
        this._serviceInterface = serviceInterface;
    }

    public int findServiceInterfacePosition(MethodSignature ms, MethodSignature fms) {
        if (ms.getReturnType() != fms.getReturnType()) {
            return -1;
        }
        if (!(ms.getName().equals(fms.getName()))) {
            return -1;
        }
        Class[] filterParameters = fms.getParameterTypes();
        int filterParameterCount = filterParameters.length;
        Class[] serviceParameters = ms.getParameterTypes();

        if (filterParameterCount != serviceParameters.length + 1) {
            return -1;
        }

        boolean found = false;
        int result = -1;

        for (int i = 0; i < filterParameterCount; ++i) {
            if (filterParameters[i] != this._serviceInterface) continue;
            result = i;
            found = true;
            break;
        }

        if (!(found)) {
            return -1;
        }

        for (i = 0; i < result; ++i) {
            if (filterParameters[i] != serviceParameters[i]) {
                return -1;
            }
        }
        for (i = result + 1; i < filterParameterCount; ++i) {
            if (filterParameters[i] != serviceParameters[(i - 1)]) {
                return -1;
            }
        }
        return result;
    }
}