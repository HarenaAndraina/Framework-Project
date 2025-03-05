package org.framework.checker;

public class Mapping {
    private  String className;
    private String methodName;
    private String url;
    private boolean isPost;
    private boolean isAuth;
    private final String grantedValue;
    private boolean grantedSet;
    

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
    
    
    
    public Mapping(String className, String methodName,String url) {
        this.className = className;
        this.methodName = methodName;
        this.isPost = false;
        this.isAuth=false;
        this.grantedSet=false;
        this.url=url;
        this.grantedValue=null;
    }

    public Mapping(String className, String methodName, String url, String granted, boolean grantedSet, boolean post, boolean auth) {
        this.className = className;
        this.methodName = methodName;
        this.url = url;
        this.grantedValue = granted;
        this.grantedSet = grantedSet;
        this.isPost = post;
        this.isAuth = auth;
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
        return grantedValue;
    }
    
    public boolean isGrantedSet() {
        return grantedSet;
    }
    public void setGrantedSet(boolean grantedSet) {
        this.grantedSet = grantedSet;
    }
    
}
