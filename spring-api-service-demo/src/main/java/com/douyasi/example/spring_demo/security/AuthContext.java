package com.douyasi.example.spring_demo.security;

public class AuthContext {

    private static ThreadLocal<AuthUser> authUser = new ThreadLocal<>();
    
    public static void addUser(AuthUser user) {
        AuthContext.authUser.set(user);
    }


    public static AuthUser getUser() {
        return AuthContext.authUser.get();
    }
    
    public static void removeUser() {
        AuthContext.authUser.remove();
    }
}
