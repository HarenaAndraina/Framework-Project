package org.framework.annotation.security;

public class Role {
    private static String role;

    public static void add(String role){
        Role.role = role; // Assign to the class variable
    }

    public static String getRole() {
        return role;
    }
}
