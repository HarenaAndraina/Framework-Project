package org.framework.view;

import java.util.HashMap;

public class ModelView {
    private String url;
    private HashMap<String,Object> data=new HashMap<String,Object>();
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public HashMap<String, Object> getData() {
        return data;
    }
    public void setData(String name,Object obj) {
        getData().put(name, obj);
    }
    
    public ModelView(String url) {
        this.url = url;
    }
    public ModelView() {
    }
    
    public void addObject(String name,Object obj){
        setData(name, obj);
    }    
}
