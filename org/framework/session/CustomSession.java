package org.framework.session;

import java.util.HashMap;
import java.util.Map;

public class CustomSession {
    private Map<String, Object> sessionList = new HashMap<>();

    public void add(String key, Object value) {
        sessionList.put(key, value);
    }

    public Object get(String key) {
        if (sessionList.containsKey(key)) {
            return sessionList.get(key);
        }else{
            return null;
        }
    }

    public void update(String key, Object updatedValue) {
        if (sessionList.containsKey(key)) {
            sessionList.put(key, updatedValue);
        } else {
            // Handle case where key does not exist, if needed
            // exception session handling
        }
    }

    public void delete(String key) {
        sessionList.remove(key);
    }

    public Map<String, Object> getSessionList() {
        return sessionList;
    }
}
