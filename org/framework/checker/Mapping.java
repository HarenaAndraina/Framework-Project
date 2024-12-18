package org.framework.checker;

public class Mapping {
    private String className;
    private String methodName;
    private String url;
    private boolean isPost;
    private boolean isAuth;
    private String granted;
    

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
    
    
    public Mapping() {
    }
    public Mapping(String className, String methodName,String url) {
        this.className = className;
        this.methodName = methodName;
        this.isPost = false;
        this.isAuth=false;
        this.granted=null;
        this.url=url;
    }
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public boolean isAuth() {
        return isAuth;
    }
    public void setAuth(boolean isAuth) {
        this.isAuth = isAuth;
    }
    public String getGranted() {
        return granted;
    }
    public void setGranted(String granted) {
        this.granted = granted;
    }
    
}
