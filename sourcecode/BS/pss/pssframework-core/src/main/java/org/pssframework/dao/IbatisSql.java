package org.pssframework.dao;

public class IbatisSql {
    private String sql;//运行期的sql，带?  
    private Object[] parameters;//;//运行期的参数，与?相匹配  
    private Class resultClass;//<select id="XXX" resultType="ZZZ">中的resultType  
  
    public Class getResultClass() {  
        return resultClass;  
    }  
  
    public void setResultClass(Class resultClass) {  
        this.resultClass = resultClass;  
    }  
  
    public void setSql(String sql) {  
        this.sql = sql;  
    }  
  
    public String getSql() {  
        return sql;  
    }  
  
    public void setParameters(Object[] parameters) {  
        this.parameters = parameters;  
    }  
  
    public Object[] getParameters() {  
        return parameters;  
    } 
}
