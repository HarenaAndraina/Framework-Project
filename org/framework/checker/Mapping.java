package org.framework.checker;

public class Mapping {
    private String className;
    private String methodName;
    private boolean isPost;
    
    public String getClassName() {
        return className;
    }
    public String getMethodName() {
        return methodName;
    }
    public boolean isPost() {
        return isPost;
    }
    public void setPost(boolean isPost) {
        this.isPost = isPost;
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
    public Mapping(String className, String methodName, boolean isPost) {
        this.className = className;
        this.methodName = methodName;
        this.isPost = isPost;
    }
    
}
