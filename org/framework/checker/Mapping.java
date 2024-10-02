package org.framework.checker;

import org.framework.annotation.outil.RequestMethod;

public class Mapping {
    private String className;
    private String methodName;
    private RequestMethod verbe;
    
    public String getClassName() {
        return className;
    }
    public String getMethodName() {
        return methodName;
    }
    public RequestMethod getVerbe() {
        return verbe;
    }
    public void setVerbe(RequestMethod verbe) {
        this.verbe = verbe;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    public Mapping(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }
    public Mapping() {
    }
    public Mapping(String className, String methodName, RequestMethod verbe) {
        this.className = className;
        this.methodName = methodName;
        this.verbe = verbe;
    }
   
}
