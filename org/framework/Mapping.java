package org.framework.checker;

public class Mapping {
    private String className;
    private String methodName;
    
    public String getClassName() {
        return className;
    }
    public String getMethodName() {
        return methodName;
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
}
