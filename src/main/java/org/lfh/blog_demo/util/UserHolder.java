package org.lfh.blog_demo.util;

public class UserHolder {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static Integer getUser() {
        return threadLocal.get();
    }

    public static void setUser(Integer user) {
        threadLocal.set(user);
    }

    public static void removeUser() {
        threadLocal.remove();
    }
}
