package org.lfh.blog_demo.util;

import org.lfh.blog_demo.entity.User;

public class UserHolder {
    //    private static ThreadLocal<User> threadLocal = new ThreadLocal<User>();
//
//    public static User getUser() {
//        return threadLocal.get();
//    }
//
//    public static void setUser(User user) {
//        threadLocal.set(user);
//    }
//
//    public static void removeUser() {
//        threadLocal.remove();
//    }
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
