package org.framework.annotation.security;

import org.framework.session.CustomSession;
import org.framework.viewScan.ViewScan;

public class Role {

    public static void add(String role){
        CustomSession session=ViewScan.getCustomSession();
        session.add("role", role);
    }

    public static String getRole() {
        CustomSession session=ViewScan.getCustomSession();
        return (String) session.get("role");
    }
}